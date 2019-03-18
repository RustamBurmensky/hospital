package com.epam.burmensky.hospital.model.bean;

import com.epam.burmensky.hospital.model.entity.Discharge;
import com.epam.burmensky.hospital.model.entity.DischargeDetails;

import java.util.List;

/**
 * Discharge entity with localized diagnosis.
 *
 * @author Rustam Burmensky
 *
 */
public class LocalizedDischargeBean extends Discharge {

    private static final long serialVersionUID = 2449222846374380429L;

    private List<DischargeDetails> dischargeDetails;

    public List<DischargeDetails> getDischargeDetails() {
        return dischargeDetails;
    }

    public LocalizedDischargeBean() {}

    public LocalizedDischargeBean(Discharge discharge, List<DischargeDetails> dischargeDetails) {
        this.setId(discharge.getId());
        this.setPatientId(discharge.getPatientId());
        this.setDate(discharge.getDate());
        this.setDischargeDetails(dischargeDetails);
    }

    public void setDischargeDetails(List<DischargeDetails> dischargeDetails) {
        this.dischargeDetails = dischargeDetails;
    }
}
