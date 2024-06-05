package koks.event.impl;

import koks.event.Event;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 21:46
 */
public class JumpEvent extends Event {

    private  float yaw;

    public JumpEvent(float yaw) {
        this.yaw = yaw;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
}
