/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.impl;

import org.apache.logging.log4j.core.LoggerContext;

public final class ContextAnchor {
    public static final ThreadLocal<LoggerContext> THREAD_CONTEXT = new ThreadLocal();

    private ContextAnchor() {
    }
}

