package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import net.cK;

public class ChunkLightStorage$ChunkLight {
   private final byte[][] skyLight;
   private final byte[][] blockLight;

   public ChunkLightStorage$ChunkLight(byte[][] var1, byte[][] var2) {
      cK.b();
      super();
      this.skyLight = var1;
      this.blockLight = var2;
      if(PacketRemapper.b() == null) {
         cK.b(new PacketRemapper[4]);
      }

   }

   public byte[][] getSkyLight() {
      return this.skyLight;
   }

   public byte[][] getBlockLight() {
      return this.blockLight;
   }
}
