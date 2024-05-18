package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.play.client.CPacketChatMessage;

public class xuluSpam extends Module {

	
	public static NetHandlerPlayClient connection;
	String message;
	
	public xuluSpam() {
		super("xuluAnnounce", "Broken", "Whenever you send a chat message, it will say |i <3 Xulu| behind it.", Keyboard.KEYBOARD_SIZE, 0xffffff, Category.COMBAT);
		
	}
	
	public void announce(NetworkPlayerInfo npi) {
		if(isEnabled()){
		}
	}

	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
		}
	}
}