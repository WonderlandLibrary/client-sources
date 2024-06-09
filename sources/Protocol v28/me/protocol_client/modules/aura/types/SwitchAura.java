package me.protocol_client.modules.aura.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.modules.aura.AuraType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import events.EventPacketSent;
import events.EventPostMotionUpdates;
import events.EventPreMotionUpdates;

public class SwitchAura extends AuraType {

	public void onUpdate(EventPreMotionUpdates event) {
		this.setName("Switch");
		if (isHealing()) {
			return;
		}
		if (Protocol.auraModule.block.getValue()) {
			for (Object o : Wrapper.getWorld().loadedEntityList) {
				if (o instanceof EntityLivingBase) {
					EntityLivingBase e = (EntityLivingBase) o;
					if (isAttackable(e) && Wrapper.getPlayer().getDistanceToEntity(e) <= Protocol.auraModule.blockrange.getValue()) {
						if (Wrapper.getPlayer().getHeldItem().getItem() instanceof ItemSword) {
							Wrapper.getPlayer().getHeldItem().useItemRightClick(Wrapper.getWorld(), Wrapper.getPlayer());
						}
					}
				}
			}
		}
		if (this.littleshits.isEmpty() || meme == null) {
			for (Object o : Wrapper.getWorld().loadedEntityList) {
				if (o instanceof EntityLivingBase) {
					EntityLivingBase ent = (EntityLivingBase) o;
					if (isAttackable(ent) && isInAttackRange(ent)) {
						if (!this.littleshits.contains(ent)) {
							this.littleshits.add(ent);
						}
					}
				}
			}
		}
		for (EntityLivingBase e : littleshits) {
			if (!isAttackable(e) || !isInAttackRange(e)) {
				littleshits.remove(e);
			}
		}
		if (!littleshits.isEmpty()) {
			meme = littleshits.get(littleshits.size() - 1);
		}
		face(meme, event);
		event.yaw = yaw;
		event.pitch = pitch;
	}

	public void afterUpdate(EventPostMotionUpdates event) {
		face(meme);
		event.yaw = yaw;
		event.pitch = pitch;
		EntityLivingBase m = null;
		if (meme != null && !littleshits.isEmpty() && isInAttackRange(meme)) {
			Wrapper.updateMS();
			if (littleshits.size() > 1) {
				m = littleshits.get(littleshits.size() - 2);
			}
			if (Wrapper.hasTimePassedM(180)) {
				EntityLivingBase nigger = meme;
				if (Protocol.auraModule.dura.getValue()) {
					Wrapper.mc().playerController.windowClick(Wrapper.getPlayer().inventoryContainer.windowId, 9, Wrapper.getPlayer().inventory.currentItem, 2, Wrapper.getPlayer());
					Wrapper.getPlayer().sendQueue.addToSendQueue(new C03PacketPlayer(true));
					attackMemerMan(nigger);
					attackMemerMan(nigger);
					attackMemerMan(nigger);
					Wrapper.mc().playerController.windowClick(Wrapper.getPlayer().inventoryContainer.windowId, 9, Wrapper.getPlayer().inventory.currentItem, 2, Wrapper.getPlayer());
					Wrapper.getPlayer().sendQueue.addToSendQueue(new C03PacketPlayer(true));
					attackMemerMan(nigger);
					attackMemerMan(nigger);
				} else {
					attackMemerMan(meme);
				}
				if (meme.getDistanceToEntity(m) <= 1.5f && m != null) {
					attackMemerMan(m);
				}
				Wrapper.updateLastMS();
			}
		}
		if (m != null) {
			reorderMobs(m);
		}
	}

