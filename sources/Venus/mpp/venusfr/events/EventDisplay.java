/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.events;

import com.mojang.blaze3d.matrix.MatrixStack;

public class EventDisplay {
    private MatrixStack matrixStack;
    private float partialTicks;
    private Type type;

    public EventDisplay(MatrixStack matrixStack, float f) {
        this.matrixStack = matrixStack;
        this.partialTicks = f;
    }

    public MatrixStack getMatrixStack() {
        return this.matrixStack;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    public Type getType() {
        return this.type;
    }

    public void setMatrixStack(MatrixStack matrixStack) {
        this.matrixStack = matrixStack;
    }

    public void setPartialTicks(float f) {
        this.partialTicks = f;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof EventDisplay)) {
            return true;
        }
        EventDisplay eventDisplay = (EventDisplay)object;
        if (!eventDisplay.canEqual(this)) {
            return true;
        }
        if (Float.compare(this.getPartialTicks(), eventDisplay.getPartialTicks()) != 0) {
            return true;
        }
        MatrixStack matrixStack = this.getMatrixStack();
        MatrixStack matrixStack2 = eventDisplay.getMatrixStack();
        if (matrixStack == null ? matrixStack2 != null : !matrixStack.equals(matrixStack2)) {
            return true;
        }
        Type type = this.getType();
        Type type2 = eventDisplay.getType();
        return type == null ? type2 != null : !((Object)((Object)type)).equals((Object)type2);
    }

    protected boolean canEqual(Object object) {
        return object instanceof EventDisplay;
    }

    public int hashCode() {
        int n = 59;
        int n2 = 1;
        n2 = n2 * 59 + Float.floatToIntBits(this.getPartialTicks());
        MatrixStack matrixStack = this.getMatrixStack();
        n2 = n2 * 59 + (matrixStack == null ? 43 : matrixStack.hashCode());
        Type type = this.getType();
        n2 = n2 * 59 + (type == null ? 43 : ((Object)((Object)type)).hashCode());
        return n2;
    }

    public String toString() {
        return "EventDisplay(matrixStack=" + this.getMatrixStack() + ", partialTicks=" + this.getPartialTicks() + ", type=" + this.getType() + ")";
    }

    public static enum Type {
        PRE,
        POST,
        HIGH;

    }
}

