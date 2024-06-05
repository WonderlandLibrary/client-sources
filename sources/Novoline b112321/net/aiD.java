package net;

import net.shadersmod.client.ShaderOption;
import net.shadersmod.client.ShaderProfile;
import net.shadersmod.client.ShaderUtils;

public class aiD {
   public static ShaderOption a(String var0, ShaderOption[] var1) {
      return ShaderUtils.getShaderOption(var0, var1);
   }

   public static ShaderProfile a(ShaderProfile[] var0, ShaderOption[] var1, boolean var2) {
      return ShaderUtils.detectProfile(var0, var1, var2);
   }

   public static boolean a(ShaderProfile var0, ShaderOption[] var1, boolean var2) {
      return ShaderUtils.matchProfile(var0, var1, var2);
   }
}
