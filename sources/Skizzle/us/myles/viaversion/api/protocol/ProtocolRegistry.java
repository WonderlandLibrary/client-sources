/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Range
 *  com.google.common.util.concurrent.ThreadFactoryBuilder
 *  org.jetbrains.annotations.Nullable
 */
package us.myles.ViaVersion.api.protocol;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.jetbrains.annotations.Nullable;
import us.myles.ViaVersion.api.Pair;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.MappingDataLoader;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;
import us.myles.ViaVersion.protocols.base.BaseProtocol;
import us.myles.ViaVersion.protocols.base.BaseProtocol1_16;
import us.myles.ViaVersion.protocols.base.BaseProtocol1_7;
import us.myles.ViaVersion.protocols.protocol1_10to1_9_3.Protocol1_10To1_9_3_4;
import us.myles.ViaVersion.protocols.protocol1_11_1to1_11.Protocol1_11_1To1_11;
import us.myles.ViaVersion.protocols.protocol1_11to1_10.Protocol1_11To1_10;
import us.myles.ViaVersion.protocols.protocol1_12_1to1_12.Protocol1_12_1To1_12;
import us.myles.ViaVersion.protocols.protocol1_12_2to1_12_1.Protocol1_12_2To1_12_1;
import us.myles.ViaVersion.protocols.protocol1_12to1_11_1.Protocol1_12To1_11_1;
import us.myles.ViaVersion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13;
import us.myles.ViaVersion.protocols.protocol1_13_2to1_13_1.Protocol1_13_2To1_13_1;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import us.myles.ViaVersion.protocols.protocol1_14_1to1_14.Protocol1_14_1To1_14;
import us.myles.ViaVersion.protocols.protocol1_14_2to1_14_1.Protocol1_14_2To1_14_1;
import us.myles.ViaVersion.protocols.protocol1_14_3to1_14_2.Protocol1_14_3To1_14_2;
import us.myles.ViaVersion.protocols.protocol1_14_4to1_14_3.Protocol1_14_4To1_14_3;
import us.myles.ViaVersion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
import us.myles.ViaVersion.protocols.protocol1_15_1to1_15.Protocol1_15_1To1_15;
import us.myles.ViaVersion.protocols.protocol1_15_2to1_15_1.Protocol1_15_2To1_15_1;
import us.myles.ViaVersion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
import us.myles.ViaVersion.protocols.protocol1_16_1to1_16.Protocol1_16_1To1_16;
import us.myles.ViaVersion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1;
import us.myles.ViaVersion.protocols.protocol1_16_3to1_16_2.Protocol1_16_3To1_16_2;
import us.myles.ViaVersion.protocols.protocol1_16_4to1_16_3.Protocol1_16_4To1_16_3;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import us.myles.ViaVersion.protocols.protocol1_17to1_16_4.Protocol1_17To1_16_4;
import us.myles.ViaVersion.protocols.protocol1_9_1_2to1_9_3_4.Protocol1_9_1_2To1_9_3_4;
import us.myles.ViaVersion.protocols.protocol1_9_1to1_9.Protocol1_9_1To1_9;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.Protocol1_9_3To1_9_1_2;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import us.myles.ViaVersion.protocols.protocol1_9to1_9_1.Protocol1_9To1_9_1;
import us.myles.viaversion.libs.fastutil.ints.Int2ObjectMap;
import us.myles.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;

public class ProtocolRegistry {
    public static final Protocol BASE_PROTOCOL = new BaseProtocol();
    public static int SERVER_PROTOCOL = -1;
    public static int maxProtocolPathSize = 50;
    private static final Int2ObjectMap<Int2ObjectMap<Protocol>> registryMap = new Int2ObjectOpenHashMap<Int2ObjectMap<Protocol>>(32);
    private static final Map<Class<? extends Protocol>, Protocol> protocols = new HashMap<Class<? extends Protocol>, Protocol>();
    private static final Map<Pair<Integer, Integer>, List<Pair<Integer, Protocol>>> pathCache = new ConcurrentHashMap<Pair<Integer, Integer>, List<Pair<Integer, Protocol>>>();
    private static final Set<Integer> supportedVersions = new HashSet<Integer>();
    private static final List<Pair<Range<Integer>, Protocol>> baseProtocols = Lists.newCopyOnWriteArrayList();
    private static final List<Protocol> registerList = new ArrayList<Protocol>();
    private static final Object MAPPING_LOADER_LOCK = new Object();
    private static Map<Class<? extends Protocol>, CompletableFuture<Void>> mappingLoaderFutures = new HashMap<Class<? extends Protocol>, CompletableFuture<Void>>();
    private static ThreadPoolExecutor mappingLoaderExecutor;
    private static boolean mappingsLoaded;

