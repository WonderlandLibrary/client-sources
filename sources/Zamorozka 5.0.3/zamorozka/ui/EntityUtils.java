package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class EntityUtils {
    public static Location3D Location;
    public static boolean lookChanged;
    public static float yaw;
    public static float pitch;
    public static Minecraft mc = Minecraft.getMinecraft();
    public static boolean targetInvisible;
    public static boolean targetPlayer;
    public static boolean targetMobs;
    public static boolean targetAnimals;
    public static boolean targetDead;

    public static synchronized float getDistanceToEntity(EntityLivingBase entity) {
        return Minecraft.player.getDistanceToEntity(entity);
    }

    public static void faceEntity(EntityLivingBase entity) {
        float[] rotations = getRotations(entity);
        if (rotations != null) {
            Minecraft.getMinecraft();
            Minecraft.player.rotationYaw = rotations[0];
            Minecraft.getMinecraft();
            Minecraft.player.rotationPitch = rotations[1] - 8.0F;
        }
    }

    public static Location predictEntityLocation(Entity e, double milliseconds) {
        if (e == null) {
            return null;
        }
        if (e.posX == e.lastTickPosX && e.posY == e.lastTickPosY && e.posZ == e.lastTickPosZ) {
            return new Location(e.posX, e.posY, e.posZ);
        }
        double ticks = milliseconds / 1000.0;
        return EntityUtils.interp(new Location(e.lastTickPosX, e.lastTickPosY, e.lastTickPosZ), new Location(e.posX + e.motionX, e.posY + e.motionY, e.posZ + e.motionZ), ticks *= 20.0);
    }

    public static Location interp(Location from, Location to, double pct) {
        double x = Location.x + (Location.x - Location.x) * pct;
        double y = Location.y + (Location.y - Location.y) * pct;
        double z = Location.z + (Location.z - Location.z) * pct;
        return new Location(x, y, z);
    }

    public static float[] getRotations(Entity ent) {
        double x = ent.posX;
        double z = ent.posZ;
        double y = ent.boundingBox.maxY - 4.5D;
        return getRotationFromPosition(x, z, y);
    }

    //EDITED
    public static float[] getRotationsNeeded(Entity entity) {
        if (entity == null) {
            return null;
        }
        double diffX = entity.posX - Minecraft.player.posX;
        double diffY;

        if ((entity instanceof EntityLivingBase)) {
            EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
            diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() * 0.9D - (Minecraft.player.posY + Minecraft.player.getEyeHeight());
        } else {
            diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D - (Minecraft.player.posY + Minecraft.player.getEyeHeight());
        }
        double diffZ = entity.posZ - Minecraft.player.posZ;
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float) (-Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D);
        return new float[]{Minecraft.player.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - Minecraft.player.rotationYaw), Minecraft.player.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Minecraft.player.rotationPitch)};
    }

    public static synchronized void faceEntityPacket(EntityLivingBase entity) {
        float[] rotations = getRotationsNeeded(entity);
        if (rotations != null) {
            yaw = limitAngleChange(Minecraft.player.prevRotationYaw, rotations[0], 55.0F);
            pitch = rotations[1];
            lookChanged = true;
        }
    }

    private static final float limitAngleChange(float current, float intended, float maxChange) {
        float change = intended - current;
        if (change > maxChange) {
            change = maxChange;
        } else if (change < -maxChange) {
            change = -maxChange;
        }
        return current + change;
    }

    public static float[] getRotationFromPosition(double x, double z, double y) {
        double xDiff = x - Minecraft.player.posX;
        double zDiff = z - Minecraft.player.posZ;
        double yDiff = y - Minecraft.player.posY + (double) Minecraft.player.getEyeHeight();
        double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float) ((double) ((float) (Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F) + Math.random() + Math.random() + Math.random() + Math.random() + Math.random() + Math.random());
        float pitch = (float) (-(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D + Math.random() + Math.random()));
        return new float[]{yaw, pitch};
    }

    //NEW
    public static float[] faceTarget(Entity target, float p_70625_2_, float p_70625_3_, boolean miss) {
        float yaw, pitch;
        double var4 = target.posX - Minecraft.player.posX;
        double var8 = target.posZ - Minecraft.player.posZ;
        double var6;
        if (target instanceof EntityLivingBase) {
            EntityLivingBase var10 = (EntityLivingBase) target;
            var6 = var10.posY + (double) var10.getEyeHeight()
                    - (Minecraft.player.posY + (double) Minecraft.player.getEyeHeight());
        } else {
            var6 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0D
                    - (Minecraft.player.posY + (double) Minecraft.player.getEyeHeight());
        }
        Random rnd = new Random();
        double var14 = MathHelper.sqrt_double(var4 * var4 + var8 * var8);
        float var12 = (float) (Math.atan2(var8, var4) * 180.0D / Math.PI) - 90.0F;
        float var13 = (float) (-(Math.atan2(var6 - (target instanceof EntityPlayer ? 0.25 : 0), var14)
                * 180.0D / Math.PI));
        pitch = changeRotation(Minecraft.player.rotationPitch, var13, p_70625_3_);
        yaw = changeRotation(Minecraft.player.rotationYaw, var12, p_70625_2_);
        return new float[]{yaw, pitch};
    }

    public static float changeRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_) {
        float var4 = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);
        if (var4 > p_70663_3_)
            var4 = p_70663_3_;
        if (var4 < -p_70663_3_)
            var4 = -p_70663_3_;
        return p_70663_1_ + var4;
    }

    public static Entity findClosestToCross(double range) {
        Entity e = Minecraft.player;
        double best = 360;
        for (Object o : Minecraft.getMinecraft().world.loadedEntityList) {
            if (o instanceof EntityPlayer) {
                EntityPlayer ent = (EntityPlayer) o;
                double diffX = ent.posX - Minecraft.player.posX;
                double diffZ = ent.posZ - Minecraft.player.posZ;
                float newYaw = (float) (Math.toDegrees(Math.atan2(diffZ, diffX)) - 90);
                double difference = Math.abs(angleDifference(newYaw, Minecraft.player.rotationYaw));
                if (ent != Minecraft.player && Minecraft.player.getDistanceToEntity(ent) <= range && ent instanceof EntityPlayer) {
                    if (difference < best) {
                        best = difference;
                        e = ent;
                    }
                }
            }
        }
        return e;
    }

    public static double angleDifference(double a, double b) {
        return ((((a - b) % 360D) + 360d) % 360D) - 180D;
    }

    public static boolean isWithingFOV(Entity en, float angle) {
        angle *= 0.5;
        double angleDifference = angleDifference(Minecraft.player.rotationYaw, getRotationToEntity(en)[0]);
        return (angleDifference > 0 && angleDifference < angle) || (-angle < angleDifference && angleDifference < 0);
    }

    public static double[] getRotationToEntity(Entity entity) {
        double pX = Minecraft.player.posX;
        double pY = Minecraft.player.posY + (Minecraft.player.getEyeHeight());
        double pZ = Minecraft.player.posZ;

        double eX = entity.posX;
        double eY = entity.posY + (entity.height / 2);
        double eZ = entity.posZ;

        double dX = pX - eX;
        double dY = pY - eY;
        double dZ = pZ - eZ;
        double dH = Math.sqrt(Math.pow(dX, 2) + Math.pow(dZ, 2));

        double yaw = (Math.toDegrees(Math.atan2(dZ, dX)) + 90);
        double pitch = (Math.toDegrees(Math.atan2(dH, dY)));

        return new double[]{yaw, 90 - pitch};
    }

    public static boolean isValid(boolean animals, boolean players, boolean invisibles, Entity e) {
        boolean state = false;
        if (!(!EntityUtils.isMob(e) || EntityUtils.isPlayer(e) || EntityUtils.isAnimal(e) || EntityUtils.isItem(e) || EntityUtils.isProjectile(e) || EntityUtils.isFalling(e) || EntityUtils.isInvisible(e))) {
            state = false;
        }
        if (!(!animals || !EntityUtils.isAnimal(e) || EntityUtils.isMob(e) || EntityUtils.isPlayer(e) || EntityUtils.isItem(e) || EntityUtils.isProjectile(e) || EntityUtils.isFalling(e) || EntityUtils.isInvisible(e))) {
            state = true;
        }
        if (!(!players || !EntityUtils.isPlayer(e) || EntityUtils.isMob(e) || EntityUtils.isAnimal(e) || EntityUtils.isItem(e) || EntityUtils.isProjectile(e) || EntityUtils.isFalling(e) || EntityUtils.isInvisible(e))) {
            state = true;
        }
        if (!(!EntityUtils.isProjectile(e) || EntityUtils.isMob(e) || EntityUtils.isAnimal(e) || EntityUtils.isPlayer(e) || EntityUtils.isItem(e) || EntityUtils.isFalling(e) || EntityUtils.isInvisible(e))) {
            state = false;
        }
        if (!(!EntityUtils.isItem(e) || EntityUtils.isMob(e) || EntityUtils.isPlayer(e) || EntityUtils.isProjectile(e) || EntityUtils.isAnimal(e) || EntityUtils.isFalling(e))) {
            state = false;
        }
        if (invisibles && EntityUtils.isInvisible(e) && animals && EntityUtils.isAnimal(e) && !EntityUtils.isProjectile(e) && !EntityUtils.isPlayer(e) && !EntityUtils.isMob(e)) {
            state = true;
        }
        if (invisibles && EntityUtils.isInvisible(e) && players && EntityUtils.isPlayer(e) && !EntityUtils.isProjectile(e) && !EntityUtils.isAnimal(e) && !EntityUtils.isMob(e)) {
            state = true;
        }
        if (invisibles && EntityUtils.isInvisible(e) && EntityUtils.isMob(e) && !EntityUtils.isProjectile(e) && !EntityUtils.isPlayer(e) && !EntityUtils.isAnimal(e)) {
            state = false;
        }
        return state;
    }

    public static boolean isPlayer(Entity e) {
        return e instanceof EntityPlayer;
    }

    public static boolean isInvisible(Entity e) {
        return e.isInvisible();
    }

    public static boolean isAnimal(Entity e) {
        return e instanceof EntityAnimal || e instanceof EntitySquid;
    }

    public static boolean isMob(Entity e) {
        return e instanceof IMob;
    }

    public static boolean isProjectile(Entity e) {
        return e instanceof EntityThrowable || e instanceof EntityFireball;
    }

    public static boolean isArrow(Entity e) {
        return e instanceof EntityArrow;
    }

    public static boolean isFalling(Entity e) {
        return e instanceof EntityFallingBlock;
    }

    public static boolean isItem(Entity e) {
        return e instanceof EntityItem;
    }

    public static boolean isNotPlayer(Entity e) {
        return EntityUtils.isAnimal(e) || EntityUtils.isMob(e) || EntityUtils.isProjectile(e) || EntityUtils.isFalling(e) || EntityUtils.isItem(e);
    }

    public static boolean isSelected(final Entity entity, final boolean canAttackCheck) {
        if (!(entity instanceof EntityLivingBase) || (!EntityUtils.targetDead && !entity.isEntityAlive()) || entity == Minecraft.player || (!EntityUtils.targetInvisible && entity.isInvisible())) {
            return false;
        }
        if (!EntityUtils.targetPlayer || !(entity instanceof EntityPlayer)) {
            return (EntityUtils.targetMobs && isMob(entity)) || (EntityUtils.targetAnimals && isAnimal(entity));
        }
        final EntityPlayer entityPlayer = (EntityPlayer) entity;
        if (!canAttackCheck) {
            return true;
        }
        if (entityPlayer.isSpectator()) {
            return false;
        }
        return canAttackCheck;
    }
}