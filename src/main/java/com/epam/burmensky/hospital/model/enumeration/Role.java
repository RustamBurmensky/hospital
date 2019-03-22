package com.epam.burmensky.hospital.model.enumeration;

import com.epam.burmensky.hospital.Path;
import com.epam.burmensky.hospital.model.bean.DetailedUserBean;

import java.util.Locale;
import java.util.ResourceBundle;

public enum Role {

    ADMIN((byte)1),
    PHYSICIAN((byte)2),
    NURSE((byte)3);

    private byte roleId;

    Role(byte roleId) {
        this.roleId = roleId;
    }

    public byte getRoleId() { return this.roleId; }

    public String getLocalizedName(Locale locale)
    {
        ResourceBundle bundle = ResourceBundle.getBundle(Path.RESOURCE_BUNDLE, locale);
        return bundle.getString(this.name());
    }

    public static Role getRole(Byte roleId) {
        return Role.values()[roleId - 1];
    }

    public static Role getRole(DetailedUserBean user) {
        int roleId = user.getRoleId();
        return Role.values()[roleId - 1];
    }

    public String getName() {
        return name().toLowerCase();
    }

}
