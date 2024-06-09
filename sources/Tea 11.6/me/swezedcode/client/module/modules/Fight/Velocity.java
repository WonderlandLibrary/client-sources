package me.swezedcode.client.module.modules.Fight;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.module.modules.Options.MemeNames;
import me.swezedcode.client.utils.events.EventReadPacket;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class Velocity extends Module {

	public Velocity() {
		super("Velocity", Keyboard.KEY_NONE, 0xFFFC2D80, ModCategory.Fight);
	}

	public static Integer percentage = 0;

	@EventListener
	public void onHit(EventReadPacket event) {
		if (!MemeNames.enabled) {
			setDisplayName("Velocity §7" + (double) percentage + "%");
		} else {
			setDisplayName("MakesYouSoFatYaCantMoveWhenHitted §7" + (double) percentage + "%");
		}
		if (event.getState() == event.getState().PRE && event.getPacket() instanceof S12PacketEntityVelocity) {
			final S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();

			if (packet.id != mc.thePlayer.getEntityId())
				return;

			event.setCancelled(true);

			double x = packet.func_149411_d() / 8000d;
			double y = packet.func_149410_e() / 8000d;
			double z = packet.func_149409_f() / 8000d;

			double percent = ((double) percentage / 100);

			x *= percent;
			y *= percent;
			z *= percent;

			if (percentage == 0)
				return;

			mc.thePlayer.motionX = x;
			mc.thePlayer.motionY = y;
			mc.thePlayer.motionZ = z;
		}
	}

}
