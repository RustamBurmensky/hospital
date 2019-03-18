package com.epam.burmensky.hospital.model.bean;

import com.epam.burmensky.hospital.model.entity.Entity;

import java.util.Date;

/**
 * Provide records for virtual table:
 * <pre>
 * |patients_description.first_name|patients_description.second_name
 * |patients_description.patronymic|patients.birthday|patients.weight|patients.height|
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

    private Date birthday;

    private short weight;

    private short height;

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

    @Override
    public String toString() {
        return "DetailedPatientBean [ firstName=" + firstName + ", secondName=" + secondName +
                ", patronymic=" + patronymic + ", birthday=" + birthday +
                ", weight=" + weight + ", height=" + height + " ]";
    }

}
