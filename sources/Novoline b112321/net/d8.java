package net;

import com.google.common.util.concurrent.ListenableFuture;
import java.io.File;
import java.util.List;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;

public class d8 {
   public static List e(ResourcePackRepository var0) {
      return var0.getRepositoryEntries();
   }

   public static IResourcePack a(ResourcePackRepository var0) {
      return var0.getResourcePackInstance();
   }

   public static void a(ResourcePackRepository var0, List var1) {
      var0.setRepositories(var1);
   }

   public static void c(ResourcePackRepository var0) {
      var0.func_148529_f();
   }

   public static void f(ResourcePackRepository var0) {
      var0.updateRepositoryEntriesAll();
   }

   public static List d(ResourcePackRepository var0) {
      return var0.getRepositoryEntriesAll();
   }

   public static File b(ResourcePackRepository var0) {
      return var0.getDirResourcepacks();
   }

   public static ListenableFuture a(ResourcePackRepository var0, File var1) {
      return var0.setResourcePackInstance(var1);
   }

   public static ListenableFuture a(ResourcePackRepository var0, String var1, String var2) {
      return var0.downloadResourcePack(var1, var2);
   }
}
