/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j.spi;

import org.slf4j.event.LoggingEvent;

public interface LoggingEventAware {
    public void log(LoggingEvent var1);
}

