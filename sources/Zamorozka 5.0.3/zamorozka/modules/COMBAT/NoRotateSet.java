package zamorozka.modules.COMBAT;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPacket;
import zamorozka.event.events.EventPacketReceive;
import zamorozka.event.events.EventReceivePacket;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class NoRotateSet extends Module {

	public NoRotateSet() {
		super("AntiServerRotations", Keyboard.KEY_NONE, Category.COMBAT);
	}

	@EventTarget
	public void onReceive(EventReceivePacket event) {
		if (event.getPacket() instanceof SPacketPlayerPosLook) {
			SPacketPlayerPosLook packet = (SPacketPlayerPosLook) event.getPacket();
			if (!nullCheck()) {
				packet.yaw = mc.player.rotationYaw;
				packet.pitch = mc.player.rotationPitch;
			}
		}
	}
}