/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tags;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraft.tags.TagRegistry;
import net.minecraft.tags.TagRegistryManager;
import net.minecraft.util.ResourceLocation;

public final class BlockTags {
    protected static final TagRegistry<Block> collection = TagRegistryManager.create(new ResourceLocation("block"), ITagCollectionSupplier::getBlockTags);
    public static final ITag.INamedTag<Block> WOOL = BlockTags.makeWrapperTag("wool");
    public static final ITag.INamedTag<Block> PLANKS = BlockTags.makeWrapperTag("planks");
    public static final ITag.INamedTag<Block> STONE_BRICKS = BlockTags.makeWrapperTag("stone_bricks");
    public static final ITag.INamedTag<Block> WOODEN_BUTTONS = BlockTags.makeWrapperTag("wooden_buttons");
    public static final ITag.INamedTag<Block> BUTTONS = BlockTags.makeWrapperTag("buttons");
    public static final ITag.INamedTag<Block> CARPETS = BlockTags.makeWrapperTag("carpets");
    public static final ITag.INamedTag<Block> WOODEN_DOORS = BlockTags.makeWrapperTag("wooden_doors");
    public static final ITag.INamedTag<Block> WOODEN_STAIRS = BlockTags.makeWrapperTag("wooden_stairs");
    public static final ITag.INamedTag<Block> WOODEN_SLABS = BlockTags.makeWrapperTag("wooden_slabs");
    public static final ITag.INamedTag<Block> WOODEN_FENCES = BlockTags.makeWrapperTag("wooden_fences");
    public static final ITag.INamedTag<Block> PRESSURE_PLATES = BlockTags.makeWrapperTag("pressure_plates");
    public static final ITag.INamedTag<Block> WOODEN_PRESSURE_PLATES = BlockTags.makeWrapperTag("wooden_pressure_plates");
    public static final ITag.INamedTag<Block> STONE_PRESSURE_PLATES = BlockTags.makeWrapperTag("stone_pressure_plates");
    public static final ITag.INamedTag<Block> WOODEN_TRAPDOORS = BlockTags.makeWrapperTag("wooden_trapdoors");
    public static final ITag.INamedTag<Block> DOORS = BlockTags.makeWrapperTag("doors");
    public static final ITag.INamedTag<Block> SAPLINGS = BlockTags.makeWrapperTag("saplings");
    public static final ITag.INamedTag<Block> LOGS_THAT_BURN = BlockTags.makeWrapperTag("logs_that_burn");
    public static final ITag.INamedTag<Block> LOGS = BlockTags.makeWrapperTag("logs");
    public static final ITag.INamedTag<Block> DARK_OAK_LOGS = BlockTags.makeWrapperTag("dark_oak_logs");
    public static final ITag.INamedTag<Block> OAK_LOGS = BlockTags.makeWrapperTag("oak_logs");
    public static final ITag.INamedTag<Block> BIRCH_LOGS = BlockTags.makeWrapperTag("birch_logs");
    public static final ITag.INamedTag<Block> ACACIA_LOGS = BlockTags.makeWrapperTag("acacia_logs");
    public static final ITag.INamedTag<Block> JUNGLE_LOGS = BlockTags.makeWrapperTag("jungle_logs");
    public static final ITag.INamedTag<Block> SPRUCE_LOGS = BlockTags.makeWrapperTag("spruce_logs");
    public static final ITag.INamedTag<Block> CRIMSON_STEMS = BlockTags.makeWrapperTag("crimson_stems");
    public static final ITag.INamedTag<Block> WARPED_STEMS = BlockTags.makeWrapperTag("warped_stems");
    public static final ITag.INamedTag<Block> BANNERS = BlockTags.makeWrapperTag("banners");
    public static final ITag.INamedTag<Block> SAND = BlockTags.makeWrapperTag("sand");
    public static final ITag.INamedTag<Block> STAIRS = BlockTags.makeWrapperTag("stairs");
    public static final ITag.INamedTag<Block> SLABS = BlockTags.makeWrapperTag("slabs");
    public static final ITag.INamedTag<Block> WALLS = BlockTags.makeWrapperTag("walls");
    public static final ITag.INamedTag<Block> ANVIL = BlockTags.makeWrapperTag("anvil");
    public static final ITag.INamedTag<Block> RAILS = BlockTags.makeWrapperTag("rails");
    public static final ITag.INamedTag<Block> LEAVES = BlockTags.makeWrapperTag("leaves");
    public static final ITag.INamedTag<Block> TRAPDOORS = BlockTags.makeWrapperTag("trapdoors");
    public static final ITag.INamedTag<Block> SMALL_FLOWERS = BlockTags.makeWrapperTag("small_flowers");
    public static final ITag.INamedTag<Block> BEDS = BlockTags.makeWrapperTag("beds");
    public static final ITag.INamedTag<Block> FENCES = BlockTags.makeWrapperTag("fences");
    public static final ITag.INamedTag<Block> TALL_FLOWERS = BlockTags.makeWrapperTag("tall_flowers");
    public static final ITag.INamedTag<Block> FLOWERS = BlockTags.makeWrapperTag("flowers");
    public static final ITag.INamedTag<Block> PIGLIN_REPELLENTS = BlockTags.makeWrapperTag("piglin_repellents");
    public static final ITag.INamedTag<Block> GOLD_ORES = BlockTags.makeWrapperTag("gold_ores");
    public static final ITag.INamedTag<Block> NON_FLAMMABLE_WOOD = BlockTags.makeWrapperTag("non_flammable_wood");
    public static final ITag.INamedTag<Block> FLOWER_POTS = BlockTags.makeWrapperTag("flower_pots");
    public static final ITag.INamedTag<Block> ENDERMAN_HOLDABLE = BlockTags.makeWrapperTag("enderman_holdable");
    public static final ITag.INamedTag<Block> ICE = BlockTags.makeWrapperTag("ice");
    public static final ITag.INamedTag<Block> VALID_SPAWN = BlockTags.makeWrapperTag("valid_spawn");
    public static final ITag.INamedTag<Block> IMPERMEABLE = BlockTags.makeWrapperTag("impermeable");
    public static final ITag.INamedTag<Block> UNDERWATER_BONEMEALS = BlockTags.makeWrapperTag("underwater_bonemeals");
    public static final ITag.INamedTag<Block> CORAL_BLOCKS = BlockTags.makeWrapperTag("coral_blocks");
    public static final ITag.INamedTag<Block> WALL_CORALS = BlockTags.makeWrapperTag("wall_corals");
    public static final ITag.INamedTag<Block> CORAL_PLANTS = BlockTags.makeWrapperTag("coral_plants");
    public static final ITag.INamedTag<Block> CORALS = BlockTags.makeWrapperTag("corals");
    public static final ITag.INamedTag<Block> BAMBOO_PLANTABLE_ON = BlockTags.makeWrapperTag("bamboo_plantable_on");
    public static final ITag.INamedTag<Block> STANDING_SIGNS = BlockTags.makeWrapperTag("standing_signs");
    public static final ITag.INamedTag<Block> WALL_SIGNS = BlockTags.makeWrapperTag("wall_signs");
    public static final ITag.INamedTag<Block> SIGNS = BlockTags.makeWrapperTag("signs");
    public static final ITag.INamedTag<Block> DRAGON_IMMUNE = BlockTags.makeWrapperTag("dragon_immune");
    public static final ITag.INamedTag<Block> WITHER_IMMUNE = BlockTags.makeWrapperTag("wither_immune");
    public static final ITag.INamedTag<Block> WITHER_SUMMON_BASE_BLOCKS = BlockTags.makeWrapperTag("wither_summon_base_blocks");
    public static final ITag.INamedTag<Block> BEEHIVES = BlockTags.makeWrapperTag("beehives");
    public static final ITag.INamedTag<Block> CROPS = BlockTags.makeWrapperTag("crops");
    public static final ITag.INamedTag<Block> BEE_GROWABLES = BlockTags.makeWrapperTag("bee_growables");
    public static final ITag.INamedTag<Block> PORTALS = BlockTags.makeWrapperTag("portals");
    public static final ITag.INamedTag<Block> FIRE = BlockTags.makeWrapperTag("fire");
    public static final ITag.INamedTag<Block> NYLIUM = BlockTags.makeWrapperTag("nylium");
    public static final ITag.INamedTag<Block> WART_BLOCKS = BlockTags.makeWrapperTag("wart_blocks");
    public static final ITag.INamedTag<Block> BEACON_BASE_BLOCKS = BlockTags.makeWrapperTag("beacon_base_blocks");
    public static final ITag.INamedTag<Block> SOUL_SPEED_BLOCKS = BlockTags.makeWrapperTag("soul_speed_blocks");
    public static final ITag.INamedTag<Block> WALL_POST_OVERRIDE = BlockTags.makeWrapperTag("wall_post_override");
    public static final ITag.INamedTag<Block> CLIMBABLE = BlockTags.makeWrapperTag("climbable");
    public static final ITag.INamedTag<Block> SHULKER_BOXES = BlockTags.makeWrapperTag("shulker_boxes");
    public static final ITag.INamedTag<Block> HOGLIN_REPELLENTS = BlockTags.makeWrapperTag("hoglin_repellents");
    public static final ITag.INamedTag<Block> SOUL_FIRE_BASE_BLOCKS = BlockTags.makeWrapperTag("soul_fire_base_blocks");
    public static final ITag.INamedTag<Block> STRIDER_WARM_BLOCKS = BlockTags.makeWrapperTag("strider_warm_blocks");
    public static final ITag.INamedTag<Block> CAMPFIRES = BlockTags.makeWrapperTag("campfires");
    public static final ITag.INamedTag<Block> GUARDED_BY_PIGLINS = BlockTags.makeWrapperTag("guarded_by_piglins");
    public static final ITag.INamedTag<Block> PREVENT_MOB_SPAWNING_INSIDE = BlockTags.makeWrapperTag("prevent_mob_spawning_inside");
    public static final ITag.INamedTag<Block> FENCE_GATES = BlockTags.makeWrapperTag("fence_gates");
    public static final ITag.INamedTag<Block> UNSTABLE_BOTTOM_CENTER = BlockTags.makeWrapperTag("unstable_bottom_center");
    public static final ITag.INamedTag<Block> MUSHROOM_GROW_BLOCK = BlockTags.makeWrapperTag("mushroom_grow_block");
    public static final ITag.INamedTag<Block> INFINIBURN_OVERWORLD = BlockTags.makeWrapperTag("infiniburn_overworld");
    public static final ITag.INamedTag<Block> INFINIBURN_NETHER = BlockTags.makeWrapperTag("infiniburn_nether");
    public static final ITag.INamedTag<Block> INFINIBURN_END = BlockTags.makeWrapperTag("infiniburn_end");
    public static final ITag.INamedTag<Block> BASE_STONE_OVERWORLD = BlockTags.makeWrapperTag("base_stone_overworld");
    public static final ITag.INamedTag<Block> BASE_STONE_NETHER = BlockTags.makeWrapperTag("base_stone_nether");

    private static ITag.INamedTag<Block> makeWrapperTag(String string) {
        return collection.createTag(string);
    }

    public static ITagCollection<Block> getCollection() {
        return collection.getCollection();
    }

    public static List<? extends ITag.INamedTag<Block>> getAllTags() {
        return collection.getTags();
    }
}

