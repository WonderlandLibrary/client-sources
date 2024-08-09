/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.versions;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;

public class V2551
extends NamespacedSchema {
    public V2551(int n, Schema schema) {
        super(n, schema);
    }

    @Override
    public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map, Map<String, Supplier<TypeTemplate>> map2) {
        super.registerTypes(schema, map, map2);
        schema.registerType(false, TypeReferences.WORLD_GEN_SETTINGS, () -> V2551.lambda$registerTypes$5(schema));
    }

    private static TypeTemplate lambda$registerTypes$5(Schema schema) {
        return DSL.fields("dimensions", DSL.compoundList(DSL.constType(V2551.func_233457_a_()), DSL.fields("generator", DSL.taggedChoiceLazy("type", DSL.string(), ImmutableMap.of("minecraft:debug", DSL::remainder, "minecraft:flat", () -> V2551.lambda$registerTypes$0(schema), "minecraft:noise", () -> V2551.lambda$registerTypes$4(schema))))));
    }

    private static TypeTemplate lambda$registerTypes$4(Schema schema) {
        return DSL.optionalFields("biome_source", DSL.taggedChoiceLazy("type", DSL.string(), ImmutableMap.of("minecraft:fixed", () -> V2551.lambda$registerTypes$1(schema), "minecraft:multi_noise", () -> V2551.lambda$registerTypes$2(schema), "minecraft:checkerboard", () -> V2551.lambda$registerTypes$3(schema), "minecraft:vanilla_layered", DSL::remainder, "minecraft:the_end", DSL::remainder)), "settings", DSL.or(DSL.constType(DSL.string()), DSL.optionalFields("default_block", TypeReferences.BLOCK_NAME.in(schema), "default_fluid", TypeReferences.BLOCK_NAME.in(schema))));
    }

    private static TypeTemplate lambda$registerTypes$3(Schema schema) {
        return DSL.fields("biomes", DSL.list(TypeReferences.BIOME.in(schema)));
    }

    private static TypeTemplate lambda$registerTypes$2(Schema schema) {
        return DSL.list(DSL.fields("biome", TypeReferences.BIOME.in(schema)));
    }

    private static TypeTemplate lambda$registerTypes$1(Schema schema) {
        return DSL.fields("biome", TypeReferences.BIOME.in(schema));
    }

    private static TypeTemplate lambda$registerTypes$0(Schema schema) {
        return DSL.optionalFields("settings", DSL.optionalFields("biome", TypeReferences.BIOME.in(schema), "layers", DSL.list(DSL.optionalFields("block", TypeReferences.BLOCK_NAME.in(schema)))));
    }
}

