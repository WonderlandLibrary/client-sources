package com.shroomclient.shroomclientnextgen.events.impl;

import com.shroomclient.shroomclientnextgen.events.Event;
import net.minecraft.client.util.math.MatrixStack;

public class Render3dEvent extends Event {

    public MatrixStack matrixStack;

    public float partialTicks;

    public Render3dEvent(MatrixStack matrixStack, float partialTicks) {
        this.partialTicks = partialTicks;
        this.matrixStack = matrixStack;
    }
}
