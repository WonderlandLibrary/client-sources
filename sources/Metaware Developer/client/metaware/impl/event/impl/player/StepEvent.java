package client.metaware.impl.event.impl.player;


import client.metaware.impl.event.Event;

public final class StepEvent extends Event {

    private EventState state;
    private float stepHeight;
    private double heightStepped;

    public StepEvent(EventState state, float stepHeight) {
        this.state = state;
        this.stepHeight = stepHeight;
    }

    public double getHeightStepped() {
        return heightStepped;
    }

    public void setHeightStepped(double heightStepped) {
        this.heightStepped = heightStepped;
    }

    public boolean isPre() {
        return state == EventState.PRE;
    }

    public void setState(EventState state) {
        this.state = state;
    }

    public boolean isPost() {return state == EventState.POST;}

    public float getStepHeight() {
        return stepHeight;
    }

    public void setStepHeight(float stepHeight) {
        this.stepHeight = stepHeight;
    }

    public enum EventState{
        PRE, POST
    }

}