package net.minecraft.server;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IProgressUpdate;

class MinecraftServer$1 implements IProgressUpdate {
   private long startTime;
   final MinecraftServer this$0;

   MinecraftServer$1(MinecraftServer var1) {
      this.this$0 = var1;
      this.startTime = System.currentTimeMillis();
   }

   public void displaySavingString(String var1) {
   }

   public void resetProgressAndMessage(String var1) {
   }

   public void setLoadingProgress(int var1) {
      if(System.currentTimeMillis() - this.startTime >= 1000L) {
         this.startTime = System.currentTimeMillis();
         MinecraftServer.access$000().info("Converting... " + var1 + "%");
      }

   }

   public void setDoneWorking() {
   }

   public void displayLoadingString(String var1) {
   }
}
