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

public class V0808
extends NamespacedSchema {
    public V0808(int n, Schema schema) {
        super(n, schema);
    }

    protected static void registerInventory(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> V0808.lambda$registerInventory$0(schema));
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
        Map<String, Supplier<TypeTemplate>> map = super.registerBlockEntities(schema);
        V0808.registerInventory(schema, map, "minecraft:shulker_box");
        return map;
    }

    private static TypeTemplate lambda$registerInventory$0(Schema schema) {
        return DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(schema)));
    }
}

