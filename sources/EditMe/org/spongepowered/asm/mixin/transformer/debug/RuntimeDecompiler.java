package org.spongepowered.asm.mixin.transformer.debug;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.jar.Manifest;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.java.decompiler.main.Fernflower;
import org.jetbrains.java.decompiler.main.extern.IBytecodeProvider;
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;
import org.jetbrains.java.decompiler.main.extern.IResultSaver;
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger.Severity;
import org.jetbrains.java.decompiler.util.InterpreterUtil;
import org.spongepowered.asm.mixin.transformer.ext.IDecompiler;

public class RuntimeDecompiler extends IFernflowerLogger implements IDecompiler, IResultSaver {
   private static final Level[] SEVERITY_LEVELS;
   private final Map options = ImmutableMap.builder().put("din", "0").put("rbr", "0").put("dgs", "1").put("asc", "1").put("den", "1").put("hdc", "1").put("ind", "    ").build();
   private final File outputPath;
   protected final Logger logger = LogManager.getLogger("fernflower");

   public RuntimeDecompiler(File var1) {
      this.outputPath = var1;
      if (this.outputPath.exists()) {
         try {
            FileUtils.deleteDirectory(this.outputPath);
         } catch (IOException var3) {
            this.logger.warn("Error cleaning output directory: {}", new Object[]{var3.getMessage()});
         }
      }

   }

   public void decompile(File var1) {
      try {
         Fernflower var2 = new Fernflower(new IBytecodeProvider(this) {
            private byte[] byteCode;
            final RuntimeDecompiler this$0;

            {
               this.this$0 = var1;
            }

            public byte[] getBytecode(String var1, String var2) throws IOException {
               if (this.byteCode == null) {
                  this.byteCode = InterpreterUtil.getBytes(new File(var1));
               }

               return this.byteCode;
            }
         }, this, this.options, this);
         var2.getStructContext().addSpace(var1, true);
         var2.decompileContext();
      } catch (Throwable var3) {
         this.logger.warn("Decompilation error while processing {}", new Object[]{var1.getName()});
      }

   }

   public void saveFolder(String var1) {
   }

   public void saveClassFile(String var1, String var2, String var3, String var4, int[] var5) {
      File var6 = new File(this.outputPath, var2 + ".java");
      var6.getParentFile().mkdirs();

      try {
         this.logger.info("Writing {}", new Object[]{var6.getAbsolutePath()});
         Files.write(var4, var6, Charsets.UTF_8);
      } catch (IOException var8) {
         this.writeMessage("Cannot write source file " + var6, (Throwable)var8);
      }

   }

   public void startReadingClass(String var1) {
      this.logger.info("Decompiling {}", new Object[]{var1});
   }

   public void writeMessage(String var1, Severity var2) {
      this.logger.log(SEVERITY_LEVELS[var2.ordinal()], var1);
   }

   public void writeMessage(String var1, Throwable var2) {
      this.logger.warn("{} {}: {}", new Object[]{var1, var2.getClass().getSimpleName(), var2.getMessage()});
   }

   public void writeMessage(String var1, Severity var2, Throwable var3) {
      this.logger.log(SEVERITY_LEVELS[var2.ordinal()], var1, var3);
   }

   public void copyFile(String var1, String var2, String var3) {
   }

   public void createArchive(String var1, String var2, Manifest var3) {
   }

   public void saveDirEntry(String var1, String var2, String var3) {
   }

   public void copyEntry(String var1, String var2, String var3, String var4) {
   }

   public void saveClassEntry(String var1, String var2, String var3, String var4, String var5) {
   }

   public void closeArchive(String var1, String var2) {
   }

   static {
      SEVERITY_LEVELS = new Level[]{Level.TRACE, Level.INFO, Level.WARN, Level.ERROR};
   }
}
