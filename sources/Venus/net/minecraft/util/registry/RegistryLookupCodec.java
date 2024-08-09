/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.registry;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.util.stream.Stream;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldSettingsImport;

public final class RegistryLookupCodec<E>
extends MapCodec<Registry<E>> {
    private final RegistryKey<? extends Registry<E>> registryKey;

    public static <E> RegistryLookupCodec<E> getLookUpCodec(RegistryKey<? extends Registry<E>> registryKey) {
        return new RegistryLookupCodec<E>(registryKey);
    }

    private RegistryLookupCodec(RegistryKey<? extends Registry<E>> registryKey) {
        this.registryKey = registryKey;
    }

    @Override
    public <T> RecordBuilder<T> encode(Registry<E> registry, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
        return recordBuilder;
    }

    @Override
    public <T> DataResult<Registry<E>> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
        return dynamicOps instanceof WorldSettingsImport ? ((WorldSettingsImport)dynamicOps).getRegistryByKey(this.registryKey) : DataResult.error("Not a registry ops");
    }

    public String toString() {
        return "RegistryLookupCodec[" + this.registryKey + "]";
    }

    @Override
    public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
        return Stream.empty();
    }

    @Override
    public RecordBuilder encode(Object object, DynamicOps dynamicOps, RecordBuilder recordBuilder) {
        return this.encode((Registry)object, dynamicOps, recordBuilder);
    }
}

