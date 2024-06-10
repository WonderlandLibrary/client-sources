/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import javax.annotation.processing.Filer;
/*     */ import javax.annotation.processing.Messager;
/*     */ import javax.lang.model.element.TypeElement;
/*     */ import javax.lang.model.type.DeclaredType;
/*     */ import javax.lang.model.type.TypeKind;
/*     */ import javax.lang.model.type.TypeMirror;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.asm.util.ObfuscationUtil;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IObfuscationEnvironment;
/*     */ import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
/*     */ import org.spongepowered.tools.obfuscation.mapping.IMappingProvider;
/*     */ import org.spongepowered.tools.obfuscation.mapping.IMappingWriter;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
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
/*     */ public abstract class ObfuscationEnvironment
/*     */   implements IObfuscationEnvironment
/*     */ {
/*     */   protected final ObfuscationType type;
/*     */   protected final IMappingProvider mappingProvider;
/*     */   protected final IMappingWriter mappingWriter;
/*     */   
/*     */   final class RemapperProxy
/*     */     implements ObfuscationUtil.IClassRemapper
/*     */   {
/*     */     public String map(String typeName) {
/*  69 */       if (ObfuscationEnvironment.this.mappingProvider == null) {
/*  70 */         return null;
/*     */       }
/*  72 */       return ObfuscationEnvironment.this.mappingProvider.getClassMapping(typeName);
/*     */     }
/*     */ 
/*     */     
/*     */     public String unmap(String typeName) {
/*  77 */       if (ObfuscationEnvironment.this.mappingProvider == null) {
/*  78 */         return null;
/*     */       }
/*  80 */       return ObfuscationEnvironment.this.mappingProvider.getClassMapping(typeName);
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
/*  97 */   protected final RemapperProxy remapper = new RemapperProxy();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final IMixinAnnotationProcessor ap;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final String outFileName;
/*     */ 
/*     */ 
/*     */   
/*     */   protected final List<String> inFileNames;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean initDone;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ObfuscationEnvironment(ObfuscationType type) {
/* 121 */     this.type = type;
/* 122 */     this.ap = type.getAnnotationProcessor();
/*     */     
/* 124 */     this.inFileNames = type.getInputFileNames();
/* 125 */     this.outFileName = type.getOutputFileName();
/*     */     
/* 127 */     this.mappingProvider = getMappingProvider((Messager)this.ap, this.ap.getProcessingEnvironment().getFiler());
/* 128 */     this.mappingWriter = getMappingWriter((Messager)this.ap, this.ap.getProcessingEnvironment().getFiler());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 133 */     return this.type.toString();
/*     */   }
/*     */   
/*     */   protected abstract IMappingProvider getMappingProvider(Messager paramMessager, Filer paramFiler);
/*     */   
/*     */   protected abstract IMappingWriter getMappingWriter(Messager paramMessager, Filer paramFiler);
/*     */   
/*     */   private boolean initMappings() {
/* 141 */     if (!this.initDone) {
/* 142 */       this.initDone = true;
/*     */       
/* 144 */       if (this.inFileNames == null) {
/* 145 */         this.ap.printMessage(Diagnostic.Kind.ERROR, "The " + this.type.getConfig().getInputFileOption() + " argument was not supplied, obfuscation processing will not occur");
/*     */         
/* 147 */         return false;
/*     */       } 
/*     */       
/* 150 */       int successCount = 0;
/*     */       
/* 152 */       for (String inputFileName : this.inFileNames) {
/* 153 */         File inputFile = new File(inputFileName);
/*     */         try {
/* 155 */           if (inputFile.isFile()) {
/* 156 */             this.ap.printMessage(Diagnostic.Kind.NOTE, "Loading " + this.type + " mappings from " + inputFile.getAbsolutePath());
/* 157 */             this.mappingProvider.read(inputFile);
/* 158 */             successCount++;
/*     */           } 
/* 160 */         } catch (Exception ex) {
/* 161 */           ex.printStackTrace();
/*     */         } 
/*     */       } 
/*     */       
/* 165 */       if (successCount < 1) {
/* 166 */         this.ap.printMessage(Diagnostic.Kind.ERROR, "No valid input files for " + this.type + " could be read, processing may not be sucessful.");
/* 167 */         this.mappingProvider.clear();
/*     */       } 
/*     */     } 
/*     */     
/* 171 */     return !this.mappingProvider.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObfuscationType getType() {
/* 178 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MappingMethod getObfMethod(MemberInfo method) {
/* 186 */     MappingMethod obfd = getObfMethod(method.asMethodMapping());
/* 187 */     if (obfd != null || !method.isFullyQualified()) {
/* 188 */       return obfd;
/*     */     }
/*     */ 
/*     */     
/* 192 */     TypeHandle type = this.ap.getTypeProvider().getTypeHandle(method.owner);
/* 193 */     if (type == null || type.isImaginary()) {
/* 194 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 198 */     TypeMirror superClass = type.getElement().getSuperclass();
/* 199 */     if (superClass.getKind() != TypeKind.DECLARED) {
/* 200 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 204 */     String superClassName = ((TypeElement)((DeclaredType)superClass).asElement()).getQualifiedName().toString();
/* 205 */     return getObfMethod(new MemberInfo(method.name, superClassName.replace('.', '/'), method.desc, method.matchAll));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MappingMethod getObfMethod(MappingMethod method) {
/* 213 */     return getObfMethod(method, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MappingMethod getObfMethod(MappingMethod method, boolean lazyRemap) {
/* 221 */     if (initMappings()) {
/* 222 */       boolean remapped = true;
/* 223 */       MappingMethod mapping = null;
/* 224 */       for (MappingMethod md = method; md != null && mapping == null; md = md.getSuper()) {
/* 225 */         mapping = this.mappingProvider.getMethodMapping(md);
/*     */       }
/*     */ 
/*     */       
/* 229 */       if (mapping == null) {
/* 230 */         if (lazyRemap) {
/* 231 */           return null;
/*     */         }
/* 233 */         mapping = method.copy();
/* 234 */         remapped = false;
/*     */       } 
/* 236 */       String remappedOwner = getObfClass(mapping.getOwner());
/* 237 */       if (remappedOwner == null || remappedOwner.equals(method.getOwner()) || remappedOwner.equals(mapping.getOwner())) {
/* 238 */         return remapped ? mapping : null;
/*     */       }
/* 240 */       if (remapped) {
/* 241 */         return mapping.move(remappedOwner);
/*     */       }
/* 243 */       String desc = ObfuscationUtil.mapDescriptor(mapping.getDesc(), this.remapper);
/* 244 */       return new MappingMethod(remappedOwner, mapping.getSimpleName(), desc);
/*     */     } 
/* 246 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MemberInfo remapDescriptor(MemberInfo method) {
/* 257 */     boolean transformed = false;
/*     */     
/* 259 */     String owner = method.owner;
/* 260 */     if (owner != null) {
/* 261 */       String newOwner = this.remapper.map(owner);
/* 262 */       if (newOwner != null) {
/* 263 */         owner = newOwner;
/* 264 */         transformed = true;
/*     */       } 
/*     */     } 
/*     */     
/* 268 */     String desc = method.desc;
/* 269 */     if (desc != null) {
/* 270 */       String newDesc = ObfuscationUtil.mapDescriptor(method.desc, this.remapper);
/* 271 */       if (!newDesc.equals(method.desc)) {
/* 272 */         desc = newDesc;
/* 273 */         transformed = true;
/*     */       } 
/*     */     } 
/*     */     
/* 277 */     return transformed ? new MemberInfo(method.name, owner, desc, method.matchAll) : null;
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
/*     */   public String remapDescriptor(String desc) {
/* 289 */     return ObfuscationUtil.mapDescriptor(desc, this.remapper);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MappingField getObfField(MemberInfo field) {
/* 297 */     return getObfField(field.asFieldMapping(), true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MappingField getObfField(MappingField field) {
/* 305 */     return getObfField(field, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MappingField getObfField(MappingField field, boolean lazyRemap) {
/* 313 */     if (!initMappings()) {
/* 314 */       return null;
/*     */     }
/*     */     
/* 317 */     MappingField mapping = this.mappingProvider.getFieldMapping(field);
/*     */     
/* 319 */     if (mapping == null) {
/* 320 */       if (lazyRemap) {
/* 321 */         return null;
/*     */       }
/* 323 */       mapping = field;
/*     */     } 
/* 325 */     String remappedOwner = getObfClass(mapping.getOwner());
/* 326 */     if (remappedOwner == null || remappedOwner.equals(field.getOwner()) || remappedOwner.equals(mapping.getOwner())) {
/* 327 */       return (mapping != field) ? mapping : null;
/*     */     }
/* 329 */     return mapping.move(remappedOwner);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getObfClass(String className) {
/* 337 */     if (!initMappings()) {
/* 338 */       return null;
/*     */     }
/* 340 */     return this.mappingProvider.getClassMapping(className);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeMappings(Collection<IMappingConsumer> consumers) {
/* 348 */     IMappingConsumer.MappingSet<MappingField> fields = new IMappingConsumer.MappingSet();
/* 349 */     IMappingConsumer.MappingSet<MappingMethod> methods = new IMappingConsumer.MappingSet();
/*     */     
/* 351 */     for (IMappingConsumer mappings : consumers) {
/* 352 */       fields.addAll((Collection)mappings.getFieldMappings(this.type));
/* 353 */       methods.addAll((Collection)mappings.getMethodMappings(this.type));
/*     */     } 
/*     */     
/* 356 */     this.mappingWriter.write(this.outFileName, this.type, fields, methods);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\ObfuscationEnvironment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */