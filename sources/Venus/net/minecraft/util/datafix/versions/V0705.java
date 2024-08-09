/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.versions;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.Hook;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.versions.V0099;
import net.minecraft.util.datafix.versions.V0100;
import net.minecraft.util.datafix.versions.V0704;

public class V0705
extends NamespacedSchema {
    protected static final Hook.HookFunction field_206597_b = new Hook.HookFunction(){

        @Override
        public <T> T apply(DynamicOps<T> dynamicOps, T t) {
            return V0099.func_209869_a(new Dynamic<T>(dynamicOps, t), V0704.field_206647_b, "minecraft:armor_stand");
        }
    };

    public V0705(int n, Schema schema) {
        super(n, schema);
    }

    protected static void registerEntity(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> V0705.lambda$registerEntity$0(schema));
    }

    protected static void registerThrowableProjectile(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> V0705.lambda$registerThrowableProjectile$1(schema));
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        HashMap<String, Supplier<TypeTemplate>> hashMap = Maps.newHashMap();
        schema.registerSimple(hashMap, "minecraft:area_effect_cloud");
        V0705.registerEntity(schema, hashMap, "minecraft:armor_stand");
        schema.register(hashMap, "minecraft:arrow", arg_0 -> V0705.lambda$registerEntities$2(schema, arg_0));
        V0705.registerEntity(schema, hashMap, "minecraft:bat");
        V0705.registerEntity(schema, hashMap, "minecraft:blaze");
        schema.registerSimple(hashMap, "minecraft:boat");
        V0705.registerEntity(schema, hashMap, "minecraft:cave_spider");
        schema.register(hashMap, "minecraft:chest_minecart", arg_0 -> V0705.lambda$registerEntities$3(schema, arg_0));
        V0705.registerEntity(schema, hashMap, "minecraft:chicken");
        schema.register(hashMap, "minecraft:commandblock_minecart", arg_0 -> V0705.lambda$registerEntities$4(schema, arg_0));
        V0705.registerEntity(schema, hashMap, "minecraft:cow");
        V0705.registerEntity(schema, hashMap, "minecraft:creeper");
        schema.register(hashMap, "minecraft:donkey", arg_0 -> V0705.lambda$registerEntities$5(schema, arg_0));
        schema.registerSimple(hashMap, "minecraft:dragon_fireball");
        V0705.registerThrowableProjectile(schema, hashMap, "minecraft:egg");
        V0705.registerEntity(schema, hashMap, "minecraft:elder_guardian");
        schema.registerSimple(hashMap, "minecraft:ender_crystal");
        V0705.registerEntity(schema, hashMap, "minecraft:ender_dragon");
        schema.register(hashMap, "minecraft:enderman", arg_0 -> V0705.lambda$registerEntities$6(schema, arg_0));
        V0705.registerEntity(schema, hashMap, "minecraft:endermite");
        V0705.registerThrowableProjectile(schema, hashMap, "minecraft:ender_pearl");
        schema.registerSimple(hashMap, "minecraft:eye_of_ender_signal");
        schema.register(hashMap, "minecraft:falling_block", arg_0 -> V0705.lambda$registerEntities$7(schema, arg_0));
        V0705.registerThrowableProjectile(schema, hashMap, "minecraft:fireball");
        schema.register(hashMap, "minecraft:fireworks_rocket", arg_0 -> V0705.lambda$registerEntities$8(schema, arg_0));
        schema.register(hashMap, "minecraft:furnace_minecart", arg_0 -> V0705.lambda$registerEntities$9(schema, arg_0));
        V0705.registerEntity(schema, hashMap, "minecraft:ghast");
        V0705.registerEntity(schema, hashMap, "minecraft:giant");
        V0705.registerEntity(schema, hashMap, "minecraft:guardian");
        schema.register(hashMap, "minecraft:hopper_minecart", arg_0 -> V0705.lambda$registerEntities$10(schema, arg_0));
        schema.register(hashMap, "minecraft:horse", arg_0 -> V0705.lambda$registerEntities$11(schema, arg_0));
        V0705.registerEntity(schema, hashMap, "minecraft:husk");
        schema.register(hashMap, "minecraft:item", arg_0 -> V0705.lambda$registerEntities$12(schema, arg_0));
        schema.register(hashMap, "minecraft:item_frame", arg_0 -> V0705.lambda$registerEntities$13(schema, arg_0));
        schema.registerSimple(hashMap, "minecraft:leash_knot");
        V0705.registerEntity(schema, hashMap, "minecraft:magma_cube");
        schema.register(hashMap, "minecraft:minecart", arg_0 -> V0705.lambda$registerEntities$14(schema, arg_0));
        V0705.registerEntity(schema, hashMap, "minecraft:mooshroom");
        schema.register(hashMap, "minecraft:mule", arg_0 -> V0705.lambda$registerEntities$15(schema, arg_0));
        V0705.registerEntity(schema, hashMap, "minecraft:ocelot");
        schema.registerSimple(hashMap, "minecraft:painting");
        schema.registerSimple(hashMap, "minecraft:parrot");
        V0705.registerEntity(schema, hashMap, "minecraft:pig");
        V0705.registerEntity(schema, hashMap, "minecraft:polar_bear");
        schema.register(hashMap, "minecraft:potion", arg_0 -> V0705.lambda$registerEntities$16(schema, arg_0));
        V0705.registerEntity(schema, hashMap, "minecraft:rabbit");
        V0705.registerEntity(schema, hashMap, "minecraft:sheep");
        V0705.registerEntity(schema, hashMap, "minecraft:shulker");
        schema.registerSimple(hashMap, "minecraft:shulker_bullet");
        V0705.registerEntity(schema, hashMap, "minecraft:silverfish");
        V0705.registerEntity(schema, hashMap, "minecraft:skeleton");
        schema.register(hashMap, "minecraft:skeleton_horse", arg_0 -> V0705.lambda$registerEntities$17(schema, arg_0));
        V0705.registerEntity(schema, hashMap, "minecraft:slime");
        V0705.registerThrowableProjectile(schema, hashMap, "minecraft:small_fireball");
        V0705.registerThrowableProjectile(schema, hashMap, "minecraft:snowball");
        V0705.registerEntity(schema, hashMap, "minecraft:snowman");
        schema.register(hashMap, "minecraft:spawner_minecart", arg_0 -> V0705.lambda$registerEntities$18(schema, arg_0));
        schema.register(hashMap, "minecraft:spectral_arrow", arg_0 -> V0705.lambda$registerEntities$19(schema, arg_0));
        V0705.registerEntity(schema, hashMap, "minecraft:spider");
        V0705.registerEntity(schema, hashMap, "minecraft:squid");
        V0705.registerEntity(schema, hashMap, "minecraft:stray");
        schema.registerSimple(hashMap, "minecraft:tnt");
        schema.register(hashMap, "minecraft:tnt_minecart", arg_0 -> V0705.lambda$registerEntities$20(schema, arg_0));
        schema.register(hashMap, "minecraft:villager", arg_0 -> V0705.lambda$registerEntities$21(schema, arg_0));
        V0705.registerEntity(schema, hashMap, "minecraft:villager_golem");
        V0705.registerEntity(schema, hashMap, "minecraft:witch");
        V0705.registerEntity(schema, hashMap, "minecraft:wither");
        V0705.registerEntity(schema, hashMap, "minecraft:wither_skeleton");
        V0705.registerThrowableProjectile(schema, hashMap, "minecraft:wither_skull");
        V0705.registerEntity(schema, hashMap, "minecraft:wolf");
        V0705.registerThrowableProjectile(schema, hashMap, "minecraft:xp_bottle");
        schema.registerSimple(hashMap, "minecraft:xp_orb");
        V0705.registerEntity(schema, hashMap, "minecraft:zombie");
        schema.register(hashMap, "minecraft:zombie_horse", arg_0 -> V0705.lambda$registerEntities$22(schema, arg_0));
        V0705.registerEntity(schema, hashMap, "minecraft:zombie_pigman");
        V0705.registerEntity(schema, hashMap, "minecraft:zombie_villager");
        schema.registerSimple(hashMap, "minecraft:evocation_fangs");
        V0705.registerEntity(schema, hashMap, "minecraft:evocation_illager");
        schema.registerSimple(hashMap, "minecraft:illusion_illager");
        schema.register(hashMap, "minecraft:llama", arg_0 -> V0705.lambda$registerEntities$23(schema, arg_0));
        schema.registerSimple(hashMap, "minecraft:llama_spit");
        V0705.registerEntity(schema, hashMap, "minecraft:vex");
        V0705.registerEntity(schema, hashMap, "minecraft:vindication_illager");
        return hashMap;
    }

    @Override
    public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map, Map<String, Supplier<TypeTemplate>> map2) {
        super.registerTypes(schema, map, map2);
        schema.registerType(true, TypeReferences.ENTITY, () -> V0705.lambda$registerTypes$24(map));
        schema.registerType(true, TypeReferences.ITEM_STACK, () -> V0705.lambda$registerTypes$25(schema));
    }

    private static TypeTemplate lambda$registerTypes$25(Schema schema) {
        return DSL.hook(DSL.optionalFields("id", TypeReferences.ITEM_NAME.in(schema), "tag", DSL.optionalFields("EntityTag", TypeReferences.ENTITY_TYPE.in(schema), "BlockEntityTag", TypeReferences.BLOCK_ENTITY.in(schema), "CanDestroy", DSL.list(TypeReferences.BLOCK_NAME.in(schema)), "CanPlaceOn", DSL.list(TypeReferences.BLOCK_NAME.in(schema)))), field_206597_b, Hook.HookFunction.IDENTITY);
    }

    private static TypeTemplate lambda$registerTypes$24(Map map) {
        return DSL.taggedChoiceLazy("id", V0705.func_233457_a_(), map);
    }

    private static TypeTemplate lambda$registerEntities$23(Schema schema, String string) {
        return DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(schema)), "SaddleItem", TypeReferences.ITEM_STACK.in(schema), "DecorItem", TypeReferences.ITEM_STACK.in(schema), V0100.equipment(schema));
    }

    private static TypeTemplate lambda$registerEntities$22(Schema schema, String string) {
        return DSL.optionalFields("SaddleItem", TypeReferences.ITEM_STACK.in(schema), V0100.equipment(schema));
    }

    private static TypeTemplate lambda$registerEntities$21(Schema schema, String string) {
        return DSL.optionalFields("Inventory", DSL.list(TypeReferences.ITEM_STACK.in(schema)), "Offers", DSL.optionalFields("Recipes", DSL.list(DSL.optionalFields("buy", TypeReferences.ITEM_STACK.in(schema), "buyB", TypeReferences.ITEM_STACK.in(schema), "sell", TypeReferences.ITEM_STACK.in(schema)))), V0100.equipment(schema));
    }

    private static TypeTemplate lambda$registerEntities$20(Schema schema, String string) {
        return DSL.optionalFields("DisplayTile", TypeReferences.BLOCK_NAME.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$19(Schema schema, String string) {
        return DSL.optionalFields("inTile", TypeReferences.BLOCK_NAME.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$18(Schema schema, String string) {
        return DSL.optionalFields("DisplayTile", TypeReferences.BLOCK_NAME.in(schema), TypeReferences.UNTAGGED_SPAWNER.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$17(Schema schema, String string) {
        return DSL.optionalFields("SaddleItem", TypeReferences.ITEM_STACK.in(schema), V0100.equipment(schema));
    }

    private static TypeTemplate lambda$registerEntities$16(Schema schema, String string) {
        return DSL.optionalFields("Potion", TypeReferences.ITEM_STACK.in(schema), "inTile", TypeReferences.BLOCK_NAME.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$15(Schema schema, String string) {
        return DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(schema)), "SaddleItem", TypeReferences.ITEM_STACK.in(schema), V0100.equipment(schema));
    }

    private static TypeTemplate lambda$registerEntities$14(Schema schema, String string) {
        return DSL.optionalFields("DisplayTile", TypeReferences.BLOCK_NAME.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$13(Schema schema, String string) {
        return DSL.optionalFields("Item", TypeReferences.ITEM_STACK.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$12(Schema schema, String string) {
        return DSL.optionalFields("Item", TypeReferences.ITEM_STACK.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$11(Schema schema, String string) {
        return DSL.optionalFields("ArmorItem", TypeReferences.ITEM_STACK.in(schema), "SaddleItem", TypeReferences.ITEM_STACK.in(schema), V0100.equipment(schema));
    }

    private static TypeTemplate lambda$registerEntities$10(Schema schema, String string) {
        return DSL.optionalFields("DisplayTile", TypeReferences.BLOCK_NAME.in(schema), "Items", DSL.list(TypeReferences.ITEM_STACK.in(schema)));
    }

    private static TypeTemplate lambda$registerEntities$9(Schema schema, String string) {
        return DSL.optionalFields("DisplayTile", TypeReferences.BLOCK_NAME.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$8(Schema schema, String string) {
        return DSL.optionalFields("FireworksItem", TypeReferences.ITEM_STACK.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$7(Schema schema, String string) {
        return DSL.optionalFields("Block", TypeReferences.BLOCK_NAME.in(schema), "TileEntityData", TypeReferences.BLOCK_ENTITY.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$6(Schema schema, String string) {
        return DSL.optionalFields("carried", TypeReferences.BLOCK_NAME.in(schema), V0100.equipment(schema));
    }

    private static TypeTemplate lambda$registerEntities$5(Schema schema, String string) {
        return DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(schema)), "SaddleItem", TypeReferences.ITEM_STACK.in(schema), V0100.equipment(schema));
    }

    private static TypeTemplate lambda$registerEntities$4(Schema schema, String string) {
        return DSL.optionalFields("DisplayTile", TypeReferences.BLOCK_NAME.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$3(Schema schema, String string) {
        return DSL.optionalFields("DisplayTile", TypeReferences.BLOCK_NAME.in(schema), "Items", DSL.list(TypeReferences.ITEM_STACK.in(schema)));
    }

    private static TypeTemplate lambda$registerEntities$2(Schema schema, String string) {
        return DSL.optionalFields("inTile", TypeReferences.BLOCK_NAME.in(schema));
    }

    private static TypeTemplate lambda$registerThrowableProjectile$1(Schema schema) {
        return DSL.optionalFields("inTile", TypeReferences.BLOCK_NAME.in(schema));
    }

    private static TypeTemplate lambda$registerEntity$0(Schema schema) {
        return V0100.equipment(schema);
    }
}

