package com.epam.burmensky.hospital.model.entity;

/**
 * Patient details entity.
 *
 * @author Burmensky Rustam
 *
 */
public class PatientDetails extends DetailsEntity {

    private static final long serialVersionUID = -807158022227841329L;

    private int patientId;

    private String firstName;

    private String secondName;

    private String patronymic;

    private String address;

    private String occupation;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
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

    @Override
    public String toString() {
        return "PatientDetails [ patientId=" + patientId + ", langId=" + langId +
                ", firstName=" + firstName + ", secondName=" + secondName +
                ", patronymic=" + patronymic + ", address=" + address +
                ", occupation=" + occupation + " ]";
    }


}
