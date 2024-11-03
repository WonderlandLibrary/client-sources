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
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.Protocol1_20_2To1_20;
import com.viaversion.viaversion.protocols.protocol1_20_3to1_20_2.Protocol1_20_3To1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20to1_19_4.Protocol1_20To1_19_4;
import com.viaversion.viaversion.protocols.protocol1_9_1to1_9.Protocol1_9_1To1_9;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.Protocol1_9_3To1_9_1_2;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.util.Pair;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import java.util.logging.Level;
import org.checkerframework.checker.nullness.qual.Nullable;
import us.myles.ViaVersion.api.protocol.ProtocolRegistry;

public class ProtocolManagerImpl implements ProtocolManager {
   private static final Protocol BASE_PROTOCOL = new BaseProtocol();
   private final Int2ObjectMap<Int2ObjectMap<Protocol>> registryMap = new Int2ObjectOpenHashMap<>(32);
   private final Map<Class<? extends Protocol>, Protocol<?, ?, ?, ?>> protocols = new HashMap<>(64);
   private final Map<ProtocolPathKey, List<ProtocolPathEntry>> pathCache = new ConcurrentHashMap<>();
   private final Set<Integer> supportedVersions = new HashSet<>();
   private final List<Pair<Range<Integer>, Protocol>> baseProtocols = Lists.newCopyOnWriteArrayList();
   private final ReadWriteLock mappingLoaderLock = new ReentrantReadWriteLock();
   private Map<Class<? extends Protocol>, CompletableFuture<Void>> mappingLoaderFutures = new HashMap<>();
   private ThreadPoolExecutor mappingLoaderExecutor;
   private boolean mappingsLoaded;
   private ServerProtocolVersion serverProtocolVersion = new ServerProtocolVersionSingleton(-1);
   private int maxPathDeltaIncrease;
   private int maxProtocolPathSize = 50;

   public ProtocolManagerImpl() {
      ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("Via-Mappingloader-%d").build();
      this.mappingLoaderExecutor = new ThreadPoolExecutor(12, Integer.MAX_VALUE, 30L, TimeUnit.SECONDS, new SynchronousQueue<>(), threadFactory);
      this.mappingLoaderExecutor.allowCoreThreadTimeOut(true);
   }

