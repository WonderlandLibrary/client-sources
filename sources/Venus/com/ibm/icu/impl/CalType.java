/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

public enum CalType {
    GREGORIAN("gregorian"),
    ISO8601("iso8601"),
    BUDDHIST("buddhist"),
    CHINESE("chinese"),
    COPTIC("coptic"),
    DANGI("dangi"),
    ETHIOPIC("ethiopic"),
    ETHIOPIC_AMETE_ALEM("ethiopic-amete-alem"),
    HEBREW("hebrew"),
    INDIAN("indian"),
    ISLAMIC("islamic"),
    ISLAMIC_CIVIL("islamic-civil"),
    ISLAMIC_RGSA("islamic-rgsa"),
    ISLAMIC_TBLA("islamic-tbla"),
    ISLAMIC_UMALQURA("islamic-umalqura"),
    JAPANESE("japanese"),
    PERSIAN("persian"),
    ROC("roc"),
    UNKNOWN("unknown");

    String id;

    private CalType(String string2) {
        this.id = string2;
    }

    public String getId() {
        return this.id;
    }
}

