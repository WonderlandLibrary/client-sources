/*     */ package org.spongepowered.tools.obfuscation.mapping.mcp;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.google.common.io.Files;
/*     */ import com.google.common.io.LineProcessor;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.Charset;
/*     */ import javax.annotation.processing.Filer;
/*     */ import javax.annotation.processing.Messager;
/*     */ import org.spongepowered.asm.mixin.throwables.MixinException;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.asm.obfuscation.mapping.mcp.MappingFieldSrg;
/*     */ import org.spongepowered.tools.obfuscation.mapping.common.MappingProvider;
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
/*     */ public class MappingProviderSrg
/*     */   extends MappingProvider
/*     */ {
/*     */   public MappingProviderSrg(Messager messager, Filer filer) {
/*  53 */     super(messager, filer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(final File input) throws IOException {
/*  59 */     final BiMap<String, String> packageMap = this.packageMap;
/*  60 */     final BiMap<String, String> classMap = this.classMap;
/*  61 */     final BiMap<MappingField, MappingField> fieldMap = this.fieldMap;
/*  62 */     final BiMap<MappingMethod, MappingMethod> methodMap = this.methodMap;
/*     */     
/*  64 */     Files.readLines(input, Charset.defaultCharset(), new LineProcessor<String>()
/*     */         {
/*     */           public String getResult() {
/*  67 */             return null;
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean processLine(String line) throws IOException {
/*  72 */             if (Strings.isNullOrEmpty(line) || line.startsWith("#")) {
/*  73 */               return true;
/*     */             }
/*     */             
/*  76 */             String type = line.substring(0, 2);
/*  77 */             String[] args = line.substring(4).split(" ");
/*     */             
/*  79 */             if (type.equals("PK")) {
/*  80 */               packageMap.forcePut(args[0], args[1]);
/*  81 */             } else if (type.equals("CL")) {
/*  82 */               classMap.forcePut(args[0], args[1]);
/*  83 */             } else if (type.equals("FD")) {
/*  84 */               fieldMap.forcePut((new MappingFieldSrg(args[0])).copy(), (new MappingFieldSrg(args[1])).copy());
/*  85 */             } else if (type.equals("MD")) {
/*  86 */               methodMap.forcePut(new MappingMethod(args[0], args[1]), new MappingMethod(args[2], args[3]));
/*     */             } else {
/*  88 */               throw new MixinException("Invalid SRG file: " + input);
/*     */             } 
/*     */             
/*  91 */             return true;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public MappingField getFieldMapping(MappingField field) {
/*     */     MappingFieldSrg mappingFieldSrg;
/*  99 */     if (field.getDesc() != null) {
/* 100 */       mappingFieldSrg = new MappingFieldSrg(field);
/*     */     }
/* 102 */     return (MappingField)this.fieldMap.get(mappingFieldSrg);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\mapping\mcp\MappingProviderSrg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */