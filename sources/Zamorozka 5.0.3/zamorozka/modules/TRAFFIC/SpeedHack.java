package zamorozka.modules.TRAFFIC;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketRecipeInfo;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import optifine.MathUtils;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventMove;
import zamorozka.event.events.EventPacket;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.EventSendPacket;
import zamorozka.event.events.EventUpdate;
import zamorozka.event.events.UpdateEvent;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.COMBAT.KillAura;
import zamorozka.modules.COMBAT.TargetStrafe;
import zamorozka.modules.PLAYER.NoFall;
import zamorozka.modules.WORLD.Terrain;
import zamorozka.modules.ZAMOROZKA.LagCheck;
import zamorozka.notification.NotificationPublisher;
import zamorozka.notification.NotificationType;
import zamorozka.ui.AngleUtil;
import zamorozka.ui.AngleUtility;
import zamorozka.ui.ClientUtils;
import zamorozka.ui.MoveUtils;
import zamorozka.ui.MovementUtil;
import zamorozka.ui.MovementUtilis;
import zamorozka.ui.MovementUtils;
import zamorozka.ui.PlayerUtil;
import zamorozka.ui.SpeedUtils;
import zamorozka.ui.Timerr;
import zamorozka.ui.Wrapper;

public class SpeedHack extends Module {

	int jumps;
	private int counter;
	private double speed, speedvalue;
	public static int stage, aacCount;
	public boolean shouldslow = false;
	public double moveSpeed;

	private int state;
	private int state1;
	private int state2;
	private int state3;
	private boolean slowDownHop;
	private double less;
	private double stair;
	private int state4;
	private int state5;
	private int state6;
	private int state7;
	private int state8;
	private int state9;
	private int state10;
	private int state11;
	private int state12;
	private int state13;
	private int state14;
	private double lastDist;
	public Timerr timer = new Timerr();
	public Timerr lastCheck = new Timerr();
	public Timerr damageTimer = new Timerr();
	private boolean boosted;

