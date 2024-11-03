package com.viaversion.viaversion.data.entity;

import com.viaversion.viaversion.api.data.entity.DimensionData;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

public final class DimensionDataImpl implements DimensionData {
   private final int minY;
   private final int height;

   public DimensionDataImpl(int minY, int height) {
      this.minY = minY;
      this.height = height;
   }

   public DimensionDataImpl(CompoundTag dimensionData) {
      Tag height = dimensionData.get("height");
      if (height instanceof IntTag) {
         this.height = ((NumberTag)height).asInt();
         Tag minY = dimensionData.get("min_y");
         if (minY instanceof IntTag) {
            this.minY = ((NumberTag)minY).asInt();
         } else {
            throw new IllegalArgumentException("min_y missing in dimension data: " + dimensionData);
         }
      } else {
         throw new IllegalArgumentException("height missing in dimension data: " + dimensionData);
      }
   }

   @Override
   public int minY() {
      return this.minY;
   }

   @Override
   public int height() {
      return this.height;
   }

   @Override
   public String toString() {
      return "DimensionData{minY=" + this.minY + ", height=" + this.height + '}';
   }
}
