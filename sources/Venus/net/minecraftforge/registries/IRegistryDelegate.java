/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraftforge.registries;

import net.minecraft.util.ResourceLocation;

public interface IRegistryDelegate<T> {
    public T get();

    public ResourceLocation name();

    public Class<T> type();
}

