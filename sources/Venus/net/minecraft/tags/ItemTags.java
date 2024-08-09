/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tags;

import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraft.tags.TagRegistry;
import net.minecraft.tags.TagRegistryManager;
import net.minecraft.util.ResourceLocation;

public final class ItemTags {
    protected static final TagRegistry<Item> collection = TagRegistryManager.create(new ResourceLocation("item"), ITagCollectionSupplier::getItemTags);
    public static final ITag.INamedTag<Item> WOOL = ItemTags.makeWrapperTag("wool");
    public static final ITag.INamedTag<Item> PLANKS = ItemTags.makeWrapperTag("planks");
    public static final ITag.INamedTag<Item> STONE_BRICKS = ItemTags.makeWrapperTag("stone_bricks");
    public static final ITag.INamedTag<Item> WOODEN_BUTTONS = ItemTags.makeWrapperTag("wooden_buttons");
    public static final ITag.INamedTag<Item> BUTTONS = ItemTags.makeWrapperTag("buttons");
    public static final ITag.INamedTag<Item> CARPETS = ItemTags.makeWrapperTag("carpets");
    public static final ITag.INamedTag<Item> WOODEN_DOORS = ItemTags.makeWrapperTag("wooden_doors");
    public static final ITag.INamedTag<Item> WOODEN_STAIRS = ItemTags.makeWrapperTag("wooden_stairs");
    public static final ITag.INamedTag<Item> WOODEN_SLABS = ItemTags.makeWrapperTag("wooden_slabs");
    public static final ITag.INamedTag<Item> WOODEN_FENCES = ItemTags.makeWrapperTag("wooden_fences");
    public static final ITag.INamedTag<Item> WOODEN_PRESSURE_PLATES = ItemTags.makeWrapperTag("wooden_pressure_plates");
    public static final ITag.INamedTag<Item> WOODEN_TRAPDOORS = ItemTags.makeWrapperTag("wooden_trapdoors");
    public static final ITag.INamedTag<Item> DOORS = ItemTags.makeWrapperTag("doors");
    public static final ITag.INamedTag<Item> SAPLINGS = ItemTags.makeWrapperTag("saplings");
    public static final ITag.INamedTag<Item> LOGS_THAT_BURN = ItemTags.makeWrapperTag("logs_that_burn");
    public static final ITag.INamedTag<Item> LOGS = ItemTags.makeWrapperTag("logs");
    public static final ITag.INamedTag<Item> DARK_OAK_LOGS = ItemTags.makeWrapperTag("dark_oak_logs");
    public static final ITag.INamedTag<Item> OAK_LOGS = ItemTags.makeWrapperTag("oak_logs");
    public static final ITag.INamedTag<Item> BIRCH_LOGS = ItemTags.makeWrapperTag("birch_logs");
    public static final ITag.INamedTag<Item> ACACIA_LOGS = ItemTags.makeWrapperTag("acacia_logs");
    public static final ITag.INamedTag<Item> JUNGLE_LOGS = ItemTags.makeWrapperTag("jungle_logs");
    public static final ITag.INamedTag<Item> SPRUCE_LOGS = ItemTags.makeWrapperTag("spruce_logs");
    public static final ITag.INamedTag<Item> CRIMSON_STEMS = ItemTags.makeWrapperTag("crimson_stems");
    public static final ITag.INamedTag<Item> WARPED_STEMS = ItemTags.makeWrapperTag("warped_stems");
    public static final ITag.INamedTag<Item> BANNERS = ItemTags.makeWrapperTag("banners");
    public static final ITag.INamedTag<Item> SAND = ItemTags.makeWrapperTag("sand");
    public static final ITag.INamedTag<Item> STAIRS = ItemTags.makeWrapperTag("stairs");
    public static final ITag.INamedTag<Item> SLABS = ItemTags.makeWrapperTag("slabs");
    public static final ITag.INamedTag<Item> WALLS = ItemTags.makeWrapperTag("walls");
    public static final ITag.INamedTag<Item> ANVIL = ItemTags.makeWrapperTag("anvil");
    public static final ITag.INamedTag<Item> RAILS = ItemTags.makeWrapperTag("rails");
    public static final ITag.INamedTag<Item> LEAVES = ItemTags.makeWrapperTag("leaves");
    public static final ITag.INamedTag<Item> TRAPDOORS = ItemTags.makeWrapperTag("trapdoors");
    public static final ITag.INamedTag<Item> SMALL_FLOWERS = ItemTags.makeWrapperTag("small_flowers");
    public static final ITag.INamedTag<Item> BEDS = ItemTags.makeWrapperTag("beds");
    public static final ITag.INamedTag<Item> FENCES = ItemTags.makeWrapperTag("fences");
    public static final ITag.INamedTag<Item> TALL_FLOWERS = ItemTags.makeWrapperTag("tall_flowers");
    public static final ITag.INamedTag<Item> FLOWERS = ItemTags.makeWrapperTag("flowers");
    public static final ITag.INamedTag<Item> PIGLIN_REPELLENTS = ItemTags.makeWrapperTag("piglin_repellents");
    public static final ITag.INamedTag<Item> PIGLIN_LOVED = ItemTags.makeWrapperTag("piglin_loved");
    public static final ITag.INamedTag<Item> GOLD_ORES = ItemTags.makeWrapperTag("gold_ores");
    public static final ITag.INamedTag<Item> NON_FLAMMABLE_WOOD = ItemTags.makeWrapperTag("non_flammable_wood");
    public static final ITag.INamedTag<Item> SOUL_FIRE_BASE_BLOCKS = ItemTags.makeWrapperTag("soul_fire_base_blocks");
    public static final ITag.INamedTag<Item> BOATS = ItemTags.makeWrapperTag("boats");
    public static final ITag.INamedTag<Item> FISHES = ItemTags.makeWrapperTag("fishes");
    public static final ITag.INamedTag<Item> SIGNS = ItemTags.makeWrapperTag("signs");
    public static final ITag.INamedTag<Item> MUSIC_DISCS = ItemTags.makeWrapperTag("music_discs");
    public static final ITag.INamedTag<Item> CREEPER_DROP_MUSIC_DISCS = ItemTags.makeWrapperTag("creeper_drop_music_discs");
    public static final ITag.INamedTag<Item> COALS = ItemTags.makeWrapperTag("coals");
    public static final ITag.INamedTag<Item> ARROWS = ItemTags.makeWrapperTag("arrows");
    public static final ITag.INamedTag<Item> LECTERN_BOOKS = ItemTags.makeWrapperTag("lectern_books");
    public static final ITag.INamedTag<Item> BEACON_PAYMENT_ITEMS = ItemTags.makeWrapperTag("beacon_payment_items");
    public static final ITag.INamedTag<Item> STONE_TOOL_MATERIALS = ItemTags.makeWrapperTag("stone_tool_materials");
    public static final ITag.INamedTag<Item> STONE_CRAFTING_MATERIALS = ItemTags.makeWrapperTag("stone_crafting_materials");

    private static ITag.INamedTag<Item> makeWrapperTag(String string) {
        return collection.createTag(string);
    }

    public static ITagCollection<Item> getCollection() {
        return collection.getCollection();
    }

    public static List<? extends ITag.INamedTag<Item>> getAllTags() {
        return collection.getTags();
    }
}

