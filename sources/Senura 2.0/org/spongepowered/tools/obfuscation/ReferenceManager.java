/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.List;
/*     */ import javax.tools.Diagnostic;
/*     */ import javax.tools.FileObject;
/*     */ import javax.tools.StandardLocation;
/*     */ import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
/*     */ import org.spongepowered.asm.mixin.refmap.ReferenceMapper;
/*     */ import org.spongepowered.asm.obfuscation.mapping.IMapping;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IReferenceManager;
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
/*     */ public class ReferenceManager
/*     */   implements IReferenceManager
/*     */ {
/*     */   private final IMixinAnnotationProcessor ap;
/*     */   private final String outRefMapFileName;
/*     */   private final List<ObfuscationEnvironment> environments;
/*     */   
/*     */   public static class ReferenceConflictException
/*     */     extends RuntimeException
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private final String oldReference;
/*     */     private final String newReference;
/*     */     
/*     */     public ReferenceConflictException(String oldReference, String newReference) {
/*  58 */       this.oldReference = oldReference;
/*  59 */       this.newReference = newReference;
/*     */     }
/*     */     
/*     */     public String getOld() {
/*  63 */       return this.oldReference;
/*     */     }
/*     */     
/*     */     public String getNew() {
/*  67 */       return this.newReference;
/*     */     }
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
/*  90 */   private final ReferenceMapper refMapper = new ReferenceMapper();
/*     */   
/*     */   private boolean allowConflicts;
/*     */   
/*     */   public ReferenceManager(IMixinAnnotationProcessor ap, List<ObfuscationEnvironment> environments) {
/*  95 */     this.ap = ap;
/*  96 */     this.environments = environments;
/*  97 */     this.outRefMapFileName = this.ap.getOption("outRefMapFile");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getAllowConflicts() {
/* 106 */     return this.allowConflicts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAllowConflicts(boolean allowConflicts) {
/* 115 */     this.allowConflicts = allowConflicts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write() {
/* 123 */     if (this.outRefMapFileName == null) {
/*     */       return;
/*     */     }
/*     */     
/* 127 */     PrintWriter writer = null;
/*     */     
/*     */     try {
/* 130 */       writer = newWriter(this.outRefMapFileName, "refmap");
/* 131 */       this.refMapper.write(writer);
/* 132 */     } catch (IOException ex) {
/* 133 */       ex.printStackTrace();
/*     */     } finally {
/* 135 */       if (writer != null) {
/*     */         try {
/* 137 */           writer.close();
/* 138 */         } catch (Exception exception) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PrintWriter newWriter(String fileName, String description) throws IOException {
/* 149 */     if (fileName.matches("^.*[\\\\/:].*$")) {
/* 150 */       File outFile = new File(fileName);
/* 151 */       outFile.getParentFile().mkdirs();
/* 152 */       this.ap.printMessage(Diagnostic.Kind.NOTE, "Writing " + description + " to " + outFile.getAbsolutePath());
/* 153 */       return new PrintWriter(outFile);
/*     */     } 
/*     */     
/* 156 */     FileObject outResource = this.ap.getProcessingEnvironment().getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", fileName, new javax.lang.model.element.Element[0]);
/* 157 */     this.ap.printMessage(Diagnostic.Kind.NOTE, "Writing " + description + " to " + (new File(outResource.toUri())).getAbsolutePath());
/* 158 */     return new PrintWriter(outResource.openWriter());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceMapper getMapper() {
/* 167 */     return this.refMapper;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMethodMapping(String className, String reference, ObfuscationData<MappingMethod> obfMethodData) {
/* 177 */     for (ObfuscationEnvironment env : this.environments) {
/* 178 */       MappingMethod obfMethod = obfMethodData.get(env.getType());
/* 179 */       if (obfMethod != null) {
/* 180 */         MemberInfo remappedReference = new MemberInfo((IMapping)obfMethod);
/* 181 */         addMapping(env.getType(), className, reference, remappedReference.toString());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMethodMapping(String className, String reference, MemberInfo context, ObfuscationData<MappingMethod> obfMethodData) {
/* 194 */     for (ObfuscationEnvironment env : this.environments) {
/* 195 */       MappingMethod obfMethod = obfMethodData.get(env.getType());
/* 196 */       if (obfMethod != null) {
/* 197 */         MemberInfo remappedReference = context.remapUsing(obfMethod, true);
/* 198 */         addMapping(env.getType(), className, reference, remappedReference.toString());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFieldMapping(String className, String reference, MemberInfo context, ObfuscationData<MappingField> obfFieldData) {
/* 211 */     for (ObfuscationEnvironment env : this.environments) {
/* 212 */       MappingField obfField = obfFieldData.get(env.getType());
/* 213 */       if (obfField != null) {
/* 214 */         MemberInfo remappedReference = MemberInfo.fromMapping((IMapping)obfField.transform(env.remapDescriptor(context.desc)));
/* 215 */         addMapping(env.getType(), className, reference, remappedReference.toString());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addClassMapping(String className, String reference, ObfuscationData<String> obfClassData) {
/* 227 */     for (ObfuscationEnvironment env : this.environments) {
/* 228 */       String remapped = obfClassData.get(env.getType());
/* 229 */       if (remapped != null) {
/* 230 */         addMapping(env.getType(), className, reference, remapped);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void addMapping(ObfuscationType type, String className, String reference, String newReference) {
/* 236 */     String oldReference = this.refMapper.addMapping(type.getKey(), className, reference, newReference);
/* 237 */     if (type.isDefault()) {
/* 238 */       this.refMapper.addMapping(null, className, reference, newReference);
/*     */     }
/*     */     
/* 241 */     if (!this.allowConflicts && oldReference != null && !oldReference.equals(newReference))
/* 242 */       throw new ReferenceConflictException(oldReference, newReference); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\ReferenceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */