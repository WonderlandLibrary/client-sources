/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.util.datafix.TypeReferences;

public class HeightmapRenamingFix
extends DataFix {
    public HeightmapRenamingFix(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getType(TypeReferences.CHUNK);
        OpticFinder<?> opticFinder = type.findField("Level");
        return this.fixTypeEverywhereTyped("HeightmapRenamingFix", type, arg_0 -> this.lambda$makeRule$1(opticFinder, arg_0));
    }

    private Dynamic<?> fix(Dynamic<?> dynamic) {
        Optional<Dynamic<?>> optional;
        Optional<Dynamic<?>> optional2;
        Optional<Dynamic<?>> optional3;
        Optional<Dynamic<?>> optional4 = dynamic.get("Heightmaps").result();
        if (!optional4.isPresent()) {
            return dynamic;
        }
        Dynamic<?> dynamic2 = optional4.get();
        Optional<Dynamic<?>> optional5 = dynamic2.get("LIQUID").result();
        if (optional5.isPresent()) {
            dynamic2 = dynamic2.remove("LIQUID");
            dynamic2 = dynamic2.set("WORLD_SURFACE_WG", optional5.get());
        }
        if ((optional3 = dynamic2.get("SOLID").result()).isPresent()) {
            dynamic2 = dynamic2.remove("SOLID");
            dynamic2 = dynamic2.set("OCEAN_FLOOR_WG", optional3.get());
            dynamic2 = dynamic2.set("OCEAN_FLOOR", optional3.get());
        }
        if ((optional2 = dynamic2.get("LIGHT").result()).isPresent()) {
            dynamic2 = dynamic2.remove("LIGHT");
            dynamic2 = dynamic2.set("LIGHT_BLOCKING", optional2.get());
        }
        if ((optional = dynamic2.get("RAIN").result()).isPresent()) {
            dynamic2 = dynamic2.remove("RAIN");
            dynamic2 = dynamic2.set("MOTION_BLOCKING", optional.get());
            dynamic2 = dynamic2.set("MOTION_BLOCKING_NO_LEAVES", optional.get());
        }
        return dynamic.set("Heightmaps", dynamic2);
    }

    private Typed lambda$makeRule$1(OpticFinder opticFinder, Typed typed) {
        return typed.updateTyped(opticFinder, this::lambda$makeRule$0);
    }

    private Typed lambda$makeRule$0(Typed typed) {
        return typed.update(DSL.remainderFinder(), this::fix);
    }
}

