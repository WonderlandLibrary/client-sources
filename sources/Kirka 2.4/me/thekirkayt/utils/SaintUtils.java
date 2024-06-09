/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils;

import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import me.thekirkayt.utils.Location;
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
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;

public final class SaintUtils {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static int getBestWeapon(Entity target) {
        int originalSlot = Minecraft.thePlayer.inventory.currentItem;
        int weaponSlot = -1;
        float weaponDamage = 1.0f;
        for (int slot = 0; slot < 9; slot = (int)((byte)(slot + 1))) {
            Minecraft.thePlayer.inventory.currentItem = slot;
            ItemStack itemStack = Minecraft.thePlayer.getHeldItem();
            if (itemStack == null) continue;
            float damage = SaintUtils.getItemDamage(itemStack);
            if (!((damage += EnchantmentHelper.func_152377_a(itemStack, EnumCreatureAttribute.UNDEFINED)) > weaponDamage)) continue;
            weaponDamage = damage;
            weaponSlot = slot;
        }
        if (weaponSlot != -1) {
            return weaponSlot;
        }
        return originalSlot;
    }

    public static float[] getFacingRotations(int x, int y, int z, EnumFacing facing) {
        EntitySnowball temp = new EntitySnowball(Minecraft.theWorld);
        temp.posX = (double)x + 0.5;
        temp.posY = (double)y + 0.5;
        temp.posZ = (double)z + 0.5;
        temp.posX += (double)facing.getDirectionVec().getX() * 0.25;
        temp.posY += (double)facing.getDirectionVec().getY() * 0.25;
        temp.posZ += (double)facing.getDirectionVec().getZ() * 0.25;
        return SaintUtils.getAngles(temp);
    }

    public static Location predictEntityLocation(Entity e, double milliseconds) {
        if (e != null) {
            if (e.posX == e.lastTickPosX && e.posY == e.lastTickPosY && e.posZ == e.lastTickPosZ) {
                return new Location(e.posX, e.posY, e.posZ);
            }
            double ticks = milliseconds / 1000.0;
            return SaintUtils.interp(new Location(e.lastTickPosX, e.lastTickPosY, e.lastTickPosZ), new Location(e.posX + e.motionX, e.posY + e.motionY, e.posZ + e.motionZ), ticks *= 20.0);
        }
        return null;
    }

    public static Location interp(Location from, Location to, double pct) {
        double x = from.x + (to.x - from.x) * pct;
        double y = from.y + (to.y - from.y) * pct;
        double z = from.z + (to.z - from.z) * pct;
        return new Location(x, y, z);
    }

    public static float getYawChangeToEntity(Entity entity) {
        double deltaX = entity.posX - Minecraft.thePlayer.posX;
        double deltaZ = entity.posZ - Minecraft.thePlayer.posZ;
        double yawToEntity = deltaZ < 0.0 && deltaX < 0.0 ? 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)) : (deltaZ < 0.0 && deltaX > 0.0 ? -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)) : Math.toDegrees(-Math.atan(deltaX / deltaZ)));
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

    public static float[] getAngles(Entity e) {
        return new float[]{SaintUtils.getYawChangeToEntity(e) + Minecraft.thePlayer.rotationYaw, SaintUtils.getPitchChangeToEntity(e) + Minecraft.thePlayer.rotationPitch};
    }

    public static float[] getEntityRotations(EntityPlayer player, Entity target) {
        double posX = target.posX - player.posX;
        double posY = target.posY + (double)target.getEyeHeight() - (player.posY + (double)player.getEyeHeight());
        double posZ = target.posZ - player.posZ;
        double var14 = MathHelper.sqrt_double(posX * posX + posZ * posZ);
        float yaw = (float)(Math.atan2(posZ, posX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(-(Math.atan2(posY, var14) * 180.0 / 3.141592653589793));
        return new float[]{yaw, pitch};
    }

    private float[] getBlockRotations(int x, int y, int z) {
        double var4 = (double)x - Minecraft.thePlayer.posX + 0.5;
        double var6 = (double)z - Minecraft.thePlayer.posZ + 0.5;
        double var8 = (double)y - (Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight() - 1.0);
        double var14 = MathHelper.sqrt_double(var4 * var4 + var6 * var6);
        float var12 = (float)(Math.atan2(var6, var4) * 180.0 / 3.141592653589793) - 90.0f;
        return new float[]{var12, (float)(-(Math.atan2(var8, var14) * 180.0 / 3.141592653589793))};
    }

    private static float getItemDamage(ItemStack itemStack) {
        Iterator iterator;
        Multimap multimap = itemStack.getAttributeModifiers();
        if (!multimap.isEmpty() && (iterator = multimap.entries().iterator()).hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            AttributeModifier attributeModifier = (AttributeModifier)entry.getValue();
            double damage = attributeModifier.getOperation() != 1 && attributeModifier.getOperation() != 2 ? attributeModifier.getAmount() : attributeModifier.getAmount() * 100.0;
            if (attributeModifier.getAmount() > 1.0) {
                return 1.0f + (float)damage;
            }
            return 1.0f;
        }
        return 1.0f;
    }
}

