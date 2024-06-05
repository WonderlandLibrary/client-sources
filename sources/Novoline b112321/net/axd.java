package net;

import java.util.Set;
import net.minecraft.client.renderer.chunk.SetVisibility;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.util.BlockPos;

public class axd {
   public static void b(VisGraph var0, BlockPos var1) {
      var0.func_178606_a(var1);
   }

   public static SetVisibility a(VisGraph var0) {
      return var0.computeVisibility();
   }

   public static Set a(VisGraph var0, BlockPos var1) {
      return var0.func_178609_b(var1);
   }
}
