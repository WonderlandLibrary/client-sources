package org.spongepowered.asm.mixin.gen;

import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.util.Bytecode;

public class AccessorGeneratorMethodProxy extends AccessorGenerator {
   private final MethodNode targetMethod;
   private final Type[] argTypes;
   private final Type returnType;
   private final boolean isInstanceMethod;

   public AccessorGeneratorMethodProxy(AccessorInfo var1) {
      super(var1);
      this.targetMethod = var1.getTargetMethod();
      this.argTypes = var1.getArgTypes();
      this.returnType = var1.getReturnType();
      this.isInstanceMethod = !Bytecode.hasFlag((MethodNode)this.targetMethod, 8);
   }

   public MethodNode generate() {
      int var1 = Bytecode.getArgsSize(this.argTypes) + this.returnType.getSize() + (this.isInstanceMethod ? 1 : 0);
      MethodNode var2 = this.createMethod(var1, var1);
      if (this.isInstanceMethod) {
         var2.instructions.add((AbstractInsnNode)(new VarInsnNode(25, 0)));
      }

      Bytecode.loadArgs(this.argTypes, var2.instructions, this.isInstanceMethod ? 1 : 0);
      boolean var3 = Bytecode.hasFlag((MethodNode)this.targetMethod, 2);
      int var4 = this.isInstanceMethod ? (var3 ? 183 : 182) : 184;
      var2.instructions.add((AbstractInsnNode)(new MethodInsnNode(var4, this.info.getClassNode().name, this.targetMethod.name, this.targetMethod.desc, false)));
      var2.instructions.add((AbstractInsnNode)(new InsnNode(this.returnType.getOpcode(172))));
      return var2;
   }
}
