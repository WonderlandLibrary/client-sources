package me.gishreload.yukon.hacks;

import java.util.Iterator;

import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.*;
import me.gishreload.yukon.module.Module;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayer;

public class AAC extends Module{
	
	public AAC() {
		super("AAC", 0, Category.ANTICHEAT);
	}
	
	public void onEnable(){
		Meanings.aac = true;
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a79Обход AAC активирован.");
	}

	public void onDisable(){
		Meanings.aac = false;
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74Обход AAC деактивирован.");
		super.onDisable();
	}
}
