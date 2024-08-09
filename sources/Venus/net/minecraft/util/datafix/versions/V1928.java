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

public class V1928
extends NamespacedSchema {
    public V1928(int n, Schema schema) {
        super(n, schema);
    }

    protected static TypeTemplate func_219884_a(Schema schema) {
        return DSL.optionalFields("ArmorItems", DSL.list(TypeReferences.ITEM_STACK.in(schema)), "HandItems", DSL.list(TypeReferences.ITEM_STACK.in(schema)));
    }

    protected static void func_219883_a(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> V1928.lambda$func_219883_a$0(schema));
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        Map<String, Supplier<TypeTemplate>> map = super.registerEntities(schema);
        map.remove("minecraft:illager_beast");
        V1928.func_219883_a(schema, map, "minecraft:ravager");
        return map;
    }

    private static TypeTemplate lambda$func_219883_a$0(Schema schema) {
        return V1928.func_219884_a(schema);
    }
}

