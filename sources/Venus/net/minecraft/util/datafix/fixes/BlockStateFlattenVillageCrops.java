/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.stream.Stream;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.BlockStateFlatteningMap;

public class BlockStateFlattenVillageCrops
extends DataFix {
    public BlockStateFlattenVillageCrops(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    public TypeRewriteRule makeRule() {
        return this.writeFixAndRead("SavedDataVillageCropFix", this.getInputSchema().getType(TypeReferences.STRUCTURE_FEATURE), this.getOutputSchema().getType(TypeReferences.STRUCTURE_FEATURE), this::fixTag);
    }

    private <T> Dynamic<T> fixTag(Dynamic<T> dynamic) {
        return dynamic.update("Children", BlockStateFlattenVillageCrops::updateChildren);
    }

    private static <T> Dynamic<T> updateChildren(Dynamic<T> dynamic) {
        return dynamic.asStreamOpt().map(BlockStateFlattenVillageCrops::updateChildren).map(dynamic::createList).result().orElse(dynamic);
    }

    private static Stream<? extends Dynamic<?>> updateChildren(Stream<? extends Dynamic<?>> stream) {
        return stream.map(BlockStateFlattenVillageCrops::lambda$updateChildren$0);
    }

    private static <T> Dynamic<T> updateSingleField(Dynamic<T> dynamic) {
        dynamic = BlockStateFlattenVillageCrops.updateCrop(dynamic, "CA");
        return BlockStateFlattenVillageCrops.updateCrop(dynamic, "CB");
    }

    private static <T> Dynamic<T> updateDoubleField(Dynamic<T> dynamic) {
        dynamic = BlockStateFlattenVillageCrops.updateCrop(dynamic, "CA");
        dynamic = BlockStateFlattenVillageCrops.updateCrop(dynamic, "CB");
        dynamic = BlockStateFlattenVillageCrops.updateCrop(dynamic, "CC");
        return BlockStateFlattenVillageCrops.updateCrop(dynamic, "CD");
    }

    private static <T> Dynamic<T> updateCrop(Dynamic<T> dynamic, String string) {
        return dynamic.get(string).asNumber().result().isPresent() ? dynamic.set(string, BlockStateFlatteningMap.getFixedNBTForID(dynamic.get(string).asInt(0) << 4)) : dynamic;
    }

    private static Dynamic lambda$updateChildren$0(Dynamic dynamic) {
        String string = dynamic.get("id").asString("");
        if ("ViF".equals(string)) {
            return BlockStateFlattenVillageCrops.updateSingleField(dynamic);
        }
        return "ViDF".equals(string) ? BlockStateFlattenVillageCrops.updateDoubleField(dynamic) : dynamic;
    }
}

