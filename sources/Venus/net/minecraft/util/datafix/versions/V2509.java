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

public class V2509
extends NamespacedSchema {
    public V2509(int n, Schema schema) {
        super(n, schema);
    }

    protected static void func_233467_a_(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> V2509.lambda$func_233467_a_$0(schema));
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        Map<String, Supplier<TypeTemplate>> map = super.registerEntities(schema);
        map.remove("minecraft:zombie_pigman");
        V2509.func_233467_a_(schema, map, "minecraft:zombified_piglin");
        return map;
    }

    private static TypeTemplate lambda$func_233467_a_$0(Schema schema) {
        return V0100.equipment(schema);
    }
}

