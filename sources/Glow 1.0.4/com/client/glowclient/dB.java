package com.client.glowclient;

import net.minecraft.client.renderer.vertex.*;

public class DB
{
    public static final int[] b;
    
    static {
        b = new int[VertexFormatElement$EnumUsage.values().length];
        try {
            DB.b[VertexFormatElement$EnumUsage.POSITION.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            DB.b[VertexFormatElement$EnumUsage.UV.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            DB.b[VertexFormatElement$EnumUsage.COLOR.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
    }
}
