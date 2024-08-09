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

public class V0135
extends Schema {
    public V0135(int n, Schema schema) {
        super(n, schema);
    }

    @Override
    public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map, Map<String, Supplier<TypeTemplate>> map2) {
        super.registerTypes(schema, map, map2);
        schema.registerType(false, TypeReferences.PLAYER, () -> V0135.lambda$registerTypes$0(schema));
        schema.registerType(true, TypeReferences.ENTITY_TYPE, () -> V0135.lambda$registerTypes$1(schema));
    }

    private static TypeTemplate lambda$registerTypes$1(Schema schema) {
        return DSL.optionalFields("Passengers", DSL.list(TypeReferences.ENTITY_TYPE.in(schema)), TypeReferences.ENTITY.in(schema));
    }

    private static TypeTemplate lambda$registerTypes$0(Schema schema) {
        return DSL.optionalFields("RootVehicle", DSL.optionalFields("Entity", TypeReferences.ENTITY_TYPE.in(schema)), "Inventory", DSL.list(TypeReferences.ITEM_STACK.in(schema)), "EnderItems", DSL.list(TypeReferences.ITEM_STACK.in(schema)));
    }
}

