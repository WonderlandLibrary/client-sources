/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j.spi;

import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.spi.MDCAdapter;

public interface SLF4JServiceProvider {
    public ILoggerFactory getLoggerFactory();

    public IMarkerFactory getMarkerFactory();

    public MDCAdapter getMDCAdapter();

    public String getRequestedApiVersion();

    public void initialize();
}

