/*     */ package org.spongepowered.tools.agent;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.lib.ClassWriter;
/*     */ import org.spongepowered.asm.lib.MethodVisitor;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
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
/*     */ class MixinAgentClassLoader
/*     */   extends ClassLoader
/*     */ {
/*  45 */   private static final Logger logger = LogManager.getLogger("mixin.agent");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   private Map<Class<?>, byte[]> mixins = (Map)new HashMap<Class<?>, byte>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   private Map<String, byte[]> targets = (Map)new HashMap<String, byte>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addMixinClass(String name) {
/*  64 */     logger.debug("Mixin class {} added to class loader", new Object[] { name });
/*     */     try {
/*  66 */       byte[] bytes = materialise(name);
/*  67 */       Class<?> clazz = defineClass(name, bytes, 0, bytes.length);
/*     */ 
/*     */       
/*  70 */       clazz.newInstance();
/*  71 */       this.mixins.put(clazz, bytes);
/*  72 */     } catch (Throwable e) {
/*  73 */       logger.catching(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addTargetClass(String name, byte[] bytecode) {
/*  84 */     this.targets.put(name, bytecode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] getFakeMixinBytecode(Class<?> clazz) {
/*  94 */     return this.mixins.get(clazz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] getOriginalTargetBytecode(String name) {
/* 104 */     return this.targets.get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] materialise(String name) {
/* 114 */     ClassWriter cw = new ClassWriter(3);
/* 115 */     cw.visit(MixinEnvironment.getCompatibilityLevel().classVersion(), 1, name.replace('.', '/'), null, 
/* 116 */         Type.getInternalName(Object.class), null);
/*     */ 
/*     */     
/* 119 */     MethodVisitor mv = cw.visitMethod(1, "<init>", "()V", null, null);
/* 120 */     mv.visitCode();
/* 121 */     mv.visitVarInsn(25, 0);
/* 122 */     mv.visitMethodInsn(183, Type.getInternalName(Object.class), "<init>", "()V", false);
/* 123 */     mv.visitInsn(177);
/* 124 */     mv.visitMaxs(1, 1);
/* 125 */     mv.visitEnd();
/*     */     
/* 127 */     cw.visitEnd();
/* 128 */     return cw.toByteArray();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\agent\MixinAgentClassLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */