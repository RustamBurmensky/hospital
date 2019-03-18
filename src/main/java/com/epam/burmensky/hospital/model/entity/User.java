package com.epam.burmensky.hospital.model.entity;

import java.util.Date;

/**
 * User entity.
 *
 * @author Burmensky Rustam
 *
 */
public class User extends Entity {

    private static final long serialVersionUID = 5756092298159895383L;

    private byte roleId;

    private int specializationId;

    private byte langId;

    private Date birthday;

    private String login;

    private String password;

    public byte getRoleId() {
        return roleId;
    }

    public void setRoleId(byte roleId) {
        this.roleId = roleId;
    }

    public int getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(int specializationId) {
        this.specializationId = specializationId;
    }

    public byte getLangId() {
        return langId;
    }

    public void setLangId(byte langId) {
        this.langId = langId;
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

    @Override
    public String toString() {
        return "User [ birthday=" + birthday + ", roleId=" + roleId +
                ", specializationId=" + specializationId + ", langId=" + getLangId() +
                ", login=" + login + ", password=" + password + " ]";
    }

}
