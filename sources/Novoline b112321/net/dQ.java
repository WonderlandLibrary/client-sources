package net;

import net.a2V;

// $FF: synthetic class
class dQ {
   static final int[] a = new int[a2V.values().length];

   static {
      try {
         a[a2V.COMBAT.ordinal()] = 1;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         a[a2V.MOVEMENT.ordinal()] = 2;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         a[a2V.VISUALS.ordinal()] = 3;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         a[a2V.PLAYER.ordinal()] = 4;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
