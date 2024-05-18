package com.darkcart.xdolf.mods.movement;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PacketMove extends Module
{
	public PacketMove()
	{
		super("PacketMove", "Coding", "Sends position packets to the server to move you some blocks forward / backwards", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.MOVEMENT);
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			Minecraft.getMinecraft().player.setPosition(Minecraft.getMinecraft().player.posX+0.0251f, Minecraft.getMinecraft().player.posY, Minecraft.getMinecraft().player.posZ);
		}}}
