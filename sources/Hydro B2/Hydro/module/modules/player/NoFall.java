package Hydro.module.modules.player;

import org.lwjgl.input.Keyboard;

import Hydro.event.Event;
import Hydro.event.events.EventPacket;
import Hydro.module.Category;
import Hydro.module.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NoFall extends Module {

	public NoFall() {
		super("NoFall", Keyboard.KEY_N, true, Category.PLAYER, "Prevents fall damage");
	}
	
	@Override
	public void onEvent(Event e) {
		if(mc.thePlayer == null)
			return;
		if(e instanceof EventPacket && ((EventPacket) e).isSending() && mc.thePlayer.fallDistance > 3) {
			EventPacket event = (EventPacket) e;
            Packet<?> packet = event.getPacket();
            if (packet instanceof C03PacketPlayer) {

            	C03PacketPlayer C03 = (C03PacketPlayer) packet;

                C03.setOnGround(true);
            }
		}
	}

}
