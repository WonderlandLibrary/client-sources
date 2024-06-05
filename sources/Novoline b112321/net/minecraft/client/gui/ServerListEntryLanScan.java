package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended$IGuiListEntry;
import net.minecraft.client.resources.I18n;

public class ServerListEntryLanScan implements GuiListExtended$IGuiListEntry {
   private final Minecraft mc = Minecraft.getInstance();

   public void drawEntry(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      int var9 = var3 + var5 / 2 - this.mc.fontRendererObj.getHeight() / 2;
      this.mc.fontRendererObj.drawString(I18n.format("lanServer.scanning", new Object[0]), (float)(this.mc.currentScreen.width / 2 - this.mc.fontRendererObj.d(I18n.format("lanServer.scanning", new Object[0])) / 2), (float)var9, 16777215);
      String var10;
      switch((int)(Minecraft.getSystemTime() / 300L % 4L)) {
      case 0:
      default:
         var10 = "O o o";
         break;
      case 1:
      case 3:
         var10 = "o O o";
         break;
      case 2:
         var10 = "o o O";
      }

      this.mc.fontRendererObj.drawString(var10, (float)(this.mc.currentScreen.width / 2 - this.mc.fontRendererObj.d(var10) / 2), (float)(var9 + this.mc.fontRendererObj.getHeight()), 8421504);
   }

   public void setSelected(int var1, int var2, int var3) {
   }

   public boolean mousePressed(int var1, int var2, int var3, int var4, int var5, int var6) {
      return false;
   }

   public void mouseReleased(int var1, int var2, int var3, int var4, int var5, int var6) {
   }
}
