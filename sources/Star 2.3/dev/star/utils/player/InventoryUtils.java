package dev.star.utils.player;

import dev.star.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;

import java.util.Arrays;
import java.util.List;

public class InventoryUtils implements Utils {
    public static final List<Block> BLOCK_BLACKLIST;

    public static int getItemSlot(Item item) {
        for (int i = 0; i < 9; i++) {
            ItemStack is = mc.thePlayer.inventory.mainInventory[i];
            if (is != null && is.getItem() == item) {
                return i;
            }
        }
        return -1;
    }

    public static boolean isEnabled = false;
    public static int renderSlot = 0;
    public static int realSlot = 0;

    public static void startSpoofing(int slot) {
        if (isEnabled) {
            realSlot = slot;
            mc.thePlayer.inventory.currentItem = realSlot;
        } else {
            renderSlot = mc.thePlayer.inventory.currentItem;
            realSlot = slot;
            mc.thePlayer.inventory.currentItem = realSlot;
            isEnabled = true;
        }
    }

    public static void stopSpoofing() {
        realSlot = renderSlot;
        isEnabled = false;
        mc.thePlayer.inventory.currentItem = renderSlot;
    }

    public static boolean isBlockBlacklisted(Item item) {
        return item instanceof ItemAnvilBlock || (item.getUnlocalizedName().contains("sand") && !item.getUnlocalizedName().contains("stone")) || item.getUnlocalizedName().contains("gravel") || item.getUnlocalizedName().contains("ladder") || item.getUnlocalizedName().contains("tnt") || item.getUnlocalizedName().contains("chest") || item.getUnlocalizedName().contains("web")
                || item.getUnlocalizedName().contains("noteblock") || item.getUnlocalizedName().replace("-", "").contains("soulsand") || item.getUnlocalizedName().contains("ice");
    }

    public static int getBlockSlot(Block block) {
        for (int i = 0; i < 9; i++) {
            ItemStack is = mc.thePlayer.inventory.mainInventory[i];
            if (is != null && is.getItem() instanceof ItemBlock && ((ItemBlock) is.getItem()).getBlock() == block) {
                return i;
            }
        }
        return -1;
    }

    public static Item getHeldItem() {
        if (mc.thePlayer == null || mc.thePlayer.getCurrentEquippedItem() == null) return null;
        return mc.thePlayer.getCurrentEquippedItem().getItem();
    }

    public static boolean isHoldingSword() {
        return getHeldItem() instanceof ItemSword;
    }

    public static void click(int slot, int mouseButton, boolean shiftClick) {
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, mouseButton, shiftClick ? 1 : 0, mc.thePlayer);
    }

    public static void drop(int slot) {
        mc.playerController.windowClick(0, slot, 1, 4, mc.thePlayer);
    }

    public static void swap(int slot, int hSlot) {
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hSlot, 2, mc.thePlayer);
    }

    public static float getSwordStrength(ItemStack stack) {
        if (stack.getItem() instanceof ItemSword) {
            ItemSword sword = (ItemSword) stack.getItem();
            float sharpness = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25F;
            float fireAspect = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 1.5F;
            return sword.getDamageVsEntity() + sharpness + fireAspect;
        }
        return 0;
    }

    public static boolean isItemEmpty(Item item) {
        return item == null || Item.getIdFromItem(item) == 0;
    }

    public static int findItem(int startSlot, int endSlot, Item item) {
        for(int i = startSlot; i < endSlot; ++i) {
            ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() == item) {
                return i;
            }
        }

        return -1;
    }

    public static int findItem(final Item item) {
        for (int i = 0; i < 9; i++) {
            final ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);

            if (itemStack == null) {
                if (item == null) {
                    return i;
                }
                continue;
            }

            if (itemStack.getItem() == item) {
                return i;
            }
        }

        return -1;
    }


    public static boolean isHotbarFull() {
        for(int i = 36; i < 45; ++i) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getStack() == null) {
                return false;
            }
        }

        return true;
    }

    public static int pickHotarBlock(boolean biggestStack) {
        int currentStackSize;
        if (biggestStack) {
            currentStackSize = 0;
            int currentSlot = 36;

            for(int i = 36; i < 45; ++i) {
                ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (itemStack != null && itemStack.getItem() instanceof ItemBlock && itemStack.stackSize > currentStackSize) {
                    Block block = ((ItemBlock)itemStack.getItem()).getBlock();
                    if (block.isFullCube() && !BLOCK_BLACKLIST.contains(block)) {
                        currentStackSize = itemStack.stackSize;
                        currentSlot = i;
                    }
                }
            }

            if (currentStackSize > 0) {
                return currentSlot - 36;
            }
        } else {
            for(currentStackSize = 36; currentStackSize < 45; ++currentStackSize) {
                ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(currentStackSize).getStack();
                if (itemStack != null && itemStack.getItem() instanceof ItemBlock && itemStack.stackSize > 0) {
                    Block block = ((ItemBlock)itemStack.getItem()).getBlock();
                    if (block.isFullCube() && !BLOCK_BLACKLIST.contains(block)) {
                        return currentStackSize - 36;
                    }
                }
            }
        }

        return -1;
    }

    static {
        BLOCK_BLACKLIST = Arrays.asList(Blocks.enchanting_table, Blocks.chest, Blocks.ender_chest, Blocks.trapped_chest, Blocks.anvil, Blocks.sand, Blocks.web, Blocks.torch, Blocks.crafting_table, Blocks.furnace, Blocks.waterlily, Blocks.dispenser, Blocks.stone_pressure_plate, Blocks.wooden_pressure_plate, Blocks.noteblock, Blocks.dropper, Blocks.tnt, Blocks.standing_banner, Blocks.wall_banner, Blocks.redstone_torch, Blocks.gravel, Blocks.cactus, Blocks.bed, Blocks.lever, Blocks.standing_sign, Blocks.wall_sign, Blocks.jukebox, Blocks.oak_fence, Blocks.spruce_fence, Blocks.birch_fence, Blocks.jungle_fence, Blocks.dark_oak_fence, Blocks.oak_fence_gate, Blocks.spruce_fence_gate, Blocks.birch_fence_gate, Blocks.jungle_fence_gate, Blocks.dark_oak_fence_gate, Blocks.nether_brick_fence, Blocks.trapdoor, Blocks.melon_block, Blocks.brewing_stand, Blocks.cauldron, Blocks.skull, Blocks.hopper, Blocks.carpet, Blocks.redstone_wire, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.daylight_detector);
    }
}
