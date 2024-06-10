/*     */ package org.spongepowered.asm.mixin.injection.code;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidSliceException;
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
/*     */ public final class MethodSlices
/*     */ {
/*     */   private final InjectionInfo info;
/*  50 */   private final Map<String, MethodSlice> slices = new HashMap<String, MethodSlice>(4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MethodSlices(InjectionInfo info) {
/*  58 */     this.info = info;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void add(MethodSlice slice) {
/*  67 */     String id = this.info.getSliceId(slice.getId());
/*  68 */     if (this.slices.containsKey(id)) {
/*  69 */       throw new InvalidSliceException(this.info, slice + " has a duplicate id, '" + id + "' was already defined");
/*     */     }
/*  71 */     this.slices.put(id, slice);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodSlice get(String id) {
/*  82 */     return this.slices.get(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  90 */     return String.format("MethodSlices%s", new Object[] { this.slices.keySet() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MethodSlices parse(InjectionInfo info) {
/* 100 */     MethodSlices slices = new MethodSlices(info);
/*     */     
/* 102 */     AnnotationNode annotation = info.getAnnotation();
/* 103 */     if (annotation != null) {
/* 104 */       for (AnnotationNode node : Annotations.getValue(annotation, "slice", true)) {
/* 105 */         MethodSlice slice = MethodSlice.parse((ISliceContext)info, node);
/* 106 */         slices.add(slice);
/*     */       } 
/*     */     }
/*     */     
/* 110 */     return slices;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\code\MethodSlices.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */