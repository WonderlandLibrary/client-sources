package org.spongepowered.asm.mixin.injection.callback;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.Surrogate;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.points.BeforeReturn;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.Locals;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.SignaturePrinter;

public class CallbackInjector extends Injector {
   private final boolean cancellable;
   private final LocalCapture localCapture;
   private final String identifier;
   private final Map ids = new HashMap();
   private int totalInjections = 0;
   private int callbackInfoVar = -1;
   private String lastId;
   private String lastDesc;
   private Target lastTarget;
   private String callbackInfoClass;

   public CallbackInjector(InjectionInfo var1, boolean var2, LocalCapture var3, String var4) {
      super(var1);
      this.cancellable = var2;
      this.localCapture = var3;
      this.identifier = var4;
   }

   protected void sanityCheck(Target var1, List var2) {
      super.sanityCheck(var1, var2);
      if (var1.isStatic != this.isStatic) {
         throw new InvalidInjectionException(this.info, "'static' modifier of callback method does not match target in " + this);
      } else {
         if ("<init>".equals(var1.method.name)) {
            Iterator var3 = var2.iterator();

            while(var3.hasNext()) {
               InjectionPoint var4 = (InjectionPoint)var3.next();
               if (!var4.getClass().equals(BeforeReturn.class)) {
                  throw new InvalidInjectionException(this.info, "Found injection point type " + var4.getClass().getSimpleName() + " targetting a ctor in " + this + ". Only RETURN allowed for a ctor target");
               }
            }
         }

      }
   }

   protected void addTargetNode(Target var1, List var2, AbstractInsnNode var3, Set var4) {
      InjectionNodes.InjectionNode var5 = var1.addInjectionNode(var3);
      Iterator var6 = var4.iterator();

      while(var6.hasNext()) {
         InjectionPoint var7 = (InjectionPoint)var6.next();
         String var8 = var7.getId();
         if (!Strings.isNullOrEmpty(var8)) {
            String var9 = (String)this.ids.get(var5.getId());
            if (var9 != null && !var9.equals(var8)) {
               Injector.logger.warn("Conflicting id for {} insn in {}, found id {} on {}, previously defined as {}", new Object[]{Bytecode.getOpcodeName(var3), var1.toString(), var8, this.info, var9});
               break;
            }

            this.ids.put(var5.getId(), var8);
         }
      }

      var2.add(var5);
      ++this.totalInjections;
   }

   protected void inject(Target var1, InjectionNodes.InjectionNode var2) {
      LocalVariableNode[] var3 = null;
      if (this.localCapture.isCaptureLocals() || this.localCapture.isPrintLocals()) {
         var3 = Locals.getLocalsAt(this.classNode, var1.method, var2.getCurrentTarget());
      }

      this.inject(new CallbackInjector.Callback(this, this.methodNode, var1, var2, var3, this.localCapture.isCaptureLocals()));
   }

   private void inject(CallbackInjector.Callback var1) {
      if (this.localCapture.isPrintLocals()) {
         this.printLocals(var1);
         this.info.addCallbackInvocation(this.methodNode);
      } else {
         MethodNode var2 = this.methodNode;
         if (!var1.checkDescriptor(this.methodNode.desc)) {
            if (this.info.getTargets().size() > 1) {
               return;
            }

            if (var1.canCaptureLocals) {
               MethodNode var3 = Bytecode.findMethod(this.classNode, this.methodNode.name, var1.getDescriptor());
               if (var3 != null && Annotations.getVisible(var3, Surrogate.class) != null) {
                  var2 = var3;
               } else {
                  String var4 = this.generateBadLVTMessage(var1);
                  switch(this.localCapture) {
                  case CAPTURE_FAILEXCEPTION:
                     Injector.logger.error("Injection error: {}", new Object[]{var4});
                     var2 = this.generateErrorMethod(var1, "org/spongepowered/asm/mixin/injection/throwables/InjectionError", var4);
                     break;
                  case CAPTURE_FAILSOFT:
                     Injector.logger.warn("Injection warning: {}", new Object[]{var4});
                     return;
                  default:
                     Injector.logger.error("Critical injection failure: {}", new Object[]{var4});
                     throw new InjectionError(var4);
                  }
               }
            } else {
               String var5 = this.methodNode.desc.replace("Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;", "Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;");
               if (var1.checkDescriptor(var5)) {
                  throw new InvalidInjectionException(this.info, "Invalid descriptor on " + this.info + "! CallbackInfoReturnable is required!");
               }

               MethodNode var6 = Bytecode.findMethod(this.classNode, this.methodNode.name, var1.getDescriptor());
               if (var6 == null || Annotations.getVisible(var6, Surrogate.class) == null) {
                  throw new InvalidInjectionException(this.info, "Invalid descriptor on " + this.info + "! Expected " + var1.getDescriptor() + " but found " + this.methodNode.desc);
               }

               var2 = var6;
            }
         }

         this.dupReturnValue(var1);
         if (this.cancellable || this.totalInjections > 1) {
            this.createCallbackInfo(var1, true);
         }

         this.invokeCallback(var1, var2);
         this.injectCancellationCode(var1);
         var1.inject();
         this.info.notifyInjected(var1.target);
      }
   }

   private String generateBadLVTMessage(CallbackInjector.Callback var1) {
      int var2 = var1.target.indexOf(var1.node);
      List var3 = summariseLocals(this.methodNode.desc, var1.target.arguments.length + 1);
      List var4 = summariseLocals(var1.getDescriptorWithAllLocals(), var1.frameSize);
      return String.format("LVT in %s has incompatible changes at opcode %d in callback %s.\nExpected: %s\n   Found: %s", var1.target, var2, this, var3, var4);
   }

   private MethodNode generateErrorMethod(CallbackInjector.Callback var1, String var2, String var3) {
      MethodNode var4 = this.info.addMethod(this.methodNode.access, this.methodNode.name + "$missing", var1.getDescriptor());
      var4.maxLocals = Bytecode.getFirstNonArgLocalIndex(Type.getArgumentTypes(var1.getDescriptor()), !this.isStatic);
      var4.maxStack = 3;
      InsnList var5 = var4.instructions;
      var5.add((AbstractInsnNode)(new TypeInsnNode(187, var2)));
      var5.add((AbstractInsnNode)(new InsnNode(89)));
      var5.add((AbstractInsnNode)(new LdcInsnNode(var3)));
      var5.add((AbstractInsnNode)(new MethodInsnNode(183, var2, "<init>", "(Ljava/lang/String;)V", false)));
      var5.add((AbstractInsnNode)(new InsnNode(191)));
      return var4;
   }

   private void printLocals(CallbackInjector.Callback var1) {
      Type[] var2 = Type.getArgumentTypes(var1.getDescriptorWithAllLocals());
      SignaturePrinter var3 = new SignaturePrinter(var1.target.method, var1.argNames);
      SignaturePrinter var4 = new SignaturePrinter(this.methodNode.name, var1.target.returnType, var2, var1.argNames);
      var4.setModifiers(this.methodNode);
      PrettyPrinter var5 = new PrettyPrinter();
      var5.kv("Target Class", this.classNode.name.replace('/', '.'));
      var5.kv("Target Method", var3);
      var5.kv("Target Max LOCALS", var1.target.getMaxLocals());
      var5.kv("Initial Frame Size", var1.frameSize);
      var5.kv("Callback Name", this.methodNode.name);
      var5.kv("Instruction", "%s %s", var1.node.getClass().getSimpleName(), Bytecode.getOpcodeName(var1.node.getCurrentTarget().getOpcode()));
      var5.hr();
      if (var1.locals.length > var1.frameSize) {
         var5.add("  %s  %20s  %s", "LOCAL", "TYPE", "NAME");

         for(int var6 = 0; var6 < var1.locals.length; ++var6) {
            String var7 = var6 == var1.frameSize ? ">" : " ";
            if (var1.locals[var6] != null) {
               var5.add("%s [%3d]  %20s  %-50s %s", var7, var6, SignaturePrinter.getTypeName(var1.localTypes[var6], false), meltSnowman(var6, var1.locals[var6].name), var6 >= var1.frameSize ? "<capture>" : "");
            } else {
               boolean var8 = var6 > 0 && var1.localTypes[var6 - 1] != null && var1.localTypes[var6 - 1].getSize() > 1;
               var5.add("%s [%3d]  %20s", var7, var6, var8 ? "<top>" : "-");
            }
         }

         var5.hr();
      }

      var5.add().add("/**").add(" * Expected callback signature").add(" * /");
      var5.add("%s {", var4);
      var5.add("    // Method body").add("}").add().print(System.err);
   }

   private void createCallbackInfo(CallbackInjector.Callback var1, boolean var2) {
      if (var1.target != this.lastTarget) {
         this.lastId = null;
         this.lastDesc = null;
      }

      this.lastTarget = var1.target;
      String var3 = this.getIdentifier(var1);
      String var4 = var1.getCallbackInfoConstructorDescriptor();
      if (!var3.equals(this.lastId) || !var4.equals(this.lastDesc) || var1.isAtReturn || this.cancellable) {
         this.instanceCallbackInfo(var1, var3, var4, var2);
      }
   }

   private void loadOrCreateCallbackInfo(CallbackInjector.Callback var1) {
      if (!this.cancellable && this.totalInjections <= 1) {
         this.createCallbackInfo(var1, false);
      } else {
         var1.add(new VarInsnNode(25, this.callbackInfoVar), false, true);
      }

   }

