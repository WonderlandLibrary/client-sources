package me.rich.module.combat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventAttack;
import me.rich.event.events.EventPreMotionUpdate;
import me.rich.event.events.EventReceivePacket;
import me.rich.event.events.EventUpdate;
import me.rich.helpers.friend.FriendManager;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketSpawnPlayer;
import net.minecraft.util.text.TextFormatting;

public class AntiBot extends Feature {
	public static ArrayList<Entity> entete = new ArrayList();
	private UUID detectedEntity;

	public AntiBot() {
		super("AntiBot", 0, Category.COMBAT);
	}

	@Override
	public void setup() {
		ArrayList<String> mode = new ArrayList<String>();
		mode.add("Matrix");
		mode.add("HitBefore");
		Main.instance.settingsManager.rSetting(new Setting("AntiBot Mode", this, "Matrix", mode));
		Main.instance.settingsManager.rSetting(new Setting("Invisibles remove", this, false));
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {this.isBot();}

	public void isBot() {
		this.setModuleName("AntiBot " + "§7[" + Main.settingsManager.getSettingByName("AntiBot Mode").getValString() + "]");

		Iterator var2 = mc.world.playerEntities.iterator();
		String mode = Main.instance.settingsManager.getSettingByName("AntiBot Mode").getValString();
		if (mode.equalsIgnoreCase("Matrix")) {
			for (final Entity e : mc.world.loadedEntityList) {
				if (e.ticksExisted < 5 && e instanceof EntityOtherPlayerMP) {
					if (((EntityOtherPlayerMP) e).hurtTime > 0 && mc.player.getDistanceToEntity(e) <= 25
							&& mc.getConnection().getPlayerInfo(e.getUniqueID()).getResponseTime() != 0) {
						mc.world.removeEntity(e);
					}
				}
			}
		}
		if (Main.settingsManager.getSettingByName(Main.moduleManager.getModule(AntiBot.class), "Invisibles remove")
				.getValBoolean()) {
			for (Object entity : mc.world.loadedEntityList)
				if (((Entity) entity).isInvisible() && entity != mc.player)
					mc.world.removeEntity((Entity) entity);
		}
	}

	@EventTarget
	public void onMouse(EventAttack event) {
		String mode = Main.instance.settingsManager.getSettingByName("AntiBot Mode").getValString();
		if (isToggled()) {
			if (mode.equalsIgnoreCase("HitBefore")) {
				EntityPlayer entityPlayer = (EntityPlayer) mc.objectMouseOver.entityHit;
				String name = entityPlayer.getName();
				
				if (entityPlayer != null) {
					if (FriendManager.getFriends().isFriend(entityPlayer.getName()))
						return;
					
					if (entete.contains(entityPlayer)) {
						NotificationPublisher.queue(TextFormatting.GRAY + name  , "already in a-b.",  NotificationType.INFO);
					} else {
						NotificationPublisher.queue(TextFormatting.GRAY + name , "added in a-b.",  NotificationType.INFO);
					}
				}
			}
		}
	}

	@EventTarget
	public void onPre(EventPreMotionUpdate event) {
		for (Entity entity : mc.world.loadedEntityList) {
			if (mc.player != null) {
				return;
			}
			if (!Main.instance.settingsManager.getSettingByName("Invisible Remove").getValBoolean()
					|| entity instanceof EntityPlayer && entity.isInvisible())
				continue;
			mc.world.removeEntity(entity);
				NotificationPublisher.queue(TextFormatting.GRAY + name  , "Removed Invisibles",  NotificationType.INFO);
		}
		}
	

	@EventTarget
	public void onPacket(EventReceivePacket event) {
		String mode = Main.instance.settingsManager.getSettingByName("AntiBot Mode").getValString();
		if (mode.equalsIgnoreCase("Matrix")) {
			if (event.getPacket() instanceof SPacketSpawnPlayer && mc.player.ticksExisted >= 9) {
				if (((SPacketSpawnPlayer) event.getPacket()).getYaw() != 0
						&& ((SPacketSpawnPlayer) event.getPacket()).getPitch() != 0) {
					detectedEntity = ((SPacketSpawnPlayer) event.getPacket()).getUniqueId();
				}
			}
		}
	}

	public void onEnable() {
		super.onEnable();
		NotificationPublisher.queue(this.getName(), "was enabled.", NotificationType.INFO);
	}

	public void onDisable() {
		NotificationPublisher.queue(this.getName(), "was disabled.", NotificationType.INFO);
		super.onDisable();
	}
}
