/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.event.impl;

import cc.diablo.event.Event;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;

public class CollideEvent
extends Event {
    public final Block block;
    public AxisAlignedBB boundingBox;
    public final double x;
    public final double y;
    public final double z;

    public CollideEvent(AxisAlignedBB bb, Block block, double x, double y, double z) {
        this.block = block;
        this.boundingBox = bb;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    public void setBoundingBox(AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }

    public Block getBlock() {
        return this.block;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }
}

