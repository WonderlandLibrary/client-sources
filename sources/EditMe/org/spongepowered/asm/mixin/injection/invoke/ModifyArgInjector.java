package org.spongepowered.asm.mixin.injection.invoke;

import java.util.Arrays;
import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Bytecode;

public class ModifyArgInjector extends InvokeInjector {
   private final int index;
   private final boolean singleArgMode;

   public ModifyArgInjector(InjectionInfo var1, int var2) {
      super(var1, "@ModifyArg");
      this.index = var2;
      this.singleArgMode = this.methodArgs.length == 1;
   }

   protected void sanityCheck(Target var1, List var2) {
      super.sanityCheck(var1, var2);
      if (this.singleArgMode && !this.methodArgs[0].equals(this.returnType)) {
         throw new InvalidInjectionException(this.info, "@ModifyArg return type on " + this + " must match the parameter type. ARG=" + this.methodArgs[0] + " RETURN=" + this.returnType);
      }
   }

   protected void checkTarget(Target var1) {
      if (!this.isStatic && var1.isStatic) {
         throw new InvalidInjectionException(this.info, "non-static callback method " + this + " targets a static method which is not supported");
      }
   }

   protected void inject(Target var1, InjectionNodes.InjectionNode var2) {
      this.checkTargetForNode(var1, var2);
      super.inject(var1, var2);
   }

   protected void injectAtInvoke(Target var1, InjectionNodes.InjectionNode var2) {
      MethodInsnNode var3 = (MethodInsnNode)var2.getCurrentTarget();
      Type[] var4 = Type.getArgumentTypes(var3.desc);
      int var5 = this.findArgIndex(var1, var4);
      InsnList var6 = new InsnList();
      boolean var7 = false;
      int var8;
      if (this.singleArgMode) {
         var8 = this.injectSingleArgHandler(var1, var4, var5, var6);
      } else {
         var8 = this.injectMultiArgHandler(var1, var4, var5, var6);
      }

      var1.insns.insertBefore(var3, (InsnList)var6);
      var1.addToLocals(var8);
      var1.addToStack(2 - (var8 - 1));
   }

   private int injectSingleArgHandler(Target var1, Type[] var2, int var3, InsnList var4) {
      int[] var5 = this.storeArgs(var1, var2, var4, var3);
      this.invokeHandlerWithArgs(var2, var4, var5, var3, var3 + 1);
      this.pushArgs(var2, var4, var5, var3 + 1, var2.length);
      return var5[var5.length - 1] - var1.getMaxLocals() + var2[var2.length - 1].getSize();
   }

   private int injectMultiArgHandler(Target var1, Type[] var2, int var3, InsnList var4) {
      if (!Arrays.equals(var2, this.methodArgs)) {
         throw new InvalidInjectionException(this.info, "@ModifyArg method " + this + " targets a method with an invalid signature " + Bytecode.getDescriptor(var2) + ", expected " + Bytecode.getDescriptor(this.methodArgs));
      } else {
         int[] var5 = this.storeArgs(var1, var2, var4, 0);
         this.pushArgs(var2, var4, var5, 0, var3);
         this.invokeHandlerWithArgs(var2, var4, var5, 0, var2.length);
         this.pushArgs(var2, var4, var5, var3 + 1, var2.length);
         return var5[var5.length - 1] - var1.getMaxLocals() + var2[var2.length - 1].getSize();
      }
   }

   protected int findArgIndex(Target var1, Type[] var2) {
      if (this.index > -1) {
         if (this.index < var2.length && var2[this.index].equals(this.returnType)) {
            return this.index;
         } else {
            throw new InvalidInjectionException(this.info, "Specified index " + this.index + " for @ModifyArg is invalid for args " + Bytecode.getDescriptor(var2) + ", expected " + this.returnType + " on " + this);
         }
      } else {
         int var3 = -1;

         for(int var4 = 0; var4 < var2.length; ++var4) {
            if (var2[var4].equals(this.returnType)) {
               if (var3 != -1) {
                  throw new InvalidInjectionException(this.info, "Found duplicate args with index [" + var3 + ", " + var4 + "] matching type " + this.returnType + " for @ModifyArg target " + var1 + " in " + this + ". Please specify index of desired arg.");
               }

               var3 = var4;
            }
         }

         if (var3 == -1) {
            throw new InvalidInjectionException(this.info, "Could not find arg matching type " + this.returnType + " for @ModifyArg target " + var1 + " in " + this);
         } else {
            return var3;
         }
      }
   }
}
