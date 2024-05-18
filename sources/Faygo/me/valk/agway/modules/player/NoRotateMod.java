package me.valk.agway.modules.player;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import me.valk.event.EventSystem;
import me.valk.event.Listener;
import me.valk.event.events.other.EventPacket;
import me.valk.event.events.other.EventPacket.EventPacketType;
import me.valk.module.ModData;
import me.valk.module.ModType;
import me.valk.module.Module;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class NoRotateMod extends Module {

	public NoRotateMod() {
		super(new ModData("NoRotate", Keyboard.KEY_P, new Color(155, 110, 220)),
				ModType.OTHER);
		
		EventSystem.register(new Listener<EventPacket>(){

			@Override
			public void onEvent(EventPacket event) {
				if(event.getType() == EventPacketType.RECEIVE && event.getPacket() instanceof S08PacketPlayerPosLook && getState()){
					S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) event
							.getPacket();

					if(p == null)
						return;

					
					packet.field_148936_d = p.rotationYaw;
					packet.field_148937_e = p.rotationPitch;
				}
			}
		
		});
	}
	
}
