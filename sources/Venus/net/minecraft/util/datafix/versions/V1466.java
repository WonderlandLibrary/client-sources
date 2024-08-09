/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.versions;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;

public class V1466
extends NamespacedSchema {
    public V1466(int n, Schema schema) {
        super(n, schema);
    }

    @Override
    public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map, Map<String, Supplier<TypeTemplate>> map2) {
        super.registerTypes(schema, map, map2);
        schema.registerType(false, TypeReferences.CHUNK, () -> V1466.lambda$registerTypes$0(schema));
        schema.registerType(false, TypeReferences.STRUCTURE_FEATURE, () -> V1466.lambda$registerTypes$1(schema));
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
        Map<String, Supplier<TypeTemplate>> map = super.registerBlockEntities(schema);
        map.put("DUMMY", DSL::remainder);
        return map;
    }

    private static TypeTemplate lambda$registerTypes$1(Schema schema) {
        return DSL.optionalFields("Children", DSL.list(DSL.optionalFields("CA", TypeReferences.BLOCK_STATE.in(schema), "CB", TypeReferences.BLOCK_STATE.in(schema), "CC", TypeReferences.BLOCK_STATE.in(schema), "CD", TypeReferences.BLOCK_STATE.in(schema))), "biome", TypeReferences.BIOME.in(schema));
    }

    private static TypeTemplate lambda$registerTypes$0(Schema schema) {
        return DSL.fields("Level", DSL.optionalFields("Entities", DSL.list(TypeReferences.ENTITY_TYPE.in(schema)), "TileEntities", DSL.list(TypeReferences.BLOCK_ENTITY.in(schema)), "TileTicks", DSL.list(DSL.fields("i", TypeReferences.BLOCK_NAME.in(schema))), "Sections", DSL.list(DSL.optionalFields("Palette", DSL.list(TypeReferences.BLOCK_STATE.in(schema)))), "Structures", DSL.optionalFields("Starts", DSL.compoundList(TypeReferences.STRUCTURE_FEATURE.in(schema)))));
    }
}

