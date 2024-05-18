package me.valk.agway.modules.movement;

import java.awt.Color;

import me.valk.event.EventListener;
import me.valk.event.events.entity.EventMoveRaw;
import me.valk.event.events.other.EventPacket;
import me.valk.module.ModData;
import me.valk.module.ModType;
import me.valk.module.Module;
import me.valk.utils.value.BooleanValue;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

public class NoFallMod extends Module {

	public NoFallMod() {
		super(new ModData("NoFall", 22, new Color(120, 10, 193)), ModType.MOVEMENT);
	}

	@Override
	public void onEnable() {
	}

	@EventListener
	public void onPacketSend(EventPacket event) {
		if (event.getPacket() instanceof C03PacketPlayer) {
			C03PacketPlayer packet = (C03PacketPlayer) event.getPacket();
			if (this.mc.thePlayer.fallDistance > 3.0f) {
				packet.field_149474_g = true;
			}
		}
	}
}