   public void registerProtocols() {
      this.registerBaseProtocol(BASE_PROTOCOL, Range.lessThan(Integer.MIN_VALUE));
      this.registerBaseProtocol(new BaseProtocol1_7(), Range.lessThan(ProtocolVersion.v1_16.getVersion()));
      this.registerBaseProtocol(new BaseProtocol1_16(), Range.atLeast(ProtocolVersion.v1_16.getVersion()));
      this.registerProtocol(new Protocol1_9To1_8(), ProtocolVersion.v1_9, ProtocolVersion.v1_8);
      this.registerProtocol(
         new Protocol1_9_1To1_9(), Arrays.asList(ProtocolVersion.v1_9_1.getVersion(), ProtocolVersion.v1_9_2.getVersion()), ProtocolVersion.v1_9.getVersion()
      );
      this.registerProtocol(new Protocol1_9_3To1_9_1_2(), ProtocolVersion.v1_9_3, ProtocolVersion.v1_9_2);
      this.registerProtocol(new Protocol1_10To1_9_3_4(), ProtocolVersion.v1_10, ProtocolVersion.v1_9_3);
      this.registerProtocol(new Protocol1_11To1_10(), ProtocolVersion.v1_11, ProtocolVersion.v1_10);
      this.registerProtocol(new Protocol1_11_1To1_11(), ProtocolVersion.v1_11_1, ProtocolVersion.v1_11);
      this.registerProtocol(new Protocol1_12To1_11_1(), ProtocolVersion.v1_12, ProtocolVersion.v1_11_1);
      this.registerProtocol(new Protocol1_12_1To1_12(), ProtocolVersion.v1_12_1, ProtocolVersion.v1_12);
      this.registerProtocol(new Protocol1_12_2To1_12_1(), ProtocolVersion.v1_12_2, ProtocolVersion.v1_12_1);
      this.registerProtocol(new Protocol1_13To1_12_2(), ProtocolVersion.v1_13, ProtocolVersion.v1_12_2);
      this.registerProtocol(new Protocol1_13_1To1_13(), ProtocolVersion.v1_13_1, ProtocolVersion.v1_13);
      this.registerProtocol(new Protocol1_13_2To1_13_1(), ProtocolVersion.v1_13_2, ProtocolVersion.v1_13_1);
      this.registerProtocol(new Protocol1_14To1_13_2(), ProtocolVersion.v1_14, ProtocolVersion.v1_13_2);
      this.registerProtocol(new Protocol1_14_1To1_14(), ProtocolVersion.v1_14_1, ProtocolVersion.v1_14);
      this.registerProtocol(new Protocol1_14_2To1_14_1(), ProtocolVersion.v1_14_2, ProtocolVersion.v1_14_1);
      this.registerProtocol(new Protocol1_14_3To1_14_2(), ProtocolVersion.v1_14_3, ProtocolVersion.v1_14_2);
      this.registerProtocol(new Protocol1_14_4To1_14_3(), ProtocolVersion.v1_14_4, ProtocolVersion.v1_14_3);
      this.registerProtocol(new Protocol1_15To1_14_4(), ProtocolVersion.v1_15, ProtocolVersion.v1_14_4);
      this.registerProtocol(new Protocol1_15_1To1_15(), ProtocolVersion.v1_15_1, ProtocolVersion.v1_15);
      this.registerProtocol(new Protocol1_15_2To1_15_1(), ProtocolVersion.v1_15_2, ProtocolVersion.v1_15_1);
      this.registerProtocol(new Protocol1_16To1_15_2(), ProtocolVersion.v1_16, ProtocolVersion.v1_15_2);
      this.registerProtocol(new Protocol1_16_1To1_16(), ProtocolVersion.v1_16_1, ProtocolVersion.v1_16);
      this.registerProtocol(new Protocol1_16_2To1_16_1(), ProtocolVersion.v1_16_2, ProtocolVersion.v1_16_1);
      this.registerProtocol(new Protocol1_16_3To1_16_2(), ProtocolVersion.v1_16_3, ProtocolVersion.v1_16_2);
      this.registerProtocol(new Protocol1_16_4To1_16_3(), ProtocolVersion.v1_16_4, ProtocolVersion.v1_16_3);
      this.registerProtocol(new Protocol1_17To1_16_4(), ProtocolVersion.v1_17, ProtocolVersion.v1_16_4);
      this.registerProtocol(new Protocol1_17_1To1_17(), ProtocolVersion.v1_17_1, ProtocolVersion.v1_17);
      this.registerProtocol(new Protocol1_18To1_17_1(), ProtocolVersion.v1_18, ProtocolVersion.v1_17_1);
      this.registerProtocol(new Protocol1_18_2To1_18(), ProtocolVersion.v1_18_2, ProtocolVersion.v1_18);
      this.registerProtocol(new Protocol1_19To1_18_2(), ProtocolVersion.v1_19, ProtocolVersion.v1_18_2);
      this.registerProtocol(new Protocol1_19_1To1_19(), ProtocolVersion.v1_19_1, ProtocolVersion.v1_19);
      this.registerProtocol(new Protocol1_19_3To1_19_1(), ProtocolVersion.v1_19_3, ProtocolVersion.v1_19_1);
      this.registerProtocol(new Protocol1_19_4To1_19_3(), ProtocolVersion.v1_19_4, ProtocolVersion.v1_19_3);
      this.registerProtocol(new Protocol1_20To1_19_4(), ProtocolVersion.v1_20, ProtocolVersion.v1_19_4);
      this.registerProtocol(new Protocol1_20_2To1_20(), ProtocolVersion.v1_20_2, ProtocolVersion.v1_20);
      this.registerProtocol(new Protocol1_20_3To1_20_2(), ProtocolVersion.v1_20_3, ProtocolVersion.v1_20_2);
   }

   @Override
   public void registerProtocol(Protocol protocol, ProtocolVersion clientVersion, ProtocolVersion serverVersion) {
      this.registerProtocol(protocol, Collections.singletonList(clientVersion.getVersion()), serverVersion.getVersion());
   }

   @Override
   public void registerProtocol(Protocol protocol, List<Integer> supportedClientVersion, int serverVersion) {
      protocol.initialize();
      if (!this.pathCache.isEmpty()) {
         this.pathCache.clear();
      }

      this.protocols.put((Class<? extends Protocol>)protocol.getClass(), protocol);

      for (int clientVersion : supportedClientVersion) {
         Preconditions.checkArgument(clientVersion != serverVersion);
         Int2ObjectMap<Protocol> protocolMap = this.registryMap.computeIfAbsent(clientVersion, s -> new Int2ObjectOpenHashMap<>(2));
         protocolMap.put(serverVersion, protocol);
      }

      protocol.register(Via.getManager().getProviders());
      if (Via.getManager().isInitialized()) {
         this.refreshVersions();
      }

      if (protocol.hasMappingDataToLoad()) {
         if (this.mappingLoaderExecutor != null) {
            this.addMappingLoaderFuture((Class<? extends Protocol>)protocol.getClass(), protocol::loadMappingData);
         } else {
            protocol.loadMappingData();
         }
      }
   }

   @Override
   public void registerBaseProtocol(Protocol baseProtocol, Range<Integer> supportedProtocols) {
      Preconditions.checkArgument(baseProtocol.isBaseProtocol(), "Protocol is not a base protocol");
      baseProtocol.initialize();
      this.baseProtocols.add(new Pair<>(supportedProtocols, baseProtocol));
      baseProtocol.register(Via.getManager().getProviders());
      if (Via.getManager().isInitialized()) {
         this.refreshVersions();
      }
   }

   public void refreshVersions() {
      this.supportedVersions.clear();
      this.supportedVersions.add(this.serverProtocolVersion.lowestSupportedVersion());

      for (ProtocolVersion version : ProtocolVersion.getProtocols()) {
         List<ProtocolPathEntry> protocolPath = this.getProtocolPath(version.getVersion(), this.serverProtocolVersion.lowestSupportedVersion());
         if (protocolPath != null) {
            this.supportedVersions.add(version.getVersion());

            for (ProtocolPathEntry pathEntry : protocolPath) {
               this.supportedVersions.add(pathEntry.outputProtocolVersion());
            }
         }
      }
   }

   @Nullable
   @Override
   public List<ProtocolPathEntry> getProtocolPath(int clientVersion, int serverVersion) {
      if (clientVersion == serverVersion) {
         return null;
      } else {
         ProtocolPathKey protocolKey = new ProtocolPathKeyImpl(clientVersion, serverVersion);
         List<ProtocolPathEntry> protocolList = this.pathCache.get(protocolKey);
         if (protocolList != null) {
            return protocolList;
         } else {
            Int2ObjectSortedMap<Protocol> outputPath = this.getProtocolPath(new Int2ObjectLinkedOpenHashMap<>(), clientVersion, serverVersion);
            if (outputPath == null) {
               return null;
            } else {
               List<ProtocolPathEntry> path = new ArrayList<>(outputPath.size());

               for (Int2ObjectMap.Entry<Protocol> entry : outputPath.int2ObjectEntrySet()) {
                  path.add(new ProtocolPathEntryImpl(entry.getIntKey(), entry.getValue()));
               }

               this.pathCache.put(protocolKey, path);
               return path;
            }
         }
      }
   }

   @Override
   public <C extends ClientboundPacketType, S extends ServerboundPacketType> VersionedPacketTransformer<C, S> createPacketTransformer(
      ProtocolVersion inputVersion, @Nullable Class<C> clientboundPacketsClass, @Nullable Class<S> serverboundPacketsClass
   ) {
      Preconditions.checkArgument(clientboundPacketsClass != ClientboundPacketType.class && serverboundPacketsClass != ServerboundPacketType.class);
      return new VersionedPacketTransformerImpl<>(inputVersion, clientboundPacketsClass, serverboundPacketsClass);
   }

   @Nullable
   private Int2ObjectSortedMap<Protocol> getProtocolPath(Int2ObjectSortedMap<Protocol> current, int clientVersion, int serverVersion) {
      if (current.size() > this.maxProtocolPathSize) {
         return null;
      } else {
         Int2ObjectMap<Protocol> toServerProtocolMap = this.registryMap.get(clientVersion);
         if (toServerProtocolMap == null) {
            return null;
         } else {
            Protocol protocol = toServerProtocolMap.get(serverVersion);
            if (protocol != null) {
               current.put(serverVersion, protocol);
               return current;
            } else {
               Int2ObjectSortedMap<Protocol> shortest = null;

               for (Int2ObjectMap.Entry<Protocol> entry : toServerProtocolMap.int2ObjectEntrySet()) {
                  int translatedToVersion = entry.getIntKey();
                  if (!current.containsKey(translatedToVersion)
                     && (
                        this.maxPathDeltaIncrease == -1
                           || Math.abs(serverVersion - translatedToVersion) - Math.abs(serverVersion - clientVersion) <= this.maxPathDeltaIncrease
                     )) {
                     Int2ObjectSortedMap<Protocol> newCurrent = new Int2ObjectLinkedOpenHashMap<>(current);
                     newCurrent.put(translatedToVersion, entry.getValue());
                     newCurrent = this.getProtocolPath(newCurrent, translatedToVersion, serverVersion);
                     if (newCurrent != null && (shortest == null || newCurrent.size() < shortest.size())) {
                        shortest = newCurrent;
                     }
                  }
               }

               return shortest;
            }
         }
      }
   }

   @Nullable
   @Override
   public <T extends Protocol> T getProtocol(Class<T> protocolClass) {
      return (T)this.protocols.get(protocolClass);
   }

