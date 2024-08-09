/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.util.LocaleData;
import com.ibm.icu.util.UResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public final class VersionInfo
implements Comparable<VersionInfo> {
    public static final VersionInfo UNICODE_1_0;
    public static final VersionInfo UNICODE_1_0_1;
    public static final VersionInfo UNICODE_1_1_0;
    public static final VersionInfo UNICODE_1_1_5;
    public static final VersionInfo UNICODE_2_0;
    public static final VersionInfo UNICODE_2_1_2;
    public static final VersionInfo UNICODE_2_1_5;
    public static final VersionInfo UNICODE_2_1_8;
    public static final VersionInfo UNICODE_2_1_9;
    public static final VersionInfo UNICODE_3_0;
    public static final VersionInfo UNICODE_3_0_1;
    public static final VersionInfo UNICODE_3_1_0;
    public static final VersionInfo UNICODE_3_1_1;
    public static final VersionInfo UNICODE_3_2;
    public static final VersionInfo UNICODE_4_0;
    public static final VersionInfo UNICODE_4_0_1;
    public static final VersionInfo UNICODE_4_1;
    public static final VersionInfo UNICODE_5_0;
    public static final VersionInfo UNICODE_5_1;
    public static final VersionInfo UNICODE_5_2;
    public static final VersionInfo UNICODE_6_0;
    public static final VersionInfo UNICODE_6_1;
    public static final VersionInfo UNICODE_6_2;
    public static final VersionInfo UNICODE_6_3;
    public static final VersionInfo UNICODE_7_0;
    public static final VersionInfo UNICODE_8_0;
    public static final VersionInfo UNICODE_9_0;
    public static final VersionInfo UNICODE_10_0;
    public static final VersionInfo UNICODE_11_0;
    public static final VersionInfo UNICODE_12_0;
    public static final VersionInfo UNICODE_12_1;
    public static final VersionInfo UNICODE_13_0;
    public static final VersionInfo ICU_VERSION;
    @Deprecated
    public static final String ICU_DATA_VERSION_PATH = "66b";
    @Deprecated
    public static final VersionInfo ICU_DATA_VERSION;
    public static final VersionInfo UCOL_RUNTIME_VERSION;
    public static final VersionInfo UCOL_BUILDER_VERSION;
    @Deprecated
    public static final VersionInfo UCOL_TAILORINGS_VERSION;
    private static volatile VersionInfo javaVersion;
    private static final VersionInfo UNICODE_VERSION;
    private int m_version_;
    private static final ConcurrentHashMap<Integer, VersionInfo> MAP_;
    private static final int LAST_BYTE_MASK_ = 255;
    private static final String INVALID_VERSION_NUMBER_ = "Invalid version number: Version number may be negative or greater than 255";
    private static volatile String TZDATA_VERSION;

    public static VersionInfo getInstance(String string) {
        int n;
        int n2;
        int n3 = string.length();
        int[] nArray = new int[]{0, 0, 0, 0};
        int n4 = 0;
        for (n2 = 0; n4 < 4 && n2 < n3; ++n2) {
            n = string.charAt(n2);
            if (n == 46) {
                ++n4;
                continue;
            }
            if ((n = (char)(n - 48)) < 0 || n > 9) {
                throw new IllegalArgumentException(INVALID_VERSION_NUMBER_);
            }
            int n5 = n4;
            nArray[n5] = nArray[n5] * 10;
            int n6 = n4;
            nArray[n6] = nArray[n6] + n;
        }
        if (n2 != n3) {
            throw new IllegalArgumentException("Invalid version number: String '" + string + "' exceeds version format");
        }
        for (n = 0; n < 4; ++n) {
            if (nArray[n] >= 0 && nArray[n] <= 255) continue;
            throw new IllegalArgumentException(INVALID_VERSION_NUMBER_);
        }
        return VersionInfo.getInstance(nArray[0], nArray[1], nArray[2], nArray[3]);
    }

    public static VersionInfo getInstance(int n, int n2, int n3, int n4) {
        VersionInfo versionInfo;
        if (n < 0 || n > 255 || n2 < 0 || n2 > 255 || n3 < 0 || n3 > 255 || n4 < 0 || n4 > 255) {
            throw new IllegalArgumentException(INVALID_VERSION_NUMBER_);
        }
        int n5 = VersionInfo.getInt(n, n2, n3, n4);
        Integer n6 = n5;
        VersionInfo versionInfo2 = MAP_.get(n6);
        if (versionInfo2 == null && (versionInfo = MAP_.putIfAbsent(n6, versionInfo2 = new VersionInfo(n5))) != null) {
            versionInfo2 = versionInfo;
        }
        return versionInfo2;
    }

    public static VersionInfo getInstance(int n, int n2, int n3) {
        return VersionInfo.getInstance(n, n2, n3, 0);
    }

    public static VersionInfo getInstance(int n, int n2) {
        return VersionInfo.getInstance(n, n2, 0, 0);
    }

    public static VersionInfo getInstance(int n) {
        return VersionInfo.getInstance(n, 0, 0, 0);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Deprecated
    public static VersionInfo javaVersion() {
        if (javaVersion != null) return javaVersion;
        Class<VersionInfo> clazz = VersionInfo.class;
        synchronized (VersionInfo.class) {
            if (javaVersion != null) return javaVersion;
            String string = System.getProperty("java.version");
            char[] cArray = string.toCharArray();
            int n = 0;
            int n2 = 0;
            int n3 = 0;
            boolean bl = false;
            while (n < cArray.length) {
                char c;
                if ((c = cArray[n++]) < '0' || c > '9') {
                    if (!bl) continue;
                    if (n3 == 3) break;
                    bl = false;
                    cArray[n2++] = 46;
                    ++n3;
                    continue;
                }
                bl = true;
                cArray[n2++] = c;
            }
            while (n2 > 0 && cArray[n2 - 1] == '.') {
                --n2;
            }
            String string2 = new String(cArray, 0, n2);
            javaVersion = VersionInfo.getInstance(string2);
            // ** MonitorExit[var0] (shouldn't be in output)
            return javaVersion;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(7);
        stringBuilder.append(this.getMajor());
        stringBuilder.append('.');
        stringBuilder.append(this.getMinor());
        stringBuilder.append('.');
        stringBuilder.append(this.getMilli());
        stringBuilder.append('.');
        stringBuilder.append(this.getMicro());
        return stringBuilder.toString();
    }

    public int getMajor() {
        return this.m_version_ >> 24 & 0xFF;
    }

    public int getMinor() {
        return this.m_version_ >> 16 & 0xFF;
    }

    public int getMilli() {
        return this.m_version_ >> 8 & 0xFF;
    }

    public int getMicro() {
        return this.m_version_ & 0xFF;
    }

    public boolean equals(Object object) {
        return object == this;
    }

    public int hashCode() {
        return this.m_version_;
    }

    @Override
    public int compareTo(VersionInfo versionInfo) {
        return this.m_version_ - versionInfo.m_version_;
    }

    private VersionInfo(int n) {
        this.m_version_ = n;
    }

    private static int getInt(int n, int n2, int n3, int n4) {
        return n << 24 | n2 << 16 | n3 << 8 | n4;
    }

    public static void main(String[] stringArray) {
        String string;
        if (ICU_VERSION.getMajor() <= 4) {
            if (ICU_VERSION.getMinor() % 2 != 0) {
                int n = ICU_VERSION.getMajor();
                int n2 = ICU_VERSION.getMinor() + 1;
                if (n2 >= 10) {
                    n2 -= 10;
                    ++n;
                }
                string = "" + n + "." + n2 + "M" + ICU_VERSION.getMilli();
            } else {
                string = ICU_VERSION.getVersionString(2, 2);
            }
        } else {
            string = ICU_VERSION.getMinor() == 0 ? "" + ICU_VERSION.getMajor() + "M" + ICU_VERSION.getMilli() : ICU_VERSION.getVersionString(2, 2);
        }
        System.out.println("International Components for Unicode for Java " + string);
        System.out.println("");
        System.out.println("Implementation Version: " + ICU_VERSION.getVersionString(2, 4));
        System.out.println("Unicode Data Version:   " + UNICODE_VERSION.getVersionString(2, 4));
        System.out.println("CLDR Data Version:      " + LocaleData.getCLDRVersion().getVersionString(2, 4));
        System.out.println("Time Zone Data Version: " + VersionInfo.getTZDataVersion());
    }

    @Deprecated
    public String getVersionString(int n, int n2) {
        int n3;
        if (n < 1 || n2 < 1 || n > 4 || n2 > 4 || n > n2) {
            throw new IllegalArgumentException("Invalid min/maxDigits range");
        }
        int[] nArray = new int[]{this.getMajor(), this.getMinor(), this.getMilli(), this.getMicro()};
        for (n3 = n2; n3 > n && nArray[n3 - 1] == 0; --n3) {
        }
        StringBuilder stringBuilder = new StringBuilder(7);
        stringBuilder.append(nArray[0]);
        for (int i = 1; i < n3; ++i) {
            stringBuilder.append(".");
            stringBuilder.append(nArray[i]);
        }
        return stringBuilder.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static String getTZDataVersion() {
        if (TZDATA_VERSION != null) return TZDATA_VERSION;
        Class<VersionInfo> clazz = VersionInfo.class;
        synchronized (VersionInfo.class) {
            if (TZDATA_VERSION != null) return TZDATA_VERSION;
            UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "zoneinfo64");
            TZDATA_VERSION = uResourceBundle.getString("TZVersion");
            // ** MonitorExit[var0] (shouldn't be in output)
            return TZDATA_VERSION;
        }
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((VersionInfo)object);
    }

    static {
        MAP_ = new ConcurrentHashMap();
        UNICODE_1_0 = VersionInfo.getInstance(1, 0, 0, 0);
        UNICODE_1_0_1 = VersionInfo.getInstance(1, 0, 1, 0);
        UNICODE_1_1_0 = VersionInfo.getInstance(1, 1, 0, 0);
        UNICODE_1_1_5 = VersionInfo.getInstance(1, 1, 5, 0);
        UNICODE_2_0 = VersionInfo.getInstance(2, 0, 0, 0);
        UNICODE_2_1_2 = VersionInfo.getInstance(2, 1, 2, 0);
        UNICODE_2_1_5 = VersionInfo.getInstance(2, 1, 5, 0);
        UNICODE_2_1_8 = VersionInfo.getInstance(2, 1, 8, 0);
        UNICODE_2_1_9 = VersionInfo.getInstance(2, 1, 9, 0);
        UNICODE_3_0 = VersionInfo.getInstance(3, 0, 0, 0);
        UNICODE_3_0_1 = VersionInfo.getInstance(3, 0, 1, 0);
        UNICODE_3_1_0 = VersionInfo.getInstance(3, 1, 0, 0);
        UNICODE_3_1_1 = VersionInfo.getInstance(3, 1, 1, 0);
        UNICODE_3_2 = VersionInfo.getInstance(3, 2, 0, 0);
        UNICODE_4_0 = VersionInfo.getInstance(4, 0, 0, 0);
        UNICODE_4_0_1 = VersionInfo.getInstance(4, 0, 1, 0);
        UNICODE_4_1 = VersionInfo.getInstance(4, 1, 0, 0);
        UNICODE_5_0 = VersionInfo.getInstance(5, 0, 0, 0);
        UNICODE_5_1 = VersionInfo.getInstance(5, 1, 0, 0);
        UNICODE_5_2 = VersionInfo.getInstance(5, 2, 0, 0);
        UNICODE_6_0 = VersionInfo.getInstance(6, 0, 0, 0);
        UNICODE_6_1 = VersionInfo.getInstance(6, 1, 0, 0);
        UNICODE_6_2 = VersionInfo.getInstance(6, 2, 0, 0);
        UNICODE_6_3 = VersionInfo.getInstance(6, 3, 0, 0);
        UNICODE_7_0 = VersionInfo.getInstance(7, 0, 0, 0);
        UNICODE_8_0 = VersionInfo.getInstance(8, 0, 0, 0);
        UNICODE_9_0 = VersionInfo.getInstance(9, 0, 0, 0);
        UNICODE_10_0 = VersionInfo.getInstance(10, 0, 0, 0);
        UNICODE_11_0 = VersionInfo.getInstance(11, 0, 0, 0);
        UNICODE_12_0 = VersionInfo.getInstance(12, 0, 0, 0);
        UNICODE_12_1 = VersionInfo.getInstance(12, 1, 0, 0);
        UNICODE_13_0 = VersionInfo.getInstance(13, 0, 0, 0);
        ICU_DATA_VERSION = ICU_VERSION = VersionInfo.getInstance(66, 1, 0, 0);
        UNICODE_VERSION = UNICODE_13_0;
        UCOL_RUNTIME_VERSION = VersionInfo.getInstance(9);
        UCOL_BUILDER_VERSION = VersionInfo.getInstance(9);
        UCOL_TAILORINGS_VERSION = VersionInfo.getInstance(1);
        TZDATA_VERSION = null;
    }
}

