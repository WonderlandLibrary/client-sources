/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.stream.Stream;
import net.minecraft.util.datafix.TypeReferences;

public class IglooMetadataRemoval
extends DataFix {
    public IglooMetadataRemoval(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getType(TypeReferences.STRUCTURE_FEATURE);
        Type<?> type2 = this.getOutputSchema().getType(TypeReferences.STRUCTURE_FEATURE);
        return this.writeFixAndRead("IglooMetadataRemovalFix", type, type2, IglooMetadataRemoval::fixTag);
    }

    private static <T> Dynamic<T> fixTag(Dynamic<T> dynamic) {
        boolean bl = dynamic.get("Children").asStreamOpt().map(IglooMetadataRemoval::lambda$fixTag$0).result().orElse(false);
        return bl ? dynamic.set("id", dynamic.createString("Igloo")).remove("Children") : dynamic.update("Children", IglooMetadataRemoval::removeIglooPieces);
    }

    private static <T> Dynamic<T> removeIglooPieces(Dynamic<T> dynamic) {
        return dynamic.asStreamOpt().map(IglooMetadataRemoval::lambda$removeIglooPieces$2).map(dynamic::createList).result().orElse(dynamic);
    }

    private static boolean isIglooPiece(Dynamic<?> dynamic) {
        return dynamic.get("id").asString("").equals("Iglu");
    }

    private static Stream lambda$removeIglooPieces$2(Stream stream) {
        return stream.filter(IglooMetadataRemoval::lambda$removeIglooPieces$1);
    }

    private static boolean lambda$removeIglooPieces$1(Dynamic dynamic) {
        return !IglooMetadataRemoval.isIglooPiece(dynamic);
    }

    private static Boolean lambda$fixTag$0(Stream stream) {
        return stream.allMatch(IglooMetadataRemoval::isIglooPiece);
    }
}

