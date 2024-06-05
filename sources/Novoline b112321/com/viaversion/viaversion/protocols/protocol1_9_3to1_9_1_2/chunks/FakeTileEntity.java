package com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.chunks;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.IntTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FakeTileEntity {
   private static final Map c = new HashMap();
   private static PacketRemapper[] b;

   private static void a(Integer var0, String var1) {
      CompoundTag var2 = new CompoundTag("");
      var2.put(new StringTag(var1));
      c.put(var0, var2);
   }

   private static void register(List var0, String var1) {
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         int var3 = ((Integer)var2.next()).intValue();
         a(Integer.valueOf(var3), var1);
      }

   }

   public static boolean a(int var0) {
      return c.containsKey(Integer.valueOf(var0));
   }

   public static CompoundTag a(int var0, int var1, int var2, int var3) {
      b();
      CompoundTag var5 = (CompoundTag)c.get(Integer.valueOf(var3));
      if(var5 != null) {
         CompoundTag var6 = var5.clone();
         var6.put(new IntTag("x", var0));
         var6.put(new IntTag("y", var1));
         var6.put(new IntTag("z", var2));
         return var6;
      } else {
         return null;
      }
   }

   static {
      b((PacketRemapper[])null);
      register(Arrays.asList(new Integer[]{Integer.valueOf(61), Integer.valueOf(62)}), "Furnace");
      register(Arrays.asList(new Integer[]{Integer.valueOf(54), Integer.valueOf(146)}), "Chest");
      a(Integer.valueOf(130), "EnderChest");
      a(Integer.valueOf(84), "RecordPlayer");
      a(Integer.valueOf(23), "Trap");
      a(Integer.valueOf(158), "Dropper");
      register(Arrays.asList(new Integer[]{Integer.valueOf(63), Integer.valueOf(68)}), "Sign");
      a(Integer.valueOf(52), "MobSpawner");
      a(Integer.valueOf(25), "Music");
      register(Arrays.asList(new Integer[]{Integer.valueOf(33), Integer.valueOf(34), Integer.valueOf(29), Integer.valueOf(36)}), "Piston");
      a(Integer.valueOf(117), "Cauldron");
      a(Integer.valueOf(116), "EnchantTable");
      register(Arrays.asList(new Integer[]{Integer.valueOf(119), Integer.valueOf(120)}), "Airportal");
      a(Integer.valueOf(138), "Beacon");
      a(Integer.valueOf(144), "Skull");
      register(Arrays.asList(new Integer[]{Integer.valueOf(178), Integer.valueOf(151)}), "DLDetector");
      a(Integer.valueOf(154), "Hopper");
      register(Arrays.asList(new Integer[]{Integer.valueOf(149), Integer.valueOf(150)}), "Comparator");
      a(Integer.valueOf(140), "FlowerPot");
      register(Arrays.asList(new Integer[]{Integer.valueOf(176), Integer.valueOf(177)}), "Banner");
      a(Integer.valueOf(209), "EndGateway");
      a(Integer.valueOf(137), "Control");
   }

   public static void b(PacketRemapper[] var0) {
      b = var0;
   }

   public static PacketRemapper[] b() {
      return b;
   }
}
