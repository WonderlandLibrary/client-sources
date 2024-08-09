/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.stats;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.StatType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class Stats {
    public static final StatType<Block> BLOCK_MINED = Stats.registerType("mined", Registry.BLOCK);
    public static final StatType<Item> ITEM_CRAFTED = Stats.registerType("crafted", Registry.ITEM);
    public static final StatType<Item> ITEM_USED = Stats.registerType("used", Registry.ITEM);
    public static final StatType<Item> ITEM_BROKEN = Stats.registerType("broken", Registry.ITEM);
    public static final StatType<Item> ITEM_PICKED_UP = Stats.registerType("picked_up", Registry.ITEM);
    public static final StatType<Item> ITEM_DROPPED = Stats.registerType("dropped", Registry.ITEM);
    public static final StatType<EntityType<?>> ENTITY_KILLED = Stats.registerType("killed", Registry.ENTITY_TYPE);
    public static final StatType<EntityType<?>> ENTITY_KILLED_BY = Stats.registerType("killed_by", Registry.ENTITY_TYPE);
    public static final StatType<ResourceLocation> CUSTOM = Stats.registerType("custom", Registry.CUSTOM_STAT);
    public static final ResourceLocation LEAVE_GAME = Stats.registerCustom("leave_game", IStatFormatter.DEFAULT);
    public static final ResourceLocation PLAY_ONE_MINUTE = Stats.registerCustom("play_one_minute", IStatFormatter.TIME);
    public static final ResourceLocation TIME_SINCE_DEATH = Stats.registerCustom("time_since_death", IStatFormatter.TIME);
    public static final ResourceLocation TIME_SINCE_REST = Stats.registerCustom("time_since_rest", IStatFormatter.TIME);
    public static final ResourceLocation SNEAK_TIME = Stats.registerCustom("sneak_time", IStatFormatter.TIME);
    public static final ResourceLocation WALK_ONE_CM = Stats.registerCustom("walk_one_cm", IStatFormatter.DISTANCE);
    public static final ResourceLocation CROUCH_ONE_CM = Stats.registerCustom("crouch_one_cm", IStatFormatter.DISTANCE);
    public static final ResourceLocation SPRINT_ONE_CM = Stats.registerCustom("sprint_one_cm", IStatFormatter.DISTANCE);
    public static final ResourceLocation WALK_ON_WATER_ONE_CM = Stats.registerCustom("walk_on_water_one_cm", IStatFormatter.DISTANCE);
    public static final ResourceLocation FALL_ONE_CM = Stats.registerCustom("fall_one_cm", IStatFormatter.DISTANCE);
    public static final ResourceLocation CLIMB_ONE_CM = Stats.registerCustom("climb_one_cm", IStatFormatter.DISTANCE);
    public static final ResourceLocation FLY_ONE_CM = Stats.registerCustom("fly_one_cm", IStatFormatter.DISTANCE);
    public static final ResourceLocation WALK_UNDER_WATER_ONE_CM = Stats.registerCustom("walk_under_water_one_cm", IStatFormatter.DISTANCE);
    public static final ResourceLocation MINECART_ONE_CM = Stats.registerCustom("minecart_one_cm", IStatFormatter.DISTANCE);
    public static final ResourceLocation BOAT_ONE_CM = Stats.registerCustom("boat_one_cm", IStatFormatter.DISTANCE);
    public static final ResourceLocation PIG_ONE_CM = Stats.registerCustom("pig_one_cm", IStatFormatter.DISTANCE);
    public static final ResourceLocation HORSE_ONE_CM = Stats.registerCustom("horse_one_cm", IStatFormatter.DISTANCE);
    public static final ResourceLocation AVIATE_ONE_CM = Stats.registerCustom("aviate_one_cm", IStatFormatter.DISTANCE);
    public static final ResourceLocation SWIM_ONE_CM = Stats.registerCustom("swim_one_cm", IStatFormatter.DISTANCE);
    public static final ResourceLocation field_232862_C_ = Stats.registerCustom("strider_one_cm", IStatFormatter.DISTANCE);
    public static final ResourceLocation JUMP = Stats.registerCustom("jump", IStatFormatter.DEFAULT);
    public static final ResourceLocation DROP = Stats.registerCustom("drop", IStatFormatter.DEFAULT);
    public static final ResourceLocation DAMAGE_DEALT = Stats.registerCustom("damage_dealt", IStatFormatter.DIVIDE_BY_TEN);
    public static final ResourceLocation DAMAGE_DEALT_ABSORBED = Stats.registerCustom("damage_dealt_absorbed", IStatFormatter.DIVIDE_BY_TEN);
    public static final ResourceLocation DAMAGE_DEALT_RESISTED = Stats.registerCustom("damage_dealt_resisted", IStatFormatter.DIVIDE_BY_TEN);
    public static final ResourceLocation DAMAGE_TAKEN = Stats.registerCustom("damage_taken", IStatFormatter.DIVIDE_BY_TEN);
    public static final ResourceLocation DAMAGE_BLOCKED_BY_SHIELD = Stats.registerCustom("damage_blocked_by_shield", IStatFormatter.DIVIDE_BY_TEN);
    public static final ResourceLocation DAMAGE_ABSORBED = Stats.registerCustom("damage_absorbed", IStatFormatter.DIVIDE_BY_TEN);
    public static final ResourceLocation DAMAGE_RESISTED = Stats.registerCustom("damage_resisted", IStatFormatter.DIVIDE_BY_TEN);
    public static final ResourceLocation DEATHS = Stats.registerCustom("deaths", IStatFormatter.DEFAULT);
    public static final ResourceLocation MOB_KILLS = Stats.registerCustom("mob_kills", IStatFormatter.DEFAULT);
    public static final ResourceLocation ANIMALS_BRED = Stats.registerCustom("animals_bred", IStatFormatter.DEFAULT);
    public static final ResourceLocation PLAYER_KILLS = Stats.registerCustom("player_kills", IStatFormatter.DEFAULT);
    public static final ResourceLocation FISH_CAUGHT = Stats.registerCustom("fish_caught", IStatFormatter.DEFAULT);
    public static final ResourceLocation TALKED_TO_VILLAGER = Stats.registerCustom("talked_to_villager", IStatFormatter.DEFAULT);
    public static final ResourceLocation TRADED_WITH_VILLAGER = Stats.registerCustom("traded_with_villager", IStatFormatter.DEFAULT);
    public static final ResourceLocation EAT_CAKE_SLICE = Stats.registerCustom("eat_cake_slice", IStatFormatter.DEFAULT);
    public static final ResourceLocation FILL_CAULDRON = Stats.registerCustom("fill_cauldron", IStatFormatter.DEFAULT);
    public static final ResourceLocation USE_CAULDRON = Stats.registerCustom("use_cauldron", IStatFormatter.DEFAULT);
    public static final ResourceLocation CLEAN_ARMOR = Stats.registerCustom("clean_armor", IStatFormatter.DEFAULT);
    public static final ResourceLocation CLEAN_BANNER = Stats.registerCustom("clean_banner", IStatFormatter.DEFAULT);
    public static final ResourceLocation CLEAN_SHULKER_BOX = Stats.registerCustom("clean_shulker_box", IStatFormatter.DEFAULT);
    public static final ResourceLocation INTERACT_WITH_BREWINGSTAND = Stats.registerCustom("interact_with_brewingstand", IStatFormatter.DEFAULT);
    public static final ResourceLocation INTERACT_WITH_BEACON = Stats.registerCustom("interact_with_beacon", IStatFormatter.DEFAULT);
    public static final ResourceLocation INSPECT_DROPPER = Stats.registerCustom("inspect_dropper", IStatFormatter.DEFAULT);
    public static final ResourceLocation INSPECT_HOPPER = Stats.registerCustom("inspect_hopper", IStatFormatter.DEFAULT);
    public static final ResourceLocation INSPECT_DISPENSER = Stats.registerCustom("inspect_dispenser", IStatFormatter.DEFAULT);
    public static final ResourceLocation PLAY_NOTEBLOCK = Stats.registerCustom("play_noteblock", IStatFormatter.DEFAULT);
    public static final ResourceLocation TUNE_NOTEBLOCK = Stats.registerCustom("tune_noteblock", IStatFormatter.DEFAULT);
    public static final ResourceLocation POT_FLOWER = Stats.registerCustom("pot_flower", IStatFormatter.DEFAULT);
    public static final ResourceLocation TRIGGER_TRAPPED_CHEST = Stats.registerCustom("trigger_trapped_chest", IStatFormatter.DEFAULT);
    public static final ResourceLocation OPEN_ENDERCHEST = Stats.registerCustom("open_enderchest", IStatFormatter.DEFAULT);
    public static final ResourceLocation ENCHANT_ITEM = Stats.registerCustom("enchant_item", IStatFormatter.DEFAULT);
    public static final ResourceLocation PLAY_RECORD = Stats.registerCustom("play_record", IStatFormatter.DEFAULT);
    public static final ResourceLocation INTERACT_WITH_FURNACE = Stats.registerCustom("interact_with_furnace", IStatFormatter.DEFAULT);
    public static final ResourceLocation INTERACT_WITH_CRAFTING_TABLE = Stats.registerCustom("interact_with_crafting_table", IStatFormatter.DEFAULT);
    public static final ResourceLocation OPEN_CHEST = Stats.registerCustom("open_chest", IStatFormatter.DEFAULT);
    public static final ResourceLocation SLEEP_IN_BED = Stats.registerCustom("sleep_in_bed", IStatFormatter.DEFAULT);
    public static final ResourceLocation OPEN_SHULKER_BOX = Stats.registerCustom("open_shulker_box", IStatFormatter.DEFAULT);
    public static final ResourceLocation OPEN_BARREL = Stats.registerCustom("open_barrel", IStatFormatter.DEFAULT);
    public static final ResourceLocation INTERACT_WITH_BLAST_FURNACE = Stats.registerCustom("interact_with_blast_furnace", IStatFormatter.DEFAULT);
    public static final ResourceLocation INTERACT_WITH_SMOKER = Stats.registerCustom("interact_with_smoker", IStatFormatter.DEFAULT);
    public static final ResourceLocation INTERACT_WITH_LECTERN = Stats.registerCustom("interact_with_lectern", IStatFormatter.DEFAULT);
    public static final ResourceLocation INTERACT_WITH_CAMPFIRE = Stats.registerCustom("interact_with_campfire", IStatFormatter.DEFAULT);
    public static final ResourceLocation INTERACT_WITH_CARTOGRAPHY_TABLE = Stats.registerCustom("interact_with_cartography_table", IStatFormatter.DEFAULT);
    public static final ResourceLocation INTERACT_WITH_LOOM = Stats.registerCustom("interact_with_loom", IStatFormatter.DEFAULT);
    public static final ResourceLocation INTERACT_WITH_STONECUTTER = Stats.registerCustom("interact_with_stonecutter", IStatFormatter.DEFAULT);
    public static final ResourceLocation BELL_RING = Stats.registerCustom("bell_ring", IStatFormatter.DEFAULT);
    public static final ResourceLocation RAID_TRIGGER = Stats.registerCustom("raid_trigger", IStatFormatter.DEFAULT);
    public static final ResourceLocation RAID_WIN = Stats.registerCustom("raid_win", IStatFormatter.DEFAULT);
    public static final ResourceLocation INTERACT_WITH_ANVIL = Stats.registerCustom("interact_with_anvil", IStatFormatter.DEFAULT);
    public static final ResourceLocation INTERACT_WITH_GRINDSTONE = Stats.registerCustom("interact_with_grindstone", IStatFormatter.DEFAULT);
    public static final ResourceLocation field_232863_aD_ = Stats.registerCustom("target_hit", IStatFormatter.DEFAULT);
    public static final ResourceLocation field_232864_aE_ = Stats.registerCustom("interact_with_smithing_table", IStatFormatter.DEFAULT);

    private static ResourceLocation registerCustom(String string, IStatFormatter iStatFormatter) {
        ResourceLocation resourceLocation = new ResourceLocation(string);
        Registry.register(Registry.CUSTOM_STAT, string, resourceLocation);
        CUSTOM.get(resourceLocation, iStatFormatter);
        return resourceLocation;
    }

    private static <T> StatType<T> registerType(String string, Registry<T> registry) {
        return Registry.register(Registry.STATS, string, new StatType<T>(registry));
    }
}

