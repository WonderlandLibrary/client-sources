/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.sound;

import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.sound.SoundStop;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

abstract class SoundStopImpl
implements SoundStop {
    static final SoundStop ALL = new SoundStopImpl(null){

        @Override
        @Nullable
        public Key sound() {
            return null;
        }
    };
    private final  @Nullable Sound.Source source;

    SoundStopImpl( @Nullable Sound.Source source) {
        this.source = source;
    }

    @Override
    public  @Nullable Sound.Source source() {
        return this.source;
    }

    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof SoundStopImpl)) {
            return true;
        }
        SoundStopImpl soundStopImpl = (SoundStopImpl)object;
        return Objects.equals(this.sound(), soundStopImpl.sound()) && Objects.equals((Object)this.source, (Object)soundStopImpl.source);
    }

    public int hashCode() {
        int n = Objects.hashCode(this.sound());
        n = 31 * n + Objects.hashCode((Object)this.source);
        return n;
    }

    @Override
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("name", this.sound()), ExaminableProperty.of("source", (Object)this.source));
    }

    public String toString() {
        return Internals.toString(this);
    }
}

