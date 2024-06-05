package net.minecraft.server.network;

import net.minecraft.network.*;

static final class SwitchEnumConnectionState
{
    static final int[] VALUES;
    
    static {
        VALUES = new int[EnumConnectionState.values().length];
        try {
            SwitchEnumConnectionState.VALUES[EnumConnectionState.LOGIN.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEnumConnectionState.VALUES[EnumConnectionState.STATUS.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
    }
}
