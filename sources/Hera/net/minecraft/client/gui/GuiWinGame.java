/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.audio.MusicTicker;
/*     */ import net.minecraft.client.audio.SoundHandler;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.Charsets;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class GuiWinGame extends GuiScreen {
/*  25 */   private static final Logger logger = LogManager.getLogger();
/*  26 */   private static final ResourceLocation MINECRAFT_LOGO = new ResourceLocation("textures/gui/title/minecraft.png");
/*  27 */   private static final ResourceLocation VIGNETTE_TEXTURE = new ResourceLocation("textures/misc/vignette.png");
/*     */   private int field_146581_h;
/*     */   private List<String> field_146582_i;
/*     */   private int field_146579_r;
/*  31 */   private float field_146578_s = 0.5F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  38 */     MusicTicker musicticker = this.mc.func_181535_r();
/*  39 */     SoundHandler soundhandler = this.mc.getSoundHandler();
/*     */     
/*  41 */     if (this.field_146581_h == 0) {
/*     */       
/*  43 */       musicticker.func_181557_a();
/*  44 */       musicticker.func_181558_a(MusicTicker.MusicType.CREDITS);
/*  45 */       soundhandler.resumeSounds();
/*     */     } 
/*     */     
/*  48 */     soundhandler.update();
/*  49 */     this.field_146581_h++;
/*  50 */     float f = (this.field_146579_r + this.height + this.height + 24) / this.field_146578_s;
/*     */     
/*  52 */     if (this.field_146581_h > f)
/*     */     {
/*  54 */       sendRespawnPacket();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/*  64 */     if (keyCode == 1)
/*     */     {
/*  66 */       sendRespawnPacket();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void sendRespawnPacket() {
/*  72 */     this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
/*  73 */     this.mc.displayGuiScreen(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/*  81 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  90 */     if (this.field_146582_i == null) {
/*     */       
/*  92 */       this.field_146582_i = Lists.newArrayList();
/*     */ 
/*     */       
/*     */       try {
/*  96 */         String s = "";
/*  97 */         String s1 = EnumChatFormatting.WHITE + EnumChatFormatting.OBFUSCATED + EnumChatFormatting.GREEN + EnumChatFormatting.AQUA;
/*  98 */         int i = 274;
/*  99 */         InputStream inputstream = this.mc.getResourceManager().getResource(new ResourceLocation("texts/end.txt")).getInputStream();
/* 100 */         BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream, Charsets.UTF_8));
/* 101 */         Random random = new Random(8124371L);
/*     */         
/* 103 */         while ((s = bufferedreader.readLine()) != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 108 */           for (s = s.replaceAll("PLAYERNAME", this.mc.getSession().getUsername()); s.contains(s1); s = String.valueOf(s2) + EnumChatFormatting.WHITE + EnumChatFormatting.OBFUSCATED + "XXXXXXXX".substring(0, random.nextInt(4) + 3) + s3) {
/*     */             
/* 110 */             int j = s.indexOf(s1);
/* 111 */             String s2 = s.substring(0, j);
/* 112 */             String s3 = s.substring(j + s1.length());
/*     */           } 
/*     */           
/* 115 */           this.field_146582_i.addAll(this.mc.fontRendererObj.listFormattedStringToWidth(s, i));
/* 116 */           this.field_146582_i.add("");
/*     */         } 
/*     */         
/* 119 */         inputstream.close();
/*     */         
/* 121 */         for (int k = 0; k < 8; k++)
/*     */         {
/* 123 */           this.field_146582_i.add("");
/*     */         }
/*     */         
/* 126 */         inputstream = this.mc.getResourceManager().getResource(new ResourceLocation("texts/credits.txt")).getInputStream();
/* 127 */         bufferedreader = new BufferedReader(new InputStreamReader(inputstream, Charsets.UTF_8));
/*     */         
/* 129 */         while ((s = bufferedreader.readLine()) != null) {
/*     */           
/* 131 */           s = s.replaceAll("PLAYERNAME", this.mc.getSession().getUsername());
/* 132 */           s = s.replaceAll("\t", "    ");
/* 133 */           this.field_146582_i.addAll(this.mc.fontRendererObj.listFormattedStringToWidth(s, i));
/* 134 */           this.field_146582_i.add("");
/*     */         } 
/*     */         
/* 137 */         inputstream.close();
/* 138 */         this.field_146579_r = this.field_146582_i.size() * 12;
/*     */       }
/* 140 */       catch (Exception exception) {
/*     */         
/* 142 */         logger.error("Couldn't load credits", exception);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawWinGameScreen(int p_146575_1_, int p_146575_2_, float p_146575_3_) {
/* 149 */     Tessellator tessellator = Tessellator.getInstance();
/* 150 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 151 */     this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
/* 152 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 153 */     int i = this.width;
/* 154 */     float f = 0.0F - (this.field_146581_h + p_146575_3_) * 0.5F * this.field_146578_s;
/* 155 */     float f1 = this.height - (this.field_146581_h + p_146575_3_) * 0.5F * this.field_146578_s;
/* 156 */     float f2 = 0.015625F;
/* 157 */     float f3 = (this.field_146581_h + p_146575_3_ - 0.0F) * 0.02F;
/* 158 */     float f4 = (this.field_146579_r + this.height + this.height + 24) / this.field_146578_s;
/* 159 */     float f5 = (f4 - 20.0F - this.field_146581_h + p_146575_3_) * 0.005F;
/*     */     
/* 161 */     if (f5 < f3)
/*     */     {
/* 163 */       f3 = f5;
/*     */     }
/*     */     
/* 166 */     if (f3 > 1.0F)
/*     */     {
/* 168 */       f3 = 1.0F;
/*     */     }
/*     */     
/* 171 */     f3 *= f3;
/* 172 */     f3 = f3 * 96.0F / 255.0F;
/* 173 */     worldrenderer.pos(0.0D, this.height, this.zLevel).tex(0.0D, (f * f2)).color(f3, f3, f3, 1.0F).endVertex();
/* 174 */     worldrenderer.pos(i, this.height, this.zLevel).tex((i * f2), (f * f2)).color(f3, f3, f3, 1.0F).endVertex();
/* 175 */     worldrenderer.pos(i, 0.0D, this.zLevel).tex((i * f2), (f1 * f2)).color(f3, f3, f3, 1.0F).endVertex();
/* 176 */     worldrenderer.pos(0.0D, 0.0D, this.zLevel).tex(0.0D, (f1 * f2)).color(f3, f3, f3, 1.0F).endVertex();
/* 177 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 185 */     drawWinGameScreen(mouseX, mouseY, partialTicks);
/* 186 */     Tessellator tessellator = Tessellator.getInstance();
/* 187 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 188 */     int i = 274;
/* 189 */     int j = this.width / 2 - i / 2;
/* 190 */     int k = this.height + 50;
/* 191 */     float f = -(this.field_146581_h + partialTicks) * this.field_146578_s;
/* 192 */     GlStateManager.pushMatrix();
/* 193 */     GlStateManager.translate(0.0F, f, 0.0F);
/* 194 */     this.mc.getTextureManager().bindTexture(MINECRAFT_LOGO);
/* 195 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 196 */     drawTexturedModalRect(j, k, 0, 0, 155, 44);
/* 197 */     drawTexturedModalRect(j + 155, k, 0, 45, 155, 44);
/* 198 */     int l = k + 200;
/*     */     
/* 200 */     for (int i1 = 0; i1 < this.field_146582_i.size(); i1++) {
/*     */       
/* 202 */       if (i1 == this.field_146582_i.size() - 1) {
/*     */         
/* 204 */         float f1 = l + f - (this.height / 2 - 6);
/*     */         
/* 206 */         if (f1 < 0.0F)
/*     */         {
/* 208 */           GlStateManager.translate(0.0F, -f1, 0.0F);
/*     */         }
/*     */       } 
/*     */       
/* 212 */       if (l + f + 12.0F + 8.0F > 0.0F && l + f < this.height) {
/*     */         
/* 214 */         String s = this.field_146582_i.get(i1);
/*     */         
/* 216 */         if (s.startsWith("[C]")) {
/*     */           
/* 218 */           this.fontRendererObj.drawStringWithShadow(s.substring(3), (j + (i - this.fontRendererObj.getStringWidth(s.substring(3))) / 2), l, 16777215);
/*     */         }
/*     */         else {
/*     */           
/* 222 */           this.fontRendererObj.fontRandom.setSeed(i1 * 4238972211L + (this.field_146581_h / 4));
/* 223 */           this.fontRendererObj.drawStringWithShadow(s, j, l, 16777215);
/*     */         } 
/*     */       } 
/*     */       
/* 227 */       l += 12;
/*     */     } 
/*     */     
/* 230 */     GlStateManager.popMatrix();
/* 231 */     this.mc.getTextureManager().bindTexture(VIGNETTE_TEXTURE);
/* 232 */     GlStateManager.enableBlend();
/* 233 */     GlStateManager.blendFunc(0, 769);
/* 234 */     int j1 = this.width;
/* 235 */     int k1 = this.height;
/* 236 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 237 */     worldrenderer.pos(0.0D, k1, this.zLevel).tex(0.0D, 1.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 238 */     worldrenderer.pos(j1, k1, this.zLevel).tex(1.0D, 1.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 239 */     worldrenderer.pos(j1, 0.0D, this.zLevel).tex(1.0D, 0.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 240 */     worldrenderer.pos(0.0D, 0.0D, this.zLevel).tex(0.0D, 0.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 241 */     tessellator.draw();
/* 242 */     GlStateManager.disableBlend();
/* 243 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiWinGame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */