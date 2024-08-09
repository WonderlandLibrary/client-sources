/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.render;

import net.minecraft.util.math.AxisAlignedBB;
import net.optifine.render.ICamera;

public class AabbFrame
extends AxisAlignedBB {
    private int frameCount = -1;
    private boolean inFrustumFully = false;

    public AabbFrame(double d, double d2, double d3, double d4, double d5, double d6) {
        super(d, d2, d3, d4, d5, d6);
    }

    public boolean isBoundingBoxInFrustumFully(ICamera iCamera, int n) {
        if (this.frameCount != n) {
            this.inFrustumFully = iCamera.isBoxInFrustumFully(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
            this.frameCount = n;
        }
        return this.inFrustumFully;
    }
}

