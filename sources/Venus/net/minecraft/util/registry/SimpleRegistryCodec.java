/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.registry;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.util.registry.WorldSettingsImport;

public final class SimpleRegistryCodec<E>
implements Codec<SimpleRegistry<E>> {
    private final Codec<SimpleRegistry<E>> registryCodec;
    private final RegistryKey<? extends Registry<E>> registryKey;
    private final Codec<E> rawCodec;

    public static <E> SimpleRegistryCodec<E> create(RegistryKey<? extends Registry<E>> registryKey, Lifecycle lifecycle, Codec<E> codec) {
        return new SimpleRegistryCodec<E>(registryKey, lifecycle, codec);
    }

    private SimpleRegistryCodec(RegistryKey<? extends Registry<E>> registryKey, Lifecycle lifecycle, Codec<E> codec) {
        this.registryCodec = SimpleRegistry.getUnboundedRegistryCodec(registryKey, lifecycle, codec);
        this.registryKey = registryKey;
        this.rawCodec = codec;
    }

    @Override
    public <T> DataResult<T> encode(SimpleRegistry<E> simpleRegistry, DynamicOps<T> dynamicOps, T t) {
        return this.registryCodec.encode(simpleRegistry, dynamicOps, t);
    }

    @Override
    public <T> DataResult<Pair<SimpleRegistry<E>, T>> decode(DynamicOps<T> dynamicOps, T t) {
        DataResult<Pair<SimpleRegistry<E>, T>> dataResult = this.registryCodec.decode(dynamicOps, t);
        return dynamicOps instanceof WorldSettingsImport ? dataResult.flatMap(arg_0 -> this.lambda$decode$1(dynamicOps, arg_0)) : dataResult;
    }

    public String toString() {
        return "RegistryDataPackCodec[" + this.registryCodec + " " + this.registryKey + " " + this.rawCodec + "]";
    }

    @Override
    public DataResult encode(Object object, DynamicOps dynamicOps, Object object2) {
        return this.encode((SimpleRegistry)object, dynamicOps, object2);
    }

    private DataResult lambda$decode$1(DynamicOps dynamicOps, Pair pair) {
        return ((WorldSettingsImport)dynamicOps).decode((SimpleRegistry)pair.getFirst(), this.registryKey, this.rawCodec).map(arg_0 -> SimpleRegistryCodec.lambda$decode$0(pair, arg_0));
    }

    private static Object lambda$decode$0(Pair pair, Object object) {
        return Pair.of(object, pair.getSecond());
    }
}

