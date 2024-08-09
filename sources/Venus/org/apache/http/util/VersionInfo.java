/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import org.apache.http.util.Args;

public class VersionInfo {
    public static final String UNAVAILABLE = "UNAVAILABLE";
    public static final String VERSION_PROPERTY_FILE = "version.properties";
    public static final String PROPERTY_MODULE = "info.module";
    public static final String PROPERTY_RELEASE = "info.release";
    public static final String PROPERTY_TIMESTAMP = "info.timestamp";
    private final String infoPackage;
    private final String infoModule;
    private final String infoRelease;
    private final String infoTimestamp;
    private final String infoClassloader;

    protected VersionInfo(String string, String string2, String string3, String string4, String string5) {
        Args.notNull(string, "Package identifier");
        this.infoPackage = string;
        this.infoModule = string2 != null ? string2 : UNAVAILABLE;
        this.infoRelease = string3 != null ? string3 : UNAVAILABLE;
        this.infoTimestamp = string4 != null ? string4 : UNAVAILABLE;
        this.infoClassloader = string5 != null ? string5 : UNAVAILABLE;
    }

    public final String getPackage() {
        return this.infoPackage;
    }

    public final String getModule() {
        return this.infoModule;
    }

    public final String getRelease() {
        return this.infoRelease;
    }

    public final String getTimestamp() {
        return this.infoTimestamp;
    }

    public final String getClassloader() {
        return this.infoClassloader;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(20 + this.infoPackage.length() + this.infoModule.length() + this.infoRelease.length() + this.infoTimestamp.length() + this.infoClassloader.length());
        stringBuilder.append("VersionInfo(").append(this.infoPackage).append(':').append(this.infoModule);
        if (!UNAVAILABLE.equals(this.infoRelease)) {
            stringBuilder.append(':').append(this.infoRelease);
        }
        if (!UNAVAILABLE.equals(this.infoTimestamp)) {
            stringBuilder.append(':').append(this.infoTimestamp);
        }
        stringBuilder.append(')');
        if (!UNAVAILABLE.equals(this.infoClassloader)) {
            stringBuilder.append('@').append(this.infoClassloader);
        }
        return stringBuilder.toString();
    }

    public static VersionInfo[] loadVersionInfo(String[] stringArray, ClassLoader classLoader) {
        Args.notNull(stringArray, "Package identifier array");
        ArrayList<VersionInfo> arrayList = new ArrayList<VersionInfo>(stringArray.length);
        for (String string : stringArray) {
            VersionInfo versionInfo = VersionInfo.loadVersionInfo(string, classLoader);
            if (versionInfo == null) continue;
            arrayList.add(versionInfo);
        }
        return arrayList.toArray(new VersionInfo[arrayList.size()]);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static VersionInfo loadVersionInfo(String string, ClassLoader classLoader) {
        Object object;
        Properties properties;
        ClassLoader classLoader2;
        block6: {
            Args.notNull(string, "Package identifier");
            classLoader2 = classLoader != null ? classLoader : Thread.currentThread().getContextClassLoader();
            properties = null;
            try {
                object = classLoader2.getResourceAsStream(string.replace('.', '/') + "/" + VERSION_PROPERTY_FILE);
                if (object == null) break block6;
                try {
                    Properties properties2 = new Properties();
                    properties2.load((InputStream)object);
                    properties = properties2;
                } finally {
                    ((InputStream)object).close();
                }
            } catch (IOException iOException) {
                // empty catch block
            }
        }
        object = null;
        if (properties != null) {
            object = VersionInfo.fromMap(string, properties, classLoader2);
        }
        return object;
    }

    protected static VersionInfo fromMap(String string, Map<?, ?> map, ClassLoader classLoader) {
        Args.notNull(string, "Package identifier");
        String string2 = null;
        String string3 = null;
        String string4 = null;
        if (map != null) {
            string2 = (String)map.get(PROPERTY_MODULE);
            if (string2 != null && string2.length() < 1) {
                string2 = null;
            }
            if ((string3 = (String)map.get(PROPERTY_RELEASE)) != null && (string3.length() < 1 || string3.equals("${pom.version}"))) {
                string3 = null;
            }
            if ((string4 = (String)map.get(PROPERTY_TIMESTAMP)) != null && (string4.length() < 1 || string4.equals("${mvn.timestamp}"))) {
                string4 = null;
            }
        }
        String string5 = null;
        if (classLoader != null) {
            string5 = classLoader.toString();
        }
        return new VersionInfo(string, string2, string3, string4, string5);
    }

    public static String getUserAgent(String string, String string2, Class<?> clazz) {
        VersionInfo versionInfo = VersionInfo.loadVersionInfo(string2, clazz.getClassLoader());
        String string3 = versionInfo != null ? versionInfo.getRelease() : UNAVAILABLE;
        String string4 = System.getProperty("java.version");
        return String.format("%s/%s (Java/%s)", string, string3, string4);
    }
}

