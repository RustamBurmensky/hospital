package com.epam.burmensky.hospital.model.bean;

import com.epam.burmensky.hospital.model.entity.Entity;

import java.util.Date;

/**
 * Provide records for virtual table:
 * <pre>
 * |patients_description.first_name|patients_description.second_name
 * |patients_description.patronymic|patients_description.address|patients_description.occupation
 * |patients.birthday|patients.weight|patients.height|
 * </pre>
 *
 * @author Rustam Burmensky
 *
 */
public class DetailedPatientBean extends Entity {

    private static final long serialVersionUID = 2631289408369298116L;

    private String firstName;

    private String secondName;

    private String patronymic;

    private String address;

    private String occupation;

    private Date birthday;

    private short weight;

    private short height;

    private Date admissionDate;

    private boolean inpatient;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public short getWeight() {
        return weight;
    }

    public void setWeight(short weight) {
        this.weight = weight;
    }

    public short getHeight() {
        return height;
    }

    public void setHeight(short height) {
        this.height = height;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }

    public boolean isInpatient() {
        return inpatient;
    }

    public void setInpatient(boolean inpatient) {
        this.inpatient = inpatient;
    }

    @Override
    public String toString() {
        return "DetailedPatientBean [ firstName=" + firstName + ", secondName=" + secondName +
                ", patronymic=" + patronymic + ", address=" + address + ", occupation=" + occupation +
                ", birthday=" + birthday + ", weight=" + weight + ", height=" + height +
                ", admissionDate=" + admissionDate + ", inpatient=" + inpatient + " ]";
    }

}
