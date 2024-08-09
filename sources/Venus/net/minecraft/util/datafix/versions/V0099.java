/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.versions;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.schemas.Schema;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class V0099
extends Schema {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<String, String> field_206693_d = DataFixUtils.make(Maps.newHashMap(), V0099::lambda$static$0);
    protected static final Hook.HookFunction field_206691_b = new Hook.HookFunction(){

        @Override
        public <T> T apply(DynamicOps<T> dynamicOps, T t) {
            return V0099.func_209869_a(new Dynamic<T>(dynamicOps, t), field_206693_d, "ArmorStand");
        }
    };

    public V0099(int n, Schema schema) {
        super(n, schema);
    }

    protected static TypeTemplate equipment(Schema schema) {
        return DSL.optionalFields("Equipment", DSL.list(TypeReferences.ITEM_STACK.in(schema)));
    }

    protected static void registerEntity(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> V0099.lambda$registerEntity$1(schema));
    }

    protected static void registerThrowableProjectile(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> V0099.lambda$registerThrowableProjectile$2(schema));
    }

    protected static void registerMinecart(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> V0099.lambda$registerMinecart$3(schema));
    }

    protected static void registerInventory(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> V0099.lambda$registerInventory$4(schema));
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        HashMap<String, Supplier<TypeTemplate>> hashMap = Maps.newHashMap();
        schema.register(hashMap, "Item", arg_0 -> V0099.lambda$registerEntities$5(schema, arg_0));
        schema.registerSimple(hashMap, "XPOrb");
        V0099.registerThrowableProjectile(schema, hashMap, "ThrownEgg");
        schema.registerSimple(hashMap, "LeashKnot");
        schema.registerSimple(hashMap, "Painting");
        schema.register(hashMap, "Arrow", arg_0 -> V0099.lambda$registerEntities$6(schema, arg_0));
        schema.register(hashMap, "TippedArrow", arg_0 -> V0099.lambda$registerEntities$7(schema, arg_0));
        schema.register(hashMap, "SpectralArrow", arg_0 -> V0099.lambda$registerEntities$8(schema, arg_0));
        V0099.registerThrowableProjectile(schema, hashMap, "Snowball");
        V0099.registerThrowableProjectile(schema, hashMap, "Fireball");
        V0099.registerThrowableProjectile(schema, hashMap, "SmallFireball");
        V0099.registerThrowableProjectile(schema, hashMap, "ThrownEnderpearl");
        schema.registerSimple(hashMap, "EyeOfEnderSignal");
        schema.register(hashMap, "ThrownPotion", arg_0 -> V0099.lambda$registerEntities$9(schema, arg_0));
        V0099.registerThrowableProjectile(schema, hashMap, "ThrownExpBottle");
        schema.register(hashMap, "ItemFrame", arg_0 -> V0099.lambda$registerEntities$10(schema, arg_0));
        V0099.registerThrowableProjectile(schema, hashMap, "WitherSkull");
        schema.registerSimple(hashMap, "PrimedTnt");
        schema.register(hashMap, "FallingSand", arg_0 -> V0099.lambda$registerEntities$11(schema, arg_0));
        schema.register(hashMap, "FireworksRocketEntity", arg_0 -> V0099.lambda$registerEntities$12(schema, arg_0));
        schema.registerSimple(hashMap, "Boat");
        schema.register(hashMap, "Minecart", () -> V0099.lambda$registerEntities$13(schema));
        V0099.registerMinecart(schema, hashMap, "MinecartRideable");
        schema.register(hashMap, "MinecartChest", arg_0 -> V0099.lambda$registerEntities$14(schema, arg_0));
        V0099.registerMinecart(schema, hashMap, "MinecartFurnace");
        V0099.registerMinecart(schema, hashMap, "MinecartTNT");
        schema.register(hashMap, "MinecartSpawner", () -> V0099.lambda$registerEntities$15(schema));
        schema.register(hashMap, "MinecartHopper", arg_0 -> V0099.lambda$registerEntities$16(schema, arg_0));
        V0099.registerMinecart(schema, hashMap, "MinecartCommandBlock");
        V0099.registerEntity(schema, hashMap, "ArmorStand");
        V0099.registerEntity(schema, hashMap, "Creeper");
        V0099.registerEntity(schema, hashMap, "Skeleton");
        V0099.registerEntity(schema, hashMap, "Spider");
        V0099.registerEntity(schema, hashMap, "Giant");
        V0099.registerEntity(schema, hashMap, "Zombie");
        V0099.registerEntity(schema, hashMap, "Slime");
        V0099.registerEntity(schema, hashMap, "Ghast");
        V0099.registerEntity(schema, hashMap, "PigZombie");
        schema.register(hashMap, "Enderman", arg_0 -> V0099.lambda$registerEntities$17(schema, arg_0));
        V0099.registerEntity(schema, hashMap, "CaveSpider");
        V0099.registerEntity(schema, hashMap, "Silverfish");
        V0099.registerEntity(schema, hashMap, "Blaze");
        V0099.registerEntity(schema, hashMap, "LavaSlime");
        V0099.registerEntity(schema, hashMap, "EnderDragon");
        V0099.registerEntity(schema, hashMap, "WitherBoss");
        V0099.registerEntity(schema, hashMap, "Bat");
        V0099.registerEntity(schema, hashMap, "Witch");
        V0099.registerEntity(schema, hashMap, "Endermite");
        V0099.registerEntity(schema, hashMap, "Guardian");
        V0099.registerEntity(schema, hashMap, "Pig");
        V0099.registerEntity(schema, hashMap, "Sheep");
        V0099.registerEntity(schema, hashMap, "Cow");
        V0099.registerEntity(schema, hashMap, "Chicken");
        V0099.registerEntity(schema, hashMap, "Squid");
        V0099.registerEntity(schema, hashMap, "Wolf");
        V0099.registerEntity(schema, hashMap, "MushroomCow");
        V0099.registerEntity(schema, hashMap, "SnowMan");
        V0099.registerEntity(schema, hashMap, "Ozelot");
        V0099.registerEntity(schema, hashMap, "VillagerGolem");
        schema.register(hashMap, "EntityHorse", arg_0 -> V0099.lambda$registerEntities$18(schema, arg_0));
        V0099.registerEntity(schema, hashMap, "Rabbit");
        schema.register(hashMap, "Villager", arg_0 -> V0099.lambda$registerEntities$19(schema, arg_0));
        schema.registerSimple(hashMap, "EnderCrystal");
        schema.registerSimple(hashMap, "AreaEffectCloud");
        schema.registerSimple(hashMap, "ShulkerBullet");
        V0099.registerEntity(schema, hashMap, "Shulker");
        return hashMap;
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
        HashMap<String, Supplier<TypeTemplate>> hashMap = Maps.newHashMap();
        V0099.registerInventory(schema, hashMap, "Furnace");
        V0099.registerInventory(schema, hashMap, "Chest");
        schema.registerSimple(hashMap, "EnderChest");
        schema.register(hashMap, "RecordPlayer", arg_0 -> V0099.lambda$registerBlockEntities$20(schema, arg_0));
        V0099.registerInventory(schema, hashMap, "Trap");
        V0099.registerInventory(schema, hashMap, "Dropper");
        schema.registerSimple(hashMap, "Sign");
        schema.register(hashMap, "MobSpawner", arg_0 -> V0099.lambda$registerBlockEntities$21(schema, arg_0));
        schema.registerSimple(hashMap, "Music");
        schema.registerSimple(hashMap, "Piston");
        V0099.registerInventory(schema, hashMap, "Cauldron");
        schema.registerSimple(hashMap, "EnchantTable");
        schema.registerSimple(hashMap, "Airportal");
        schema.registerSimple(hashMap, "Control");
        schema.registerSimple(hashMap, "Beacon");
        schema.registerSimple(hashMap, "Skull");
        schema.registerSimple(hashMap, "DLDetector");
        V0099.registerInventory(schema, hashMap, "Hopper");
        schema.registerSimple(hashMap, "Comparator");
        schema.register(hashMap, "FlowerPot", arg_0 -> V0099.lambda$registerBlockEntities$22(schema, arg_0));
        schema.registerSimple(hashMap, "Banner");
        schema.registerSimple(hashMap, "Structure");
        schema.registerSimple(hashMap, "EndGateway");
        return hashMap;
    }

    @Override
    public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map, Map<String, Supplier<TypeTemplate>> map2) {
        schema.registerType(false, TypeReferences.LEVEL, DSL::remainder);
        schema.registerType(false, TypeReferences.PLAYER, () -> V0099.lambda$registerTypes$23(schema));
        schema.registerType(false, TypeReferences.CHUNK, () -> V0099.lambda$registerTypes$24(schema));
        schema.registerType(true, TypeReferences.BLOCK_ENTITY, () -> V0099.lambda$registerTypes$25(map2));
        schema.registerType(true, TypeReferences.ENTITY_TYPE, () -> V0099.lambda$registerTypes$26(schema));
        schema.registerType(false, TypeReferences.ENTITY_NAME, V0099::lambda$registerTypes$27);
        schema.registerType(true, TypeReferences.ENTITY, () -> V0099.lambda$registerTypes$28(map));
        schema.registerType(true, TypeReferences.ITEM_STACK, () -> V0099.lambda$registerTypes$29(schema));
        schema.registerType(false, TypeReferences.OPTIONS, DSL::remainder);
        schema.registerType(false, TypeReferences.BLOCK_NAME, V0099::lambda$registerTypes$30);
        schema.registerType(false, TypeReferences.ITEM_NAME, V0099::lambda$registerTypes$31);
        schema.registerType(false, TypeReferences.STATS, DSL::remainder);
        schema.registerType(false, TypeReferences.SAVED_DATA, () -> V0099.lambda$registerTypes$32(schema));
        schema.registerType(false, TypeReferences.STRUCTURE_FEATURE, DSL::remainder);
        schema.registerType(false, TypeReferences.OBJECTIVE, DSL::remainder);
        schema.registerType(false, TypeReferences.TEAM, DSL::remainder);
        schema.registerType(true, TypeReferences.UNTAGGED_SPAWNER, DSL::remainder);
        schema.registerType(false, TypeReferences.POI_CHUNK, DSL::remainder);
        schema.registerType(true, TypeReferences.WORLD_GEN_SETTINGS, DSL::remainder);
    }

    protected static <T> T func_209869_a(Dynamic<T> dynamic, Map<String, String> map, String string) {
        return dynamic.update("tag", arg_0 -> V0099.lambda$func_209869_a$35(dynamic, map, string, arg_0)).getValue();
    }

    private static Dynamic lambda$func_209869_a$35(Dynamic dynamic, Map map, String string, Dynamic dynamic2) {
        return dynamic2.update("BlockEntityTag", arg_0 -> V0099.lambda$func_209869_a$33(dynamic, map, arg_0)).update("EntityTag", arg_0 -> V0099.lambda$func_209869_a$34(dynamic, string, arg_0));
    }

    private static Dynamic lambda$func_209869_a$34(Dynamic dynamic, String string, Dynamic dynamic2) {
        String string2 = dynamic.get("id").asString("");
        return Objects.equals(NamespacedSchema.ensureNamespaced(string2), "minecraft:armor_stand") ? dynamic2.set("id", dynamic.createString(string)) : dynamic2;
    }

    private static Dynamic lambda$func_209869_a$33(Dynamic dynamic, Map map, Dynamic dynamic2) {
        String string = dynamic.get("id").asString("");
        String string2 = (String)map.get(NamespacedSchema.ensureNamespaced(string));
        if (string2 == null) {
            LOGGER.warn("Unable to resolve BlockEntity for ItemStack: {}", (Object)string);
            return dynamic2;
        }
        return dynamic2.set("id", dynamic.createString(string2));
    }

    private static TypeTemplate lambda$registerTypes$32(Schema schema) {
        return DSL.optionalFields("data", DSL.optionalFields("Features", DSL.compoundList(TypeReferences.STRUCTURE_FEATURE.in(schema)), "Objectives", DSL.list(TypeReferences.OBJECTIVE.in(schema)), "Teams", DSL.list(TypeReferences.TEAM.in(schema))));
    }

    private static TypeTemplate lambda$registerTypes$31() {
        return DSL.constType(NamespacedSchema.func_233457_a_());
    }

    private static TypeTemplate lambda$registerTypes$30() {
        return DSL.or(DSL.constType(DSL.intType()), DSL.constType(NamespacedSchema.func_233457_a_()));
    }

    private static TypeTemplate lambda$registerTypes$29(Schema schema) {
        return DSL.hook(DSL.optionalFields("id", DSL.or(DSL.constType(DSL.intType()), TypeReferences.ITEM_NAME.in(schema)), "tag", DSL.optionalFields("EntityTag", TypeReferences.ENTITY_TYPE.in(schema), "BlockEntityTag", TypeReferences.BLOCK_ENTITY.in(schema), "CanDestroy", DSL.list(TypeReferences.BLOCK_NAME.in(schema)), "CanPlaceOn", DSL.list(TypeReferences.BLOCK_NAME.in(schema)))), field_206691_b, Hook.HookFunction.IDENTITY);
    }

    private static TypeTemplate lambda$registerTypes$28(Map map) {
        return DSL.taggedChoiceLazy("id", DSL.string(), map);
    }

    private static TypeTemplate lambda$registerTypes$27() {
        return DSL.constType(NamespacedSchema.func_233457_a_());
    }

    private static TypeTemplate lambda$registerTypes$26(Schema schema) {
        return DSL.optionalFields("Riding", TypeReferences.ENTITY_TYPE.in(schema), TypeReferences.ENTITY.in(schema));
    }

    private static TypeTemplate lambda$registerTypes$25(Map map) {
        return DSL.taggedChoiceLazy("id", DSL.string(), map);
    }

    private static TypeTemplate lambda$registerTypes$24(Schema schema) {
        return DSL.fields("Level", DSL.optionalFields("Entities", DSL.list(TypeReferences.ENTITY_TYPE.in(schema)), "TileEntities", DSL.list(TypeReferences.BLOCK_ENTITY.in(schema)), "TileTicks", DSL.list(DSL.fields("i", TypeReferences.BLOCK_NAME.in(schema)))));
    }

    private static TypeTemplate lambda$registerTypes$23(Schema schema) {
        return DSL.optionalFields("Inventory", DSL.list(TypeReferences.ITEM_STACK.in(schema)), "EnderItems", DSL.list(TypeReferences.ITEM_STACK.in(schema)));
    }

    private static TypeTemplate lambda$registerBlockEntities$22(Schema schema, String string) {
        return DSL.optionalFields("Item", DSL.or(DSL.constType(DSL.intType()), TypeReferences.ITEM_NAME.in(schema)));
    }

    private static TypeTemplate lambda$registerBlockEntities$21(Schema schema, String string) {
        return TypeReferences.UNTAGGED_SPAWNER.in(schema);
    }

    private static TypeTemplate lambda$registerBlockEntities$20(Schema schema, String string) {
        return DSL.optionalFields("RecordItem", TypeReferences.ITEM_STACK.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$19(Schema schema, String string) {
        return DSL.optionalFields("Inventory", DSL.list(TypeReferences.ITEM_STACK.in(schema)), "Offers", DSL.optionalFields("Recipes", DSL.list(DSL.optionalFields("buy", TypeReferences.ITEM_STACK.in(schema), "buyB", TypeReferences.ITEM_STACK.in(schema), "sell", TypeReferences.ITEM_STACK.in(schema)))), V0099.equipment(schema));
    }

    private static TypeTemplate lambda$registerEntities$18(Schema schema, String string) {
        return DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(schema)), "ArmorItem", TypeReferences.ITEM_STACK.in(schema), "SaddleItem", TypeReferences.ITEM_STACK.in(schema), V0099.equipment(schema));
    }

    private static TypeTemplate lambda$registerEntities$17(Schema schema, String string) {
        return DSL.optionalFields("carried", TypeReferences.BLOCK_NAME.in(schema), V0099.equipment(schema));
    }

    private static TypeTemplate lambda$registerEntities$16(Schema schema, String string) {
        return DSL.optionalFields("DisplayTile", TypeReferences.BLOCK_NAME.in(schema), "Items", DSL.list(TypeReferences.ITEM_STACK.in(schema)));
    }

    private static TypeTemplate lambda$registerEntities$15(Schema schema) {
        return DSL.optionalFields("DisplayTile", TypeReferences.BLOCK_NAME.in(schema), TypeReferences.UNTAGGED_SPAWNER.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$14(Schema schema, String string) {
        return DSL.optionalFields("DisplayTile", TypeReferences.BLOCK_NAME.in(schema), "Items", DSL.list(TypeReferences.ITEM_STACK.in(schema)));
    }

    private static TypeTemplate lambda$registerEntities$13(Schema schema) {
        return DSL.optionalFields("DisplayTile", TypeReferences.BLOCK_NAME.in(schema), "Items", DSL.list(TypeReferences.ITEM_STACK.in(schema)));
    }

    private static TypeTemplate lambda$registerEntities$12(Schema schema, String string) {
        return DSL.optionalFields("FireworksItem", TypeReferences.ITEM_STACK.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$11(Schema schema, String string) {
        return DSL.optionalFields("Block", TypeReferences.BLOCK_NAME.in(schema), "TileEntityData", TypeReferences.BLOCK_ENTITY.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$10(Schema schema, String string) {
        return DSL.optionalFields("Item", TypeReferences.ITEM_STACK.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$9(Schema schema, String string) {
        return DSL.optionalFields("inTile", TypeReferences.BLOCK_NAME.in(schema), "Potion", TypeReferences.ITEM_STACK.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$8(Schema schema, String string) {
        return DSL.optionalFields("inTile", TypeReferences.BLOCK_NAME.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$7(Schema schema, String string) {
        return DSL.optionalFields("inTile", TypeReferences.BLOCK_NAME.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$6(Schema schema, String string) {
        return DSL.optionalFields("inTile", TypeReferences.BLOCK_NAME.in(schema));
    }

    private static TypeTemplate lambda$registerEntities$5(Schema schema, String string) {
        return DSL.optionalFields("Item", TypeReferences.ITEM_STACK.in(schema));
    }

    private static TypeTemplate lambda$registerInventory$4(Schema schema) {
        return DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(schema)));
    }

    private static TypeTemplate lambda$registerMinecart$3(Schema schema) {
        return DSL.optionalFields("DisplayTile", TypeReferences.BLOCK_NAME.in(schema));
    }

    private static TypeTemplate lambda$registerThrowableProjectile$2(Schema schema) {
        return DSL.optionalFields("inTile", TypeReferences.BLOCK_NAME.in(schema));
    }

    private static TypeTemplate lambda$registerEntity$1(Schema schema) {
        return V0099.equipment(schema);
    }

    private static void lambda$static$0(HashMap hashMap) {
        hashMap.put("minecraft:furnace", "Furnace");
        hashMap.put("minecraft:lit_furnace", "Furnace");
        hashMap.put("minecraft:chest", "Chest");
        hashMap.put("minecraft:trapped_chest", "Chest");
        hashMap.put("minecraft:ender_chest", "EnderChest");
        hashMap.put("minecraft:jukebox", "RecordPlayer");
        hashMap.put("minecraft:dispenser", "Trap");
        hashMap.put("minecraft:dropper", "Dropper");
        hashMap.put("minecraft:sign", "Sign");
        hashMap.put("minecraft:mob_spawner", "MobSpawner");
        hashMap.put("minecraft:noteblock", "Music");
        hashMap.put("minecraft:brewing_stand", "Cauldron");
        hashMap.put("minecraft:enhanting_table", "EnchantTable");
        hashMap.put("minecraft:command_block", "CommandBlock");
        hashMap.put("minecraft:beacon", "Beacon");
        hashMap.put("minecraft:skull", "Skull");
        hashMap.put("minecraft:daylight_detector", "DLDetector");
        hashMap.put("minecraft:hopper", "Hopper");
        hashMap.put("minecraft:banner", "Banner");
        hashMap.put("minecraft:flower_pot", "FlowerPot");
        hashMap.put("minecraft:repeating_command_block", "CommandBlock");
        hashMap.put("minecraft:chain_command_block", "CommandBlock");
        hashMap.put("minecraft:standing_sign", "Sign");
        hashMap.put("minecraft:wall_sign", "Sign");
        hashMap.put("minecraft:piston_head", "Piston");
        hashMap.put("minecraft:daylight_detector_inverted", "DLDetector");
        hashMap.put("minecraft:unpowered_comparator", "Comparator");
        hashMap.put("minecraft:powered_comparator", "Comparator");
        hashMap.put("minecraft:wall_banner", "Banner");
        hashMap.put("minecraft:standing_banner", "Banner");
        hashMap.put("minecraft:structure_block", "Structure");
        hashMap.put("minecraft:end_portal", "Airportal");
        hashMap.put("minecraft:end_gateway", "EndGateway");
        hashMap.put("minecraft:shield", "Banner");
    }
}

