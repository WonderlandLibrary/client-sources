/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.NamedEntityFix;

public class RemoveGolemGossip
extends NamedEntityFix {
    public RemoveGolemGossip(Schema schema, boolean bl) {
        super(schema, bl, "Remove Golem Gossip Fix", TypeReferences.ENTITY, "minecraft:villager");
    }

    @Override
    protected Typed<?> fix(Typed<?> typed) {
        return typed.update(DSL.remainderFinder(), RemoveGolemGossip::func_242266_a);
    }

    private static Dynamic<?> func_242266_a(Dynamic<?> dynamic) {
        return dynamic.update("Gossips", arg_0 -> RemoveGolemGossip.lambda$func_242266_a$1(dynamic, arg_0));
    }

    private static Dynamic lambda$func_242266_a$1(Dynamic dynamic, Dynamic dynamic2) {
        return dynamic.createList(dynamic2.asStream().filter(RemoveGolemGossip::lambda$func_242266_a$0));
    }

    private static boolean lambda$func_242266_a$0(Dynamic dynamic) {
        return !dynamic.get("Type").asString("").equals("golem");
    }
}

