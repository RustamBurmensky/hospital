package com.epam.burmensky.hospital.model.enumeration;

import com.epam.burmensky.hospital.model.entity.DetailsEntity;

import java.util.Locale;
import java.util.ResourceBundle;

public enum Language {
    LANG_EN((byte)1, Locale.ENGLISH),
    LANG_UA((byte)2, new Locale("uk")),
    LANG_RU((byte)3, new Locale("ru"));

    private byte langId;
    private Locale locale;

    private static final String resourceBundlePath = "resources";

    Language(byte langId, Locale locale) {
        this.langId = langId;
        this.locale = locale;
    }

    public byte getLangId() {
        return this.langId;
    }

    public Locale getLocale() { return this.locale; }

    public String getLangName() { return name().toLowerCase(); }

    public String getLocalizedName(Locale locale)
    {
        ResourceBundle bundle = ResourceBundle.getBundle(resourceBundlePath, locale);
        return bundle.getString(this.name());
    }

    public static Language getLanguage(byte languageId) {
        return Language.values()[languageId - 1];
    }

    public static Language languageFromLocale(Locale locale) {
        Language result = Language.LANG_EN;
        for (Language lang : Language.values()) {
            if (lang.getLocale().getLanguage().equals(locale.getLanguage())) {
                result = lang;
            }
        }
        return result;
    }

    public static Language getLanguage(DetailsEntity entity) {
        byte langId = entity.getLangId();
        return Language.values()[langId];
    }

}
