package me.vaziak.sensation.client.impl.movement;

import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.property.impl.BooleanProperty;

/**
 * @author Spec
 */
public class FastLadder extends Module {

    private BooleanProperty vanilla = new BooleanProperty("Vanilla", "Just zoom", null, false);
	private boolean timer;

    public FastLadder() {
        super("FastLadder", Category.MOVEMENT);
        registerValue(vanilla);
    }

    @Collect
    public void onTickEvent(PlayerUpdateEvent event) {
        setMode(vanilla.getValue() ? "Vanilla" : "NCP");
        if (mc.thePlayer != null && mc.thePlayer.getFoodStats().getFoodLevel() > 5 && mc.thePlayer.isOnLadder()) {
            if (vanilla.getValue()) {

            	if (mc.thePlayer.ticksExisted % 4 == 0) {
            		mc.thePlayer.motionY *= 3;
            		timer = true;
            	}
            } else {  
            	if (mc.thePlayer.ticksExisted % 2 == 0) {
            		mc.timer.timerSpeed = 1.8f;
            		timer = true;
            	} else { 
            		mc.timer.timerSpeed = 1.0f;
            	}
            }
        } else {
        	if (timer) {
        		mc.thePlayer.motionY = 0;
        		mc.timer.timerSpeed = 1.0f;
        		timer = false;
        	}
        }
    }
}
