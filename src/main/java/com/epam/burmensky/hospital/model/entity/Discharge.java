package com.epam.burmensky.hospital.model.entity;

import java.util.Date;

/**
 * Hospital discharge entity.
 *
 * @author Burmensky Rustam
 *
 */
public class Discharge extends Entity {

    private static final long serialVersionUID = 6773606029979767036L;

    private int patientId;

    private Date date;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Discharge [ patientId=" + patientId + ", date=" + date + " ]";
    }

}
