package net.shadersmod.client;

import java.util.ArrayList;
import net.optifine.Lang;
import net.shadersmod.client.ShaderOption;
import net.shadersmod.client.ShaderProfile;
import net.shadersmod.client.ShaderUtils;
import net.shadersmod.client.Shaders;

public class ShaderOptionProfile extends ShaderOption {
   private ShaderProfile[] profiles = null;
   private ShaderOption[] options = null;
   private static final String m = "<profile>";
   private static final String p = "<custom>";

   public ShaderOptionProfile(ShaderProfile[] var1, ShaderOption[] var2) {
      super("<profile>", "", detectProfileName(var1, var2), getProfileNames(var1), detectProfileName(var1, var2, true), (String)null);
      this.profiles = var1;
      this.options = var2;
   }

   public void nextValue() {
      ShaderOption.p();
      super.nextValue();
      if(this.getValue().equals("<custom>")) {
         super.nextValue();
      }

      this.applyProfileOptions();
   }

   public void updateProfile() {
      ShaderProfile var1 = this.getProfile(this.getValue());
      if(!ShaderUtils.matchProfile(var1, this.options, false)) {
         String var2 = detectProfileName(this.profiles, this.options);
         this.setValue(var2);
      }

   }

   private void applyProfileOptions() {
      ShaderOption.p();
      ShaderProfile var2 = this.getProfile(this.getValue());
      if(var2 != null) {
         String[] var3 = var2.getOptions();
         int var5 = var3.length;
         int var6 = 0;
         if(var6 < var5) {
            String var7 = var3[var6];
            ShaderOption var8 = this.getOption(var7);
            String var9 = var2.getValue(var7);
            var8.setValue(var9);
            ++var6;
         }
      }

   }

   private ShaderOption getOption(String var1) {
      ShaderOption.p();
      ShaderOption[] var3 = this.options;
      int var4 = var3.length;
      int var5 = 0;
      if(var5 < var4) {
         ShaderOption var6 = var3[var5];
         if(var6.getName().equals(var1)) {
            return var6;
         }

         ++var5;
      }

      return null;
   }

   private ShaderProfile getProfile(String var1) {
      ShaderProfile[] var3 = this.profiles;
      ShaderOption.p();
      int var4 = var3.length;
      int var5 = 0;
      if(var5 < var4) {
         ShaderProfile var6 = var3[var5];
         if(var6.getName().equals(var1)) {
            return var6;
         }

         ++var5;
      }

      return null;
   }

   public String getNameText() {
      return Lang.get("of.shaders.profile");
   }

   public String getValueText(String var1) {
      String[] var2 = ShaderOption.p();
      return var1.equals("<custom>")?Lang.get("of.general.custom", "<custom>"):Shaders.translate("profile." + var1, var1);
   }

   public String getValueColor(String var1) {
      String[] var2 = ShaderOption.p();
      return var1.equals("<custom>")?"§c":"§a";
   }

   private static String detectProfileName(ShaderProfile[] var0, ShaderOption[] var1) {
      return detectProfileName(var0, var1, false);
   }

   private static String detectProfileName(ShaderProfile[] var0, ShaderOption[] var1, boolean var2) {
      ShaderOption.p();
      ShaderProfile var4 = ShaderUtils.detectProfile(var0, var1, var2);
      return var4 == null?"<custom>":var4.getName();
   }

   private static String[] getProfileNames(ShaderProfile[] var0) {
      ArrayList var2 = new ArrayList();
      int var4 = var0.length;
      ShaderOption.p();
      int var5 = 0;
      if(var5 < var4) {
         ShaderProfile var6 = var0[var5];
         var2.add(var6.getName());
         ++var5;
      }

      var2.add("<custom>");
      String[] var3 = (String[])((String[])((String[])var2.toArray(new String[var2.size()])));
      return var3;
   }
}
