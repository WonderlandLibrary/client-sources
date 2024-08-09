/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.ResourceLocation;

public interface IResourcePack
extends AutoCloseable {
    public InputStream getRootResourceStream(String var1) throws IOException;

    public InputStream getResourceStream(ResourcePackType var1, ResourceLocation var2) throws IOException;

    public Collection<ResourceLocation> getAllResourceLocations(ResourcePackType var1, String var2, String var3, int var4, Predicate<String> var5);

    public boolean resourceExists(ResourcePackType var1, ResourceLocation var2);

    public Set<String> getResourceNamespaces(ResourcePackType var1);

    @Nullable
    public <T> T getMetadata(IMetadataSectionSerializer<T> var1) throws IOException;

    public String getName();

    @Override
    public void close();
}

