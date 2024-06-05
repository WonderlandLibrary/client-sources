package xyz.vergoclient.modules.impl.combat;

import java.util.*;
import java.util.stream.Collectors;

import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.*;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.opengl.GL11;

import xyz.vergoclient.Vergo;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.*;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.modules.OnSettingChangeInterface;
import xyz.vergoclient.modules.impl.miscellaneous.AntiBot;
import xyz.vergoclient.modules.impl.miscellaneous.Teams;
import xyz.vergoclient.settings.BooleanSetting;
import xyz.vergoclient.settings.ModeSetting;
import xyz.vergoclient.settings.NumberSetting;
import xyz.vergoclient.settings.SettingChangeEvent;
import xyz.vergoclient.util.animations.Animation;
import xyz.vergoclient.util.animations.Direction;
import xyz.vergoclient.util.animations.impl.DecelerateAnimation;
import xyz.vergoclient.util.main.RenderUtils;
import xyz.vergoclient.util.main.RotationUtils;
import xyz.vergoclient.util.main.Timer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import xyz.vergoclient.util.main.TimerUtil;

public class KillAura extends Module implements OnSettingChangeInterface, OnEventInterface {

	// Timers
	Timer blockTimer;

	Animation animation1;

	public KillAura() {
		super("KillAura", Category.COMBAT);
		this.blockTimer = new Timer();
	}

	// Settings
	public NumberSetting rangeSetting = new NumberSetting("Range", 3.8, 0.5, 6, 0.1),
			minApsSetting = new NumberSetting("Min", 10, 0.1, 20, 0.1),
			maxApsSetting = new NumberSetting("Max", 14, 0.1, 20, 0.1),
	tickSwitchTimeSetting = new NumberSetting("Switch Timer", 20, 1, 100, 1);
	public BooleanSetting targetPlayersSetting = new BooleanSetting("Players", true),
			targetAnimalsSetting = new BooleanSetting("Animals", false),
			targetMobsSetting = new BooleanSetting("Mobs", false),
			targetOtherSetting = new BooleanSetting("Others", false),
			rayTraceCheck = new BooleanSetting("Visible Only", false),
	visualizeTargetCircle = new BooleanSetting("Visualize Target", true);
	public ModeSetting targetSelectionSetting = new ModeSetting("Attack Mode", "Switch", "Switch", "Single"),
			targetSortingSetting = new ModeSetting("Priority", "Health", "Health", "Distance"),
			rotationSetting = new ModeSetting("Rotations", "Lock", "Smoother", "Lock"/*, "Spin", "None", "Almost legit", "Bezier Curve"*/),
			autoblockSetting = new ModeSetting("Block Mode", "Hypixel");

	@Override
	public void loadSettings() {

		autoblockSetting.modes.clear();
		autoblockSetting.modes.addAll(Arrays.asList("None", "Hypixel"));

		rotationSetting.modes.addAll(Arrays.asList("Lock", "Smoother"));

		addSettings(rangeSetting, minApsSetting, maxApsSetting, targetPlayersSetting, targetAnimalsSetting,
				targetMobsSetting, targetOtherSetting, rayTraceCheck, rotationSetting, targetSelectionSetting, targetSortingSetting,
				autoblockSetting, visualizeTargetCircle);

	}

	// Vars used in the module
	private transient static double currentAps = 10, tickSwitch = 0;
	public transient static EntityLivingBase target, lastTarget = null;
	private transient static TimerUtil apsTimer = new TimerUtil();
	private transient static boolean isBlocking = false;

	public TimerUtil critTimer = new TimerUtil();

	@Override
	public void onSettingChange(SettingChangeEvent e) {

		if (e.setting == minApsSetting) {
			if (currentAps < minApsSetting.getValueAsDouble())
				currentAps = minApsSetting.getValueAsDouble();
			if (maxApsSetting.getValueAsDouble() < minApsSetting.getValueAsDouble()) {
				maxApsSetting.setValue(minApsSetting.getValueAsDouble());
			}
		} else if (e.setting == maxApsSetting) {
			if (currentAps > maxApsSetting.getValueAsDouble())
				currentAps = maxApsSetting.getValueAsDouble();
			if (minApsSetting.getValueAsDouble() > maxApsSetting.getValueAsDouble()) {
				minApsSetting.setValue(maxApsSetting.getValueAsDouble());
			}
		} else if (e.setting == targetSelectionSetting) {
			if (targetSelectionSetting.is("Switch")) {
				if (!settings.contains(tickSwitchTimeSetting)) {
					tickSwitch = tickSwitchTimeSetting.getValueAsDouble();
					settings.add(tickSwitchTimeSetting);
				}
			} else {
				if (settings.contains(tickSwitchTimeSetting)) {
					settings.remove(tickSwitchTimeSetting);
				}
			}
		} else if (e.setting == tickSwitchTimeSetting) {
			if (tickSwitch > tickSwitchTimeSetting.getValueAsDouble())
				tickSwitch = tickSwitchTimeSetting.getValueAsDouble();
		}

	}

