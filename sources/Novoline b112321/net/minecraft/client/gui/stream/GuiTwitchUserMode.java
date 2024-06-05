package net.minecraft.client.gui.stream;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.stream.IStream;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import tv.twitch.chat.ChatUserInfo;
import tv.twitch.chat.ChatUserMode;
import tv.twitch.chat.ChatUserSubscription;

public class GuiTwitchUserMode extends GuiScreen {
   private static final EnumChatFormatting field_152331_a = EnumChatFormatting.DARK_GREEN;
   private static final EnumChatFormatting field_152335_f = EnumChatFormatting.RED;
   private static final EnumChatFormatting field_152336_g = EnumChatFormatting.DARK_PURPLE;
   private final ChatUserInfo field_152337_h;
   private final IChatComponent field_152338_i;
   private final List field_152332_r = Lists.newArrayList();
   private final IStream stream;
   private int field_152334_t;

   public GuiTwitchUserMode(IStream var1, ChatUserInfo var2) {
      this.stream = var1;
      this.field_152337_h = var2;
      this.field_152338_i = new ChatComponentText(var2.displayName);
      this.field_152332_r.addAll(func_152328_a(var2.modes, var2.subscriptions, var1));
   }

   public static List func_152328_a(Set var0, Set var1, IStream var2) {
      Object var3 = null;
      boolean var4 = var2.func_152927_B();
      ArrayList var5 = Lists.newArrayList();

      for(ChatUserMode var7 : var0) {
         IChatComponent var8 = func_152329_a(var7, (String)var3, var4);
         ChatComponentText var9 = new ChatComponentText("- ");
         var9.appendSibling(var8);
         var5.add(var9);
      }

      for(ChatUserSubscription var11 : var1) {
         IChatComponent var12 = func_152330_a(var11, (String)var3, var4);
         ChatComponentText var13 = new ChatComponentText("- ");
         var13.appendSibling(var12);
         var5.add(var13);
      }

      return var5;
   }

   public static IChatComponent func_152330_a(ChatUserSubscription var0, String var1, boolean var2) {
      ChatComponentTranslation var3 = null;
      if(var0 == ChatUserSubscription.TTV_CHAT_USERSUB_SUBSCRIBER) {
         var3 = new ChatComponentTranslation("stream.user.subscription.subscriber", new Object[0]);
         var3.getChatStyle().setColor(field_152331_a);
      } else if(var0 == ChatUserSubscription.TTV_CHAT_USERSUB_TURBO) {
         var3 = new ChatComponentTranslation("stream.user.subscription.turbo", new Object[0]);
         var3.getChatStyle().setColor(field_152336_g);
      }

      return var3;
   }

   public static IChatComponent func_152329_a(ChatUserMode var0, String var1, boolean var2) {
      ChatComponentTranslation var3 = null;
      if(var0 == ChatUserMode.TTV_CHAT_USERMODE_ADMINSTRATOR) {
         var3 = new ChatComponentTranslation("stream.user.mode.administrator", new Object[0]);
         var3.getChatStyle().setColor(field_152336_g);
      } else if(var0 == ChatUserMode.TTV_CHAT_USERMODE_BANNED) {
         var3 = new ChatComponentTranslation("stream.user.mode.banned", new Object[0]);
         var3.getChatStyle().setColor(field_152335_f);
      } else if(var0 == ChatUserMode.TTV_CHAT_USERMODE_BROADCASTER) {
         var3 = new ChatComponentTranslation("stream.user.mode.broadcaster", new Object[0]);
         var3.getChatStyle().setColor(field_152331_a);
      } else if(var0 == ChatUserMode.TTV_CHAT_USERMODE_MODERATOR) {
         var3 = new ChatComponentTranslation("stream.user.mode.moderator", new Object[0]);
         var3.getChatStyle().setColor(field_152331_a);
      } else if(var0 == ChatUserMode.TTV_CHAT_USERMODE_STAFF) {
         var3 = new ChatComponentTranslation("stream.user.mode.staff", new Object[0]);
         var3.getChatStyle().setColor(field_152336_g);
      }

      return var3;
   }

   public void initGui() {
      int var1 = this.width / 3;
      int var2 = var1 - 130;
      this.buttonList.add(new GuiButton(1, var1 * 0 + var2 / 2, this.height - 70, 130, 20, I18n.format("stream.userinfo.timeout", new Object[0])));
      this.buttonList.add(new GuiButton(0, var1 * 1 + var2 / 2, this.height - 70, 130, 20, I18n.format("stream.userinfo.ban", new Object[0])));
      this.buttonList.add(new GuiButton(2, var1 * 2 + var2 / 2, this.height - 70, 130, 20, I18n.format("stream.userinfo.mod", new Object[0])));
      this.buttonList.add(new GuiButton(5, var1 * 0 + var2 / 2, this.height - 45, 130, 20, I18n.format("gui.cancel", new Object[0])));
      this.buttonList.add(new GuiButton(3, var1 * 1 + var2 / 2, this.height - 45, 130, 20, I18n.format("stream.userinfo.unban", new Object[0])));
      this.buttonList.add(new GuiButton(4, var1 * 2 + var2 / 2, this.height - 45, 130, 20, I18n.format("stream.userinfo.unmod", new Object[0])));
      int var3 = 0;

      for(IChatComponent var5 : this.field_152332_r) {
         var3 = Math.max(var3, this.fontRendererObj.d(var5.getFormattedText()));
      }

      this.field_152334_t = this.width / 2 - var3 / 2;
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if(var1.enabled) {
         if(var1.id == 0) {
            this.stream.func_152917_b("/ban " + this.field_152337_h.displayName);
         } else if(var1.id == 3) {
            this.stream.func_152917_b("/unban " + this.field_152337_h.displayName);
         } else if(var1.id == 2) {
            this.stream.func_152917_b("/mod " + this.field_152337_h.displayName);
         } else if(var1.id == 4) {
            this.stream.func_152917_b("/unmod " + this.field_152337_h.displayName);
         } else if(var1.id == 1) {
            this.stream.func_152917_b("/timeout " + this.field_152337_h.displayName);
         }

         this.mc.displayGuiScreen((GuiScreen)null);
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, this.field_152338_i.getUnformattedText(), this.width / 2, 70, 16777215);
      int var4 = 80;

      for(IChatComponent var6 : this.field_152332_r) {
         this.drawString(this.fontRendererObj, var6.getFormattedText(), this.field_152334_t, var4, 16777215);
         var4 += this.fontRendererObj.getHeight();
      }

      super.drawScreen(var1, var2, var3);
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
