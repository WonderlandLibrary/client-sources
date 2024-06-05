package net.minecraft.client.renderer;

import net.minecraft.item.EnumAction;

// $FF: synthetic class
class ItemRenderer$1 {
   static final int[] $SwitchMap$net$minecraft$item$EnumAction = new int[EnumAction.values().length];

   static {
      try {
         $SwitchMap$net$minecraft$item$EnumAction[EnumAction.NONE.ordinal()] = 1;
      } catch (NoSuchFieldError var5) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$item$EnumAction[EnumAction.EAT.ordinal()] = 2;
      } catch (NoSuchFieldError var4) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$item$EnumAction[EnumAction.DRINK.ordinal()] = 3;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$item$EnumAction[EnumAction.BLOCK.ordinal()] = 4;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$item$EnumAction[EnumAction.BOW.ordinal()] = 5;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
