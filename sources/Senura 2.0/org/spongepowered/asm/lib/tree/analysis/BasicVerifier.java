/*     */ package org.spongepowered.asm.lib.tree.analysis;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.FieldInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.InvokeDynamicInsnNode;
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
/*     */ 
/*     */ 
/*     */ public class BasicVerifier
/*     */   extends BasicInterpreter
/*     */ {
/*     */   public BasicVerifier() {
/*  50 */     super(327680);
/*     */   }
/*     */   
/*     */   protected BasicVerifier(int api) {
/*  54 */     super(api);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicValue copyOperation(AbstractInsnNode insn, BasicValue value) throws AnalyzerException {
/*     */     Value expected;
/*  61 */     switch (insn.getOpcode()) {
/*     */       case 21:
/*     */       case 54:
/*  64 */         expected = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 23:
/*     */       case 56:
/*  68 */         expected = BasicValue.FLOAT_VALUE;
/*     */         break;
/*     */       case 22:
/*     */       case 55:
/*  72 */         expected = BasicValue.LONG_VALUE;
/*     */         break;
/*     */       case 24:
/*     */       case 57:
/*  76 */         expected = BasicValue.DOUBLE_VALUE;
/*     */         break;
/*     */       case 25:
/*  79 */         if (!value.isReference()) {
/*  80 */           throw new AnalyzerException(insn, null, "an object reference", value);
/*     */         }
/*     */         
/*  83 */         return value;
/*     */       case 58:
/*  85 */         if (!value.isReference() && 
/*  86 */           !BasicValue.RETURNADDRESS_VALUE.equals(value)) {
/*  87 */           throw new AnalyzerException(insn, null, "an object reference or a return address", value);
/*     */         }
/*     */         
/*  90 */         return value;
/*     */       default:
/*  92 */         return value;
/*     */     } 
/*  94 */     if (!expected.equals(value)) {
/*  95 */       throw new AnalyzerException(insn, null, expected, value);
/*     */     }
/*  97 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicValue unaryOperation(AbstractInsnNode insn, BasicValue value) throws AnalyzerException {
/*     */     BasicValue expected;
/* 104 */     switch (insn.getOpcode()) {
/*     */       case 116:
/*     */       case 132:
/*     */       case 133:
/*     */       case 134:
/*     */       case 135:
/*     */       case 145:
/*     */       case 146:
/*     */       case 147:
/*     */       case 153:
/*     */       case 154:
/*     */       case 155:
/*     */       case 156:
/*     */       case 157:
/*     */       case 158:
/*     */       case 170:
/*     */       case 171:
/*     */       case 172:
/*     */       case 188:
/*     */       case 189:
/* 124 */         expected = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 118:
/*     */       case 139:
/*     */       case 140:
/*     */       case 141:
/*     */       case 174:
/* 131 */         expected = BasicValue.FLOAT_VALUE;
/*     */         break;
/*     */       case 117:
/*     */       case 136:
/*     */       case 137:
/*     */       case 138:
/*     */       case 173:
/* 138 */         expected = BasicValue.LONG_VALUE;
/*     */         break;
/*     */       case 119:
/*     */       case 142:
/*     */       case 143:
/*     */       case 144:
/*     */       case 175:
/* 145 */         expected = BasicValue.DOUBLE_VALUE;
/*     */         break;
/*     */       case 180:
/* 148 */         expected = newValue(
/* 149 */             Type.getObjectType(((FieldInsnNode)insn).owner));
/*     */         break;
/*     */       case 192:
/* 152 */         if (!value.isReference()) {
/* 153 */           throw new AnalyzerException(insn, null, "an object reference", value);
/*     */         }
/*     */         
/* 156 */         return super.unaryOperation(insn, value);
/*     */       case 190:
/* 158 */         if (!isArrayValue(value)) {
/* 159 */           throw new AnalyzerException(insn, null, "an array reference", value);
/*     */         }
/*     */         
/* 162 */         return super.unaryOperation(insn, value);
/*     */       case 176:
/*     */       case 191:
/*     */       case 193:
/*     */       case 194:
/*     */       case 195:
/*     */       case 198:
/*     */       case 199:
/* 170 */         if (!value.isReference()) {
/* 171 */           throw new AnalyzerException(insn, null, "an object reference", value);
/*     */         }
/*     */         
/* 174 */         return super.unaryOperation(insn, value);
/*     */       case 179:
/* 176 */         expected = newValue(Type.getType(((FieldInsnNode)insn).desc));
/*     */         break;
/*     */       default:
/* 179 */         throw new Error("Internal error.");
/*     */     } 
/* 181 */     if (!isSubTypeOf(value, expected)) {
/* 182 */       throw new AnalyzerException(insn, null, expected, value);
/*     */     }
/* 184 */     return super.unaryOperation(insn, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicValue binaryOperation(AbstractInsnNode insn, BasicValue value1, BasicValue value2) throws AnalyzerException {
/*     */     BasicValue expected1;
/*     */     BasicValue expected2;
/*     */     FieldInsnNode fin;
/* 193 */     switch (insn.getOpcode()) {
/*     */       case 46:
/* 195 */         expected1 = newValue(Type.getType("[I"));
/* 196 */         expected2 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 51:
/* 199 */         if (isSubTypeOf(value1, newValue(Type.getType("[Z")))) {
/* 200 */           expected1 = newValue(Type.getType("[Z"));
/*     */         } else {
/* 202 */           expected1 = newValue(Type.getType("[B"));
/*     */         } 
/* 204 */         expected2 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 52:
/* 207 */         expected1 = newValue(Type.getType("[C"));
/* 208 */         expected2 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 53:
/* 211 */         expected1 = newValue(Type.getType("[S"));
/* 212 */         expected2 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 47:
/* 215 */         expected1 = newValue(Type.getType("[J"));
/* 216 */         expected2 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 48:
/* 219 */         expected1 = newValue(Type.getType("[F"));
/* 220 */         expected2 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 49:
/* 223 */         expected1 = newValue(Type.getType("[D"));
/* 224 */         expected2 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 50:
/* 227 */         expected1 = newValue(Type.getType("[Ljava/lang/Object;"));
/* 228 */         expected2 = BasicValue.INT_VALUE;
/*     */         break;
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
/*     */       case 159:
/*     */       case 160:
/*     */       case 161:
/*     */       case 162:
/*     */       case 163:
/*     */       case 164:
/* 247 */         expected1 = BasicValue.INT_VALUE;
/* 248 */         expected2 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 98:
/*     */       case 102:
/*     */       case 106:
/*     */       case 110:
/*     */       case 114:
/*     */       case 149:
/*     */       case 150:
/* 257 */         expected1 = BasicValue.FLOAT_VALUE;
/* 258 */         expected2 = BasicValue.FLOAT_VALUE;
/*     */         break;
/*     */       case 97:
/*     */       case 101:
/*     */       case 105:
/*     */       case 109:
/*     */       case 113:
/*     */       case 127:
/*     */       case 129:
/*     */       case 131:
/*     */       case 148:
/* 269 */         expected1 = BasicValue.LONG_VALUE;
/* 270 */         expected2 = BasicValue.LONG_VALUE;
/*     */         break;
/*     */       case 121:
/*     */       case 123:
/*     */       case 125:
/* 275 */         expected1 = BasicValue.LONG_VALUE;
/* 276 */         expected2 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 99:
/*     */       case 103:
/*     */       case 107:
/*     */       case 111:
/*     */       case 115:
/*     */       case 151:
/*     */       case 152:
/* 285 */         expected1 = BasicValue.DOUBLE_VALUE;
/* 286 */         expected2 = BasicValue.DOUBLE_VALUE;
/*     */         break;
/*     */       case 165:
/*     */       case 166:
/* 290 */         expected1 = BasicValue.REFERENCE_VALUE;
/* 291 */         expected2 = BasicValue.REFERENCE_VALUE;
/*     */         break;
/*     */       case 181:
/* 294 */         fin = (FieldInsnNode)insn;
/* 295 */         expected1 = newValue(Type.getObjectType(fin.owner));
/* 296 */         expected2 = newValue(Type.getType(fin.desc));
/*     */         break;
/*     */       default:
/* 299 */         throw new Error("Internal error.");
/*     */     } 
/* 301 */     if (!isSubTypeOf(value1, expected1)) {
/* 302 */       throw new AnalyzerException(insn, "First argument", expected1, value1);
/*     */     }
/* 304 */     if (!isSubTypeOf(value2, expected2)) {
/* 305 */       throw new AnalyzerException(insn, "Second argument", expected2, value2);
/*     */     }
/*     */     
/* 308 */     if (insn.getOpcode() == 50) {
/* 309 */       return getElementValue(value1);
/*     */     }
/* 311 */     return super.binaryOperation(insn, value1, value2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicValue ternaryOperation(AbstractInsnNode insn, BasicValue value1, BasicValue value2, BasicValue value3) throws AnalyzerException {
/*     */     BasicValue expected1;
/*     */     BasicValue expected3;
/* 321 */     switch (insn.getOpcode()) {
/*     */       case 79:
/* 323 */         expected1 = newValue(Type.getType("[I"));
/* 324 */         expected3 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 84:
/* 327 */         if (isSubTypeOf(value1, newValue(Type.getType("[Z")))) {
/* 328 */           expected1 = newValue(Type.getType("[Z"));
/*     */         } else {
/* 330 */           expected1 = newValue(Type.getType("[B"));
/*     */         } 
/* 332 */         expected3 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 85:
/* 335 */         expected1 = newValue(Type.getType("[C"));
/* 336 */         expected3 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 86:
/* 339 */         expected1 = newValue(Type.getType("[S"));
/* 340 */         expected3 = BasicValue.INT_VALUE;
/*     */         break;
/*     */       case 80:
/* 343 */         expected1 = newValue(Type.getType("[J"));
/* 344 */         expected3 = BasicValue.LONG_VALUE;
/*     */         break;
/*     */       case 81:
/* 347 */         expected1 = newValue(Type.getType("[F"));
/* 348 */         expected3 = BasicValue.FLOAT_VALUE;
/*     */         break;
/*     */       case 82:
/* 351 */         expected1 = newValue(Type.getType("[D"));
/* 352 */         expected3 = BasicValue.DOUBLE_VALUE;
/*     */         break;
/*     */       case 83:
/* 355 */         expected1 = value1;
/* 356 */         expected3 = BasicValue.REFERENCE_VALUE;
/*     */         break;
/*     */       default:
/* 359 */         throw new Error("Internal error.");
/*     */     } 
/* 361 */     if (!isSubTypeOf(value1, expected1)) {
/* 362 */       throw new AnalyzerException(insn, "First argument", "a " + expected1 + " array reference", value1);
/*     */     }
/* 364 */     if (!BasicValue.INT_VALUE.equals(value2)) {
/* 365 */       throw new AnalyzerException(insn, "Second argument", BasicValue.INT_VALUE, value2);
/*     */     }
/* 367 */     if (!isSubTypeOf(value3, expected3)) {
/* 368 */       throw new AnalyzerException(insn, "Third argument", expected3, value3);
/*     */     }
/*     */     
/* 371 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicValue naryOperation(AbstractInsnNode insn, List<? extends BasicValue> values) throws AnalyzerException {
/* 377 */     int opcode = insn.getOpcode();
/* 378 */     if (opcode == 197) {
/* 379 */       for (int i = 0; i < values.size(); i++) {
/* 380 */         if (!BasicValue.INT_VALUE.equals(values.get(i))) {
/* 381 */           throw new AnalyzerException(insn, null, BasicValue.INT_VALUE, (Value)values
/* 382 */               .get(i));
/*     */         }
/*     */       } 
/*     */     } else {
/* 386 */       int i = 0;
/* 387 */       int j = 0;
/* 388 */       if (opcode != 184 && opcode != 186) {
/* 389 */         Type owner = Type.getObjectType(((MethodInsnNode)insn).owner);
/* 390 */         if (!isSubTypeOf(values.get(i++), newValue(owner))) {
/* 391 */           throw new AnalyzerException(insn, "Method owner", 
/* 392 */               newValue(owner), (Value)values.get(0));
/*     */         }
/*     */       } 
/* 395 */       String desc = (opcode == 186) ? ((InvokeDynamicInsnNode)insn).desc : ((MethodInsnNode)insn).desc;
/*     */       
/* 397 */       Type[] args = Type.getArgumentTypes(desc);
/* 398 */       while (i < values.size()) {
/* 399 */         BasicValue expected = newValue(args[j++]);
/* 400 */         BasicValue encountered = values.get(i++);
/* 401 */         if (!isSubTypeOf(encountered, expected)) {
/* 402 */           throw new AnalyzerException(insn, "Argument " + j, expected, encountered);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 407 */     return super.naryOperation(insn, values);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void returnOperation(AbstractInsnNode insn, BasicValue value, BasicValue expected) throws AnalyzerException {
/* 414 */     if (!isSubTypeOf(value, expected)) {
/* 415 */       throw new AnalyzerException(insn, "Incompatible return type", expected, value);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isArrayValue(BasicValue value) {
/* 421 */     return value.isReference();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BasicValue getElementValue(BasicValue objectArrayValue) throws AnalyzerException {
/* 426 */     return BasicValue.REFERENCE_VALUE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isSubTypeOf(BasicValue value, BasicValue expected) {
/* 431 */     return value.equals(expected);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\tree\analysis\BasicVerifier.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */