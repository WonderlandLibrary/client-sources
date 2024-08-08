package me.xatzdevelopments.xatz.client.modules;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.client.tools.timer;
import me.xatzdevelopments.xatz.module.Module;
import me.xatzdevelopments.xatz.utils.Movement.MoveUtils2;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class Test extends Module {

	public Test() {
		super("RedeskyFly", Keyboard.KEY_NONE, Category.HIDDEN, "Stronk redesky fly."); // Moement
	}

	@Override
	public void onDisable() {
		mc.timer.timerSpeed = 1f;
		mc.thePlayer.capabilities.isFlying = false;
		
		super.onDisable();
	}
 
	@Override
	public void onEnable() {
        
		super.onEnable();
	}

	@Override
	public void onUpdate() {
        mc.thePlayer.motionY = 0;
        if(MoveUtils2.isMoving()) {
            mc.thePlayer.motionZ *= 1.1453; //1653
            mc.thePlayer.motionX *= 1.1453;

		super.onUpdate();
        }
	}

}
