package win.sightclient.module.player;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventPacket;
import win.sightclient.module.Category;
import win.sightclient.module.Module;

public class NoRotate extends Module {

	public NoRotate() {
		super("NoRotate", Category.PLAYER);
	}

	@Override
	public void onEvent(Event e) {
		if (e instanceof EventPacket && Minecraft.getMinecraft().thePlayer != null) {
			EventPacket ep = (EventPacket)e;
			if (ep.getPacket() instanceof S08PacketPlayerPosLook) {
				S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) ep.getPacket();
				packet.yaw = mc.thePlayer.rotationYaw;
				packet.pitch = mc.thePlayer.rotationPitch;
			}
		}
	}
}
