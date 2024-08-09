/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.datafix.fixes.VillagerLevelAndXpFix;

public class ZombieVillagerXpFix
extends NamedEntityFix {
    public ZombieVillagerXpFix(Schema schema, boolean bl) {
        super(schema, bl, "Zombie Villager XP rebuild", TypeReferences.ENTITY, "minecraft:zombie_villager");
    }

    @Override
    protected Typed<?> fix(Typed<?> typed) {
        return typed.update(DSL.remainderFinder(), ZombieVillagerXpFix::lambda$fix$0);
    }

    private static Dynamic lambda$fix$0(Dynamic dynamic) {
        Optional<Number> optional = dynamic.get("Xp").asNumber().result();
        if (!optional.isPresent()) {
            int n = dynamic.get("VillagerData").get("level").asInt(1);
            return dynamic.set("Xp", dynamic.createInt(VillagerLevelAndXpFix.func_223001_a(n)));
        }
        return dynamic;
    }
}

