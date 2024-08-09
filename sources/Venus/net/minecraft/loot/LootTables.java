/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.Set;
import net.minecraft.util.ResourceLocation;

public class LootTables {
    private static final Set<ResourceLocation> LOOT_TABLES = Sets.newHashSet();
    private static final Set<ResourceLocation> READ_ONLY_LOOT_TABLES = Collections.unmodifiableSet(LOOT_TABLES);
    public static final ResourceLocation EMPTY = new ResourceLocation("empty");
    public static final ResourceLocation CHESTS_SPAWN_BONUS_CHEST = LootTables.register("chests/spawn_bonus_chest");
    public static final ResourceLocation CHESTS_END_CITY_TREASURE = LootTables.register("chests/end_city_treasure");
    public static final ResourceLocation CHESTS_SIMPLE_DUNGEON = LootTables.register("chests/simple_dungeon");
    public static final ResourceLocation CHESTS_VILLAGE_VILLAGE_WEAPONSMITH = LootTables.register("chests/village/village_weaponsmith");
    public static final ResourceLocation CHESTS_VILLAGE_VILLAGE_TOOLSMITH = LootTables.register("chests/village/village_toolsmith");
    public static final ResourceLocation CHESTS_VILLAGE_VILLAGE_ARMORER = LootTables.register("chests/village/village_armorer");
    public static final ResourceLocation CHESTS_VILLAGE_VILLAGE_CARTOGRAPHER = LootTables.register("chests/village/village_cartographer");
    public static final ResourceLocation CHESTS_VILLAGE_VILLAGE_MASON = LootTables.register("chests/village/village_mason");
    public static final ResourceLocation CHESTS_VILLAGE_VILLAGE_SHEPHERD = LootTables.register("chests/village/village_shepherd");
    public static final ResourceLocation CHESTS_VILLAGE_VILLAGE_BUTCHER = LootTables.register("chests/village/village_butcher");
    public static final ResourceLocation CHESTS_VILLAGE_VILLAGE_FLETCHER = LootTables.register("chests/village/village_fletcher");
    public static final ResourceLocation CHESTS_VILLAGE_VILLAGE_FISHER = LootTables.register("chests/village/village_fisher");
    public static final ResourceLocation CHESTS_VILLAGE_VILLAGE_TANNERY = LootTables.register("chests/village/village_tannery");
    public static final ResourceLocation CHESTS_VILLAGE_VILLAGE_TEMPLE = LootTables.register("chests/village/village_temple");
    public static final ResourceLocation CHESTS_VILLAGE_VILLAGE_DESERT_HOUSE = LootTables.register("chests/village/village_desert_house");
    public static final ResourceLocation CHESTS_VILLAGE_VILLAGE_PLAINS_HOUSE = LootTables.register("chests/village/village_plains_house");
    public static final ResourceLocation CHESTS_VILLAGE_VILLAGE_TAIGA_HOUSE = LootTables.register("chests/village/village_taiga_house");
    public static final ResourceLocation CHESTS_VILLAGE_VILLAGE_SNOWY_HOUSE = LootTables.register("chests/village/village_snowy_house");
    public static final ResourceLocation CHESTS_VILLAGE_VILLAGE_SAVANNA_HOUSE = LootTables.register("chests/village/village_savanna_house");
    public static final ResourceLocation CHESTS_ABANDONED_MINESHAFT = LootTables.register("chests/abandoned_mineshaft");
    public static final ResourceLocation CHESTS_NETHER_BRIDGE = LootTables.register("chests/nether_bridge");
    public static final ResourceLocation CHESTS_STRONGHOLD_LIBRARY = LootTables.register("chests/stronghold_library");
    public static final ResourceLocation CHESTS_STRONGHOLD_CROSSING = LootTables.register("chests/stronghold_crossing");
    public static final ResourceLocation CHESTS_STRONGHOLD_CORRIDOR = LootTables.register("chests/stronghold_corridor");
    public static final ResourceLocation CHESTS_DESERT_PYRAMID = LootTables.register("chests/desert_pyramid");
    public static final ResourceLocation CHESTS_JUNGLE_TEMPLE = LootTables.register("chests/jungle_temple");
    public static final ResourceLocation CHESTS_JUNGLE_TEMPLE_DISPENSER = LootTables.register("chests/jungle_temple_dispenser");
    public static final ResourceLocation CHESTS_IGLOO_CHEST = LootTables.register("chests/igloo_chest");
    public static final ResourceLocation CHESTS_WOODLAND_MANSION = LootTables.register("chests/woodland_mansion");
    public static final ResourceLocation CHESTS_UNDERWATER_RUIN_SMALL = LootTables.register("chests/underwater_ruin_small");
    public static final ResourceLocation CHESTS_UNDERWATER_RUIN_BIG = LootTables.register("chests/underwater_ruin_big");
    public static final ResourceLocation CHESTS_BURIED_TREASURE = LootTables.register("chests/buried_treasure");
    public static final ResourceLocation CHESTS_SHIPWRECK_MAP = LootTables.register("chests/shipwreck_map");
    public static final ResourceLocation CHESTS_SHIPWRECK_SUPPLY = LootTables.register("chests/shipwreck_supply");
    public static final ResourceLocation CHESTS_SHIPWRECK_TREASURE = LootTables.register("chests/shipwreck_treasure");
    public static final ResourceLocation CHESTS_PILLAGER_OUTPOST = LootTables.register("chests/pillager_outpost");
    public static final ResourceLocation BASTION_TREASURE = LootTables.register("chests/bastion_treasure");
    public static final ResourceLocation BASTION_OTHER = LootTables.register("chests/bastion_other");
    public static final ResourceLocation BASTION_BRIDGE = LootTables.register("chests/bastion_bridge");
    public static final ResourceLocation BASTION_HOGLIN_STABLE = LootTables.register("chests/bastion_hoglin_stable");
    public static final ResourceLocation RUINED_PORTAL = LootTables.register("chests/ruined_portal");
    public static final ResourceLocation ENTITIES_SHEEP_WHITE = LootTables.register("entities/sheep/white");
    public static final ResourceLocation ENTITIES_SHEEP_ORANGE = LootTables.register("entities/sheep/orange");
    public static final ResourceLocation ENTITIES_SHEEP_MAGENTA = LootTables.register("entities/sheep/magenta");
    public static final ResourceLocation ENTITIES_SHEEP_LIGHT_BLUE = LootTables.register("entities/sheep/light_blue");
    public static final ResourceLocation ENTITIES_SHEEP_YELLOW = LootTables.register("entities/sheep/yellow");
    public static final ResourceLocation ENTITIES_SHEEP_LIME = LootTables.register("entities/sheep/lime");
    public static final ResourceLocation ENTITIES_SHEEP_PINK = LootTables.register("entities/sheep/pink");
    public static final ResourceLocation ENTITIES_SHEEP_GRAY = LootTables.register("entities/sheep/gray");
    public static final ResourceLocation ENTITIES_SHEEP_LIGHT_GRAY = LootTables.register("entities/sheep/light_gray");
    public static final ResourceLocation ENTITIES_SHEEP_CYAN = LootTables.register("entities/sheep/cyan");
    public static final ResourceLocation ENTITIES_SHEEP_PURPLE = LootTables.register("entities/sheep/purple");
    public static final ResourceLocation ENTITIES_SHEEP_BLUE = LootTables.register("entities/sheep/blue");
    public static final ResourceLocation ENTITIES_SHEEP_BROWN = LootTables.register("entities/sheep/brown");
    public static final ResourceLocation ENTITIES_SHEEP_GREEN = LootTables.register("entities/sheep/green");
    public static final ResourceLocation ENTITIES_SHEEP_RED = LootTables.register("entities/sheep/red");
    public static final ResourceLocation ENTITIES_SHEEP_BLACK = LootTables.register("entities/sheep/black");
    public static final ResourceLocation GAMEPLAY_FISHING = LootTables.register("gameplay/fishing");
    public static final ResourceLocation GAMEPLAY_FISHING_JUNK = LootTables.register("gameplay/fishing/junk");
    public static final ResourceLocation GAMEPLAY_FISHING_TREASURE = LootTables.register("gameplay/fishing/treasure");
    public static final ResourceLocation GAMEPLAY_FISHING_FISH = LootTables.register("gameplay/fishing/fish");
    public static final ResourceLocation GAMEPLAY_CAT_MORNING_GIFT = LootTables.register("gameplay/cat_morning_gift");
    public static final ResourceLocation GAMEPLAY_HERO_OF_THE_VILLAGE_ARMORER_GIFT = LootTables.register("gameplay/hero_of_the_village/armorer_gift");
    public static final ResourceLocation GAMEPLAY_HERO_OF_THE_VILLAGE_BUTCHER_GIFT = LootTables.register("gameplay/hero_of_the_village/butcher_gift");
    public static final ResourceLocation GAMEPLAY_HERO_OF_THE_VILLAGE_CARTOGRAPHER_GIFT = LootTables.register("gameplay/hero_of_the_village/cartographer_gift");
    public static final ResourceLocation GAMEPLAY_HERO_OF_THE_VILLAGE_CLERIC_GIFT = LootTables.register("gameplay/hero_of_the_village/cleric_gift");
    public static final ResourceLocation GAMEPLAY_HERO_OF_THE_VILLAGE_FARMER_GIFT = LootTables.register("gameplay/hero_of_the_village/farmer_gift");
    public static final ResourceLocation GAMEPLAY_HERO_OF_THE_VILLAGE_FISHERMAN_GIFT = LootTables.register("gameplay/hero_of_the_village/fisherman_gift");
    public static final ResourceLocation GAMEPLAY_HERO_OF_THE_VILLAGE_FLETCHER_GIFT = LootTables.register("gameplay/hero_of_the_village/fletcher_gift");
    public static final ResourceLocation GAMEPLAY_HERO_OF_THE_VILLAGE_LEATHERWORKER_GIFT = LootTables.register("gameplay/hero_of_the_village/leatherworker_gift");
    public static final ResourceLocation GAMEPLAY_HERO_OF_THE_VILLAGE_LIBRARIAN_GIFT = LootTables.register("gameplay/hero_of_the_village/librarian_gift");
    public static final ResourceLocation GAMEPLAY_HERO_OF_THE_VILLAGE_MASON_GIFT = LootTables.register("gameplay/hero_of_the_village/mason_gift");
    public static final ResourceLocation GAMEPLAY_HERO_OF_THE_VILLAGE_SHEPHERD_GIFT = LootTables.register("gameplay/hero_of_the_village/shepherd_gift");
    public static final ResourceLocation GAMEPLAY_HERO_OF_THE_VILLAGE_TOOLSMITH_GIFT = LootTables.register("gameplay/hero_of_the_village/toolsmith_gift");
    public static final ResourceLocation GAMEPLAY_HERO_OF_THE_VILLAGE_WEAPONSMITH_GIFT = LootTables.register("gameplay/hero_of_the_village/weaponsmith_gift");
    public static final ResourceLocation PIGLIN_BARTERING = LootTables.register("gameplay/piglin_bartering");

    private static ResourceLocation register(String string) {
        return LootTables.register(new ResourceLocation(string));
    }

    private static ResourceLocation register(ResourceLocation resourceLocation) {
        if (LOOT_TABLES.add(resourceLocation)) {
            return resourceLocation;
        }
        throw new IllegalArgumentException(resourceLocation + " is already a registered built-in loot table");
    }

    public static Set<ResourceLocation> getReadOnlyLootTables() {
        return READ_ONLY_LOOT_TABLES;
    }
}

