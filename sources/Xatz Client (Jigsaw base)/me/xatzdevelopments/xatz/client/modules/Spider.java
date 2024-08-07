package me.xatzdevelopments.xatz.client.modules;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.main.Xatz;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.module.Module;
import me.xatzdevelopments.xatz.utils.Timer.WaitTimer;

public class Spider extends Module {

	WaitTimer timer = new WaitTimer();
	boolean jumped = false;

	public Spider() {
		super("Spider", Keyboard.KEY_NONE, Category.MOVEMENT,
				"Automatically climbs walls for you, works 2.5 blocks on NCP mode");
	}

	@Override
	public void onDisable() {

		super.onDisable();
	}

	@Override
	public void onEnable() {

		super.onEnable();
	}

	@Override
	public void onUpdate() {
		if (!mc.thePlayer.isCollidedHorizontally || mc.thePlayer.isOnLadder()) {
			timer.reset();
		}
		if (mode("NCP")) {
			if (timer.hasTimeElapsed(200, false) && mc.thePlayer.onGround) {
				Xatz.sendChatMessage(".damage");
				mc.thePlayer.jump();
				mc.thePlayer.jump();
				mc.thePlayer.jump();
			}
			if (timer.hasTimeElapsed(250, false) && !jumped) {

				jumped = true;
			}
			if (timer.hasTimeElapsed(400, true) && !mc.thePlayer.onGround) {
				mc.thePlayer.motionY = 0.46;
				jumped = false;
			}
		}
		if (mode("Vanilla")) {
			if (mc.thePlayer.isCollidedHorizontally) {
				mc.thePlayer.motionY = 0.33;
			}
		}

		super.onUpdate();
	}

	@Override
	public String[] getModes() {
		return new String[] { "Vanilla", "NCP" };
	}
	
	public String getModeName() {
		return "Mode: ";
	}

}
