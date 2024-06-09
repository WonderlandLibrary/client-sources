package me.swezedcode.client.module.modules.Motion;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import me.swezedcode.client.utils.values.BooleanValue;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class Sprint extends Module {

	public Sprint() {
		super("Sprint", Keyboard.KEY_Z, 0xFFE218F5, ModCategory.Motion);
	}

	private BooleanValue keepSprint = new BooleanValue(this, "KeepSprint", "keepsprint", Boolean.valueOf(true));

	@EventListener
	public void onMove(EventPreMotionUpdates e) {
		if (mc.gameSettings.keyBindForward.pressed || mc.gameSettings.keyBindBack.pressed
				|| mc.gameSettings.keyBindRight.pressed || mc.gameSettings.keyBindLeft.pressed) {
			setDisplayName(getName());
			if (!mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isOnLadder() && !mc.thePlayer.isSneaking()) {
				mc.thePlayer.setSprinting(true);
			}
		}
	}

}