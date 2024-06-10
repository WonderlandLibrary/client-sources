/*     */ package org.spongepowered.asm.lib.tree.analysis;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.IincInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.InvokeDynamicInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.MultiANewArrayInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.VarInsnNode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Frame<V extends Value>
/*     */ {
/*     */   private V returnValue;
/*     */   private V[] values;
/*     */   private int locals;
/*     */   private int top;
/*     */   
/*     */   public Frame(int nLocals, int nStack) {
/*  88 */     this.values = (V[])new Value[nLocals + nStack];
/*  89 */     this.locals = nLocals;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Frame(Frame<? extends V> src) {
/*  99 */     this(src.locals, src.values.length - src.locals);
/* 100 */     init(src);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Frame<V> init(Frame<? extends V> src) {
/* 111 */     this.returnValue = src.returnValue;
/* 112 */     System.arraycopy(src.values, 0, this.values, 0, this.values.length);
/* 113 */     this.top = src.top;
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReturn(V v) {
/* 125 */     this.returnValue = v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLocals() {
/* 134 */     return this.locals;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxStackSize() {
/* 143 */     return this.values.length - this.locals;
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
/*     */   public V getLocal(int i) throws IndexOutOfBoundsException {
/* 156 */     if (i >= this.locals) {
/* 157 */       throw new IndexOutOfBoundsException("Trying to access an inexistant local variable");
/*     */     }
/*     */     
/* 160 */     return this.values[i];
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
/*     */   public void setLocal(int i, V value) throws IndexOutOfBoundsException {
/* 175 */     if (i >= this.locals) {
/* 176 */       throw new IndexOutOfBoundsException("Trying to access an inexistant local variable " + i);
/*     */     }
/*     */     
/* 179 */     this.values[i] = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStackSize() {
/* 189 */     return this.top;
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
/*     */   public V getStack(int i) throws IndexOutOfBoundsException {
/* 202 */     return this.values[i + this.locals];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearStack() {
/* 209 */     this.top = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V pop() throws IndexOutOfBoundsException {
/* 220 */     if (this.top == 0) {
/* 221 */       throw new IndexOutOfBoundsException("Cannot pop operand off an empty stack.");
/*     */     }
/*     */     
/* 224 */     return this.values[--this.top + this.locals];
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
/*     */   public void push(V value) throws IndexOutOfBoundsException {
/* 236 */     if (this.top + this.locals >= this.values.length) {
/* 237 */       throw new IndexOutOfBoundsException("Insufficient maximum stack size.");
/*     */     }
/*     */     
/* 240 */     this.values[this.top++ + this.locals] = value; } public void execute(AbstractInsnNode insn, Interpreter<V> interpreter) throws AnalyzerException {
/*     */     V value2;
/*     */     V value1;
/*     */     int var;
/*     */     String desc;
/*     */     int i;
/*     */     V value3;
/*     */     List<V> values;
/*     */     int j;
/* 249 */     switch (insn.getOpcode()) {
/*     */       case 0:
/*     */         return;
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/*     */       case 6:
/*     */       case 7:
/*     */       case 8:
/*     */       case 9:
/*     */       case 10:
/*     */       case 11:
/*     */       case 12:
/*     */       case 13:
/*     */       case 14:
/*     */       case 15:
/*     */       case 16:
/*     */       case 17:
/*     */       case 18:
/* 270 */         push(interpreter.newOperation(insn));
/*     */       
/*     */       case 21:
/*     */       case 22:
/*     */       case 23:
/*     */       case 24:
/*     */       case 25:
/* 277 */         push(interpreter.copyOperation(insn, 
/* 278 */               getLocal(((VarInsnNode)insn).var)));
/*     */       
/*     */       case 46:
/*     */       case 47:
/*     */       case 48:
/*     */       case 49:
/*     */       case 50:
/*     */       case 51:
/*     */       case 52:
/*     */       case 53:
/* 288 */         value2 = pop();
/* 289 */         value1 = pop();
/* 290 */         push(interpreter.binaryOperation(insn, value1, value2));
/*     */       
/*     */       case 54:
/*     */       case 55:
/*     */       case 56:
/*     */       case 57:
/*     */       case 58:
/* 297 */         value1 = interpreter.copyOperation(insn, pop());
/* 298 */         var = ((VarInsnNode)insn).var;
/* 299 */         setLocal(var, value1);
/* 300 */         if (value1.getSize() == 2) {
/* 301 */           setLocal(var + 1, interpreter.newValue(null));
/*     */         }
/* 303 */         if (var > 0) {
/* 304 */           Value local = (Value)getLocal(var - 1);
/* 305 */           if (local != null && local.getSize() == 2) {
/* 306 */             setLocal(var - 1, interpreter.newValue(null));
/*     */           }
/*     */         } 
/*     */       
/*     */       case 79:
/*     */       case 80:
/*     */       case 81:
/*     */       case 82:
/*     */       case 83:
/*     */       case 84:
/*     */       case 85:
/*     */       case 86:
/* 318 */         value3 = pop();
/* 319 */         value2 = pop();
/* 320 */         value1 = pop();
/* 321 */         interpreter.ternaryOperation(insn, value1, value2, value3);
/*     */       
/*     */       case 87:
/* 324 */         if (pop().getSize() == 2) {
/* 325 */           throw new AnalyzerException(insn, "Illegal use of POP");
/*     */         }
/*     */       
/*     */       case 88:
/* 329 */         if (pop().getSize() == 1 && 
/* 330 */           pop().getSize() != 1) {
/* 331 */           throw new AnalyzerException(insn, "Illegal use of POP2");
/*     */         }
/*     */ 
/*     */       
/*     */       case 89:
/* 336 */         value1 = pop();
/* 337 */         if (value1.getSize() != 1) {
/* 338 */           throw new AnalyzerException(insn, "Illegal use of DUP");
/*     */         }
/* 340 */         push(value1);
/* 341 */         push(interpreter.copyOperation(insn, value1));
/*     */       
/*     */       case 90:
/* 344 */         value1 = pop();
/* 345 */         value2 = pop();
/* 346 */         if (value1.getSize() != 1 || value2.getSize() != 1) {
/* 347 */           throw new AnalyzerException(insn, "Illegal use of DUP_X1");
/*     */         }
/* 349 */         push(interpreter.copyOperation(insn, value1));
/* 350 */         push(value2);
/* 351 */         push(value1);
/*     */       
/*     */       case 91:
/* 354 */         value1 = pop();
/* 355 */         if (value1.getSize() == 1)
/* 356 */         { value2 = pop();
/* 357 */           if (value2.getSize() == 1)
/* 358 */           { value3 = pop();
/* 359 */             if (value3.getSize() == 1)
/* 360 */             { push(interpreter.copyOperation(insn, value1));
/* 361 */               push(value3);
/* 362 */               push(value2);
/* 363 */               push(value1);
/*     */ 
/*     */               
/*     */                }
/*     */             
/*     */             else
/*     */             
/*     */             { 
/*     */ 
/*     */               
/* 373 */               throw new AnalyzerException(insn, "Illegal use of DUP_X2"); }  } else { push(interpreter.copyOperation(insn, value1)); push(value2); push(value1); }  } else { throw new AnalyzerException(insn, "Illegal use of DUP_X2"); } 
/*     */       case 92:
/* 375 */         value1 = pop();
/* 376 */         if (value1.getSize() == 1)
/* 377 */         { value2 = pop();
/* 378 */           if (value2.getSize() == 1)
/* 379 */           { push(value2);
/* 380 */             push(value1);
/* 381 */             push(interpreter.copyOperation(insn, value2));
/* 382 */             push(interpreter.copyOperation(insn, value1));
/*     */             
/*     */              }
/*     */           
/*     */           else
/*     */           
/*     */           { 
/*     */             
/* 390 */             throw new AnalyzerException(insn, "Illegal use of DUP2"); }  } else { push(value1); push(interpreter.copyOperation(insn, value1)); } 
/*     */       case 93:
/* 392 */         value1 = pop();
/* 393 */         if (value1.getSize() == 1)
/* 394 */         { value2 = pop();
/* 395 */           if (value2.getSize() == 1)
/* 396 */           { value3 = pop();
/* 397 */             if (value3.getSize() == 1)
/* 398 */             { push(interpreter.copyOperation(insn, value2));
/* 399 */               push(interpreter.copyOperation(insn, value1));
/* 400 */               push(value3);
/* 401 */               push(value2);
/* 402 */               push(value1);
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */                }
/*     */             
/*     */             else
/*     */             
/*     */             { 
/*     */ 
/*     */ 
/*     */               
/* 415 */               throw new AnalyzerException(insn, "Illegal use of DUP2_X1"); }  } else { throw new AnalyzerException(insn, "Illegal use of DUP2_X1"); }  } else { value2 = pop(); if (value2.getSize() == 1) { push(interpreter.copyOperation(insn, value1)); push(value2); push(value1); } else { throw new AnalyzerException(insn, "Illegal use of DUP2_X1"); }  } 
/*     */       case 94:
/* 417 */         value1 = pop();
/* 418 */         if (value1.getSize() == 1)
/* 419 */         { value2 = pop();
/* 420 */           if (value2.getSize() == 1)
/* 421 */           { value3 = pop();
/* 422 */             if (value3.getSize() == 1)
/* 423 */             { V value4 = pop();
/* 424 */               if (value4.getSize() == 1)
/* 425 */               { push(interpreter.copyOperation(insn, value2));
/* 426 */                 push(interpreter.copyOperation(insn, value1));
/* 427 */                 push(value4);
/* 428 */                 push(value3);
/* 429 */                 push(value2);
/* 430 */                 push(value1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                  }
/*     */               
/*     */               else
/*     */               
/*     */               { 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 460 */                 throw new AnalyzerException(insn, "Illegal use of DUP2_X2"); }  } else { push(interpreter.copyOperation(insn, value2)); push(interpreter.copyOperation(insn, value1)); push(value3); push(value2); push(value1); }  } else { throw new AnalyzerException(insn, "Illegal use of DUP2_X2"); }  } else { value2 = pop(); if (value2.getSize() == 1) { value3 = pop(); if (value3.getSize() == 1) { push(interpreter.copyOperation(insn, value1)); push(value3); push(value2); push(value1); } else { throw new AnalyzerException(insn, "Illegal use of DUP2_X2"); }  } else { push(interpreter.copyOperation(insn, value1)); push(value2); push(value1); }  } 
/*     */       case 95:
/* 462 */         value2 = pop();
/* 463 */         value1 = pop();
/* 464 */         if (value1.getSize() != 1 || value2.getSize() != 1) {
/* 465 */           throw new AnalyzerException(insn, "Illegal use of SWAP");
/*     */         }
/* 467 */         push(interpreter.copyOperation(insn, value2));
/* 468 */         push(interpreter.copyOperation(insn, value1));
/*     */       
/*     */       case 96:
/*     */       case 97:
/*     */       case 98:
/*     */       case 99:
/*     */       case 100:
/*     */       case 101:
/*     */       case 102:
/*     */       case 103:
/*     */       case 104:
/*     */       case 105:
/*     */       case 106:
/*     */       case 107:
/*     */       case 108:
/*     */       case 109:
/*     */       case 110:
/*     */       case 111:
/*     */       case 112:
/*     */       case 113:
/*     */       case 114:
/*     */       case 115:
/* 490 */         value2 = pop();
/* 491 */         value1 = pop();
/* 492 */         push(interpreter.binaryOperation(insn, value1, value2));
/*     */       
/*     */       case 116:
/*     */       case 117:
/*     */       case 118:
/*     */       case 119:
/* 498 */         push(interpreter.unaryOperation(insn, pop()));
/*     */       
/*     */       case 120:
/*     */       case 121:
/*     */       case 122:
/*     */       case 123:
/*     */       case 124:
/*     */       case 125:
/*     */       case 126:
/*     */       case 127:
/*     */       case 128:
/*     */       case 129:
/*     */       case 130:
/*     */       case 131:
/* 512 */         value2 = pop();
/* 513 */         value1 = pop();
/* 514 */         push(interpreter.binaryOperation(insn, value1, value2));
/*     */       
/*     */       case 132:
/* 517 */         var = ((IincInsnNode)insn).var;
/* 518 */         setLocal(var, interpreter.unaryOperation(insn, getLocal(var)));
/*     */       
/*     */       case 133:
/*     */       case 134:
/*     */       case 135:
/*     */       case 136:
/*     */       case 137:
/*     */       case 138:
/*     */       case 139:
/*     */       case 140:
/*     */       case 141:
/*     */       case 142:
/*     */       case 143:
/*     */       case 144:
/*     */       case 145:
/*     */       case 146:
/*     */       case 147:
/* 535 */         push(interpreter.unaryOperation(insn, pop()));
/*     */       
/*     */       case 148:
/*     */       case 149:
/*     */       case 150:
/*     */       case 151:
/*     */       case 152:
/* 542 */         value2 = pop();
/* 543 */         value1 = pop();
/* 544 */         push(interpreter.binaryOperation(insn, value1, value2));
/*     */       
/*     */       case 153:
/*     */       case 154:
/*     */       case 155:
/*     */       case 156:
/*     */       case 157:
/*     */       case 158:
/* 552 */         interpreter.unaryOperation(insn, pop());
/*     */       
/*     */       case 159:
/*     */       case 160:
/*     */       case 161:
/*     */       case 162:
/*     */       case 163:
/*     */       case 164:
/*     */       case 165:
/*     */       case 166:
/* 562 */         value2 = pop();
/* 563 */         value1 = pop();
/* 564 */         interpreter.binaryOperation(insn, value1, value2);
/*     */       
/*     */       case 167:
/*     */         return;
/*     */       case 168:
/* 569 */         push(interpreter.newOperation(insn));
/*     */       
/*     */       case 169:
/*     */         return;
/*     */       case 170:
/*     */       case 171:
/* 575 */         interpreter.unaryOperation(insn, pop());
/*     */       
/*     */       case 172:
/*     */       case 173:
/*     */       case 174:
/*     */       case 175:
/*     */       case 176:
/* 582 */         value1 = pop();
/* 583 */         interpreter.unaryOperation(insn, value1);
/* 584 */         interpreter.returnOperation(insn, value1, this.returnValue);
/*     */       
/*     */       case 177:
/* 587 */         if (this.returnValue != null) {
/* 588 */           throw new AnalyzerException(insn, "Incompatible return type");
/*     */         }
/*     */       
/*     */       case 178:
/* 592 */         push(interpreter.newOperation(insn));
/*     */       
/*     */       case 179:
/* 595 */         interpreter.unaryOperation(insn, pop());
/*     */       
/*     */       case 180:
/* 598 */         push(interpreter.unaryOperation(insn, pop()));
/*     */       
/*     */       case 181:
/* 601 */         value2 = pop();
/* 602 */         value1 = pop();
/* 603 */         interpreter.binaryOperation(insn, value1, value2);
/*     */       
/*     */       case 182:
/*     */       case 183:
/*     */       case 184:
/*     */       case 185:
/* 609 */         values = new ArrayList<V>();
/* 610 */         desc = ((MethodInsnNode)insn).desc;
/* 611 */         for (j = (Type.getArgumentTypes(desc)).length; j > 0; j--) {
/* 612 */           values.add(0, pop());
/*     */         }
/* 614 */         if (insn.getOpcode() != 184) {
/* 615 */           values.add(0, pop());
/*     */         }
/* 617 */         if (Type.getReturnType(desc) == Type.VOID_TYPE) {
/* 618 */           interpreter.naryOperation(insn, values);
/*     */         } else {
/* 620 */           push(interpreter.naryOperation(insn, values));
/*     */         } 
/*     */ 
/*     */       
/*     */       case 186:
/* 625 */         values = new ArrayList<V>();
/* 626 */         desc = ((InvokeDynamicInsnNode)insn).desc;
/* 627 */         for (j = (Type.getArgumentTypes(desc)).length; j > 0; j--) {
/* 628 */           values.add(0, pop());
/*     */         }
/* 630 */         if (Type.getReturnType(desc) == Type.VOID_TYPE) {
/* 631 */           interpreter.naryOperation(insn, values);
/*     */         } else {
/* 633 */           push(interpreter.naryOperation(insn, values));
/*     */         } 
/*     */ 
/*     */       
/*     */       case 187:
/* 638 */         push(interpreter.newOperation(insn));
/*     */       
/*     */       case 188:
/*     */       case 189:
/*     */       case 190:
/* 643 */         push(interpreter.unaryOperation(insn, pop()));
/*     */       
/*     */       case 191:
/* 646 */         interpreter.unaryOperation(insn, pop());
/*     */       
/*     */       case 192:
/*     */       case 193:
/* 650 */         push(interpreter.unaryOperation(insn, pop()));
/*     */       
/*     */       case 194:
/*     */       case 195:
/* 654 */         interpreter.unaryOperation(insn, pop());
/*     */       
/*     */       case 197:
/* 657 */         values = new ArrayList<V>();
/* 658 */         for (i = ((MultiANewArrayInsnNode)insn).dims; i > 0; i--) {
/* 659 */           values.add(0, pop());
/*     */         }
/* 661 */         push(interpreter.naryOperation(insn, values));
/*     */       
/*     */       case 198:
/*     */       case 199:
/* 665 */         interpreter.unaryOperation(insn, pop());
/*     */     } 
/*     */     
/* 668 */     throw new RuntimeException("Illegal opcode " + insn.getOpcode());
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
/*     */   public boolean merge(Frame<? extends V> frame, Interpreter<V> interpreter) throws AnalyzerException {
/* 686 */     if (this.top != frame.top) {
/* 687 */       throw new AnalyzerException(null, "Incompatible stack heights");
/*     */     }
/* 689 */     boolean changes = false;
/* 690 */     for (int i = 0; i < this.locals + this.top; i++) {
/* 691 */       V v = interpreter.merge(this.values[i], frame.values[i]);
/* 692 */       if (!v.equals(this.values[i])) {
/* 693 */         this.values[i] = v;
/* 694 */         changes = true;
/*     */       } 
/*     */     } 
/* 697 */     return changes;
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
/*     */   public boolean merge(Frame<? extends V> frame, boolean[] access) {
/* 712 */     boolean changes = false;
/* 713 */     for (int i = 0; i < this.locals; i++) {
/* 714 */       if (!access[i] && !this.values[i].equals(frame.values[i])) {
/* 715 */         this.values[i] = frame.values[i];
/* 716 */         changes = true;
/*     */       } 
/*     */     } 
/* 719 */     return changes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 729 */     StringBuilder sb = new StringBuilder(); int i;
/* 730 */     for (i = 0; i < getLocals(); i++) {
/* 731 */       sb.append(getLocal(i));
/*     */     }
/* 733 */     sb.append(' ');
/* 734 */     for (i = 0; i < getStackSize(); i++) {
/* 735 */       sb.append(getStack(i).toString());
/*     */     }
/* 737 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\tree\analysis\Frame.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */