package optifine;

import java.util.HashMap;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import shadersmod.client.Shaders;

public class CrashReporter {
   private static String makeSummary(CrashReport var0) {
      Throwable var1 = var0.getCrashCause();
      if (var1 == null) {
         return "Unknown";
      } else {
         StackTraceElement[] var2 = var1.getStackTrace();
         String var3 = "unknown";
         if (var2.length > 0) {
            var3 = var2[0].toString().trim();
         }

         String var4 = String.valueOf((new StringBuilder(String.valueOf(var1.getClass().getName()))).append(": ").append(var1.getMessage()).append(" (").append(var0.getDescription()).append(")").append(" [").append(var3).append("]"));
         return var4;
      }
   }

   public static void extendCrashReport(CrashReportCategory var0) {
      var0.addCrashSection("OptiFine Version", Config.getVersion());
      if (Config.getGameSettings() != null) {
         var0.addCrashSection("Render Distance Chunks", String.valueOf((new StringBuilder()).append(Config.getChunkViewDistance())));
         var0.addCrashSection("Mipmaps", String.valueOf((new StringBuilder()).append(Config.getMipmapLevels())));
         var0.addCrashSection("Anisotropic Filtering", String.valueOf((new StringBuilder()).append(Config.getAnisotropicFilterLevel())));
         var0.addCrashSection("Antialiasing", String.valueOf((new StringBuilder()).append(Config.getAntialiasingLevel())));
         var0.addCrashSection("Multitexture", String.valueOf((new StringBuilder()).append(Config.isMultiTexture())));
      }

      var0.addCrashSection("Shaders", String.valueOf((new StringBuilder()).append(Shaders.getShaderPackName())));
      var0.addCrashSection("OpenGlVersion", String.valueOf((new StringBuilder()).append(Config.openGlVersion)));
      var0.addCrashSection("OpenGlRenderer", String.valueOf((new StringBuilder()).append(Config.openGlRenderer)));
      var0.addCrashSection("OpenGlVendor", String.valueOf((new StringBuilder()).append(Config.openGlVendor)));
      var0.addCrashSection("CpuCount", String.valueOf((new StringBuilder()).append(Config.getAvailableProcessors())));
   }

   private static String makeReport(CrashReport var0) {
      StringBuffer var1 = new StringBuffer();
      var1.append(String.valueOf((new StringBuilder("OptiFineVersion: ")).append(Config.getVersion()).append("\n")));
      var1.append(String.valueOf((new StringBuilder("Summary: ")).append(makeSummary(var0)).append("\n")));
      var1.append("\n");
      var1.append(var0.getCompleteReport());
      var1.append("\n");
      return var1.toString();
   }

   public static void onCrashReport(CrashReport var0, CrashReportCategory var1) {
      try {
         GameSettings var2 = Config.getGameSettings();
         if (var2 == null) {
            return;
         }

         if (!var2.snooperEnabled) {
            return;
         }

         Throwable var3 = var0.getCrashCause();
         if (var3 == null) {
            return;
         }

         if (var3.getClass() == Throwable.class) {
            return;
         }

         if (var3.getClass().getName().contains(".fml.client.SplashProgress")) {
            return;
         }

         extendCrashReport(var1);
         String var4 = "http://optifine.net/crashReport";
         String var5 = makeReport(var0);
         byte[] var6 = var5.getBytes("ASCII");
         IFileUploadListener var7 = new IFileUploadListener() {
            public void fileUploadFinished(String var1, byte[] var2, Throwable var3) {
            }
         };
         HashMap var8 = new HashMap();
         var8.put("OF-Version", Config.getVersion());
         var8.put("OF-Summary", makeSummary(var0));
         FileUploadThread var9 = new FileUploadThread(var4, var8, var6, var7);
         var9.setPriority(10);
         var9.start();
         Thread.sleep(1000L);
      } catch (Exception var10) {
         Config.dbg(String.valueOf((new StringBuilder(String.valueOf(var10.getClass().getName()))).append(": ").append(var10.getMessage())));
      }

   }
}
