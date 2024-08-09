/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net;

import org.apache.logging.log4j.util.EnglishEnums;

public enum Facility {
    KERN(0),
    USER(1),
    MAIL(2),
    DAEMON(3),
    AUTH(4),
    SYSLOG(5),
    LPR(6),
    NEWS(7),
    UUCP(8),
    CRON(9),
    AUTHPRIV(10),
    FTP(11),
    NTP(12),
    LOG_AUDIT(13),
    LOG_ALERT(14),
    CLOCK(15),
    LOCAL0(16),
    LOCAL1(17),
    LOCAL2(18),
    LOCAL3(19),
    LOCAL4(20),
    LOCAL5(21),
    LOCAL6(22),
    LOCAL7(23);

    private final int code;

    private Facility(int n2) {
        this.code = n2;
    }

    public static Facility toFacility(String string) {
        return Facility.toFacility(string, null);
    }

    public static Facility toFacility(String string, Facility facility) {
        return EnglishEnums.valueOf(Facility.class, string, facility);
    }

    public int getCode() {
        return this.code;
    }

    public boolean isEqual(String string) {
        return this.name().equalsIgnoreCase(string);
    }
}

