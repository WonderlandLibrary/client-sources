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
import net.minecraft.util.datafix.versions.V0100;

public class V1470
extends NamespacedSchema {
    public V1470(int n, Schema schema) {
        super(n, schema);
    }

    protected static void registerEntity(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> V1470.lambda$registerEntity$0(schema));
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        Map<String, Supplier<TypeTemplate>> map = super.registerEntities(schema);
        V1470.registerEntity(schema, map, "minecraft:turtle");
        V1470.registerEntity(schema, map, "minecraft:cod_mob");
        V1470.registerEntity(schema, map, "minecraft:tropical_fish");
        V1470.registerEntity(schema, map, "minecraft:salmon_mob");
        V1470.registerEntity(schema, map, "minecraft:puffer_fish");
        V1470.registerEntity(schema, map, "minecraft:phantom");
        V1470.registerEntity(schema, map, "minecraft:dolphin");
        V1470.registerEntity(schema, map, "minecraft:drowned");
        schema.register(map, "minecraft:trident", arg_0 -> V1470.lambda$registerEntities$1(schema, arg_0));
        return map;
    }

    private static TypeTemplate lambda$registerEntities$1(Schema schema, String string) {
        return DSL.optionalFields("inBlockState", TypeReferences.BLOCK_STATE.in(schema));
    }

    private static TypeTemplate lambda$registerEntity$0(Schema schema) {
        return V0100.equipment(schema);
    }
}

