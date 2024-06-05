package de.tired.module.impl.list.movement;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.event.EventTarget;
import de.tired.event.events.UpdateEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import de.tired.tired.other.ClientHelper;

@ModuleAnnotation(name = "Glide", category = ModuleCategory.MOVEMENT, clickG = "Glide around the world")
public class Glide extends Module {

	@EventTarget
	public void onUpdate(UpdateEvent e) {
		if (MC.thePlayer.onGround) return;
		ClientHelper.INSTANCE.doSpeedup(0.37);
		MC.timer.timerSpeed = 0.4F;
		if (MC.thePlayer.ticksExisted % 4 == 0) {
			MC.thePlayer.motionY += 0.33;
			MC.timer.timerSpeed = 0.2F;
		} else {
			MC.thePlayer.motionY -= 0.0001F;
		}

	}

	@Override
	public void onState() {
		double x = MC.thePlayer.posX;
		double y = MC.thePlayer.posY;
		double z = MC.thePlayer.posZ;

		MC.thePlayer.motionX = 0;
		MC.thePlayer.motionY = 0;
		MC.thePlayer.motionZ = 0;

	}

	@Override
	public void onUndo() {
		ClientHelper.INSTANCE.doSpeedup(0.16);
		MC.timer.timerSpeed = 1F;
		MC.thePlayer.motionY = 0;
	}
}
