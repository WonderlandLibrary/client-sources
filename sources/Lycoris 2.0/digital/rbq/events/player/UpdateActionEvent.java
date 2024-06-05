/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.events.player;

import digital.rbq.events.Event;

public final class UpdateActionEvent
implements Event {
    private boolean sprintState;
    private boolean sneakState;

    public UpdateActionEvent(boolean sprintState, boolean sneakState) {
        this.sprintState = sprintState;
        this.sneakState = sneakState;
    }

    public boolean isSprintState() {
        return this.sprintState;
    }

    public void setSprintState(boolean sprintState) {
        this.sprintState = sprintState;
    }

    public boolean isSneakState() {
        return this.sneakState;
    }

    public void setSneakState(boolean sneakState) {
        this.sneakState = sneakState;
    }
}

