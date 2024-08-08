package org.spongepowered.asm.lib;

class Frame {
   static final int DIM = -268435456;
   static final int ARRAY_OF = 268435456;
   static final int ELEMENT_OF = -268435456;
   static final int KIND = 251658240;
   static final int TOP_IF_LONG_OR_DOUBLE = 8388608;
   static final int VALUE = 8388607;
   static final int BASE_KIND = 267386880;
   static final int BASE_VALUE = 1048575;
   static final int BASE = 16777216;
   static final int OBJECT = 24117248;
   static final int UNINITIALIZED = 25165824;
   private static final int LOCAL = 33554432;
   private static final int STACK = 50331648;
   static final int TOP = 16777216;
   static final int BOOLEAN = 16777225;
   static final int BYTE = 16777226;
   static final int CHAR = 16777227;
   static final int SHORT = 16777228;
   static final int INTEGER = 16777217;
   static final int FLOAT = 16777218;
   static final int DOUBLE = 16777219;
   static final int LONG = 16777220;
   static final int NULL = 16777221;
   static final int UNINITIALIZED_THIS = 16777222;
   static final int[] SIZE;
   Label owner;
   int[] inputLocals;
   int[] inputStack;
   private int[] outputLocals;
   private int[] outputStack;
   int outputStackTop;
   private int initializationCount;
   private int[] initializations;

   final void set(ClassWriter var1, int var2, Object[] var3, int var4, Object[] var5) {
      for(int var6 = convert(var1, var2, var3, this.inputLocals); var6 < var3.length; this.inputLocals[var6++] = 16777216) {
      }

      int var7 = 0;

      for(int var8 = 0; var8 < var4; ++var8) {
         if (var5[var8] == Opcodes.LONG || var5[var8] == Opcodes.DOUBLE) {
            ++var7;
         }
      }

      this.inputStack = new int[var4 + var7];
      convert(var1, var4, var5, this.inputStack);
      this.outputStackTop = 0;
      this.initializationCount = 0;
   }

   private static int convert(ClassWriter var0, int var1, Object[] var2, int[] var3) {
      int var4 = 0;

      for(int var5 = 0; var5 < var1; ++var5) {
         if (var2[var5] instanceof Integer) {
            var3[var4++] = 16777216 | (Integer)var2[var5];
            if (var2[var5] == Opcodes.LONG || var2[var5] == Opcodes.DOUBLE) {
               var3[var4++] = 16777216;
            }
         } else if (var2[var5] instanceof String) {
            var3[var4++] = type(var0, Type.getObjectType((String)var2[var5]).getDescriptor());
         } else {
            var3[var4++] = 25165824 | var0.addUninitializedType("", ((Label)var2[var5]).position);
         }
      }

      return var4;
   }

   final void set(Frame var1) {
      this.inputLocals = var1.inputLocals;
      this.inputStack = var1.inputStack;
      this.outputLocals = var1.outputLocals;
      this.outputStack = var1.outputStack;
      this.outputStackTop = var1.outputStackTop;
      this.initializationCount = var1.initializationCount;
      this.initializations = var1.initializations;
   }

   private int get(int var1) {
      if (this.outputLocals != null && var1 < this.outputLocals.length) {
         int var2 = this.outputLocals[var1];
         if (var2 == 0) {
            var2 = this.outputLocals[var1] = 33554432 | var1;
         }

         return var2;
      } else {
         return 33554432 | var1;
      }
   }

   private void set(int var1, int var2) {
      if (this.outputLocals == null) {
         this.outputLocals = new int[10];
      }

      int var3 = this.outputLocals.length;
      if (var1 >= var3) {
         int[] var4 = new int[Math.max(var1 + 1, 2 * var3)];
         System.arraycopy(this.outputLocals, 0, var4, 0, var3);
         this.outputLocals = var4;
      }

      this.outputLocals[var1] = var2;
   }

   private void push(int var1) {
      if (this.outputStack == null) {
         this.outputStack = new int[10];
      }

      int var2 = this.outputStack.length;
      if (this.outputStackTop >= var2) {
         int[] var3 = new int[Math.max(this.outputStackTop + 1, 2 * var2)];
         System.arraycopy(this.outputStack, 0, var3, 0, var2);
         this.outputStack = var3;
      }

      this.outputStack[this.outputStackTop++] = var1;
      int var4 = this.owner.inputStackTop + this.outputStackTop;
      if (var4 > this.owner.outputStackMax) {
         this.owner.outputStackMax = var4;
      }

   }

   private void push(ClassWriter var1, String var2) {
      int var3 = type(var1, var2);
      if (var3 != 0) {
         this.push(var3);
         if (var3 == 16777220 || var3 == 16777219) {
            this.push(16777216);
         }
      }

   }

   private static int type(ClassWriter var0, String var1) {
      int var2 = var1.charAt(0) == '(' ? var1.indexOf(41) + 1 : 0;
      String var3;
      switch(var1.charAt(var2)) {
      case 'B':
      case 'C':
      case 'I':
      case 'S':
      case 'Z':
         return 16777217;
      case 'D':
         return 16777219;
      case 'E':
      case 'G':
      case 'H':
      case 'K':
      case 'M':
      case 'N':
      case 'O':
      case 'P':
      case 'Q':
      case 'R':
      case 'T':
      case 'U':
      case 'W':
      case 'X':
      case 'Y':
      default:
         int var4;
         for(var4 = var2 + 1; var1.charAt(var4) == '['; ++var4) {
         }

         int var5;
         switch(var1.charAt(var4)) {
         case 'B':
            var5 = 16777226;
            break;
         case 'C':
            var5 = 16777227;
            break;
         case 'D':
            var5 = 16777219;
            break;
         case 'E':
         case 'G':
         case 'H':
         case 'K':
         case 'L':
         case 'M':
         case 'N':
         case 'O':
         case 'P':
         case 'Q':
         case 'R':
         case 'T':
         case 'U':
         case 'V':
         case 'W':
         case 'X':
         case 'Y':
         default:
            var3 = var1.substring(var4 + 1, var1.length() - 1);
            var5 = 24117248 | var0.addType(var3);
            break;
         case 'F':
            var5 = 16777218;
            break;
         case 'I':
            var5 = 16777217;
            break;
         case 'J':
            var5 = 16777220;
            break;
         case 'S':
            var5 = 16777228;
            break;
         case 'Z':
            var5 = 16777225;
         }

         return var4 - var2 << 28 | var5;
      case 'F':
         return 16777218;
      case 'J':
         return 16777220;
      case 'L':
         var3 = var1.substring(var2 + 1, var1.length() - 1);
         return 24117248 | var0.addType(var3);
      case 'V':
         return 0;
      }
   }

   private int pop() {
      return this.outputStackTop > 0 ? this.outputStack[--this.outputStackTop] : 50331648 | -(--this.owner.inputStackTop);
   }

   private void pop(int var1) {
      if (this.outputStackTop >= var1) {
         this.outputStackTop -= var1;
      } else {
         Label var10000 = this.owner;
         var10000.inputStackTop -= var1 - this.outputStackTop;
         this.outputStackTop = 0;
      }

   }

   private void pop(String var1) {
      char var2 = var1.charAt(0);
      if (var2 == '(') {
         this.pop((Type.getArgumentsAndReturnSizes(var1) >> 2) - 1);
      } else if (var2 != 'J' && var2 != 'D') {
         this.pop(1);
      } else {
         this.pop(2);
      }

   }

   private void init(int var1) {
      if (this.initializations == null) {
         this.initializations = new int[2];
      }

      int var2 = this.initializations.length;
      if (this.initializationCount >= var2) {
         int[] var3 = new int[Math.max(this.initializationCount + 1, 2 * var2)];
         System.arraycopy(this.initializations, 0, var3, 0, var2);
         this.initializations = var3;
      }

      this.initializations[this.initializationCount++] = var1;
   }

