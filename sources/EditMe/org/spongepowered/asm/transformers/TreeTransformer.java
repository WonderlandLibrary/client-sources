package org.spongepowered.asm.transformers;

import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.service.ILegacyClassTransformer;

public abstract class TreeTransformer implements ILegacyClassTransformer {
   private ClassReader classReader;
   private ClassNode classNode;

   protected final ClassNode readClass(byte[] var1) {
      return this.readClass(var1, true);
   }

   protected final ClassNode readClass(byte[] var1, boolean var2) {
      ClassReader var3 = new ClassReader(var1);
      if (var2) {
         this.classReader = var3;
      }

      ClassNode var4 = new ClassNode();
      var3.accept(var4, 8);
      return var4;
   }

   protected final byte[] writeClass(ClassNode var1) {
      MixinClassWriter var2;
      if (this.classReader != null && this.classNode == var1) {
         this.classNode = null;
         var2 = new MixinClassWriter(this.classReader, 3);
         this.classReader = null;
         var1.accept(var2);
         return var2.toByteArray();
      } else {
         this.classNode = null;
         var2 = new MixinClassWriter(3);
         var1.accept(var2);
         return var2.toByteArray();
      }
   }
}
