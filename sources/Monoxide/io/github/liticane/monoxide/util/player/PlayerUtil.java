package io.github.liticane.monoxide.util.player;

import io.github.liticane.monoxide.util.interfaces.ClientInformationAccess;
import io.github.liticane.monoxide.util.interfaces.Methods;
import io.github.liticane.monoxide.util.world.WorldUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class PlayerUtil implements Methods {

    public static boolean canBuildForward() {
        final float yaw = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw);
        return (yaw > 77.5 && yaw < 102.5)
                || (yaw > 167.5 || yaw < -167.0f)
                || (yaw < -77.5 && yaw > -102.5)
                || (yaw > -12.5 && yaw < 12.5);
    }

    public static void addChatMessgae(final Object message, boolean prefix) {
        if (mc.thePlayer != null) {
            final String msg = String.format(message.toString());
            mc.thePlayer.addChatMessage(new ChatComponentText(prefix ? ClientInformationAccess.PREFIX + ": " + msg : msg));
        }
    }

    public static int getLeatherArmorColor(EntityPlayer player) {
        int armorColor = -1;
        for (ItemStack itemStack : player.inventory.armorInventory) {
            if (itemStack != null && itemStack.getItem() instanceof ItemArmor) {
                ItemArmor armor = (ItemArmor) itemStack.getItem();
                if (armor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
                    // The color of leather armor is stored in the item's display tag
                    if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("display", 10)) {
                        armorColor = itemStack.getTagCompound().getCompoundTag("display").getInteger("color");
                        break;
                    }
                }
            }
        }
        return armorColor;
    }


    public static int getIndexOfItem() {
        final InventoryPlayer inventoryPlayer = mc.thePlayer.inventory;
        return inventoryPlayer.currentItem;
    }

    public static ItemStack getItemStack() {
        return (mc.thePlayer == null || mc.thePlayer.inventoryContainer == null ? null : mc.thePlayer.inventoryContainer.getSlot(getIndexOfItem() + 36).getStack());
    }

    public static EnumFacing getEnumFacing(final Vec3 position) {
        for (int x2 = -1; x2 <= 1; x2 += 2) {
            if (!(WorldUtil.getBlock(position.xCoord + x2, position.yCoord, position.zCoord) instanceof BlockAir)) {
                if (x2 > 0) {
                    return EnumFacing.WEST;
                } else {
                    return EnumFacing.EAST;
                }
            }
        }

        for (int y2 = -1; y2 <= 1; y2 += 2) {
            if (!(WorldUtil.getBlock(position.xCoord, position.yCoord + y2, position.zCoord) instanceof BlockAir)) {
                if (y2 < 0) {
                    return EnumFacing.UP;
                }
            }
        }

        for (int z2 = -1; z2 <= 1; z2 += 2) {
            if (!(WorldUtil.getBlock(position.xCoord, position.yCoord, position.zCoord + z2) instanceof BlockAir)) {
                if (z2 < 0) {
                    return EnumFacing.SOUTH;
                } else {
                    return EnumFacing.NORTH;
                }
            }
        }

        return null;
    }

}