package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.IntTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.bgR;
import net.p9;

public class FlowerPotHandler implements p9 {
   private static final Int2ObjectMap c = new Int2ObjectOpenHashMap(22, 1.0F);
   private static final Pair AIR = new Pair("minecraft:air", Byte.valueOf((byte)0));
   private static int[] b;

   private static void register(int var0, String var1, byte var2) {
      c.put(var0, new Pair(var1, Byte.valueOf(var2)));
   }

   public static boolean isFlowah(int var0) {
      int[] var1 = b();
      return var0 >= 5265 && var0 <= 5286;
   }

   public Pair b(int var1) {
      b();
      Pair var3 = (Pair)c.get(var1);
      return var3 != null?var3:AIR;
   }

   public CompoundTag a(bgR var1, int var2, CompoundTag var3) {
      Pair var4 = this.b(var2);
      var3.put(new StringTag("Item", (String)var4.getKey()));
      var3.put(new IntTag("Data", ((Byte)var4.getValue()).byteValue()));
      return var3;
   }

   static {
      b((int[])null);
      c.put(5265, AIR);
      register(5266, "minecraft:sapling", (byte)0);
      register(5267, "minecraft:sapling", (byte)1);
      register(5268, "minecraft:sapling", (byte)2);
      register(5269, "minecraft:sapling", (byte)3);
      register(5270, "minecraft:sapling", (byte)4);
      register(5271, "minecraft:sapling", (byte)5);
      register(5272, "minecraft:tallgrass", (byte)2);
      register(5273, "minecraft:yellow_flower", (byte)0);
      register(5274, "minecraft:red_flower", (byte)0);
      register(5275, "minecraft:red_flower", (byte)1);
      register(5276, "minecraft:red_flower", (byte)2);
      register(5277, "minecraft:red_flower", (byte)3);
      register(5278, "minecraft:red_flower", (byte)4);
      register(5279, "minecraft:red_flower", (byte)5);
      register(5280, "minecraft:red_flower", (byte)6);
      register(5281, "minecraft:red_flower", (byte)7);
      register(5282, "minecraft:red_flower", (byte)8);
      register(5283, "minecraft:red_mushroom", (byte)0);
      register(5284, "minecraft:brown_mushroom", (byte)0);
      register(5285, "minecraft:deadbush", (byte)0);
      register(5286, "minecraft:cactus", (byte)0);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }
}
