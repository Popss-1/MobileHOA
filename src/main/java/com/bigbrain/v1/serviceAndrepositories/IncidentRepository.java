package com.bigbrain.v1.serviceAndrepositories;

import com.bigbrain.v1.models.Incidents;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class IncidentRepository implements IncidentDao{

    private final JdbcTemplate jdbc;
    public IncidentRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int save(Incidents incident) {
        return jdbc.update("INSERT INTO Incidents(IncidentCategory, Description, UserIDFK, ReportedByPhoneNumber, IncidentStatus, Latitude, longitude, Title, IncidentDate) Values(?,?,?,?,?,?,?,?,?)",
                incident.getIncidentCategory(), incident.getDescription(), incident.getUserIDFK(), incident.getReportedByPhoneNumber(), incident.getIncidentStatus(), incident.getLatitude(),
                incident.getLongitude(), incident.getTitle(), incident.getIncidentDate());
    }

    @Override
    public List<Incidents> findAll() {
        try{
            return jdbc.query("SELECT IncidentIdPK,IncidentCategory, Description, UserIDFK, ReportedByPhoneNumber, IncidentStatus, Latitude, longitude, Title, IncidentDate FROM Incidents",
                    new BeanPropertyRowMapper<Incidents>(Incidents.class));
        }
        catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public int deleteById(int incidentIDPK) {
        try{
            return jdbc.update("DELETE FROM Incidents WHERE IncidentIdPK=?");
        }
        catch(EmptyResultDataAccessException e){
            return 0;
        }
    }

    @Override
    public int deleteByStatus(String incidentStatus) {
        try{
            return jdbc.update("DELETE FROM Incidents WHERE IncidentStatus=?");
        }
        catch(EmptyResultDataAccessException e){
            return 0;
        }
    }

    @Override
    public int deleteByDate(Date incidentDate) {
        try{
            return jdbc.update("DELETE FROM Incidents WHERE IncidentDate=?");
        }
        catch(EmptyResultDataAccessException e){
            return 0;
        }
    }

    @Override
    public int updateById(Incidents incident, int incidentIDPK) {
        try{
            return jdbc.update("UPDATE Incidents SET  IncidentCategory=?, Description=?, ReportedByPhoneNumber=?, IncidentStatus=?, Latitude=?, Longitude=?, Title=?, IncidentDate=? WHERE IncidentIdPK=? ",
                    incident.getIncidentCategory(), incident.getDescription(), incident.getReportedByPhoneNumber(), incident.getIncidentStatus(), incident.getLatitude(), incident.getLongitude(), incident.getTitle(), incident.getIncidentDate());
        }
        catch(EmptyResultDataAccessException e){
            return 0;
        }
    }
}