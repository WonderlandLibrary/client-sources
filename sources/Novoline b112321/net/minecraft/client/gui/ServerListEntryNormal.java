package net.minecraft.client.gui;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiListExtended$IGuiListEntry;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerListEntryNormal implements GuiListExtended$IGuiListEntry {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final ThreadPoolExecutor field_148302_b = new ScheduledThreadPoolExecutor(5, (new ThreadFactoryBuilder()).setNameFormat("Server Pinger #%d").setDaemon(true).build());
   private static final ResourceLocation UNKNOWN_SERVER = new ResourceLocation("textures/misc/unknown_server.png");
   private static final ResourceLocation SERVER_SELECTION_BUTTONS = new ResourceLocation("textures/gui/server_selection.png");
   private final GuiMultiplayer field_148303_c;
   private final Minecraft mc;
   private final ServerData field_148301_e;
   private final ResourceLocation field_148306_i;
   private String field_148299_g;
   private DynamicTexture field_148305_h;
   private long field_148298_f;

   protected ServerListEntryNormal(GuiMultiplayer var1, ServerData var2) {
      this.field_148303_c = var1;
      this.field_148301_e = var2;
      this.mc = Minecraft.getInstance();
      this.field_148306_i = new ResourceLocation("servers/" + var2.serverIP + "/icon");
      this.field_148305_h = (DynamicTexture)this.mc.getTextureManager().getTexture(this.field_148306_i);
   }

   public void drawEntry(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      if(!this.field_148301_e.field_78841_f) {
         this.field_148301_e.field_78841_f = true;
         this.field_148301_e.b = -2L;
         this.field_148301_e.serverMOTD = "";
         this.field_148301_e.h = "";
         field_148302_b.submit(this::lambda$drawEntry$0);
      }

      boolean var9 = this.field_148301_e.version > 47;
      boolean var10 = this.field_148301_e.version < 47;
      boolean var11 = true;
      this.mc.fontRendererObj.drawString(this.field_148301_e.serverName, (float)(var2 + 32 + 3), (float)(var3 + 1), 16777215);
      List var12 = this.mc.fontRendererObj.listFormattedStringToWidth(this.field_148301_e.serverMOTD, var4 - 32 - 2);

      for(int var13 = 0; var13 < Math.min(var12.size(), 2); ++var13) {
         this.mc.fontRendererObj.drawString((String)var12.get(var13), (float)(var2 + 32 + 3), (float)(var3 + 12 + this.mc.fontRendererObj.getHeight() * var13), 8421504);
      }

      String var23 = EnumChatFormatting.DARK_RED + this.field_148301_e.gameVersion;
      int var14 = this.mc.fontRendererObj.d(var23);
      this.mc.fontRendererObj.drawString(var23, (float)(var2 + var4 - var14 - 15 - 2), (float)(var3 + 1), 8421504);
      byte var15 = 0;
      String var16 = null;
      byte var17 = 5;
      String var18 = "Client out of date!";
      var16 = this.field_148301_e.playerList;
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(Gui.icons);
      Gui.drawModalRectWithCustomSizedTexture(var2 + var4 - 15, var3, (float)(var15 * 10), (float)(176 + var17 * 8), 10, 8, 256.0F, 256.0F);
      if(this.field_148301_e.getBase64EncodedIconData() != null && !this.field_148301_e.getBase64EncodedIconData().equals(this.field_148299_g)) {
         this.field_148299_g = this.field_148301_e.getBase64EncodedIconData();
         this.prepareServerIcon();
         this.field_148303_c.getServerList().saveServerList();
      }

      if(this.field_148305_h != null) {
         this.func_178012_a(var2, var3, this.field_148306_i);
      } else {
         this.func_178012_a(var2, var3, UNKNOWN_SERVER);
      }

      int var19 = var6 - var2;
      int var20 = var7 - var3;
      if(var19 >= var4 - 15 && var19 <= var4 - 5 && var20 <= 8) {
         this.field_148303_c.setHoveringText(var18);
      } else if(var19 >= var4 - var14 - 15 - 2 && var19 <= var4 - 15 - 2 && var20 <= 8) {
         this.field_148303_c.setHoveringText(var16);
      }

      if(!this.mc.gameSettings.touchscreen) {
         ;
      }

      this.mc.getTextureManager().bindTexture(SERVER_SELECTION_BUTTONS);
      Gui.drawRect(var2, var3, var2 + 32, var3 + 32, -1601138544);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      int var21 = var6 - var2;
      int var22 = var7 - var3;
      if(this.func_178013_b()) {
         if(var21 < 32 && var21 > 16) {
            Gui.drawModalRectWithCustomSizedTexture(var2, var3, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
         } else {
            Gui.drawModalRectWithCustomSizedTexture(var2, var3, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
         }
      }

      if(this.field_148303_c.func_175392_a(this, var1)) {
         if(var21 < 16 && var22 < 16) {
            Gui.drawModalRectWithCustomSizedTexture(var2, var3, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
         } else {
            Gui.drawModalRectWithCustomSizedTexture(var2, var3, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
         }
      }

      if(this.field_148303_c.func_175394_b(this, var1)) {
         if(var21 < 16 && var22 > 16) {
            Gui.drawModalRectWithCustomSizedTexture(var2, var3, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
         } else {
            Gui.drawModalRectWithCustomSizedTexture(var2, var3, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
         }
      }

   }

   protected void func_178012_a(int var1, int var2, ResourceLocation var3) {
      this.mc.getTextureManager().bindTexture(var3);
      GlStateManager.enableBlend();
      Gui.drawModalRectWithCustomSizedTexture(var1, var2, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
      GlStateManager.disableBlend();
   }

   private boolean func_178013_b() {
      return true;
   }

   private void prepareServerIcon() {
      // $FF: Couldn't be decompiled
   }

   public boolean mousePressed(int var1, int var2, int var3, int var4, int var5, int var6) {
      if(var5 <= 32) {
         if(var5 < 32 && var5 > 16 && this.func_178013_b()) {
            this.field_148303_c.selectServer(var1);
            this.field_148303_c.connectToSelected();
            return true;
         }

         if(var5 < 16 && var6 < 16 && this.field_148303_c.func_175392_a(this, var1)) {
            this.field_148303_c.func_175391_a(this, var1, GuiScreen.isShiftKeyDown());
            return true;
         }

         if(var5 < 16 && var6 > 16 && this.field_148303_c.func_175394_b(this, var1)) {
            this.field_148303_c.func_175393_b(this, var1, GuiScreen.isShiftKeyDown());
            return true;
         }
      }

      this.field_148303_c.selectServer(var1);
      if(Minecraft.getSystemTime() - this.field_148298_f < 250L) {
         this.field_148303_c.connectToSelected();
      }

      this.field_148298_f = Minecraft.getSystemTime();
      return false;
   }

   public void setSelected(int var1, int var2, int var3) {
   }

   public void mouseReleased(int var1, int var2, int var3, int var4, int var5, int var6) {
   }

   public ServerData getServerData() {
      return this.field_148301_e;
   }

   private void lambda$drawEntry$0() {
      try {
         this.field_148303_c.getOldServerPinger().ping(this.field_148301_e);
      } catch (UnknownHostException var2) {
         this.field_148301_e.b = -1L;
         this.field_148301_e.serverMOTD = EnumChatFormatting.DARK_RED + "Can\'t resolve hostname";
      } catch (Exception var3) {
         this.field_148301_e.b = -1L;
         this.field_148301_e.serverMOTD = EnumChatFormatting.DARK_RED + "Can\'t connect to server.";
      }

   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
