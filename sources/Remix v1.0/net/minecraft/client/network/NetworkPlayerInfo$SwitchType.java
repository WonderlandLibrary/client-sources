package net.minecraft.client.network;

import com.mojang.authlib.minecraft.*;

static final class SwitchType
{
    static final int[] field_178875_a;
    
    static {
        field_178875_a = new int[MinecraftProfileTexture.Type.values().length];
        try {
            SwitchType.field_178875_a[MinecraftProfileTexture.Type.SKIN.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchType.field_178875_a[MinecraftProfileTexture.Type.CAPE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
    }
}
