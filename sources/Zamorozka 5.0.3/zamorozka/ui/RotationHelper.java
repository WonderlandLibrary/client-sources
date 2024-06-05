package zamorozka.ui;

import com.google.common.base.Predicates;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.*;

import java.util.List;
import java.util.Random;

public class RotationHelper implements MCUtil {
	
    public static float pitch() {
        return mc.player.rotationPitch;
    }

    public static void pitch(float pitch) {
        mc.player.rotationPitch = pitch;
    }

    public static float yaw() {
        return mc.player.rotationYaw;
    }

    public static void yaw(float yaw) {
        mc.player.rotationYaw = yaw;
    }

    public static float[] faceTarget(Entity target, float p_706252, float p_706253, boolean miss) {
        double var6;
        double var4 = target.posX - mc.player.posX;
        double var8 = target.posZ - mc.player.posZ;
        if (target instanceof EntityLivingBase) {
            EntityLivingBase var10 = (EntityLivingBase)target;
            var6 = var10.posY + (double)var10.getEyeHeight() - (mc.player.posY + (double)mc.player.getEyeHeight());
        } else {
            var6 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0 - (mc.player.posY + (double)mc.player.getEyeHeight());
        }
        Random rnd = new Random();
        double var14 = MathHelper.sqrt_double(var4 * var4 + var8 * var8);
        float var12 = (float)(Math.atan2(var8, var4) * 180.0 / 3.141592653589793) - 90.0f;
        float var13 = (float)(- Math.atan2(var6 - (target instanceof EntityPlayer ? 0.25 : 0.0), var14) * 180.0 / 3.141592653589793);
        float pitch = RotationHelper.changeRotation(mc.player.rotationPitch, var13, p_706253);
        float yaw = RotationHelper.changeRotation(mc.player.rotationYaw, var12, p_706252);
        return new float[]{yaw, pitch};
    }

    public static float changeRotation(float p_706631, float p_706632, float p_706633) {
        float var4 = MathHelper.wrapAngleTo180_float(p_706632 - p_706631);
        if (var4 > p_706633) {
            var4 = p_706633;
        }
        if (var4 < - p_706633) {
            var4 = - p_706633;
        }
        return p_706631 + var4;
    }

    public static double[] getRotationToEntity(Entity entity) {
        double pX = mc.player.posX;
        double pY = mc.player.posY + (double)mc.player.getEyeHeight();
        double pZ = mc.player.posZ;
        double eX = entity.posX;
        double eY = entity.posY + (double)(entity.height / 2.0f);
        double eZ = entity.posZ;
        double dX = pX - eX;
        double dY = pY - eY;
        double dZ = pZ - eZ;
        double dH = Math.sqrt(Math.pow(dX, 2.0) + Math.pow(dZ, 2.0));
        double yaw = Math.toDegrees(Math.atan2(dZ, dX)) + 90.0;
        double pitch = Math.toDegrees(Math.atan2(dH, dY));
        return new double[]{yaw, 90.0 - pitch};
    }

