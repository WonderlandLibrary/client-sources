/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.BlockStateFlatteningMap;
import net.minecraft.util.datafix.fixes.BlockStateFlatternEntities;
import org.apache.commons.lang3.math.NumberUtils;

public class BlockStateFlattenGenOptions
extends DataFix {
    private static final Splitter field_199181_a = Splitter.on(';').limit(5);
    private static final Splitter field_199182_b = Splitter.on(',');
    private static final Splitter field_199183_c = Splitter.on('x').limit(2);
    private static final Splitter field_199184_d = Splitter.on('*').limit(2);
    private static final Splitter field_199185_e = Splitter.on(':').limit(3);

    public BlockStateFlattenGenOptions(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    public TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("LevelFlatGeneratorInfoFix", this.getInputSchema().getType(TypeReferences.LEVEL), this::lambda$makeRule$0);
    }

    private Dynamic<?> fix(Dynamic<?> dynamic) {
        return dynamic.get("generatorName").asString("").equalsIgnoreCase("flat") ? dynamic.update("generatorOptions", this::lambda$fix$2) : dynamic;
    }

    @VisibleForTesting
    String fixString(String string) {
        String string2;
        int n;
        if (string.isEmpty()) {
            return "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
        }
        Iterator<String> iterator2 = field_199181_a.split(string).iterator();
        String string3 = iterator2.next();
        if (iterator2.hasNext()) {
            n = NumberUtils.toInt(string3, 0);
            string2 = iterator2.next();
        } else {
            n = 0;
            string2 = string3;
        }
        if (n >= 0 && n <= 3) {
            StringBuilder stringBuilder = new StringBuilder();
            Splitter splitter = n < 3 ? field_199183_c : field_199184_d;
            stringBuilder.append(StreamSupport.stream(field_199182_b.split(string2).spliterator(), false).map(arg_0 -> BlockStateFlattenGenOptions.lambda$fixString$3(splitter, n, arg_0)).collect(Collectors.joining(",")));
            while (iterator2.hasNext()) {
                stringBuilder.append(';').append(iterator2.next());
            }
            return stringBuilder.toString();
        }
        return "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
    }

    private static String lambda$fixString$3(Splitter splitter, int n, String string) {
        String string2;
        int n2;
        List<String> list = splitter.splitToList(string);
        if (list.size() == 2) {
            n2 = NumberUtils.toInt(list.get(0));
            string2 = list.get(1);
        } else {
            n2 = 1;
            string2 = list.get(0);
        }
        List<String> list2 = field_199185_e.splitToList(string2);
        int n3 = list2.get(0).equals("minecraft") ? 1 : 0;
        String string3 = list2.get(n3);
        int n4 = n == 3 ? BlockStateFlatternEntities.getBlockId("minecraft:" + string3) : NumberUtils.toInt(string3, 0);
        int n5 = n3 + 1;
        int n6 = list2.size() > n5 ? NumberUtils.toInt(list2.get(n5), 0) : 0;
        return (String)(n2 == 1 ? "" : n2 + "*") + BlockStateFlatteningMap.getFixedNBTForID(n4 << 4 | n6).get("Name").asString("");
    }

    private Dynamic lambda$fix$2(Dynamic dynamic) {
        return DataFixUtils.orElse(dynamic.asString().map(this::fixString).map(arg_0 -> BlockStateFlattenGenOptions.lambda$fix$1(dynamic, arg_0)).result(), dynamic);
    }

    private static Dynamic lambda$fix$1(Dynamic dynamic, String string) {
        return dynamic.createString(string);
    }

    private Typed lambda$makeRule$0(Typed typed) {
        return typed.update(DSL.remainderFinder(), this::fix);
    }
}

