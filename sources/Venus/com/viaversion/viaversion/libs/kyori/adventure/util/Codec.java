/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$ScheduledForRemoval
 */
package com.viaversion.viaversion.libs.kyori.adventure.util;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public interface Codec<D, E, DX extends Throwable, EX extends Throwable> {
    @NotNull
    public static <D, E, DX extends Throwable, EX extends Throwable> Codec<D, E, DX, EX> codec(@NotNull Decoder<D, E, DX> decoder, @NotNull Encoder<D, E, EX> encoder) {
        return new Codec<D, E, DX, EX>(decoder, encoder){
            final Decoder val$decoder;
            final Encoder val$encoder;
            {
                this.val$decoder = decoder;
                this.val$encoder = encoder;
            }

            @Override
            @NotNull
            public D decode(@NotNull E e) throws Throwable {
                return this.val$decoder.decode(e);
            }

            @Override
            @NotNull
            public E encode(@NotNull D d) throws Throwable {
                return this.val$encoder.encode(d);
            }
        };
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    @NotNull
    public static <D, E, DX extends Throwable, EX extends Throwable> Codec<D, E, DX, EX> of(@NotNull Decoder<D, E, DX> decoder, @NotNull Encoder<D, E, EX> encoder) {
        return new Codec<D, E, DX, EX>(decoder, encoder){
            final Decoder val$decoder;
            final Encoder val$encoder;
            {
                this.val$decoder = decoder;
                this.val$encoder = encoder;
            }

            @Override
            @NotNull
            public D decode(@NotNull E e) throws Throwable {
                return this.val$decoder.decode(e);
            }

            @Override
            @NotNull
            public E encode(@NotNull D d) throws Throwable {
                return this.val$encoder.encode(d);
            }
        };
    }

    @NotNull
    public D decode(@NotNull E var1) throws DX;

    @NotNull
    public E encode(@NotNull D var1) throws EX;

    public static interface Encoder<D, E, X extends Throwable> {
        @NotNull
        public E encode(@NotNull D var1) throws X;
    }

    public static interface Decoder<D, E, X extends Throwable> {
        @NotNull
        public D decode(@NotNull E var1) throws X;
    }
}

