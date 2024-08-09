/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

public final class Version {
    private static final String apiVersion = "2.0.5";
    private static final String buildNumber = "1088";
    private static final String antBuildNumberToken = "@BUILD_NUMBER@";
    private static final String antAPIVersionToken = "@API_VERSION@";

    private Version() {
    }

    public static String getVersion() {
        String string = "Unversioned";
        if (!antAPIVersionToken.equals(apiVersion)) {
            string = apiVersion;
        }
        if (!antBuildNumberToken.equals(buildNumber)) {
            string = string + "-b1088";
        }
        return string;
    }
}