    public static void init() {
    }

    public static void registerProtocol(Protocol protocol, ProtocolVersion supported, ProtocolVersion output) {
        ProtocolRegistry.registerProtocol(protocol, Collections.singletonList(supported.getVersion()), output.getVersion());
    }

    public static void registerProtocol(Protocol protocol, List<Integer> supported, int output) {
        if (!pathCache.isEmpty()) {
            pathCache.clear();
        }
        protocols.put(protocol.getClass(), protocol);
        for (int version : supported) {
            Int2ObjectMap protocolMap = registryMap.computeIfAbsent(version, s -> new Int2ObjectOpenHashMap(2));
            protocolMap.put(output, protocol);
        }
        if (Via.getPlatform().isPluginEnabled()) {
            protocol.register(Via.getManager().getProviders());
            ProtocolRegistry.refreshVersions();
        } else {
            registerList.add(protocol);
        }
        if (protocol.hasMappingDataToLoad()) {
            if (mappingLoaderExecutor != null) {
                ProtocolRegistry.addMappingLoaderFuture(protocol.getClass(), protocol::loadMappingData);
            } else {
                protocol.loadMappingData();
            }
        }
    }

    public static void registerBaseProtocol(Protocol baseProtocol, Range<Integer> supportedProtocols) {
        baseProtocols.add(new Pair<Range<Integer>, Protocol>(supportedProtocols, baseProtocol));
        if (Via.getPlatform().isPluginEnabled()) {
            baseProtocol.register(Via.getManager().getProviders());
            ProtocolRegistry.refreshVersions();
        } else {
            registerList.add(baseProtocol);
        }
    }

    public static void refreshVersions() {
        supportedVersions.clear();
        supportedVersions.add(SERVER_PROTOCOL);
        for (ProtocolVersion versions : ProtocolVersion.getProtocols()) {
            List<Pair<Integer, Protocol>> paths = ProtocolRegistry.getProtocolPath(versions.getVersion(), SERVER_PROTOCOL);
            if (paths == null) continue;
            supportedVersions.add(versions.getVersion());
            for (Pair<Integer, Protocol> path : paths) {
                supportedVersions.add(path.getKey());
            }
        }
    }

    public static SortedSet<Integer> getSupportedVersions() {
        return Collections.unmodifiableSortedSet(new TreeSet<Integer>(supportedVersions));
    }

    public static boolean isWorkingPipe() {
        for (Int2ObjectMap map : registryMap.values()) {
            if (!map.containsKey(SERVER_PROTOCOL)) continue;
            return true;
        }
        return false;
    }

    public static void onServerLoaded() {
        for (Protocol protocol : registerList) {
            protocol.register(Via.getManager().getProviders());
        }
        registerList.clear();
    }

    @Nullable
    private static List<Pair<Integer, Protocol>> getProtocolPath(List<Pair<Integer, Protocol>> current, int clientVersion, int serverVersion) {
        if (clientVersion == serverVersion) {
            return null;
        }
        if (current.size() > maxProtocolPathSize) {
            return null;
        }
        Int2ObjectMap inputMap = (Int2ObjectMap)registryMap.get(clientVersion);
        if (inputMap == null) {
            return null;
        }
        Protocol protocol = (Protocol)inputMap.get(serverVersion);
        if (protocol != null) {
            current.add(new Pair<Integer, Protocol>(serverVersion, protocol));
            return current;
        }
        List<Pair<Integer, Protocol>> shortest = null;
        for (Int2ObjectMap.Entry entry : inputMap.int2ObjectEntrySet()) {
            Pair pair;
            if (entry.getIntKey() == serverVersion || current.contains(pair = new Pair(entry.getIntKey(), entry.getValue()))) continue;
            List<Pair<Integer, Protocol>> newCurrent = new ArrayList<Pair<Integer, Protocol>>(current);
            newCurrent.add(pair);
            if ((newCurrent = ProtocolRegistry.getProtocolPath(newCurrent, entry.getKey(), serverVersion)) == null || shortest != null && shortest.size() <= newCurrent.size()) continue;
            shortest = newCurrent;
        }
        return shortest;
    }

