package client.metaware.impl.event.impl.player;


import client.metaware.impl.event.Event;

public class PlayerJumpEvent extends Event {
    public double motionY;

    public PlayerJumpEvent(double motionY) {
        this.motionY = motionY;
    }

    public void setMotionY(double d) {
        this.motionY = d;
    }

    public double getMotionY() {
        return motionY;
    }
}