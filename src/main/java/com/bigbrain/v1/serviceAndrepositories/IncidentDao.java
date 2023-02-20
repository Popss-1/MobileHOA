package com.bigbrain.v1.serviceAndrepositories;
import java.util.Date;
import java.util.List;
import com.bigbrain.v1.models.Incidents;

public interface IncidentDao {

    int save(Incidents incident);

    List<Incidents>findAll();

    List<Incidents>findAllByID(int userIDFK);

    Incidents findIncidentByPK(int incidentIDPK);

    int deleteById(int incidentIDPK);

    int deleteByStatus(String incidentStatus);

    int deleteByDate(Date incidentDate);

    int updateById(Incidents incident, int incidentIDPK);



}
