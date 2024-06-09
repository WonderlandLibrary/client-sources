package com.client.glowclient.utils;

import net.minecraft.realms.*;
import net.minecraft.client.*;

public class VersionUtils
{
    public static String k() {
        return RealmsSharedConstants.VERSION_STRING;
    }
    
    public static String A() {
        return "1.12.2";
    }
    
    public static String D() {
        return String.format("Minecraft %s (%s/%s/%s)", k(), Minecraft.getMinecraft().getVersion(), ClientBrandRetriever.getClientModName(), Minecraft.getMinecraft().getVersionType());
    }
    
    public VersionUtils() {
        super();
    }
    
    public static String M() {
        return "4.0.1.3";
    }
    
    public static int D() {
        return 1343;
    }
    
    public static int M() {
        return RealmsSharedConstants.NETWORK_PROTOCOL_VERSION;
    }
}
