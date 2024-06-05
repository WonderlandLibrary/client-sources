package me.superskidder.lune.utils.player;

import java.util.List;
import java.util.Random;

import me.superskidder.lune.utils.client.Location;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RotationUtils {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static float[] getRot(EntityLivingBase EntityLivingBase) {
        if (EntityLivingBase != null) {
            double d = mc.thePlayer.posX;
            double d2 = mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight();
            double d3 = mc.thePlayer.posZ;
            double d4 = EntityLivingBase.posX;
            double d5 = EntityLivingBase.posY + (double) (EntityLivingBase.height / 2.0f);
            double d6 = EntityLivingBase.posZ;
            double d7 = d - d4;
            double d8 = d2 - d5;
            double d9 = d3 - d6;
            double d10 = Math.sqrt(Math.pow(d7, 2.0) + Math.pow(d9, 2.0));
            float f = (float) (Math.toDegrees(Math.atan2(d9, d7)) + 90.0);
            float f2 = (float) Math.toDegrees(Math.atan2(d10, d8));
            return new float[]{(float) ((double) f + (new Random().nextBoolean() ? Math.random() : -Math.random())),
                    (float) ((double) (90.0f - f2) + (new Random().nextBoolean() ? Math.random() : -Math.random()))};
        }
        return null;
    }

    public static float[] getBlockRotations(int x2, int y2, int z2, EnumFacing facing) {
        Minecraft mc = Minecraft.getMinecraft();
        Minecraft mc2 = Minecraft.getMinecraft();
        EntitySnowball temp = new EntitySnowball(mc.theWorld);
        temp.posX = (double) x2 + 0.5;
        temp.posY = (double) y2 + 0.5;
        temp.posZ = (double) z2 + 0.5;
        return RotationUtils.getAngles(temp);
    }

    public static float getYawChangeGiven(double posX, double posZ, float yaw) {
        double deltaX = posX - Minecraft.getMinecraft().thePlayer.posX;
        double deltaZ = posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double yawToEntity;
        if (deltaZ < 0.0D && deltaX < 0.0D) {
            yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
        } else if (deltaZ < 0.0D && deltaX > 0.0D) {
            yawToEntity = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
        } else {
            yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }

        return MathHelper.wrapAngleTo180_float(-(yaw - (float) yawToEntity));
    }

    public static float[] getRandomRotationsInBox(final AxisAlignedBB box) {
        final float[] maxRotations = getMaxRotations(box);
        final float yaw = (float) MathHelper.getRandomDoubleInRange(new Random(), maxRotations[0], maxRotations[1]);
        final float pitch = (float) MathHelper.getRandomDoubleInRange(new Random(), maxRotations[2], maxRotations[3]);
        return new float[]{yaw, pitch};
    }

//	public static float[] getRotationsForAura(final EntityPlayer entityPlayer, final double range) {
//		if (entityPlayer == null) {
//			System.out.println("Fuck you ! Entity is nul!");
//			return null;
//		}
//		final double n2 = entityPlayer.posX - Minecraft.getMinecraft().thePlayer.posX;
//		final double n3 = entityPlayer.posZ - Minecraft.getMinecraft().thePlayer.posZ;
//		Class1356 class1356 = new Class1356(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ);
//		final Class1356 class1357 = new Class1356(Minecraft.getMinecraft().thePlayer.posX,
//				Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight(),
//				Minecraft.getMinecraft().thePlayer.posZ);
//		double n4 = entityPlayer.boundingBox.minY + 0.7;
//		while (n4 < entityPlayer.boundingBox.maxY - 0.1) {
//			if (class1357.distanceTo(new Class1356(entityPlayer.posX, n4, entityPlayer.posZ)) < class1357
//					.distanceTo(class1356)) {
//				class1356 = new Class1356(entityPlayer.posX, n4, entityPlayer.posZ);
//			}
//			n4 += 0.1;
//		}
//		if (class1357.distanceTo(class1356) >= range) {
//			return null;
//		}
//		return new float[] { (float) (Math.atan2(n3, n2) * 180.0 / 3.141592653589793) - 90.0f,
//				(float) (-(Math.atan2(class1356.getY()
//						- (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight()),
//						MathHelper.sqrt_double(n2 * n2 + n3 * n3)) * 180.0 / 3.141592653589793)) };
//	}

    public static float[] faceTarget(Entity target, float p_706252, float p_706253, boolean miss) {
        Minecraft mc = Minecraft.getMinecraft();
        double var6;
        double var4 = target.posX - mc.thePlayer.posX;
        double var8 = target.posZ - mc.thePlayer.posZ;
        if (target instanceof EntityLivingBase) {
            EntityLivingBase var10 = (EntityLivingBase) target;
            var6 = var10.posY + (double) var10.getEyeHeight()
                    - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
        } else {
            var6 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0
                    - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
        }

        double var14 = MathHelper.sqrt_double(var4 * var4 + var8 * var8);
        float var12 = (float) (Math.atan2(var8, var4) * 180.0 / 3.1415926535897932384626) - 90.0f;
        float var13 = (float) (-Math.atan2(var6 - (target instanceof EntityPlayer ? 0.25 : 0.0), var14) * 180.0
                / 3.1415926535897932384626);
        float pitch = changeRotation(mc.thePlayer.rotationPitch, var13, p_706253);
        float yaw = changeRotation(mc.thePlayer.rotationYaw, var12, p_706252);
        return new float[]{yaw, pitch};
    }

    public static float changeRotation(float p_706631, float p_706632, float p_706633) {
        float var4 = MathHelper.wrapAngleTo180_float(p_706632 - p_706631);
        if (var4 > p_706633) {
            var4 = p_706633;
        }
        if (var4 < -p_706633) {
            var4 = -p_706633;
        }
        return p_706631 + var4;
    }

    public static Vec3[] getCorners(AxisAlignedBB box) {
        return new Vec3[]{new Vec3(box.minX, box.minY, box.minZ), new Vec3(box.maxX, box.minY, box.minZ),
                new Vec3(box.minX, box.maxY, box.minZ), new Vec3(box.minX, box.minY, box.maxZ),
                new Vec3(box.maxX, box.maxY, box.minZ), new Vec3(box.minX, box.maxY, box.maxZ),
                new Vec3(box.maxX, box.minY, box.maxZ), new Vec3(box.maxX, box.maxY, box.maxZ)};
    }

    public static float[] getMaxRotations(AxisAlignedBB box) {
        float minYaw = 2.14748365E9f;
        float maxYaw = -2.14748365E9f;
        float minPitch = 2.14748365E9f;
        float maxPitch = -2.14748365E9f;
        Vec3[] arrvec3 = getCorners(box);
        int n = arrvec3.length;
        int n2 = 0;
        while (n2 < n) {
            Vec3 pos = arrvec3[n2];
            float[] rot = getRotationFromPosition(pos.xCoord, pos.yCoord, pos.zCoord);
            if (rot[0] < minYaw) {
                minYaw = rot[0];
            }
            if (rot[0] > maxYaw) {
                maxYaw = rot[0];
            }
            if (rot[1] < minPitch) {
                minPitch = rot[1];
            }
            if (rot[1] > maxPitch) {
                maxPitch = rot[1];
            }
            ++n2;
        }
        return new float[]{minYaw, maxYaw, minPitch, maxPitch};
    }

    public static float[] getAngles(Entity e2) {
        Minecraft mc2 = Minecraft.getMinecraft();
        return new float[]{RotationUtils.getYawChangeToEntity(e2) + mc.thePlayer.rotationYaw,
                RotationUtils.getPitchChangeToEntity(e2) + mc.thePlayer.rotationPitch};
    }

    public static float getYawChange(double posX, double posZ) {
        double deltaX = posX - Minecraft.getMinecraft().thePlayer.posX;
        double deltaZ = posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double yawToEntity;

        if (deltaZ < 0.0D && deltaX < 0.0D) {
            yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
        } else if (deltaZ < 0.0D && deltaX > 0.0D) {
            yawToEntity = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
        } else {
            yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }

        return MathHelper.wrapAngleTo180_float(-(Minecraft.getMinecraft().thePlayer.rotationYaw - (float) yawToEntity));
    }

    public static float getPitchChange(Entity entity, double posY) {
        double deltaX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
        double deltaZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double deltaY = posY - 2.2D + (double) entity.getEyeHeight() - Minecraft.getMinecraft().thePlayer.posY;
        double distanceXZ = (double) MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
        double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return -MathHelper
                .wrapAngleTo180_float(Minecraft.getMinecraft().thePlayer.rotationPitch - (float) pitchToEntity) - 2.5F;
    }

    public static float getNewAngle(float angle) {
        angle %= 360.0F;

        if (angle >= 180.0F) {
            angle -= 360.0F;
        }

        if (angle < -180.0F) {
            angle += 360.0F;
        }

        return angle;
    }

    public static float[] getRotations(Entity entity) {
        double diffY;
        if (entity == null) {
            return null;
        }
        Minecraft.getMinecraft();
        double diffX = entity.posX - mc.thePlayer.posX;
        Minecraft.getMinecraft();
        double diffZ = entity.posZ - mc.thePlayer.posZ;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase elb = (EntityLivingBase) entity;
            Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            diffY = elb.posY + ((double) elb.getEyeHeight() - 0.2)
                    - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
        } else {
            Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0
                    - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
        }
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float) ((-Math.atan2(diffY, dist)) * 180.0 / 3.141592653589793) - 60.0f;
        return new float[]{yaw, pitch};
    }

    public static float getYawChangeToEntity(Entity entity) {
        Minecraft mc2 = Minecraft.getMinecraft();
        double deltaX = entity.posX - mc.thePlayer.posX;
        double deltaZ = entity.posZ - mc.thePlayer.posZ;
        double yawToEntity = deltaZ < 0.0 && deltaX < 0.0 ? 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX))
                : (deltaZ < 0.0 && deltaX > 0.0 ? -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX))
                : Math.toDegrees(-Math.atan(deltaX / deltaZ)));
        return MathHelper.wrapAngleTo180_float(-mc.thePlayer.rotationYaw - (float) yawToEntity);
    }

    public static float getPitchChangeToEntity(Entity entity) {
        Minecraft mc2 = Minecraft.getMinecraft();
        double deltaX = entity.posX - mc.thePlayer.posX;
        double deltaZ = entity.posZ - mc.thePlayer.posZ;
        double deltaY = entity.posY - 1.6 + (double) entity.getEyeHeight() - 0.4 - mc.thePlayer.posY;
        double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
        double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return -MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationPitch - (float) pitchToEntity);
    }

    public static float[] getRotationFromPosition(double x2, double z2, double y2) {
        Minecraft.getMinecraft();
        double xDiff = x2 - mc.thePlayer.posX;
        Minecraft.getMinecraft();
        double zDiff = z2 - mc.thePlayer.posZ;
        Minecraft.getMinecraft();
        double yDiff = y2 - mc.thePlayer.posY - 0.8;
        double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) + 90.0f;
        float pitch = (float) ((-Math.atan2(yDiff, dist)) * 180.0 / 3.141592653589793) + 90.0f;
        return new float[]{yaw, pitch};
    }

    public static float getDistanceBetweenAngles(float angle1, float angle2) {
        float angle3 = Math.abs(angle1 - angle2) % 360.0f;
        if (angle3 > 180.0f) {
            angle3 = 0.0f;
        }
        return angle3;
    }

    public static float[] getBowAngles(Entity entity) {
        double xDelta = entity.posX - entity.lastTickPosX;
        double zDelta = entity.posZ - entity.lastTickPosZ;
        Minecraft.getMinecraft();
        double d2 = mc.thePlayer.getDistanceToEntity(entity);
        d2 -= d2 % 0.8;
        double xMulti = 1.0;
        double zMulti = 1.0;
        boolean sprint = entity.isSprinting();
        xMulti = d2 / 0.8 * xDelta * (sprint ? 1.25 : 1.0);
        zMulti = d2 / 0.8 * zDelta * (sprint ? 1.25 : 1.0);
        Minecraft.getMinecraft();
        double x2 = entity.posX + xMulti - mc.thePlayer.posX;
        Minecraft.getMinecraft();
        double z2 = entity.posZ + zMulti - mc.thePlayer.posZ;
        Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        double y2 = mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight()
                - (entity.posY + (double) entity.getEyeHeight());
        Minecraft.getMinecraft();
        double dist = mc.thePlayer.getDistanceToEntity(entity);
        float yaw = (float) Math.toDegrees(Math.atan2(z2, x2)) - 90.0f;
        float pitch = (float) Math.toDegrees(Math.atan2(y2, dist));
        return new float[]{yaw, pitch};
    }

    public static float normalizeAngle(float angle) {
        return (angle + 360.0f) % 360.0f;
    }

    public static float getTrajAngleSolutionLow(float d3, float d1, float velocity) {
        float g2 = 0.006f;
        float sqrt = velocity * velocity * velocity * velocity
                - g2 * (g2 * (d3 * d3) + 2.0f * d1 * (velocity * velocity));
        return (float) Math
                .toDegrees(Math.atan(((double) (velocity * velocity) - Math.sqrt(sqrt)) / (double) (g2 * d3)));
    }

    public static Vec3 getEyesPos() {
        return new Vec3(mc.thePlayer.posX,
                mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
    }

    public static float[] getRotationsBlock(BlockPos pos) {
        Minecraft mc2 = Minecraft.getMinecraft();
        double d0 = (double) pos.getX() - mc.thePlayer.posX;
        double d1 = (double) pos.getY() - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
        double d2 = (double) pos.getZ() - mc.thePlayer.posZ;
        double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        float f2 = (float) (MathHelper.atan2(d2, d0) * 180.0 / 3.141592653589793) - 90.0f;
        float f1 = (float) (-Math.toDegrees(Math.atan2(d3, d1)));
        return new float[]{f1};
    }

    public static float[] getNeededRotations(Vec3 vec) {
        Vec3 eyesPos = RotationUtils.getEyesPos();
        double diffX = vec.xCoord - eyesPos.xCoord + 0.5;
        double diffY = vec.yCoord - eyesPos.yCoord + 0.5;
        double diffZ = vec.zCoord - eyesPos.zCoord + 0.5;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float) ((-Math.atan2(diffY, diffXZ)) * 180.0 / 3.141592653589793);
        float[] arrf = new float[]{MathHelper.wrapAngleTo180_float(yaw),
                Minecraft.getMinecraft().gameSettings.keyBindJump.pressed ? 90.0f
                        : MathHelper.wrapAngleTo180_float(pitch)};
        return arrf;
    }

    public static void faceVectorPacketInstant(Vec3 vec) {
        float[] rotations = RotationUtils.getNeededRotations(vec);
        Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        mc.thePlayer.sendQueue.addToSendQueue(
                new C03PacketPlayer.C05PacketPlayerLook(rotations[0], rotations[1], mc.thePlayer.onGround));
    }

    public static float[] getRotationsForAura(Entity entity, double maxRange) {
        double diffY;
        if (entity == null) {
            System.out.println("Fuck you ! Entity is nul!");
            return null;
        }
        Minecraft.getMinecraft();
        double diffX = entity.posX - mc.thePlayer.posX;
        Minecraft.getMinecraft();
        double diffZ = entity.posZ - mc.thePlayer.posZ;
        Location BestPos = new Location(entity.posX, entity.posY, entity.posZ);
        Minecraft.getMinecraft();
        Location myEyePos = new Location(mc.thePlayer.posX,
                mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
        for (diffY = entity.boundingBox.minY + 0.7; diffY < entity.boundingBox.maxY - 0.1; diffY += 0.1) {
            if (myEyePos.distanceTo(new Location(entity.posX, diffY, entity.posZ)) >= myEyePos.distanceTo(BestPos))
                continue;
            BestPos = new Location(entity.posX, diffY, entity.posZ);
        }
        if (myEyePos.distanceTo(BestPos) >= maxRange) {
            return null;
        }
        diffY = BestPos.getY() - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float) (-Math.atan2(diffY, dist) * 180.0 / 3.141592653589793);
        return new float[]{yaw, pitch};
    }

    public static float[] getAverageRotations(List<EntityLivingBase> targetList) {
        double posX = 0.0;
        double posY = 0.0;
        double posZ = 0.0;
        for (Entity ent : targetList) {
            posX += ent.posX;
            posY += ent.boundingBox.maxY - 2.0;
            posZ += ent.posZ;
        }
        return new float[]{
                RotationUtils.getRotationFromPosition(posX /= (double) targetList.size(),
                        posZ /= (double) targetList.size(), posY /= (double) targetList.size())[0],
                RotationUtils.getRotationFromPosition(posX, posZ, posY)[1]};
    }

    public static float[] getRotationsEx(EntityLivingBase ent) {
        double x = ent.posX;
        double z = ent.posZ;
        double y = ent.posY + (double) (ent.getEyeHeight() / 2.0F);
        return getRotationFromPosition(x, z, y);
    }

    public static float pitch() {
        return mc.thePlayer.rotationPitch;
    }

    public static void pitch(float pitch) {
        mc.thePlayer.rotationPitch = pitch;
    }

    public static float yaw() {
        return mc.thePlayer.rotationYaw;
    }

    public static float[] getRotationsInsane(Entity entity, double maxRange) {
        if (entity == null) {
            System.out.println("Fuck you ! Entity is nul!");
            return null;
        } else {
            double diffX = entity.posX - mc.thePlayer.posX;
            double diffZ = entity.posZ - mc.thePlayer.posZ;
            Location BestPos = new Location(entity.posX, entity.posY, entity.posZ);
            Location myEyePos = new Location(mc.thePlayer.posX,
                    mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);

            for (double diffY = entity.boundingBox.minY + 0.7D; diffY < entity.boundingBox.maxY - 0.1D; diffY += 0.1D) {
                if (myEyePos.distanceTo(new Location(entity.posX, diffY, entity.posZ)) < myEyePos.distanceTo(BestPos)) {
                    BestPos = new Location(entity.posX, diffY, entity.posZ);
                }
            }

            if (myEyePos.distanceTo(BestPos) >= maxRange) {
                return null;
            } else {
                double var15 = BestPos.getY()
                        - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
                double dist = (double) MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
                float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
                float pitch = (float) (-(Math.atan2(var15, dist) * 180.0D / 3.141592653589793D));
                return new float[]{yaw, pitch};
            }
        }
    }

    public static boolean canEntityBeSeen(Entity e) {
        Minecraft mc = Minecraft.getMinecraft();
        Vec3 vec1 = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);

        AxisAlignedBB box = e.getEntityBoundingBox();
        Vec3 vec2 = new Vec3(e.posX, e.posY + (e.getEyeHeight() / 1.32F), e.posZ);
        double minx = e.posX - 0.25;
        double maxx = e.posX + 0.25;
        double miny = e.posY;
        double maxy = e.posY + Math.abs(e.posY - box.maxY);
        double minz = e.posZ - 0.25;
        double maxz = e.posZ + 0.25;
        boolean see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null ? true : false;
        if (see)
            return true;
        vec2 = new Vec3(maxx, miny, minz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null ? true : false;
        if (see)
            return true;
        vec2 = new Vec3(minx, miny, minz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null ? true : false;

        if (see)
            return true;
        vec2 = new Vec3(minx, miny, maxz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null ? true : false;
        if (see)
            return true;
        vec2 = new Vec3(maxx, miny, maxz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null ? true : false;
        if (see)
            return true;

        vec2 = new Vec3(maxx, maxy, minz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null ? true : false;

        if (see)
            return true;
        vec2 = new Vec3(minx, maxy, minz);

        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null ? true : false;
        if (see)
            return true;
        vec2 = new Vec3(minx, maxy, maxz - 0.1);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null ? true : false;
        if (see)
            return true;
        vec2 = new Vec3(maxx, maxy, maxz);
        see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null ? true : false;
        if (see)
            return true;

        return false;
    }

    public static float[] getPredictedRotations(EntityLivingBase ent) {
        double x = ent.posX + (ent.posX - ent.lastTickPosX);
        double z = ent.posZ + (ent.posZ - ent.lastTickPosZ);
        double y = ent.posY + ent.getEyeHeight() / 2.0F;
        return getRotationFromPosition(x, z, y);
    }

    public static double[] getRotationToEntity(Entity entity) {
        double pX = mc.thePlayer.posX;
        double pY = mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight();
        double pZ = mc.thePlayer.posZ;
        double eX = entity.posX;
        double eY = entity.posY + (double) (entity.height / 2.0f);
        double eZ = entity.posZ;
        double dX = pX - eX;
        double dY = pY - eY;
        double dZ = pZ - eZ;
        double dH = Math.sqrt(Math.pow(dX, 2.0) + Math.pow(dZ, 2.0));
        double yaw = Math.toDegrees(Math.atan2(dZ, dX)) + 90.0;
        double pitch = Math.toDegrees(Math.atan2(dH, dY));
        return new double[]{yaw, 90.0 - pitch};
    }


    public static float[] grabBlockRotations(BlockPos pos) {
        return RotationUtils.getVecRotation(
                mc.thePlayer.getPositionVector().addVector(0.0, mc.thePlayer.getEyeHeight(), 0.0),
                new Vec3((double) pos.getX() + 0.5, (double) pos.getY()-1, (double) pos.getZ() + 0.5));
    }

    public static float[] getVecRotation(Vec3 position) {
        return RotationUtils.getVecRotation(
                mc.thePlayer.getPositionVector().addVector(0.0, mc.thePlayer.getEyeHeight(), 0.0),
                position);
    }

    public static float[] getVecRotation(Vec3 origin, Vec3 position) {
        Vec3 difference = position.subtract(origin);
        double distance = difference.flat().lengthVector();
        float yaw = (float) Math.toDegrees(Math.atan2(difference.zCoord, difference.xCoord)) - 90.0f;
        float pitch = (float) (-Math.toDegrees(Math.atan2(difference.yCoord, distance)));
        return new float[]{yaw, pitch};
    }

    public static int wrapAngleToDirection(float yaw, int zones) {
        int angle = (int) ((double) (yaw + (float) (360 / (2 * zones))) + 0.5) % 360;
        if (angle < 0) {
            angle += 360;
        }
        return angle / (360 / zones);
    }

    public static float getYawChange(float yaw, double posX, double posZ) {
        double deltaX = posX - Minecraft.getMinecraft().thePlayer.posX;
        double deltaZ = posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double yawToEntity = 0;
        if ((deltaZ < 0.0D) && (deltaX < 0.0D)) {
            if (deltaX != 0)
                yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
        } else if ((deltaZ < 0.0D) && (deltaX > 0.0D)) {
            if (deltaX != 0)
                yawToEntity = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
        } else {
            if (deltaZ != 0)
                yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }

        return MathHelper.wrapAngleTo180_float(-(yaw - (float) yawToEntity));
    }

    public static float getPitchChange(float pitch, Entity entity, double posY) {
        double deltaX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
        double deltaZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double deltaY = posY - 2.2D + entity.getEyeHeight() - Minecraft.getMinecraft().thePlayer.posY;
        double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
        double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return -MathHelper.wrapAngleTo180_float(pitch - (float) pitchToEntity) - 2.5F;
    }

}
