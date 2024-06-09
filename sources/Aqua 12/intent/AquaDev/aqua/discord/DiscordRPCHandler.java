// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.discord;

import net.aql.Lib;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;

public class DiscordRPCHandler
{
    public static final DiscordRPCHandler instance;
    private final DiscordRPC discordRPC;
    String serverIP;
    public static String second;
    
    public DiscordRPCHandler() {
        this.discordRPC = new DiscordRPC();
        this.serverIP = (Minecraft.getMinecraft().isSingleplayer() ? "Singleplayer" : Minecraft.getMinecraft().getCurrentServerData().serverIP);
    }
    
    public void init() {
        if (GL11.glGetString(7937).contains("NVIDIA") || GL11.glGetString(7937).contains("AMD") || GL11.glGetString(7937).contains("RX")) {
            this.discordRPC.start();
        }
    }
    
    public void shutdown() {
        this.discordRPC.shutdown();
    }
    
    public DiscordRPC getDiscordRPC() {
        return this.discordRPC;
    }
    
    static {
        instance = new DiscordRPCHandler();
        DiscordRPCHandler.second = "Client  " + Lib.getUID() + "";
    }
}
