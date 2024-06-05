package net.minecraft.client.stream;

import net.UZ;

class TwitchStream$1$1 extends Thread {
   final UZ a;

   TwitchStream$1$1(UZ var1, String var2) {
      super(var2);
      this.a = var1;
   }

   public void run() {
      this.a.b.shutdownStream();
   }
}
