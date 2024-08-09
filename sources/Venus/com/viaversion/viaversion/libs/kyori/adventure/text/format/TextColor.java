/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Range
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.NamedTextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleBuilderApplicable;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColorImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextFormat;
import com.viaversion.viaversion.libs.kyori.adventure.util.HSVLike;
import com.viaversion.viaversion.libs.kyori.adventure.util.RGBLike;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

public interface TextColor
extends Comparable<TextColor>,
Examinable,
RGBLike,
StyleBuilderApplicable,
TextFormat {
    @NotNull
    public static TextColor color(int n) {
        int n2 = n & 0xFFFFFF;
        NamedTextColor namedTextColor = NamedTextColor.namedColor(n2);
        return namedTextColor != null ? namedTextColor : new TextColorImpl(n2);
    }

    @NotNull
    public static TextColor color(@NotNull RGBLike rGBLike) {
        if (rGBLike instanceof TextColor) {
            return (TextColor)rGBLike;
        }
        return TextColor.color(rGBLike.red(), rGBLike.green(), rGBLike.blue());
    }

    @NotNull
    public static TextColor color(@NotNull HSVLike hSVLike) {
        float f = hSVLike.s();
        float f2 = hSVLike.v();
        if (f == 0.0f) {
            return TextColor.color(f2, f2, f2);
        }
        float f3 = hSVLike.h() * 6.0f;
        int n = (int)Math.floor(f3);
        float f4 = f3 - (float)n;
        float f5 = f2 * (1.0f - f);
        float f6 = f2 * (1.0f - f * f4);
        float f7 = f2 * (1.0f - f * (1.0f - f4));
        if (n == 0) {
            return TextColor.color(f2, f7, f5);
        }
        if (n == 1) {
            return TextColor.color(f6, f2, f5);
        }
        if (n == 2) {
            return TextColor.color(f5, f2, f7);
        }
        if (n == 3) {
            return TextColor.color(f5, f6, f2);
        }
        if (n == 4) {
            return TextColor.color(f7, f5, f2);
        }
        return TextColor.color(f2, f5, f6);
    }

    @NotNull
    public static TextColor color(@Range(from=0L, to=255L) int n, @Range(from=0L, to=255L) int n2, @Range(from=0L, to=255L) int n3) {
        return TextColor.color((n & 0xFF) << 16 | (n2 & 0xFF) << 8 | n3 & 0xFF);
    }

    @NotNull
    public static TextColor color(float f, float f2, float f3) {
        return TextColor.color((int)(f * 255.0f), (int)(f2 * 255.0f), (int)(f3 * 255.0f));
    }

    @Nullable
    public static TextColor fromHexString(@NotNull String string) {
        if (string.startsWith("#")) {
            try {
                int n = Integer.parseInt(string.substring(1), 16);
                return TextColor.color(n);
            } catch (NumberFormatException numberFormatException) {
                return null;
            }
        }
        return null;
    }

    @Nullable
    public static TextColor fromCSSHexString(@NotNull String string) {
        if (string.startsWith("#")) {
            int n;
            String string2 = string.substring(1);
            if (string2.length() != 3 && string2.length() != 6) {
                return null;
            }
            try {
                n = Integer.parseInt(string2, 16);
            } catch (NumberFormatException numberFormatException) {
                return null;
            }
            if (string2.length() == 6) {
                return TextColor.color(n);
            }
            int n2 = (n & 0xF00) >> 8 | (n & 0xF00) >> 4;
            int n3 = (n & 0xF0) >> 4 | n & 0xF0;
            int n4 = (n & 0xF) << 4 | n & 0xF;
            return TextColor.color(n2, n3, n4);
        }
        return null;
    }

    public int value();

    @NotNull
    default public String asHexString() {
        return String.format("#%06x", this.value());
    }

    @Override
    default public @Range(from=0L, to=255L) int red() {
        return this.value() >> 16 & 0xFF;
    }

    @Override
    default public @Range(from=0L, to=255L) int green() {
        return this.value() >> 8 & 0xFF;
    }

    @Override
    default public @Range(from=0L, to=255L) int blue() {
        return this.value() & 0xFF;
    }

    @NotNull
    public static TextColor lerp(float f, @NotNull RGBLike rGBLike, @NotNull RGBLike rGBLike2) {
        float f2 = Math.min(1.0f, Math.max(0.0f, f));
        int n = rGBLike.red();
        int n2 = rGBLike2.red();
        int n3 = rGBLike.green();
        int n4 = rGBLike2.green();
        int n5 = rGBLike.blue();
        int n6 = rGBLike2.blue();
        return TextColor.color(Math.round((float)n + f2 * (float)(n2 - n)), Math.round((float)n3 + f2 * (float)(n4 - n3)), Math.round((float)n5 + f2 * (float)(n6 - n5)));
    }

    @Override
    default public void styleApply(@NotNull Style.Builder builder) {
        builder.color(this);
    }

    @Override
    default public int compareTo(TextColor textColor) {
        return Integer.compare(this.value(), textColor.value());
    }

    @Override
    @NotNull
    default public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("value", this.asHexString()));
    }

    @Override
    default public int compareTo(Object object) {
        return this.compareTo((TextColor)object);
    }
}

