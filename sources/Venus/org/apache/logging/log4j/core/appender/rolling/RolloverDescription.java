/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling;

import org.apache.logging.log4j.core.appender.rolling.action.Action;

public interface RolloverDescription {
    public String getActiveFileName();

    public boolean getAppend();

    public Action getSynchronous();

    public Action getAsynchronous();
}

