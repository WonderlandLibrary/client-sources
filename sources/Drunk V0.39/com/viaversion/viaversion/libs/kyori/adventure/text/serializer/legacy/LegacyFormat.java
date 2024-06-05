/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.NamedTextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class LegacyFormat
implements Examinable {
    static final LegacyFormat RESET = new LegacyFormat(true);
    @Nullable
    private final NamedTextColor color;
    @Nullable
    private final TextDecoration decoration;
    private final boolean reset;

    LegacyFormat(@Nullable NamedTextColor color) {
        this.color = color;
        this.decoration = null;
        this.reset = false;
    }

    LegacyFormat(@Nullable TextDecoration decoration) {
        this.color = null;
        this.decoration = decoration;
        this.reset = false;
    }

    private LegacyFormat(boolean reset) {
        this.color = null;
        this.decoration = null;
        this.reset = reset;
    }

    @Nullable
    public TextColor color() {
        return this.color;
    }

    @Nullable
    public TextDecoration decoration() {
        return this.decoration;
    }

    public boolean reset() {
        return this.reset;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) return false;
        if (this.getClass() != other.getClass()) {
            return false;
        }
        LegacyFormat that = (LegacyFormat)other;
        if (this.color != that.color) return false;
        if (this.decoration != that.decoration) return false;
        if (this.reset != that.reset) return false;
        return true;
    }

    public int hashCode() {
        int result = Objects.hashCode(this.color);
        result = 31 * result + Objects.hashCode(this.decoration);
        return 31 * result + Boolean.hashCode(this.reset);
    }

    @Override
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("color", this.color), ExaminableProperty.of("decoration", this.decoration), ExaminableProperty.of("reset", this.reset));
    }
}

