package com.epam.burmensky.hospital.model.entity;

/**
 * User details entity.
 *
 * @author Burmensky Rustam
 *
 */
public class UserDetails extends DetailsEntity {

    private static final long serialVersionUID = 3548324198698584426L;

    private int userId;

    private String firstName;

    private String secondName;

    private String patronymic;

    public String getFirstName() {
        return firstName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "UserDetails [ userId=" + userId + ", langId=" + langId +
                ", firstName=" + firstName + ", secondName=" + secondName +
                ", patronymic=" + patronymic + " ]";
    }

}
