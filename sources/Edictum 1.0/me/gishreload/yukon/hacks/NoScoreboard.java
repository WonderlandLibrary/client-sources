package me.gishreload.yukon.hacks;

import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.Category;
import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.Meanings;
import me.gishreload.yukon.module.Module;
import net.minecraft.network.play.client.CPacketPlayer;


public class NoScoreboard extends Module{

	public NoScoreboard() {
		super("NoScoreboard", 0, Category.RENDER);
	}
	
	public void onEnable(){
		Meanings.sc = true;
	}
	
	public void onDisable() {
		Meanings.sc = false;
	}
}
