/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.stream.Stream;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.NamedEntityFix;

public class BlockEntityBannerColor
extends NamedEntityFix {
    public BlockEntityBannerColor(Schema schema, boolean bl) {
        super(schema, bl, "BlockEntityBannerColorFix", TypeReferences.BLOCK_ENTITY, "minecraft:banner");
    }

    public Dynamic<?> fixTag(Dynamic<?> dynamic) {
        dynamic = dynamic.update("Base", BlockEntityBannerColor::lambda$fixTag$0);
        return dynamic.update("Patterns", BlockEntityBannerColor::lambda$fixTag$4);
    }

    @Override
    protected Typed<?> fix(Typed<?> typed) {
        return typed.update(DSL.remainderFinder(), this::fixTag);
    }

    private static Dynamic lambda$fixTag$4(Dynamic dynamic) {
        return DataFixUtils.orElse(dynamic.asStreamOpt().map(BlockEntityBannerColor::lambda$fixTag$3).map(dynamic::createList).result(), dynamic);
    }

    private static Stream lambda$fixTag$3(Stream stream) {
        return stream.map(BlockEntityBannerColor::lambda$fixTag$2);
    }

    private static Dynamic lambda$fixTag$2(Dynamic dynamic) {
        return dynamic.update("Color", BlockEntityBannerColor::lambda$fixTag$1);
    }

    private static Dynamic lambda$fixTag$1(Dynamic dynamic) {
        return dynamic.createInt(15 - dynamic.asInt(0));
    }

    private static Dynamic lambda$fixTag$0(Dynamic dynamic) {
        return dynamic.createInt(15 - dynamic.asInt(0));
    }
}

