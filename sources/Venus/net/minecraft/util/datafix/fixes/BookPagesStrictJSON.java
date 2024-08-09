/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.gson.JsonParseException;
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
import net.minecraft.util.JSONUtils;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.SignStrictJSON;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.apache.commons.lang3.StringUtils;

public class BookPagesStrictJSON
extends DataFix {
    public BookPagesStrictJSON(Schema schema, boolean bl) {
        super(schema, bl);
    }

    public Dynamic<?> fixTag(Dynamic<?> dynamic) {
        return dynamic.update("pages", arg_0 -> BookPagesStrictJSON.lambda$fixTag$2(dynamic, arg_0));
    }

    @Override
    public TypeRewriteRule makeRule() {
        Type<?> type = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
        OpticFinder<?> opticFinder = type.findField("tag");
        return this.fixTypeEverywhereTyped("ItemWrittenBookPagesStrictJsonFix", type, arg_0 -> this.lambda$makeRule$4(opticFinder, arg_0));
    }

    private Typed lambda$makeRule$4(OpticFinder opticFinder, Typed typed) {
        return typed.updateTyped(opticFinder, this::lambda$makeRule$3);
    }

    private Typed lambda$makeRule$3(Typed typed) {
        return typed.update(DSL.remainderFinder(), this::fixTag);
    }

    private static Dynamic lambda$fixTag$2(Dynamic dynamic, Dynamic dynamic2) {
        return DataFixUtils.orElse(dynamic2.asStreamOpt().map(BookPagesStrictJSON::lambda$fixTag$1).map(dynamic::createList).result(), dynamic.emptyList());
    }

    private static Stream lambda$fixTag$1(Stream stream) {
        return stream.map(BookPagesStrictJSON::lambda$fixTag$0);
    }

    private static Dynamic lambda$fixTag$0(Dynamic dynamic) {
        if (!dynamic.asString().result().isPresent()) {
            return dynamic;
        }
        String string = dynamic.asString("");
        ITextComponent iTextComponent = null;
        if (!"null".equals(string) && !StringUtils.isEmpty(string)) {
            if (string.charAt(0) == '\"' && string.charAt(string.length() - 1) == '\"' || string.charAt(0) == '{' && string.charAt(string.length() - 1) == '}') {
                try {
                    iTextComponent = JSONUtils.fromJson(SignStrictJSON.GSON, string, ITextComponent.class, true);
                    if (iTextComponent == null) {
                        iTextComponent = StringTextComponent.EMPTY;
                    }
                } catch (JsonParseException jsonParseException) {
                    // empty catch block
                }
                if (iTextComponent == null) {
                    try {
                        iTextComponent = ITextComponent.Serializer.getComponentFromJson(string);
                    } catch (JsonParseException jsonParseException) {
                        // empty catch block
                    }
                }
                if (iTextComponent == null) {
                    try {
                        iTextComponent = ITextComponent.Serializer.getComponentFromJsonLenient(string);
                    } catch (JsonParseException jsonParseException) {
                        // empty catch block
                    }
                }
                if (iTextComponent == null) {
                    iTextComponent = new StringTextComponent(string);
                }
            } else {
                iTextComponent = new StringTextComponent(string);
            }
        } else {
            iTextComponent = StringTextComponent.EMPTY;
        }
        return dynamic.createString(ITextComponent.Serializer.toJson(iTextComponent));
    }
}

