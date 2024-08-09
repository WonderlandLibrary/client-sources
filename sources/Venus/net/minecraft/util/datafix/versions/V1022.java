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

public class V1022
extends Schema {
    public V1022(int n, Schema schema) {
        super(n, schema);
    }

    @Override
    public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map, Map<String, Supplier<TypeTemplate>> map2) {
        super.registerTypes(schema, map, map2);
        schema.registerType(false, TypeReferences.RECIPE, V1022::lambda$registerTypes$0);
        schema.registerType(false, TypeReferences.PLAYER, () -> V1022.lambda$registerTypes$1(schema));
        schema.registerType(false, TypeReferences.HOTBAR, () -> V1022.lambda$registerTypes$2(schema));
    }

    private static TypeTemplate lambda$registerTypes$2(Schema schema) {
        return DSL.compoundList(DSL.list(TypeReferences.ITEM_STACK.in(schema)));
    }

    private static TypeTemplate lambda$registerTypes$1(Schema schema) {
        return DSL.optionalFields("RootVehicle", DSL.optionalFields("Entity", TypeReferences.ENTITY_TYPE.in(schema)), "Inventory", DSL.list(TypeReferences.ITEM_STACK.in(schema)), "EnderItems", DSL.list(TypeReferences.ITEM_STACK.in(schema)), DSL.optionalFields("ShoulderEntityLeft", TypeReferences.ENTITY_TYPE.in(schema), "ShoulderEntityRight", TypeReferences.ENTITY_TYPE.in(schema), "recipeBook", DSL.optionalFields("recipes", DSL.list(TypeReferences.RECIPE.in(schema)), "toBeDisplayed", DSL.list(TypeReferences.RECIPE.in(schema)))));
    }

    private static TypeTemplate lambda$registerTypes$0() {
        return DSL.constType(NamespacedSchema.func_233457_a_());
    }
}

