/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.conditions;

import java.util.function.Predicate;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootTypesManager;
import net.minecraft.loot.conditions.Alternative;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.DamageSourceProperties;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.conditions.EntityHasScore;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.Inverted;
import net.minecraft.loot.conditions.KilledByPlayer;
import net.minecraft.loot.conditions.LocationCheck;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.loot.conditions.RandomChanceWithLooting;
import net.minecraft.loot.conditions.Reference;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.loot.conditions.TableBonus;
import net.minecraft.loot.conditions.TimeCheck;
import net.minecraft.loot.conditions.WeatherCheck;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class LootConditionManager {
    public static final LootConditionType INVERTED = LootConditionManager.register("inverted", new Inverted.Serializer());
    public static final LootConditionType ALTERNATIVE = LootConditionManager.register("alternative", new Alternative.Serializer());
    public static final LootConditionType RANDOM_CHANCE = LootConditionManager.register("random_chance", new RandomChance.Serializer());
    public static final LootConditionType RANDOM_CHANCE_WITH_LOOTING = LootConditionManager.register("random_chance_with_looting", new RandomChanceWithLooting.Serializer());
    public static final LootConditionType ENTITY_PROPERTIES = LootConditionManager.register("entity_properties", new EntityHasProperty.Serializer());
    public static final LootConditionType KILLED_BY_PLAYER = LootConditionManager.register("killed_by_player", new KilledByPlayer.Serializer());
    public static final LootConditionType ENTITY_SCORES = LootConditionManager.register("entity_scores", new EntityHasScore.Serializer());
    public static final LootConditionType BLOCK_STATE_PROPERTY = LootConditionManager.register("block_state_property", new BlockStateProperty.Serializer());
    public static final LootConditionType MATCH_TOOL = LootConditionManager.register("match_tool", new MatchTool.Serializer());
    public static final LootConditionType TABLE_BONUS = LootConditionManager.register("table_bonus", new TableBonus.Serializer());
    public static final LootConditionType SURVIVES_EXPLOSION = LootConditionManager.register("survives_explosion", new SurvivesExplosion.Serializer());
    public static final LootConditionType DAMAGE_SOURCE_PROPERTIES = LootConditionManager.register("damage_source_properties", new DamageSourceProperties.Serializer());
    public static final LootConditionType LOCATION_CHECK = LootConditionManager.register("location_check", new LocationCheck.Serializer());
    public static final LootConditionType WEATHER_CHECK = LootConditionManager.register("weather_check", new WeatherCheck.Serializer());
    public static final LootConditionType REFERENCE = LootConditionManager.register("reference", new Reference.Serializer());
    public static final LootConditionType TIME_CHECK = LootConditionManager.register("time_check", new TimeCheck.Serializer());

    private static LootConditionType register(String string, ILootSerializer<? extends ILootCondition> iLootSerializer) {
        return Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation(string), new LootConditionType(iLootSerializer));
    }

    public static Object func_237474_a_() {
        return LootTypesManager.getLootTypeRegistryWrapper(Registry.LOOT_CONDITION_TYPE, "condition", "condition", ILootCondition::func_230419_b_).getSerializer();
    }

    public static <T> Predicate<T> and(Predicate<T>[] predicateArray) {
        switch (predicateArray.length) {
            case 0: {
                return LootConditionManager::lambda$and$0;
            }
            case 1: {
                return predicateArray[0];
            }
            case 2: {
                return predicateArray[0].and(predicateArray[5]);
            }
        }
        return arg_0 -> LootConditionManager.lambda$and$1(predicateArray, arg_0);
    }

    public static <T> Predicate<T> or(Predicate<T>[] predicateArray) {
        switch (predicateArray.length) {
            case 0: {
                return LootConditionManager::lambda$or$2;
            }
            case 1: {
                return predicateArray[0];
            }
            case 2: {
                return predicateArray[0].or(predicateArray[5]);
            }
        }
        return arg_0 -> LootConditionManager.lambda$or$3(predicateArray, arg_0);
    }

    private static boolean lambda$or$3(Predicate[] predicateArray, Object object) {
        for (Predicate predicate : predicateArray) {
            if (!predicate.test(object)) continue;
            return false;
        }
        return true;
    }

    private static boolean lambda$or$2(Object object) {
        return true;
    }

    private static boolean lambda$and$1(Predicate[] predicateArray, Object object) {
        for (Predicate predicate : predicateArray) {
            if (predicate.test(object)) continue;
            return true;
        }
        return false;
    }

    private static boolean lambda$and$0(Object object) {
        return false;
    }
}

