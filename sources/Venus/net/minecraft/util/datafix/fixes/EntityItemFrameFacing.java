/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.NamedEntityFix;

public class EntityItemFrameFacing
extends NamedEntityFix {
    public EntityItemFrameFacing(Schema schema, boolean bl) {
        super(schema, bl, "EntityItemFrameDirectionFix", TypeReferences.ENTITY, "minecraft:item_frame");
    }

    public Dynamic<?> fixTag(Dynamic<?> dynamic) {
        return dynamic.set("Facing", dynamic.createByte(EntityItemFrameFacing.direction2dTo3d(dynamic.get("Facing").asByte((byte)0))));
    }

    @Override
    protected Typed<?> fix(Typed<?> typed) {
        return typed.update(DSL.remainderFinder(), this::fixTag);
    }

    private static byte direction2dTo3d(byte by) {
        switch (by) {
            case 0: {
                return 0;
            }
            case 1: {
                return 1;
            }
            default: {
                return 1;
            }
            case 3: 
        }
        return 0;
    }
}

