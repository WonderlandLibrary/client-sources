/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextProcessing;

public class BidiReorder {
    private final String field_244283_a;
    private final List<Style> field_244284_b;
    private final Int2IntFunction field_244285_c;

    private BidiReorder(String string, List<Style> list, Int2IntFunction int2IntFunction) {
        this.field_244283_a = string;
        this.field_244284_b = ImmutableList.copyOf(list);
        this.field_244285_c = int2IntFunction;
    }

    public String func_244286_a() {
        return this.field_244283_a;
    }

    public List<IReorderingProcessor> func_244287_a(int n, int n2, boolean bl) {
        if (n2 == 0) {
            return ImmutableList.of();
        }
        ArrayList<IReorderingProcessor> arrayList = Lists.newArrayList();
        Style style = this.field_244284_b.get(n);
        int n3 = n;
        for (int i = 1; i < n2; ++i) {
            int n4 = n + i;
            Style style2 = this.field_244284_b.get(n4);
            if (style2.equals(style)) continue;
            String string = this.field_244283_a.substring(n3, n4);
            arrayList.add(bl ? IReorderingProcessor.func_242246_b(string, style, this.field_244285_c) : IReorderingProcessor.fromString(string, style));
            style = style2;
            n3 = n4;
        }
        if (n3 < n + n2) {
            String string = this.field_244283_a.substring(n3, n + n2);
            arrayList.add(bl ? IReorderingProcessor.func_242246_b(string, style, this.field_244285_c) : IReorderingProcessor.fromString(string, style));
        }
        return bl ? Lists.reverse(arrayList) : arrayList;
    }

    public static BidiReorder func_244290_a(ITextProperties iTextProperties, Int2IntFunction int2IntFunction, UnaryOperator<String> unaryOperator) {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<Style> arrayList = Lists.newArrayList();
        iTextProperties.getComponentWithStyle((arg_0, arg_1) -> BidiReorder.lambda$func_244290_a$1(stringBuilder, arrayList, arg_0, arg_1), Style.EMPTY);
        return new BidiReorder((String)unaryOperator.apply(stringBuilder.toString()), arrayList, int2IntFunction);
    }

    private static Optional lambda$func_244290_a$1(StringBuilder stringBuilder, List list, Style style, String string) {
        TextProcessing.func_238346_c_(string, style, (arg_0, arg_1, arg_2) -> BidiReorder.lambda$func_244290_a$0(stringBuilder, list, arg_0, arg_1, arg_2));
        return Optional.empty();
    }

    private static boolean lambda$func_244290_a$0(StringBuilder stringBuilder, List list, int n, Style style, int n2) {
        stringBuilder.appendCodePoint(n2);
        int n3 = Character.charCount(n2);
        for (int i = 0; i < n3; ++i) {
            list.add(style);
        }
        return false;
    }
}

