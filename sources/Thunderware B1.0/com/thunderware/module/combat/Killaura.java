package com.thunderware.module.combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;

import com.thunderware.Thunder;
import com.thunderware.events.Event;
import com.thunderware.events.listeners.EventMotion;
import com.thunderware.module.ModuleBase;
import com.thunderware.settings.settings.ModeSetting;
import com.thunderware.settings.settings.NumberSetting;
import com.thunderware.utils.EntityUtils;
import com.thunderware.utils.TimerUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;

public class Killaura extends ModuleBase {

	public NumberSetting cps = new NumberSetting("CPS", 9.5, 0.25, 1, 15);
	public NumberSetting reach = new NumberSetting("Reach", 4.2, 0.1, 2, 6);
	
	public Killaura() {
		super("Killaura", Keyboard.KEY_X,Category.COMBAT);
		addSettings(cps, reach);
	}
	
	private TimerUtils timer = new TimerUtils();
	public static Entity currentTarget;
	
	public void onEvent(Event baseEvent) {
		if(Thunder.i.moduleManager.getModuleByName("Flight").isToggled())
			return;
		if(baseEvent instanceof EventMotion && mc.thePlayer.ticksExisted > 40) {
			setSuffix(Math.floor(cps.getValue()) + " | " + Math.floor(reach.getValue()));
			EventMotion event = (EventMotion)baseEvent;
			CopyOnWriteArrayList<Entity> ent = AntiBot.getEntities();
			
			Entity target = getMainEntity(EntityUtils.distanceSort(ent));
			if(target != null) {
				float[] rots = ncpRotations(target, event);
				if(target.getDistanceToEntity(mc.thePlayer) <= (reach.getValue() + 1)) {
					currentTarget = target;
					//if(mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)
						//mc.thePlayer.setItemInUse(mc.thePlayer.getHeldItem(), 2);
					event.yaw = rots[0];
					event.pitch = rots[1];
				}else {
					currentTarget = null;
				}
				if(target.getDistanceToEntity(mc.thePlayer) <= reach.getValue() && timer.hasReached((cps.getValue() + (Math.random() * 5)) / 1000)) {
					
					mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C02PacketUseEntity(target,C02PacketUseEntity.Action.ATTACK));

					mc.thePlayer.swingItem();
					
					timer.reset();
				}
			}else {
				currentTarget = null;
			}
		}
	}

	public Entity getMainEntity(CopyOnWriteArrayList<Entity> entities) {
		if(entities.size() > 0)
			for(Entity ent : entities) {
				if(ent != mc.thePlayer && ent instanceof EntityLivingBase && ent.isEntityAlive()) {
					if(ent instanceof EntityPlayer) {
						return ent;
					}
					
					//Delete Line Below When Finished
					return ent;
				}
			}
		
		return null;
	}
	
	/*
	 * From Old Client <3
	*/
	public static float[] ncpRotations(Entity e, EventMotion p) {
		double x = e.posX + (e.posX - e.lastTickPosX) - p.getX();
		double y = (e.posY + e.getEyeHeight()) - (p.getY() + Minecraft.getMinecraft().thePlayer.getEyeHeight()) - 0.1;
		double z = e.posZ + (e.posZ - e.lastTickPosZ) - p.getZ();
		double dist = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));

		float yaw = (float) Math.toDegrees(-Math.atan(x / z));
		float pitch = (float) -Math.toDegrees(Math.atan(y / dist));

		if (x < 0 && z < 0)
			yaw = 90 + (float) Math.toDegrees(Math.atan(z / x));
		else if (x > 0 && z < 0)
			yaw = -90 + (float) Math.toDegrees(Math.atan(z / x));

		yaw += Math.random() * 4 - Math.random();
		pitch += Math.random() * 4 - Math.random();

		if (pitch > 90)
			pitch = 90;
		if (pitch < -90)
			pitch = -90;
		if (yaw > 180)
			yaw = 180;
		if (yaw < -180)
			yaw = -180;

		return new float[]{yaw, pitch};
	}
	
}
