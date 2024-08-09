/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.util.datafix.TypeReferences;

public class MapIdFix
extends DataFix {
    public MapIdFix(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getType(TypeReferences.SAVED_DATA);
        OpticFinder<?> opticFinder = type.findField("data");
        return this.fixTypeEverywhereTyped("Map id fix", type, arg_0 -> MapIdFix.lambda$makeRule$1(opticFinder, arg_0));
    }

    private static Typed lambda$makeRule$1(OpticFinder opticFinder, Typed typed) {
        Optional optional = typed.getOptionalTyped(opticFinder);
        return optional.isPresent() ? typed : typed.update(DSL.remainderFinder(), MapIdFix::lambda$makeRule$0);
    }

    private static Dynamic lambda$makeRule$0(Dynamic dynamic) {
        return dynamic.createMap(ImmutableMap.of(dynamic.createString("data"), dynamic));
    }
}