   private int init(ClassWriter var1, int var2) {
      int var3;
      if (var2 == 16777222) {
         var3 = 24117248 | var1.addType(var1.thisName);
      } else {
         if ((var2 & -1048576) != 25165824) {
            return var2;
         }

         String var4 = var1.typeTable[var2 & 1048575].strVal1;
         var3 = 24117248 | var1.addType(var4);
      }

      for(int var8 = 0; var8 < this.initializationCount; ++var8) {
         int var5 = this.initializations[var8];
         int var6 = var5 & -268435456;
         int var7 = var5 & 251658240;
         if (var7 == 33554432) {
            var5 = var6 + this.inputLocals[var5 & 8388607];
         } else if (var7 == 50331648) {
            var5 = var6 + this.inputStack[this.inputStack.length - (var5 & 8388607)];
         }

         if (var2 == var5) {
            return var3;
         }
      }

      return var2;
   }

   final void initInputFrame(ClassWriter var1, int var2, Type[] var3, int var4) {
      this.inputLocals = new int[var4];
      this.inputStack = new int[0];
      int var5 = 0;
      if ((var2 & 8) == 0) {
         if ((var2 & 524288) == 0) {
            this.inputLocals[var5++] = 24117248 | var1.addType(var1.thisName);
         } else {
            this.inputLocals[var5++] = 16777222;
         }
      }

      for(int var6 = 0; var6 < var3.length; ++var6) {
         int var7 = type(var1, var3[var6].getDescriptor());
         this.inputLocals[var5++] = var7;
         if (var7 == 16777220 || var7 == 16777219) {
            this.inputLocals[var5++] = 16777216;
         }
      }

      while(var5 < var4) {
         this.inputLocals[var5++] = 16777216;
      }

   }

