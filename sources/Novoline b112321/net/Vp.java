package net;

import net.minecraft.block.Block$SoundType;

final class Vp extends Block$SoundType {
   Vp(String var1, float var2, float var3) {
      super(var1, var2, var3);
   }

   public String getBreakSound() {
      return "dig.wood";
   }
}
