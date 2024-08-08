package org.spongepowered.asm.mixin.injection.invoke;

import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.invoke.arg.ArgsClassGenerator;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Bytecode;

public class ModifyArgsInjector extends InvokeInjector {
   private final ArgsClassGenerator argsClassGenerator;

   public ModifyArgsInjector(InjectionInfo var1) {
      super(var1, "@ModifyArgs");
      this.argsClassGenerator = (ArgsClassGenerator)var1.getContext().getExtensions().getGenerator(ArgsClassGenerator.class);
   }

   protected void checkTarget(Target var1) {
      this.checkTargetModifiers(var1, false);
   }

   protected void inject(Target var1, InjectionNodes.InjectionNode var2) {
      this.checkTargetForNode(var1, var2);
      super.inject(var1, var2);
   }

   protected void injectAtInvoke(Target var1, InjectionNodes.InjectionNode var2) {
      MethodInsnNode var3 = (MethodInsnNode)var2.getCurrentTarget();
      Type[] var4 = Type.getArgumentTypes(var3.desc);
      if (var4.length == 0) {
         throw new InvalidInjectionException(this.info, "@ModifyArgs injector " + this + " targets a method invocation " + var3.name + var3.desc + " with no arguments!");
      } else {
         String var5 = this.argsClassGenerator.getClassRef(var3.desc);
         boolean var6 = this.verifyTarget(var1);
         InsnList var7 = new InsnList();
         var1.addToStack(1);
         this.packArgs(var7, var5, var3);
         if (var6) {
            var1.addToStack(Bytecode.getArgsSize(var1.arguments));
            Bytecode.loadArgs(var1.arguments, var7, var1.isStatic ? 0 : 1);
         }

         this.invokeHandler(var7);
         this.unpackArgs(var7, var5, var4);
         var1.insns.insertBefore(var3, (InsnList)var7);
      }
   }

   private boolean verifyTarget(Target var1) {
      String var2 = String.format("(L%s;)V", ArgsClassGenerator.ARGS_REF);
      if (!this.methodNode.desc.equals(var2)) {
         String var3 = Bytecode.changeDescriptorReturnType(var1.method.desc, "V");
         String var4 = String.format("(L%s;%s", ArgsClassGenerator.ARGS_REF, var3.substring(1));
         if (this.methodNode.desc.equals(var4)) {
            return true;
         } else {
            throw new InvalidInjectionException(this.info, "@ModifyArgs injector " + this + " has an invalid signature " + this.methodNode.desc + ", expected " + var2 + " or " + var4);
         }
      } else {
         return false;
      }
   }

   private void packArgs(InsnList var1, String var2, MethodInsnNode var3) {
      String var4 = Bytecode.changeDescriptorReturnType(var3.desc, "L" + var2 + ";");
      var1.add((AbstractInsnNode)(new MethodInsnNode(184, var2, "of", var4, false)));
      var1.add((AbstractInsnNode)(new InsnNode(89)));
      if (!this.isStatic) {
         var1.add((AbstractInsnNode)(new VarInsnNode(25, 0)));
         var1.add((AbstractInsnNode)(new InsnNode(95)));
      }

   }

   private void unpackArgs(InsnList var1, String var2, Type[] var3) {
      for(int var4 = 0; var4 < var3.length; ++var4) {
         if (var4 < var3.length - 1) {
            var1.add((AbstractInsnNode)(new InsnNode(89)));
         }

         var1.add((AbstractInsnNode)(new MethodInsnNode(182, var2, "$" + var4, "()" + var3[var4].getDescriptor(), false)));
         if (var4 < var3.length - 1) {
            if (var3[var4].getSize() == 1) {
               var1.add((AbstractInsnNode)(new InsnNode(95)));
            } else {
               var1.add((AbstractInsnNode)(new InsnNode(93)));
               var1.add((AbstractInsnNode)(new InsnNode(88)));
            }
         }
      }

   }
}
