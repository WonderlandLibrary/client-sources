package me.enrythebest.reborn.cracked.util;

import java.util.*;
import net.minecraft.src.*;
import me.enrythebest.reborn.cracked.*;
import java.lang.ref.*;

public class MorbidHelper
{
    private static HashMap data;
    
    static {
        MorbidHelper.data = new MorbidHelper$1();
    }
    
    public static void sendPacket(final Packet var0) {
        MorbidWrapper.getPlayer().sendQueue.addToSendQueue(var0);
    }
    
    public static String getVersion() {
        return "Morbid §0[§fP. 1.0§0]§f - Modded by America10Ultra - ";
    }
    
    public static String getMinecraftVer() {
        return "Minecraft 1.5.2";
    }
    
    public static String getName() {
        return getValue("client.name");
    }
    
    public static String getValue(final String var0) {
        return MorbidHelper.data.get(var0);
    }
    
    public static void errLog(final Object var0) {
        System.err.println(String.format("[%s]: " + var0, getValue("client.name")));
    }
    
    public static void gc() {
        Object var0 = new Object();
        final WeakReference var2 = new WeakReference((T)var0);
        var0 = null;
        while (var2.get() != null) {
            System.gc();
        }
    }
}