   void execute(int var1, int var2, ClassWriter var3, Item var4) {
      int var5;
      int var6;
      int var7;
      String var9;
      switch(var1) {
      case 0:
      case 116:
      case 117:
      case 118:
      case 119:
      case 145:
      case 146:
      case 147:
      case 167:
      case 177:
         break;
      case 1:
         this.push(16777221);
         break;
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 16:
      case 17:
      case 21:
         this.push(16777217);
         break;
      case 9:
      case 10:
      case 22:
         this.push(16777220);
         this.push(16777216);
         break;
      case 11:
      case 12:
      case 13:
      case 23:
         this.push(16777218);
         break;
      case 14:
      case 15:
      case 24:
         this.push(16777219);
         this.push(16777216);
         break;
      case 18:
         switch(var4.type) {
         case 3:
            this.push(16777217);
            return;
         case 4:
            this.push(16777218);
            return;
         case 5:
            this.push(16777220);
            this.push(16777216);
            return;
         case 6:
            this.push(16777219);
            this.push(16777216);
            return;
         case 7:
            this.push(24117248 | var3.addType("java/lang/Class"));
            return;
         case 8:
            this.push(24117248 | var3.addType("java/lang/String"));
            return;
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         default:
            this.push(24117248 | var3.addType("java/lang/invoke/MethodHandle"));
            return;
         case 16:
            this.push(24117248 | var3.addType("java/lang/invoke/MethodType"));
            return;
         }
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
      case 197:
      default:
         this.pop(var2);
         this.push(var3, var4.strVal1);
         break;
      case 25:
         this.push(this.get(var2));
         break;
      case 46:
      case 51:
      case 52:
      case 53:
         this.pop(2);
         this.push(16777217);
         break;
      case 47:
      case 143:
         this.pop(2);
         this.push(16777220);
         this.push(16777216);
         break;
      case 48:
         this.pop(2);
         this.push(16777218);
         break;
      case 49:
      case 138:
         this.pop(2);
         this.push(16777219);
         this.push(16777216);
         break;
      case 50:
         this.pop(1);
         var5 = this.pop();
         this.push(-268435456 + var5);
         break;
      case 54:
      case 56:
      case 58:
         var5 = this.pop();
         this.set(var2, var5);
         if (var2 > 0) {
            var6 = this.get(var2 - 1);
            if (var6 != 16777220 && var6 != 16777219) {
               if ((var6 & 251658240) != 16777216) {
                  this.set(var2 - 1, var6 | 8388608);
               }
            } else {
               this.set(var2 - 1, 16777216);
            }
         }
         break;
      case 55:
      case 57:
         this.pop(1);
         var5 = this.pop();
         this.set(var2, var5);
         this.set(var2 + 1, 16777216);
         if (var2 > 0) {
            var6 = this.get(var2 - 1);
            if (var6 != 16777220 && var6 != 16777219) {
               if ((var6 & 251658240) != 16777216) {
                  this.set(var2 - 1, var6 | 8388608);
               }
            } else {
               this.set(var2 - 1, 16777216);
            }
         }
         break;
      case 79:
      case 81:
      case 83:
      case 84:
      case 85:
      case 86:
         this.pop(3);
         break;
      case 80:
      case 82:
         this.pop(4);
         break;
      case 87:
      case 153:
      case 154:
      case 155:
      case 156:
      case 157:
      case 158:
      case 170:
      case 171:
      case 172:
      case 174:
      case 176:
      case 191:
      case 194:
      case 195:
      case 198:
      case 199:
         this.pop(1);
         break;
      case 88:
      case 159:
      case 160:
      case 161:
      case 162:
      case 163:
      case 164:
      case 165:
      case 166:
      case 173:
      case 175:
         this.pop(2);
         break;
      case 89:
         var5 = this.pop();
         this.push(var5);
         this.push(var5);
         break;
      case 90:
         var5 = this.pop();
         var6 = this.pop();
         this.push(var5);
         this.push(var6);
         this.push(var5);
         break;
      case 91:
         var5 = this.pop();
         var6 = this.pop();
         var7 = this.pop();
         this.push(var5);
         this.push(var7);
         this.push(var6);
         this.push(var5);
         break;
      case 92:
         var5 = this.pop();
         var6 = this.pop();
         this.push(var6);
         this.push(var5);
         this.push(var6);
         this.push(var5);
         break;
      case 93:
         var5 = this.pop();
         var6 = this.pop();
         var7 = this.pop();
         this.push(var6);
         this.push(var5);
         this.push(var7);
         this.push(var6);
         this.push(var5);
         break;
      case 94:
         var5 = this.pop();
         var6 = this.pop();
         var7 = this.pop();
         int var8 = this.pop();
         this.push(var6);
         this.push(var5);
         this.push(var8);
         this.push(var7);
         this.push(var6);
         this.push(var5);
         break;
      case 95:
         var5 = this.pop();
         var6 = this.pop();
         this.push(var5);
         this.push(var6);
         break;
      case 96:
      case 100:
      case 104:
      case 108:
      case 112:
      case 120:
      case 122:
      case 124:
      case 126:
      case 128:
      case 130:
      case 136:
      case 142:
      case 149:
      case 150:
         this.pop(2);
         this.push(16777217);
         break;
      case 97:
      case 101:
      case 105:
      case 109:
      case 113:
      case 127:
      case 129:
      case 131:
         this.pop(4);
         this.push(16777220);
         this.push(16777216);
         break;
      case 98:
      case 102:
      case 106:
      case 110:
      case 114:
      case 137:
      case 144:
         this.pop(2);
         this.push(16777218);
         break;
      case 99:
      case 103:
      case 107:
      case 111:
      case 115:
         this.pop(4);
         this.push(16777219);
         this.push(16777216);
         break;
      case 121:
      case 123:
      case 125:
         this.pop(3);
         this.push(16777220);
         this.push(16777216);
         break;
      case 132:
         this.set(var2, 16777217);
         break;
      case 133:
      case 140:
         this.pop(1);
         this.push(16777220);
         this.push(16777216);
         break;
      case 134:
         this.pop(1);
         this.push(16777218);
         break;
      case 135:
      case 141:
         this.pop(1);
         this.push(16777219);
         this.push(16777216);
         break;
      case 139:
      case 190:
      case 193:
         this.pop(1);
         this.push(16777217);
         break;
      case 148:
      case 151:
      case 152:
         this.pop(4);
         this.push(16777217);
         break;
      case 168:
      case 169:
         throw new RuntimeException("JSR/RET are not supported with computeFrames option");
      case 178:
         this.push(var3, var4.strVal3);
         break;
      case 179:
         this.pop(var4.strVal3);
         break;
      case 180:
         this.pop(1);
         this.push(var3, var4.strVal3);
         break;
      case 181:
         this.pop(var4.strVal3);
         this.pop();
         break;
      case 182:
      case 183:
      case 184:
      case 185:
         this.pop(var4.strVal3);
         if (var1 != 184) {
            var5 = this.pop();
            if (var1 == 183 && var4.strVal2.charAt(0) == '<') {
               this.init(var5);
            }
         }

         this.push(var3, var4.strVal3);
         break;
      case 186:
         this.pop(var4.strVal2);
         this.push(var3, var4.strVal2);
         break;
      case 187:
         this.push(25165824 | var3.addUninitializedType(var4.strVal1, var2));
         break;
      case 188:
         this.pop();
         switch(var2) {
         case 4:
            this.push(285212681);
            return;
         case 5:
            this.push(285212683);
            return;
         case 6:
            this.push(285212674);
            return;
         case 7:
            this.push(285212675);
            return;
         case 8:
            this.push(285212682);
            return;
         case 9:
            this.push(285212684);
            return;
         case 10:
            this.push(285212673);
            return;
         default:
            this.push(285212676);
            return;
         }
      case 189:
         var9 = var4.strVal1;
         this.pop();
         if (var9.charAt(0) == '[') {
            this.push(var3, '[' + var9);
         } else {
            this.push(292552704 | var3.addType(var9));
         }
         break;
      case 192:
         var9 = var4.strVal1;
         this.pop();
         if (var9.charAt(0) == '[') {
            this.push(var3, var9);
         } else {
            this.push(24117248 | var3.addType(var9));
         }
      }

   }

