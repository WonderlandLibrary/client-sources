/*    */ package org.spongepowered.asm.mixin.gen;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import org.spongepowered.asm.lib.tree.MethodNode;
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
/*    */ public abstract class AccessorGenerator
/*    */ {
/*    */   protected final AccessorInfo info;
/*    */   
/*    */   public AccessorGenerator(AccessorInfo info) {
/* 44 */     this.info = info;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final MethodNode createMethod(int maxLocals, int maxStack) {
/* 55 */     MethodNode method = this.info.getMethod();
/* 56 */     MethodNode accessor = new MethodNode(327680, method.access & 0xFFFFFBFF | 0x1000, method.name, method.desc, null, null);
/*    */     
/* 58 */     accessor.visibleAnnotations = new ArrayList();
/* 59 */     accessor.visibleAnnotations.add(this.info.getAnnotation());
/* 60 */     accessor.maxLocals = maxLocals;
/* 61 */     accessor.maxStack = maxStack;
/* 62 */     return accessor;
/*    */   }
/*    */   
/*    */   public abstract MethodNode generate();
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\gen\AccessorGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */