/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import javax.vecmath.Vector2f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import ru.govno.client.Client;
import ru.govno.client.utils.Combat.CountHelper;
import ru.govno.client.utils.Combat.GCDFix;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.RandomUtils;

public class RotationUtil {
    public static RotationUtil instance = new RotationUtil();
    private static final Minecraft mc = Minecraft.getMinecraft();
    static float prevAdditionYaw;
    public static Vec3d vec;
    public static float Yaw;
    public static float Pitch;

    public static boolean canSeeEntityAtFov(Entity entityLiving, float scope) {
        double diffX = entityLiving.posX - Minecraft.player.posX;
        double diffZ = entityLiving.posZ - Minecraft.player.posZ;
        float yaw = (float)(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0);
        double difference = RotationUtil.angleDifference(yaw, Minecraft.player.rotationYaw);
        return difference <= (double)scope;
    }

    public static double angleDifference(float oldYaw, float newYaw) {
        float yaw = Math.abs(oldYaw - newYaw) % 360.0f;
        if (yaw > 180.0f) {
            yaw = 360.0f - yaw;
        }
        return yaw;
    }

    public static float[] getFacePosRemote(Vec3d src, Vec3d dest) {
        double diffX = dest.xCoord - src.xCoord;
        double diffY = dest.yCoord - src.yCoord;
        double diffZ = dest.zCoord - src.zCoord;
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI));
        float[] arrf = new float[]{RotationUtil.fixRotation(Minecraft.player.rotationYaw, yaw), RotationUtil.fixRotation(Minecraft.player.rotationPitch, pitch)};
        return arrf;
    }

    public static float[] getFacePosEntityRemote(EntityLivingBase facing, Entity en) {
        if (en == null) {
            return new float[]{facing.rotationYaw, facing.rotationPitch};
        }
        Vec3d vec = new Vec3d(facing.posX, facing.posY, facing.posZ);
        Vec3d vec1 = new Vec3d(en.posX, en.posY, en.posZ);
        return RotationUtil.getFacePosRemote(new Vec3d(facing.posX, facing.posY, facing.posZ), new Vec3d(en.posX, en.posY, en.posZ));
    }

    public static EntityLivingBase getClosestEntityToEntity(float range, Entity ent) {
        EntityLivingBase closestEntity = null;
        float mindistance = range;
        for (Object o : Minecraft.getMinecraft().world.loadedEntityList) {
            EntityLivingBase en;
            if (!RotationUtil.isNotItem(o) || ent.isEntityEqual((EntityLivingBase)o) || !(ent.getDistanceToEntity(en = (EntityLivingBase)o) < mindistance)) continue;
            mindistance = ent.getDistanceToEntity(en);
            closestEntity = en;
        }
        return closestEntity;
    }

    public static boolean isNotItem(Object o) {
        return o instanceof EntityLivingBase;
    }

    public static float getAngleDifference(float dir, float yaw) {
        float f = Math.abs(yaw - dir) % 360.0f;
        return f > 180.0f ? 360.0f - f : f;
    }

    public static float[] getRotations(double x, double y, double z) {
        double diffX = x + 0.5 - Minecraft.player.posX;
        double diffY = (y + 0.5) / 2.0 - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
        double diffZ = z + 0.5 - Minecraft.player.posZ;
        double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI));
        return new float[]{yaw, pitch};
    }

    public static Float[] getLookAngles(Vec3d vec) {
        Float[] angles = new Float[2];
        Minecraft mc = Minecraft.getMinecraft();
        angles[0] = Float.valueOf((float)(Math.atan2(Minecraft.player.posZ - vec.zCoord, Minecraft.player.posX - vec.xCoord) / Math.PI * 180.0) + 90.0f);
        float heightdiff = (float)(Minecraft.player.posY + (double)Minecraft.player.getEyeHeight() - vec.yCoord);
        float distance = (float)Math.sqrt((Minecraft.player.posZ - vec.zCoord) * (Minecraft.player.posZ - vec.zCoord) + (Minecraft.player.posX - vec.xCoord) * (Minecraft.player.posX - vec.xCoord));
        angles[1] = Float.valueOf((float)(Math.atan2(heightdiff, distance) / Math.PI * 180.0));
        return angles;
    }

    public static Vec3d getBestPos(final Entity entity) {
        double diffX = entity.posX - Minecraft.player.posX;
        double diffZ = entity.posZ - Minecraft.player.posZ;
        float yaw = (float)(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0) + RotationUtil.getFixedRotation(Yaw);
        double diffY = entity.posY - Minecraft.player.posY;
        double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float pitch = (float)Math.toDegrees(-Math.atan2(diffY, dist)) + RotationUtil.getFixedRotation(Pitch);
        ArrayList<Vec3d> vec3ds = new ArrayList<Vec3d>();
        float X = (float)((entity.posX - entity.prevPosX) * (double)mc.getRenderPartialTicks());
        float Y = (float)((entity.posY - entity.prevPosY) * (double)mc.getRenderPartialTicks());
        float Z = (float)((entity.posZ - entity.prevPosZ) * (double)mc.getRenderPartialTicks());
        AxisAlignedBB aabb = entity.getEntityBoundingBox().offset(X, Y, Z);
        double accuracy = 0.2f;
        double minx = aabb.minX + accuracy;
        double maxx = aabb.maxX - accuracy;
        double miny = aabb.minY + accuracy;
        double maxy = aabb.maxY - accuracy;
        double minz = aabb.minZ + accuracy;
        double maxz = aabb.maxZ - accuracy;
        for (double x = minx; x <= maxx; x += accuracy) {
            for (double y = miny; y <= maxy; y += accuracy) {
                for (double z = minz; z <= maxz; z += accuracy) {
                    if (!Minecraft.player.canEntityBeSeenCoords(entity.posX + x, entity.posY + y, entity.posZ + z) || MathUtils.getPointedEntity(new Vector2f(yaw, pitch), 8.0, 1.0f, true) != null) continue;
                    vec3ds.add(new Vec3d(x, y, z));
                }
            }
        }
        vec3ds.sort(new Comparator<Vec3d>(){

            @Override
            public int compare(Vec3d o1, Vec3d o2) {
                float d = RotationUtil.getDistance(o1, new Vec3d(0.0, entity.getEyeHeight(), 0.0)) - RotationUtil.getDistance(o2, new Vec3d(0.0, entity.getEyeHeight(), 0.0));
                System.out.println();
                return (int)(d * 100000.0f);
            }
        });
        return (Vec3d)vec3ds.get(0);
    }

    public static float getDistance(Vec3d vec3d1, Vec3d vec3d2) {
        float f = (float)(vec3d1.xCoord - vec3d2.xCoord);
        float f1 = (float)(vec3d1.yCoord - vec3d2.yCoord);
        float f2 = (float)(vec3d1.zCoord - vec3d2.zCoord);
        return MathHelper.sqrt(f * f + f1 * f1 + f2 * f2);
    }

    public static float[] rotate(Entity base, boolean attackContext) {
        int yawDeltaAbs;
        Vec3d pos = RotationUtil.getBestPos(base);
        float pitchToHead = 0.0f;
        Minecraft.getMinecraft();
        EntityPlayerSP client = Minecraft.player;
        double x = base.posX + pos.xCoord - client.posX;
        double y = base.posY + pos.yCoord - client.getPositionEyes((float)1.0f).yCoord;
        double z = base.posZ + pos.zCoord - client.posZ;
        double dst = Math.sqrt(Math.pow(x, 2.0) + Math.pow(z, 2.0));
        pitchToHead = (float)(-Math.toDegrees(Math.atan2(y, dst)));
        float sensitivity = 1.0001f;
        double x2 = base.posX + pos.xCoord - client.posX;
        double y2 = base.posY + pos.yCoord - client.getPositionEyes((float)1.0f).yCoord;
        double z2 = base.posZ + pos.zCoord - client.posZ;
        double dst2 = Math.sqrt(Math.pow(x2, 2.0) + Math.pow(z2, 2.0));
        float yawToTarget = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(z2, x2)) - 90.0);
        float pitchToTarget = (float)(-Math.toDegrees(Math.atan2(y2, dst2)));
        float yawDelta = MathHelper.wrapDegrees(yawToTarget - Minecraft.player.lastReportedYaw) / sensitivity;
        float pitchDelta = (pitchToTarget - EntityPlayerSP.lastReportedPitch) / sensitivity;
        if (yawDelta > 180.0f) {
            yawDelta -= 180.0f;
        }
        if ((yawDeltaAbs = (int)Math.abs(yawDelta)) < 360) {
            float pitchDeltaAbs = Math.abs(pitchDelta);
            float additionYaw = Math.min(Math.max(yawDeltaAbs, 1), 80);
            float additionPitch = Math.max(attackContext ? pitchDeltaAbs : 1.0f, 2.0f);
            if (Math.abs(additionYaw - prevAdditionYaw) <= 3.0f) {
                additionYaw = prevAdditionYaw + 3.1f;
            }
            float newYaw = Minecraft.player.lastReportedYaw + (yawDelta > 0.0f ? additionYaw : -additionYaw) * sensitivity;
            float newPitch = MathHelper.clamp(EntityPlayerSP.lastReportedPitch + (pitchDelta > 0.0f ? additionPitch : -additionPitch) * sensitivity, -90.0f, 90.0f);
            prevAdditionYaw = additionYaw;
            return new float[]{newYaw, newPitch};
        }
        return null;
    }

    public static Vec3d getResolvePos(Entity entity, double accuracy) {
        double renderOffsetX = (entity.posX - entity.prevPosX) * (double)mc.getRenderPartialTicks();
        double renderOffsetY = (entity.posY - entity.prevPosY) * (double)mc.getRenderPartialTicks();
        double renderOffsetZ = (entity.posZ - entity.prevPosZ) * (double)mc.getRenderPartialTicks();
        AxisAlignedBB aabb = entity.getEntityBoundingBox().offset(renderOffsetX, renderOffsetY, renderOffsetZ);
        List<Vec3d> points = RotationUtil.generateMultipoints(aabb, accuracy);
        if (points.isEmpty()) {
            return null;
        }
        points.sort(Comparator.comparingDouble(multipoint -> multipoint.distanceTo(Minecraft.player.getPositionEyes(mc.getRenderPartialTicks()))));
        return points.get(0);
    }

    public static Vec3d getResolvePosOfFake(EntityLivingBase fake, Entity entity, double accuracy) {
        double renderOffsetX = (entity.posX - entity.prevPosX) * (double)mc.getRenderPartialTicks();
        double renderOffsetY = (entity.posY - entity.prevPosY) * (double)mc.getRenderPartialTicks();
        double renderOffsetZ = (entity.posZ - entity.prevPosZ) * (double)mc.getRenderPartialTicks();
        AxisAlignedBB aabb = entity.getEntityBoundingBox().offset(renderOffsetX, renderOffsetY, renderOffsetZ);
        List<Vec3d> points = RotationUtil.generateMultipoints(aabb, accuracy);
        if (points.isEmpty()) {
            return null;
        }
        points.sort(Comparator.comparingDouble(multipoint -> multipoint.distanceTo(fake != null ? fake.getPositionEyes(mc.getRenderPartialTicks()) : Minecraft.player.getPositionEyes(mc.getRenderPartialTicks()))));
        return points.get(0);
    }

    private static List<Vec3d> generateMultipoints(AxisAlignedBB aabb, double accuracy) {
        ArrayList<Vec3d> multipoints = new ArrayList<Vec3d>();
        accuracy = 1.0 / accuracy;
        for (double x = aabb.minX; x < aabb.maxX; x += accuracy * (aabb.maxX - aabb.minX)) {
            for (double y = aabb.minY; y < aabb.maxY; y += accuracy * (aabb.maxY - aabb.minY)) {
                for (double z = aabb.minZ; z < aabb.maxZ; z += accuracy * (aabb.maxZ - aabb.minZ)) {
                    multipoints.add(new Vec3d(x, y, z));
                }
            }
        }
        return multipoints;
    }

    public static float[] getMatrixRots(Entity entityIn) {
        Entity e = entityIn;
        double d = e.posX - Minecraft.player.posX;
        double d1 = e.posZ - Minecraft.player.posZ;
        if (e instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase)e;
        }
        EntityLivingBase entitylivingbase = (EntityLivingBase)e;
        float y = (float)(entitylivingbase.posY + (double)entitylivingbase.getEyeHeight() - (double)(entitylivingbase.getEyeHeight() / 3.0f));
        double lastY = (double)y + 0.5 - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
        if (Minecraft.player.posY + 1.5 < e.posY) {
            lastY = Minecraft.player.getDistanceToEntity(e) > 3.0f ? (double)y - (double)0.7f - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight()) : (double)y - (double)0.2f - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
        }
        if (Minecraft.player.posY > e.posY + 2.0) {
            lastY = (double)y + (double)1.2f - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
        }
        if (Minecraft.player.posY > e.posY + 2.5) {
            lastY = Minecraft.player.getDistanceSqToEntity(e) <= 3.0 ? (double)y + 1.0 - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight()) : (double)y + (double)1.2f - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
        }
        if (Minecraft.player.posY > e.posY + 3.5) {
            lastY = (double)y + (double)2.2f - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
        }
        if (Minecraft.player.posY > e.posY + 4.5) {
            lastY = (double)y + (double)2.2f - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
        }
        double d2 = MathHelper.sqrt(d * d + d1 * d1);
        float yaw = (float)(Math.atan2(d1, d) * 180.0 / Math.PI - 92.0) + RandomUtils.nextFloat(1.0f, 6.0f);
        float pitch = (float)(-(Math.atan2(lastY, d2) * 210.0 / Math.PI)) + RandomUtils.nextFloat(1.0f, 6.0f);
        if (Minecraft.player.getDistanceToEntity(e) <= 3.0f) {
            yaw = (float)(Math.atan2(d1, d) * 180.0 / Math.PI - 92.0) + RandomUtils.nextFloat(1.0f, 5.0f);
            pitch = (float)(-(Math.atan2(lastY, d2) * 200.0 / Math.PI)) + RandomUtils.nextFloat(1.0f, 7.0f);
        }
        if (Minecraft.player.getDistanceToEntity(e) >= 2.0f) {
            yaw = (float)(Math.atan2(d1, d) * 180.0 / Math.PI - 92.0) + RandomUtils.nextFloat(1.0f, 6.0f);
            pitch = (float)(-(Math.atan2(lastY, d2) * 200.0 / Math.PI)) + RandomUtils.nextFloat(1.0f, 6.0f);
        }
        if (Minecraft.player.getDistanceToEntity(e) >= 4.0f) {
            yaw = (float)(Math.atan2(d1, d) * 180.0 / Math.PI - 92.0) + RandomUtils.nextFloat(1.0f, 5.0f);
            pitch = (float)(-(Math.atan2(lastY, d2) * 200.0 / Math.PI)) + RandomUtils.nextFloat(1.0f, 5.0f);
        }
        if (Minecraft.player.getDistanceToEntity(e) <= 0.5f) {
            yaw = (float)(Math.atan2(d1, d) * 180.0 / Math.PI - 92.0) + RandomUtils.nextFloat(1.0f, 6.0f);
            pitch = (float)(-(Math.atan2(lastY, d2) * 180.0 / Math.PI)) + RandomUtils.nextFloat(1.0f, 6.0f);
        }
        yaw = Minecraft.player.rotationYaw + RotationUtil.getSensitivity(MathHelper.wrapDegrees(yaw - Minecraft.player.rotationYaw));
        pitch = Minecraft.player.rotationPitch + RotationUtil.getSensitivity(MathHelper.wrapDegrees(pitch - Minecraft.player.rotationPitch));
        pitch = MathHelper.clamp(pitch, -89.999f, 87.5f);
        Yaw += RotationUtil.getSensitivity(MathHelper.clamp(MathHelper.wrapDegrees(yaw - Yaw), -80.0f, 80.0f));
        Pitch += RotationUtil.getSensitivity(MathHelper.clamp(pitch - Pitch, -5.0f, 5.0f));
        return new float[]{Yaw, Pitch};
    }

    public static float[] getRots(Entity entityIn, boolean random, boolean turnAwayBoundingBox) {
        Vec3d to = entityIn.getBestVec3dOnEntityBox();
        if (to == null) {
            to = entityIn.getPositionVector().addVector(0.0, entityIn.getEyeHeight(), 0.0);
        }
        float[] rotate = new float[]{Yaw, Pitch};
        try {
            rotate = RotationUtil.getNeededFacing(to, random, Minecraft.player, turnAwayBoundingBox);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public static float[] getRots2(Entity entityIn, boolean random, boolean turnAwayBoundingBox) {
        return RotationUtil.getNeededFacing2((EntityLivingBase)entityIn, random, turnAwayBoundingBox);
    }

    public static boolean canEntityBeSeen(Vec3d pos, Vec3d pos2) {
        return RotationUtil.mc.world.rayTraceBlocks(pos, pos2, false, true, false) == null;
    }

    public static Vec3d getPos(EntityLivingBase target, float range) {
        Vec3d vec3d = null;
        for (Vec3d vec : RotationUtil.getPosityionEye(target, range)) {
            if (!RotationUtil.canEntityBeSeen(vec, Minecraft.player.getPositionVector())) continue;
            vec3d = vec;
        }
        return vec3d;
    }

    public static ArrayList<Vec3d> getPosityionEye(EntityLivingBase target, float range) {
        ArrayList<Vec3d> posses = new ArrayList<Vec3d>();
        float X = (float)((target.posX - target.prevPosX) * (double)mc.getRenderPartialTicks());
        float Y = (float)((target.posY - target.prevPosY) * (double)mc.getRenderPartialTicks());
        float Z = (float)((target.posZ - target.prevPosZ) * (double)mc.getRenderPartialTicks());
        AxisAlignedBB aabb = target.getEntityBoundingBox().offset(X, Y, Z);
        double accuracy = 0.05f;
        double minx = aabb.minX + accuracy;
        double maxx = aabb.maxX + accuracy;
        double miny = aabb.minY + accuracy;
        double maxy = aabb.maxY - accuracy;
        double minz = aabb.minZ - accuracy;
        double maxz = aabb.maxZ - accuracy;
        for (double x = minx; x <= maxx; x += accuracy) {
            for (double y = miny; y <= maxy; y += accuracy) {
                for (double z = minz; z <= maxz; z += accuracy) {
                    Vec3d vec = new Vec3d(x, y, z);
                    if (!(RotationUtil.getDistance(vec, Minecraft.player.getPositionVector()) <= range)) continue;
                    range = RotationUtil.getDistance(vec, Minecraft.player.getPositionVector());
                    posses.add(vec);
                }
            }
        }
        return posses;
    }

    public static float[] getMatrixRots2(Entity e, float range) {
        double diffZ;
        double diffX;
        double diffY;
        double raz;
        block11: {
            block9: {
                block10: {
                    raz = MathUtils.clamp(Minecraft.player.posY + (double)Minecraft.player.getEyeHeight() - e.posY, (double)e.height * 0.3, (double)Math.min(Minecraft.player.getEyeHeight(), e.height * 0.8f));
                    diffY = e.posY + raz - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
                    vec = RotationUtil.getPos((EntityLivingBase)e, range);
                    if (vec == null) break block9;
                    if (!(Minecraft.player.getSmoothDistanceToEntityXZ(e) > 2.0f)) break block9;
                    diffX = RotationUtil.vec.xCoord - Minecraft.player.getPositionVector().xCoord;
                    diffZ = RotationUtil.vec.zCoord - Minecraft.player.getPositionVector().zCoord;
                    if ((double)Minecraft.player.getDistanceToEntity(e) <= 0.5) break block10;
                    if ((int)Minecraft.player.posX != (int)e.posX) break block11;
                    if ((int)Minecraft.player.posZ != (int)e.posZ) break block11;
                }
                diffX = e.posX - Minecraft.player.posX;
                diffZ = e.posZ - Minecraft.player.posZ;
                break block11;
            }
            diffX = e.posX - Minecraft.player.posX;
            diffZ = e.posZ - Minecraft.player.posZ;
        }
        if (e instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase)e;
        }
        EntityLivingBase entitylivingbase = (EntityLivingBase)e;
        float y = (float)(raz + Minecraft.player.posY);
        double lastY = diffY;
        double distance = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        float randomXZ = (float)MathUtils.randomNumber(1.0, -1.0);
        float randomY = (float)MathUtils.randomNumber(0.5, -0.5);
        if (MathUtils.getPointedEntity(new Vector2f(Yaw, Pitch), range, 1.0f, true) == null) {
            randomXZ = -randomXZ;
            randomY = -randomY;
        }
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI - 92.0) + randomXZ;
        float pitch = (float)(-(Math.atan2(lastY, distance) * 210.0 / Math.PI)) + randomY;
        if (Minecraft.player.getDistanceToEntity(e) <= 3.0f) {
            yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI - 92.0) + randomXZ;
            pitch = (float)(-(Math.atan2(lastY, distance) * 170.0 / Math.PI)) + randomY;
        }
        if (Minecraft.player.getDistanceToEntity(e) >= 2.0f) {
            yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI - 92.0) + randomXZ;
            pitch = (float)(-(Math.atan2(lastY, distance) * 200.0 / Math.PI)) + randomY;
        }
        if (Minecraft.player.getDistanceToEntity(e) >= 4.0f) {
            yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI - 92.0) + randomXZ;
            pitch = (float)(-(Math.atan2(lastY, distance) * 200.0 / Math.PI)) + randomY;
        }
        if (Minecraft.player.getDistanceToEntity(e) <= 0.5f) {
            yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI - 92.0) + randomXZ;
            pitch = (float)(-(Math.atan2(lastY, distance) * 180.0 / Math.PI)) + randomY;
        }
        if (Minecraft.player.getDistanceToEntity(e) <= 1.0f) {
            yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI - 92.0) + randomXZ;
            pitch = (float)(-(Math.atan2(lastY, distance) * 180.0 / Math.PI)) + randomY;
        }
        yaw = Minecraft.player.rotationYaw + RotationUtil.getSensitivity(MathHelper.wrapDegrees(yaw - Minecraft.player.rotationYaw));
        pitch = Minecraft.player.rotationPitch + RotationUtil.getSensitivity(MathHelper.wrapDegrees(pitch - Minecraft.player.rotationPitch));
        pitch = MathHelper.clamp(pitch, -89.999f, 87.5f);
        Yaw += RotationUtil.getSensitivity(MathHelper.clamp(MathHelper.wrapDegrees(yaw - Yaw), -(30.0f + randomXZ), 30.0f + randomXZ));
        Pitch += RotationUtil.getSensitivity(MathHelper.clamp(pitch - Pitch, -(10.0f + randomY), 10.0f + randomY));
        return new float[]{Yaw, Pitch};
    }

    public static float getSensitivity(float rot) {
        return RotationUtil.round(rot) * RotationUtil.lese();
    }

    public static float round(float delta) {
        return Math.round(delta / RotationUtil.lese());
    }

    public static float lese() {
        return (float)((double)RotationUtil.getLastSensivity() * 0.15);
    }

    public static float getLastSensivity() {
        float sensivity = (float)((double)RotationUtil.mc.gameSettings.mouseSensitivity * 0.6 + 0.2);
        return sensivity * sensivity * sensivity * 8.0f;
    }

    public static float RotateHui(float from, float to, float minstep, float maxstep) {
        float f = MathUtils.wrapDegrees(to - from) * MathUtils.clamp(0.6f, 0.0f, 1.0f);
        f = f < 0.0f ? MathUtils.clamp(f, -maxstep, -minstep) : MathUtils.clamp(f, minstep, maxstep);
        if (Math.abs(f) > Math.abs(MathUtils.wrapDegrees(to - from))) {
            return to;
        }
        return from + f;
    }

    public static float[] getRotationsHui(Entity entityIn) {
        double diffX = entityIn.posX - Minecraft.player.posX;
        double diffZ = entityIn.posZ - Minecraft.player.posZ;
        double diffY = entityIn instanceof EntityLivingBase ? entityIn.posY + (double)entityIn.getEyeHeight() - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight()) - (double)0.2f : (entityIn.getEntityBoundingBox().minY + entityIn.getEntityBoundingBox().maxY) / 2.0 - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
        if (!Minecraft.player.canEntityBeSeen(entityIn)) {
            diffY = entityIn.posY + (double)entityIn.height - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
        }
        double diffXZ = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0 + (double)GCDFix.getFixedRotation((float)RandomUtils.getRandomDouble(-1.75, 1.75)));
        float pitch = (float)(Math.toDegrees(-Math.atan2(diffY, diffXZ)) + (double)GCDFix.getFixedRotation((float)RandomUtils.getRandomDouble(-1.8f, 1.75)));
        yaw = Minecraft.player.rotationYaw + GCDFix.getFixedRotation(MathHelper.wrapDegrees(yaw - Minecraft.player.rotationYaw));
        pitch = Minecraft.player.rotationPitch + GCDFix.getFixedRotation(MathHelper.wrapDegrees(pitch - Minecraft.player.rotationPitch));
        pitch = MathHelper.clamp(pitch, -90.0f, 90.0f);
        return new float[]{yaw, pitch};
    }

    public static float[] getSunriseRots(Entity e) {
        float[] rots = RotationUtil.getRotationsHui(e);
        Yaw = GCDFix.getFixedRotation(RotationUtil.RotateHui(Yaw, rots[0], 40.0f, 50.0f));
        Pitch = GCDFix.getFixedRotation(RotationUtil.RotateHui(Pitch, rots[1], 0.35f, 2.1f));
        return new float[]{Yaw, Pitch};
    }

    public static float[] getRotationsOfFakeEnt(EntityLivingBase me, Entity e) {
        if (e != null && me != null) {
            double diffY = RotationUtil.getResolvePosOfFake((EntityLivingBase)me, (Entity)e, (double)9.0).yCoord - me.posY - (double)me.getEyeHeight();
            double diffX = RotationUtil.getResolvePosOfFake((EntityLivingBase)me, (Entity)e, (double)6.0).xCoord - me.posX;
            double diffZ = RotationUtil.getResolvePosOfFake((EntityLivingBase)me, (Entity)e, (double)6.0).zCoord - me.posZ;
            double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
            float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI - 90.0);
            float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI));
            yaw = me.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - me.rotationYaw);
            pitch = me.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - me.rotationPitch);
            pitch = MathHelper.clamp_float(pitch, -90.0f, 90.0f);
            return new float[]{yaw, pitch};
        }
        return new float[]{0.0f, 0.0f};
    }

    public static float[] getMatrixRotations4(Entity target) {
        double XDiff = target.posX - target.lastTickPosX;
        double YDiff = target.posY - target.lastTickPosY;
        double ZDiff = target.posZ - target.lastTickPosZ;
        float predict = 2.0f;
        double x = target.posX + XDiff * (double)predict;
        double y = target.posY + YDiff * (double)predict;
        double z = target.posZ + ZDiff * (double)predict;
        double diffX = x - Minecraft.player.posX;
        double diffZ = z - Minecraft.player.posZ;
        double diffY = y + (double)Minecraft.player.getEyeHeight() - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight()) - 0.4;
        double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)((double)((float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI - 90.0)) + MathUtils.getRandomInRange(-1.6f, 1.6f));
        float pitch = (float)((double)((float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI))) + MathUtils.getRandomInRange(-1.6f, 1.6f));
        yaw = Minecraft.player.rotationYaw + GCDFix.getFixedRotation(MathHelper.wrapDegrees(yaw - Minecraft.player.rotationYaw));
        pitch = Minecraft.player.rotationPitch + GCDFix.getFixedRotation(MathHelper.wrapDegrees(pitch - Minecraft.player.rotationPitch));
        pitch = MathHelper.clamp(pitch, -90.0f, 90.0f);
        return new float[]{yaw -= (Minecraft.player.lastReportedPreYaw - Minecraft.player.rotationYaw) * 2.0f, pitch};
    }

    public static float[] getMatrixRotations4BlockPos(BlockPos target) {
        double x = (float)target.getX() + 0.5f;
        double y = (float)target.getY() - 0.5f;
        double z = (float)target.getZ() + 0.5f;
        double diffX = x - Minecraft.player.posX;
        double diffZ = z - Minecraft.player.posZ;
        double diffY = y + (double)Minecraft.player.getEyeHeight() - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight()) - 0.4;
        double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI - 90.0);
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI));
        yaw = Minecraft.player.rotationYaw + RotationUtil.getSensitivity(MathHelper.wrapDegrees(yaw - Minecraft.player.rotationYaw));
        pitch = Minecraft.player.rotationPitch + RotationUtil.getSensitivity(MathHelper.wrapDegrees(pitch - Minecraft.player.rotationPitch));
        pitch = MathHelper.clamp(pitch, -89.999f, 87.5f);
        Yaw += RotationUtil.getSensitivity(MathHelper.clamp(MathHelper.wrapDegrees(yaw - Yaw), -20.0f, 20.0f));
        Pitch += RotationUtil.getSensitivity(MathHelper.clamp(pitch - Pitch, -5.0f, 5.0f));
        return new float[]{Yaw, Pitch};
    }

    public static float[] getLookRotations(Entity e, boolean oldPositionUse) {
        double diffY;
        double diffX = (oldPositionUse ? e.prevPosX : e.posX) - (oldPositionUse ? Minecraft.player.prevPosX : Minecraft.player.posX);
        double diffZ = (oldPositionUse ? e.prevPosZ : e.posZ) - (oldPositionUse ? Minecraft.player.prevPosZ : Minecraft.player.posZ);
        if (e instanceof EntityLivingBase) {
            EntityLivingBase entitylivingbase = (EntityLivingBase)e;
            float randomed = RandomUtils.nextFloat((float)(entitylivingbase.posY + (double)(entitylivingbase.getEyeHeight() / 1.05f)), (float)(entitylivingbase.posY + (double)entitylivingbase.getEyeHeight() - (double)(entitylivingbase.getEyeHeight() / 3.0f)));
            diffY = (double)randomed - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
        } else {
            diffY = (double)RandomUtils.nextFloat((float)e.getEntityBoundingBox().minY, (float)e.getEntityBoundingBox().maxY) - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
        }
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI - 90.0);
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI));
        yaw = Minecraft.player.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - Minecraft.player.rotationYaw);
        pitch = Minecraft.player.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Minecraft.player.rotationPitch);
        pitch = MathHelper.clamp_float(pitch, -90.0f, 90.0f);
        return new float[]{yaw, pitch};
    }

    public static float[] getLookRots(Entity self, Entity ent) {
        double diffY;
        double diffX = ent.posX - self.posX;
        double diffZ = ent.posZ - self.posZ;
        if (ent instanceof EntityLivingBase) {
            EntityLivingBase entitylivingbase = (EntityLivingBase)ent;
            float randomed = RandomUtils.nextFloat((float)(entitylivingbase.posY + (double)(entitylivingbase.getEyeHeight() / 1.05f)), (float)(entitylivingbase.posY + (double)entitylivingbase.getEyeHeight() - (double)(entitylivingbase.getEyeHeight() / 3.0f)));
            diffY = (double)randomed - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
        } else {
            diffY = (double)RandomUtils.nextFloat((float)ent.getEntityBoundingBox().minY, (float)ent.getEntityBoundingBox().maxY) - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
        }
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI - 90.0);
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI));
        yaw = Minecraft.player.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - Minecraft.player.rotationYaw);
        pitch = Minecraft.player.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Minecraft.player.rotationPitch);
        pitch = MathHelper.clamp_float(pitch, -90.0f, 90.0f);
        return new float[]{yaw, pitch};
    }

    public static float[] getRatationsG1(Entity entity) {
        double diffX = entity.posX - Minecraft.player.posX;
        double diffZ = entity.posZ - Minecraft.player.posZ;
        double diffY = entity instanceof EntityLivingBase ? entity.posY + (double)entity.getEyeHeight() - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight()) - 2.8 : (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
        double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI - 90.0) + CountHelper.nextFloat(-1.5f, 2.0f);
        float yawBody = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI - 90.0);
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / 5.0 + (double)CountHelper.nextFloat(-1.0f, 1.0f)));
        float pitch2 = (float)(-(Math.atan2(diffY, dist) * 180.0 / 5.0));
        if (Math.abs(yaw - Minecraft.player.rotationYaw) > 160.0f) {
            Minecraft.player.setSprinting(false);
        }
        yaw = Minecraft.player.prevRotationYaw + GCDFix.getFixedRotation(MathHelper.wrapDegrees(yaw - Minecraft.player.rotationYaw));
        yawBody = Minecraft.player.prevRotationYaw + MathHelper.wrapDegrees(yawBody - Minecraft.player.rotationYaw);
        pitch = Minecraft.player.prevRotationPitch + GCDFix.getFixedRotation(MathHelper.wrapDegrees(pitch - Minecraft.player.rotationPitch));
        pitch = MathHelper.clamp(pitch, -90.0f, 90.0f);
        return new float[]{yaw, pitch, yawBody, pitch2};
    }

    public static float[] getBowRotations(Entity e, boolean oldPositionUse) {
        double diffY;
        double diffX = (oldPositionUse ? e.prevPosX : e.posX) - (oldPositionUse ? Minecraft.player.prevPosX : Minecraft.player.posX);
        double diffZ = (oldPositionUse ? e.prevPosZ : e.posZ) - (oldPositionUse ? Minecraft.player.prevPosZ : Minecraft.player.posZ);
        if (e instanceof EntityLivingBase) {
            EntityLivingBase entitylivingbase = (EntityLivingBase)e;
            float randomed = RandomUtils.nextFloat((float)(entitylivingbase.posY + (double)(entitylivingbase.getEyeHeight() / 1.5f)), (float)(entitylivingbase.posY + (double)entitylivingbase.getEyeHeight() - (double)(entitylivingbase.getEyeHeight() / 3.0f)));
            diffY = (double)randomed - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
        } else {
            diffY = (double)RandomUtils.nextFloat((float)e.getEntityBoundingBox().minY, (float)e.getEntityBoundingBox().maxY) - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
        }
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI - 90.0);
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI));
        yaw = Minecraft.player.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - Minecraft.player.rotationYaw);
        pitch = Minecraft.player.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Minecraft.player.rotationPitch);
        pitch = MathHelper.clamp_float(pitch, -90.0f, 90.0f);
        return new float[]{yaw, pitch};
    }

    public static float getYawToEntity(Entity entity) {
        double pX = Minecraft.player.posX;
        double pZ = Minecraft.player.posZ;
        double eX = entity.posX;
        double eZ = entity.posZ;
        double dX = pX - eX;
        double dZ = pZ - eZ;
        double yaw = Math.toDegrees(Math.atan2(dZ, dX)) + 90.0;
        return (float)yaw;
    }

    public static float getYawToEntity(Entity mainEntity, Entity targetEntity) {
        double pX = mainEntity.posX;
        double pZ = mainEntity.posZ;
        double eX = targetEntity.posX;
        double eZ = targetEntity.posZ;
        double dX = pX - eX;
        double dZ = pZ - eZ;
        double yaw = Math.toDegrees(Math.atan2(dZ, dX)) + 90.0;
        return (float)yaw;
    }

    public static float getNormalizedYaw(float yaw) {
        float yawStageFirst = yaw % 360.0f;
        if (yawStageFirst > 180.0f) {
            return yawStageFirst -= 360.0f;
        }
        if (yawStageFirst < -180.0f) {
            return yawStageFirst += 360.0f;
        }
        return yawStageFirst;
    }

    public static boolean isAimAtMe(Entity entity) {
        float entityYaw = RotationUtil.getNormalizedYaw(entity.rotationYaw);
        float entityPitch = entity.rotationPitch;
        double pMinX = Minecraft.player.getEntityBoundingBox().minX;
        double pMaxX = Minecraft.player.getEntityBoundingBox().maxX;
        double pMaxY = Minecraft.player.posY + (double)Minecraft.player.height;
        double pMinY = Minecraft.player.getEntityBoundingBox().minY;
        double pMaxZ = Minecraft.player.getEntityBoundingBox().maxZ;
        double pMinZ = Minecraft.player.getEntityBoundingBox().minZ;
        double eX = entity.posX;
        double eY = entity.posY + (double)(entity.height / 2.0f);
        double eZ = entity.posZ;
        double dMaxX = pMaxX - eX;
        double dMaxY = pMaxY - eY;
        double dMaxZ = pMaxZ - eZ;
        double dMinX = pMinX - eX;
        double dMinY = pMinY - eY;
        double dMinZ = pMinZ - eZ;
        double dMinH = Math.sqrt(Math.pow(dMinX, 2.0) + Math.pow(dMinZ, 2.0));
        double dMaxH = Math.sqrt(Math.pow(dMaxX, 2.0) + Math.pow(dMaxZ, 2.0));
        double maxPitch = 90.0 - Math.toDegrees(Math.atan2(dMaxH, dMaxY));
        double minPitch = 90.0 - Math.toDegrees(Math.atan2(dMinH, dMinY));
        boolean yawAt = Math.abs(RotationUtil.getNormalizedYaw(RotationUtil.getYawToEntity(entity, Minecraft.player)) - entityYaw) <= 16.0f - Minecraft.player.getDistanceToEntity(entity) / 2.0f;
        boolean pitchAt = maxPitch >= (double)entityPitch && (double)entityPitch >= minPitch || minPitch >= (double)entityPitch && (double)entityPitch >= maxPitch;
        return yawAt && pitchAt;
    }

    public static float getSensitivityMultiplier() {
        float f = RotationUtil.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        return f * f * f * 8.0f * 0.15f;
    }

    public static float getYawToPos(BlockPos blockPos) {
        int pX = Minecraft.player.getPosition().getX();
        int pZ = Minecraft.player.getPosition().getZ();
        double dX = pX - blockPos.getX();
        double dZ = pZ - blockPos.getZ();
        double yaw = Math.toDegrees(Math.atan2(dZ, dX)) + 90.0;
        return (float)yaw;
    }

    public static float[] faceTarget(Entity target, float p_706252, float p_706253, boolean miss) {
        double posY;
        double posX = target.posX - Minecraft.player.posX;
        double posZ = target.posZ - Minecraft.player.posZ;
        if (target instanceof EntityLivingBase) {
            EntityLivingBase var6 = (EntityLivingBase)target;
            posY = var6.posY + (double)var6.getEyeHeight() - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
        } else {
            posY = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0 - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
        }
        float range = Minecraft.player.getDistanceToEntity(target);
        float calculate = MathHelper.sqrt(posX * posX + posZ * posZ);
        float var9 = (float)(Math.atan2(posZ, posX) * 180.0 / Math.PI) - 90.0f + (float)RandomUtils.randomNumber((int)(4.0f / range + calculate), (int)(-5.0f / range + calculate));
        float var10 = (float)(-(Math.atan2(posY, calculate) * 180.0 / Math.PI) + (double)RandomUtils.randomNumber((int)(5.0f / range + calculate), (int)(-4.0f / range + calculate)));
        float f = RotationUtil.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float pitch = RotationUtil.updateRotation(Minecraft.player.rotationPitch, var10, p_706253);
        float yaw = RotationUtil.updateRotation(Minecraft.player.rotationYaw, var9, p_706252);
        float gcd = f * f * f * 1.2f + (float)RandomUtils.randomNumber((int)f, (int)(-f));
        yaw -= yaw % gcd;
        pitch -= pitch % gcd;
        return new float[]{yaw, pitch};
    }

    public static float updateRotation(float current, float intended, float speed) {
        float f = MathHelper.wrapDegrees(intended - current);
        if (f > speed) {
            f = speed;
        }
        if (f < -speed) {
            f = -speed;
        }
        return current + f;
    }

    public static float fixRotation(float p_70663_1_, float p_70663_2_) {
        float var4 = MathHelper.wrapDegrees(p_70663_2_ - p_70663_1_);
        if (var4 > 360.0f) {
            var4 = 360.0f;
        }
        if (var4 < -360.0f) {
            var4 = -360.0f;
        }
        return p_70663_1_ + var4;
    }

    public static float[] getRotationFromPosition(double x, double z, double y) {
        double xDiff = x - Minecraft.player.posX;
        double zDiff = z - Minecraft.player.posZ;
        double yDiff = y - Minecraft.player.posY - 1.2;
        double dist = MathHelper.sqrt(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / Math.PI));
        return new float[]{yaw, pitch};
    }

    public static Vec3d getEyesPos() {
        return new Vec3d(Minecraft.player.posX, Minecraft.player.posY + (double)Minecraft.player.getEyeHeight(), Minecraft.player.posZ);
    }

    static float[] getTurnAwayedRotate(float[] prevRotate, double randomYawPos) {
        float yawPlus = 0.0f;
        float pitchPlus = 0.0f;
        boolean saded = false;
        randomYawPos = randomYawPos > 0.0 ? 1.0 : -1.0;
        for (int ext = 0; ext <= 30; ++ext) {
            saded = MathUtils.getPointedEntity(new Vector2f(prevRotate[0] + yawPlus, prevRotate[1]), 6.0, 1.0f, false) == null;
            if (saded) continue;
            yawPlus = (float)((double)yawPlus + 6.0 * randomYawPos);
        }
        boolean saded2 = false;
        for (int ext = 0; ext <= 30; ++ext) {
            saded2 = MathUtils.getPointedEntity(new Vector2f(prevRotate[0], prevRotate[1] + pitchPlus), 6.0, 1.0f, false) == null;
            if (saded2) continue;
            pitchPlus = (float)((double)pitchPlus + 3.0 * randomYawPos);
        }
        float newYaw = prevRotate[0];
        float newPitch = prevRotate[1];
        if (Math.abs(yawPlus) > Math.abs(pitchPlus)) {
            newPitch += pitchPlus;
        } else {
            newYaw += yawPlus;
        }
        return new float[]{newYaw, newPitch};
    }

    static float randF(float min, float max) {
        float random = new Random().nextFloat();
        return MathUtils.clamp(min + (max - min) * random, min, max);
    }

    public static float[] getNeededFacing(Vec3d vec, boolean randomizer, Entity rotateAt, boolean turnAwayBoundingBox) {
        Vec3d eyesPos = new Vec3d(rotateAt.posX, rotateAt.posY + (double)rotateAt.getEyeHeight(), rotateAt.posZ);
        double[] diffs = new double[]{vec.xCoord - eyesPos.xCoord, vec.yCoord - eyesPos.yCoord, vec.zCoord - eyesPos.zCoord};
        diffs = new double[]{diffs[0], diffs[1], diffs[2], Math.sqrt(diffs[0] * diffs[0] + diffs[2] * diffs[2])};
        float R_T_D = 57.29578f;
        float yaw = (float)Math.atan2(diffs[2], diffs[0]) * 57.29578f - 90.0f;
        float pitch = (float)Math.atan2(diffs[1], diffs[3]) * -57.29578f;
        float randYaw = 0.0f;
        if (randomizer) {
            float randStrengh = 1.07f * MathUtils.clamp(1.0f - (float)rotateAt.getDistanceAtVec3dToVec3d(eyesPos, vec) / 2.25f, 0.0f, 1.0f);
            yaw = Yaw + RotationUtil.getFixedRotation(yaw - Yaw) + (randomizer ? (randYaw = RotationUtil.randF(-randStrengh, randStrengh)) : 0.0f);
            pitch = Pitch + RotationUtil.getFixedRotation(pitch - Pitch) + (randomizer ? RotationUtil.randF(-randStrengh, randStrengh) : 0.0f);
        }
        if (turnAwayBoundingBox) {
            float[] turnedRotate = RotationUtil.getTurnAwayedRotate(new float[]{yaw, pitch}, randomizer ? (double)randYaw : 0.0);
            yaw = turnedRotate[0];
            pitch = turnedRotate[1];
        }
        yaw = GCDFix.getFixedRotation(rotateAt.rotationYaw + MathHelper.wrapDegrees(yaw - rotateAt.rotationYaw));
        pitch = GCDFix.getFixedRotation(rotateAt.rotationPitch + MathHelper.wrapDegrees(pitch - rotateAt.rotationPitch));
        if (randomizer) {
            float randomSpeed = 0.85f + 0.15f * RotationUtil.randF(0.0f, 1.0f);
            float speedYaw = 73.15f + 14.65f * randomSpeed;
            float speedPitch = 14.45f + 6.475f * randomSpeed;
            Yaw = GCDFix.getFixedRotation(Yaw + MathHelper.clamp(MathHelper.wrapDegrees(yaw - Yaw), -speedYaw, speedYaw));
            Pitch = GCDFix.getFixedRotation(Pitch + (float)MathHelper.clamp(MathHelper.clamp((double)pitch, -89.5, 89.5) - (double)Pitch, (double)(-speedPitch), (double)speedPitch));
        }
        return new float[]{randomizer ? Yaw : yaw, randomizer ? Pitch : pitch};
    }

    private static double sqrt(double var1, double var3) {
        return Math.sqrt(Math.pow(var1, 2.0) + Math.pow(var3, 2.0));
    }

    public static float M(float def, float min, float max) {
        return Math.min(Math.max(def, min), max);
    }

    public static float[] getNeededFacing2(EntityLivingBase target, boolean randomizer, boolean turnAwayBoundingBox) {
        Vec3d eyesPos = RotationUtil.getEyesPos();
        Vec3d to = target.getBestVec3dOnEntityBox();
        Vec3d rot = to.addVector(-eyesPos.xCoord, -eyesPos.yCoord, -eyesPos.zCoord);
        double xzD = MathHelper.sqrt(rot.xCoord * rot.xCoord + rot.zCoord * rot.zCoord);
        float yaw = (float)(Math.atan2(rot.zCoord, rot.xCoord) * 180.0 / Math.PI - 90.0);
        float pitch = (float)Math.toDegrees(-Math.atan2(rot.yCoord, xzD));
        if (turnAwayBoundingBox) {
            float[] turnedRotate = RotationUtil.getTurnAwayedRotate(new float[]{yaw, pitch}, -1.0);
            yaw = turnedRotate[0];
            pitch = turnedRotate[1];
        }
        float yRand = 0.5f + Math.abs(Pitch - pitch) / 2.5f;
        yaw = Yaw + RotationUtil.getFixedRotation(MathUtils.wrapAngleTo180_float(yaw - Yaw - (0.5f + 1.0f * (float)Math.random()) * yRand));
        pitch = MathHelper.clamp(Pitch + RotationUtil.getFixedRotation(MathHelper.wrapDegrees(pitch - Pitch)), -90.0f, 90.0f);
        if (randomizer) {
            Pitch += MathUtils.clamp(MathUtils.wrapDegrees(pitch - Pitch), -5.0f, 5.0f);
            float yawSpeed = 40.0f - 20.0f * (float)Math.random();
            Yaw += MathUtils.clamp(MathUtils.wrapDegrees(yaw - Yaw), -yawSpeed, yawSpeed);
            return new float[]{Yaw, Pitch};
        }
        return new float[]{yaw, pitch};
    }

    public static double Interpolate(double current, double old, double scale) {
        return old + (current - old) * scale;
    }

    public static float getDeltaMouse(float delta) {
        return Math.round(delta / RotationUtil.getGCDValue());
    }

    public static float randomNumber(float max, float min) {
        return (float)((double)min + Math.random() * (double)(max - min));
    }

    public static float getFixedRotation(float rot) {
        return RotationUtil.getDeltaMouse(rot) * RotationUtil.getGCDValue();
    }

    public static float getAngle(Entity entity) {
        double d = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)Minecraft.getMinecraft().getRenderPartialTicks();
        Minecraft.getMinecraft().getRenderManager();
        double x = d - RenderManager.renderPosX;
        double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)Minecraft.getMinecraft().getRenderPartialTicks();
        Minecraft.getMinecraft().getRenderManager();
        double z = d2 - RenderManager.renderPosZ;
        float yaw = (float)(-Math.toDegrees(Math.atan2(x, z)));
        double d3 = yaw;
        Minecraft.getMinecraft();
        double d4 = Minecraft.player.rotationYaw;
        Minecraft.getMinecraft();
        return (float)(d3 - RotationUtil.Interpolate(d4, Minecraft.player.prevRotationYaw, 1.0));
    }

    public static float getGCDValue() {
        return (float)((double)RotationUtil.getGCD() * 0.15);
    }

    public static float getGCD() {
        float f1 = (float)((double)Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6 + 0.2);
        return f1 * f1 * f1 * 8.0f;
    }

    public static float Rotate(float from, float to, float minstep, float maxstep) {
        float f = MathUtils.wrapDegrees(to - from) * 0.6f;
        f = f < 0.0f ? MathUtils.clamp(f, -maxstep, -minstep) : MathUtils.clamp(f, minstep, maxstep);
        if (Math.abs(f) > Math.abs(MathUtils.wrapDegrees(to - from))) {
            return to;
        }
        return MathUtils.wrapDegrees(from + f);
    }

    public static float[] rotats(EntityLivingBase entity, float Yaw, float Pitch) {
        double diffX = entity.posX - Minecraft.player.posX;
        double diffZ = entity.posZ - Minecraft.player.posZ;
        double raz = MathUtils.clamp(Minecraft.player.posY + (double)Minecraft.player.getEyeHeight() - entity.posY, (double)entity.height * 0.3, (double)Math.min(Minecraft.player.getEyeHeight(), entity.height * 0.8f));
        double diffY = entity.posY + raz - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
        if (!entity.canEntityBeSeen(Minecraft.player)) {
            diffY = entity.posY + (double)entity.height - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
        }
        double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0) + RotationUtil.getFixedRotation(Yaw);
        float pitch = (float)Math.toDegrees(-Math.atan2(diffY, dist)) + RotationUtil.getFixedRotation(Pitch);
        yaw = Minecraft.player.rotationYaw + RotationUtil.getFixedRotation(MathUtils.wrapDegrees(yaw - Minecraft.player.rotationYaw));
        pitch = Minecraft.player.rotationPitch + RotationUtil.getFixedRotation(pitch - Minecraft.player.rotationPitch);
        pitch = MathUtils.clamp(pitch, -80.0f, 85.0f);
        return new float[]{yaw, pitch};
    }

    public static float[] rotationBebra(EntityLivingBase entity) {
        double diffX = entity.posX - Minecraft.player.posX;
        double diffZ = entity.posZ - Minecraft.player.posZ;
        double diffY = entity.posY + (double)(entity.height / 2.0f) - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
        if (!entity.canEntityBeSeen(Minecraft.player)) {
            diffY = entity.posY + (double)entity.height - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
        }
        double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0);
        float pitch = (float)Math.toDegrees(-Math.atan2(diffY, dist));
        Yaw = Minecraft.player.rotationYaw + RotationUtil.getFixedRotation(MathUtils.wrapDegrees(Yaw - Minecraft.player.rotationYaw));
        Pitch = Minecraft.player.rotationPitch + RotationUtil.getFixedRotation(Pitch - Minecraft.player.rotationPitch);
        Yaw += MathUtils.clamp(MathUtils.wrapDegrees(yaw - Yaw), -80.0f, 80.0f);
        Pitch += MathUtils.clamp(pitch - Pitch, -20.0f, 20.0f);
        Yaw += RotationUtil.getFixedRotation((float)RandomUtils.getRandomDouble(-1.5, 1.5));
        Pitch += RotationUtil.getFixedRotation((float)RandomUtils.getRandomDouble(-1.5, 1.5));
        Yaw = MathUtils.wrapDegrees(Yaw);
        Pitch = MathUtils.clamp(Pitch, -90.0f, 90.0f);
        Client.msg(Yaw + " " + Pitch, false);
        return new float[]{Yaw, Pitch};
    }

    static {
        Yaw = 0.0f;
        Pitch = 0.0f;
    }
}

