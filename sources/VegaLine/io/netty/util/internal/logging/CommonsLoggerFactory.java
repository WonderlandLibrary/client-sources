/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.logging;

import io.netty.util.internal.logging.CommonsLogger;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import org.apache.commons.logging.LogFactory;

@Deprecated
public class CommonsLoggerFactory
extends InternalLoggerFactory {
    public static final InternalLoggerFactory INSTANCE = new CommonsLoggerFactory();

    @Deprecated
    public CommonsLoggerFactory() {
    }

    @Override
    public InternalLogger newInstance(String name) {
        return new CommonsLogger(LogFactory.getLog(name), name);
    }
}

