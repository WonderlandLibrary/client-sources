/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameters;
import net.minecraft.util.ResourceLocation;

public class LootParameterSets {
    private static final BiMap<ResourceLocation, LootParameterSet> REGISTRY = HashBiMap.create();
    public static final LootParameterSet EMPTY = LootParameterSets.register("empty", LootParameterSets::lambda$static$0);
    public static final LootParameterSet CHEST = LootParameterSets.register("chest", LootParameterSets::lambda$static$1);
    public static final LootParameterSet COMMAND = LootParameterSets.register("command", LootParameterSets::lambda$static$2);
    public static final LootParameterSet SELECTOR = LootParameterSets.register("selector", LootParameterSets::lambda$static$3);
    public static final LootParameterSet FISHING = LootParameterSets.register("fishing", LootParameterSets::lambda$static$4);
    public static final LootParameterSet ENTITY = LootParameterSets.register("entity", LootParameterSets::lambda$static$5);
    public static final LootParameterSet GIFT = LootParameterSets.register("gift", LootParameterSets::lambda$static$6);
    public static final LootParameterSet field_237453_h_ = LootParameterSets.register("barter", LootParameterSets::lambda$static$7);
    public static final LootParameterSet ADVANCEMENT = LootParameterSets.register("advancement_reward", LootParameterSets::lambda$static$8);
    public static final LootParameterSet field_237454_j_ = LootParameterSets.register("advancement_entity", LootParameterSets::lambda$static$9);
    public static final LootParameterSet GENERIC = LootParameterSets.register("generic", LootParameterSets::lambda$static$10);
    public static final LootParameterSet BLOCK = LootParameterSets.register("block", LootParameterSets::lambda$static$11);

    private static LootParameterSet register(String string, Consumer<LootParameterSet.Builder> consumer) {
        LootParameterSet.Builder builder = new LootParameterSet.Builder();
        consumer.accept(builder);
        LootParameterSet lootParameterSet = builder.build();
        ResourceLocation resourceLocation = new ResourceLocation(string);
        LootParameterSet lootParameterSet2 = REGISTRY.put(resourceLocation, lootParameterSet);
        if (lootParameterSet2 != null) {
            throw new IllegalStateException("Loot table parameter set " + resourceLocation + " is already registered");
        }
        return lootParameterSet;
    }

    @Nullable
    public static LootParameterSet getValue(ResourceLocation resourceLocation) {
        return (LootParameterSet)REGISTRY.get(resourceLocation);
    }

    @Nullable
    public static ResourceLocation getKey(LootParameterSet lootParameterSet) {
        return (ResourceLocation)REGISTRY.inverse().get(lootParameterSet);
    }

    private static void lambda$static$11(LootParameterSet.Builder builder) {
        builder.required(LootParameters.BLOCK_STATE).required(LootParameters.field_237457_g_).required(LootParameters.TOOL).optional(LootParameters.THIS_ENTITY).optional(LootParameters.BLOCK_ENTITY).optional(LootParameters.EXPLOSION_RADIUS);
    }

    private static void lambda$static$10(LootParameterSet.Builder builder) {
        builder.required(LootParameters.THIS_ENTITY).required(LootParameters.LAST_DAMAGE_PLAYER).required(LootParameters.DAMAGE_SOURCE).required(LootParameters.KILLER_ENTITY).required(LootParameters.DIRECT_KILLER_ENTITY).required(LootParameters.field_237457_g_).required(LootParameters.BLOCK_STATE).required(LootParameters.BLOCK_ENTITY).required(LootParameters.TOOL).required(LootParameters.EXPLOSION_RADIUS);
    }

    private static void lambda$static$9(LootParameterSet.Builder builder) {
        builder.required(LootParameters.THIS_ENTITY).required(LootParameters.field_237457_g_);
    }

    private static void lambda$static$8(LootParameterSet.Builder builder) {
        builder.required(LootParameters.THIS_ENTITY).required(LootParameters.field_237457_g_);
    }

    private static void lambda$static$7(LootParameterSet.Builder builder) {
        builder.required(LootParameters.THIS_ENTITY);
    }

    private static void lambda$static$6(LootParameterSet.Builder builder) {
        builder.required(LootParameters.field_237457_g_).required(LootParameters.THIS_ENTITY);
    }

    private static void lambda$static$5(LootParameterSet.Builder builder) {
        builder.required(LootParameters.THIS_ENTITY).required(LootParameters.field_237457_g_).required(LootParameters.DAMAGE_SOURCE).optional(LootParameters.KILLER_ENTITY).optional(LootParameters.DIRECT_KILLER_ENTITY).optional(LootParameters.LAST_DAMAGE_PLAYER);
    }

    private static void lambda$static$4(LootParameterSet.Builder builder) {
        builder.required(LootParameters.field_237457_g_).required(LootParameters.TOOL).optional(LootParameters.THIS_ENTITY);
    }

    private static void lambda$static$3(LootParameterSet.Builder builder) {
        builder.required(LootParameters.field_237457_g_).required(LootParameters.THIS_ENTITY);
    }

    private static void lambda$static$2(LootParameterSet.Builder builder) {
        builder.required(LootParameters.field_237457_g_).optional(LootParameters.THIS_ENTITY);
    }

    private static void lambda$static$1(LootParameterSet.Builder builder) {
        builder.required(LootParameters.field_237457_g_).optional(LootParameters.THIS_ENTITY);
    }

    private static void lambda$static$0(LootParameterSet.Builder builder) {
    }
}

