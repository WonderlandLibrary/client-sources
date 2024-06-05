package koks.event.impl;

import koks.event.Event;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 19:37
 */
public class EventBobbing extends Event {

    float distanceWalkedModified;

    public EventBobbing(float distanceWalkedModified) {
        this.distanceWalkedModified = distanceWalkedModified;
    }

    public float getDistanceWalkedModified() {
        return distanceWalkedModified;
    }

    public void setDistanceWalkedModified(float distanceWalkedModified) {
        this.distanceWalkedModified = distanceWalkedModified;
    }
}
