/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.NamedEntityFix;

public class VillagerProfessionFix
extends NamedEntityFix {
    public VillagerProfessionFix(Schema schema, String string) {
        super(schema, false, "Villager profession data fix (" + string + ")", TypeReferences.ENTITY, string);
    }

    @Override
    protected Typed<?> fix(Typed<?> typed) {
        Dynamic dynamic = typed.get(DSL.remainderFinder());
        return typed.set(DSL.remainderFinder(), dynamic.remove("Profession").remove("Career").remove("CareerLevel").set("VillagerData", dynamic.createMap(ImmutableMap.of(dynamic.createString("type"), dynamic.createString("minecraft:plains"), dynamic.createString("profession"), dynamic.createString(VillagerProfessionFix.func_219811_a(dynamic.get("Profession").asInt(0), dynamic.get("Career").asInt(0))), dynamic.createString("level"), DataFixUtils.orElse(dynamic.get("CareerLevel").result(), dynamic.createInt(1))))));
    }

    private static String func_219811_a(int n, int n2) {
        if (n == 0) {
            if (n2 == 2) {
                return "minecraft:fisherman";
            }
            if (n2 == 3) {
                return "minecraft:shepherd";
            }
            return n2 == 4 ? "minecraft:fletcher" : "minecraft:farmer";
        }
        if (n == 1) {
            return n2 == 2 ? "minecraft:cartographer" : "minecraft:librarian";
        }
        if (n == 2) {
            return "minecraft:cleric";
        }
        if (n == 3) {
            if (n2 == 2) {
                return "minecraft:weaponsmith";
            }
            return n2 == 3 ? "minecraft:toolsmith" : "minecraft:armorer";
        }
        if (n == 4) {
            return n2 == 2 ? "minecraft:leatherworker" : "minecraft:butcher";
        }
        return n == 5 ? "minecraft:nitwit" : "minecraft:none";
    }
}

