package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import optifine.Reflector;

import java.util.List;

import java.util.Random;

public class CombatUtil {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static float[] getRotations(Entity entity) {
        double pX = Minecraft.player.posX;
        double pY = Minecraft.player.posY + (double) Minecraft.player.getEyeHeight();
        double pZ = Minecraft.player.posZ;
        double eX = entity.posX;
        double eY = entity.posY + (double) (entity.height / 2.0f);
        double eZ = entity.posZ;
        double dX = pX - eX;
        double dY = pY - eY;
        double dZ = pZ - eZ;
        double dH = Math.sqrt(Math.pow(dX, 2.0) + Math.pow(dZ, 2.0));
        double yaw = Math.toDegrees(Math.atan2(dZ, dX)) + 90.0;
        double pitch = Math.toDegrees(Math.atan2(dH, dY));
        return new float[]{(float) yaw, (float) (90.0 - pitch)};
    }
    
    public static Object[] getEntityCustom(float pitch, float yaw, final double distance, final double expand, final float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();
        final Entity var2 = mc.getRenderViewEntity();
        Entity entity = null;
        if (var2 == null || mc.world == null) {
            return null;
        }
        mc.mcProfiler.startSection("pick");
        final Vec3d var3 = var2.getPositionEyes(0.0f);
		final Vec3d var4 = var2.getLookCustom(pitch, yaw , 0.0f);
        final Vec3d var5 = var3.addVector(var4.xCoord * distance, var4.yCoord * distance, var4.zCoord * distance);
        Vec3d var6 = null;
        final float var7 = 1.0f;
        final List var8 = mc.world.getEntitiesWithinAABBExcludingEntity(var2, var2.getEntityBoundingBox().addCoord(var4.xCoord * distance, var4.yCoord * distance, var4.zCoord * distance).expand(var7, var7, var7));
        double var9 = distance;
        for (int var10 = 0; var10 < var8.size(); ++var10) {
            final Entity var11 = (Entity) var8.get(var10);
            if (var11.canBeCollidedWith()) {
                final float var12 = var11.getCollisionBorderSize();

                AxisAlignedBB var13 = var11.getEntityBoundingBox().expand(var12, var12, var12);
                var13 = var13.expand(expand, expand, expand);
                final RayTraceResult var14 = var13.calculateIntercept(var3, var5);
                if (var13.isVecInside(var3)) {
                    if (0.0 < var9 || var9 == 0.0) {
                        entity = var11;
                        var6 = ((var14 == null) ? var3 : var14.hitVec);
                        var9 = 0.0;
                    }
                }
                else if (var14 != null) {
                    final double var15 = var3.distanceTo(var14.hitVec);
                    if (var15 < var9 || var9 == 0.0) {
                        boolean canRiderInteract = false;
                        if (Reflector.ForgeEntity_canRiderInteract.exists()) {
                            canRiderInteract = Reflector.callBoolean(var11, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
                        }
                        if (var11 == var2.ridingEntity && !canRiderInteract) {
                            if (var9 == 0.0) {
                                entity = var11;
                                var6 = var14.hitVec;
                            }
                        }
                        else {
                            entity = var11;
                            var6 = var14.hitVec;
                            var9 = var15;
                        }
                    }
                }
            }
        }
        if (var9 < distance && !(entity instanceof EntityLivingBase) && !(entity instanceof EntityItemFrame)) {
            entity = null;
        }
        mc.mcProfiler.endSection();
        if (entity == null || var6 == null) {
            return null;
        }
        return new Object[] { entity, var6 };
    }

    public static boolean hasWeapon() {
        if (Minecraft.player.inventory.getCurrentItem() != null)
            return false;

        return (Minecraft.player.inventory.getCurrentItem().getItem() instanceof ItemAxe) || (Minecraft.player.inventory.getCurrentItem().getItem() instanceof ItemSword);
    }

    public static boolean isHeldingSword() {
        return Minecraft.player.getHeldItemMainhand() != null && Minecraft.player.getHeldItemMainhand().getItem() instanceof ItemSword;
    }

    private static float[] getDirectionToEntity(Entity var0) {
        return new float[]{CombatUtil.getYaw(var0) + Minecraft.player.rotationYaw, CombatUtil.getPitch(var0) + Minecraft.player.rotationPitch};
    }

    public static float[] getDirectionToBlock(double var0, double var1, double var2, EnumFacing var3) {
        EntityEgg var4 = new EntityEgg(CombatUtil.mc.world);
        var4.posX = var0 + 0.5;
        var4.posY = var1 + 0.5;
        var4.posZ = var2 + 0.5;
        var4.posX += (double) var3.getDirectionVec().getX() * 0.25;
        var4.posY += (double) var3.getDirectionVec().getY() * 0.25;
        var4.posZ += (double) var3.getDirectionVec().getZ() * 0.25;
        return CombatUtil.getDirectionToEntity(var4);
    }

    public static float[] getRotationNeededForBlock(EntityPlayer paramEntityPlayer, BlockPos pos) {
        double d1 = (double) pos.getX() - paramEntityPlayer.posX;
        double d2 = (double) pos.getY() + 0.5 - (paramEntityPlayer.posY + (double) paramEntityPlayer.getEyeHeight());
        double d3 = (double) pos.getZ() - paramEntityPlayer.posZ;
        double d4 = Math.sqrt(d1 * d1 + d3 * d3);
        float f1 = (float) (Math.atan2(d3, d1) * 180.0 / 3.141592653589793) - 90.0f;
        float f2 = (float) (-Math.atan2(d2, d4) * 180.0 / 3.141592653589793);
        return new float[]{f1, f2};
    }

    public static float getYaw(Entity var0) {
        double var1 = var0.posX - Minecraft.player.posX;
        double var3 = var0.posZ - Minecraft.player.posZ;
        double var5 = var3 < 0.0 && var1 < 0.0 ? 90.0 + Math.toDegrees(Math.atan(var3 / var1)) : (var3 < 0.0 && var1 > 0.0 ? -90.0 + Math.toDegrees(Math.atan(var3 / var1)) : Math.toDegrees(-Math.atan(var1 / var3)));
        return MathHelper.wrapAngleTo180_float(-Minecraft.player.rotationYaw - (float) var5);
    }

    public static float getPitch(Entity var0) {
        double var1 = var0.posX - Minecraft.player.posX;
        double var3 = var0.posZ - Minecraft.player.posZ;
        double var5 = var0.posY - 1.6 + (double) var0.getEyeHeight() - Minecraft.player.posY;
        double var7 = MathHelper.sqrt_double(var1 * var1 + var3 * var3);
        double var9 = -Math.toDegrees(Math.atan(var5 / var7));
        return -MathHelper.wrapAngleTo180_float(Minecraft.player.rotationPitch - (float) var9);
    }

    public static float[] getRotationFromPosition(double x, double y, double z) {
        double xDiff = x - Minecraft.player.posX;
        double zDiff = z - Minecraft.player.posZ;
        double yDiff = y - Minecraft.player.posY - (double) Minecraft.player.getEyeHeight();
        double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float) (-Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793);
        return new float[]{yaw, pitch};
    }

