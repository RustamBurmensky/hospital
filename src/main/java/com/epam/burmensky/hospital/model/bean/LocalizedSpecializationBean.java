package com.epam.burmensky.hospital.model.bean;

import com.epam.burmensky.hospital.model.entity.Specialization;
import com.epam.burmensky.hospital.model.entity.SpecializationDetails;

import java.util.List;

/**
 * Specialization entity with localized name.
 *
 * @author Rustam Burmensky
 *
 */
public class LocalizedSpecializationBean extends Specialization {

    private static final long serialVersionUID = 5372272012943934977L;

    public LocalizedSpecializationBean() {}

    public LocalizedSpecializationBean(Specialization specialization, List<SpecializationDetails> specializationDetails) {
        this.setId(specialization.getId());
        this.setSpecializationDetails(specializationDetails);
    }

    private List<SpecializationDetails> specializationDetails;

    public List<SpecializationDetails> getSpecializationDetails() {
        return specializationDetails;
    }

    public void setSpecializationDetails(List<SpecializationDetails> specializationDetails) {
        this.specializationDetails = specializationDetails;
    }
}