    public static float[] getRotations(Entity entity) {
        double diffY;
        if (entity == null) {
            return null;
        }
        double diffX = entity.posX - mc.player.posX;
        double diffZ = entity.posZ - mc.player.posZ;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase elb = (EntityLivingBase)entity;
            diffY = elb.posY + ((double)elb.getEyeHeight() - 0.4) - (mc.player.posY + (double)mc.player.getEyeHeight());
        } else {
            diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0 - (mc.player.posY + (double)mc.player.getEyeHeight());
        }
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(- Math.atan2(diffY, dist) * 180.0 / 3.141592653589793);
        return new float[]{yaw, pitch};
    }

    public static float getDistanceBetweenAngles(float angle1, float angle2) {
        float angle3 = Math.abs(angle1 - angle2) % 360.0f;
        if (angle3 > 180.0f) {
            angle3 = 0.0f;
        }
        return angle3;
    }

    public static float[] grabBlockRotations(BlockPos pos) {
        return RotationHelper.getVecRotation(mc.player.getPositionVector().addVector(0.0, mc.player.getEyeHeight(), 0.0));
    }

    public static float[] getVecRotation(Vec3d position) {
        return RotationHelper.getVecRotation(mc.player.getPositionVector().addVector(0.0, mc.player.getEyeHeight(), 0.0));
    }


    public static int wrapAngleToDirection(float yaw, int zones) {
        int angle = (int)((double)(yaw + (float)(360 / (2 * zones))) + 0.5) % 360;
        if (angle < 0) {
            angle += 360;
        }
        return angle / (360 / zones);
    }
    
    public static final float[] smoothRotation(final float[] currentRotations, final float[] targetRotations, final float rotationSpeed) {
		final float yawDiff = getDifference(targetRotations[0], currentRotations[0]);
		final float pitchDiff = getDifference(targetRotations[1], currentRotations[1]);

		float rotationSpeedYaw = rotationSpeed;

		if (yawDiff > rotationSpeed) {
			rotationSpeedYaw = rotationSpeed;
		} else {
			rotationSpeedYaw = Math.max(yawDiff, -rotationSpeed);
		}

		float rotationSpeedPitch = rotationSpeed;

		if (pitchDiff > rotationSpeed) {
			rotationSpeedPitch = rotationSpeed;
		} else {
			rotationSpeedPitch = Math.max(pitchDiff, -rotationSpeed);
		}

		final float newYaw = currentRotations[0] + rotationSpeedYaw;
		final float newPitch = currentRotations[1] + rotationSpeedPitch;

		return new float[] { newYaw, newPitch };
	}
    
    public final static float getDifference(final float a, final float b) {
		float r = ((a - b) % 360.0F);
		if (r < -180.0) {
			r += 360.0;
		}
		if (r >= 180.0) {
			r -= 360.0;
		}
		return r;
	}
    
    public static final Entity raycastEntity(double range, float[] rotations) {
		final Entity player = mc.getRenderViewEntity();

		if (player != null && mc.world != null) {
			final Vec3d eyeHeight = player.getPositionEyes(mc.timer.renderPartialTicks);

			final Vec3d looks = RotationHelper.getVectorForRotation(rotations[0], rotations[1]);
			final Vec3d vec = eyeHeight.addVector(looks.xCoord * range, looks.yCoord * range, looks.zCoord * range);
			final List<Entity> list = mc.world.getEntitiesInAABBexcluding(player, player.getEntityBoundingBox().addCoord(looks.xCoord * range, looks.yCoord * range, looks.zCoord * range).expand(1, 1, 1), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));

			Entity raycastedEntity = null;

			for (final Entity entity : list) {
				if (!(entity instanceof EntityLivingBase))
					continue;

				final float borderSize = entity.getCollisionBorderSize();
				final AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(borderSize, borderSize, borderSize);
				final RayTraceResult movingobjectposition = axisalignedbb.calculateIntercept(eyeHeight, vec);

				if (axisalignedbb.isVecInside(eyeHeight)) {
					if (range >= 0.0D) {
						raycastedEntity = entity;
						range = 0.0D;
					}
				} else if (movingobjectposition != null) {
					final double distance = eyeHeight.distanceTo(movingobjectposition.hitVec);

					if (distance < range || range == 0.0D) {

						if (entity == player.ridingEntity) {
							if (range == 0.0D) {
								raycastedEntity = entity;
							}
						} else {
							raycastedEntity = entity;
							range = distance;
						}
					}
				}
			}
			return raycastedEntity;
		}
		return null;
	}
    
    public final static Vec3d getVectorForRotation(final float yaw, final float pitch) {
		final double f = Math.cos(Math.toRadians(-yaw) - Math.PI);
		final double f1 = Math.sin(Math.toRadians(-yaw) - Math.PI);
		final double f2 = -Math.cos(Math.toRadians(-pitch));
		final double f3 = Math.sin(Math.toRadians(-pitch));
		return new Vec3d((f1 * f2), f3, (f * f2));
	}
    
    public final static Vec3d getVectorForRotation2(float yaw, float pitch)
    {
    	final double f = Math.cos(Math.toRadians(-yaw) - Math.PI);
    	final double f1 = Math.sin(Math.toRadians(-yaw) - Math.PI);
    	final double f2 = -Math.cos(Math.toRadians(-pitch));
    	final double f3 = Math.sin(Math.toRadians(-pitch));
        return new Vec3d((double)(f1 * f2), (double)f3, (double)(f * f2));
    }
    
    public static Entity raycast(final double range, final Entity target) {
		final Vec3d eyePosition = target.getPositionVector().add(new Vec3d(0, target.getEyeHeight(), 0));
		final Vec3d eyeHeight = mc.player.getPositionVector().add(new Vec3d(0, mc.player.getEyeHeight(), 0));
		Vec3d hitVec = null;
		final AxisAlignedBB axisAlignedBB = mc.player.getEntityBoundingBox().addCoord(eyePosition.xCoord - eyeHeight.xCoord, eyePosition.yCoord - eyeHeight.yCoord, eyePosition.zCoord - eyeHeight.zCoord).expand(1.0F, 1.0F, 1.0F);
		final List<Entity> entities = mc.world.getEntitiesWithinAABBExcludingEntity(mc.player, axisAlignedBB);
		double rangeExtension = range + 0.5;
		
		Entity raycastedEntity = null;
		for (final Entity entity : entities) {

			if (entity.canBeCollidedWith()) {
				float collisionSize = entity.getCollisionBorderSize();
				final AxisAlignedBB expandedBox = entity.getEntityBoundingBox().expand(collisionSize, collisionSize, collisionSize);
				final RayTraceResult movingObjectPosition = expandedBox.calculateIntercept(eyeHeight, eyePosition);

				if (expandedBox.isVecInside(eyeHeight)) {
					if (0.0D < rangeExtension || rangeExtension == 0.0D) {
						raycastedEntity = entity;
						hitVec = movingObjectPosition == null ? eyeHeight : movingObjectPosition.hitVec;
						rangeExtension = 0.0D;
					}
				} else if (movingObjectPosition != null) {
					final double eyeDistance = eyeHeight.distanceTo(movingObjectPosition.hitVec);

					if (eyeDistance < rangeExtension || rangeExtension == 0.0D) {
						raycastedEntity = entity;
						hitVec = movingObjectPosition.hitVec;
						rangeExtension = eyeDistance;
					}
				}
			}
		}
		return raycastedEntity;
	}

}