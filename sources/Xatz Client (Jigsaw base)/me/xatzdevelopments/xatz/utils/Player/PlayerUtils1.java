package me.xatzdevelopments.xatz.utils.Player;

import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.block.Block;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;
import java.util.Iterator;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class PlayerUtils1
{
    public static void faceEntity(final Entity entity) {
        final double x = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
        final double z = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        final double y = entity.posY + entity.getEyeHeight() / 1.4 - Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight() / 1.4;
        final double helper = MathHelper.sqrt_double(x * x + z * z);
        float newYaw = (float)Math.toDegrees(-Math.atan(x / z));
        final float newPitch = (float)(-Math.toDegrees(Math.atan(y / helper)));
        if (z < 0.0 && x < 0.0) {
            newYaw = (float)(90.0 + Math.toDegrees(Math.atan(z / x)));
        }
        else if (z < 0.0 && x > 0.0) {
            newYaw = (float)(-90.0 + Math.toDegrees(Math.atan(z / x)));
        }
        Minecraft.getMinecraft().thePlayer.rotationYaw = newYaw;
        Minecraft.getMinecraft().thePlayer.rotationPitch = newPitch;
        Minecraft.getMinecraft().thePlayer.rotationYawHead = newPitch;
    }
    
    public static float[] getRotations(final Entity entity) {
        final double x = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
        final double z = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        final double y = entity.posY + entity.getEyeHeight() / 1.4 - Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight() / 1.4;
        final double helper = MathHelper.sqrt_double(x * x + z * z);
        float newYaw = (float)Math.toDegrees(-Math.atan(x / z));
        final float newPitch = (float)(-Math.toDegrees(Math.atan(y / helper)));
        if (z < 0.0 && x < 0.0) {
            newYaw = (float)(90.0 + Math.toDegrees(Math.atan(z / x)));
        }
        else if (z < 0.0 && x > 0.0) {
            newYaw = (float)(-90.0 + Math.toDegrees(Math.atan(z / x)));
        }
        return new float[] { newYaw, newPitch };
    }
    
    public static int getBestWeapon(final Entity target) {
        final int originalSlot = Minecraft.getMinecraft().thePlayer.inventory.currentItem;
        int weaponSlot = -1;
        float weaponDamage = 1.0f;
        for (byte slot = 0; slot < 9; ++slot) {
            Minecraft.getMinecraft().thePlayer.inventory.currentItem = slot;
            final ItemStack itemStack = Minecraft.getMinecraft().thePlayer.getHeldItem();
            if (itemStack != null) {
                float damage = getItemDamage(itemStack);
                damage += EnchantmentHelper.func_152377_a(Minecraft.getMinecraft().thePlayer.getHeldItem(), ((EntityLivingBase)target).getCreatureAttribute());
                if (damage > weaponDamage) {
                    weaponDamage = damage;
                    weaponSlot = slot;
                }
            }
        }
        if (weaponSlot != -1) {
            return weaponSlot;
        }
        return originalSlot;
    }
    
    private static float getItemDamage(final ItemStack itemStack) {
        final Multimap multimap = itemStack.getAttributeModifiers();
        if (!multimap.isEmpty()) {
            final Iterator iterator = multimap.entries().iterator();
            if (iterator.hasNext()) {
                final Map.Entry entry = (Entry) iterator.next();
                final AttributeModifier attributeModifier = (AttributeModifier) entry.getValue();
                double damage;
                if (attributeModifier.getOperation() != 1 && attributeModifier.getOperation() != 2) {
                    damage = attributeModifier.getAmount();
                }
                else {
                    damage = attributeModifier.getAmount() * 100.0;
                }
                if (attributeModifier.getAmount() > 1.0) {
                    return 1.0f + (float)damage;
                }
                return 1.0f;
            }
        }
        return 1.0f;
    }
    
    public static void faceBlock(final BlockPos pos) {
        final double diffX = pos.getX() - Minecraft.getMinecraft().thePlayer.posX;
        final double diffZ = pos.getZ() - Minecraft.getMinecraft().thePlayer.posZ;
        final double diffY = pos.getY() - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
        final EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
        thePlayer.rotationPitch += MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch);
        final EntityPlayerSP thePlayer2 = Minecraft.getMinecraft().thePlayer;
        thePlayer2.rotationYaw += MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw);
    }
    
    public static void blinkToPos(final double[] startPos, final BlockPos endPos, final double slack) {
        double curX = startPos[0];
        double curY = startPos[1];
        double curZ = startPos[2];
        final double endX = endPos.getX() + 0.5;
        final double endY = endPos.getY() + 1.0;
        final double endZ = endPos.getZ() + 0.5;
        double distance = Math.abs(curX - endX) + Math.abs(curY - endY) + Math.abs(curZ - endZ);
        int count = 0;
        while (distance > slack) {
            distance = Math.abs(curX - endX) + Math.abs(curY - endY) + Math.abs(curZ - endZ);
            if (count > 120) {
                break;
            }
            final double diffX = curX - endX;
            final double diffY = curY - endY;
            final double diffZ = curZ - endZ;
            final double offset = ((count & 0x1) == 0x0) ? 0.4 : 0.25;
            if (diffX < 0.0) {
                if (Math.abs(diffX) > offset) {
                    curX += offset;
                }
                else {
                    curX += Math.abs(diffX);
                }
            }
            if (diffX > 0.0) {
                if (Math.abs(diffX) > offset) {
                    curX -= offset;
                }
                else {
                    curX -= Math.abs(diffX);
                }
            }
            if (diffY < 0.0) {
                if (Math.abs(diffY) > 0.25) {
                    curY += 0.25;
                }
                else {
                    curY += Math.abs(diffY);
                }
            }
            if (diffY > 0.0) {
                if (Math.abs(diffY) > 0.25) {
                    curY -= 0.25;
                }
                else {
                    curY -= Math.abs(diffY);
                }
            }
            if (diffZ < 0.0) {
                if (Math.abs(diffZ) > offset) {
                    curZ += offset;
                }
                else {
                    curZ += Math.abs(diffZ);
                }
            }
            if (diffZ > 0.0) {
                if (Math.abs(diffZ) > offset) {
                    curZ -= offset;
                }
                else {
                    curZ -= Math.abs(diffZ);
                }
            }
            Minecraft.getMinecraft().getNetHandler().addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(curX, curY, curZ, true));
            ++count;
        }
    }
    
    public static double[] getPos() {
        return new double[] { Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ };
    }
    
    public static Block getBlock(final BlockPos pos) {
        return Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
    }
    
    public static Block getBlock(final int x, final int y, final int z) {
        return Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
    }
    
    public void addMessage(final String s) {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentText("§8[§fONE§c2§8]§f: " + s));
    }
}
