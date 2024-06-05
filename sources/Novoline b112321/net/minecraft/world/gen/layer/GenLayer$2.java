package net.minecraft.world.gen.layer;

import java.util.concurrent.Callable;
import net.minecraft.world.biome.BiomeGenBase;

final class GenLayer$2 implements Callable {
   final BiomeGenBase val$biomegenbase1;

   GenLayer$2(BiomeGenBase var1) {
      this.val$biomegenbase1 = var1;
   }

   public String call() throws Exception {
      return String.valueOf(this.val$biomegenbase1);
   }
}
