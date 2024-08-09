/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.versions;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.Hook;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.versions.V0099;

public class V0704
extends Schema {
    protected static final Map<String, String> field_206647_b = DataFixUtils.make(Maps.newHashMap(), V0704::lambda$static$0);
    protected static final Hook.HookFunction field_206648_c = new Hook.HookFunction(){

        @Override
        public <T> T apply(DynamicOps<T> dynamicOps, T t) {
            return V0099.func_209869_a(new Dynamic<T>(dynamicOps, t), field_206647_b, "ArmorStand");
        }
    };

    public V0704(int n, Schema schema) {
        super(n, schema);
    }

    protected static void registerInventory(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> V0704.lambda$registerInventory$1(schema));
    }

    @Override
    public Type<?> getChoiceType(DSL.TypeReference typeReference, String string) {
        return Objects.equals(typeReference.typeName(), TypeReferences.BLOCK_ENTITY.typeName()) ? super.getChoiceType(typeReference, NamespacedSchema.ensureNamespaced(string)) : super.getChoiceType(typeReference, string);
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
        HashMap<String, Supplier<TypeTemplate>> hashMap = Maps.newHashMap();
        V0704.registerInventory(schema, hashMap, "minecraft:furnace");
        V0704.registerInventory(schema, hashMap, "minecraft:chest");
        schema.registerSimple(hashMap, "minecraft:ender_chest");
        schema.register(hashMap, "minecraft:jukebox", arg_0 -> V0704.lambda$registerBlockEntities$2(schema, arg_0));
        V0704.registerInventory(schema, hashMap, "minecraft:dispenser");
        V0704.registerInventory(schema, hashMap, "minecraft:dropper");
        schema.registerSimple(hashMap, "minecraft:sign");
        schema.register(hashMap, "minecraft:mob_spawner", arg_0 -> V0704.lambda$registerBlockEntities$3(schema, arg_0));
        schema.registerSimple(hashMap, "minecraft:noteblock");
        schema.registerSimple(hashMap, "minecraft:piston");
        V0704.registerInventory(schema, hashMap, "minecraft:brewing_stand");
        schema.registerSimple(hashMap, "minecraft:enchanting_table");
        schema.registerSimple(hashMap, "minecraft:end_portal");
        schema.registerSimple(hashMap, "minecraft:beacon");
        schema.registerSimple(hashMap, "minecraft:skull");
        schema.registerSimple(hashMap, "minecraft:daylight_detector");
        V0704.registerInventory(schema, hashMap, "minecraft:hopper");
        schema.registerSimple(hashMap, "minecraft:comparator");
        schema.register(hashMap, "minecraft:flower_pot", arg_0 -> V0704.lambda$registerBlockEntities$4(schema, arg_0));
        schema.registerSimple(hashMap, "minecraft:banner");
        schema.registerSimple(hashMap, "minecraft:structure_block");
        schema.registerSimple(hashMap, "minecraft:end_gateway");
        schema.registerSimple(hashMap, "minecraft:command_block");
        return hashMap;
    }

    @Override
    public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map, Map<String, Supplier<TypeTemplate>> map2) {
        super.registerTypes(schema, map, map2);
        schema.registerType(false, TypeReferences.BLOCK_ENTITY, () -> V0704.lambda$registerTypes$5(map2));
        schema.registerType(true, TypeReferences.ITEM_STACK, () -> V0704.lambda$registerTypes$6(schema));
    }

    private static TypeTemplate lambda$registerTypes$6(Schema schema) {
        return DSL.hook(DSL.optionalFields("id", TypeReferences.ITEM_NAME.in(schema), "tag", DSL.optionalFields("EntityTag", TypeReferences.ENTITY_TYPE.in(schema), "BlockEntityTag", TypeReferences.BLOCK_ENTITY.in(schema), "CanDestroy", DSL.list(TypeReferences.BLOCK_NAME.in(schema)), "CanPlaceOn", DSL.list(TypeReferences.BLOCK_NAME.in(schema)))), field_206648_c, Hook.HookFunction.IDENTITY);
    }

    private static TypeTemplate lambda$registerTypes$5(Map map) {
        return DSL.taggedChoiceLazy("id", NamespacedSchema.func_233457_a_(), map);
    }

    private static TypeTemplate lambda$registerBlockEntities$4(Schema schema, String string) {
        return DSL.optionalFields("Item", DSL.or(DSL.constType(DSL.intType()), TypeReferences.ITEM_NAME.in(schema)));
    }

    private static TypeTemplate lambda$registerBlockEntities$3(Schema schema, String string) {
        return TypeReferences.UNTAGGED_SPAWNER.in(schema);
    }

    private static TypeTemplate lambda$registerBlockEntities$2(Schema schema, String string) {
        return DSL.optionalFields("RecordItem", TypeReferences.ITEM_STACK.in(schema));
    }

    private static TypeTemplate lambda$registerInventory$1(Schema schema) {
        return DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(schema)));
    }

    private static void lambda$static$0(HashMap hashMap) {
        hashMap.put("minecraft:furnace", "minecraft:furnace");
        hashMap.put("minecraft:lit_furnace", "minecraft:furnace");
        hashMap.put("minecraft:chest", "minecraft:chest");
        hashMap.put("minecraft:trapped_chest", "minecraft:chest");
        hashMap.put("minecraft:ender_chest", "minecraft:ender_chest");
        hashMap.put("minecraft:jukebox", "minecraft:jukebox");
        hashMap.put("minecraft:dispenser", "minecraft:dispenser");
        hashMap.put("minecraft:dropper", "minecraft:dropper");
        hashMap.put("minecraft:sign", "minecraft:sign");
        hashMap.put("minecraft:mob_spawner", "minecraft:mob_spawner");
        hashMap.put("minecraft:noteblock", "minecraft:noteblock");
        hashMap.put("minecraft:brewing_stand", "minecraft:brewing_stand");
        hashMap.put("minecraft:enhanting_table", "minecraft:enchanting_table");
        hashMap.put("minecraft:command_block", "minecraft:command_block");
        hashMap.put("minecraft:beacon", "minecraft:beacon");
        hashMap.put("minecraft:skull", "minecraft:skull");
        hashMap.put("minecraft:daylight_detector", "minecraft:daylight_detector");
        hashMap.put("minecraft:hopper", "minecraft:hopper");
        hashMap.put("minecraft:banner", "minecraft:banner");
        hashMap.put("minecraft:flower_pot", "minecraft:flower_pot");
        hashMap.put("minecraft:repeating_command_block", "minecraft:command_block");
        hashMap.put("minecraft:chain_command_block", "minecraft:command_block");
        hashMap.put("minecraft:shulker_box", "minecraft:shulker_box");
        hashMap.put("minecraft:white_shulker_box", "minecraft:shulker_box");
        hashMap.put("minecraft:orange_shulker_box", "minecraft:shulker_box");
        hashMap.put("minecraft:magenta_shulker_box", "minecraft:shulker_box");
        hashMap.put("minecraft:light_blue_shulker_box", "minecraft:shulker_box");
        hashMap.put("minecraft:yellow_shulker_box", "minecraft:shulker_box");
        hashMap.put("minecraft:lime_shulker_box", "minecraft:shulker_box");
        hashMap.put("minecraft:pink_shulker_box", "minecraft:shulker_box");
        hashMap.put("minecraft:gray_shulker_box", "minecraft:shulker_box");
        hashMap.put("minecraft:silver_shulker_box", "minecraft:shulker_box");
        hashMap.put("minecraft:cyan_shulker_box", "minecraft:shulker_box");
        hashMap.put("minecraft:purple_shulker_box", "minecraft:shulker_box");
        hashMap.put("minecraft:blue_shulker_box", "minecraft:shulker_box");
        hashMap.put("minecraft:brown_shulker_box", "minecraft:shulker_box");
        hashMap.put("minecraft:green_shulker_box", "minecraft:shulker_box");
        hashMap.put("minecraft:red_shulker_box", "minecraft:shulker_box");
        hashMap.put("minecraft:black_shulker_box", "minecraft:shulker_box");
        hashMap.put("minecraft:bed", "minecraft:bed");
        hashMap.put("minecraft:light_gray_shulker_box", "minecraft:shulker_box");
        hashMap.put("minecraft:banner", "minecraft:banner");
        hashMap.put("minecraft:white_banner", "minecraft:banner");
        hashMap.put("minecraft:orange_banner", "minecraft:banner");
        hashMap.put("minecraft:magenta_banner", "minecraft:banner");
        hashMap.put("minecraft:light_blue_banner", "minecraft:banner");
        hashMap.put("minecraft:yellow_banner", "minecraft:banner");
        hashMap.put("minecraft:lime_banner", "minecraft:banner");
        hashMap.put("minecraft:pink_banner", "minecraft:banner");
        hashMap.put("minecraft:gray_banner", "minecraft:banner");
        hashMap.put("minecraft:silver_banner", "minecraft:banner");
        hashMap.put("minecraft:cyan_banner", "minecraft:banner");
        hashMap.put("minecraft:purple_banner", "minecraft:banner");
        hashMap.put("minecraft:blue_banner", "minecraft:banner");
        hashMap.put("minecraft:brown_banner", "minecraft:banner");
        hashMap.put("minecraft:green_banner", "minecraft:banner");
        hashMap.put("minecraft:red_banner", "minecraft:banner");
        hashMap.put("minecraft:black_banner", "minecraft:banner");
        hashMap.put("minecraft:standing_sign", "minecraft:sign");
        hashMap.put("minecraft:wall_sign", "minecraft:sign");
        hashMap.put("minecraft:piston_head", "minecraft:piston");
        hashMap.put("minecraft:daylight_detector_inverted", "minecraft:daylight_detector");
        hashMap.put("minecraft:unpowered_comparator", "minecraft:comparator");
        hashMap.put("minecraft:powered_comparator", "minecraft:comparator");
        hashMap.put("minecraft:wall_banner", "minecraft:banner");
        hashMap.put("minecraft:standing_banner", "minecraft:banner");
        hashMap.put("minecraft:structure_block", "minecraft:structure_block");
        hashMap.put("minecraft:end_portal", "minecraft:end_portal");
        hashMap.put("minecraft:end_gateway", "minecraft:end_gateway");
        hashMap.put("minecraft:sign", "minecraft:sign");
        hashMap.put("minecraft:shield", "minecraft:banner");
    }
}

