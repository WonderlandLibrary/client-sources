/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.util.datafix.TypeReferences;

public class AddBedTileEntity
extends DataFix {
    public AddBedTileEntity(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    public TypeRewriteRule makeRule() {
        Type<?> type = this.getOutputSchema().getType(TypeReferences.CHUNK);
        Type<?> type2 = type.findFieldType("Level");
        Type<?> type3 = type2.findFieldType("TileEntities");
        if (!(type3 instanceof List.ListType)) {
            throw new IllegalStateException("Tile entity type is not a list type.");
        }
        List.ListType listType = (List.ListType)type3;
        return this.cap(type2, listType);
    }

    private <TE> TypeRewriteRule cap(Type<?> type, List.ListType<TE> listType) {
        Type<TE> type2 = listType.getElement();
        OpticFinder<?> opticFinder = DSL.fieldFinder("Level", type);
        OpticFinder<TE> opticFinder2 = DSL.fieldFinder("TileEntities", listType);
        int n = 416;
        return TypeRewriteRule.seq(this.fixTypeEverywhere("InjectBedBlockEntityType", this.getInputSchema().findChoiceType(TypeReferences.BLOCK_ENTITY), this.getOutputSchema().findChoiceType(TypeReferences.BLOCK_ENTITY), AddBedTileEntity::lambda$cap$1), this.fixTypeEverywhereTyped("BedBlockEntityInjecter", this.getOutputSchema().getType(TypeReferences.CHUNK), arg_0 -> AddBedTileEntity.lambda$cap$4(opticFinder, opticFinder2, type2, arg_0)));
    }

    private static Typed lambda$cap$4(OpticFinder opticFinder, OpticFinder opticFinder2, Type type, Typed typed) {
        Typed<Object> typed2 = typed.getTyped(opticFinder);
        Dynamic<?> dynamic = typed2.get(DSL.remainderFinder());
        int n = dynamic.get("xPos").asInt(0);
        int n2 = dynamic.get("zPos").asInt(0);
        ArrayList arrayList = Lists.newArrayList((Iterable)typed2.getOrCreate(opticFinder2));
        List list = dynamic.get("Sections").asList(Function.identity());
        for (int i = 0; i < list.size(); ++i) {
            Dynamic dynamic2 = (Dynamic)list.get(i);
            int n3 = dynamic2.get("Y").asInt(0);
            Stream<Integer> stream = dynamic2.get("Blocks").asStream().map(AddBedTileEntity::lambda$cap$2);
            int n4 = 0;
            Iterator iterator2 = ((Iterable)stream::iterator).iterator();
            while (iterator2.hasNext()) {
                int n5 = (Integer)iterator2.next();
                if (416 == (n5 & 0xFF) << 4) {
                    int n6 = n4 & 0xF;
                    int n7 = n4 >> 8 & 0xF;
                    int n8 = n4 >> 4 & 0xF;
                    HashMap hashMap = Maps.newHashMap();
                    hashMap.put(dynamic2.createString("id"), dynamic2.createString("minecraft:bed"));
                    hashMap.put(dynamic2.createString("x"), dynamic2.createInt(n6 + (n << 4)));
                    hashMap.put(dynamic2.createString("y"), dynamic2.createInt(n7 + (n3 << 4)));
                    hashMap.put(dynamic2.createString("z"), dynamic2.createInt(n8 + (n2 << 4)));
                    hashMap.put(dynamic2.createString("color"), dynamic2.createShort((short)14));
                    arrayList.add(type.read(dynamic2.createMap(hashMap)).result().orElseThrow(AddBedTileEntity::lambda$cap$3).getFirst());
                }
                ++n4;
            }
        }
        return !arrayList.isEmpty() ? typed.set(opticFinder, typed2.set(opticFinder2, arrayList)) : typed;
    }

    private static IllegalStateException lambda$cap$3() {
        return new IllegalStateException("Could not parse newly created bed block entity.");
    }

    private static Integer lambda$cap$2(Dynamic dynamic) {
        return dynamic.asInt(0);
    }

    private static Function lambda$cap$1(DynamicOps dynamicOps) {
        return AddBedTileEntity::lambda$cap$0;
    }

    private static Pair lambda$cap$0(Pair pair) {
        return pair;
    }
}

