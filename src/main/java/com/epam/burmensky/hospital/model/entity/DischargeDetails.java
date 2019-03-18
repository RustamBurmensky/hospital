package com.epam.burmensky.hospital.model.entity;

/**
 * Hospital discharge details entity.
 *
 * @author Burmensky Rustam
 *
 */
public class DischargeDetails extends DetailsEntity {

    private static final long serialVersionUID = -5504924533332781867L;

    private int dischargeId;

    private String diagnosis;

    public int getDischargeId() {
        return dischargeId;
    }

    public void setDischargeId(int dischargeId) {
        this.dischargeId = dischargeId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    @Override
    public String toString() {
        return "DischargeDetails [ dischargeId=" + dischargeId + ", langId=" + langId +
                ", diagnosis=" + diagnosis + " ]";
    }
}
