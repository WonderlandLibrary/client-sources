package zamorozka.modules.COMBAT;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;

import de.Hero.settings.Setting;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketConfirmTransaction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import zamorozka.event.EventTarget;
import zamorozka.event.events.AttackEvent;
import zamorozka.event.events.EventPacket;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.EventStep;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.TRAFFIC.Fly;
import zamorozka.modules.TRAFFIC.LongJump;
import zamorozka.modules.TRAFFIC.SpeedHack;
import zamorozka.ui.AngleUtil;
import zamorozka.ui.ClientUtils;
import zamorozka.ui.MovementUtilis;
import zamorozka.ui.MovementUtils;
import zamorozka.ui.Timerr;

public class Criticals extends Module {

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("AAC");
		options.add("NCP");
		options.add("Matrix");
		options.add("Legit");
		options.add("MatrixNoGround");
		options.add("WellmoreAir");
		options.add("Hypixel");
		options.add("Packet");
		options.add("Horizon");
		options.add("Spartan");
		Zamorozka.instance.settingsManager.rSetting(new Setting("Criticals Mode", this, "AAC", options));
		Zamorozka.settingsManager.rSetting(new Setting("MatrixDelayTimer", this, 675, 0, 1500, true));
		Zamorozka.settingsManager.rSetting(new Setting("MatrixCritChance", this, 85, 0, 100, true));
		Zamorozka.settingsManager.rSetting(new Setting("EntityHurtTime", this, 15, 0, 20, true));
		Zamorozka.settingsManager.rSetting(new Setting("OnlySword", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("CheckWater", this, true));
	}

	EntityLivingBase target = KillAura.target;
	public final double[] hypixelOffsets = new double[] { 0.05000000074505806D, 0.0015999999595806003D, 0.029999999329447746D, 0.0015999999595806003D };
	public final double[] matrixPosOffsets = new double[] { 0.048, 0.003, 0.002, 0.0152 };
	Timerr lastStep = new Timerr();
	Timerr timer = new Timerr();
	int groundTicks, stage, count;

	public Criticals() {
		super("Criticals", 0, Category.COMBAT);
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		double cr = Zamorozka.settingsManager.getSettingByName("MatrixCritChance").getValDouble();
		String mode = Zamorozka.settingsManager.getSettingByName("Criticals Mode").getValString();
		String modeput = Character.toUpperCase(mode.charAt(0)) + mode.substring(1);
		this.setDisplayName("Criticals §f§" + " " + modeput);
		if (mode.equalsIgnoreCase("Matrix")) {
			this.setDisplayName("Criticals §f§" + " " + modeput + ", " + (int) cr);
		}
		if (mode.equalsIgnoreCase("MatrixNoGround")) {
			this.setDisplayName("Criticals §f§" + " " + modeput + ", " + (int) cr);
		}
		if (mode.equalsIgnoreCase("WellmoreAir")) {
			this.setDisplayName("Criticals §f§" + " " + "WellmoreAir");
		}
		if (mode.equalsIgnoreCase("WellmoreAir")) {
			if (mc.player.fallDistance > 0.3) {
				mc.player.motionY = 0;
				if (mc.gameSettings.keyBindJump.pressed)
					mc.player.motionY = 0.12;
				if (mc.gameSettings.keyBindSneak.pressed && !Zamorozka.settingsManager.getSettingByName("AutoShiftPress").getValBoolean())
					mc.player.motionY = -0.12;
			}
		}
		if (mode.equalsIgnoreCase("Legit")) {
			if (KillAura.target != null) {
				if (mc.player.getDistanceToEntity(KillAura.target) <= Zamorozka.settingsManager.getSettingByName("AttackRange").getValDouble() + 1) {
					if (!mc.player.onGround)
						return;
					if (ModuleManager.getModule(KillAura.class).getState()) {
						if (!mc.gameSettings.keyBindJump.isKeyDown()) {
							mc.player.jump();
						}
					}
				}
			}
		}
		if (MovementUtilis.isOnGround(0.001)) {
			groundTicks++;
		} else if (!mc.player.onGround) {
			groundTicks = 0;
		}
	}

