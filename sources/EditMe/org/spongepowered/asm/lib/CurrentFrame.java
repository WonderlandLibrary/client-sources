package org.spongepowered.asm.lib;

class CurrentFrame extends Frame {
   void execute(int var1, int var2, ClassWriter var3, Item var4) {
      super.execute(var1, var2, var3, var4);
      Frame var5 = new Frame();
      this.merge(var3, var5, 0);
      this.set(var5);
      this.owner.inputStackTop = 0;
   }
}
