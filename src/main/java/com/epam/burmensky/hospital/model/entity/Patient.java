package com.epam.burmensky.hospital.model.entity;

import java.util.Date;

/**
 * Patient entity.
 *
 * @author Burmensky Rustam
 *
 */
public class Patient extends Entity {

    private static final long serialVersionUID = -7009683436309850609L;

    private Date birthday;

    private short weight;

    private short height;

    private Date admissionDate;

    private boolean inpatient;

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
        return "Patient [ birthday=" + birthday + ", weight=" + weight +
                ", height=" + height + ", admissionDate=" + admissionDate +
                ", inpatient=" + inpatient + " ]";
    }
}
