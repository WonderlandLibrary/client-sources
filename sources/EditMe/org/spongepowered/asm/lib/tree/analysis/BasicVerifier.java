package org.spongepowered.asm.lib.tree.analysis;

import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.InvokeDynamicInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;

public class BasicVerifier extends BasicInterpreter {
   public BasicVerifier() {
      super(327680);
   }

   protected BasicVerifier(int var1) {
      super(var1);
   }

   public BasicValue copyOperation(AbstractInsnNode var1, BasicValue var2) throws AnalyzerException {
      BasicValue var3;
      switch(var1.getOpcode()) {
      case 21:
      case 54:
         var3 = BasicValue.INT_VALUE;
         break;
      case 22:
      case 55:
         var3 = BasicValue.LONG_VALUE;
         break;
      case 23:
      case 56:
         var3 = BasicValue.FLOAT_VALUE;
         break;
      case 24:
      case 57:
         var3 = BasicValue.DOUBLE_VALUE;
         break;
      case 25:
         if (!var2.isReference()) {
            throw new AnalyzerException(var1, (String)null, "an object reference", var2);
         }

         return var2;
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
      case 46:
      case 47:
      case 48:
      case 49:
      case 50:
      case 51:
      case 52:
      case 53:
      default:
         return var2;
      case 58:
         if (!var2.isReference() && !BasicValue.RETURNADDRESS_VALUE.equals(var2)) {
            throw new AnalyzerException(var1, (String)null, "an object reference or a return address", var2);
         }

         return var2;
      }

      if (!var3.equals(var2)) {
         throw new AnalyzerException(var1, (String)null, var3, var2);
      } else {
         return var2;
      }
   }

   public BasicValue unaryOperation(AbstractInsnNode var1, BasicValue var2) throws AnalyzerException {
      BasicValue var3;
      switch(var1.getOpcode()) {
      case 116:
      case 132:
      case 133:
      case 134:
      case 135:
      case 145:
      case 146:
      case 147:
      case 153:
      case 154:
      case 155:
      case 156:
      case 157:
      case 158:
      case 170:
      case 171:
      case 172:
      case 188:
      case 189:
         var3 = BasicValue.INT_VALUE;
         break;
      case 117:
      case 136:
      case 137:
      case 138:
      case 173:
         var3 = BasicValue.LONG_VALUE;
         break;
      case 118:
      case 139:
      case 140:
      case 141:
      case 174:
         var3 = BasicValue.FLOAT_VALUE;
         break;
      case 119:
      case 142:
      case 143:
      case 144:
      case 175:
         var3 = BasicValue.DOUBLE_VALUE;
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
      case 148:
      case 149:
      case 150:
      case 151:
      case 152:
      case 159:
      case 160:
      case 161:
      case 162:
      case 163:
      case 164:
      case 165:
      case 166:
      case 167:
      case 168:
      case 169:
      case 177:
      case 178:
      case 181:
      case 182:
      case 183:
      case 184:
      case 185:
      case 186:
      case 187:
      case 196:
      case 197:
      default:
         throw new Error("Internal error.");
      case 176:
      case 191:
      case 193:
      case 194:
      case 195:
      case 198:
      case 199:
         if (!var2.isReference()) {
            throw new AnalyzerException(var1, (String)null, "an object reference", var2);
         }

         return super.unaryOperation(var1, var2);
      case 179:
         var3 = this.newValue(Type.getType(((FieldInsnNode)var1).desc));
         break;
      case 180:
         var3 = this.newValue(Type.getObjectType(((FieldInsnNode)var1).owner));
         break;
      case 190:
         if (!this.isArrayValue(var2)) {
            throw new AnalyzerException(var1, (String)null, "an array reference", var2);
         }

         return super.unaryOperation(var1, var2);
      case 192:
         if (!var2.isReference()) {
            throw new AnalyzerException(var1, (String)null, "an object reference", var2);
         }

         return super.unaryOperation(var1, var2);
      }

      if (!this.isSubTypeOf(var2, var3)) {
         throw new AnalyzerException(var1, (String)null, var3, var2);
      } else {
         return super.unaryOperation(var1, var2);
      }
   }