	@EventTarget
	public void onAttack(AttackEvent event) {
		if (!(mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) && Zamorozka.settingsManager.getSettingByName("OnlySword").getValBoolean())
			return;
		if ((mc.player.isInWater() || mc.player.isInLava()) && Zamorozka.settingsManager.getSettingByName("CheckWater").getValBoolean())
			return;
		if (ModuleManager.getModule(KillAura.class).getState() && KillAura.target != null) {
			String mode = Zamorozka.settingsManager.getSettingByName("Criticals Mode").getValString();
			if (KillAura.target.hurtResistantTime <= Zamorozka.settingsManager.getSettingByName("EntityHurtTime").getValDouble()) {
				double x = mc.player.posX;
				double y = mc.player.posY;
				double z = mc.player.posZ;
				if (mode.equalsIgnoreCase("Packet")) {
					mc.getConnection().sendPacket(new CPacketPlayer.Position(x, y + 2, z, true));
					mc.getConnection().sendPacket(new CPacketPlayer.Position(x, y, z, false));
				}
				if (mode.equalsIgnoreCase("Hypixel")) {
					if (mc.player.isCollidedVertically && mc.player.onGround && !(ModuleManager.getModule(LongJump.class).getState() || ModuleManager.getModule(Fly.class).getState()) && timer.delay(200L)) {
						for (double offset : hypixelOffsets) {
							mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + offset, mc.player.posZ, false));
							timer.reset();
						}
					}
				}
				if (mode.equalsIgnoreCase("NCP")) {
					if (timer.delay(500) && mc.player.onGround) {
						mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.11, mc.player.posZ, true));
						mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
						timer.reset();
					}
				}
				if (mode.equalsIgnoreCase("Horizon")) {
					if (mc.player.motionX == 0.0 && mc.player.motionZ == 0.0) {
						mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.0001, mc.player.posZ, true));
						mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
					}
				}
				if (mode.equalsIgnoreCase("Spartan")) {
					if (mc.player.onGround) {
						mc.player.setPosition(x, y + 0.04, z);
						mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.01, mc.player.posZ, true));
						mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
					}
				}
				if (mode.equalsIgnoreCase("AAC")) {
					EntityLivingBase target = KillAura.target;
					mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.0031311231111, mc.player.posZ, false));
					mc.player.onCriticalHit(target);
				}
				if (mode.equalsIgnoreCase("Matrix")) {
					double cr = Zamorozka.settingsManager.getSettingByName("MatrixCritChance").getValDouble();
					long dl = (long) Zamorozka.settingsManager.getSettingByName("MatrixDelayTimer").getValDouble();
					if (timer.delay(dl) && AngleUtil.randomFloat(0, 100) <= cr && !ModuleManager.getModule(SpeedHack.class).getState()) {
						mc.player.setPosition(x, y + 0.4012362343123123126537612537125321671253715623682676, z);
						mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.0672234246, mc.player.posZ, true));
						mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
						// mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX,
						// mc.player.posY + 0.0002343443, mc.player.posZ, false));
						// mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX,
						// mc.player.posY, mc.player.posZ, false));
						timer.reset();
					}
				}
				if (mode.equalsIgnoreCase("MatrixNoGround")) {
					double cr = Zamorozka.settingsManager.getSettingByName("MatrixCritChance").getValDouble();
					long dl = (long) Zamorozka.settingsManager.getSettingByName("MatrixDelayTimer").getValDouble();
					if (timer.delay(dl) && AngleUtil.randomFloat(0, 100) <= cr && !mc.player.onGround) {
						mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.0672234246, mc.player.posZ, true));
						mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
						timer.reset();
					}
				}
			}
		}
	}

	@EventTarget
	public void onPacket(EventPacket event) {
		Packet<?> packet = event.getPacket();

		if (packet instanceof SPacketPlayerPosLook) {
			stage = 0;
		}
		if (packet instanceof CPacketConfirmTransaction) {
			CPacketConfirmTransaction confirmTransaction = (CPacketConfirmTransaction) packet;
			boolean accepted = confirmTransaction.isAccepted();
			int uid = confirmTransaction.getUid();
			if (accepted && uid == 0) {
				count++;
			}
		}
	}

	@EventTarget
	public void onStep(EventStep event) {
		if (!event.isPre()) {
			lastStep.reset();
			if (!mc.player.isCollidedHorizontally) {
				stage = 0;
			}
		}
	}
}