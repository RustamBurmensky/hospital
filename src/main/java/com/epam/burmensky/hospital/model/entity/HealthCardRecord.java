package com.epam.burmensky.hospital.model.entity;

import java.util.Date;

/**
 * Health card record entity.
 *
 * @author Burmensky Rustam
 *
 */
public class HealthCardRecord extends Entity {

    private static final long serialVersionUID = -7421536554134297911L;

    private int userId;

    private int patientId;

    private Date date;

    private byte recordTypeId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

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

    public byte getRecordTypeId() {
        return recordTypeId;
    }

    public void setRecordTypeId(byte recordTypeId) {
        this.recordTypeId = recordTypeId;
    }

    @Override
    public String toString() {
        return "HealthCardRecord [ userId=" + userId + ", patientId=" + patientId +
                ", date=" + date + ", recordTypeId=" + recordTypeId + " ]";
    }

}
