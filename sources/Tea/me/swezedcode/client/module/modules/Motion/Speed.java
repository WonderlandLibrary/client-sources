package me.swezedcode.client.module.modules.Motion;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;
import com.darkmagician6.eventapi.types.EventType;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.module.modules.Options.MemeNames;
import me.swezedcode.client.module.modules.Player.NoSlow;
import me.swezedcode.client.module.modules.Visual.NameTags;
import me.swezedcode.client.utils.ModuleUtils;
import me.swezedcode.client.utils.block.BlockHelper;
import me.swezedcode.client.utils.events.EventMotion;
import me.swezedcode.client.utils.events.EventMove;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import me.swezedcode.client.utils.timer.TimerUtils;
import me.swezedcode.client.utils.values.BooleanValue;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;

public class Speed extends Module {

	public Speed() {
		super("Speed", Keyboard.KEY_Z, 0xFFE218F5, ModCategory.Motion);
	}

	private int timeState;
	public int state;
	public double moveSpeed;
	private double lastDist;
	private int cooldownHops;
	private boolean wasOnWater = false;
	private boolean doTime = true;
	private int tState = 0;
	double y = 0.0D;

	public BooleanValue slowHop = new BooleanValue(this, "SlowHop", "slowhop", true);
	public BooleanValue BHop = new BooleanValue(this, "Bhop", "bhop", false);
	public BooleanValue yPort = new BooleanValue(this, "yPort", "yport", false);
	public BooleanValue hy = new BooleanValue(this, "Hypixel", "hypixel", false);

	@EventListener
	public void onUpdate(EventPreMotionUpdates event) { }

	@EventListener
	public void onMotion(EventMotion event) {
		if(yPort.getValue()) {
			if(event.getType() != EventType.PRE) return;
			if(BlockHelper.isOnLiquid() || BlockHelper.isOnLiquid() || BlockHelper.isUnderBlock()){
	            return;
	        }
	        if(event.getType() == EventType.PRE && mc.thePlayer.onGround && mc.thePlayer.isMoving()){
	            if(mc.thePlayer.ticksExisted % 2 != 0) event.setLocation(event.getLocation().add(0, 0.4, 0));
	            mc.thePlayer.setSpeed(mc.thePlayer.ticksExisted % 2 == 0 ? 0.348F : 0.2f);
	            mc.timer.timerSpeed = 1.095f;
	        }
		}
		if (hy.getValue()) {
			if(event.getType() != EventType.PRE) return;
			if(BlockHelper.isOnLiquid() || BlockHelper.isOnLiquid() || BlockHelper.isUnderBlock()){
	            return;
	        }
	        if(event.getType() == EventType.PRE && mc.thePlayer.onGround && mc.thePlayer.isMoving()){
	            if(mc.thePlayer.ticksExisted % 2 != 0) event.setLocation(event.getLocation().add(0, 0.4, 0));
	            mc.thePlayer.setSpeed(mc.thePlayer.ticksExisted % 2 == 0 ? 0.45F : 0.2f);
	            mc.timer.timerSpeed = 1.095f;
	        }
		}
	}

