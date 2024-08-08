package org.spongepowered.asm.lib.tree.analysis;

import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;

public abstract class Interpreter {
   protected final int api;

   protected Interpreter(int var1) {
      this.api = var1;
   }

   public abstract Value newValue(Type var1);

   public abstract Value newOperation(AbstractInsnNode var1) throws AnalyzerException;

   public abstract Value copyOperation(AbstractInsnNode var1, Value var2) throws AnalyzerException;

   public abstract Value unaryOperation(AbstractInsnNode var1, Value var2) throws AnalyzerException;

   public abstract Value binaryOperation(AbstractInsnNode var1, Value var2, Value var3) throws AnalyzerException;

   public abstract Value ternaryOperation(AbstractInsnNode var1, Value var2, Value var3, Value var4) throws AnalyzerException;

   public abstract Value naryOperation(AbstractInsnNode var1, List var2) throws AnalyzerException;

   public abstract void returnOperation(AbstractInsnNode var1, Value var2, Value var3) throws AnalyzerException;

   public abstract Value merge(Value var1, Value var2);
}
