package zamorozka.modules.COMBAT;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.util.math.MathHelper;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPacket;
import zamorozka.event.events.EventReceivePacket;
import zamorozka.event.events.EventSendPacket;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.ui.AngleUtil;
import zamorozka.ui.ClientUtils;
import zamorozka.ui.MovementUtilis;
import zamorozka.ui.MovementUtils;
import zamorozka.ui.Wrapper;

public class VelocityPercentage extends Module {

	private double motionX;
	private double motionZ;

	public VelocityPercentage() {
		super("Velocity", Keyboard.KEY_NONE, Category.COMBAT);
	}

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Custom");
		options.add("Cancel");
		options.add("AirStrafe");
		options.add("Slowdown");
		options.add("OldAAC");
		options.add("MatrixTest");
		options.add("NewMatrixFlagless");
		options.add("NewMatrix");
		options.add("MatrixOLD");
		Zamorozka.instance.settingsManager.rSetting(new Setting("Velocity Mode", this, "Cancel", options));
		Zamorozka.instance.settingsManager.rSetting(new Setting("CancelChance", this, 100, 0, 100, true));
		Zamorozka.instance.settingsManager.rSetting(new Setting("CustomHorizontal", this, 100, 0, 100, true));
		Zamorozka.instance.settingsManager.rSetting(new Setting("CustomVertical", this, 100, 0, 100, true));
	}

	@EventTarget
	public void onPacket1(EventPacket event) {
		if (nullCheck() && !getState())
			return;
		String mode = Zamorozka.instance.settingsManager.getSettingByName("Velocity Mode").getValString();
		String modeput = Character.toUpperCase(mode.charAt(0)) + mode.substring(1);
		if (mode.equalsIgnoreCase("Custom")) {
			if (getState()) {
				double hori = Zamorozka.settingsManager.getSettingByName("CustomHorizontal").getValDouble();
				double vert = Zamorozka.settingsManager.getSettingByName("CustomVertical").getValDouble();
				this.setDisplayName("Velocity §f§ " + "Custom, " + "H: " + (int) hori + "% " + "V: " + (int) vert + "%");
				if (event.getPacket() instanceof SPacketEntityVelocity) {
					Entity entity = mc.getConnection().clientWorldController.getEntityByID(((SPacketEntityVelocity) event.getPacket()).getEntityID());
					if (entity instanceof EntityPlayerSP) {
						SPacketEntityVelocity vel = ((SPacketEntityVelocity) event.getPacket());
						if (hori == 0 && vert == 0) {
							event.setCancelled(true);
							return;
						}
						if (hori != 100) {
							vel.motionX = (int) (vel.motionX / 100 * hori);
							vel.motionZ = (int) (vel.motionZ / 100 * hori);
						}

						if (vert != 100) {
							vel.motionY = (int) (vel.motionY / 100 * vert);
						}
					}
				}
				if (event.getPacket() instanceof SPacketExplosion) {
					SPacketExplosion vel = ((SPacketExplosion) event.getPacket());
					if (hori == 0 && vert == 0) {
						event.setCancelled(true);
						return;
					}
					if (hori != 100) {
						vel.motionX = (int) (vel.motionX / 100 * hori);
						vel.motionZ = (int) (vel.motionZ / 100 * hori);
					}

					if (vert != 100) {
						vel.motionY = (int) (vel.motionY / 100 * vert);
					}

				}
			}
		}
	}

	@EventTarget
	public void onPacket(EventPacket event) {
		if (nullCheck())
			return;
		if (ModuleManager.getModule(VelocityPercentage.class).getState()) {
			String mode = Zamorozka.instance.settingsManager.getSettingByName("Velocity Mode").getValString();
			String modeput = Character.toUpperCase(mode.charAt(0)) + mode.substring(1);
			if (!mode.equalsIgnoreCase("Custom")) {
				if (mode.equalsIgnoreCase("Cancel")) {
					this.setDisplayName("Velocity §f§ " + modeput + ", Chance: " + (int) Zamorozka.settingsManager.getSettingByName("CancelChance").getValDouble() + "%");
				} else {
					this.setDisplayName("Velocity §f§ " + modeput);
				}
			}
			if (mode.equalsIgnoreCase("Cancel")) {
				if (event.getPacket() instanceof SPacketExplosion) {
					event.setCancelled(true);
				}
			}
			if (mode.equalsIgnoreCase("NewMatrix")) {
				this.setDisplayName("Velocity §f§ " + "NewMatrix");
				if (mc.player.hurtResistantTime > 13 && mc.player.hurtResistantTime < 20 && !mc.player.onGround) {
					if (mc.player.hurtResistantTime == 16) {
						mc.player.motionX *= 0.25;
						mc.player.motionZ *= 0.25;
					}
				}
			}

			if (mode.equalsIgnoreCase("MatrixOLD")) {
				if (this.mc.player.hurtTime == 9) {
					this.motionX = this.mc.player.motionX;
					this.motionZ = this.mc.player.motionZ;
				} else if (this.mc.player.hurtResistantTime == 15) {
					this.mc.player.motionX = -this.motionX * 0.275D;
					this.mc.player.motionZ = -this.motionZ * 0.75D;
					mc.player.motionY = -0.0535f;
					mc.player.setSpeed(0.2f);
				}
			}

			if (mode.equalsIgnoreCase("NewMatrixFlagless")) {
				this.setDisplayName("Velocity §f§ " + "NewMatrixFlagless");
				if (mc.player.hurtResistantTime == 15 && !nullCheck()) {
					mc.player.motionX *= 0.75;
					mc.player.motionZ *= 0.75;
					mc.player.motionY *= -0.75;
					double x = mc.player.motionX * 0;
					double z = mc.player.motionZ * 0;
					mc.player.setVelocity(x, mc.player.motionY, z);
				}
			}
			if (mode.equalsIgnoreCase("AirStrafe")) {
				if (mc.player.hurtTime > 0) {
					MovementUtils.strafe();
					/*mc.player.motionX *= 0.9;
					mc.player.motionZ *= 0.9;*/
				}
			}
			if (mode.equalsIgnoreCase("Slowdown")) {
				if (mc.player.hurtTime > 0) {
					mc.player.motionX = 0;
					mc.player.motionY = 0;
					mc.player.motionZ = 0;

				} else
					mc.player.getAir();
			}
			if (mode.equalsIgnoreCase("OldAAC")) {
				boolean shalopay = false;
				if (mc.player.hurtTime == 9 & shalopay & mc.player.onGround) {
					shalopay = false;
				}
				if (mc.player.hurtTime > 0 & mc.player.hurtTime <= 7) {
					mc.player.motionX *= 0.5;
					mc.player.motionZ *= 0.5;
				}
				if (mc.player.hurtTime == 5) {
					mc.player.motionX = 0.0;
					mc.player.motionZ = 0.0;
					shalopay = true;
				}
				if (mc.player.hurtTime == 4) {
					final double playerYaw = Math.toRadians(mc.player.rotationYaw);
					mc.player.setPosition(mc.player.posX - (Math.sin(playerYaw) * 0.05), mc.player.posY, mc.player.posZ);
				}
			}
		}
	}

	private void matrix1() {
		if (mc.player.hurtTime == 9) {
			if (mc.player.onGround) {
				mc.player.capabilities.allowFlying = true;
				double velocity = mc.player.rotationYawHead;
				velocity = Math.toRadians(velocity);
				double motionX = Math.sin(velocity) * 0.053;
				double motionZ = Math.sin(velocity) * 0.039;
				final double motionY = -Math.sin(velocity) * 0.1;
				motionX = mc.player.motionX;
				motionZ = mc.player.motionZ;
			} else {
				mc.player.motionX = 0.0;
				mc.player.motionY = 0.0;
				mc.player.motionZ = 0.0;
			}
		} else {
			if (mc.player.hurtTime == 8) {
				if (mc.player.onGround) {
					final double motionX2 = 0.048;
					final double motionZ2 = 0.036;
					mc.player.motionX = -motionX2 * 0.11;
					mc.player.motionZ = -motionZ2 * 0.11;
					mc.timer.timerSpeed = 0.9f;
				} else {
					mc.player.motionX = 0.0;
					mc.player.motionY = 0.0;
					mc.player.motionZ = 0.0;
					mc.timer.timerSpeed = 1f;
				}
			}
		}
	}

	private void matrix2() {
		if (this.mc.player.hurtTime > 1 && this.mc.player.hurtTime < 10) {
			this.mc.player.motionX *= -3.25;
			this.mc.player.motionZ *= -3.25;
			if (this.mc.player.hurtTime == 5) {
				mc.player.motionY = -9.0;
			}
		}
	}

	private void matrix3() {
		if (mc.player.hurtTime > 0.0) {
			if (mc.player.onGround) {
				double yaw = mc.player.rotationYawHead;
				yaw = Math.toRadians(yaw);
				final double dX = Math.sin(yaw) * 0.0;
				final double dZ = -Math.sin(yaw) * 0.0;
				mc.player.motionX = dZ;
				mc.player.motionZ = dZ;
			}
		}
	}

	private void matrix4() {
		if (mc.player.hurtTime > 0.0) {
			if (mc.player.onGround) {
				double yaw = mc.player.rotationYawHead;
				yaw = Math.toRadians(yaw);
				final double dX = -Math.sin(yaw) * 0.045;
				final double dZ = -Math.sin(yaw) * 0.045;
				mc.player.motionX = dZ;
				mc.player.motionZ = dZ;
			}
		}
	}
}