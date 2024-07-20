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

    public PropertiesUtil(Properties props) {
        this.props = props;
    }

    public PropertiesUtil(String propertiesFileName) {
        Properties properties = new Properties();
        for (URL url : LoaderUtil.findResources(propertiesFileName)) {
            try {
                InputStream in = url.openStream();
                Throwable throwable = null;
                try {
                    properties.load(in);
                } catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                } finally {
                    if (in == null) continue;
                    if (throwable != null) {
                        try {
                            in.close();
                        } catch (Throwable x2) {
                            throwable.addSuppressed(x2);
                        }
                        continue;
                    }
                    in.close();
                }
            } catch (IOException ioe) {
                LowLevelLogUtil.logException("Unable to read " + url.toString(), ioe);
            }
        }
        this.props = properties;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static Properties loadClose(InputStream in, Object source) {
        Properties props = new Properties();
        if (null != in) {
            try {
                props.load(in);
            } catch (IOException e) {
                LowLevelLogUtil.logException("Unable to read " + source, e);
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    LowLevelLogUtil.logException("Unable to close " + source, e);
                }
            }
        }
        return props;
    }

    public static PropertiesUtil getProperties() {
        return LOG4J_PROPERTIES;
    }

    public boolean getBooleanProperty(String name) {
        return this.getBooleanProperty(name, false);
    }

    public boolean getBooleanProperty(String name, boolean defaultValue) {
        String prop = this.getStringProperty(name);
        return prop == null ? defaultValue : "true".equalsIgnoreCase(prop);
    }

    public Charset getCharsetProperty(String name) {
        return this.getCharsetProperty(name, Charset.defaultCharset());
    }

    public Charset getCharsetProperty(String name, Charset defaultValue) {
        String prop = this.getStringProperty(name);
        return prop == null ? defaultValue : Charset.forName(prop);
    }

    public double getDoubleProperty(String name, double defaultValue) {
        String prop = this.getStringProperty(name);
        if (prop != null) {
            try {
                return Double.parseDouble(prop);
            } catch (Exception ignored) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public int getIntegerProperty(String name, int defaultValue) {
        String prop = this.getStringProperty(name);
        if (prop != null) {
            try {
                return Integer.parseInt(prop);
            } catch (Exception ignored) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public long getLongProperty(String name, long defaultValue) {
        String prop = this.getStringProperty(name);
        if (prop != null) {
            try {
                return Long.parseLong(prop);
            } catch (Exception ignored) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public String getStringProperty(String name) {
        String prop = null;
        try {
            prop = System.getProperty(name);
        } catch (SecurityException securityException) {
            // empty catch block
        }
        return prop == null ? this.props.getProperty(name) : prop;
    }

    public String getStringProperty(String name, String defaultValue) {
        String prop = this.getStringProperty(name);
        return prop == null ? defaultValue : prop;
    }

    public static Properties getSystemProperties() {
        try {
            return new Properties(System.getProperties());
        } catch (SecurityException ex) {
            LowLevelLogUtil.logException("Unable to access system properties.", ex);
            return new Properties();
        }
    }

    public static Properties extractSubset(Properties properties, String prefix) {
        Properties subset = new Properties();
        if (prefix == null || prefix.length() == 0) {
            return subset;
        }
        String prefixToMatch = prefix.charAt(prefix.length() - 1) != '.' ? prefix + '.' : prefix;
        ArrayList<String> keys = new ArrayList<String>();
        for (String key : properties.stringPropertyNames()) {
            if (!key.startsWith(prefixToMatch)) continue;
            subset.setProperty(key.substring(prefixToMatch.length()), properties.getProperty(key));
            keys.add(key);
        }
        for (String key : keys) {
            properties.remove(key);
        }
        return subset;
    }

    public static Map<String, Properties> partitionOnCommonPrefixes(Properties properties) {
        ConcurrentHashMap<String, Properties> parts = new ConcurrentHashMap<String, Properties>();
        for (String key : properties.stringPropertyNames()) {
            String prefix = key.substring(0, key.indexOf(46));
            if (!parts.containsKey(prefix)) {
                parts.put(prefix, new Properties());
            }
            ((Properties)parts.get(prefix)).setProperty(key.substring(key.indexOf(46) + 1), properties.getProperty(key));
        }
        return parts;
    }

    public boolean isOsWindows() {
        return this.getStringProperty("os.name").startsWith("Windows");
    }
}

