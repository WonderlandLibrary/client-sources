package cc.novoline.modules.misc;

import cc.novoline.modules.misc.AutoHypixel;
import cc.novoline.utils.tasks.FutureTask;
import net.minecraft.network.play.client.C01PacketChatMessage;

class AutoHypixel$1 extends FutureTask {
   final String val$split;
   final AutoHypixel this$0;

   AutoHypixel$1(AutoHypixel var1, int var2, String var3) {
      super(var2);
      this.this$0 = var1;
      this.val$split = var3;
   }

   public void execute() {
      this.this$0.sendPacket(new C01PacketChatMessage(this.val$split));
   }

   public void run() {
   }
}
