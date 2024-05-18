package me.swezedcode.client.module.modules.World;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;

public class FastPlace extends Module {

	public FastPlace() {
		super("FastPlace", Keyboard.KEY_NONE, 0xFF13E89D, ModCategory.World);
	}

	@EventListener
	public void onPlace(EventPreMotionUpdates e) {
		mc.rightClickDelayTimer = 0;
	}
	
	@Override
	public void onEnable() {
		
	}
	
	@Override
	public void onDisable() {
		mc.rightClickDelayTimer = 6;
	}
	
}
