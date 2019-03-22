package com.epam.burmensky.hospital.model.bean;

import com.epam.burmensky.hospital.model.entity.Patient;
import com.epam.burmensky.hospital.model.entity.PatientDetails;

import java.util.List;

/**
 * Patient entity with localized full name.
 *
 * @author Rustam Burmensky
 *
 */
public class LocalizedPatientBean extends Patient {

    private static final long serialVersionUID = -8437671206295292061L;

    public LocalizedPatientBean() {}

    public LocalizedPatientBean(Patient patient, List<PatientDetails> patientDetails) {
        this.setId(patient.getId());
        this.setBirthday(patient.getBirthday());
        this.setWeight(patient.getWeight());
        this.setHeight(patient.getHeight());
        this.setAdmissionDate(patient.getAdmissionDate());
        this.setInpatient(patient.isInpatient());
        this.setPatientDetails(patientDetails);
    }

    private List<PatientDetails> patientDetails;

    public List<PatientDetails> getPatientDetails() {
        return patientDetails;
    }

    public void setPatientDetails(List<PatientDetails> patientDetails) {
        this.patientDetails = patientDetails;
    }

}
