/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.text;

import java.util.Optional;
import net.minecraft.util.ICharacterConsumer;
import net.minecraft.util.Unit;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

public class TextProcessing {
    private static final Optional<Object> field_238336_a_ = Optional.of(Unit.INSTANCE);

    private static boolean func_238344_a_(Style style, ICharacterConsumer iCharacterConsumer, int n, char c) {
        return Character.isSurrogate(c) ? iCharacterConsumer.accept(n, style, 65533) : iCharacterConsumer.accept(n, style, c);
    }

    public static boolean func_238341_a_(String string, Style style, ICharacterConsumer iCharacterConsumer) {
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (Character.isHighSurrogate(c)) {
                if (i + 1 >= n) {
                    if (iCharacterConsumer.accept(i, style, 65533)) break;
                    return true;
                }
                char c2 = string.charAt(i + 1);
                if (Character.isLowSurrogate(c2)) {
                    if (!iCharacterConsumer.accept(i, style, Character.toCodePoint(c, c2))) {
                        return true;
                    }
                    ++i;
                    continue;
                }
                if (iCharacterConsumer.accept(i, style, 65533)) continue;
                return true;
            }
            if (TextProcessing.func_238344_a_(style, iCharacterConsumer, i, c)) continue;
            return true;
        }
        return false;
    }

    public static boolean func_238345_b_(String string, Style style, ICharacterConsumer iCharacterConsumer) {
        int n = string.length();
        for (int i = n - 1; i >= 0; --i) {
            char c = string.charAt(i);
            if (Character.isLowSurrogate(c)) {
                if (i - 1 < 0) {
                    if (iCharacterConsumer.accept(0, style, 65533)) break;
                    return true;
                }
                char c2 = string.charAt(i - 1);
                if (!(Character.isHighSurrogate(c2) ? !iCharacterConsumer.accept(--i, style, Character.toCodePoint(c2, c)) : !iCharacterConsumer.accept(i, style, 65533))) continue;
                return true;
            }
            if (TextProcessing.func_238344_a_(style, iCharacterConsumer, i, c)) continue;
            return true;
        }
        return false;
    }

    public static boolean func_238346_c_(String string, Style style, ICharacterConsumer iCharacterConsumer) {
        return TextProcessing.func_238339_a_(string, 0, style, iCharacterConsumer);
    }

    public static boolean func_238339_a_(String string, int n, Style style, ICharacterConsumer iCharacterConsumer) {
        return TextProcessing.func_238340_a_(string, n, style, style, iCharacterConsumer);
    }

    public static boolean func_238340_a_(String string, int n, Style style, Style style2, ICharacterConsumer iCharacterConsumer) {
        int n2 = string.length();
        Style style3 = style;
        for (int i = n; i < n2; ++i) {
            char c;
            char c2 = string.charAt(i);
            if (c2 == '\u00a7') {
                if (i + 1 >= n2) break;
                c = string.charAt(i + 1);
                TextFormatting textFormatting = TextFormatting.fromFormattingCode(c);
                if (textFormatting != null) {
                    style3 = textFormatting == TextFormatting.RESET ? style2 : style3.forceFormatting(textFormatting);
                }
                ++i;
                continue;
            }
            if (Character.isHighSurrogate(c2)) {
                if (i + 1 >= n2) {
                    if (iCharacterConsumer.accept(i, style3, 65533)) break;
                    return true;
                }
                c = string.charAt(i + 1);
                if (Character.isLowSurrogate(c)) {
                    if (!iCharacterConsumer.accept(i, style3, Character.toCodePoint(c2, c))) {
                        return true;
                    }
                    ++i;
                    continue;
                }
                if (iCharacterConsumer.accept(i, style3, 65533)) continue;
                return true;
            }
            if (TextProcessing.func_238344_a_(style3, iCharacterConsumer, i, c2)) continue;
            return true;
        }
        return false;
    }

    public static boolean func_238343_a_(ITextProperties iTextProperties, Style style, ICharacterConsumer iCharacterConsumer) {
        return !iTextProperties.getComponentWithStyle((arg_0, arg_1) -> TextProcessing.lambda$func_238343_a_$0(iCharacterConsumer, arg_0, arg_1), style).isPresent();
    }

    public static String func_238338_a_(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        TextProcessing.func_238341_a_(string, Style.EMPTY, (arg_0, arg_1, arg_2) -> TextProcessing.lambda$func_238338_a_$1(stringBuilder, arg_0, arg_1, arg_2));
        return stringBuilder.toString();
    }

    public static String func_244782_a(ITextProperties iTextProperties) {
        StringBuilder stringBuilder = new StringBuilder();
        TextProcessing.func_238343_a_(iTextProperties, Style.EMPTY, (arg_0, arg_1, arg_2) -> TextProcessing.lambda$func_244782_a$2(stringBuilder, arg_0, arg_1, arg_2));
        return stringBuilder.toString();
    }

    private static boolean lambda$func_244782_a$2(StringBuilder stringBuilder, int n, Style style, int n2) {
        stringBuilder.appendCodePoint(n2);
        return false;
    }

    private static boolean lambda$func_238338_a_$1(StringBuilder stringBuilder, int n, Style style, int n2) {
        stringBuilder.appendCodePoint(n2);
        return false;
    }

    private static Optional lambda$func_238343_a_$0(ICharacterConsumer iCharacterConsumer, Style style, String string) {
        return TextProcessing.func_238339_a_(string, 0, style, iCharacterConsumer) ? Optional.empty() : field_238336_a_;
    }
}

