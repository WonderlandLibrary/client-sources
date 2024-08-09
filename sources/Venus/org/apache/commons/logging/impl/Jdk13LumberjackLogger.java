/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.logging.impl;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;

public class Jdk13LumberjackLogger
implements Log,
Serializable {
    private static final long serialVersionUID = -8649807923527610591L;
    protected transient Logger logger = null;
    protected String name = null;
    private String sourceClassName = "unknown";
    private String sourceMethodName = "unknown";
    private boolean classAndMethodFound = false;
    protected static final Level dummyLevel = Level.FINE;

    public Jdk13LumberjackLogger(String string) {
        this.name = string;
        this.logger = this.getLogger();
    }

    private void log(Level level, String string, Throwable throwable) {
        if (this.getLogger().isLoggable(level)) {
            LogRecord logRecord = new LogRecord(level, string);
            if (!this.classAndMethodFound) {
                this.getClassAndMethod();
            }
            logRecord.setSourceClassName(this.sourceClassName);
            logRecord.setSourceMethodName(this.sourceMethodName);
            if (throwable != null) {
                logRecord.setThrown(throwable);
            }
            this.getLogger().log(logRecord);
        }
    }

    private void getClassAndMethod() {
        try {
            Throwable throwable = new Throwable();
            throwable.fillInStackTrace();
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            throwable.printStackTrace(printWriter);
            String string = stringWriter.getBuffer().toString();
            StringTokenizer stringTokenizer = new StringTokenizer(string, "\n");
            stringTokenizer.nextToken();
            String string2 = stringTokenizer.nextToken();
            while (string2.indexOf(this.getClass().getName()) == -1) {
                string2 = stringTokenizer.nextToken();
            }
            while (string2.indexOf(this.getClass().getName()) >= 0) {
                string2 = stringTokenizer.nextToken();
            }
            int n = string2.indexOf("at ") + 3;
            int n2 = string2.indexOf(40);
            String string3 = string2.substring(n, n2);
            int n3 = string3.lastIndexOf(46);
            this.sourceClassName = string3.substring(0, n3);
            this.sourceMethodName = string3.substring(n3 + 1);
        } catch (Exception exception) {
            // empty catch block
        }
        this.classAndMethodFound = true;
    }

    public void debug(Object object) {
        this.log(Level.FINE, String.valueOf(object), null);
    }

    public void debug(Object object, Throwable throwable) {
        this.log(Level.FINE, String.valueOf(object), throwable);
    }

    public void error(Object object) {
        this.log(Level.SEVERE, String.valueOf(object), null);
    }

    public void error(Object object, Throwable throwable) {
        this.log(Level.SEVERE, String.valueOf(object), throwable);
    }

    public void fatal(Object object) {
        this.log(Level.SEVERE, String.valueOf(object), null);
    }

    public void fatal(Object object, Throwable throwable) {
        this.log(Level.SEVERE, String.valueOf(object), throwable);
    }

    public Logger getLogger() {
        if (this.logger == null) {
            this.logger = Logger.getLogger(this.name);
        }
        return this.logger;
    }

    public void info(Object object) {
        this.log(Level.INFO, String.valueOf(object), null);
    }

    public void info(Object object, Throwable throwable) {
        this.log(Level.INFO, String.valueOf(object), throwable);
    }

    public boolean isDebugEnabled() {
        return this.getLogger().isLoggable(Level.FINE);
    }

    public boolean isErrorEnabled() {
        return this.getLogger().isLoggable(Level.SEVERE);
    }

    public boolean isFatalEnabled() {
        return this.getLogger().isLoggable(Level.SEVERE);
    }

    public boolean isInfoEnabled() {
        return this.getLogger().isLoggable(Level.INFO);
    }

    public boolean isTraceEnabled() {
        return this.getLogger().isLoggable(Level.FINEST);
    }

    public boolean isWarnEnabled() {
        return this.getLogger().isLoggable(Level.WARNING);
    }

    public void trace(Object object) {
        this.log(Level.FINEST, String.valueOf(object), null);
    }

    public void trace(Object object, Throwable throwable) {
        this.log(Level.FINEST, String.valueOf(object), throwable);
    }

    public void warn(Object object) {
        this.log(Level.WARNING, String.valueOf(object), null);
    }

    public void warn(Object object, Throwable throwable) {
        this.log(Level.WARNING, String.valueOf(object), throwable);
    }
}

