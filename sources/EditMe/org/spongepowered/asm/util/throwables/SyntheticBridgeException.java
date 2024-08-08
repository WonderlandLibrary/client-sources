package org.spongepowered.asm.util.throwables;

import java.util.ListIterator;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.PrettyPrinter;

public class SyntheticBridgeException extends MixinException {
   private static final long serialVersionUID = 1L;
   private final SyntheticBridgeException.Problem problem;
   private final String name;
   private final String desc;
   private final int index;
   private final AbstractInsnNode a;
   private final AbstractInsnNode b;

   public SyntheticBridgeException(SyntheticBridgeException.Problem var1, String var2, String var3, int var4, AbstractInsnNode var5, AbstractInsnNode var6) {
      super(var1.getMessage(var2, var3, var4, var5, var6));
      this.problem = var1;
      this.name = var2;
      this.desc = var3;
      this.index = var4;
      this.a = var5;
      this.b = var6;
   }

   public void printAnalysis(IMixinContext var1, MethodNode var2, MethodNode var3) {
      PrettyPrinter var4 = new PrettyPrinter();
      var4.addWrapped(100, this.getMessage()).hr();
      var4.add().kv("Method", this.name + this.desc).kv("Problem Type", this.problem).add().hr();
      String var5 = (String)Annotations.getValue(Annotations.getVisible(var2, MixinMerged.class), "mixin");
      String var6 = var5 != null ? var5 : var1.getTargetClassRef().replace('/', '.');
      this.printMethod(var4.add("Existing method").add().kv("Owner", var6).add(), var2).hr();
      this.printMethod(var4.add("Incoming method").add().kv("Owner", var1.getClassRef().replace('/', '.')).add(), var3).hr();
      this.printProblem(var4, var1, var2, var3).print(System.err);
   }

   private PrettyPrinter printMethod(PrettyPrinter var1, MethodNode var2) {
      int var3 = 0;

      for(ListIterator var4 = var2.instructions.iterator(); var4.hasNext(); ++var3) {
         var1.kv(var3 == this.index ? ">>>>" : "", Bytecode.describeNode((AbstractInsnNode)var4.next()));
      }

      return var1.add();
   }

   private PrettyPrinter printProblem(PrettyPrinter var1, IMixinContext var2, MethodNode var3, MethodNode var4) {
      Type var5 = Type.getObjectType(var2.getTargetClassRef());
      var1.add("Analysis").add();
      Type var25;
      switch(this.problem) {
      case BAD_INSN:
         var1.add("The bridge methods are not compatible because they contain incompatible opcodes");
         var1.add("at index " + this.index + ":").add();
         var1.kv("Existing opcode: %s", Bytecode.getOpcodeName(this.a));
         var1.kv("Incoming opcode: %s", Bytecode.getOpcodeName(this.b)).add();
         var1.add("This implies that the bridge methods are from different interfaces. This problem");
         var1.add("may not be resolvable without changing the base interfaces.").add();
         break;
      case BAD_LOAD:
         var1.add("The bridge methods are not compatible because they contain different variables at");
         var1.add("opcode index " + this.index + ".").add();
         ListIterator var6 = var3.instructions.iterator();
         ListIterator var7 = var4.instructions.iterator();
         Type[] var8 = Type.getArgumentTypes(var3.desc);
         Type[] var9 = Type.getArgumentTypes(var4.desc);

         for(int var19 = 0; var6.hasNext() && var7.hasNext(); ++var19) {
            AbstractInsnNode var20 = (AbstractInsnNode)var6.next();
            AbstractInsnNode var21 = (AbstractInsnNode)var7.next();
            if (var20 instanceof VarInsnNode && var21 instanceof VarInsnNode) {
               VarInsnNode var22 = (VarInsnNode)var20;
               VarInsnNode var23 = (VarInsnNode)var21;
               Type var24 = var22.var > 0 ? var8[var22.var - 1] : var5;
               var25 = var23.var > 0 ? var9[var23.var - 1] : var5;
               var1.kv("Target " + var19, "%8s %-2d %s", Bytecode.getOpcodeName(var22), var22.var, var24);
               var1.kv("Incoming " + var19, "%8s %-2d %s", Bytecode.getOpcodeName(var23), var23.var, var25);
               if (var24.equals(var25)) {
                  var1.kv("", "Types match: %s", var24);
               } else if (var24.getSort() != var25.getSort()) {
                  var1.kv("", "Types are incompatible");
               } else if (var24.getSort() == 10) {
                  ClassInfo var27 = ClassInfo.getCommonSuperClassOrInterface(var24, var25);
                  var1.kv("", "Common supertype: %s", var27);
               }

               var1.add();
            }
         }

         var1.add("Since this probably means that the methods come from different interfaces, you");
         var1.add("may have a \"multiple inheritance\" problem, it may not be possible to implement");
         var1.add("both root interfaces");
         break;
      case BAD_CAST:
         var1.add("Incompatible CHECKCAST encountered at opcode " + this.index + ", this could indicate that the bridge");
         var1.add("is casting down for contravariant generic types. It may be possible to coalesce the");
         var1.add("bridges by adjusting the types in the target method.").add();
         Type var10 = Type.getObjectType(((TypeInsnNode)this.a).desc);
         Type var11 = Type.getObjectType(((TypeInsnNode)this.b).desc);
         var1.kv("Target type", var10);
         var1.kv("Incoming type", var11);
         var1.kv("Common supertype", ClassInfo.getCommonSuperClassOrInterface(var10, var11)).add();
         break;
      case BAD_INVOKE_NAME:
         var1.add("Incompatible invocation targets in synthetic bridge. This is extremely unusual");
         var1.add("and implies that a remapping transformer has incorrectly remapped a method. This");
         var1.add("is an unrecoverable error.");
         break;
      case BAD_INVOKE_DESC:
         MethodInsnNode var12 = (MethodInsnNode)this.a;
         MethodInsnNode var13 = (MethodInsnNode)this.b;
         Type[] var14 = Type.getArgumentTypes(var12.desc);
         Type[] var15 = Type.getArgumentTypes(var13.desc);
         if (var14.length != var15.length) {
            int var16 = Type.getArgumentTypes(var3.desc).length;
            String var17 = var14.length == var16 ? "The TARGET" : (var15.length == var16 ? " The INCOMING" : "NEITHER");
            var1.add("Mismatched invocation descriptors in synthetic bridge implies that a remapping");
            var1.add("transformer has incorrectly coalesced a bridge method with a conflicting name.");
            var1.add("Overlapping bridge methods should always have the same number of arguments, yet");
            var1.add("the target method has %d arguments, the incoming method has %d. This is an", var14.length, var15.length);
            var1.add("unrecoverable error. %s method has the expected arg count of %d", var17, var16);
            break;
         } else {
            var25 = Type.getReturnType(var12.desc);
            Type var26 = Type.getReturnType(var13.desc);
            var1.add("Incompatible invocation descriptors in synthetic bridge implies that generified");
            var1.add("types are incompatible over one or more generic superclasses or interfaces. It may");
            var1.add("be possible to adjust the generic types on implemented members to rectify this");
            var1.add("problem by coalescing the appropriate generic types.").add();
            this.printTypeComparison(var1, "return type", var25, var26);

            for(int var18 = 0; var18 < var14.length; ++var18) {
               this.printTypeComparison(var1, "arg " + var18, var14[var18], var15[var18]);
            }

            return var1;
         }
      case BAD_LENGTH:
         var1.add("Mismatched bridge method length implies the bridge methods are incompatible");
         var1.add("and may originate from different superinterfaces. This is an unrecoverable");
         var1.add("error.").add();
      }

      return var1;
   }

