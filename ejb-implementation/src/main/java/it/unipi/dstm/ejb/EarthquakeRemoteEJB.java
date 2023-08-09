package it.unipi.dstm.ejb;

import it.unipi.dstm.EarthquakeDTO;
import it.unipi.dstm.EarthquakeInterface;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Stateless
public class EarthquakeRemoteEJB  implements EarthquakeInterface {


    @Resource(lookup="jdbc/Regional1Pool")
    private DataSource dataSource;

    @Override
    public List<EarthquakeDTO> listEarthquakes(java.sql.Date startDate,java.sql.Date endDate) {

        Connection connection=null;
        ResultSet rs=null;
        PreparedStatement pstm=null;
        List<EarthquakeDTO> earthquakeDTOS=new ArrayList<>();
        List<java.sql.Date> params = new ArrayList<>();
        try {

            connection=dataSource.getConnection();
            StringBuilder sqlStringBuilder=new StringBuilder();
            sqlStringBuilder.append("select " );
            sqlStringBuilder.append("  e.magnitude,  ");
            sqlStringBuilder.append("  e.latitude,  ");
            sqlStringBuilder.append("  e.longitude,  ");
            sqlStringBuilder.append("  e.depth,  ");
            sqlStringBuilder.append("  e.date  ");
            sqlStringBuilder.append(" from earthquakedata e ");
            sqlStringBuilder.append("  where 1 = 1  ");
            if(startDate!=null)
            {
                sqlStringBuilder.append(" and e.date >= ? ");
                params.add(startDate);

            }
            if (endDate !=null)
            {
                sqlStringBuilder.append(" and e.date <= ? ");
                params.add(endDate);

            }


            pstm=connection.prepareStatement(sqlStringBuilder.toString());
            System.out.println("PARAMS:" + params);
            System.out.println("PARAMS SIZE:" + params.size());

            for (int i = 0; i < params.size(); i++) {
                int k=i;
                pstm.setDate(k+1, params.get(i));
            }
            System.out.println(pstm.toString());

            rs=pstm.executeQuery();

            rs.next();
            System.out.println("RS DOUBLE: " + rs.getDouble(1));

            while (rs.next())
            {
                EarthquakeDTO earthquakeDTO=new EarthquakeDTO();
                earthquakeDTO.setMagnitude(rs.getDouble(1));
                earthquakeDTO.setLatitude(rs.getDouble(2));
                earthquakeDTO.setLongitude(rs.getDouble(3));
                earthquakeDTO.setDepth(rs.getDouble(4));
//                earthquakeDTO.setD(rs.getDouble(4));
                earthquakeDTO.setDate(new Date(rs.getTimestamp(5).getTime()));

                earthquakeDTOS.add(earthquakeDTO);

                System.out.println("MAGNITUDE: "+ rs.getDouble(1));

            }

        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        try {
            assert rs != null;
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /*EarthquakeDTO dto=new EarthquakeDTO();
        dto.setMagnitude(5.5);
        earthquakeDTOS.add(dto);*/

        return earthquakeDTOS;
    }
}
