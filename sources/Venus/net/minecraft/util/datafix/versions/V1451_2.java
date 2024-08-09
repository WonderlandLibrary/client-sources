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

public class V1451_2
extends NamespacedSchema {
    public V1451_2(int n, Schema schema) {
        super(n, schema);
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
        Map<String, Supplier<TypeTemplate>> map = super.registerBlockEntities(schema);
        schema.register(map, "minecraft:piston", arg_0 -> V1451_2.lambda$registerBlockEntities$0(schema, arg_0));
        return map;
    }

    private static TypeTemplate lambda$registerBlockEntities$0(Schema schema, String string) {
        return DSL.optionalFields("blockState", TypeReferences.BLOCK_STATE.in(schema));
    }
}