	@Override
	public void onEnable() {
		this.critTimer.reset();

		lastYaw = mc.thePlayer.rotationYaw;
		lastPitch = mc.thePlayer.rotationPitch;

		target = null;

		this.blockTimer.reset();

		animation1 = new DecelerateAnimation(800, 1.8f, Direction.FORWARDS);
	}

	@Override
	public void onDisable() {
		target = null;
		mc.thePlayer.clearItemInUse();
		mc.gameSettings.keyBindUseItem.pressed = false;
	}

	public float animation;

	@Override
	public void onEvent(Event e) {

		if (e instanceof EventRender3D && e.isPre()) {

			/*if (visualizeTargetCircle.isEnabled() && target != null) {

				final float timer = mc.timer.renderPartialTicks;
				final double x = target.lastTickPosX + (target.posX - target.lastTickPosX) * timer;
				final double y = target.lastTickPosY + (target.posY - target.lastTickPosY) * timer;
				final double z = target.lastTickPosZ + (target.posZ - target.lastTickPosZ) * timer;

				GlStateManager.pushMatrix();
				GlStateManager.pushAttrib();
				Vec3 lastLine = null;

				for (short i = 0; i <= 360 * 2; i++) {

					float f = (float) ((target.rotationYaw + i) * (Math.PI / 180));
					double x2 = x, z2 = z;
					x2 -= (double) (MathHelper.sin(f) * 0.7) * -1;
					z2 += (double) (MathHelper.cos(f) * 0.7) * -1;

					if (lastLine == null) {
						lastLine = new Vec3(x2, y, z2);
						continue;
					}

					if (i != 0) {
						GL11.glColor4f(0.7f, 0.52f, 1.0f, 0.05f);

						RenderUtils.drawLine(lastLine.xCoord, y, lastLine.zCoord, x2, y + 2, z2);

					}

					lastLine.xCoord = x2;
					lastLine.zCoord = z2;

				}
				GlStateManager.popMatrix();
				GlStateManager.popAttrib();
			}*/

			drawVisTarget();
		}
		if (e instanceof EventUpdate && e.isPre()) {
			EventUpdate event = (EventUpdate) e;

			setTarget();

			// If there is no target return
			if (target == null) {
				if(mc.thePlayer.isUsingItem() && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword && !mc.gameSettings.keyBindUseItem.isPressed()) {
					mc.thePlayer.clearItemInUse();
					return;
				} else if(mc.thePlayer.isUsingItem() && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword && mc.gameSettings.keyBindUseItem.isPressed()) {
					return;
				} else {
					return;
				}
			}

			// Autoblock
			if ((!autoblockSetting.is("Legit") || !apsTimer.hasTimeElapsed((long) (1000 / currentAps), false)))
				block(true);

			if(rotationSetting.is("Lock")) {
				// Sets the rotations
				boolean shouldHit = setRotations(event);

				// If the rotations haven't looked at the player yet then don't send a damage
				// packet
				if (!shouldHit)
					return;

				RenderUtils.setCustomYaw(event.getYaw());
				RenderUtils.setCustomPitch(event.getPitch());
			} else if(rotationSetting.is("Smoother")) {

				// New Updated Smoother Rotations
				float[] rotations = getRotationsToEnt(target);

				// So rotations don't ban
				lastTarget = target;

				// Gets the sensitivity, then sets the rotations to the correct thing.
				float sens = RotationUtils.getSensitivityMultiplier();

				// Smooth the rotations.
				rotations[0] = RotationUtils.smoothRotation(mc.thePlayer.rotationYaw, rotations[0], 360);
				rotations[1] = RotationUtils.smoothRotation(mc.thePlayer.rotationPitch, rotations[1], 90);

				// Round Up The rotations.
				rotations[0] = Math.round(rotations[0] / sens) * sens;
				rotations[1] = Math.round(rotations[1] / sens) * sens;

				// Client sided rotations.
				RenderUtils.setCustomYaw(rotations[0]);
				RenderUtils.setCustomPitch(rotations[1]);

				// Server-sided Rotations.
				event.setYaw(rotations[0]);
				event.setPitch(rotations[1]);
			}

			// Hits at the aps that the user set
			if (apsTimer.hasTimeElapsed((long) (1000 / currentAps), true)) {

				if (minApsSetting.getValueAsDouble() != maxApsSetting.getValueAsDouble()) {
					currentAps = RandomUtils.nextDouble(minApsSetting.getValueAsDouble(),
							maxApsSetting.getValueAsDouble());
				} else {
					currentAps = maxApsSetting.getValueAsDouble();
				}

				// Send hit packet
				for (int i = 0; i < 1; i++) {
					mc.thePlayer.swingItem();
					mc.leftClickCounter = 0;
					mc.getNetHandler().getNetworkManager().sendPacket(new C02PacketUseEntity(target, Action.ATTACK));
				}

				// autoblock
				if (autoblockSetting.is("Hypixel")) {
					if (target != null) {
						block(true);
					}
				}

			}

		}

		if (e instanceof EventSendPacket && e.isPre()) {
			EventSendPacket event = (EventSendPacket) e;
			if (event.packet instanceof C09PacketHeldItemChange) {
				if (mc.thePlayer.getCurrentEquippedItem() == null
						|| !(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword)) {
					isBlocking = false;
				}
			}
		}

	}

