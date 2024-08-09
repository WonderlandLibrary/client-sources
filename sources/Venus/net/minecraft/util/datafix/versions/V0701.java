/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.versions;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.versions.V0100;

public class V0701
extends Schema {
    public V0701(int n, Schema schema) {
        super(n, schema);
    }

    protected static void registerEntity(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> V0701.lambda$registerEntity$0(schema));
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        Map<String, Supplier<TypeTemplate>> map = super.registerEntities(schema);
        V0701.registerEntity(schema, map, "WitherSkeleton");
        V0701.registerEntity(schema, map, "Stray");
        return map;
    }

    private static TypeTemplate lambda$registerEntity$0(Schema schema) {
        return V0100.equipment(schema);
    }
}

