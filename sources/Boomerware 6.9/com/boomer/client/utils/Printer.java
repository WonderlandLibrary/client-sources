package com.boomer.client.utils;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentTranslation;

public class Printer {

	public static void print(final String message) {
			Minecraft.getMinecraft().thePlayer.addChatMessage(
					new ChatComponentTranslation(
							ChatFormatting.GRAY + "[" + ChatFormatting.RED + "BoomerWare"
									+ ChatFormatting.GRAY + "]" + ChatFormatting.WHITE + ": "+ message, new Object[0]));
	}
}