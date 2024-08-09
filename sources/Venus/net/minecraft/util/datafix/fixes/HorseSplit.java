/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.EntityRename;

public class HorseSplit
extends EntityRename {
    public HorseSplit(Schema schema, boolean bl) {
        super("EntityHorseSplitFix", schema, bl);
    }

    @Override
    protected Pair<String, Typed<?>> fix(String string, Typed<?> typed) {
        Dynamic<?> dynamic = typed.get(DSL.remainderFinder());
        if (Objects.equals("EntityHorse", string)) {
            int n = dynamic.get("Type").asInt(0);
            String string2 = switch (n) {
                default -> "Horse";
                case 1 -> "Donkey";
                case 2 -> "Mule";
                case 3 -> "ZombieHorse";
                case 4 -> "SkeletonHorse";
            };
            dynamic.remove("Type");
            Type<?> type = this.getOutputSchema().findChoiceType(TypeReferences.ENTITY).types().get(string2);
            return Pair.of(string2, (Typed)((Pair)typed.write().flatMap(type::readTyped).result().orElseThrow(HorseSplit::lambda$fix$0)).getFirst());
        }
        return Pair.of(string, typed);
    }

    private static IllegalStateException lambda$fix$0() {
        return new IllegalStateException("Could not parse the new horse");
    }
}

