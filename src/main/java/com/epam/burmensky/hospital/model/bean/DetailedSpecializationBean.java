package com.epam.burmensky.hospital.model.bean;

import com.epam.burmensky.hospital.model.entity.Entity;

/**
 * Provide records for virtual table:
 * <pre>
 * |specializations.id|specializations_description.name|
 * </pre>
 *
 * @author Rustam Burmensky
 *
 */
public class DetailedSpecializationBean extends Entity {

    private static final long serialVersionUID = -5141063264937655537L;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DetailedSpecializationBean [ name=" + name + " ]";
    }
}
