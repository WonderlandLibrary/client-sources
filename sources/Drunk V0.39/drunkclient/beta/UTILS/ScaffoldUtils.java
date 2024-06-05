/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.UTILS;

import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Vec3;

public class ScaffoldUtils {
    private static List<Block> invalidBlocks = Arrays.asList(Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.snow_layer, Blocks.chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.crafting_table, Blocks.furnace, Blocks.stone_slab, Blocks.wooden_slab, Blocks.stone_slab2);

    public static boolean canIItemBePlaced(Item item) {
        if (Item.getIdFromItem(item) == 116) {
            return false;
        }
        if (Item.getIdFromItem(item) == 30) {
            return false;
        }
        if (Item.getIdFromItem(item) == 31) {
            return false;
        }
        if (Item.getIdFromItem(item) == 175) {
            return false;
        }
        if (Item.getIdFromItem(item) == 28) {
            return false;
        }
        if (Item.getIdFromItem(item) == 27) {
            return false;
        }
        if (Item.getIdFromItem(item) == 66) {
            return false;
        }
        if (Item.getIdFromItem(item) == 157) {
            return false;
        }
        if (Item.getIdFromItem(item) == 31) {
            return false;
        }
        if (Item.getIdFromItem(item) == 6) {
            return false;
        }
        if (Item.getIdFromItem(item) == 31) {
            return false;
        }
        if (Item.getIdFromItem(item) == 32) {
            return false;
        }
        if (Item.getIdFromItem(item) == 140) {
            return false;
        }
        if (Item.getIdFromItem(item) == 390) {
            return false;
        }
        if (Item.getIdFromItem(item) == 37) {
            return false;
        }
        if (Item.getIdFromItem(item) == 38) {
            return false;
        }
        if (Item.getIdFromItem(item) == 39) {
            return false;
        }
        if (Item.getIdFromItem(item) == 40) {
            return false;
        }
        if (Item.getIdFromItem(item) == 69) {
            return false;
        }
        if (Item.getIdFromItem(item) == 50) {
            return false;
        }
        if (Item.getIdFromItem(item) == 75) {
            return false;
        }
        if (Item.getIdFromItem(item) == 76) {
            return false;
        }
        if (Item.getIdFromItem(item) == 54) {
            return false;
        }
        if (Item.getIdFromItem(item) == 130) {
            return false;
        }
        if (Item.getIdFromItem(item) == 146) {
            return false;
        }
        if (Item.getIdFromItem(item) == 342) {
            return false;
        }
        if (Item.getIdFromItem(item) == 12) {
            return false;
        }
        if (Item.getIdFromItem(item) == 77) {
            return false;
        }
        if (Item.getIdFromItem(item) == 143) {
            return false;
        }
        if (Item.getIdFromItem(item) != 46) return true;
        return false;
    }

    public static int getBlockSlot() {
        int i = 36;
        while (i < 45) {
            Minecraft.getMinecraft();
            if (Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack() != null) {
                Minecraft.getMinecraft();
                if (Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemBlock) {
                    Minecraft.getMinecraft();
                    if (!invalidBlocks.contains(((ItemBlock)Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack().getItem()).getBlock())) {
                        return i - 36;
                    }
                }
            }
            ++i;
        }
        return -1;
    }

    public static boolean canItemBePlaced(ItemStack item) {
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 116) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 30) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 31) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 175) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 28) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 27) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 66) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 157) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 31) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 6) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 31) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 32) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 140) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 390) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 37) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 38) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 39) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 40) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 69) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 50) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 75) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 76) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 54) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 130) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 146) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 342) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 12) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 77) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 143) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 46) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) != 145) return true;
        return false;
    }

    public static int findBlockSlot() {
        int slot = -1;
        int highestStack = -1;
        int i = 36;
        while (i < 45) {
            Minecraft.getMinecraft();
            ItemStack stack = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() instanceof ItemBlock && ScaffoldUtils.canIItemBePlaced(stack.getItem()) && stack.stackSize > 0 && stack.stackSize > highestStack) {
                slot = i;
                highestStack = stack.stackSize;
            }
            ++i;
        }
        return slot;
    }

    public static float[] getRotations(BlockPos block, EnumFacing face) {
        double d = (double)block.getX() + 0.5;
        Minecraft.getMinecraft();
        double x = d - Minecraft.thePlayer.posX + (double)face.getFrontOffsetX() / 2.0;
        double d2 = (double)block.getZ() + 0.5;
        Minecraft.getMinecraft();
        double z = d2 - Minecraft.thePlayer.posZ + (double)face.getFrontOffsetZ() / 2.0;
        double y = (double)block.getY() + 0.5;
        Minecraft.getMinecraft();
        double d3 = Minecraft.thePlayer.posY;
        Minecraft.getMinecraft();
        double d1 = d3 + (double)Minecraft.thePlayer.getEyeHeight() - y;
        double d22 = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float)(Math.atan2(z, x) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(Math.atan2(d1, d22) * 180.0 / Math.PI);
        if (!(yaw < 0.0f)) return new float[]{yaw, pitch};
        yaw += 360.0f;
        return new float[]{yaw, pitch};
    }

    public static int grabBlockSlot() {
        int slot = -1;
        int highestStack = -1;
        boolean didGetHotbar = false;
        int i = 0;
        while (true) {
            if (i >= 36) {
                if (slot <= 8) return slot;
                int hotbarNum = ScaffoldUtils.getFreeHotbarSlot();
                if (hotbarNum == -1) return -1;
                PlayerControllerMP playerControllerMP = Minecraft.getMinecraft().playerController;
                Minecraft.getMinecraft();
                int n = Minecraft.thePlayer.inventoryContainer.windowId;
                Minecraft.getMinecraft();
                playerControllerMP.windowClick(n, slot, hotbarNum, 2, Minecraft.thePlayer);
                return slot;
            }
            Minecraft.getMinecraft();
            ItemStack itemStack = Minecraft.thePlayer.inventory.mainInventory[i];
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && ScaffoldUtils.canItemBePlaced(itemStack) && itemStack.stackSize > 0) {
                int hotbarNum;
                Minecraft.getMinecraft();
                if (Minecraft.thePlayer.inventory.mainInventory[i].stackSize > highestStack && i < 9) {
                    Minecraft.getMinecraft();
                    highestStack = Minecraft.thePlayer.inventory.mainInventory[i].stackSize;
                    slot = i;
                    if (slot == ScaffoldUtils.getLastHotbarSlot()) {
                        didGetHotbar = true;
                    }
                }
                if (i > 8 && !didGetHotbar && (hotbarNum = ScaffoldUtils.getFreeHotbarSlot()) != -1) {
                    Minecraft.getMinecraft();
                    if (Minecraft.thePlayer.inventory.mainInventory[i].stackSize > highestStack) {
                        Minecraft.getMinecraft();
                        highestStack = Minecraft.thePlayer.inventory.mainInventory[i].stackSize;
                        slot = i;
                    }
                }
            }
            ++i;
        }
    }

    public static int getLastHotbarSlot() {
        int hotbarNum = -1;
        int k = 0;
        while (k < 9) {
            Minecraft.getMinecraft();
            ItemStack itemStack = Minecraft.thePlayer.inventory.mainInventory[k];
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && ScaffoldUtils.canItemBePlaced(itemStack) && itemStack.stackSize > 1) {
                hotbarNum = k;
            }
            ++k;
        }
        return hotbarNum;
    }

    public static int getFreeHotbarSlot() {
        int hotbarNum = -1;
        int k = 0;
        while (k < 9) {
            Minecraft.getMinecraft();
            hotbarNum = Minecraft.thePlayer.inventory.mainInventory[k] == null ? k : 7;
            ++k;
        }
        return hotbarNum;
    }

    private Vec3 grabPosition(BlockPos position, EnumFacing facing) {
        Vec3 offset = new Vec3((double)facing.getDirectionVec().getX() / 2.0, (double)facing.getDirectionVec().getY() / 2.0 - 5.0, (double)facing.getDirectionVec().getZ() / 2.0);
        Vec3 point = new Vec3((double)position.getX() + 0.5, (double)position.getY() + 0.5, (double)position.getZ() + 0.5);
        return point.add(offset);
    }

    public static float clampRotation() {
        Minecraft.getMinecraft();
        float rotationYaw = Minecraft.thePlayer.rotationYaw;
        float n = 1.0f;
        Minecraft.getMinecraft();
        MovementInput cfr_ignored_0 = Minecraft.thePlayer.movementInput;
        if (MovementInput.moveForward < 0.0f) {
            rotationYaw += 180.0f;
            n = -0.5f;
        } else {
            Minecraft.getMinecraft();
            MovementInput cfr_ignored_1 = Minecraft.thePlayer.movementInput;
            if (MovementInput.moveForward > 0.0f) {
                n = 0.5f;
            }
        }
        Minecraft.getMinecraft();
        MovementInput cfr_ignored_2 = Minecraft.thePlayer.movementInput;
        if (MovementInput.moveStrafe > 0.0f) {
            rotationYaw -= 90.0f * n;
        }
        Minecraft.getMinecraft();
        MovementInput cfr_ignored_3 = Minecraft.thePlayer.movementInput;
        if (!(MovementInput.moveStrafe < 0.0f)) return rotationYaw * ((float)Math.PI / 180);
        rotationYaw += 90.0f * n;
        return rotationYaw * ((float)Math.PI / 180);
    }
}