	@EventListener
	public void onSpeed(EventMove event) {
		if (hy.getValue()) {
			if(!MemeNames.enabled) {
				setDisplayName(getName() + " §7Hypixel");
			}else{
				setDisplayName("RunFatass §7Hypixel");
			}
		}else if(yPort.getValue()) {
			if(!MemeNames.enabled) {
				setDisplayName(getName() + " §7yPort");
			}else{
				setDisplayName("RunFatass §7yPort");
			}
		}else if (BHop.getValue()) {
			if(!MemeNames.enabled) {
				setDisplayName("Speed §7Bhop");
			}else{
				setDisplayName("RunFatass §7Bhop");
			}
			if ((BlockHelper.isOnLiquid()) || (BlockHelper.isInLiquid())) {
				this.cooldownHops = 3;
				return;
			}
			if ((this.mc.thePlayer.isEntityInsideOpaqueBlock() || (this.mc.thePlayer.isOnLadder()))) {
				this.moveSpeed = 0.0D;
				this.wasOnWater = true;
				return;
			}
			if (this.wasOnWater) {
				this.moveSpeed = 0.0D;
				this.wasOnWater = false;
				return;
			}
			if ((this.mc.thePlayer.moveForward == 0.0F) && (this.mc.thePlayer.moveStrafing == 0.0F)) {
				if (!this.mc.thePlayer.isMoving()) {
					this.moveSpeed = (getBaseMoveSpeed() * 0.87D);
				} else {
					this.moveSpeed = (getBaseMoveSpeed() * 0.5D);
				}
				return;
			}
			if (this.mc.thePlayer.onGround) {
				this.state = 2;
				this.mc.timer.timerSpeed = 1.0F;
				this.timeState += 1;
				if (this.timeState > 4) {
					this.timeState = 0;
				}
				if (TimerUtils.hD(3L)) {
					this.doTime = (!this.doTime);
					TimerUtils.rt();
				}
				this.mc.thePlayer.motionY *= 1.049999952316284D;
			}
			if (round(this.mc.thePlayer.posY - (int) this.mc.thePlayer.posY, 3) == round(0.138D, 3)) {
				this.mc.thePlayer.motionY -= 0.08D;
				event.y -= 0.09316090325960147D;
				this.mc.thePlayer.posY -= 0.09316090325960147D;
			}
			if ((this.state == 1)
					&& ((this.mc.thePlayer.moveForward != 0.0F) || (this.mc.thePlayer.moveStrafing != 0.0F))) {
				this.state = 2;
				this.moveSpeed = (1.35D * getBaseMoveSpeed() - 0.01D);
			} else if (this.state == 2) {
				this.state = 3;
				if ((this.mc.thePlayer.moveForward != 0.0F) || (this.mc.thePlayer.moveStrafing != 0.0F)) {
					this.mc.thePlayer.motionY = 0.4D;
					event.y = 0.4D;
					if (this.cooldownHops > 0) {
						this.cooldownHops -= 1;
					}
					this.moveSpeed *= 2.149D;
				}
			} else if (this.state == 3) {
				this.state = 4;
				double difference = 0.66D * (this.lastDist - getBaseMoveSpeed());
				this.moveSpeed = (this.lastDist - difference);
			} else {
				if ((this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer,
						this.mc.thePlayer.boundingBox.offset(0.0D, this.mc.thePlayer.motionY, 0.0D)).size() > 0)
						|| (this.mc.thePlayer.isCollidedVertically)) {
					this.state = 1;
				}
				this.moveSpeed = (this.lastDist - this.lastDist / 159.0D);
			}
			this.moveSpeed = Math.max(this.moveSpeed, getBaseMoveSpeed());
			MovementInput movementInput = this.mc.thePlayer.movementInput;
			float forward = movementInput.moveForward;
			float strafe = movementInput.moveStrafe;
			float yaw = this.mc.thePlayer.rotationYaw;
			if ((forward == 0.0F) && (strafe == 0.0F)) {
				event.x = 0.0D;
				event.z = 0.0D;
			} else if (forward != 0.0F) {
				if (strafe >= 1.0F) {
					yaw += (forward > 0.0F ? -45 : 45);
					strafe = 0.0F;
				} else if (strafe <= -1.0F) {
					yaw += (forward > 0.0F ? 45 : -45);
					strafe = 0.0F;
				}
				if (forward > 0.0F) {
					forward = 1.0F;
				} else if (forward < 0.0F) {
					forward = -1.0F;
				}
			}
			double mx = Math.cos(Math.toRadians(yaw + 90.0F));
			double mz = Math.sin(Math.toRadians(yaw + 90.0F));
			double motionX = forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz;
			double motionZ = forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx;
			if ((this.cooldownHops == 0)) {
				if (((this.mc.thePlayer.isUsingItem()) || (this.mc.thePlayer.isBlocking()))
						&& (!ModuleUtils.getMod(NoSlow.class).isToggled())) {
					motionX *= 0.4000000059604645D;
					motionZ *= 0.4000000059604645D;
				}
				event.x = (forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz);
				event.z = (forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx);
			}
			this.mc.thePlayer.stepHeight = 0.6F;
			if ((forward == 0.0F) && (strafe == 0.0F)) {
				event.x = 0.0D;
				event.z = 0.0D;
			} else {
				boolean collideCheck = false;
				if (this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer,
						this.mc.thePlayer.boundingBox.expand(0.5D, 0.0D, 0.5D)).size() > 0) {
					collideCheck = true;
				}
				if (forward != 0.0F) {
					if (strafe >= 1.0F) {
						yaw += (forward > 0.0F ? -45 : 45);
						strafe = 0.0F;
					} else if (strafe <= -1.0F) {
						yaw += (forward > 0.0F ? 45 : -45);
						strafe = 0.0F;
					}
					if (forward > 0.0F) {
						forward = 1.0F;
					} else if (forward < 0.0F) {
						forward = -1.0F;
					}
				}
			}
		} else if (slowHop.getValue()) {
			if(!MemeNames.enabled) {
				setDisplayName("Speed §7SlowHop");
			}else{
				setDisplayName("RunFatass §7SlowHop");
			}
			if ((BlockHelper.isOnLiquid()) || (BlockHelper.isInLiquid())) {
				this.cooldownHops = 2;
				return;
			}
			if ((mc.thePlayer.isOnLadder()) || (mc.thePlayer.isEntityInsideOpaqueBlock())) {
				this.moveSpeed = 0.0D;
				this.wasOnWater = true;
				return;
			}
			if (this.wasOnWater) {
				this.moveSpeed = 0.0D;
				this.wasOnWater = false;
				return;
			}
			if ((mc.thePlayer.moveForward == 0.0F) && (mc.thePlayer.moveStrafing == 0.0F)) {
				if (!mc.thePlayer.isMoving()) {
					this.moveSpeed = (getBaseMoveSpeed() * 0.87D);
				} else {
					this.moveSpeed = (getBaseMoveSpeed() * 0.5D);
				}
				return;
			}
			if (mc.thePlayer.onGround) {
				this.state = 2;
				mc.timer.timerSpeed = 1.0F;
				this.timeState += 1;
				if (this.timeState > 4) {
					this.timeState = 0;
				}
				if (TimerUtils.hasReached(3000L)) {
					this.doTime = (!this.doTime);
					TimerUtils.rt();
				}
				mc.thePlayer.motionY *= 1.0499999523162842D;
			}
			if (round(mc.thePlayer.posY - (int) mc.thePlayer.posY, 3) == round(0.138D, 3)) {
				event.y -= 0.09316090325960147D;
				mc.thePlayer.posY -= 0.09316090325960147D;
			}
			if ((this.state == 1) && ((mc.thePlayer.moveForward != 0.0F) || (mc.thePlayer.moveStrafing != 0.0F))) {
				this.state = 2;
				this.moveSpeed = (1.35D * getBaseMoveSpeed() - 0.01D);
			} else if (this.state == 2) {
				this.state = 3;
				if ((mc.thePlayer.moveForward != 0.0F) || (mc.thePlayer.moveStrafing != 0.0F)) {
					this.mc.timer.timerSpeed = 1.0f;
					mc.thePlayer.motionY = 0.42D;
					event.y = 0.42D;
					if (this.cooldownHops > 0) {
						this.cooldownHops -= 1;
					}
					this.moveSpeed *= 2.149D;
				}
			} else if (this.state == 3) {
				this.state = 4;
				double difference = 0.66D * (this.lastDist - getBaseMoveSpeed());
				this.moveSpeed = (this.lastDist - difference);
			} else {
				if ((Minecraft.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer,
						this.mc.thePlayer.boundingBox.offset(0.0D, mc.thePlayer.motionY, 0.0D)).size() > 0)
						|| (this.mc.thePlayer.isCollidedVertically)) {
					this.state = 1;
				}
				this.mc.timer.timerSpeed = (this.mc.thePlayer.ticksExisted % 2 == 0 ? 1.025F : 1.003F);
				this.moveSpeed = (this.lastDist - this.lastDist / 27.0D);
			}
			this.moveSpeed = Math.max(this.moveSpeed, getBaseMoveSpeed());
			MovementInput movementInput = this.mc.thePlayer.movementInput;
			float forward = movementInput.moveForward;
			float strafe = movementInput.moveStrafe;
			float yaw = this.mc.thePlayer.rotationYaw;
			if ((forward == 0.0F) && (strafe == 0.0F)) {
				event.x = 0.0D;
				event.z = 0.0D;
			} else if (forward != 0.0F) {
				if (strafe >= 1.0F) {
					yaw += (forward > 0.0F ? -45 : 45);
					strafe = 0.0F;
				} else if (strafe <= -1.0F) {
					yaw += (forward > 0.0F ? 45 : -45);
					strafe = 0.0F;
				}
				if (forward > 0.0F) {
					forward = 1.0F;
				} else if (forward < 0.0F) {
					forward = -1.0F;
				}
			}
			double mx = Math.cos(Math.toRadians(yaw + 90.0F));
			double mz = Math.sin(Math.toRadians(yaw + 90.0F));
			double motionX = forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz;
			double motionZ = forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx;
			if (((this.mc.thePlayer.isUsingItem()) || (this.mc.thePlayer.isBlocking()))
					&& (!ModuleUtils.getMod(NoSlow.class).isToggled())) {
				mc.timer.timerSpeed = 1.0f;
				motionX *= 0.4000000059604645D;
				motionZ *= 0.4000000059604645D;
			}
			event.x = (forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz);
			event.z = (forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx);

			this.mc.thePlayer.stepHeight = 0.6F;
			if ((forward == 0.0F) && (strafe == 0.0F)) {
				event.x = 0.0D;
				event.z = 0.0D;
			} else {
				boolean collideCheck = false;
				if (Minecraft.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer,
						this.mc.thePlayer.boundingBox.expand(0.5D, 0.0D, 0.5D)).size() > 0) {
					collideCheck = true;
				}
				if (forward != 0.0F) {
					if (strafe >= 1.0F) {
						yaw += (forward > 0.0F ? -45 : 45);
						strafe = 0.0F;
					} else if (strafe <= -1.0F) {
						yaw += (forward > 0.0F ? 45 : -45);
						strafe = 0.0F;
					}
					if (forward > 0.0F) {
						forward = 1.0F;
					} else if (forward < 0.0F) {
						forward = -1.0F;
					}
				}
			}
		}
	}

	public double getBaseMoveSpeed() {
		double boostSpeed = 0.2873D;
		if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
			int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
			boostSpeed *= (1.0D + 0.2D * (amplifier + 1));
		}
		return boostSpeed;
	}

	@EventListener
	private void c(EventPreMotionUpdates event) {
		boolean speedy = mc.thePlayer.isPotionActive(Potion.moveSpeed);
		double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
		double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
		this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
	}

	@Override
	public void onEnable() {
		if (slowHop.getValue()) {
			this.cooldownHops = 2;
			this.state = 0;
			this.moveSpeed = (getBaseMoveSpeed() * 0.800000011920929D);
		}
		if(hy.getValue()) {
			this.cooldownHops = 2;
			this.state = 0;
		}
		if(BHop.getValue()) {
			this.cooldownHops = 2;
			this.state = 3;
			this.moveSpeed = (getBaseMoveSpeed() * 0.800000011920929D);
		}
	}

	@Override
	public void onDisable() {

	}

	public static void setSpeed(float speed) {
		mc.thePlayer.motionX = (-(Math.sin(getDirection()) * speed));
		mc.thePlayer.motionZ = (Math.cos(getDirection()) * speed);
	}

	public double round(double value, int places) {
		if (places < 0) {
			throw new IllegalArgumentException();
		}
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static float getDirection() {
		float var1 = mc.thePlayer.rotationYaw;
		if (mc.thePlayer.moveForward < 0.0F) {
			var1 += 180.0F;
		}
		float forward = 1.0F;
		if (mc.thePlayer.moveForward < 0.0F) {
			forward = -0.5F;
		} else if (mc.thePlayer.moveForward > 0.0F) {
			forward = 0.5F;
		} else {
			forward = 1.0F;
		}
		if (mc.thePlayer.moveStrafing > 0.0F) {
			var1 -= 90.0F * forward;
		}
		if (mc.thePlayer.moveStrafing < 0.0F) {
			var1 += 90.0F * forward;
		}
		var1 *= 0.017453292F;

		return var1;
	}

}
