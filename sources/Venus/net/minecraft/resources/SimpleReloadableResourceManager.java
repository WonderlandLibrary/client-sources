/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.resources.AsyncReloader;
import net.minecraft.resources.DebugAsyncReloader;
import net.minecraft.resources.FallbackResourceManager;
import net.minecraft.resources.IAsyncReloader;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Unit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleReloadableResourceManager
implements IReloadableResourceManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Map<String, FallbackResourceManager> namespaceResourceManagers = Maps.newHashMap();
    private final List<IFutureReloadListener> reloadListeners = Lists.newArrayList();
    private final List<IFutureReloadListener> initTaskQueue = Lists.newArrayList();
    private final Set<String> resourceNamespaces = Sets.newLinkedHashSet();
    private final List<IResourcePack> resourcePacks = Lists.newArrayList();
    private final ResourcePackType type;

    public SimpleReloadableResourceManager(ResourcePackType resourcePackType) {
        this.type = resourcePackType;
    }

    public void addResourcePack(IResourcePack iResourcePack) {
        this.resourcePacks.add(iResourcePack);
        for (String string : iResourcePack.getResourceNamespaces(this.type)) {
            this.resourceNamespaces.add(string);
            FallbackResourceManager fallbackResourceManager = this.namespaceResourceManagers.get(string);
            if (fallbackResourceManager == null) {
                fallbackResourceManager = new FallbackResourceManager(this.type, string);
                this.namespaceResourceManagers.put(string, fallbackResourceManager);
            }
            fallbackResourceManager.addResourcePack(iResourcePack);
        }
    }

    @Override
    public Set<String> getResourceNamespaces() {
        return this.resourceNamespaces;
    }

    @Override
    public IResource getResource(ResourceLocation resourceLocation) throws IOException {
        IResourceManager iResourceManager = this.namespaceResourceManagers.get(resourceLocation.getNamespace());
        if (iResourceManager != null) {
            return iResourceManager.getResource(resourceLocation);
        }
        throw new FileNotFoundException(resourceLocation.toString());
    }

    @Override
    public boolean hasResource(ResourceLocation resourceLocation) {
        IResourceManager iResourceManager = this.namespaceResourceManagers.get(resourceLocation.getNamespace());
        return iResourceManager != null ? iResourceManager.hasResource(resourceLocation) : false;
    }

    @Override
    public List<IResource> getAllResources(ResourceLocation resourceLocation) throws IOException {
        IResourceManager iResourceManager = this.namespaceResourceManagers.get(resourceLocation.getNamespace());
        if (iResourceManager != null) {
            return iResourceManager.getAllResources(resourceLocation);
        }
        throw new FileNotFoundException(resourceLocation.toString());
    }

    @Override
    public Collection<ResourceLocation> getAllResourceLocations(String string, Predicate<String> predicate) {
        HashSet<ResourceLocation> hashSet = Sets.newHashSet();
        for (FallbackResourceManager fallbackResourceManager : this.namespaceResourceManagers.values()) {
            hashSet.addAll(fallbackResourceManager.getAllResourceLocations(string, predicate));
        }
        ArrayList arrayList = Lists.newArrayList(hashSet);
        Collections.sort(arrayList);
        return arrayList;
    }

    private void clearResourceNamespaces() {
        this.namespaceResourceManagers.clear();
        this.resourceNamespaces.clear();
        this.resourcePacks.forEach(IResourcePack::close);
        this.resourcePacks.clear();
    }

    @Override
    public void close() {
        this.clearResourceNamespaces();
    }

    @Override
    public void addReloadListener(IFutureReloadListener iFutureReloadListener) {
        this.reloadListeners.add(iFutureReloadListener);
        this.initTaskQueue.add(iFutureReloadListener);
    }

    protected IAsyncReloader initializeAsyncReloader(Executor executor, Executor executor2, List<IFutureReloadListener> list, CompletableFuture<Unit> completableFuture) {
        DebugAsyncReloader debugAsyncReloader = LOGGER.isDebugEnabled() ? new DebugAsyncReloader(this, Lists.newArrayList(list), executor, executor2, completableFuture) : AsyncReloader.create(this, Lists.newArrayList(list), executor, executor2, completableFuture);
        this.initTaskQueue.clear();
        return debugAsyncReloader;
    }

    @Override
    public IAsyncReloader reloadResources(Executor executor, Executor executor2, CompletableFuture<Unit> completableFuture, List<IResourcePack> list) {
        this.clearResourceNamespaces();
        LOGGER.info("Reloading ResourceManager: {}", () -> SimpleReloadableResourceManager.lambda$reloadResources$0(list));
        for (IResourcePack iResourcePack : list) {
            try {
                this.addResourcePack(iResourcePack);
            } catch (Exception exception) {
                LOGGER.error("Failed to add resource pack {}", (Object)iResourcePack.getName(), (Object)exception);
                return new FailedPackReloader(new FailedPackException(iResourcePack, (Throwable)exception));
            }
        }
        return this.initializeAsyncReloader(executor, executor2, this.reloadListeners, completableFuture);
    }

    @Override
    public Stream<IResourcePack> getResourcePackStream() {
        return this.resourcePacks.stream();
    }

    private static Object lambda$reloadResources$0(List list) {
        return list.stream().map(IResourcePack::getName).collect(Collectors.joining(", "));
    }

    static class FailedPackReloader
    implements IAsyncReloader {
        private final FailedPackException exception;
        private final CompletableFuture<Unit> onceDone;

        public FailedPackReloader(FailedPackException failedPackException) {
            this.exception = failedPackException;
            this.onceDone = new CompletableFuture();
            this.onceDone.completeExceptionally(failedPackException);
        }

        @Override
        public CompletableFuture<Unit> onceDone() {
            return this.onceDone;
        }

        @Override
        public float estimateExecutionSpeed() {
            return 0.0f;
        }

        @Override
        public boolean asyncPartDone() {
            return true;
        }

        @Override
        public boolean fullyDone() {
            return false;
        }

        @Override
        public void join() {
            throw this.exception;
        }
    }

    public static class FailedPackException
    extends RuntimeException {
        private final IResourcePack pack;

        public FailedPackException(IResourcePack iResourcePack, Throwable throwable) {
            super(iResourcePack.getName(), throwable);
            this.pack = iResourcePack;
        }

        public IResourcePack getPack() {
            return this.pack;
        }
    }
}

