
package zamorozka.modules.COMBAT;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import de.Hero.clickgui.ClickGUI;
import de.Hero.settings.Setting;
import net.java.games.input.Keyboard;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventAttack;
import zamorozka.event.events.EventMove;
import zamorozka.event.events.EventPacket;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.EventRender3D;
import zamorozka.event.events.EventUpdate;
import zamorozka.event.events.RenderEvent3D;
import zamorozka.event.events.UpdateEvent;
import zamorozka.main.Zamorozka;
import zamorozka.main.indexer;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.TRAFFIC.SpeedHack;
import zamorozka.modules.ZAMOROZKA.LagCheck;
import zamorozka.notification.NotificationPublisher;
import zamorozka.notification.NotificationType;
import zamorozka.ui.AimUtil;
import zamorozka.ui.AngleUtil;
import zamorozka.ui.ClientUtils;
import zamorozka.ui.EntityValidator;
import zamorozka.ui.GLUtils;
import zamorozka.ui.MoveUtils;
import zamorozka.ui.MovementUtilis;
import zamorozka.ui.RotUtils;
import zamorozka.ui.RotationUtils;
import zamorozka.ui.Timer2;
import zamorozka.ui.TimerHelper;
import zamorozka.ui.Timerr;
import zamorozka.ui.VoidCheck;
import zamorozka.ui.WallCheck;

public class TargetStrafe extends Module {

