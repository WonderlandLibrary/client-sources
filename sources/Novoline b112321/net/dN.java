package net;

import net.aR5;

// $FF: synthetic class
class dN {
   static final int[] a = new int[aR5.values().length];

   static {
      try {
         a[aR5.Byte.ordinal()] = 1;
      } catch (NoSuchFieldError var11) {
         ;
      }

      try {
         a[aR5.OptUUID.ordinal()] = 2;
      } catch (NoSuchFieldError var10) {
         ;
      }

      try {
         a[aR5.VarInt.ordinal()] = 3;
      } catch (NoSuchFieldError var9) {
         ;
      }

      try {
         a[aR5.Float.ordinal()] = 4;
      } catch (NoSuchFieldError var8) {
         ;
      }

      try {
         a[aR5.String.ordinal()] = 5;
      } catch (NoSuchFieldError var7) {
         ;
      }

      try {
         a[aR5.Boolean.ordinal()] = 6;
      } catch (NoSuchFieldError var6) {
         ;
      }

      try {
         a[aR5.Slot.ordinal()] = 7;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         a[aR5.Position.ordinal()] = 8;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         a[aR5.Vector3F.ordinal()] = 9;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         a[aR5.Chat.ordinal()] = 10;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         a[aR5.BlockID.ordinal()] = 11;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
