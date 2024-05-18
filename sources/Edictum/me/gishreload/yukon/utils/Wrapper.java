package me.gishreload.yukon.utils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.network.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.settings.*;
import net.minecraft.init.Blocks;
import net.minecraft.network.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.client.gui.*;

public class Wrapper
{
    protected IChunkProvider chunkProvider;
	public static Minecraft mc = Minecraft.getMinecraft();
    private static Wrapper theWrapper;
    
    static {
        Wrapper.theWrapper = new Wrapper();
    }
    
    public Wrapper() {
        this.mc = Minecraft.getMinecraft();
    }
    
    public static Wrapper getInstance() {
        return Wrapper.theWrapper;
    }
    
    public static EntityPlayerSP getPlayer() {
        return Minecraft.thePlayer;
    }
    public FontRenderer getFontRenderer() {
        return Minecraft.fontRendererObj;
    }
    
    public NetHandlerPlayClient getQueue() {
        return Minecraft.thePlayer.connection;
    }
    
    public EntityRenderer getEntityRenderer() {
        return this.mc.entityRenderer;
    }
    
    public GameSettings getGameSettings() {
        return this.mc.gameSettings;
    }
    
    public static void sendPackets(final Packet packet) {
        getInstance().getPlayer().connection.sendPacket(packet);
    }
    
    public PlayerControllerMP getPlayerContoller() {
        return Minecraft.playerController;
    }
    
    public RenderGlobal getRenderGlobal() {
        return this.mc.renderGlobal;
    }
    public RayTraceResult getMouseOverObject() {
        return this.mc.objectMouseOver;
    }
    
    public GuiIngame getGuiIngame() {
        return this.mc.ingameGUI;
    }
    
    public Minecraft getMinecraft() {
        return this.mc;
    }
}
