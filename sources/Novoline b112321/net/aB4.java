package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import java.util.Optional;
import viaversion.viaversion.api.minecraft.Position;
import viaversion.viaversion.protocols.protocol1_9to1_8.storage.CommandBlockStorage;

public class aB4 {
   public static void a(CommandBlockStorage var0, Position var1, CompoundTag var2) {
      var0.addOrUpdateBlock(var1, var2);
   }

   public static Optional a(CommandBlockStorage var0, Position var1) {
      return var0.getCommandBlock(var1);
   }

   public static void a(CommandBlockStorage var0, int var1, int var2) {
      var0.unloadChunk(var1, var2);
   }

   public static void a(CommandBlockStorage var0, boolean var1) {
      var0.setPermissions(var1);
   }

   public static boolean a(CommandBlockStorage var0) {
      return var0.isPermissions();
   }

   public static void b(CommandBlockStorage var0) {
      var0.unloadChunks();
   }
}
