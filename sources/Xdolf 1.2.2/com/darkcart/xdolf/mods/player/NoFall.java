package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Client;
import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketPlayer;

public class NoFall extends Module
{
	public NoFall()
	{
		super("NoFall", "Prevents fall damage. Doesn't work on most servers.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}
	
	@Override
	public void beforeUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			
		}
	}
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			if (player.fallDistance > 1) {
				player.connection.sendPacket(new CPacketPlayer(true));
			}
		}
	}
}