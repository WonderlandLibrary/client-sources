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

public class V2100
extends NamespacedSchema {
    public V2100(int n, Schema schema) {
        super(n, schema);
    }

    protected static void func_226217_a_(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> V2100.lambda$func_226217_a_$0(schema));
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        Map<String, Supplier<TypeTemplate>> map = super.registerEntities(schema);
        V2100.func_226217_a_(schema, map, "minecraft:bee");
        V2100.func_226217_a_(schema, map, "minecraft:bee_stinger");
        return map;
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
        Map<String, Supplier<TypeTemplate>> map = super.registerBlockEntities(schema);
        schema.register(map, "minecraft:beehive", () -> V2100.lambda$registerBlockEntities$1(schema));
        return map;
    }

    private static TypeTemplate lambda$registerBlockEntities$1(Schema schema) {
        return DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(schema)), "Bees", DSL.list(DSL.optionalFields("EntityData", TypeReferences.ENTITY_TYPE.in(schema))));
    }

    private static TypeTemplate lambda$func_226217_a_$0(Schema schema) {
        return V0100.equipment(schema);
    }
}

