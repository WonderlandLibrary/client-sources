package com.minus.event;

import com.minus.event.Event;
import net.minecraft.client.gui.DrawContext;

public class EventRender2D implements Event {
    private DrawContext context;
    private int width;
    private int height;

    public EventRender2D(DrawContext context, int width, int height) {
        this.context = context;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setContext(DrawContext context) {
        this.context = context;
    }

    public DrawContext getContext() {
        return context;
    }
}
