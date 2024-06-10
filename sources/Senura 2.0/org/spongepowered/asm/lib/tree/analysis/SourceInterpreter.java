/*     */ package org.spongepowered.asm.lib.tree.analysis;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.spongepowered.asm.lib.Opcodes;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.FieldInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.InvokeDynamicInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.LdcInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodInsnNode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SourceInterpreter
/*     */   extends Interpreter<SourceValue>
/*     */   implements Opcodes
/*     */ {
/*     */   public SourceInterpreter() {
/*  53 */     super(327680);
/*     */   }
/*     */   
/*     */   protected SourceInterpreter(int api) {
/*  57 */     super(api);
/*     */   }
/*     */ 
/*     */   
/*     */   public SourceValue newValue(Type type) {
/*  62 */     if (type == Type.VOID_TYPE) {
/*  63 */       return null;
/*     */     }
/*  65 */     return new SourceValue((type == null) ? 1 : type.getSize());
/*     */   }
/*     */ 
/*     */   
/*     */   public SourceValue newOperation(AbstractInsnNode insn) {
/*     */     Object cst;
/*  71 */     switch (insn.getOpcode())
/*     */     { case 9:
/*     */       case 10:
/*     */       case 14:
/*     */       case 15:
/*  76 */         size = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  88 */         return new SourceValue(size, insn);case 18: cst = ((LdcInsnNode)insn).cst; size = (cst instanceof Long || cst instanceof Double) ? 2 : 1; return new SourceValue(size, insn);case 178: size = Type.getType(((FieldInsnNode)insn).desc).getSize(); return new SourceValue(size, insn); }  int size = 1; return new SourceValue(size, insn);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SourceValue copyOperation(AbstractInsnNode insn, SourceValue value) {
/*  94 */     return new SourceValue(value.getSize(), insn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SourceValue unaryOperation(AbstractInsnNode insn, SourceValue value) {
/* 101 */     switch (insn.getOpcode())
/*     */     { case 117:
/*     */       case 119:
/*     */       case 133:
/*     */       case 135:
/*     */       case 138:
/*     */       case 140:
/*     */       case 141:
/*     */       case 143:
/* 110 */         size = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 118 */         return new SourceValue(size, insn);case 180: size = Type.getType(((FieldInsnNode)insn).desc).getSize(); return new SourceValue(size, insn); }  int size = 1; return new SourceValue(size, insn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SourceValue binaryOperation(AbstractInsnNode insn, SourceValue value1, SourceValue value2) {
/* 125 */     switch (insn.getOpcode())
/*     */     { case 47:
/*     */       case 49:
/*     */       case 97:
/*     */       case 99:
/*     */       case 101:
/*     */       case 103:
/*     */       case 105:
/*     */       case 107:
/*     */       case 109:
/*     */       case 111:
/*     */       case 113:
/*     */       case 115:
/*     */       case 121:
/*     */       case 123:
/*     */       case 125:
/*     */       case 127:
/*     */       case 129:
/*     */       case 131:
/* 144 */         size = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 149 */         return new SourceValue(size, insn); }  int size = 1; return new SourceValue(size, insn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SourceValue ternaryOperation(AbstractInsnNode insn, SourceValue value1, SourceValue value2, SourceValue value3) {
/* 156 */     return new SourceValue(1, insn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SourceValue naryOperation(AbstractInsnNode insn, List<? extends SourceValue> values) {
/* 163 */     int size, opcode = insn.getOpcode();
/* 164 */     if (opcode == 197) {
/* 165 */       size = 1;
/*     */     } else {
/* 167 */       String desc = (opcode == 186) ? ((InvokeDynamicInsnNode)insn).desc : ((MethodInsnNode)insn).desc;
/*     */       
/* 169 */       size = Type.getReturnType(desc).getSize();
/*     */     } 
/* 171 */     return new SourceValue(size, insn);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void returnOperation(AbstractInsnNode insn, SourceValue value, SourceValue expected) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public SourceValue merge(SourceValue d, SourceValue w) {
/* 181 */     if (d.insns instanceof SmallSet && w.insns instanceof SmallSet) {
/*     */       
/* 183 */       Set<AbstractInsnNode> s = ((SmallSet<AbstractInsnNode>)d.insns).union((SmallSet<AbstractInsnNode>)w.insns);
/* 184 */       if (s == d.insns && d.size == w.size) {
/* 185 */         return d;
/*     */       }
/* 187 */       return new SourceValue(Math.min(d.size, w.size), s);
/*     */     } 
/*     */     
/* 190 */     if (d.size != w.size || !d.insns.containsAll(w.insns)) {
/* 191 */       HashSet<AbstractInsnNode> s = new HashSet<AbstractInsnNode>();
/* 192 */       s.addAll(d.insns);
/* 193 */       s.addAll(w.insns);
/* 194 */       return new SourceValue(Math.min(d.size, w.size), s);
/*     */     } 
/* 196 */     return d;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\tree\analysis\SourceInterpreter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */