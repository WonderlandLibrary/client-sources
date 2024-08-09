/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import java.util.List;
import net.minecraft.util.ICharacterConsumer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextProcessing;

@FunctionalInterface
public interface IReorderingProcessor {
    public static final IReorderingProcessor field_242232_a = IReorderingProcessor::lambda$static$0;

    public boolean accept(ICharacterConsumer var1);

    public static IReorderingProcessor fromCodePoint(int n, Style style) {
        return arg_0 -> IReorderingProcessor.lambda$fromCodePoint$1(style, n, arg_0);
    }

    public static IReorderingProcessor fromString(String string, Style style) {
        return string.isEmpty() ? field_242232_a : arg_0 -> IReorderingProcessor.lambda$fromString$2(string, style, arg_0);
    }

    public static IReorderingProcessor func_242246_b(String string, Style style, Int2IntFunction int2IntFunction) {
        return string.isEmpty() ? field_242232_a : arg_0 -> IReorderingProcessor.lambda$func_242246_b$3(string, style, int2IntFunction, arg_0);
    }

    public static ICharacterConsumer func_242237_a(ICharacterConsumer iCharacterConsumer, Int2IntFunction int2IntFunction) {
        return (arg_0, arg_1, arg_2) -> IReorderingProcessor.lambda$func_242237_a$4(iCharacterConsumer, int2IntFunction, arg_0, arg_1, arg_2);
    }

    public static IReorderingProcessor func_242234_a(IReorderingProcessor iReorderingProcessor, IReorderingProcessor iReorderingProcessor2) {
        return IReorderingProcessor.func_242244_b(iReorderingProcessor, iReorderingProcessor2);
    }

    public static IReorderingProcessor func_242241_a(List<IReorderingProcessor> list) {
        int n = list.size();
        switch (n) {
            case 0: {
                return field_242232_a;
            }
            case 1: {
                return list.get(0);
            }
            case 2: {
                return IReorderingProcessor.func_242244_b(list.get(0), list.get(1));
            }
        }
        return IReorderingProcessor.func_242247_b(ImmutableList.copyOf(list));
    }

    public static IReorderingProcessor func_242244_b(IReorderingProcessor iReorderingProcessor, IReorderingProcessor iReorderingProcessor2) {
        return arg_0 -> IReorderingProcessor.lambda$func_242244_b$5(iReorderingProcessor, iReorderingProcessor2, arg_0);
    }

    public static IReorderingProcessor func_242247_b(List<IReorderingProcessor> list) {
        return arg_0 -> IReorderingProcessor.lambda$func_242247_b$6(list, arg_0);
    }

    private static boolean lambda$func_242247_b$6(List list, ICharacterConsumer iCharacterConsumer) {
        for (IReorderingProcessor iReorderingProcessor : list) {
            if (iReorderingProcessor.accept(iCharacterConsumer)) continue;
            return true;
        }
        return false;
    }

    private static boolean lambda$func_242244_b$5(IReorderingProcessor iReorderingProcessor, IReorderingProcessor iReorderingProcessor2, ICharacterConsumer iCharacterConsumer) {
        return iReorderingProcessor.accept(iCharacterConsumer) && iReorderingProcessor2.accept(iCharacterConsumer);
    }

    private static boolean lambda$func_242237_a$4(ICharacterConsumer iCharacterConsumer, Int2IntFunction int2IntFunction, int n, Style style, int n2) {
        return iCharacterConsumer.accept(n, style, (Integer)int2IntFunction.apply(n2));
    }

    private static boolean lambda$func_242246_b$3(String string, Style style, Int2IntFunction int2IntFunction, ICharacterConsumer iCharacterConsumer) {
        return TextProcessing.func_238345_b_(string, style, IReorderingProcessor.func_242237_a(iCharacterConsumer, int2IntFunction));
    }

    private static boolean lambda$fromString$2(String string, Style style, ICharacterConsumer iCharacterConsumer) {
        return TextProcessing.func_238341_a_(string, style, iCharacterConsumer);
    }

    private static boolean lambda$fromCodePoint$1(Style style, int n, ICharacterConsumer iCharacterConsumer) {
        return iCharacterConsumer.accept(0, style, n);
    }

    private static boolean lambda$static$0(ICharacterConsumer iCharacterConsumer) {
        return false;
    }
}

