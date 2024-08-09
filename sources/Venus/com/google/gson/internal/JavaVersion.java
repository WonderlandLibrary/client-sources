/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.gson.internal;

public final class JavaVersion {
    private static final int majorJavaVersion = JavaVersion.determineMajorJavaVersion();

    private static int determineMajorJavaVersion() {
        String string = System.getProperty("java.version");
        return JavaVersion.getMajorJavaVersion(string);
    }

    static int getMajorJavaVersion(String string) {
        int n = JavaVersion.parseDotted(string);
        if (n == -1) {
            n = JavaVersion.extractBeginningInt(string);
        }
        if (n == -1) {
            return 1;
        }
        return n;
    }

    private static int parseDotted(String string) {
        try {
            String[] stringArray = string.split("[._]");
            int n = Integer.parseInt(stringArray[0]);
            if (n == 1 && stringArray.length > 1) {
                return Integer.parseInt(stringArray[5]);
            }
            return n;
        } catch (NumberFormatException numberFormatException) {
            return 1;
        }
    }

    private static int extractBeginningInt(String string) {
        try {
            char c;
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < string.length() && Character.isDigit(c = string.charAt(i)); ++i) {
                stringBuilder.append(c);
            }
            return Integer.parseInt(stringBuilder.toString());
        } catch (NumberFormatException numberFormatException) {
            return 1;
        }
    }

    public static int getMajorJavaVersion() {
        return majorJavaVersion;
    }

    public static boolean isJava9OrLater() {
        return majorJavaVersion >= 9;
    }

    private JavaVersion() {
    }
}

