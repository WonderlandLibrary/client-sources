/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.tools;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Generate {
    static final String PACKAGE_DECLARATION = "package %s;%n%n";
    static final String FQCN_FIELD = "    private static final String FQCN = %s.class.getName();%n";
    static final String LEVEL_FIELD = "    private static final Level %s = Level.forName(\"%s\", %d);%n";
    static final String FACTORY_METHODS = "%n    /**%n     * Returns a custom Logger with the name of the calling class.%n     * %n     * @return The custom Logger for the calling class.%n     */%n    public static CLASSNAME create() {%n        final Logger wrapped = LogManager.getLogger();%n        return new CLASSNAME(wrapped);%n    }%n%n    /**%n     * Returns a custom Logger using the fully qualified name of the Class as%n     * the Logger name.%n     * %n     * @param loggerName The Class whose name should be used as the Logger name.%n     *            If null it will default to the calling class.%n     * @return The custom Logger.%n     */%n    public static CLASSNAME create(final Class<?> loggerName) {%n        final Logger wrapped = LogManager.getLogger(loggerName);%n        return new CLASSNAME(wrapped);%n    }%n%n    /**%n     * Returns a custom Logger using the fully qualified name of the Class as%n     * the Logger name.%n     * %n     * @param loggerName The Class whose name should be used as the Logger name.%n     *            If null it will default to the calling class.%n     * @param messageFactory The message factory is used only when creating a%n     *            logger, subsequent use does not change the logger but will log%n     *            a warning if mismatched.%n     * @return The custom Logger.%n     */%n    public static CLASSNAME create(final Class<?> loggerName, final MessageFactory messageFactory) {%n        final Logger wrapped = LogManager.getLogger(loggerName, messageFactory);%n        return new CLASSNAME(wrapped);%n    }%n%n    /**%n     * Returns a custom Logger using the fully qualified class name of the value%n     * as the Logger name.%n     * %n     * @param value The value whose class name should be used as the Logger%n     *            name. If null the name of the calling class will be used as%n     *            the logger name.%n     * @return The custom Logger.%n     */%n    public static CLASSNAME create(final Object value) {%n        final Logger wrapped = LogManager.getLogger(value);%n        return new CLASSNAME(wrapped);%n    }%n%n    /**%n     * Returns a custom Logger using the fully qualified class name of the value%n     * as the Logger name.%n     * %n     * @param value The value whose class name should be used as the Logger%n     *            name. If null the name of the calling class will be used as%n     *            the logger name.%n     * @param messageFactory The message factory is used only when creating a%n     *            logger, subsequent use does not change the logger but will log%n     *            a warning if mismatched.%n     * @return The custom Logger.%n     */%n    public static CLASSNAME create(final Object value, final MessageFactory messageFactory) {%n        final Logger wrapped = LogManager.getLogger(value, messageFactory);%n        return new CLASSNAME(wrapped);%n    }%n%n    /**%n     * Returns a custom Logger with the specified name.%n     * %n     * @param name The logger name. If null the name of the calling class will%n     *            be used.%n     * @return The custom Logger.%n     */%n    public static CLASSNAME create(final String name) {%n        final Logger wrapped = LogManager.getLogger(name);%n        return new CLASSNAME(wrapped);%n    }%n%n    /**%n     * Returns a custom Logger with the specified name.%n     * %n     * @param name The logger name. If null the name of the calling class will%n     *            be used.%n     * @param messageFactory The message factory is used only when creating a%n     *            logger, subsequent use does not change the logger but will log%n     *            a warning if mismatched.%n     * @return The custom Logger.%n     */%n    public static CLASSNAME create(final String name, final MessageFactory messageFactory) {%n        final Logger wrapped = LogManager.getLogger(name, messageFactory);%n        return new CLASSNAME(wrapped);%n    }%n";
    static final String METHODS = "%n    /**%n     * Logs a message with the specific Marker at the {@code CUSTOM_LEVEL} level.%n     * %n     * @param marker the marker data specific to this log statement%n     * @param msg the message string to be logged%n     */%n    public void methodName(final Marker marker, final Message msg) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, marker, msg, (Throwable) null);%n    }%n%n    /**%n     * Logs a message with the specific Marker at the {@code CUSTOM_LEVEL} level.%n     * %n     * @param marker the marker data specific to this log statement%n     * @param msg the message string to be logged%n     * @param t A Throwable or null.%n     */%n    public void methodName(final Marker marker, final Message msg, final Throwable t) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, marker, msg, t);%n    }%n%n    /**%n     * Logs a message object with the {@code CUSTOM_LEVEL} level.%n     * %n     * @param marker the marker data specific to this log statement%n     * @param message the message object to log.%n     */%n    public void methodName(final Marker marker, final Object message) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, marker, message, (Throwable) null);%n    }%n%n    /**%n     * Logs a message CharSequence with the {@code CUSTOM_LEVEL} level.%n     * %n     * @param marker the marker data specific to this log statement%n     * @param message the message CharSequence to log.%n     * @since Log4j-2.6%n     */%n    public void methodName(final Marker marker, final CharSequence message) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, marker, message, (Throwable) null);%n    }%n%n    /**%n     * Logs a message at the {@code CUSTOM_LEVEL} level including the stack trace of%n     * the {@link Throwable} {@code t} passed as parameter.%n     * %n     * @param marker the marker data specific to this log statement%n     * @param message the message to log.%n     * @param t the exception to log, including its stack trace.%n     */%n    public void methodName(final Marker marker, final Object message, final Throwable t) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, marker, message, t);%n    }%n%n    /**%n     * Logs a message at the {@code CUSTOM_LEVEL} level including the stack trace of%n     * the {@link Throwable} {@code t} passed as parameter.%n     * %n     * @param marker the marker data specific to this log statement%n     * @param message the CharSequence to log.%n     * @param t the exception to log, including its stack trace.%n     * @since Log4j-2.6%n     */%n    public void methodName(final Marker marker, final CharSequence message, final Throwable t) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, marker, message, t);%n    }%n%n    /**%n     * Logs a message object with the {@code CUSTOM_LEVEL} level.%n     * %n     * @param marker the marker data specific to this log statement%n     * @param message the message object to log.%n     */%n    public void methodName(final Marker marker, final String message) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, marker, message, (Throwable) null);%n    }%n%n    /**%n     * Logs a message with parameters at the {@code CUSTOM_LEVEL} level.%n     * %n     * @param marker the marker data specific to this log statement%n     * @param message the message to log; the format depends on the message factory.%n     * @param params parameters to the message.%n     * @see #getMessageFactory()%n     */%n    public void methodName(final Marker marker, final String message, final Object... params) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, marker, message, params);%n    }%n%n    /**%n     * Logs a message with parameters at the {@code CUSTOM_LEVEL} level.%n     * %n     * @param marker the marker data specific to this log statement%n     * @param message the message to log; the format depends on the message factory.%n     * @param p0 parameter to the message.%n     * @see #getMessageFactory()%n     * @since Log4j-2.6%n     */%n    public void methodName(final Marker marker, final String message, final Object p0) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, marker, message, p0);%n    }%n%n    /**%n     * Logs a message with parameters at the {@code CUSTOM_LEVEL} level.%n     * %n     * @param marker the marker data specific to this log statement%n     * @param message the message to log; the format depends on the message factory.%n     * @param p0 parameter to the message.%n     * @param p1 parameter to the message.%n     * @see #getMessageFactory()%n     * @since Log4j-2.6%n     */%n    public void methodName(final Marker marker, final String message, final Object p0, final Object p1) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, marker, message, p0, p1);%n    }%n%n    /**%n     * Logs a message with parameters at the {@code CUSTOM_LEVEL} level.%n     * %n     * @param marker the marker data specific to this log statement%n     * @param message the message to log; the format depends on the message factory.%n     * @param p0 parameter to the message.%n     * @param p1 parameter to the message.%n     * @param p2 parameter to the message.%n     * @see #getMessageFactory()%n     * @since Log4j-2.6%n     */%n    public void methodName(final Marker marker, final String message, final Object p0, final Object p1, final Object p2) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, marker, message, p0, p1, p2);%n    }%n%n    /**%n     * Logs a message with parameters at the {@code CUSTOM_LEVEL} level.%n     * %n     * @param marker the marker data specific to this log statement%n     * @param message the message to log; the format depends on the message factory.%n     * @param p0 parameter to the message.%n     * @param p1 parameter to the message.%n     * @param p2 parameter to the message.%n     * @param p3 parameter to the message.%n     * @see #getMessageFactory()%n     * @since Log4j-2.6%n     */%n    public void methodName(final Marker marker, final String message, final Object p0, final Object p1, final Object p2,%n            final Object p3) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, marker, message, p0, p1, p2, p3);%n    }%n%n    /**%n     * Logs a message with parameters at the {@code CUSTOM_LEVEL} level.%n     * %n     * @param marker the marker data specific to this log statement%n     * @param message the message to log; the format depends on the message factory.%n     * @param p0 parameter to the message.%n     * @param p1 parameter to the message.%n     * @param p2 parameter to the message.%n     * @param p3 parameter to the message.%n     * @param p4 parameter to the message.%n     * @see #getMessageFactory()%n     * @since Log4j-2.6%n     */%n    public void methodName(final Marker marker, final String message, final Object p0, final Object p1, final Object p2,%n            final Object p3, final Object p4) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, marker, message, p0, p1, p2, p3, p4);%n    }%n%n    /**%n     * Logs a message with parameters at the {@code CUSTOM_LEVEL} level.%n     * %n     * @param marker the marker data specific to this log statement%n     * @param message the message to log; the format depends on the message factory.%n     * @param p0 parameter to the message.%n     * @param p1 parameter to the message.%n     * @param p2 parameter to the message.%n     * @param p3 parameter to the message.%n     * @param p4 parameter to the message.%n     * @param p5 parameter to the message.%n     * @see #getMessageFactory()%n     * @since Log4j-2.6%n     */%n    public void methodName(final Marker marker, final String message, final Object p0, final Object p1, final Object p2,%n            final Object p3, final Object p4, final Object p5) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, marker, message, p0, p1, p2, p3, p4, p5);%n    }%n%n    /**%n     * Logs a message with parameters at the {@code CUSTOM_LEVEL} level.%n     * %n     * @param marker the marker data specific to this log statement%n     * @param message the message to log; the format depends on the message factory.%n     * @param p0 parameter to the message.%n     * @param p1 parameter to the message.%n     * @param p2 parameter to the message.%n     * @param p3 parameter to the message.%n     * @param p4 parameter to the message.%n     * @param p5 parameter to the message.%n     * @param p6 parameter to the message.%n     * @see #getMessageFactory()%n     * @since Log4j-2.6%n     */%n    public void methodName(final Marker marker, final String message, final Object p0, final Object p1, final Object p2,%n            final Object p3, final Object p4, final Object p5, final Object p6) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, marker, message, p0, p1, p2, p3, p4, p5, p6);%n    }%n%n    /**%n     * Logs a message with parameters at the {@code CUSTOM_LEVEL} level.%n     * %n     * @param marker the marker data specific to this log statement%n     * @param message the message to log; the format depends on the message factory.%n     * @param p0 parameter to the message.%n     * @param p1 parameter to the message.%n     * @param p2 parameter to the message.%n     * @param p3 parameter to the message.%n     * @param p4 parameter to the message.%n     * @param p5 parameter to the message.%n     * @param p6 parameter to the message.%n     * @param p7 parameter to the message.%n     * @see #getMessageFactory()%n     * @since Log4j-2.6%n     */%n    public void methodName(final Marker marker, final String message, final Object p0, final Object p1, final Object p2,%n            final Object p3, final Object p4, final Object p5, final Object p6,%n            final Object p7) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, marker, message, p0, p1, p2, p3, p4, p5, p6, p7);%n    }%n%n    /**%n     * Logs a message with parameters at the {@code CUSTOM_LEVEL} level.%n     * %n     * @param marker the marker data specific to this log statement%n     * @param message the message to log; the format depends on the message factory.%n     * @param p0 parameter to the message.%n     * @param p1 parameter to the message.%n     * @param p2 parameter to the message.%n     * @param p3 parameter to the message.%n     * @param p4 parameter to the message.%n     * @param p5 parameter to the message.%n     * @param p6 parameter to the message.%n     * @param p7 parameter to the message.%n     * @param p8 parameter to the message.%n     * @see #getMessageFactory()%n     * @since Log4j-2.6%n     */%n    public void methodName(final Marker marker, final String message, final Object p0, final Object p1, final Object p2,%n            final Object p3, final Object p4, final Object p5, final Object p6,%n            final Object p7, final Object p8) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);%n    }%n%n    /**%n     * Logs a message with parameters at the {@code CUSTOM_LEVEL} level.%n     * %n     * @param marker the marker data specific to this log statement%n     * @param message the message to log; the format depends on the message factory.%n     * @param p0 parameter to the message.%n     * @param p1 parameter to the message.%n     * @param p2 parameter to the message.%n     * @param p3 parameter to the message.%n     * @param p4 parameter to the message.%n     * @param p5 parameter to the message.%n     * @param p6 parameter to the message.%n     * @param p7 parameter to the message.%n     * @param p8 parameter to the message.%n     * @param p9 parameter to the message.%n     * @see #getMessageFactory()%n     * @since Log4j-2.6%n     */%n    public void methodName(final Marker marker, final String message, final Object p0, final Object p1, final Object p2,%n            final Object p3, final Object p4, final Object p5, final Object p6,%n            final Object p7, final Object p8, final Object p9) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, marker, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);%n    }%n%n    /**%n     * Logs a message at the {@code CUSTOM_LEVEL} level including the stack trace of%n     * the {@link Throwable} {@code t} passed as parameter.%n     * %n     * @param marker the marker data specific to this log statement%n     * @param message the message to log.%n     * @param t the exception to log, including its stack trace.%n     */%n    public void methodName(final Marker marker, final String message, final Throwable t) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, marker, message, t);%n    }%n%n    /**%n     * Logs the specified Message at the {@code CUSTOM_LEVEL} level.%n     * %n     * @param msg the message string to be logged%n     */%n    public void methodName(final Message msg) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, null, msg, (Throwable) null);%n    }%n%n    /**%n     * Logs the specified Message at the {@code CUSTOM_LEVEL} level.%n     * %n     * @param msg the message string to be logged%n     * @param t A Throwable or null.%n     */%n    public void methodName(final Message msg, final Throwable t) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, null, msg, t);%n    }%n%n    /**%n     * Logs a message object with the {@code CUSTOM_LEVEL} level.%n     * %n     * @param message the message object to log.%n     */%n    public void methodName(final Object message) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, null, message, (Throwable) null);%n    }%n%n    /**%n     * Logs a message at the {@code CUSTOM_LEVEL} level including the stack trace of%n     * the {@link Throwable} {@code t} passed as parameter.%n     * %n     * @param message the message to log.%n     * @param t the exception to log, including its stack trace.%n     */%n    public void methodName(final Object message, final Throwable t) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, null, message, t);%n    }%n%n    /**%n     * Logs a message CharSequence with the {@code CUSTOM_LEVEL} level.%n     * %n     * @param message the message CharSequence to log.%n     * @since Log4j-2.6%n     */%n    public void methodName(final CharSequence message) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, null, message, (Throwable) null);%n    }%n%n    /**%n     * Logs a CharSequence at the {@code CUSTOM_LEVEL} level including the stack trace of%n     * the {@link Throwable} {@code t} passed as parameter.%n     * %n     * @param message the CharSequence to log.%n     * @param t the exception to log, including its stack trace.%n     * @since Log4j-2.6%n     */%n    public void methodName(final CharSequence message, final Throwable t) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, null, message, t);%n    }%n%n    /**%n     * Logs a message object with the {@code CUSTOM_LEVEL} level.%n     * %n     * @param message the message object to log.%n     */%n    public void methodName(final String message) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, null, message, (Throwable) null);%n    }%n%n    /**%n     * Logs a message with parameters at the {@code CUSTOM_LEVEL} level.%n     * %n     * @param message the message to log; the format depends on the message factory.%n     * @param params parameters to the message.%n     * @see #getMessageFactory()%n     */%n    public void methodName(final String message, final Object... params) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, null, message, params);%n    }%n%n    /**%n     * Logs a message with parameters at the {@code CUSTOM_LEVEL} level.%n     * %n     * @param message the message to log; the format depends on the message factory.%n     * @param p0 parameter to the message.%n     * @see #getMessageFactory()%n     * @since Log4j-2.6%n     */%n    public void methodName(final String message, final Object p0) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, null, message, p0);%n    }%n%n    /**%n     * Logs a message with parameters at the {@code CUSTOM_LEVEL} level.%n     * %n     * @param message the message to log; the format depends on the message factory.%n     * @param p0 parameter to the message.%n     * @param p1 parameter to the message.%n     * @see #getMessageFactory()%n     * @since Log4j-2.6%n     */%n    public void methodName(final String message, final Object p0, final Object p1) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, null, message, p0, p1);%n    }%n%n    /**%n     * Logs a message with parameters at the {@code CUSTOM_LEVEL} level.%n     * %n     * @param message the message to log; the format depends on the message factory.%n     * @param p0 parameter to the message.%n     * @param p1 parameter to the message.%n     * @param p2 parameter to the message.%n     * @see #getMessageFactory()%n     * @since Log4j-2.6%n     */%n    public void methodName(final String message, final Object p0, final Object p1, final Object p2) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, null, message, p0, p1, p2);%n    }%n%n    /**%n     * Logs a message with parameters at the {@code CUSTOM_LEVEL} level.%n     * %n     * @param message the message to log; the format depends on the message factory.%n     * @param p0 parameter to the message.%n     * @param p1 parameter to the message.%n     * @param p2 parameter to the message.%n     * @param p3 parameter to the message.%n     * @see #getMessageFactory()%n     * @since Log4j-2.6%n     */%n    public void methodName(final String message, final Object p0, final Object p1, final Object p2,%n            final Object p3) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, null, message, p0, p1, p2, p3);%n    }%n%n    /**%n     * Logs a message with parameters at the {@code CUSTOM_LEVEL} level.%n     * %n     * @param message the message to log; the format depends on the message factory.%n     * @param p0 parameter to the message.%n     * @param p1 parameter to the message.%n     * @param p2 parameter to the message.%n     * @param p3 parameter to the message.%n     * @param p4 parameter to the message.%n     * @see #getMessageFactory()%n     * @since Log4j-2.6%n     */%n    public void methodName(final String message, final Object p0, final Object p1, final Object p2,%n            final Object p3, final Object p4) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, null, message, p0, p1, p2, p3, p4);%n    }%n%n    /**%n     * Logs a message with parameters at the {@code CUSTOM_LEVEL} level.%n     * %n     * @param message the message to log; the format depends on the message factory.%n     * @param p0 parameter to the message.%n     * @param p1 parameter to the message.%n     * @param p2 parameter to the message.%n     * @param p3 parameter to the message.%n     * @param p4 parameter to the message.%n     * @param p5 parameter to the message.%n     * @see #getMessageFactory()%n     * @since Log4j-2.6%n     */%n    public void methodName(final String message, final Object p0, final Object p1, final Object p2,%n            final Object p3, final Object p4, final Object p5) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, null, message, p0, p1, p2, p3, p4, p5);%n    }%n%n    /**%n     * Logs a message with parameters at the {@code CUSTOM_LEVEL} level.%n     * %n     * @param message the message to log; the format depends on the message factory.%n     * @param p0 parameter to the message.%n     * @param p1 parameter to the message.%n     * @param p2 parameter to the message.%n     * @param p3 parameter to the message.%n     * @param p4 parameter to the message.%n     * @param p5 parameter to the message.%n     * @param p6 parameter to the message.%n     * @see #getMessageFactory()%n     * @since Log4j-2.6%n     */%n    public void methodName(final String message, final Object p0, final Object p1, final Object p2,%n            final Object p3, final Object p4, final Object p5, final Object p6) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, null, message, p0, p1, p2, p3, p4, p5, p6);%n    }%n%n    /**%n     * Logs a message with parameters at the {@code CUSTOM_LEVEL} level.%n     * %n     * @param message the message to log; the format depends on the message factory.%n     * @param p0 parameter to the message.%n     * @param p1 parameter to the message.%n     * @param p2 parameter to the message.%n     * @param p3 parameter to the message.%n     * @param p4 parameter to the message.%n     * @param p5 parameter to the message.%n     * @param p6 parameter to the message.%n     * @param p7 parameter to the message.%n     * @see #getMessageFactory()%n     * @since Log4j-2.6%n     */%n    public void methodName(final String message, final Object p0, final Object p1, final Object p2,%n            final Object p3, final Object p4, final Object p5, final Object p6,%n            final Object p7) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, null, message, p0, p1, p2, p3, p4, p5, p6, p7);%n    }%n%n    /**%n     * Logs a message with parameters at the {@code CUSTOM_LEVEL} level.%n     * %n     * @param message the message to log; the format depends on the message factory.%n     * @param p0 parameter to the message.%n     * @param p1 parameter to the message.%n     * @param p2 parameter to the message.%n     * @param p3 parameter to the message.%n     * @param p4 parameter to the message.%n     * @param p5 parameter to the message.%n     * @param p6 parameter to the message.%n     * @param p7 parameter to the message.%n     * @param p8 parameter to the message.%n     * @see #getMessageFactory()%n     * @since Log4j-2.6%n     */%n    public void methodName(final String message, final Object p0, final Object p1, final Object p2,%n            final Object p3, final Object p4, final Object p5, final Object p6,%n            final Object p7, final Object p8) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, null, message, p0, p1, p2, p3, p4, p5, p6, p7, p8);%n    }%n%n    /**%n     * Logs a message with parameters at the {@code CUSTOM_LEVEL} level.%n     * %n     * @param message the message to log; the format depends on the message factory.%n     * @param p0 parameter to the message.%n     * @param p1 parameter to the message.%n     * @param p2 parameter to the message.%n     * @param p3 parameter to the message.%n     * @param p4 parameter to the message.%n     * @param p5 parameter to the message.%n     * @param p6 parameter to the message.%n     * @param p7 parameter to the message.%n     * @param p8 parameter to the message.%n     * @param p9 parameter to the message.%n     * @see #getMessageFactory()%n     * @since Log4j-2.6%n     */%n    public void methodName(final String message, final Object p0, final Object p1, final Object p2,%n            final Object p3, final Object p4, final Object p5, final Object p6,%n            final Object p7, final Object p8, final Object p9) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, null, message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);%n    }%n%n    /**%n     * Logs a message at the {@code CUSTOM_LEVEL} level including the stack trace of%n     * the {@link Throwable} {@code t} passed as parameter.%n     * %n     * @param message the message to log.%n     * @param t the exception to log, including its stack trace.%n     */%n    public void methodName(final String message, final Throwable t) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, null, message, t);%n    }%n%n    /**%n     * Logs a message which is only to be constructed if the logging level is the {@code CUSTOM_LEVEL}level.%n     *%n     * @param msgSupplier A function, which when called, produces the desired log message;%n     *            the format depends on the message factory.%n     * @since Log4j-2.4%n     */%n    public void methodName(final Supplier<?> msgSupplier) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, null, msgSupplier, (Throwable) null);%n    }%n%n    /**%n     * Logs a message (only to be constructed if the logging level is the {@code CUSTOM_LEVEL}%n     * level) including the stack trace of the {@link Throwable} <code>t</code> passed as parameter.%n     *%n     * @param msgSupplier A function, which when called, produces the desired log message;%n     *            the format depends on the message factory.%n     * @param t the exception to log, including its stack trace.%n     * @since Log4j-2.4%n     */%n    public void methodName(final Supplier<?> msgSupplier, final Throwable t) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, null, msgSupplier, t);%n    }%n%n    /**%n     * Logs a message which is only to be constructed if the logging level is the%n     * {@code CUSTOM_LEVEL} level with the specified Marker.%n     *%n     * @param marker the marker data specific to this log statement%n     * @param msgSupplier A function, which when called, produces the desired log message;%n     *            the format depends on the message factory.%n     * @since Log4j-2.4%n     */%n    public void methodName(final Marker marker, final Supplier<?> msgSupplier) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, marker, msgSupplier, (Throwable) null);%n    }%n%n    /**%n     * Logs a message with parameters which are only to be constructed if the logging level is the%n     * {@code CUSTOM_LEVEL} level.%n     *%n     * @param marker the marker data specific to this log statement%n     * @param message the message to log; the format depends on the message factory.%n     * @param paramSuppliers An array of functions, which when called, produce the desired log message parameters.%n     * @since Log4j-2.4%n     */%n    public void methodName(final Marker marker, final String message, final Supplier<?>... paramSuppliers) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, marker, message, paramSuppliers);%n    }%n%n    /**%n     * Logs a message (only to be constructed if the logging level is the {@code CUSTOM_LEVEL}%n     * level) with the specified Marker and including the stack trace of the {@link Throwable}%n     * <code>t</code> passed as parameter.%n     *%n     * @param marker the marker data specific to this log statement%n     * @param msgSupplier A function, which when called, produces the desired log message;%n     *            the format depends on the message factory.%n     * @param t A Throwable or null.%n     * @since Log4j-2.4%n     */%n    public void methodName(final Marker marker, final Supplier<?> msgSupplier, final Throwable t) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, marker, msgSupplier, t);%n    }%n%n    /**%n     * Logs a message with parameters which are only to be constructed if the logging level is%n     * the {@code CUSTOM_LEVEL} level.%n     *%n     * @param message the message to log; the format depends on the message factory.%n     * @param paramSuppliers An array of functions, which when called, produce the desired log message parameters.%n     * @since Log4j-2.4%n     */%n    public void methodName(final String message, final Supplier<?>... paramSuppliers) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, null, message, paramSuppliers);%n    }%n%n    /**%n     * Logs a message which is only to be constructed if the logging level is the%n     * {@code CUSTOM_LEVEL} level with the specified Marker. The {@code MessageSupplier} may or may%n     * not use the {@link MessageFactory} to construct the {@code Message}.%n     *%n     * @param marker the marker data specific to this log statement%n     * @param msgSupplier A function, which when called, produces the desired log message.%n     * @since Log4j-2.4%n     */%n    public void methodName(final Marker marker, final MessageSupplier msgSupplier) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, marker, msgSupplier, (Throwable) null);%n    }%n%n    /**%n     * Logs a message (only to be constructed if the logging level is the {@code CUSTOM_LEVEL}%n     * level) with the specified Marker and including the stack trace of the {@link Throwable}%n     * <code>t</code> passed as parameter. The {@code MessageSupplier} may or may not use the%n     * {@link MessageFactory} to construct the {@code Message}.%n     *%n     * @param marker the marker data specific to this log statement%n     * @param msgSupplier A function, which when called, produces the desired log message.%n     * @param t A Throwable or null.%n     * @since Log4j-2.4%n     */%n    public void methodName(final Marker marker, final MessageSupplier msgSupplier, final Throwable t) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, marker, msgSupplier, t);%n    }%n%n    /**%n     * Logs a message which is only to be constructed if the logging level is the%n     * {@code CUSTOM_LEVEL} level. The {@code MessageSupplier} may or may not use the%n     * {@link MessageFactory} to construct the {@code Message}.%n     *%n     * @param msgSupplier A function, which when called, produces the desired log message.%n     * @since Log4j-2.4%n     */%n    public void methodName(final MessageSupplier msgSupplier) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, null, msgSupplier, (Throwable) null);%n    }%n%n    /**%n     * Logs a message (only to be constructed if the logging level is the {@code CUSTOM_LEVEL}%n     * level) including the stack trace of the {@link Throwable} <code>t</code> passed as parameter.%n     * The {@code MessageSupplier} may or may not use the {@link MessageFactory} to construct the%n     * {@code Message}.%n     *%n     * @param msgSupplier A function, which when called, produces the desired log message.%n     * @param t the exception to log, including its stack trace.%n     * @since Log4j-2.4%n     */%n    public void methodName(final MessageSupplier msgSupplier, final Throwable t) {%n        logger.logIfEnabled(FQCN, CUSTOM_LEVEL, null, msgSupplier, t);%n    }%n";

    private Generate() {
    }

    private static void generate(String[] stringArray, Type type) {
        Generate.generate(stringArray, type, System.out);
    }

    static void generate(String[] stringArray, Type type, PrintStream printStream) {
        if (!Generate.validate(stringArray)) {
            Generate.usage(printStream, type.generator());
            System.exit(-1);
        }
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(stringArray));
        String string = (String)arrayList.remove(0);
        List<LevelInfo> list = LevelInfo.parse(arrayList, type.generator());
        printStream.println(Generate.generateSource(string, list, type));
    }

    static boolean validate(String[] stringArray) {
        return stringArray.length < 2;
    }

    private static void usage(PrintStream printStream, Class<?> clazz) {
        printStream.println("Usage: java " + clazz.getName() + " className LEVEL1=intLevel1 [LEVEL2=intLevel2...]");
        printStream.println("       Where className is the fully qualified class name of the custom/extended logger");
        printStream.println("       to generate, followed by a space-separated list of custom log levels.");
        printStream.println("       For each custom log level, specify NAME=intLevel (without spaces).");
    }

    static String generateSource(String string, List<LevelInfo> list, Type type) {
        StringBuilder stringBuilder = new StringBuilder(10000 * list.size());
        int n = string.lastIndexOf(46);
        String string2 = string.substring(0, n >= 0 ? n : 0);
        if (!string2.isEmpty()) {
            stringBuilder.append(String.format(PACKAGE_DECLARATION, string2));
        }
        stringBuilder.append(String.format(type.imports(), ""));
        String string3 = string.substring(string.lastIndexOf(46) + 1);
        String string4 = Generate.javadocDescription(list);
        stringBuilder.append(String.format(type.declaration(), string4, string3));
        stringBuilder.append(String.format(FQCN_FIELD, string3));
        for (LevelInfo levelInfo : list) {
            stringBuilder.append(String.format(LEVEL_FIELD, levelInfo.name, levelInfo.name, levelInfo.intLevel));
        }
        stringBuilder.append(String.format(type.constructor(), string3));
        stringBuilder.append(String.format(FACTORY_METHODS.replaceAll("CLASSNAME", string3), ""));
        for (LevelInfo levelInfo : list) {
            String string5 = Generate.camelCase(levelInfo.name);
            String string6 = METHODS.replaceAll("CUSTOM_LEVEL", levelInfo.name);
            String string7 = string6.replaceAll("methodName", string5);
            stringBuilder.append(String.format(string7, ""));
        }
        stringBuilder.append('}');
        stringBuilder.append(System.getProperty("line.separator"));
        return stringBuilder.toString();
    }

    static String javadocDescription(List<LevelInfo> list) {
        if (list.size() == 1) {
            return "the " + list.get((int)0).name + " custom log level.";
        }
        StringBuilder stringBuilder = new StringBuilder(512);
        stringBuilder.append("the ");
        String string = "";
        for (int i = 0; i < list.size(); ++i) {
            stringBuilder.append(string);
            stringBuilder.append(list.get((int)i).name);
            string = i == list.size() - 2 ? " and " : ", ";
        }
        stringBuilder.append(" custom log levels.");
        return stringBuilder.toString();
    }

    static String camelCase(String string) {
        StringBuilder stringBuilder = new StringBuilder(string.length());
        boolean bl = true;
        for (char c : string.toCharArray()) {
            if (c == '_') {
                bl = false;
                continue;
            }
            stringBuilder.append(bl ? Character.toLowerCase(c) : Character.toUpperCase(c));
            bl = true;
        }
        return stringBuilder.toString();
    }

    static void access$100(String[] stringArray, Type type) {
        Generate.generate(stringArray, type);
    }

    static void access$200(PrintStream printStream, Class clazz) {
        Generate.usage(printStream, clazz);
    }

    static class 1 {
    }

    static class LevelInfo {
        final String name;
        final int intLevel;

        LevelInfo(String string) {
            String[] stringArray = string.split("=");
            this.name = stringArray[0];
            this.intLevel = Integer.parseInt(stringArray[5]);
        }

        public static List<LevelInfo> parse(List<String> list, Class<?> clazz) {
            ArrayList<LevelInfo> arrayList = new ArrayList<LevelInfo>(list.size());
            for (int i = 0; i < list.size(); ++i) {
                try {
                    arrayList.add(new LevelInfo(list.get(i)));
                    continue;
                } catch (Exception exception) {
                    System.err.println("Cannot parse custom level '" + list.get(i) + "': " + exception.toString());
                    Generate.access$200(System.err, clazz);
                    System.exit(-1);
                }
            }
            return arrayList;
        }
    }

    public static final class ExtendedLogger {
        public static void main(String[] stringArray) {
            Generate.access$100(stringArray, Type.EXTEND);
        }

        private ExtendedLogger() {
        }
    }

    public static final class CustomLogger {
        public static void main(String[] stringArray) {
            Generate.access$100(stringArray, Type.CUSTOM);
        }

        private CustomLogger() {
        }
    }

    static enum Type {
        CUSTOM{

            @Override
            String imports() {
                return "import java.io.Serializable;%nimport org.apache.logging.log4j.Level;%nimport org.apache.logging.log4j.LogManager;%nimport org.apache.logging.log4j.Logger;%nimport org.apache.logging.log4j.Marker;%nimport org.apache.logging.log4j.message.Message;%nimport org.apache.logging.log4j.message.MessageFactory;%nimport org.apache.logging.log4j.spi.AbstractLogger;%nimport org.apache.logging.log4j.spi.ExtendedLoggerWrapper;%nimport org.apache.logging.log4j.util.MessageSupplier;%nimport org.apache.logging.log4j.util.Supplier;%n%n";
            }

            @Override
            String declaration() {
                return "/**%n * Custom Logger interface with convenience methods for%n * %s%n * <p>Compatible with Log4j 2.6 or higher.</p>%n */%npublic final class %s implements Serializable {%n    private static final long serialVersionUID = " + System.nanoTime() + "L;%n" + "    private final ExtendedLoggerWrapper logger;%n" + "%n";
            }

            @Override
            String constructor() {
                return "%n    private %s(final Logger logger) {%n        this.logger = new ExtendedLoggerWrapper((AbstractLogger) logger, logger.getName(), logger.getMessageFactory());%n    }%n";
            }

            @Override
            Class<?> generator() {
                return CustomLogger.class;
            }
        }
        ,
        EXTEND{

            @Override
            String imports() {
                return "import org.apache.logging.log4j.Level;%nimport org.apache.logging.log4j.LogManager;%nimport org.apache.logging.log4j.Logger;%nimport org.apache.logging.log4j.Marker;%nimport org.apache.logging.log4j.message.Message;%nimport org.apache.logging.log4j.message.MessageFactory;%nimport org.apache.logging.log4j.spi.AbstractLogger;%nimport org.apache.logging.log4j.spi.ExtendedLoggerWrapper;%nimport org.apache.logging.log4j.util.MessageSupplier;%nimport org.apache.logging.log4j.util.Supplier;%n%n";
            }

            @Override
            String declaration() {
                return "/**%n * Extended Logger interface with convenience methods for%n * %s%n * <p>Compatible with Log4j 2.6 or higher.</p>%n */%npublic final class %s extends ExtendedLoggerWrapper {%n    private static final long serialVersionUID = " + System.nanoTime() + "L;%n" + "    private final ExtendedLoggerWrapper logger;%n" + "%n";
            }

            @Override
            String constructor() {
                return "%n    private %s(final Logger logger) {%n        super((AbstractLogger) logger, logger.getName(), logger.getMessageFactory());%n        this.logger = this;%n    }%n";
            }

            @Override
            Class<?> generator() {
                return ExtendedLogger.class;
            }
        };


        private Type() {
        }

        abstract String imports();

        abstract String declaration();

        abstract String constructor();

        abstract Class<?> generator();

        Type(1 var3_3) {
            this();
        }
    }
}

