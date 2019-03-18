package com.epam.burmensky.hospital.model.bean;

import com.epam.burmensky.hospital.model.entity.Entity;

import java.util.Date;

/**
 * Provide records for virtual table:
 * <pre>
 * |appointments.patient_id|patients_description.first_name|patients_description.second_name
 * |patients_description.patronymic|patients.birthday|
 * </pre>
 *
 * @author Rustam Burmensky
 *
 */
public class PatientAppointmentBean extends Entity {

    private static final long serialVersionUID = 6132984403716505322L;

    private String firstName;

    private String secondName;

    private String patronymic;

    private Date birthday;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
