/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.logging;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLogger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.NOPLoggerFactory;

public class Slf4JLoggerFactory
extends InternalLoggerFactory {
    public static final InternalLoggerFactory INSTANCE;
    static final boolean $assertionsDisabled;

    @Deprecated
    public Slf4JLoggerFactory() {
    }

    Slf4JLoggerFactory(boolean bl) {
        if (!$assertionsDisabled && !bl) {
            throw new AssertionError();
        }
        if (LoggerFactory.getILoggerFactory() instanceof NOPLoggerFactory) {
            throw new NoClassDefFoundError("NOPLoggerFactory not supported");
        }
    }

    @Override
    public InternalLogger newInstance(String string) {
        return new Slf4JLogger(LoggerFactory.getLogger(string));
    }

    static {
        $assertionsDisabled = !Slf4JLoggerFactory.class.desiredAssertionStatus();
        INSTANCE = new Slf4JLoggerFactory();
    }
}