   private void dupReturnValue(CallbackInjector.Callback var1) {
      if (var1.isAtReturn) {
         var1.add(new InsnNode(89));
         var1.add(new VarInsnNode(var1.target.returnType.getOpcode(54), var1.marshalVar()));
      }
   }

   protected void instanceCallbackInfo(CallbackInjector.Callback var1, String var2, String var3, boolean var4) {
      this.lastId = var2;
      this.lastDesc = var3;
      this.callbackInfoVar = var1.marshalVar();
      this.callbackInfoClass = var1.target.getCallbackInfoClass();
      boolean var5 = var4 && this.totalInjections > 1 && !var1.isAtReturn && !this.cancellable;
      var1.add(new TypeInsnNode(187, this.callbackInfoClass), true, !var4, var5);
      var1.add(new InsnNode(89), true, true, var5);
      var1.add(new LdcInsnNode(var2), true, !var4, var5);
      var1.add(new InsnNode(this.cancellable ? 4 : 3), true, !var4, var5);
      if (var1.isAtReturn) {
         var1.add(new VarInsnNode(var1.target.returnType.getOpcode(21), var1.marshalVar()), true, !var4);
         var1.add(new MethodInsnNode(183, this.callbackInfoClass, "<init>", var3, false));
      } else {
         var1.add(new MethodInsnNode(183, this.callbackInfoClass, "<init>", var3, false), false, false, var5);
      }

      if (var4) {
         var1.target.addLocalVariable(this.callbackInfoVar, "callbackInfo" + this.callbackInfoVar, "L" + this.callbackInfoClass + ";");
         var1.add(new VarInsnNode(58, this.callbackInfoVar), false, false, var5);
      }

   }

   private void invokeCallback(CallbackInjector.Callback var1, MethodNode var2) {
      if (!this.isStatic) {
         var1.add(new VarInsnNode(25, 0), false, true);
      }

      if (var1.captureArgs()) {
         Bytecode.loadArgs(var1.target.arguments, var1, this.isStatic ? 0 : 1, -1);
      }

      this.loadOrCreateCallbackInfo(var1);
      if (var1.canCaptureLocals) {
         Locals.loadLocals(var1.localTypes, var1, var1.frameSize, var1.extraArgs);
      }

      this.invokeHandler(var1, var2);
   }

   private String getIdentifier(CallbackInjector.Callback var1) {
      String var2 = Strings.isNullOrEmpty(this.identifier) ? var1.target.method.name : this.identifier;
      String var3 = (String)this.ids.get(var1.node.getId());
      return var2 + (Strings.isNullOrEmpty(var3) ? "" : ":" + var3);
   }

   protected void injectCancellationCode(CallbackInjector.Callback var1) {
      if (this.cancellable) {
         var1.add(new VarInsnNode(25, this.callbackInfoVar));
         var1.add(new MethodInsnNode(182, this.callbackInfoClass, CallbackInfo.getIsCancelledMethodName(), CallbackInfo.getIsCancelledMethodSig(), false));
         LabelNode var2 = new LabelNode();
         var1.add(new JumpInsnNode(153, var2));
         this.injectReturnCode(var1);
         var1.add(var2);
      }
   }

   protected void injectReturnCode(CallbackInjector.Callback var1) {
      if (var1.target.returnType.equals(Type.VOID_TYPE)) {
         var1.add(new InsnNode(177));
      } else {
         var1.add(new VarInsnNode(25, var1.marshalVar()));
         String var2 = CallbackInfoReturnable.getReturnAccessor(var1.target.returnType);
         String var3 = CallbackInfoReturnable.getReturnDescriptor(var1.target.returnType);
         var1.add(new MethodInsnNode(182, this.callbackInfoClass, var2, var3, false));
         if (var1.target.returnType.getSort() == 10) {
            var1.add(new TypeInsnNode(192, var1.target.returnType.getInternalName()));
         }

         var1.add(new InsnNode(var1.target.returnType.getOpcode(172)));
      }

   }

   protected boolean isStatic() {
      return this.isStatic;
   }

   private static List summariseLocals(String var0, int var1) {
      return summariseLocals(Type.getArgumentTypes(var0), var1);
   }

   private static List summariseLocals(Type[] var0, int var1) {
      ArrayList var2 = new ArrayList();
      if (var0 != null) {
         for(; var1 < var0.length; ++var1) {
            if (var0[var1] != null) {
               var2.add(var0[var1].toString());
            }
         }
      }

      return var2;
   }

   static String meltSnowman(int var0, String var1) {
      return var1 != null && 9731 == var1.charAt(0) ? "var" + var0 : var1;
   }

