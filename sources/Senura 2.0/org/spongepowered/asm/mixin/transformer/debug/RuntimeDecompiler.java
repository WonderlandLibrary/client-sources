/*     */ package org.spongepowered.asm.mixin.transformer.debug;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.io.Files;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.jar.Manifest;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.jetbrains.java.decompiler.main.Fernflower;
/*     */ import org.jetbrains.java.decompiler.main.extern.IBytecodeProvider;
/*     */ import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;
/*     */ import org.jetbrains.java.decompiler.main.extern.IResultSaver;
/*     */ import org.jetbrains.java.decompiler.util.InterpreterUtil;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.IDecompiler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RuntimeDecompiler
/*     */   extends IFernflowerLogger
/*     */   implements IDecompiler, IResultSaver
/*     */ {
/*  52 */   private static final Level[] SEVERITY_LEVELS = new Level[] { Level.TRACE, Level.INFO, Level.WARN, Level.ERROR };
/*     */   
/*  54 */   private final Map<String, Object> options = (Map<String, Object>)ImmutableMap.builder()
/*  55 */     .put("din", "0").put("rbr", "0").put("dgs", "1").put("asc", "1")
/*  56 */     .put("den", "1").put("hdc", "1").put("ind", "    ").build();
/*     */   
/*     */   private final File outputPath;
/*     */   
/*  60 */   protected final Logger logger = LogManager.getLogger("fernflower");
/*     */   
/*     */   public RuntimeDecompiler(File outputPath) {
/*  63 */     this.outputPath = outputPath;
/*  64 */     if (this.outputPath.exists()) {
/*     */       try {
/*  66 */         FileUtils.deleteDirectory(this.outputPath);
/*  67 */       } catch (IOException ex) {
/*  68 */         this.logger.warn("Error cleaning output directory: {}", new Object[] { ex.getMessage() });
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void decompile(File file) {
/*     */     try {
/*  76 */       Fernflower fernflower = new Fernflower(new IBytecodeProvider()
/*     */           {
/*     */             private byte[] byteCode;
/*     */ 
/*     */             
/*     */             public byte[] getBytecode(String externalPath, String internalPath) throws IOException {
/*  82 */               if (this.byteCode == null) {
/*  83 */                 this.byteCode = InterpreterUtil.getBytes(new File(externalPath));
/*     */               }
/*  85 */               return this.byteCode;
/*     */             }
/*     */           },  this, this.options, this);
/*     */ 
/*     */       
/*  90 */       fernflower.getStructContext().addSpace(file, true);
/*  91 */       fernflower.decompileContext();
/*  92 */     } catch (Throwable ex) {
/*  93 */       this.logger.warn("Decompilation error while processing {}", new Object[] { file.getName() });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveFolder(String path) {}
/*     */ 
/*     */   
/*     */   public void saveClassFile(String path, String qualifiedName, String entryName, String content, int[] mapping) {
/* 103 */     File file = new File(this.outputPath, qualifiedName + ".java");
/* 104 */     file.getParentFile().mkdirs();
/*     */     try {
/* 106 */       this.logger.info("Writing {}", new Object[] { file.getAbsolutePath() });
/* 107 */       Files.write(content, file, Charsets.UTF_8);
/* 108 */     } catch (IOException ex) {
/* 109 */       writeMessage("Cannot write source file " + file, ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void startReadingClass(String className) {
/* 115 */     this.logger.info("Decompiling {}", new Object[] { className });
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeMessage(String message, IFernflowerLogger.Severity severity) {
/* 120 */     this.logger.log(SEVERITY_LEVELS[severity.ordinal()], message);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeMessage(String message, Throwable t) {
/* 125 */     this.logger.warn("{} {}: {}", new Object[] { message, t.getClass().getSimpleName(), t.getMessage() });
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeMessage(String message, IFernflowerLogger.Severity severity, Throwable t) {
/* 130 */     this.logger.log(SEVERITY_LEVELS[severity.ordinal()], message, t);
/*     */   }
/*     */   
/*     */   public void copyFile(String source, String path, String entryName) {}
/*     */   
/*     */   public void createArchive(String path, String archiveName, Manifest manifest) {}
/*     */   
/*     */   public void saveDirEntry(String path, String archiveName, String entryName) {}
/*     */   
/*     */   public void copyEntry(String source, String path, String archiveName, String entry) {}
/*     */   
/*     */   public void saveClassEntry(String path, String archiveName, String qualifiedName, String entryName, String content) {}
/*     */   
/*     */   public void closeArchive(String path, String archiveName) {}
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\transformer\debug\RuntimeDecompiler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */