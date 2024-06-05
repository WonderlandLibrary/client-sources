/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.gson.internal;

public final class JavaVersion {
    private static final int majorJavaVersion = JavaVersion.determineMajorJavaVersion();

    private static int determineMajorJavaVersion() {
        String javaVersion = System.getProperty("java.version");
        return JavaVersion.getMajorJavaVersion(javaVersion);
    }

    static int getMajorJavaVersion(String javaVersion) {
        int version = JavaVersion.parseDotted(javaVersion);
        if (version == -1) {
            version = JavaVersion.extractBeginningInt(javaVersion);
        }
        if (version != -1) return version;
        return 6;
    }

    private static int parseDotted(String javaVersion) {
        try {
            String[] parts = javaVersion.split("[._]");
            int firstVer = Integer.parseInt(parts[0]);
            if (firstVer != 1) return firstVer;
            if (parts.length <= 1) return firstVer;
            return Integer.parseInt(parts[1]);
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }

    private static int extractBeginningInt(String javaVersion) {
        try {
            StringBuilder num = new StringBuilder();
            int i = 0;
            while (i < javaVersion.length()) {
                char c = javaVersion.charAt(i);
                if (!Character.isDigit(c)) return Integer.parseInt(num.toString());
                num.append(c);
                ++i;
            }
            return Integer.parseInt(num.toString());
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }

    public static int getMajorJavaVersion() {
        return majorJavaVersion;
    }

    public static boolean isJava9OrLater() {
        if (majorJavaVersion < 9) return false;
        return true;
    }

    private JavaVersion() {
    }
}

