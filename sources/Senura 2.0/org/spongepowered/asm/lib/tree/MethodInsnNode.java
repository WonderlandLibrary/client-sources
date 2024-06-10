/*     */ package org.spongepowered.asm.lib.tree;
/*     */ 
/*     */ import java.util.Map;
/*     */ import org.spongepowered.asm.lib.MethodVisitor;
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
/*     */ public class MethodInsnNode
/*     */   extends AbstractInsnNode
/*     */ {
/*     */   public String owner;
/*     */   public String name;
/*     */   public String desc;
/*     */   public boolean itf;
/*     */   
/*     */   @Deprecated
/*     */   public MethodInsnNode(int opcode, String owner, String name, String desc) {
/*  85 */     this(opcode, owner, name, desc, (opcode == 185));
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
/*     */   public MethodInsnNode(int opcode, String owner, String name, String desc, boolean itf) {
/* 108 */     super(opcode);
/* 109 */     this.owner = owner;
/* 110 */     this.name = name;
/* 111 */     this.desc = desc;
/* 112 */     this.itf = itf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOpcode(int opcode) {
/* 123 */     this.opcode = opcode;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getType() {
/* 128 */     return 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public void accept(MethodVisitor mv) {
/* 133 */     mv.visitMethodInsn(this.opcode, this.owner, this.name, this.desc, this.itf);
/* 134 */     acceptAnnotations(mv);
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels) {
/* 139 */     return new MethodInsnNode(this.opcode, this.owner, this.name, this.desc, this.itf);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\tree\MethodInsnNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */