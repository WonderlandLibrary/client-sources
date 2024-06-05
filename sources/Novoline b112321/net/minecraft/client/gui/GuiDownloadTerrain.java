package net.minecraft.client.gui;

import cc.novoline.events.EventManager;
import cc.novoline.events.events.LoadWorldEvent;
import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.C00PacketKeepAlive;

public class GuiDownloadTerrain extends GuiScreen {
   private final NetHandlerPlayClient netHandlerPlayClient;
   private int progress;

   public GuiDownloadTerrain(NetHandlerPlayClient var1) {
      this.netHandlerPlayClient = var1;
   }

   protected void keyTyped(char var1, int var2) throws IOException {
   }

   public void initGui() {
      EventManager.call(new LoadWorldEvent());
      this.buttonList.clear();
   }

   public void updateScreen() {
      ++this.progress;
      if(this.progress % 20 == 0) {
         this.netHandlerPlayClient.b(new C00PacketKeepAlive());
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawBackground(0);
      this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingTerrain", new Object[0]), this.width / 2, this.height / 2 - 50, 16777215);
      super.drawScreen(var1, var2, var3);
   }

   public boolean doesGuiPauseGame() {
      return false;
   }
}
