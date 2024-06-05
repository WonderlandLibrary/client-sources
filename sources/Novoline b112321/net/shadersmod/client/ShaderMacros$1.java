package net.shadersmod.client;

import net.minecraft.util.Util$EnumOS;

// $FF: synthetic class
class ShaderMacros$1 {
   static final int[] $SwitchMap$net$minecraft$util$Util$EnumOS = new int[Util$EnumOS.values().length];

   static {
      try {
         $SwitchMap$net$minecraft$util$Util$EnumOS[Util$EnumOS.WINDOWS.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$util$Util$EnumOS[Util$EnumOS.OSX.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$util$Util$EnumOS[Util$EnumOS.LINUX.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