   final boolean merge(ClassWriter var1, Frame var2, int var3) {
      boolean var4 = false;
      int var5 = this.inputLocals.length;
      int var6 = this.inputStack.length;
      if (var2.inputLocals == null) {
         var2.inputLocals = new int[var5];
         var4 = true;
      }

      int var7;
      int var8;
      int var9;
      int var10;
      int var11;
      for(var7 = 0; var7 < var5; ++var7) {
         if (this.outputLocals != null && var7 < this.outputLocals.length) {
            var8 = this.outputLocals[var7];
            if (var8 == 0) {
               var9 = this.inputLocals[var7];
            } else {
               var10 = var8 & -268435456;
               var11 = var8 & 251658240;
               if (var11 == 16777216) {
                  var9 = var8;
               } else {
                  if (var11 == 33554432) {
                     var9 = var10 + this.inputLocals[var8 & 8388607];
                  } else {
                     var9 = var10 + this.inputStack[var6 - (var8 & 8388607)];
                  }

                  if ((var8 & 8388608) != 0 && (var9 == 16777220 || var9 == 16777219)) {
                     var9 = 16777216;
                  }
               }
            }
         } else {
            var9 = this.inputLocals[var7];
         }

         if (this.initializations != null) {
            var9 = this.init(var1, var9);
         }

         var4 |= merge(var1, var9, var2.inputLocals, var7);
      }

      if (var3 > 0) {
         for(var7 = 0; var7 < var5; ++var7) {
            var9 = this.inputLocals[var7];
            var4 |= merge(var1, var9, var2.inputLocals, var7);
         }

         if (var2.inputStack == null) {
            var2.inputStack = new int[1];
            var4 = true;
         }

         var4 |= merge(var1, var3, var2.inputStack, 0);
         return var4;
      } else {
         int var12 = this.inputStack.length + this.owner.inputStackTop;
         if (var2.inputStack == null) {
            var2.inputStack = new int[var12 + this.outputStackTop];
            var4 = true;
         }

         for(var7 = 0; var7 < var12; ++var7) {
            var9 = this.inputStack[var7];
            if (this.initializations != null) {
               var9 = this.init(var1, var9);
            }

            var4 |= merge(var1, var9, var2.inputStack, var7);
         }

         for(var7 = 0; var7 < this.outputStackTop; ++var7) {
            var8 = this.outputStack[var7];
            var10 = var8 & -268435456;
            var11 = var8 & 251658240;
            if (var11 == 16777216) {
               var9 = var8;
            } else {
               if (var11 == 33554432) {
                  var9 = var10 + this.inputLocals[var8 & 8388607];
               } else {
                  var9 = var10 + this.inputStack[var6 - (var8 & 8388607)];
               }

               if ((var8 & 8388608) != 0 && (var9 == 16777220 || var9 == 16777219)) {
                  var9 = 16777216;
               }
            }

            if (this.initializations != null) {
               var9 = this.init(var1, var9);
            }

            var4 |= merge(var1, var9, var2.inputStack, var12 + var7);
         }

         return var4;
      }
   }

