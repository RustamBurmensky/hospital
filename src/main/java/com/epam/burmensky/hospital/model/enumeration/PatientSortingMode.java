package com.epam.burmensky.hospital.model.enumeration;

import java.util.Locale;
import java.util.ResourceBundle;

public enum PatientSortingMode {

    ALPHABETICAL((byte)1," ORDER BY patients_description.second_name," +
            " patients_description.first_name, patients_description.patronymic"),
    ALPHABETICAL_DESC((byte)2, " ORDER BY patients_description.second_name DESC," +
            " patients_description.first_name DESC, patients_description.patronymic DESC"),
    BY_BIRTHDAY((byte)3," ORDER BY patients.birthday"),
    BY_BIRTHDAY_DESC((byte)4," ORDER BY patients.birthday DESC");

    private static final String resourceBundlePath = "resources";

    private byte sortModeId;
    private String sqlOrderBy;

    PatientSortingMode(byte sortModeId, String sqlOrderBy) {
        this.sortModeId = sortModeId;
        this.sqlOrderBy = sqlOrderBy;
    }

    public byte getSortModeId() { return this.sortModeId; }

    public String getSQLOrderBy() {
        return this.sqlOrderBy;
    }

    public String getLocalizedName(Locale locale)
    {
        ResourceBundle bundle = ResourceBundle.getBundle(resourceBundlePath, locale);
        return bundle.getString(this.name());
    }

    public static PatientSortingMode getSortingMode(byte sortingModeId) {
        return PatientSortingMode.values()[sortingModeId - 1];
    }

}
