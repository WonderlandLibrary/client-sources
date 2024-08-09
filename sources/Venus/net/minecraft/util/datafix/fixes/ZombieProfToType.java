/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Random;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.NamedEntityFix;

public class ZombieProfToType
extends NamedEntityFix {
    private static final Random RANDOM = new Random();

    public ZombieProfToType(Schema schema, boolean bl) {
        super(schema, bl, "EntityZombieVillagerTypeFix", TypeReferences.ENTITY, "Zombie");
    }

    public Dynamic<?> fixTag(Dynamic<?> dynamic) {
        if (dynamic.get("IsVillager").asBoolean(true)) {
            if (!dynamic.get("ZombieType").result().isPresent()) {
                int n = this.getVillagerProfession(dynamic.get("VillagerProfession").asInt(-1));
                if (n == -1) {
                    n = this.getVillagerProfession(RANDOM.nextInt(6));
                }
                dynamic = dynamic.set("ZombieType", dynamic.createInt(n));
            }
            dynamic = dynamic.remove("IsVillager");
        }
        return dynamic;
    }

    private int getVillagerProfession(int n) {
        return n >= 0 && n < 6 ? n : -1;
    }

    @Override
    protected Typed<?> fix(Typed<?> typed) {
        return typed.update(DSL.remainderFinder(), this::fixTag);
    }
}

