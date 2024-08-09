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

public class V1906
extends NamespacedSchema {
    public V1906(int n, Schema schema) {
        super(n, schema);
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
        Map<String, Supplier<TypeTemplate>> map = super.registerBlockEntities(schema);
        V1906.func_219880_a(schema, map, "minecraft:barrel");
        V1906.func_219880_a(schema, map, "minecraft:smoker");
        V1906.func_219880_a(schema, map, "minecraft:blast_furnace");
        schema.register(map, "minecraft:lectern", arg_0 -> V1906.lambda$registerBlockEntities$0(schema, arg_0));
        schema.registerSimple(map, "minecraft:bell");
        return map;
    }

    protected static void func_219880_a(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> V1906.lambda$func_219880_a$1(schema));
    }

    private static TypeTemplate lambda$func_219880_a$1(Schema schema) {
        return DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(schema)));
    }

    private static TypeTemplate lambda$registerBlockEntities$0(Schema schema, String string) {
        return DSL.optionalFields("Book", TypeReferences.ITEM_STACK.in(schema));
    }
}