	private void drawVisTarget() {
		// New Visualize Target
		if(target == null) {
			return;
		}
		GlStateManager.pushMatrix();
		final float timer = mc.timer.renderPartialTicks;
		final double x = target.lastTickPosX + (target.posX - target.lastTickPosX) * timer;
		final double y = target.lastTickPosY + (target.posY - target.lastTickPosY) * timer;
		final double z = target.lastTickPosZ + (target.posZ - target.lastTickPosZ) * timer;

		Vec3 lastLine = null;

		for (short i = 0; i <= 360 * 2; i++) {

			float f = (float) ((target.rotationYaw + i) * (Math.PI / 180));
			double x2 = x, z2 = z;
			x2 -= (double) (MathHelper.sin(f) * 0.7) * -1;
			z2 += (double) (MathHelper.cos(f) * 0.7) * -1;

			if (lastLine == null) {
				lastLine = new Vec3(x2, y, z2);
				continue;
			}

			if (i != 0) {
				GL11.glColor4f(0.7f, 0.52f, 1.0f, 0.9f);

				if(animation1.isDone()) {
					animation1.changeDirection();
				}

				// Render Moving Circles.
				RenderUtils.drawLine(lastLine.xCoord, y + animation1.getOutput() + 0.2f, lastLine.zCoord, x2, y + animation1.getOutput() + 0.2f, z2);
				GL11.glColor4f(0.7f, 0.52f, 1.0f, 0.6f);
				RenderUtils.drawLine(lastLine.xCoord, y + animation1.getOutput() + 0.15f, lastLine.zCoord, x2, y + animation1.getOutput() + 0.15f, z2);
				GL11.glColor4f(0.7f, 0.52f, 1.0f, 0.3f);
				RenderUtils.drawLine(lastLine.xCoord, y + animation1.getOutput() + 0.1f, lastLine.zCoord, x2, y + animation1.getOutput() + 0.1f, z2);
			}

			lastLine.xCoord = x2;
			lastLine.zCoord = z2;

		}

		GlStateManager.popMatrix();

	}

	// Returns false if the rotation is not finished
	public static transient float lastYaw = 0, lastPitch = 0;

