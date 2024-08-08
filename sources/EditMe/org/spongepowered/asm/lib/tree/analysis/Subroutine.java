package org.spongepowered.asm.lib.tree.analysis;

import java.util.ArrayList;
import java.util.List;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;

class Subroutine {
   LabelNode start;
   boolean[] access;
   List callers;

   private Subroutine() {
   }

   Subroutine(LabelNode var1, int var2, JumpInsnNode var3) {
      this.start = var1;
      this.access = new boolean[var2];
      this.callers = new ArrayList();
      this.callers.add(var3);
   }

   public Subroutine copy() {
      Subroutine var1 = new Subroutine();
      var1.start = this.start;
      var1.access = new boolean[this.access.length];
      System.arraycopy(this.access, 0, var1.access, 0, this.access.length);
      var1.callers = new ArrayList(this.callers);
      return var1;
   }

   public boolean merge(Subroutine var1) throws AnalyzerException {
      boolean var2 = false;

      int var3;
      for(var3 = 0; var3 < this.access.length; ++var3) {
         if (var1.access[var3] && !this.access[var3]) {
            this.access[var3] = true;
            var2 = true;
         }
      }

      if (var1.start == this.start) {
         for(var3 = 0; var3 < var1.callers.size(); ++var3) {
            JumpInsnNode var4 = (JumpInsnNode)var1.callers.get(var3);
            if (!this.callers.contains(var4)) {
               this.callers.add(var4);
               var2 = true;
            }
         }
      }

      return var2;
   }
}
