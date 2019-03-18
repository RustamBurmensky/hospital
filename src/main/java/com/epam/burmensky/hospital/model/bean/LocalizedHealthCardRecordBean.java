package com.epam.burmensky.hospital.model.bean;

import com.epam.burmensky.hospital.model.entity.HealthCardRecord;
import com.epam.burmensky.hospital.model.entity.HealthCardRecordDetails;

import java.util.List;

/**
 * HealthCardRecord entity with localized text.
 *
 * @author Rustam Burmensky
 *
 */
public class LocalizedHealthCardRecordBean extends HealthCardRecord {

    private static final long serialVersionUID = -8015607721458974273L;

    public LocalizedHealthCardRecordBean() {}

    public LocalizedHealthCardRecordBean(HealthCardRecord record, List<HealthCardRecordDetails> recordDetails) {
        this.setId(record.getId());
        this.setUserId(record.getUserId());
        this.setPatientId(record.getPatientId());
        this.setDate(record.getDate());
        this.setRecordTypeId(record.getRecordTypeId());
        this.setHealthCardRecordDetails(recordDetails);
    }

    private List<HealthCardRecordDetails> healthCardRecordDetails;

    public List<HealthCardRecordDetails> getHealthCardRecordDetails() {
        return healthCardRecordDetails;
    }

    public void setHealthCardRecordDetails(List<HealthCardRecordDetails> healthCardRecordDetails) {
        this.healthCardRecordDetails = healthCardRecordDetails;
    }
}
