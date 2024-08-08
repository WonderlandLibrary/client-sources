package org.spongepowered.asm.mixin.transformer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
import org.spongepowered.asm.transformers.MixinClassWriter;
import org.spongepowered.asm.transformers.TreeTransformer;
import org.spongepowered.asm.util.Bytecode;

class MixinPostProcessor extends TreeTransformer implements MixinConfig.IListener {
   private final Set syntheticInnerClasses = new HashSet();
   private final Map accessorMixins = new HashMap();
   private final Set loadable = new HashSet();

   public void onInit(MixinInfo var1) {
      Iterator var2 = var1.getSyntheticInnerClasses().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         this.registerSyntheticInner(var3.replace('/', '.'));
      }

   }

   public void onPrepare(MixinInfo var1) {
      String var2 = var1.getClassName();
      if (var1.isLoadable()) {
         this.registerLoadable(var2);
      }

      if (var1.isAccessor()) {
         this.registerAccessor(var1);
      }

   }

   void registerSyntheticInner(String var1) {
      this.syntheticInnerClasses.add(var1);
   }

   void registerLoadable(String var1) {
      this.loadable.add(var1);
   }

   void registerAccessor(MixinInfo var1) {
      this.registerLoadable(var1.getClassName());
      this.accessorMixins.put(var1.getClassName(), var1);
   }

   boolean canTransform(String var1) {
      return this.syntheticInnerClasses.contains(var1) || this.loadable.contains(var1);
   }

   public String getName() {
      return this.getClass().getName();
   }

   public boolean isDelegationExcluded() {
      return true;
   }

   public byte[] transformClassBytes(String var1, String var2, byte[] var3) {
      if (this.syntheticInnerClasses.contains(var2)) {
         return this.processSyntheticInner(var3);
      } else if (this.accessorMixins.containsKey(var2)) {
         MixinInfo var4 = (MixinInfo)this.accessorMixins.get(var2);
         return this.processAccessor(var3, var4);
      } else {
         return var3;
      }
   }

   private byte[] processSyntheticInner(byte[] var1) {
      ClassReader var2 = new ClassReader(var1);
      MixinClassWriter var3 = new MixinClassWriter(var2, 0);
      ClassVisitor var4 = new ClassVisitor(this, 327680, var3) {
         final MixinPostProcessor this$0;

         {
            this.this$0 = var1;
         }

         public void visit(int var1, int var2, String var3, String var4, String var5, String[] var6) {
            super.visit(var1, var2 | 1, var3, var4, var5, var6);
         }

         public FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5) {
            if ((var1 & 6) == 0) {
               var1 |= 1;
            }

            return super.visitField(var1, var2, var3, var4, var5);
         }

         public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
            if ((var1 & 6) == 0) {
               var1 |= 1;
            }

            return super.visitMethod(var1, var2, var3, var4, var5);
         }
      };
      var2.accept(var4, 8);
      return var3.toByteArray();
   }

   private byte[] processAccessor(byte[] var1, MixinInfo var2) {
      if (!MixinEnvironment.getCompatibilityLevel().isAtLeast(MixinEnvironment.CompatibilityLevel.JAVA_8)) {
         return var1;
      } else {
         boolean var3 = false;
         MixinInfo.MixinClassNode var4 = var2.getClassNode(0);
         ClassInfo var5 = (ClassInfo)var2.getTargets().get(0);
         Iterator var6 = var4.mixinMethods.iterator();

         while(true) {
            MixinInfo.MixinMethodNode var7;
            AnnotationNode var8;
            AnnotationNode var9;
            do {
               do {
                  if (!var6.hasNext()) {
                     if (var3) {
                        return this.writeClass(var4);
                     }

                     return var1;
                  }

                  var7 = (MixinInfo.MixinMethodNode)var6.next();
               } while(!Bytecode.hasFlag((MethodNode)var7, 8));

               var8 = var7.getVisibleAnnotation(Accessor.class);
               var9 = var7.getVisibleAnnotation(Invoker.class);
            } while(var8 == null && var9 == null);

            ClassInfo.Method var10 = getAccessorMethod(var2, var7, var5);
            createProxy(var7, var5, var10);
            var3 = true;
         }
      }
   }

   private static ClassInfo.Method getAccessorMethod(MixinInfo var0, MethodNode var1, ClassInfo var2) throws MixinTransformerError {
      ClassInfo.Method var3 = var0.getClassInfo().findMethod((MethodNode)var1, 10);
      if (!var3.isRenamed()) {
         throw new MixinTransformerError("Unexpected state: " + var0 + " loaded before " + var2 + " was conformed");
      } else {
         return var3;
      }
   }

   private static void createProxy(MethodNode var0, ClassInfo var1, ClassInfo.Method var2) {
      var0.instructions.clear();
      Type[] var3 = Type.getArgumentTypes(var0.desc);
      Type var4 = Type.getReturnType(var0.desc);
      Bytecode.loadArgs(var3, var0.instructions, 0);
      var0.instructions.add((AbstractInsnNode)(new MethodInsnNode(184, var1.getName(), var2.getName(), var0.desc, false)));
      var0.instructions.add((AbstractInsnNode)(new InsnNode(var4.getOpcode(172))));
      var0.maxStack = Bytecode.getFirstNonArgLocalIndex(var3, false);
      var0.maxLocals = 0;
   }
}
