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
import net.minecraft.util.datafix.fixes.BlockStateFlatteningMap;
import net.minecraft.util.datafix.fixes.NamedEntityFix;

public class PistonPushedBlock
extends NamedEntityFix {
    public PistonPushedBlock(Schema schema, boolean bl) {
        super(schema, bl, "BlockEntityBlockStateFix", TypeReferences.BLOCK_ENTITY, "minecraft:piston");
    }

    @Override
    protected Typed<?> fix(Typed<?> typed) {
        Type<?> type = this.getOutputSchema().getChoiceType(TypeReferences.BLOCK_ENTITY, "minecraft:piston");
        Type<?> type2 = type.findFieldType("blockState");
        OpticFinder<?> opticFinder = DSL.fieldFinder("blockState", type2);
        Dynamic<?> dynamic = typed.get(DSL.remainderFinder());
        int n = dynamic.get("blockId").asInt(0);
        dynamic = dynamic.remove("blockId");
        int n2 = dynamic.get("blockData").asInt(0) & 0xF;
        dynamic = dynamic.remove("blockData");
        Dynamic<?> dynamic2 = BlockStateFlatteningMap.getFixedNBTForID(n << 4 | n2);
        Typed<?> typed2 = type.pointTyped(typed.getOps()).orElseThrow(PistonPushedBlock::lambda$fix$0);
        return typed2.set(DSL.remainderFinder(), dynamic).set(opticFinder, type2.readTyped(dynamic2).result().orElseThrow(PistonPushedBlock::lambda$fix$1).getFirst());
    }

    private static IllegalStateException lambda$fix$1() {
        return new IllegalStateException("Could not parse newly created block state tag.");
    }

    private static IllegalStateException lambda$fix$0() {
        return new IllegalStateException("Could not create new piston block entity.");
    }
}

