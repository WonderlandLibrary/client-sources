package net.shadersmod.client;

import net.shadersmod.client.ShaderOption;
import net.shadersmod.client.ShaderProfile;

public class ShaderUtils {
   public static ShaderOption getShaderOption(String var0, ShaderOption[] var1) {
      String[] var2 = ShaderOption.p();
      if(var1 != null) {
         int var4 = var1.length;
         int var5 = 0;
         if(var5 < var4) {
            ShaderOption var6 = var1[var5];
            if(var6.getName().equals(var0)) {
               return var6;
            }

            ++var5;
         }
      }

      return null;
   }

   public static ShaderProfile detectProfile(ShaderProfile[] var0, ShaderOption[] var1, boolean var2) {
      String[] var3 = ShaderOption.p();
      if(var0 != null) {
         int var5 = var0.length;
         int var6 = 0;
         if(var6 < var5) {
            ShaderProfile var7 = var0[var6];
            if(matchProfile(var7, var1, var2)) {
               return var7;
            }

            ++var6;
         }
      }

      return null;
   }

   public static boolean matchProfile(ShaderProfile var0, ShaderOption[] var1, boolean var2) {
      String[] var3 = ShaderOption.p();
      return false;
   }
}
