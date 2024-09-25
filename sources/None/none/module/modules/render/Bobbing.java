package none.module.modules.render;

import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventTick;
import none.module.Category;
import none.module.Module;
import none.utils.MoveUtils;
import none.valuesystem.NumberValue;

public class Bobbing extends Module{

	public Bobbing() {
		super("Bobbing", "Bobbing", Category.RENDER);
	}
	
	public static NumberValue<Float> speed = new NumberValue<>("Speed-Yaw", 0.05F, 0F, 1F);

	@Override
	@RegisterEvent(events = {EventTick.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (event instanceof EventTick) {
			if (MoveUtils.isMoveKeyPressed()) {
				if (mc.thePlayer.onGround) {
					mc.thePlayer.cameraYaw = speed.getObject();
				}else {
					mc.thePlayer.cameraYaw = 0.0F;
				}
			}
		}
	}

}
