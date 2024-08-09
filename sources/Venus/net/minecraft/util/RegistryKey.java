/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class RegistryKey<T> {
    private static final Map<String, RegistryKey<?>> UNIVERSAL_KEY_MAP = Collections.synchronizedMap(Maps.newIdentityHashMap());
    private final ResourceLocation parent;
    private final ResourceLocation location;

    public static <T> RegistryKey<T> getOrCreateKey(RegistryKey<? extends Registry<T>> registryKey, ResourceLocation resourceLocation) {
        return RegistryKey.getOrCreateKey(registryKey.location, resourceLocation);
    }

    public static <T> RegistryKey<Registry<T>> getOrCreateRootKey(ResourceLocation resourceLocation) {
        return RegistryKey.getOrCreateKey(Registry.ROOT, resourceLocation);
    }

    private static <T> RegistryKey<T> getOrCreateKey(ResourceLocation resourceLocation, ResourceLocation resourceLocation2) {
        String string = (resourceLocation + ":" + resourceLocation2).intern();
        return UNIVERSAL_KEY_MAP.computeIfAbsent(string, arg_0 -> RegistryKey.lambda$getOrCreateKey$0(resourceLocation, resourceLocation2, arg_0));
    }

    private RegistryKey(ResourceLocation resourceLocation, ResourceLocation resourceLocation2) {
        this.parent = resourceLocation;
        this.location = resourceLocation2;
    }

    public String toString() {
        return "ResourceKey[" + this.parent + " / " + this.location + "]";
    }

    public boolean isParent(RegistryKey<? extends Registry<?>> registryKey) {
        return this.parent.equals(registryKey.getLocation());
    }

    public ResourceLocation getLocation() {
        return this.location;
    }

    public static <T> Function<ResourceLocation, RegistryKey<T>> getKeyCreator(RegistryKey<? extends Registry<T>> registryKey) {
        return arg_0 -> RegistryKey.lambda$getKeyCreator$1(registryKey, arg_0);
    }

    private static RegistryKey lambda$getKeyCreator$1(RegistryKey registryKey, ResourceLocation resourceLocation) {
        return RegistryKey.getOrCreateKey(registryKey, resourceLocation);
    }

    private static RegistryKey lambda$getOrCreateKey$0(ResourceLocation resourceLocation, ResourceLocation resourceLocation2, String string) {
        return new RegistryKey(resourceLocation, resourceLocation2);
    }
}

