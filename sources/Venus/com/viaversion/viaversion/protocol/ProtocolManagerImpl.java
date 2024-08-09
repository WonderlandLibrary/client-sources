/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocol;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.ProtocolManager;
import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;
import com.viaversion.viaversion.api.protocol.ProtocolPathKey;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.VersionedPacketTransformer;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectSortedMap;
import com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator;
import com.viaversion.viaversion.protocol.ProtocolPathEntryImpl;
import com.viaversion.viaversion.protocol.ProtocolPathKeyImpl;
import com.viaversion.viaversion.protocol.ServerProtocolVersionSingleton;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import com.viaversion.viaversion.protocol.packet.VersionedPacketTransformerImpl;
import com.viaversion.viaversion.protocols.base.BaseProtocol;
import com.viaversion.viaversion.protocols.base.BaseProtocol1_16;
import com.viaversion.viaversion.protocols.base.BaseProtocol1_7;
import com.viaversion.viaversion.protocols.protocol1_10to1_9_3.Protocol1_10To1_9_3_4;
import com.viaversion.viaversion.protocols.protocol1_11_1to1_11.Protocol1_11_1To1_11;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.Protocol1_12_1To1_12;
import com.viaversion.viaversion.protocols.protocol1_12_2to1_12_1.Protocol1_12_2To1_12_1;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.Protocol1_12To1_11_1;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13;
import com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.Protocol1_13_2To1_13_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_14_1to1_14.Protocol1_14_1To1_14;
import com.viaversion.viaversion.protocols.protocol1_14_2to1_14_1.Protocol1_14_2To1_14_1;
import com.viaversion.viaversion.protocols.protocol1_14_3to1_14_2.Protocol1_14_3To1_14_2;
import com.viaversion.viaversion.protocols.protocol1_14_4to1_14_3.Protocol1_14_4To1_14_3;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_15_1to1_15.Protocol1_15_1To1_15;
import com.viaversion.viaversion.protocols.protocol1_15_2to1_15_1.Protocol1_15_2To1_15_1;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
import com.viaversion.viaversion.protocols.protocol1_16_1to1_16.Protocol1_16_1To1_16;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1;
import com.viaversion.viaversion.protocols.protocol1_16_3to1_16_2.Protocol1_16_3To1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_4to1_16_3.Protocol1_16_4To1_16_3;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.Protocol1_17_1To1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.Protocol1_17To1_16_4;
import com.viaversion.viaversion.protocols.protocol1_18_2to1_18.Protocol1_18_2To1_18;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.Protocol1_18To1_17_1;
import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.Protocol1_19_1To1_19;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.Protocol1_19_3To1_19_1;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.Protocol1_19_4To1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.Protocol1_19To1_18_2;
import com.viaversion.viaversion.protocols.protocol1_20to1_19_4.Protocol1_20To1_19_4;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.Protocol1_9_1_2To1_9_3_4;
import com.viaversion.viaversion.protocols.protocol1_9_1to1_9.Protocol1_9_1To1_9;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.Protocol1_9_3To1_9_1_2;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_9_1.Protocol1_9To1_9_1;
import com.viaversion.viaversion.util.Pair;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.Nullable;
import us.myles.ViaVersion.api.protocol.ProtocolRegistry;

public class ProtocolManagerImpl
implements ProtocolManager {
    private static final Protocol BASE_PROTOCOL = new BaseProtocol();
    private final Int2ObjectMap<Int2ObjectMap<Protocol>> registryMap = new Int2ObjectOpenHashMap<Int2ObjectMap<Protocol>>(32);
    private final Map<Class<? extends Protocol>, Protocol<?, ?, ?, ?>> protocols = new HashMap(64);
    private final Map<ProtocolPathKey, List<ProtocolPathEntry>> pathCache = new ConcurrentHashMap<ProtocolPathKey, List<ProtocolPathEntry>>();
    private final Set<Integer> supportedVersions = new HashSet<Integer>();
    private final List<Pair<Range<Integer>, Protocol>> baseProtocols = Lists.newCopyOnWriteArrayList();
    private final ReadWriteLock mappingLoaderLock = new ReentrantReadWriteLock();
    private Map<Class<? extends Protocol>, CompletableFuture<Void>> mappingLoaderFutures = new HashMap<Class<? extends Protocol>, CompletableFuture<Void>>();
    private ThreadPoolExecutor mappingLoaderExecutor;
    private boolean mappingsLoaded;
    private ServerProtocolVersion serverProtocolVersion = new ServerProtocolVersionSingleton(-1);
    private int maxPathDeltaIncrease;
    private int maxProtocolPathSize = 50;

    public ProtocolManagerImpl() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("Via-Mappingloader-%d").build();
        this.mappingLoaderExecutor = new ThreadPoolExecutor(12, Integer.MAX_VALUE, 30L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), threadFactory);
        this.mappingLoaderExecutor.allowCoreThreadTimeOut(false);
    }

    public void registerProtocols() {
        this.registerBaseProtocol(BASE_PROTOCOL, Range.lessThan(Integer.MIN_VALUE));
        this.registerBaseProtocol(new BaseProtocol1_7(), Range.lessThan(ProtocolVersion.v1_16.getVersion()));
        this.registerBaseProtocol(new BaseProtocol1_16(), Range.atLeast(ProtocolVersion.v1_16.getVersion()));
        this.registerProtocol((Protocol)new Protocol1_9To1_8(), ProtocolVersion.v1_9, ProtocolVersion.v1_8);
        this.registerProtocol((Protocol)new Protocol1_9_1To1_9(), Arrays.asList(ProtocolVersion.v1_9_1.getVersion(), ProtocolVersion.v1_9_2.getVersion()), ProtocolVersion.v1_9.getVersion());
        this.registerProtocol((Protocol)new Protocol1_9_3To1_9_1_2(), ProtocolVersion.v1_9_3, ProtocolVersion.v1_9_2);
        this.registerProtocol((Protocol)new Protocol1_9To1_9_1(), ProtocolVersion.v1_9, ProtocolVersion.v1_9_1);
        this.registerProtocol((Protocol)new Protocol1_9_1_2To1_9_3_4(), Arrays.asList(ProtocolVersion.v1_9_1.getVersion(), ProtocolVersion.v1_9_2.getVersion()), ProtocolVersion.v1_9_3.getVersion());
        this.registerProtocol((Protocol)new Protocol1_10To1_9_3_4(), ProtocolVersion.v1_10, ProtocolVersion.v1_9_3);
        this.registerProtocol((Protocol)new Protocol1_11To1_10(), ProtocolVersion.v1_11, ProtocolVersion.v1_10);
        this.registerProtocol((Protocol)new Protocol1_11_1To1_11(), ProtocolVersion.v1_11_1, ProtocolVersion.v1_11);
        this.registerProtocol((Protocol)new Protocol1_12To1_11_1(), ProtocolVersion.v1_12, ProtocolVersion.v1_11_1);
        this.registerProtocol((Protocol)new Protocol1_12_1To1_12(), ProtocolVersion.v1_12_1, ProtocolVersion.v1_12);
        this.registerProtocol((Protocol)new Protocol1_12_2To1_12_1(), ProtocolVersion.v1_12_2, ProtocolVersion.v1_12_1);
        this.registerProtocol((Protocol)new Protocol1_13To1_12_2(), ProtocolVersion.v1_13, ProtocolVersion.v1_12_2);
        this.registerProtocol((Protocol)new Protocol1_13_1To1_13(), ProtocolVersion.v1_13_1, ProtocolVersion.v1_13);
        this.registerProtocol((Protocol)new Protocol1_13_2To1_13_1(), ProtocolVersion.v1_13_2, ProtocolVersion.v1_13_1);
        this.registerProtocol((Protocol)new Protocol1_14To1_13_2(), ProtocolVersion.v1_14, ProtocolVersion.v1_13_2);
        this.registerProtocol((Protocol)new Protocol1_14_1To1_14(), ProtocolVersion.v1_14_1, ProtocolVersion.v1_14);
        this.registerProtocol((Protocol)new Protocol1_14_2To1_14_1(), ProtocolVersion.v1_14_2, ProtocolVersion.v1_14_1);
        this.registerProtocol((Protocol)new Protocol1_14_3To1_14_2(), ProtocolVersion.v1_14_3, ProtocolVersion.v1_14_2);
        this.registerProtocol((Protocol)new Protocol1_14_4To1_14_3(), ProtocolVersion.v1_14_4, ProtocolVersion.v1_14_3);
        this.registerProtocol((Protocol)new Protocol1_15To1_14_4(), ProtocolVersion.v1_15, ProtocolVersion.v1_14_4);
        this.registerProtocol((Protocol)new Protocol1_15_1To1_15(), ProtocolVersion.v1_15_1, ProtocolVersion.v1_15);
        this.registerProtocol((Protocol)new Protocol1_15_2To1_15_1(), ProtocolVersion.v1_15_2, ProtocolVersion.v1_15_1);
        this.registerProtocol((Protocol)new Protocol1_16To1_15_2(), ProtocolVersion.v1_16, ProtocolVersion.v1_15_2);
        this.registerProtocol((Protocol)new Protocol1_16_1To1_16(), ProtocolVersion.v1_16_1, ProtocolVersion.v1_16);
        this.registerProtocol((Protocol)new Protocol1_16_2To1_16_1(), ProtocolVersion.v1_16_2, ProtocolVersion.v1_16_1);
        this.registerProtocol((Protocol)new Protocol1_16_3To1_16_2(), ProtocolVersion.v1_16_3, ProtocolVersion.v1_16_2);
        this.registerProtocol((Protocol)new Protocol1_16_4To1_16_3(), ProtocolVersion.v1_16_4, ProtocolVersion.v1_16_3);
        this.registerProtocol((Protocol)new Protocol1_17To1_16_4(), ProtocolVersion.v1_17, ProtocolVersion.v1_16_4);
        this.registerProtocol((Protocol)new Protocol1_17_1To1_17(), ProtocolVersion.v1_17_1, ProtocolVersion.v1_17);
        this.registerProtocol((Protocol)new Protocol1_18To1_17_1(), ProtocolVersion.v1_18, ProtocolVersion.v1_17_1);
        this.registerProtocol((Protocol)new Protocol1_18_2To1_18(), ProtocolVersion.v1_18_2, ProtocolVersion.v1_18);
        this.registerProtocol((Protocol)new Protocol1_19To1_18_2(), ProtocolVersion.v1_19, ProtocolVersion.v1_18_2);
        this.registerProtocol((Protocol)new Protocol1_19_1To1_19(), ProtocolVersion.v1_19_1, ProtocolVersion.v1_19);
        this.registerProtocol((Protocol)new Protocol1_19_3To1_19_1(), ProtocolVersion.v1_19_3, ProtocolVersion.v1_19_1);
        this.registerProtocol((Protocol)new Protocol1_19_4To1_19_3(), ProtocolVersion.v1_19_4, ProtocolVersion.v1_19_3);
        this.registerProtocol((Protocol)new Protocol1_20To1_19_4(), ProtocolVersion.v1_20, ProtocolVersion.v1_19_4);
    }

    @Override
    public void registerProtocol(Protocol protocol, ProtocolVersion protocolVersion, ProtocolVersion protocolVersion2) {
        this.registerProtocol(protocol, Collections.singletonList(protocolVersion.getVersion()), protocolVersion2.getVersion());
    }

    @Override
    public void registerProtocol(Protocol protocol, List<Integer> list, int n) {
        protocol.initialize();
        if (!this.pathCache.isEmpty()) {
            this.pathCache.clear();
        }
        this.protocols.put(protocol.getClass(), protocol);
        for (int n2 : list) {
            Preconditions.checkArgument(n2 != n);
            Int2ObjectMap int2ObjectMap = this.registryMap.computeIfAbsent(n2, ProtocolManagerImpl::lambda$registerProtocol$0);
            int2ObjectMap.put(n, protocol);
        }
        protocol.register(Via.getManager().getProviders());
        if (Via.getManager().isInitialized()) {
            this.refreshVersions();
        }
        if (protocol.hasMappingDataToLoad()) {
            if (this.mappingLoaderExecutor != null) {
                this.addMappingLoaderFuture(protocol.getClass(), protocol::loadMappingData);
            } else {
                protocol.loadMappingData();
            }
        }
    }

    @Override
    public void registerBaseProtocol(Protocol protocol, Range<Integer> range) {
        Preconditions.checkArgument(protocol.isBaseProtocol(), "Protocol is not a base protocol");
        protocol.initialize();
        this.baseProtocols.add(new Pair<Range<Integer>, Protocol>(range, protocol));
        protocol.register(Via.getManager().getProviders());
        if (Via.getManager().isInitialized()) {
            this.refreshVersions();
        }
    }

    public void refreshVersions() {
        this.supportedVersions.clear();
        this.supportedVersions.add(this.serverProtocolVersion.lowestSupportedVersion());
        for (ProtocolVersion protocolVersion : ProtocolVersion.getProtocols()) {
            List<ProtocolPathEntry> list = this.getProtocolPath(protocolVersion.getVersion(), this.serverProtocolVersion.lowestSupportedVersion());
            if (list == null) continue;
            this.supportedVersions.add(protocolVersion.getVersion());
            for (ProtocolPathEntry protocolPathEntry : list) {
                this.supportedVersions.add(protocolPathEntry.outputProtocolVersion());
            }
        }
    }

    @Override
    public @Nullable List<ProtocolPathEntry> getProtocolPath(int n, int n2) {
        if (n == n2) {
            return null;
        }
        ProtocolPathKeyImpl protocolPathKeyImpl = new ProtocolPathKeyImpl(n, n2);
        List<ProtocolPathEntry> list = this.pathCache.get(protocolPathKeyImpl);
        if (list != null) {
            return list;
        }
        Int2ObjectSortedMap<Protocol> int2ObjectSortedMap = this.getProtocolPath(new Int2ObjectLinkedOpenHashMap<Protocol>(), n, n2);
        if (int2ObjectSortedMap == null) {
            return null;
        }
        ArrayList<ProtocolPathEntry> arrayList = new ArrayList<ProtocolPathEntry>(int2ObjectSortedMap.size());
        for (Int2ObjectMap.Entry entry : int2ObjectSortedMap.int2ObjectEntrySet()) {
            arrayList.add(new ProtocolPathEntryImpl(entry.getIntKey(), (Protocol)entry.getValue()));
        }
        this.pathCache.put(protocolPathKeyImpl, arrayList);
        return arrayList;
    }

    @Override
    public <C extends ClientboundPacketType, S extends ServerboundPacketType> VersionedPacketTransformer<C, S> createPacketTransformer(ProtocolVersion protocolVersion, @Nullable Class<C> clazz, @Nullable Class<S> clazz2) {
        Preconditions.checkArgument(clazz != ClientboundPacketType.class && clazz2 != ServerboundPacketType.class);
        return new VersionedPacketTransformerImpl<C, S>(protocolVersion, clazz, clazz2);
    }

    private @Nullable Int2ObjectSortedMap<Protocol> getProtocolPath(Int2ObjectSortedMap<Protocol> int2ObjectSortedMap, int n, int n2) {
        if (int2ObjectSortedMap.size() > this.maxProtocolPathSize) {
            return null;
        }
        Int2ObjectMap int2ObjectMap = (Int2ObjectMap)this.registryMap.get(n);
        if (int2ObjectMap == null) {
            return null;
        }
        Protocol protocol = (Protocol)int2ObjectMap.get(n2);
        if (protocol != null) {
            int2ObjectSortedMap.put(n2, protocol);
            return int2ObjectSortedMap;
        }
        Int2ObjectSortedMap<Protocol> int2ObjectSortedMap2 = null;
        for (Int2ObjectMap.Entry entry : int2ObjectMap.int2ObjectEntrySet()) {
            int n3 = entry.getIntKey();
            if (int2ObjectSortedMap.containsKey(n3) || this.maxPathDeltaIncrease != -1 && Math.abs(n2 - n3) - Math.abs(n2 - n) > this.maxPathDeltaIncrease) continue;
            Int2ObjectSortedMap<Protocol> int2ObjectSortedMap3 = new Int2ObjectLinkedOpenHashMap<Protocol>((Int2ObjectMap<Protocol>)int2ObjectSortedMap);
            int2ObjectSortedMap3.put(n3, (Protocol)entry.getValue());
            if ((int2ObjectSortedMap3 = this.getProtocolPath(int2ObjectSortedMap3, n3, n2)) == null || int2ObjectSortedMap2 != null && int2ObjectSortedMap3.size() >= int2ObjectSortedMap2.size()) continue;
            int2ObjectSortedMap2 = int2ObjectSortedMap3;
        }
        return int2ObjectSortedMap2;
    }

    @Override
    public <T extends Protocol> @Nullable T getProtocol(Class<T> clazz) {
        return (T)this.protocols.get(clazz);
    }

    @Override
    public @Nullable Protocol getProtocol(int n, int n2) {
        Int2ObjectMap int2ObjectMap = (Int2ObjectMap)this.registryMap.get(n);
        return int2ObjectMap != null ? (Protocol)int2ObjectMap.get(n2) : null;
    }

    @Override
    public Protocol getBaseProtocol(int n) {
        for (Pair<Range<Integer>, Protocol> pair : Lists.reverse(this.baseProtocols)) {
            if (!pair.key().contains(n)) continue;
            return pair.value();
        }
        throw new IllegalStateException("No Base Protocol for " + n);
    }

    @Override
    public Collection<Protocol<?, ?, ?, ?>> getProtocols() {
        return Collections.unmodifiableCollection(this.protocols.values());
    }

    @Override
    public ServerProtocolVersion getServerProtocolVersion() {
        return this.serverProtocolVersion;
    }

    public void setServerProtocol(ServerProtocolVersion serverProtocolVersion) {
        this.serverProtocolVersion = serverProtocolVersion;
        ProtocolRegistry.SERVER_PROTOCOL = serverProtocolVersion.lowestSupportedVersion();
    }

    @Override
    public boolean isWorkingPipe() {
        for (Int2ObjectMap int2ObjectMap : this.registryMap.values()) {
            IntBidirectionalIterator intBidirectionalIterator = this.serverProtocolVersion.supportedVersions().iterator();
            while (intBidirectionalIterator.hasNext()) {
                int n = (Integer)intBidirectionalIterator.next();
                if (!int2ObjectMap.containsKey(n)) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    public SortedSet<Integer> getSupportedVersions() {
        return Collections.unmodifiableSortedSet(new TreeSet<Integer>(this.supportedVersions));
    }

    @Override
    public void setMaxPathDeltaIncrease(int n) {
        this.maxPathDeltaIncrease = Math.max(-1, n);
    }

    @Override
    public int getMaxPathDeltaIncrease() {
        return this.maxPathDeltaIncrease;
    }

    @Override
    public int getMaxProtocolPathSize() {
        return this.maxProtocolPathSize;
    }

    @Override
    public void setMaxProtocolPathSize(int n) {
        this.maxProtocolPathSize = n;
    }

    @Override
    public Protocol getBaseProtocol() {
        return BASE_PROTOCOL;
    }

    @Override
    public void completeMappingDataLoading(Class<? extends Protocol> clazz) throws Exception {
        if (this.mappingsLoaded) {
            return;
        }
        CompletableFuture<Void> completableFuture = this.getMappingLoaderFuture(clazz);
        if (completableFuture != null) {
            completableFuture.get();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean checkForMappingCompletion() {
        this.mappingLoaderLock.readLock().lock();
        try {
            if (this.mappingsLoaded) {
                boolean bl = false;
                return bl;
            }
            for (CompletableFuture<Void> completableFuture : this.mappingLoaderFutures.values()) {
                if (completableFuture.isDone()) continue;
                boolean bl = false;
                return bl;
            }
            this.shutdownLoaderExecutor();
            boolean bl = true;
            return bl;
        } finally {
            this.mappingLoaderLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addMappingLoaderFuture(Class<? extends Protocol> clazz, Runnable runnable) {
        CompletionStage completionStage = CompletableFuture.runAsync(runnable, this.mappingLoaderExecutor).exceptionally((Function)this.mappingLoaderThrowable(clazz));
        this.mappingLoaderLock.writeLock().lock();
        try {
            this.mappingLoaderFutures.put(clazz, (CompletableFuture<Void>)completionStage);
        } finally {
            this.mappingLoaderLock.writeLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addMappingLoaderFuture(Class<? extends Protocol> clazz, Class<? extends Protocol> clazz2, Runnable runnable) {
        CompletionStage completionStage = ((CompletableFuture)this.getMappingLoaderFuture(clazz2).whenCompleteAsync((arg_0, arg_1) -> ProtocolManagerImpl.lambda$addMappingLoaderFuture$1(runnable, arg_0, arg_1), (Executor)this.mappingLoaderExecutor)).exceptionally(this.mappingLoaderThrowable(clazz));
        this.mappingLoaderLock.writeLock().lock();
        try {
            this.mappingLoaderFutures.put(clazz, (CompletableFuture<Void>)completionStage);
        } finally {
            this.mappingLoaderLock.writeLock().unlock();
        }
    }

    @Override
    public @Nullable CompletableFuture<Void> getMappingLoaderFuture(Class<? extends Protocol> clazz) {
        this.mappingLoaderLock.readLock().lock();
        try {
            CompletableFuture<Void> completableFuture = this.mappingsLoaded ? null : this.mappingLoaderFutures.get(clazz);
            return completableFuture;
        } finally {
            this.mappingLoaderLock.readLock().unlock();
        }
    }

    @Override
    public PacketWrapper createPacketWrapper(@Nullable PacketType packetType, @Nullable ByteBuf byteBuf, UserConnection userConnection) {
        return new PacketWrapperImpl(packetType, byteBuf, userConnection);
    }

    @Override
    @Deprecated
    public PacketWrapper createPacketWrapper(int n, @Nullable ByteBuf byteBuf, UserConnection userConnection) {
        return new PacketWrapperImpl(n, byteBuf, userConnection);
    }

    public void shutdownLoaderExecutor() {
        Preconditions.checkArgument(!this.mappingsLoaded);
        Via.getPlatform().getLogger().info("Finished mapping loading, shutting down loader executor!");
        this.mappingsLoaded = true;
        this.mappingLoaderExecutor.shutdown();
        this.mappingLoaderExecutor = null;
        this.mappingLoaderFutures.clear();
        this.mappingLoaderFutures = null;
        MappingDataLoader.clearCache();
    }

    private Function<Throwable, Void> mappingLoaderThrowable(Class<? extends Protocol> clazz) {
        return arg_0 -> ProtocolManagerImpl.lambda$mappingLoaderThrowable$2(clazz, arg_0);
    }

    private static Void lambda$mappingLoaderThrowable$2(Class clazz, Throwable throwable) {
        Via.getPlatform().getLogger().severe("Error during mapping loading of " + clazz.getSimpleName());
        throwable.printStackTrace();
        return null;
    }

    private static void lambda$addMappingLoaderFuture$1(Runnable runnable, Void void_, Throwable throwable) {
        runnable.run();
    }

    private static Int2ObjectMap lambda$registerProtocol$0(int n) {
        return new Int2ObjectOpenHashMap(2);
    }
}

