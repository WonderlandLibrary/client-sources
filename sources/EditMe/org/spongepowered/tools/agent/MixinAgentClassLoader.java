package org.spongepowered.tools.agent;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.ClassWriter;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.mixin.MixinEnvironment;

class MixinAgentClassLoader extends ClassLoader {
   private static final Logger logger = LogManager.getLogger("mixin.agent");
   private Map mixins = new HashMap();
   private Map targets = new HashMap();

   void addMixinClass(String var1) {
      logger.debug("Mixin class {} added to class loader", new Object[]{var1});

      try {
         byte[] var2 = this.materialise(var1);
         Class var3 = this.defineClass(var1, var2, 0, var2.length);
         var3.newInstance();
         this.mixins.put(var3, var2);
      } catch (Throwable var4) {
         logger.catching(var4);
      }

   }

   void addTargetClass(String var1, byte[] var2) {
      this.targets.put(var1, var2);
   }

   byte[] getFakeMixinBytecode(Class var1) {
      return (byte[])this.mixins.get(var1);
   }

   byte[] getOriginalTargetBytecode(String var1) {
      return (byte[])this.targets.get(var1);
   }

   private byte[] materialise(String var1) {
      ClassWriter var2 = new ClassWriter(3);
      var2.visit(MixinEnvironment.getCompatibilityLevel().classVersion(), 1, var1.replace('.', '/'), (String)null, Type.getInternalName(Object.class), (String[])null);
      MethodVisitor var3 = var2.visitMethod(1, "<init>", "()V", (String)null, (String[])null);
      var3.visitCode();
      var3.visitVarInsn(25, 0);
      var3.visitMethodInsn(183, Type.getInternalName(Object.class), "<init>", "()V", false);
      var3.visitInsn(177);
      var3.visitMaxs(1, 1);
      var3.visitEnd();
      var2.visitEnd();
      return var2.toByteArray();
   }
}
