/*     */ package net.minecraft.client.gui;
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufInputStream;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.handler.codec.base64.Base64;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.InputStream;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ScheduledThreadPoolExecutor;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ServerListEntryNormal implements GuiListExtended.IGuiListEntry {
/*  27 */   private static final Logger logger = LogManager.getLogger();
/*  28 */   private static final ThreadPoolExecutor field_148302_b = new ScheduledThreadPoolExecutor(5, (new ThreadFactoryBuilder()).setNameFormat("Server Pinger #%d").setDaemon(true).build());
/*  29 */   private static final ResourceLocation UNKNOWN_SERVER = new ResourceLocation("textures/misc/unknown_server.png");
/*  30 */   private static final ResourceLocation SERVER_SELECTION_BUTTONS = new ResourceLocation("textures/gui/server_selection.png");
/*     */   
/*     */   private final GuiMultiplayer field_148303_c;
/*     */   private final Minecraft mc;
/*     */   private final ServerData field_148301_e;
/*     */   private final ResourceLocation field_148306_i;
/*     */   private String field_148299_g;
/*     */   private DynamicTexture field_148305_h;
/*     */   private long field_148298_f;
/*     */   
/*     */   protected ServerListEntryNormal(GuiMultiplayer p_i45048_1_, ServerData p_i45048_2_) {
/*  41 */     this.field_148303_c = p_i45048_1_;
/*  42 */     this.field_148301_e = p_i45048_2_;
/*  43 */     this.mc = Minecraft.getMinecraft();
/*  44 */     this.field_148306_i = new ResourceLocation("servers/" + p_i45048_2_.serverIP + "/icon");
/*  45 */     this.field_148305_h = (DynamicTexture)this.mc.getTextureManager().getTexture(this.field_148306_i);
/*     */   }
/*     */   public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
/*     */     int l;
/*     */     String s1;
/*  50 */     if (!this.field_148301_e.field_78841_f) {
/*     */       
/*  52 */       this.field_148301_e.field_78841_f = true;
/*  53 */       this.field_148301_e.pingToServer = -2L;
/*  54 */       this.field_148301_e.serverMOTD = "";
/*  55 */       this.field_148301_e.populationInfo = "";
/*  56 */       field_148302_b.submit(new Runnable()
/*     */           {
/*     */             
/*     */             public void run()
/*     */             {
/*     */               try {
/*  62 */                 ServerListEntryNormal.this.field_148303_c.getOldServerPinger().ping(ServerListEntryNormal.this.field_148301_e);
/*     */               }
/*  64 */               catch (UnknownHostException var2) {
/*     */                 
/*  66 */                 ServerListEntryNormal.this.field_148301_e.pingToServer = -1L;
/*  67 */                 ServerListEntryNormal.this.field_148301_e.serverMOTD = EnumChatFormatting.DARK_RED + "Can't resolve hostname";
/*     */               }
/*  69 */               catch (Exception var3) {
/*     */                 
/*  71 */                 ServerListEntryNormal.this.field_148301_e.pingToServer = -1L;
/*  72 */                 ServerListEntryNormal.this.field_148301_e.serverMOTD = EnumChatFormatting.DARK_RED + "Can't connect to server.";
/*     */               } 
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/*  78 */     boolean flag = (this.field_148301_e.version > 47);
/*  79 */     boolean flag1 = (this.field_148301_e.version < 47);
/*  80 */     boolean flag2 = !(!flag && !flag1);
/*  81 */     this.mc.fontRendererObj.drawString(this.field_148301_e.serverName, x + 32 + 3, y + 1, 16777215);
/*  82 */     List<String> list = this.mc.fontRendererObj.listFormattedStringToWidth(this.field_148301_e.serverMOTD, listWidth - 32 - 2);
/*     */     
/*  84 */     for (int i = 0; i < Math.min(list.size(), 2); i++)
/*     */     {
/*  86 */       this.mc.fontRendererObj.drawString(list.get(i), x + 32 + 3, y + 12 + this.mc.fontRendererObj.FONT_HEIGHT * i, 8421504);
/*     */     }
/*     */     
/*  89 */     String s2 = flag2 ? (EnumChatFormatting.DARK_RED + this.field_148301_e.gameVersion) : this.field_148301_e.populationInfo;
/*  90 */     int j = this.mc.fontRendererObj.getStringWidth(s2);
/*  91 */     this.mc.fontRendererObj.drawString(s2, x + listWidth - j - 15 - 2, y + 1, 8421504);
/*  92 */     int k = 0;
/*  93 */     String s = null;
/*     */ 
/*     */ 
/*     */     
/*  97 */     if (flag2) {
/*     */       
/*  99 */       l = 5;
/* 100 */       s1 = flag ? "Client out of date!" : "Server out of date!";
/* 101 */       s = this.field_148301_e.playerList;
/*     */     }
/* 103 */     else if (this.field_148301_e.field_78841_f && this.field_148301_e.pingToServer != -2L) {
/*     */       
/* 105 */       if (this.field_148301_e.pingToServer < 0L) {
/*     */         
/* 107 */         l = 5;
/*     */       }
/* 109 */       else if (this.field_148301_e.pingToServer < 150L) {
/*     */         
/* 111 */         l = 0;
/*     */       }
/* 113 */       else if (this.field_148301_e.pingToServer < 300L) {
/*     */         
/* 115 */         l = 1;
/*     */       }
/* 117 */       else if (this.field_148301_e.pingToServer < 600L) {
/*     */         
/* 119 */         l = 2;
/*     */       }
/* 121 */       else if (this.field_148301_e.pingToServer < 1000L) {
/*     */         
/* 123 */         l = 3;
/*     */       }
/*     */       else {
/*     */         
/* 127 */         l = 4;
/*     */       } 
/*     */       
/* 130 */       if (this.field_148301_e.pingToServer < 0L)
/*     */       {
/* 132 */         s1 = "(no connection)";
/*     */       }
/*     */       else
/*     */       {
/* 136 */         s1 = String.valueOf(this.field_148301_e.pingToServer) + "ms";
/* 137 */         s = this.field_148301_e.playerList;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 142 */       k = 1;
/* 143 */       l = (int)(Minecraft.getSystemTime() / 100L + (slotIndex * 2) & 0x7L);
/*     */       
/* 145 */       if (l > 4)
/*     */       {
/* 147 */         l = 8 - l;
/*     */       }
/*     */       
/* 150 */       s1 = "Pinging...";
/*     */     } 
/*     */     
/* 153 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 154 */     this.mc.getTextureManager().bindTexture(Gui.icons);
/* 155 */     Gui.drawModalRectWithCustomSizedTexture(x + listWidth - 15, y, (k * 10), (176 + l * 8), 10, 8, 256.0F, 256.0F);
/*     */     
/* 157 */     if (this.field_148301_e.getBase64EncodedIconData() != null && !this.field_148301_e.getBase64EncodedIconData().equals(this.field_148299_g)) {
/*     */       
/* 159 */       this.field_148299_g = this.field_148301_e.getBase64EncodedIconData();
/* 160 */       prepareServerIcon();
/* 161 */       this.field_148303_c.getServerList().saveServerList();
/*     */     } 
/*     */     
/* 164 */     if (this.field_148305_h != null) {
/*     */       
/* 166 */       func_178012_a(x, y, this.field_148306_i);
/*     */     }
/*     */     else {
/*     */       
/* 170 */       func_178012_a(x, y, UNKNOWN_SERVER);
/*     */     } 
/*     */     
/* 173 */     int i1 = mouseX - x;
/* 174 */     int j1 = mouseY - y;
/*     */     
/* 176 */     if (i1 >= listWidth - 15 && i1 <= listWidth - 5 && j1 >= 0 && j1 <= 8) {
/*     */       
/* 178 */       this.field_148303_c.setHoveringText(s1);
/*     */     }
/* 180 */     else if (i1 >= listWidth - j - 15 - 2 && i1 <= listWidth - 15 - 2 && j1 >= 0 && j1 <= 8) {
/*     */       
/* 182 */       this.field_148303_c.setHoveringText(s);
/*     */     } 
/*     */     
/* 185 */     if (this.mc.gameSettings.touchscreen || isSelected) {
/*     */       
/* 187 */       this.mc.getTextureManager().bindTexture(SERVER_SELECTION_BUTTONS);
/* 188 */       Gui.drawRect(x, y, x + 32, y + 32, -1601138544);
/* 189 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 190 */       int k1 = mouseX - x;
/* 191 */       int l1 = mouseY - y;
/*     */       
/* 193 */       if (func_178013_b())
/*     */       {
/* 195 */         if (k1 < 32 && k1 > 16) {
/*     */           
/* 197 */           Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*     */         }
/*     */         else {
/*     */           
/* 201 */           Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*     */         } 
/*     */       }
/*     */       
/* 205 */       if (this.field_148303_c.func_175392_a(this, slotIndex))
/*     */       {
/* 207 */         if (k1 < 16 && l1 < 16) {
/*     */           
/* 209 */           Gui.drawModalRectWithCustomSizedTexture(x, y, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*     */         }
/*     */         else {
/*     */           
/* 213 */           Gui.drawModalRectWithCustomSizedTexture(x, y, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*     */         } 
/*     */       }
/*     */       
/* 217 */       if (this.field_148303_c.func_175394_b(this, slotIndex))
/*     */       {
/* 219 */         if (k1 < 16 && l1 > 16) {
/*     */           
/* 221 */           Gui.drawModalRectWithCustomSizedTexture(x, y, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*     */         }
/*     */         else {
/*     */           
/* 225 */           Gui.drawModalRectWithCustomSizedTexture(x, y, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_178012_a(int p_178012_1_, int p_178012_2_, ResourceLocation p_178012_3_) {
/* 233 */     this.mc.getTextureManager().bindTexture(p_178012_3_);
/* 234 */     GlStateManager.enableBlend();
/* 235 */     Gui.drawModalRectWithCustomSizedTexture(p_178012_1_, p_178012_2_, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
/* 236 */     GlStateManager.disableBlend();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_178013_b() {
/* 241 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void prepareServerIcon() {
/* 246 */     if (this.field_148301_e.getBase64EncodedIconData() == null) {
/*     */       
/* 248 */       this.mc.getTextureManager().deleteTexture(this.field_148306_i);
/* 249 */       this.field_148305_h = null;
/*     */     } else {
/*     */       BufferedImage bufferedimage;
/*     */       
/* 253 */       ByteBuf bytebuf = Unpooled.copiedBuffer(this.field_148301_e.getBase64EncodedIconData(), Charsets.UTF_8);
/* 254 */       ByteBuf bytebuf1 = Base64.decode(bytebuf);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 260 */         bufferedimage = TextureUtil.readBufferedImage((InputStream)new ByteBufInputStream(bytebuf1));
/* 261 */         Validate.validState((bufferedimage.getWidth() == 64), "Must be 64 pixels wide", new Object[0]);
/* 262 */         Validate.validState((bufferedimage.getHeight() == 64), "Must be 64 pixels high", new Object[0]);
/*     */       
/*     */       }
/* 265 */       catch (Throwable throwable) {
/*     */         
/* 267 */         logger.error("Invalid icon for server " + this.field_148301_e.serverName + " (" + this.field_148301_e.serverIP + ")", throwable);
/* 268 */         this.field_148301_e.setBase64EncodedIconData(null);
/*     */       }
/*     */       finally {
/*     */         
/* 272 */         bytebuf.release();
/* 273 */         bytebuf1.release();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 279 */       if (this.field_148305_h == null) {
/*     */         
/* 281 */         this.field_148305_h = new DynamicTexture(bufferedimage.getWidth(), bufferedimage.getHeight());
/* 282 */         this.mc.getTextureManager().loadTexture(this.field_148306_i, (ITextureObject)this.field_148305_h);
/*     */       } 
/*     */       
/* 285 */       bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), this.field_148305_h.getTextureData(), 0, bufferedimage.getWidth());
/* 286 */       this.field_148305_h.updateDynamicTexture();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
/* 295 */     if (p_148278_5_ <= 32) {
/*     */       
/* 297 */       if (p_148278_5_ < 32 && p_148278_5_ > 16 && func_178013_b()) {
/*     */         
/* 299 */         this.field_148303_c.selectServer(slotIndex);
/* 300 */         this.field_148303_c.connectToSelected();
/* 301 */         return true;
/*     */       } 
/*     */       
/* 304 */       if (p_148278_5_ < 16 && p_148278_6_ < 16 && this.field_148303_c.func_175392_a(this, slotIndex)) {
/*     */         
/* 306 */         this.field_148303_c.func_175391_a(this, slotIndex, GuiScreen.isShiftKeyDown());
/* 307 */         return true;
/*     */       } 
/*     */       
/* 310 */       if (p_148278_5_ < 16 && p_148278_6_ > 16 && this.field_148303_c.func_175394_b(this, slotIndex)) {
/*     */         
/* 312 */         this.field_148303_c.func_175393_b(this, slotIndex, GuiScreen.isShiftKeyDown());
/* 313 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 317 */     this.field_148303_c.selectServer(slotIndex);
/*     */     
/* 319 */     if (Minecraft.getSystemTime() - this.field_148298_f < 250L)
/*     */     {
/* 321 */       this.field_148303_c.connectToSelected();
/*     */     }
/*     */     
/* 324 */     this.field_148298_f = Minecraft.getSystemTime();
/* 325 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerData getServerData() {
/* 341 */     return this.field_148301_e;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\ServerListEntryNormal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */