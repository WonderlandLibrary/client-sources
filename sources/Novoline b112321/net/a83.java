package net;

import net.minecraft.world.gen.ChunkProviderSettings;
import net.minecraft.world.gen.ChunkProviderSettings$Factory;

public class a83 {
   public static ChunkProviderSettings$Factory a(String var0) {
      return ChunkProviderSettings$Factory.jsonToFactory(var0);
   }

   public static ChunkProviderSettings a(ChunkProviderSettings$Factory var0) {
      return var0.func_177864_b();
   }

   public static void b(ChunkProviderSettings$Factory var0) {
      var0.func_177863_a();
   }
}
