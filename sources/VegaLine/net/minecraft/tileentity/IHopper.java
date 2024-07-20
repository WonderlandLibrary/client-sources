/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

public interface IHopper
extends IInventory {
    public World getWorld();

    public double getXPos();

    public double getYPos();

    public double getZPos();
}

