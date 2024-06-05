package me.darkmagician6.morbid;

import net.minecraft.client.*;

public class MorbidWrapper
{
    public static Minecraft mcObj() {
        return Minecraft.x();
    }
    
    public static bdv getPlayer() {
        return mcObj().g;
    }
    
    public static bdr getController() {
        return mcObj().b;
    }
    
    public static bfy getRenderGlobal() {
        return mcObj().f;
    }
    
    public static bge getRenderEngine() {
        return mcObj().p;
    }
    
    public static bfq getEntityRenderer() {
        return mcObj().u;
    }
    
    public static aab getWorld() {
        return mcObj().e;
    }
    
    public static awv getFontRenderer() {
        return mcObj().q;
    }
    
    public static avy getGameSettings() {
        return mcObj().z;
    }
    
    public static awf getSession() {
        return mcObj().k;
    }
    
    public static void setSession(final awf session) {
        mcObj().k = session;
    }
    
    public static String getUsername() {
        if (mcObj().H) {
            return getPlayer().bS;
        }
        return getSession().a;
    }
    
    public static final axs getScaledResolution() {
        return new axs(mcObj().z, mcObj().c, mcObj().d);
    }
    
    public static final int getScreenWidth() {
        return getScaledResolution().a();
    }
    
    public static final int getScreenHeight() {
        return getScaledResolution().b();
    }
    
    public static bgy getRenderManager() {
        return bgy.a;
    }
    
    public static void addChat(final String s) {
        getPlayer().b("§0[§4Morbid§0]:§f " + s);
    }
}
