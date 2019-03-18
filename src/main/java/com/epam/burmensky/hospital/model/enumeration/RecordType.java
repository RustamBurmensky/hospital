package com.epam.burmensky.hospital.model.enumeration;

import java.util.Locale;
import java.util.ResourceBundle;

public enum RecordType {

    DIAGNOSIS((byte)1),
    PRESCRIPTION((byte)2),
    PROCEDURE((byte)3),
    SURGERY((byte)4);

    private static final String resourceBundlePath = "resources";

    private byte recordTypeId;

    RecordType(byte recordTypeId) {
        this.recordTypeId = recordTypeId;
    }

    public byte getRecordTypeId() { return this.recordTypeId; }

    public String getLocalizedName(Locale locale)
    {
        ResourceBundle bundle = ResourceBundle.getBundle(resourceBundlePath, locale);
        return bundle.getString(this.name());
    }

    public static RecordType getRecordType(Byte recordTypeId) {
        return RecordType.values()[recordTypeId - 1];
    }

}
