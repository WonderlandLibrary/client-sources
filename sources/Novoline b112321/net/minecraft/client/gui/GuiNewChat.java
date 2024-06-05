package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.a2t;
import net.aw5;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer$EnumChatVisibility;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class GuiNewChat extends Gui {
   private static final Logger logger = LogManager.getLogger();
   private final Minecraft mc;
   private final List sentMessages = Lists.newArrayList();
   private final List chatLines = Lists.newArrayList();
   private final List field_146253_i = Lists.newArrayList();
   private int scrollPos;
   private boolean l;

   public GuiNewChat(Minecraft var1) {
      this.mc = var1;
   }

   public void c(int var1) {
      if(this.mc.gameSettings.chatVisibility != EntityPlayer$EnumChatVisibility.HIDDEN) {
         int var2 = this.getLineCount();
         boolean var3 = false;
         boolean var4 = false;
         int var5 = this.field_146253_i.size();
         float var6 = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;
      }
   }

   public List a() {
      return this.chatLines;
   }

   public List o() {
      return this.field_146253_i;
   }

   public void clearChatMessages() {
      this.field_146253_i.clear();
      this.chatLines.clear();
      this.sentMessages.clear();
   }

   public void printChatMessage(IChatComponent var1) {
      this.printChatMessageWithOptionalDeletion(var1, 0);
   }

   public void printChatMessageWithOptionalDeletion(@NotNull IChatComponent var1, int var2) {
      this.setChatLine(var1, var2, this.mc.ingameGUI.getUpdateCounter(), false);
      logger.info("[CHAT] " + var1.getUnformattedText());
   }

   private void setChatLine(IChatComponent var1, int var2, int var3, boolean var4) {
      this.deleteChatLine(var2);
      int var5 = MathHelper.floor_float((float)this.getChatWidth() / this.getChatScale());
      List var6 = this.a(var1, var5);
      boolean var7 = this.getChatOpen();

      for(IChatComponent var9 : var6) {
         if(this.scrollPos > 0) {
            this.l = true;
            this.scroll(1);
         }

         this.field_146253_i.add(0, new ChatLine(var3, var9, var2));
      }

      while(this.field_146253_i.size() > 1000) {
         this.field_146253_i.remove(this.field_146253_i.size() - 1);
      }

      this.chatLines.add(0, new ChatLine(var3, var1, var2));

      while(this.chatLines.size() > 1000) {
         this.chatLines.remove(this.chatLines.size() - 1);
      }

   }

   public void refreshChat() {
      this.field_146253_i.clear();
      this.resetScroll();
      int var1 = this.chatLines.size() - 1;

      while(true) {
         ChatLine var2 = (ChatLine)this.chatLines.get(var1);
         this.setChatLine(var2.getChatComponent(), var2.getChatLineID(), var2.getUpdatedCounter(), true);
         --var1;
      }
   }

   public List getSentMessages() {
      return this.sentMessages;
   }

   public void addToSentMessages(String var1) {
      if(this.sentMessages.isEmpty() || !((String)this.sentMessages.get(this.sentMessages.size() - 1)).equals(var1)) {
         this.sentMessages.add(var1);
      }

   }

   public void resetScroll() {
      this.scrollPos = 0;
      this.l = false;
   }

   public void scroll(int var1) {
      this.scrollPos += var1;
      int var2 = this.field_146253_i.size();
      if(this.scrollPos > var2 - this.getLineCount()) {
         this.scrollPos = var2 - this.getLineCount();
      }

      if(this.scrollPos <= 0) {
         this.scrollPos = 0;
         this.l = false;
      }

   }

   public IChatComponent a(int var1, int var2) {
      if(!this.getChatOpen()) {
         return null;
      } else {
         ScaledResolution var3 = new ScaledResolution(this.mc);
         int var4 = var3.getScaleFactor();
         float var5 = this.getChatScale();
         int var6 = var1 / var4 - 3;
         int var7 = var2 / var4 - 27;
         var6 = MathHelper.floor_float((float)var6 / var5);
         var7 = MathHelper.floor_float((float)var7 / var5);
         return null;
      }
   }

   public boolean getChatOpen() {
      return this.mc.currentScreen instanceof GuiChat;
   }

   public void deleteChatLine(int var1) {
      Iterator var2 = this.field_146253_i.iterator();

      while(var2.hasNext()) {
         ChatLine var3 = (ChatLine)var2.next();
         if(var3.getChatLineID() == var1) {
            var2.remove();
         }
      }

      var2 = this.chatLines.iterator();

      while(var2.hasNext()) {
         ChatLine var5 = (ChatLine)var2.next();
         if(var5.getChatLineID() == var1) {
            var2.remove();
            break;
         }
      }

   }

   public int getChatWidth() {
      return calculateChatboxWidth(this.mc.gameSettings.chatWidth);
   }

   public int getChatHeight() {
      return calculateChatboxHeight(this.getChatOpen()?this.mc.gameSettings.chatHeightFocused:this.mc.gameSettings.chatHeightUnfocused);
   }

   public float getChatScale() {
      return this.mc.gameSettings.chatScale;
   }

   public static int calculateChatboxWidth(float var0) {
      boolean var1 = true;
      boolean var2 = true;
      return MathHelper.floor_float(var0 * 280.0F + 40.0F);
   }

   public static int calculateChatboxHeight(float var0) {
      boolean var1 = true;
      boolean var2 = true;
      return MathHelper.floor_float(var0 * 160.0F + 20.0F);
   }

   public int getLineCount() {
      return this.getChatHeight() / 9;
   }

   private boolean g() {
      return false;
   }

   private List a(IChatComponent var1, int var2) {
      return this.g()?GuiUtilRenderComponents.a(var1, var2, this.j(), false, false):GuiUtilRenderComponents.func_178908_a(var1, var2, this.n(), false, false);
   }

   private int i() {
      return this.g()?this.j().c():this.n().f();
   }

   private float b(String var1) {
      return this.g()?(float)this.j().a(var1):(float)this.n().d(var1);
   }

   private void a(String var1, float var2, float var3, int var4) {
      if(this.g()) {
         this.j().a(var1, (double)var2, (double)var3, var4, true);
      } else {
         this.n().drawString(var1, var2, var3, var4, true);
      }

   }

   private FontRenderer n() {
      return Minecraft.getMinecraft().fontRendererObj;
   }

   private a2t j() {
      return aw5.a;
   }
}
