package me.protocol_client.modules;

import org.lwjgl.input.Keyboard;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventBoundingBox;
import events.EventMove;
import events.EventPacketRecieve;
import events.EventPrePlayerUpdate;
import events.EventPushOutOfBlock;
import events.EventTick;

public class VanillaPhase extends Module {
	private double	speed	= 0.2;
	public int		moveUnder;
	private boolean	colliding;

	public VanillaPhase() {
		super("Vanilla Phase", "vanillaphase", 0, Category.MOVEMENT, new String[] { "" });
	}

	// TODO: Aris gave me permission to skid this, please don't yell at me for
	// skidding when you came in my code to skid :^)
	public void onEnable() {
		EventManager.register(this);
	}

	public void onDisable() {
		EventManager.unregister(this);
	}

	@EventTarget
	public void onPlayerUpdate(EventPrePlayerUpdate event) {
	}

	@EventTarget
	public void onPacket(EventPacketRecieve event) {
		if (event.getPacket() instanceof S08PacketPlayerPosLook && this.moveUnder == 2) {
			this.moveUnder = 1;
		}
	}

	@EventTarget
	public void onPush(EventPushOutOfBlock event) {
		event.setCancelled(true);
	}

	@EventTarget
	public void onBB(EventBoundingBox event) {
		if (Protocol.phase.isInsideBlock()) {
			event.setBoundingBox(null);
		}
	}

	@EventTarget
	public void onMove(EventMove event) {
		if (Wrapper.getPlayer().moveForward == 0 && Wrapper.getPlayer().moveStrafing == 0) {
			event.setCancelled(true);
		}
		if (Protocol.phase.isInsideBlock()) {
			Wrapper.getPlayer().noClip = true;
			if (Wrapper.getPlayer().movementInput.jump) {
				Wrapper.getPlayer().motionY = this.speed;
				event.y = Wrapper.getPlayer().motionY;
			} else if (Wrapper.getPlayer().movementInput.sneak) {
				Wrapper.getPlayer().motionY = -this.speed;
				event.y = Wrapper.getPlayer().motionY;
			} else {
				Wrapper.getPlayer().motionY = 0.0;
				event.y = 0.0;
			}
		}
	}

	@EventTarget
	public void onTick(EventTick event) {
		if (Wrapper.getPlayer() != null && this.moveUnder == 1) {
			Wrapper.sendPacket((Packet) new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY - 2.0, Wrapper.getPlayer().posZ, true));
			Wrapper.sendPacket((Packet) new C03PacketPlayer.C04PacketPlayerPosition(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, true));
			this.moveUnder = 0;
		}
	}
}
