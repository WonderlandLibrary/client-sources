/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.spi;

import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.spi.AbstractLogger;

@Deprecated
public class LoggerContextKey {
    public static String create(String string) {
        return LoggerContextKey.create(string, AbstractLogger.DEFAULT_MESSAGE_FACTORY_CLASS);
    }

    public static String create(String string, MessageFactory messageFactory) {
        Class<MessageFactory> clazz = messageFactory != null ? messageFactory.getClass() : AbstractLogger.DEFAULT_MESSAGE_FACTORY_CLASS;
        return LoggerContextKey.create(string, clazz);
    }

    public static String create(String string, Class<? extends MessageFactory> clazz) {
        Class<? extends MessageFactory> clazz2 = clazz != null ? clazz : AbstractLogger.DEFAULT_MESSAGE_FACTORY_CLASS;
        return string + "." + clazz2.getName();
    }
}

