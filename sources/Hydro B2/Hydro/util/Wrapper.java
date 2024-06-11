package Hydro.util;

import Hydro.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class Wrapper {
	
	private static final Timer2 inventoryTimer = new Timer2();
	
	public static void log(String message) {
		System.out.println(Client.prefix + message);
	}
	
	public static boolean invCooldownElapsed(long time) {
        return inventoryTimer.hasTimeElapsed(time, true);
    }
	
	public static void addChatMessage(String message) {
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "Hydro" + EnumChatFormatting.GRAY + ": " + EnumChatFormatting.RESET + message));
	}

	public static EntityPlayer player(){
		return Minecraft.getMinecraft().thePlayer;
	}

	public static Minecraft mc(){
		return Minecraft.getMinecraft();
	}

	public static PlayerControllerMP playerController() {
		return mc().playerController;
	}

}
