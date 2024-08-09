/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.registry;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenSettingsExport;
import net.minecraft.util.registry.WorldSettingsImport;

public final class RegistryKeyCodec<E>
implements Codec<Supplier<E>> {
    private final RegistryKey<? extends Registry<E>> registryKey;
    private final Codec<E> registryCodec;
    private final boolean allowInlineDefinitions;

    public static <E> RegistryKeyCodec<E> create(RegistryKey<? extends Registry<E>> registryKey, Codec<E> codec) {
        return RegistryKeyCodec.create(registryKey, codec, true);
    }

    public static <E> Codec<List<Supplier<E>>> getValueCodecs(RegistryKey<? extends Registry<E>> registryKey, Codec<E> codec) {
        return Codec.either(RegistryKeyCodec.create(registryKey, codec, false).listOf(), codec.xmap(RegistryKeyCodec::lambda$getValueCodecs$1, Supplier::get).listOf()).xmap(RegistryKeyCodec::lambda$getValueCodecs$4, Either::left);
    }

    private static <E> RegistryKeyCodec<E> create(RegistryKey<? extends Registry<E>> registryKey, Codec<E> codec, boolean bl) {
        return new RegistryKeyCodec<E>(registryKey, codec, bl);
    }

    private RegistryKeyCodec(RegistryKey<? extends Registry<E>> registryKey, Codec<E> codec, boolean bl) {
        this.registryKey = registryKey;
        this.registryCodec = codec;
        this.allowInlineDefinitions = bl;
    }

    @Override
    public <T> DataResult<T> encode(Supplier<E> supplier, DynamicOps<T> dynamicOps, T t) {
        return dynamicOps instanceof WorldGenSettingsExport ? ((WorldGenSettingsExport)dynamicOps).encode(supplier.get(), t, this.registryKey, this.registryCodec) : this.registryCodec.encode(supplier.get(), dynamicOps, t);
    }

    @Override
    public <T> DataResult<Pair<Supplier<E>, T>> decode(DynamicOps<T> dynamicOps, T t) {
        return dynamicOps instanceof WorldSettingsImport ? ((WorldSettingsImport)dynamicOps).decode(t, this.registryKey, this.registryCodec, this.allowInlineDefinitions) : this.registryCodec.decode(dynamicOps, t).map(RegistryKeyCodec::lambda$decode$7);
    }

    public String toString() {
        return "RegistryFileCodec[" + this.registryKey + " " + this.registryCodec + "]";
    }

    @Override
    public DataResult encode(Object object, DynamicOps dynamicOps, Object object2) {
        return this.encode((Supplier)object, dynamicOps, object2);
    }

    private static Pair lambda$decode$7(Pair pair) {
        return pair.mapFirst(RegistryKeyCodec::lambda$decode$6);
    }

    private static Supplier lambda$decode$6(Object object) {
        return () -> RegistryKeyCodec.lambda$decode$5(object);
    }

    private static Object lambda$decode$5(Object object) {
        return object;
    }

    private static List lambda$getValueCodecs$4(Either either) {
        return either.map(RegistryKeyCodec::lambda$getValueCodecs$2, RegistryKeyCodec::lambda$getValueCodecs$3);
    }

    private static List lambda$getValueCodecs$3(List list) {
        return list;
    }

    private static List lambda$getValueCodecs$2(List list) {
        return list;
    }

    private static Supplier lambda$getValueCodecs$1(Object object) {
        return () -> RegistryKeyCodec.lambda$getValueCodecs$0(object);
    }

    private static Object lambda$getValueCodecs$0(Object object) {
        return object;
    }
}

