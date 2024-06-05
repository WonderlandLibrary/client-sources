package net.minecraft.client.gui;

import java.io.IOException;
import net.aHM;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction$Action;

public class GuiSleepMP extends aHM {
   public void initGui() {
      super.initGui();
      this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height - 40, I18n.format("multiplayer.stopSleeping", new Object[0])));
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if(var2 == 1) {
         this.wakeFromSleep();
      } else if(var2 != 28 && var2 != 156) {
         super.keyTyped(var1, var2);
      } else {
         String var3 = this.T.getText().trim();
         if(!var3.isEmpty()) {
            this.mc.player.c(var3);
         }

         this.T.setText("");
         this.mc.ingameGUI.n().e();
      }

   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if(var1.id == 1) {
         this.wakeFromSleep();
      } else {
         super.actionPerformed(var1);
      }

   }

   private void wakeFromSleep() {
      NetHandlerPlayClient var1 = this.mc.player.connection;
      var1.b(new C0BPacketEntityAction(this.mc.player, C0BPacketEntityAction$Action.STOP_SLEEPING));
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
