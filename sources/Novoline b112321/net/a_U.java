package net;

import java.util.Collection;
import net.shadersmod.client.ShaderProfile;

public class a_U {
   public static boolean b(ShaderProfile var0, String var1) {
      return var0.isProgramDisabled(var1);
   }

   public static String[] a(ShaderProfile var0) {
      return var0.getOptions();
   }

   public static String c(ShaderProfile var0, String var1) {
      return var0.getValue(var1);
   }

   public static void a(ShaderProfile var0, ShaderProfile var1) {
      var0.addOptionValues(var1);
   }

   public static Collection c(ShaderProfile var0) {
      return var0.getDisabledPrograms();
   }

   public static void a(ShaderProfile var0, Collection var1) {
      var0.addDisabledPrograms(var1);
   }

   public static String b(ShaderProfile var0) {
      return var0.getName();
   }

   public static void a(ShaderProfile var0, String var1) {
      var0.addDisabledProgram(var1);
   }

   public static void a(ShaderProfile var0, String var1, String var2) {
      var0.addOptionValue(var1, var2);
   }
}
