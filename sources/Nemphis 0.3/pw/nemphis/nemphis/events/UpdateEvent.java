/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.events;

import pw.vertexcode.util.event.Event;

public class UpdateEvent
implements Event {
    private updateType type;

    public updateType getType() {
        return this.type;
    }

    public static enum updateType {
        MID,
        PRE,
        POST;
        

        private updateType(String string2, int n2) {
        }
    }

}

