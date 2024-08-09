/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.logging;

import java.lang.reflect.Constructor;
import java.util.Hashtable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.NoOpLog;

public class LogSource {
    protected static Hashtable logs = new Hashtable();
    protected static boolean log4jIsAvailable = false;
    protected static boolean jdk14IsAvailable = false;
    protected static Constructor logImplctor = null;

    private LogSource() {
    }

    public static void setLogImplementation(String string) throws LinkageError, NoSuchMethodException, SecurityException, ClassNotFoundException {
        try {
            Class<?> clazz = Class.forName(string);
            Class[] classArray = new Class[]{"".getClass()};
            logImplctor = clazz.getConstructor(classArray);
        } catch (Throwable throwable) {
            logImplctor = null;
        }
    }

    public static void setLogImplementation(Class clazz) throws LinkageError, ExceptionInInitializerError, NoSuchMethodException, SecurityException {
        Class[] classArray = new Class[]{"".getClass()};
        logImplctor = clazz.getConstructor(classArray);
    }

    public static Log getInstance(String string) {
        Log log2 = (Log)logs.get(string);
        if (null == log2) {
            log2 = LogSource.makeNewLogInstance(string);
            logs.put(string, log2);
        }
        return log2;
    }

    public static Log getInstance(Class clazz) {
        return LogSource.getInstance(clazz.getName());
    }

    public static Log makeNewLogInstance(String string) {
        Log log2;
        try {
            Object[] objectArray = new Object[]{string};
            log2 = (Log)logImplctor.newInstance(objectArray);
        } catch (Throwable throwable) {
            log2 = null;
        }
        if (null == log2) {
            log2 = new NoOpLog(string);
        }
        return log2;
    }

    public static String[] getLogNames() {
        return logs.keySet().toArray(new String[logs.size()]);
    }

    static {
        try {
            log4jIsAvailable = null != Class.forName("org.apache.log4j.Logger");
        } catch (Throwable throwable) {
            log4jIsAvailable = false;
        }
        try {
            jdk14IsAvailable = null != Class.forName("java.util.logging.Logger") && null != Class.forName("org.apache.commons.logging.impl.Jdk14Logger");
        } catch (Throwable throwable) {
            jdk14IsAvailable = false;
        }
        String string = null;
        try {
            string = System.getProperty("org.apache.commons.logging.log");
            if (string == null) {
                string = System.getProperty("org.apache.commons.logging.Log");
            }
        } catch (Throwable throwable) {
            // empty catch block
        }
        if (string != null) {
            try {
                LogSource.setLogImplementation(string);
            } catch (Throwable throwable) {
                try {
                    LogSource.setLogImplementation("org.apache.commons.logging.impl.NoOpLog");
                } catch (Throwable throwable2) {}
            }
        } else {
            try {
                if (log4jIsAvailable) {
                    LogSource.setLogImplementation("org.apache.commons.logging.impl.Log4JLogger");
                } else if (jdk14IsAvailable) {
                    LogSource.setLogImplementation("org.apache.commons.logging.impl.Jdk14Logger");
                } else {
                    LogSource.setLogImplementation("org.apache.commons.logging.impl.NoOpLog");
                }
            } catch (Throwable throwable) {
                try {
                    LogSource.setLogImplementation("org.apache.commons.logging.impl.NoOpLog");
                } catch (Throwable throwable3) {
                    // empty catch block
                }
            }
        }
    }
}

