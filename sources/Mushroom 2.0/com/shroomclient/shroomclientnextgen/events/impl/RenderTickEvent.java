package com.shroomclient.shroomclientnextgen.events.impl;

import com.shroomclient.shroomclientnextgen.events.Event;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

public class RenderTickEvent extends Event {

    public MatrixStack matrixStack;
    public DrawContext drawContext;

    public RenderTickEvent(MatrixStack matrixStack, DrawContext drawContext) {
        this.matrixStack = matrixStack;
        this.drawContext = drawContext;
    }
}
