/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.util;

public class PlatformFeatureDetector {
    private Boolean isRunningOnAndroid = null;

    public boolean isRunningOnAndroid() {
        if (this.isRunningOnAndroid == null) {
            String string = System.getProperty("java.runtime.name");
            this.isRunningOnAndroid = string != null && string.startsWith("Android Runtime");
        }
        return this.isRunningOnAndroid;
    }
}

