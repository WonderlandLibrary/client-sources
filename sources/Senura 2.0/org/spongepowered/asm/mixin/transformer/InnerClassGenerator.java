/*     */ package org.spongepowered.asm.mixin.transformer;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.lib.AnnotationVisitor;
/*     */ import org.spongepowered.asm.lib.ClassReader;
/*     */ import org.spongepowered.asm.lib.ClassVisitor;
/*     */ import org.spongepowered.asm.lib.commons.ClassRemapper;
/*     */ import org.spongepowered.asm.lib.commons.Remapper;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.IClassGenerator;
/*     */ import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
/*     */ import org.spongepowered.asm.service.MixinService;
/*     */ import org.spongepowered.asm.transformers.MixinClassWriter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class InnerClassGenerator
/*     */   implements IClassGenerator
/*     */ {
/*     */   static class InnerClassInfo
/*     */     extends Remapper
/*     */   {
/*     */     private final String name;
/*     */     private final String originalName;
/*     */     private final MixinInfo owner;
/*     */     private final MixinTargetContext target;
/*     */     private final String ownerName;
/*     */     private final String targetName;
/*     */     
/*     */     InnerClassInfo(String name, String originalName, MixinInfo owner, MixinTargetContext target) {
/*  90 */       this.name = name;
/*  91 */       this.originalName = originalName;
/*  92 */       this.owner = owner;
/*  93 */       this.ownerName = owner.getClassRef();
/*  94 */       this.target = target;
/*  95 */       this.targetName = target.getTargetClassRef();
/*     */     }
/*     */     
/*     */     String getName() {
/*  99 */       return this.name;
/*     */     }
/*     */     
/*     */     String getOriginalName() {
/* 103 */       return this.originalName;
/*     */     }
/*     */     
/*     */     MixinInfo getOwner() {
/* 107 */       return this.owner;
/*     */     }
/*     */     
/*     */     MixinTargetContext getTarget() {
/* 111 */       return this.target;
/*     */     }
/*     */     
/*     */     String getOwnerName() {
/* 115 */       return this.ownerName;
/*     */     }
/*     */     
/*     */     String getTargetName() {
/* 119 */       return this.targetName;
/*     */     }
/*     */     
/*     */     byte[] getClassBytes() throws ClassNotFoundException, IOException {
/* 123 */       return MixinService.getService().getBytecodeProvider().getClassBytes(this.originalName, true);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String mapMethodName(String owner, String name, String desc) {
/* 132 */       if (this.ownerName.equalsIgnoreCase(owner)) {
/* 133 */         ClassInfo.Method method = this.owner.getClassInfo().findMethod(name, desc, 10);
/* 134 */         if (method != null) {
/* 135 */           return method.getName();
/*     */         }
/*     */       } 
/* 138 */       return super.mapMethodName(owner, name, desc);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String map(String key) {
/* 146 */       if (this.originalName.equals(key))
/* 147 */         return this.name; 
/* 148 */       if (this.ownerName.equals(key)) {
/* 149 */         return this.targetName;
/*     */       }
/* 151 */       return key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 159 */       return this.name;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class InnerClassAdapter
/*     */     extends ClassRemapper
/*     */   {
/*     */     private final InnerClassGenerator.InnerClassInfo info;
/*     */ 
/*     */ 
/*     */     
/*     */     public InnerClassAdapter(ClassVisitor cv, InnerClassGenerator.InnerClassInfo info) {
/* 173 */       super(327680, cv, info);
/* 174 */       this.info = info;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void visitSource(String source, String debug) {
/* 183 */       super.visitSource(source, debug);
/* 184 */       AnnotationVisitor av = this.cv.visitAnnotation("Lorg/spongepowered/asm/mixin/transformer/meta/MixinInner;", false);
/* 185 */       av.visit("mixin", this.info.getOwner().toString());
/* 186 */       av.visit("name", this.info.getOriginalName().substring(this.info.getOriginalName().lastIndexOf('/') + 1));
/* 187 */       av.visitEnd();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void visitInnerClass(String name, String outerName, String innerName, int access) {
/* 197 */       if (name.startsWith(this.info.getOriginalName() + "$")) {
/* 198 */         throw new InvalidMixinException(this.info.getOwner(), "Found unsupported nested inner class " + name + " in " + this.info
/* 199 */             .getOriginalName());
/*     */       }
/*     */       
/* 202 */       super.visitInnerClass(name, outerName, innerName, access);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 210 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 216 */   private final Map<String, String> innerClassNames = new HashMap<String, String>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 221 */   private final Map<String, InnerClassInfo> innerClasses = new HashMap<String, InnerClassInfo>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String registerInnerClass(MixinInfo owner, String originalName, MixinTargetContext context) {
/* 230 */     String id = String.format("%s%s", new Object[] { originalName, context });
/* 231 */     String ref = this.innerClassNames.get(id);
/* 232 */     if (ref == null) {
/* 233 */       ref = getUniqueReference(originalName, context);
/* 234 */       this.innerClassNames.put(id, ref);
/* 235 */       this.innerClasses.put(ref, new InnerClassInfo(ref, originalName, owner, context));
/* 236 */       logger.debug("Inner class {} in {} on {} gets unique name {}", new Object[] { originalName, owner.getClassRef(), context
/* 237 */             .getTargetClassRef(), ref });
/*     */     } 
/* 239 */     return ref;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] generate(String name) {
/* 248 */     String ref = name.replace('.', '/');
/* 249 */     InnerClassInfo info = this.innerClasses.get(ref);
/* 250 */     if (info != null) {
/* 251 */       return generate(info);
/*     */     }
/* 253 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] generate(InnerClassInfo info) {
/*     */     try {
/* 265 */       logger.debug("Generating mapped inner class {} (originally {})", new Object[] { info.getName(), info.getOriginalName() });
/* 266 */       ClassReader cr = new ClassReader(info.getClassBytes());
/* 267 */       MixinClassWriter mixinClassWriter = new MixinClassWriter(cr, 0);
/* 268 */       cr.accept((ClassVisitor)new InnerClassAdapter((ClassVisitor)mixinClassWriter, info), 8);
/* 269 */       return mixinClassWriter.toByteArray();
/* 270 */     } catch (InvalidMixinException ex) {
/* 271 */       throw ex;
/* 272 */     } catch (Exception ex) {
/* 273 */       logger.catching(ex);
/*     */ 
/*     */       
/* 276 */       return null;
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
/*     */   private static String getUniqueReference(String originalName, MixinTargetContext context) {
/* 288 */     String name = originalName.substring(originalName.lastIndexOf('$') + 1);
/* 289 */     if (name.matches("^[0-9]+$")) {
/* 290 */       name = "Anonymous";
/*     */     }
/* 292 */     return String.format("%s$%s$%s", new Object[] { context.getTargetClassRef(), name, UUID.randomUUID().toString().replace("-", "") });
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\transformer\InnerClassGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */