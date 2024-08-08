package org.spongepowered.asm.lib.tree.analysis;

import java.util.ArrayList;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.IincInsnNode;
import org.spongepowered.asm.lib.tree.InvokeDynamicInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MultiANewArrayInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;

public class Frame {
   private Value returnValue;
   private Value[] values;
   private int locals;
   private int top;

   public Frame(int var1, int var2) {
      this.values = (Value[])(new Value[var1 + var2]);
      this.locals = var1;
   }

   public Frame(Frame var1) {
      this(var1.locals, var1.values.length - var1.locals);
      this.init(var1);
   }

   public Frame init(Frame var1) {
      this.returnValue = var1.returnValue;
      System.arraycopy(var1.values, 0, this.values, 0, this.values.length);
      this.top = var1.top;
      return this;
   }

   public void setReturn(Value var1) {
      this.returnValue = var1;
   }

   public int getLocals() {
      return this.locals;
   }

   public int getMaxStackSize() {
      return this.values.length - this.locals;
   }

   public Value getLocal(int var1) throws IndexOutOfBoundsException {
      if (var1 >= this.locals) {
         throw new IndexOutOfBoundsException("Trying to access an inexistant local variable");
      } else {
         return this.values[var1];
      }
   }

   public void setLocal(int var1, Value var2) throws IndexOutOfBoundsException {
      if (var1 >= this.locals) {
         throw new IndexOutOfBoundsException("Trying to access an inexistant local variable " + var1);
      } else {
         this.values[var1] = var2;
      }
   }

   public int getStackSize() {
      return this.top;
   }

   public Value getStack(int var1) throws IndexOutOfBoundsException {
      return this.values[var1 + this.locals];
   }

   public void clearStack() {
      this.top = 0;
   }

   public Value pop() throws IndexOutOfBoundsException {
      if (this.top == 0) {
         throw new IndexOutOfBoundsException("Cannot pop operand off an empty stack.");
      } else {
         return this.values[--this.top + this.locals];
      }
   }

   public void push(Value var1) throws IndexOutOfBoundsException {
      if (this.top + this.locals >= this.values.length) {
         throw new IndexOutOfBoundsException("Insufficient maximum stack size.");
      } else {
         this.values[this.top++ + this.locals] = var1;
      }
   }

   public void execute(AbstractInsnNode var1, Interpreter var2) throws AnalyzerException {
      Value var3;
      Value var4;
      int var5;
      Value var7;
      ArrayList var9;
      int var10;
      String var11;
      switch(var1.getOpcode()) {
      case 0:
      case 167:
      case 169:
         break;
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 16:
      case 17:
      case 18:
         this.push(var2.newOperation(var1));
         break;
      case 19:
      case 20:
      case 26:
      case 27:
      case 28:
      case 29:
      case 30:
      case 31:
      case 32:
      case 33:
      case 34:
      case 35:
      case 36:
      case 37:
      case 38:
      case 39:
      case 40:
      case 41:
      case 42:
      case 43:
      case 44:
      case 45:
      case 59:
      case 60:
      case 61:
      case 62:
      case 63:
      case 64:
      case 65:
      case 66:
      case 67:
      case 68:
      case 69:
      case 70:
      case 71:
      case 72:
      case 73:
      case 74:
      case 75:
      case 76:
      case 77:
      case 78:
      case 196:
      default:
         throw new RuntimeException("Illegal opcode " + var1.getOpcode());
      case 21:
      case 22:
      case 23:
      case 24:
      case 25:
         this.push(var2.copyOperation(var1, this.getLocal(((VarInsnNode)var1).var)));
         break;
      case 46:
      case 47:
      case 48:
      case 49:
      case 50:
      case 51:
      case 52:
      case 53:
         var3 = this.pop();
         var4 = this.pop();
         this.push(var2.binaryOperation(var1, var4, var3));
         break;
      case 54:
      case 55:
      case 56:
      case 57:
      case 58:
         var4 = var2.copyOperation(var1, this.pop());
         var5 = ((VarInsnNode)var1).var;
         this.setLocal(var5, var4);
         if (var4.getSize() == 2) {
            this.setLocal(var5 + 1, var2.newValue((Type)null));
         }

         if (var5 > 0) {
            Value var12 = this.getLocal(var5 - 1);
            if (var12 != null && var12.getSize() == 2) {
               this.setLocal(var5 - 1, var2.newValue((Type)null));
            }
         }
         break;
      case 79:
      case 80:
      case 81:
      case 82:
      case 83:
      case 84:
      case 85:
      case 86:
         var7 = this.pop();
         var3 = this.pop();
         var4 = this.pop();
         var2.ternaryOperation(var1, var4, var3, var7);
         break;
      case 87:
         if (this.pop().getSize() == 2) {
            throw new AnalyzerException(var1, "Illegal use of POP");
         }
         break;
      case 88:
         if (this.pop().getSize() == 1 && this.pop().getSize() != 1) {
            throw new AnalyzerException(var1, "Illegal use of POP2");
         }
         break;
      case 89:
         var4 = this.pop();
         if (var4.getSize() != 1) {
            throw new AnalyzerException(var1, "Illegal use of DUP");
         }

         this.push(var4);
         this.push(var2.copyOperation(var1, var4));
         break;
      case 90:
         var4 = this.pop();
         var3 = this.pop();
         if (var4.getSize() == 1 && var3.getSize() == 1) {
            this.push(var2.copyOperation(var1, var4));
            this.push(var3);
            this.push(var4);
            break;
         }

         throw new AnalyzerException(var1, "Illegal use of DUP_X1");
      case 91:
         var4 = this.pop();
         if (var4.getSize() == 1) {
            var3 = this.pop();
            if (var3.getSize() != 1) {
               this.push(var2.copyOperation(var1, var4));
               this.push(var3);
               this.push(var4);
               break;
            }

            var7 = this.pop();
            if (var7.getSize() == 1) {
               this.push(var2.copyOperation(var1, var4));
               this.push(var7);
               this.push(var3);
               this.push(var4);
               break;
            }
         }

         throw new AnalyzerException(var1, "Illegal use of DUP_X2");
      case 92:
         var4 = this.pop();
         if (var4.getSize() == 1) {
            var3 = this.pop();
            if (var3.getSize() != 1) {
               throw new AnalyzerException(var1, "Illegal use of DUP2");
            }

            this.push(var3);
            this.push(var4);
            this.push(var2.copyOperation(var1, var3));
            this.push(var2.copyOperation(var1, var4));
         } else {
            this.push(var4);
            this.push(var2.copyOperation(var1, var4));
         }
         break;
      case 93:
         var4 = this.pop();
         if (var4.getSize() == 1) {
            var3 = this.pop();
            if (var3.getSize() != 1) {
               throw new AnalyzerException(var1, "Illegal use of DUP2_X1");
            }

            var7 = this.pop();
            if (var7.getSize() != 1) {
               throw new AnalyzerException(var1, "Illegal use of DUP2_X1");
            }

            this.push(var2.copyOperation(var1, var3));
            this.push(var2.copyOperation(var1, var4));
            this.push(var7);
            this.push(var3);
            this.push(var4);
         } else {
            var3 = this.pop();
            if (var3.getSize() != 1) {
               throw new AnalyzerException(var1, "Illegal use of DUP2_X1");
            }

            this.push(var2.copyOperation(var1, var4));
            this.push(var3);
            this.push(var4);
         }
         break;
      case 94:
         var4 = this.pop();
         if (var4.getSize() == 1) {
            var3 = this.pop();
            if (var3.getSize() == 1) {
               var7 = this.pop();
               if (var7.getSize() != 1) {
                  this.push(var2.copyOperation(var1, var3));
                  this.push(var2.copyOperation(var1, var4));
                  this.push(var7);
                  this.push(var3);
                  this.push(var4);
                  break;
               }

               Value var8 = this.pop();
               if (var8.getSize() == 1) {
                  this.push(var2.copyOperation(var1, var3));
                  this.push(var2.copyOperation(var1, var4));
                  this.push(var8);
                  this.push(var7);
                  this.push(var3);
                  this.push(var4);
                  break;
               }
            }
         } else {
            var3 = this.pop();
            if (var3.getSize() != 1) {
               this.push(var2.copyOperation(var1, var4));
               this.push(var3);
               this.push(var4);
               break;
            }

            var7 = this.pop();
            if (var7.getSize() == 1) {
               this.push(var2.copyOperation(var1, var4));
               this.push(var7);
               this.push(var3);
               this.push(var4);
               break;
            }
         }

         throw new AnalyzerException(var1, "Illegal use of DUP2_X2");
      case 95:
         var3 = this.pop();
         var4 = this.pop();
         if (var4.getSize() != 1 || var3.getSize() != 1) {
            throw new AnalyzerException(var1, "Illegal use of SWAP");
         }

         this.push(var2.copyOperation(var1, var3));
         this.push(var2.copyOperation(var1, var4));
         break;
      case 96:
      case 97:
      case 98:
      case 99:
      case 100:
      case 101:
      case 102:
      case 103:
      case 104:
      case 105:
      case 106:
      case 107:
      case 108:
      case 109:
      case 110:
      case 111:
      case 112:
      case 113:
      case 114:
      case 115:
         var3 = this.pop();
         var4 = this.pop();
         this.push(var2.binaryOperation(var1, var4, var3));
         break;
      case 116:
      case 117:
      case 118:
      case 119:
         this.push(var2.unaryOperation(var1, this.pop()));
         break;
      case 120:
      case 121:
      case 122:
      case 123:
      case 124:
      case 125:
      case 126:
      case 127:
      case 128:
      case 129:
      case 130:
      case 131:
         var3 = this.pop();
         var4 = this.pop();
         this.push(var2.binaryOperation(var1, var4, var3));
         break;
      case 132:
         var5 = ((IincInsnNode)var1).var;
         this.setLocal(var5, var2.unaryOperation(var1, this.getLocal(var5)));
         break;
      case 133:
      case 134:
      case 135:
      case 136:
      case 137:
      case 138:
      case 139:
      case 140:
      case 141:
      case 142:
      case 143:
      case 144:
      case 145:
      case 146:
      case 147:
         this.push(var2.unaryOperation(var1, this.pop()));
         break;
      case 148:
      case 149:
      case 150:
      case 151:
      case 152:
         var3 = this.pop();
         var4 = this.pop();
         this.push(var2.binaryOperation(var1, var4, var3));
         break;
      case 153:
      case 154:
      case 155:
      case 156:
      case 157:
      case 158:
         var2.unaryOperation(var1, this.pop());
         break;
      case 159:
      case 160:
      case 161:
      case 162:
      case 163:
      case 164:
      case 165:
      case 166:
         var3 = this.pop();
         var4 = this.pop();
         var2.binaryOperation(var1, var4, var3);
         break;
      case 168:
         this.push(var2.newOperation(var1));
         break;
      case 170:
      case 171:
         var2.unaryOperation(var1, this.pop());
         break;
      case 172:
      case 173:
      case 174:
      case 175:
      case 176:
         var4 = this.pop();
         var2.unaryOperation(var1, var4);
         var2.returnOperation(var1, var4, this.returnValue);
         break;
      case 177:
         if (this.returnValue != null) {
            throw new AnalyzerException(var1, "Incompatible return type");
         }
         break;
      case 178:
         this.push(var2.newOperation(var1));
         break;
      case 179:
         var2.unaryOperation(var1, this.pop());
         break;
      case 180:
         this.push(var2.unaryOperation(var1, this.pop()));
         break;
      case 181:
         var3 = this.pop();
         var4 = this.pop();
         var2.binaryOperation(var1, var4, var3);
         break;
      case 182:
      case 183:
      case 184:
      case 185:
         var9 = new ArrayList();
         var11 = ((MethodInsnNode)var1).desc;

         for(var10 = Type.getArgumentTypes(var11).length; var10 > 0; --var10) {
            var9.add(0, this.pop());
         }

         if (var1.getOpcode() != 184) {
            var9.add(0, this.pop());
         }

         if (Type.getReturnType(var11) == Type.VOID_TYPE) {
            var2.naryOperation(var1, var9);
         } else {
            this.push(var2.naryOperation(var1, var9));
         }
         break;
      case 186:
         var9 = new ArrayList();
         var11 = ((InvokeDynamicInsnNode)var1).desc;

         for(var10 = Type.getArgumentTypes(var11).length; var10 > 0; --var10) {
            var9.add(0, this.pop());
         }

         if (Type.getReturnType(var11) == Type.VOID_TYPE) {
            var2.naryOperation(var1, var9);
         } else {
            this.push(var2.naryOperation(var1, var9));
         }
         break;
      case 187:
         this.push(var2.newOperation(var1));
         break;
      case 188:
      case 189:
      case 190:
         this.push(var2.unaryOperation(var1, this.pop()));
         break;
      case 191:
         var2.unaryOperation(var1, this.pop());
         break;
      case 192:
      case 193:
         this.push(var2.unaryOperation(var1, this.pop()));
         break;
      case 194:
      case 195:
         var2.unaryOperation(var1, this.pop());
         break;
      case 197:
         var9 = new ArrayList();

         for(int var6 = ((MultiANewArrayInsnNode)var1).dims; var6 > 0; --var6) {
            var9.add(0, this.pop());
         }

         this.push(var2.naryOperation(var1, var9));
         break;
      case 198:
      case 199:
         var2.unaryOperation(var1, this.pop());
      }

   }

   public boolean merge(Frame var1, Interpreter var2) throws AnalyzerException {
      if (this.top != var1.top) {
         throw new AnalyzerException((AbstractInsnNode)null, "Incompatible stack heights");
      } else {
         boolean var3 = false;

         for(int var4 = 0; var4 < this.locals + this.top; ++var4) {
            Value var5 = var2.merge(this.values[var4], var1.values[var4]);
            if (!var5.equals(this.values[var4])) {
               this.values[var4] = var5;
               var3 = true;
            }
         }

         return var3;
      }
   }

   public boolean merge(Frame var1, boolean[] var2) {
      boolean var3 = false;

      for(int var4 = 0; var4 < this.locals; ++var4) {
         if (!var2[var4] && !this.values[var4].equals(var1.values[var4])) {
            this.values[var4] = var1.values[var4];
            var3 = true;
         }
      }

      return var3;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();

      int var2;
      for(var2 = 0; var2 < this.getLocals(); ++var2) {
         var1.append(this.getLocal(var2));
      }

      var1.append(' ');

      for(var2 = 0; var2 < this.getStackSize(); ++var2) {
         var1.append(this.getStack(var2).toString());
      }

      return var1.toString();
   }
}
