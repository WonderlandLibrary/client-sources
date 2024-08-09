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

public class V2502
extends NamespacedSchema {
    public V2502(int n, Schema schema) {
        super(n, schema);
    }

    protected static void func_233463_a_(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> V2502.lambda$func_233463_a_$0(schema));
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        Map<String, Supplier<TypeTemplate>> map = super.registerEntities(schema);
        V2502.func_233463_a_(schema, map, "minecraft:hoglin");
        return map;
    }

    private static TypeTemplate lambda$func_233463_a_$0(Schema schema) {
        return V0100.equipment(schema);
    }
}

