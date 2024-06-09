package com.client.glowclient;

import net.minecraft.network.play.server.*;

public class KF
{
    public static final int[] b;
    
    static {
        b = new int[SPacketPlayerListItem$Action.values().length];
        try {
            KF.b[SPacketPlayerListItem$Action.ADD_PLAYER.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            KF.b[SPacketPlayerListItem$Action.REMOVE_PLAYER.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
    }
}
