package Hydro.module.modules.player;

import Hydro.event.Event;
import Hydro.event.events.EventPacket;
import Hydro.module.Category;
import Hydro.module.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity extends Module {

	public Velocity() {
		super("Velocity", 0, true, Category.PLAYER, "Allows you to take no knockback");
	}
	
	public void onEvent(Event e) {
        if (e instanceof EventPacket && ((EventPacket)e).isRecieving()) {

            EventPacket event = (EventPacket) e;
            Packet<?> packet = event.getPacket();
            if (packet instanceof S12PacketEntityVelocity) {

                S12PacketEntityVelocity s12 = (S12PacketEntityVelocity) packet;
                
                if (s12.getEntityID() == mc.thePlayer.getEntityId())
                    e.setCancelled(true);
            }

            if (packet instanceof S27PacketExplosion) {
                e.setCancelled(true);
            }
        }
    }

}
