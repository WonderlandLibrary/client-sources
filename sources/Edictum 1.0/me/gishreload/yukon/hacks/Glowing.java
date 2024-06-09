package me.gishreload.yukon.hacks;

import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.Category;
import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.Meanings;
import me.gishreload.yukon.module.Module;
import net.minecraft.entity.Entity;

public class Glowing extends Module {

	public Glowing() {
		super("Glowing", 0, Category.RENDER);
	}
	
	@Override
	public void onEnable() {
		Meanings.glow = true;
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eGlowing \u00a72\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}
	@Override
	public void onDisable() {
		Meanings.glow = false;
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eGlowing \u00a74\u0434\u0435\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
}
	@Override
	public void onUpdate(){
		for(Object o : mc.theWorld.loadedEntityList){
			Entity e = (Entity) o;
			e.setGlowing(Meanings.glow);
		}
	}
}
