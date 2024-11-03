package com.viaversion.viaversion.api.minecraft.blockentity;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface BlockEntity {
   static byte pack(int sectionX, int sectionZ) {
      return (byte)((sectionX & 15) << 4 | sectionZ & 15);
   }

   default byte sectionX() {
      return (byte)(this.packedXZ() >> 4 & 15);
   }

   default byte sectionZ() {
      return (byte)(this.packedXZ() & 15);
   }

   byte packedXZ();

   short y();

   int typeId();

   @Nullable
   CompoundTag tag();

   BlockEntity withTypeId(int var1);
}
