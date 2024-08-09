/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.versions;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.Hook;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.versions.V0100;
import net.minecraft.util.datafix.versions.V0705;

public class V1460
extends NamespacedSchema {
    public V1460(int n, Schema schema) {
        super(n, schema);
    }

    protected static void registerEntity(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> V1460.lambda$registerEntity$0(schema));
    }

    protected static void registerInventory(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> V1460.lambda$registerInventory$1(schema));
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        HashMap<String, Supplier<TypeTemplate>> hashMap = Maps.newHashMap();
        schema.registerSimple(hashMap, "minecraft:area_effect_cloud");
        V1460.registerEntity(schema, hashMap, "minecraft:armor_stand");
        schema.register(hashMap, "minecraft:arrow", arg_0 -> V1460.lambda$registerEntities$2(schema, arg_0));
        V1460.registerEntity(schema, hashMap, "minecraft:bat");
        V1460.registerEntity(schema, hashMap, "minecraft:blaze");
        schema.registerSimple(hashMap, "minecraft:boat");
        V1460.registerEntity(schema, hashMap, "minecraft:cave_spider");
        schema.register(hashMap, "minecraft:chest_minecart", arg_0 -> V1460.lambda$registerEntities$3(schema, arg_0));
        V1460.registerEntity(schema, hashMap, "minecraft:chicken");
        schema.register(hashMap, "minecraft:commandblock_minecart", arg_0 -> V1460.lambda$registerEntities$4(schema, arg_0));
        V1460.registerEntity(schema, hashMap, "minecraft:cow");
        V1460.registerEntity(schema, hashMap, "minecraft:creeper");
        schema.register(hashMap, "minecraft:donkey", arg_0 -> V1460.lambda$registerEntities$5(schema, arg_0));
        schema.registerSimple(hashMap, "minecraft:dragon_fireball");
        schema.registerSimple(hashMap, "minecraft:egg");
        V1460.registerEntity(schema, hashMap, "minecraft:elder_guardian");
        schema.registerSimple(hashMap, "minecraft:ender_crystal");
        V1460.registerEntity(schema, hashMap, "minecraft:ender_dragon");
        schema.register(hashMap, "minecraft:enderman", arg_0 -> V1460.lambda$registerEntities$6(schema, arg_0));
        V1460.registerEntity(schema, hashMap, "minecraft:endermite");
        schema.registerSimple(hashMap, "minecraft:ender_pearl");
        schema.registerSimple(hashMap, "minecraft:evocation_fangs");
        V1460.registerEntity(schema, hashMap, "minecraft:evocation_illager");
        schema.registerSimple(hashMap, "minecraft:eye_of_ender_signal");
        schema.register(hashMap, "minecraft:falling_block", arg_0 -> V1460.lambda$registerEntities$7(schema, arg_0));
        schema.registerSimple(hashMap, "minecraft:fireball");
        schema.register(hashMap, "minecraft:fireworks_rocket", arg_0 -> V1460.lambda$registerEntities$8(schema, arg_0));
        schema.register(hashMap, "minecraft:furnace_minecart", arg_0 -> V1460.lambda$registerEntities$9(schema, arg_0));
        V1460.registerEntity(schema, hashMap, "minecraft:ghast");
        V1460.registerEntity(schema, hashMap, "minecraft:giant");
        V1460.registerEntity(schema, hashMap, "minecraft:guardian");
        schema.register(hashMap, "minecraft:hopper_minecart", arg_0 -> V1460.lambda$registerEntities$10(schema, arg_0));
        schema.register(hashMap, "minecraft:horse", arg_0 -> V1460.lambda$registerEntities$11(schema, arg_0));
        V1460.registerEntity(schema, hashMap, "minecraft:husk");
        schema.registerSimple(hashMap, "minecraft:illusion_illager");
        schema.register(hashMap, "minecraft:item", arg_0 -> V1460.lambda$registerEntities$12(schema, arg_0));
        schema.register(hashMap, "minecraft:item_frame", arg_0 -> V1460.lambda$registerEntities$13(schema, arg_0));
        schema.registerSimple(hashMap, "minecraft:leash_knot");
        schema.register(hashMap, "minecraft:llama", arg_0 -> V1460.lambda$registerEntities$14(schema, arg_0));
        schema.registerSimple(hashMap, "minecraft:llama_spit");
        V1460.registerEntity(schema, hashMap, "minecraft:magma_cube");
        schema.register(hashMap, "minecraft:minecart", arg_0 -> V1460.lambda$registerEntities$15(schema, arg_0));
        V1460.registerEntity(schema, hashMap, "minecraft:mooshroom");
        schema.register(hashMap, "minecraft:mule", arg_0 -> V1460.lambda$registerEntities$16(schema, arg_0));
        V1460.registerEntity(schema, hashMap, "minecraft:ocelot");
        schema.registerSimple(hashMap, "minecraft:painting");
        schema.registerSimple(hashMap, "minecraft:parrot");
        V1460.registerEntity(schema, hashMap, "minecraft:pig");
        V1460.registerEntity(schema, hashMap, "minecraft:polar_bear");
        schema.register(hashMap, "minecraft:potion", arg_0 -> V1460.lambda$registerEntities$17(schema, arg_0));
        V1460.registerEntity(schema, hashMap, "minecraft:rabbit");
        V1460.registerEntity(schema, hashMap, "minecraft:sheep");
        V1460.registerEntity(schema, hashMap, "minecraft:shulker");
        schema.registerSimple(hashMap, "minecraft:shulker_bullet");
        V1460.registerEntity(schema, hashMap, "minecraft:silverfish");
        V1460.registerEntity(schema, hashMap, "minecraft:skeleton");
        schema.register(hashMap, "minecraft:skeleton_horse", arg_0 -> V1460.lambda$registerEntities$18(schema, arg_0));
        V1460.registerEntity(schema, hashMap, "minecraft:slime");
        schema.registerSimple(hashMap, "minecraft:small_fireball");
        schema.registerSimple(hashMap, "minecraft:snowball");
        V1460.registerEntity(schema, hashMap, "minecraft:snowman");
        schema.register(hashMap, "minecraft:spawner_minecart", arg_0 -> V1460.lambda$registerEntities$19(schema, arg_0));
        schema.register(hashMap, "minecraft:spectral_arrow", arg_0 -> V1460.lambda$registerEntities$20(schema, arg_0));
        V1460.registerEntity(schema, hashMap, "minecraft:spider");
        V1460.registerEntity(schema, hashMap, "minecraft:squid");
        V1460.registerEntity(schema, hashMap, "minecraft:stray");
        schema.registerSimple(hashMap, "minecraft:tnt");
        schema.register(hashMap, "minecraft:tnt_minecart", arg_0 -> V1460.lambda$registerEntities$21(schema, arg_0));
        V1460.registerEntity(schema, hashMap, "minecraft:vex");
        schema.register(hashMap, "minecraft:villager", arg_0 -> V1460.lambda$registerEntities$22(schema, arg_0));
        V1460.registerEntity(schema, hashMap, "minecraft:villager_golem");
        V1460.registerEntity(schema, hashMap, "minecraft:vindication_illager");
        V1460.registerEntity(schema, hashMap, "minecraft:witch");
        V1460.registerEntity(schema, hashMap, "minecraft:wither");
        V1460.registerEntity(schema, hashMap, "minecraft:wither_skeleton");
        schema.registerSimple(hashMap, "minecraft:wither_skull");
        V1460.registerEntity(schema, hashMap, "minecraft:wolf");
        schema.registerSimple(hashMap, "minecraft:xp_bottle");
        schema.registerSimple(hashMap, "minecraft:xp_orb");
        V1460.registerEntity(schema, hashMap, "minecraft:zombie");
        schema.register(hashMap, "minecraft:zombie_horse", arg_0 -> V1460.lambda$registerEntities$23(schema, arg_0));
        V1460.registerEntity(schema, hashMap, "minecraft:zombie_pigman");
        V1460.registerEntity(schema, hashMap, "minecraft:zombie_villager");
        return hashMap;
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
        HashMap<String, Supplier<TypeTemplate>> hashMap = Maps.newHashMap();
        V1460.registerInventory(schema, hashMap, "minecraft:furnace");
        V1460.registerInventory(schema, hashMap, "minecraft:chest");
        V1460.registerInventory(schema, hashMap, "minecraft:trapped_chest");
        schema.registerSimple(hashMap, "minecraft:ender_chest");
        schema.register(hashMap, "minecraft:jukebox", arg_0 -> V1460.lambda$registerBlockEntities$24(schema, arg_0));
        V1460.registerInventory(schema, hashMap, "minecraft:dispenser");
        V1460.registerInventory(schema, hashMap, "minecraft:dropper");
        schema.registerSimple(hashMap, "minecraft:sign");
        schema.register(hashMap, "minecraft:mob_spawner", arg_0 -> V1460.lambda$registerBlockEntities$25(schema, arg_0));
        schema.register(hashMap, "minecraft:piston", arg_0 -> V1460.lambda$registerBlockEntities$26(schema, arg_0));
        V1460.registerInventory(schema, hashMap, "minecraft:brewing_stand");
        schema.registerSimple(hashMap, "minecraft:enchanting_table");
        schema.registerSimple(hashMap, "minecraft:end_portal");
        schema.registerSimple(hashMap, "minecraft:beacon");
        schema.registerSimple(hashMap, "minecraft:skull");
        schema.registerSimple(hashMap, "minecraft:daylight_detector");
        V1460.registerInventory(schema, hashMap, "minecraft:hopper");
        schema.registerSimple(hashMap, "minecraft:comparator");
        schema.registerSimple(hashMap, "minecraft:banner");
        schema.registerSimple(hashMap, "minecraft:structure_block");
        schema.registerSimple(hashMap, "minecraft:end_gateway");
        schema.registerSimple(hashMap, "minecraft:command_block");
        V1460.registerInventory(schema, hashMap, "minecraft:shulker_box");
        schema.registerSimple(hashMap, "minecraft:bed");
        return hashMap;
    }

    @Override
    public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map, Map<String, Supplier<TypeTemplate>> map2) {
        schema.registerType(false, TypeReferences.LEVEL, DSL::remainder);
        schema.registerType(false, TypeReferences.RECIPE, V1460::lambda$registerTypes$27);
        schema.registerType(false, TypeReferences.PLAYER, () -> V1460.lambda$registerTypes$28(schema));
        schema.registerType(false, TypeReferences.CHUNK, () -> V1460.lambda$registerTypes$29(schema));
        schema.registerType(true, TypeReferences.BLOCK_ENTITY, () -> V1460.lambda$registerTypes$30(map2));
        schema.registerType(true, TypeReferences.ENTITY_TYPE, () -> V1460.lambda$registerTypes$31(schema));
        schema.registerType(true, TypeReferences.ENTITY, () -> V1460.lambda$registerTypes$32(map));
        schema.registerType(true, TypeReferences.ITEM_STACK, () -> V1460.lambda$registerTypes$33(schema));
        schema.registerType(false, TypeReferences.HOTBAR, () -> V1460.lambda$registerTypes$34(schema));
        schema.registerType(false, TypeReferences.OPTIONS, DSL::remainder);
        schema.registerType(false, TypeReferences.STRUCTURE, () -> V1460.lambda$registerTypes$35(schema));
        schema.registerType(false, TypeReferences.BLOCK_NAME, V1460::lambda$registerTypes$36);
        schema.registerType(false, TypeReferences.ITEM_NAME, V1460::lambda$registerTypes$37);
        schema.registerType(false, TypeReferences.BLOCK_STATE, DSL::remainder);
        Supplier<TypeTemplate> supplier = () -> V1460.lambda$registerTypes$38(schema);
        schema.registerType(false, TypeReferences.STATS, () -> V1460.lambda$registerTypes$39(schema, supplier));
        schema.registerType(false, TypeReferences.SAVED_DATA, () -> V1460.lambda$registerTypes$40(schema));
        schema.registerType(false, TypeReferences.STRUCTURE_FEATURE, () -> V1460.lambda$registerTypes$41(schema));
        schema.registerType(false, TypeReferences.OBJECTIVE, DSL::remainder);
        schema.registerType(false, TypeReferences.TEAM, DSL::remainder);
        schema.registerType(true, TypeReferences.UNTAGGED_SPAWNER, () -> V1460.lambda$registerTypes$42(schema));
        schema.registerType(false, TypeReferences.ADVANCEMENTS, () -> V1460.lambda$registerTypes$43(schema));
        schema.registerType(false, TypeReferences.BIOME, V1460::lambda$registerTypes$44);
        schema.registerType(false, TypeReferences.ENTITY_NAME, V1460::lambda$registerTypes$45);
        schema.registerType(false, TypeReferences.POI_CHUNK, DSL::remainder);
        schema.registerType(true, TypeReferences.WORLD_GEN_SETTINGS, DSL::remainder);
    }

    private static TypeTemplate lambda$registerTypes$45() {
        return DSL.constType(V1460.func_233457_a_());
    }

    private static TypeTemplate lambda$registerTypes$44() {
        return DSL.constType(V1460.func_233457_a_());
    }

    private static TypeTemplate lambda$registerTypes$43(Schema schema) {
        return DSL.optionalFields("minecraft:adventure/adventuring_time", DSL.optionalFields("criteria", DSL.compoundList(TypeReferences.BIOME.in(schema), DSL.constType(DSL.string()))), "minecraft:adventure/kill_a_mob", DSL.optionalFields("criteria", DSL.compoundList(TypeReferences.ENTITY_NAME.in(schema), DSL.constType(DSL.string()))), "minecraft:adventure/kill_all_mobs", DSL.optionalFields("criteria", DSL.compoundList(TypeReferences.ENTITY_NAME.in(schema), DSL.constType(DSL.string()))), "minecraft:husbandry/bred_all_animals", DSL.optionalFields("criteria", DSL.compoundList(TypeReferences.ENTITY_NAME.in(schema), DSL.constType(DSL.string()))));
    }

    private static TypeTemplate lambda$registerTypes$42(Schema schema) {
        return DSL.optionalFields("SpawnPotentials", DSL.list(DSL.fields("Entity", TypeReferences.ENTITY_TYPE.in(schema))), "SpawnData", TypeReferences.ENTITY_TYPE.in(schema));
    }

    private static TypeTemplate lambda$registerTypes$41(Schema schema) {
        return DSL.optionalFields("Children", DSL.list(DSL.optionalFields("CA", TypeReferences.BLOCK_STATE.in(schema), "CB", TypeReferences.BLOCK_STATE.in(schema), "CC", TypeReferences.BLOCK_STATE.in(schema), "CD", TypeReferences.BLOCK_STATE.in(schema))));
    }

    private static TypeTemplate lambda$registerTypes$40(Schema schema) {
        return DSL.optionalFields("data", DSL.optionalFields("Features", DSL.compoundList(TypeReferences.STRUCTURE_FEATURE.in(schema)), "Objectives", DSL.list(TypeReferences.OBJECTIVE.in(schema)), "Teams", DSL.list(TypeReferences.TEAM.in(schema))));
    }

    private static TypeTemplate lambda$registerTypes$39(Schema schema, Supplier supplier) {
        return DSL.optionalFields("stats", DSL.optionalFields("minecraft:mined", DSL.compoundList(TypeReferences.BLOCK_NAME.in(schema), DSL.constType(DSL.intType())), "minecraft:crafted", (TypeTemplate)supplier.get(), "minecraft:used", (TypeTemplate)supplier.get(), "minecraft:broken", (TypeTemplate)supplier.get(), "minecraft:picked_up", (TypeTemplate)supplier.get(), DSL.optionalFields("minecraft:dropped", (TypeTemplate)supplier.get(), "minecraft:killed", DSL.compoundList(TypeReferences.ENTITY_NAME.in(schema), DSL.constType(DSL.intType())), "minecraft:killed_by", DSL.compoundList(TypeReferences.ENTITY_NAME.in(schema), DSL.constType(DSL.intType())), "minecraft:custom", DSL.compoundList(DSL.constType(V1460.func_233457_a_()), DSL.constType(DSL.intType())))));
    }

    private static TypeTemplate lambda$registerTypes$38(Schema schema) {
        return DSL.compoundList(TypeReferences.ITEM_NAME.in(schema), DSL.constType(DSL.intType()));
    }

    private static TypeTemplate lambda$registerTypes$37() {
        return DSL.constType(V1460.func_233457_a_());
    }

    private static TypeTemplate lambda$registerTypes$36() {
        return DSL.constType(V1460.func_233457_a_());
    }

    private static TypeTemplate lambda$registerTypes$35(Schema schema) {
        return DSL.optionalFields("entities", DSL.list(DSL.optionalFields("nbt", TypeReferences.ENTITY_TYPE.in(schema))), "blocks", DSL.list(DSL.optionalFields("nbt", TypeReferences.BLOCK_ENTITY.in(schema))), "palette", DSL.list(TypeReferences.BLOCK_STATE.in(schema)));
    }

    private static TypeTemplate lambda$registerTypes$34(Schema schema) {
        return DSL.compoundList(DSL.list(TypeReferences.ITEM_STACK.in(schema)));
    }

    private static TypeTemplate lambda$registerTypes$33(Schema schema) {
        return DSL.hook(DSL.optionalFields("id", TypeReferences.ITEM_NAME.in(schema), "tag", DSL.optionalFields("EntityTag", TypeReferences.ENTITY_TYPE.in(schema), "BlockEntityTag", TypeReferences.BLOCK_ENTITY.in(schema), "CanDestroy", DSL.list(TypeReferences.BLOCK_NAME.in(schema)), "CanPlaceOn", DSL.list(TypeReferences.BLOCK_NAME.in(schema)))), V0705.field_206597_b, Hook.HookFunction.IDENTITY);
    }

    private static TypeTemplate lambda$registerTypes$32(Map map) {
        return DSL.taggedChoiceLazy("id", V1460.func_233457_a_(), map);
    }

    private static TypeTemplate lambda$registerTypes$31(Schema schema) {
        return DSL.optionalFields("Passengers", DSL.list(TypeReferences.ENTITY_TYPE.in(schema)), TypeReferences.ENTITY.in(schema));
    }

    private static TypeTemplate lambda$registerTypes$30(Map map) {
        return DSL.taggedChoiceLazy("id", V1460.func_233457_a_(), map);
    }

    private static TypeTemplate lambda$registerTypes$29(Schema schema) {
        return DSL.fields("Level", DSL.optionalFields("Entities", DSL.list(TypeReferences.ENTITY_TYPE.in(schema)), "TileEntities", DSL.list(TypeReferences.BLOCK_ENTITY.in(schema)), "TileTicks", DSL.list(DSL.fields("i", TypeReferences.BLOCK_NAME.in(schema))), "Sections", DSL.list(DSL.optionalFields("Palette", DSL.list(TypeReferences.BLOCK_STATE.in(schema))))));
    }

    private static TypeTemplate lambda$registerTypes$28(Schema schema) {
        return DSL.optionalFields("RootVehicle", DSL.optionalFields("Entity", TypeReferences.ENTITY_TYPE.in(schema)), "Inventory", DSL.list(TypeReferences.ITEM_STACK.in(schema)), "EnderItems", DSL.list(TypeReferences.ITEM_STACK.in(schema)), DSL.optionalFields("ShoulderEntityLeft", TypeReferences.ENTITY_TYPE.in(schema), "ShoulderEntityRight", TypeReferences.ENTITY_TYPE.in(schema), "recipeBook", DSL.optionalFields("recipes", DSL.list(TypeReferences.RECIPE.in(schema)), "toBeDisplayed", DSL.list(TypeReferences.RECIPE.in(schema)))));
    }

    private static TypeTemplate lambda$registerTypes$27() {
        return DSL.constType(V1460.func_233457_a_());
    }

    private static TypeTemplate lambda$registerBlockEntities$26(Schema schema, String string) {
        return DSL.optionalFields("blockState", TypeReferences.BLOCK_STATE.in(schema));
    }

    private static TypeTemplate lambda$registerBlockEntities$25(Schema schema, String string) {
        return TypeReferences.UNTAGGED_SPAWNER.in(schema);
    }

    private static TypeTemplate lambda$registerBlockEntities$24(Schema schema, String string) {
        return DSL.optionalFields("RecordItem", TypeReferences.ITEM_STACK.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$23(Schema schema, String string) {
        return DSL.optionalFields("SaddleItem", TypeReferences.ITEM_STACK.in(schema), V0100.equipment(schema));
    }

    private static TypeTemplate lambda$registerEntities$22(Schema schema, String string) {
        return DSL.optionalFields("Inventory", DSL.list(TypeReferences.ITEM_STACK.in(schema)), "Offers", DSL.optionalFields("Recipes", DSL.list(DSL.optionalFields("buy", TypeReferences.ITEM_STACK.in(schema), "buyB", TypeReferences.ITEM_STACK.in(schema), "sell", TypeReferences.ITEM_STACK.in(schema)))), V0100.equipment(schema));
    }

    private static TypeTemplate lambda$registerEntities$21(Schema schema, String string) {
        return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$20(Schema schema, String string) {
        return DSL.optionalFields("inBlockState", TypeReferences.BLOCK_STATE.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$19(Schema schema, String string) {
        return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(schema), TypeReferences.UNTAGGED_SPAWNER.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$18(Schema schema, String string) {
        return DSL.optionalFields("SaddleItem", TypeReferences.ITEM_STACK.in(schema), V0100.equipment(schema));
    }

    private static TypeTemplate lambda$registerEntities$17(Schema schema, String string) {
        return DSL.optionalFields("Potion", TypeReferences.ITEM_STACK.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$16(Schema schema, String string) {
        return DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(schema)), "SaddleItem", TypeReferences.ITEM_STACK.in(schema), V0100.equipment(schema));
    }

    private static TypeTemplate lambda$registerEntities$15(Schema schema, String string) {
        return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$14(Schema schema, String string) {
        return DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(schema)), "SaddleItem", TypeReferences.ITEM_STACK.in(schema), "DecorItem", TypeReferences.ITEM_STACK.in(schema), V0100.equipment(schema));
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
        return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(schema), "Items", DSL.list(TypeReferences.ITEM_STACK.in(schema)));
    }

    private static TypeTemplate lambda$registerEntities$9(Schema schema, String string) {
        return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$8(Schema schema, String string) {
        return DSL.optionalFields("FireworksItem", TypeReferences.ITEM_STACK.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$7(Schema schema, String string) {
        return DSL.optionalFields("BlockState", TypeReferences.BLOCK_STATE.in(schema), "TileEntityData", TypeReferences.BLOCK_ENTITY.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$6(Schema schema, String string) {
        return DSL.optionalFields("carriedBlockState", TypeReferences.BLOCK_STATE.in(schema), V0100.equipment(schema));
    }

    private static TypeTemplate lambda$registerEntities$5(Schema schema, String string) {
        return DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(schema)), "SaddleItem", TypeReferences.ITEM_STACK.in(schema), V0100.equipment(schema));
    }

    private static TypeTemplate lambda$registerEntities$4(Schema schema, String string) {
        return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$3(Schema schema, String string) {
        return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(schema), "Items", DSL.list(TypeReferences.ITEM_STACK.in(schema)));
    }

    private static TypeTemplate lambda$registerEntities$2(Schema schema, String string) {
        return DSL.optionalFields("inBlockState", TypeReferences.BLOCK_STATE.in(schema));
    }

    private static TypeTemplate lambda$registerInventory$1(Schema schema) {
        return DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(schema)));
    }

    private static TypeTemplate lambda$registerEntity$0(Schema schema) {
        return V0100.equipment(schema);
    }
}

