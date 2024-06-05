package zamorozka.modules.TRAFFIC;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.client.CPacketClientStatus.State;
import net.minecraft.network.play.client.CPacketConfirmTransaction;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import net.minecraft.network.play.client.CPacketSpectate;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameType;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPacket;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.PLAYER.NoFall;
import zamorozka.modules.ZAMOROZKA.LagCheck;
import zamorozka.notification.NotificationPublisher;
import zamorozka.notification.NotificationType;
import zamorozka.ui.AngleUtil;
import zamorozka.ui.ChatUtils;
import zamorozka.ui.ClientUtils;
import zamorozka.ui.MoveUtils;
import zamorozka.ui.MovementUtilis;
import zamorozka.ui.MovementUtils;
import zamorozka.ui.PlayerUtil;
import zamorozka.ui.PlayerUtilis;
import zamorozka.ui.TimeHelper;
import zamorozka.ui.TimerHelper;

public class Fly extends Module {

	private int counter, level, stage, ticks;

	public double flyHeight;

	TimeHelper timer = new TimeHelper();

	public static float speed = 3F;

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("VerticalFly", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("BigJump", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("Redesky", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("FlyCubeCraft", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("FlyPosition", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("PacketWalk", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("MiniGlide", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("VanFlight", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("FastJump", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("MatrixSlowLatest", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("MatrixLatestGlide", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("AirJump", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("LineFly", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("TickFly", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("FlyJump", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("TNTFly", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("Glide", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("Floats", this, false));
		// Zamorozka.settingsManager.rSetting(new Setting("Hypixel", this, false));
	}

	public Fly() {
		super("Fly", 0, Category.TRAFFIC);
	}

	public static int tick = 0;
	private int state;
	private boolean ground;
	private double oldPosY;
	public static boolean helpBool;
	public static int times = 0;
	private int xPos;
	private int yPos;
	private int zPos;
	private static int radius = 10;
	private EnumFacing side = EnumFacing.NORTH;
	// fdfdidfijof

	@Override
	public void onEnable() {
		/*
		 * if
		 * (Zamorozka.settingsManager.getSettingByName("FlyCubeCraft").getValBoolean())
		 * { mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX,
		 * mc.player.posY - 4, mc.player.posZ, false)); }
		 */
		if (Zamorozka.settingsManager.getSettingByName("Floats").getValBoolean()) {
			MovementUtilis.setMotion(0.3 + MovementUtilis.getSpeedEffect() * 0.05f);
			mc.player.motionY = 0.41999998688698f + MovementUtilis.getJumpEffect() * 0.1;
			if (this.mc.player.ticksExisted % 2 == 0)
				mc.player.motionY = 0.01;
			if (this.mc.player.ticksExisted % 3 == 0)
				mc.player.motionY = -0.01;
		}
		super.onEnable();
	}

	@EventTarget
	public void onPre(EventPreMotionUpdates event) {
		if (!getState())
			return;
		/*
		 * double yaw = mc.player.rotationYaw * 0.017453292; double x = mc.player.posX;
		 * double y = mc.player.posY; double z = mc.player.posZ; if
		 * (Zamorozka.settingsManager.getSettingByName("Hypixel").getValBoolean()) { if
		 * (mc.player.onGround) { mc.player.jump(); mc.gameSettings.keyBindJump.pressed
		 * = false; this.counter = 5; } else if (this.counter < 8) { if (this.counter ==
		 * 1) { mc.timer.timerSpeed = 0.3f; } else { mc.timer.timerSpeed = 1f; } if
		 * (mc.player.motionY < 0) { mc.player.motionY *= -0.54f;
		 * mc.getConnection().sendPacket(new CPacketConfirmTransaction(2, (short) 2,
		 * true)); if(mc.player.ticksExisted % 4 == 0) { MovementUtilis.setPos(x -
		 * Math.sin(yaw) * 0.3, y, z + Math.cos(yaw) * 0.2); } } ++this.counter; } else
		 * { this.counter = 0; } }
		 */

		if (Zamorozka.settingsManager.getSettingByName("AirJump").getValBoolean()) {
			if (mc.player.hurtTime > 9) {
				mc.player.motionY = 1.8f;
			}
		}
		if (Zamorozka.settingsManager.getSettingByName("FastJump").getValBoolean()) {
			if (mc.gameSettings.keyBindJump.isKeyDown()) {
				if (mc.player.motionY * 2 < 0.9) {
					mc.player.motionY = 1.47f;
					mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, true));
				}
			}

		}

		if (Zamorozka.settingsManager.getSettingByName("MatrixSlowLatest").getValBoolean()) {
			if (!getState())
				return;
			mc.gameSettings.keyBindJump.pressed = true;
			mc.gameSettings.keyBindForward.pressed = true;
			if (this.state++ == 6) {
				this.state = 0;
			} else if (this.state == 2) {
				this.mc.player.motionY *= -0.00001;
				this.mc.player.onGround = true;
			} else {
				if (this.mc.player.ticksExisted % 4 == 0) {
					mc.timer.timerSpeed = 0.01f;
				}
				if (mc.player.ticksExisted % 20 == 0 && !mc.player.onGround) {
					mc.timer.timerSpeed = 1000f;
				} else {
					mc.timer.timerSpeed = 0.02f;
				}
			}
		}

		if (Zamorozka.settingsManager.getSettingByName("MatrixLatestGlide").getValBoolean()) {
			if (!getState())
				return;
			if (this.state++ == 6) {
				this.state = 0;
			} else if (this.state == 2) {
				this.mc.player.motionY *= -0.0001;
			}
			this.mc.timer.timerSpeed = 0.3f;
		}

		if (Zamorozka.settingsManager.getSettingByName("FlyCubeCraft").getValBoolean()) {
			if (!getState())
				return;
			mc.getConnection().sendPacket(new CPacketSpectate(UUID.randomUUID()));
			this.mc.player.motionY = 0;
			switch (++this.state) {
			case 1: {
				MovementUtilis.setTpSpeedAndUpdate((double) ((double) this.state % 4.0 / 2.0 + (double) new Random().nextInt(3) / 30.0));
				this.mc.timer.timerSpeed = 0.24f;
				break;
			}
			default: {
				this.mc.timer.timerSpeed = 0.3f + (float) (this.state * 3) / 100.0f;
				MovementUtilis.setTpSpeedAndUpdate((double) ((double) this.state % 4.0 / 2.0 + (double) new Random().nextInt(3) / 30.0));
				break;
			}
			case 5: {
				MovementUtilis.setTpSpeedAndUpdate((double) ((double) this.state % 4.0 / 2.0 + (double) new Random().nextInt(3) / 30.0));
				this.mc.timer.timerSpeed = 0.34f;
				this.state = 0;
				break;
			}
			}
		}

		if (Zamorozka.settingsManager.getSettingByName("Redesky").getValBoolean()) {
			if (!getState())
				return;
			if (mc.player.onGround) {
				this.counter = 5;
				MovementUtilis.vClip(2);
			}
			mc.timer.timerSpeed = 0.3F;
			if (mc.player.posY == oldPosY) {
				MovementUtilis.vClip(5);
				MovementUtilis.vClipPacket(10, true);
				MovementUtilis.hClipPacket(5);
				this.counter = 5;
			} else if (this.counter < 7) {
				if (this.counter == 1) {
					MovementUtilis.hClip(4.5);
				}
				++this.counter;
			} else {
				this.counter = 0;
			}
			MovementUtilis.hClipPacket(5);
			MovementUtilis.vClipPacket(15, false);
		}

		if (Zamorozka.settingsManager.getSettingByName("VanFlight").getValBoolean()) {
			if (!getState())
				return;
			mc.player.motionY = mc.gameSettings.keyBindSneak.isKeyDown() ? -1 : mc.gameSettings.keyBindJump.isKeyDown() ? 1 : 0;
			MovementUtilis.setMotion(1);
		}
		if (Zamorozka.settingsManager.getSettingByName("Floats").getValBoolean()) {
			if (!getState())
				return;
			if (this.mc.player.ticksExisted % 1 == 0)
				mc.player.motionY = 0.001;
			if (this.mc.player.ticksExisted % 1 == 0)
				mc.player.motionY = -0.001;
			if (mc.player.ticksExisted % 20 == 0)
				if (mc.player.motionY == -0.001) {
					PlayerUtil.damage();
				}
		}
		if (Zamorozka.settingsManager.getSettingByName("PacketWalk").getValBoolean()) {
			if (!getState())
				return;
			this.mc.player.motionY = 0.0D;
			this.mc.player.setPosition(this.mc.player.posX, this.mc.player.posY + 1.0E-10D, this.mc.player.posZ);
			this.mc.player.onGround = true;
			if (this.mc.player.ticksExisted % 3 == 0)
				this.mc.player.connection.sendPacket(new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY - 1.0E-10D, this.mc.player.posZ, true));
		}
		if (Zamorozka.settingsManager.getSettingByName("MiniGlide").getValBoolean()) {
			if (!getState())
				return;
			mc.player.motionY = 0;
			mc.timer.elapsedTicks = 1 / 8;
			if (mc.player.ticksExisted % 2 == 0) {
				mc.player.motionY = 0.000001;
			}

		}
		if (Zamorozka.settingsManager.getSettingByName("VerticalFly").getValBoolean()) {
			if (!getState())
				return;
			if (mc.player.hurtTime > 9) {
				mc.player.motionY = 1.7f;
			}
		}
		if (Zamorozka.settingsManager.getSettingByName("BigJump").getValBoolean()) {
			if (!getState())
				return;

			if (mc.player.motionY == 0.0030162615090425808) {
				mc.player.motionY = 0.84;
			}

		}
		if (Zamorozka.settingsManager.getSettingByName("TickFly").getValBoolean()) {
			if (!getState())
				return;
			if (mc.player.ticksExisted % 3 == 0) {
				mc.player.motionY = 0.0001;
			}
			if (mc.player.ticksExisted % 4 == 0) {
				mc.player.motionY = 0.01;
			}
			if (mc.player.ticksExisted % 1 == 0) {
				mc.timer.elapsedTicks = 2;

			}
		}
		if (Zamorozka.settingsManager.getSettingByName("FlyJump").getValBoolean()) {
			if (!getState())
				return;
			if (mc.gameSettings.keyBindForward.isKeyDown() && mc.player.isAirBorne) {
				mc.timer.timerSpeed = 1000;
				if (mc.player.ticksExisted % 2 == 0) {
					mc.player.motionY = 0.1;
				}
			}
		}
		if (Zamorozka.settingsManager.getSettingByName("FlyPosition").getValBoolean()) {
			if (!getState())
				return;
			this.mc.player.motionX = 0.0D;
			this.mc.player.motionY = -0.4000000059604645D;
			this.mc.player.motionZ = 0.0D;
			double x = this.mc.player.posX;
			double y = this.mc.player.posY;
			double z = this.mc.player.posZ;
			x += Math.cos(Math.toRadians((this.mc.player.rotationYaw + 90.0F))) * .1D;
			z += Math.sin(Math.toRadians((this.mc.player.rotationYaw + 90.0F))) * .1D;
			this.mc.player.setPosition(x, y + 0.1D, z);

		}
		if (Zamorozka.settingsManager.getSettingByName("Glide").getValBoolean()) {
			if (!getState())
				return;
			if (mc.player.ticksExisted % 5 == 0) {
				mc.player.motionY = 0;
				mc.timer.timerSpeed = .5f;
				if (mc.player.ticksExisted % 7 == 0) {
					mc.player.motionY = 0;
					mc.timer.timerSpeed = .6f;

				}
			}
		}
		if (Zamorozka.settingsManager.getSettingByName("LineFly").getValBoolean()) {
			if (!getState())
				return;
			if (mc.gameSettings.keyBindJump.isKeyDown()) {
				mc.player.motionY = 0;
				if (mc.player.ticksExisted % 4 == 0) {

					mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY - 0.00000000009909999, mc.player.posZ, true));
					mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));

				}
			}
		}
		if (Zamorozka.settingsManager.getSettingByName("TNTFly").getValBoolean()) {
			if (!getState())
				return;
			for (int x = -radius; x < radius; x++) {
				for (int y = radius; y > -radius; y--) {
					for (int z = -radius; z < radius; z++) {
						this.xPos = (int) mc.player.posX + x;
						this.yPos = (int) mc.player.posY + y;
						this.zPos = (int) mc.player.posZ + z;
						BlockPos blockPos = new BlockPos(this.xPos, this.yPos, this.zPos);
						Block block = mc.world.getBlockState(blockPos).getBlock();
						if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.motionY - 0.42D, mc.player.posZ)).getBlock() == Blocks.AIR) {
							mc.world.setBlockState(new BlockPos(xPos, mc.player.getPosition().getY() - 1, zPos), Blocks.TNT.getDefaultState(), 2);
						}
					}
				}
			}
		}
	}

	private void updateNCP() {
		this.mc.player.motionY = 0.0;
		switch (++this.state) {
		case 1: {
			MovementUtilis.setMotion((double) ((double) this.state % 4.0 / 10.0 + (double) new Random().nextInt(3) / 50.0));
			this.mc.timer.timerSpeed = 0.34f;
			break;
		}
		default: {
			this.mc.timer.timerSpeed = 0.32f + (float) (this.state * 3) / 100.0f;
			MovementUtilis.setMotion((double) ((double) this.state % 4.0 / 10.0 + (double) new Random().nextInt(3) / 50.0));
			break;
		}
		case 5: {
			MovementUtilis.setMotion((double) ((double) this.state % 4.0 / 10.0 + (double) new Random().nextInt(3) / 50.0));
			this.mc.timer.timerSpeed = 0.34f;
			this.state = 0;
		}
		}
	}

	private void resetNCP() {
		this.mc.timer.timerSpeed = 1.0f;
		this.state = 0;
	}

	public void updateFlyHeight() {
		double h = 1;
		AxisAlignedBB box = mc.player.getEntityBoundingBox().expand(0.0625, 0.0625, 0.0625);
		for (flyHeight = 0; flyHeight < mc.player.posY; flyHeight += h) {
			AxisAlignedBB nextBox = box.offset(0, -flyHeight, 0);

			if (mc.world.checkBlockCollision(nextBox)) {
				if (h < 0.0625)
					break;

				flyHeight -= h;
				h /= 2;
			}
		}
	}

	public void goToGround() {
		if (flyHeight > 320)
			return;

		double minY = mc.player.posY - flyHeight;

		if (minY <= 0)
			return;

		for (double y = mc.player.posY; y > minY;) {
			y -= 9.9;
			if (y < minY)
				y = minY;

			CPacketPlayer.Position packet = new CPacketPlayer.Position(mc.player.posX, y, mc.player.posZ, true);
			mc.player.connection.sendPacket(packet);
		}

		for (double y = minY; y < mc.player.posY;) {
			y += 9.9;
			if (y > mc.player.posY)
				y = mc.player.posY;

			CPacketPlayer.Position packet = new CPacketPlayer.Position(mc.player.posX, y, mc.player.posZ, true);
			mc.player.connection.sendPacket(packet);
		}
	}

	@EventTarget
	public void onPacket(EventPacket event) {
		Packet<?> p = event.getPacket();
		if (event.isIncoming()) {
			if (p instanceof SPacketPlayerPosLook && mc.player != null) {
				mc.player.onGround = false;
				mc.player.motionX *= 0;
				mc.player.motionZ *= 0;
				mc.player.jumpMovementFactor = 0;
				if (Zamorozka.moduleManager.getModule(Fly.class).getState() && Zamorozka.moduleManager.getModule(LagCheck.class).getState() && !Zamorozka.settingsManager.getSettingByName("WellmoreClip").getValBoolean()) {
					Zamorozka.moduleManager.getModule(Fly.class).toggle();
					NotificationPublisher.queue("LagBack", "Fly was lagback!", NotificationType.WARNING);
					isEnabled = false;
					ModuleManager.getModule(Fly.class).setState(false);
				}
			}
		}
	}

	public void damage2() {
		mc.player.connection.sendPacket(new CPacketKeepAlive(System.currentTimeMillis()));
	}

	@Override
	public void onDisable() {
		if (Zamorozka.settingsManager.getSettingByName("Redesky").getValBoolean()) {
			for (int i = 0; i < 10; i++) {
				MovementUtilis.hClipPacket(11);
				MovementUtilis.hClipPacket(-11);
				MovementUtilis.resetMotion();
			}
		}
		mc.player.capabilities.isFlying = false;
		mc.timer.timerSpeed = 1f;
		mc.player.isDead = false;
		super.onDisable();
	}

}