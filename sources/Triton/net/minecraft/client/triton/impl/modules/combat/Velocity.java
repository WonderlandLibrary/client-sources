// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.modules.combat;

import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.PacketReceiveEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.management.option.Option;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

@Mod
public class Velocity extends Module {
	@Option.Op(min = 0.0, max = 200.0, increment = 5.0, name = "Percent")
	private double percent;

	public Velocity() {
		this.percent = 0.0;
	}

	@EventTarget
	private void onPacketReceive(final PacketReceiveEvent event) {
		if (event.getPacket() instanceof S12PacketEntityVelocity) {
			final S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();
			if (ClientUtils.world().getEntityByID(packet.func_149412_c()) == ClientUtils.player()) {
				if (this.percent > 0.0) {
					S12PacketEntityVelocity vel = packet;
					vel.field_149415_b = (int) (vel.field_149415_b * (this.percent / 100.0D));
					vel.field_149416_c = (int) (vel.field_149416_c * (this.percent / 100.0D));
					vel.field_149414_d = (int) (vel.field_149414_d * (this.percent / 100.0D));
				} else {
					event.setCancelled(true);
				}
			}
		} else if (event.getPacket() instanceof S27PacketExplosion) {
			final S27PacketExplosion explo;
			final S27PacketExplosion packet2 = explo = (S27PacketExplosion) event.getPacket();
			explo.field_149152_f = (float) (explo.field_149152_f * (this.percent / 100.0D));
			explo.field_149153_g = (float) (explo.field_149153_g * (this.percent / 100.0D));
			explo.field_149159_h = (float) (explo.field_149159_h * (this.percent / 100.0D));
		}
	}
}
