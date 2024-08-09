/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.ItemIntIDToString;
import net.minecraft.util.datafix.fixes.ItemStackDataFlattening;
import net.minecraft.util.datafix.fixes.NamedEntityFix;

public class JukeboxRecordItem
extends NamedEntityFix {
    public JukeboxRecordItem(Schema schema, boolean bl) {
        super(schema, bl, "BlockEntityJukeboxFix", TypeReferences.BLOCK_ENTITY, "minecraft:jukebox");
    }

    @Override
    protected Typed<?> fix(Typed<?> typed) {
        Type<?> type = this.getInputSchema().getChoiceType(TypeReferences.BLOCK_ENTITY, "minecraft:jukebox");
        Type<?> type2 = type.findFieldType("RecordItem");
        OpticFinder<?> opticFinder = DSL.fieldFinder("RecordItem", type2);
        Dynamic<?> dynamic = typed.get(DSL.remainderFinder());
        int n = dynamic.get("Record").asInt(0);
        if (n > 0) {
            dynamic.remove("Record");
            String string = ItemStackDataFlattening.updateItem(ItemIntIDToString.getItem(n), 0);
            if (string != null) {
                Dynamic dynamic2 = dynamic.emptyMap();
                dynamic2 = dynamic2.set("id", dynamic2.createString(string));
                dynamic2 = dynamic2.set("Count", dynamic2.createByte((byte)1));
                return typed.set(opticFinder, type2.readTyped(dynamic2).result().orElseThrow(JukeboxRecordItem::lambda$fix$0).getFirst()).set(DSL.remainderFinder(), dynamic);
            }
        }
        return typed;
    }

    private static IllegalStateException lambda$fix$0() {
        return new IllegalStateException("Could not create record item stack.");
    }
}

