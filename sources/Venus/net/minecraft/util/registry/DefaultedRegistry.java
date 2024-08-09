/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.registry;

import com.mojang.serialization.Lifecycle;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;

public class DefaultedRegistry<T>
extends SimpleRegistry<T> {
    private final ResourceLocation defaultValueKey;
    private T defaultValue;

    public DefaultedRegistry(String string, RegistryKey<? extends Registry<T>> registryKey, Lifecycle lifecycle) {
        super(registryKey, lifecycle);
        this.defaultValueKey = new ResourceLocation(string);
    }

    @Override
    public <V extends T> V register(int n, RegistryKey<T> registryKey, V v, Lifecycle lifecycle) {
        if (this.defaultValueKey.equals(registryKey.getLocation())) {
            this.defaultValue = v;
        }
        return super.register(n, registryKey, v, lifecycle);
    }

    @Override
    public int getId(@Nullable T t) {
        int n = super.getId(t);
        return n == -1 ? super.getId(this.defaultValue) : n;
    }

    @Override
    @Nonnull
    public ResourceLocation getKey(T t) {
        ResourceLocation resourceLocation = super.getKey(t);
        return resourceLocation == null ? this.defaultValueKey : resourceLocation;
    }

    @Override
    @Nonnull
    public T getOrDefault(@Nullable ResourceLocation resourceLocation) {
        Object t = super.getOrDefault(resourceLocation);
        return t == null ? this.defaultValue : t;
    }

    @Override
    public Optional<T> getOptional(@Nullable ResourceLocation resourceLocation) {
        return Optional.ofNullable(super.getOrDefault(resourceLocation));
    }

    @Override
    @Nonnull
    public T getByValue(int n) {
        Object t = super.getByValue(n);
        return t == null ? this.defaultValue : t;
    }

    @Override
    @Nonnull
    public T getRandom(Random random2) {
        Object t = super.getRandom(random2);
        return t == null ? this.defaultValue : t;
    }

    public ResourceLocation getDefaultKey() {
        return this.defaultValueKey;
    }
}

