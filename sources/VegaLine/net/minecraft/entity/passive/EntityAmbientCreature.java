/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class EntityAmbientCreature
extends EntityLiving
implements IAnimals {
    public EntityAmbientCreature(World worldIn) {
        super(worldIn);
    }

    @Override
    public boolean canBeLeashedTo(EntityPlayer player) {
        return false;
    }
}

