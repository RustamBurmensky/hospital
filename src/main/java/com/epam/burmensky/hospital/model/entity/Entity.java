package com.epam.burmensky.hospital.model.entity;

import java.io.Serializable;

/**
 * Class hierarchy root of entities which have identifier field.
 *
 * @author Rustam Burmensky
 *
 */
public abstract class Entity implements Serializable {

    private static final long serialVersionUID = -5721514566104405875L;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
