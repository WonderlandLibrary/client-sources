/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.text;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.util.text.TextFormatting;

public final class Color {
    private static final Map<TextFormatting, Color> FORMATTING_TO_COLOR_MAP = Stream.of(TextFormatting.values()).filter(TextFormatting::isColor).collect(ImmutableMap.toImmutableMap(Function.identity(), Color::lambda$static$0));
    private static final Map<String, Color> NAME_TO_COLOR_MAP = FORMATTING_TO_COLOR_MAP.values().stream().collect(ImmutableMap.toImmutableMap(Color::lambda$static$1, Function.identity()));
    private final int color;
    @Nullable
    private final String name;

    private Color(int n, String string) {
        this.color = n;
        this.name = string;
    }

    public Color(int n) {
        this.color = n;
        this.name = null;
    }

    public int getColor() {
        return this.color;
    }

    public String getName() {
        return this.name != null ? this.name : this.getHex();
    }

    public String getHex() {
        return String.format("#%06X", this.color);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object != null && this.getClass() == object.getClass()) {
            Color color = (Color)object;
            return this.color == color.color;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.color, this.name);
    }

    public String toString() {
        return this.name != null ? this.name : this.getHex();
    }

    @Nullable
    public static Color fromTextFormatting(TextFormatting textFormatting) {
        return FORMATTING_TO_COLOR_MAP.get((Object)textFormatting);
    }

    public static Color fromInt(int n) {
        return new Color(n);
    }

    @Nullable
    public static Color fromHex(String string) {
        if (string.startsWith("#")) {
            try {
                int n = Integer.parseInt(string.substring(1), 16);
                return Color.fromInt(n);
            } catch (NumberFormatException numberFormatException) {
                return null;
            }
        }
        return NAME_TO_COLOR_MAP.get(string);
    }

    private static String lambda$static$1(Color color) {
        return color.name;
    }

    private static Color lambda$static$0(TextFormatting textFormatting) {
        return new Color(textFormatting.getColor(), textFormatting.getFriendlyName());
    }
}

