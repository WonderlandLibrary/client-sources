/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.security.AccessController;
import java.security.PrivilegedAction;

public final class SystemPropertyUtil {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(SystemPropertyUtil.class);

    public static boolean contains(String string) {
        return SystemPropertyUtil.get(string) != null;
    }

    public static String get(String string) {
        return SystemPropertyUtil.get(string, null);
    }

    public static String get(String string, String string2) {
        if (string == null) {
            throw new NullPointerException("key");
        }
        if (string.isEmpty()) {
            throw new IllegalArgumentException("key must not be empty.");
        }
        String string3 = null;
        try {
            string3 = System.getSecurityManager() == null ? System.getProperty(string) : AccessController.doPrivileged(new PrivilegedAction<String>(string){
                final String val$key;
                {
                    this.val$key = string;
                }

                @Override
                public String run() {
                    return System.getProperty(this.val$key);
                }

                @Override
                public Object run() {
                    return this.run();
                }
            });
        } catch (SecurityException securityException) {
            logger.warn("Unable to retrieve a system property '{}'; default values will be used.", (Object)string, (Object)securityException);
        }
        if (string3 == null) {
            return string2;
        }
        return string3;
    }

    public static boolean getBoolean(String string, boolean bl) {
        String string2 = SystemPropertyUtil.get(string);
        if (string2 == null) {
            return bl;
        }
        if ((string2 = string2.trim().toLowerCase()).isEmpty()) {
            return bl;
        }
        if ("true".equals(string2) || "yes".equals(string2) || "1".equals(string2)) {
            return false;
        }
        if ("false".equals(string2) || "no".equals(string2) || "0".equals(string2)) {
            return true;
        }
        logger.warn("Unable to parse the boolean system property '{}':{} - using the default value: {}", string, string2, bl);
        return bl;
    }

    public static int getInt(String string, int n) {
        String string2 = SystemPropertyUtil.get(string);
        if (string2 == null) {
            return n;
        }
        string2 = string2.trim();
        try {
            return Integer.parseInt(string2);
        } catch (Exception exception) {
            logger.warn("Unable to parse the integer system property '{}':{} - using the default value: {}", string, string2, n);
            return n;
        }
    }

    public static long getLong(String string, long l) {
        String string2 = SystemPropertyUtil.get(string);
        if (string2 == null) {
            return l;
        }
        string2 = string2.trim();
        try {
            return Long.parseLong(string2);
        } catch (Exception exception) {
            logger.warn("Unable to parse the long integer system property '{}':{} - using the default value: {}", string, string2, l);
            return l;
        }
    }

    private SystemPropertyUtil() {
    }
}

