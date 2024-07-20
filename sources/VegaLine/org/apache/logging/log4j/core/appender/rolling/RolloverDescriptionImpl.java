/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling;

import java.util.Objects;
import org.apache.logging.log4j.core.appender.rolling.RolloverDescription;
import org.apache.logging.log4j.core.appender.rolling.action.Action;

public final class RolloverDescriptionImpl
implements RolloverDescription {
    private final String activeFileName;
    private final boolean append;
    private final Action synchronous;
    private final Action asynchronous;

    public RolloverDescriptionImpl(String activeFileName, boolean append, Action synchronous, Action asynchronous) {
        Objects.requireNonNull(activeFileName, "activeFileName");
        this.append = append;
        this.activeFileName = activeFileName;
        this.synchronous = synchronous;
        this.asynchronous = asynchronous;
    }

    @Override
    public String getActiveFileName() {
        return this.activeFileName;
    }

    @Override
    public boolean getAppend() {
        return this.append;
    }

    @Override
    public Action getSynchronous() {
        return this.synchronous;
    }

    @Override
    public Action getAsynchronous() {
        return this.asynchronous;
    }
}

