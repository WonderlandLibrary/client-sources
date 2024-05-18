/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class GuiNewChat
/*     */   extends Gui {
/*  17 */   private static final Logger logger = LogManager.getLogger();
/*     */   private final Minecraft mc;
/*  19 */   private final List<String> sentMessages = Lists.newArrayList();
/*  20 */   private final List<ChatLine> chatLines = Lists.newArrayList();
/*  21 */   private final List<ChatLine> field_146253_i = Lists.newArrayList();
/*     */   
/*     */   private int scrollPos;
/*     */   private boolean isScrolled;
/*     */   
/*     */   public GuiNewChat(Minecraft mcIn) {
/*  27 */     this.mc = mcIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawChat(int p_146230_1_) {
/*  32 */     if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
/*     */       
/*  34 */       int i = getLineCount();
/*  35 */       boolean flag = false;
/*  36 */       int j = 0;
/*  37 */       int k = this.field_146253_i.size();
/*  38 */       float f = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;
/*     */       
/*  40 */       if (k > 0) {
/*     */         
/*  42 */         if (getChatOpen())
/*     */         {
/*  44 */           flag = true;
/*     */         }
/*     */         
/*  47 */         float f1 = getChatScale();
/*  48 */         int l = MathHelper.ceiling_float_int(getChatWidth() / f1);
/*  49 */         GlStateManager.pushMatrix();
/*  50 */         GlStateManager.translate(2.0F, 20.0F, 0.0F);
/*  51 */         GlStateManager.scale(f1, f1, 1.0F);
/*     */         
/*  53 */         for (int i1 = 0; i1 + this.scrollPos < this.field_146253_i.size() && i1 < i; i1++) {
/*     */           
/*  55 */           ChatLine chatline = this.field_146253_i.get(i1 + this.scrollPos);
/*     */           
/*  57 */           if (chatline != null) {
/*     */             
/*  59 */             int j1 = p_146230_1_ - chatline.getUpdatedCounter();
/*     */             
/*  61 */             if (j1 < 200 || flag) {
/*     */               
/*  63 */               double d0 = j1 / 200.0D;
/*  64 */               d0 = 1.0D - d0;
/*  65 */               d0 *= 10.0D;
/*  66 */               d0 = MathHelper.clamp_double(d0, 0.0D, 1.0D);
/*  67 */               d0 *= d0;
/*  68 */               int l1 = (int)(255.0D * d0);
/*     */               
/*  70 */               if (flag)
/*     */               {
/*  72 */                 l1 = 255;
/*     */               }
/*     */               
/*  75 */               l1 = (int)(l1 * f);
/*  76 */               j++;
/*     */               
/*  78 */               if (l1 > 3) {
/*     */                 
/*  80 */                 int i2 = 0;
/*  81 */                 int j2 = -i1 * 9;
/*  82 */                 drawRect(i2, j2 - 9, i2 + l + 4, j2, l1 / 2 << 24);
/*  83 */                 String s = chatline.getChatComponent().getFormattedText();
/*  84 */                 GlStateManager.enableBlend();
/*  85 */                 this.mc.fontRendererObj.drawStringWithShadow(s, i2, (j2 - 8), 16777215 + (l1 << 24));
/*  86 */                 GlStateManager.disableAlpha();
/*  87 */                 GlStateManager.disableBlend();
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/*  93 */         if (flag) {
/*     */           
/*  95 */           int k2 = this.mc.fontRendererObj.FONT_HEIGHT;
/*  96 */           GlStateManager.translate(-3.0F, 0.0F, 0.0F);
/*  97 */           int l2 = k * k2 + k;
/*  98 */           int i3 = j * k2 + j;
/*  99 */           int j3 = this.scrollPos * i3 / k;
/* 100 */           int k1 = i3 * i3 / l2;
/*     */           
/* 102 */           if (l2 != i3) {
/*     */             
/* 104 */             int k3 = (j3 > 0) ? 170 : 96;
/* 105 */             int l3 = this.isScrolled ? 13382451 : 3355562;
/* 106 */             drawRect(0, -j3, 2, -j3 - k1, l3 + (k3 << 24));
/* 107 */             drawRect(2, -j3, 1, -j3 - k1, 13421772 + (k3 << 24));
/*     */           } 
/*     */         } 
/*     */         
/* 111 */         GlStateManager.popMatrix();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearChatMessages() {
/* 121 */     this.field_146253_i.clear();
/* 122 */     this.chatLines.clear();
/* 123 */     this.sentMessages.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void printChatMessage(IChatComponent p_146227_1_) {
/* 128 */     printChatMessageWithOptionalDeletion(p_146227_1_, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printChatMessageWithOptionalDeletion(IChatComponent p_146234_1_, int p_146234_2_) {
/* 136 */     setChatLine(p_146234_1_, p_146234_2_, this.mc.ingameGUI.getUpdateCounter(), false);
/* 137 */     logger.info("[CHAT] " + p_146234_1_.getUnformattedText());
/*     */   }
/*     */ 
/*     */   
/*     */   private void setChatLine(IChatComponent p_146237_1_, int p_146237_2_, int p_146237_3_, boolean p_146237_4_) {
/* 142 */     if (p_146237_2_ != 0)
/*     */     {
/* 144 */       deleteChatLine(p_146237_2_);
/*     */     }
/*     */     
/* 147 */     int i = MathHelper.floor_float(getChatWidth() / getChatScale());
/* 148 */     List<IChatComponent> list = GuiUtilRenderComponents.func_178908_a(p_146237_1_, i, this.mc.fontRendererObj, false, false);
/* 149 */     boolean flag = getChatOpen();
/*     */     
/* 151 */     for (IChatComponent ichatcomponent : list) {
/*     */       
/* 153 */       if (flag && this.scrollPos > 0) {
/*     */         
/* 155 */         this.isScrolled = true;
/* 156 */         scroll(1);
/*     */       } 
/*     */       
/* 159 */       this.field_146253_i.add(0, new ChatLine(p_146237_3_, ichatcomponent, p_146237_2_));
/*     */     } 
/*     */     
/* 162 */     while (this.field_146253_i.size() > 100)
/*     */     {
/* 164 */       this.field_146253_i.remove(this.field_146253_i.size() - 1);
/*     */     }
/*     */     
/* 167 */     if (!p_146237_4_) {
/*     */       
/* 169 */       this.chatLines.add(0, new ChatLine(p_146237_3_, p_146237_1_, p_146237_2_));
/*     */       
/* 171 */       while (this.chatLines.size() > 100)
/*     */       {
/* 173 */         this.chatLines.remove(this.chatLines.size() - 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void refreshChat() {
/* 180 */     this.field_146253_i.clear();
/* 181 */     resetScroll();
/*     */     
/* 183 */     for (int i = this.chatLines.size() - 1; i >= 0; i--) {
/*     */       
/* 185 */       ChatLine chatline = this.chatLines.get(i);
/* 186 */       setChatLine(chatline.getChatComponent(), chatline.getChatLineID(), chatline.getUpdatedCounter(), true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getSentMessages() {
/* 192 */     return this.sentMessages;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addToSentMessages(String p_146239_1_) {
/* 200 */     if (this.sentMessages.isEmpty() || !((String)this.sentMessages.get(this.sentMessages.size() - 1)).equals(p_146239_1_))
/*     */     {
/* 202 */       this.sentMessages.add(p_146239_1_);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetScroll() {
/* 211 */     this.scrollPos = 0;
/* 212 */     this.isScrolled = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void scroll(int p_146229_1_) {
/* 220 */     this.scrollPos += p_146229_1_;
/* 221 */     int i = this.field_146253_i.size();
/*     */     
/* 223 */     if (this.scrollPos > i - getLineCount())
/*     */     {
/* 225 */       this.scrollPos = i - getLineCount();
/*     */     }
/*     */     
/* 228 */     if (this.scrollPos <= 0) {
/*     */       
/* 230 */       this.scrollPos = 0;
/* 231 */       this.isScrolled = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getChatComponent(int p_146236_1_, int p_146236_2_) {
/* 240 */     if (!getChatOpen())
/*     */     {
/* 242 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 246 */     ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 247 */     int i = scaledresolution.getScaleFactor();
/* 248 */     float f = getChatScale();
/* 249 */     int j = p_146236_1_ / i - 3;
/* 250 */     int k = p_146236_2_ / i - 27;
/* 251 */     j = MathHelper.floor_float(j / f);
/* 252 */     k = MathHelper.floor_float(k / f);
/*     */     
/* 254 */     if (j >= 0 && k >= 0) {
/*     */       
/* 256 */       int l = Math.min(getLineCount(), this.field_146253_i.size());
/*     */       
/* 258 */       if (j <= MathHelper.floor_float(getChatWidth() / getChatScale()) && k < this.mc.fontRendererObj.FONT_HEIGHT * l + l) {
/*     */         
/* 260 */         int i1 = k / this.mc.fontRendererObj.FONT_HEIGHT + this.scrollPos;
/*     */         
/* 262 */         if (i1 >= 0 && i1 < this.field_146253_i.size()) {
/*     */           
/* 264 */           ChatLine chatline = this.field_146253_i.get(i1);
/* 265 */           int j1 = 0;
/*     */           
/* 267 */           for (IChatComponent ichatcomponent : chatline.getChatComponent()) {
/*     */             
/* 269 */             if (ichatcomponent instanceof ChatComponentText) {
/*     */               
/* 271 */               j1 += this.mc.fontRendererObj.getStringWidth(GuiUtilRenderComponents.func_178909_a(((ChatComponentText)ichatcomponent).getChatComponentText_TextValue(), false));
/*     */               
/* 273 */               if (j1 > j)
/*     */               {
/* 275 */                 return ichatcomponent;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 281 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 285 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 290 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getChatOpen() {
/* 300 */     return this.mc.currentScreen instanceof GuiChat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteChatLine(int p_146242_1_) {
/* 308 */     Iterator<ChatLine> iterator = this.field_146253_i.iterator();
/*     */     
/* 310 */     while (iterator.hasNext()) {
/*     */       
/* 312 */       ChatLine chatline = iterator.next();
/*     */       
/* 314 */       if (chatline.getChatLineID() == p_146242_1_)
/*     */       {
/* 316 */         iterator.remove();
/*     */       }
/*     */     } 
/*     */     
/* 320 */     iterator = this.chatLines.iterator();
/*     */     
/* 322 */     while (iterator.hasNext()) {
/*     */       
/* 324 */       ChatLine chatline1 = iterator.next();
/*     */       
/* 326 */       if (chatline1.getChatLineID() == p_146242_1_) {
/*     */         
/* 328 */         iterator.remove();
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getChatWidth() {
/* 336 */     return calculateChatboxWidth(this.mc.gameSettings.chatWidth);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getChatHeight() {
/* 341 */     return calculateChatboxHeight(getChatOpen() ? this.mc.gameSettings.chatHeightFocused : this.mc.gameSettings.chatHeightUnfocused);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getChatScale() {
/* 349 */     return this.mc.gameSettings.chatScale;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int calculateChatboxWidth(float p_146233_0_) {
/* 354 */     int i = 320;
/* 355 */     int j = 40;
/* 356 */     return MathHelper.floor_float(p_146233_0_ * (i - j) + j);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int calculateChatboxHeight(float p_146243_0_) {
/* 361 */     int i = 180;
/* 362 */     int j = 20;
/* 363 */     return MathHelper.floor_float(p_146243_0_ * (i - j) + j);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLineCount() {
/* 368 */     return getChatHeight() / 9;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiNewChat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */