package net.minecraft.client.audio;

import net.aeH;
import paulscode.sound.SoundSystemLogger;

class SoundManager$1 extends SoundSystemLogger {
   final aeH a;

   SoundManager$1(aeH var1) {
      this.a = var1;
   }

   public void message(String var1, int var2) {
      if(!var1.isEmpty()) {
         aeH.b().info(var1);
      }

   }

   public void importantMessage(String var1, int var2) {
      if(!var1.isEmpty()) {
         aeH.b().warn(var1);
      }

   }

   public void errorMessage(String var1, String var2, int var3) {
      if(!var2.isEmpty()) {
         aeH.b().error("Error in class \'" + var1 + "\'");
         aeH.b().error(var2);
      }

   }
}
