package me.rich.module.combat;

import java.util.ArrayList;
import java.util.List;
import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventPacket;
import me.rich.event.events.EventPreMotionUpdate;
import me.rich.helpers.Util;
import me.rich.helpers.combat.RotationHelper;
import me.rich.helpers.friend.Friend;
import me.rich.helpers.friend.FriendManager;
import me.rich.helpers.movement.MovementHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.EnumHand;

public class KillAura extends Feature {

	public static EntityLivingBase target;
	private double healthBarWidth;
	private double healthBarWidth1;
	private double hudHeight;
	int easingHealth = 0;
	List<EntityLivingBase> targets;

	public KillAura() {
		super("KillAura", 0, Category.COMBAT);
		this.targets = new ArrayList<EntityLivingBase>();
		ArrayList<String> rotation = new ArrayList<String>();
		rotation.add("Packet");
		rotation.add("Client");
		rotation.add("None");
		Main.settingsManager.rSetting(new Setting("Rotation Mode", this, "Packet", rotation));
		Main.settingsManager.rSetting(new Setting("FOV", this, 360.0, 0.0, 360.0, true));
		Main.settingsManager.rSetting(new Setting("Range", this, 4.0, 3.0, 7.0, false));
		Main.settingsManager.rSetting(new Setting("Players", this, true));
		Main.settingsManager.rSetting(new Setting("Mobs", this, false));
		Main.settingsManager.rSetting(new Setting("Invisible", this, false));
		Main.settingsManager.rSetting(new Setting("Walls", this, false));
		Main.settingsManager.rSetting(new Setting("NoSwing", this, false));
		Main.settingsManager.rSetting(new Setting("Particles Size", this, 5, 0, 15, true));
		Main.settingsManager.rSetting(new Setting("ShieldBreak", this, false));
		Main.settingsManager.rSetting(new Setting("ShieldBlock", this, false));
		Main.settingsManager.rSetting(new Setting("OnlyCrits", this, false));
		Main.settingsManager.rSetting(new Setting("Crits Fall Distance", this, 0.1, 0.08, 0.2, false));

	}

	@EventTarget
	public void onSendPacket(EventPacket event) {
		if (isToggled()) {
			if (event.getPacket() instanceof SPacketPlayerPosLook) {
				SPacketPlayerPosLook packet1 = (SPacketPlayerPosLook) event.getPacket();
				Minecraft.player.rotationYaw = packet1.getYaw();
				Minecraft.player.rotationPitch = packet1.getPitch();
			}
		}
	}

	@EventTarget
	public void onEventPreMotion(EventPreMotionUpdate mamanooma) {
		if (isToggled()) {
			String mode = Main.settingsManager.getSettingByName("Rotation Mode").getValString();
			this.setModuleName(
					"Killaura §7Rotation: " + Main.settingsManager.getSettingByName("Rotation Mode").getValString()
							+ " [" + Main.settingsManager.getSettingByName("Range").getValFloat() + "]");
			if (Minecraft.player.isEntityAlive()) {
				target = this.getClosest(Main.settingsManager.getSettingByName("Range").getValDouble());
				if (target == null) {
					return;
				}
				if (target.getHealth() > 0.0f) {
					float cdValue = Main.settingsManager.getSettingByName("OnlyCrits").getValBoolean() ? 0.95f
							: 1.0f;
					if (Minecraft.player.getCooledAttackStrength(0.0f) >= cdValue) {

						if (!MovementHelper.isBlockAboveHead()) {
							if (!(Minecraft.player.fallDistance > Main.settingsManager
									.getSettingByName("Crits Fall Distance").getValFloat())) {
								if (!Minecraft.player.killaurachecks() && Main.settingsManager
										.getSettingByName("OnlyCrits").getValBoolean()) {
									return;
								}
							}
						} else if (Minecraft.player.fallDistance != 0.0f) {
							if (!Minecraft.player.killaurachecks()
									&& Main.settingsManager.getSettingByName("OnlyCrits").getValBoolean()) {
								return;
							}
						}

						for (int i = 0; i < Main.settingsManager
								.getSettingByName(Main.moduleManager.getModule(KillAura.class), "Particles Size")
								.getValDouble(); i++)
							Minecraft.player.onCriticalHit(target);

						mc.playerController.attackEntity(Minecraft.player, target);
						if (Main.settingsManager.getSettingByName("NoSwing").getValBoolean()) {
							Minecraft.player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
						} else {
							Minecraft.player.swingArm(EnumHand.MAIN_HAND);
						}
						timerHelper.reset();
					}
				}
			}
		}
	}

