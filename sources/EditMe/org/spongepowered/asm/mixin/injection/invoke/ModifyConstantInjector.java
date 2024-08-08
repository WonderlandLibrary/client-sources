package org.spongepowered.asm.mixin.injection.invoke;

import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Bytecode;

public class ModifyConstantInjector extends RedirectInjector {
   private static final int OPCODE_OFFSET = 6;

   public ModifyConstantInjector(InjectionInfo var1) {
      super(var1, "@ModifyConstant");
   }

   protected void inject(Target var1, InjectionNodes.InjectionNode var2) {
      if (this.preInject(var2)) {
         if (var2.isReplaced()) {
            throw new UnsupportedOperationException("Target failure for " + this.info);
         } else {
            AbstractInsnNode var3 = var2.getCurrentTarget();
            if (var3 instanceof JumpInsnNode) {
               this.checkTargetModifiers(var1, false);
               this.injectExpandedConstantModifier(var1, (JumpInsnNode)var3);
            } else if (Bytecode.isConstant(var3)) {
               this.checkTargetModifiers(var1, false);
               this.injectConstantModifier(var1, var3);
            } else {
               throw new InvalidInjectionException(this.info, this.annotationType + " annotation is targetting an invalid insn in " + var1 + " in " + this);
            }
         }
      }
   }

   private void injectExpandedConstantModifier(Target var1, JumpInsnNode var2) {
      int var3 = var2.getOpcode();
      if (var3 >= 155 && var3 <= 158) {
         InsnList var4 = new InsnList();
         var4.add((AbstractInsnNode)(new InsnNode(3)));
         AbstractInsnNode var5 = this.invokeConstantHandler(Type.getType("I"), var1, var4, var4);
         var4.add((AbstractInsnNode)(new JumpInsnNode(var3 + 6, var2.label)));
         var1.replaceNode(var2, var5, var4);
         var1.addToStack(1);
      } else {
         throw new InvalidInjectionException(this.info, this.annotationType + " annotation selected an invalid opcode " + Bytecode.getOpcodeName(var3) + " in " + var1 + " in " + this);
      }
   }

   private void injectConstantModifier(Target var1, AbstractInsnNode var2) {
      Type var3 = Bytecode.getConstantType(var2);
      InsnList var4 = new InsnList();
      InsnList var5 = new InsnList();
      AbstractInsnNode var6 = this.invokeConstantHandler(var3, var1, var4, var5);
      var1.wrapNode(var2, var6, var4, var5);
   }

   private AbstractInsnNode invokeConstantHandler(Type var1, Target var2, InsnList var3, InsnList var4) {
      String var5 = Bytecode.generateDescriptor(var1, var1);
      boolean var6 = this.checkDescriptor(var5, var2, "getter");
      if (!this.isStatic) {
         var3.insert((AbstractInsnNode)(new VarInsnNode(25, 0)));
         var2.addToStack(1);
      }

      if (var6) {
         this.pushArgs(var2.arguments, var4, var2.getArgIndices(), 0, var2.arguments.length);
         var2.addToStack(Bytecode.getArgsSize(var2.arguments));
      }

      return this.invokeHandler(var4);
   }
}
