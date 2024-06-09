package me.swezedcode.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;

public class Wrapper {

	public static Minecraft mc = Minecraft.getMinecraft();

	public static String ip = "";
	
	public static FontRenderer getFontRenderer() {
		return getMinecraft().fontRendererObj;
	}

	public static Minecraft getMinecraft() {
		return Minecraft.getMinecraft();
	}

	public static EntityPlayer getPlayer() {
		return getMinecraft().thePlayer;
	}

	public static WorldClient getWorld() {
		return getMinecraft().theWorld;
	}

	public static void sendPacket(Packet packet) {
		getMinecraft().thePlayer.sendQueue.addToSendQueue(packet);
	}

	public static void sendPacketDirect(Packet packet) {
		NetHandlerPlayClient.netManager.sendPacket(packet);
	}

}
