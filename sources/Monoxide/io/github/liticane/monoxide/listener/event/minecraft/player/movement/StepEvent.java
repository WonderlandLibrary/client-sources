package io.github.liticane.monoxide.listener.event.minecraft.player.movement;

import io.github.liticane.monoxide.listener.event.Event;

public class StepEvent extends Event {

    StepState state;
    float stepHeight;

    public StepEvent(StepState state, float stepHeight) {
        this.state = state;
        this.stepHeight = stepHeight;
    }

    public StepState getState() {
        return state;
    }

    public float getStepHeight() {
        return stepHeight;
    }

    public void setStepHeight(float stepHeight) {
        this.stepHeight = stepHeight;
    }

    public enum StepState {
        PRE, POST
    }
}
