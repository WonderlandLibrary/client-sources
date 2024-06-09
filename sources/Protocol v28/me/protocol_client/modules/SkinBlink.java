package me.protocol_client.modules;

import java.util.Random;

import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.entity.player.EnumPlayerModelParts;

import org.lwjgl.input.Keyboard;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class SkinBlink extends Module{

	public SkinBlink() {
		super("Skin Blinker", "skinblink", Keyboard.KEY_NONE, Category.MISC, new String[]{"dsdfsdfsdfsdghgh"});
	}

	int timer;
	
	@EventTarget
	public void onPreUpdate(EventPreMotionUpdates event) {
		timer++;
		if(timer > 3){
			Random random = new Random();
			 mc.gameSettings.func_178878_a(EnumPlayerModelParts.HAT, random.nextBoolean());
			    mc.gameSettings.func_178878_a(EnumPlayerModelParts.JACKET, random.nextBoolean());
			    mc.gameSettings.func_178878_a(EnumPlayerModelParts.LEFT_PANTS_LEG, random.nextBoolean());
			    mc.gameSettings.func_178878_a(EnumPlayerModelParts.RIGHT_PANTS_LEG, random.nextBoolean());
			    mc.gameSettings.func_178878_a(EnumPlayerModelParts.LEFT_SLEEVE, random.nextBoolean());
			    mc.gameSettings.func_178878_a(EnumPlayerModelParts.RIGHT_SLEEVE, random.nextBoolean());
			    timer = 0;
			}
	}
	public void onDisable()
	  {
		  EventManager.unregister(this);//Registers the Object of this class to the EventManager.
	  }
	  public void onEnable() {
	        EventManager.register(this);//Registers the Object of this class to the EventManager.
	   }
}