   @Nullable
   @Override
   public Protocol getProtocol(int clientVersion, int serverVersion) {
      Int2ObjectMap<Protocol> map = this.registryMap.get(clientVersion);
      return map != null ? map.get(serverVersion) : null;
   }

   @Override
   public Protocol getBaseProtocol(int serverVersion) {
      for (Pair<Range<Integer>, Protocol> rangeProtocol : Lists.reverse(this.baseProtocols)) {
         if (rangeProtocol.key().contains(serverVersion)) {
            return rangeProtocol.value();
         }
      }

      throw new IllegalStateException("No Base Protocol for " + serverVersion);
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
      for (Int2ObjectMap<Protocol> map : this.registryMap.values()) {
         for (int protocolVersion : this.serverProtocolVersion.supportedVersions()) {
            if (map.containsKey(protocolVersion)) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public SortedSet<Integer> getSupportedVersions() {
      return Collections.unmodifiableSortedSet(new TreeSet<>(this.supportedVersions));
   }

   @Override
   public void setMaxPathDeltaIncrease(int maxPathDeltaIncrease) {
      this.maxPathDeltaIncrease = Math.max(-1, maxPathDeltaIncrease);
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
   public void setMaxProtocolPathSize(int maxProtocolPathSize) {
      this.maxProtocolPathSize = maxProtocolPathSize;
   }

   @Override
   public Protocol getBaseProtocol() {
      return BASE_PROTOCOL;
   }

   @Override
   public void completeMappingDataLoading(Class<? extends Protocol> protocolClass) throws Exception {
      if (!this.mappingsLoaded) {
         CompletableFuture<Void> future = this.getMappingLoaderFuture(protocolClass);
         if (future != null) {
            future.get();
         }
      }
   }

   @Override
   public boolean checkForMappingCompletion() {
      this.mappingLoaderLock.readLock().lock();

      boolean var3;
      try {
         if (this.mappingsLoaded) {
            return false;
         }

         Iterator var1 = this.mappingLoaderFutures.values().iterator();

         CompletableFuture<Void> future;
         do {
            if (!var1.hasNext()) {
               this.shutdownLoaderExecutor();
               return true;
            }

            future = (CompletableFuture<Void>)var1.next();
         } while (future.isDone());

         var3 = false;
      } finally {
         this.mappingLoaderLock.readLock().unlock();
      }

      return var3;
   }

   @Override
   public void addMappingLoaderFuture(Class<? extends Protocol> protocolClass, Runnable runnable) {
      CompletableFuture<Void> future = CompletableFuture.runAsync(runnable, this.mappingLoaderExecutor)
         .exceptionally(this.mappingLoaderThrowable(protocolClass));
      this.mappingLoaderLock.writeLock().lock();

      try {
         this.mappingLoaderFutures.put(protocolClass, future);
      } finally {
         this.mappingLoaderLock.writeLock().unlock();
      }
   }

   @Override
   public void addMappingLoaderFuture(Class<? extends Protocol> protocolClass, Class<? extends Protocol> dependsOn, Runnable runnable) {
      CompletableFuture<Void> future = this.getMappingLoaderFuture(dependsOn)
         .whenCompleteAsync((v, throwable) -> runnable.run(), this.mappingLoaderExecutor)
         .exceptionally(this.mappingLoaderThrowable(protocolClass));
      this.mappingLoaderLock.writeLock().lock();

      try {
         this.mappingLoaderFutures.put(protocolClass, future);
      } finally {
         this.mappingLoaderLock.writeLock().unlock();
      }
   }

   @Nullable
   @Override
   public CompletableFuture<Void> getMappingLoaderFuture(Class<? extends Protocol> protocolClass) {
      this.mappingLoaderLock.readLock().lock();

      CompletableFuture var2;
      try {
         var2 = this.mappingsLoaded ? null : this.mappingLoaderFutures.get(protocolClass);
      } finally {
         this.mappingLoaderLock.readLock().unlock();
      }

      return var2;
   }

   @Override
   public PacketWrapper createPacketWrapper(@Nullable PacketType packetType, @Nullable ByteBuf buf, UserConnection connection) {
      return new PacketWrapperImpl(packetType, buf, connection);
   }

   @Deprecated
   @Override
   public PacketWrapper createPacketWrapper(int packetId, @Nullable ByteBuf buf, UserConnection connection) {
      return new PacketWrapperImpl(packetId, buf, connection);
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

   private Function<Throwable, Void> mappingLoaderThrowable(Class<? extends Protocol> protocolClass) {
      return throwable -> {
         Via.getPlatform().getLogger().log(Level.SEVERE, "Error during mapping loading of " + protocolClass.getSimpleName(), throwable);
         return null;
      };
   }
}
