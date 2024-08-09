/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Matrix4f;

public class ClippingHelperDummy
extends ClippingHelper {
    public ClippingHelperDummy() {
        super(new Matrix4f(), new Matrix4f());
    }

    @Override
    public boolean isBoundingBoxInFrustum(AxisAlignedBB axisAlignedBB) {
        return false;
    }
}

