/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;
import net.minecraft.util.datafix.TypeReferences;

public class BiomeIdFix
extends DataFix {
    public BiomeIdFix(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getType(TypeReferences.CHUNK);
        OpticFinder<?> opticFinder = type.findField("Level");
        return this.fixTypeEverywhereTyped("Leaves fix", type, arg_0 -> BiomeIdFix.lambda$makeRule$2(opticFinder, arg_0));
    }

    private static Typed lambda$makeRule$2(OpticFinder opticFinder, Typed typed) {
        return typed.updateTyped(opticFinder, BiomeIdFix::lambda$makeRule$1);
    }

    private static Typed lambda$makeRule$1(Typed typed) {
        return typed.update(DSL.remainderFinder(), BiomeIdFix::lambda$makeRule$0);
    }

    private static Dynamic lambda$makeRule$0(Dynamic dynamic) {
        int n;
        Optional<IntStream> optional = dynamic.get("Biomes").asIntStreamOpt().result();
        if (!optional.isPresent()) {
            return dynamic;
        }
        int[] nArray = optional.get().toArray();
        int[] nArray2 = new int[1024];
        for (n = 0; n < 4; ++n) {
            for (int i = 0; i < 4; ++i) {
                int n2 = (n << 2) + 2;
                int n3 = (i << 2) + 2;
                int n4 = n2 << 4 | n3;
                nArray2[n << 2 | i] = n4 < nArray.length ? nArray[n4] : -1;
            }
        }
        for (n = 1; n < 64; ++n) {
            System.arraycopy(nArray2, 0, nArray2, n * 16, 16);
        }
        return dynamic.set("Biomes", dynamic.createIntList(Arrays.stream(nArray2)));
    }
}

