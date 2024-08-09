/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.versions;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.versions.V0100;

public class V0703
extends Schema {
    public V0703(int n, Schema schema) {
        super(n, schema);
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        Map<String, Supplier<TypeTemplate>> map = super.registerEntities(schema);
        map.remove("EntityHorse");
        schema.register(map, "Horse", () -> V0703.lambda$registerEntities$0(schema));
        schema.register(map, "Donkey", () -> V0703.lambda$registerEntities$1(schema));
        schema.register(map, "Mule", () -> V0703.lambda$registerEntities$2(schema));
        schema.register(map, "ZombieHorse", () -> V0703.lambda$registerEntities$3(schema));
        schema.register(map, "SkeletonHorse", () -> V0703.lambda$registerEntities$4(schema));
        return map;
    }

    private static TypeTemplate lambda$registerEntities$4(Schema schema) {
        return DSL.optionalFields("SaddleItem", TypeReferences.ITEM_STACK.in(schema), V0100.equipment(schema));
    }

    private static TypeTemplate lambda$registerEntities$3(Schema schema) {
        return DSL.optionalFields("SaddleItem", TypeReferences.ITEM_STACK.in(schema), V0100.equipment(schema));
    }

    private static TypeTemplate lambda$registerEntities$2(Schema schema) {
        return DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(schema)), "SaddleItem", TypeReferences.ITEM_STACK.in(schema), V0100.equipment(schema));
    }

    private static TypeTemplate lambda$registerEntities$1(Schema schema) {
        return DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(schema)), "SaddleItem", TypeReferences.ITEM_STACK.in(schema), V0100.equipment(schema));
    }

    private static TypeTemplate lambda$registerEntities$0(Schema schema) {
        return DSL.optionalFields("ArmorItem", TypeReferences.ITEM_STACK.in(schema), "SaddleItem", TypeReferences.ITEM_STACK.in(schema), V0100.equipment(schema));
    }
}

