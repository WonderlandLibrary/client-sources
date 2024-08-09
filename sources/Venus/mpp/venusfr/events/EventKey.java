/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.events;

public class EventKey {
    int key;

    public boolean isKeyDown(int n) {
        return this.key == n;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int n) {
        this.key = n;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof EventKey)) {
            return true;
        }
        EventKey eventKey = (EventKey)object;
        if (!eventKey.canEqual(this)) {
            return true;
        }
        return this.getKey() != eventKey.getKey();
    }

    protected boolean canEqual(Object object) {
        return object instanceof EventKey;
    }

    public int hashCode() {
        int n = 59;
        int n2 = 1;
        n2 = n2 * 59 + this.getKey();
        return n2;
    }

    public String toString() {
        return "EventKey(key=" + this.getKey() + ")";
    }

    public EventKey(int n) {
        this.key = n;
    }
}

