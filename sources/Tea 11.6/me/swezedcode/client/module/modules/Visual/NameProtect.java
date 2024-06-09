package me.swezedcode.client.module.modules.Visual;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import me.swezedcode.client.utils.values.BooleanValue;

public class NameProtect extends Module {
	
	public BooleanValue changeAllPlayerNamesBoolValue = new BooleanValue(this, "Change all players name", "", Boolean.valueOf(false));
	public static boolean changeAllPlayerNames = false;
	public static String name = "Me";
	
	public NameProtect() {
		super("NameProtect", Keyboard.KEY_NONE, 0xFF2ED15F, ModCategory.Visual);
	}
	
	@EventListener
	public void onPre(EventPreMotionUpdates e) {
		if(changeAllPlayerNamesBoolValue.getValue()) {
			changeAllPlayerNames = true;
		}else{
			changeAllPlayerNames = false;
		}
	}
	
}
