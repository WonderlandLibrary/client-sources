package me.valk.agway.modules.render;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import me.valk.event.EventListener;
import me.valk.event.events.player.EventPlayerUpdate;
import me.valk.module.ModData;
import me.valk.module.ModType;
import me.valk.module.Module;
import net.minecraft.client.Minecraft;

public class FullbrightMod extends Module {

	public FullbrightMod() {
		super(new ModData("Fullbright", Keyboard.KEY_B, new Color(66, 174, 212)),
				ModType.RENDER);
	}
	
	@Override
	public void onDisable() {
		Minecraft.getMinecraft().gameSettings.gammaSetting = 1.0f;
	}
	
	@EventListener
	public void onTick(EventPlayerUpdate event){
		Minecraft.getMinecraft().gameSettings.gammaSetting = 10.0f;
	}
	

}
