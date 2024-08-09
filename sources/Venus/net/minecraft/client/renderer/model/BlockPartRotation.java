/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model;

import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;

public class BlockPartRotation {
    public final Vector3f origin;
    public final Direction.Axis axis;
    public final float angle;
    public final boolean rescale;

    public BlockPartRotation(Vector3f vector3f, Direction.Axis axis, float f, boolean bl) {
        this.origin = vector3f;
        this.axis = axis;
        this.angle = f;
        this.rescale = bl;
    }
}

