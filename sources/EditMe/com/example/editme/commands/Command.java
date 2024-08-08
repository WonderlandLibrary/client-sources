package com.example.editme.commands;

import com.example.editme.settings.Setting;
import com.example.editme.util.client.Wrapper;
import com.example.editme.util.command.syntax.SyntaxChunk;
import com.example.editme.util.setting.SettingsManager;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentBase;

public abstract class Command {
   protected String label;
   protected String syntax;
   public static Setting commandPrefix = SettingsManager.s("commandPrefix", ".");
   protected String description;
   protected SyntaxChunk[] syntaxChunks;

   public SyntaxChunk[] getSyntaxChunks() {
      return this.syntaxChunks;
   }

   public static char SECTIONSIGN() {
      return '§';
   }

   public abstract void call(String[] var1);

   public static String getCommandPrefix() {
      return (String)commandPrefix.getValue();
   }

   public Command(String var1, SyntaxChunk[] var2) {
      this.label = var1;
      this.syntaxChunks = var2;
      this.description = "Descriptionless";
   }

   public static void sendRawChatMessage(String var0) {
      Wrapper.getPlayer().func_145747_a(new Command.ChatMessage(var0));
   }

   protected SyntaxChunk getSyntaxChunk(String var1) {
      SyntaxChunk[] var2 = this.syntaxChunks;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         SyntaxChunk var5 = var2[var4];
         if (var5.getType().equals(var1)) {
            return var5;
         }
      }

      return null;
   }

   public String getLabel() {
      return this.label;
   }

   public static void sendChatMessage(String var0) {
      sendRawChatMessage(String.valueOf((new StringBuilder()).append("[").append(ChatFormatting.RED.toString()).append("editme").append(ChatFormatting.RESET.toString()).append("]").append(var0)));
   }

   protected void setDescription(String var1) {
      this.description = var1;
   }

   public String getDescription() {
      return this.description;
   }

   public static void sendStringChatMessage(String[] var0) {
      sendChatMessage("");
      String[] var1 = var0;
      int var2 = var0.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         String var4 = var1[var3];
         sendRawChatMessage(var4);
      }

   }

   public static class ChatMessage extends TextComponentBase {
      String text;

      public ChatMessage(String var1) {
         Pattern var2 = Pattern.compile("&[0123456789abcdefrlosmk]");
         Matcher var3 = var2.matcher(var1);
         StringBuffer var4 = new StringBuffer();

         while(var3.find()) {
            String var5 = String.valueOf((new StringBuilder()).append("§").append(var3.group().substring(1)));
            var3.appendReplacement(var4, var5);
         }

         var3.appendTail(var4);
         this.text = var4.toString();
      }

      public ITextComponent func_150259_f() {
         return new Command.ChatMessage(this.text);
      }

      public String func_150261_e() {
         return this.text;
      }
   }
}
