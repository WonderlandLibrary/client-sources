package net.minecraft.client.gui;

import exhibition.Client;
import exhibition.management.notifications.user.Notifications;
import exhibition.module.Module;
import exhibition.module.impl.combat.Killaura;
import java.io.IOException;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.C00PacketKeepAlive;

public class GuiDownloadTerrain extends GuiScreen {
   private NetHandlerPlayClient netHandlerPlayClient;
   private int progress;

   public GuiDownloadTerrain(NetHandlerPlayClient p_i45023_1_) {
      this.netHandlerPlayClient = p_i45023_1_;
   }

   protected void keyTyped(char typedChar, int keyCode) throws IOException {
   }

   public void initGui() {
      if (((Module)Client.getModuleManager().get(Killaura.class)).isEnabled()) {
         ((Module)Client.getModuleManager().get(Killaura.class)).toggle();
         Notifications.getManager().post("Server Change", "Aura was disabled.", 1500L, Notifications.Type.NOTIFY);
      }

      this.buttonList.clear();
   }

   public void updateScreen() {
      ++this.progress;
      if (this.progress % 20 == 0) {
         this.netHandlerPlayClient.addToSendQueue(new C00PacketKeepAlive());
      }

   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawBackground(0);
      this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingTerrain"), this.width / 2, this.height / 2 - 50, 16777215);
      super.drawScreen(mouseX, mouseY, partialTicks);
   }

   public boolean doesGuiPauseGame() {
      return false;
   }
}
