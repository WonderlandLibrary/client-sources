/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourcePack;
import net.minecraft.util.ResourceLocation;

public interface IResourceManager {
    public Set<String> getResourceNamespaces();

    public IResource getResource(ResourceLocation var1) throws IOException;

    public boolean hasResource(ResourceLocation var1);

    public List<IResource> getAllResources(ResourceLocation var1) throws IOException;

    public Collection<ResourceLocation> getAllResourceLocations(String var1, Predicate<String> var2);

    public Stream<IResourcePack> getResourcePackStream();

    public static enum Instance implements IResourceManager
    {
        INSTANCE;


        @Override
        public Set<String> getResourceNamespaces() {
            return ImmutableSet.of();
        }

        @Override
        public IResource getResource(ResourceLocation resourceLocation) throws IOException {
            throw new FileNotFoundException(resourceLocation.toString());
        }

        @Override
        public boolean hasResource(ResourceLocation resourceLocation) {
            return true;
        }

        @Override
        public List<IResource> getAllResources(ResourceLocation resourceLocation) {
            return ImmutableList.of();
        }

        @Override
        public Collection<ResourceLocation> getAllResourceLocations(String string, Predicate<String> predicate) {
            return ImmutableSet.of();
        }

        @Override
        public Stream<IResourcePack> getResourcePackStream() {
            return Stream.of(new IResourcePack[0]);
        }
    }
}

