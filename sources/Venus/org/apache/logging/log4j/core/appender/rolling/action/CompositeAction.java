/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling.action;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.core.appender.rolling.action.AbstractAction;
import org.apache.logging.log4j.core.appender.rolling.action.Action;

public class CompositeAction
extends AbstractAction {
    private final Action[] actions;
    private final boolean stopOnError;

    public CompositeAction(List<Action> list, boolean bl) {
        this.actions = new Action[list.size()];
        list.toArray(this.actions);
        this.stopOnError = bl;
    }

    @Override
    public void run() {
        try {
            this.execute();
        } catch (IOException iOException) {
            LOGGER.warn("Exception during file rollover.", (Throwable)iOException);
        }
    }

    @Override
    public boolean execute() throws IOException {
        if (this.stopOnError) {
            for (Action action : this.actions) {
                if (action.execute()) continue;
                return true;
            }
            return false;
        }
        boolean bl = true;
        IOException iOException = null;
        for (Action action : this.actions) {
            try {
                bl &= action.execute();
            } catch (IOException iOException2) {
                bl = false;
                if (iOException != null) continue;
                iOException = iOException2;
            }
        }
        if (iOException != null) {
            throw iOException;
        }
        return bl;
    }

    public String toString() {
        return CompositeAction.class.getSimpleName() + Arrays.toString(this.actions);
    }

    public Action[] getActions() {
        return this.actions;
    }

    public boolean isStopOnError() {
        return this.stopOnError;
    }
}

