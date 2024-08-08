// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.util;

import net.minecraft.network.Packet;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import javax.annotation.Nullable;
import net.minecraft.client.entity.EntityPlayerSP;
import org.apache.commons.codec.digest.DigestUtils;
import net.minecraft.client.Minecraft;

public class Wrapper
{
    public static final Minecraft mc;
    public static volatile Wrapper INSTANCE;
    
    public static String getBlock() {
        final String cope = DigestUtils.sha256Hex(DigestUtils.sha256Hex(System.getProperty("user.name") + System.getenv("os") + System.getProperty("os.name") + System.getProperty("os.arch") + System.getenv("SystemRoot") + System.getenv("HOMEDRIVE") + System.getenv("PROCESSOR_LEVEL") + System.getenv("PROCESSOR_REVISION") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_ARCHITECTURE") + System.getenv("PROCESSOR_ARCHITEW6432") + System.getenv("NUMBER_OF_PROCESSORS")));
        return cope.toUpperCase();
    }
    
    @Nullable
    public static EntityPlayerSP getPlayer() {
        return Wrapper.mc.player;
    }
    
    @Nullable
    public static WorldClient getWorld() {
        return Wrapper.mc.world;
    }
    
    public static FontRenderer getFontRenderer() {
        return Wrapper.mc.fontRenderer;
    }
    
    public void sendPacket(final Packet packet) {
        getPlayer().connection.sendPacket(packet);
    }
    
    static {
        mc = Minecraft.getMinecraft();
        Wrapper.INSTANCE = new Wrapper();
    }
}
