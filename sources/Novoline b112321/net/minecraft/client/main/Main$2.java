package net.minecraft.client.main;

import net.minecraft.client.Minecraft;

final class Main$2 extends Thread {
   Main$2(String var1) {
      super(var1);
   }

   public void run() {
      Minecraft.stopIntegratedServer();
   }
}
