/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$ScheduledForRemoval
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.util.HSVLike;
import com.viaversion.viaversion.libs.kyori.adventure.util.Index;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class NamedTextColor
implements TextColor {
    private static final int BLACK_VALUE = 0;
    private static final int DARK_BLUE_VALUE = 170;
    private static final int DARK_GREEN_VALUE = 43520;
    private static final int DARK_AQUA_VALUE = 43690;
    private static final int DARK_RED_VALUE = 0xAA0000;
    private static final int DARK_PURPLE_VALUE = 0xAA00AA;
    private static final int GOLD_VALUE = 0xFFAA00;
    private static final int GRAY_VALUE = 0xAAAAAA;
    private static final int DARK_GRAY_VALUE = 0x555555;
    private static final int BLUE_VALUE = 0x5555FF;
    private static final int GREEN_VALUE = 0x55FF55;
    private static final int AQUA_VALUE = 0x55FFFF;
    private static final int RED_VALUE = 0xFF5555;
    private static final int LIGHT_PURPLE_VALUE = 0xFF55FF;
    private static final int YELLOW_VALUE = 0xFFFF55;
    private static final int WHITE_VALUE = 0xFFFFFF;
    public static final NamedTextColor BLACK = new NamedTextColor("black", 0);
    public static final NamedTextColor DARK_BLUE = new NamedTextColor("dark_blue", 170);
    public static final NamedTextColor DARK_GREEN = new NamedTextColor("dark_green", 43520);
    public static final NamedTextColor DARK_AQUA = new NamedTextColor("dark_aqua", 43690);
    public static final NamedTextColor DARK_RED = new NamedTextColor("dark_red", 0xAA0000);
    public static final NamedTextColor DARK_PURPLE = new NamedTextColor("dark_purple", 0xAA00AA);
    public static final NamedTextColor GOLD = new NamedTextColor("gold", 0xFFAA00);
    public static final NamedTextColor GRAY = new NamedTextColor("gray", 0xAAAAAA);
    public static final NamedTextColor DARK_GRAY = new NamedTextColor("dark_gray", 0x555555);
    public static final NamedTextColor BLUE = new NamedTextColor("blue", 0x5555FF);
    public static final NamedTextColor GREEN = new NamedTextColor("green", 0x55FF55);
    public static final NamedTextColor AQUA = new NamedTextColor("aqua", 0x55FFFF);
    public static final NamedTextColor RED = new NamedTextColor("red", 0xFF5555);
    public static final NamedTextColor LIGHT_PURPLE = new NamedTextColor("light_purple", 0xFF55FF);
    public static final NamedTextColor YELLOW = new NamedTextColor("yellow", 0xFFFF55);
    public static final NamedTextColor WHITE = new NamedTextColor("white", 0xFFFFFF);
    private static final List<NamedTextColor> VALUES = Collections.unmodifiableList(Arrays.asList(BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE));
    public static final Index<String, NamedTextColor> NAMES = Index.create(NamedTextColor::lambda$static$0, VALUES);
    private final String name;
    private final int value;
    private final HSVLike hsv;

    @Nullable
    public static NamedTextColor namedColor(int n) {
        switch (n) {
            case 0: {
                return BLACK;
            }
            case 170: {
                return DARK_BLUE;
            }
            case 43520: {
                return DARK_GREEN;
            }
            case 43690: {
                return DARK_AQUA;
            }
            case 0xAA0000: {
                return DARK_RED;
            }
            case 0xAA00AA: {
                return DARK_PURPLE;
            }
            case 0xFFAA00: {
                return GOLD;
            }
            case 0xAAAAAA: {
                return GRAY;
            }
            case 0x555555: {
                return DARK_GRAY;
            }
            case 0x5555FF: {
                return BLUE;
            }
            case 0x55FF55: {
                return GREEN;
            }
            case 0x55FFFF: {
                return AQUA;
            }
            case 0xFF5555: {
                return RED;
            }
            case 0xFF55FF: {
                return LIGHT_PURPLE;
            }
            case 0xFFFF55: {
                return YELLOW;
            }
            case 0xFFFFFF: {
                return WHITE;
            }
        }
        return null;
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    @Nullable
    public static NamedTextColor ofExact(int n) {
        switch (n) {
            case 0: {
                return BLACK;
            }
            case 170: {
                return DARK_BLUE;
            }
            case 43520: {
                return DARK_GREEN;
            }
            case 43690: {
                return DARK_AQUA;
            }
            case 0xAA0000: {
                return DARK_RED;
            }
            case 0xAA00AA: {
                return DARK_PURPLE;
            }
            case 0xFFAA00: {
                return GOLD;
            }
            case 0xAAAAAA: {
                return GRAY;
            }
            case 0x555555: {
                return DARK_GRAY;
            }
            case 0x5555FF: {
                return BLUE;
            }
            case 0x55FF55: {
                return GREEN;
            }
            case 0x55FFFF: {
                return AQUA;
            }
            case 0xFF5555: {
                return RED;
            }
            case 0xFF55FF: {
                return LIGHT_PURPLE;
            }
            case 0xFFFF55: {
                return YELLOW;
            }
            case 0xFFFFFF: {
                return WHITE;
            }
        }
        return null;
    }

    @NotNull
    public static NamedTextColor nearestTo(@NotNull TextColor textColor) {
        if (textColor instanceof NamedTextColor) {
            return (NamedTextColor)textColor;
        }
        Objects.requireNonNull(textColor, "color");
        float f = Float.MAX_VALUE;
        NamedTextColor namedTextColor = VALUES.get(0);
        int n = VALUES.size();
        for (int i = 0; i < n; ++i) {
            NamedTextColor namedTextColor2 = VALUES.get(i);
            float f2 = NamedTextColor.distance(textColor.asHSV(), namedTextColor2.asHSV());
            if (f2 < f) {
                namedTextColor = namedTextColor2;
                f = f2;
            }
            if (f2 == 0.0f) break;
        }
        return namedTextColor;
    }

    private static float distance(@NotNull HSVLike hSVLike, @NotNull HSVLike hSVLike2) {
        float f = 3.0f * Math.min(Math.abs(hSVLike.h() - hSVLike2.h()), 1.0f - Math.abs(hSVLike.h() - hSVLike2.h()));
        float f2 = hSVLike.s() - hSVLike2.s();
        float f3 = hSVLike.v() - hSVLike2.v();
        return f * f + f2 * f2 + f3 * f3;
    }

    private NamedTextColor(String string, int n) {
        this.name = string;
        this.value = n;
        this.hsv = HSVLike.fromRGB(this.red(), this.green(), this.blue());
    }

    @Override
    public int value() {
        return this.value;
    }

    @Override
    @NotNull
    public HSVLike asHSV() {
        return this.hsv;
    }

    @NotNull
    public String toString() {
        return this.name;
    }

    @Override
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.concat(Stream.of(ExaminableProperty.of("name", this.name)), TextColor.super.examinableProperties());
    }

    private static String lambda$static$0(NamedTextColor namedTextColor) {
        return namedTextColor.name;
    }
}

