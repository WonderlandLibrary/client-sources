/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.logging.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogConfigurationException;

public class SimpleLog
implements Log,
Serializable {
    private static final long serialVersionUID = 136942970684951178L;
    protected static final String systemPrefix = "org.apache.commons.logging.simplelog.";
    protected static final Properties simpleLogProps = new Properties();
    protected static final String DEFAULT_DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss:SSS zzz";
    protected static volatile boolean showLogName = false;
    protected static volatile boolean showShortName = true;
    protected static volatile boolean showDateTime = false;
    protected static volatile String dateTimeFormat = "yyyy/MM/dd HH:mm:ss:SSS zzz";
    protected static DateFormat dateFormatter = null;
    public static final int LOG_LEVEL_TRACE = 1;
    public static final int LOG_LEVEL_DEBUG = 2;
    public static final int LOG_LEVEL_INFO = 3;
    public static final int LOG_LEVEL_WARN = 4;
    public static final int LOG_LEVEL_ERROR = 5;
    public static final int LOG_LEVEL_FATAL = 6;
    public static final int LOG_LEVEL_ALL = 0;
    public static final int LOG_LEVEL_OFF = 7;
    protected volatile String logName = null;
    protected volatile int currentLogLevel;
    private volatile String shortLogName = null;
    static Class class$java$lang$Thread;
    static Class class$org$apache$commons$logging$impl$SimpleLog;

    private static String getStringProperty(String string) {
        String string2 = null;
        try {
            string2 = System.getProperty(string);
        } catch (SecurityException securityException) {
            // empty catch block
        }
        return string2 == null ? simpleLogProps.getProperty(string) : string2;
    }

    private static String getStringProperty(String string, String string2) {
        String string3 = SimpleLog.getStringProperty(string);
        return string3 == null ? string2 : string3;
    }

    private static boolean getBooleanProperty(String string, boolean bl) {
        String string2 = SimpleLog.getStringProperty(string);
        return string2 == null ? bl : "true".equalsIgnoreCase(string2);
    }

    public SimpleLog(String string) {
        this.logName = string;
        this.setLevel(3);
        String string2 = SimpleLog.getStringProperty("org.apache.commons.logging.simplelog.log." + this.logName);
        int n = String.valueOf(string).lastIndexOf(".");
        while (null == string2 && n > -1) {
            string = string.substring(0, n);
            string2 = SimpleLog.getStringProperty("org.apache.commons.logging.simplelog.log." + string);
            n = String.valueOf(string).lastIndexOf(".");
        }
        if (null == string2) {
            string2 = SimpleLog.getStringProperty("org.apache.commons.logging.simplelog.defaultlog");
        }
        if ("all".equalsIgnoreCase(string2)) {
            this.setLevel(0);
        } else if ("trace".equalsIgnoreCase(string2)) {
            this.setLevel(1);
        } else if ("debug".equalsIgnoreCase(string2)) {
            this.setLevel(2);
        } else if ("info".equalsIgnoreCase(string2)) {
            this.setLevel(3);
        } else if ("warn".equalsIgnoreCase(string2)) {
            this.setLevel(4);
        } else if ("error".equalsIgnoreCase(string2)) {
            this.setLevel(5);
        } else if ("fatal".equalsIgnoreCase(string2)) {
            this.setLevel(6);
        } else if ("off".equalsIgnoreCase(string2)) {
            this.setLevel(7);
        }
    }

    public void setLevel(int n) {
        this.currentLogLevel = n;
    }

    public int getLevel() {
        return this.currentLogLevel;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void log(int n, Object object, Throwable throwable) {
        Object object2;
        Object object3;
        StringBuffer stringBuffer = new StringBuffer();
        if (showDateTime) {
            object3 = new Date();
            DateFormat dateFormat = dateFormatter;
            synchronized (dateFormat) {
                object2 = dateFormatter.format((Date)object3);
            }
            stringBuffer.append((String)object2);
            stringBuffer.append(" ");
        }
        switch (n) {
            case 1: {
                stringBuffer.append("[TRACE] ");
                break;
            }
            case 2: {
                stringBuffer.append("[DEBUG] ");
                break;
            }
            case 3: {
                stringBuffer.append("[INFO] ");
                break;
            }
            case 4: {
                stringBuffer.append("[WARN] ");
                break;
            }
            case 5: {
                stringBuffer.append("[ERROR] ");
                break;
            }
            case 6: {
                stringBuffer.append("[FATAL] ");
            }
        }
        if (showShortName) {
            if (this.shortLogName == null) {
                object3 = this.logName.substring(this.logName.lastIndexOf(".") + 1);
                this.shortLogName = ((String)object3).substring(((String)object3).lastIndexOf("/") + 1);
            }
            stringBuffer.append(String.valueOf(this.shortLogName)).append(" - ");
        } else if (showLogName) {
            stringBuffer.append(String.valueOf(this.logName)).append(" - ");
        }
        stringBuffer.append(String.valueOf(object));
        if (throwable != null) {
            stringBuffer.append(" <");
            stringBuffer.append(throwable.toString());
            stringBuffer.append(">");
            object3 = new StringWriter(1024);
            object2 = new PrintWriter((Writer)object3);
            throwable.printStackTrace((PrintWriter)object2);
            ((PrintWriter)object2).close();
            stringBuffer.append(((StringWriter)object3).toString());
        }
        this.write(stringBuffer);
    }

    protected void write(StringBuffer stringBuffer) {
        System.err.println(stringBuffer.toString());
    }

    protected boolean isLevelEnabled(int n) {
        return n >= this.currentLogLevel;
    }

    public final void debug(Object object) {
        if (this.isLevelEnabled(1)) {
            this.log(2, object, null);
        }
    }

    public final void debug(Object object, Throwable throwable) {
        if (this.isLevelEnabled(1)) {
            this.log(2, object, throwable);
        }
    }

    public final void trace(Object object) {
        if (this.isLevelEnabled(0)) {
            this.log(1, object, null);
        }
    }

    public final void trace(Object object, Throwable throwable) {
        if (this.isLevelEnabled(0)) {
            this.log(1, object, throwable);
        }
    }

    public final void info(Object object) {
        if (this.isLevelEnabled(0)) {
            this.log(3, object, null);
        }
    }

    public final void info(Object object, Throwable throwable) {
        if (this.isLevelEnabled(0)) {
            this.log(3, object, throwable);
        }
    }

    public final void warn(Object object) {
        if (this.isLevelEnabled(1)) {
            this.log(4, object, null);
        }
    }

    public final void warn(Object object, Throwable throwable) {
        if (this.isLevelEnabled(1)) {
            this.log(4, object, throwable);
        }
    }

    public final void error(Object object) {
        if (this.isLevelEnabled(0)) {
            this.log(5, object, null);
        }
    }

    public final void error(Object object, Throwable throwable) {
        if (this.isLevelEnabled(0)) {
            this.log(5, object, throwable);
        }
    }

    public final void fatal(Object object) {
        if (this.isLevelEnabled(1)) {
            this.log(6, object, null);
        }
    }

    public final void fatal(Object object, Throwable throwable) {
        if (this.isLevelEnabled(1)) {
            this.log(6, object, throwable);
        }
    }

    public final boolean isDebugEnabled() {
        return this.isLevelEnabled(1);
    }

    public final boolean isErrorEnabled() {
        return this.isLevelEnabled(0);
    }

    public final boolean isFatalEnabled() {
        return this.isLevelEnabled(1);
    }

    public final boolean isInfoEnabled() {
        return this.isLevelEnabled(0);
    }

    public final boolean isTraceEnabled() {
        return this.isLevelEnabled(0);
    }

    public final boolean isWarnEnabled() {
        return this.isLevelEnabled(1);
    }

    private static ClassLoader getContextClassLoader() {
        ClassLoader classLoader = null;
        try {
            Method method = (class$java$lang$Thread == null ? (class$java$lang$Thread = SimpleLog.class$("java.lang.Thread")) : class$java$lang$Thread).getMethod("getContextClassLoader", null);
            try {
                classLoader = (ClassLoader)method.invoke(Thread.currentThread(), null);
            } catch (IllegalAccessException illegalAccessException) {
            } catch (InvocationTargetException invocationTargetException) {
                if (!(invocationTargetException.getTargetException() instanceof SecurityException)) {
                    throw new LogConfigurationException("Unexpected InvocationTargetException", invocationTargetException.getTargetException());
                }
            }
        } catch (NoSuchMethodException noSuchMethodException) {
            // empty catch block
        }
        if (classLoader == null) {
            classLoader = (class$org$apache$commons$logging$impl$SimpleLog == null ? (class$org$apache$commons$logging$impl$SimpleLog = SimpleLog.class$("org.apache.commons.logging.impl.SimpleLog")) : class$org$apache$commons$logging$impl$SimpleLog).getClassLoader();
        }
        return classLoader;
    }

    private static InputStream getResourceAsStream(String string) {
        return (InputStream)AccessController.doPrivileged(new PrivilegedAction(string){
            private final String val$name;
            {
                this.val$name = string;
            }

            public Object run() {
                ClassLoader classLoader = SimpleLog.access$000();
                if (classLoader != null) {
                    return classLoader.getResourceAsStream(this.val$name);
                }
                return ClassLoader.getSystemResourceAsStream(this.val$name);
            }
        });
    }

    static Class class$(String string) {
        try {
            return Class.forName(string);
        } catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }

    static ClassLoader access$000() {
        return SimpleLog.getContextClassLoader();
    }

    static {
        InputStream inputStream = SimpleLog.getResourceAsStream("simplelog.properties");
        if (null != inputStream) {
            try {
                simpleLogProps.load(inputStream);
                inputStream.close();
            } catch (IOException iOException) {
                // empty catch block
            }
        }
        showLogName = SimpleLog.getBooleanProperty("org.apache.commons.logging.simplelog.showlogname", showLogName);
        showShortName = SimpleLog.getBooleanProperty("org.apache.commons.logging.simplelog.showShortLogname", showShortName);
        if (showDateTime = SimpleLog.getBooleanProperty("org.apache.commons.logging.simplelog.showdatetime", showDateTime)) {
            dateTimeFormat = SimpleLog.getStringProperty("org.apache.commons.logging.simplelog.dateTimeFormat", dateTimeFormat);
            try {
                dateFormatter = new SimpleDateFormat(dateTimeFormat);
            } catch (IllegalArgumentException illegalArgumentException) {
                dateTimeFormat = DEFAULT_DATE_TIME_FORMAT;
                dateFormatter = new SimpleDateFormat(dateTimeFormat);
            }
        }
    }
}

