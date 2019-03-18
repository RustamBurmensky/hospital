package com.epam.burmensky.hospital.model.entity;

/**
 * Specialization details entity.
 *
 * @author Burmensky Rustam
 *
 */
public class SpecializationDetails extends DetailsEntity {

    private static final long serialVersionUID = -3599620018181140434L;

    private int specializationId;

    private byte langId;

    private String name;

    public int getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(int specializationId) {
        this.specializationId = specializationId;
    }

    @Override
    public byte getLangId() {
        return langId;
    }

    @Override
    public void setLangId(byte langId) {
        this.langId = langId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
