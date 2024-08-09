/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.events;

public class ActionEvent {
    private boolean sprintState;

    public ActionEvent(boolean bl) {
        this.sprintState = bl;
    }

    public boolean isSprintState() {
        return this.sprintState;
    }

    public void setSprintState(boolean bl) {
        this.sprintState = bl;
    }
}

