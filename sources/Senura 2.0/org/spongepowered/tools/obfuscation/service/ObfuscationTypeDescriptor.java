/*     */ package org.spongepowered.tools.obfuscation.service;
/*     */ 
/*     */ import org.spongepowered.tools.obfuscation.ObfuscationEnvironment;
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
/*     */ public class ObfuscationTypeDescriptor
/*     */ {
/*     */   private final String key;
/*     */   private final String inputFileArgName;
/*     */   private final String extraInputFilesArgName;
/*     */   private final String outFileArgName;
/*     */   private final Class<? extends ObfuscationEnvironment> environmentType;
/*     */   
/*     */   public ObfuscationTypeDescriptor(String key, String inputFileArgName, String outFileArgName, Class<? extends ObfuscationEnvironment> environmentType) {
/*  63 */     this(key, inputFileArgName, null, outFileArgName, environmentType);
/*     */   }
/*     */ 
/*     */   
/*     */   public ObfuscationTypeDescriptor(String key, String inputFileArgName, String extraInputFilesArgName, String outFileArgName, Class<? extends ObfuscationEnvironment> environmentType) {
/*  68 */     this.key = key;
/*  69 */     this.inputFileArgName = inputFileArgName;
/*  70 */     this.extraInputFilesArgName = extraInputFilesArgName;
/*  71 */     this.outFileArgName = outFileArgName;
/*  72 */     this.environmentType = environmentType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getKey() {
/*  79 */     return this.key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInputFileOption() {
/*  86 */     return this.inputFileArgName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExtraInputFilesOption() {
/*  93 */     return this.extraInputFilesArgName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOutputFileOption() {
/* 100 */     return this.outFileArgName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<? extends ObfuscationEnvironment> getEnvironmentType() {
/* 107 */     return this.environmentType;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\service\ObfuscationTypeDescriptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */