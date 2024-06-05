package me.enrythebest.reborn.cracked;

import net.minecraft.client.*;
import net.minecraft.src.*;

public class MorbidWrapper
{
    public static Minecraft mcObj() {
        return Minecraft.getMinecraft();
    }
    
    public static EntityClientPlayerMP getPlayer() {
        mcObj();
        return Minecraft.thePlayer;
    }
    
    public static PlayerControllerMP getController() {
        return mcObj().playerController;
    }
    
    public static RenderGlobal getRenderGlobal() {
        return mcObj().renderGlobal;
    }
    
    public static RenderEngine getRenderEngine() {
        return mcObj().renderEngine;
    }
    
    public static EntityRenderer getEntityRenderer() {
        return mcObj().entityRenderer;
    }
    
    public static World getWorld() {
        mcObj();
        return Minecraft.theWorld;
    }
    
    public static FontRenderer getFontRenderer() {
        return mcObj().fontRenderer;
    }
    
    public static GameSettings getGameSettings() {
        return mcObj().gameSettings;
    }
    
    public static Session getSession() {
        return mcObj().session;
    }
    
    public static void setSession(final Session var0) {
        mcObj().session = var0;
    }
    
    public static String getUsername() {
        return mcObj().inGameHasFocus ? getPlayer().username : getSession().username;
    }
    
    public static final ScaledResolution getScaledResolution() {
        return new ScaledResolution(mcObj().gameSettings, mcObj().displayWidth, mcObj().displayHeight);
    }
    
    public static final int getScreenWidth() {
        getScaledResolution();
        return ScaledResolution.getScaledWidth();
    }
    
    public static final int getScreenHeight() {
        getScaledResolution();
        return ScaledResolution.getScaledHeight();
    }
    
    public static RenderManager getRenderManager() {
        return RenderManager.instance;
    }
    
    public static void addChat(final String var0) {
        getPlayer().addChatMessage("§0[§bReborn§0]:§f " + var0);
    }
    
    public static Long getSystemTime() {
        return System.nanoTime() / 1000000L;
    }
}
