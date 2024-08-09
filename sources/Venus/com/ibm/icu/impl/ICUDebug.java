/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.util.VersionInfo;

public final class ICUDebug {
    private static String params;
    private static boolean debug;
    private static boolean help;
    public static final String javaVersionString;
    public static final boolean isJDK14OrHigher;
    public static final VersionInfo javaVersion;

    public static VersionInfo getInstanceLenient(String string) {
        int[] nArray = new int[4];
        boolean bl = false;
        int n = 0;
        int n2 = 0;
        while (n < string.length()) {
            char c;
            if ((c = string.charAt(n++)) < '0' || c > '9') {
                if (!bl) continue;
                if (n2 == 3) break;
                bl = false;
                ++n2;
                continue;
            }
            if (bl) {
                nArray[n2] = nArray[n2] * 10 + (c - 48);
                if (nArray[n2] <= 255) continue;
                nArray[n2] = 0;
                break;
            }
            bl = true;
            nArray[n2] = c - 48;
        }
        return VersionInfo.getInstance(nArray[0], nArray[1], nArray[2], nArray[3]);
    }

    public static boolean enabled() {
        return debug;
    }

    public static boolean enabled(String string) {
        if (debug) {
            boolean bl;
            boolean bl2 = bl = params.indexOf(string) != -1;
            if (help) {
                System.out.println("\nICUDebug.enabled(" + string + ") = " + bl);
            }
            return bl;
        }
        return true;
    }

    public static String value(String string) {
        String string2 = "false";
        if (debug) {
            int n = params.indexOf(string);
            if (n != -1) {
                int n2;
                string2 = params.length() > (n += string.length()) && params.charAt(n) == '=' ? params.substring(n, (n2 = params.indexOf(",", ++n)) == -1 ? params.length() : n2) : "true";
            }
            if (help) {
                System.out.println("\nICUDebug.value(" + string + ") = " + string2);
            }
        }
        return string2;
    }

    static {
        VersionInfo versionInfo;
        try {
            params = System.getProperty("ICUDebug");
        } catch (SecurityException securityException) {
            // empty catch block
        }
        debug = params != null;
        boolean bl = help = debug && (params.equals("") || params.indexOf("help") != -1);
        if (debug) {
            System.out.println("\nICUDebug=" + params);
        }
        isJDK14OrHigher = (javaVersion = ICUDebug.getInstanceLenient(javaVersionString = System.getProperty("java.version", "0"))).compareTo(versionInfo = VersionInfo.getInstance("1.4.0")) >= 0;
    }
}

