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
import java.util.stream.Stream;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ItemLoreComponentizeFix
extends DataFix {
    public ItemLoreComponentizeFix(Schema schema, boolean bl) {
        super(schema, bl);
    }

    @Override
    protected TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
        OpticFinder<?> opticFinder = type.findField("tag");
        return this.fixTypeEverywhereTyped("Item Lore componentize", type, arg_0 -> ItemLoreComponentizeFix.lambda$makeRule$4(opticFinder, arg_0));
    }

    private static <T> Stream<Dynamic<T>> func_219830_a(Stream<Dynamic<T>> stream) {
        return stream.map(ItemLoreComponentizeFix::lambda$func_219830_a$5);
    }

    private static String func_219837_a(String string) {
        return ITextComponent.Serializer.toJson(new StringTextComponent(string));
    }

    private static Dynamic lambda$func_219830_a$5(Dynamic dynamic) {
        return DataFixUtils.orElse(dynamic.asString().map(ItemLoreComponentizeFix::func_219837_a).map(dynamic::createString).result(), dynamic);
    }

    private static Typed lambda$makeRule$4(OpticFinder opticFinder, Typed typed) {
        return typed.updateTyped(opticFinder, ItemLoreComponentizeFix::lambda$makeRule$3);
    }

    private static Typed lambda$makeRule$3(Typed typed) {
        return typed.update(DSL.remainderFinder(), ItemLoreComponentizeFix::lambda$makeRule$2);
    }

    private static Dynamic lambda$makeRule$2(Dynamic dynamic) {
        return dynamic.update("display", ItemLoreComponentizeFix::lambda$makeRule$1);
    }

    private static Dynamic lambda$makeRule$1(Dynamic dynamic) {
        return dynamic.update("Lore", ItemLoreComponentizeFix::lambda$makeRule$0);
    }

    private static Dynamic lambda$makeRule$0(Dynamic dynamic) {
        return DataFixUtils.orElse(dynamic.asStreamOpt().map(ItemLoreComponentizeFix::func_219830_a).map(dynamic::createList).result(), dynamic);
    }
}

