/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers;

import com.google.common.collect.Lists;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.DataFixerUpper;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap;
import it.unimi.dsi.fastutil.ints.IntAVLTreeSet;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataFixerBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    private final int dataVersion;
    private final Int2ObjectSortedMap<Schema> schemas = new Int2ObjectAVLTreeMap<Schema>();
    private final List<DataFix> globalList = Lists.newArrayList();
    private final IntSortedSet fixerVersions = new IntAVLTreeSet();

    public DataFixerBuilder(int n) {
        this.dataVersion = n;
    }

    public Schema addSchema(int n, BiFunction<Integer, Schema, Schema> biFunction) {
        return this.addSchema(n, 0, biFunction);
    }

    public Schema addSchema(int n, int n2, BiFunction<Integer, Schema, Schema> biFunction) {
        int n3 = DataFixUtils.makeKey(n, n2);
        Schema schema = this.schemas.isEmpty() ? null : (Schema)this.schemas.get(DataFixerUpper.getLowestSchemaSameVersion(this.schemas, n3 - 1));
        Schema schema2 = biFunction.apply(DataFixUtils.makeKey(n, n2), schema);
        this.addSchema(schema2);
        return schema2;
    }

    public void addSchema(Schema schema) {
        this.schemas.put(schema.getVersionKey(), schema);
    }

    public void addFixer(DataFix dataFix) {
        int n = DataFixUtils.getVersion(dataFix.getVersionKey());
        if (n > this.dataVersion) {
            LOGGER.warn("Ignored fix registered for version: {} as the DataVersion of the game is: {}", (Object)n, (Object)this.dataVersion);
            return;
        }
        this.globalList.add(dataFix);
        this.fixerVersions.add(dataFix.getVersionKey());
    }

    public DataFixer build(Executor executor) {
        DataFixerUpper dataFixerUpper = new DataFixerUpper(new Int2ObjectAVLTreeMap<Schema>(this.schemas), new ArrayList<DataFix>(this.globalList), new IntAVLTreeSet(this.fixerVersions));
        IntBidirectionalIterator intBidirectionalIterator = dataFixerUpper.fixerVersions().iterator();
        while (intBidirectionalIterator.hasNext()) {
            int n = intBidirectionalIterator.nextInt();
            Schema schema = (Schema)this.schemas.get(n);
            for (String string : schema.types()) {
                CompletableFuture.runAsync(() -> this.lambda$build$1(schema, string, dataFixerUpper, n), executor).exceptionally(DataFixerBuilder::lambda$build$2);
            }
        }
        return dataFixerUpper;
    }

    private static Void lambda$build$2(Throwable throwable) {
        LOGGER.error("Unable to build datafixers", throwable);
        Runtime.getRuntime().exit(1);
        return null;
    }

    private void lambda$build$1(Schema schema, String string, DataFixerUpper dataFixerUpper, int n) {
        Type<?> type = schema.getType(() -> DataFixerBuilder.lambda$null$0(string));
        TypeRewriteRule typeRewriteRule = dataFixerUpper.getRule(DataFixUtils.getVersion(n), this.dataVersion);
        type.rewrite(typeRewriteRule, DataFixerUpper.OPTIMIZATION_RULE);
    }

    private static String lambda$null$0(String string) {
        return string;
    }
}

