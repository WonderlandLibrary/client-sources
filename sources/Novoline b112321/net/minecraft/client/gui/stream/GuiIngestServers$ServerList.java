package net.minecraft.client.gui.stream;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.stream.GuiIngestServers;
import net.minecraft.client.stream.IngestServerTester;
import net.minecraft.util.EnumChatFormatting;
import tv.twitch.broadcast.IngestServer;

class GuiIngestServers$ServerList extends GuiSlot {
   final GuiIngestServers this$0;

   public GuiIngestServers$ServerList(GuiIngestServers var1, Minecraft var2) {
      super(var2, var1.width, var1.height, 32, var1.height - 35, (int)((double)var2.fontRendererObj.getHeight() * 3.5D));
      this.this$0 = var1;
      this.setShowSelectionBox(false);
   }

   protected int getSize() {
      return this.mc.getTwitchStream().func_152925_v().length;
   }

   protected void elementClicked(int var1, boolean var2, int var3, int var4) {
      this.mc.gameSettings.streamPreferredServer = this.mc.getTwitchStream().func_152925_v()[var1].serverUrl;
      this.mc.gameSettings.saveOptions();
   }

   protected boolean isSelected(int var1) {
      return this.mc.getTwitchStream().func_152925_v()[var1].serverUrl.equals(this.mc.gameSettings.streamPreferredServer);
   }

   protected void drawBackground() {
   }

   protected void drawSlot(int var1, int var2, int var3, int var4, int var5, int var6) {
      IngestServer var7 = this.mc.getTwitchStream().func_152925_v()[var1];
      String var8 = var7.serverUrl.replaceAll("\\{stream_key\\}", "");
      String var9 = (int)var7.bitrateKbps + " kbps";
      String var10 = null;
      IngestServerTester var11 = this.mc.getTwitchStream().func_152932_y();
      if(var7 == var11.func_153040_c()) {
         var8 = EnumChatFormatting.GREEN + var8;
         var9 = (int)(var11.func_153030_h() * 100.0F) + "%";
      } else if(var1 < var11.func_153028_p()) {
         if(var7.bitrateKbps == 0.0F) {
            var9 = EnumChatFormatting.RED + "Down!";
         }
      } else {
         var9 = EnumChatFormatting.OBFUSCATED + "1234" + EnumChatFormatting.RESET + " kbps";
      }

      var2 = var2 - 15;
      if(this.isSelected(var1)) {
         var10 = EnumChatFormatting.BLUE + "(Preferred)";
      } else if(var7.defaultServer) {
         var10 = EnumChatFormatting.GREEN + "(Default)";
      }

      this.this$0.drawString(GuiIngestServers.access$000(this.this$0), var7.serverName, var2 + 2, var3 + 5, 16777215);
      this.this$0.drawString(GuiIngestServers.access$100(this.this$0), var8, var2 + 2, var3 + GuiIngestServers.access$200(this.this$0).getHeight() + 5 + 3, 3158064);
      this.this$0.drawString(GuiIngestServers.access$300(this.this$0), var9, this.getScrollBarX() - 5 - GuiIngestServers.access$400(this.this$0).d(var9), var3 + 5, 8421504);
      this.this$0.drawString(GuiIngestServers.access$500(this.this$0), var10, this.getScrollBarX() - 5 - GuiIngestServers.access$600(this.this$0).d(var10), var3 + 5 + 3 + GuiIngestServers.access$700(this.this$0).getHeight(), 8421504);
   }

   protected int getScrollBarX() {
      return super.getScrollBarX() + 15;
   }
}
