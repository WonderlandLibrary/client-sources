/*    */ package org.spongepowered.asm.lib.commons;
/*    */ 
/*    */ import org.spongepowered.asm.lib.AnnotationVisitor;
/*    */ import org.spongepowered.asm.lib.FieldVisitor;
/*    */ import org.spongepowered.asm.lib.TypePath;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FieldRemapper
/*    */   extends FieldVisitor
/*    */ {
/*    */   private final Remapper remapper;
/*    */   
/*    */   public FieldRemapper(FieldVisitor fv, Remapper remapper) {
/* 48 */     this(327680, fv, remapper);
/*    */   }
/*    */ 
/*    */   
/*    */   protected FieldRemapper(int api, FieldVisitor fv, Remapper remapper) {
/* 53 */     super(api, fv);
/* 54 */     this.remapper = remapper;
/*    */   }
/*    */ 
/*    */   
/*    */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/* 59 */     AnnotationVisitor av = this.fv.visitAnnotation(this.remapper.mapDesc(desc), visible);
/*    */     
/* 61 */     return (av == null) ? null : new AnnotationRemapper(av, this.remapper);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/* 67 */     AnnotationVisitor av = super.visitTypeAnnotation(typeRef, typePath, this.remapper
/* 68 */         .mapDesc(desc), visible);
/* 69 */     return (av == null) ? null : new AnnotationRemapper(av, this.remapper);
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\commons\FieldRemapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */