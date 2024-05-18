package me.valk.agway.modules.player;

import java.awt.Color;
import java.util.Objects;

import me.valk.event.EventListener;
import me.valk.event.EventType;
import me.valk.event.events.entity.EventEntityCollision;
import me.valk.event.events.player.EventMotion;
import me.valk.event.events.player.EventPushOut;
import me.valk.help.world.Location;
import me.valk.module.ModData;
import me.valk.module.ModType;
import me.valk.module.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;

/**
 * Created by V0idstar on 8/3/2016.
 */
public class FreecamMod extends Module {

	public FreecamMod() {
		super(new ModData("Freecam", 0, new Color(155, 110, 220)), ModType.PLAYER);
	}

	double[] xyz = new double[] { 0, 0, 0 };
	float[] angles = new float[] { 0, 0 };
	boolean ground = true;

	@Override
	public void onDisable() {
		if (Objects.isNull(mc.theWorld))
			return;
		p.capabilities.isFlying = false;
		p.noClip = false;
		mc.theWorld.removeEntityFromWorld(-1337);
		if (xyz[0] == 0 && xyz[1] == 0 && xyz[2] == 0)
			return;
		p.setPosition(xyz[0], xyz[1], xyz[2]);
	}

	@EventListener
	public void onBB(EventEntityCollision e) {
		e.setCancelled(true);
	}

	@Override
	public void onEnable() {
		if (Objects.isNull(this.mc.theWorld))
			return;

		xyz = new double[] { p.posX, p.posY, p.posZ };
		angles = new float[] { p.rotationYaw, p.rotationPitch };
		ground = p.onGround;
		EntityOtherPlayerMP ent = new EntityOtherPlayerMP(mc.theWorld, p.getGameProfile());
		ent.inventory = p.inventory;
		ent.inventoryContainer = p.inventoryContainer;
		ent.setPositionAndRotation(xyz[0], p.boundingBox.minY, xyz[2], p.rotationYaw, p.rotationPitch);
		ent.rotationYawHead = p.rotationYawHead;
		mc.theWorld.addEntityToWorld(-1337, ent);
	}

	@EventListener
	public void onPush(EventPushOut e2) {
		e2.setCancelled(true);
	}

	@EventListener
	public void onMotion(EventMotion event) {
		if (event.getType() == EventType.PRE) {
			p.capabilities.isFlying = true;
			p.noClip = true;
			if (p.isMoving()) {
				p.setSpeed(p.getSpeed() + 0.03f);
				p.setSprinting(true);
			} else
				p.setSpeed(0.0f);
			if (mc.gameSettings.keyBindJump.getIsKeyPressed())
				p.motionY = 0.3;
			else if (mc.gameSettings.keyBindSneak.getIsKeyPressed())
				p.motionY = -0.3;
			else
				p.motionY = 0;
			event.setLocation(new Location(xyz[0], xyz[1], xyz[2]));
			event.setYaw(angles[0]);
			event.setPitch(angles[1]);
			event.setOnGround(ground);
		}
	}
}