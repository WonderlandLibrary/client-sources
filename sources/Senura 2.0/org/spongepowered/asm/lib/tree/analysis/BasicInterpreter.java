/*     */ package org.spongepowered.asm.lib.tree.analysis;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.spongepowered.asm.lib.Opcodes;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.FieldInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.IntInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.InvokeDynamicInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.LdcInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.MultiANewArrayInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.TypeInsnNode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicInterpreter
/*     */   extends Interpreter<BasicValue>
/*     */   implements Opcodes
/*     */ {
/*     */   public BasicInterpreter() {
/*  56 */     super(327680);
/*     */   }
/*     */   
/*     */   protected BasicInterpreter(int api) {
/*  60 */     super(api);
/*     */   }
/*     */ 
/*     */   
/*     */   public BasicValue newValue(Type type) {
/*  65 */     if (type == null) {
/*  66 */       return BasicValue.UNINITIALIZED_VALUE;
/*     */     }
/*  68 */     switch (type.getSort()) {
/*     */       case 0:
/*  70 */         return null;
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/*  76 */         return BasicValue.INT_VALUE;
/*     */       case 6:
/*  78 */         return BasicValue.FLOAT_VALUE;
/*     */       case 7:
/*  80 */         return BasicValue.LONG_VALUE;
/*     */       case 8:
/*  82 */         return BasicValue.DOUBLE_VALUE;
/*     */       case 9:
/*     */       case 10:
/*  85 */         return BasicValue.REFERENCE_VALUE;
/*     */     } 
/*  87 */     throw new Error("Internal error");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicValue newOperation(AbstractInsnNode insn) throws AnalyzerException {
/*     */     Object cst;
/*  94 */     switch (insn.getOpcode()) {
/*     */       case 1:
/*  96 */         return newValue(Type.getObjectType("null"));
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/*     */       case 6:
/*     */       case 7:
/*     */       case 8:
/* 104 */         return BasicValue.INT_VALUE;
/*     */       case 9:
/*     */       case 10:
/* 107 */         return BasicValue.LONG_VALUE;
/*     */       case 11:
/*     */       case 12:
/*     */       case 13:
/* 111 */         return BasicValue.FLOAT_VALUE;
/*     */       case 14:
/*     */       case 15:
/* 114 */         return BasicValue.DOUBLE_VALUE;
/*     */       case 16:
/*     */       case 17:
/* 117 */         return BasicValue.INT_VALUE;
/*     */       case 18:
/* 119 */         cst = ((LdcInsnNode)insn).cst;
/* 120 */         if (cst instanceof Integer)
/* 121 */           return BasicValue.INT_VALUE; 
/* 122 */         if (cst instanceof Float)
/* 123 */           return BasicValue.FLOAT_VALUE; 
/* 124 */         if (cst instanceof Long)
/* 125 */           return BasicValue.LONG_VALUE; 
/* 126 */         if (cst instanceof Double)
/* 127 */           return BasicValue.DOUBLE_VALUE; 
/* 128 */         if (cst instanceof String)
/* 129 */           return newValue(Type.getObjectType("java/lang/String")); 
/* 130 */         if (cst instanceof Type) {
/* 131 */           int sort = ((Type)cst).getSort();
/* 132 */           if (sort == 10 || sort == 9)
/* 133 */             return newValue(Type.getObjectType("java/lang/Class")); 
/* 134 */           if (sort == 11) {
/* 135 */             return newValue(
/* 136 */                 Type.getObjectType("java/lang/invoke/MethodType"));
/*     */           }
/* 138 */           throw new IllegalArgumentException("Illegal LDC constant " + cst);
/*     */         } 
/*     */         
/* 141 */         if (cst instanceof org.spongepowered.asm.lib.Handle) {
/* 142 */           return newValue(
/* 143 */               Type.getObjectType("java/lang/invoke/MethodHandle"));
/*     */         }
/* 145 */         throw new IllegalArgumentException("Illegal LDC constant " + cst);
/*     */ 
/*     */       
/*     */       case 168:
/* 149 */         return BasicValue.RETURNADDRESS_VALUE;
/*     */       case 178:
/* 151 */         return newValue(Type.getType(((FieldInsnNode)insn).desc));
/*     */       case 187:
/* 153 */         return newValue(Type.getObjectType(((TypeInsnNode)insn).desc));
/*     */     } 
/* 155 */     throw new Error("Internal error.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicValue copyOperation(AbstractInsnNode insn, BasicValue value) throws AnalyzerException {
/* 162 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public BasicValue unaryOperation(AbstractInsnNode insn, BasicValue value) throws AnalyzerException {
/*     */     String desc;
/* 168 */     switch (insn.getOpcode()) {
/*     */       case 116:
/*     */       case 132:
/*     */       case 136:
/*     */       case 139:
/*     */       case 142:
/*     */       case 145:
/*     */       case 146:
/*     */       case 147:
/* 177 */         return BasicValue.INT_VALUE;
/*     */       case 118:
/*     */       case 134:
/*     */       case 137:
/*     */       case 144:
/* 182 */         return BasicValue.FLOAT_VALUE;
/*     */       case 117:
/*     */       case 133:
/*     */       case 140:
/*     */       case 143:
/* 187 */         return BasicValue.LONG_VALUE;
/*     */       case 119:
/*     */       case 135:
/*     */       case 138:
/*     */       case 141:
/* 192 */         return BasicValue.DOUBLE_VALUE;
/*     */       case 153:
/*     */       case 154:
/*     */       case 155:
/*     */       case 156:
/*     */       case 157:
/*     */       case 158:
/*     */       case 170:
/*     */       case 171:
/*     */       case 172:
/*     */       case 173:
/*     */       case 174:
/*     */       case 175:
/*     */       case 176:
/*     */       case 179:
/* 207 */         return null;
/*     */       case 180:
/* 209 */         return newValue(Type.getType(((FieldInsnNode)insn).desc));
/*     */       case 188:
/* 211 */         switch (((IntInsnNode)insn).operand) {
/*     */           case 4:
/* 213 */             return newValue(Type.getType("[Z"));
/*     */           case 5:
/* 215 */             return newValue(Type.getType("[C"));
/*     */           case 8:
/* 217 */             return newValue(Type.getType("[B"));
/*     */           case 9:
/* 219 */             return newValue(Type.getType("[S"));
/*     */           case 10:
/* 221 */             return newValue(Type.getType("[I"));
/*     */           case 6:
/* 223 */             return newValue(Type.getType("[F"));
/*     */           case 7:
/* 225 */             return newValue(Type.getType("[D"));
/*     */           case 11:
/* 227 */             return newValue(Type.getType("[J"));
/*     */         } 
/* 229 */         throw new AnalyzerException(insn, "Invalid array type");
/*     */       
/*     */       case 189:
/* 232 */         desc = ((TypeInsnNode)insn).desc;
/* 233 */         return newValue(Type.getType("[" + Type.getObjectType(desc)));
/*     */       case 190:
/* 235 */         return BasicValue.INT_VALUE;
/*     */       case 191:
/* 237 */         return null;
/*     */       case 192:
/* 239 */         desc = ((TypeInsnNode)insn).desc;
/* 240 */         return newValue(Type.getObjectType(desc));
/*     */       case 193:
/* 242 */         return BasicValue.INT_VALUE;
/*     */       case 194:
/*     */       case 195:
/*     */       case 198:
/*     */       case 199:
/* 247 */         return null;
/*     */     } 
/* 249 */     throw new Error("Internal error.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicValue binaryOperation(AbstractInsnNode insn, BasicValue value1, BasicValue value2) throws AnalyzerException {
/* 257 */     switch (insn.getOpcode()) {
/*     */       case 46:
/*     */       case 51:
/*     */       case 52:
/*     */       case 53:
/*     */       case 96:
/*     */       case 100:
/*     */       case 104:
/*     */       case 108:
/*     */       case 112:
/*     */       case 120:
/*     */       case 122:
/*     */       case 124:
/*     */       case 126:
/*     */       case 128:
/*     */       case 130:
/* 273 */         return BasicValue.INT_VALUE;
/*     */       case 48:
/*     */       case 98:
/*     */       case 102:
/*     */       case 106:
/*     */       case 110:
/*     */       case 114:
/* 280 */         return BasicValue.FLOAT_VALUE;
/*     */       case 47:
/*     */       case 97:
/*     */       case 101:
/*     */       case 105:
/*     */       case 109:
/*     */       case 113:
/*     */       case 121:
/*     */       case 123:
/*     */       case 125:
/*     */       case 127:
/*     */       case 129:
/*     */       case 131:
/* 293 */         return BasicValue.LONG_VALUE;
/*     */       case 49:
/*     */       case 99:
/*     */       case 103:
/*     */       case 107:
/*     */       case 111:
/*     */       case 115:
/* 300 */         return BasicValue.DOUBLE_VALUE;
/*     */       case 50:
/* 302 */         return BasicValue.REFERENCE_VALUE;
/*     */       case 148:
/*     */       case 149:
/*     */       case 150:
/*     */       case 151:
/*     */       case 152:
/* 308 */         return BasicValue.INT_VALUE;
/*     */       case 159:
/*     */       case 160:
/*     */       case 161:
/*     */       case 162:
/*     */       case 163:
/*     */       case 164:
/*     */       case 165:
/*     */       case 166:
/*     */       case 181:
/* 318 */         return null;
/*     */     } 
/* 320 */     throw new Error("Internal error.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicValue ternaryOperation(AbstractInsnNode insn, BasicValue value1, BasicValue value2, BasicValue value3) throws AnalyzerException {
/* 328 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicValue naryOperation(AbstractInsnNode insn, List<? extends BasicValue> values) throws AnalyzerException {
/* 334 */     int opcode = insn.getOpcode();
/* 335 */     if (opcode == 197)
/* 336 */       return newValue(Type.getType(((MultiANewArrayInsnNode)insn).desc)); 
/* 337 */     if (opcode == 186) {
/* 338 */       return newValue(
/* 339 */           Type.getReturnType(((InvokeDynamicInsnNode)insn).desc));
/*     */     }
/* 341 */     return newValue(Type.getReturnType(((MethodInsnNode)insn).desc));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void returnOperation(AbstractInsnNode insn, BasicValue value, BasicValue expected) throws AnalyzerException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicValue merge(BasicValue v, BasicValue w) {
/* 353 */     if (!v.equals(w)) {
/* 354 */       return BasicValue.UNINITIALIZED_VALUE;
/*     */     }
/* 356 */     return v;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\tree\analysis\BasicInterpreter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */