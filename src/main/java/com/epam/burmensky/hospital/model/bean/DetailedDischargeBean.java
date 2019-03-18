package com.epam.burmensky.hospital.model.bean;

import com.epam.burmensky.hospital.model.entity.Entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Provide records for virtual table:
 * <pre>
 * |discharges.patient_id|patients_description.first_name|patients_description.second_name
 * |patients_description.patronymic|discharges.date|discharges_description.diagnosis|
 * </pre>
 *
 * @author Rustam Burmensky
 *
 */
public class DetailedDischargeBean extends Entity {

    private static final long serialVersionUID = -1317200444415980507L;

    private int patientId;

    private String patientFirstName;

    private String patientSecondName;

    private String patientPatronymic;

    private Date date;

    private String diagnosis;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientSecondName() {
        return patientSecondName;
    }

    public void setPatientSecondName(String patientSecondName) {
        this.patientSecondName = patientSecondName;
    }

    public String getPatientPatronymic() {
        return patientPatronymic;
    }

    public void setPatientPatronymic(String patientPatronymic) {
        this.patientPatronymic = patientPatronymic;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
}
