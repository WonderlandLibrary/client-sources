package com.shroomclient.shroomclientnextgen.events.impl;

import com.shroomclient.shroomclientnextgen.events.Event;
import net.minecraft.client.gui.DrawContext;

public class RenderScreenEvent extends Event {

    public DrawContext context;
    public int mouseX;
    public int mouseY;
    public float delta;

    public RenderScreenEvent(
        DrawContext context,
        int mouseX,
        int mouseY,
        float delta
    ) {
        this.context = context;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.delta = delta;
    }
}