   private PrettyPrinter printTypeComparison(PrettyPrinter var1, String var2, Type var3, Type var4) {
      var1.kv("Target " + var2, "%s", var3);
      var1.kv("Incoming " + var2, "%s", var4);
      if (var3.equals(var4)) {
         var1.kv("Analysis", "Types match: %s", var3);
      } else if (var3.getSort() != var4.getSort()) {
         var1.kv("Analysis", "Types are incompatible");
      } else if (var3.getSort() == 10) {
         ClassInfo var5 = ClassInfo.getCommonSuperClassOrInterface(var3, var4);
         var1.kv("Analysis", "Common supertype: L%s;", var5);
      }

      return var1.add();
   }

   public static enum Problem {
      BAD_INSN("Conflicting opcodes %4$s and %5$s at offset %3$d in synthetic bridge method %1$s%2$s"),
      BAD_LOAD("Conflicting variable access at offset %3$d in synthetic bridge method %1$s%2$s"),
      BAD_CAST("Conflicting type cast at offset %3$d in synthetic bridge method %1$s%2$s"),
      BAD_INVOKE_NAME("Conflicting synthetic bridge target method name in synthetic bridge method %1$s%2$s Existing:%6$s Incoming:%7$s"),
      BAD_INVOKE_DESC("Conflicting synthetic bridge target method descriptor in synthetic bridge method %1$s%2$s Existing:%8$s Incoming:%9$s"),
      BAD_LENGTH("Mismatched bridge method length for synthetic bridge method %1$s%2$s unexpected extra opcode at offset %3$d");

      private final String message;
      private static final SyntheticBridgeException.Problem[] $VALUES = new SyntheticBridgeException.Problem[]{BAD_INSN, BAD_LOAD, BAD_CAST, BAD_INVOKE_NAME, BAD_INVOKE_DESC, BAD_LENGTH};

      private Problem(String var3) {
         this.message = var3;
      }

      String getMessage(String var1, String var2, int var3, AbstractInsnNode var4, AbstractInsnNode var5) {
         return String.format(this.message, var1, var2, var3, Bytecode.getOpcodeName(var4), Bytecode.getOpcodeName(var4), getInsnName(var4), getInsnName(var5), getInsnDesc(var4), getInsnDesc(var5));
      }

      private static String getInsnName(AbstractInsnNode var0) {
         return var0 instanceof MethodInsnNode ? ((MethodInsnNode)var0).name : "";
      }

      private static String getInsnDesc(AbstractInsnNode var0) {
         return var0 instanceof MethodInsnNode ? ((MethodInsnNode)var0).desc : "";
      }
   }
}