	public static int direction = -1;
	private double moveSpeed, lastDist;
	public int stage;
	public TimerHelper timer4 = new TimerHelper();
	public Timerr timer = new Timerr();
	public Timerr lastCheck = new Timerr();
	public Timerr damageTimer = new Timerr();
	private Timer2 tim = new Timer2();

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Default");
		options.add("SunriseBypass");
		Zamorozka.instance.settingsManager.rSetting(new Setting("Strafe Mode", this, "Default", options));
		ArrayList<String> options1 = new ArrayList<>();
		options1.add("VelocityFIX");
		options1.add("EventMotion");
		Zamorozka.instance.settingsManager.rSetting(new Setting("Motion Mode", this, "EventMotion", options1));
		Zamorozka.settingsManager.rSetting(new Setting("PotionSpeedReduce", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("TargetRange", this, 2.5f, 0f, 7f, true));
		Zamorozka.settingsManager.rSetting(new Setting("ForwardSpeed", this, 0.85D, 0D, 3D, true));
		Zamorozka.settingsManager.rSetting(new Setting("StrafeSpeed", this, 0.85D, 0D, 3D, true));
		Zamorozka.settingsManager.rSetting(new Setting("SwitchWhenWall", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("SwitchWhenTargetLook", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("AutoShiftPress", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("KeepDistance", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("KeepDistanceRange", this, 4, 0, 6, false));
		Zamorozka.settingsManager.rSetting(new Setting("KeepDistanceStrength", this, 90, 90, 1000, false));
		Zamorozka.settingsManager.rSetting(new Setting("DamageBoost", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("DamageBoostTicks", this, 0, 0, 8, false));
		Zamorozka.settingsManager.rSetting(new Setting("DamageBoostStrength", this, 2, 1, 3, false));
		Zamorozka.settingsManager.rSetting(new Setting("TimerSwitch", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("TimerSwitchValue", this, 500, 100, 5000, false));
	}

	public TargetStrafe() {
		super("TargetStrafe", 0, Category.COMBAT);
	}

	@EventTarget
	public void onUpdate(EventPreMotionUpdates event) { // EventUpdate
		EntityLivingBase target = KillAura.target;
		double mode = Zamorozka.settingsManager.getSettingByName("TargetRange").getValDouble();
		String mode2 = Zamorozka.instance.settingsManager.getSettingByName("Strafe Mode").getValString();
		String mode3 = Zamorozka.settingsManager.getSettingByName("Motion Mode").getValString();
		String md = Zamorozka.settingsManager.getSettingByName("DamageBoost").getValBoolean() ? (String) ", DamageBoost" : "";
		String modeput = Character.toUpperCase(mode2.charAt(0)) + mode2.substring(1);
		String modeput1 = Character.toUpperCase(mode3.charAt(0)) + mode3.substring(1);
		if (mode3.equalsIgnoreCase("VelocityFIX")) {
			this.setDisplayName("TargetStrafe §f§" + " " + "Strafe: " + modeput + ", " + "Motion: " + "VelocityFIX");
		} else {
			this.setDisplayName("TargetStrafe §f§" + " " + "Strafe: " + modeput + ", " + "Motion: " + "EventMotion");
		}
		long dl = (long) Zamorozka.settingsManager.getSettingByName("TimerSwitchValue").getValDouble();
		EntityValidator.add(new VoidCheck());
		if (mc.gameSettings.keyBindLeft.isPressed())
			this.direction = 1;
		if (mc.gameSettings.keyBindRight.isPressed())
			this.direction = -1;
		if (Zamorozka.settingsManager.getSettingByName("SwitchWhenWall").getValBoolean() && mc.player.isCollidedHorizontally && target != null) {
			switchDirection();
		}
		if (tim.check(dl) && target != null && Zamorozka.settingsManager.getSettingByName("TimerSwitch").getValBoolean()) {
			switchDirection();
			tim.reset();
		}
		if (Zamorozka.settingsManager.getSettingByName("AutoShiftPress").getValBoolean() && canStrafe() && mc.player.getDistanceToEntity(target) <= Zamorozka.settingsManager.getSettingByName("AttackRange").getValDouble()
				&& ModuleManager.getModule(SpeedHack.class).getState() && target != null && mc.player.hurtTime > 0) {
			mc.gameSettings.keyBindSneak.pressed = true;
		} else {
			mc.gameSettings.keyBindSneak.pressed = false;
		}
		if (org.lwjgl.input.Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
			mc.gameSettings.keyBindSneak.pressed = true;
		}
		if (target != null) {
			if (Zamorozka.settingsManager.getSettingByName("SwitchWhenTargetLook").getValBoolean() && AimUtil.isAimAtMe(target)) {
				switchDirection();
			}
		}
		if (!(mc.currentScreen instanceof ClickGUI || (mc.currentScreen instanceof GuiInventory) || (mc.currentScreen instanceof GuiScreen)))
			return;
		if (mc.player.onGround && Zamorozka.settingsManager.getSettingByName("AutoJump").getValBoolean() && target != null && ModuleManager.getModule(SpeedHack.class).getState()) {
			mc.player.jump();
		}
	}

	public void strafe(EventMove event, double moveSpeed) {
		String mode = Zamorozka.settingsManager.getSettingByName("Strafe Mode").getValString();
		EntityLivingBase target = KillAura.target;
		float[] rotations = RotUtils.getRotationsEntity(target);
		double rangeee = Zamorozka.settingsManager.getSettingByName("TargetRange").getValDouble();
		if (mc.player.getDistanceToEntity(target) <= Zamorozka.settingsManager.getSettingByName("AttackRange").getValDouble()) {
			if (mode.equalsIgnoreCase("Default")) {
				if (mc.player.getDistanceToEntity(target) <= rangeee && !mc.player.isPotionActive(Potion.getPotionById(25)) && target != null) {
					MovementUtilis.setSpeed(event, moveSpeed, rotations[0], this.direction, 0.0D);
				} else {
					MovementUtilis.setSpeed(event, moveSpeed, rotations[0], this.direction, 1.0D);
				}
			}
			if (mode.equalsIgnoreCase("SunriseBypass")) {
				if (mc.player.getDistanceToEntity(target) <= rangeee && !mc.player.isPotionActive(Potion.getPotionById(25)) && target != null) {
					MovementUtilis.setSpeed2(event, moveSpeed, rotations[0], this.direction, 0.0D);
				} else {
					MovementUtilis.setSpeed2(event, moveSpeed, rotations[0], this.direction, 1.0D);
				}
			}
		}
	}

	private void switchDirection() {
		if (this.direction == 1) {
			this.direction = -1;
		} else {
			this.direction = 1;
		}
	}

	public boolean canStrafe() {
		return (ModuleManager.getModule(KillAura.class).getState() && KillAura.target != null);
	}
}