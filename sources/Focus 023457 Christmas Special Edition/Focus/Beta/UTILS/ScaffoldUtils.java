package Focus.Beta.UTILS;

import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.util.Arrays;
import java.util.List;

public class ScaffoldUtils {
    private static List<Block> invalidBlocks = Arrays.asList(Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.snow_layer, Blocks.chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.crafting_table, Blocks.furnace, Blocks.stone_slab, Blocks.wooden_slab, Blocks.stone_slab2);


    public static boolean canIItemBePlaced(Item item)
    {
        if (item.getIdFromItem(item) == 116)
            return false;
        if (item.getIdFromItem(item) == 30)
            return false;
        if (item.getIdFromItem(item) == 31)
            return false;
        if (item.getIdFromItem(item) == 175)
            return false;
        if (item.getIdFromItem(item) == 28)
            return false;
        if (item.getIdFromItem(item) == 27)
            return false;
        if (item.getIdFromItem(item) == 66)
            return false;
        if (item.getIdFromItem(item) == 157)
            return false;
        if (item.getIdFromItem(item) == 31)
            return false;
        if (item.getIdFromItem(item) == 6)
            return false;
        if (item.getIdFromItem(item) == 31)
            return false;
        if (item.getIdFromItem(item) == 32)
            return false;
        if (item.getIdFromItem(item) == 140)
            return false;
        if (item.getIdFromItem(item) == 390)
            return false;
        if (item.getIdFromItem(item) == 37)
            return false;
        if (item.getIdFromItem(item) == 38)
            return false;
        if (item.getIdFromItem(item) == 39)
            return false;
        if (item.getIdFromItem(item) == 40)
            return false;
        if (item.getIdFromItem(item) == 69)
            return false;
        if (item.getIdFromItem(item) == 50)
            return false;
        if (item.getIdFromItem(item) == 75)
            return false;
        if (item.getIdFromItem(item) == 76)
            return false;
        if (item.getIdFromItem(item) == 54)
            return false;
        if (item.getIdFromItem(item) == 130)
            return false;
        if (item.getIdFromItem(item) == 146)
            return false;
        if (item.getIdFromItem(item) == 342)
            return false;
        if (item.getIdFromItem(item) == 12)
            return false;
        if (item.getIdFromItem(item) == 77)
            return false;
        if (item.getIdFromItem(item) == 143)
            return false;
        if (item.getIdFromItem(item) == 46)
            return false;
        return true;
    }





    public static int getBlockSlot() {
        for (int i = 36; i < 45; ++i) {
            if (Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack() != null && Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemBlock && !invalidBlocks.contains(((ItemBlock) Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack().getItem()).getBlock())) {
                return i - 36;
            }
        }
        return -1;
    }

    public static boolean canItemBePlaced(ItemStack item)
    {
        if (item.getItem().getIdFromItem(item.getItem()) == 116)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 30)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 31)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 175)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 28)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 27)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 66)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 157)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 31)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 6)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 31)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 32)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 140)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 390)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 37)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 38)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 39)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 40)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 69)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 50)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 75)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 76)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 54)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 130)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 146)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 342)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 12)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 77)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 143)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 46)
            return false;
        if (item.getItem().getIdFromItem(item.getItem()) == 145)
            return false;

        return true;
    }


    // TODO: Get block fron inventory
    public static int findBlockSlot() {
        int slot = -1;
        int highestStack = -1;
        for (int i = 36; i < 45; i++) {
            ItemStack stack = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null &&
                    stack.getItem() instanceof ItemBlock &&
                    canIItemBePlaced(stack.getItem()) && stack.stackSize > 0) {
                if (stack.stackSize > highestStack) {
                    slot = i;
                    highestStack = stack.stackSize;
                }
            }
        }

        return slot;
    }


    public static float[] getRotations(final BlockPos block, final EnumFacing face) {
        final double x = block.getX() + 0.5 - Minecraft.getMinecraft().thePlayer.posX + face.getFrontOffsetX() / 2.0;
        final double z = block.getZ() + 0.5 - Minecraft.getMinecraft().thePlayer.posZ + face.getFrontOffsetZ() / 2.0;
        double y = block.getY() + 0.5;
        final double d1 = Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight() - y;
        final double d2 = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float) (Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float) (Math.atan2(d1, d2) * 180.0 / 3.141592653589793);
        if (yaw < 0.0f) {
            yaw += 360.0f;
        }
        return new float[]{yaw, pitch};
    }

    public static int grabBlockSlot() {
        int slot = -1;
        int highestStack = -1;
        boolean didGetHotbar = false;
        for (int i = 0; i < 36; ++i) {
            final ItemStack itemStack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i];
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && canItemBePlaced(itemStack) && itemStack.stackSize > 0) {
                if (Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i].stackSize > highestStack && i < 9) {
                    highestStack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i].stackSize;
                    slot = i;
                    if (slot == getLastHotbarSlot()) {
                        didGetHotbar = true;
                    }
                }
                if (i > 8 && !didGetHotbar) {
                    int hotbarNum = getFreeHotbarSlot();
                    if (hotbarNum != -1 && Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i].stackSize > highestStack) {
                        highestStack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i].stackSize;
                        slot = i;
                    }
                }
            }
        }
        if (slot > 8) {
            int hotbarNum = getFreeHotbarSlot();
            if (hotbarNum != -1) {
                Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, Minecraft.getMinecraft().thePlayer);
            } else {
                return -1;
            }
        }
        return slot;
    }

    public static int getLastHotbarSlot() {
        int hotbarNum = -1;
        for (int k = 0; k < 9; k++) {
            final ItemStack itemStack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[k];
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && canItemBePlaced(itemStack) && itemStack.stackSize > 1) {
                hotbarNum = k;
                continue;
            }
        }
        return hotbarNum;
    }

    public static int getFreeHotbarSlot() {
        int hotbarNum = -1;
        for (int k = 0; k < 9; k++) {
            if (Minecraft.getMinecraft().thePlayer.inventory.mainInventory[k] == null) {
                hotbarNum = k;
                continue;
            } else {
                hotbarNum = 7;
            }
        }
        return hotbarNum;
    }

    private Vec3 grabPosition(final BlockPos position, final EnumFacing facing) {
        final Vec3 offset = new Vec3(facing.getDirectionVec().getX() / 2.0, (facing.getDirectionVec().getY() / 2.0) - 5, facing.getDirectionVec().getZ() / 2.0);
        final Vec3 point = new Vec3(position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5);
        return point.add(offset);
    }

    public static float clampRotation() {
        float rotationYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        float n = 1.0f;
        if (Minecraft.getMinecraft().thePlayer.movementInput.moveForward < 0.0f) {
            rotationYaw += 180.0f;
            n = -0.5f;
        }
        else if (Minecraft.getMinecraft().thePlayer.movementInput.moveForward > 0.0f) {
            n = 0.5f;
        }
        if (Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe > 0.0f) {
            rotationYaw -= 90.0f * n;
        }
        if (Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe < 0.0f) {
            rotationYaw += 90.0f * n;
        }
        return rotationYaw * 0.017453292f;
    }

}