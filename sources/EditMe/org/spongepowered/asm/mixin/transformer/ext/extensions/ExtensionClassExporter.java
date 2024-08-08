package org.spongepowered.asm.mixin.transformer.ext.extensions;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.ext.IDecompiler;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;
import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
import org.spongepowered.asm.util.Constants;
import org.spongepowered.asm.util.perf.Profiler;

public class ExtensionClassExporter implements IExtension {
   private static final String DECOMPILER_CLASS = "org.spongepowered.asm.mixin.transformer.debug.RuntimeDecompiler";
   private static final String EXPORT_CLASS_DIR = "class";
   private static final String EXPORT_JAVA_DIR = "java";
   private static final Logger logger = LogManager.getLogger("mixin");
   private final File classExportDir;
   private final IDecompiler decompiler;

   public ExtensionClassExporter(MixinEnvironment var1) {
      this.classExportDir = new File(Constants.DEBUG_OUTPUT_DIR, "class");
      this.decompiler = this.initDecompiler(var1, new File(Constants.DEBUG_OUTPUT_DIR, "java"));

      try {
         FileUtils.deleteDirectory(this.classExportDir);
      } catch (IOException var3) {
         logger.warn("Error cleaning class output directory: {}", new Object[]{var3.getMessage()});
      }

   }

   private IDecompiler initDecompiler(MixinEnvironment var1, File var2) {
      if (!var1.getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE)) {
         return null;
      } else {
         try {
            boolean var3 = var1.getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE_THREADED);
            logger.info("Attempting to load Fernflower decompiler{}", new Object[]{var3 ? " (Threaded mode)" : ""});
            String var4 = "org.spongepowered.asm.mixin.transformer.debug.RuntimeDecompiler" + (var3 ? "Async" : "");
            Class var5 = Class.forName(var4);
            Constructor var6 = var5.getDeclaredConstructor(File.class);
            IDecompiler var7 = (IDecompiler)var6.newInstance(var2);
            logger.info("Fernflower decompiler was successfully initialised, exported classes will be decompiled{}", new Object[]{var3 ? " in a separate thread" : ""});
            return var7;
         } catch (Throwable var8) {
            logger.info("Fernflower could not be loaded, exported classes will not be decompiled. {}: {}", new Object[]{var8.getClass().getSimpleName(), var8.getMessage()});
            return null;
         }
      }
   }

   private String prepareFilter(String var1) {
      var1 = "^\\Q" + var1.replace("**", "\u0081").replace("*", "\u0082").replace("?", "\u0083") + "\\E$";
      return var1.replace("\u0081", "\\E.*\\Q").replace("\u0082", "\\E[^\\.]+\\Q").replace("\u0083", "\\E.\\Q").replace("\\Q\\E", "");
   }

   private boolean applyFilter(String var1, String var2) {
      return Pattern.compile(this.prepareFilter(var1), 2).matcher(var2).matches();
   }

   public boolean checkActive(MixinEnvironment var1) {
      return true;
   }

   public void preApply(ITargetClassContext var1) {
   }

   public void postApply(ITargetClassContext var1) {
   }

   public void export(MixinEnvironment var1, String var2, boolean var3, byte[] var4) {
      if (var3 || var1.getOption(MixinEnvironment.Option.DEBUG_EXPORT)) {
         String var5 = var1.getOptionValue(MixinEnvironment.Option.DEBUG_EXPORT_FILTER);
         if (var3 || var5 == null || this.applyFilter(var5, var2)) {
            Profiler.Section var6 = MixinEnvironment.getProfiler().begin("debug.export");
            File var7 = this.dumpClass(var2.replace('.', '/'), var4);
            if (this.decompiler != null) {
               this.decompiler.decompile(var7);
            }

            var6.end();
         }
      }

   }

   public File dumpClass(String var1, byte[] var2) {
      File var3 = new File(this.classExportDir, var1 + ".class");

      try {
         FileUtils.writeByteArrayToFile(var3, var2);
      } catch (IOException var5) {
      }

      return var3;
   }
}
