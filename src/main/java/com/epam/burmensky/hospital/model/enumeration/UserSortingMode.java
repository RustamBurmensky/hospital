package com.epam.burmensky.hospital.model.enumeration;

import com.epam.burmensky.hospital.Path;

import java.util.Locale;
import java.util.ResourceBundle;

public enum UserSortingMode {

    ALPHABETICAL((byte)1, " ORDER BY users_description.second_name, users_description.first_name, users_description.patronymic"),
    ALPHABETICAL_DESC((byte)2, " ORDER BY users_description.second_name DESC, users_description.first_name DESC, users_description.patronymic DESC"),
    BY_SPECIALIZATION((byte)3," ORDER BY specializations_description.name"),
    BY_SPECIALIZATION_DESC((byte)4," ORDER BY specializations_description.name DESC"),
    BY_PATIENTS_NUMBER((byte)5, " ORDER BY patients_number"),
    BY_PATIENTS_NUMBER_DESC((byte)6, " ORDER BY patients_number DESC");

    private String sqlOrderBy;
    private byte sortModeId;

    UserSortingMode(byte sortModeId, String sqlOrderBy) {
        this.sortModeId = sortModeId;
        this.sqlOrderBy = sqlOrderBy;
    }

    public String getSQLOrderBy() {
        return this.sqlOrderBy;
    }

    public byte getSortModeId() { return this.sortModeId; }

    public String getLocalizedName(Locale locale)
    {
        ResourceBundle bundle = ResourceBundle.getBundle(Path.RESOURCE_BUNDLE, locale);
        return bundle.getString(this.name());
    }

    public static UserSortingMode getSortingMode(byte sortingModeId) {
        return UserSortingMode.values()[sortingModeId - 1];
    }

}
