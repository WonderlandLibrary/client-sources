// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.utils.other;

import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.Minecraft;

public class ServerUtils
{
    public static Minecraft mc;
    public static boolean fakeHypixel;
    public static ServerData lastLogin;
    
    public static ServerData getServerData() {
        return ServerUtils.mc.isSingleplayer() ? null : ((ServerUtils.mc.getCurrentServerData() != null) ? ServerUtils.mc.getCurrentServerData() : ((GuiMultiplayer.currentServer() != null) ? GuiMultiplayer.currentServer() : null));
    }
    
    public static boolean isOnServer(final String ip) {
        return ServerUtils.mc.theWorld != null && ServerUtils.mc.thePlayer != null && !ServerUtils.mc.isSingleplayer() && getCurrentServerIP().endsWith(ip);
    }
    
    public static String getCurrentServerIP() {
        if (ServerUtils.mc.isSingleplayer()) {
            return "Singleplayer";
        }
        return ServerUtils.mc.getCurrentServerData().serverIP;
    }
    
    public static boolean isOnHypixel() {
        return !ServerUtils.mc.isSingleplayer() && isOnServer("hypixel.net") && !ServerUtils.fakeHypixel;
    }
    
    public static void setFakeHypixel(final boolean isFake) {
        ServerUtils.fakeHypixel = isFake;
    }
    
    static {
        ServerUtils.mc = Minecraft.getMinecraft();
    }
}
