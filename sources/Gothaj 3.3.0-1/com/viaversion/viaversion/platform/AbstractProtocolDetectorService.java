package com.viaversion.viaversion.platform;

import com.viaversion.viaversion.api.platform.ProtocolDetectorService;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class AbstractProtocolDetectorService implements ProtocolDetectorService {
   protected final Object2IntMap<String> detectedProtocolIds = new Object2IntOpenHashMap<>();
   protected final ReadWriteLock lock = new ReentrantReadWriteLock();

   protected AbstractProtocolDetectorService() {
      this.detectedProtocolIds.defaultReturnValue(-1);
   }

   @Override
   public int serverProtocolVersion(String serverName) {
      this.lock.readLock().lock();

      int detectedProtocol;
      try {
         detectedProtocol = this.detectedProtocolIds.getInt(serverName);
      } finally {
         this.lock.readLock().unlock();
      }

      if (detectedProtocol != -1) {
         return detectedProtocol;
      } else {
         Map<String, Integer> servers = this.configuredServers();
         Integer protocol = servers.get(serverName);
         if (protocol != null) {
            return protocol;
         } else {
            Integer defaultProtocol = servers.get("default");
            return defaultProtocol != null ? defaultProtocol : this.lowestSupportedProtocolVersion();
         }
      }
   }

   @Override
   public void setProtocolVersion(String serverName, int protocolVersion) {
      this.lock.writeLock().lock();

      try {
         this.detectedProtocolIds.put(serverName, protocolVersion);
      } finally {
         this.lock.writeLock().unlock();
      }
   }

   @Override
   public int uncacheProtocolVersion(String serverName) {
      this.lock.writeLock().lock();

      int var2;
      try {
         var2 = this.detectedProtocolIds.removeInt(serverName);
      } finally {
         this.lock.writeLock().unlock();
      }

      return var2;
   }

   @Override
   public Object2IntMap<String> detectedProtocolVersions() {
      this.lock.readLock().lock();

      Object2IntOpenHashMap var1;
      try {
         var1 = new Object2IntOpenHashMap<>(this.detectedProtocolIds);
      } finally {
         this.lock.readLock().unlock();
      }

      return var1;
   }

   protected abstract Map<String, Integer> configuredServers();

   protected abstract int lowestSupportedProtocolVersion();
}
