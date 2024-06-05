package net.minecraft.client.gui;

import cc.novoline.Novoline;
import cc.novoline.modules.visual.ClickGUI;
import net.aHv;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.resources.I18n;

public class GuiIngameMenu extends GuiScreen {
   private int field_146445_a;
   private int field_146444_f;

   public void initGui() {
      this.field_146445_a = 0;
      this.buttonList.clear();
      byte var1 = -16;
      boolean var2 = true;
      this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + var1, I18n.format("menu.returnToMenu", new Object[0])));
      if(!this.mc.isIntegratedServerRunning()) {
         ((GuiButton)this.buttonList.get(0)).displayString = I18n.format("menu.disconnect", new Object[0]);
      }

      this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24 + var1, I18n.format("menu.returnToGame", new Object[0])));
      this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + var1, 98, 20, I18n.format("menu.options", new Object[0])));
      GuiButton var3;
      this.buttonList.add(var3 = new GuiButton(7, this.width / 2 + 2, this.height / 4 + 96 + var1, 98, 20, I18n.format("menu.shareToLan", new Object[0])));
      this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 48 + var1, 98, 20, I18n.format("gui.achievements", new Object[0])));
      this.buttonList.add(new GuiButton(6, this.width / 2 + 2, this.height / 4 + 48 + var1, 98, 20, I18n.format("gui.stats", new Object[0])));
      GuiButton var4;
      this.buttonList.add(var4 = new GuiButton(8, this.width / 2 - 100, this.height / 4 + 73 + var1, "Reconnect"));
      boolean var5 = this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic();
      var4.enabled = true;
      var3.enabled = var5;
   }

   protected void actionPerformed(GuiButton var1) {
      switch(var1.id) {
      case 0:
         this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
         break;
      case 1:
         ClickGUI var2 = (ClickGUI)Novoline.getInstance().getModuleManager().getModule(ClickGUI.class);
         var2.setMwPort(false);
         var2.b();
         if(this.mc.world != null) {
            this.mc.world.sendQuittingDisconnectingPacket();
            this.mc.world = null;
         }

         this.mc.displayGuiScreen(new aHv());
         var1.enabled = false;
      case 2:
      case 3:
      default:
         break;
      case 4:
         this.mc.displayGuiScreen((GuiScreen)null);
         this.mc.setIngameFocus();
         break;
      case 5:
         this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.player.getStatFileWriter()));
         break;
      case 6:
         this.mc.displayGuiScreen(new GuiStats(this, this.mc.player.getStatFileWriter()));
         break;
      case 7:
         this.mc.displayGuiScreen(new GuiShareToLan(this));
         break;
      case 8:
         this.mc.world.sendQuittingDisconnectingPacket();
         this.mc.displayGuiScreen(new GuiConnecting(this.mc.previousScreen, this.mc, this.mc.getCurrentServerData()));
      }

   }

   public void updateScreen() {
      super.updateScreen();
      ++this.field_146444_f;
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, I18n.format("menu.game", new Object[0]), this.width / 2, 40, 16777215);
      super.drawScreen(var1, var2, var3);
   }
}
