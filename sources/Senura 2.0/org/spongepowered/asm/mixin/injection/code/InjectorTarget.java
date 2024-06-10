/*     */ package org.spongepowered.asm.mixin.injection.code;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*     */ import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
/*     */ import org.spongepowered.asm.util.Annotations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InjectorTarget
/*     */ {
/*     */   private final ISliceContext context;
/*  53 */   private final Map<String, ReadOnlyInsnList> cache = new HashMap<String, ReadOnlyInsnList>();
/*     */ 
/*     */ 
/*     */   
/*     */   private final Target target;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String mergedBy;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int mergedPriority;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InjectorTarget(ISliceContext context, Target target) {
/*  71 */     this.context = context;
/*  72 */     this.target = target;
/*     */     
/*  74 */     AnnotationNode merged = Annotations.getVisible(target.method, MixinMerged.class);
/*  75 */     this.mergedBy = (String)Annotations.getValue(merged, "mixin");
/*  76 */     this.mergedPriority = ((Integer)Annotations.getValue(merged, "priority", Integer.valueOf(1000))).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  81 */     return this.target.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Target getTarget() {
/*  88 */     return this.target;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodNode getMethod() {
/*  95 */     return this.target.method;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMerged() {
/* 102 */     return (this.mergedBy != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMergedBy() {
/* 110 */     return this.mergedBy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMergedPriority() {
/* 118 */     return this.mergedPriority;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InsnList getSlice(String id) {
/* 128 */     ReadOnlyInsnList slice = this.cache.get(id);
/* 129 */     if (slice == null) {
/* 130 */       MethodSlice sliceInfo = this.context.getSlice(id);
/* 131 */       if (sliceInfo != null) {
/* 132 */         slice = sliceInfo.getSlice(this.target.method);
/*     */       } else {
/*     */         
/* 135 */         slice = new ReadOnlyInsnList(this.target.method.instructions);
/*     */       } 
/* 137 */       this.cache.put(id, slice);
/*     */     } 
/*     */     
/* 140 */     return slice;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InsnList getSlice(InjectionPoint injectionPoint) {
/* 150 */     return getSlice(injectionPoint.getSlice());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 157 */     for (ReadOnlyInsnList insns : this.cache.values()) {
/* 158 */       insns.dispose();
/*     */     }
/*     */     
/* 161 */     this.cache.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\code\InjectorTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */