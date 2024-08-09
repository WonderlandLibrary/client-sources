package ru.FecuritySQ.event;

public class Event {
    public boolean cancel;
    public boolean cancelUsingT2o;
    private float partialTicks;

    public void setCancel(boolean cancel){
        this.cancel = cancel;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
