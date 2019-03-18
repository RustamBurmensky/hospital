package com.epam.burmensky.hospital.model.entity;

import java.io.Serializable;

/**
 * Class hierarchy root of detail entities.
 *
 * @author Rustam Burmensky
 *
 */
public abstract class DetailsEntity implements Serializable {

    private static final long serialVersionUID = -4412725386805599501L;

    protected byte langId;

    public byte getLangId() {
        return langId;
    }

    public void setLangId(byte langId) {
        this.langId = langId;
    }
}
