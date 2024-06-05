package com.viaversion.viaversion.api.minecraft.chunks;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import java.util.List;
import net.aiV;

public interface Chunk {
   int h();

   int i();

   boolean isBiomeData();

   boolean isFullChunk();

   /** @deprecated */
   @Deprecated
   default boolean b() {
      return this.isFullChunk();
   }

   boolean isIgnoreOldLightData();

   void setIgnoreOldLightData(boolean var1);

   int k();

   aiV[] f();

   int[] getBiomeData();

   void setBiomeData(int[] var1);

   CompoundTag c();

   void a(CompoundTag var1);

   List getBlockEntities();
}
