package me.gishreload.yukon.hacks;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.Category;
import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.module.Module;
import net.minecraft.block.Block;

public class Xray extends Module{
	
	public static ArrayList<Block> xrayBlocks = new ArrayList(); 
	
	public Xray() {
		super("Xray", Keyboard.KEY_X, Category.RENDER);
	}
	
	public void onEnable(){
		mc.renderGlobal.loadRenderers();
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eXray \u00a72\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}
	
	public void onDisable() {
		mc.renderGlobal.loadRenderers();
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eXray \u00a74\u0434\u0435\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}
}
