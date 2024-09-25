package none.module.modules.world;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;
import none.valuesystem.NumberValue;

public class GameTimer extends Module{

	public GameTimer() {
		super("Timer", "Timer", Category.WORLD, Keyboard.KEY_NONE);
	}
	
	private NumberValue<Integer> SPEED = new NumberValue<>("Timer-Speed", 100, 1, 1000);
	
	@Override
	protected void onEnable() {
		super.onEnable();
		mc.timer.timerSpeed = 1.0F;
	}
	
	@Override
	protected void onDisable() {
		super.onDisable();
		mc.timer.timerSpeed = 1.0F;
	}
	
	@Override
	@RegisterEvent(events = {EventPreMotionUpdate.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		setDisplayName(getName() + ChatFormatting.WHITE + ":" + ChatFormatting.GRAY + (SPEED.getInteger() / 100F));
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate e = (EventPreMotionUpdate) event;
			if (e.isPre()) {
				mc.timer.timerSpeed = SPEED.getInteger() / 100F;
			}
		}
	}

}
