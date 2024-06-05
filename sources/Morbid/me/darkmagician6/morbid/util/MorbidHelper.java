package me.darkmagician6.morbid.util;

import java.util.*;
import me.darkmagician6.morbid.*;
import java.lang.ref.*;

public class MorbidHelper
{
    private static HashMap data;
    
    public static void sendPacket(final ei paramPacket) {
        MorbidWrapper.getPlayer().a.c(paramPacket);
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
    
    public static String getValue(final String paramString) {
        return MorbidHelper.data.get(paramString);
    }
    
    public static void errLog(final Object paramObject) {
        System.err.println(String.format("[%s]: " + paramObject, getValue("client.name")));
    }
    
    public static void gc() {
        Object localObject = new Object();
        final WeakReference localWeakReference = new WeakReference((T)localObject);
        localObject = null;
        while (localWeakReference.get() != null) {
            System.gc();
        }
    }
    
    static {
        MorbidHelper.data = new HashMap() {};
    }
}
