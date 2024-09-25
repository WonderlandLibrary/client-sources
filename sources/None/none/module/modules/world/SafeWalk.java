package none.module.modules.world;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;
import none.valuesystem.BooleanValue;

public class SafeWalk extends Module{
	
	public SafeWalk() {
		super("Safewalk", "Safewalk", Category.WORLD, Keyboard.KEY_NONE);
	}
	
	public static BooleanValue airsafe = new BooleanValue("AirSafe", false);
	
	@Override
	@RegisterEvent(events = EventPreMotionUpdate.class)
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		if (airsafe.getObject()) {
			setDisplayName(getName() + ChatFormatting.WHITE + " " + airsafe.getName());
		}else {
			setDisplayName(getName());
		}
	}

}
