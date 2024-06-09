package com.client.glowclient;

import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;

public class Q extends N
{
    @Override
    public boolean M(final Entity entity) {
        return entity.isCreatureType(EnumCreatureType.CREATURE, false) || entity.isCreatureType(EnumCreatureType.AMBIENT, false) || entity.isCreatureType(EnumCreatureType.WATER_CREATURE, false) || entity instanceof EntityVillager || entity instanceof EntityGolem;
    }
    
    @Override
    public V M(final Entity entity) {
        return V.d;
    }
    
    public Q() {
        super();
    }
}