	private float[] getRotationsToEnt(Entity ent) {
		final double differenceX = ent.posX - mc.thePlayer.posX;
		final double differenceY = (ent.posY + ent.height) - (mc.thePlayer.posY + mc.thePlayer.height) - 0.5;
		final double differenceZ = ent.posZ - mc.thePlayer.posZ;
		final float rotationYaw = (float) (Math.atan2(differenceZ, differenceX) * 180.0D / Math.PI) - 90.0f;
		final float rotationPitch = (float) (Math.atan2(differenceY, mc.thePlayer.getDistanceToEntity(ent)) * 180.0D
				/ Math.PI);
		final float finishedYaw = mc.thePlayer.rotationYaw
				+ MathHelper.wrapAngleTo180_float(rotationYaw - mc.thePlayer.rotationYaw);
		final float finishedPitch = mc.thePlayer.rotationPitch
				+ MathHelper.wrapAngleTo180_float(rotationPitch - mc.thePlayer.rotationPitch);
		return new float[]{finishedYaw, -MathHelper.clamp_float(finishedPitch, -90, 90)};
	}

	private boolean setRotations(EventUpdate e) {

		if (rotationSetting.is("Lock")) {
			float[] rots = RotationUtils.getRotationToEntity(target);
			e.setYaw(rots[0]);
			e.setPitch(rots[1]);
			return true;
		}

		return false;

	}

	private void setTarget() {
		if (target != null && (target.isDead || target.getHealth() <= 0)) {
			target = null;
		}

		if (targetSelectionSetting.is("Switch")) {
			if (tickSwitch <= 0) {
				tickSwitch = tickSwitchTimeSetting.getValueAsDouble();
			} else if (target != null) {
				if (mc.thePlayer.getDistance(target.posX, target.posY, target.posZ) <= rangeSetting
						.getValueAsDouble()) {
					tickSwitch--;
					return;
				} else {
					tickSwitch = tickSwitchTimeSetting.getValueAsDouble();
				}
			}
		}

		List<EntityLivingBase> potentialTargets = (List<EntityLivingBase>) mc.theWorld.loadedEntityList.stream()
				.filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
		potentialTargets = potentialTargets.stream()
				.filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < rangeSetting.getValueAsDouble()
						&& entity != mc.thePlayer)
				.collect(Collectors.toList());

		if (targetSortingSetting.is("Health")) {
			potentialTargets.sort(Comparator.comparingDouble(entity -> ((EntityLivingBase) entity).getHealth()));
		} else if (targetSortingSetting.is("Distance")) {
			potentialTargets.sort(Comparator
					.comparingDouble(entity -> ((EntityLivingBase) entity).getDistanceToEntity(mc.thePlayer)));
		}

		ArrayList<EntityLivingBase> targets = new ArrayList<>();
		for (EntityLivingBase e : potentialTargets) {

			if (rayTraceCheck.isEnabled()) {
				if (!e.canEntityBeSeen(mc.thePlayer)) {
					continue;
				}
			}

			if (e instanceof EntityPlayer && targetPlayersSetting.isEnabled())
				if (Vergo.config.modAntibot.isDisabled() || !AntiBot.isBot(((EntityPlayer) e)))
					if (Vergo.config.modTeams.isDisabled() || !Teams.isOnSameTeam(e))
						targets.add(e);

			if (e instanceof EntityAnimal && targetAnimalsSetting.isEnabled())
				targets.add(e);

			if (e instanceof EntityMob && targetMobsSetting.isEnabled())
				targets.add(e);

			if (!(e instanceof EntityPlayer || e instanceof EntityAnimal || e instanceof EntityMob)
					&& targetOtherSetting.isEnabled())
				targets.add(e);

		}

		if (targets.isEmpty()) {
			target = null;
			return;
		}

		// Get target
		target = targets.get(0);
	}

	private void block(boolean shouldBlock) {

		if (mc.thePlayer.getCurrentEquippedItem() == null || !(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) || autoblockSetting.is("None"))
			return;

		mc.gameSettings.keyBindUseItem.pressed = true;

		// Start blocking
		if (shouldBlock && !isBlocking) {
			if (this.blockTimer.delay(1L)) {
				mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255,
						null, 0, 0, 0));
			}

			isBlocking = true;
		}

		// Stop blocking
		else if (!shouldBlock && isBlocking) {
			long random = RandomUtils.nextLong(2000, 2500);
			if (autoblockSetting.is("Hypixel")) {
				if (this.blockTimer.delay(random)) {
					BlockPos debug = new BlockPos(0, 0, 0);
					mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C07PacketPlayerDigging(
							net.minecraft.network.play.client.C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, debug,
							EnumFacing.DOWN));
					this.blockTimer.reset();
				}
			}

			isBlocking = false;
		}
	}

}