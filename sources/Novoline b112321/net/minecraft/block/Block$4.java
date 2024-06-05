package net.minecraft.block;

import net.minecraft.block.Block$SoundType;

final class Block$4 extends Block$SoundType {
   Block$4(String var1, float var2, float var3) {
      super(var1, var2, var3);
   }

   public String getBreakSound() {
      return "mob.slime.big";
   }

   public String getPlaceSound() {
      return "mob.slime.big";
   }

   public String getStepSound() {
      return "mob.slime.small";
   }
}
