package com.viaversion.viarewind.api.minecraft;

public class IdDataCombine {
   public static int idFromCombined(int combined) {
      return combined >> 4;
   }

   public static int dataFromCombined(int combined) {
      return combined & 15;
   }

   public static int toCombined(int id, int data) {
      return id << 4 | data & 15;
   }
}
