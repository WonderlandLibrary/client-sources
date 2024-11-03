package dev.stephen.nexus.utils.mc;

import dev.stephen.nexus.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.CropBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.*;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.screen.slot.Slot;

public class InventoryUtils implements Utils {

    private static final Item[] badItems = {
            Items.LADDER,
            Items.CHEST,
            Items.TORCH,
            Items.TORCH,
            Items.REDSTONE_TORCH,
            Items.FLOWER_POT,
            Items.GLASS_PANE,
            Items.IRON_BARS,
            Items.VINE,
            Items.OAK_FENCE,
            Items.SPRUCE_FENCE,
            Items.BIRCH_FENCE,
            Items.JUNGLE_FENCE,
            Items.ACACIA_FENCE,
            Items.DARK_OAK_FENCE,
            Items.WARPED_FENCE,
            Items.CRIMSON_FENCE,
            Items.NETHER_BRICK_FENCE,
            Items.COBBLESTONE_WALL,
            Items.MOSSY_COBBLESTONE_WALL,
            Items.BRICK_WALL,
            Items.PRISMARINE_WALL,
            Items.RED_SANDSTONE_WALL,
            Items.SANDSTONE_WALL,
            Items.STONE_BRICK_WALL,
            Items.NETHER_BRICK_WALL,
            Items.RED_NETHER_BRICK_WALL,
            Items.ANDESITE_WALL, Items.CACTUS,
            Items.DIORITE_WALL,
            Items.GRANITE_WALL,
            Items.END_ROD,
            Items.LILY_PAD,
            Items.CAULDRON,
            Items.LECTERN,
            Items.STONE_SLAB,
            Items.COBBLESTONE_SLAB,
            Items.STONE_BRICK_SLAB,
            Items.SANDSTONE_SLAB,
            Items.RED_SANDSTONE_SLAB,
            Items.BRICK_SLAB,
            Items.QUARTZ_SLAB,
            Items.OAK_SLAB,
            Items.SPRUCE_SLAB,
            Items.BIRCH_SLAB,
            Items.JUNGLE_SLAB,
            Items.ACACIA_SLAB,
            Items.DARK_OAK_SLAB,
            Items.PURPUR_SLAB,
            Items.NETHER_BRICK_SLAB,
            Items.RED_NETHER_BRICK_SLAB,
            Items.PRISMARINE_SLAB,
            Items.PRISMARINE_BRICK_SLAB,
            Items.DARK_PRISMARINE_SLAB,
            Items.CAMPFIRE,
            Items.SOUL_CAMPFIRE,
            Items.WHITE_BED,
            Items.ORANGE_BED,
            Items.MAGENTA_BED,
            Items.LIGHT_BLUE_BED,
            Items.YELLOW_BED,
            Items.LIME_BED,
            Items.PINK_BED,
            Items.GRAY_BED,
            Items.LIGHT_GRAY_BED,
            Items.CYAN_BED,
            Items.PURPLE_BED,
            Items.BLUE_BED,
            Items.BROWN_BED,
            Items.GREEN_BED,
            Items.RED_BED,
            Items.BLACK_BED,
            Items.SWEET_BERRIES,
            Items.CAKE,
            Items.CARVED_PUMPKIN,
            Items.JACK_O_LANTERN,
            Items.BELL,
            Items.COMPOSTER,
            Items.SCAFFOLDING,
            Items.BARREL,
            Items.BEE_NEST,
            Items.BEEHIVE,
            Items.LOOM,
            Items.SMOKER,
            Items.BLAST_FURNACE,
            Items.CARTOGRAPHY_TABLE,
            Items.FLETCHING_TABLE,
            Items.GRINDSTONE,
            Items.SMITHING_TABLE,
            Items.STONECUTTER,
            Items.COBWEB,
            Items.SPAWNER,
            Items.CHEST_MINECART,
            Items.FURNACE_MINECART,
            Items.HOPPER_MINECART,
            Items.TNT_MINECART,
            Items.BEEHIVE,
            Items.BEE_NEST,
            Items.CARROT,
            Items.POTATO,
            Items.WHEAT,
            Items.BEETROOT,
            Items.WHEAT_SEEDS
    };

    public static boolean isBlockPlaceable(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem && !(((BlockItem) stack.getItem()).getBlock() instanceof CropBlock)) {
            Block block = ((BlockItem) stack.getItem()).getBlock();
            if (block.getDefaultState().isSolid()) {
                for (Item badItem : badItems) {
                    if (stack.getItem() == badItem) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static float getSwordDamage(Slot slot) {
        ItemStack stack = slot.getStack();

        int sharp = EnchantmentHelper.getLevel(mc.world.getRegistryManager().get(Enchantments.SHARPNESS.getRegistryRef()).getEntry(Enchantments.SHARPNESS).get(), stack);
        float sharpDmg = 0;
        if (sharp > 0) {
            sharpDmg = 0.5f * sharp + 0.5f;
        }

        if (stack.getItem() instanceof SwordItem s) {
            return s.getMaterial().getAttackDamage() + sharpDmg;
        }

        return -1;
    }

    public static float getProtection(Slot slot) {
        ItemStack stack = slot.getStack();

        int prot = EnchantmentHelper.getLevel(mc.world.getRegistryManager().get(Enchantments.PROTECTION.getRegistryRef()).getEntry(Enchantments.PROTECTION).get(), stack);
        float protMulti = 1;

        if (prot > 0) {
            protMulti = 1f - (0.04f * ((float) prot));
        }

        protMulti -= (((ArmorItem) stack.getItem()).getProtection() * 0.04f);
        return 1f - protMulti;
    }

    public static float getProtection(ItemStack stack) {
        int prot = EnchantmentHelper.getLevel(mc.world.getRegistryManager().get(Enchantments.PROTECTION.getRegistryRef()).getEntry(Enchantments.PROTECTION).get(), stack);
        float protMulti = 1;

        if (prot > 0) {
            protMulti = 1f - (0.04f * ((float) prot));
        }

        protMulti -= (((ArmorItem) stack.getItem()).getProtection() * 0.04f);
        return 1f - protMulti;
    }

    public static float getDestroySpeed(Slot slot) {
        ItemStack stack = slot.getStack();

        int eff = EnchantmentHelper.getLevel(mc.world.getRegistryManager().get(Enchantments.EFFICIENCY.getRegistryRef()).getEntry(Enchantments.EFFICIENCY).get(), stack);
        ToolMaterial mat = ((ToolItem) stack.getItem()).getMaterial();
        int speed = switch (mat) {
            case ToolMaterials.WOOD -> 2;
            case ToolMaterials.STONE -> 4;
            case ToolMaterials.IRON -> 6;
            case ToolMaterials.DIAMOND -> 8;
            case ToolMaterials.NETHERITE -> 9;
            case ToolMaterials.GOLD -> 12;
            case null, default -> 1;
        };

        if (eff > 0) {
            speed += 1;
            speed += (int) Math.pow(eff, 2);
        }

        return speed;
    }

    public static float getBowDamage(Slot slot) {
        ItemStack stack = slot.getStack();

        int power = EnchantmentHelper.getLevel(mc.world.getRegistryManager().get(Enchantments.POWER.getRegistryRef()).getEntry(Enchantments.POWER).get(), stack);
        float dmgMulti = 1f;
        if (power > 0) {
            dmgMulti += 0.25f * ((float) (1 + power));
        }
        return dmgMulti;
    }

    public static void closeInventorySilently() {
        PacketUtils.sendPacket(new CloseHandledScreenC2SPacket(0));
    }
}
