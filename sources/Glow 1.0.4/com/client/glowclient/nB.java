package com.client.glowclient;

import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;

public class nb
{
    public static boolean M(final World world) {
        return world.provider.hasSkyLight();
    }
    
    public static String M(final Class<? extends TileEntity> clazz) {
        final ResourceLocation key;
        if ((key = TileEntity.getKey((Class)clazz)) != null) {
            return key.toString();
        }
        return "";
    }
    
    public nb() {
        super();
    }
}
