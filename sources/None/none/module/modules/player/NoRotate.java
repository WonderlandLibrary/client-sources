package none.module.modules.player;

import org.lwjgl.input.Keyboard;

import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPacket;
import none.module.Category;
import none.module.Module;

public class NoRotate extends Module{

	public NoRotate() {
		super("NoRotate", "NoRotate", Category.PLAYER, Keyboard.KEY_NONE);
	}

	@Override
	@RegisterEvent(events = {EventPacket.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (event instanceof EventPacket && mc.thePlayer != null &&mc.thePlayer.ticksExisted > 10) {
			EventPacket ep = (EventPacket) event;
			Packet p = ep.getPacket();
			
			if (ep.isIncoming() && p instanceof S08PacketPlayerPosLook) {
				S08PacketPlayerPosLook pac = (S08PacketPlayerPosLook) ep.getPacket();
            	pac.yaw = mc.thePlayer.rotationYaw;
            	pac.pitch = mc.thePlayer.rotationPitch;
			}
		}
	}

}