	@EventTarget
	public void onRotations(EventPreMotionUpdate event) {
		if (isToggled()) {
			if (target == null) {
				return;
			}
			if (target.getHealth() > 0.0F) {
				String mode = Main.settingsManager.getSettingByName("Rotation Mode").getValString();
				float[] rots = RotationHelper.getRatations(target);
				if (mode.equalsIgnoreCase("Packet")) {
					event.setYaw(rots[0]);
					Minecraft.player.renderYawOffset = rots[0];
					Minecraft.player.rotationYawHead = rots[0];
					event.setPitch(rots[1]);
				}
				if (mode.equalsIgnoreCase("Client")) {
					Minecraft.player.rotationYaw = rots[0];
				}

				if (Main.settingsManager.getSettingByName("ShieldBreak").getValBoolean()
						&& target instanceof EntityPlayer
						&& (target.getActiveHand() == EnumHand.OFF_HAND && Item
								.getIdFromItem(target.getHeldItem(EnumHand.OFF_HAND).getItem()) == 442)
						&& (target).isActiveItemStackBlocking()) {

					Minecraft.player.inventory.currentItem = Util.getAxeAtHotbar();
					mc.playerController.attackEntity(Minecraft.player, KillAura.target);
					Minecraft.player.swingArm(EnumHand.MAIN_HAND);
					Minecraft.player.inventory.currentItem = Util.getSwordAtHotbar();

				}
			}
		}
	}

	private EntityLivingBase getClosest(double range) {
		this.targets.clear();
		double dist = range;
		EntityLivingBase target = null;
		for (Object object : mc.world.loadedEntityList) {
			EntityLivingBase player;
			Entity entity = (Entity) object;
			if (!(entity instanceof EntityLivingBase) || !canAttack(player = (EntityLivingBase) entity))
				continue;
			double currentDist = Minecraft.player.getDistanceToEntity(player);
			if (!(currentDist <= dist))
				continue;
			dist = currentDist;
			target = player;
			this.targets.add(player);
		}
		return target;
	}

	

	public static boolean canAttack(EntityLivingBase player) {
		String mode = Main.settingsManager.getSettingByName("AntiBot Mode").getValString();
		if (player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob
				|| player instanceof EntityVillager) {
			if (player instanceof EntityPlayer
					&& !Main.settingsManager.getSettingByName("Players").getValBoolean()) {
				return false;
			}
			if (player instanceof EntityAnimal
					&& !Main.settingsManager.getSettingByName("Mobs").getValBoolean()) {
				return false;
			}
			if (player instanceof EntityMob
					&& !Main.settingsManager.getSettingByName("Mobs").getValBoolean()) {
				return false;
			}

			if (player instanceof EntityVillager
					&& !Main.settingsManager.getSettingByName("Mobs").getValBoolean()) {
				return false;
			}
		}
		if (player.isInvisible() && !Main.settingsManager.getSettingByName("Invisible").getValBoolean()) {
			return false;
		}

		for (Friend friend : FriendManager.friendsList) {
			if (!player.getName().equals(friend.getName()))
				continue;
			return false;
		}

		if (Main.moduleManager.getModule(AntiBot.class).isToggled() && mode.equalsIgnoreCase("HitBefore")
				&& !AntiBot.entete.contains(player)) {
			return false;
		}

		if (!RotationHelper.canSeeEntityAtFov(player,
				(float) Main.settingsManager.getSettingByName("FOV").getValDouble())
				&& !KillAura.canSeeEntityAtFov(player,
						(float) Main.settingsManager.getSettingByName("FOV").getValDouble())) {
			return false;
		}
		if (!range(player, Main.settingsManager.getSettingByName("Range").getValDouble())) {
			return false;
		}
		if (!player.canEntityBeSeen(Minecraft.player)) {
			return Main.settingsManager.getSettingByName("Walls").getValBoolean();
		}
		return player != Minecraft.player;
	}

	private static boolean range(EntityLivingBase entity, double range) {
		Minecraft.player.getDistanceToEntity(entity);
		return (double) Minecraft.player.getDistanceToEntity(entity) <= range;
	}

	public static boolean canSeeEntityAtFov(Entity entityLiving, float scope) {
		double diffX = entityLiving.posX - Minecraft.player.posX;
		double diffZ = entityLiving.posZ - Minecraft.player.posZ;
		float newYaw = (float) (Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0);
		double d = newYaw;
		double difference = KillAura.angleDifference(d, Minecraft.player.rotationYaw);
		return difference <= (double) scope;
	}

	public static double angleDifference(double a, double b) {
		float yaw360 = (float) (Math.abs(a - b) % 360.0);
		if (yaw360 > 180.0f) {
			yaw360 = 360.0f - yaw360;
		}
		return yaw360;
	}

	@Override
	public void onEnable() {
		super.onEnable();
		NotificationPublisher.queue(getName(), "was enabled.", NotificationType.INFO);
	}

	public void onDisable() {
		NotificationPublisher.queue(getName(), "was disabled.", NotificationType.INFO);
		super.onDisable();
	}
}
