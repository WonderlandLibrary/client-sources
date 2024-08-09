/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.simple;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.simple.SimpleLogger;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.apache.logging.log4j.spi.LoggerContext;
import org.apache.logging.log4j.spi.LoggerRegistry;
import org.apache.logging.log4j.util.PropertiesUtil;

public class SimpleLoggerContext
implements LoggerContext {
    private static final String SYSTEM_OUT = "system.out";
    private static final String SYSTEM_ERR = "system.err";
    protected static final String DEFAULT_DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss:SSS zzz";
    protected static final String SYSTEM_PREFIX = "org.apache.logging.log4j.simplelog.";
    private final PropertiesUtil props;
    private final boolean showLogName;
    private final boolean showShortName;
    private final boolean showDateTime;
    private final boolean showContextMap;
    private final String dateTimeFormat;
    private final Level defaultLevel;
    private final PrintStream stream;
    private final LoggerRegistry<ExtendedLogger> loggerRegistry = new LoggerRegistry();

    public SimpleLoggerContext() {
        PrintStream printStream;
        this.props = new PropertiesUtil("log4j2.simplelog.properties");
        this.showContextMap = this.props.getBooleanProperty("org.apache.logging.log4j.simplelog.showContextMap", true);
        this.showLogName = this.props.getBooleanProperty("org.apache.logging.log4j.simplelog.showlogname", true);
        this.showShortName = this.props.getBooleanProperty("org.apache.logging.log4j.simplelog.showShortLogname", false);
        this.showDateTime = this.props.getBooleanProperty("org.apache.logging.log4j.simplelog.showdatetime", true);
        String string = this.props.getStringProperty("org.apache.logging.log4j.simplelog.level");
        this.defaultLevel = Level.toLevel(string, Level.ERROR);
        this.dateTimeFormat = this.showDateTime ? this.props.getStringProperty("org.apache.logging.log4j.simplelog.dateTimeFormat", DEFAULT_DATE_TIME_FORMAT) : null;
        String string2 = this.props.getStringProperty("org.apache.logging.log4j.simplelog.logFile", SYSTEM_ERR);
        if (SYSTEM_ERR.equalsIgnoreCase(string2)) {
            printStream = System.err;
        } else if (SYSTEM_OUT.equalsIgnoreCase(string2)) {
            printStream = System.out;
        } else {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(string2);
                printStream = new PrintStream(fileOutputStream);
            } catch (FileNotFoundException fileNotFoundException) {
                printStream = System.err;
            }
        }
        this.stream = printStream;
    }

    @Override
    public ExtendedLogger getLogger(String string) {
        return this.getLogger(string, null);
    }

    @Override
    public ExtendedLogger getLogger(String string, MessageFactory messageFactory) {
        ExtendedLogger extendedLogger = this.loggerRegistry.getLogger(string, messageFactory);
        if (extendedLogger != null) {
            AbstractLogger.checkMessageFactory(extendedLogger, messageFactory);
            return extendedLogger;
        }
        SimpleLogger simpleLogger = new SimpleLogger(string, this.defaultLevel, this.showLogName, this.showShortName, this.showDateTime, this.showContextMap, this.dateTimeFormat, messageFactory, this.props, this.stream);
        this.loggerRegistry.putIfAbsent(string, messageFactory, simpleLogger);
        return this.loggerRegistry.getLogger(string, messageFactory);
    }

    @Override
    public boolean hasLogger(String string) {
        return true;
    }

    @Override
    public boolean hasLogger(String string, MessageFactory messageFactory) {
        return true;
    }

    @Override
    public boolean hasLogger(String string, Class<? extends MessageFactory> clazz) {
        return true;
    }

    @Override
    public Object getExternalContext() {
        return null;
    }
}

