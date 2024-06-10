package xyz.gucciclient.utils;

import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.multiplayer.*;

public class Wrapper
{
    public static Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }
    
    public static EntityPlayerSP getPlayer() {
        return getMinecraft().thePlayer;
    }
    
    public static WorldClient getWorld() {
        return getMinecraft().theWorld;
    }
    
    public static void drawCenteredString(final String text, final int x, final int y, final int color) {
        getMinecraft().fontRendererObj.drawString(text, x - getMinecraft().fontRendererObj.getStringWidth(text) / 2, y, color);
    }
    
    public static PlayerControllerMP getPlayerController() {
        return getMinecraft().playerController;
    }
}