	public void attackMemerMan(EntityLivingBase man) {
		tTicks++;
		if (tTicks >= (20 / Protocol.auraModule.delay.getValue())) {
			if (Wrapper.getPlayer().isBlocking()) {
				Wrapper.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
			}
			if (Protocol.auraModule.crits.getValue()) {
				this.crit();
			}
			Wrapper.getPlayer().swingItem();
			Wrapper.sendPacket(new C02PacketUseEntity(man, C02PacketUseEntity.Action.ATTACK));
			Wrapper.getPlayer().swingItem();
			if (shouldEnchantCrit(man)) {
				Wrapper.getPlayer().onEnchantmentCritical(man);
			}
			if (shouldShowCrits(Protocol.auraModule.crits.getValue())) {
				Wrapper.getPlayer().onCriticalHit(man);
			}
			if (man.hurtTime > 0) {
				littleshits.remove(man);
				switchTicks = 0;
			}
			this.lastTarget = man;
			tTicks = 0;
		}
	}

	public void onPacketOut(EventPacketSent event) {
		if ((!isHealing())) {
			if (meme != null) {
				face(meme);
			}
			if (event.getPacket() instanceof C03PacketPlayer) {
				final C03PacketPlayer look = (C03PacketPlayer) event.getPacket();
				look.yaw = yaw;
				look.pitch = pitch;
			}
			if (event.getPacket() instanceof C03PacketPlayer) {
				final C03PacketPlayer look = (C03PacketPlayer) event.getPacket();
				look.yaw = yaw;
				look.pitch = pitch;
			}
			if (event.getPacket() instanceof C03PacketPlayer) {
				final C03PacketPlayer look = (C03PacketPlayer) event.getPacket();
				look.yaw = yaw;
				look.pitch = pitch;
			}
			if (event.getPacket() instanceof C03PacketPlayer) {
				final C03PacketPlayer look = (C03PacketPlayer) event.getPacket();
				look.yaw = yaw;
				look.pitch = pitch;
			}
			if (event.getPacket() instanceof C03PacketPlayer) {
				final C03PacketPlayer look = (C03PacketPlayer) event.getPacket();
				look.yaw = yaw;
				look.pitch = pitch;
			}
			if (event.getPacket() instanceof C03PacketPlayer) {
				final C03PacketPlayer look = (C03PacketPlayer) event.getPacket();
				look.yaw = yaw;
				look.pitch = pitch;
			}
			if (event.getPacket() instanceof C03PacketPlayer) {
				final C03PacketPlayer look = (C03PacketPlayer) event.getPacket();
				look.yaw = yaw;
				look.pitch = pitch;
			}
		}
	}

	public void reorderMobs(EntityLivingBase m) {
		ArrayList<EntityLivingBase> newList = (ArrayList<EntityLivingBase>) littleshits;

		Collections.sort(newList, new Comparator<EntityLivingBase>() {
			public int compare(EntityLivingBase mod, EntityLivingBase mod1) {
				if (mod.getDistanceToEntity(m) > mod1.getDistanceToEntity(m)) {
					return -1;
				}
				if (mod.getDistanceToEntity(m) < mod1.getDistanceToEntity(m)) {
					return 1;
				}
				return 0;
			}
		});
		littleshits = newList;
	}

	public static int getDistanceFromMouse(Entity entity) {
		float[] neededRotations = getRotationsNeeded(entity);
		if (neededRotations != null) {
			float neededYaw = yaw - neededRotations[0], neededPitch = pitch - neededRotations[1];
			float distanceFromMouse = MathHelper.sqrt_float(neededYaw * neededYaw + neededPitch * neededPitch);
			return (int) distanceFromMouse;
		}
		return -1;
	}

	public static float[] getRotationsNeeded(Entity entity) {
		if (entity == null)
			return null;
		double diffX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
		double diffY;
		if (entity instanceof EntityLivingBase) {
			EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
			diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() * 0.9 - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
		} else diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
		double diffZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
		double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
		float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
		float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
		return new float[] { Minecraft.getMinecraft().thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw), Minecraft.getMinecraft().thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch) };
	}

	private Object		hacker	= new Object();
	private Runnable	hack	= () -> {
									while (true) {
										synchronized (hacker) {
											if (Protocol.auraModule.isToggled() || Wrapper.getWorld() == null) {
												try {
													hacker.wait();
												} catch (Exception e) {
													System.out.println("you got hacked");
												}
											}
										}

									}
								};
}
