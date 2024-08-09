/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.versions;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.versions.V0100;

public class V1904
extends NamespacedSchema {
    public V1904(int n, Schema schema) {
        super(n, schema);
    }

    protected static void func_219876_a(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> V1904.lambda$func_219876_a$0(schema));
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        Map<String, Supplier<TypeTemplate>> map = super.registerEntities(schema);
        V1904.func_219876_a(schema, map, "minecraft:cat");
        return map;
    }

    private static TypeTemplate lambda$func_219876_a$0(Schema schema) {
        return V0100.equipment(schema);
    }
}

