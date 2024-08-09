/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.Optional;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DelegatingDynamicOps;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;

public class WorldGenSettingsExport<T>
extends DelegatingDynamicOps<T> {
    private final DynamicRegistries dynamicRegistries;

    public static <T> WorldGenSettingsExport<T> create(DynamicOps<T> dynamicOps, DynamicRegistries dynamicRegistries) {
        return new WorldGenSettingsExport<T>(dynamicOps, dynamicRegistries);
    }

    private WorldGenSettingsExport(DynamicOps<T> dynamicOps, DynamicRegistries dynamicRegistries) {
        super(dynamicOps);
        this.dynamicRegistries = dynamicRegistries;
    }

    protected <E> DataResult<T> encode(E e, T t, RegistryKey<? extends Registry<E>> registryKey, Codec<E> codec) {
        MutableRegistry mutableRegistry;
        Optional<RegistryKey<E>> optional;
        Optional optional2 = this.dynamicRegistries.func_230521_a_(registryKey);
        if (optional2.isPresent() && (optional = (mutableRegistry = optional2.get()).getOptionalKey(e)).isPresent()) {
            RegistryKey<E> registryKey2 = optional.get();
            return ResourceLocation.CODEC.encode(registryKey2.getLocation(), this.ops, t);
        }
        return codec.encode(e, this, t);
    }
}

