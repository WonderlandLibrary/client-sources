package zamorozka.modules.COMBAT;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.Hero.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.main.Main;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.EntityEquipmentSlot.Type;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import optifine.CustomColors;

import org.apache.commons.codec.language.bm.Rule.RPattern;
import org.lwjgl.opengl.GL11;
import zamorozka.event.EventTarget;
import zamorozka.event.events.AttackEvent;
import zamorozka.event.events.*;
import zamorozka.main.Zamorozka;
import zamorozka.main.indexer;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.ZAMOROZKA.YoutuberMode;
import zamorozka.notification.NotificationPublisher;
import zamorozka.notification.NotificationType;
import zamorozka.ui.*;
import zamorozka.ui.font.Fonts;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class KillAura extends Module {
	private Timer2 timecheck = new Timer2();
	private static final Color COLOR = new Color(25, 25, 25, 255);
	public static EntityLivingBase target;
	private static int test;
	private static float animtest;
	private static boolean anim;
	private final AngleUtil angleUtility = new AngleUtil(110, 120, 30, 40);// This is the angle utility
	public Float lastYaw;
	private double time;
	private float[] angles = new float[2];
	private double healthBarWidth;
	private double hudHeight;
	TimerHelper timer;
	Timer2 tim = new Timer2();

	/* target hud */

	private static final double ANIMATION_SPEED = 8;

	private double animationState;
	private double animationState1;
	private double healthBarWidth1;
	private double hudHeight1;

	/* rotations */

	private float lastPitchs;
	private float lastYaws;

	public KillAura() {
		super("KillAura", 0, Category.COMBAT);
	}

	private static float getSensitivityMultiplier() {
		float f = mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
		return (f * f * f * 8.0F) * 0.15F;
	}

	public static synchronized void normrots(Entity target) {
		float[] rotations = getrots(target);
		float sens = getSensitivityMultiplier();
		float yaw = rotations[0] + MathUtil.getRandomInRange(5, 1);
		float pitch = rotations[1] + MathUtil.getRandomInRange(2, 1);
		float yawGCD = (Math.round(yaw / sens) * sens);
		float pitchGCD = (Math.round(pitch / sens) * sens);
		Minecraft.player.rotationYaw = yawGCD;
		if (Zamorozka.settingsManager.getSettingByName("PitchRotations").getValBoolean()) {
			Minecraft.player.rotationPitch = pitchGCD;
		}
	}

	public static float[] getrots(Entity entity) {
		if (entity == null) {
			return null;
		}

		final double diffX = entity.posX - Minecraft.player.posX;
		final double diffZ = entity.posZ - Minecraft.player.posZ;
		double diffY;

		if (entity instanceof EntityLivingBase) {
			final EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
			diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (Minecraft.player.posY + Minecraft.player.getEyeHeight() + 1);
		} else {
			diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D - (Minecraft.player.posY + Minecraft.player.getEyeHeight() + 1);
		}

		final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
		float yaw = (float) (Math.atan2(diffZ, diffX) * 180 / 3.141592653589793D) - 90.0F;
		float pitch = (float) -(Math.atan2(diffY, dist) * 180 / 3.141592653589793D);
		return new float[] { Minecraft.player.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - Minecraft.player.rotationYaw), Minecraft.player.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Minecraft.player.rotationPitch) };
	}

	public static Color setAlpha(Color color, int alpha) {
		alpha = (int) BetterColor.clamp(alpha, 0, 255);
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
	}

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		Zamorozka.settingsManager.rSetting(new Setting("Rotation Mode", this, "Matrix", options));
		options.add("None");
		options.add("Visual");
		options.add("Legit");
		options.add("LegitSnap");
		options.add("Matrix");
		ArrayList<String> options2 = new ArrayList<>();
		Zamorozka.settingsManager.rSetting(new Setting("Circle Mode", this, "Custom", options2));
		options2.add("CustomWithJello");
		options2.add("Custom");
		options2.add("Jello");
		ArrayList<String> options5 = new ArrayList<>();
		Zamorozka.settingsManager.rSetting(new Setting("CircleColor Mode", this, "ClientColor", options5));
		options5.add("ClientColor");
		options5.add("White");
		ArrayList<String> options3 = new ArrayList<>();
		Zamorozka.settingsManager.rSetting(new Setting("TargetHUD Mode", this, "Smertnix", options3));
		options3.add("Smertnix");
		options3.add("Shalopay");
		options3.add("NovolineOLD");
		options3.add("ExhiDEV");
		options3.add("HVH");
		Zamorozka.settingsManager.rSetting(new Setting("PitchRotations", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("FOV", this, 360, 0, 360, true));
		Zamorozka.settingsManager.rSetting(new Setting("AttackRange", this, 4, 3, 7, false));
		Zamorozka.settingsManager.rSetting(new Setting("TicksExisted", this, 0, 0, 100, false));
		Zamorozka.settingsManager.rSetting(new Setting("HitChance", this, 100, 1, 100, true));
		Zamorozka.settingsManager.rSetting(new Setting("CustomCoolDown", this, 1, 0.9, 1, true));
		Zamorozka.settingsManager.rSetting(new Setting("CritFallDistance", this, 0.2, 0.01, 1, true));
		Zamorozka.settingsManager.rSetting(new Setting("CritOnly", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("Friends", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("AutoResetCooldown", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("Combo", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("SnapHead", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("AutoSwordSwitch", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("Sword/Axe only", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("AutoBreakingShield", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("SeeHealthOnHit", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("AutoPressShield", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("SunriseBypass", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("RotationStrafe", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("DeathDisable", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("AutoWinDisable", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("NoVisuallySwing", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("CoolDown", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("IgnoreNuked", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("AttackInvisibles", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("HitArmorStand", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("Players", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("Animals", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("Monsters", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("Villagers", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("NoHitTeams", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("ThoughWalls", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("HeadESP", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("TargetESP", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("RenderCircle", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("RenderOwnCircle", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("TargetHUD", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("TargetHUDPosX", this, 70, -700, 600, true));
		Zamorozka.settingsManager.rSetting(new Setting("TargetHUDPosY", this, 80, -400, 300, true));
		Zamorozka.settingsManager.rSetting(new Setting("CircleWidth", this, 3, 0.1, 15, true));
		Zamorozka.settingsManager.rSetting(new Setting("CirclePoint", this, 7, 1, 90, true));
		Zamorozka.settingsManager.rSetting(new Setting("ComboSpeed", this, 0.2, 0, 1, true));
		Zamorozka.settingsManager.rSetting(new Setting("RotationSpeed", this, 1.9, 1, 20, true));
		Zamorozka.settingsManager.rSetting(new Setting("RotationRandom", this, 0, 0, 10, true));
		Zamorozka.settingsManager.rSetting(new Setting("LegitSnapCooldown", this, 0.9, 0.1, 0.99, true));
		Zamorozka.settingsManager.rSetting(new Setting("RayTrace", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("RayTraceBoxReduce", this, 0.7, 0.4, 1.5, false));
		Zamorozka.settingsManager.rSetting(new Setting("SlowMotion", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("SlowMotionValue", this, 0.6, 0.1, 1.2, false));
		Zamorozka.settingsManager.rSetting(new Setting("SprintDisable", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("1.8PVP", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("1.8CPSDelay", this, 100, 1, 150, true));
		Zamorozka.settingsManager.rSetting(new Setting("1.8CPSRandom", this, 50, 0, 150, true));
	}

	@EventTarget
	public void onUpdate(EventPreMotionUpdates event) {
		if (!getState())
			return;
		String mode = Zamorozka.settingsManager.getSettingByName("Rotation Mode").getValString();
		String modeput = Character.toUpperCase(mode.charAt(0)) + mode.substring(1);
		this.setDisplayName("KillAura " + ChatFormatting.WHITE + "Rotation mode: " + modeput + ", Range: " + ClientUtils.round((float) Zamorozka.settingsManager.getSettingByName("AttackRange").getValDouble(), 2));
		target = getClosest(Zamorozka.settingsManager.getSettingByName("AttackRange").getValDouble());
		if (target == null)
			return;
		float fl = (float) Zamorozka.settingsManager.getSettingByName("CritFallDistance").getValDouble();
		float ff = (float) Zamorozka.settingsManager.getSettingByName("CustomCoolDown").getValDouble();
		if (Zamorozka.settingsManager.getSettingByName("SlowMotion").getValBoolean() && target != null) {
			mc.player.motionX *= Zamorozka.settingsManager.getSettingByName("SlowMotionValue").getValDouble();
			mc.player.motionZ *= Zamorozka.settingsManager.getSettingByName("SlowMotionValue").getValDouble();
		}

		if (Zamorozka.settingsManager.getSettingByName("SprintDisable").getValBoolean() && target != null && getState()) {
			if (target == null)
				return;
			mc.player.setSprinting(false);
		}

		if (Zamorozka.settingsManager.getSettingByName("CritOnly").getValBoolean()) {
			ff = 0.95f;
		}
		if (Zamorozka.settingsManager.getSettingByName("SnapHead").getValBoolean()) {
			if (Minecraft.player.getCooledAttackStrength(0) >= ff) {
				for (ItemStack stack : target.getArmorInventoryList())
					if ((stack.func_190926_b()) && Zamorozka.settingsManager.getSettingByName("IgnoreNuked").getValBoolean())
						return;
				normrots(target);
			}
		}
		if (Zamorozka.settingsManager.getSettingByName("CoolDown").getValBoolean()) {
			float chance = (float) Zamorozka.settingsManager.getSettingByName("HitChance").getValDouble();
			if (!(Minecraft.player.getHeldItemMainhand().getItem() instanceof ItemSword || Minecraft.player.getHeldItemMainhand().getItem() instanceof ItemAxe) && Zamorozka.settingsManager.getSettingByName("Sword/Axe only").getValBoolean())
				return;
			for (ItemStack stack : target.getArmorInventoryList())
				if ((stack.func_190926_b()) && Zamorozka.settingsManager.getSettingByName("IgnoreNuked").getValBoolean())
					return;
			if (Zamorozka.settingsManager.getSettingByName("CritOnly").getValBoolean()) {
				ff = 0.95f;
			}
			if (Minecraft.player.getCooledAttackStrength(0) >= ff && AngleUtil.randomFloat(0.0f, 100.0f) <= chance && target.getHealth() > 0) {
				double gt = Zamorozka.settingsManager.getSettingByName("1.8CPSDelay").getValDouble();
				double gt1 = Zamorozka.settingsManager.getSettingByName("1.8CPSRandom").getValDouble();
				if (!tim.check((float) (gt + KillauraUtil.getRandomDouble(0, gt1))) && (Zamorozka.settingsManager.getSettingByName("1.8PVP").getValBoolean()))
					return;
				boolean lookingat = RotationSpoofer.isLookingAtEntity(target);
				if (!lookingat && (Zamorozka.settingsManager.getSettingByName("RayTrace").getValBoolean()))
					return;
				// критикалсы!!!
				if (!MovementUtilis.isBlockAboveHead()) {
					if (!(Minecraft.player.fallDistance > fl) && !Minecraft.player.isInLiquid2() && (Zamorozka.settingsManager.getSettingByName("CritOnly").getValBoolean()))
						return;
				} else {
					if ((mc.player.fallDistance != 0) && !Minecraft.player.isInLiquid2() && (Zamorozka.settingsManager.getSettingByName("CritOnly").getValBoolean()))
						return;
				}
				// критикалс биг энд (дик)
				mc.playerController.attackEntity(Minecraft.player, target);
				Minecraft.player.swingArm(EnumHand.MAIN_HAND);
				tim.reset();
				if (Zamorozka.settingsManager.getSettingByName("Combo").getValBoolean()) {
					MovementUtils.strafe();
					float dir = Minecraft.player.rotationYaw + ((Minecraft.player.moveForward < 0) ? 180 : 0)
							+ ((Minecraft.player.moveStrafing > 0) ? (-90F * ((Minecraft.player.moveForward < 0) ? -.5F : ((Minecraft.player.moveForward > 0) ? .4F : 1F))) : 0);
					float xDir = (float) ((float) Math.cos((dir + 90F) * Math.PI / 180));
					float zDir = (float) ((float) Math.sin((dir + 90F) * Math.PI / 180));
					Minecraft.player.motionX = xDir * Zamorozka.settingsManager.getSettingByName("ComboSpeed").getValDouble();
					Minecraft.player.motionZ = zDir * Zamorozka.settingsManager.getSettingByName("ComboSpeed").getValDouble();
				}
				if (Zamorozka.settingsManager.getSettingByName("SeeHealthOnHit").getValBoolean()) {
					ChatUtils.printChatprefix(ChatFormatting.RED + target.getName() + ChatFormatting.RESET + " has " + ClientUtils.round(target.getHealth() / 2, 0) + ChatFormatting.RED + " \u2764");
				}
				if (Zamorozka.settingsManager.getSettingByName("AutoSwordSwitch").getValBoolean()) {
					Minecraft.player.inventory.currentItem = EntityUtil.getSwordAtHotbar();
				}
				if (Zamorozka.settingsManager.getSettingByName("SunriseBypass").getValBoolean()) {
					target = null;
				}
			}
			if (Zamorozka.settingsManager.getSettingByName("DeathDisable").getValBoolean()) {
				if (Minecraft.player.getHealth() <= 0) {
					ModuleManager.getModule(KillAura.class).setState(false);
					isEnabled = false;
					ChatUtils.printChatprefix("KillAura was disabled on death!");
				}
			}
		}
	}

	@EventTarget
	public void onPreUpdate(EventPreMotionUpdates event) {
		if (!getState())
			return;

		if (!(Minecraft.player.getHeldItemMainhand().getItem() instanceof ItemSword || Minecraft.player.getHeldItemMainhand().getItem() instanceof ItemAxe) && Zamorozka.settingsManager.getSettingByName("Sword/Axe only").getValBoolean())
			return;

		String mode = Zamorozka.settingsManager.getSettingByName("Rotation Mode").getValString();

		if (mode.equalsIgnoreCase("Matrix")) {
			// Rotations by koloslolya
			if (target != null && target.getHealth() > 0 && canAttack(target)) {
				for (ItemStack stack : target.getArmorInventoryList())
					if ((stack.func_190926_b()) && Zamorozka.settingsManager.getSettingByName("IgnoreNuked").getValBoolean())
						return;
				float[] rots = RotationUtils.getVodkaRotations(target, false);
				event.setYaw(rots[0]);
				event.setPitch(rots[1]);
				if (Zamorozka.settingsManager.getSettingByName("PitchRotations").getValBoolean()) {
					Minecraft.player.rotationPitchHead = rots[1];
				}
				Minecraft.player.renderYawOffset = rots[0];
				Minecraft.player.rotationYawHead = rots[0];
			}
		}

		if (mode.equalsIgnoreCase("Visual")) {
			if (target != null && target.getHealth() > 0 && canAttack(target)) {
				for (ItemStack stack : target.getArmorInventoryList())
					if ((stack.func_190926_b()) && Zamorozka.settingsManager.getSettingByName("IgnoreNuked").getValBoolean())
						return;
				float[] rots = RotationUtils.getRotationsToward(target);
				Minecraft.player.rotationYawHead = rots[0];
				Minecraft.player.renderYawOffset = rots[0];
				if (Zamorozka.settingsManager.getSettingByName("PitchRotations").getValBoolean()) {
					Minecraft.player.rotationPitchHead = rots[1];
				}
			}
		}

		float ff = (float) Zamorozka.settingsManager.getSettingByName("CustomCoolDown").getValDouble();
		if (Zamorozka.settingsManager.getSettingByName("CritOnly").getValBoolean()) {
			ff = 0.95f;
		}

		if (Zamorozka.settingsManager.getSettingByName("SnapHead").getValBoolean()) {
			if (Minecraft.player.getCooledAttackStrength(0) >= ff) {
				for (ItemStack stack : target.getArmorInventoryList())
					if ((stack.func_190926_b()) && Zamorozka.settingsManager.getSettingByName("IgnoreNuked").getValBoolean())
						return;
				float[] rots = getrots(target);
				float sens = getSensitivityMultiplier();
				float yaw = rots[0] + MathUtil.getRandomInRange(1, -3);
				float pitch = rots[1] + MathUtil.getRandomInRange(1, -3);
				float yawGCD = (Math.round(yaw / sens) * sens);
				float pitchGCD = (Math.round(pitch / sens) * sens);
				event.setYaw(yawGCD);
				if (Zamorozka.settingsManager.getSettingByName("PitchRotations").getValBoolean()) {
					event.setPitch(pitchGCD);
				}
			}
		}

		if (Zamorozka.settingsManager.getSettingByName("AutoPressShield").getValBoolean()) {
			if ((target == null) || !(Minecraft.player.getDistanceToEntity(target) <= Zamorozka.settingsManager.getSettingByName("AttackRange").getValDouble())) {
				blockByShield(false);
				return;
			}
			if (Minecraft.player.getCooledAttackStrength(0) <= 0.1) {
				blockByShield(true);
			}
			if (Minecraft.player.getCooledAttackStrength(0) >= 0.75) {
				blockByShield(false);
			}
		}

		if (Zamorozka.settingsManager.getSettingByName("AutoBreakingShield").getValBoolean()) {
			if (target == null)
				return;
			if (target.isBlocking()) {
				mc.player.inventory.currentItem = EntityUtil.getAxeAtHotbar();
				boolean look = RotationUtilis.isLookingAtEntity1(target);
				if (look) {
					EntityUtil.attackEntity(target, true, true);
				}
			} else {
				if (target.getHeldItemOffhand().getItem() instanceof ItemShield) {
					mc.player.inventory.currentItem = InventoryUtil.getSwordAtHotbar();
				}
			}
		}
	}

	@EventTarget
	public void onMove(EventMove e) {
		if (target == null)
			return;
		if (!getState())
			return;
		String mod = Zamorozka.settingsManager.getSettingByName("Rotation Mode").getValString();
		if (mod.equalsIgnoreCase("Legit") || mod.equalsIgnoreCase("LegitSnap"))
			return;
		if (mc.gameSettings.keyBindBack.isKeyDown())
			return;
		if (Zamorozka.settingsManager.getSettingByName("RotationStrafe").getValBoolean()) {
			if (!mc.gameSettings.keyBindJump.isKeyDown() && mc.player.onGround) {
				MovementUtilis.setSpeedAt(e, (float) (Zamorozka.fakeYaw + MovementUtilis.getPressedMoveDir() - this.mc.player.rotationYaw), MovementUtilis.getMoveSpeed(e));
				/*
				 * mc.player.motionX *= 0.6; mc.player.motionZ *= 0.6;
				 */
			}
		}
	}

	@EventTarget
	public void onFps(RenderGameTick event) {
		// Rotations by Maximus_
		if (target == null)
			return;
		if (!(Minecraft.player.getHeldItemMainhand().getItem() instanceof ItemSword || Minecraft.player.getHeldItemMainhand().getItem() instanceof ItemAxe) && Zamorozka.settingsManager.getSettingByName("Sword/Axe only").getValBoolean())
			return;

		for (ItemStack stack : target.getArmorInventoryList())
			if ((stack.func_190926_b()) && Zamorozka.settingsManager.getSettingByName("IgnoreNuked").getValBoolean())
				return;
		String mode = Zamorozka.settingsManager.getSettingByName("Rotation mode").getValString();
		float llcl = (float) Zamorozka.settingsManager.getSettingByName("LegitSnapCooldown").getValDouble();
		if (Minecraft.player.getCooledAttackStrength(0) <= llcl && mode.equalsIgnoreCase("LegitSnap"))
			return;
		Random rnd = new Random();
		float rotRand = (float) Zamorozka.settingsManager.getSettingByName("RotationRandom").getValDouble();
		float rotSpeed = (float) Zamorozka.settingsManager.getSettingByName("RotationSpeed").getValDouble();
		float fov = (float) Zamorozka.settingsManager.getSettingByName("FOV").getValDouble();
		RotationUtils2.Rotation rotation = RotationUtils2.getNeededRotations(target.getEntityBoundingBox().getCenter());
		if (abs(to180(Minecraft.player.rotationYaw - rotation.getYaw())) > fov)
			return;
		if (target.getHealth() > 0) {
			float deltaYaw = (to180(Minecraft.player.rotationYaw - rotation.getYaw()) + (rnd.nextFloat() * rotRand) - rotRand / 2F) * (rotSpeed / 10);
			float deltaPitch = (Minecraft.player.rotationPitch - rotation.getPitch() + (rnd.nextFloat() * rotRand - rotRand / 2F)) * (rotSpeed / 10);
			float f = getSensitivityMultiplier();
			if (mode.equalsIgnoreCase("Legit") || mode.equalsIgnoreCase("LegitSnap")) {
				Minecraft.player.rotationYaw -= (int) (deltaYaw / f) * f;
				Minecraft.player.rotationPitch -= (int) (deltaPitch / f) * f;
			}
		}
	}

	private EntityLivingBase getClosest(double range) {
		EntityLivingBase target = null;
		for (Object object : mc.world.loadedEntityList) {
			Entity entity = (Entity) object;
			if (entity instanceof EntityLivingBase) {
				EntityLivingBase player = (EntityLivingBase) entity;
				if (canAttack(player)) {

					target = player;

				}
			}
		}
		return target;
	}

	@EventTarget
	public void onChat(EventChatMessage event) {
		if (Zamorozka.settingsManager.getSettingByName("AutoWinDisable").getValBoolean()) {
			if (event.getMessage().contains("Победитель") || event.getMessage().contains("Win")) {
				ModuleManager.getModule(KillAura.class).setState(false);
				isEnabled = false;
				NotificationPublisher.queue("Module", "KillAura was Disabled because of Win!", NotificationType.INFO);
			}
		}
	}

	public static boolean canAttack(EntityLivingBase player) {
		if (player == null)
			return false;

		if (Zamorozka.settingsManager.getSettingByName("Friends").getValBoolean() && indexer.getFriends().isFriend(player.getName())) {
			return false;
		}
		if (player instanceof EntityPlayer && !Zamorozka.settingsManager.getSettingByName("Players").getValBoolean())
			return false;
		if (player instanceof EntityAnimal && !Zamorozka.settingsManager.getSettingByName("Animals").getValBoolean())
			return false;
		if (player instanceof EntityMob && !Zamorozka.settingsManager.getSettingByName("Monsters").getValBoolean())
			return false;
		if (player instanceof EntityVillager && !Zamorozka.settingsManager.getSettingByName("Villagers").getValBoolean())
			return false;
		if (player instanceof EntityArmorStand && !Zamorozka.settingsManager.getSettingByName("HitArmorStand").getValBoolean())
			return false;

		/*
		 * if (ModuleManager.getModule(AntiBot2.class).getState() &&
		 * Zamorozka.settingsManager.getSettingByName("MatrixMixCheck").getValBoolean()
		 * && AntiBot2.bots.contains(player)) { return false; }
		 */

		if (ModuleManager.getModule(RadiusAntiBot.class).getState() && !RadiusAntiBot.notAlwaysInRadius.contains(player)) {
			return false;
		}

		if (ModuleManager.getModule(AntiBot2.class).getState() && Zamorozka.settingsManager.getSettingByName("HitBefore").getValBoolean() && !AntiBot2.nobotsTimolia.contains(player)) {
			return false;
		}

		if (AntiBot2.getInvalid().contains(player) || player.isPlayerSleeping())
			return false;
		if (player.isOnSameTeam(Minecraft.player) && Zamorozka.settingsManager.getSettingByName("NoHitTeams").getValBoolean())
			return false;
		if (player.isInvisible() && !Zamorozka.settingsManager.getSettingByName("AttackInvisibles").getValBoolean())
			return false;
		if (!isInFOV(player, Zamorozka.settingsManager.getSettingByName("FOV").getValDouble()))
			return false;
		if (!range(player, Zamorozka.settingsManager.getSettingByName("AttackRange").getValDouble()))
			return false;
		if (!(player.ticksExisted > Zamorozka.settingsManager.getSettingByName("TicksExisted").getValDouble()))
			return false;
		if (!behindWall(player) && !Zamorozka.settingsManager.getSettingByName("ThoughWalls").getValBoolean())
			return false;

		return player != Minecraft.player;
	}

	private static boolean range(EntityLivingBase entity, double range) {
		return Minecraft.player.getDistanceToEntity(entity) <= range;
	}

	private static boolean behindWall(EntityLivingBase entity) {
		return Minecraft.player.canEntityBeSeen(entity);
	}

	float to180(float ang) {
		float value = ang % 360.0f;

		if (value >= 180.0f) {
			value -= 360.0f;
		}

		if (value < -180.0f) {
			value += 360.0f;
		}

		return value;
	}

	private static boolean isInFOV(EntityLivingBase entity, double angle) {
		angle *= .5D;
		double angleDiff = getAngleDifference(Minecraft.player.rotationYaw, getRotations(entity.posX, entity.posY, entity.posZ)[0]);
		return (angleDiff > 0 && angleDiff < angle) || (-angle < angleDiff && angleDiff < 0);
	}

	private static float getAngleDifference(float dir, float yaw) {
		float f = Math.abs(yaw - dir) % 360F;
		return f > 180F ? 360F - f : f;
	}

	private static float[] getRotations(double x, double y, double z) {
		double diffX = x + .5D - Minecraft.player.posX;
		double diffY = (y + .5D) / 2D - (Minecraft.player.posY + Minecraft.player.getEyeHeight());
		double diffZ = z + .5D - Minecraft.player.posZ;

		double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
		float yaw = (float) (Math.atan2(diffZ, diffX) * 180D / Math.PI) - 90F;
		float pitch = (float) -(Math.atan2(diffY, dist) * 180D / Math.PI);

		return new float[] { yaw, pitch };
	}

	@EventTarget
	public void onRender3D1(RenderEvent3D render) {
		if (ModuleManager.getModule(KillAura.class).getState() && target != null) {
			for (ItemStack stack : target.getArmorInventoryList())
				if ((stack.func_190926_b()) && Zamorozka.settingsManager.getSettingByName("IgnoreNuked").getValBoolean())
					return;

			if (canAttack(target) && Zamorozka.settingsManager.getSettingByName("HeadESP").getValBoolean() && target.getHealth() > 0 && ModuleManager.getModule(KillAura.class).getState()) {
				GL11.glPushMatrix();
				int color = target.hurtResistantTime > 15 ? Colors.getColor(new Color(255, 70, 70, 80)) : Colors.getColor(new Color(255, 255, 255, 80));
				{
					double x = target.lastTickPosX + (target.posX - target.lastTickPosX) * mc.timer.renderPartialTicks - RenderManager.renderPosX;
					double y = target.lastTickPosY + (target.posY - target.lastTickPosY) * mc.timer.renderPartialTicks - RenderManager.renderPosY;
					double z = target.lastTickPosZ + (target.posZ - target.lastTickPosZ) * mc.timer.renderPartialTicks - RenderManager.renderPosZ;
					x -= 0.5D;
					z -= 0.5D;
					y += target.getEyeHeight() + 0.35D - (target.isSneaking() ? 0.25D : 0.0D);
					double mid = 0.5D;
					GL11.glEnable(3042);
					GL11.glBlendFunc(770, 771);
					GL11.glTranslated(x + mid, y + mid, z + mid);
					GL11.glRotated((-target.rotationYaw % 360.0F), 0.0D, 1.0D, 0.0D);
					GL11.glTranslated(-(x + mid), -(y + mid), -(z + mid));
					GL11.glDisable(3553);
					GL11.glEnable(2848);
					GL11.glDisable(2929);
					GL11.glDepthMask(false);
					// пофиксить рендер баг через (Glstatemanager.enableBlend());
					RenderingUtils.glColor(color);
					RenderingUtils.drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 0.05D, z + 1.0D));
					GL11.glDisable(2848);
					GL11.glEnable(3553);
					GL11.glEnable(2929);
					GL11.glDepthMask(true);
					GL11.glDisable(3042);
					GL11.glPopMatrix();
				}
			}

			if (Zamorozka.settingsManager.getSettingByName("TargetESP").getValBoolean() && target != null && !nullCheck()) {
				RenderUtils.drawEntityESP5(target, Zamorozka.getClientColors());
			}

			String colorm = Zamorozka.settingsManager.getSettingByName("CircleColor Mode").getValString();
			int clr = -1;
			float wid = (float) Zamorozka.settingsManager.getSettingByName("CircleWidth").getValDouble();
			if (colorm.equalsIgnoreCase("White")) {
				clr = -1;
			} else if (colorm.equalsIgnoreCase("ClientColor")) {
				clr = Zamorozka.getClientColor();
			}
			if (Zamorozka.settingsManager.getSettingByName("RenderOwnCircle").getValBoolean()) {
				double dl = ModuleManager.getModule(TargetStrafe.class).getState() ? Zamorozka.settingsManager.getSettingByName("TargetRange").getValDouble() : 1;
				RenderingUtils.drawLinesAroundPlayer(mc.player, dl, render.getRenderPartialTicks(), (int) Zamorozka.settingsManager.getSettingByName("CirclePoint").getValDouble(), wid, clr);
			}
			String md = Zamorozka.settingsManager.getSettingByName("Circle Mode").getValString();
			if (Zamorozka.settingsManager.getSettingByName("RenderCircle").getValBoolean()) {
				if (md.equalsIgnoreCase("Custom")) {
					double dl = ModuleManager.getModule(TargetStrafe.class).getState() ? Zamorozka.settingsManager.getSettingByName("TargetRange").getValDouble() : 1;
					RenderingUtils.drawLinesAroundPlayer(target, dl, render.getRenderPartialTicks(), (int) Zamorozka.settingsManager.getSettingByName("CirclePoint").getValDouble(), wid, clr);
				} else if (md.equalsIgnoreCase("Jello")) {
					onRend();
				} else if (md.equalsIgnoreCase("CustomWithJello")) {
					double dl = ModuleManager.getModule(TargetStrafe.class).getState() ? Zamorozka.settingsManager.getSettingByName("TargetRange").getValDouble() : 1;
					RenderingUtils.drawLinesAroundPlayer(target, dl, render.getRenderPartialTicks(), (int) Zamorozka.settingsManager.getSettingByName("CirclePoint").getValDouble(), wid, clr);
					onRend();
				}
			}
		}
	}

	public void onRend() {
		if (Zamorozka.settingsManager.getSettingByName("RenderCircle").getValBoolean()) {
			for (ItemStack stack : target.getArmorInventoryList())
				if ((stack.func_190926_b()) && Zamorozka.settingsManager.getSettingByName("IgnoreNuked").getValBoolean())
					return;
			if (canAttack(target) && target.getHealth() > 0 && Minecraft.player.getDistanceToEntity(target) <= (float) Zamorozka.settingsManager.getSettingByName("AttackRange").getValDouble() && !target.isDead) {
				if (!Zamorozka.settingsManager.getSettingByName("ThoughWalls").getValBoolean() && !Minecraft.player.canEntityBeSeen(target))
					return;
				time += .01 * (AnimationUtil.deltaTime * .1);
				double height = 0.8 * (1 + Math.sin(2 * Math.PI * (time * .3)));
				if (height > 0.995) {
					anim = true;
				} else if (height < 0.05) {
					anim = false;
				}

				final double x = target.lastTickPosX + (target.posX - target.lastTickPosX) * mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosX;
				final double y = target.lastTickPosY + (target.posY - target.lastTickPosY) * mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosY;
				final double z = target.lastTickPosZ + (target.posZ - target.lastTickPosZ) * mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosZ;

				GlStateManager.enableBlend();
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glEnable(GL11.GL_LINE_SMOOTH);
				GlStateManager.disableDepth();
				GlStateManager.disableTexture2D();
				GlStateManager.disableAlpha();
				GL11.glLineWidth(0.8F);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				GL11.glDisable(GL11.GL_CULL_FACE);
				final double size = target.width * 1.2;
				if (test <= 10) {
					if (anim) {
						animtest += 0.01F;
					} else {
						animtest -= 0.01F;
					}
					test = 10;
				}
				test--;
				double gg = mc.player.onGround ? 0.35 : 0.65;
				double y2 = 0;
				y2 += target.getEyeHeight() - (target.isSneaking() ? 0.25D : 0.0D);
				if (animtest <= y) {
					anim = true;
				} else if (animtest >= y + y2 + gg) {
					anim = false;
				}
				GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
				{
					for (int j = 0; j < 361; j++) {
						RenderUtils2.color(setAlpha(Zamorozka.getClientColors(), (int) (255 * (1 - height))));
						double x1 = x + Math.cos(Math.toRadians(j)) * size;
						double z1 = z - Math.sin(Math.toRadians(j)) * size;
						GL11.glVertex3d(x1, y + animtest, z1);
						RenderUtils2.color(setAlpha(Zamorozka.getClientColors(), 0));
						GL11.glVertex3d(x1, y + animtest + (.5 * height), z1);
					}
				}
				GL11.glEnd();
				GL11.glBegin(GL11.GL_LINE_LOOP);
				{
					for (int j = 0; j < 361; j++) {
						RenderUtils2.color(Zamorozka.getClientColors());
						GL11.glVertex3d(x + Math.cos(Math.toRadians(j)) * size, y + animtest, z - Math.sin(Math.toRadians(j)) * size);
					}
				}
				GL11.glEnd();
				GlStateManager.enableAlpha();
				GL11.glShadeModel(GL11.GL_FLAT);
				GL11.glDisable(GL11.GL_LINE_SMOOTH);
				GL11.glEnable(GL11.GL_CULL_FACE);
				GlStateManager.enableTexture2D();
				GlStateManager.enableDepth();
				GlStateManager.disableBlend();
				GlStateManager.resetColor();
			}
		}
	}

	@EventTarget
	public void onRenderGui(EventRender2D event) {
		renderTargetHud(event);
	}

	private void renderTargetHud(EventRender2D event) {
		if (!Zamorozka.settingsManager.getSettingByName("TargetHUD").getValBoolean() || target == null)
			return;

		for (ItemStack stack : target.getArmorInventoryList())
			if (stack.func_190926_b() && Zamorozka.settingsManager.getSettingByName("IgnoreNuked").getValBoolean())
				return;

		switch (Zamorozka.settingsManager.getSettingByName("TargetHUD Mode").getValString().toLowerCase()) {
		case "novolineold":
			renderDevHud();
			break;
		case "smertnix":
			renderSmertnixHud(event.getResolution());
			break;
		case "shalopay":
			renderShalopayHud(event.getResolution());
			break;
		case "exhidev":
			renderExhiHud();
			break;
		case "hvh":
			renderHVHHud(event.getResolution());
			break;
		}
	}

	private void renderHVHHud(ScaledResolution res) {
		GlStateManager.pushMatrix();
		if (target.getHealth() > 0 && canAttack(target)) {
			float ping = EntityUtil.getPing(mc.player);
			float ping2 = EntityUtil.getPing(target);
			float scaledWidth = res.getScaledWidth();
			float scaledHeight = res.getScaledHeight();

			float x = (float) (scaledWidth / 2.0F - Zamorozka.settingsManager.getSettingByName("TargetHUDPosX").getValDouble()), y = (float) (scaledHeight / 2.0F + Zamorozka.settingsManager.getSettingByName("TargetHUDPosY").getValDouble());
			// player h
			if (mc.player != target) {
				double hpPercentage = (mc.player.getHealth() / mc.player.getMaxHealth());
				hpPercentage = MathHelper.clamp(hpPercentage, 0.0D, 1.0D);
				double hpWidth = 92.0D * hpPercentage;
				int healthColor = Colors.getHealthColor(mc.player.getHealth(), mc.player.getMaxHealth()).getRGB();
				String healthStr = "HP: " + (int) mc.player.getHealth() / 2.0F;
				float health1 = mc.player.getHealth();
				double hpPercentage1 = (mc.player.getHealth() / mc.player.getMaxHealth());
				hpPercentage1 = MathHelper.clamp(hpPercentage, 0.0D, 1.0D);
				double hpWidth1 = 92.0D * hpPercentage1;
				int healthColor1 = Colors.getHealthColor(mc.player.getHealth(), mc.player.getMaxHealth()).getRGB();
				String healthStr1 = "HP: " + (int) mc.player.getHealth() / 2.0F;
				this.healthBarWidth = AnimationUtil.animate(hpWidth, this.healthBarWidth, 0.5029999852180481D);
				this.healthBarWidth1 = AnimationUtil.animate(hpWidth1, this.healthBarWidth1, 0.5029999852180481D);
				GL11.glEnable(3089);
				RenderUtils.prepareScissorBox(x - 55.0F, y - 150.0F, x + 243.0F, y + 100.0F);
				RenderingUtils.drawRoundedRect((x - 55F), (y - 4.0F), (x + 241.0F), (y + 66.0F), Colors.getTeamColor(mc.player), Colors.getTeamColor(mc.player));
				RenderUtils.drawRect((x - 54.0F), (y - 3.0F), (x + 240.0F), (y + 65.0F), COLOR.getRGB());
				RenderingUtils.drawRoundedRect((x - 10.0F), (y + 15.0F), (x - 10.0F) + this.healthBarWidth1, (y + 25.0F), healthColor1, healthColor1);
				// Zamorozka.FONT_MANAGER.arraylist6.drawStringWithShadow(healthStr, x - 11, y +
				// 15, -1);
				// Zamorozka.FONT_MANAGER.arraylist5.drawStringWithShadow("Ping: " + (int) ping
				// + "ms", x - 50, y + 40, new Color(255, 255, 255, 255).getRGB());
				// Zamorozka.FONT_MANAGER.arraylist5.drawStringWithShadow("Ping: " + (int) ping2
				// + "ms", x + 190, y + 40, new Color(255, 255, 255, 255).getRGB());
				Zamorozka.FONT_MANAGER.arraylist5.drawStringWithShadow("(YOU)", x - 11, y + 5, -1);
				// RenderingUtils.drawRoundedRect((int) x - (int) 50.0, (int) y, 38, 38, -1);
				// RotationUtils2.drawEntityOnScreen(x + 230, y + 63, 13,
				// this.target.rotationYaw, this.target.rotationPitch, this.target);
				if (target instanceof EntityPlayer) {
					Zamorozka.FONT_MANAGER.arraylist3.drawStringWithShadow("VS", x + 83.0F, y + 43.0F, -1);
					RenderUtils2.drawArrow(x + 90.0F, y + 18.0F, -1);
				}
				final ArrayList<ItemStack> list = new ArrayList<ItemStack>();
				for (int i = 0; i < 5; ++i) {
					final ItemStack getEquipmentInSlot = mc.player.getEquipmentInSlot(i);
					if (getEquipmentInSlot != null) {
						list.add(getEquipmentInSlot);
					}
				}
				float x2 = (float) (scaledWidth / 2.0F - Zamorozka.settingsManager.getSettingByName("TargetHUDPosX").getValDouble()), y2 = (float) (scaledHeight / 2.0F + Zamorozka.settingsManager.getSettingByName("TargetHUDPosY").getValDouble());
				int n10 = -(list.size() * 9);
				for (final ItemStack itemStack : list) {
					RenderHelper.enableGUIStandardItemLighting();
					RenderingUtils.drawRoundedRect((int) x2 - (int) 40.0, (int) y2 + (int) 48.0, 14, 15, new Color(45, 45, 45, 255).getRGB());
					RenderingTools.renderItem(itemStack, (int) x2 - (int) 41.0, (int) y2 + (int) 47.0);
					x2 += 16;
				}
				final List var5 = GuiPlayerTabOverlay.ENTRY_ORDERING.sortedCopy(Minecraft.player.connection.getPlayerInfoMap());
				for (final Object aVar5 : var5) {
					final NetworkPlayerInfo var6 = (NetworkPlayerInfo) aVar5;
					if (mc.world.getPlayerEntityByUUID(var6.getGameProfile().getId()) == mc.player) {
						Minecraft.getTextureManager().bindTexture(var6.getLocationSkin());
						Gui.drawScaledCustomSizeModalRect(x - 50, y, 8.0f, 8.0f, 8, 8, 36, 36, 64.0f, 64.0f);
						if (((EntityPlayer) target).isWearing(EnumPlayerModelParts.HAT)) {
							// Gui.drawScaledCustomSizeModalRect(x - 50, y + 2, 40.0f, 8.0f, 8, 8, 32, 32,
							// 64.0f, 64.0f);
						}
						GlStateManager.bindTexture(0);
					}
					GL11.glDisable(3089);
				}
			}
			// target h
		}
		float scaledWidth = res.getScaledWidth();
		float scaledHeight = res.getScaledHeight();
		float x = (float) (scaledWidth / 2.0F - Zamorozka.settingsManager.getSettingByName("TargetHUDPosX").getValDouble()), y = (float) (scaledHeight / 2.0F + Zamorozka.settingsManager.getSettingByName("TargetHUDPosY").getValDouble());
		if (target.getHealth() > 0 && canAttack(target)) {
			float health = target.getHealth();
			double hpPercentage = (health / target.getMaxHealth());
			hpPercentage = MathHelper.clamp(hpPercentage, 0.0D, 1.0D);
			double hpWidth = 92.0D * hpPercentage;
			int healthColor = Colors.getHealthColor(target.getHealth(), target.getMaxHealth()).getRGB();
			String healthStr = "HP: " + (int) target.getHealth() / 2.0F;
			this.healthBarWidth = AnimationUtil.animate(hpWidth, this.healthBarWidth, 1.2029999852180481D);
			this.hudHeight = AnimationUtil.animate(40.0D, this.hudHeight, 0.10000000149011612D);
			GL11.glEnable(3089);
			RenderingUtils.drawRoundedRect((x + 105.0F), (y + 15.0F), (x + 105.0F) + this.healthBarWidth, (y + 25F), healthColor, healthColor);
			Zamorozka.FONT_MANAGER.arraylist5.drawStringWithShadow("(TARGET)", x + 160, y + 5, -1);
			final ArrayList<ItemStack> list1 = new ArrayList<ItemStack>();
			for (int i = 4; i < 5; ++i) {
				final ItemStack getEquipmentInSlot = ((EntityPlayer) target).getEquipmentInSlot(i);
				if (getEquipmentInSlot != null) {
					list1.add(getEquipmentInSlot);
				}
			}
			for (final ItemStack itemStack : list1) {
				RenderHelper.enableGUIStandardItemLighting();
				mc.getRenderItem().renderItemIntoGUI(itemStack, (int) x + (int) 147.0, (int) y + (int) 47.0);
				if (Zamorozka.settingsManager.getSettingByName("ItemsDur").getValBoolean()) {
					mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, itemStack, (int) x + (int) 147.0, (int) y + (int) 47.0);
				}
			}
			final ArrayList<ItemStack> list = new ArrayList<ItemStack>();
			for (int i = 0; i < 5; ++i) {
				final ItemStack getEquipmentInSlot = ((EntityPlayer) target).getEquipmentInSlot(i);
				if (getEquipmentInSlot != null || EntityEquipmentSlot.Type.HAND != null) {
					list.add(getEquipmentInSlot);
				}
			}
			float x2 = (float) (scaledWidth / 2.0F - Zamorozka.settingsManager.getSettingByName("TargetHUDPosX").getValDouble()), y2 = (float) (scaledHeight / 2.0F + Zamorozka.settingsManager.getSettingByName("TargetHUDPosY").getValDouble());
			int n10 = -(list.size() * 9);
			for (final ItemStack itemStack : list) {
				RenderHelper.enableGUIStandardItemLighting();
				RenderingUtils.drawRoundedRect((int) x2 + (int) 147.0, (int) y2 + (int) 48.0, 14, 15, new Color(45, 45, 45, 255).getRGB());
				RenderingTools.renderItem(itemStack, (int) x2 + (int) 147.0, (int) y2 + (int) 47.0);
				x2 += 16;
			}
			final List var5 = GuiPlayerTabOverlay.ENTRY_ORDERING.sortedCopy(Minecraft.player.connection.getPlayerInfoMap());
			for (final Object aVar5 : var5) {
				final NetworkPlayerInfo var6 = (NetworkPlayerInfo) aVar5;
				if (mc.world.getPlayerEntityByUUID(var6.getGameProfile().getId()) == target) {
					Minecraft.getTextureManager().bindTexture(var6.getLocationSkin());
					Gui.drawScaledCustomSizeModalRect(x + 200, y, 8.0f, 8.0f, 8, 8, 36, 36, 64.0f, 64.0f);
					if (((EntityPlayer) target).isWearing(EnumPlayerModelParts.HAT)) {
						// Gui.drawScaledCustomSizeModalRect(x + 2, y + 2, 40.0f, 8.0f, 8, 8, 32, 32,
						// 64.0f, 64.0f);
					}
					GlStateManager.bindTexture(0);
				}
				GL11.glDisable(3089);
			}
		}
		GlStateManager.popMatrix();
	}

	private void renderExhiHud() {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

		final float scaledWidth = sr.getScaledWidth();
		final float scaledHeight = sr.getScaledHeight();
		if (KillAura.target != null && ModuleManager.getModule(KillAura.class).getState()) {
			if (KillAura.target instanceof EntityOtherPlayerMP) {
				float startX = 20;
				float renderX = (sr.getScaledWidth() / 2) + startX;
				float renderY = (sr.getScaledHeight() / 2) + 10;
				int maxX2 = 30;
				if (((EntityPlayer) target).getEquipmentInSlot(3) != null) {
					maxX2 += 15;
				}
				if (((EntityPlayer) target).getEquipmentInSlot(2) != null) {
					maxX2 += 15;
				}
				if (((EntityPlayer) target).getEquipmentInSlot(1) != null) {
					maxX2 += 15;
				}
				if (((EntityPlayer) target).getEquipmentInSlot(0) != null) {
					maxX2 += 15;
				}
				final float width = 140.0f;
				final float height = 40.0f;
				final float xOffset = 40.0f;
				float x = (float) (scaledWidth / 2.0F - Zamorozka.settingsManager.getSettingByName("TargetHUDPosX").getValDouble()), y = (float) (scaledHeight / 2.0F + Zamorozka.settingsManager.getSettingByName("TargetHUDPosY").getValDouble());
				final float health = this.target.getHealth();
				double hpPercentage = health / this.target.getMaxHealth();
				hpPercentage = MathHelper.clamp_double(hpPercentage, 0.0, 1.0);
				final double hpWidth = 60.0 * hpPercentage;
				final int healthColor = ColorUtilities.getHealthColor(this.target.getHealth(), this.target.getMaxHealth()).getRGB();
				final String healthStr = String.valueOf((int) this.target.getHealth() / 1.0f);
				int xAdd = 0;
				double multiplier = 0.85;
				GlStateManager.pushMatrix();
				GlStateManager.scale(multiplier, multiplier, multiplier);

				GlStateManager.popMatrix();
				this.healthBarWidth = AnimationUtil.animate(hpWidth, this.healthBarWidth, 0.1);
				// RenderingUtils.drawGradientRect(x - 3.5, y - 3.5, x + 105.5f, y + 42.4f, new
				// Color(10, 10, 10, 255).getRGB(), new Color(10, 10, 10, 255).getRGB());
				// RenderUtils.prepareScissorBox(x, y, x + 140.0f, (float) (y +
				// this.hudHeight));
				RenderingUtils.drawGradientRect(x - 3, y - 3.2, x + 104.8f, y + 41.8f, new Color(40, 40, 40, 255).getRGB(), Zamorozka.getClientColor());
				// RenderingUtils.drawGradientRect(x - 1.4, y - 1.5, x + 103.5f, y + 40.5f, new
				// Color(74, 74, 74, 255).getRGB(), new Color(74, 74, 74, 255).getRGB());
				RenderingUtils.drawGradientRect(x - 1, y - 1, x + 103.0f, y + 40.0f, new Color(32, 32, 32, 255).getRGB(), new Color(10, 10, 10, 255).getRGB());
				RenderingUtils.drawRect(x + 25.0f, y + 11.0f, x + 87f, y + 14.29f, new Color(105, 105, 105, 40).getRGB());
				// RenderingUtils.drawRect(x + 25.0f, y + 11.0f, x + 30f + this.healthBarWidth,
				// y + 13.5f, RenderUtils.getColorFromPercentage(this.target.getHealth(),
				// this.target.getMaxHealth()));
				RenderingUtils.drawGradientRect(x + 25.0f, y + 11.0f, x + 30f + this.healthBarWidth, y + 15.5f, new Color(40, 40, 40, 255).getRGB(), Zamorozka.getClientColor());
				float ping = EntityUtil.getPing(target);
				String targetName = target.getName();
				if (ModuleManager.getModule(YoutuberMode.class).getState() && Zamorozka.settingsManager.getSettingByName("SpoofNames").getValBoolean()) {
					String newstr = "";
					char[] rdm = { 'l', 'i', 'j', '\'', ';', ':', '|' };
					for (int k = 0; k < targetName.length(); k++) {
						char ch = rdm[MiscUtils.randomNumber(rdm.length - 1, 0)];
						newstr = newstr.concat(ch + "");
					}
					targetName = newstr;
				}
				Fonts.comfortaa18.drawStringWithShadow(targetName, x + 24.8f, y + 1.9f, new Color(255, 255, 255).getRGB());
				/*
				 * mc.fontRendererObj.drawStringWithShadow(ChatFormatting.RED + "\u2764", x +
				 * 68.0f, y + 10.8f, new Color(50, 50, 50).getRGB());
				 * Fonts.comfortaa16.drawStringWithShadow(healthStr, x + 50.0f, y + 12.2f, -1);
				 */
				Fonts.comfortaa12.drawString("l   " + "l   " + "l   " + "l   " + "l   " + "l   " + "l   " + "l   ", x + 30.0f, y + 11.5f, new Color(50, 50, 50).getRGB());
				Fonts.comfortaa12.drawStringWithShadow("HP: " + healthStr + ", ", x - 12.5f + 44.0f - Fonts.comfortaa18.getStringWidth(healthStr) / 2.0f, y + 30.0f, -1);
				Fonts.comfortaa12.drawStringWithShadow(target.onGround ? " Ground: Yes" : " Ground: No", x + Fonts.comfortaa12.getStringWidth(healthStr) + 48 - Fonts.comfortaa18.getStringWidth(healthStr) / 2.0f, y + 30.0f, -1);
				Fonts.default10.drawStringWithShadow(ping + "ms", (x + 2f), (int) (y + 2.0f), -1);
				GuiInventory.drawEntityOnScreen((int) (x + 12f), (int) (y + 38.0f), 15, this.target.rotationYaw, this.target.rotationPitch, this.target);
			}
		} else {
			this.healthBarWidth = 60.0;
			this.hudHeight = 0.0;
			this.target = null;
		}
	}

	private void renderDevHud() {
		if (canAttack(target) && target.getHealth() > 0 && Minecraft.player.getDistanceToEntity(target) <= Zamorozka.settingsManager.getSettingByName("AttackRange").getValDouble() && ModuleManager.getModule(KillAura.class).getState()) {
			if (target == null)
				return;
			ScaledResolution sr = new ScaledResolution(mc);
			String name = StringUtils.stripControlCodes(target.getName());
			float startX = 20;
			float renderX = (sr.getScaledWidth() / 2F) + startX;
			float renderY = (sr.getScaledHeight() / 2F) + 10;
			int maxX2 = 30;
			float healthPercentage = target.getHealth() / target.getMaxHealth();
			if (target instanceof EntityPlayer) {
				if (((EntityPlayer) target).getEquipmentInSlot(3) != null) {
					maxX2 += 15;
				}
				if (((EntityPlayer) target).getEquipmentInSlot(2) != null) {
					maxX2 += 15;
				}
				if (((EntityPlayer) target).getEquipmentInSlot(1) != null) {
					maxX2 += 15;
				}
				if (((EntityPlayer) target).getEquipmentInSlot(0) != null) {
					maxX2 += 15;
				}
			}

			if (target.getHeldItemMainhand() != null) {
				maxX2 += 15;
			}

			float maxX = Math.max(maxX2, Fonts.elliot17.getStringWidth(name) + 30);
			Gui.drawRect(renderX, renderY, renderX + maxX, renderY + 40, new Color(0, 0, 0, 0.6f).getRGB());
			Gui.drawRect(renderX, renderY + 38, renderX + (maxX * healthPercentage), renderY + 40, RenderingUtils.getHealthColor(target));
			// RenderUtils.drawRect((renderX), (renderY), renderX + (maxX *
			// healthPercentage), (renderY + 1.5F), RenderingUtils.getHealthColor(target));
			Fonts.elliot17.drawStringWithShadow(name, renderX + 25, renderY + 7, -1);
			int xAdd = 0;
			double multiplier = 0.85;
			GlStateManager.pushMatrix();
			GlStateManager.scale(multiplier, multiplier, multiplier);
			if (target instanceof EntityPlayer) {
				if (((EntityPlayer) target).getEquipmentInSlot(4) != null) {
					mc.getRenderItem().renderItemAndEffectIntoGUI(((EntityPlayer) target).getEquipmentInSlot(4), (int) ((((sr.getScaledWidth() / 2) + startX + 23) + xAdd) / multiplier), (int) (((sr.getScaledHeight() / 2) + 28) / multiplier));
					xAdd += 15;
				}
				if (((EntityPlayer) target).getEquipmentInSlot(3) != null) {
					mc.getRenderItem().renderItemAndEffectIntoGUI(((EntityPlayer) target).getEquipmentInSlot(3), (int) ((((sr.getScaledWidth() / 2) + startX + 23) + xAdd) / multiplier), (int) (((sr.getScaledHeight() / 2) + 28) / multiplier));
					xAdd += 15;
				}
				if (((EntityPlayer) target).getEquipmentInSlot(2) != null) {
					mc.getRenderItem().renderItemAndEffectIntoGUI(((EntityPlayer) target).getEquipmentInSlot(2), (int) ((((sr.getScaledWidth() / 2) + startX + 23) + xAdd) / multiplier), (int) (((sr.getScaledHeight() / 2) + 28) / multiplier));
					xAdd += 15;
				}
				if (((EntityPlayer) target).getEquipmentInSlot(1) != null) {
					mc.getRenderItem().renderItemAndEffectIntoGUI(((EntityPlayer) target).getEquipmentInSlot(1), (int) ((((sr.getScaledWidth() / 2) + startX + 23) + xAdd) / multiplier), (int) (((sr.getScaledHeight() / 2) + 28) / multiplier));
					xAdd += 15;
				}
				if (((EntityPlayer) target).getEquipmentInSlot(0) != null) {
					mc.getRenderItem().renderItemAndEffectIntoGUI(((EntityPlayer) target).getEquipmentInSlot(0), (int) ((((sr.getScaledWidth() / 2) + startX + 23) + xAdd) / multiplier), (int) (((sr.getScaledHeight() / 2) + 28) / multiplier));
				}
			}

			GlStateManager.popMatrix();
			GuiInventory.drawEntityOnScreen((int) renderX + 12, (int) renderY + 33, 15, target.rotationYaw, target.rotationPitch, target);
		}
	}

	private void renderSmertnixHud(ScaledResolution res) {
		if (target.getHealth() > 0 && canAttack(target)) {
			float ping = EntityUtil.getPing(target);
			float scaledWidth = res.getScaledWidth();
			float scaledHeight = res.getScaledHeight();
			float x = (float) (scaledWidth / 2.0F - Zamorozka.settingsManager.getSettingByName("TargetHUDPosX").getValDouble());
			float y = (float) (scaledHeight / 2.0F + Zamorozka.settingsManager.getSettingByName("TargetHUDPosY").getValDouble());

			float health = target.getHealth();
			double hpPercentage = (health / target.getMaxHealth());
			hpPercentage = MathHelper.clamp(hpPercentage, 0.0D, 1.0D);
			double hpWidth = 92.0D * hpPercentage;
			int healthColor = Colors.getHealthColor(target.getHealth(), target.getMaxHealth()).getRGB();
			String healthStr = "HP: " + (int) target.getHealth() / 2.0F;
			this.healthBarWidth = AnimationUtil.animate(hpWidth, this.healthBarWidth, 0.2029999852180481D);
			this.hudHeight = AnimationUtil.animate(40.0D, this.hudHeight, 0.10000000149011612D);
			GL11.glEnable(3089);
			RenderUtils.prepareScissorBox(x - 4.0F, y - 150.0F, x + 143.0F, y + 100.0F);
			RenderUtils.drawRect((x - 4.0F), (y - 4.0F), (x + 141.0F), (y + 66.0F), Colors.getTeamColor(target));
			RenderUtils.drawRect((x - 3.0F), (y - 3.0F), (x + 140.0F), (y + 65.0F), COLOR.getRGB());
			RenderUtils.drawRect((x + 40.0F), (y + 15.0F), (x + 40.0F) + this.healthBarWidth, (y + 25.0F), healthColor);
			/*
			 * RenderUtils.prepareScissorBox(x - 444.0F, y - 550.0F, x + 143.0F, y +
			 * 100.0F); RenderUtils.drawRect((x -554.0F), (y - 554.0F), (x + 541.0F), (y +
			 * 566.0F), Colors.getTeamColor(target)); RenderUtils.drawRect((x -553.0F), (y -
			 * 553.0F), (x + 540.0F), (y + 565.0F), COLOR.getRGB()); RenderUtils.drawRect((x
			 * + 540.0F), (y + 515.0F), (x + 450.0F) + this.healthBarWidth, (y + 25.0F),
			 * healthColor);
			 */
			RenderingUtils.drawItemTextures(target, (int) (x + 27.0), (int) (y + 25.0F));
			if (target.getHealth() > Minecraft.player.getHealth()) {
				Fonts.comfortaa18.drawStringWithShadow("Losing :(", x + 1.0F, y + 43.0F, new Color(250, 62, 59).getRGB());
			} else if (target.getHealth() < Minecraft.player.getHealth()) {
				Fonts.comfortaa18.drawStringWithShadow("Winning :)", x + 1.0F, y + 43.0F, new Color(42, 250, 48).getRGB());
			} else if (target.getHealth() == Minecraft.player.getHealth()) {
				Fonts.comfortaa18.drawStringWithShadow("EZZ", x + 1.0F, y + 43.0F, new Color(250, 215, 42).getRGB());
			}
			List<PotionEffect> potions = new ArrayList<>();
			float pY = -2;
			for (Object o : target.getActivePotionEffects())
				potions.add((PotionEffect) o);
			potions.sort(Comparator.comparingDouble(effect -> -mc.fontRendererObj.getStringWidth(I18n.format(Potion.getPotionById(CustomColors.getPotionId(effect.getEffectName())).getName()))));

			for (PotionEffect effect : potions) {
				Potion potion = Potion.getPotionById(CustomColors.getPotionId(effect.getEffectName()));
				String name = I18n.format(potion.getName());
				String PType = "";
				ScaledResolution sr = new ScaledResolution(mc);
				if (effect.getAmplifier() == 1) {
					name = name + " II";
				} else if (effect.getAmplifier() == 2) {
					name = name + " III";
				} else if (effect.getAmplifier() == 3) {
					name = name + " IV";
				}
				if ((effect.getDuration() < 600) && (effect.getDuration() > 300)) {
					PType = PType + ": " + Potion.getDurationString(effect);
				} else if (effect.getDuration() < 300) {
					PType = PType + ": " + Potion.getDurationString(effect);
				} else if (effect.getDuration() > 600) {
					PType = PType + ": " + Potion.getDurationString(effect);
				}
				Fonts.comfortaa20.drawStringWithShadow(name + "" + ChatFormatting.WHITE + PType + ChatFormatting.RESET, x - Fonts.comfortaa20.getStringWidth(name + PType) + 115, y - 15 + pY, potion.getLiquidColor());
				pY -= 13;
			}
			String name = target.getName();
			if (ModuleManager.getModule(YoutuberMode.class).getState() && Zamorozka.settingsManager.getSettingByName("SpoofNames").getValBoolean()) {
				String newstr = "";
				char[] rdm = { 'l', 'i', 'j', '\'', ';', ':', '|' };
				for (int k = 0; k < name.length(); k++) {
					char ch = rdm[MiscUtils.randomNumber(rdm.length - 1, 0)];
					newstr = newstr.concat(ch + "");
				}
				name = newstr;
			}
			Fonts.comfortaa18.drawStringWithShadow(" Dist: " + ClientUtils.round(Minecraft.player.getDistanceToEntity(target), 1) + "", x + Fonts.comfortaa18.getStringWidth("" + ping) + 32, y + 55, new Color(255, 255, 255, 255).getRGB());
			Fonts.comfortaa20.drawStringWithShadow(healthStr, x + 40.0F + 46.0F - mc.fontRendererObj.getStringWidth(healthStr) / 2.0F, y + 16.0F, -1);
			// Fonts.comfortaa20.drawStringWithShadow(healthStr, x + 40.0F + 46.0F -
			// mc.fontRendererObj.getStringWidth(healthStr) / 2.0F, y + 16.0F, -1);
			Fonts.comfortaa18.drawStringWithShadow(name, x + 40.0F, y + 2.0F, -1);
			Fonts.comfortaa18.drawStringWithShadow("Ping: " + (int) ping + "ms, ", x + 1, y + 55, new Color(255, 255, 255, 255).getRGB());
			if (target instanceof EntityPlayer) {
				for (ItemStack is : target.getArmorInventoryList()) {
					EntityPlayer entityPlayer = (EntityPlayer) KillAura.target;
					if (is.func_190926_b()) {
						if ((!entityPlayer.inventory.armorItemInSlot(2).getItem().equals(Items.DIAMOND_CHESTPLATE) && !entityPlayer.inventory.armorItemInSlot(2).getItem().equals(Items.DIAMOND_BOOTS)
								&& !entityPlayer.inventory.armorItemInSlot(2).getItem().equals(Items.DIAMOND_HELMET) && !entityPlayer.inventory.armorItemInSlot(2).getItem().equals(Items.DIAMOND_LEGGINGS))) {
							Fonts.comfortaa18.drawStringWithShadow("(Nuked Player)", x + 43.0F, y + 30.0F, -1);
						}
					}
				}
			}
			final List var5 = GuiPlayerTabOverlay.ENTRY_ORDERING.sortedCopy(Minecraft.player.connection.getPlayerInfoMap());
			for (final Object aVar5 : var5) {
				final NetworkPlayerInfo var6 = (NetworkPlayerInfo) aVar5;
				if (mc.world.getPlayerEntityByUUID(var6.getGameProfile().getId()) == target) {
					Minecraft.getTextureManager().bindTexture(var6.getLocationSkin());
					Gui.drawScaledCustomSizeModalRect(x + 1, y + 3, 8.0f, 8.0f, 8, 8, 36, 36, 64.0f, 64.0f);
					if (((EntityPlayer) target).isWearing(EnumPlayerModelParts.HAT)) {
						Gui.drawScaledCustomSizeModalRect(x + 2, y + 2, 40.0f, 8.0f, 8, 8, 32, 32, 64.0f, 64.0f);
					}
					GlStateManager.bindTexture(0);
				}
				GL11.glDisable(3089);
			}
		} else {
			this.healthBarWidth = 92.0D;
			this.hudHeight = 0.0D;
			this.target = null;
			this.timer.reset();
		}
	}

	private void renderShalopayHud(ScaledResolution res) {
		if (target.getHealth() > 0 && canAttack(target)) {
			float ping = EntityUtil.getPing(target);
			float scaledWidth = res.getScaledWidth();
			float scaledHeight = res.getScaledHeight();
			float x = (float) (scaledWidth / 2.0F - Zamorozka.settingsManager.getSettingByName("TargetHUDPosX").getValDouble()), y = (float) (scaledHeight / 2.0F + Zamorozka.settingsManager.getSettingByName("TargetHUDPosY").getValDouble());
			float health = target.getHealth();
			double hpPercentage = (health / target.getMaxHealth());
			hpPercentage = MathHelper.clamp(hpPercentage, 0.0D, 1.0D);
			double hpWidth = 92.0D * hpPercentage;
			String healthStr = "Health: " + (int) target.getHealth() / 2.0F;
			this.healthBarWidth = AnimationUtil.calculateCompensation((float) hpWidth, (float) this.healthBarWidth, 1, (float) 0.4529999852180481D);
			this.hudHeight = AnimationUtil.calculateCompensation((float) 50.0D, (float) this.hudHeight, 0, (float) 0.20000000149011612D);
			GL11.glEnable(3089);
			RenderUtils.prepareScissorBox(x - 4.0F, y - 150.0F, x + 141.0F, y + 100.0F);
			RenderUtils.drawRect((x - 3.0F), (y - 3.0F), (x + 140.0F), (y + 45.0F), 0x66000000);
			RenderUtils.drawRect((x - 3), y + 43.0F, (x + 48.0F) + this.healthBarWidth, (y + 45.0F), Zamorozka.getClientColor());

			String name = target.getName();
			if (ModuleManager.getModule(YoutuberMode.class).getState() && Zamorozka.settingsManager.getSettingByName("SpoofNames").getValBoolean()) {
				String newstr = "";
				char[] rdm = { 'l', 'i', 'j', '\'', ';', ':', '|' };
				for (int k = 0; k < name.length(); k++) {
					char ch = rdm[MiscUtils.randomNumber(rdm.length - 1, 0)];
					newstr = newstr.concat(ch + "");
				}
				name = newstr;
			}
			Zamorozka.FONT_MANAGER.arraylist2.drawStringWithShadow(healthStr, x + 20.0F + 46.0F - mc.fontRendererObj.getStringWidth(healthStr) / 3.0F, y + 23.0F, -1);
			Zamorozka.FONT_MANAGER.arraylist2.drawStringWithShadow(name, x + 39.0F, y + 2.0F, -1);

			final List var5 = GuiPlayerTabOverlay.ENTRY_ORDERING.sortedCopy(Minecraft.player.connection.getPlayerInfoMap());
			for (final Object aVar5 : var5) {
				final NetworkPlayerInfo var6 = (NetworkPlayerInfo) aVar5;
				if (mc.world.getPlayerEntityByUUID(var6.getGameProfile().getId()) == target) {
					Minecraft.getTextureManager().bindTexture(var6.getLocationSkin());
					Gui.drawScaledCustomSizeModalRect(x + 1, y + 3, 8.0f, 8.0f, 8, 8, 36, 36, 64.0f, 64.0f);
					if (((EntityPlayer) target).isWearing(EnumPlayerModelParts.HAT)) {
						Gui.drawScaledCustomSizeModalRect(x + 2, y + 2, 40.0f, 8.0f, 8, 8, 32, 32, 64.0f, 64.0f);
					}
					GlStateManager.bindTexture(0);
				}
				GL11.glDisable(3089);
			}
		}
	}

	public void blockByShield(boolean state) {
		if (mc.player.getHeldItemOffhand().getItem() instanceof ItemShield) {
			mc.gameSettings.keyBindUseItem.pressed = state;
		}
	}

	@Override
	public void onEnable() {
		NotificationPublisher.queue("Module", "KillAura was Enabled!", NotificationType.INFO);
		super.onEnable();
	}

	@Override
	public void onDisable() {
		this.blockByShield(false);
		NotificationPublisher.queue("Module", "KillAura was Disabled!", NotificationType.INFO);
		super.onDisable();
	}
}