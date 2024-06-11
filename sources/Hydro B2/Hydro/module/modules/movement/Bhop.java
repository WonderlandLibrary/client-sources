package Hydro.module.modules.movement;

import org.lwjgl.input.Keyboard;

import Hydro.Client;
import Hydro.ClickGui.settings.Setting;
import Hydro.event.Event;
import Hydro.event.events.EventMotion;
import Hydro.event.events.EventUpdate;
import Hydro.module.Category;
import Hydro.module.Module;
import Hydro.util.MoveUtils;
import Hydro.util.MovementUtil;

public class Bhop extends Module {

	public Bhop() {
		super("Bhop", Keyboard.KEY_X, true, Category.MOVEMENT, "Go faster");
		Client.instance.settingsManager.rSetting(new Setting("bhoptimer", "Timer boost(disabled)", this, 5, 1, 10, true));
	}
	
    public void onDisable() {
        mc.timer.timerSpeed = 1;
        mc.thePlayer.speedInAir = 0.02F;
    }
    
    public void onEvent(Event e) {
        if(e instanceof EventMotion) {
            if(e.isPre()) {
                if(mc.thePlayer.isInWater())
                    return;
                if (mc.thePlayer.onGround && mc.thePlayer.isMoving()) {
                    mc.thePlayer.jump();

                    mc.thePlayer.speedInAir = 0.04f;
                    MoveUtils.setMoveSpeed((EventMotion) e, 1, "event");
                }else {
                    mc.thePlayer.speedInAir = 0.022f;
                    MoveUtils.setMoveSpeed((EventMotion) e, 0.6, "event");
                }
            }
        }
    }

}
