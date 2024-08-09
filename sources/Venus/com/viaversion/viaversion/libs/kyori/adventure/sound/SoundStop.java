/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$NonExtendable
 */
package com.viaversion.viaversion.libs.kyori.adventure.sound;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.sound.Sound;
import com.viaversion.viaversion.libs.kyori.adventure.sound.SoundStopImpl;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import java.util.Objects;
import java.util.function.Supplier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ApiStatus.NonExtendable
public interface SoundStop
extends Examinable {
    @NotNull
    public static SoundStop all() {
        return SoundStopImpl.ALL;
    }

    @NotNull
    public static SoundStop named(@NotNull Key key) {
        Objects.requireNonNull(key, "sound");
        return new SoundStopImpl(null, key){
            final Key val$sound;
            {
                this.val$sound = key;
                super(source);
            }

            @Override
            @NotNull
            public Key sound() {
                return this.val$sound;
            }
        };
    }

    @NotNull
    public static SoundStop named( @NotNull Sound.Type type) {
        Objects.requireNonNull(type, "sound");
        return new SoundStopImpl(null, type){
            final Sound.Type val$sound;
            {
                this.val$sound = type;
                super(source);
            }

            @Override
            @NotNull
            public Key sound() {
                return this.val$sound.key();
            }
        };
    }

    @NotNull
    public static SoundStop named(@NotNull Supplier<? extends Sound.Type> supplier) {
        Objects.requireNonNull(supplier, "sound");
        return new SoundStopImpl(null, supplier){
            final Supplier val$sound;
            {
                this.val$sound = supplier;
                super(source);
            }

            @Override
            @NotNull
            public Key sound() {
                return ((Sound.Type)this.val$sound.get()).key();
            }
        };
    }

    @NotNull
    public static SoundStop source( @NotNull Sound.Source source) {
        Objects.requireNonNull(source, "source");
        return new SoundStopImpl(source){

            @Override
            @Nullable
            public Key sound() {
                return null;
            }
        };
    }

    @NotNull
    public static SoundStop namedOnSource(@NotNull Key key,  @NotNull Sound.Source source) {
        Objects.requireNonNull(key, "sound");
        Objects.requireNonNull(source, "source");
        return new SoundStopImpl(source, key){
            final Key val$sound;
            {
                this.val$sound = key;
                super(source);
            }

            @Override
            @NotNull
            public Key sound() {
                return this.val$sound;
            }
        };
    }

    @NotNull
    public static SoundStop namedOnSource( @NotNull Sound.Type type,  @NotNull Sound.Source source) {
        Objects.requireNonNull(type, "sound");
        return SoundStop.namedOnSource(type.key(), source);
    }

    @NotNull
    public static SoundStop namedOnSource(@NotNull Supplier<? extends Sound.Type> supplier,  @NotNull Sound.Source source) {
        Objects.requireNonNull(supplier, "sound");
        Objects.requireNonNull(source, "source");
        return new SoundStopImpl(source, supplier){
            final Supplier val$sound;
            {
                this.val$sound = supplier;
                super(source);
            }

            @Override
            @NotNull
            public Key sound() {
                return ((Sound.Type)this.val$sound.get()).key();
            }
        };
    }

    @Nullable
    public Key sound();

    public  @Nullable Sound.Source source();
}

