package none.module.modules.movement;

import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventMove;
import none.event.events.EventPreMotionUpdate;
import none.event.events.EventTick;
import none.module.Category;
import none.module.Module;
import none.valuesystem.ModeValue;

public class Highjump extends Module{
	
	public Highjump() {
		super("Highjump", "Highjump", Category.MOVEMENT);
	}
	
	public String[] mode = {"AACv3"};
	public ModeValue modes = new ModeValue("Mode", "AACv3", mode);
	
	@Override
	@RegisterEvent(events = {EventMove.class, EventPreMotionUpdate.class, EventTick.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		String mode = modes.getSelected();
		
		if (event instanceof EventTick) {
			if (mode.equalsIgnoreCase("AACv3")) {
				if (!mc.thePlayer.onGround) {
					mc.thePlayer.motionY += 0.059D;
				}
			}
		}
	}

}
