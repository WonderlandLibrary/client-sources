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
import net.minecraft.util.datafix.versions.V0100;

public class V1451_3
extends NamespacedSchema {
    public V1451_3(int n, Schema schema) {
        super(n, schema);
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        Map<String, Supplier<TypeTemplate>> map = super.registerEntities(schema);
        schema.registerSimple(map, "minecraft:egg");
        schema.registerSimple(map, "minecraft:ender_pearl");
        schema.registerSimple(map, "minecraft:fireball");
        schema.register(map, "minecraft:potion", arg_0 -> V1451_3.lambda$registerEntities$0(schema, arg_0));
        schema.registerSimple(map, "minecraft:small_fireball");
        schema.registerSimple(map, "minecraft:snowball");
        schema.registerSimple(map, "minecraft:wither_skull");
        schema.registerSimple(map, "minecraft:xp_bottle");
        schema.register(map, "minecraft:arrow", () -> V1451_3.lambda$registerEntities$1(schema));
        schema.register(map, "minecraft:enderman", () -> V1451_3.lambda$registerEntities$2(schema));
        schema.register(map, "minecraft:falling_block", () -> V1451_3.lambda$registerEntities$3(schema));
        schema.register(map, "minecraft:spectral_arrow", () -> V1451_3.lambda$registerEntities$4(schema));
        schema.register(map, "minecraft:chest_minecart", () -> V1451_3.lambda$registerEntities$5(schema));
        schema.register(map, "minecraft:commandblock_minecart", () -> V1451_3.lambda$registerEntities$6(schema));
        schema.register(map, "minecraft:furnace_minecart", () -> V1451_3.lambda$registerEntities$7(schema));
        schema.register(map, "minecraft:hopper_minecart", () -> V1451_3.lambda$registerEntities$8(schema));
        schema.register(map, "minecraft:minecart", () -> V1451_3.lambda$registerEntities$9(schema));
        schema.register(map, "minecraft:spawner_minecart", () -> V1451_3.lambda$registerEntities$10(schema));
        schema.register(map, "minecraft:tnt_minecart", () -> V1451_3.lambda$registerEntities$11(schema));
        return map;
    }

    private static TypeTemplate lambda$registerEntities$11(Schema schema) {
        return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$10(Schema schema) {
        return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(schema), TypeReferences.UNTAGGED_SPAWNER.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$9(Schema schema) {
        return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$8(Schema schema) {
        return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(schema), "Items", DSL.list(TypeReferences.ITEM_STACK.in(schema)));
    }

    private static TypeTemplate lambda$registerEntities$7(Schema schema) {
        return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$6(Schema schema) {
        return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$5(Schema schema) {
        return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(schema), "Items", DSL.list(TypeReferences.ITEM_STACK.in(schema)));
    }

    private static TypeTemplate lambda$registerEntities$4(Schema schema) {
        return DSL.optionalFields("inBlockState", TypeReferences.BLOCK_STATE.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$3(Schema schema) {
        return DSL.optionalFields("BlockState", TypeReferences.BLOCK_STATE.in(schema), "TileEntityData", TypeReferences.BLOCK_ENTITY.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$2(Schema schema) {
        return DSL.optionalFields("carriedBlockState", TypeReferences.BLOCK_STATE.in(schema), V0100.equipment(schema));
    }

    private static TypeTemplate lambda$registerEntities$1(Schema schema) {
        return DSL.optionalFields("inBlockState", TypeReferences.BLOCK_STATE.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$0(Schema schema, String string) {
        return DSL.optionalFields("Potion", TypeReferences.ITEM_STACK.in(schema));
    }
}

