package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.layer.GenLayerEdge$Mode;

// $FF: synthetic class
class GenLayerEdge$1 {
   static final int[] $SwitchMap$net$minecraft$world$gen$layer$GenLayerEdge$Mode = new int[GenLayerEdge$Mode.values().length];

   static {
      try {
         $SwitchMap$net$minecraft$world$gen$layer$GenLayerEdge$Mode[GenLayerEdge$Mode.COOL_WARM.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$world$gen$layer$GenLayerEdge$Mode[GenLayerEdge$Mode.HEAT_ICE.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$world$gen$layer$GenLayerEdge$Mode[GenLayerEdge$Mode.SPECIAL.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
