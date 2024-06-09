package com.client.glowclient.utils;

import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.entity.*;
import java.util.*;
import net.minecraft.client.network.*;

public final class MinecraftHelper
{
    public static final boolean M = true;
    public static final boolean G = true;
    public static final String d = "1.12";
    public static final Minecraft mc;
    public static final String A = "1.12";
    public static final NavigableMap<Integer, String> B;
    public static final boolean b = false;
    
    public static WorldClient getWorld() {
        return MinecraftHelper.mc.world;
    }
    
    public static EntityPlayerSP getPlayer() {
        return MinecraftHelper.mc.player;
    }
    
    static {
        final TreeMap<Integer, String> treeMap;
        (treeMap = new TreeMap<Integer, String>()).put(335, "1.12");
        B = Collections.unmodifiableNavigableMap((NavigableMap<Integer, ?>)treeMap);
        mc = Minecraft.getMinecraft();
    }
    
    public static NetHandlerPlayClient getConnection() {
        return getPlayer().connection;
    }
    
    public void isRunningOnMac() {
        super();
    }
    
    public static boolean M() {
        return Minecraft.IS_RUNNING_ON_MAC;
    }
}
