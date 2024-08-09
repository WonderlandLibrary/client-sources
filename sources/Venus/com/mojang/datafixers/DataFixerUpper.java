/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.functions.PointFreeRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataFixerUpper
implements DataFixer {
    public static boolean ERRORS_ARE_FATAL = false;
    private static final Logger LOGGER = LogManager.getLogger();
    protected static final PointFreeRule OPTIMIZATION_RULE = DataFixUtils.make(DataFixerUpper::lambda$static$5);
    private final Int2ObjectSortedMap<Schema> schemas;
    private final List<DataFix> globalList;
    private final IntSortedSet fixerVersions;
    private final Long2ObjectMap<TypeRewriteRule> rules = Long2ObjectMaps.synchronize(new Long2ObjectOpenHashMap());

    protected DataFixerUpper(Int2ObjectSortedMap<Schema> int2ObjectSortedMap, List<DataFix> list, IntSortedSet intSortedSet) {
        this.schemas = int2ObjectSortedMap;
        this.globalList = list;
        this.fixerVersions = intSortedSet;
    }

    @Override
    public <T> Dynamic<T> update(DSL.TypeReference typeReference, Dynamic<T> dynamic, int n, int n2) {
        if (n < n2) {
            Type<?> type = this.getType(typeReference, n);
            DataResult dataResult = type.readAndWrite(dynamic.getOps(), this.getType(typeReference, n2), this.getRule(n, n2), OPTIMIZATION_RULE, dynamic.getValue());
            Object t = dataResult.resultOrPartial(LOGGER::error).orElse(dynamic.getValue());
            return new Dynamic(dynamic.getOps(), t);
        }
        return dynamic;
    }

    @Override
    public Schema getSchema(int n) {
        return (Schema)this.schemas.get(DataFixerUpper.getLowestSchemaSameVersion(this.schemas, n));
    }

    protected Type<?> getType(DSL.TypeReference typeReference, int n) {
        return this.getSchema(DataFixUtils.makeKey(n)).getType(typeReference);
    }

    protected static int getLowestSchemaSameVersion(Int2ObjectSortedMap<Schema> int2ObjectSortedMap, int n) {
        if (n < int2ObjectSortedMap.firstIntKey()) {
            return int2ObjectSortedMap.firstIntKey();
        }
        return int2ObjectSortedMap.subMap(0, n + 1).lastIntKey();
    }

    private int getLowestFixSameVersion(int n) {
        if (n < this.fixerVersions.firstInt()) {
            return this.fixerVersions.firstInt() - 1;
        }
        return this.fixerVersions.subSet(0, n + 1).lastInt();
    }

    protected TypeRewriteRule getRule(int n, int n2) {
        if (n >= n2) {
            return TypeRewriteRule.nop();
        }
        int n3 = this.getLowestFixSameVersion(DataFixUtils.makeKey(n));
        int n4 = DataFixUtils.makeKey(n2);
        long l = (long)n3 << 32 | (long)n4;
        return (TypeRewriteRule)this.rules.computeIfAbsent((Object)l, arg_0 -> this.lambda$getRule$6(n3, n4, arg_0));
    }

    protected IntSortedSet fixerVersions() {
        return this.fixerVersions;
    }

    private TypeRewriteRule lambda$getRule$6(int n, int n2, Long l) {
        ArrayList<TypeRewriteRule> arrayList = Lists.newArrayList();
        for (DataFix dataFix : this.globalList) {
            TypeRewriteRule typeRewriteRule;
            int n3 = dataFix.getVersionKey();
            if (n3 <= n || n3 > n2 || (typeRewriteRule = dataFix.getRule()) == TypeRewriteRule.nop()) continue;
            arrayList.add(typeRewriteRule);
        }
        return TypeRewriteRule.seq(arrayList);
    }

    private static PointFreeRule lambda$static$5() {
        PointFreeRule pointFreeRule = PointFreeRule.orElse(PointFreeRule.orElse(PointFreeRule.CataFuseSame.INSTANCE, PointFreeRule.orElse(PointFreeRule.CataFuseDifferent.INSTANCE, PointFreeRule.LensAppId.INSTANCE)), PointFreeRule.orElse(PointFreeRule.LensComp.INSTANCE, PointFreeRule.orElse(PointFreeRule.AppNest.INSTANCE, PointFreeRule.LensCompFunc.INSTANCE)));
        PointFreeRule pointFreeRule2 = PointFreeRule.many(PointFreeRule.once(PointFreeRule.orElse(pointFreeRule, PointFreeRule.CompAssocLeft.INSTANCE)));
        PointFreeRule pointFreeRule3 = PointFreeRule.many(PointFreeRule.once(PointFreeRule.orElse(PointFreeRule.SortInj.INSTANCE, PointFreeRule.SortProj.INSTANCE)));
        PointFreeRule pointFreeRule4 = PointFreeRule.many(PointFreeRule.once(PointFreeRule.orElse(pointFreeRule, PointFreeRule.CompAssocRight.INSTANCE)));
        return PointFreeRule.seq(ImmutableList.of(() -> DataFixerUpper.lambda$null$0(pointFreeRule2), () -> DataFixerUpper.lambda$null$1(pointFreeRule3), () -> DataFixerUpper.lambda$null$2(pointFreeRule4), () -> DataFixerUpper.lambda$null$3(pointFreeRule2), () -> DataFixerUpper.lambda$null$4(pointFreeRule4)));
    }

    private static PointFreeRule lambda$null$4(PointFreeRule pointFreeRule) {
        return pointFreeRule;
    }

    private static PointFreeRule lambda$null$3(PointFreeRule pointFreeRule) {
        return pointFreeRule;
    }

    private static PointFreeRule lambda$null$2(PointFreeRule pointFreeRule) {
        return pointFreeRule;
    }

    private static PointFreeRule lambda$null$1(PointFreeRule pointFreeRule) {
        return pointFreeRule;
    }

    private static PointFreeRule lambda$null$0(PointFreeRule pointFreeRule) {
        return pointFreeRule;
    }
}

