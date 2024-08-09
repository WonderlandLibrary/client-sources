/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.model;

import net.minecraft.util.Direction;

public class QuadBounds {
    private float minX = Float.MAX_VALUE;
    private float minY = Float.MAX_VALUE;
    private float minZ = Float.MAX_VALUE;
    private float maxX = -3.4028235E38f;
    private float maxY = -3.4028235E38f;
    private float maxZ = -3.4028235E38f;

    public QuadBounds(int[] nArray) {
        int n = nArray.length / 4;
        for (int i = 0; i < 4; ++i) {
            int n2 = i * n;
            float f = Float.intBitsToFloat(nArray[n2 + 0]);
            float f2 = Float.intBitsToFloat(nArray[n2 + 1]);
            float f3 = Float.intBitsToFloat(nArray[n2 + 2]);
            if (this.minX > f) {
                this.minX = f;
            }
            if (this.minY > f2) {
                this.minY = f2;
            }
            if (this.minZ > f3) {
                this.minZ = f3;
            }
            if (this.maxX < f) {
                this.maxX = f;
            }
            if (this.maxY < f2) {
                this.maxY = f2;
            }
            if (!(this.maxZ < f3)) continue;
            this.maxZ = f3;
        }
    }

    public float getMinX() {
        return this.minX;
    }

    public float getMinY() {
        return this.minY;
    }

    public float getMinZ() {
        return this.minZ;
    }

    public float getMaxX() {
        return this.maxX;
    }

    public float getMaxY() {
        return this.maxY;
    }

    public float getMaxZ() {
        return this.maxZ;
    }

    public boolean isFaceQuad(Direction direction) {
        float f;
        float f2;
        float f3;
        switch (1.$SwitchMap$net$minecraft$util$Direction[direction.ordinal()]) {
            case 1: {
                f3 = this.getMinY();
                f2 = this.getMaxY();
                f = 0.0f;
                break;
            }
            case 2: {
                f3 = this.getMinY();
                f2 = this.getMaxY();
                f = 1.0f;
                break;
            }
            case 3: {
                f3 = this.getMinZ();
                f2 = this.getMaxZ();
                f = 0.0f;
                break;
            }
            case 4: {
                f3 = this.getMinZ();
                f2 = this.getMaxZ();
                f = 1.0f;
                break;
            }
            case 5: {
                f3 = this.getMinX();
                f2 = this.getMaxX();
                f = 0.0f;
                break;
            }
            case 6: {
                f3 = this.getMinX();
                f2 = this.getMaxX();
                f = 1.0f;
                break;
            }
            default: {
                return true;
            }
        }
        return f3 == f && f2 == f;
    }

    public boolean isFullQuad(Direction direction) {
        float f;
        float f2;
        float f3;
        float f4;
        switch (1.$SwitchMap$net$minecraft$util$Direction[direction.ordinal()]) {
            case 1: 
            case 2: {
                f4 = this.getMinX();
                f3 = this.getMaxX();
                f2 = this.getMinZ();
                f = this.getMaxZ();
                break;
            }
            case 3: 
            case 4: {
                f4 = this.getMinX();
                f3 = this.getMaxX();
                f2 = this.getMinY();
                f = this.getMaxY();
                break;
            }
            case 5: 
            case 6: {
                f4 = this.getMinY();
                f3 = this.getMaxY();
                f2 = this.getMinZ();
                f = this.getMaxZ();
                break;
            }
            default: {
                return true;
            }
        }
        return f4 == 0.0f && f3 == 1.0f && f2 == 0.0f && f == 1.0f;
    }
}

