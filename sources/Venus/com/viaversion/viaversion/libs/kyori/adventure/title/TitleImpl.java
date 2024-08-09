/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.UnknownNullability
 */
package com.viaversion.viaversion.libs.kyori.adventure.title;

import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.title.Title;
import com.viaversion.viaversion.libs.kyori.adventure.title.TitlePart;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.time.Duration;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

final class TitleImpl
implements Title {
    private final Component title;
    private final Component subtitle;
    @Nullable
    private final Title.Times times;

    TitleImpl(@NotNull Component component, @NotNull Component component2, @Nullable Title.Times times) {
        this.title = Objects.requireNonNull(component, "title");
        this.subtitle = Objects.requireNonNull(component2, "subtitle");
        this.times = times;
    }

    @Override
    @NotNull
    public Component title() {
        return this.title;
    }

    @Override
    @NotNull
    public Component subtitle() {
        return this.subtitle;
    }

    @Override
    @Nullable
    public Title.Times times() {
        return this.times;
    }

    @Override
    public <T> @UnknownNullability T part(@NotNull TitlePart<T> titlePart) {
        Objects.requireNonNull(titlePart, "part");
        if (titlePart == TitlePart.TITLE) {
            return (T)this.title;
        }
        if (titlePart == TitlePart.SUBTITLE) {
            return (T)this.subtitle;
        }
        if (titlePart == TitlePart.TIMES) {
            return (T)this.times;
        }
        throw new IllegalArgumentException("Don't know what " + titlePart + " is.");
    }

    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        TitleImpl titleImpl = (TitleImpl)object;
        return this.title.equals(titleImpl.title) && this.subtitle.equals(titleImpl.subtitle) && Objects.equals(this.times, titleImpl.times);
    }

    public int hashCode() {
        int n = this.title.hashCode();
        n = 31 * n + this.subtitle.hashCode();
        n = 31 * n + Objects.hashCode(this.times);
        return n;
    }

    @Override
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("title", this.title), ExaminableProperty.of("subtitle", this.subtitle), ExaminableProperty.of("times", this.times));
    }

    public String toString() {
        return Internals.toString(this);
    }

    static class TimesImpl
    implements Title.Times {
        private final Duration fadeIn;
        private final Duration stay;
        private final Duration fadeOut;

        TimesImpl(@NotNull Duration duration, @NotNull Duration duration2, @NotNull Duration duration3) {
            this.fadeIn = Objects.requireNonNull(duration, "fadeIn");
            this.stay = Objects.requireNonNull(duration2, "stay");
            this.fadeOut = Objects.requireNonNull(duration3, "fadeOut");
        }

        @Override
        @NotNull
        public Duration fadeIn() {
            return this.fadeIn;
        }

        @Override
        @NotNull
        public Duration stay() {
            return this.stay;
        }

        @Override
        @NotNull
        public Duration fadeOut() {
            return this.fadeOut;
        }

        public boolean equals(@Nullable Object object) {
            if (this == object) {
                return false;
            }
            if (!(object instanceof TimesImpl)) {
                return true;
            }
            TimesImpl timesImpl = (TimesImpl)object;
            return this.fadeIn.equals(timesImpl.fadeIn) && this.stay.equals(timesImpl.stay) && this.fadeOut.equals(timesImpl.fadeOut);
        }

        public int hashCode() {
            int n = this.fadeIn.hashCode();
            n = 31 * n + this.stay.hashCode();
            n = 31 * n + this.fadeOut.hashCode();
            return n;
        }

        @Override
        @NotNull
        public Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of(ExaminableProperty.of("fadeIn", this.fadeIn), ExaminableProperty.of("stay", this.stay), ExaminableProperty.of("fadeOut", this.fadeOut));
        }

        public String toString() {
            return Internals.toString(this);
        }
    }
}

