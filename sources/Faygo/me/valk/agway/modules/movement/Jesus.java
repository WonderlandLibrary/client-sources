package me.valk.agway.modules.movement;

import java.awt.Color;

import me.valk.event.EventListener;
import me.valk.event.EventType;
import me.valk.event.events.entity.EventEntityCollision;
import me.valk.event.events.other.EventPacket;
import me.valk.event.events.other.EventPacket.EventPacketType;
import me.valk.event.events.player.EventPlayerUpdate;
import me.valk.help.entity.Player;
import me.valk.module.ModData;
import me.valk.module.ModType;
import me.valk.module.Module;
import net.minecraft.block.BlockLiquid;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S49PacketUpdateEntityNBT;
import net.minecraft.util.AxisAlignedBB;

public class Jesus extends Module {

	public Jesus() {
		super(new ModData("Jesus", 0, new Color(255, 255, 255)), ModType.MOVEMENT);

	}

	@EventListener
	public void onBoundingBox(EventEntityCollision e) {
		if (((e.getBlock() instanceof BlockLiquid)) && e.getEntity() == p && (!Player.isInLiquid())
				&& (p.fallDistance < 3.0F) && (!p.isSneaking())) {
			e.setBoundingBox(
					AxisAlignedBB.fromBounds(e.getLocation().getX(), e.getLocation().getY(), e.getLocation().getZ(),
							e.getLocation().getX() + 1, e.getLocation().getY() + 1, e.getLocation().getZ() + 1));
		}
	}

	@EventListener
	public void onPacketSend(EventPacket event) {
		if (event.getPacket() instanceof S49PacketUpdateEntityNBT) {
			S49PacketUpdateEntityNBT packet = (S49PacketUpdateEntityNBT) event.getPacket();

		}

		if ((event.getPacket() instanceof C03PacketPlayer) && event.getType() == EventPacketType.SEND) {
			C03PacketPlayer player = (C03PacketPlayer) event.getPacket();

			if (Player.isOnLiquid()) {
				player.setY(p.posY + (p.ticksExisted % 2 == 0 ? 0.01 : -0.01));
			}
		}
	}

	@EventListener
	public void onUpdate(EventPlayerUpdate event) {
		if (event.getType() == EventType.PRE && Player.isInLiquid() && !p.isSneaking()
				&& !mc.gameSettings.keyBindJump.pressed) {
			p.motionY = 0.1;
		}
	}
}
