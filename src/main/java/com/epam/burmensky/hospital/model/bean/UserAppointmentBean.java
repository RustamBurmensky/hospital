package com.epam.burmensky.hospital.model.bean;

import com.epam.burmensky.hospital.model.entity.Entity;

import java.util.Date;

/**
 * Provide records for virtual table:
 * <pre>
 * |appointments.user_id|users.role_id|specializations_description.name|users.birthday
 * |users_description.first_name|users_description.second_name|users_description.patronymic|
 * </pre>
 *
 * @author Rustam Burmensky
 *
 */
public class UserAppointmentBean extends Entity {

    private static final long serialVersionUID = 1451132296064479846L;

    private byte roleId;

    private String specializationName;

    private Date birthday;

    private String firstName;

    private String secondName;

    private String patronymic;

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
}
