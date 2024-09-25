package none.module.modules.combat;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;
import none.valuesystem.ModeValue;
import none.valuesystem.NumberValue;

public class Reach extends Module{

	public Reach() {
		super("Reach", "Reach", Category.COMBAT, Keyboard.KEY_NONE);
	}
	
	private static String[] modes = {"Extended"};
	public static ModeValue reachmode = new ModeValue("Reach-Mode", "Extended", modes);
	
	public static NumberValue<Double> range = new NumberValue<>("Range", 4.2, 3.5, 7.0);

	@Override
	@RegisterEvent(events = {EventPreMotionUpdate.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		setDisplayName(getName() + " " + ChatFormatting.WHITE + reachmode.getSelected());
	}

}
