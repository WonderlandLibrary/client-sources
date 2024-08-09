/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.events;

public class EventUpdate {
    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof EventUpdate)) {
            return true;
        }
        EventUpdate eventUpdate = (EventUpdate)object;
        return !eventUpdate.canEqual(this);
    }

    protected boolean canEqual(Object object) {
        return object instanceof EventUpdate;
    }

    public int hashCode() {
        boolean bl = true;
        return 0;
    }

    public String toString() {
        return "EventUpdate()";
    }
}

