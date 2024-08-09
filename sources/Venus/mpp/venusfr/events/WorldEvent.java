/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.events;

import com.mojang.blaze3d.matrix.MatrixStack;

public class WorldEvent {
    private MatrixStack stack;
    private float partialTicks;

    public WorldEvent(MatrixStack matrixStack, float f) {
        this.stack = matrixStack;
        this.partialTicks = f;
    }

    public MatrixStack getStack() {
        return this.stack;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    public void setStack(MatrixStack matrixStack) {
        this.stack = matrixStack;
    }

    public void setPartialTicks(float f) {
        this.partialTicks = f;
    }
}

