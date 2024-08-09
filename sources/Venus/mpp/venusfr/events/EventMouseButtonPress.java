/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.events;

import net.minecraftforge.eventbus.api.Event;

public class EventMouseButtonPress
extends Event {
    private int button;

    public int getButton() {
        return this.button;
    }

    public void setButton(int n) {
        this.button = n;
    }

    public String toString() {
        return "EventMouseButtonPress(button=" + this.getButton() + ")";
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof EventMouseButtonPress)) {
            return true;
        }
        EventMouseButtonPress eventMouseButtonPress = (EventMouseButtonPress)object;
        if (!eventMouseButtonPress.canEqual(this)) {
            return true;
        }
        if (!super.equals(object)) {
            return true;
        }
        return this.getButton() != eventMouseButtonPress.getButton();
    }

    protected boolean canEqual(Object object) {
        return object instanceof EventMouseButtonPress;
    }

    public int hashCode() {
        int n = 59;
        int n2 = super.hashCode();
        n2 = n2 * 59 + this.getButton();
        return n2;
    }

    public EventMouseButtonPress(int n) {
        this.button = n;
    }
}

