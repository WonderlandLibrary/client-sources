/*     */ package org.spongepowered.asm.mixin.injection.points;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.ListIterator;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint.AtCode;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
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
/*     */ 
/*     */ 
/*     */ @AtCode("JUMP")
/*     */ public class JumpInsnPoint
/*     */   extends InjectionPoint
/*     */ {
/*     */   private final int opCode;
/*     */   private final int ordinal;
/*     */   
/*     */   public JumpInsnPoint(InjectionPointData data) {
/*  78 */     this.opCode = data.getOpcode(-1, new int[] { 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 198, 199, -1 });
/*     */ 
/*     */     
/*  81 */     this.ordinal = data.getOrdinal();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/*  86 */     boolean found = false;
/*  87 */     int ordinal = 0;
/*     */     
/*  89 */     ListIterator<AbstractInsnNode> iter = insns.iterator();
/*  90 */     while (iter.hasNext()) {
/*  91 */       AbstractInsnNode insn = iter.next();
/*     */       
/*  93 */       if (insn instanceof org.spongepowered.asm.lib.tree.JumpInsnNode && (this.opCode == -1 || insn.getOpcode() == this.opCode)) {
/*  94 */         if (this.ordinal == -1 || this.ordinal == ordinal) {
/*  95 */           nodes.add(insn);
/*  96 */           found = true;
/*     */         } 
/*     */         
/*  99 */         ordinal++;
/*     */       } 
/*     */     } 
/*     */     
/* 103 */     return found;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\points\JumpInsnPoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */