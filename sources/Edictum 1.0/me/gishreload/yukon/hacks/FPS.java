package me.gishreload.yukon.hacks;

import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.Category;
import me.gishreload.yukon.Meanings;
import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketPlayer;


public class FPS extends Module{
	public  FPS() {
		super("FPS", 0, Category.OTHER);
	}
	
	public void onEnable(){
		Meanings.fps = true;
	}
	
	public void onDisable() {
		Meanings.fps = true;
	}

@Override
public void onUpdate() {
	if(this.isToggled()){

	}
super.onUpdate();
}
}