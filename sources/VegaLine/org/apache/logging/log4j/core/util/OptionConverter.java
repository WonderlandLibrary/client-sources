/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import java.util.Locale;
import java.util.Properties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.logging.log4j.util.Strings;

public final class OptionConverter {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static final String DELIM_START = "${";
    private static final char DELIM_STOP = '}';
    private static final int DELIM_START_LEN = 2;
    private static final int DELIM_STOP_LEN = 1;
    private static final int ONE_K = 1024;

    private OptionConverter() {
    }

    public static String[] concatenateArrays(String[] l, String[] r) {
        int len = l.length + r.length;
        String[] a = new String[len];
        System.arraycopy(l, 0, a, 0, l.length);
        System.arraycopy(r, 0, a, l.length, r.length);
        return a;
    }

    public static String convertSpecialChars(String s) {
        int len = s.length();
        StringBuilder sbuf = new StringBuilder(len);
        int i = 0;
        while (i < len) {
            int c;
            if ((c = s.charAt(i++)) == 92) {
                c = s.charAt(i++);
                switch (c) {
                    case 110: {
                        c = 10;
                        break;
                    }
                    case 114: {
                        c = 13;
                        break;
                    }
                    case 116: {
                        c = 9;
                        break;
                    }
                    case 102: {
                        c = 12;
                        break;
                    }
                    case 98: {
                        c = 8;
                        break;
                    }
                    case 34: {
                        c = 34;
                        break;
                    }
                    case 39: {
                        c = 39;
                        break;
                    }
                    case 92: {
                        c = 92;
                        break;
                    }
                }
            }
            sbuf.append((char)c);
        }
        return sbuf.toString();
    }

    public static Object instantiateByKey(Properties props, String key, Class<?> superClass, Object defaultValue) {
        String className = OptionConverter.findAndSubst(key, props);
        if (className == null) {
            LOGGER.error("Could not find value for key {}", (Object)key);
            return defaultValue;
        }
        return OptionConverter.instantiateByClassName(className.trim(), superClass, defaultValue);
    }

    public static boolean toBoolean(String value, boolean defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        String trimmedVal = value.trim();
        if ("true".equalsIgnoreCase(trimmedVal)) {
            return true;
        }
        if ("false".equalsIgnoreCase(trimmedVal)) {
            return false;
        }
        return defaultValue;
    }

    public static int toInt(String value, int defaultValue) {
        if (value != null) {
            String s = value.trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                LOGGER.error("[{}] is not in proper int form.", (Object)s, (Object)e);
            }
        }
        return defaultValue;
    }

    public static long toFileSize(String value, long defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        String str = value.trim().toUpperCase(Locale.ENGLISH);
        long multiplier = 1L;
        int index = str.indexOf("KB");
        if (index != -1) {
            multiplier = 1024L;
            str = str.substring(0, index);
        } else {
            index = str.indexOf("MB");
            if (index != -1) {
                multiplier = 0x100000L;
                str = str.substring(0, index);
            } else {
                index = str.indexOf("GB");
                if (index != -1) {
                    multiplier = 0x40000000L;
                    str = str.substring(0, index);
                }
            }
        }
        try {
            return Long.parseLong(str) * multiplier;
        } catch (NumberFormatException e) {
            LOGGER.error("[{}] is not in proper int form.", (Object)str);
            LOGGER.error("[{}] not in expected format.", (Object)value, (Object)e);
            return defaultValue;
        }
    }

    public static String findAndSubst(String key, Properties props) {
        String value = props.getProperty(key);
        if (value == null) {
            return null;
        }
        try {
            return OptionConverter.substVars(value, props);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Bad option value [{}].", (Object)value, (Object)e);
            return value;
        }
    }

    public static Object instantiateByClassName(String className, Class<?> superClass, Object defaultValue) {
        if (className != null) {
            try {
                Class<?> classObj = LoaderUtil.loadClass(className);
                if (!superClass.isAssignableFrom(classObj)) {
                    LOGGER.error("A \"{}\" object is not assignable to a \"{}\" variable.", (Object)className, (Object)superClass.getName());
                    LOGGER.error("The class \"{}\" was loaded by [{}] whereas object of type [{}] was loaded by [{}].", (Object)superClass.getName(), (Object)superClass.getClassLoader(), (Object)classObj.getName());
                    return defaultValue;
                }
                return classObj.newInstance();
            } catch (Exception e) {
                LOGGER.error("Could not instantiate class [{}].", (Object)className, (Object)e);
            }
        }
        return defaultValue;
    }

    public static String substVars(String val, Properties props) throws IllegalArgumentException {
        StringBuilder sbuf = new StringBuilder();
        int i = 0;
        while (true) {
            int j;
            if ((j = val.indexOf(DELIM_START, i)) == -1) {
                if (i == 0) {
                    return val;
                }
                sbuf.append(val.substring(i, val.length()));
                return sbuf.toString();
            }
            sbuf.append(val.substring(i, j));
            int k = val.indexOf(125, j);
            if (k == -1) {
                throw new IllegalArgumentException(Strings.dquote(val) + " has no closing brace. Opening brace at position " + j + '.');
            }
            String key = val.substring(j += 2, k);
            String replacement = PropertiesUtil.getProperties().getStringProperty(key, null);
            if (replacement == null && props != null) {
                replacement = props.getProperty(key);
            }
            if (replacement != null) {
                String recursiveReplacement = OptionConverter.substVars(replacement, props);
                sbuf.append(recursiveReplacement);
            }
            i = k + 1;
        }
    }
}

