/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity;

import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public interface IEntityMultiPart {
    public World getWorld();

    public boolean attackEntityFromPart(MultiPartEntityPart var1, DamageSource var2, float var3);
}