    @Nullable
    public static List<Pair<Integer, Protocol>> getProtocolPath(int clientVersion, int serverVersion) {
        Pair<Integer, Integer> protocolKey = new Pair<Integer, Integer>(clientVersion, serverVersion);
        List<Pair<Integer, Protocol>> protocolList = pathCache.get(protocolKey);
        if (protocolList != null) {
            return protocolList;
        }
        List<Pair<Integer, Protocol>> outputPath = ProtocolRegistry.getProtocolPath(new ArrayList<Pair<Integer, Protocol>>(), clientVersion, serverVersion);
        if (outputPath != null) {
            pathCache.put(protocolKey, outputPath);
        }
        return outputPath;
    }

    @Nullable
    public static Protocol getProtocol(Class<? extends Protocol> protocolClass) {
        return protocols.get(protocolClass);
    }

    public static Protocol getBaseProtocol(int serverVersion) {
        for (Pair rangeProtocol : Lists.reverse(baseProtocols)) {
            if (!((Range)rangeProtocol.getKey()).contains((Comparable)Integer.valueOf(serverVersion))) continue;
            return (Protocol)rangeProtocol.getValue();
        }
        throw new IllegalStateException("No Base Protocol for " + serverVersion);
    }

    public static boolean isBaseProtocol(Protocol protocol) {
        for (Pair<Range<Integer>, Protocol> p : baseProtocols) {
            if (p.getValue() != protocol) continue;
            return true;
        }
        return false;
    }

