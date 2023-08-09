package it.unipi.dstm;

import javax.ejb.Stateless;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Stateless
public class DataServersRemoteEJB implements DataServersInterface {
    List<EarthquakeDTO> list1=new ArrayList<>();
    @Override
    public List<EarthquakeDTO> collectServersData(java.sql.Date startDate,java.sql.Date endDate) {
        try {
            list1 = dataCollection("172.18.0.66", "regional1",startDate,endDate,"region1");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            list1.addAll(dataCollection("172.18.0.65", "regional2",startDate,endDate, "region2"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            list1.addAll(dataCollection("172.18.0.67", "regional3",startDate,endDate, "region3"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list1;
    }

    public List<EarthquakeDTO> dataCollection (String IP, String dbName,java.sql.Date startDate, java.sql.Date endDate, String region) throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://"+IP+":3306/"+dbName+"?autoReconnect=true&useSSL=false";
        String user = "user1";
        String password = "admin";
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, user, password);
        ResultSet rs = null;
        List<EarthquakeDTO> earthquakeDTOS=new ArrayList<>();

        PreparedStatement pstm = null;
        List<java.sql.Date> params = new ArrayList<>();

        try{

            StringBuilder sqlStringBuilder=new StringBuilder();
            sqlStringBuilder.append("select " );
            sqlStringBuilder.append("  e.magnitude,  ");
            sqlStringBuilder.append("  e.latitude,  ");
            sqlStringBuilder.append("  e.longitude,  ");
            sqlStringBuilder.append("  e.depth,  ");
            sqlStringBuilder.append("  e.date  ");
            sqlStringBuilder.append(" from earthquakedata e ");
            sqlStringBuilder.append("  where 1 = 1  ");
            if(startDate!=null && !Objects.equals(startDate.toString(), ""))
            {
                sqlStringBuilder.append(" and e.date >= ? ");
                params.add(startDate);;

            }
            if (endDate !=null && !Objects.equals(endDate.toString(), ""))
            {
                sqlStringBuilder.append(" and e.date <= ? ");
                params.add(endDate);

            }



            pstm = conn.prepareStatement(sqlStringBuilder.toString());
            for (int i = 0; i < params.size(); i++) {
                int k=i;
                pstm.setDate(k+1, params.get(i));
            }

            rs = pstm.executeQuery();
            while (rs.next()) {
                EarthquakeDTO earthquakeDTO=new EarthquakeDTO();
                earthquakeDTO.setMagnitude(rs.getDouble(1));
                earthquakeDTO.setLatitude(rs.getDouble(2));
                earthquakeDTO.setLongitude(rs.getDouble(3));
                earthquakeDTO.setDepth(rs.getDouble(4));
                earthquakeDTO.setDate(new Date(rs.getTimestamp(5).getTime()));
                earthquakeDTO.setRegion(region);
                earthquakeDTOS.add(earthquakeDTO);
            }

        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return earthquakeDTOS;
    }
}

