package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class GuiUtilRenderComponents {
   public static String func_178909_a(String var0, boolean var1) {
      return !Minecraft.getInstance().gameSettings.chatColours?EnumChatFormatting.a(var0):var0;
   }

   public static List func_178908_a(IChatComponent var0, int var1, FontRenderer var2, boolean var3, boolean var4) {
      byte var5 = 0;
      ChatComponentText var6 = new ChatComponentText("");
      ArrayList var7 = Lists.newArrayList();
      ArrayList var8 = Lists.newArrayList(var0);

      for(int var9 = 0; var9 < var8.size(); ++var9) {
         IChatComponent var10 = (IChatComponent)var8.get(var9);
         String var11 = var10.getUnformattedTextForChat();
         boolean var12 = false;
         if(var11.contains("\n")) {
            int var13 = var11.indexOf(10);
            String var14 = var11.substring(var13 + 1);
            var11 = var11.substring(0, var13 + 1);
            ChatComponentText var15 = new ChatComponentText(var14);
            var15.setChatStyle(var10.getChatStyle().createShallowCopy());
            var8.add(var9 + 1, var15);
            var12 = true;
         }

         String var24 = func_178909_a(var10.getChatStyle().getFormattingCode() + var11, var4);
         String var25 = var24.endsWith("\n")?var24.substring(0, var24.length() - 1):var24;
         int var26 = var2.d(var25);
         ChatComponentText var16 = new ChatComponentText(var25);
         var16.setChatStyle(var10.getChatStyle().createShallowCopy());
         if(var5 + var26 > var1) {
            String var17 = var2.trimStringToWidth(var24, var1 - var5, false);
            String var18 = var17.length() < var24.length()?var24.substring(var17.length()):null;
            if(!var18.isEmpty()) {
               int var19 = var17.lastIndexOf(" ");
               if(var2.d(var24.substring(0, var19)) > 0) {
                  var17 = var24.substring(0, var19);
                  ++var19;
                  var18 = var24.substring(var19);
               } else if(!var24.contains(" ")) {
                  var17 = "";
                  var18 = var24;
               }

               ChatComponentText var20 = new ChatComponentText(var18);
               var20.setChatStyle(var10.getChatStyle().createShallowCopy());
               var8.add(var9 + 1, var20);
            }

            var26 = var2.d(var17);
            var16 = new ChatComponentText(var17);
            var16.setChatStyle(var10.getChatStyle().createShallowCopy());
            var12 = true;
         }

         if(var5 + var26 <= var1) {
            int var10000 = var5 + var26;
            var6.appendSibling(var16);
         } else {
            var12 = true;
         }

         var7.add(var6);
         var5 = 0;
         var6 = new ChatComponentText("");
      }

      var7.add(var6);
      return var7;
   }

   public static List func_178908_a(IChatComponent var0, int var1, cc.novoline.utils.fonts.api.FontRenderer var2, boolean var3, boolean var4) {
      byte var5 = 0;
      ChatComponentText var6 = new ChatComponentText("");
      ArrayList var7 = Lists.newArrayList();
      ArrayList var8 = Lists.newArrayList(var0);

      for(int var9 = 0; var9 < var8.size(); ++var9) {
         IChatComponent var10 = (IChatComponent)var8.get(var9);
         String var11 = var10.getUnformattedTextForChat();
         boolean var12 = false;
         if(var11.contains("\n")) {
            int var13 = var11.indexOf(10);
            String var14 = var11.substring(var13 + 1);
            var11 = var11.substring(0, var13 + 1);
            ChatComponentText var15 = new ChatComponentText(var14);
            var15.setChatStyle(var10.getChatStyle().createShallowCopy());
            var8.add(var9 + 1, var15);
            var12 = true;
         }

         String var24 = func_178909_a(var10.getChatStyle().getFormattingCode() + var11, var4);
         String var25 = var24.endsWith("\n")?var24.substring(0, var24.length() - 1):var24;
         int var26 = var2.stringWidth(var25);
         ChatComponentText var16 = new ChatComponentText(var25);
         var16.setChatStyle(var10.getChatStyle().createShallowCopy());
         if(var5 + var26 > var1) {
            String var17 = var2.trimStringToWidth(var24, var1 - var5, false);
            String var18 = var17.length() < var24.length()?var24.substring(var17.length()):null;
            if(!var18.isEmpty()) {
               int var19 = var17.lastIndexOf(" ");
               if(var2.stringWidth(var24.substring(0, var19)) > 0) {
                  var17 = var24.substring(0, var19);
                  ++var19;
                  var18 = var24.substring(var19);
               } else if(!var24.contains(" ")) {
                  var17 = "";
                  var18 = var24;
               }

               ChatComponentText var20 = new ChatComponentText(var18);
               var20.setChatStyle(var10.getChatStyle().createShallowCopy());
               var8.add(var9 + 1, var20);
            }

            var26 = var2.stringWidth(var17);
            var16 = new ChatComponentText(var17);
            var16.setChatStyle(var10.getChatStyle().createShallowCopy());
            var12 = true;
         }

         if(var5 + var26 <= var1) {
            int var10000 = var5 + var26;
            var6.appendSibling(var16);
         } else {
            var12 = true;
         }

         var7.add(var6);
         var5 = 0;
         var6 = new ChatComponentText("");
      }

      var7.add(var6);
      return var7;
   }
}