   private static boolean merge(ClassWriter var0, int var1, int[] var2, int var3) {
      int var4 = var2[var3];
      if (var4 == var1) {
         return false;
      } else {
         if ((var1 & 268435455) == 16777221) {
            if (var4 == 16777221) {
               return false;
            }

            var1 = 16777221;
         }

         if (var4 == 0) {
            var2[var3] = var1;
            return true;
         } else {
            int var5;
            if ((var4 & 267386880) != 24117248 && (var4 & -268435456) == 0) {
               if (var4 == 16777221) {
                  var5 = (var1 & 267386880) != 24117248 && (var1 & -268435456) == 0 ? 16777216 : var1;
               } else {
                  var5 = 16777216;
               }
            } else {
               if (var1 == 16777221) {
                  return false;
               }

               int var6;
               if ((var1 & -1048576) == (var4 & -1048576)) {
                  if ((var4 & 267386880) == 24117248) {
                     var5 = var1 & -268435456 | 24117248 | var0.getMergedType(var1 & 1048575, var4 & 1048575);
                  } else {
                     var6 = -268435456 + (var4 & -268435456);
                     var5 = var6 | 24117248 | var0.addType("java/lang/Object");
                  }
               } else if ((var1 & 267386880) != 24117248 && (var1 & -268435456) == 0) {
                  var5 = 16777216;
               } else {
                  var6 = ((var1 & -268435456) != 0 && (var1 & 267386880) != 24117248 ? -268435456 : 0) + (var1 & -268435456);
                  int var7 = ((var4 & -268435456) != 0 && (var4 & 267386880) != 24117248 ? -268435456 : 0) + (var4 & -268435456);
                  var5 = Math.min(var6, var7) | 24117248 | var0.addType("java/lang/Object");
               }
            }

            if (var4 != var5) {
               var2[var3] = var5;
               return true;
            } else {
               return false;
            }
         }
      }
   }

   static {
      int[] var0 = new int[202];
      String var1 = "EFFFFFFFFGGFFFGGFFFEEFGFGFEEEEEEEEEEEEEEEEEEEEDEDEDDDDDCDCDEEEEEEEEEEEEEEEEEEEEBABABBBBDCFFFGGGEDCDCDCDCDCDCDCDCDCDCEEEEDDDDDDDCDCDCEFEFDDEEFFDEDEEEBDDBBDDDDDDCCCCCCCCEFEDDDCDCDEEEEEEEEEEFEEEEEEDDEEDDEE";

      for(int var2 = 0; var2 < var0.length; ++var2) {
         var0[var2] = var1.charAt(var2) - 69;
      }

      SIZE = var0;
   }
}
