/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.functions;

import java.util.function.BiFunction;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootTypesManager;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.CopyBlockState;
import net.minecraft.loot.functions.CopyName;
import net.minecraft.loot.functions.CopyNbt;
import net.minecraft.loot.functions.EnchantRandomly;
import net.minecraft.loot.functions.EnchantWithLevels;
import net.minecraft.loot.functions.ExplorationMap;
import net.minecraft.loot.functions.ExplosionDecay;
import net.minecraft.loot.functions.FillPlayerHead;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.LimitCount;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetAttributes;
import net.minecraft.loot.functions.SetContents;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.loot.functions.SetDamage;
import net.minecraft.loot.functions.SetLootTable;
import net.minecraft.loot.functions.SetLore;
import net.minecraft.loot.functions.SetNBT;
import net.minecraft.loot.functions.SetName;
import net.minecraft.loot.functions.SetStewEffect;
import net.minecraft.loot.functions.Smelt;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class LootFunctionManager {
    public static final BiFunction<ItemStack, LootContext, ItemStack> IDENTITY = LootFunctionManager::lambda$static$0;
    public static final LootFunctionType SET_COUNT = LootFunctionManager.func_237451_a_("set_count", new SetCount.Serializer());
    public static final LootFunctionType ENCHANT_WITH_LEVELS = LootFunctionManager.func_237451_a_("enchant_with_levels", new EnchantWithLevels.Serializer());
    public static final LootFunctionType ENCHANT_RANDOMLY = LootFunctionManager.func_237451_a_("enchant_randomly", new EnchantRandomly.Serializer());
    public static final LootFunctionType SET_NBT = LootFunctionManager.func_237451_a_("set_nbt", new SetNBT.Serializer());
    public static final LootFunctionType FURNACE_SMELT = LootFunctionManager.func_237451_a_("furnace_smelt", new Smelt.Serializer());
    public static final LootFunctionType LOOTING_ENCHANT = LootFunctionManager.func_237451_a_("looting_enchant", new LootingEnchantBonus.Serializer());
    public static final LootFunctionType SET_DAMAGE = LootFunctionManager.func_237451_a_("set_damage", new SetDamage.Serializer());
    public static final LootFunctionType SET_ATTRIBUTES = LootFunctionManager.func_237451_a_("set_attributes", new SetAttributes.Serializer());
    public static final LootFunctionType SET_NAME = LootFunctionManager.func_237451_a_("set_name", new SetName.Serializer());
    public static final LootFunctionType EXPLORATION_MAP = LootFunctionManager.func_237451_a_("exploration_map", new ExplorationMap.Serializer());
    public static final LootFunctionType SET_STEW_EFFECT = LootFunctionManager.func_237451_a_("set_stew_effect", new SetStewEffect.Serializer());
    public static final LootFunctionType COPY_NAME = LootFunctionManager.func_237451_a_("copy_name", new CopyName.Serializer());
    public static final LootFunctionType SET_CONTENTS = LootFunctionManager.func_237451_a_("set_contents", new SetContents.Serializer());
    public static final LootFunctionType LIMIT_COUNT = LootFunctionManager.func_237451_a_("limit_count", new LimitCount.Serializer());
    public static final LootFunctionType APPLY_BONUS = LootFunctionManager.func_237451_a_("apply_bonus", new ApplyBonus.Serializer());
    public static final LootFunctionType SET_LOOT_TABLE = LootFunctionManager.func_237451_a_("set_loot_table", new SetLootTable.Serializer());
    public static final LootFunctionType EXPLOSION_DECAY = LootFunctionManager.func_237451_a_("explosion_decay", new ExplosionDecay.Serializer());
    public static final LootFunctionType SET_LORE = LootFunctionManager.func_237451_a_("set_lore", new SetLore.Serializer());
    public static final LootFunctionType FILL_PLAYER_HEAD = LootFunctionManager.func_237451_a_("fill_player_head", new FillPlayerHead.Serializer());
    public static final LootFunctionType COPY_NBT = LootFunctionManager.func_237451_a_("copy_nbt", new CopyNbt.Serializer());
    public static final LootFunctionType COPY_STATE = LootFunctionManager.func_237451_a_("copy_state", new CopyBlockState.Serializer());

    private static LootFunctionType func_237451_a_(String string, ILootSerializer<? extends ILootFunction> iLootSerializer) {
        return Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation(string), new LootFunctionType(iLootSerializer));
    }

    public static Object func_237450_a_() {
        return LootTypesManager.getLootTypeRegistryWrapper(Registry.LOOT_FUNCTION_TYPE, "function", "function", ILootFunction::getFunctionType).getSerializer();
    }

    public static BiFunction<ItemStack, LootContext, ItemStack> combine(BiFunction<ItemStack, LootContext, ItemStack>[] biFunctionArray) {
        switch (biFunctionArray.length) {
            case 0: {
                return IDENTITY;
            }
            case 1: {
                return biFunctionArray[0];
            }
            case 2: {
                BiFunction<ItemStack, LootContext, ItemStack> biFunction = biFunctionArray[0];
                BiFunction<ItemStack, LootContext, ItemStack> biFunction2 = biFunctionArray[5];
                return (arg_0, arg_1) -> LootFunctionManager.lambda$combine$1(biFunction2, biFunction, arg_0, arg_1);
            }
        }
        return (arg_0, arg_1) -> LootFunctionManager.lambda$combine$2(biFunctionArray, arg_0, arg_1);
    }

    private static ItemStack lambda$combine$2(BiFunction[] biFunctionArray, ItemStack itemStack, LootContext lootContext) {
        for (BiFunction biFunction : biFunctionArray) {
            itemStack = (ItemStack)biFunction.apply(itemStack, lootContext);
        }
        return itemStack;
    }

    private static ItemStack lambda$combine$1(BiFunction biFunction, BiFunction biFunction2, ItemStack itemStack, LootContext lootContext) {
        return (ItemStack)biFunction.apply((ItemStack)biFunction2.apply(itemStack, lootContext), lootContext);
    }

    private static ItemStack lambda$static$0(ItemStack itemStack, LootContext lootContext) {
        return itemStack;
    }
}

