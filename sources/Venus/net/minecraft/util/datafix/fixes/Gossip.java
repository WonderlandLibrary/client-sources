/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.stream.Stream;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.AbstractUUIDFix;
import net.minecraft.util.datafix.fixes.NamedEntityFix;

public class Gossip
extends NamedEntityFix {
    public Gossip(Schema schema, String string) {
        super(schema, false, "Gossip for for " + string, TypeReferences.ENTITY, string);
    }

    @Override
    protected Typed<?> fix(Typed<?> typed) {
        return typed.update(DSL.remainderFinder(), Gossip::lambda$fix$3);
    }

    private static Dynamic lambda$fix$3(Dynamic dynamic) {
        return dynamic.update("Gossips", Gossip::lambda$fix$2);
    }

    private static Dynamic lambda$fix$2(Dynamic dynamic) {
        return DataFixUtils.orElse(dynamic.asStreamOpt().result().map(Gossip::lambda$fix$1).map(dynamic::createList), dynamic);
    }

    private static Stream lambda$fix$1(Stream stream) {
        return stream.map(Gossip::lambda$fix$0);
    }

    private static Dynamic lambda$fix$0(Dynamic dynamic) {
        return AbstractUUIDFix.func_233064_c_(dynamic, "Target", "Target").orElse(dynamic);
    }
}

