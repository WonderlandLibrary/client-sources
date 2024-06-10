/*    */ package org.spongepowered.asm.lib.tree;
/*    */ 
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
/*    */ public class TypeAnnotationNode
/*    */   extends AnnotationNode
/*    */ {
/*    */   public int typeRef;
/*    */   public TypePath typePath;
/*    */   
/*    */   public TypeAnnotationNode(int typeRef, TypePath typePath, String desc) {
/* 73 */     this(327680, typeRef, typePath, desc);
/* 74 */     if (getClass() != TypeAnnotationNode.class) {
/* 75 */       throw new IllegalStateException();
/*    */     }
/*    */   }
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
/*    */   public TypeAnnotationNode(int api, int typeRef, TypePath typePath, String desc) {
/* 96 */     super(api, desc);
/* 97 */     this.typeRef = typeRef;
/* 98 */     this.typePath = typePath;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\tree\TypeAnnotationNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */