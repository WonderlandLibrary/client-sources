package com.boomer.client.event.events.render;

import com.boomer.client.event.Event;

/**
 * made by Xen for BoomerWare
 *
 * @since 6/11/2019
 **/
public class RenderStringEvent extends Event {
    private String text;

    public RenderStringEvent(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
