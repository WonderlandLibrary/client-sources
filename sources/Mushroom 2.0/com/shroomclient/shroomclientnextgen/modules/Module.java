package com.shroomclient.shroomclientnextgen.modules;

import com.shroomclient.shroomclientnextgen.events.Bus;
import com.shroomclient.shroomclientnextgen.events.impl.ModuleStateChangeEvent;

public abstract class Module {

    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean flag, boolean silent, boolean loadDone) {
        boolean changed = enabled != flag;
        if (!silent && changed) {
            if (
                Bus.post(
                    new ModuleStateChangeEvent(this.getClass(), enabled, flag)
                )
            ) return;
        }

        enabled = flag;
        if (changed && loadDone) {
            if (enabled) {
                onEnable();
            } else {
                onDisable();
            }
        }
    }

    public void setEnabled(boolean flag, boolean silent) {
        setEnabled(flag, silent, true);
    }

    public void postEnabledStateHandlers() {
        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    // Returns the new state
    public boolean toggle(boolean silent) {
        setEnabled(!enabled, silent);
        return enabled;
    }

    protected abstract void onEnable();

    protected abstract void onDisable();
}
