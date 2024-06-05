package net;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.cA;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.minecraft.Position;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.BlockStorage$ReplacementData;

public class cX extends cA {
   private static final IntSet d = new IntOpenHashSet(46, 1.0F);
   private final Map e = new ConcurrentHashMap();
   private static String[] c;

   public cX(UserConnection var1) {
      super(var1);
   }

   public void a(Position var1, int var2) {
      this.a(var1, var2, -1);
   }

   public void a(Position var1, int var2, int var3) {
      String[] var4 = a();
      if(d.contains(var2)) {
         this.e.put(var1, new BlockStorage$ReplacementData(var2, var3));
      }
   }

   public boolean a(int var1) {
      return d.contains(var1);
   }

   public boolean c(Position var1) {
      return this.e.containsKey(var1);
   }

   public BlockStorage$ReplacementData b(Position var1) {
      return (BlockStorage$ReplacementData)this.e.get(var1);
   }

   public BlockStorage$ReplacementData a(Position var1) {
      return (BlockStorage$ReplacementData)this.e.remove(var1);
   }

   static {
      String[] var10000 = new String[4];
      b(var10000);
      d.add(5266);

      for(int var0 = 0; var0 < 16; ++var0) {
         d.add(972 + var0);
      }

      for(int var1 = 0; var1 < 20; ++var1) {
         d.add(6854 + var1);
      }

      for(int var2 = 0; var2 < 4; ++var2) {
         d.add(7110 + var2);
      }

      for(int var3 = 0; var3 < 5; ++var3) {
         d.add(5447 + var3);
      }

   }

   public static void b(String[] var0) {
      c = var0;
   }

   public static String[] a() {
      return c;
   }
}
