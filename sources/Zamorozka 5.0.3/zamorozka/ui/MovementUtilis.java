package zamorozka.ui;

import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.block.BlockPressurePlate.Sensitivity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketConfirmTransaction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketSpectate;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import zamorozka.event.events.EventMove;
import zamorozka.main.Zamorozka;
import zamorozka.module.ModuleManager;
import zamorozka.modules.COMBAT.KillAura;
import zamorozka.modules.COMBAT.TargetStrafe;
import zamorozka.modules.TRAFFIC.SpeedHack;

public class MovementUtilis implements MCUtil {
	public static boolean status;
	private static int cunt;

	public static int getSpeedEffect() {
		if (mc.player.isPotionActive(Potion.getPotionById(1)))
			return mc.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier() + 1;
		else
			return 0;
	}
	
    public static void teleportForwardPacket(float power) {
        final double playerYaw = Math.toRadians(mc.player.rotationYaw);
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX + power * -Math.sin(playerYaw), mc.player.posY, mc.player.posZ + power * Math.cos(playerYaw), true));
    }

	public static void setSpeedClean(EventMove moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
		double forward = pseudoForward;
		double strafe = pseudoStrafe;
		float yaw = pseudoYaw;
		if (forward != 0.0D) {
			if (strafe > 0.0D) {
				yaw += ((forward > 0.0D) ? -45 : 45);
			} else if (strafe < 0.0D) {
				yaw += ((forward > 0.0D) ? 45 : -45);
			}
			strafe = 0.0D;
			if (forward > 0.0D) {
				forward = 1.0D;
			} else if (forward < 0.0D) {
				forward = -1.0D;
			}
		}
		if (strafe > 0.0D) {
			strafe = 1.0D;
		} else if (strafe < 0.0D) {
			strafe = -1.0D;
		}
		double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
		double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
		moveEvent.setX(forward * moveSpeed * mx + strafe * moveSpeed * mz);
		moveEvent.setZ(forward * moveSpeed * mz - strafe * moveSpeed * mx);
	}

	private static boolean isMovingEnoughForSprint() {
		MovementInput movementInput = Wrapper.getPlayer().movementInput;
		return movementInput.moveForward > 0.8F || movementInput.moveForward < -0.8F || movementInput.moveStrafe > 0.8F || movementInput.moveStrafe < -0.8F;
	}

	/*     */ public static double getXDirAt(float angle) {
		/* 151 */ Minecraft mc = Minecraft.getMinecraft();
		/* 152 */ double rot = 90.0D;
		/* 153 */ rot += angle;
		/* 154 */ return Math.cos(rot * Math.PI / 180.0D);
		/*     */ }

	/*     */
	/*     */ public static double getZDirAt(float angle) {
		/* 158 */ Minecraft mc = Minecraft.getMinecraft();
		/* 159 */ double rot = 90.0D;
		/* 160 */ rot += angle;
		/* 161 */ return Math.sin(rot * Math.PI / 180.0D);
		/*     */ }

	/*     */
	/*     */ public static void setSpeedAt(float angle, double speed) {
		/* 165 */ Minecraft mc = Minecraft.getMinecraft();
		/* 166 */ mc.player.motionX = getXDirAt(angle) * speed;
		/* 167 */ mc.player.motionZ = getZDirAt(angle) * speed;
		/*     */ }

	/*     */
	/*     */ public static void setSpeedAt(EventMove e, float angle, double speed) {
		/* 171 */ Minecraft mc = Minecraft.getMinecraft();
		if (!mc.gameSettings.keyBindJump.isKeyDown() && mc.player.onGround) {
			if (!(mc.player.getDistanceToEntity(KillAura.target) <= 1f)) {
				/* 172 */ e.setX(getXDirAt(angle) * speed);

				/* 173 */ e.setZ(getZDirAt(angle) * speed);
				/*     */ }
		}
	}

	public static double getJumpMotion() {
		/* 231 */ Minecraft mc = Minecraft.getMinecraft();
		/* 232 */ double oldMotion = mc.player.motionY;
		/* 233 */ double oldX = mc.player.motionX;
		/* 234 */ double oldZ = mc.player.motionZ;
		/* 235 */ mc.player.jump();
		/* 236 */ double jumpMotion = mc.player.motionY;
		/* 237 */ mc.player.motionY = oldMotion;
		/* 238 */ mc.player.motionX = oldX;
		/* 239 */ mc.player.motionZ = oldZ;
		/* 240 */ return jumpMotion;
		/*     */ }

	public static void packetJump(Boolean onGround) {
		/* 351 */ Minecraft mc = Minecraft.getMinecraft();
		/* 352 */ mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + getJumpMotion(), mc.player.posZ, false));
		/* 353 */ mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.75320000648499D, mc.player.posZ, false));
		/* 354 */ mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.00133601766969D, mc.player.posZ, false));
		/* 355 */ mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.16610931183763D, mc.player.posZ, false));
		/* 356 */ mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.24918714173913D, mc.player.posZ, false));
		/* 357 */ mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.17078714021325D, mc.player.posZ, false));
		/* 358 */ mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.01555513569664D, mc.player.posZ, false));
		/* 359 */ mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.78502776678368D, mc.player.posZ, false));
		/* 360 */ mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.48071093932614D, mc.player.posZ, false));
		/* 361 */ mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.10408044108749D, mc.player.posZ, false));
		/* 362 */ mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, onGround.booleanValue()));
		/*     */ }

	/*
	 * public static void setSpeed(MoveEntityEvent e, double speed) { final
	 * EntityPlayerSP player = Wrapper.getPlayer(); final TargetStrafe targetStrafe
	 * = TargetStrafe.getInstance(); final KillAura killAura =
	 * KillAura.getInstance(); if (targetStrafe.isEnabled() &&
	 * (!targetStrafe.holdSpaceProperty.getValue() ||
	 * Keyboard.isKeyDown(Keyboard.KEY_SPACE))) { final EntityLivingBase target =
	 * killAura.getTarget(); if (target != null) { float dist =
	 * Wrapper.getPlayer().getDistanceToEntity(target); double radius =
	 * targetStrafe.radiusProperty.getValue(); setSpeed(e, speed, dist <= radius +
	 * 1.0E-4D ? 0 : 1, dist <= radius + 1.0D ? targetStrafe.direction : 0,
	 * RotationUtils.getYawToEntity(target, false)); return; } } setSpeed(e, speed,
	 * player.moveForward, player.moveStrafing,
	 * killAura.aacRotationStrafing.getValue() ? player.currentEvent.getYaw() :
	 * player.rotationYaw); }
	 */

	public static void setSpeed(EventMove e, double speed, float forward, float strafing, float yaw) {
		boolean reversed = forward < 0.0f;
		float strafingYaw = 90.0f * (forward > 0.0f ? 0.5f : reversed ? -0.5f : 1.0f);

		if (reversed)
			yaw += 180.0f;
		if (strafing > 0.0f)
			yaw -= strafingYaw;
		else if (strafing < 0.0f)
			yaw += strafingYaw;

		double x = Math.cos(Math.toRadians(yaw + 90.0f));
		double z = Math.cos(Math.toRadians(yaw));

		e.setX(x * speed);
		e.setZ(z * speed);
	}

	public static Block getBlockUnder() {
		EntityPlayerSP player = Wrapper.getPlayer();
		return Wrapper.getWorld().getBlockState(new BlockPos(player.posX, Math.floor(player.getEntityBoundingBox().minY) - 1, player.posZ)).getBlock();
	}

	public static boolean isOnIce() {
		Block blockUnder = getBlockUnder();
		return blockUnder instanceof BlockIce || blockUnder instanceof BlockPackedIce;
	}

	public static void setMotion1(EventMove e, double speed) {
		double forward = mc.player.movementInput.moveForward;
		double strafe = mc.player.movementInput.moveStrafe;
		float yaw = mc.player.rotationYaw;
		if (forward == 0.0 && strafe == 0.0) {
			if (e != null) {
				e.setX(0);
				e.setZ(0);
			} else {
				mc.player.motionX = 0.0;
				mc.player.motionZ = 0.0;
			}
		} else {
			if (forward != 0.0) {
				if (strafe > 0.0) {
					yaw += ((forward > 0.0) ? -45 : 45);
				} else if (strafe < 0.0) {
					yaw += ((forward > 0.0) ? 45 : -45);
				}
				strafe = 0.0;
				if (forward > 0.0) {
					forward = 1.0;
				} else if (forward < 0.0) {
					forward = -1.0;
				}
			}
			if (e != null) {
				e.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 91.50F)));
				e.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.25F)));
			} else {
				mc.player.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 91.50F));
				mc.player.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.25F));
			}
		}
	}

	public boolean shouldSprint() {
		final boolean hasFood = this.mc.player.getFoodStats().getFoodLevel() > 6;
		final boolean isNotCollided = !this.mc.player.isCollidedHorizontally;
		final boolean isMoving = this.mc.player.motionX != 0.0 || this.mc.player.motionZ != 0.0;
		final boolean isSneaking = !this.mc.player.isSneaking();
		return hasFood && isSneaking && isNotCollided && isMoving;
	}

	/**
	 * teleport local player relative to local players coordinates
	 * 
	 * @param x x
	 * @param y y
	 * @param z z
	 */
	public static void setPos(double x, double y, double z) {
		mc.player.setPosition(x, y, z);
	}

	public static void setPosPlus(double x, double y, double z) {
		mc.player.setPosition(mc.player.posX + x, mc.player.posY + y, mc.player.posZ + z);
	}

	public static void setTpSpeedAndUpdate(final double speed) {
		final double dX = -Math.sin(getDirection()) * speed;
		final double dZ = Math.cos(getDirection()) * speed;
		mc.player.setPositionAndUpdate(mc.player.posX + dX, mc.player.posY, mc.player.posZ + dZ);
	}

	public boolean shouldStep() {
		final boolean isCollided = this.mc.player.isCollidedHorizontally;
		final boolean onGround = this.mc.player.onGround;
		final boolean onLadder = !this.mc.player.isOnLadder();
		final boolean isInWater = !this.mc.player.isInWater();
		return isCollided && onLadder && isInWater && onGround;
	}

	public void teleport(final double x, final double y, final double z) {
		double playerX = this.mc.player.posX;
		double playerY = this.mc.player.posY;
		double playerZ = this.mc.player.posZ;
		double xDistance = x - playerX;
		double yDistance = y - playerY;
		double zDistance = z - playerZ;
		double distance = Math.sqrt(xDistance * xDistance + yDistance * yDistance + zDistance * zDistance);
		if (distance < 5.0) {
			this.mc.player.setPosition(x, y, z);
			return;
		}
		distance /= 8.0;
		xDistance /= distance;
		yDistance /= distance;
		zDistance /= distance;
		for (int i = 0; i < distance; ++i) {
			playerX += xDistance;
			playerY += yDistance;
			playerZ += zDistance;
			this.mc.player.setPosition(playerX, playerY, playerZ);
			try {
				Thread.sleep(10L);
			} catch (Exception ex) {
			}
		}
		this.mc.player.setPosition(x, y, z);
	}

	public static Block getBlockAtPos(BlockPos inBlockPos) {
		IBlockState s = Minecraft.getMinecraft().world.getBlockState(inBlockPos);
		return s.getBlock();
	}

	public static void vClipPacket(float offset, boolean onGround) {
		if (mc.player.isMoving()) {
			mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + offset, mc.player.posZ, onGround));
		}
	}

	public static void hClipPacket(float offset) {
		if (mc.player.isMoving()) {
			float playerYaw = (float) (mc.player.rotationYaw * Math.PI / 180);
			mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX - (Math.sin(playerYaw) * offset), mc.player.posY, mc.player.posZ + (Math.cos(playerYaw) * offset), false));
		}
	}

	public static void resetMotion() {
		mc.player.motionX = 0.0;
		mc.player.motionY = 0.0;
		mc.player.motionZ = 0.0;
	}

	public static void resetMotionY() {
		mc.player.motionY = 0.0;
	}

	public static float getDirectionAAC() {
		Minecraft mc = Minecraft.getMinecraft();
		float var1 = mc.player.rotationYaw;
		if (mc.player.moveForward < 0.0f) {
			var1 += 180.0f;
		}
		float forward = 1.0f;
		if (mc.player.moveForward < 0.0f) {
			forward = -0.5f;
		} else if (mc.player.moveForward > 0.0f) {
			forward = 0.5f;
		}
		if (mc.player.moveStrafing > 0.0f) {
			var1 -= 90.0f * forward;
		}
		if (mc.player.moveStrafing < 0.0f) {
			var1 += 90.0f * forward;
		}
		return var1 *= 0.017453292f;
	}

	public static float getDistanceToGround(Entity e) {
		if (mc.player.isCollidedVertically && mc.player.onGround) {
			return 0.0F;
		}
		for (float a = (float) e.posY; a > 0.0F; a -= 1.0F) {
			int[] stairs = { 53, 67, 108, 109, 114, 128, 134, 135, 136, 156, 163, 164, 180 };
			int[] exemptIds = { 6, 27, 28, 30, 31, 32, 37, 38, 39, 40, 50, 51, 55, 59, 63, 65, 66, 68, 69, 70, 72, 75, 76, 77, 83, 92, 93, 94, 104, 105, 106, 115, 119, 131, 132, 143, 147, 148, 149, 150, 157, 171, 175, 176, 177 };
			Block block = mc.world.getBlockState(new BlockPos(e.posX, a - 1.0F, e.posZ)).getBlock();
			if (!(block instanceof BlockAir)) {
				if ((Block.getIdFromBlock(block) == 44) || (Block.getIdFromBlock(block) == 126)) {
					return (float) (e.posY - a - 0.5D) < 0.0F ? 0.0F : (float) (e.posY - a - 0.5D);
				}
				int[] arrayOfInt1;
				int j = (arrayOfInt1 = stairs).length;
				for (int i = 0; i < j; i++) {
					int id = arrayOfInt1[i];
					if (Block.getIdFromBlock(block) == id) {
						return (float) (e.posY - a - 1.0D) < 0.0F ? 0.0F : (float) (e.posY - a - 1.0D);
					}
				}
				j = (arrayOfInt1 = exemptIds).length;
				for (int i = 0; i < j; i++) {
					int id = arrayOfInt1[i];
					if (Block.getIdFromBlock(block) == id) {
						return (float) (e.posY - a) < 0.0F ? 0.0F : (float) (e.posY - a);
					}
				}
				return (float) (e.posY - a + block.getBlockBoundsMaxY() - 1.0D);
			}
		}
		return 0.0F;
	}

	public static boolean isBlockAboveHead() {
		AxisAlignedBB bb = new AxisAlignedBB(mc.player.posX - 0.3, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ + 0.3, mc.player.posX + 0.3, mc.player.posY + 2.5, mc.player.posZ - 0.3);
		return !mc.world.getCollisionBoxes(mc.player, bb).isEmpty();
	}

	public static boolean isCollidedH(double dist) {
		AxisAlignedBB bb = new AxisAlignedBB(mc.player.posX - 0.3, mc.player.posY + 2, mc.player.posZ + 0.3, mc.player.posX + 0.3, mc.player.posY + 3, mc.player.posZ - 0.3);
		if (!mc.world.getCollisionBoxes(mc.player, bb.offset(0.3 + dist, 0, 0)).isEmpty()) {
			return true;
		} else if (!mc.world.getCollisionBoxes(mc.player, bb.offset(-0.3 - dist, 0, 0)).isEmpty()) {
			return true;
		} else if (!mc.world.getCollisionBoxes(mc.player, bb.offset(0, 0, 0.3 + dist)).isEmpty()) {
			return true;
		} else if (!mc.world.getCollisionBoxes(mc.player, bb.offset(0, 0, -0.3 - dist)).isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isRealCollidedH(double dist) {
		AxisAlignedBB bb = new AxisAlignedBB(mc.player.posX - 0.3, mc.player.posY + 0.5, mc.player.posZ + 0.3, mc.player.posX + 0.3, mc.player.posY + 1.9, mc.player.posZ - 0.3);
		if (!mc.world.getCollisionBoxes(mc.player, bb.offset(0.3 + dist, 0, 0)).isEmpty()) {
			return true;
		} else if (!mc.world.getCollisionBoxes(mc.player, bb.offset(-0.3 - dist, 0, 0)).isEmpty()) {
			return true;
		} else if (!mc.world.getCollisionBoxes(mc.player, bb.offset(0, 0, 0.3 + dist)).isEmpty()) {
			return true;
		} else if (!mc.world.getCollisionBoxes(mc.player, bb.offset(0, 0, -0.3 - dist)).isEmpty()) {
			return true;
		}
		return false;
	}

	public static double getPosForSetPosX(double value) {
		double yaw = Math.toRadians(Minecraft.getMinecraft().player.rotationYaw);
		double x = -Math.sin(yaw) * value;
		return x;
	}

	public static double getPosForSetPosZ(double value) {
		double yaw = Math.toRadians(Minecraft.getMinecraft().player.rotationYaw);
		double z = Math.cos(yaw) * value;
		return z;
	}

	public static void forward(final double length) {
		final double yaw = Math.toRadians(mc.player.rotationYaw);
		mc.player.setPosition(mc.player.posX + (-Math.sin(yaw) * length), mc.player.posY, mc.player.posZ + (Math.cos(yaw) * length));
	}

	public static void yClip(final double length) {
		final double yaw = Math.toRadians(mc.player.rotationYaw);
		mc.player.setPosition(mc.player.posX, mc.player.posY + (Math.cos(yaw) * length), mc.player.posZ);
	}

	public static double getJumpBoostModifier(double baseJumpHeight) {
		if (mc.player.isPotionActive(Potion.getPotionById(8))) {
			int amplifier = mc.player.getActivePotionEffect(Potion.getPotionById(8)).getAmplifier();
			baseJumpHeight += ((amplifier + 1) * 0.1F);
		}
		return baseJumpHeight;
	}

	public static float getDirection(EntityLivingBase e) {
		float yaw = e.rotationYaw;
		float forward = e.moveForward;
		float strafe = e.moveStrafing;
		yaw += (forward < 0.0F ? 180 : 0);
		if (strafe < 0.0F) {
			yaw += (forward == 0.0F ? 90 : forward < 0.0F ? -45 : 45);
		}
		if (strafe > 0.0F) {
			yaw -= (forward == 0.0F ? 90 : forward < 0.0F ? -45 : 45);
		}
		return yaw * 0.017453292F;
	}

	public static boolean isMoving() {
		return Minecraft.player != null && (Minecraft.player.movementInput.moveForward != 0F || Minecraft.player.movementInput.moveStrafe != 0F);
	}

	public static boolean hasMotion() {
		return Minecraft.player.motionX != 0D && Minecraft.player.motionZ != 0D && Minecraft.player.motionY != 0D;
	}

	public static float getDirection() {
		return MovementUtilis.getDirection(mc.player);
	}

	public static void vClip(double d) {
		mc.player.setPosition(mc.player.posX, mc.player.posY + d, mc.player.posZ);
	}

	public static void hClip(double offset) {
		mc.player.setPosition(mc.player.posX + -MathHelper.sin(getDirection()) * offset, mc.player.posY, mc.player.posZ + MathHelper.cos(getDirection()) * offset);
	}

	public static int getJumpEffect() {
		if (mc.player.isPotionActive(Potion.getPotionById(8)))
			return mc.player.getActivePotionEffect(Potion.getPotionById(8)).getAmplifier() + 1;
		else
			return 0;
	}

	public static boolean isOnGround2(final double height) {
		return !mc.world.getEntitiesWithinAABBExcludingEntity(mc.player, mc.player.getEntityBoundingBox().offset(0.0, -height, 0.0)).isEmpty();
	}

	public static boolean isOnGround(double height) {
		return mc.world.getEntitiesWithinAABBExcludingEntity(mc.player, mc.player.getEntityBoundingBox().offset(0.0D, height, 0.0D)).isEmpty();
	}

	public static boolean isOnGround(final double motionX, final double motionZ) {
		return mc.world.getEntitiesWithinAABBExcludingEntity(mc.player, mc.player.getEntityBoundingBox().offset(motionX, 0.001, motionZ)).isEmpty();
	}

	public static double getBaseMoveSpeed() {
		double baseSpeed = 0.2873;
		if (mc.player.isPotionActive(Potion.getPotionById(1))) {
			final int amplifier = mc.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
			baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
		}
		return baseSpeed;
	}

	public static void setSpeed(EventMove moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
		String md = Zamorozka.settingsManager.getSettingByName("Motion Mode").getValString();
		double forwplus = Zamorozka.settingsManager.getSettingByName("ForwardSpeed").getValDouble();
		double strplus = Zamorozka.settingsManager.getSettingByName("StrafeSpeed").getValDouble();
		if (md.equalsIgnoreCase("EventMotion")) {
			if (Zamorozka.settingsManager.getSettingByName("PotionSpeedReduce").getValBoolean()) {
				if (mc.player.isPotionActive(MobEffects.SPEED)) {
					forwplus = Zamorozka.settingsManager.getSettingByName("ForwardSpeed").getValDouble() / 1.4;
					strplus = Zamorozka.settingsManager.getSettingByName("ForwardSpeed").getValDouble() / 1.4;
				}
			}
		}
		if (md.equalsIgnoreCase("VelocityFIX")) {
			if (Zamorozka.settingsManager.getSettingByName("PotionSpeedReduce").getValBoolean()) {
				if (mc.player.isPotionActive(MobEffects.SPEED)) {
					forwplus = Zamorozka.settingsManager.getSettingByName("ForwardSpeed").getValDouble() / 1.37;
					strplus = Zamorozka.settingsManager.getSettingByName("ForwardSpeed").getValDouble() / 1.37;
				}
			}
		}
		if (mc.player.hurtTime > Zamorozka.settingsManager.getSettingByName("DamageBoostTicks").getValDouble() && Zamorozka.settingsManager.getSettingByName("DamageBoost").getValBoolean() && ModuleManager.getModule(TargetStrafe.class).getState()
				&& ModuleManager.getModule(SpeedHack.class).getState() && ModuleManager.getModule(KillAura.class).getState() && KillAura.target != null) {
			forwplus = Zamorozka.settingsManager.getSettingByName("DamageBoostStrength").getValDouble();
			strplus = Zamorozka.settingsManager.getSettingByName("DamageBoostStrength").getValDouble();
		}
		double forward = pseudoForward;
		double strafe = pseudoStrafe;
		EntityLivingBase target = KillAura.target;
		int col = -1;
		if (Zamorozka.settingsManager.getSettingByName("KeepDistance").getValBoolean() && ModuleManager.getModule(TargetStrafe.class).getState()) {
			col = (int) -Zamorozka.settingsManager.getSettingByName("KeepDistanceStrength").getValDouble();
		} else {
			col = 90;
		}
		float yaw = pseudoYaw;
		if (forward != 0.0D) {
			if (strafe > 0.0D) {
				yaw += ((forward > 0.0D) ? -45 : 45);
				// yaw += ((forward > 0.0D) ? AngleUtil.randomFloat(40f, 45f) :
				// AngleUtil.randomFloat(-40f, -45f));
			} else if (strafe < 0.0D) {
				yaw += ((forward > 0.0D) ? 45 : -45);
				// yaw += ((forward > 0.0D) ? AngleUtil.randomFloat(40f, 45f) :
				// AngleUtil.randomFloat(-40f, -45f));
			}
			strafe = 0.0D;
			if (forward > 0.0D) {
				forward = forwplus;
			} else if (forward < 0.0D) {
				forward = -forwplus;
			}
		}
		if (strafe > 0.0D) {
			strafe = strplus;
		} else if (strafe < 0.0D) {
			strafe = -strplus;
		}
		// double mx = Math.cos(Math.toRadians((yaw + AngleUtil.randomFloat(85f,
		// 90f))));
		// double mz = Math.sin(Math.toRadians((yaw + AngleUtil.randomFloat(85f,
		// 90f))));
		double mx = Math.cos(Math.toRadians((yaw + col)));
		double mz = Math.sin(Math.toRadians((yaw + col)));
		if (md.equalsIgnoreCase("VelocityFIX")) {
			mc.player.motionX = (forward * moveSpeed * mx + strafe * moveSpeed * mz);
			mc.player.motionZ = (forward * moveSpeed * mz - strafe * moveSpeed * mx);
		}
		if (md.equalsIgnoreCase("EventMotion")) {
			moveEvent.setX(forward * moveSpeed * mx + strafe * moveSpeed * mz);
			moveEvent.setZ(forward * moveSpeed * mz - strafe * moveSpeed * mx);
		}
	}

	public static void setSpeed2(EventMove moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
		String md = Zamorozka.settingsManager.getSettingByName("Motion Mode").getValString();
		double forwplus = Zamorozka.settingsManager.getSettingByName("ForwardSpeed").getValDouble();
		double strplus = Zamorozka.settingsManager.getSettingByName("StrafeSpeed").getValDouble();
		if (mc.player.hurtTime > Zamorozka.settingsManager.getSettingByName("DamageBoostTicks").getValDouble() && Zamorozka.settingsManager.getSettingByName("DamageBoost").getValBoolean() && ModuleManager.getModule(TargetStrafe.class).getState()
				&& ModuleManager.getModule(SpeedHack.class).getState() && ModuleManager.getModule(KillAura.class).getState() && KillAura.target != null) {
			forwplus = Zamorozka.settingsManager.getSettingByName("DamageBoostStrength").getValDouble();
			strplus = Zamorozka.settingsManager.getSettingByName("DamageBoostStrength").getValDouble();
		}
		double forward = pseudoForward;
		double strafe = pseudoStrafe;
		EntityLivingBase target = KillAura.target;
		int col = -1;
		if (Zamorozka.settingsManager.getSettingByName("KeepDistance").getValBoolean() && ModuleManager.getModule(TargetStrafe.class).getState()
				&& mc.player.getDistanceToEntity(target) <= Zamorozka.settingsManager.getSettingByName("KeepDistanceRange").getValDouble()) {
			col = -90;
		} else {
			col = 90;
		}
		float yaw = pseudoYaw;
		if (forward != 0.0D) {
			if (strafe > 0.0D) {
				// yaw += ((forward > 0.0D) ? -45 : 45);
				yaw += ((forward > 0.0D) ? AngleUtil.randomFloat(44f, 45f) : AngleUtil.randomFloat(-44f, -45f));
			} else if (strafe < 0.0D) {
				// yaw += ((forward > 0.0D) ? 45 : -45);
				yaw += ((forward > 0.0D) ? AngleUtil.randomFloat(44f, 45f) : AngleUtil.randomFloat(-44f, -45f));
			}
			strafe = 0.0D;
			if (forward > 0.0D) {
				forward = forwplus;
			} else if (forward < 0.0D) {
				forward = -forwplus;
			}
		}
		if (strafe > 0.0D) {
			strafe = strplus;
		} else if (strafe < 0.0D) {
			strafe = -strplus;
		}
		double mx = Math.cos(Math.toRadians((yaw + AngleUtil.randomFloat(89f, 90f))));
		double mz = Math.sin(Math.toRadians((yaw + AngleUtil.randomFloat(89f, 90f))));
		// double mx = Math.cos(Math.toRadians((yaw + col)));
		// double mz = Math.sin(Math.toRadians((yaw + col)));
		if (md.equalsIgnoreCase("VelocityFIX")) {
			mc.player.motionX = (forward * moveSpeed * mx + strafe * moveSpeed * mz);
			mc.player.motionZ = (forward * moveSpeed * mz - strafe * moveSpeed * mx);
		}
		if (md.equalsIgnoreCase("EventMotion")) {
			moveEvent.setX(forward * moveSpeed * mx + strafe * moveSpeed * mz);
			moveEvent.setZ(forward * moveSpeed * mz - strafe * moveSpeed * mx);
		}
	}

	public static void setMotion(EventMove event, double speed) {
		double forward = MovementInput.moveForward;
		double strafe = MovementInput.moveStrafe;
		float yaw = mc.player.rotationYaw;
		if (forward == 0.0D && strafe == 0.0D) {
			event.setX(0.0D);
			event.setZ(0.0D);
		} else {
			if (forward != 0.0D) {
				if (strafe > 0.0D) {
					yaw += (forward > 0.0D ? -45 : 45);
				} else if (strafe < 0.0D) {
					yaw += (forward > 0.0D ? 45 : -45);
				}
				strafe = 0.0D;
				if (forward > 0.0D) {
					forward = 1;
				} else if (forward < 0.0D) {
					forward = -1;
				}
			}
			event.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F)));
			event.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F)));
		}
	}

	public static void setMotion(double speed) {
		double forward = MovementInput.moveForward;
		double strafe = MovementInput.moveStrafe;
		float yaw = mc.player.rotationYaw;
		if ((forward == 0.0D) && (strafe == 0.0D)) {
			mc.player.motionX = 0;
			mc.player.motionZ = 0;
		} else {
			if (forward != 0.0D) {
				if (strafe > 0.0D) {
					yaw += (forward > 0.0D ? -45 : 45);
				} else if (strafe < 0.0D) {
					yaw += (forward > 0.0D ? 45 : -45);
				}
				strafe = 0.0D;
				if (forward > 0.0D) {
					forward = 1;
				} else if (forward < 0.0D) {
					forward = -1;
				}
			}
			mc.player.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F));
			mc.player.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F));
		}
	}

	public static boolean c() {
		for (int i = (int) Math.ceil(mc.player.posY); i >= 0; --i) {
			if (mc.world.getBlockState(new BlockPos(mc.player.posX, i, mc.player.posZ)).getBlock() != Blocks.AIR) {
				return false;
			}
		}
		return true;
	}

	public static float a(final double n, final double n2) {
		return (float) (Math.atan2(n2 - mc.player.posZ, n - mc.player.posX) * 180.0 / 3.141592653589793) - 90.0f;
	}

	public static Block getBlockUnderPlayer(EntityPlayer inPlayer, double height) {
		return mc.world.getBlockState(new BlockPos(inPlayer.posX, inPlayer.posY - height, inPlayer.posZ)).getBlock();
	}

	public static void tpRel(double x, double y, double z) {
		mc.player.setPosition(mc.player.posX + x, mc.player.posY + y, mc.player.posZ + z);
	}

	/*     */ public static double getMoveSpeed(EventMove e) {
		/* 119 */ Minecraft mc = Minecraft.getMinecraft();
		/* 120 */ double xspeed = e.getX();
		/* 121 */ double zspeed = e.getZ();
		/* 122 */ return Math.sqrt(xspeed * xspeed + zspeed * zspeed);
		/*     */ }

	/*     */
	/*     */ public static boolean moveKeysDown() {
		/* 126 */ Minecraft mc = Minecraft.getMinecraft();
		/* 127 */ return !(mc.player.movementInput.moveForward == 0.0F && mc.player.movementInput.moveStrafe == 0.0F);
		/*     */ }

	/*     */ public static double getPressedMoveDir() {
		/* 222 */ Minecraft mc = Minecraft.getMinecraft();
		/* 223 */ double rot = Math.atan2(mc.player.moveForward, mc.player.moveStrafing) / Math.PI * 180.0D;
		/* 224 */ if (rot == 0.0D && mc.player.moveStrafing == 0.0F)
			/* 225 */ rot = 90.0D;
		/* 226 */ rot += mc.player.rotationYaw;
		/* 227 */ return rot - 90.0D;
		/*     */ }

	public static double getPlayerMoveDir() {
		/* 214 */ Minecraft mc = Minecraft.getMinecraft();
		/* 215 */ double xspeed = mc.player.motionX;
		/* 216 */ double zspeed = mc.player.motionZ;
		/* 217 */ double direction = Math.atan2(xspeed, zspeed) / Math.PI * 180.0D;
		/* 218 */ return -direction;
		/*     */ }
}