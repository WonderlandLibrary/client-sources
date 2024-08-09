/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io;

import java.io.Serializable;
import org.apache.commons.io.FilenameUtils;

public enum IOCase implements Serializable
{
    SENSITIVE("Sensitive", true),
    INSENSITIVE("Insensitive", false),
    SYSTEM("System", !FilenameUtils.isSystemWindows());

    private static final long serialVersionUID = -6343169151696340687L;
    private final String name;
    private final transient boolean sensitive;

    public static IOCase forName(String string) {
        for (IOCase iOCase : IOCase.values()) {
            if (!iOCase.getName().equals(string)) continue;
            return iOCase;
        }
        throw new IllegalArgumentException("Invalid IOCase name: " + string);
    }

    private IOCase(String string2, boolean bl) {
        this.name = string2;
        this.sensitive = bl;
    }

    private Object readResolve() {
        return IOCase.forName(this.name);
    }

    public String getName() {
        return this.name;
    }

    public boolean isCaseSensitive() {
        return this.sensitive;
    }

    public int checkCompareTo(String string, String string2) {
        if (string == null || string2 == null) {
            throw new NullPointerException("The strings must not be null");
        }
        return this.sensitive ? string.compareTo(string2) : string.compareToIgnoreCase(string2);
    }

    public boolean checkEquals(String string, String string2) {
        if (string == null || string2 == null) {
            throw new NullPointerException("The strings must not be null");
        }
        return this.sensitive ? string.equals(string2) : string.equalsIgnoreCase(string2);
    }

    public boolean checkStartsWith(String string, String string2) {
        return string.regionMatches(!this.sensitive, 0, string2, 0, string2.length());
    }

    public boolean checkEndsWith(String string, String string2) {
        int n = string2.length();
        return string.regionMatches(!this.sensitive, string.length() - n, string2, 0, n);
    }

    public int checkIndexOf(String string, int n, String string2) {
        int n2 = string.length() - string2.length();
        if (n2 >= n) {
            for (int i = n; i <= n2; ++i) {
                if (!this.checkRegionMatches(string, i, string2)) continue;
                return i;
            }
        }
        return 1;
    }

    public boolean checkRegionMatches(String string, int n, String string2) {
        return string.regionMatches(!this.sensitive, n, string2, 0, string2.length());
    }

    public String toString() {
        return this.name;
    }
}

