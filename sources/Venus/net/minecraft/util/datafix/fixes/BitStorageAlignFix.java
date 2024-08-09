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
import com.mojang.datafixers.types.templates.List;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.stream.LongStream;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.math.MathHelper;

public class BitStorageAlignFix
extends DataFix {
    public BitStorageAlignFix(Schema schema) {
        super(schema, false);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getType(TypeReferences.CHUNK);
        Type<?> type2 = type.findFieldType("Level");
        OpticFinder<?> opticFinder = DSL.fieldFinder("Level", type2);
        OpticFinder<?> opticFinder2 = opticFinder.type().findField("Sections");
        Type type3 = ((List.ListType)opticFinder2.type()).getElement();
        OpticFinder opticFinder3 = DSL.typeFinder(type3);
        Type<Pair<String, Dynamic<?>>> type4 = DSL.named(TypeReferences.BLOCK_STATE.typeName(), DSL.remainderType());
        OpticFinder<Pair<String, Dynamic<?>>> opticFinder4 = DSL.fieldFinder("Palette", DSL.list(type4));
        return this.fixTypeEverywhereTyped("BitStorageAlignFix", type, this.getOutputSchema().getType(TypeReferences.CHUNK), arg_0 -> this.lambda$makeRule$1(opticFinder, opticFinder2, opticFinder3, opticFinder4, arg_0));
    }

    private Typed<?> func_233092_a_(Typed<?> typed) {
        return typed.update(DSL.remainderFinder(), BitStorageAlignFix::lambda$func_233092_a_$5);
    }

    private static Typed<?> func_233089_a_(OpticFinder<?> opticFinder, OpticFinder<?> opticFinder2, OpticFinder<List<Pair<String, Dynamic<?>>>> opticFinder3, Typed<?> typed) {
        return typed.updateTyped(opticFinder, arg_0 -> BitStorageAlignFix.lambda$func_233089_a_$10(opticFinder2, opticFinder3, arg_0));
    }

    private static Dynamic<?> func_233097_a_(Dynamic<?> dynamic, Dynamic<?> dynamic2, int n, int n2) {
        long[] lArray = dynamic2.asLongStream().toArray();
        long[] lArray2 = BitStorageAlignFix.func_233086_a_(n, n2, lArray);
        return dynamic.createLongList(LongStream.of(lArray2));
    }

    public static long[] func_233086_a_(int n, int n2, long[] lArray) {
        int n3 = lArray.length;
        if (n3 == 0) {
            return lArray;
        }
        long l = (1L << n2) - 1L;
        int n4 = 64 / n2;
        int n5 = (n + n4 - 1) / n4;
        long[] lArray2 = new long[n5];
        int n6 = 0;
        int n7 = 0;
        long l2 = 0L;
        int n8 = 0;
        long l3 = lArray[0];
        long l4 = n3 > 1 ? lArray[1] : 0L;
        for (int i = 0; i < n; ++i) {
            int n9;
            long l5;
            int n10 = i * n2;
            int n11 = n10 >> 6;
            int n12 = (i + 1) * n2 - 1 >> 6;
            int n13 = n10 ^ n11 << 6;
            if (n11 != n8) {
                l3 = l4;
                l4 = n11 + 1 < n3 ? lArray[n11 + 1] : 0L;
                n8 = n11;
            }
            if (n11 == n12) {
                l5 = l3 >>> n13 & l;
            } else {
                n9 = 64 - n13;
                l5 = (l3 >>> n13 | l4 << n9) & l;
            }
            n9 = n7 + n2;
            if (n9 >= 64) {
                lArray2[n6++] = l2;
                l2 = l5;
                n7 = n2;
                continue;
            }
            l2 |= l5 << n7;
            n7 = n9;
        }
        if (l2 != 0L) {
            lArray2[n6] = l2;
        }
        return lArray2;
    }

    private static Typed lambda$func_233089_a_$10(OpticFinder opticFinder, OpticFinder opticFinder2, Typed typed) {
        return typed.updateTyped(opticFinder, arg_0 -> BitStorageAlignFix.lambda$func_233089_a_$9(opticFinder2, arg_0));
    }

    private static Typed lambda$func_233089_a_$9(OpticFinder opticFinder, Typed typed) {
        int n = typed.getOptional(opticFinder).map(BitStorageAlignFix::lambda$func_233089_a_$6).orElse(0);
        return n != 0 && !MathHelper.isPowerOfTwo(n) ? typed.update(DSL.remainderFinder(), arg_0 -> BitStorageAlignFix.lambda$func_233089_a_$8(n, arg_0)) : typed;
    }

    private static Dynamic lambda$func_233089_a_$8(int n, Dynamic dynamic) {
        return dynamic.update("BlockStates", arg_0 -> BitStorageAlignFix.lambda$func_233089_a_$7(dynamic, n, arg_0));
    }

    private static Dynamic lambda$func_233089_a_$7(Dynamic dynamic, int n, Dynamic dynamic2) {
        return BitStorageAlignFix.func_233097_a_(dynamic, dynamic2, 4096, n);
    }

    private static Integer lambda$func_233089_a_$6(List list) {
        return Math.max(4, DataFixUtils.ceillog2(list.size()));
    }

    private static Dynamic lambda$func_233092_a_$5(Dynamic dynamic) {
        return dynamic.update("Heightmaps", arg_0 -> BitStorageAlignFix.lambda$func_233092_a_$4(dynamic, arg_0));
    }

    private static Dynamic lambda$func_233092_a_$4(Dynamic dynamic, Dynamic dynamic2) {
        return dynamic2.updateMapValues(arg_0 -> BitStorageAlignFix.lambda$func_233092_a_$3(dynamic, arg_0));
    }

    private static Pair lambda$func_233092_a_$3(Dynamic dynamic, Pair pair) {
        return pair.mapSecond(arg_0 -> BitStorageAlignFix.lambda$func_233092_a_$2(dynamic, arg_0));
    }

    private static Dynamic lambda$func_233092_a_$2(Dynamic dynamic, Dynamic dynamic2) {
        return BitStorageAlignFix.func_233097_a_(dynamic, dynamic2, 256, 9);
    }

    private Typed lambda$makeRule$1(OpticFinder opticFinder, OpticFinder opticFinder2, OpticFinder opticFinder3, OpticFinder opticFinder4, Typed typed) {
        return typed.updateTyped(opticFinder, arg_0 -> this.lambda$makeRule$0(opticFinder2, opticFinder3, opticFinder4, arg_0));
    }

    private Typed lambda$makeRule$0(OpticFinder opticFinder, OpticFinder opticFinder2, OpticFinder opticFinder3, Typed typed) {
        return this.func_233092_a_(BitStorageAlignFix.func_233089_a_(opticFinder, opticFinder2, opticFinder3, typed));
    }
}

