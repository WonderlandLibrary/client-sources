package Squad.Utils;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

public class Wrapper {
	private static Minecraft mc = Minecraft.getMinecraft();
	private static FontRenderer font;
	private static ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
	private static Random random = new Random();
	
	public static EntityPlayerSP getPlayer(){
		return mc.thePlayer;
	}
	
	public static void damagePlayer(){
		for (int i = 0; i < 100; i++){
	          mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.24D, mc.thePlayer.posZ, false));
	          mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.2D, mc.thePlayer.posZ, false));
	        }
	        mc.thePlayer.swingItem();
	}
	
	public static GameSettings getGameSettings(){
		return mc.gameSettings;
	}
	
	public static FontRenderer getFontRenderer(){
		return mc.fontRendererObj;
	}
	
	public static int getDisplayWidth(){
		return mc.displayWidth;
	}
	
	public static int getDisplayHeight(){
		return mc.displayHeight;
	}
	
	public static Minecraft getMC(){
		return mc;
	}
	
	public static WorldClient getWorld(){
		return mc.theWorld;
	}
	
	public static PlayerControllerMP getPlayerController(){
		return mc.playerController;
	}
	
    public static Block getBlock(BlockPos pos) {
        Minecraft.getMinecraft();
        return getWorld().getBlockState(pos).getBlock();
    }

	public static NetHandlerPlayClient getNetHandler() {
		return mc.getNetHandler();
	}
	
	public static Random getRandom() {
		return random;
	}

    public static Block getBlockUnderPlayer(EntityPlayer inPlayer) {
        return Wrapper.getBlock(new BlockPos(inPlayer.posX, inPlayer.posY - 1.0, inPlayer.posZ));
    }

    public static Block getBlockOverPlayer(EntityPlayer inPlayer) {
        return Wrapper.getBlock(new BlockPos(inPlayer.posX, inPlayer.posY + 2.0, inPlayer.posZ));
    }
    public static ScaledResolution getScaledRes() {
    	  ScaledResolution scr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
    	  return scr;
    	 }
}
