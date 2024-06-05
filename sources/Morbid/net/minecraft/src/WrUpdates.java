package net.minecraft.src;

import java.util.*;

public class WrUpdates
{
    private static IWrUpdater wrUpdater;
    
    static {
        WrUpdates.wrUpdater = null;
    }
    
    public static void setWrUpdater(final IWrUpdater var0) {
        if (WrUpdates.wrUpdater != null) {
            WrUpdates.wrUpdater.terminate();
        }
        WrUpdates.wrUpdater = var0;
        if (WrUpdates.wrUpdater != null) {
            try {
                WrUpdates.wrUpdater.initialize();
            }
            catch (Exception var) {
                WrUpdates.wrUpdater = null;
                var.printStackTrace();
            }
        }
    }
    
    public static boolean hasWrUpdater() {
        return WrUpdates.wrUpdater != null;
    }
    
    public static IWrUpdater getWrUpdater() {
        return WrUpdates.wrUpdater;
    }
    
    public static WorldRenderer makeWorldRenderer(final World var0, final List var1, final int var2, final int var3, final int var4, final int var5) {
        return (WrUpdates.wrUpdater == null) ? new WorldRenderer(var0, var1, var2, var3, var4, var5) : WrUpdates.wrUpdater.makeWorldRenderer(var0, var1, var2, var3, var4, var5);
    }
    
    public static boolean updateRenderers(final RenderGlobal var0, final EntityLiving var1, final boolean var2) {
        try {
            return WrUpdates.wrUpdater.updateRenderers(var0, var1, var2);
        }
        catch (Exception var3) {
            var3.printStackTrace();
            setWrUpdater(null);
            return false;
        }
    }
    
    public static void resumeBackgroundUpdates() {
        if (WrUpdates.wrUpdater != null) {
            WrUpdates.wrUpdater.resumeBackgroundUpdates();
        }
    }
    
    public static void pauseBackgroundUpdates() {
        if (WrUpdates.wrUpdater != null) {
            WrUpdates.wrUpdater.pauseBackgroundUpdates();
        }
    }
    
    public static void finishCurrentUpdate() {
        if (WrUpdates.wrUpdater != null) {
            WrUpdates.wrUpdater.finishCurrentUpdate();
        }
    }
    
    public static void preRender(final RenderGlobal var0, final EntityLiving var1) {
        if (WrUpdates.wrUpdater != null) {
            WrUpdates.wrUpdater.preRender(var0, var1);
        }
    }
    
    public static void postRender() {
        if (WrUpdates.wrUpdater != null) {
            WrUpdates.wrUpdater.postRender();
        }
    }
}
