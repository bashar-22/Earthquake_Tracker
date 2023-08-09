package it.unipi.dstm;

import javax.ejb.Remote;
import java.sql.Date;
import java.util.List;

@Remote
public interface DataServersInterface {
    public List<EarthquakeDTO> collectServersData (Date startDate, Date endDate);
}
