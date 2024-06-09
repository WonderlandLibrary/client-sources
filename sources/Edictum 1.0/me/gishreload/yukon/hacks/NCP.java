package me.gishreload.yukon.hacks;

import java.util.Iterator;

import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.*;
import me.gishreload.yukon.module.Module;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;

public class NCP extends Module{
	
	public NCP() {
		super("NCP", 0, Category.ANTICHEAT);
	}
	
	public void onEnable(){
		Meanings.ncp = true;
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a79Обход NCP активирован.");
	}

	public void onDisable(){
		Meanings.ncp = false;
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74Обход NCP деактивирован.");
		super.onDisable();
	}
}
