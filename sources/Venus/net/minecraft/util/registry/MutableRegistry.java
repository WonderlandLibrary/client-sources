/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.registry;

import com.mojang.serialization.Lifecycle;
import java.util.OptionalInt;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;

public abstract class MutableRegistry<T>
extends Registry<T> {
    public MutableRegistry(RegistryKey<? extends Registry<T>> registryKey, Lifecycle lifecycle) {
        super(registryKey, lifecycle);
    }

    public abstract <V extends T> V register(int var1, RegistryKey<T> var2, V var3, Lifecycle var4);

    public abstract <V extends T> V register(RegistryKey<T> var1, V var2, Lifecycle var3);

    public abstract <V extends T> V validateAndRegister(OptionalInt var1, RegistryKey<T> var2, V var3, Lifecycle var4);
}

