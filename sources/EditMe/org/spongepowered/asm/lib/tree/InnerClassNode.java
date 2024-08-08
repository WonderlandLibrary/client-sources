package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.ClassVisitor;

public class InnerClassNode {
   public String name;
   public String outerName;
   public String innerName;
   public int access;

   public InnerClassNode(String var1, String var2, String var3, int var4) {
      this.name = var1;
      this.outerName = var2;
      this.innerName = var3;
      this.access = var4;
   }

   public void accept(ClassVisitor var1) {
      var1.visitInnerClass(this.name, this.outerName, this.innerName, this.access);
   }
}
