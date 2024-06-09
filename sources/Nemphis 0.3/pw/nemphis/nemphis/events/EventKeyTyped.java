/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.events;

import pw.vertexcode.util.event.Event;

public class EventKeyTyped
implements Event {
    private int key;

    public EventKeyTyped(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}

