/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math;

import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.ListNBT;

public class Rotations {
    protected final float x;
    protected final float y;
    protected final float z;

    public Rotations(float f, float f2, float f3) {
        this.x = !Float.isInfinite(f) && !Float.isNaN(f) ? f % 360.0f : 0.0f;
        this.y = !Float.isInfinite(f2) && !Float.isNaN(f2) ? f2 % 360.0f : 0.0f;
        this.z = !Float.isInfinite(f3) && !Float.isNaN(f3) ? f3 % 360.0f : 0.0f;
    }

    public Rotations(ListNBT listNBT) {
        this(listNBT.getFloat(0), listNBT.getFloat(1), listNBT.getFloat(2));
    }

    public ListNBT writeToNBT() {
        ListNBT listNBT = new ListNBT();
        listNBT.add(FloatNBT.valueOf(this.x));
        listNBT.add(FloatNBT.valueOf(this.y));
        listNBT.add(FloatNBT.valueOf(this.z));
        return listNBT;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Rotations)) {
            return true;
        }
        Rotations rotations = (Rotations)object;
        return this.x == rotations.x && this.y == rotations.y && this.z == rotations.z;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getZ() {
        return this.z;
    }
}

