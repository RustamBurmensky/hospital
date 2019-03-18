package com.epam.burmensky.hospital.model.bean;

import com.epam.burmensky.hospital.model.entity.Entity;

import java.util.Date;

/**
 * Provide records for virtual table:
 * <pre>
 * |users_description.second_name|users_description.first_name|users_description.patronymic|
 * |specialization_description.name|health_card_records.user_id|health_card_records.patient_id|
 * |health_card_records.date|health_card_records.record_type_id|health_card_records_description.text|
 * </pre>
 *
 * @author Rustam Burmensky
 *
 */
public class DetailedHealthCardRecordBean extends Entity {

    private static final long serialVersionUID = -8201343339548840550L;

    private int userId;

    private String physicianFirstName;

    private String physicianSecondName;

    private String physicianPatronymic;

    private String specializationName;

    private int patientId;

    private Date date;

    private byte recordTypeId;

    private String text;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPhysicianFirstName() {
        return physicianFirstName;
    }

    public void setPhysicianFirstName(String physicianFirstName) {
        this.physicianFirstName = physicianFirstName;
    }

    public String getPhysicianSecondName() {
        return physicianSecondName;
    }

    public void setPhysicianSecondName(String physicianSecondName) {
        this.physicianSecondName = physicianSecondName;
    }

    public String getPhysicianPatronymic() {
        return physicianPatronymic;
    }

    public void setPhysicianPatronymic(String physicianPatronymic) {
        this.physicianPatronymic = physicianPatronymic;
    }

    public String getSpecializationName() {
        return specializationName;
    }

    public void setSpecializationName(String specializationName) {
        this.specializationName = specializationName;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "DetailedHealthRecordBean [ userId=" + userId + ", physicianFirstName=" + physicianFirstName +
                ", physicianSecondName=" + physicianSecondName + ", physicianPatronymic=" + physicianPatronymic +
                ", patientId=" + patientId + ", date=" + date + ", recordTypeId=" + recordTypeId +
                ", text=" + text + " ]";
    }

}
