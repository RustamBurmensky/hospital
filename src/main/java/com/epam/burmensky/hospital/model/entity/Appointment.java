package com.epam.burmensky.hospital.model.entity;

import java.io.Serializable;

/**
 * Physician to patient appointment entity
 *
 * @author Burmensky Rustam
 *
 */
public class Appointment implements Serializable {

    private static final long serialVersionUID = -1788773051874183986L;

    private int userId;

    private int patientId;

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

    @Override
    public String toString() {
        return "Appointment [ userId=" + userId + ", patientId=" + patientId + " ]";
    }
}
