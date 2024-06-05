package net.minecraft.client.gui;

import java.io.IOException;
import net.aHv;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;

public class GuiGameOver extends GuiScreen implements GuiYesNoCallback {
   private int enableButtonsTimer;
   private boolean field_146346_f = false;

   public void initGui() {
      this.buttonList.clear();
      if(this.mc.world.getWorldInfo().isHardcoreModeEnabled()) {
         if(this.mc.isIntegratedServerRunning()) {
            this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, I18n.format("deathScreen.deleteWorld", new Object[0])));
         } else {
            this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, I18n.format("deathScreen.leaveServer", new Object[0])));
         }
      } else {
         this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 72, I18n.format("deathScreen.respawn", new Object[0])));
         this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, I18n.format("deathScreen.titleScreen", new Object[0])));
         if(this.mc.getSession() == null) {
            ((GuiButton)this.buttonList.get(1)).enabled = false;
         }
      }

      for(GuiButton var2 : this.buttonList) {
         var2.enabled = false;
      }

   }

   protected void keyTyped(char var1, int var2) throws IOException {
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      switch(var1.id) {
      case 0:
         this.mc.player.respawnPlayer();
         this.mc.displayGuiScreen((GuiScreen)null);
         break;
      case 1:
         if(this.mc.world.getWorldInfo().isHardcoreModeEnabled()) {
            this.mc.displayGuiScreen(new aHv());
         } else {
            GuiYesNo var2 = new GuiYesNo(this, I18n.format("deathScreen.quit.confirm", new Object[0]), "", I18n.format("deathScreen.titleScreen", new Object[0]), I18n.format("deathScreen.respawn", new Object[0]), 0);
            this.mc.displayGuiScreen(var2);
            var2.setButtonDelay(20);
         }
      }

   }

   public void confirmClicked(boolean var1, int var2) {
      this.mc.world.sendQuittingDisconnectingPacket();
      this.mc.loadWorld((WorldClient)null);
      this.mc.displayGuiScreen(new aHv());
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawGradientRect(0.0F, 0.0F, (float)this.width, (float)this.height, 1615855616, -1602211792);
      GlStateManager.pushMatrix();
      GlStateManager.scale(2.0F, 2.0F, 2.0F);
      boolean var4 = this.mc.world.getWorldInfo().isHardcoreModeEnabled();
      String var5 = I18n.format("deathScreen.title.hardcore", new Object[0]);
      this.drawCenteredString(this.fontRendererObj, var5, this.width / 2 / 2, 30, 16777215);
      GlStateManager.popMatrix();
      this.drawCenteredString(this.fontRendererObj, I18n.format("deathScreen.hardcoreInfo", new Object[0]), this.width / 2, 144, 16777215);
      this.drawCenteredString(this.fontRendererObj, I18n.format("deathScreen.score", new Object[0]) + ": " + EnumChatFormatting.YELLOW + this.mc.player.getScore(), this.width / 2, 100, 16777215);
      super.drawScreen(var1, var2, var3);
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public void updateScreen() {
      super.updateScreen();
      ++this.enableButtonsTimer;
      if(this.enableButtonsTimer == 20) {
         for(GuiButton var2 : this.buttonList) {
            var2.enabled = true;
         }
      }

   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
