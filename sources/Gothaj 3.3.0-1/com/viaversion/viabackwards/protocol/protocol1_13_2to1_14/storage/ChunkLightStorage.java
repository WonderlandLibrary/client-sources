package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ChunkLightStorage implements StorableObject {
   public static final byte[] FULL_LIGHT = new byte[2048];
   public static final byte[] EMPTY_LIGHT = new byte[2048];
   private static Constructor<?> fastUtilLongObjectHashMap;
   private final Map<Long, ChunkLightStorage.ChunkLight> storedLight = this.createLongObjectMap();

   public void setStoredLight(byte[][] skyLight, byte[][] blockLight, int x, int z) {
      this.storedLight.put(this.getChunkSectionIndex(x, z), new ChunkLightStorage.ChunkLight(skyLight, blockLight));
   }

   public ChunkLightStorage.ChunkLight getStoredLight(int x, int z) {
      return this.storedLight.get(this.getChunkSectionIndex(x, z));
   }

   public void clear() {
      this.storedLight.clear();
   }

   public void unloadChunk(int x, int z) {
      this.storedLight.remove(this.getChunkSectionIndex(x, z));
   }

   private long getChunkSectionIndex(int x, int z) {
      return ((long)x & 67108863L) << 38 | (long)z & 67108863L;
   }

   private Map<Long, ChunkLightStorage.ChunkLight> createLongObjectMap() {
      if (fastUtilLongObjectHashMap != null) {
         try {
            return (Map<Long, ChunkLightStorage.ChunkLight>)fastUtilLongObjectHashMap.newInstance();
         } catch (InstantiationException | InvocationTargetException | IllegalAccessException var2) {
            var2.printStackTrace();
         }
      }

      return new HashMap<>();
   }

   static {
      Arrays.fill(FULL_LIGHT, (byte)-1);
      Arrays.fill(EMPTY_LIGHT, (byte)0);

      try {
         fastUtilLongObjectHashMap = Class.forName("com.viaversion.viaversion.libs.fastutil.longs.Long2ObjectOpenHashMap").getConstructor();
      } catch (NoSuchMethodException | ClassNotFoundException var1) {
      }
   }

   public static class ChunkLight {
      private final byte[][] skyLight;
      private final byte[][] blockLight;

      public ChunkLight(byte[][] skyLight, byte[][] blockLight) {
         this.skyLight = skyLight;
         this.blockLight = blockLight;
      }

      public byte[][] skyLight() {
         return this.skyLight;
      }

      public byte[][] blockLight() {
         return this.blockLight;
      }
   }
}
