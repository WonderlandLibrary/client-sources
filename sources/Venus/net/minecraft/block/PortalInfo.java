/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.util.math.vector.Vector3d;

public class PortalInfo {
    public final Vector3d pos;
    public final Vector3d motion;
    public final float rotationYaw;
    public final float rotationPitch;

    public PortalInfo(Vector3d vector3d, Vector3d vector3d2, float f, float f2) {
        this.pos = vector3d;
        this.motion = vector3d2;
        this.rotationYaw = f;
        this.rotationPitch = f2;
    }
}

