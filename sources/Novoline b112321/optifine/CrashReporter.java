package optifine;

import net.auJ;
import net.minecraft.crash.CrashReport;
import optifine.Config;
import optifine.MatchBlock;
import shadersmod.client.Shaders;

public class CrashReporter {
   public static void a(CrashReport param0, auJ param1) {
      // $FF: Couldn't be decompiled
   }

   private static String makeReport(CrashReport var0) {
      StringBuffer var1 = new StringBuffer();
      var1.append("OptiFineVersion: " + Config.getVersion() + "\n");
      var1.append("Summary: " + makeSummary(var0) + "\n");
      var1.append("\n");
      var1.append(var0.getCompleteReport());
      var1.append("\n");
      return var1.toString();
   }

   private static String makeSummary(CrashReport var0) {
      MatchBlock.b();
      Throwable var2 = var0.getCrashCause();
      if(var2 == null) {
         return "Unknown";
      } else {
         StackTraceElement[] var3 = var2.getStackTrace();
         String var4 = "unknown";
         if(var3.length > 0) {
            var4 = var3[0].toString().trim();
         }

         String var5 = var2.getClass().getName() + ": " + var2.getMessage() + " (" + var0.getDescription() + ") [" + var4 + "]";
         return var5;
      }
   }

   public static void a(auJ var0) {
      MatchBlock.b();
      var0.a((String)"OptiFine Version", (Object)Config.getVersion());
      if(Config.getGameSettings() != null) {
         var0.a((String)"Render Distance Chunks", (Object)("" + Config.getChunkViewDistance()));
         var0.a((String)"Mipmaps", (Object)("" + Config.getMipmapLevels()));
         var0.a((String)"Anisotropic Filtering", (Object)("" + Config.getAnisotropicFilterLevel()));
         var0.a((String)"Antialiasing", (Object)("" + Config.getAntialiasingLevel()));
         var0.a((String)"Multitexture", (Object)("" + Config.isMultiTexture()));
      }

      var0.a((String)"Shaders", (Object)("" + Shaders.getShaderPackName()));
      var0.a((String)"OpenGlVersion", (Object)("" + Config.openGlVersion));
      var0.a((String)"OpenGlRenderer", (Object)("" + Config.openGlRenderer));
      var0.a((String)"OpenGlVendor", (Object)("" + Config.openGlVendor));
      var0.a((String)"CpuCount", (Object)("" + Config.getAvailableProcessors()));
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
