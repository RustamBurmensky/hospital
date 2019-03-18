package com.epam.burmensky.hospital.model.entity;

/**
 * Health card record details entity.
 *
 * @author Burmensky Rustam
 *
 */
public class HealthCardRecordDetails extends DetailsEntity {

    private static final long serialVersionUID = 6904948101395359916L;

    private int recordId;

    private String text;

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "HealthCardRecordDetails [ recordId=" + recordId + ", langId=" + langId +
                ", text=" + text + " ]";
    }

}
