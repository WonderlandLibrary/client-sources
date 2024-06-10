/*     */ package org.spongepowered.asm.mixin.injection.modify;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.ListIterator;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.lib.tree.VarInsnNode;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint.AtCode;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
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
/*     */ @AtCode("LOAD")
/*     */ public class BeforeLoadLocal
/*     */   extends ModifyVariableInjector.ContextualInjectionPoint
/*     */ {
/*     */   private final Type returnType;
/*     */   private final LocalVariableDiscriminator discriminator;
/*     */   private final int opcode;
/*     */   private final int ordinal;
/*     */   private boolean opcodeAfter;
/*     */   
/*     */   static class SearchState
/*     */   {
/*     */     private final boolean print;
/*     */     private final int targetOrdinal;
/*  96 */     private int ordinal = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean pendingCheck = false;
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean found = false;
/*     */ 
/*     */ 
/*     */     
/*     */     private VarInsnNode varNode;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     SearchState(int targetOrdinal, boolean print) {
/* 115 */       this.targetOrdinal = targetOrdinal;
/* 116 */       this.print = print;
/*     */     }
/*     */     
/*     */     boolean success() {
/* 120 */       return this.found;
/*     */     }
/*     */     
/*     */     boolean isPendingCheck() {
/* 124 */       return this.pendingCheck;
/*     */     }
/*     */     
/*     */     void setPendingCheck() {
/* 128 */       this.pendingCheck = true;
/*     */     }
/*     */     
/*     */     void register(VarInsnNode node) {
/* 132 */       this.varNode = node;
/*     */     }
/*     */     
/*     */     void check(Collection<AbstractInsnNode> nodes, AbstractInsnNode insn, int local) {
/* 136 */       this.pendingCheck = false;
/* 137 */       if (local != this.varNode.var && (local > -2 || !this.print)) {
/*     */         return;
/*     */       }
/*     */       
/* 141 */       if (this.targetOrdinal == -1 || this.targetOrdinal == this.ordinal) {
/* 142 */         nodes.add(insn);
/* 143 */         this.found = true;
/*     */       } 
/*     */       
/* 146 */       this.ordinal++;
/* 147 */       this.varNode = null;
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
/*     */   protected BeforeLoadLocal(InjectionPointData data) {
/* 180 */     this(data, 21, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BeforeLoadLocal(InjectionPointData data, int opcode, boolean opcodeAfter) {
/* 185 */     super(data.getContext());
/* 186 */     this.returnType = data.getMethodReturnType();
/* 187 */     this.discriminator = data.getLocalVariableDiscriminator();
/* 188 */     this.opcode = data.getOpcode(this.returnType.getOpcode(opcode));
/* 189 */     this.ordinal = data.getOrdinal();
/* 190 */     this.opcodeAfter = opcodeAfter;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean find(Target target, Collection<AbstractInsnNode> nodes) {
/* 195 */     SearchState state = new SearchState(this.ordinal, this.discriminator.printLVT());
/*     */     
/* 197 */     ListIterator<AbstractInsnNode> iter = target.method.instructions.iterator();
/* 198 */     while (iter.hasNext()) {
/* 199 */       AbstractInsnNode insn = iter.next();
/* 200 */       if (state.isPendingCheck()) {
/* 201 */         int local = this.discriminator.findLocal(this.returnType, this.discriminator.isArgsOnly(), target, insn);
/* 202 */         state.check(nodes, insn, local); continue;
/* 203 */       }  if (insn instanceof VarInsnNode && insn.getOpcode() == this.opcode && (this.ordinal == -1 || !state.success())) {
/* 204 */         state.register((VarInsnNode)insn);
/* 205 */         if (this.opcodeAfter) {
/* 206 */           state.setPendingCheck(); continue;
/*     */         } 
/* 208 */         int local = this.discriminator.findLocal(this.returnType, this.discriminator.isArgsOnly(), target, insn);
/* 209 */         state.check(nodes, insn, local);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 214 */     return state.success();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\modify\BeforeLoadLocal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */