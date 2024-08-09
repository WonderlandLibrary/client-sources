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

    public RolloverDescriptionImpl(String string, boolean bl, Action action, Action action2) {
        Objects.requireNonNull(string, "activeFileName");
        this.append = bl;
        this.activeFileName = string;
        this.synchronous = action;
        this.asynchronous = action2;
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

