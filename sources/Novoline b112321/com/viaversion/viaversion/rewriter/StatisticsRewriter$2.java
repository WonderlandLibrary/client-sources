package com.viaversion.viaversion.rewriter;

import net.Ch;

// $FF: synthetic class
class StatisticsRewriter$2 {
   static final int[] $SwitchMap$com$viaversion$viaversion$api$minecraft$RegistryType = new int[Ch.values().length];

   static {
      try {
         $SwitchMap$com$viaversion$viaversion$api$minecraft$RegistryType[Ch.BLOCK.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         $SwitchMap$com$viaversion$viaversion$api$minecraft$RegistryType[Ch.ITEM.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         $SwitchMap$com$viaversion$viaversion$api$minecraft$RegistryType[Ch.ENTITY.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
