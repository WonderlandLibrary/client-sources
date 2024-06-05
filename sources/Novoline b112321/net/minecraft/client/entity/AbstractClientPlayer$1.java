package net.minecraft.client.entity;

import cc.novoline.modules.misc.Killsults;
import cc.novoline.utils.tasks.FutureTask;
import net.minecraft.entity.player.EntityPlayer;

class AbstractClientPlayer$1 extends FutureTask {
   final EntityPlayer e;
   final Killsults a;

   AbstractClientPlayer$1(Killsults var1, int var2, EntityPlayer var3) {
      super(var2);
      this.a = var1;
      this.e = var3;
   }

   public void execute() {
      Killsults.a(this.a, this.e);
   }

   public void run() {
   }
}
