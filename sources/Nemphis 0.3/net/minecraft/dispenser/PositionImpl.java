/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.dispenser;

import net.minecraft.dispenser.IPosition;

public class PositionImpl
implements IPosition {
    protected final double x;
    protected final double y;
    protected final double z;
    private static final String __OBFID = "CL_00001208";

    public PositionImpl(double xCoord, double yCoord, double zCoord) {
        this.x = xCoord;
        this.y = yCoord;
        this.z = zCoord;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getZ() {
        return this.z;
    }
}

