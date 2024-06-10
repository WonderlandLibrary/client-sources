/*     */ package org.spongepowered.asm.util.asm;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.analysis.SimpleVerifier;
/*     */ import org.spongepowered.asm.mixin.transformer.ClassInfo;
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
/*     */ public class MixinVerifier
/*     */   extends SimpleVerifier
/*     */ {
/*     */   private Type currentClass;
/*     */   private Type currentSuperClass;
/*     */   private List<Type> currentClassInterfaces;
/*     */   private boolean isInterface;
/*     */   
/*     */   public MixinVerifier(Type currentClass, Type currentSuperClass, List<Type> currentClassInterfaces, boolean isInterface) {
/*  44 */     super(currentClass, currentSuperClass, currentClassInterfaces, isInterface);
/*  45 */     this.currentClass = currentClass;
/*  46 */     this.currentSuperClass = currentSuperClass;
/*  47 */     this.currentClassInterfaces = currentClassInterfaces;
/*  48 */     this.isInterface = isInterface;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isInterface(Type type) {
/*  53 */     if (this.currentClass != null && type.equals(this.currentClass)) {
/*  54 */       return this.isInterface;
/*     */     }
/*  56 */     return ClassInfo.forType(type).isInterface();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Type getSuperClass(Type type) {
/*  61 */     if (this.currentClass != null && type.equals(this.currentClass)) {
/*  62 */       return this.currentSuperClass;
/*     */     }
/*  64 */     ClassInfo c = ClassInfo.forType(type).getSuperClass();
/*  65 */     return (c == null) ? null : Type.getType("L" + c.getName() + ";");
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isAssignableFrom(Type type, Type other) {
/*  70 */     if (type.equals(other)) {
/*  71 */       return true;
/*     */     }
/*  73 */     if (this.currentClass != null && type.equals(this.currentClass)) {
/*  74 */       if (getSuperClass(other) == null) {
/*  75 */         return false;
/*     */       }
/*  77 */       if (this.isInterface) {
/*  78 */         return (other.getSort() == 10 || other.getSort() == 9);
/*     */       }
/*  80 */       return isAssignableFrom(type, getSuperClass(other));
/*     */     } 
/*  82 */     if (this.currentClass != null && other.equals(this.currentClass)) {
/*  83 */       if (isAssignableFrom(type, this.currentSuperClass)) {
/*  84 */         return true;
/*     */       }
/*  86 */       if (this.currentClassInterfaces != null) {
/*  87 */         for (int i = 0; i < this.currentClassInterfaces.size(); i++) {
/*  88 */           Type v = this.currentClassInterfaces.get(i);
/*  89 */           if (isAssignableFrom(type, v)) {
/*  90 */             return true;
/*     */           }
/*     */         } 
/*     */       }
/*  94 */       return false;
/*     */     } 
/*  96 */     ClassInfo typeInfo = ClassInfo.forType(type);
/*  97 */     if (typeInfo == null) {
/*  98 */       return false;
/*     */     }
/* 100 */     if (typeInfo.isInterface()) {
/* 101 */       typeInfo = ClassInfo.forName("java/lang/Object");
/*     */     }
/* 103 */     return ClassInfo.forType(other).hasSuperClass(typeInfo);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\as\\util\asm\MixinVerifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */