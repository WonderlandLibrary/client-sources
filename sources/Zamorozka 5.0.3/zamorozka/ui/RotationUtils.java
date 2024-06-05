package zamorozka.ui;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import zamorozka.main.Zamorozka;

import java.util.List;
import java.util.Random;

public class RotationUtils {
	private static final Minecraft mc = Minecraft.getMinecraft();
	public static Random random = new Random();

	public static float getAngleDistance(Entity entity) {
		return Math.abs((float) RotationUtils.normalizeAngle(RotationUtils.getYaw(entity)));
	}

	public static float[] rotationsToVector(Vec3d localVec33) {
		Vec3d localVec31 = mc.player.getPositionEyes(1.0F);
		Vec3d localVec32 = localVec33.subtract(localVec31);
		return new float[] { (float) Math.toDegrees(Math.atan2(localVec32.zCoord, localVec32.xCoord)) - 90.0F, (float) -Math.toDegrees(Math.atan2(localVec32.yCoord, Math.hypot(localVec32.xCoord, localVec32.zCoord))) };
	}

	public static Vec3d getEyesPos() {
		return new Vec3d(Minecraft.player.posX, Minecraft.player.posY + (double) Minecraft.player.getEyeHeight(), Minecraft.player.posZ);
	}

	public static float getAACYaw() {
		switch (mc.getRenderViewEntity().getHorizontalFacing()) {
		case EAST:
			return 90;

		case SOUTH:
			return 180;

		case WEST:
			return 270;

		default:
			return 0;
		}
	}

	public static float[] getRotationsBlock(BlockPos pos) {
		Minecraft mc = Minecraft.getMinecraft();
		double d0 = (double) pos.getX() - Minecraft.player.posX;
		double d1 = (double) pos.getY() - (Minecraft.player.posY + (double) Minecraft.player.getEyeHeight());
		double d2 = (double) pos.getZ() - Minecraft.player.posZ;
		double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
		float f2 = (float) (MathHelper.atan2(d2, d0) * 180.0 / 3.141592653589793) - 90.0f;
		float f1 = (float) (-Math.toDegrees(Math.atan2(d3, d1)));
		return new float[] { f1 };
	}

	public static float[] getNeededRotations(Vec3d vec) {
		Vec3d eyesPos = RotationUtils.getEyesPos();
		double diffX = vec.xCoord - eyesPos.xCoord;
		double diffY = vec.yCoord - eyesPos.yCoord;
		double diffZ = vec.zCoord - eyesPos.zCoord;
		double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
		float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
		float pitch = (float) ((-Math.atan2(diffY, diffXZ)) * 180.0 / 3.141592653589793);
		float[] arrf = new float[2];
		arrf[0] = MathHelper.wrapAngleTo180_float(yaw);
		arrf[1] = Minecraft.getMinecraft().gameSettings.keyBindJump.pressed ? 90.0f : MathHelper.wrapAngleTo180_float(pitch);
		return arrf;
	}

	public static float[] getFacingRotations(final int paramInt1, final double d, final int paramInt3) {
		final EntityPig localEntityPig = new EntityPig(Minecraft.getMinecraft().world);
		localEntityPig.posX = paramInt1 + 0.5;
		localEntityPig.posY = d + 0.5;
		localEntityPig.posZ = paramInt3 + 0.5;
		return getRotationsNeeded(localEntityPig);
	}

	public static float[] getRotations(Vec3d position) {
		return RotationUtils.getRotations(Minecraft.player.getPositionVector().addVector(0.0, Minecraft.player.getEyeHeight(), 0.0), position);
	}

	public static float[] getRotations(Vec3d origin, Vec3d position) {
		Vec3d difference = position.subtract(origin);
		double distance = difference.lengthVector();
		float yaw = (float) Math.toDegrees(Math.atan2(difference.zCoord, difference.xCoord)) - 90.0f;
		float pitch = (float) (-Math.toDegrees(Math.atan2(difference.yCoord, distance)));
		return new float[] { yaw, pitch };
	}

	public static float[] getRotationsNeeded(final Entity entity) {
		if (entity == null) {
			return null;
		}
		Minecraft mc = Minecraft.getMinecraft();
		final double xSize = entity.posX - mc.player.posX;
		final double ySize = entity.posY + entity.getEyeHeight() / 2 - (mc.player.posY + mc.player.getEyeHeight());
		final double zSize = entity.posZ - mc.player.posZ;
		final double theta = MathHelper.sqrt_double(xSize * xSize + zSize * zSize);
		final float yaw = (float) (Math.atan2(zSize, xSize) * 180 / Math.PI) - 90;
		final float pitch = (float) (-(Math.atan2(ySize, theta) * 180 / Math.PI));
		return new float[] { (mc.player.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.player.rotationYaw)) % 360, (mc.player.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.player.rotationPitch)) % 360.0f };
	}

	public static float clampRotation() {
		float rotationYaw = mc.player.rotationYaw;
		float n = 1.0f;
		if (mc.player.movementInput.moveForward < 0.0f) {
			rotationYaw += 180.0f;
			n = -0.5f;
		} else if (mc.player.movementInput.moveForward > 0.0f) {
			n = 0.5f;
		}
		if (mc.player.movementInput.moveStrafe > 0.0f) {
			rotationYaw -= 90.0f * n;
		}
		if (mc.player.movementInput.moveStrafe < 0.0f) {
			rotationYaw += 90.0f * n;
		}
		return rotationYaw * 0.017453292f;
	}

	public static float[] getRotationsNeeded2(Entity entity) {
		if (entity == null) {
			return null;
		}
		double yawrandomizer1 = RandomUtils.nextDouble(0.95, 1.05);
		double yawrandomizer2 = RandomUtils.nextDouble(0.95, 1.05);
		double pitchrandomizer1 = RandomUtils.nextDouble(0.0, 2.0);
		double pitchrandomizer2 = RandomUtils.nextDouble(0.0, 2.0);
		double entityPosX = entity.posX;
		double entityPosY = entity.posY;
		double entityPosZ = entity.posZ;
		double playerPosX = Minecraft.getMinecraft().player.posX;
		double playerPosY = Minecraft.getMinecraft().player.posY;
		double playerPosZ = Minecraft.getMinecraft().player.posZ;
		double diffX = Math.abs(entityPosX) - Math.abs(playerPosX);
		double diffY = (Math.abs(entityPosY) + entity.getEyeHeight()) - (Math.abs(playerPosY) + mc.player.getEyeHeight());
		double diffZ = Math.abs(entityPosZ) - Math.abs(playerPosZ);
		double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
		float yaw;
		if (mc.isSingleplayer()) {
			yaw = (float) ((float) (Math.atan2(-diffZ, -diffX) * 180.0D / Math.PI) - 90.0F * yawrandomizer1 * yawrandomizer2);
		} else {
			yaw = (float) ((float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F * yawrandomizer1 * yawrandomizer2);
		}
		float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
		pitch = MathHelper.clamp_float(pitch, -90F, 90F);
		return new float[] { Minecraft.getMinecraft().player.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().player.rotationYaw),
				Minecraft.getMinecraft().player.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().player.rotationPitch) };
	}

	public static float[] calculateLookAt(double px, double py, double pz, EntityPlayer player) {
		double dirx = player.posX - px;
		double diry = player.posY - py;
		double dirz = player.posZ - pz;
		final double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
		dirx /= len;
		diry /= len;
		dirz /= len;
		double pitch = Math.asin(diry);
		double yaw = Math.atan2(dirz, dirx);
		pitch = pitch * 180.0 / 3.141592653589793;
		yaw = yaw * 180.0 / 3.141592653589793;
		yaw += 90.0;
		return new float[] { (float) yaw, (float) pitch };
	}

	public static void setRenderRotations(float yawRend, float pitchRend) {
		mc.player.renderYawOffset = yawRend;
		mc.player.rotationYawHead = yawRend;
		mc.player.rotationPitchHead = pitchRend;
	}

	public static float[] getRotationsToward(Entity closestEntity) {
		double xDist = closestEntity.posX - Minecraft.player.posX;
		double yDist = closestEntity.posY + closestEntity.getEyeHeight() - (Minecraft.player.posY + Minecraft.player.getEyeHeight() + 0.5);
		double zDist = closestEntity.posZ - Minecraft.player.posZ;
		double fDist = MathHelper.sqrt(xDist * xDist + zDist * zDist);
		float yaw = fixRotation(Minecraft.player.rotationYaw, (float) (MathHelper.atan2(zDist, xDist) * 180.0D / Math.PI) - 90.0F);
		float pitch = fixRotation(Minecraft.player.rotationPitch, (float) (-(MathHelper.atan2(yDist, fDist) * 180.0D / Math.PI)));
		return new float[] { yaw, pitch };
	}

	public static float fixRotation(final float p_70663_1_, final float p_70663_2_) {
		float var4 = MathHelper.wrapDegrees(p_70663_2_ - p_70663_1_);
		if (var4 > (float) 360.0) {
			var4 = (float) 360.0;
		}
		if (var4 < -(float) 360.0) {
			var4 = -(float) 360.0;
		}
		return p_70663_1_ + var4;
	}

	public static float[] faceTargetFast(final Entity target, final float p_706252, final float p_706253, final boolean miss) {
		final double var4 = target.posX - mc.player.posX;
		final double var5 = target.posZ - mc.player.posZ;
		double var7;
		if (target instanceof EntityLivingBase) {
			final EntityLivingBase var6 = (EntityLivingBase) target;
			var7 = var6.posY + var6.getEyeHeight() - (mc.player.posY + mc.player.getEyeHeight());
		} else {
			var7 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0 - (mc.player.posY + mc.player.getEyeHeight());
		}
		final double var8 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
		final float var9 = (float) (Math.atan2(var5, var4) * 180.0 / 3.141592653589793) - 90.0f;
		final float var10 = (float) (-(Math.atan2(var7 - ((target instanceof EntityPlayer) ? 0.25 : 0.0), var8) * 180.0 / 3.141592653589793));
		final float pitch = changeRotation(mc.player.rotationPitch, var10, p_706253);
		final float yaw = changeRotation(mc.player.rotationYaw, var9, p_706252);
		return new float[] { yaw, pitch };
	}

	public static float[] getRotationFromEntity(Entity target) {
		double var4 = target.posX - RotationUtils.mc.player.posX;
		double var5 = target.posZ - RotationUtils.mc.player.posZ;
		double var6 = target.posY + (double) target.getEyeHeight() / 1.3 - (RotationUtils.mc.player.posY + (double) RotationUtils.mc.player.getEyeHeight());
		double var7 = MathHelper.sqrt((double) (var4 * var4 + var5 * var5));
		float yaw = (float) (Math.atan2((double) var5, (double) var4) * 180.0 / 3.141592653589793) - 90.0f;
		float pitch = (float) (-(Math.atan2((double) var6, (double) var7) * 180.0 / 3.141592653589793));
		return new float[] { yaw, pitch };
	}

	public static float[] getRotationsLegit(Entity e) {
		double deltaX = e.posX + (e.posX - e.lastTickPosX) - mc.player.posX, deltaY = e.posY - 3.5 + e.getEyeHeight() - mc.player.posY + mc.player.getEyeHeight(), deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - mc.player.posZ,
				distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));

		float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ)), pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance));

		if (deltaX < 0 && deltaZ < 0) {
			yaw = (float) (90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
		} else if (deltaX > 0 && deltaZ < 0) {
			yaw = (float) (-90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
		}
		return new float[] { yaw, pitch };
	}

	public static float[] getSmoothRotations(Entity entity) {
		double d = entity.posX - entity.prevPosX - (mc.player.posX - mc.player.prevPosX);
		double d2 = entity.posY - entity.prevPosY - (mc.player.posY - mc.player.prevPosY);
		double d3 = entity.posZ - entity.prevPosZ - (mc.player.posZ - mc.player.prevPosZ);
		double d4 = Math.sqrt(Math.pow(d, 2.0) + Math.pow(d3, 2.0));
		float f = (float) Math.toDegrees(-Math.atan(d / d3));
		float f2 = (float) (-Math.toDegrees(Math.atan(d2 / d4)));
		if (d < 0.0 && d3 < 0.0) {
			f = (float) (90.0 + Math.toDegrees(Math.atan(d3 / d)));
		} else if (d > 0.0 && d3 < 0.0) {
			f = (float) (-90.0 + Math.toDegrees(Math.atan(d3 / d)));
		}
		return new float[] { f, f2 };
	}

	public static float[] mouseFix(float yaw, float pitch) {
		float k = mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
		float k1 = k * k * k * 8.0f;
		yaw -= yaw % k1;
		pitch -= pitch % k1;
		return new float[] { yaw, pitch };
	}

	public static float[] getSmoothRotations(Vec3d vec, float currentYaw, float currentPitch) {
		double diffX = vec.xCoord + 0.5 - Minecraft.getMinecraft().player.posX;
		double diffY = vec.yCoord + 0.5 - (Minecraft.getMinecraft().player.posY + (double) Minecraft.getMinecraft().player.getEyeHeight());
		double diffZ = vec.zCoord + 0.5 - Minecraft.getMinecraft().player.posZ;
		double dist = MathHelper.sqrt((double) (diffX * diffX + diffZ * diffZ));
		float yaw = (float) (Math.atan2((double) diffZ, (double) diffX) * 180.0 / 3.141592653589793) - 90.0f;
		float pitch = (float) (-(Math.atan2((double) diffY, (double) dist) * 180.0 / 3.141592653589793));
		boolean aim = false;
		float max = 5.0f;
		float yawChange = 0.0f;
		if (MathHelper.wrapAngleTo180_float((float) (yaw - currentYaw)) > max * 2.0f) {
			aim = true;
			yawChange = max;
		} else if (MathHelper.wrapAngleTo180_float((float) (yaw - currentYaw)) < -max * 2.0f) {
			aim = true;
			yawChange = -max;
		}
		float pitchChange = 0.0f;
		if (MathHelper.wrapAngleTo180_float((float) (pitch - currentPitch)) > max * 4.0f) {
			aim = true;
			pitchChange = max;
		} else if (MathHelper.wrapAngleTo180_float((float) (pitch - currentPitch)) < -max * 4.0f) {
			aim = true;
			pitchChange = -max;
		}
		float[] rotations = new float[] { currentYaw, currentPitch };
		if (aim) {
			rotations[0] = (float) ((double) currentYaw + (double) MathHelper.wrapAngleTo180_float((float) (yaw - currentYaw)) / (1.5 * (random.nextDouble() * 2.0 + 1.0)));
			rotations[1] = (float) ((double) currentPitch + (double) MathHelper.wrapAngleTo180_float((float) (pitch - currentPitch)) / (1.5 * (random.nextDouble() * 2.0 + 1.0)));
		}
		return rotations;
	}

	public static float[] getDirectionToBlock(int var0, int var1, int var2, EnumFacing var3) {
		EntityEgg var4 = new EntityEgg((World) RotationUtils.mc.world);
		var4.posX = (double) var0 + 0.5;
		var4.posY = (double) var1 + 0.5;
		var4.posZ = (double) var2 + 0.5;
		var4.posX += (double) var3.getDirectionVec().getX() * 0.25;
		var4.posY += (double) var3.getDirectionVec().getY() * 0.25;
		var4.posZ += (double) var3.getDirectionVec().getZ() * 0.25;
		return RotationUtils.getDirectionToEntity((Entity) var4);
	}

	public static float[] getNewDirectionToBlock(int var0, int var1, int var2, EnumFacing var3) {
		return RotationUtils.getNewDirectionToPosition((double) var0 + 0.5 + (double) var3.getDirectionVec().getX() * 0.5, (double) var1 + 0.25 + (double) var3.getDirectionVec().getY() * 0.5,
				(double) var2 + 0.5 + (double) var3.getDirectionVec().getZ() * 0.5);
	}

	public static float[] getNewDirectionToPosition(double x, double y, double z) {
		double diffX = RotationUtils.mc.player.posX - x;
		double diffY = y - (RotationUtils.mc.player.posY + (double) RotationUtils.mc.player.getEyeHeight());
		double diffZ = RotationUtils.mc.player.posZ - z;
		double diffXZ = Math.sqrt((double) (diffX * diffX + diffZ * diffZ));
		float yaw = (float) (Math.atan2((double) diffZ, (double) diffX) * 180.0 / 3.141592653589793) + 90.0f;
		float pitch = (float) (-Math.atan2((double) diffY, (double) diffXZ) * 180.0 / 3.141592653589793);
		return new float[] { yaw, pitch };
	}

	public static float[] getFaceRotating(Vec3d vec, EnumFacing face) {
		EntityEgg var4 = new EntityEgg((World) RotationUtils.mc.world);
		var4.posX = vec.xCoord;
		var4.posY = vec.yCoord;
		var4.posZ = vec.zCoord;
		var4.posX += (double) face.getDirectionVec().getX() * 0.25;
		var4.posY += (double) face.getDirectionVec().getY() * 0.25;
		var4.posZ += (double) face.getDirectionVec().getZ() * 0.25;
		return RotationUtils.getDirectionToEntity((Entity) var4);
	}

	private static float[] getDirectionToEntity(Entity var0) {
		return new float[] { RotationUtils.getYaw(var0) + RotationUtils.mc.player.rotationYaw, RotationUtils.getPitch(var0) + RotationUtils.mc.player.rotationPitch };
	}

	public static float getYaw(Entity var0) {
		double var1 = var0.posX - RotationUtils.mc.player.posX;
		double var3 = var0.posZ - RotationUtils.mc.player.posZ;
		double var5 = var3 < 0.0 && var1 < 0.0 ? 90.0 + Math.toDegrees((double) Math.atan((double) (var3 / var1)))
				: (var3 < 0.0 && var1 > 0.0 ? -90.0 + Math.toDegrees((double) Math.atan((double) (var3 / var1))) : Math.toDegrees((double) (-Math.atan((double) (var1 / var3)))));
		return MathHelper.wrapAngleTo180_float((float) (-(RotationUtils.mc.player.rotationYaw - (float) var5)));
	}

	public static float getPitch(Entity var0) {
		double var1 = var0.posX - RotationUtils.mc.player.posX;
		double var3 = var0.posZ - RotationUtils.mc.player.posZ;
		double var5 = var0.posY - 1.6 + (double) var0.getEyeHeight() - RotationUtils.mc.player.posY;
		double var7 = MathHelper.sqrt((double) (var1 * var1 + var3 * var3));
		double var9 = -Math.toDegrees((double) Math.atan((double) (var5 / var7)));
		return -MathHelper.wrapAngleTo180_float((float) (RotationUtils.mc.player.rotationPitch - (float) var9));
	}

	public static float[] getNeededFacing(Vec3d vec) {
		Vec3d eyesPos = RotationUtils.getEyesPos();
		double diffX = vec.xCoord - eyesPos.xCoord;
		double diffY = vec.yCoord - (Minecraft.player.posY + Minecraft.player.getEyeHeight() + 0.2);
		double diffZ = vec.zCoord - eyesPos.zCoord;
		double diffXZ = Math.sqrt((double) (diffX * diffX + diffZ * diffZ));
		float yaw = (float) Math.toDegrees((double) Math.atan2((double) diffZ, (double) diffX)) - 90.0f;
		float pitch = (float) (-Math.toDegrees((double) Math.atan2((double) diffY, (double) diffXZ)));
		pitch = MathHelper.clamp_float(pitch, -90F, 90F);
		return new float[] { MathHelper.wrapAngleTo180_float(yaw), MathHelper.wrapAngleTo180_float(pitch) };
	}

	public static float[] getNeededFacing(Vec3d target, Vec3d from) {
		double diffX = target.xCoord - from.xCoord;
		double diffY = target.yCoord - from.yCoord;
		double diffZ = target.zCoord - from.zCoord;
		double diffXZ = Math.sqrt((double) (diffX * diffX + diffZ * diffZ));
		float yaw = (float) Math.toDegrees((double) Math.atan2((double) diffZ, (double) diffX)) - 90.0f;
		float pitch = (float) (-Math.toDegrees((double) Math.atan2((double) diffY, (double) diffXZ)));
		return new float[] { MathHelper.wrapAngleTo180_float((float) yaw), MathHelper.wrapAngleTo180_float((float) pitch) };
	}

	public static float[] faceTarget(Entity target, float p_70625_2_, float p_70625_3_, boolean miss) {
		double var6;
		double var4 = target.posX - RotationUtils.mc.player.posX;
		double var8 = target.posZ - RotationUtils.mc.player.posZ;
		if (target instanceof EntityLivingBase) {
			EntityLivingBase var10 = (EntityLivingBase) target;
			var6 = var10.posY + (double) var10.getEyeHeight() - (RotationUtils.mc.player.posY + (double) RotationUtils.mc.player.getEyeHeight());
		} else {
			var6 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0 - (RotationUtils.mc.player.posY + (double) RotationUtils.mc.player.getEyeHeight());
		}
		Random rnd = new Random();
		double var14 = MathHelper.sqrt((double) (var4 * var4 + var8 * var8));
		float var12 = (float) (Math.atan2((double) var8, (double) var4) * 180.0 / 3.141592653589793) - 90.0f;
		float var13 = (float) (-(Math.atan2((double) (var6 - (target instanceof EntityPlayer ? 0.25 : 0.0)), (double) var14) * 180.0 / 3.141592653589793));
		float pitch = RotationUtils.changeRotation(RotationUtils.mc.player.rotationPitch, var13, p_70625_3_);
		float yaw = RotationUtils.changeRotation(RotationUtils.mc.player.rotationYaw, var12, p_70625_2_);
		return new float[] { yaw, pitch };
	}

	public static float changeRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_) {
		float var4 = MathHelper.wrapAngleTo180_float((float) (p_70663_2_ - p_70663_1_));
		if (var4 > p_70663_3_) {
			var4 = p_70663_3_;
		}
		if (var4 < -p_70663_3_) {
			var4 = -p_70663_3_;
		}
		return p_70663_1_ + var4;
	}

	public static float[] getRotate(Entity entity, float speed1, float speed2, float Cyaw, float Cpitch) {
		double y;
		double x = entity.posX;
		double z = entity.posZ;
		if (entity instanceof EntityLivingBase) {
			EntityLivingBase entitylivingbase = (EntityLivingBase) entity;
			y = entitylivingbase.posY + (double) entitylivingbase.getEyeHeight() - (RotationUtils.mc.player.posY + (double) RotationUtils.mc.player.getEyeHeight());
		} else {
			y = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (RotationUtils.mc.player.posY + (double) RotationUtils.mc.player.getEyeHeight());
		}
		double dist = Math.sqrt((double) (x * x + z * z));
		float f0 = (float) (Math.atan2((double) z, (double) x) * 180.0 / 3.141592653589793) - 90.0f;
		float f1 = (float) (-(Math.atan2((double) y, (double) dist) * 180.0 / 3.141592653589793));
		float yaw = RotationUtils.set(Cpitch, f1, speed2);
		float pitch = RotationUtils.set(Cyaw, f0, speed1);
		return new float[] { yaw, pitch };
	}

	public static boolean isHitPos(BlockPos pos, float yaw, float pitch) {
		double x = pos.getX();
		double y = pos.getY();
		double z = pos.getZ();
		double x2 = RotationUtils.mc.player.posX;
		double y2 = RotationUtils.mc.player.posY;
		double z2 = RotationUtils.mc.player.posZ;
		double dist = MathHelper.sqrt((double) ((x - RotationUtils.mc.player.posX) * (x - RotationUtils.mc.player.posX) + (z - RotationUtils.mc.player.posZ) * (z - RotationUtils.mc.player.posZ)));
		double d0 = -MathHelper.sin((float) yaw);
		double d1 = MathHelper.cos((float) yaw);
		double d2 = -MathHelper.sin((float) pitch);
		double adX = 0.0;
		double adY = 0.0;
		double adZ = 0.0;
		adX = d0 > 0.0 ? 0.1 : -0.1;
		adZ = d1 > 0.0 ? 0.1 : -0.1;
		adY = d2 > 0.0 ? 0.1 : -0.1;
		BlockPos pos2 = new BlockPos(x2, y2, z2);
		float f = 0.0f;
		while ((double) f < dist) {
			if ((x2 += adX) == x) {
				adX = 0.0;
			}
			if ((y2 += adY) == y) {
				adY = 0.0;
			}
			if ((z2 += adZ) == z) {
				adZ = 0.0;
			}
			if (!(RotationUtils.mc.world.getBlockState(pos2 = new BlockPos(x2, y, z2)).getBlock() instanceof BlockAir))
				break;
			f = (float) ((double) f + 0.01);
		}
		Block block1 = RotationUtils.mc.world.getBlockState(pos).getBlock();
		Block block2 = RotationUtils.mc.world.getBlockState(pos2).getBlock();
		return block1 == block2;
	}

	public static float[] getRotations2(Entity entity, float yawSpeed, float pitchSpeed) {
		double x = entity.posX - RotationUtils.mc.player.posX;
		double z = entity.posZ - RotationUtils.mc.player.posZ;
		double y = entity.boundingBox.maxY - 4.0 - RotationUtils.mc.player.posY + (double) RotationUtils.mc.player.getEyeHeight();
		double dist = Math.sqrt((double) (x * x + z * z));
		float f0 = (float) (Math.atan2((double) z, (double) x) * 180.0 / 3.141592653589793) - 90.0f;
		float f1 = (float) (-(Math.atan2((double) y, (double) dist) * 180.0 / 3.141592653589793));
		float yaw = RotationUtils.set(mc.player.rotationYaw, f1, yawSpeed);
		float pitch = RotationUtils.set(mc.player.rotationPitch, f0, pitchSpeed);
		return new float[] { yaw, pitch };
	}

	public static float[] smoothRotations(final Entity target, final float speed) {
		final double x = target.posX - EntityUtils.mc.player.posX;
		final double z = target.posZ - EntityUtils.mc.player.posZ;
		final double y = target.posY + target.getEyeHeight() - 0.1 - (EntityUtils.mc.player.posY + EntityUtils.mc.player.getEyeHeight());
		final double dist = MathHelper.sqrt(x * x + z * z);
		final float tempYaw = (float) (Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
		final float tempPitch = (float) (-(Math.atan2(y, dist) * 180.0 / 3.141592653589793));
		final float pitch = changeRotation(EntityUtils.mc.player.rotationPitch, tempPitch, speed);
		final float yaw = changeRotation(EntityUtils.mc.player.rotationYaw, tempYaw, speed);
		return new float[] { yaw, pitch };
	}

	public static float[] getRotation(Entity target) {
		double diffY;
		if (target == null) {
			return new float[] { 0.0f, 0.0f };
		}
		double diffX = target.posX - RotationUtils.mc.player.posX;
		double diffZ = target.posZ - RotationUtils.mc.player.posZ;
		if (target instanceof EntityLivingBase) {
			EntityLivingBase entity = (EntityLivingBase) target;
			diffY = entity.posY + (double) entity.getEyeHeight() - (RotationUtils.mc.player.posY + (double) RotationUtils.mc.player.getEyeHeight());
		} else {
			diffY = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0 - (RotationUtils.mc.player.posY + (double) RotationUtils.mc.player.getEyeHeight());
		}
		double dist = Math.sqrt((double) (diffX * diffX + diffZ * diffZ));
		float yaw = (float) (Math.atan2((double) diffZ, (double) diffX) * 180.0 / 3.141592653589793) - 90.0f;
		float pitch = (float) (-(Math.atan2((double) diffY, (double) dist) * 180.0 / 3.141592653589793));
		return new float[] { yaw, pitch };
	}

	public static float set(float f1, float f2, float f3) {
		float f = MathHelper.wrapAngleTo180_float((float) (f2 - f1));
		if (f > f3) {
			f = f3;
		}
		if (f < -f3) {
			f = -f3;
		}
		return f1 + f;
	}

	public static float[] getRotations(Entity ent) {
		double x = ent.posX;
		double z = ent.posZ;
		double y = ent.boundingBox.maxY - 4.0;
		return RotationUtils.getRotationFromPosition(x, z, y);
	}

	public static float[] getFaceRotating(Entity entity) {
		double posX = entity.posX - RotationUtils.mc.player.posX;
		double posY = entity instanceof EntityLivingBase ? entity.posY + (double) entity.getEyeHeight() - (RotationUtils.mc.player.posY + (double) RotationUtils.mc.player.getEyeHeight())
				: (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (RotationUtils.mc.player.posY + (double) RotationUtils.mc.player.getEyeHeight());
		double posZ = entity.posZ - RotationUtils.mc.player.posZ;
		double var14 = MathHelper.sqrt((double) (posX * posX + posZ * posZ));
		float yaw = (float) (Math.atan2((double) posZ, (double) posX) * 180.0 / 3.141592653589793) - 90.0f;
		float pitch = (float) (-(Math.atan2((double) posY, (double) var14) * 180.0 / 3.141592653589793));
		return new float[] { yaw, pitch };
	}

	public static float[] getNewFaceRotating(Entity entity) {
		double xDelta = entity.posX - entity.lastTickPosX;
		double zDelta = entity.posZ - entity.lastTickPosZ;
		double xMulti = 1.0;
		double zMulti = 1.0;
		boolean sprint = entity.isSprinting();
		xMulti = xDelta * (sprint ? 0.97 : 1.0);
		zMulti = zDelta * (sprint ? 0.97 : 1.0);
		double x = entity.posX + xMulti - RotationUtils.mc.player.posX;
		double z = entity.posZ + zMulti - RotationUtils.mc.player.posZ;
		double y = entity.posY + (double) RotationUtils.mc.player.getEyeHeight() - (RotationUtils.mc.player.posY + (double) RotationUtils.mc.player.getEyeHeight());
		double distance = RotationUtils.mc.player.getDistanceToEntity(entity);
		float yaw = (float) Math.toDegrees((double) Math.atan2((double) z, (double) x)) - 90.0f;
		float pitch = (float) Math.toDegrees((double) Math.atan2((double) y, (double) distance));
		return new float[] { yaw, pitch };
	}

	public static float[] getBestFaceRotating(EntityLivingBase entity) {
		float targetYaw = RotationUtils.getFaceRotating((Entity) entity)[0];
		float targetPitch = entity.posY > RotationUtils.mc.player.posY ? RotationUtils.getNewFaceRotating((Entity) entity)[1] : RotationUtils.getFaceRotating((Entity) entity)[1];
		return new float[] { targetYaw, targetPitch };
	}

	public static float[] getRotationFromPosition(double x, double z, double y) {
		double xDiff = x - Minecraft.getMinecraft().player.posX;
		double zDiff = z - Minecraft.getMinecraft().player.posZ;
		double yDiff = y - Minecraft.getMinecraft().player.posY + (double) Minecraft.getMinecraft().player.getEyeHeight();
		double dist = MathHelper.sqrt((double) (xDiff * xDiff + zDiff * zDiff));
		float yaw = (float) (Math.atan2((double) zDiff, (double) xDiff) * 180.0 / 3.141592653589793) - 90.0f;
		float pitch = (float) (-(Math.atan2((double) yDiff, (double) dist) * 180.0 / 3.141592653589793));
		return new float[] { yaw, pitch };
	}

	public static float[] getFaceRotating(BlockPos pos, EnumFacing face) {
		EntityEgg var4 = new EntityEgg((World) RotationUtils.mc.world);
		var4.posX = (double) pos.getX() + 0.5;
		var4.posY = (double) pos.getY() - 0.5;
		var4.posZ = (double) pos.getZ() + 0.5;
		var4.posX += (double) face.getDirectionVec().getX() * 0.25;
		var4.posY += (double) face.getDirectionVec().getY() * 0.25;
		var4.posZ += (double) face.getDirectionVec().getZ() * 0.25;
		return RotationUtils.getDirectionToEntity((Entity) var4);
	}

	public static float[] getFaceRotating(double x, double y, double z, EnumFacing face) {
		EntityEgg var4 = new EntityEgg((World) RotationUtils.mc.world);
		var4.posX = x;
		var4.posY = y;
		var4.posZ = z;
		var4.posX += (double) face.getDirectionVec().getX() * 0.25;
		var4.posY += (double) face.getDirectionVec().getY() * 0.25;
		var4.posZ += (double) face.getDirectionVec().getZ() * 0.25;
		return RotationUtils.getDirectionToEntity((Entity) var4);
	}

	public static float[] getFaceRotatingFromPos(double x, double y, double z) {
		double xDist = x - RotationUtils.mc.player.posX;
		double yDist = y - (RotationUtils.mc.player.posY + (double) RotationUtils.mc.player.getEyeHeight());
		double zDist = z - RotationUtils.mc.player.posZ;
		double dist = MathHelper.sqrt((double) (xDist * xDist + zDist * zDist));
		float yaw = (float) (Math.atan2((double) zDist, (double) xDist) * 180.0 / 3.141592653589793) - 90.0f;
		float pitch = (float) (-(Math.atan2((double) yDist, (double) dist) * 180.0 / 3.141592653589793));
		return new float[] { yaw, pitch };
	}

	public static float[] getRotationFromPos(double x, double z, double y) {
		double var4 = x - RotationUtils.mc.player.posX;
		double var5 = z - RotationUtils.mc.player.posZ;
		double var6 = y - (RotationUtils.mc.player.posY + (double) RotationUtils.mc.player.getEyeHeight());
		double var7 = MathHelper.sqrt((double) (var4 * var4 + var5 * var5));
		float yaw = (float) (Math.atan2((double) var5, (double) var4) * 180.0 / 3.141592653589793) - 90.0f;
		float pitch = (float) (-(Math.atan2((double) var6, (double) var7) * 180.0 / 3.141592653589793));
		return new float[] { yaw, pitch };
	}

	public static float getTrajAngleSolutionLow(float d3, float d1, float velocity) {
		float g = 0.006f;
		float sqrt = velocity * velocity * velocity * velocity - 0.006f * (0.006f * (d3 * d3) + 2.0f * d1 * (velocity * velocity));
		return (float) Math.toDegrees((double) Math.atan((double) (((double) (velocity * velocity) - Math.sqrt((double) sqrt)) / (double) (0.006f * d3))));
	}

	public static boolean checkRotation(EntityLivingBase target, float nowYaw, float nowPitch) {
		float targetYaw = RotationUtils.getNewAngle(RotationUtils.getRotationFromEntity((Entity) target)[0]);
		float targetPitch = RotationUtils.getNewAngle(RotationUtils.getRotationFromEntity((Entity) target)[1]);
		float f1 = RotationUtils.getNewAngle(nowYaw);
		float f2 = RotationUtils.getNewAngle(nowPitch);
		float f3 = Math.abs((float) (targetYaw - f1));
		float f4 = Math.abs((float) (targetPitch - f2));
		return f3 <= 3600.0f && f4 <= 3600.0f;
	}

	public static float getNewAngle(float angle) {
		if ((angle %= 360.0f) >= 180.0f) {
			angle -= 360.0f;
		}
		if (angle < -180.0f) {
			angle += 360.0f;
		}
		return angle;
	}

	public static float getDistanceBetweenAngles(float angle1, float angle2) {
		float angle3 = Math.abs((float) (angle1 - angle2)) % 360.0f;
		if (angle3 > 180.0f) {
			angle3 = 360.0f - angle3;
		}
		return angle3;
	}

	public static float getYawChangeToEntity(Entity entity) {
		double deltaX = entity.posX - RotationUtils.mc.player.posX;
		double deltaZ = entity.posZ - RotationUtils.mc.player.posZ;
		double yawToEntity = deltaZ < 0.0 && deltaX < 0.0 ? 90.0 + Math.toDegrees((double) Math.atan((double) (deltaZ / deltaX)))
				: (deltaZ < 0.0 && deltaX > 0.0 ? -90.0 + Math.toDegrees((double) Math.atan((double) (deltaZ / deltaX))) : Math.toDegrees((double) (-Math.atan((double) (deltaX / deltaZ)))));
		return MathHelper.wrapAngleTo180_float((float) (-(RotationUtils.mc.player.rotationYaw - (float) yawToEntity)));
	}

	public static float getAngle(float[] original, float[] rotations) {
		float curYaw = RotationUtils.normalizeAngle(original[0]);
		rotations[0] = RotationUtils.normalizeAngle(rotations[0]);
		float curPitch = RotationUtils.normalizeAngle(original[1]);
		rotations[1] = RotationUtils.normalizeAngle(rotations[1]);
		float fixedYaw = RotationUtils.normalizeAngle(curYaw - rotations[0]);
		float fixedPitch = RotationUtils.normalizeAngle(curPitch - rotations[1]);
		return Math.abs((float) (RotationUtils.normalizeAngle(fixedYaw) + Math.abs((float) fixedPitch)));
	}

	public static float getAngle(float[] rotations) {
		return RotationUtils.getAngle(new float[] { RotationUtils.mc.player.rotationYaw, RotationUtils.mc.player.rotationPitch }, rotations);
	}

	public static float getAngletest(float yaw, float pitch, float[] rotations) {
		return RotationUtils.getAngle(new float[] { yaw, pitch }, rotations);
	}

	public static float[] getAnglesToEntity(Entity e) {
		return new float[] { RotationUtils.getYawChangeToEntity(e) + RotationUtils.mc.player.rotationYaw, RotationUtils.getPitchChangeToEntity(e) + RotationUtils.mc.player.rotationPitch };
	}

	public static float[] getFaceRotatingChange(Entity entity) {
		return new float[] { RotationUtils.getYawChangeToEntity(entity), RotationUtils.getPitchChangeToEntity(entity) };
	}

	public static float normalizeAngle(float angle) {
		return MathHelper.wrapAngleTo180_float((float) ((angle + 180.0f) % 360.0f - 180.0f));
	}

	public static float getPitchChangeToEntity(Entity entity) {
		double deltaX = entity.posX - RotationUtils.mc.player.posX;
		double deltaZ = entity.posZ - RotationUtils.mc.player.posZ;
		double deltaY = entity.posY - (double) entity.getEyeHeight() - (RotationUtils.mc.player.posY + (double) RotationUtils.mc.player.getEyeHeight());
		double distanceXZ = MathHelper.sqrt((double) (deltaX * deltaX + deltaZ * deltaZ));
		double pitchToEntity = -Math.toDegrees((double) Math.atan((double) (deltaY / distanceXZ)));
		return -MathHelper.wrapAngleTo180_float((float) (RotationUtils.mc.player.rotationPitch - (float) pitchToEntity));
	}

	public static float[] getVodkaVecRotations(Vec3d e) {
		// Variables
		Vec3d eyesPos = RotationUtils.mc.player.getPositionEyes(1f);
		double diffX = e.xCoord - eyesPos.xCoord;
		double diffZ = e.zCoord - eyesPos.zCoord;
		double diffY = e.yCoord - eyesPos.yCoord;
		// Distance between player and entity
		double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);

		// Getting needed rotations
		float yaw = (float) (((Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f)) + RandomUtils.nextFloat(-2F, 2F);
		float pitch = (float) (-(Math.atan2(diffY, dist) * 180.0 / Math.PI)) + RandomUtils.nextFloat(-2F, 2F);

		// WTF WHY ITS NOT CLAMPING AHHH
		// System.out.println(wrapAngleTo180_float(yaw - mc.player.rotationYaw) + " " +
		// (yaw
		// - mc.player.rotationYaw));
		yaw = (mc.player.rotationYaw + GCDFix.getFixedRotation(MathHelper.wrapAngleTo180_float(yaw - mc.player.rotationYaw)));
		pitch = mc.player.rotationPitch + GCDFix.getFixedRotation(MathHelper.wrapAngleTo180_float(pitch - mc.player.rotationPitch));
		pitch = MathHelper.clamp_float(pitch, -90F, 90F);
		return new float[] { yaw, pitch };
	}

	public static float[] getVodkaRotations(Entity e, boolean oldPositionUse) {
		// Variables
		double diffX = (oldPositionUse ? e.prevPosX : e.posX) - (oldPositionUse ? mc.player.prevPosX : mc.player.posX);
		double diffZ = (oldPositionUse ? e.prevPosZ : e.posZ) - (oldPositionUse ? mc.player.prevPosZ : mc.player.posZ);
		double diffY;

		// Getting center of entity
		if (e instanceof EntityLivingBase) {
			EntityLivingBase entitylivingbase = (EntityLivingBase) e;
			float randomed = RandomUtils.nextFloat((float) (entitylivingbase.posY + entitylivingbase.getEyeHeight() / 1.5F), (float) (entitylivingbase.posY + entitylivingbase.getEyeHeight() - entitylivingbase.getEyeHeight() / 3F));
			diffY = randomed - (mc.player.posY + (double) mc.player.getEyeHeight());
		} else {
			diffY = RandomUtils.nextFloat((float) e.getEntityBoundingBox().minY, (float) e.getEntityBoundingBox().maxY) - (mc.player.posY + (double) mc.player.getEyeHeight());
		}

		// Distance between player and entity
		double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);

		// Getting needed rotations
		float yaw = (float) (((Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f)) + RandomUtils.nextFloat(-2F, 2F);
		float pitch = (float) (-(Math.atan2(diffY, dist) * 180.0 / Math.PI)) + RandomUtils.nextFloat(-2F, 2F);

		// Set rotations
		yaw = (mc.player.rotationYaw + GCDFix.getFixedRotation(MathHelper.wrapAngleTo180_float(yaw - mc.player.rotationYaw)));
		pitch = mc.player.rotationPitch + GCDFix.getFixedRotation(MathHelper.wrapAngleTo180_float(pitch - mc.player.rotationPitch));
		pitch = MathHelper.clamp_float(pitch, -90F, 90F);
		return new float[] { yaw, pitch };
	}

	public static float[] getFaceRotatingChange(Vec3d vec) {
		double deltaX = vec.xCoord - RotationUtils.mc.player.posX;
		double deltaZ = vec.yCoord - RotationUtils.mc.player.posZ;
		double deltaY = vec.zCoord - (RotationUtils.mc.player.posY + (double) RotationUtils.mc.player.getEyeHeight());
		double distanceXZ = MathHelper.sqrt((double) (deltaX * deltaX + deltaZ * deltaZ));
		double pitchToEntity = -Math.toDegrees((double) Math.atan((double) (deltaY / distanceXZ)));
		float pitch = -MathHelper.wrapAngleTo180_float((float) (RotationUtils.mc.player.rotationPitch - (float) pitchToEntity));
		double yawToEntity = deltaZ < 0.0 && deltaX < 0.0 ? 90.0 + Math.toDegrees((double) Math.atan((double) (deltaZ / deltaX)))
				: (deltaZ < 0.0 && deltaX > 0.0 ? -90.0 + Math.toDegrees((double) Math.atan((double) (deltaZ / deltaX))) : Math.toDegrees((double) (-Math.atan((double) (deltaX / deltaZ)))));
		float yaw = MathHelper.wrapAngleTo180_float((float) (-(RotationUtils.mc.player.rotationYaw - (float) yawToEntity)));
		return new float[] { yaw, pitch };
	}

	public static float getYawToEntity(Entity entity, boolean useOldPos) {
		final EntityPlayerSP player = Minecraft.player;
		double xDist = (useOldPos ? entity.prevPosX : entity.posX) - (useOldPos ? player.prevPosX : player.posX);
		double zDist = (useOldPos ? entity.prevPosZ : entity.posZ) - (useOldPos ? player.prevPosZ : player.posZ);
		float rotationYaw = useOldPos ? Minecraft.player.prevRotationYaw : Minecraft.player.rotationYaw;
		float var1 = (float) (Math.atan2(zDist, xDist) * 180.0D / Math.PI) - 90.0F;
		return rotationYaw + MathHelper.wrapAngleTo180_float(var1 - rotationYaw);
	}

	public static EntityLivingBase rayCast(float yaw, float pitch, double range) {
		Minecraft mc = Minecraft.getMinecraft();
		if (mc.world != null && mc.player != null) {
			Vec3d position = mc.player.getPositionEyes(mc.timer.renderPartialTicks);
			Vec3d lookVector = mc.player.getVectorForRotation(pitch, yaw);
			double reachDistance = range;
			Entity pointedEntity = null;
			List<Entity> var5 = mc.world.getEntitiesWithinAABBExcludingEntity(mc.player,
					mc.player.getEntityBoundingBox()
							.addCoord(lookVector.xCoord * mc.playerController.getBlockReachDistance(), lookVector.yCoord * mc.playerController.getBlockReachDistance(), lookVector.zCoord * mc.playerController.getBlockReachDistance())
							.expand(reachDistance, reachDistance, reachDistance));
			for (int var6 = 0; var6 < var5.size(); ++var6) {
				Entity currentEntity = (Entity) var5.get(var6);
				if (currentEntity.canBeCollidedWith()) {
					RayTraceResult objPosition = currentEntity.getEntityBoundingBox().expand((double) currentEntity.getCollisionBorderSize(), (double) currentEntity.getCollisionBorderSize(), (double) currentEntity.getCollisionBorderSize())
							.contract(0.1, 0.1, 0.1).calculateIntercept(position, position.addVector(lookVector.xCoord * reachDistance, lookVector.yCoord * reachDistance, lookVector.zCoord * reachDistance));
					if (objPosition != null) {
						double distance = position.distanceTo(objPosition.hitVec);
						if (distance < reachDistance) {
							if (currentEntity == mc.player.ridingEntity && reachDistance == 0.0D) {
								pointedEntity = currentEntity;
							} else {
								pointedEntity = currentEntity;
								reachDistance = distance;
							}
						}
					}
				}
			}
			if (pointedEntity != null && (pointedEntity instanceof EntityLivingBase))
				return (EntityLivingBase) pointedEntity;
		}
		return null;
	}

	public static float clampF(float intended, float current, float maxchange) {
		float change = MathHelper.wrapAngleTo180_float(intended - current);
		if (change > maxchange) {
			change = maxchange;
		} else if (change < -maxchange) {
			change = -maxchange;
		}
		return MathHelper.wrapAngleTo180_float(current + change);
	}

	public static float[] getRotationsToEnt(Entity ent, EntityPlayerSP playerSP) {
		final double differenceX = ent.posX - playerSP.posX;
		final double differenceY = (ent.posY + ent.height) - (playerSP.posY + playerSP.height);
		final double differenceZ = ent.posZ - playerSP.posZ;
		final float rotationYaw = (float) (Math.atan2(differenceZ, differenceX) * 180.0D / Math.PI) - 90.0f;
		final float rotationPitch = (float) (Math.atan2(differenceY, playerSP.getDistanceToEntity(ent)) * 180.0D / Math.PI);
		final float finishedYaw = playerSP.rotationYaw + MathHelper.wrapAngleTo180_float(rotationYaw - playerSP.rotationYaw);
		final float finishedPitch = playerSP.rotationPitch + MathHelper.wrapAngleTo180_float(rotationPitch - playerSP.rotationPitch);
		return new float[] { finishedYaw, -finishedPitch };
	}
}