    public static void completeMappingDataLoading(Class<? extends Protocol> protocolClass) throws Exception {
        if (mappingsLoaded) {
            return;
        }
        CompletableFuture<Void> future = ProtocolRegistry.getMappingLoaderFuture(protocolClass);
        if (future == null) {
            return;
        }
        future.get();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean checkForMappingCompletion() {
        Object object = MAPPING_LOADER_LOCK;
        synchronized (object) {
            if (mappingsLoaded) {
                return false;
            }
            for (CompletableFuture<Void> future : mappingLoaderFutures.values()) {
                if (future.isDone()) continue;
                return false;
            }
            ProtocolRegistry.shutdownLoaderExecutor();
            return true;
        }
    }

    private static void shutdownLoaderExecutor() {
        Via.getPlatform().getLogger().info("Shutting down mapping loader executor!");
        mappingsLoaded = true;
        mappingLoaderExecutor.shutdown();
        mappingLoaderExecutor = null;
        mappingLoaderFutures.clear();
        mappingLoaderFutures = null;
        if (MappingDataLoader.isCacheJsonMappings()) {
            MappingDataLoader.getMappingsCache().clear();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void addMappingLoaderFuture(Class<? extends Protocol> protocolClass, Runnable runnable) {
        Object object = MAPPING_LOADER_LOCK;
        synchronized (object) {
            CompletionStage future = CompletableFuture.runAsync(runnable, mappingLoaderExecutor).exceptionally(throwable -> {
                Via.getPlatform().getLogger().severe("Error during mapping loading of " + protocolClass.getSimpleName());
                throwable.printStackTrace();
                return null;
            });
            mappingLoaderFutures.put(protocolClass, (CompletableFuture<Void>)future);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void addMappingLoaderFuture(Class<? extends Protocol> protocolClass, Class<? extends Protocol> dependsOn, Runnable runnable) {
        Object object = MAPPING_LOADER_LOCK;
        synchronized (object) {
            CompletionStage future = ((CompletableFuture)ProtocolRegistry.getMappingLoaderFuture(dependsOn).whenCompleteAsync((v, throwable) -> runnable.run(), (Executor)mappingLoaderExecutor)).exceptionally(throwable -> {
                Via.getPlatform().getLogger().severe("Error during mapping loading of " + protocolClass.getSimpleName());
                throwable.printStackTrace();
                return null;
            });
            mappingLoaderFutures.put(protocolClass, (CompletableFuture<Void>)future);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public static CompletableFuture<Void> getMappingLoaderFuture(Class<? extends Protocol> protocolClass) {
        Object object = MAPPING_LOADER_LOCK;
        synchronized (object) {
            if (mappingsLoaded) {
                return null;
            }
            return mappingLoaderFutures.get(protocolClass);
        }
    }

    static {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("Via-Mappingloader-%d").build();
        mappingLoaderExecutor = new ThreadPoolExecutor(5, 16, 45L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), threadFactory);
        mappingLoaderExecutor.allowCoreThreadTimeOut(true);
        ProtocolRegistry.registerBaseProtocol(BASE_PROTOCOL, (Range<Integer>)Range.lessThan((Comparable)Integer.valueOf(Integer.MIN_VALUE)));
        ProtocolRegistry.registerBaseProtocol(new BaseProtocol1_7(), (Range<Integer>)Range.lessThan((Comparable)Integer.valueOf(ProtocolVersion.v1_16.getVersion())));
        ProtocolRegistry.registerBaseProtocol(new BaseProtocol1_16(), (Range<Integer>)Range.atLeast((Comparable)Integer.valueOf(ProtocolVersion.v1_16.getVersion())));
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_9To1_8(), ProtocolVersion.v1_9, ProtocolVersion.v1_8);
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_9_1To1_9(), Arrays.asList(ProtocolVersion.v1_9_1.getVersion(), ProtocolVersion.v1_9_2.getVersion()), ProtocolVersion.v1_9.getVersion());
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_9_3To1_9_1_2(), ProtocolVersion.v1_9_3, ProtocolVersion.v1_9_2);
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_9To1_9_1(), ProtocolVersion.v1_9, ProtocolVersion.v1_9_2);
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_9_1_2To1_9_3_4(), Arrays.asList(ProtocolVersion.v1_9_1.getVersion(), ProtocolVersion.v1_9_2.getVersion()), ProtocolVersion.v1_9_3.getVersion());
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_10To1_9_3_4(), ProtocolVersion.v1_10, ProtocolVersion.v1_9_3);
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_11To1_10(), ProtocolVersion.v1_11, ProtocolVersion.v1_10);
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_11_1To1_11(), ProtocolVersion.v1_11_1, ProtocolVersion.v1_11);
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_12To1_11_1(), ProtocolVersion.v1_12, ProtocolVersion.v1_11_1);
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_12_1To1_12(), ProtocolVersion.v1_12_1, ProtocolVersion.v1_12);
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_12_2To1_12_1(), ProtocolVersion.v1_12_2, ProtocolVersion.v1_12_1);
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_13To1_12_2(), ProtocolVersion.v1_13, ProtocolVersion.v1_12_2);
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_13_1To1_13(), ProtocolVersion.v1_13_1, ProtocolVersion.v1_13);
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_13_2To1_13_1(), ProtocolVersion.v1_13_2, ProtocolVersion.v1_13_1);
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_14To1_13_2(), ProtocolVersion.v1_14, ProtocolVersion.v1_13_2);
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_14_1To1_14(), ProtocolVersion.v1_14_1, ProtocolVersion.v1_14);
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_14_2To1_14_1(), ProtocolVersion.v1_14_2, ProtocolVersion.v1_14_1);
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_14_3To1_14_2(), ProtocolVersion.v1_14_3, ProtocolVersion.v1_14_2);
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_14_4To1_14_3(), ProtocolVersion.v1_14_4, ProtocolVersion.v1_14_3);
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_15To1_14_4(), ProtocolVersion.v1_15, ProtocolVersion.v1_14_4);
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_15_1To1_15(), ProtocolVersion.v1_15_1, ProtocolVersion.v1_15);
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_15_2To1_15_1(), ProtocolVersion.v1_15_2, ProtocolVersion.v1_15_1);
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_16To1_15_2(), ProtocolVersion.v1_16, ProtocolVersion.v1_15_2);
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_16_1To1_16(), ProtocolVersion.v1_16_1, ProtocolVersion.v1_16);
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_16_2To1_16_1(), ProtocolVersion.v1_16_2, ProtocolVersion.v1_16_1);
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_16_3To1_16_2(), ProtocolVersion.v1_16_3, ProtocolVersion.v1_16_2);
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_16_4To1_16_3(), ProtocolVersion.v1_16_4, ProtocolVersion.v1_16_3);
        ProtocolRegistry.registerProtocol((Protocol)new Protocol1_17To1_16_4(), ProtocolVersion.v1_17, ProtocolVersion.v1_16_4);
    }
}

