/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.lua.classes;

import net.minecraft.util.math.vector.Vector3d;

public class VectorClass {
    public Vector3d vector3d;

    public VectorClass(Vector3d vector3d) {
        this.vector3d = vector3d;
    }

    public double getX() {
        return this.vector3d.x;
    }

    public double getY() {
        return this.vector3d.y;
    }

    public double getZ() {
        return this.vector3d.z;
    }
}

