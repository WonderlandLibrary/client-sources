package none.module.modules.render;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventTick;
import none.module.Category;
import none.module.Module;
import none.valuesystem.BooleanValue;

public class TrueSight extends Module{
	
	public static BooleanValue entity = new BooleanValue("Entities", true);
	
	public TrueSight() {
		super("TrueSight", "TrueSight", Category.RENDER, Keyboard.KEY_NONE);
	}

	@Override
	@RegisterEvent(events = EventTick.class)
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		String str = "";
		if (entity.getObject()) {
			str = "Entity";
		}
		
		setDisplayName(getName() + " " + ChatFormatting.WHITE + "[" + str + "]");
	}
}
