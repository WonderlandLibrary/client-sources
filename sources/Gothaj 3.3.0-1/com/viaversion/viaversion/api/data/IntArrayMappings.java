package com.viaversion.viaversion.api.data;

import java.util.Arrays;

public class IntArrayMappings implements Mappings {
   private final int[] mappings;
   private final int mappedIds;

   protected IntArrayMappings(int[] mappings, int mappedIds) {
      this.mappings = mappings;
      this.mappedIds = mappedIds;
   }

   public static IntArrayMappings of(int[] mappings, int mappedIds) {
      return new IntArrayMappings(mappings, mappedIds);
   }

   @Deprecated
   public static Mappings.Builder<IntArrayMappings> builder() {
      return Mappings.builder(IntArrayMappings::new);
   }

   @Override
   public int getNewId(int id) {
      return id >= 0 && id < this.mappings.length ? this.mappings[id] : -1;
   }

   @Override
   public void setNewId(int id, int mappedId) {
      this.mappings[id] = mappedId;
   }

   @Override
   public int size() {
      return this.mappings.length;
   }

   @Override
   public int mappedSize() {
      return this.mappedIds;
   }

   @Override
   public Mappings inverse() {
      int[] inverse = new int[this.mappedIds];
      Arrays.fill(inverse, -1);

      for (int id = 0; id < this.mappings.length; id++) {
         int mappedId = this.mappings[id];
         if (mappedId != -1 && inverse[mappedId] == -1) {
            inverse[mappedId] = id;
         }
      }

      return of(inverse, this.mappings.length);
   }

   public int[] raw() {
      return this.mappings;
   }
}
