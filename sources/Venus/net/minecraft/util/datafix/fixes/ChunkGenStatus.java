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
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.datafix.TypeReferences;

public class ChunkGenStatus
extends DataFix {
    public ChunkGenStatus(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    public TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getType(TypeReferences.CHUNK);
        Type<?> type2 = this.getOutputSchema().getType(TypeReferences.CHUNK);
        Type<?> type3 = type.findFieldType("Level");
        Type<?> type4 = type2.findFieldType("Level");
        Type<?> type5 = type3.findFieldType("TileTicks");
        OpticFinder<?> opticFinder = DSL.fieldFinder("Level", type3);
        OpticFinder<?> opticFinder2 = DSL.fieldFinder("TileTicks", type5);
        return TypeRewriteRule.seq(this.fixTypeEverywhereTyped("ChunkToProtoChunkFix", type, this.getOutputSchema().getType(TypeReferences.CHUNK), arg_0 -> ChunkGenStatus.lambda$makeRule$7(opticFinder, type4, opticFinder2, arg_0)), this.writeAndRead("Structure biome inject", this.getInputSchema().getType(TypeReferences.STRUCTURE_FEATURE), this.getOutputSchema().getType(TypeReferences.STRUCTURE_FEATURE)));
    }

    private static short packOffsetCoordinates(int n, int n2, int n3) {
        return (short)(n & 0xF | (n2 & 0xF) << 4 | (n3 & 0xF) << 8);
    }

    private static Typed lambda$makeRule$7(OpticFinder opticFinder, Type type, OpticFinder opticFinder2, Typed typed) {
        return typed.updateTyped(opticFinder, type, arg_0 -> ChunkGenStatus.lambda$makeRule$6(opticFinder2, type, arg_0));
    }

    private static Typed lambda$makeRule$6(OpticFinder opticFinder, Type type, Typed typed) {
        Dynamic<Object> dynamic;
        Optional optional = typed.getOptionalTyped(opticFinder).flatMap(ChunkGenStatus::lambda$makeRule$0).flatMap(ChunkGenStatus::lambda$makeRule$1);
        Dynamic<Object> dynamic2 = typed.get(DSL.remainderFinder());
        boolean bl = dynamic2.get("TerrainPopulated").asBoolean(true) && (!dynamic2.get("LightPopulated").asNumber().result().isPresent() || dynamic2.get("LightPopulated").asBoolean(true));
        dynamic2 = dynamic2.set("Status", dynamic2.createString(bl ? "mobs_spawned" : "empty"));
        dynamic2 = dynamic2.set("hasLegacyStructureData", dynamic2.createBoolean(false));
        if (bl) {
            Object object;
            Object object2;
            Optional<ByteBuffer> optional2 = dynamic2.get("Biomes").asByteBufferOpt().result();
            if (optional2.isPresent()) {
                object2 = optional2.get();
                object = new int[256];
                for (int i = 0; i < ((int[])object).length; ++i) {
                    if (i >= ((Buffer)object2).capacity()) continue;
                    object[i] = ((ByteBuffer)object2).get(i) & 0xFF;
                }
                dynamic2 = dynamic2.set("Biomes", dynamic2.createIntList(Arrays.stream((int[])object)));
            }
            object2 = dynamic2;
            object = IntStream.range(0, 16).mapToObj(ChunkGenStatus::lambda$makeRule$2).collect(Collectors.toList());
            if (optional.isPresent()) {
                ((Stream)optional.get()).forEach(arg_0 -> ChunkGenStatus.lambda$makeRule$3((List)object, arg_0));
                dynamic2 = dynamic2.set("ToBeTicked", dynamic2.createList(object.stream().map(arg_0 -> ChunkGenStatus.lambda$makeRule$4((Dynamic)object2, arg_0))));
            }
            dynamic = DataFixUtils.orElse(typed.set(DSL.remainderFinder(), dynamic2).write().result(), dynamic2);
        } else {
            dynamic = dynamic2;
        }
        return type.readTyped(dynamic).result().orElseThrow(ChunkGenStatus::lambda$makeRule$5).getFirst();
    }

    private static IllegalStateException lambda$makeRule$5() {
        return new IllegalStateException("Could not read the new chunk");
    }

    private static Dynamic lambda$makeRule$4(Dynamic dynamic, ShortList shortList) {
        return dynamic.createList(shortList.stream().map(dynamic::createShort));
    }

    private static void lambda$makeRule$3(List list, Dynamic dynamic) {
        int n = dynamic.get("x").asInt(0);
        int n2 = dynamic.get("y").asInt(0);
        int n3 = dynamic.get("z").asInt(0);
        short s = ChunkGenStatus.packOffsetCoordinates(n, n2, n3);
        ((ShortList)list.get(n2 >> 4)).add(s);
    }

    private static ShortArrayList lambda$makeRule$2(int n) {
        return new ShortArrayList();
    }

    private static Optional lambda$makeRule$1(Dynamic dynamic) {
        return dynamic.asStreamOpt().result();
    }

    private static Optional lambda$makeRule$0(Typed typed) {
        return typed.write().result();
    }
}

