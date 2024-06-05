package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiNewChat extends Gui {
   private static final String __OBFID = "CL_00000669";
   private boolean isScrolled;
   private int scrollPos;
   private final List field_146253_i = Lists.newArrayList();
   private final Minecraft mc;
   private static final Logger logger = LogManager.getLogger();
   private final List chatLines = Lists.newArrayList();
   private final List sentMessages = Lists.newArrayList();

   public void deleteChatLine(int var1) {
      Iterator var2 = this.field_146253_i.iterator();

      ChatLine var3;
      while(var2.hasNext()) {
         var3 = (ChatLine)var2.next();
         if (var3.getChatLineID() == var1) {
            var2.remove();
         }
      }

      var2 = this.chatLines.iterator();

      while(var2.hasNext()) {
         var3 = (ChatLine)var2.next();
         if (var3.getChatLineID() == var1) {
            var2.remove();
            break;
         }
      }

   }

   public int getChatHeight() {
      return calculateChatboxHeight(this.getChatOpen() ? this.mc.gameSettings.chatHeightFocused : this.mc.gameSettings.chatHeightUnfocused);
   }

   public boolean getChatOpen() {
      return this.mc.currentScreen instanceof GuiChat;
   }

   public void clearChatMessages() {
      this.field_146253_i.clear();
      this.chatLines.clear();
      this.sentMessages.clear();
   }

   public void addToSentMessages(String var1) {
      if (this.sentMessages.isEmpty() || !((String)this.sentMessages.get(this.sentMessages.size() - 1)).equals(var1)) {
         this.sentMessages.add(var1);
      }

   }

   public void printChatMessageWithOptionalDeletion(IChatComponent var1, int var2) {
      this.setChatLine(var1, var2, this.mc.ingameGUI.getUpdateCounter(), false);
      logger.info(String.valueOf((new StringBuilder("[CHAT] ")).append(var1.getUnformattedText())));
   }

   public int getLineCount() {
      return this.getChatHeight() / 9;
   }

   public void printChatMessage(IChatComponent var1) {
      this.printChatMessageWithOptionalDeletion(var1, 0);
   }

   public IChatComponent getChatComponent(int var1, int var2) {
      if (!this.getChatOpen()) {
         return null;
      } else {
         ScaledResolution var3 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
         int var4 = var3.getScaleFactor();
         float var5 = this.getChatScale();
         int var6 = var1 / var4 - 3;
         int var7 = var2 / var4 - 27;
         var6 = MathHelper.floor_float((float)var6 / var5);
         var7 = MathHelper.floor_float((float)var7 / var5);
         if (var6 >= 0 && var7 >= 0) {
            int var8 = Math.min(this.getLineCount(), this.field_146253_i.size());
            if (var6 <= MathHelper.floor_float((float)this.getChatWidth() / this.getChatScale()) && var7 < this.mc.fontRendererObj.FONT_HEIGHT * var8 + var8) {
               int var9 = var7 / this.mc.fontRendererObj.FONT_HEIGHT + this.scrollPos;
               if (var9 >= 0 && var9 < this.field_146253_i.size()) {
                  ChatLine var10 = (ChatLine)this.field_146253_i.get(var9);
                  int var11 = 0;
                  Iterator var12 = var10.getChatComponent().iterator();

                  while(var12.hasNext()) {
                     IChatComponent var13 = (IChatComponent)var12.next();
                     if (var13 instanceof ChatComponentText) {
                        var11 += this.mc.fontRendererObj.getStringWidth(GuiUtilRenderComponents.func_178909_a(((ChatComponentText)var13).getChatComponentText_TextValue(), false));
                        if (var11 > var6) {
                           return var13;
                        }
                     }
                  }
               }

               return null;
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   public static int calculateChatboxWidth(float var0) {
      short var1 = 320;
      byte var2 = 40;
      return MathHelper.floor_float(var0 * (float)(var1 - var2) + (float)var2);
   }

   private void setChatLine(IChatComponent var1, int var2, int var3, boolean var4) {
      if (var2 != 0) {
         this.deleteChatLine(var2);
      }

      int var5 = MathHelper.floor_float((float)this.getChatWidth() / this.getChatScale());
      List var6 = GuiUtilRenderComponents.func_178908_a(var1, var5, this.mc.fontRendererObj, false, false);
      boolean var7 = this.getChatOpen();

      IChatComponent var8;
      for(Iterator var9 = var6.iterator(); var9.hasNext(); this.field_146253_i.add(0, new ChatLine(var3, var8, var2))) {
         var8 = (IChatComponent)var9.next();
         if (var7 && this.scrollPos > 0) {
            this.isScrolled = true;
            this.scroll(1);
         }
      }

      while(this.field_146253_i.size() > 100) {
         this.field_146253_i.remove(this.field_146253_i.size() - 1);
      }

      if (!var4) {
         this.chatLines.add(0, new ChatLine(var3, var1, var2));

         while(this.chatLines.size() > 100) {
            this.chatLines.remove(this.chatLines.size() - 1);
         }
      }

   }

   public int getChatWidth() {
      return calculateChatboxWidth(this.mc.gameSettings.chatWidth);
   }

   public void scroll(int var1) {
      this.scrollPos += var1;
      int var2 = this.field_146253_i.size();
      if (this.scrollPos > var2 - this.getLineCount()) {
         this.scrollPos = var2 - this.getLineCount();
      }

      if (this.scrollPos <= 0) {
         this.scrollPos = 0;
         this.isScrolled = false;
      }

   }

   public static int calculateChatboxHeight(float var0) {
      short var1 = 180;
      byte var2 = 20;
      return MathHelper.floor_float(var0 * (float)(var1 - var2) + (float)var2);
   }

   public void drawChat(int var1) {
      if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
         int var2 = this.getLineCount();
         boolean var3 = false;
         int var4 = 0;
         int var5 = this.field_146253_i.size();
         float var6 = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;
         if (var5 > 0) {
            if (this.getChatOpen()) {
               var3 = true;
            }

            float var7 = this.getChatScale();
            int var8 = MathHelper.ceiling_float_int((float)this.getChatWidth() / var7);
            GlStateManager.pushMatrix();
            GlStateManager.translate(2.0F, 20.0F, 0.0F);
            GlStateManager.scale(var7, var7, 1.0F);

            int var9;
            int var10;
            int var11;
            for(var9 = 0; var9 + this.scrollPos < this.field_146253_i.size() && var9 < var2; ++var9) {
               ChatLine var12 = (ChatLine)this.field_146253_i.get(var9 + this.scrollPos);
               if (var12 != null) {
                  var10 = var1 - var12.getUpdatedCounter();
                  if (var10 < 200 || var3) {
                     double var13 = (double)var10 / 200.0D;
                     var13 = 1.0D - var13;
                     var13 *= 10.0D;
                     var13 = MathHelper.clamp_double(var13, 0.0D, 1.0D);
                     var13 *= var13;
                     var11 = (int)(255.0D * var13);
                     if (var3) {
                        var11 = 255;
                     }

                     var11 = (int)((float)var11 * var6);
                     ++var4;
                     if (var11 > 3) {
                        byte var15 = 0;
                        int var16 = -var9 * 9;
                        drawRect((double)var15, (double)(var16 - 9), (double)(var15 + var8 + 4), (double)var16, var11 / 2 << 24);
                        String var17 = var12.getChatComponent().getFormattedText();
                        GlStateManager.enableBlend();
                        this.mc.fontRendererObj.drawStringWithShadow(var17, (double)((float)var15), (double)((float)(var16 - 8)), 16777215 + (var11 << 24));
                        GlStateManager.disableAlpha();
                        GlStateManager.disableBlend();
                     }
                  }
               }
            }

            if (var3) {
               var9 = this.mc.fontRendererObj.FONT_HEIGHT;
               GlStateManager.translate(-3.0F, 0.0F, 0.0F);
               int var18 = var5 * var9 + var5;
               var10 = var4 * var9 + var4;
               int var19 = this.scrollPos * var10 / var5;
               int var14 = var10 * var10 / var18;
               if (var18 != var10) {
                  var11 = var19 > 0 ? 170 : 96;
                  int var20 = this.isScrolled ? 13382451 : 3355562;
                  drawRect(0.0D, (double)(-var19), 2.0D, (double)(-var19 - var14), var20 + (var11 << 24));
                  drawRect(2.0D, (double)(-var19), 1.0D, (double)(-var19 - var14), 13421772 + (var11 << 24));
               }
            }

            GlStateManager.popMatrix();
         }
      }

   }

   public List getSentMessages() {
      return this.sentMessages;
   }

   public float getChatScale() {
      return this.mc.gameSettings.chatScale;
   }

   public void refreshChat() {
      this.field_146253_i.clear();
      this.resetScroll();

      for(int var1 = this.chatLines.size() - 1; var1 >= 0; --var1) {
         ChatLine var2 = (ChatLine)this.chatLines.get(var1);
         this.setChatLine(var2.getChatComponent(), var2.getChatLineID(), var2.getUpdatedCounter(), true);
      }

   }

   public GuiNewChat(Minecraft var1) {
      this.mc = var1;
   }

   public void resetScroll() {
      this.scrollPos = 0;
      this.isScrolled = false;
   }
}
