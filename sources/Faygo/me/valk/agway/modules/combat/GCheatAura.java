package me.valk.agway.modules.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;

import me.valk.Vital;
import me.valk.event.EventListener;
import me.valk.event.EventType;
import me.valk.event.events.other.EventTick;
import me.valk.event.events.player.EventMotion;
import me.valk.help.world.Location;
import me.valk.module.ModData;
import me.valk.module.ModType;
import me.valk.module.Module;
import me.valk.utils.AimUtils;
import me.valk.utils.MathUtil;
import me.valk.utils.TimerUtils;
import me.valk.utils.value.BooleanValue;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class GCheatAura extends Module {

	private EntityLivingBase target;
	public int random;
	private List<EntityLivingBase> loaded = new ArrayList<>();
	private TimerUtils time = new TimerUtils();
	public BooleanValue invis = new BooleanValue("Invis", true);
	private TimerUtils timer = new TimerUtils();

	public GCheatAura() {
		super(new ModData("AdvancedAura", 0, new Color(255, 255, 255)), ModType.COMBAT);
		addValue(invis);
	}

	@EventListener
	public void onMotion(EventMotion event) {
		target = this.getBestEntity();

		if (target == null)
			return;
		if (event.getType() == EventType.PRE) {
			double distance = 2.147483647E9;
			for (Object object : mc.theWorld.loadedEntityList) {
				if (object instanceof EntityLivingBase && object != null && object != p) {
					EntityLivingBase entity = (EntityLivingBase) object;
					float[] direction = AimUtils.getRotations(entity);
					float a3 = direction[0];
					float yawDif = getAngleDifference(a3, p.rotationYaw);
					float a4 = direction[0];
					float pitchDif = getAngleDifference(a4, p.rotationPitch);
					if (!entity.isDead && entity != null && entity != p) {
						if (p.getDistanceToEntity(entity) < 6.0 && entity.isEntityAlive()) {
							EntityLivingBase entitylivingbase2 = entity;
							if (entitylivingbase2 != p && yawDif < 360.0f
									&& distance > Math.sqrt(yawDif * yawDif + pitchDif * pitchDif)) {
								distance = Math.sqrt(yawDif * yawDif + pitchDif * pitchDif);
								this.target = entity;
							}
						}
					}
				}

				if (p.getDistanceToEntity(target) < 6.0 && this.target != null && !this.target.isInvisible()
						&& target != p) {
					Location location = p.getLocation();
					float yawChange = AimUtils.getYawChangeToEntity(this.target);
					float pitchChange = AimUtils.getPitchChangeToEntity(this.target);
					Location location2 = location;
					location2.setYaw(p.rotationYaw + yawChange);
					Location location3 = location;
					location3.setPitch(p.rotationPitch + pitchChange);
					p.setLocation(location);
				}
			}
		}
	}
	
	//when you cant find a decompiler thats not jd-gui
	public static float getAngleDifference(final float a, final float b) {
		float dist = (a - b + 360.0f) % 360.0f;
		if (dist > 180.0f) {
			dist = 360.0f - dist;
		}
		return Math.abs(dist);
	}

	@EventListener
	public void Tick(EventTick e) {
		target = this.getBestEntity();
		if (mc.theWorld == null) {
			return;
		}
		if (p == null) {
			return;
		}

		this.random = MathUtil.getRandomInRange(1, 40);
		if (timer.hasReached((1000 / (11) + this.random)) && target != null && target.getDistanceToEntity(p) < 6) {
			mc.leftClickCounter = 0;
			p.swingItem();
			if (mc.objectMouseOver.entityHit != null) {
				p.sendQueue.addToSendQueue(
						new C02PacketUseEntity(mc.objectMouseOver.entityHit, C02PacketUseEntity.Action.ATTACK));
			}
			mc.playerController.isHittingBlock = false;
			timer.reset();
		}

	}
	
	public boolean isEntityInFov(final EntityLivingBase entity, double angle) {
		angle *= 0.5;
		final double angleDifference = MathUtil.getAngleDifference(p.rotationYaw, AimUtils.getRotations(entity)[0]);
		return (angleDifference > 0.0 && angleDifference < angle)
				|| (-angle < angleDifference && angleDifference < 0.0);
	}
	
	public boolean isValid(EntityLivingBase entity) {

		return (entity != null && entity.isEntityAlive() && this.isEntityInFov(entity, 360)
				&& entity != p
				&& entity instanceof EntityPlayer
				&& entity.getDistanceToEntity(p) <= 5.5
				&& (!entity.isInvisible() || this.invis.getValue()) && entity.ticksExisted > 20
				&& !Vital.getManagers().getFriendManager().hasFriend(entity.getName()));
	}


	public EntityLivingBase getBestEntity() {
		if (loaded != null) {
			loaded.clear();
		}
		for (Object object : mc.theWorld.loadedEntityList) {
			if (object instanceof EntityLivingBase) {
				EntityLivingBase e = (EntityLivingBase) object;
				if (isValid(e)) {
					loaded.add(e);
				}
			}
		}
		if (loaded.isEmpty()) {
			return null;
		}
		loaded.sort((o1, o2) -> {
			float[] rot1 = AimUtils.getRotations(o1);
			float[] rot2 = AimUtils.getRotations(o2);
			return (int) ((mc.thePlayer.rotationYaw - rot1[0]) % 360 - (mc.thePlayer.rotationYaw - rot2[0]) % 360);
		});
		return loaded.get(0);
	}
}
