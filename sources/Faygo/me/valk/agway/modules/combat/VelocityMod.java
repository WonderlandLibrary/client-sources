package me.valk.agway.modules.combat;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import me.valk.event.EventListener;
import me.valk.event.events.other.EventPacket;
import me.valk.event.events.other.EventPacket.EventPacketType;
import me.valk.module.ModData;
import me.valk.module.ModType;
import me.valk.module.Module;
import me.valk.utils.value.RestrictedValue;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class VelocityMod extends Module {

	public RestrictedValue<Integer> percentage = new RestrictedValue<Integer>("percentage", 0, 0, 100);

	public VelocityMod(){
		super(new ModData("Velocity", Keyboard.KEY_NONE, new Color(120, 255, 40)), ModType.COMBAT);
		addValue(percentage);
	}

	public void onEnable(){
		this.setDisplayName(this.getName() + "§7 " + (double)percentage.getValue() + "%");
	}

	@EventListener
	public void onPacket(EventPacket event){
		if(event.getType() == EventPacketType.RECEIVE && event.getPacket() instanceof  S12PacketEntityVelocity){
			S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();

			if(packet.id != p.getEntityId()) return;

			event.setCancelled(true);

			double x = packet.func_149411_d() / 8000d;
			double y = packet.func_149410_e() / 8000d;
			double z = packet.func_149409_f() / 8000d;

			double percent = ((double) percentage.getValue() /100);

			x *= percent;
			y *= percent;
			z *= percent;


			if(percentage.getValue() == 0) return;

			p.motionX = x;
			p.motionY = y;
			p.motionZ = z;
		}
	}

}