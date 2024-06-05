package net.minecraft.client.gui;

import com.google.common.base.Charsets;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import java.awt.image.BufferedImage;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerListEntryNormal implements GuiListExtended.IGuiListEntry {
   private static final ResourceLocation field_178014_d = new ResourceLocation("textures/gui/server_selection.png");
   private static final ThreadPoolExecutor field_148302_b = new ScheduledThreadPoolExecutor(5, (new ThreadFactoryBuilder()).setNameFormat("Server Pinger #%d").setDaemon(true).build());
   private static final String __OBFID = "CL_00000817";
   private final GuiMultiplayer field_148303_c;
   private final ResourceLocation field_148306_i;
   private static final Logger logger = LogManager.getLogger();
   private DynamicTexture field_148305_h;
   private static final ResourceLocation field_178015_c = new ResourceLocation("textures/misc/unknown_server.png");
   private String field_148299_g;
   private long field_148298_f;
   private final Minecraft field_148300_d;
   private final ServerData field_148301_e;

   protected void func_178012_a(int var1, int var2, ResourceLocation var3) {
      this.field_148300_d.getTextureManager().bindTexture(var3);
      GlStateManager.enableBlend();
      Gui.drawModalRectWithCustomSizedTexture(var1, var2, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
      GlStateManager.disableBlend();
   }

   static GuiMultiplayer access$0(ServerListEntryNormal var0) {
      return var0.field_148303_c;
   }

   public void mouseReleased(int var1, int var2, int var3, int var4, int var5, int var6) {
   }

   public boolean mousePressed(int var1, int var2, int var3, int var4, int var5, int var6) {
      if (var5 <= 32) {
         if (var5 < 32 && var5 > 16 && this.func_178013_b()) {
            this.field_148303_c.selectServer(var1);
            this.field_148303_c.connectToSelected();
            return true;
         }

         if (var5 < 16 && var6 < 16 && this.field_148303_c.func_175392_a(this, var1)) {
            this.field_148303_c.func_175391_a(this, var1, GuiScreen.isShiftKeyDown());
            return true;
         }

         if (var5 < 16 && var6 > 16 && this.field_148303_c.func_175394_b(this, var1)) {
            this.field_148303_c.func_175393_b(this, var1, GuiScreen.isShiftKeyDown());
            return true;
         }
      }

      this.field_148303_c.selectServer(var1);
      if (Minecraft.getSystemTime() - this.field_148298_f < 250L) {
         this.field_148303_c.connectToSelected();
      }

      this.field_148298_f = Minecraft.getSystemTime();
      return false;
   }

   public void drawEntry(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      if (!this.field_148301_e.field_78841_f) {
         this.field_148301_e.field_78841_f = true;
         this.field_148301_e.pingToServer = -2L;
         this.field_148301_e.serverMOTD = "";
         this.field_148301_e.populationInfo = "";
         field_148302_b.submit(new Runnable(this) {
            final ServerListEntryNormal this$0;
            private static final String __OBFID = "CL_00000818";

            {
               this.this$0 = var1;
            }

            public void run() {
               try {
                  ServerListEntryNormal.access$0(this.this$0).getOldServerPinger().ping(ServerListEntryNormal.access$1(this.this$0));
               } catch (UnknownHostException var2) {
                  ServerListEntryNormal.access$1(this.this$0).pingToServer = -1L;
                  ServerListEntryNormal.access$1(this.this$0).serverMOTD = String.valueOf((new StringBuilder()).append(EnumChatFormatting.DARK_RED).append("Can't resolve hostname"));
               } catch (Exception var3) {
                  ServerListEntryNormal.access$1(this.this$0).pingToServer = -1L;
                  ServerListEntryNormal.access$1(this.this$0).serverMOTD = String.valueOf((new StringBuilder()).append(EnumChatFormatting.DARK_RED).append("Can't connect to server."));
               }

            }
         });
      }

      boolean var9 = this.field_148301_e.version > 47;
      boolean var10 = this.field_148301_e.version < 47;
      boolean var11 = var9 || var10;
      this.field_148300_d.fontRendererObj.drawString(this.field_148301_e.serverName, (double)(var2 + 32 + 3), (double)(var3 + 1), 16777215);
      List var12 = this.field_148300_d.fontRendererObj.listFormattedStringToWidth(this.field_148301_e.serverMOTD, var4 - 32 - 2);

      for(int var13 = 0; var13 < Math.min(var12.size(), 2); ++var13) {
         this.field_148300_d.fontRendererObj.drawString((String)var12.get(var13), (double)(var2 + 32 + 3), (double)(var3 + 12 + this.field_148300_d.fontRendererObj.FONT_HEIGHT * var13), 8421504);
      }

      String var23 = var11 ? String.valueOf((new StringBuilder()).append(EnumChatFormatting.DARK_RED).append(this.field_148301_e.gameVersion)) : this.field_148301_e.populationInfo;
      int var14 = this.field_148300_d.fontRendererObj.getStringWidth(var23);
      this.field_148300_d.fontRendererObj.drawString(var23, (double)(var2 + var4 - var14 - 15 - 2), (double)(var3 + 1), 8421504);
      byte var15 = 0;
      String var16 = null;
      int var17;
      String var18;
      if (var11) {
         var17 = 5;
         var18 = var9 ? "Client out of date!" : "Server out of date!";
         var16 = this.field_148301_e.playerList;
      } else if (this.field_148301_e.field_78841_f && this.field_148301_e.pingToServer != -2L) {
         if (this.field_148301_e.pingToServer < 0L) {
            var17 = 5;
         } else if (this.field_148301_e.pingToServer < 150L) {
            var17 = 0;
         } else if (this.field_148301_e.pingToServer < 300L) {
            var17 = 1;
         } else if (this.field_148301_e.pingToServer < 600L) {
            var17 = 2;
         } else if (this.field_148301_e.pingToServer < 1000L) {
            var17 = 3;
         } else {
            var17 = 4;
         }

         if (this.field_148301_e.pingToServer < 0L) {
            var18 = "(no connection)";
         } else {
            var18 = String.valueOf((new StringBuilder(String.valueOf(this.field_148301_e.pingToServer))).append("ms"));
            var16 = this.field_148301_e.playerList;
         }
      } else {
         var15 = 1;
         var17 = (int)(Minecraft.getSystemTime() / 100L + (long)(var1 * 2) & 7L);
         if (var17 > 4) {
            var17 = 8 - var17;
         }

         var18 = "Pinging...";
      }

      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_148300_d.getTextureManager().bindTexture(Gui.icons);
      Gui.drawModalRectWithCustomSizedTexture(var2 + var4 - 15, var3, (float)(var15 * 10), (float)(176 + var17 * 8), 10, 8, 256.0F, 256.0F);
      if (this.field_148301_e.getBase64EncodedIconData() != null && !this.field_148301_e.getBase64EncodedIconData().equals(this.field_148299_g)) {
         this.field_148299_g = this.field_148301_e.getBase64EncodedIconData();
         this.prepareServerIcon();
         this.field_148303_c.getServerList().saveServerList();
      }

      if (this.field_148305_h != null) {
         this.func_178012_a(var2, var3, this.field_148306_i);
      } else {
         this.func_178012_a(var2, var3, field_178015_c);
      }

      int var19 = var6 - var2;
      int var20 = var7 - var3;
      if (var19 >= var4 - 15 && var19 <= var4 - 5 && var20 >= 0 && var20 <= 8) {
         this.field_148303_c.func_146793_a(var18);
      } else if (var19 >= var4 - var14 - 15 - 2 && var19 <= var4 - 15 - 2 && var20 >= 0 && var20 <= 8) {
         this.field_148303_c.func_146793_a(var16);
      }

      if (this.field_148300_d.gameSettings.touchscreen || var8) {
         this.field_148300_d.getTextureManager().bindTexture(field_178014_d);
         Gui.drawRect((double)var2, (double)var3, (double)(var2 + 32), (double)(var3 + 32), -1601138544);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         int var21 = var6 - var2;
         int var22 = var7 - var3;
         if (this.func_178013_b()) {
            if (var21 < 32 && var21 > 16) {
               Gui.drawModalRectWithCustomSizedTexture(var2, var3, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
            } else {
               Gui.drawModalRectWithCustomSizedTexture(var2, var3, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
            }
         }

         if (this.field_148303_c.func_175392_a(this, var1)) {
            if (var21 < 16 && var22 < 16) {
               Gui.drawModalRectWithCustomSizedTexture(var2, var3, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
            } else {
               Gui.drawModalRectWithCustomSizedTexture(var2, var3, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
            }
         }

         if (this.field_148303_c.func_175394_b(this, var1)) {
            if (var21 < 16 && var22 > 16) {
               Gui.drawModalRectWithCustomSizedTexture(var2, var3, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
            } else {
               Gui.drawModalRectWithCustomSizedTexture(var2, var3, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
            }
         }
      }

   }

   private boolean func_178013_b() {
      return true;
   }

   static ServerData access$1(ServerListEntryNormal var0) {
      return var0.field_148301_e;
   }

   public void setSelected(int var1, int var2, int var3) {
   }

   protected ServerListEntryNormal(GuiMultiplayer var1, ServerData var2) {
      this.field_148303_c = var1;
      this.field_148301_e = var2;
      this.field_148300_d = Minecraft.getMinecraft();
      this.field_148306_i = new ResourceLocation(String.valueOf((new StringBuilder("servers/")).append(var2.serverIP).append("/icon")));
      this.field_148305_h = (DynamicTexture)this.field_148300_d.getTextureManager().getTexture(this.field_148306_i);
   }

   public ServerData getServerData() {
      return this.field_148301_e;
   }

   private void prepareServerIcon() {
      if (this.field_148301_e.getBase64EncodedIconData() == null) {
         this.field_148300_d.getTextureManager().deleteTexture(this.field_148306_i);
         this.field_148305_h = null;
      } else {
         ByteBuf var1 = Unpooled.copiedBuffer(this.field_148301_e.getBase64EncodedIconData(), Charsets.UTF_8);
         ByteBuf var2 = Base64.decode(var1);

         BufferedImage var3;
         label82: {
            try {
               var3 = TextureUtil.func_177053_a(new ByteBufInputStream(var2));
               Validate.validState(var3.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
               Validate.validState(var3.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
               break label82;
            } catch (Exception var8) {
               logger.error(String.valueOf((new StringBuilder("Invalid icon for server ")).append(this.field_148301_e.serverName).append(" (").append(this.field_148301_e.serverIP).append(")")), var8);
               this.field_148301_e.setBase64EncodedIconData((String)null);
            } finally {
               var1.release();
               var2.release();
            }

            return;
         }

         if (this.field_148305_h == null) {
            this.field_148305_h = new DynamicTexture(var3.getWidth(), var3.getHeight());
            this.field_148300_d.getTextureManager().loadTexture(this.field_148306_i, this.field_148305_h);
         }

         var3.getRGB(0, 0, var3.getWidth(), var3.getHeight(), this.field_148305_h.getTextureData(), 0, var3.getWidth());
         this.field_148305_h.updateDynamicTexture();
      }

   }
}
