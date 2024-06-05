package net;

import net.abi;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.minecraft.Position;
import viaversion.viaversion.api.minecraft.chunks.Chunk;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;

public class _o {
   public static void a(UserConnection var0, int var1, int var2, int var3, int var4) {
      ConnectionData.updateBlockStorage(var0, var1, var2, var3, var4);
   }

   public static abi b(int var0) {
      return ConnectionData.d(var0);
   }

   public static void a(UserConnection var0, Position var1) {
      ConnectionData.update(var0, var1);
   }

   public static void a() {
      ConnectionData.init();
   }

   public static boolean b() {
      return ConnectionData.needStoreBlocks();
   }

   public static boolean c(int var0) {
      return ConnectionData.isWelcome(var0);
   }

   public static void a(UserConnection var0, Chunk var1) {
      ConnectionData.connectBlocks(var0, var1);
   }

   public static void a(UserConnection var0, int var1, int var2, int var3) {
      ConnectionData.updateChunkSectionNeighbours(var0, var1, var2, var3);
   }

   public static int a(String var0) {
      return ConnectionData.getId(var0);
   }

   public static int a(UserConnection var0, Position var1, int var2) {
      return ConnectionData.connect(var0, var1, var2);
   }

   public static void a(UserConnection var0) {
      ConnectionData.clearBlockStorage(var0);
   }

   public static String a(int var0) {
      return ConnectionData.getKey(var0);
   }
}
