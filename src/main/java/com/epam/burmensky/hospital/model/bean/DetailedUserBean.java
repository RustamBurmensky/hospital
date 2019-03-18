package com.epam.burmensky.hospital.model.bean;

import com.epam.burmensky.hospital.model.entity.Entity;

import java.util.Date;

/**
 * Provide records for virtual table:
 * <pre>
 * |users_description.first_name|users_description.second_name|users_description.patronymic
 * |users.role_id|specializations_description.name|users.birthday|users.login|users.password|
 * </pre>
 *
 * @author Rustam Burmensky
 *
 */
public class DetailedUserBean extends Entity {

    private static final long serialVersionUID = 7290358340440638204L;

    private String firstName;

    private String secondName;

    private String patronymic;

    private byte roleId;

    private String specializationName;

    private Date birthday;

    private String login;

    private String password;

    private int patientsNumber;

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

    public byte getRoleId() {
        return roleId;
    }

    public void setRoleId(byte roleId) {
        this.roleId = roleId;
    }

    public String getSpecializationName() {
        return specializationName;
    }

    public void setSpecializationName(String specializationName) {
        this.specializationName = specializationName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPatientsNumber() {
        return patientsNumber;
    }

    public void setPatientsNumber(int patientsNumber) {
        this.patientsNumber = patientsNumber;
    }

    @Override
    public String toString() {
        return "DetailedUserBean [ firstName=" + firstName + ", secondName=" + secondName +
                ", patronymic=" + patronymic + ", birthday=" + birthday + ", roleId=" + roleId +
                ", specializationName=" + specializationName + ", login=" + login +
                ", password=" + password + ", patientsNumber=" + patientsNumber + " ]";
    }

}
