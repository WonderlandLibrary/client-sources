package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.MusicTicker$MusicType;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatus$EnumState;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiWinGame extends GuiScreen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final ResourceLocation MINECRAFT_LOGO = new ResourceLocation("textures/gui/title/minecraft.png");
   private static final ResourceLocation VIGNETTE_TEXTURE = new ResourceLocation("textures/misc/vignette.png");
   private int field_146581_h;
   private List field_146582_i;
   private int field_146579_r;
   private final float field_146578_s = 0.5F;

   public void updateScreen() {
      MusicTicker var1 = this.mc.func_181535_r();
      SoundHandler var2 = this.mc.getSoundHandler();
      if(this.field_146581_h == 0) {
         var1.func_181557_a();
         var1.func_181558_a(MusicTicker$MusicType.CREDITS);
         var2.resumeSounds();
      }

      var2.update();
      ++this.field_146581_h;
      float var10000 = (float)(this.field_146579_r + this.height + this.height + 24);
      this.getClass();
      float var3 = var10000 / 0.5F;
      if((float)this.field_146581_h > var3) {
         this.sendRespawnPacket();
      }

   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if(var2 == 1) {
         this.sendRespawnPacket();
      }

   }

   private void sendRespawnPacket() {
      this.mc.player.connection.b(new C16PacketClientStatus(C16PacketClientStatus$EnumState.PERFORM_RESPAWN));
      this.mc.displayGuiScreen((GuiScreen)null);
   }

   public boolean doesGuiPauseGame() {
      return true;
   }

   public void initGui() {
      if(this.field_146582_i == null) {
         this.field_146582_i = Lists.newArrayList();

         try {
            String var2 = "" + EnumChatFormatting.WHITE + EnumChatFormatting.OBFUSCATED + EnumChatFormatting.GREEN + EnumChatFormatting.AQUA;
            boolean var3 = true;
            InputStream var4 = this.mc.getResourceManager().getResource(new ResourceLocation("texts/end.txt")).getInputStream();
            BufferedReader var5 = new BufferedReader(new InputStreamReader(var4, Charsets.UTF_8));
            Random var6 = new Random(8124371L);

            String var1;
            while((var1 = var5.readLine()) != null) {
               String var7;
               String var8;
               for(var1 = var1.replaceAll("PLAYERNAME", this.mc.getSession().getUsername()); var1.contains(var2); var1 = var7 + EnumChatFormatting.WHITE + EnumChatFormatting.OBFUSCATED + "XXXXXXXX".substring(0, var6.nextInt(4) + 3) + var8) {
                  int var9 = var1.indexOf(var2);
                  var7 = var1.substring(0, var9);
                  var8 = var1.substring(var9 + var2.length());
               }

               this.field_146582_i.addAll(this.mc.fontRendererObj.listFormattedStringToWidth(var1, 274));
               this.field_146582_i.add("");
            }

            var4.close();

            for(int var17 = 0; var17 < 8; ++var17) {
               this.field_146582_i.add("");
            }

            var4 = this.mc.getResourceManager().getResource(new ResourceLocation("texts/credits.txt")).getInputStream();
            var5 = new BufferedReader(new InputStreamReader(var4, Charsets.UTF_8));

            while((var1 = var5.readLine()) != null) {
               var1 = var1.replaceAll("PLAYERNAME", this.mc.getSession().getUsername());
               var1 = var1.replaceAll("\t", "    ");
               this.field_146582_i.addAll(this.mc.fontRendererObj.listFormattedStringToWidth(var1, 274));
               this.field_146582_i.add("");
            }

            var4.close();
            this.field_146579_r = this.field_146582_i.size() * 12;
         } catch (Exception var10) {
            LOGGER.error("Couldn\'t load credits", var10);
         }
      }

   }

   private void drawWinGameScreen(int var1, int var2, float var3) {
      Tessellator var4 = Tessellator.getInstance();
      WorldRenderer var5 = var4.getWorldRenderer();
      this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
      var5.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      int var6 = this.width;
      float var10001 = ((float)this.field_146581_h + var3) * 0.5F;
      this.getClass();
      float var7 = 0.0F - var10001 * 0.5F;
      float var10000 = (float)this.height;
      var10001 = ((float)this.field_146581_h + var3) * 0.5F;
      this.getClass();
      float var8 = var10000 - var10001 * 0.5F;
      float var9 = 0.015625F;
      float var10 = ((float)this.field_146581_h + var3 - 0.0F) * 0.02F;
      var10000 = (float)(this.field_146579_r + this.height + this.height + 24);
      this.getClass();
      float var11 = var10000 / 0.5F;
      float var12 = (var11 - 20.0F - ((float)this.field_146581_h + var3)) * 0.005F;
      if(var12 < var10) {
         var10 = var12;
      }

      if(var10 > 1.0F) {
         var10 = 1.0F;
      }

      var10 = var10 * var10;
      var10 = var10 * 96.0F / 255.0F;
      var5.pos(0.0D, (double)this.height, (double)this.zLevel).tex(0.0D, (double)(var7 * 0.015625F)).color(var10, var10, var10, 1.0F).endVertex();
      var5.pos((double)var6, (double)this.height, (double)this.zLevel).tex((double)((float)var6 * 0.015625F), (double)(var7 * 0.015625F)).color(var10, var10, var10, 1.0F).endVertex();
      var5.pos((double)var6, 0.0D, (double)this.zLevel).tex((double)((float)var6 * 0.015625F), (double)(var8 * 0.015625F)).color(var10, var10, var10, 1.0F).endVertex();
      var5.pos(0.0D, 0.0D, (double)this.zLevel).tex(0.0D, (double)(var8 * 0.015625F)).color(var10, var10, var10, 1.0F).endVertex();
      var4.draw();
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawWinGameScreen(var1, var2, var3);
      Tessellator var4 = Tessellator.getInstance();
      WorldRenderer var5 = var4.getWorldRenderer();
      boolean var6 = true;
      int var7 = this.width / 2 - 137;
      int var8 = this.height + 50;
      float var10000 = -((float)this.field_146581_h + var3);
      this.getClass();
      float var9 = var10000 * 0.5F;
      GlStateManager.pushMatrix();
      GlStateManager.translate(0.0F, var9, 0.0F);
      this.mc.getTextureManager().bindTexture(MINECRAFT_LOGO);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.drawTexturedModalRect(var7, var8, 0, 0, 155, 44);
      this.drawTexturedModalRect(var7 + 155, var8, 0, 45, 155, 44);
      int var10 = var8 + 200;

      for(int var11 = 0; var11 < this.field_146582_i.size(); ++var11) {
         if(var11 == this.field_146582_i.size() - 1) {
            float var12 = (float)var10 + var9 - (float)(this.height / 2 - 6);
            if(var12 < 0.0F) {
               GlStateManager.translate(0.0F, -var12, 0.0F);
            }
         }

         if((float)var10 + var9 + 12.0F + 8.0F > 0.0F && (float)var10 + var9 < (float)this.height) {
            String var14 = (String)this.field_146582_i.get(var11);
            if(var14.startsWith("[C]")) {
               this.fontRendererObj.drawStringWithShadow(var14.substring(3), (float)(var7 + (274 - this.fontRendererObj.d(var14.substring(3))) / 2), (float)var10, 16777215);
            } else {
               this.fontRendererObj.fontRandom.setSeed((long)var11 * 4238972211L + (long)(this.field_146581_h / 4));
               this.fontRendererObj.drawStringWithShadow(var14, (float)var7, (float)var10, 16777215);
            }
         }

         var10 += 12;
      }

      GlStateManager.popMatrix();
      this.mc.getTextureManager().bindTexture(VIGNETTE_TEXTURE);
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(0, 769);
      int var13 = this.width;
      int var15 = this.height;
      var5.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      var5.pos(0.0D, (double)var15, (double)this.zLevel).tex(0.0D, 1.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      var5.pos((double)var13, (double)var15, (double)this.zLevel).tex(1.0D, 1.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      var5.pos((double)var13, 0.0D, (double)this.zLevel).tex(1.0D, 0.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      var5.pos(0.0D, 0.0D, (double)this.zLevel).tex(0.0D, 0.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      var4.draw();
      GlStateManager.disableBlend();
      super.drawScreen(var1, var2, var3);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
