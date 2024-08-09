/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.locale;

import java.util.Objects;

public final class LSR {
    public static final int REGION_INDEX_LIMIT = 1677;
    public static final boolean DEBUG_OUTPUT = false;
    public final String language;
    public final String script;
    public final String region;
    final int regionIndex;

    public LSR(String string, String string2, String string3) {
        this.language = string;
        this.script = string2;
        this.region = string3;
        this.regionIndex = LSR.indexForRegion(string3);
    }

    public static final int indexForRegion(String string) {
        if (string.length() == 2) {
            int n = string.charAt(0) - 65;
            if (n < 0 || 25 < n) {
                return 1;
            }
            int n2 = string.charAt(1) - 65;
            if (n2 < 0 || 25 < n2) {
                return 1;
            }
            return 26 * n + n2 + 1001;
        }
        if (string.length() == 3) {
            int n = string.charAt(0) - 48;
            if (n < 0 || 9 < n) {
                return 1;
            }
            int n3 = string.charAt(1) - 48;
            if (n3 < 0 || 9 < n3) {
                return 1;
            }
            int n4 = string.charAt(2) - 48;
            if (n4 < 0 || 9 < n4) {
                return 1;
            }
            return (10 * n + n3) * 10 + n4 + 1;
        }
        return 1;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.language);
        if (!this.script.isEmpty()) {
            stringBuilder.append('-').append(this.script);
        }
        if (!this.region.isEmpty()) {
            stringBuilder.append('-').append(this.region);
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        if (object.getClass() != this.getClass()) return false;
        LSR lSR = (LSR)object;
        if (!this.language.equals(lSR.language)) return false;
        if (!this.script.equals(lSR.script)) return false;
        if (!this.region.equals(lSR.region)) return false;
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.language, this.script, this.region);
    }
}

