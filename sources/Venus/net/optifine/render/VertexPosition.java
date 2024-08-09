/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.render;

import net.optifine.shaders.Shaders;

public class VertexPosition {
    private int frameId;
    private float posX;
    private float posY;
    private float posZ;
    private float velocityX;
    private float velocityY;
    private float velocityZ;
    private boolean velocityValid;

    public void setPosition(int n, float f, float f2, float f3) {
        if (!Shaders.isShadowPass && n != this.frameId) {
            if (this.frameId != 0) {
                this.velocityX = f - this.posX;
                this.velocityY = f2 - this.posY;
                this.velocityZ = f3 - this.posZ;
                this.velocityValid = n - this.frameId <= 3 && !Shaders.pointOfViewChanged;
            }
            this.frameId = n;
            this.posX = f;
            this.posY = f2;
            this.posZ = f3;
        }
    }

    public boolean isVelocityValid() {
        return this.velocityValid;
    }

    public float getVelocityX() {
        return this.velocityX;
    }

    public float getVelocityY() {
        return this.velocityY;
    }

    public float getVelocityZ() {
        return this.velocityZ;
    }

    public int getFrameId() {
        return this.frameId;
    }
}

