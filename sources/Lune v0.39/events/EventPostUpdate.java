package me.superskidder.lune.events;

import me.superskidder.lune.manager.event.Event;

/**
 * @description: Post
 * @author: Qian_Xia
 * @create: 2020-08-24 14:08
 **/
public class EventPostUpdate extends Event {
    private float yaw;
    private float pitch;

    public EventPostUpdate(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}
