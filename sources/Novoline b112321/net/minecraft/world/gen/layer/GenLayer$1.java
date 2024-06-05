package net.minecraft.world.gen.layer;

import java.util.concurrent.Callable;
import net.minecraft.world.biome.BiomeGenBase;

final class GenLayer$1 implements Callable {
   final BiomeGenBase val$biomegenbase;

   GenLayer$1(BiomeGenBase var1) {
      this.val$biomegenbase = var1;
   }

   public String call() throws Exception {
      return String.valueOf(this.val$biomegenbase);
   }
}
