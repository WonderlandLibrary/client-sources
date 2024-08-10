package cc.slack.utils.player;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.*;

import java.util.Arrays;
import java.util.List;
import cc.slack.utils.client.IMinecraft;


public final class InventoryUtil implements IMinecraft {

    public static final List<Block> BLOCK_BLACKLIST = Arrays.asList(
            Blocks.enchanting_table,
            Blocks.chest,
            Blocks.ender_chest,
            Blocks.trapped_chest,
            Blocks.anvil,
            Blocks.sand,
            Blocks.web,
            Blocks.torch,
            Blocks.crafting_table,
            Blocks.furnace,
            Blocks.waterlily,
            Blocks.dispenser,
            Blocks.stone_pressure_plate,
            Blocks.wooden_pressure_plate,
            Blocks.noteblock,
            Blocks.dropper,
            Blocks.tnt,
            Blocks.standing_banner,
            Blocks.wall_banner,
            Blocks.redstone_torch,
            Blocks.gravel,
            Blocks.cactus,
            Blocks.bed,
            Blocks.lever,
            Blocks.standing_sign,
            Blocks.wall_sign,
            Blocks.jukebox,
            Blocks.oak_fence,
            Blocks.spruce_fence,
            Blocks.birch_fence,
            Blocks.jungle_fence,
            Blocks.dark_oak_fence,
            Blocks.oak_fence_gate,
            Blocks.spruce_fence_gate,
            Blocks.birch_fence_gate,
            Blocks.jungle_fence_gate,
            Blocks.dark_oak_fence_gate,
            Blocks.nether_brick_fence,
            //Blocks.cake,
            Blocks.trapdoor,
            Blocks.melon_block,
            Blocks.brewing_stand,
            Blocks.cauldron,
            Blocks.skull,
            Blocks.hopper,
            Blocks.carpet,
            Blocks.redstone_wire,
            Blocks.light_weighted_pressure_plate,
            Blocks.heavy_weighted_pressure_plate,
            Blocks.daylight_detector
    );

    public static Slot getSlot(int i){return mc.thePlayer.inventoryContainer.getSlot(i); }

    public static int findItem(final Item item) {
        return findItem(36,45, item);
    }

    public static int findItem(final int startSlot, final int endSlot, final Item item) {
        for (int i = startSlot; i < endSlot; i++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

            if (stack != null && stack.getItem() == item)
                return i;
        }
        return -1;
    }

    public static int findFireball() {
        for (int i = 36; i < 45; i++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

            if (stack != null && (stack.getItem() instanceof ItemFireball || stack.getItem() instanceof ItemFireworkCharge))
                return i;
        }
        return -1;
    }

    public static float getDamage(ItemStack stack){
        float damage = 0;
        Item item = stack.getItem();
        if(item instanceof ItemSword){
            ItemSword sword = (ItemSword)item;
            damage += sword.getDamageVsEntity();
        }
        damage += EnchantmentHelper.getEnchantmentLevel(16, stack) * 1.25f +
                EnchantmentHelper.getEnchantmentLevel(20, stack) * 1.02f;
        return damage;

    }

    public static boolean isHotbarFull() {
        for (int i = 36; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getStack() == null)
                return false;
        }
        return true;
    }

    public static int getEmptySlot() {
        for(int i = 36; i < 45; ++i) {
            if(!getSlot(i).getHasStack()) return i;
        }
        return -1;
    }

    public static void swap(int slot1, int hotbarSlot){
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot1, hotbarSlot, 2, mc.thePlayer);
    }


    public static int pickHotarBlock(boolean biggestStack) {
        if (biggestStack) {
            int currentStackSize = 0;
            int currentSlot = 36;
            for (int i = 36; i < 45; i++) {
                final ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

                if (itemStack != null && itemStack.getItem() instanceof ItemBlock && itemStack.stackSize > currentStackSize) {
                    final Block block = ((ItemBlock) itemStack.getItem()).getBlock();

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
            for (int i = 36; i < 45; i++) {
                final ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

                if (itemStack != null && itemStack.getItem() instanceof ItemBlock && itemStack.stackSize > 0) {
                    final Block block = ((ItemBlock) itemStack.getItem()).getBlock();

                    if (block.isFullCube() && !BLOCK_BLACKLIST.contains(block))
                        return i - 36;
                }
            }
        }
        return -1;
    }

    public static boolean hasItemWithCheck(Item item, boolean checkHotbar, boolean checkInventory) {
        if(checkHotbar) {
            for(int i = 36; i < 45; ++i) {
                if(getSlot(i).getHasStack()) {
                    ItemStack itemStack = getSlot(i).getStack();
                    if(item.getUnlocalizedName().equalsIgnoreCase(itemStack.getItem().getUnlocalizedName()))
                        return true;
                }
            }
        }

        if(checkInventory) {
            for (int i = 9; i < 36; ++i) {
                if (getSlot(i).getHasStack()) {
                    ItemStack itemStack = getSlot(i).getStack();
                    if (item.getUnlocalizedName().equalsIgnoreCase(itemStack.getItem().getUnlocalizedName()))
                        return true;
                }
            }
        }
        return false;
    }

}