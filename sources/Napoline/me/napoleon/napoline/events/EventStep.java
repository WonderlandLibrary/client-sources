package me.napoleon.napoline.events;

import me.napoleon.napoline.manager.event.Event;
import me.napoleon.napoline.manager.event.EventCategory;

/**
 * @description:
 * @author: Qian_Xia
 * @create: 2020-08-24 19:00
 **/
public class EventStep extends Event {
    private float height;
    private final EventCategory eventType;

    public EventStep(EventCategory eventType, float height) {
        this.eventType = eventType;
        this.height = height;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public EventCategory getEventType() {
        return this.eventType;
    }
}
