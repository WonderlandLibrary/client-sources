package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.LanServerDetector;
import net.minecraft.client.resources.I18n;

public class ServerListEntryLanDetected implements GuiListExtended.IGuiListEntry {
   private static final String __OBFID = "CL_00000816";
   protected final Minecraft field_148293_a;
   private long field_148290_d = 0L;
   private final GuiMultiplayer field_148292_c;
   protected final LanServerDetector.LanServer field_148291_b;

   public LanServerDetector.LanServer getLanServer() {
      return this.field_148291_b;
   }

   public boolean mousePressed(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.field_148292_c.selectServer(var1);
      if (Minecraft.getSystemTime() - this.field_148290_d < 250L) {
         this.field_148292_c.connectToSelected();
      }

      this.field_148290_d = Minecraft.getSystemTime();
      return false;
   }

   protected ServerListEntryLanDetected(GuiMultiplayer var1, LanServerDetector.LanServer var2) {
      this.field_148292_c = var1;
      this.field_148291_b = var2;
      this.field_148293_a = Minecraft.getMinecraft();
   }

   public void mouseReleased(int var1, int var2, int var3, int var4, int var5, int var6) {
   }

   public void drawEntry(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      this.field_148293_a.fontRendererObj.drawString(I18n.format("lanServer.title"), (double)(var2 + 32 + 3), (double)(var3 + 1), 16777215);
      this.field_148293_a.fontRendererObj.drawString(this.field_148291_b.getServerMotd(), (double)(var2 + 32 + 3), (double)(var3 + 12), 8421504);
      if (this.field_148293_a.gameSettings.hideServerAddress) {
         this.field_148293_a.fontRendererObj.drawString(I18n.format("selectServer.hiddenAddress"), (double)(var2 + 32 + 3), (double)(var3 + 12 + 11), 3158064);
      } else {
         this.field_148293_a.fontRendererObj.drawString(this.field_148291_b.getServerIpPort(), (double)(var2 + 32 + 3), (double)(var3 + 12 + 11), 3158064);
      }

   }

   public void setSelected(int var1, int var2, int var3) {
   }
}
