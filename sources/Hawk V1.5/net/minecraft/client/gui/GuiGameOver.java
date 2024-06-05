package net.minecraft.client.gui;

import java.io.IOException;
import java.util.Iterator;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;

public class GuiGameOver extends GuiScreen implements GuiYesNoCallback {
   private static final String __OBFID = "CL_00000690";
   private int field_146347_a;
   private boolean field_146346_f = false;

   protected void actionPerformed(GuiButton var1) throws IOException {
      switch(var1.id) {
      case 0:
         this.mc.thePlayer.respawnPlayer();
         this.mc.displayGuiScreen((GuiScreen)null);
         break;
      case 1:
         GuiYesNo var2 = new GuiYesNo(this, I18n.format("deathScreen.quit.confirm"), "", I18n.format("deathScreen.titleScreen"), I18n.format("deathScreen.respawn"), 0);
         this.mc.displayGuiScreen(var2);
         var2.setButtonDelay(20);
      }

   }

   public void confirmClicked(boolean var1, int var2) {
      if (var1) {
         this.mc.theWorld.sendQuittingDisconnectingPacket();
         this.mc.loadWorld((WorldClient)null);
         this.mc.displayGuiScreen(new GuiMainMenu());
      } else {
         this.mc.thePlayer.respawnPlayer();
         this.mc.displayGuiScreen((GuiScreen)null);
      }

   }

   public void initGui() {
      this.buttonList.clear();
      if (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
         if (this.mc.isIntegratedServerRunning()) {
            this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, I18n.format("deathScreen.deleteWorld")));
         } else {
            this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, I18n.format("deathScreen.leaveServer")));
         }
      } else {
         this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 72, I18n.format("deathScreen.respawn")));
         this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, I18n.format("deathScreen.titleScreen")));
         if (this.mc.getSession() == null) {
            ((GuiButton)this.buttonList.get(1)).enabled = false;
         }
      }

      GuiButton var1;
      for(Iterator var2 = this.buttonList.iterator(); var2.hasNext(); var1.enabled = false) {
         var1 = (GuiButton)var2.next();
      }

   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawGradientRect(0, 0, this.width, this.height, 1615855616, -1602211792);
      GlStateManager.pushMatrix();
      GlStateManager.scale(2.0F, 2.0F, 2.0F);
      boolean var4 = this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled();
      String var5 = var4 ? I18n.format("deathScreen.title.hardcore") : I18n.format("deathScreen.title");
      this.drawCenteredString(this.fontRendererObj, var5, this.width / 2 / 2, 30, 16777215);
      GlStateManager.popMatrix();
      if (var4) {
         this.drawCenteredString(this.fontRendererObj, I18n.format("deathScreen.hardcoreInfo"), this.width / 2, 144, 16777215);
      }

      this.drawCenteredString(this.fontRendererObj, String.valueOf((new StringBuilder(String.valueOf(I18n.format("deathScreen.score")))).append(": ").append(EnumChatFormatting.YELLOW).append(this.mc.thePlayer.getScore())), this.width / 2, 100, 16777215);
      super.drawScreen(var1, var2, var3);
   }

   public void updateScreen() {
      super.updateScreen();
      ++this.field_146347_a;
      GuiButton var1;
      if (this.field_146347_a == 20) {
         for(Iterator var2 = this.buttonList.iterator(); var2.hasNext(); var1.enabled = true) {
            var1 = (GuiButton)var2.next();
         }
      }

   }

   protected void keyTyped(char var1, int var2) throws IOException {
   }
}
