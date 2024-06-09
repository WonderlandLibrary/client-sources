package com.client.glowclient;

import net.minecraft.entity.*;

public class T extends N
{
    @Override
    public boolean M(final Entity entity) {
        return entity.isCreatureType(EnumCreatureType.MONSTER, false);
    }
    
    @Override
    public V M(final Entity entity) {
        return V.B;
    }
    
    public T() {
        super();
    }
}
