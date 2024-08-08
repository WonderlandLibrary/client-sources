package org.spongepowered.asm.lib.tree.analysis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.InvokeDynamicInsnNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;

public class SourceInterpreter extends Interpreter implements Opcodes {
   public SourceInterpreter() {
      super(327680);
   }

   protected SourceInterpreter(int var1) {
      super(var1);
   }

   public SourceValue newValue(Type var1) {
      return var1 == Type.VOID_TYPE ? null : new SourceValue(var1 == null ? 1 : var1.getSize());
   }

   public SourceValue newOperation(AbstractInsnNode var1) {
      int var2;
      switch(var1.getOpcode()) {
      case 9:
      case 10:
      case 14:
      case 15:
         var2 = 2;
         break;
      case 18:
         Object var3 = ((LdcInsnNode)var1).cst;
         var2 = !(var3 instanceof Long) && !(var3 instanceof Double) ? 1 : 2;
         break;
      case 178:
         var2 = Type.getType(((FieldInsnNode)var1).desc).getSize();
         break;
      default:
         var2 = 1;
      }

      return new SourceValue(var2, var1);
   }

   public SourceValue copyOperation(AbstractInsnNode var1, SourceValue var2) {
      return new SourceValue(var2.getSize(), var1);
   }

   public SourceValue unaryOperation(AbstractInsnNode var1, SourceValue var2) {
      int var3;
      switch(var1.getOpcode()) {
      case 117:
      case 119:
      case 133:
      case 135:
      case 138:
      case 140:
      case 141:
      case 143:
         var3 = 2;
         break;
      case 180:
         var3 = Type.getType(((FieldInsnNode)var1).desc).getSize();
         break;
      default:
         var3 = 1;
      }

      return new SourceValue(var3, var1);
   }

   public SourceValue binaryOperation(AbstractInsnNode var1, SourceValue var2, SourceValue var3) {
      byte var4;
      switch(var1.getOpcode()) {
      case 47:
      case 49:
      case 97:
      case 99:
      case 101:
      case 103:
      case 105:
      case 107:
      case 109:
      case 111:
      case 113:
      case 115:
      case 121:
      case 123:
      case 125:
      case 127:
      case 129:
      case 131:
         var4 = 2;
         break;
      default:
         var4 = 1;
      }

      return new SourceValue(var4, var1);
   }

   public SourceValue ternaryOperation(AbstractInsnNode var1, SourceValue var2, SourceValue var3, SourceValue var4) {
      return new SourceValue(1, var1);
   }

   public SourceValue naryOperation(AbstractInsnNode var1, List var2) {
      int var3 = var1.getOpcode();
      int var4;
      if (var3 == 197) {
         var4 = 1;
      } else {
         String var5 = var3 == 186 ? ((InvokeDynamicInsnNode)var1).desc : ((MethodInsnNode)var1).desc;
         var4 = Type.getReturnType(var5).getSize();
      }

      return new SourceValue(var4, var1);
   }

   public void returnOperation(AbstractInsnNode var1, SourceValue var2, SourceValue var3) {
   }

   public SourceValue merge(SourceValue var1, SourceValue var2) {
      if (var1.insns instanceof SmallSet && var2.insns instanceof SmallSet) {
         Set var4 = ((SmallSet)var1.insns).union((SmallSet)var2.insns);
         return var4 == var1.insns && var1.size == var2.size ? var1 : new SourceValue(Math.min(var1.size, var2.size), var4);
      } else if (var1.size == var2.size && var1.insns.containsAll(var2.insns)) {
         return var1;
      } else {
         HashSet var3 = new HashSet();
         var3.addAll(var1.insns);
         var3.addAll(var2.insns);
         return new SourceValue(Math.min(var1.size, var2.size), var3);
      }
   }

   public Value merge(Value var1, Value var2) {
      return this.merge((SourceValue)var1, (SourceValue)var2);
   }

   public void returnOperation(AbstractInsnNode var1, Value var2, Value var3) throws AnalyzerException {
      this.returnOperation(var1, (SourceValue)var2, (SourceValue)var3);
   }

   public Value naryOperation(AbstractInsnNode var1, List var2) throws AnalyzerException {
      return this.naryOperation(var1, var2);
   }

   public Value ternaryOperation(AbstractInsnNode var1, Value var2, Value var3, Value var4) throws AnalyzerException {
      return this.ternaryOperation(var1, (SourceValue)var2, (SourceValue)var3, (SourceValue)var4);
   }

   public Value binaryOperation(AbstractInsnNode var1, Value var2, Value var3) throws AnalyzerException {
      return this.binaryOperation(var1, (SourceValue)var2, (SourceValue)var3);
   }

   public Value unaryOperation(AbstractInsnNode var1, Value var2) throws AnalyzerException {
      return this.unaryOperation(var1, (SourceValue)var2);
   }

   public Value copyOperation(AbstractInsnNode var1, Value var2) throws AnalyzerException {
      return this.copyOperation(var1, (SourceValue)var2);
   }

   public Value newOperation(AbstractInsnNode var1) throws AnalyzerException {
      return this.newOperation(var1);
   }

   public Value newValue(Type var1) {
      return this.newValue(var1);
   }
}
