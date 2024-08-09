/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;

public class SpawnEggNames
extends DataFix {
    private static final String[] ENTITY_IDS = DataFixUtils.make(new String[256], SpawnEggNames::lambda$static$0);

    public SpawnEggNames(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    public TypeRewriteRule makeRule() {
        Schema schema = this.getInputSchema();
        Type<?> type = schema.getType(TypeReferences.ITEM_STACK);
        OpticFinder<Pair<String, String>> opticFinder = DSL.fieldFinder("id", DSL.named(TypeReferences.ITEM_NAME.typeName(), NamespacedSchema.func_233457_a_()));
        OpticFinder<String> opticFinder2 = DSL.fieldFinder("id", DSL.string());
        OpticFinder<?> opticFinder3 = type.findField("tag");
        OpticFinder<?> opticFinder4 = opticFinder3.type().findField("EntityTag");
        OpticFinder<?> opticFinder5 = DSL.typeFinder(schema.getTypeRaw(TypeReferences.ENTITY));
        Type<?> type2 = this.getOutputSchema().getTypeRaw(TypeReferences.ENTITY);
        return this.fixTypeEverywhereTyped("ItemSpawnEggFix", type, arg_0 -> SpawnEggNames.lambda$makeRule$6(opticFinder, opticFinder3, opticFinder4, opticFinder5, opticFinder2, type2, arg_0));
    }

    private static Typed lambda$makeRule$6(OpticFinder opticFinder, OpticFinder opticFinder2, OpticFinder opticFinder3, OpticFinder opticFinder4, OpticFinder opticFinder5, Type type, Typed typed) {
        Optional optional = typed.getOptional(opticFinder);
        if (optional.isPresent() && Objects.equals(((Pair)optional.get()).getSecond(), "minecraft:spawn_egg")) {
            Dynamic dynamic = typed.get(DSL.remainderFinder());
            short s = dynamic.get("Damage").asShort((short)0);
            Optional optional2 = typed.getOptionalTyped(opticFinder2);
            Optional optional3 = optional2.flatMap(arg_0 -> SpawnEggNames.lambda$makeRule$1(opticFinder3, arg_0));
            Optional optional4 = optional3.flatMap(arg_0 -> SpawnEggNames.lambda$makeRule$2(opticFinder4, arg_0));
            Optional optional5 = optional4.flatMap(arg_0 -> SpawnEggNames.lambda$makeRule$3(opticFinder5, arg_0));
            Typed<?> typed2 = typed;
            String string = ENTITY_IDS[s & 0xFF];
            if (!(string == null || optional5.isPresent() && Objects.equals(optional5.get(), string))) {
                Typed typed3 = typed.getOrCreateTyped(opticFinder2);
                Typed typed4 = typed3.getOrCreateTyped(opticFinder3);
                Typed typed5 = typed4.getOrCreateTyped(opticFinder4);
                Dynamic dynamic2 = dynamic;
                Typed typed6 = (Typed)((Pair)typed5.write().flatMap(arg_0 -> SpawnEggNames.lambda$makeRule$4(type, dynamic2, string, arg_0)).result().orElseThrow(SpawnEggNames::lambda$makeRule$5)).getFirst();
                typed2 = typed.set(opticFinder2, typed3.set(opticFinder3, typed4.set(opticFinder4, typed6)));
            }
            if (s != 0) {
                dynamic = dynamic.set("Damage", dynamic.createShort((short)0));
                typed2 = typed2.set(DSL.remainderFinder(), dynamic);
            }
            return typed2;
        }
        return typed;
    }

    private static IllegalStateException lambda$makeRule$5() {
        return new IllegalStateException("Could not parse new entity");
    }

    private static DataResult lambda$makeRule$4(Type type, Dynamic dynamic, String string, Dynamic dynamic2) {
        return type.readTyped(dynamic2.set("id", dynamic.createString(string)));
    }

    private static Optional lambda$makeRule$3(OpticFinder opticFinder, Typed typed) {
        return typed.getOptional(opticFinder);
    }

    private static Optional lambda$makeRule$2(OpticFinder opticFinder, Typed typed) {
        return typed.getOptionalTyped(opticFinder);
    }

    private static Optional lambda$makeRule$1(OpticFinder opticFinder, Typed typed) {
        return typed.getOptionalTyped(opticFinder);
    }

    private static void lambda$static$0(String[] stringArray) {
        stringArray[1] = "Item";
        stringArray[2] = "XPOrb";
        stringArray[7] = "ThrownEgg";
        stringArray[8] = "LeashKnot";
        stringArray[9] = "Painting";
        stringArray[10] = "Arrow";
        stringArray[11] = "Snowball";
        stringArray[12] = "Fireball";
        stringArray[13] = "SmallFireball";
        stringArray[14] = "ThrownEnderpearl";
        stringArray[15] = "EyeOfEnderSignal";
        stringArray[16] = "ThrownPotion";
        stringArray[17] = "ThrownExpBottle";
        stringArray[18] = "ItemFrame";
        stringArray[19] = "WitherSkull";
        stringArray[20] = "PrimedTnt";
        stringArray[21] = "FallingSand";
        stringArray[22] = "FireworksRocketEntity";
        stringArray[23] = "TippedArrow";
        stringArray[24] = "SpectralArrow";
        stringArray[25] = "ShulkerBullet";
        stringArray[26] = "DragonFireball";
        stringArray[30] = "ArmorStand";
        stringArray[41] = "Boat";
        stringArray[42] = "MinecartRideable";
        stringArray[43] = "MinecartChest";
        stringArray[44] = "MinecartFurnace";
        stringArray[45] = "MinecartTNT";
        stringArray[46] = "MinecartHopper";
        stringArray[47] = "MinecartSpawner";
        stringArray[40] = "MinecartCommandBlock";
        stringArray[48] = "Mob";
        stringArray[49] = "Monster";
        stringArray[50] = "Creeper";
        stringArray[51] = "Skeleton";
        stringArray[52] = "Spider";
        stringArray[53] = "Giant";
        stringArray[54] = "Zombie";
        stringArray[55] = "Slime";
        stringArray[56] = "Ghast";
        stringArray[57] = "PigZombie";
        stringArray[58] = "Enderman";
        stringArray[59] = "CaveSpider";
        stringArray[60] = "Silverfish";
        stringArray[61] = "Blaze";
        stringArray[62] = "LavaSlime";
        stringArray[63] = "EnderDragon";
        stringArray[64] = "WitherBoss";
        stringArray[65] = "Bat";
        stringArray[66] = "Witch";
        stringArray[67] = "Endermite";
        stringArray[68] = "Guardian";
        stringArray[69] = "Shulker";
        stringArray[90] = "Pig";
        stringArray[91] = "Sheep";
        stringArray[92] = "Cow";
        stringArray[93] = "Chicken";
        stringArray[94] = "Squid";
        stringArray[95] = "Wolf";
        stringArray[96] = "MushroomCow";
        stringArray[97] = "SnowMan";
        stringArray[98] = "Ozelot";
        stringArray[99] = "VillagerGolem";
        stringArray[100] = "EntityHorse";
        stringArray[101] = "Rabbit";
        stringArray[120] = "Villager";
        stringArray[200] = "EnderCrystal";
    }
}

