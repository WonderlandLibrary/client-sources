/*     */ package org.spongepowered.asm.mixin.injection.points;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.FieldInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint.AtCode;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
/*     */ import org.spongepowered.asm.util.Bytecode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @AtCode("FIELD")
/*     */ public class BeforeFieldAccess
/*     */   extends BeforeInvoke
/*     */ {
/*     */   private static final String ARRAY_GET = "get";
/*     */   private static final String ARRAY_SET = "set";
/*     */   private static final String ARRAY_LENGTH = "length";
/*     */   public static final int ARRAY_SEARCH_FUZZ_DEFAULT = 8;
/*     */   private final int opcode;
/*     */   private final int arrOpcode;
/*     */   private final int fuzzFactor;
/*     */   
/*     */   public BeforeFieldAccess(InjectionPointData data) {
/* 116 */     super(data);
/* 117 */     this.opcode = data.getOpcode(-1, new int[] { 180, 181, 178, 179, -1 });
/*     */     
/* 119 */     String array = data.get("array", "");
/* 120 */     this
/*     */       
/* 122 */       .arrOpcode = "get".equalsIgnoreCase(array) ? 46 : ("set".equalsIgnoreCase(array) ? 79 : ("length".equalsIgnoreCase(array) ? 190 : 0));
/* 123 */     this.fuzzFactor = Math.min(Math.max(data.get("fuzz", 8), 1), 32);
/*     */   }
/*     */   
/*     */   public int getFuzzFactor() {
/* 127 */     return this.fuzzFactor;
/*     */   }
/*     */   
/*     */   public int getArrayOpcode() {
/* 131 */     return this.arrOpcode;
/*     */   }
/*     */   
/*     */   private int getArrayOpcode(String desc) {
/* 135 */     if (this.arrOpcode != 190) {
/* 136 */       return Type.getType(desc).getElementType().getOpcode(this.arrOpcode);
/*     */     }
/* 138 */     return this.arrOpcode;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean matchesInsn(AbstractInsnNode insn) {
/* 143 */     if (insn instanceof FieldInsnNode && (((FieldInsnNode)insn).getOpcode() == this.opcode || this.opcode == -1)) {
/* 144 */       if (this.arrOpcode == 0) {
/* 145 */         return true;
/*     */       }
/*     */       
/* 148 */       if (insn.getOpcode() != 178 && insn.getOpcode() != 180) {
/* 149 */         return false;
/*     */       }
/*     */       
/* 152 */       return (Type.getType(((FieldInsnNode)insn).desc).getSort() == 9);
/*     */     } 
/*     */     
/* 155 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean addInsn(InsnList insns, Collection<AbstractInsnNode> nodes, AbstractInsnNode insn) {
/* 160 */     if (this.arrOpcode > 0) {
/* 161 */       FieldInsnNode fieldInsn = (FieldInsnNode)insn;
/* 162 */       int accOpcode = getArrayOpcode(fieldInsn.desc);
/* 163 */       log("{} > > > > searching for array access opcode {} fuzz={}", new Object[] { this.className, Bytecode.getOpcodeName(accOpcode), Integer.valueOf(this.fuzzFactor) });
/*     */       
/* 165 */       if (findArrayNode(insns, fieldInsn, accOpcode, this.fuzzFactor) == null) {
/* 166 */         log("{} > > > > > failed to locate matching insn", new Object[] { this.className });
/* 167 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 171 */     log("{} > > > > > adding matching insn", new Object[] { this.className });
/*     */     
/* 173 */     return super.addInsn(insns, nodes, insn);
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
/*     */   public static AbstractInsnNode findArrayNode(InsnList insns, FieldInsnNode fieldNode, int opcode, int searchRange) {
/* 191 */     int pos = 0;
/* 192 */     for (Iterator<AbstractInsnNode> iter = insns.iterator(insns.indexOf((AbstractInsnNode)fieldNode) + 1); iter.hasNext(); ) {
/* 193 */       AbstractInsnNode insn = iter.next();
/* 194 */       if (insn.getOpcode() == opcode)
/* 195 */         return insn; 
/* 196 */       if (insn.getOpcode() == 190 && pos == 0)
/* 197 */         return null; 
/* 198 */       if (insn instanceof FieldInsnNode) {
/* 199 */         FieldInsnNode field = (FieldInsnNode)insn;
/* 200 */         if (field.desc.equals(fieldNode.desc) && field.name.equals(fieldNode.name) && field.owner.equals(fieldNode.owner)) {
/* 201 */           return null;
/*     */         }
/*     */       } 
/* 204 */       if (pos++ > searchRange) {
/* 205 */         return null;
/*     */       }
/*     */     } 
/* 208 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\points\BeforeFieldAccess.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */