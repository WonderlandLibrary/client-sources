/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.SimpleResource;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FallbackResourceManager
implements IResourceManager {
    private static final Logger LOGGER = LogManager.getLogger();
    protected final List<IResourcePack> resourcePacks = Lists.newArrayList();
    private final ResourcePackType type;
    private final String namespace;

    public FallbackResourceManager(ResourcePackType resourcePackType, String string) {
        this.type = resourcePackType;
        this.namespace = string;
    }

    public void addResourcePack(IResourcePack iResourcePack) {
        this.resourcePacks.add(iResourcePack);
    }

    @Override
    public Set<String> getResourceNamespaces() {
        return ImmutableSet.of(this.namespace);
    }

    @Override
    public IResource getResource(ResourceLocation resourceLocation) throws IOException {
        this.checkResourcePath(resourceLocation);
        IResourcePack iResourcePack = null;
        ResourceLocation resourceLocation2 = FallbackResourceManager.getLocationMcmeta(resourceLocation);
        for (int i = this.resourcePacks.size() - 1; i >= 0; --i) {
            IResourcePack iResourcePack2 = this.resourcePacks.get(i);
            if (iResourcePack == null && iResourcePack2.resourceExists(this.type, resourceLocation2)) {
                iResourcePack = iResourcePack2;
            }
            if (!iResourcePack2.resourceExists(this.type, resourceLocation)) continue;
            InputStream inputStream = null;
            if (iResourcePack != null) {
                inputStream = this.getInputStream(resourceLocation2, iResourcePack);
            }
            return new SimpleResource(iResourcePack2.getName(), resourceLocation, this.getInputStream(resourceLocation, iResourcePack2), inputStream);
        }
        throw new FileNotFoundException(resourceLocation.toString());
    }

    @Override
    public boolean hasResource(ResourceLocation resourceLocation) {
        if (!this.func_219541_f(resourceLocation)) {
            return true;
        }
        for (int i = this.resourcePacks.size() - 1; i >= 0; --i) {
            IResourcePack iResourcePack = this.resourcePacks.get(i);
            if (!iResourcePack.resourceExists(this.type, resourceLocation)) continue;
            return false;
        }
        return true;
    }

    protected InputStream getInputStream(ResourceLocation resourceLocation, IResourcePack iResourcePack) throws IOException {
        InputStream inputStream = iResourcePack.getResourceStream(this.type, resourceLocation);
        return LOGGER.isDebugEnabled() ? new LeakComplainerInputStream(inputStream, resourceLocation, iResourcePack.getName()) : inputStream;
    }

    private void checkResourcePath(ResourceLocation resourceLocation) throws IOException {
        if (!this.func_219541_f(resourceLocation)) {
            throw new IOException("Invalid relative path to resource: " + resourceLocation);
        }
    }

    private boolean func_219541_f(ResourceLocation resourceLocation) {
        return !resourceLocation.getPath().contains("..");
    }

    @Override
    public List<IResource> getAllResources(ResourceLocation resourceLocation) throws IOException {
        this.checkResourcePath(resourceLocation);
        ArrayList<IResource> arrayList = Lists.newArrayList();
        ResourceLocation resourceLocation2 = FallbackResourceManager.getLocationMcmeta(resourceLocation);
        for (IResourcePack iResourcePack : this.resourcePacks) {
            if (!iResourcePack.resourceExists(this.type, resourceLocation)) continue;
            InputStream inputStream = iResourcePack.resourceExists(this.type, resourceLocation2) ? this.getInputStream(resourceLocation2, iResourcePack) : null;
            arrayList.add(new SimpleResource(iResourcePack.getName(), resourceLocation, this.getInputStream(resourceLocation, iResourcePack), inputStream));
        }
        if (arrayList.isEmpty()) {
            throw new FileNotFoundException(resourceLocation.toString());
        }
        return arrayList;
    }

    @Override
    public Collection<ResourceLocation> getAllResourceLocations(String string, Predicate<String> predicate) {
        ArrayList<ResourceLocation> arrayList = Lists.newArrayList();
        for (IResourcePack iResourcePack : this.resourcePacks) {
            arrayList.addAll(iResourcePack.getAllResourceLocations(this.type, this.namespace, string, Integer.MAX_VALUE, predicate));
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    @Override
    public Stream<IResourcePack> getResourcePackStream() {
        return this.resourcePacks.stream();
    }

    static ResourceLocation getLocationMcmeta(ResourceLocation resourceLocation) {
        return new ResourceLocation(resourceLocation.getNamespace(), resourceLocation.getPath() + ".mcmeta");
    }

    static class LeakComplainerInputStream
    extends FilterInputStream {
        private final String message;
        private boolean isClosed;

        public LeakComplainerInputStream(InputStream inputStream, ResourceLocation resourceLocation, String string) {
            super(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            new Exception().printStackTrace(new PrintStream(byteArrayOutputStream));
            this.message = "Leaked resource: '" + resourceLocation + "' loaded from pack: '" + string + "'\n" + byteArrayOutputStream;
        }

        @Override
        public void close() throws IOException {
            super.close();
            this.isClosed = true;
        }

        protected void finalize() throws Throwable {
            if (!this.isClosed) {
                LOGGER.warn(this.message);
            }
            super.finalize();
        }
    }
}

