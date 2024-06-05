package zamorozka.modules.TRAFFIC;

import org.lwjgl.input.Keyboard;

import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.ClientUtils;
import zamorozka.ui.MoveUtils;
import zamorozka.ui.PlayerUtil;
import zamorozka.ui.SpeedUtils;
import zamorozka.ui.Wrapper;

public class TestSpeed extends Module {
	
	public TestSpeed() {
		super("TestSpeed", Keyboard.KEY_NONE, Category.TRAFFIC);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(mc.player.onGround) {
			mc.player.jump();
	        mc.timer.timerSpeed = 1.055f;
	        mc.player.motionX *= 1.085f;
	        mc.player.motionZ *= 1.085f;
	    }
 }
	
	@Override
	public void onDisable() {
		mc.timer.timerSpeed = 1f;
		super.onDisable();
	}
	
}