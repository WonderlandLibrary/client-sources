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

public class V2501
extends NamespacedSchema {
    public V2501(int n, Schema schema) {
        super(n, schema);
    }

    private static void func_233461_a_(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> V2501.lambda$func_233461_a_$0(schema));
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
        Map<String, Supplier<TypeTemplate>> map = super.registerBlockEntities(schema);
        V2501.func_233461_a_(schema, map, "minecraft:furnace");
        V2501.func_233461_a_(schema, map, "minecraft:smoker");
        V2501.func_233461_a_(schema, map, "minecraft:blast_furnace");
        return map;
    }

    private static TypeTemplate lambda$func_233461_a_$0(Schema schema) {
        return DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(schema)), "RecipesUsed", DSL.compoundList(TypeReferences.RECIPE.in(schema), DSL.constType(DSL.intType())));
    }
}

