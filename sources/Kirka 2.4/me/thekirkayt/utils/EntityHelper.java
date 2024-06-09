/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils;

import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import me.thekirkayt.client.friend.FriendManager;
import me.thekirkayt.utils.Location;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;

public final class EntityHelper {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static int getBestWeapon(Entity target) {
        int originalSlot = Minecraft.thePlayer.inventory.currentItem;
        int weaponSlot = -1;
        float weaponDamage = 1.0f;
        for (int slot = 0; slot < 9; slot = (int)((byte)(slot + 1))) {
            Minecraft.thePlayer.inventory.currentItem = slot;
            ItemStack itemStack = Minecraft.thePlayer.getHeldItem();
            if (itemStack == null) continue;
            float damage = EntityHelper.getItemDamage(itemStack);
            if (!((damage += EnchantmentHelper.func_152377_a(itemStack, EnumCreatureAttribute.UNDEFINED)) > weaponDamage)) continue;
            weaponDamage = damage;
            weaponSlot = slot;
        }
        if (weaponSlot != -1) {
            return weaponSlot;
        }
        return originalSlot;
    }

    public static double[] teleportToPosition(double[] startPosition, double[] endPosition, double setOffset, double slack, boolean extendOffset, boolean onGround) {
        boolean wasSneaking = false;
        if (Minecraft.thePlayer.isSneaking()) {
            wasSneaking = true;
        }
        double startX = startPosition[0];
        double startY = startPosition[1];
        double startZ = startPosition[2];
        double endX = endPosition[0];
        double endY = endPosition[1];
        double endZ = endPosition[2];
        double distance = Math.abs(startX - startY) + Math.abs(startY - endY) + Math.abs(startZ - endZ);
        int count = 0;
        while (distance > slack) {
            distance = Math.abs(startX - endX) + Math.abs(startY - endY) + Math.abs(startZ - endZ);
            if (count > 120) break;
            double offset = extendOffset && (count & 1) == 0 ? setOffset + 0.15 : setOffset;
            double diffX = startX - endX;
            double diffY = startY - endY;
            double diffZ = startZ - endZ;
            if (diffX < 0.0) {
                startX = Math.abs(diffX) > offset ? (startX += offset) : (startX += Math.abs(diffX));
            }
            if (diffX > 0.0) {
                startX = Math.abs(diffX) > offset ? (startX -= offset) : (startX -= Math.abs(diffX));
            }
            if (diffY < 0.0) {
                startY = Math.abs(diffY) > offset ? (startY += offset) : (startY += Math.abs(diffY));
            }
            if (diffY > 0.0) {
                startY = Math.abs(diffY) > offset ? (startY -= offset) : (startY -= Math.abs(diffY));
            }
            if (diffZ < 0.0) {
                startZ = Math.abs(diffZ) > offset ? (startZ += offset) : (startZ += Math.abs(diffZ));
            }
            if (diffZ > 0.0) {
                startZ = Math.abs(diffZ) > offset ? (startZ -= offset) : (startZ -= Math.abs(diffZ));
            }
            if (wasSneaking) {
                Minecraft.getNetHandler().addToSendQueue(new C0BPacketEntityAction(Minecraft.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
            }
            Minecraft.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(startX, startY, startZ, onGround));
            ++count;
        }
        if (wasSneaking) {
            Minecraft.getNetHandler().addToSendQueue(new C0BPacketEntityAction(Minecraft.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
        }
        return new double[]{startX, startY, startZ};
    }

    public static Entity findEntity(double range) {
        double r = range;
        Entity entity = null;
        for (Object ent : Minecraft.theWorld.loadedEntityList) {
            Entity e = (Entity)ent;
            if (!((double)Minecraft.thePlayer.getDistanceToEntity(e) <= r) || !(e instanceof EntityAnimal) && !(e instanceof EntityPlayer) && !(e instanceof EntityMob) || e == Minecraft.thePlayer || !e.isEntityAlive() || FriendManager.isFriend(e.getName()) || !EntityHelper.isValidTarget(e)) continue;
            r = Minecraft.thePlayer.getDistanceToEntity(e);
            entity = e;
        }
        return entity;
    }

    public static boolean isValidTarget(Entity entity) {
        if (Minecraft.thePlayer == null) {
            return false;
        }
        if (entity == null) {
            return false;
        }
        if (Minecraft.thePlayer == entity) {
            return false;
        }
        if (entity instanceof EntityPlayer && !((EntityPlayer)entity).isUsingItem()) {
            if (entity.onGround) {
                if (entity.isSprinting()) {
                    return true;
                }
            } else if (entity.hurtResistantTime == 0) {
                return false;
            }
        }
        if (Minecraft.thePlayer == null) {
            return false;
        }
        if (Minecraft.thePlayer == entity) {
            return false;
        }
        if (entity.isInvisible()) {
            return false;
        }
        if (entity.ticksExisted < 50) {
            return false;
        }
        if (!entity.isInvisible() && entity.onGround) {
            return true;
        }
        if (entity.isInvisible()) {
            return false;
        }
        return entity instanceof EntityPlayer || entity instanceof EntityAnimal || entity instanceof EntityMob;
    }

    public static float[] getFacingRotations(int x, int y, int z, EnumFacing facing) {
        EntitySnowball temp = new EntitySnowball(Minecraft.theWorld);
        temp.posX = (double)x + 0.5;
        temp.posY = (double)y + 0.5;
        temp.posZ = (double)z + 0.5;
        temp.posX += (double)facing.getDirectionVec().getX() * 0.25;
        temp.posY += (double)facing.getDirectionVec().getY() * 0.25;
        temp.posZ += (double)facing.getDirectionVec().getZ() * 0.25;
        return EntityHelper.getAngles(temp);
    }

    public static Location predictEntityLocation(Entity e, double milliseconds) {
        if (e != null) {
            if (e.posX == e.lastTickPosX && e.posY == e.lastTickPosY && e.posZ == e.lastTickPosZ) {
                return new Location(e.posX, e.posY, e.posZ);
            }
            double ticks = milliseconds / 1000.0;
            return EntityHelper.interp(new Location(e.lastTickPosX, e.lastTickPosY, e.lastTickPosZ), new Location(e.posX + e.motionX, e.posY + e.motionY, e.posZ + e.motionZ), ticks *= 20.0);
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
        return new float[]{EntityHelper.getYawChangeToEntity(e) + Minecraft.thePlayer.rotationYaw, EntityHelper.getPitchChangeToEntity(e) + Minecraft.thePlayer.rotationPitch};
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

