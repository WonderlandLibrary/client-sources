/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.LowLevelLogUtil;

public final class PropertiesUtil {
    private static final PropertiesUtil LOG4J_PROPERTIES = new PropertiesUtil("log4j2.component.properties");
    private final Properties props;

    public PropertiesUtil(Properties properties) {
        this.props = properties;
    }

    public PropertiesUtil(String string) {
        Properties properties = new Properties();
        for (URL uRL : LoaderUtil.findResources(string)) {
            try {
                InputStream inputStream = uRL.openStream();
                Throwable throwable = null;
                try {
                    properties.load(inputStream);
                } catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                } finally {
                    if (inputStream == null) continue;
                    if (throwable != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                        continue;
                    }
                    inputStream.close();
                }
            } catch (IOException iOException) {
                LowLevelLogUtil.logException("Unable to read " + uRL.toString(), iOException);
            }
        }
        this.props = properties;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static Properties loadClose(InputStream inputStream, Object object) {
        Properties properties = new Properties();
        if (null != inputStream) {
            try {
                properties.load(inputStream);
            } catch (IOException iOException) {
                LowLevelLogUtil.logException("Unable to read " + object, iOException);
            } finally {
                try {
                    inputStream.close();
                } catch (IOException iOException) {
                    LowLevelLogUtil.logException("Unable to close " + object, iOException);
                }
            }
        }
        return properties;
    }

    public static PropertiesUtil getProperties() {
        return LOG4J_PROPERTIES;
    }

    public boolean getBooleanProperty(String string) {
        return this.getBooleanProperty(string, true);
    }

    public boolean getBooleanProperty(String string, boolean bl) {
        String string2 = this.getStringProperty(string);
        return string2 == null ? bl : "true".equalsIgnoreCase(string2);
    }

    public Charset getCharsetProperty(String string) {
        return this.getCharsetProperty(string, Charset.defaultCharset());
    }

    public Charset getCharsetProperty(String string, Charset charset) {
        String string2 = this.getStringProperty(string);
        return string2 == null ? charset : Charset.forName(string2);
    }

    public double getDoubleProperty(String string, double d) {
        String string2 = this.getStringProperty(string);
        if (string2 != null) {
            try {
                return Double.parseDouble(string2);
            } catch (Exception exception) {
                return d;
            }
        }
        return d;
    }

    public int getIntegerProperty(String string, int n) {
        String string2 = this.getStringProperty(string);
        if (string2 != null) {
            try {
                return Integer.parseInt(string2);
            } catch (Exception exception) {
                return n;
            }
        }
        return n;
    }

    public long getLongProperty(String string, long l) {
        String string2 = this.getStringProperty(string);
        if (string2 != null) {
            try {
                return Long.parseLong(string2);
            } catch (Exception exception) {
                return l;
            }
        }
        return l;
    }

    public String getStringProperty(String string) {
        String string2 = null;
        try {
            string2 = System.getProperty(string);
        } catch (SecurityException securityException) {
            // empty catch block
        }
        return string2 == null ? this.props.getProperty(string) : string2;
    }

    public String getStringProperty(String string, String string2) {
        String string3 = this.getStringProperty(string);
        return string3 == null ? string2 : string3;
    }

    public static Properties getSystemProperties() {
        try {
            return new Properties(System.getProperties());
        } catch (SecurityException securityException) {
            LowLevelLogUtil.logException("Unable to access system properties.", securityException);
            return new Properties();
        }
    }

    public static Properties extractSubset(Properties properties, String string) {
        Properties properties2 = new Properties();
        if (string == null || string.length() == 0) {
            return properties2;
        }
        String string2 = string.charAt(string.length() - 1) != '.' ? string + '.' : string;
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String string3 : properties.stringPropertyNames()) {
            if (!string3.startsWith(string2)) continue;
            properties2.setProperty(string3.substring(string2.length()), properties.getProperty(string3));
            arrayList.add(string3);
        }
        for (String string3 : arrayList) {
            properties.remove(string3);
        }
        return properties2;
    }

    public static Map<String, Properties> partitionOnCommonPrefixes(Properties properties) {
        ConcurrentHashMap<String, Properties> concurrentHashMap = new ConcurrentHashMap<String, Properties>();
        for (String string : properties.stringPropertyNames()) {
            String string2 = string.substring(0, string.indexOf(46));
            if (!concurrentHashMap.containsKey(string2)) {
                concurrentHashMap.put(string2, new Properties());
            }
            ((Properties)concurrentHashMap.get(string2)).setProperty(string.substring(string.indexOf(46) + 1), properties.getProperty(string));
        }
        return concurrentHashMap;
    }

    public boolean isOsWindows() {
        return this.getStringProperty("os.name").startsWith("Windows");
    }
}

