package net;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import net.optifine.Config;
import net.shadersmod.client.BlockAlias;
import net.shadersmod.client.IShaderPack;
import net.shadersmod.client.ShaderOption;

public class Bg {
   private static BlockAlias[][] a = (BlockAlias[][])null;

   public static int a(int var0, int var1) {
      String[] var2 = ShaderOption.p();
      if(a == null) {
         return var0;
      } else if(var0 >= 0 && var0 < a.length) {
         BlockAlias[] var3 = a[var0];
         if(var3 != null) {
            int var5 = var3.length;
            int var6 = 0;
            if(var6 < var5) {
               BlockAlias var7 = var3[var6];
               if(var7.matches(var0, var1)) {
                  return var7.getBlockId();
               }

               ++var6;
            }
         }

         return var0;
      } else {
         return var0;
      }
   }

   public static void a(IShaderPack var0) {
      ShaderOption.p();
      a();
      String var2 = "/shaders/block.properties";
      IShaderPack var10000 = var0;
      String var10001 = var2;

      try {
         InputStream var3 = var10000.getResourceAsStream(var10001);
      } catch (IOException var4) {
         Config.warn("[Shaders] Error reading: " + var2);
      }
   }

   private static void a(List var0, BlockAlias var1) {
      int[] var3 = var1.getMatchBlockIds();
      String[] var2 = ShaderOption.p();
      int var5 = var3.length;
      int var6 = 0;
      if(var6 < var5) {
         int var7 = var3[var6];
         if(var7 >= var0.size()) {
            var0.add((Object)null);
         }

         Object var8 = (List)var0.get(var7);
         if(var8 == null) {
            var8 = new ArrayList();
            var0.set(var7, var8);
         }

         ((List)var8).add(var1);
         ++var6;
      }

   }

   private static BlockAlias[][] a(List var0) {
      ShaderOption.p();
      BlockAlias[][] var2 = new BlockAlias[var0.size()][];
      int var3 = 0;
      if(var3 < var2.length) {
         List var4 = (List)var0.get(var3);
         var2[var3] = (BlockAlias[])var4.toArray(new BlockAlias[0]);
         ++var3;
      }

      return var2;
   }

   public static void a() {
      a = (BlockAlias[][])null;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