	public SpeedHack() {
		super("Speed", 0, Category.TRAFFIC);
	}

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("MatrixFastHop");
		options.add("MatrixTimerHop");
		options.add("Sunrise");
		options.add("ComboDev");
		options.add("TestBoost");
		options.add("Wellmore");
		options.add("TargetStrafe");
		options.add("Bhop");
		options.add("NCPBhop");
		options.add("IntaveTest");
		options.add("NewMatrix");
		options.add("MatrixOLD");
		options.add("OnGround");
		options.add("OnGround2");
		options.add("TimerHop");
		options.add("DamageHop");
		options.add("LegitHop");
		options.add("FastestHop");
		Zamorozka.instance.settingsManager.rSetting(new Setting("Speed Mode", this, "TargetStrafe", options));
		Zamorozka.instance.settingsManager.rSetting(new Setting("AutoWalk", this, true));
		Zamorozka.instance.settingsManager.rSetting(new Setting("AutoJump", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("TimerAbuse", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("TimerAbuseValue", this, 1.035, 1, 1.2, true));
		Zamorozka.settingsManager.rSetting(new Setting("WaterCheck", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("WithTimer", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("NoSprinting", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("WellmoreSpeed", this, 1.035, 0.5, 2, true));
		Zamorozka.settingsManager.rSetting(new Setting("WellmoreStrafeSpeed", this, 0.23, 0.2, 0.4, true));
		Zamorozka.settingsManager.rSetting(new Setting("SunriseSpeed", this, 0.98, 0.5, 2, true));
		Zamorozka.settingsManager.rSetting(new Setting("SunriseStrafe", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("SunriseStrafeSpeed", this, 0.26, 0.1, 0.5, true));
		Zamorozka.settingsManager.rSetting(new Setting("SunriseTimerSpeed", this, 1.2, 1, 4, true));
		Zamorozka.settingsManager.rSetting(new Setting("NewMatrixTimer", this, 2.8, 1, 4, true));
		Zamorozka.settingsManager.rSetting(new Setting("NewMatrixMotion", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("NewMatrixStrafing", this, true));
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		if (Zamorozka.settingsManager.getSettingByName("WaterCheck").getValBoolean()) {
			if (mc.player.isInWater() || mc.player.isInLava()) {
				isEnabled = false;
				ModuleManager.getModule(SpeedHack.class).setState(false);
			}
		}
		float abuse = (float) Zamorozka.settingsManager.getSettingByName("TimerAbuseValue").getValDouble();
		if (Zamorozka.settingsManager.getSettingByName("TimerAbuse").getValBoolean() && ModuleManager.getModule(TargetStrafe.class).getState()) {
			if (mc.player.hurtTime > 0) {
				mc.timer.timerSpeed = abuse;
			}
		}
		String mode = Zamorozka.instance.settingsManager.getSettingByName("Speed Mode").getValString();
		String modeput = Character.toUpperCase(mode.charAt(0)) + mode.substring(1);
		this.setDisplayName("Speed ßfß" + " " + modeput);

		if (mode.equalsIgnoreCase("MatrixFastHop")) {
			if (mc.player.isMoving()) {
				if (mc.player.onGround) {
					mc.player.jump();
					mc.gameSettings.keyBindJump.pressed = false;
					mc.player.speedInAir = 0.02098f;
					mc.timer.timerSpeed = 1.055f;
				} else {
					MovementUtils.strafe();
				}
			} else {
				mc.timer.timerSpeed = 1f;
			}
		}
		if (mode.equalsIgnoreCase("TestBoost")) {
			if (this.mc.player != null && this.mc.world != null) {
				MovementUtils.strafe();
				if (this.mc.gameSettings.keyBindForward.pressed) {
					if (this.mc.player.onGround) {
						this.mc.player.jumpMovementFactor = 0.0268f;
						this.mc.player.speedInAir = 0.0205f;
						this.mc.player.jump();
						this.mc.timer.timerSpeed = 1f;
					} else {
						this.mc.timer.timerSpeed = 1.04f;
					}
				} else {
					this.mc.timer.timerSpeed = 1f;
				}
			}

		}
		if (mode.equalsIgnoreCase("MatrixOLD")) {
			if (mc.player.onGround && mc.gameSettings.keyBindForward.pressed && mc.player.ticksExisted % 3 == 0) {
				mc.player.jump();
			}
			if (mc.player.ticksExisted % 3 == 0 && !mc.player.onGround && mc.gameSettings.keyBindForward.pressed) {
				double dir = Math.toRadians(mc.player.rotationYaw);
				mc.player.motionX += -Math.sin(dir) * 0.3;
				mc.player.motionZ += Math.cos(dir) * 0.3;
				double dir1 = Math.toRadians(mc.player.rotationYaw);
				mc.player.motionX += -Math.sin(dir1) * -0.1;
				mc.player.motionZ += Math.cos(dir1) * -0.1;
			}
			if (mc.gameSettings.keyBindLeft.pressed && mc.player.ticksExisted % 3 == 0) {
				double dir = Math.toRadians(mc.player.rotationYaw - 90);
				mc.player.motionX += -Math.sin(dir) * 0.3;
				mc.player.motionZ += Math.cos(dir) * 0.3;
				double dir1 = Math.toRadians(mc.player.rotationYaw - 90);
				mc.player.motionX += -Math.sin(dir1) * -0.1;
				mc.player.motionZ += Math.cos(dir1) * -0.1;
				double dir2 = Math.toRadians(mc.player.rotationYaw);
				mc.player.motionX += -Math.sin(dir2) * -0.1;
				mc.player.motionZ += Math.cos(dir2) * -0.1;
			}
			if (mc.gameSettings.keyBindRight.pressed && mc.player.ticksExisted % 3 == 0) {
				double dir = Math.toRadians(mc.player.rotationYaw + 90);
				mc.player.motionX += -Math.sin(dir) * 0.3;
				mc.player.motionZ += Math.cos(dir) * 0.3;
				double dir1 = Math.toRadians(mc.player.rotationYaw + 90);
				mc.player.motionX += -Math.sin(dir1) * -0.1;
				mc.player.motionZ += Math.cos(dir1) * -0.1;
				double dir2 = Math.toRadians(mc.player.rotationYaw);
				mc.player.motionX += -Math.sin(dir2) * -0.1;
				mc.player.motionZ += Math.cos(dir2) * -0.1;
			}
			if (mc.gameSettings.keyBindBack.pressed && mc.player.ticksExisted % 3 == 0) {
				double dir = Math.toRadians(mc.player.rotationYaw + 180);
				mc.player.motionX += -Math.sin(dir) * 0.3;
				mc.player.motionZ += Math.cos(dir) * 0.3;
				double dir1 = Math.toRadians(mc.player.rotationYaw + 180);
				mc.player.motionX += -Math.sin(dir1) * -0.1;
				mc.player.motionZ += Math.cos(dir1) * -0.1;
			}
		}
		if (mode.equalsIgnoreCase("NewMatrix")) { // ‚ ÔÂÂ‰ÂÎ˚‚‡ÌËË
			if (!(mc.player.isOnLadder() || mc.player.isInWater() || mc.player.isInLava())) {
				mc.gameSettings.keyBindJump.pressed = false;
				if (Zamorozka.settingsManager.getSettingByName("NewMatrixStrafing").getValBoolean()) {
					MovementUtils.strafe();
				}
				if (mc.player.onGround) {
					mc.player.jump();
					this.counter = 5;
				} else if (this.counter < 5) {
					if (this.counter == 1) {
						mc.timer.timerSpeed = 1.03f;
					} else {
						mc.timer.timerSpeed = 1f;
					}
					++this.counter;
				} else {
					this.counter = 0;
				}
			}
			if (!mc.player.onGround) {
				mc.player.speedInAir = 0.02099f;
				mc.player.jumpMovementFactor = 0.027f;
			}
			if (mc.player.motionY == 0.0030162615090425808) {
				mc.timer.timerSpeed = 1.02f;
			} else {
				mc.timer.timerSpeed = 1;
			}
			if (mc.player.ticksExisted % 60 > 39) {
				mc.timer.timerSpeed = (float) Zamorozka.settingsManager.getSettingByName("NewMatrixTimer").getValDouble();
			}
		}

		if (mode.equalsIgnoreCase("IntaveTest")) {
			MovementUtils.strafe();
			if (mc.player.isMoving()) {
				if (mc.player.onGround) {
					if (!mc.player.isOnLadder()) {
						if (!mc.player.isInWater()) {
							if (!mc.player.isInLava()) {
								mc.player.jump();
								mc.player.motionX *= 1.0255;
								mc.player.motionZ *= 1.0255;
								return;
							}
						}
					}
					mc.player.motionX *= 1.0;
				} else {
					mc.player.motionX *= 1.0;
				}
			} else {
				mc.player.motionY *= 1.0;
			}
		}
		if (mode.equalsIgnoreCase("MatrixTimerHop")) {
			mc.gameSettings.keyBindJump.pressed = false;
			MovementUtils.strafe();
			if (this.mc.player.onGround) {
				this.mc.player.jump();
				mc.player.speedInAir = 0.02098f;
				mc.timer.timerSpeed = 1.055f;
				if (Zamorozka.settingsManager.getSettingByName("WithTimer").getValBoolean())
					this.mc.timer.timerSpeed = 1.055f;
				mc.player.motionX *= 1.0085F;
				mc.player.motionZ *= 1.0085F;
				this.counter = 0;
			} else {
				this.mc.timer.timerSpeed = 1.0f;
				double speed = 1.0;
				if (this.counter < 2) {
					if (this.counter > 0) {
						if (Zamorozka.settingsManager.getSettingByName("WithTimer").getValBoolean())
							this.mc.timer.timerSpeed = 3.95f;
						speed = 1.0235;
					} else {
						this.mc.timer.timerSpeed = 1.0f;
					}
					++this.counter;
				}
				double Motion = Math.sqrt((double) (this.mc.player.motionX * this.mc.player.motionX + this.mc.player.motionZ * this.mc.player.motionZ));
				if (this.mc.player.hurtTime < 5) {
					this.mc.player.motionX = -Math.sin((double) MovementUtils.getDirection()) * speed * Motion;
					this.mc.player.motionZ = Math.cos((double) MovementUtils.getDirection()) * speed * Motion;
				}
			}
		}
		if (mode.equalsIgnoreCase("ComboDev")) {
			MovementUtils.strafe();
			mc.gameSettings.keyBindJump.pressed = true;
			mc.player.motionX *= 1.004F;
			mc.player.motionZ *= 1.004F;
		}
		if (mode.equalsIgnoreCase("OnGround2")) {
			boolean speed = true;
			if (this.mc.player.onGround) {
				if (speed) {
					if (this.counter < 2) {
						this.mc.timer.timerSpeed = this.counter >= 0.1 ? 4f : 0.25f;
						++this.counter;
					} else {
						this.counter = 0;
					}
				} else {
					this.mc.timer.timerSpeed = 1.0f;
					this.mc.player.jump();
				}
			} else {
				this.mc.timer.timerSpeed = 1.0f;
				if (speed) {
					double playerSpeed = 1.0;
					double Motion = Math.sqrt((double) (this.mc.player.motionX * this.mc.player.motionX + this.mc.player.motionZ * this.mc.player.motionZ));
					if (this.mc.player.hurtTime < 5) {
						this.mc.player.motionX = -Math.sin((double) MovementUtilis.getDirection()) * playerSpeed * Motion;
						this.mc.player.motionZ = Math.cos((double) MovementUtilis.getDirection()) * playerSpeed * Motion;
					}
				}
			}
		}
		if (mode.equalsIgnoreCase("Wellmore")) {
			MovementUtils.strafe();
			double speed6 = Zamorozka.settingsManager.getSettingByName("WellmoreStrafeSpeed").getValDouble();
			double speed2 = Zamorozka.settingsManager.getSettingByName("WellmoreSpeed").getValDouble();
			if (mc.gameSettings.keyBindLeft.pressed || mc.gameSettings.keyBindRight.pressed || mc.gameSettings.keyBindBack.pressed) {
				speed = -(double) stage / 500 + MovementUtilis.getSpeedEffect() * 0.5;
				speed = Math.max(shouldslow ? speed - 0.2005 : speed, 0.2005);
				MoveUtils.setMotion(speed);
				if (mc.player.onGround) {
					mc.player.jump();
				}
			} else {
				if (mc.player.onGround) {
					mc.player.jump();
					mc.player.motionX *= speed2;
					mc.player.motionZ *= speed2;
					if (Zamorozka.settingsManager.getSettingByName("WithTimer").getValBoolean())
						mc.timer.timerSpeed = 1.05F;
					mc.player.speedInAir = 0.0202f;
				} else {
					mc.player.jumpMovementFactor = 0.0263f;
					double speed = 1.0;
					if (this.counter < 2) {
						if (this.counter > 0) {
							if (Zamorozka.settingsManager.getSettingByName("WithTimer").getValBoolean())
								this.mc.timer.timerSpeed = 1.5f;
							speed = 1.0155;
						} else {
							if (Zamorozka.settingsManager.getSettingByName("WithTimer").getValBoolean())
								this.mc.timer.timerSpeed = 1.0f;
						}
						++this.counter;
					}
					double Motion = Math.sqrt((double) (this.mc.player.motionX * this.mc.player.motionX + this.mc.player.motionZ * this.mc.player.motionZ));
					if (this.mc.player.hurtTime < 5) {
						this.mc.player.motionX = -Math.sin((double) MovementUtils.getDirection()) * speed * Motion;
						this.mc.player.motionZ = Math.cos((double) MovementUtils.getDirection()) * speed * Motion;
					}
				}
			}
		}
		if (mode.equalsIgnoreCase("Sunrise")) {
			mc.gameSettings.keyBindJump.pressed = false;
			if (Zamorozka.settingsManager.getSettingByName("SunriseStrafe").getValBoolean()) {
				MovementUtils.strafe();
			}
			double speed3 = Zamorozka.settingsManager.getSettingByName("SunriseStrafeSpeed").getValDouble();
			double speed4 = Zamorozka.settingsManager.getSettingByName("SunriseSpeed").getValDouble();
			if (mc.gameSettings.keyBindLeft.pressed || mc.gameSettings.keyBindRight.pressed || mc.gameSettings.keyBindBack.pressed) {
				double more = jumps <= 0 ? 0 : (double) jumps / 10;
				speed = speed3 - (double) stage / 300 + MovementUtilis.getSpeedEffect() * 0.3;
				if (Zamorozka.settingsManager.getSettingByName("SunriseStrafe").getValBoolean()) {
					speed = Math.max(shouldslow ? speed - 0.2 : speed, 0.2);
					MovementUtilis.setMotion(speed);
				}
				if (mc.player.onGround) {
					mc.player.jump();
					this.counter = 5;
				}
			} else {
				if (mc.player.onGround) {
					mc.player.jump();
					this.counter = 5;
					mc.player.speedInAir *= 0.02098f;
					mc.player.motionX *= speed4;
					mc.player.motionZ *= speed4;
					if (Zamorozka.settingsManager.getSettingByName("WithTimer").getValBoolean())
						mc.timer.timerSpeed = 1.045F;
					mc.player.speedInAir = 0.0214f;
				} else {
					if (this.counter == 1) {
						mc.timer.timerSpeed = 1.02f;
					} else if (this.counter == 5) {
						mc.timer.timerSpeed = 1.05f;
					} else {
						mc.timer.timerSpeed = 1f;
					}
					mc.player.jumpMovementFactor = 0.0265f;
					if (this.mc.player.ticksExisted % 80 == 50) {
					} else {
						double speed = 1.0;
						if (this.counter < 2) {
							if (this.counter > 0) {
								if (Zamorozka.settingsManager.getSettingByName("WithTimer").getValBoolean())
									this.mc.timer.timerSpeed = 1.4f;
								speed = 4;
							} else {
								this.mc.timer.timerSpeed = 1.0f;
							}
							++this.counter;
						}
						double Motion = Math.sqrt((double) (this.mc.player.motionX * this.mc.player.motionX + this.mc.player.motionZ * this.mc.player.motionZ));
						if (this.mc.player.hurtTime < 5 && Zamorozka.settingsManager.getSettingByName("SunriseStrafe").getValBoolean()) {
							this.mc.player.motionX = -Math.sin((double) MovementUtils.getDirection()) * speed * Motion;
							this.mc.player.motionZ = Math.cos((double) MovementUtils.getDirection()) * speed * Motion;
						}
					}
					if (this.mc.player.ticksExisted % 80 == 0) {
						if (Zamorozka.settingsManager.getSettingByName("WithTimer").getValBoolean()) {
							mc.timer.timerSpeed = (float) Zamorozka.settingsManager.getSettingByName("SunriseTimerSpeed").getValDouble();
						}
					}
				}
			}
		}
	}

	private double getCurrentSpeed(int stage) {
		double speed = MovementUtilis.getBaseMoveSpeed() + 0.028D * MovementUtilis.getSpeedEffect() + MovementUtilis.getSpeedEffect() / 15.0D;
		double initSpeed = 0.4145D + MovementUtilis.getSpeedEffect() / 12.5D;
		double decrease = stage / 500.0D * 1.87D;
		if (stage == 0) {
			speed = 0.64D + (MovementUtilis.getSpeedEffect() + 0.028D * MovementUtilis.getSpeedEffect()) * 0.134D;
		} else if (stage == 1) {
			speed = initSpeed;
		} else if (stage >= 2) {
			speed = initSpeed - decrease;
		}
		return Math.max(speed, this.slowDownHop ? speed : (MovementUtilis.getBaseMoveSpeed() + 0.028D * MovementUtilis.getSpeedEffect()));
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
				if (Zamorozka.moduleManager.getModule(SpeedHack.class).getState() && Zamorozka.moduleManager.getModule(LagCheck.class).getState()) {
					Zamorozka.moduleManager.getModule(SpeedHack.class).toggle();
					NotificationPublisher.queue("LagBack", "SpeedHack was lagback!", NotificationType.WARNING);
					isEnabled = false;
					ModuleManager.getModule(SpeedHack.class).setState(false);
					isEnabled = false;
					ModuleManager.getModule(SpeedHack.class).setState(false);
				}
			}
		}
	}

	@EventTarget
	public void onMove(EventMove eventMove) {
		if (Zamorozka.settingsManager.getSettingByName("AutoWalk").getValBoolean()) {
			mc.gameSettings.keyBindForward.pressed = true;
		}
		if (Zamorozka.settingsManager.getSettingByName("AutoJump").getValBoolean()) {
			mc.gameSettings.keyBindJump.pressed = true;
		}
		String mode = Zamorozka.instance.settingsManager.getSettingByName("Speed Mode").getValString();
		String modeput = Character.toUpperCase(mode.charAt(0)) + mode.substring(1);
		if (mode.equalsIgnoreCase("TargetStrafe")) {
			modeput = "TargetStrafe";
		}
		this.setDisplayName("Speed ßfß" + " " + modeput);
		TargetStrafe targetStrafe = (TargetStrafe) ModuleManager.getModule(TargetStrafe.class);
		if (mode.equalsIgnoreCase("TargetStrafe")) {
			if (Zamorozka.settingsManager.getSettingByName("NewMatrixStrafing").getValBoolean()) {
				MovementUtils.strafe();
			}
			EntityLivingBase target = KillAura.target;
			if (targetStrafe.canStrafe() && ModuleManager.getModule(TargetStrafe.class).getState() && target.getHealth() > 0) {
				targetStrafe.strafe(eventMove, this.moveSpeed);
				float abuse = (float) Zamorozka.settingsManager.getSettingByName("TimerAbuseValue").getValDouble();
				if (Zamorozka.settingsManager.getSettingByName("TimerAbuse").getValBoolean() && ModuleManager.getModule(TargetStrafe.class).getState()) {
					mc.timer.timerSpeed = abuse;
				}
			}
			if (Zamorozka.settingsManager.getSettingByName("AutoJump").getValBoolean()) {
				mc.gameSettings.keyBindJump.pressed = true;
			}
		}
		if (mode.equalsIgnoreCase("Bhop")) {
			if (MovementInput.moveForward == 0.0f && MovementInput.moveStrafe == 0.0f) {
				this.moveSpeed = MovementUtilis.getBaseMoveSpeed();
			}
			if (stage == 1 && mc.player.isCollidedVertically && (MovementInput.moveForward != 0.0F || MovementInput.moveStrafe != 0.0F)) {
				this.moveSpeed = 1.35 + MovementUtilis.getBaseMoveSpeed() - 0.01;
			}

			if (!mc.player.isInLiquid() && stage == 2 && mc.player.isCollidedVertically && (MovementInput.moveForward != 0.0F || MovementInput.moveStrafe != 0.0F)) {
				double motY = 0.407D;
				this.mc.player.motionY = motY;
				eventMove.setY(motY);
				mc.player.jump();
				state7 = 0;
				this.moveSpeed *= 1.5;
				if (state4 > 0) {
					this.moveSpeed *= 1.4D;
					state4--;
				}
			} else if (stage == 3) {
				final double difference = 0.66 * (lastDist - MovementUtilis.getBaseMoveSpeed());
				this.moveSpeed = lastDist - difference;
			} else {
				final List collidingList = mc.world.getEntitiesWithinAABBExcludingEntity(mc.player, mc.player.boundingBox.offset(0.0, mc.player.motionY, 0.0));
				if ((collidingList.size() > 0 || MovementUtilis.isOnGround(0.01)) && stage > 0) {
					stage = ((MovementInput.moveForward != 0.0F || MovementInput.moveStrafe != 0.0F) ? 1 : 0);
				}
				this.moveSpeed = lastDist - lastDist / 159.0;
			}
			if (!mc.player.canRenderOnFire()) {
				// ChatUtils.printChat("shalopaygay");
				state3 = 0;
			}

			this.moveSpeed = Math.max(this.moveSpeed, MovementUtilis.getBaseMoveSpeed());

			if (stage > 0) {
				if (mc.player.isInLiquid())
					this.moveSpeed = 0.1;
				EntityLivingBase target = KillAura.target;
				if (targetStrafe.canStrafe() && ModuleManager.getModule(TargetStrafe.class).getState() && target.getHealth() > 0) {
					targetStrafe.strafe(eventMove, this.moveSpeed);
				} else {
					MovementUtilis.setMotion(eventMove, this.moveSpeed);
				}
			}
			if (mc.player.isMoving()) {
				++stage;
			}
		}
		if (mode.equalsIgnoreCase("TimerHop")) {
			this.state += 1;
			MovementUtils.strafe();
			switch (this.state) {
			case 1:
				this.mc.timer.timerSpeed = 2f;
				break;
			case 2:
				this.moveSpeed = 2.39;
				mc.timer.timerSpeed = (float) 5;
				state++;
				break;
			case 3:
				this.moveSpeed = 2.55;
				mc.timer.timerSpeed = 6;
				state++;
				break;
			case 4:
				mc.timer.timerSpeed = 0.5f;
				this.state = 0;
				break;
			default:
				break;
			}
		}
		if (mode.equalsIgnoreCase("NCPBhop")) {
			EntityLivingBase target = KillAura.target;
			boolean collided = this.mc.player.isCollidedHorizontally;
			if (collided)
				this.stage = -1;
			if (this.stair > 0.0D)
				this.stair -= 0.3D;
			this.less -= (this.less > 1.0D) ? 0.12D : 0.11D;
			if (this.less < 0.0D)
				this.less = 0.0D;
			if (!this.mc.player.isInWater() && this.mc.player.onGround && this.mc.player.isMoving()) {
				collided = this.mc.player.isCollidedHorizontally;
				if (this.stage >= 0 || collided) {
					this.stage = 0;
					double motY = 0.407D;
					if (this.stair == 0.0D) {
						this.mc.player.motionY = motY;
						eventMove.setY(this.mc.player.motionY = motY);
						if (Zamorozka.settingsManager.getSettingByName("WithTimer").getValBoolean()) {
							mc.timer.timerSpeed = 1.05f;
						}
					}
					this.less++;
					this.slowDownHop = this.less > 1.0D && !this.slowDownHop;
					if (this.less > 1.12D)
						this.less = 1.12D;
				}
			}
			this.moveSpeed = getCurrentSpeed(this.stage) + 0.0335D;
			this.moveSpeed *= 0.8500000000000001D;
			if (this.stair > 0.0D)
				this.moveSpeed *= 1D - MovementUtilis.getSpeedEffect() * 0.50;
			if (this.stage < 0)
				this.moveSpeed = MovementUtilis.getBaseMoveSpeed();
			if (this.slowDownHop)
				this.moveSpeed *= 0.8575D;
			if (this.mc.player.isInWater())
				this.moveSpeed = 0.351D;
			if (this.mc.player.isMoving()) {
				if (targetStrafe.canStrafe() && ModuleManager.getModule(TargetStrafe.class).getState() && target.getHealth() > 0) {
					targetStrafe.strafe(eventMove, this.moveSpeed);
				} else {
					MovementUtilis.setMotion(eventMove, this.moveSpeed);
				}
				this.stage++;
			}
		}
		if (mode.equalsIgnoreCase("OnGround")) {
			if (mc.player.field_191988_bg == 0.0f && mc.player.moveStrafing == 0.0f) {
				this.moveSpeed = MovementUtilis.getBaseMoveSpeed();
			}
			if (this.stage == 0 && (mc.player.field_191988_bg != 0.0f || mc.player.moveStrafing != 0.0f)) {
				this.stage = 1;
				this.moveSpeed = MovementUtilis.getBaseMoveSpeed() - 0.01;
			} else if (this.stage == 1 && (mc.player.field_191988_bg != 0.0f || mc.player.moveStrafing != 0.0f)) {
				this.stage = 2;
				this.moveSpeed = 1.63 * MovementUtilis.getBaseMoveSpeed() - 0.01;
			} else if (mc.player.field_191988_bg != 0.0f || mc.player.moveStrafing != 0.0f) {
				this.stage = 1;
				this.moveSpeed = 1.1 * MovementUtilis.getBaseMoveSpeed() - 0.01;
			}
			if (this.stage > 0 && mc.player.onGround && MovementUtilis.isOnGround(mc.player.motionX, mc.player.motionZ)) {
				if (targetStrafe.canStrafe()) {
					targetStrafe.strafe(eventMove, this.moveSpeed);
				} else {
					MovementUtilis.setMotion(eventMove, this.moveSpeed);
				}
			}
		}
		if (mode.equalsIgnoreCase("DamageHop")) {
			EntityLivingBase target = KillAura.target;
			if (MovementInput.moveForward == 0.0f && MovementInput.moveStrafe == 0.0f) {
				this.moveSpeed = MovementUtilis.getBaseMoveSpeed();
			}
			if (stage == 1 && mc.player.isCollidedVertically && (MovementInput.moveForward != 0.0F || MovementInput.moveStrafe != 0.0F)) {
				this.moveSpeed = 1.2 * MovementUtilis.getBaseMoveSpeed() - 0.01;
			}
			if (!mc.player.isInLiquid() && stage == 2 && mc.player.isCollidedVertically && (MovementInput.moveForward != 0.0F || MovementInput.moveStrafe != 0.0F)) {
				if (this.damageTimer.delay(2500.0F)) {
					this.damageTimer.reset();
					damage2();
				}

				double motY = 0.407D;
				this.mc.player.motionY = motY;
				eventMove.setY(motY);
				mc.player.jump();
				this.moveSpeed *= 2.14D;
			} else if (stage == 3) {
				final double difference = 0.66 * (lastDist - MovementUtilis.getBaseMoveSpeed());
				this.moveSpeed = lastDist - difference;
			} else {
				final List collidingList = mc.world.getEntitiesWithinAABBExcludingEntity(mc.player, mc.player.boundingBox.offset(0.0, mc.player.motionY, 0.0));
				if ((collidingList.size() > 0 || MovementUtilis.isOnGround(0.01)) && stage > 0) {
					stage = ((MovementInput.moveForward != 0.0F || MovementInput.moveStrafe != 0.0F) ? 1 : 0);
				}
				this.moveSpeed = lastDist - lastDist / 159.0;
			}
			this.moveSpeed = Math.max(this.moveSpeed, MovementUtilis.getBaseMoveSpeed());
			if (this.stage > 0) {
				if (mc.player.isMoving()) {
					if (mc.player.motionY > 0.0D) {
						mc.player.motionY += 0.0012D;
					} else if (mc.player.fallDistance < 1.5F) {
						mc.player.motionY += 0.0012D;
					}
				}
				if (targetStrafe.canStrafe() && ModuleManager.getModule(TargetStrafe.class).getState() && target.getHealth() > 0) {
					targetStrafe.strafe(eventMove, this.moveSpeed);
				} else {
					MovementUtilis.setMotion(eventMove, this.moveSpeed);
				}
			}
			if (mc.player.field_191988_bg != 0.0F || mc.player.moveStrafing != 0.0F)
				this.stage++;
		}
		if (mode.equalsIgnoreCase("FastestHop")) {
			mc.gameSettings.keyBindJump.pressed = false;
			if (mc.player.isMoving()) {
				if (mc.gameSettings.keyBindJump.pressed) {

					eventMove.setY(this.mc.player.motionY = 0.22f);
				}
				if (mc.player.onGround) {
					double motY = 0.307D;
					this.mc.player.motionY = motY;
					eventMove.setY(this.mc.player.motionY = motY);
				}
				if (targetStrafe.canStrafe() && ModuleManager.getModule(TargetStrafe.class).getState()) {
					targetStrafe.strafe(eventMove, 1.3);
				} else {
					MovementUtilis.setMotion(eventMove, 1.3);
				}
			}
		}
		if (mode.equalsIgnoreCase("LegitHop")) {
			if (mc.player.isMoving()) {
				MovementUtils.strafe();
				if (mc.player.onGround) {
					if (mc.player.isPotionActive(Potion.getPotionById(8))) {
						eventMove.setY(mc.player.motionY = 0.41999998688698 + (mc.player.getActivePotionEffect(Potion.getPotionById(8)).getAmplifier() + 1) * 0.1);
					} else {
						eventMove.setY(mc.player.motionY = 0.41999998688698);
					}
					mc.player.jump();
				}
			}
		}
	}

	@EventTarget
	public void onUpdate(UpdateEvent event) {
		String mode = Zamorozka.instance.settingsManager.getSettingByName("Speed Mode").getValString();
		if (mode.equalsIgnoreCase("OnGround")) {
			if (mc.player.isMoving() && mc.player.onGround && MovementUtilis.isOnGround(mc.player.motionX, mc.player.motionZ)) {
				if (mc.timer.timerSpeed >= 1.0f && this.stage == 2) {
					mc.timer.timerSpeed = 1.2f;
				} else if (mc.timer.timerSpeed > 1.0f) {
					mc.timer.timerSpeed = 1.0f;
				}
				if (this.stage == 2) {
					event.setOnGround(false);
					if (mc.world.getEntitiesWithinAABBExcludingEntity(mc.player, mc.player.getEntityBoundingBox().offset(0.0, 0.42, 0.0)).isEmpty()) {
						event.setY(event.getY() + 0.42);
					} else {
						event.setY(event.getY() + 0.2);
					}
				}
			} else {
				if (MovementUtilis.isOnGround(3.0) && mc.player.fallDistance < 0.1f) {
					this.stage = 0;
					mc.player.motionY -= 9.0;
					return;
				}
				this.stage = -1;
				if (mc.timer.timerSpeed > 1.0f) {
					mc.timer.timerSpeed = 1.0f;
				}

			}
		}
		if (event.isPre()) {
			double xDist = mc.player.posX - mc.player.prevPosX;
			double zDist = mc.player.posZ - mc.player.prevPosZ;
			lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
		}
	}

	public void damage2() {
		for (int i = 0; i < 48; i++) {
			mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.0625D, mc.player.posZ, false));
			mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
			if (i % 3 == 0)
				mc.player.connection.sendPacket(new CPacketKeepAlive(System.currentTimeMillis()));
		}
		mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.0E-6D, mc.player.posZ, false));
		mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
		mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, true));
	}

	public void befasterAAClastest() {
		if (mc.player.moveForward > 0.0f && mc.player.isSprinting()) {
			if (!mc.player.onGround) {
				double zPos;
				double xPos;
				if (mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 0.015, Minecraft.player.posZ)).getBlock().equals((Object) Blocks.AIR)) {
					mc.player.setPosition(Minecraft.player.posX, Minecraft.player.posY - 0.015, Minecraft.player.posZ);
				}
				if (mc.world.getBlockState(
						new BlockPos(xPos = Minecraft.player.posX + (Minecraft.player.posX - Minecraft.player.lastTickPosX) * 15.0, Minecraft.player.posY, zPos = Minecraft.player.posZ + (Minecraft.player.posZ - Minecraft.player.lastTickPosZ) * 15.0))
						.getBlock().equals((Object) Blocks.AIR)) {
					Minecraft.player.setPosition(xPos, Minecraft.player.posY, zPos);
				}
				double randomSpeed = Math.random() * 2.1;
				randomSpeed = Math.abs((double) randomSpeed);
				mc.player.motionX += Minecraft.player.motionX * (randomSpeed * 0.003567);
				mc.player.motionZ += Minecraft.player.motionZ * (randomSpeed * 0.003567);
				mc.player.speedInAir *= 1.0001f;
			} else if (mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + 1.15E-10, Minecraft.player.posZ)).getBlock().equals((Object) Blocks.AIR)) {
				mc.player.setPosition(mc.player.posX, Minecraft.player.posY + 1.1500115E-12, Minecraft.player.posZ);
				mc.player.motionX *= 1.0123541355133057;
				mc.player.motionZ *= 1.0123541355133057;
			}
		}
	}

	public double round(final double value, final int places) {
		if (places < 0) {
			throw new IllegalArgumentException();
		}
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	@Override
	public void onEnable() {
		if (mc.player != null) {
			moveSpeed = MovementUtilis.getBaseMoveSpeed();
			this.less = 0.0D;
			lastDist = 0.0;
			stage = 2;
			state3 = 0;
			state4 = 0;
			mc.timer.timerSpeed = 1;
			this.damageTimer.reset();
		}
		super.onEnable();
	}

	public void onDisable() {
		if (!mc.gameSettings.isKeyDown(mc.gameSettings.keyBindJump))
			mc.gameSettings.keyBindJump.pressed = false;
		if (!mc.gameSettings.isKeyDown(mc.gameSettings.keyBindForward))
			mc.gameSettings.keyBindForward.pressed = false;
		mc.player.speedInAir = 0.02F;
		mc.timer.timerSpeed = 1;
		stage = 1;
		this.moveSpeed = MovementUtilis.getBaseMoveSpeed();
		mc.timer.timerSpeed = 1f;
		mc.player.speedInAir = 0.02f;
		mc.gameSettings.keyBindJump.pressed = false;
		super.onDisable();
	}

}