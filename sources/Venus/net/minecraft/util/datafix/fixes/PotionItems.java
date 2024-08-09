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
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;

public class PotionItems
extends DataFix {
    private static final String[] POTION_IDS = DataFixUtils.make(new String[128], PotionItems::lambda$static$0);

    public PotionItems(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    public TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
        OpticFinder<Pair<String, String>> opticFinder = DSL.fieldFinder("id", DSL.named(TypeReferences.ITEM_NAME.typeName(), NamespacedSchema.func_233457_a_()));
        OpticFinder<?> opticFinder2 = type.findField("tag");
        return this.fixTypeEverywhereTyped("ItemPotionFix", type, arg_0 -> PotionItems.lambda$makeRule$1(opticFinder, opticFinder2, arg_0));
    }

    private static Typed lambda$makeRule$1(OpticFinder opticFinder, OpticFinder opticFinder2, Typed typed) {
        Optional optional = typed.getOptional(opticFinder);
        if (optional.isPresent() && Objects.equals(((Pair)optional.get()).getSecond(), "minecraft:potion")) {
            Dynamic dynamic = typed.get(DSL.remainderFinder());
            Optional optional2 = typed.getOptionalTyped(opticFinder2);
            short s = dynamic.get("Damage").asShort((short)0);
            if (optional2.isPresent()) {
                Typed<?> typed2 = typed;
                Dynamic dynamic2 = optional2.get().get(DSL.remainderFinder());
                Optional<String> optional3 = dynamic2.get("Potion").asString().result();
                if (!optional3.isPresent()) {
                    String string = POTION_IDS[s & 0x7F];
                    Typed<?> typed3 = optional2.get().set(DSL.remainderFinder(), dynamic2.set("Potion", dynamic2.createString(string == null ? "minecraft:water" : string)));
                    typed2 = typed.set(opticFinder2, typed3);
                    if ((s & 0x4000) == 16384) {
                        typed2 = typed2.set(opticFinder, Pair.of(TypeReferences.ITEM_NAME.typeName(), "minecraft:splash_potion"));
                    }
                }
                if (s != 0) {
                    dynamic = dynamic.set("Damage", dynamic.createShort((short)0));
                }
                return typed2.set(DSL.remainderFinder(), dynamic);
            }
        }
        return typed;
    }

    private static void lambda$static$0(String[] stringArray) {
        stringArray[0] = "minecraft:water";
        stringArray[1] = "minecraft:regeneration";
        stringArray[2] = "minecraft:swiftness";
        stringArray[3] = "minecraft:fire_resistance";
        stringArray[4] = "minecraft:poison";
        stringArray[5] = "minecraft:healing";
        stringArray[6] = "minecraft:night_vision";
        stringArray[7] = null;
        stringArray[8] = "minecraft:weakness";
        stringArray[9] = "minecraft:strength";
        stringArray[10] = "minecraft:slowness";
        stringArray[11] = "minecraft:leaping";
        stringArray[12] = "minecraft:harming";
        stringArray[13] = "minecraft:water_breathing";
        stringArray[14] = "minecraft:invisibility";
        stringArray[15] = null;
        stringArray[16] = "minecraft:awkward";
        stringArray[17] = "minecraft:regeneration";
        stringArray[18] = "minecraft:swiftness";
        stringArray[19] = "minecraft:fire_resistance";
        stringArray[20] = "minecraft:poison";
        stringArray[21] = "minecraft:healing";
        stringArray[22] = "minecraft:night_vision";
        stringArray[23] = null;
        stringArray[24] = "minecraft:weakness";
        stringArray[25] = "minecraft:strength";
        stringArray[26] = "minecraft:slowness";
        stringArray[27] = "minecraft:leaping";
        stringArray[28] = "minecraft:harming";
        stringArray[29] = "minecraft:water_breathing";
        stringArray[30] = "minecraft:invisibility";
        stringArray[31] = null;
        stringArray[32] = "minecraft:thick";
        stringArray[33] = "minecraft:strong_regeneration";
        stringArray[34] = "minecraft:strong_swiftness";
        stringArray[35] = "minecraft:fire_resistance";
        stringArray[36] = "minecraft:strong_poison";
        stringArray[37] = "minecraft:strong_healing";
        stringArray[38] = "minecraft:night_vision";
        stringArray[39] = null;
        stringArray[40] = "minecraft:weakness";
        stringArray[41] = "minecraft:strong_strength";
        stringArray[42] = "minecraft:slowness";
        stringArray[43] = "minecraft:strong_leaping";
        stringArray[44] = "minecraft:strong_harming";
        stringArray[45] = "minecraft:water_breathing";
        stringArray[46] = "minecraft:invisibility";
        stringArray[47] = null;
        stringArray[48] = null;
        stringArray[49] = "minecraft:strong_regeneration";
        stringArray[50] = "minecraft:strong_swiftness";
        stringArray[51] = "minecraft:fire_resistance";
        stringArray[52] = "minecraft:strong_poison";
        stringArray[53] = "minecraft:strong_healing";
        stringArray[54] = "minecraft:night_vision";
        stringArray[55] = null;
        stringArray[56] = "minecraft:weakness";
        stringArray[57] = "minecraft:strong_strength";
        stringArray[58] = "minecraft:slowness";
        stringArray[59] = "minecraft:strong_leaping";
        stringArray[60] = "minecraft:strong_harming";
        stringArray[61] = "minecraft:water_breathing";
        stringArray[62] = "minecraft:invisibility";
        stringArray[63] = null;
        stringArray[64] = "minecraft:mundane";
        stringArray[65] = "minecraft:long_regeneration";
        stringArray[66] = "minecraft:long_swiftness";
        stringArray[67] = "minecraft:long_fire_resistance";
        stringArray[68] = "minecraft:long_poison";
        stringArray[69] = "minecraft:healing";
        stringArray[70] = "minecraft:long_night_vision";
        stringArray[71] = null;
        stringArray[72] = "minecraft:long_weakness";
        stringArray[73] = "minecraft:long_strength";
        stringArray[74] = "minecraft:long_slowness";
        stringArray[75] = "minecraft:long_leaping";
        stringArray[76] = "minecraft:harming";
        stringArray[77] = "minecraft:long_water_breathing";
        stringArray[78] = "minecraft:long_invisibility";
        stringArray[79] = null;
        stringArray[80] = "minecraft:awkward";
        stringArray[81] = "minecraft:long_regeneration";
        stringArray[82] = "minecraft:long_swiftness";
        stringArray[83] = "minecraft:long_fire_resistance";
        stringArray[84] = "minecraft:long_poison";
        stringArray[85] = "minecraft:healing";
        stringArray[86] = "minecraft:long_night_vision";
        stringArray[87] = null;
        stringArray[88] = "minecraft:long_weakness";
        stringArray[89] = "minecraft:long_strength";
        stringArray[90] = "minecraft:long_slowness";
        stringArray[91] = "minecraft:long_leaping";
        stringArray[92] = "minecraft:harming";
        stringArray[93] = "minecraft:long_water_breathing";
        stringArray[94] = "minecraft:long_invisibility";
        stringArray[95] = null;
        stringArray[96] = "minecraft:thick";
        stringArray[97] = "minecraft:regeneration";
        stringArray[98] = "minecraft:swiftness";
        stringArray[99] = "minecraft:long_fire_resistance";
        stringArray[100] = "minecraft:poison";
        stringArray[101] = "minecraft:strong_healing";
        stringArray[102] = "minecraft:long_night_vision";
        stringArray[103] = null;
        stringArray[104] = "minecraft:long_weakness";
        stringArray[105] = "minecraft:strength";
        stringArray[106] = "minecraft:long_slowness";
        stringArray[107] = "minecraft:leaping";
        stringArray[108] = "minecraft:strong_harming";
        stringArray[109] = "minecraft:long_water_breathing";
        stringArray[110] = "minecraft:long_invisibility";
        stringArray[111] = null;
        stringArray[112] = null;
        stringArray[113] = "minecraft:regeneration";
        stringArray[114] = "minecraft:swiftness";
        stringArray[115] = "minecraft:long_fire_resistance";
        stringArray[116] = "minecraft:poison";
        stringArray[117] = "minecraft:strong_healing";
        stringArray[118] = "minecraft:long_night_vision";
        stringArray[119] = null;
        stringArray[120] = "minecraft:long_weakness";
        stringArray[121] = "minecraft:strength";
        stringArray[122] = "minecraft:long_slowness";
        stringArray[123] = "minecraft:leaping";
        stringArray[124] = "minecraft:strong_harming";
        stringArray[125] = "minecraft:long_water_breathing";
        stringArray[126] = "minecraft:long_invisibility";
        stringArray[127] = null;
    }
}

