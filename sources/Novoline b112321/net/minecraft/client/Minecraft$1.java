package net.minecraft.client;

import net.minecraft.client.Minecraft;

class Minecraft$1 extends Thread {
   final Minecraft this$0;

   Minecraft$1(Minecraft var1, String var2) {
      super(var2);
      this.this$0 = var1;
   }

   public void run() {
      while(this.this$0.running) {
         long var10000 = 2147483647L;

         try {
            Thread.sleep(var10000);
         } catch (InterruptedException var2) {
            ;
         }
      }

   }
}
