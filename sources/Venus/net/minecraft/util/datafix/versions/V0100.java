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

public class V0100
extends Schema {
    public V0100(int n, Schema schema) {
        super(n, schema);
    }

    protected static TypeTemplate equipment(Schema schema) {
        return DSL.optionalFields("ArmorItems", DSL.list(TypeReferences.ITEM_STACK.in(schema)), "HandItems", DSL.list(TypeReferences.ITEM_STACK.in(schema)));
    }

    protected static void registerEntity(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
        schema.register(map, string, () -> V0100.lambda$registerEntity$0(schema));
    }

    @Override
    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
        Map<String, Supplier<TypeTemplate>> map = super.registerEntities(schema);
        V0100.registerEntity(schema, map, "ArmorStand");
        V0100.registerEntity(schema, map, "Creeper");
        V0100.registerEntity(schema, map, "Skeleton");
        V0100.registerEntity(schema, map, "Spider");
        V0100.registerEntity(schema, map, "Giant");
        V0100.registerEntity(schema, map, "Zombie");
        V0100.registerEntity(schema, map, "Slime");
        V0100.registerEntity(schema, map, "Ghast");
        V0100.registerEntity(schema, map, "PigZombie");
        schema.register(map, "Enderman", arg_0 -> V0100.lambda$registerEntities$1(schema, arg_0));
        V0100.registerEntity(schema, map, "CaveSpider");
        V0100.registerEntity(schema, map, "Silverfish");
        V0100.registerEntity(schema, map, "Blaze");
        V0100.registerEntity(schema, map, "LavaSlime");
        V0100.registerEntity(schema, map, "EnderDragon");
        V0100.registerEntity(schema, map, "WitherBoss");
        V0100.registerEntity(schema, map, "Bat");
        V0100.registerEntity(schema, map, "Witch");
        V0100.registerEntity(schema, map, "Endermite");
        V0100.registerEntity(schema, map, "Guardian");
        V0100.registerEntity(schema, map, "Pig");
        V0100.registerEntity(schema, map, "Sheep");
        V0100.registerEntity(schema, map, "Cow");
        V0100.registerEntity(schema, map, "Chicken");
        V0100.registerEntity(schema, map, "Squid");
        V0100.registerEntity(schema, map, "Wolf");
        V0100.registerEntity(schema, map, "MushroomCow");
        V0100.registerEntity(schema, map, "SnowMan");
        V0100.registerEntity(schema, map, "Ozelot");
        V0100.registerEntity(schema, map, "VillagerGolem");
        schema.register(map, "EntityHorse", arg_0 -> V0100.lambda$registerEntities$2(schema, arg_0));
        V0100.registerEntity(schema, map, "Rabbit");
        schema.register(map, "Villager", arg_0 -> V0100.lambda$registerEntities$3(schema, arg_0));
        V0100.registerEntity(schema, map, "Shulker");
        schema.registerSimple(map, "AreaEffectCloud");
        schema.registerSimple(map, "ShulkerBullet");
        return map;
    }

    @Override
    public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> map, Map<String, Supplier<TypeTemplate>> map2) {
        super.registerTypes(schema, map, map2);
        schema.registerType(false, TypeReferences.STRUCTURE, () -> V0100.lambda$registerTypes$4(schema));
        schema.registerType(false, TypeReferences.BLOCK_STATE, DSL::remainder);
    }

    private static TypeTemplate lambda$registerTypes$4(Schema schema) {
        return DSL.optionalFields("entities", DSL.list(DSL.optionalFields("nbt", TypeReferences.ENTITY_TYPE.in(schema))), "blocks", DSL.list(DSL.optionalFields("nbt", TypeReferences.BLOCK_ENTITY.in(schema))), "palette", DSL.list(TypeReferences.BLOCK_STATE.in(schema)));
    }

    private static TypeTemplate lambda$registerEntities$3(Schema schema, String string) {
        return DSL.optionalFields("Inventory", DSL.list(TypeReferences.ITEM_STACK.in(schema)), "Offers", DSL.optionalFields("Recipes", DSL.list(DSL.optionalFields("buy", TypeReferences.ITEM_STACK.in(schema), "buyB", TypeReferences.ITEM_STACK.in(schema), "sell", TypeReferences.ITEM_STACK.in(schema)))), V0100.equipment(schema));
    }

    private static TypeTemplate lambda$registerEntities$2(Schema schema, String string) {
        return DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(schema)), "ArmorItem", TypeReferences.ITEM_STACK.in(schema), "SaddleItem", TypeReferences.ITEM_STACK.in(schema), V0100.equipment(schema));
    }

    private static TypeTemplate lambda$registerEntities$1(Schema schema, String string) {
        return DSL.optionalFields("carried", TypeReferences.BLOCK_NAME.in(schema), V0100.equipment(schema));
    }

    private static TypeTemplate lambda$registerEntity$0(Schema schema) {
        return V0100.equipment(schema);
    }
}

