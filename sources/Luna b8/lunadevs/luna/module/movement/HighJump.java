package lunadevs.luna.module.movement;

import org.lwjgl.input.Keyboard;

import com.zCore.Core.zCore;

import lunadevs.luna.category.Category;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import lunadevs.luna.utils.MoveUtils;
import net.minecraft.network.play.client.C03PacketPlayer;

public class HighJump extends Module {

	private boolean active;

	public HighJump() {
		super("Waterjump", Keyboard.KEY_NONE, Category.MOVEMENT, true);
	}

	@Override
	public void onUpdate() {
		if (!this.isEnabled) return;
		if (mc.gameSettings.keyBindSneak.pressed) {
		    return;
		   }
		   if(mc.thePlayer.isInWater() && !mc.gameSettings.keyBindSneak.isPressed() && !mc.gameSettings.keyBindJump.isPressed()) {
		    MoveUtils.setSpeed((float)((Float) 0.15F).floatValue());
		             mc.timer.timerSpeed = 1.4f;
		    mc.thePlayer.motionY = 0.5D;
		   } else if (!mc.thePlayer.isInWater()) {
		             mc.timer.timerSpeed = 1.0f;

		   }
			super.onUpdate();
	}

	
	@Override
	public void onEnable() {
		active=true;
	}
	@Override
	public void onDisable() {
		mc.timer.timerSpeed = 1.0f;
		active=false;
		super.onDisable();
	}

	@Override
	public String getValue() {
		return "Old";
	}

}
