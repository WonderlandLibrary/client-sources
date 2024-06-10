/*     */ package org.spongepowered.asm.mixin.transformer;
/*     */ 
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
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
/*     */ public class Config
/*     */ {
/*     */   private final String name;
/*     */   private final MixinConfig config;
/*     */   
/*     */   public Config(MixinConfig config) {
/*  46 */     this.name = config.getName();
/*  47 */     this.config = config;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  51 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MixinConfig get() {
/*  58 */     return this.config;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVisited() {
/*  65 */     return this.config.isVisited();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMixinConfig getConfig() {
/*  72 */     return this.config;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MixinEnvironment getEnvironment() {
/*  79 */     return this.config.getEnvironment();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  87 */     return this.config.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  95 */     return (obj instanceof Config && this.name.equals(((Config)obj).name));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 103 */     return this.name.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static Config create(String configFile, MixinEnvironment outer) {
/* 116 */     return MixinConfig.create(configFile, outer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Config create(String configFile) {
/* 126 */     return MixinConfig.create(configFile, MixinEnvironment.getDefaultEnvironment());
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\transformer\Config.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */