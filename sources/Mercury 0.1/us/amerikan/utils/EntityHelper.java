/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.utils;

import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import us.amerikan.utils.Location;

public class EntityHelper {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static int getBestWeapon(Entity target) {
        int originalSlot = Minecraft.thePlayer.inventory.currentItem;
        int b2 = -1;
        float weaponDamage = 1.0f;
        for (int slot = 0; slot < 9; slot = (int)((byte)(slot + 1))) {
            Minecraft.thePlayer.inventory.currentItem = slot;
            ItemStack itemStack = Minecraft.thePlayer.getHeldItem();
            if (itemStack == null) continue;
            float damage = EntityHelper.getItemDamage(itemStack);
            if (!((damage += EnchantmentHelper.func_152377_a(itemStack, EnumCreatureAttribute.UNDEFINED)) > weaponDamage)) continue;
            weaponDamage = damage;
            b2 = slot;
        }
        if (b2 != -1) {
            return b2;
        }
        return originalSlot;
    }

    public static float[] getFacingRotations(int x2, int y2, int z2, EnumFacing facing) {
        EntitySnowball entitySnowball1 = new EntitySnowball(EntityHelper.mc.theWorld);
        entitySnowball1.posX = (double)x2 + 0.5;
        entitySnowball1.posY = (double)y2 + 0.5;
        entitySnowball1.posZ = (double)z2 + 0.5;
        EntitySnowball entitySnowball2 = entitySnowball1;
        entitySnowball2.posX += (double)facing.getDirectionVec().getX() * 0.25;
        EntitySnowball entitySnowball3 = entitySnowball1;
        entitySnowball3.posY += (double)facing.getDirectionVec().getY() * 0.25;
        EntitySnowball entitySnowball4 = entitySnowball1;
        entitySnowball4.posZ += (double)facing.getDirectionVec().getZ() * 0.25;
        return EntityHelper.getAngles(entitySnowball1);
    }

    public static Location predictEntityLocation(Entity e2, double milliseconds) {
        if (e2 == null) {
            return null;
        }
        if (e2.posX == e2.lastTickPosX && e2.posY == e2.lastTickPosY && e2.posZ == e2.lastTickPosZ) {
            return new Location(e2.posX, e2.posY, e2.posZ);
        }
        double ticks = milliseconds / 1000.0;
        return EntityHelper.interp(new Location(e2.lastTickPosX, e2.lastTickPosY, e2.lastTickPosZ), new Location(e2.posX + e2.motionX, e2.posY + e2.motionY, e2.posZ + e2.motionZ), ticks *= 20.0);
    }

    public static Location interp(Location from, Location to2, double pct) {
        double x2 = Location.x + (Location.x - Location.x) * pct;
        double y2 = Location.y + (Location.y - Location.y) * pct;
        double z2 = Location.z + (Location.z - Location.z) * pct;
        return new Location(x2, y2, z2);
    }

    public static float getYawChangeToEntity(Entity entity) {
        double deltaX = entity.posX - Minecraft.thePlayer.posX;
        double deltaZ = entity.posZ - Minecraft.thePlayer.posZ;
        double yawToEntity = 0.0;
        if (deltaZ < 0.0 && deltaX < 0.0) {
            yawToEntity = 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        } else if (deltaZ < 0.0 && deltaX > 0.0) {
            double d2 = -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        } else {
            double d3 = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }
        return MathHelper.wrapAngleTo180_float(-(Minecraft.thePlayer.rotationYaw - (float)yawToEntity));
    }

    public static float getPitchChangeToEntity(Entity entity) {
        double deltaX = entity.posX - Minecraft.thePlayer.posX;
        double deltaZ = entity.posZ - Minecraft.thePlayer.posZ;
        double deltaY = entity.posY - 1.6 + (double)entity.getEyeHeight() - Minecraft.thePlayer.posY;
        double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
        double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return -MathHelper.wrapAngleTo180_float(Minecraft.thePlayer.rotationPitch - (float)pitchToEntity);
    }

    public static float[] getAngles(Entity e2) {
        return new float[]{EntityHelper.getYawChangeToEntity(e2) + Minecraft.thePlayer.rotationYaw, EntityHelper.getPitchChangeToEntity(e2) + Minecraft.thePlayer.rotationPitch};
    }

    public static float[] getEntityRotations(EntityPlayer player, Entity target) {
        double posX = target.posX - player.posX;
        double posY = target.posY + (double)target.getEyeHeight() - player.posY + (double)player.getEyeHeight();
        double posZ = target.posZ - player.posZ;
        double var14 = MathHelper.sqrt_double(posX * posX + posZ * posZ);
        float yaw = (float)(Math.atan2(posZ, posX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(-(Math.atan2(posY, var14) * 180.0 / 3.141592653589793));
        return new float[]{yaw, pitch};
    }

    private static float getItemDamage(ItemStack itemStack) {
        Iterator iterator;
        Multimap multimap = itemStack.getAttributeModifiers();
        if (!multimap.isEmpty() && (iterator = multimap.entries().iterator()).hasNext()) {
            Map.Entry entry = iterator.next();
            AttributeModifier attributeModifier = (AttributeModifier)entry.getValue();
            double damage = attributeModifier.getOperation() != 1 && attributeModifier.getOperation() != 2 ? attributeModifier.getAmount() : attributeModifier.getAmount() * 100.0;
            if (attributeModifier.getAmount() > 1.0) {
                return 1.0f + (float)damage;
            }
            return 1.0f;
        }
        return 1.0f;
    }

    private static double directionCheck(double sourceX, double sourceY, double sourceZ, double dirX, double dirY, double dirZ, double targetX, double targetY, double targetZ, double targetWidth, double targetHeight, double precision) {
        double dirLength = Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        if (dirLength == 0.0) {
            dirLength = 1.0;
        }
        double dX = targetX - sourceX;
        double dY = targetY - sourceY;
        double dZ = targetZ - sourceZ;
        double targetDist = Math.sqrt(dX * dX + dY * dY + dZ * dZ);
        double xPrediction = targetDist * dirX / dirLength;
        double yPrediction = targetDist * dirY / dirLength;
        double zPrediction = targetDist * dirZ / dirLength;
        double off = 0.0;
        off += Math.max(Math.abs(dX - xPrediction) - targetWidth / 2.0 + precision, 0.0);
        off += Math.max(Math.abs(dZ - zPrediction) - targetWidth / 2.0 + precision, 0.0);
        if ((off += Math.max(Math.abs(dY - yPrediction) - targetHeight / 2.0 + precision, 0.0)) > 1.0) {
            off = Math.sqrt(off);
        }
        return off;
    }

    public static double getDirectionCheckVal(Entity e2, Vec3 lookVec) {
        return EntityHelper.directionCheck(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight(), Minecraft.thePlayer.posZ, lookVec.xCoord, lookVec.yCoord, lookVec.zCoord, e2.posX, e2.posY + (double)e2.height / 2.0, e2.posZ, e2.width, e2.height, 5.0);
    }

    public static float[] getEntityRotations2(EntityPlayer player, Entity target) {
        double posX = target.posX - player.posX;
        double posY = target.posY + (double)target.getEyeHeight() - player.posY + (double)player.getEyeHeight() + 0.5;
        double posZ = target.posZ - player.posZ;
        double var14 = MathHelper.sqrt_double(posX * posX + posZ * posZ);
        float yaw = (float)(Math.atan2(posZ, posX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(-(Math.atan2(posY, var14) * 180.0 / 3.141592653589793));
        return new float[]{yaw, pitch};
    }
}