   private class Callback extends InsnList {
      private final MethodNode handler;
      private final AbstractInsnNode head;
      final Target target;
      final InjectionNodes.InjectionNode node;
      final LocalVariableNode[] locals;
      final Type[] localTypes;
      final int frameSize;
      final int extraArgs;
      final boolean canCaptureLocals;
      final boolean isAtReturn;
      final String desc;
      final String descl;
      final String[] argNames;
      int ctor;
      int invoke;
      private int marshalVar;
      private boolean captureArgs;
      final CallbackInjector this$0;

      Callback(CallbackInjector var1, MethodNode var2, Target var3, InjectionNodes.InjectionNode var4, LocalVariableNode[] var5, boolean var6) {
         this.this$0 = var1;
         this.marshalVar = -1;
         this.captureArgs = true;
         this.handler = var2;
         this.target = var3;
         this.head = var3.insns.getFirst();
         this.node = var4;
         this.locals = var5;
         this.localTypes = var5 != null ? new Type[var5.length] : null;
         this.frameSize = Bytecode.getFirstNonArgLocalIndex(var3.arguments, !var1.isStatic());
         ArrayList var7 = null;
         if (var5 != null) {
            int var8 = var1.isStatic() ? 0 : 1;
            var7 = new ArrayList();

            for(int var9 = 0; var9 <= var5.length; ++var9) {
               if (var9 == this.frameSize) {
                  var7.add(var3.returnType == Type.VOID_TYPE ? "ci" : "cir");
               }

               if (var9 < var5.length && var5[var9] != null) {
                  this.localTypes[var9] = Type.getType(var5[var9].desc);
                  if (var9 >= var8) {
                     var7.add(CallbackInjector.meltSnowman(var9, var5[var9].name));
                  }
               }
            }
         }

         this.extraArgs = Math.max(0, Bytecode.getFirstNonArgLocalIndex(this.handler) - (this.frameSize + 1));
         this.argNames = var7 != null ? (String[])var7.toArray(new String[var7.size()]) : null;
         this.canCaptureLocals = var6 && var5 != null && var5.length > this.frameSize;
         this.isAtReturn = this.node.getCurrentTarget() instanceof InsnNode && this.isValueReturnOpcode(this.node.getCurrentTarget().getOpcode());
         this.desc = var3.getCallbackDescriptor(this.localTypes, var3.arguments);
         this.descl = var3.getCallbackDescriptor(true, this.localTypes, var3.arguments, this.frameSize, this.extraArgs);
         this.invoke = var3.arguments.length + (this.canCaptureLocals ? this.localTypes.length - this.frameSize : 0);
      }

      private boolean isValueReturnOpcode(int var1) {
         return var1 >= 172 && var1 < 177;
      }

      String getDescriptor() {
         return this.canCaptureLocals ? this.descl : this.desc;
      }

      String getDescriptorWithAllLocals() {
         return this.target.getCallbackDescriptor(true, this.localTypes, this.target.arguments, this.frameSize, 32767);
      }

      String getCallbackInfoConstructorDescriptor() {
         return this.isAtReturn ? CallbackInfo.getConstructorDescriptor(this.target.returnType) : CallbackInfo.getConstructorDescriptor();
      }

      void add(AbstractInsnNode var1, boolean var2, boolean var3) {
         this.add(var1, var2, var3, false);
      }

      void add(AbstractInsnNode var1, boolean var2, boolean var3, boolean var4) {
         if (var4) {
            this.target.insns.insertBefore(this.head, var1);
         } else {
            this.add(var1);
         }

         this.ctor += var2 ? 1 : 0;
         this.invoke += var3 ? 1 : 0;
      }

      void inject() {
         this.target.insertBefore((InjectionNodes.InjectionNode)this.node, this);
         this.target.addToStack(Math.max(this.invoke, this.ctor));
      }

      boolean checkDescriptor(String var1) {
         if (this.getDescriptor().equals(var1)) {
            return true;
         } else if (this.target.getSimpleCallbackDescriptor().equals(var1) && !this.canCaptureLocals) {
            this.captureArgs = false;
            return true;
         } else {
            Type[] var2 = Type.getArgumentTypes(var1);
            Type[] var3 = Type.getArgumentTypes(this.descl);
            if (var2.length != var3.length) {
               return false;
            } else {
               for(int var4 = 0; var4 < var3.length; ++var4) {
                  Type var5 = var2[var4];
                  if (!var5.equals(var3[var4])) {
                     if (var5.getSort() == 9) {
                        return false;
                     }

                     if (Annotations.getInvisibleParameter(this.handler, Coerce.class, var4) == null) {
                        return false;
                     }

                     if (!Injector.canCoerce(var2[var4], var3[var4])) {
                        return false;
                     }
                  }
               }

               return true;
            }
         }
      }

      boolean captureArgs() {
         return this.captureArgs;
      }

      int marshalVar() {
         if (this.marshalVar < 0) {
            this.marshalVar = this.target.allocateLocal();
         }

         return this.marshalVar;
      }
   }
}
