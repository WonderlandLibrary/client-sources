package me.swezedcode.client.module.modules.Fight;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;

public class AutoLog extends Module {

	public AutoLog() {
		super("AutoLog", Keyboard.KEY_NONE, 0xFFF94DFF, ModCategory.Fight);
	}

	@EventListener
	public void onLog(EventPreMotionUpdates event) {
		for (Object obj : mc.theWorld.loadedEntityList) {
			Entity e = (Entity) obj;
			if (e instanceof EntityPlayerSP && e != mc.thePlayer && mc.thePlayer.getDistanceSqToEntity(e) <= 6) {
				mc.theWorld.sendQuittingDisconnectingPacket();
			}
		}
	}

}
