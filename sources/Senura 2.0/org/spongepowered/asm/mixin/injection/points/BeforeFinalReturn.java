/*    */ package org.spongepowered.asm.mixin.injection.points;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.ListIterator;
/*    */ import org.spongepowered.asm.lib.Type;
/*    */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*    */ import org.spongepowered.asm.lib.tree.InsnList;
/*    */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*    */ import org.spongepowered.asm.mixin.injection.InjectionPoint.AtCode;
/*    */ import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
/*    */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
/*    */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
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
/*    */ @AtCode("TAIL")
/*    */ public class BeforeFinalReturn
/*    */   extends InjectionPoint
/*    */ {
/*    */   private final IMixinContext context;
/*    */   
/*    */   public BeforeFinalReturn(InjectionPointData data) {
/* 64 */     super(data);
/*    */     
/* 66 */     this.context = data.getContext();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean checkPriority(int targetPriority, int ownerPriority) {
/* 71 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/* 76 */     AbstractInsnNode ret = null;
/*    */ 
/*    */     
/* 79 */     int returnOpcode = Type.getReturnType(desc).getOpcode(172);
/*    */     
/* 81 */     ListIterator<AbstractInsnNode> iter = insns.iterator();
/* 82 */     while (iter.hasNext()) {
/* 83 */       AbstractInsnNode insn = iter.next();
/* 84 */       if (insn instanceof org.spongepowered.asm.lib.tree.InsnNode && insn.getOpcode() == returnOpcode) {
/* 85 */         ret = insn;
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 90 */     if (ret == null) {
/* 91 */       throw new InvalidInjectionException(this.context, "TAIL could not locate a valid RETURN in the target method!");
/*    */     }
/*    */     
/* 94 */     nodes.add(ret);
/* 95 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\points\BeforeFinalReturn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */