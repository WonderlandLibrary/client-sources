package org.spongepowered.asm.mixin.injection.modify;

import java.util.Collection;
import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.SignaturePrinter;

public class ModifyVariableInjector extends Injector {
   private final LocalVariableDiscriminator discriminator;

   public ModifyVariableInjector(InjectionInfo var1, LocalVariableDiscriminator var2) {
      super(var1);
      this.discriminator = var2;
   }

   protected boolean findTargetNodes(MethodNode var1, InjectionPoint var2, InsnList var3, Collection var4) {
      if (var2 instanceof ModifyVariableInjector.ContextualInjectionPoint) {
         Target var5 = this.info.getContext().getTargetMethod(var1);
         return ((ModifyVariableInjector.ContextualInjectionPoint)var2).find(var5, var4);
      } else {
         return var2.find(var1.desc, var3, var4);
      }
   }

   protected void sanityCheck(Target var1, List var2) {
      super.sanityCheck(var1, var2);
      if (var1.isStatic != this.isStatic) {
         throw new InvalidInjectionException(this.info, "'static' of variable modifier method does not match target in " + this);
      } else {
         int var3 = this.discriminator.getOrdinal();
         if (var3 < -1) {
            throw new InvalidInjectionException(this.info, "Invalid ordinal " + var3 + " specified in " + this);
         } else if (this.discriminator.getIndex() == 0 && !this.isStatic) {
            throw new InvalidInjectionException(this.info, "Invalid index 0 specified in non-static variable modifier " + this);
         }
      }
   }

   protected void inject(Target var1, InjectionNodes.InjectionNode var2) {
      if (var2.isReplaced()) {
         throw new InvalidInjectionException(this.info, "Variable modifier target for " + this + " was removed by another injector");
      } else {
         ModifyVariableInjector.Context var3 = new ModifyVariableInjector.Context(this.returnType, this.discriminator.isArgsOnly(), var1, var2.getCurrentTarget());
         if (this.discriminator.printLVT()) {
            this.printLocals(var3);
         }

         try {
            int var4 = this.discriminator.findLocal(var3);
            if (var4 > -1) {
               this.inject(var3, var4);
            }
         } catch (InvalidImplicitDiscriminatorException var5) {
            if (this.discriminator.printLVT()) {
               this.info.addCallbackInvocation(this.methodNode);
               return;
            }

            throw new InvalidInjectionException(this.info, "Implicit variable modifier injection failed in " + this, var5);
         }

         var1.insns.insertBefore(var3.node, var3.insns);
         var1.addToStack(this.isStatic ? 1 : 2);
      }
   }

   private void printLocals(ModifyVariableInjector.Context var1) {
      SignaturePrinter var2 = new SignaturePrinter(this.methodNode.name, this.returnType, this.methodArgs, new String[]{"var"});
      var2.setModifiers(this.methodNode);
      (new PrettyPrinter()).kvWidth(20).kv("Target Class", this.classNode.name.replace('/', '.')).kv("Target Method", var1.target.method.name).kv("Callback Name", this.methodNode.name).kv("Capture Type", SignaturePrinter.getTypeName(this.returnType, false)).kv("Instruction", "%s %s", var1.node.getClass().getSimpleName(), Bytecode.getOpcodeName(var1.node.getOpcode())).hr().kv("Match mode", this.discriminator.isImplicit(var1) ? "IMPLICIT (match single)" : "EXPLICIT (match by criteria)").kv("Match ordinal", this.discriminator.getOrdinal() < 0 ? "any" : this.discriminator.getOrdinal()).kv("Match index", this.discriminator.getIndex() < var1.baseArgIndex ? "any" : this.discriminator.getIndex()).kv("Match name(s)", this.discriminator.hasNames() ? this.discriminator.getNames() : "any").kv("Args only", this.discriminator.isArgsOnly()).hr().add((PrettyPrinter.IPrettyPrintable)var1).print(System.err);
   }

   private void inject(ModifyVariableInjector.Context var1, int var2) {
      if (!this.isStatic) {
         var1.insns.add((AbstractInsnNode)(new VarInsnNode(25, 0)));
      }

      var1.insns.add((AbstractInsnNode)(new VarInsnNode(this.returnType.getOpcode(21), var2)));
      this.invokeHandler(var1.insns);
      var1.insns.add((AbstractInsnNode)(new VarInsnNode(this.returnType.getOpcode(54), var2)));
   }

   abstract static class ContextualInjectionPoint extends InjectionPoint {
      protected final IMixinContext context;

      ContextualInjectionPoint(IMixinContext var1) {
         this.context = var1;
      }

      public boolean find(String var1, InsnList var2, Collection var3) {
         throw new InvalidInjectionException(this.context, this.getAtCode() + " injection point must be used in conjunction with @ModifyVariable");
      }

      abstract boolean find(Target var1, Collection var2);
   }

   static class Context extends LocalVariableDiscriminator.Context {
      final InsnList insns = new InsnList();

      public Context(Type var1, boolean var2, Target var3, AbstractInsnNode var4) {
         super(var1, var2, var3, var4);
      }
   }
}
