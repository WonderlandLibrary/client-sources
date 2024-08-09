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

public class V1800
extends NamespacedSchema {
    public V1800(int n, Schema schema) {
        super(n, schema);
    }

    protected static void func_219873_a(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> V1800.lambda$func_219873_a$0(schema));
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        Map<String, Supplier<TypeTemplate>> map = super.registerEntities(schema);
        V1800.func_219873_a(schema, map, "minecraft:panda");
        schema.register(map, "minecraft:pillager", arg_0 -> V1800.lambda$registerEntities$1(schema, arg_0));
        return map;
    }

    private static TypeTemplate lambda$registerEntities$1(Schema schema, String string) {
        return DSL.optionalFields("Inventory", DSL.list(TypeReferences.ITEM_STACK.in(schema)), V0100.equipment(schema));
    }

    private static TypeTemplate lambda$func_219873_a$0(Schema schema) {
        return V0100.equipment(schema);
    }
}