   public BasicValue binaryOperation(AbstractInsnNode var1, BasicValue var2, BasicValue var3) throws AnalyzerException {
      BasicValue var4;
      BasicValue var5;
      switch(var1.getOpcode()) {
      case 46:
         var4 = this.newValue(Type.getType("[I"));
         var5 = BasicValue.INT_VALUE;
         break;
      case 47:
         var4 = this.newValue(Type.getType("[J"));
         var5 = BasicValue.INT_VALUE;
         break;
      case 48:
         var4 = this.newValue(Type.getType("[F"));
         var5 = BasicValue.INT_VALUE;
         break;
      case 49:
         var4 = this.newValue(Type.getType("[D"));
         var5 = BasicValue.INT_VALUE;
         break;
      case 50:
         var4 = this.newValue(Type.getType("[Ljava/lang/Object;"));
         var5 = BasicValue.INT_VALUE;
         break;
      case 51:
         if (this.isSubTypeOf(var2, this.newValue(Type.getType("[Z")))) {
            var4 = this.newValue(Type.getType("[Z"));
         } else {
            var4 = this.newValue(Type.getType("[B"));
         }

         var5 = BasicValue.INT_VALUE;
         break;
      case 52:
         var4 = this.newValue(Type.getType("[C"));
         var5 = BasicValue.INT_VALUE;
         break;
      case 53:
         var4 = this.newValue(Type.getType("[S"));
         var5 = BasicValue.INT_VALUE;
         break;
      case 54:
      case 55:
      case 56:
      case 57:
      case 58:
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
      case 79:
      case 80:
      case 81:
      case 82:
      case 83:
      case 84:
      case 85:
      case 86:
      case 87:
      case 88:
      case 89:
      case 90:
      case 91:
      case 92:
      case 93:
      case 94:
      case 95:
      case 116:
      case 117:
      case 118:
      case 119:
      case 132:
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
      case 153:
      case 154:
      case 155:
      case 156:
      case 157:
      case 158:
      case 167:
      case 168:
      case 169:
      case 170:
      case 171:
      case 172:
      case 173:
      case 174:
      case 175:
      case 176:
      case 177:
      case 178:
      case 179:
      case 180:
      default:
         throw new Error("Internal error.");
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
      case 159:
      case 160:
      case 161:
      case 162:
      case 163:
      case 164:
         var4 = BasicValue.INT_VALUE;
         var5 = BasicValue.INT_VALUE;
         break;
      case 97:
      case 101:
      case 105:
      case 109:
      case 113:
      case 127:
      case 129:
      case 131:
      case 148:
         var4 = BasicValue.LONG_VALUE;
         var5 = BasicValue.LONG_VALUE;
         break;
      case 98:
      case 102:
      case 106:
      case 110:
      case 114:
      case 149:
      case 150:
         var4 = BasicValue.FLOAT_VALUE;
         var5 = BasicValue.FLOAT_VALUE;
         break;
      case 99:
      case 103:
      case 107:
      case 111:
      case 115:
      case 151:
      case 152:
         var4 = BasicValue.DOUBLE_VALUE;
         var5 = BasicValue.DOUBLE_VALUE;
         break;
      case 121:
      case 123:
      case 125:
         var4 = BasicValue.LONG_VALUE;
         var5 = BasicValue.INT_VALUE;
         break;
      case 165:
      case 166:
         var4 = BasicValue.REFERENCE_VALUE;
         var5 = BasicValue.REFERENCE_VALUE;
         break;
      case 181:
         FieldInsnNode var6 = (FieldInsnNode)var1;
         var4 = this.newValue(Type.getObjectType(var6.owner));
         var5 = this.newValue(Type.getType(var6.desc));
      }

      if (!this.isSubTypeOf(var2, var4)) {
         throw new AnalyzerException(var1, "First argument", var4, var2);
      } else if (!this.isSubTypeOf(var3, var5)) {
         throw new AnalyzerException(var1, "Second argument", var5, var3);
      } else {
         return var1.getOpcode() == 50 ? this.getElementValue(var2) : super.binaryOperation(var1, var2, var3);
      }
   }

   public BasicValue ternaryOperation(AbstractInsnNode var1, BasicValue var2, BasicValue var3, BasicValue var4) throws AnalyzerException {
      BasicValue var5;
      BasicValue var6;
      switch(var1.getOpcode()) {
      case 79:
         var5 = this.newValue(Type.getType("[I"));
         var6 = BasicValue.INT_VALUE;
         break;
      case 80:
         var5 = this.newValue(Type.getType("[J"));
         var6 = BasicValue.LONG_VALUE;
         break;
      case 81:
         var5 = this.newValue(Type.getType("[F"));
         var6 = BasicValue.FLOAT_VALUE;
         break;
      case 82:
         var5 = this.newValue(Type.getType("[D"));
         var6 = BasicValue.DOUBLE_VALUE;
         break;
      case 83:
         var5 = var2;
         var6 = BasicValue.REFERENCE_VALUE;
         break;
      case 84:
         if (this.isSubTypeOf(var2, this.newValue(Type.getType("[Z")))) {
            var5 = this.newValue(Type.getType("[Z"));
         } else {
            var5 = this.newValue(Type.getType("[B"));
         }

         var6 = BasicValue.INT_VALUE;
         break;
      case 85:
         var5 = this.newValue(Type.getType("[C"));
         var6 = BasicValue.INT_VALUE;
         break;
      case 86:
         var5 = this.newValue(Type.getType("[S"));
         var6 = BasicValue.INT_VALUE;
         break;
      default:
         throw new Error("Internal error.");
      }

      if (!this.isSubTypeOf(var2, var5)) {
         throw new AnalyzerException(var1, "First argument", "a " + var5 + " array reference", var2);
      } else if (!BasicValue.INT_VALUE.equals(var3)) {
         throw new AnalyzerException(var1, "Second argument", BasicValue.INT_VALUE, var3);
      } else if (!this.isSubTypeOf(var4, var6)) {
         throw new AnalyzerException(var1, "Third argument", var6, var4);
      } else {
         return null;
      }
   }

   public BasicValue naryOperation(AbstractInsnNode var1, List var2) throws AnalyzerException {
      int var3 = var1.getOpcode();
      int var4;
      if (var3 == 197) {
         for(var4 = 0; var4 < var2.size(); ++var4) {
            if (!BasicValue.INT_VALUE.equals(var2.get(var4))) {
               throw new AnalyzerException(var1, (String)null, BasicValue.INT_VALUE, (Value)var2.get(var4));
            }
         }
      } else {
         var4 = 0;
         int var5 = 0;
         if (var3 != 184 && var3 != 186) {
            Type var6 = Type.getObjectType(((MethodInsnNode)var1).owner);
            if (!this.isSubTypeOf((BasicValue)var2.get(var4++), this.newValue(var6))) {
               throw new AnalyzerException(var1, "Method owner", this.newValue(var6), (Value)var2.get(0));
            }
         }

         String var10 = var3 == 186 ? ((InvokeDynamicInsnNode)var1).desc : ((MethodInsnNode)var1).desc;
         Type[] var7 = Type.getArgumentTypes(var10);

         while(var4 < var2.size()) {
            BasicValue var8 = this.newValue(var7[var5++]);
            BasicValue var9 = (BasicValue)var2.get(var4++);
            if (!this.isSubTypeOf(var9, var8)) {
               throw new AnalyzerException(var1, "Argument " + var5, var8, var9);
            }
         }
      }

      return super.naryOperation(var1, var2);
   }

   public void returnOperation(AbstractInsnNode var1, BasicValue var2, BasicValue var3) throws AnalyzerException {
      if (!this.isSubTypeOf(var2, var3)) {
         throw new AnalyzerException(var1, "Incompatible return type", var3, var2);
      }
   }

   protected boolean isArrayValue(BasicValue var1) {
      return var1.isReference();
   }

   protected BasicValue getElementValue(BasicValue var1) throws AnalyzerException {
      return BasicValue.REFERENCE_VALUE;
   }

   protected boolean isSubTypeOf(BasicValue var1, BasicValue var2) {
      return var1.equals(var2);
   }

   public void returnOperation(AbstractInsnNode var1, Value var2, Value var3) throws AnalyzerException {
      this.returnOperation(var1, (BasicValue)var2, (BasicValue)var3);
   }

   public Value naryOperation(AbstractInsnNode var1, List var2) throws AnalyzerException {
      return this.naryOperation(var1, var2);
   }

   public Value ternaryOperation(AbstractInsnNode var1, Value var2, Value var3, Value var4) throws AnalyzerException {
      return this.ternaryOperation(var1, (BasicValue)var2, (BasicValue)var3, (BasicValue)var4);
   }

   public Value binaryOperation(AbstractInsnNode var1, Value var2, Value var3) throws AnalyzerException {
      return this.binaryOperation(var1, (BasicValue)var2, (BasicValue)var3);
   }

   public Value unaryOperation(AbstractInsnNode var1, Value var2) throws AnalyzerException {
      return this.unaryOperation(var1, (BasicValue)var2);
   }

   public Value copyOperation(AbstractInsnNode var1, Value var2) throws AnalyzerException {
      return this.copyOperation(var1, (BasicValue)var2);
   }
}