    public static float[] getRotationsNeededBlock(double x, double y, double z) {
        double diffX = x + 0.5 - Minecraft.player.posX;
        double diffZ = z + 0.5 - Minecraft.player.posZ;
        double diffY = y + 0.5 - (Minecraft.player.posY + (double) Minecraft.player.getEyeHeight());
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float) (-Math.atan2(diffY, dist) * 180.0 / 3.141592653589793);
        return new float[]{Minecraft.player.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - Minecraft.player.rotationYaw), Minecraft.player.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Minecraft.player.rotationPitch)};
    }

    public static float[] getHypixelRotationsNeededBlock(double x, double y, double z) {
        double diffX = x + 0.5 - Minecraft.player.posX;
        double diffZ = z + 0.5 - Minecraft.player.posZ;
        double diffY = y + 0.5 - (Minecraft.player.posY + (double) Minecraft.player.getEyeHeight());
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float) (-Math.atan2(diffY, dist) * 180.0 / 3.141592653589793);
        return new float[]{Minecraft.player.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - (float) (120 + new Random().nextInt(2))), Minecraft.player.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Minecraft.player.rotationPitch)};
    }

    public static float[] getRotationsNeededBlock(double x, double y, double z, double x1, double y1, double z1) {
        double diffX = x1 + 0.5 - x;
        double diffZ = z1 + 0.5 - z;
        double diffY = y1 + 0.5 - (y + (double) Minecraft.player.getEyeHeight());
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float) (-Math.atan2(diffY, dist) * 180.0 / 3.141592653589793);
        return new float[]{yaw, pitch};
    }

    public static float getTrajAngleSolutionLow(float d3, float d1, float velocity) {
        float g = 0.006f;
        float sqrt = velocity * velocity * velocity * velocity - g * (g * (d3 * d3) + 2.0f * d1 * (velocity * velocity));
        return (float) Math.toDegrees(Math.atan(((double) (velocity * velocity) - Math.sqrt(sqrt)) / (double) (g * d3)));
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
        float angle = Math.abs(angle1 - angle2) % 360.0f;
        if (angle > 180.0f) {
            angle = 360.0f - angle;
        }
        return angle;
    }

    public static Vec3d[] getCorners(AxisAlignedBB box) {
        return new Vec3d[]{new Vec3d(box.minX, box.minY, box.minZ), new Vec3d(box.maxX, box.minY, box.minZ), new Vec3d(box.minX, box.maxY, box.minZ), new Vec3d(box.minX, box.minY, box.maxZ), new Vec3d(box.maxX, box.maxY, box.minZ), new Vec3d(box.minX, box.maxY, box.maxZ), new Vec3d(box.maxX, box.minY, box.maxZ), new Vec3d(box.maxX, box.maxY, box.maxZ)};
    }

    public static AxisAlignedBB getCloserBox(AxisAlignedBB b1, AxisAlignedBB b2) {
        Vec3d[] arrvec3 = CombatUtil.getCorners(b2);
        int n = arrvec3.length;
        int n2 = 0;
        while (n2 < n) {
            Vec3d pos = arrvec3[n2];
            if (CombatUtil.isRotationIn(CombatUtil.getRotationFromPosition(pos.xCoord, pos.yCoord, pos.zCoord), b1)) {
                return CombatUtil.getDistanceToBox(b2) < CombatUtil.getDistanceToBox(b1) ? b2 : b1;
            }
            ++n2;
        }
        return b2;
    }

    public static double getDistanceToBox(AxisAlignedBB box) {
        return Minecraft.player.getDistance((box.minX + box.maxX) / 2.0, (box.minY + box.maxY) / 2.0, (box.minZ + box.maxZ) / 2.0);
    }

    public static boolean isRotationIn(float[] rotation, AxisAlignedBB box) {
        float[] maxRotations = CombatUtil.getMaxRotations(box);
        return maxRotations[0] < rotation[0] && maxRotations[2] < rotation[1] && maxRotations[1] > rotation[0] && maxRotations[3] > rotation[1];
    }


    public static float[] getMaxRotations(AxisAlignedBB box) {
        float minYaw = 2.14748365E9f;
        float maxYaw = -2.14748365E9f;
        float minPitch = 2.14748365E9f;
        float maxPitch = -2.14748365E9f;
        Vec3d[] arrvec3 = CombatUtil.getCorners(box);
        int n = arrvec3.length;
        int n2 = 0;
        while (n2 < n) {
            Vec3d pos = arrvec3[n2];
            float[] rot = CombatUtil.getRotationFromPosition(pos.xCoord, pos.yCoord, pos.zCoord);
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

    public static AxisAlignedBB expandBox(AxisAlignedBB box, double multiplier) {
        multiplier = 1.0 - multiplier / 100.0;
        return box.expand((box.maxX - box.minX) * multiplier, 0.12, (box.maxZ - box.minZ) * multiplier);
    }

    public static AxisAlignedBB contractBox(AxisAlignedBB box, double multiplier) {
        multiplier = 1.0 - multiplier / 100.0;
        return box.contract((box.maxX - box.minX) * multiplier, 0.12, (box.maxZ - box.minZ) * multiplier);
    }

    public static float getYawDifference(float current, float target) {
        float rot = (target + 180.0f - current) % 360.0f;
        return rot + ((rot > 0.0f) ? -180.0f : 180.0f);
    }

    public static float getPitchDifference(float current, float target) {
        float rot = (target + 90.0f - current) % 180.0f;
        return rot + ((rot > 0.0f) ? -90.0f : 90.0f);
    }


    public static float[] getRotations(Object entity) {
        Entity eny = (Entity) entity;
        double pX = Minecraft.player.posX;
        double pY = Minecraft.player.posY + (double) Minecraft.player.getEyeHeight();
        double pZ = Minecraft.player.posZ;
        double eX = eny.posX;
        double eY = eny.posY + (double) (eny.height / 2.0F);
        double eZ = eny.posZ;
        double dX = pX - eX;
        double dY = pY - eY;
        double dZ = pZ - eZ;
        double dH = Math.sqrt(Math.pow(dX, 2.0D) + Math.pow(dZ, 2.0D));
        double yaw = Math.toDegrees(Math.atan2(dZ, dX)) + 90.0D;
        double pitch = Math.toDegrees(Math.atan2(dH, dY));
        return new float[]{(float) yaw, (float) (90.0D - pitch)};
    }

    public static double updateRotation(double p_706631, double p_706632, double d) {
        double var4 = MathHelper.wrapAngleTo180_float((float) (p_706632 - p_706631));
        if (var4 > d) {
            var4 = d;
        }
        if (var4 < -d) {
            var4 = -d;
        }
        return p_706631 + var4;
    }

    public static float[] getIntaveRots(BlockPos bp, EnumFacing enumface) {
        double x = (double) bp.getX() + 0.5;
        double y = (double) bp.getY() + 0.5;
        double z = (double) bp.getZ() + 0.5;
        if (enumface != null) {
            if (EnumFacing.UP != null) {
                y += 0.5;
            } else if (EnumFacing.DOWN != null) {
                y -= 0.5;
            } else if (EnumFacing.WEST != null) {
                x += 0.5;
            } else if (EnumFacing.EAST != null) {
                x -= 0.5;
            } else if (EnumFacing.NORTH != null) {
                z += 0.5;
            } else if (EnumFacing.SOUTH != null) {
                z -= 0.5;
            }
        }
        double dX = x - Minecraft.player.posX;
        double dY = y - Minecraft.player.posY + (double) Minecraft.player.getEyeHeight();
        double dZ = z - Minecraft.player.posZ;
        float yaw = (float) (Math.atan2(dZ, dX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float) ((-Math.atan2(dY, Math.sqrt(dX * dX + dZ * dZ))) * 180.0 / 3.141592653589793);
        yaw = MathHelper.wrapAngleTo180_float(yaw);
        pitch = MathHelper.wrapAngleTo180_float(pitch);
        return new float[]{yaw, pitch};
    }


}