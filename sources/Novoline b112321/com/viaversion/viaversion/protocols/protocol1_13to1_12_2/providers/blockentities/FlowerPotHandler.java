package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.util.Pair;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.aYj;
import net.aq_;
import net.bgR;

public class FlowerPotHandler implements aq_ {
   private static final Map flowers = new ConcurrentHashMap();

   public static void register(String var0, byte var1, byte var2, int var3) {
      flowers.put(new Pair(var0, Byte.valueOf(var2)), Integer.valueOf(var3));
      flowers.put(new Pair(Byte.valueOf(var1), Byte.valueOf(var2)), Integer.valueOf(var3));
   }

   public int a(bgR var1, CompoundTag var2) {
      boolean var3 = aYj.b();
      Byte var4 = (Byte)(var2.contains("Item")?var2.get("Item").getValue():null);
      Byte var5 = (Byte)(var2.contains("Data")?var2.get("Data").getValue():null);
      if(var4 instanceof String) {
         var4 = ((String)var4).replace("minecraft:", "");
      }

      if(var4 instanceof Number) {
         var4 = Byte.valueOf(((Number)var4).byteValue());
      }

      var4 = Byte.valueOf((byte)0);
      if(var5 instanceof Number) {
         var5 = Byte.valueOf(((Number)var5).byteValue());
      }

      var5 = Byte.valueOf((byte)0);
      Integer var6 = (Integer)flowers.get(new Pair(var4, Byte.valueOf(((Byte)var5).byteValue())));
      if(var6 != null) {
         return var6.intValue();
      } else {
         var6 = (Integer)flowers.get(new Pair(var4, Byte.valueOf((byte)0)));
         return var6 != null?var6.intValue():5265;
      }
   }

   static {
      register("air", (byte)0, (byte)0, 5265);
      register("sapling", (byte)6, (byte)0, 5266);
      register("sapling", (byte)6, (byte)1, 5267);
      register("sapling", (byte)6, (byte)2, 5268);
      register("sapling", (byte)6, (byte)3, 5269);
      register("sapling", (byte)6, (byte)4, 5270);
      register("sapling", (byte)6, (byte)5, 5271);
      register("tallgrass", (byte)31, (byte)2, 5272);
      register("yellow_flower", (byte)37, (byte)0, 5273);
      register("red_flower", (byte)38, (byte)0, 5274);
      register("red_flower", (byte)38, (byte)1, 5275);
      register("red_flower", (byte)38, (byte)2, 5276);
      register("red_flower", (byte)38, (byte)3, 5277);
      register("red_flower", (byte)38, (byte)4, 5278);
      register("red_flower", (byte)38, (byte)5, 5279);
      register("red_flower", (byte)38, (byte)6, 5280);
      register("red_flower", (byte)38, (byte)7, 5281);
      register("red_flower", (byte)38, (byte)8, 5282);
      register("red_mushroom", (byte)40, (byte)0, 5283);
      register("brown_mushroom", (byte)39, (byte)0, 5284);
      register("deadbush", (byte)32, (byte)0, 5285);
      register("cactus", (byte)81, (byte)0, 5286);
   }
}
