package com.example.editme.commands;

import com.example.editme.util.command.syntax.SyntaxChunk;
import com.example.editme.util.module.ModuleManager;
import java.util.Arrays;

public class HelpCommand extends Command {
   private static final HelpCommand.Subject[] subjects = new HelpCommand.Subject[]{new HelpCommand.Subject(new String[]{"type", "int", "boolean", "double", "float"}, new String[]{"Every module has a value, and that value is always of a certain &btype.\n", "These types are displayed in editme as the ones java use. They mean the following:", "&bboolean&r: Enabled or not. Values &3true/false", "&bfloat&r: A number with a decimal point", "&bdouble&r: Like a float, but a more accurate decimal point", "&bint&r: A number with no decimal point"})};
   private static String subjectsList = "";

   public void call(String[] var1) {
      if (var1[0] == null) {
         Command.sendStringChatMessage(new String[]{"editme r5", "commands&7 to view all available commands", "bind <module> <key>&7 to bind mods", String.valueOf((new StringBuilder()).append("&7Press &r").append(ModuleManager.getModuleByName("ClickGUI").getBindName()).append("&7 to open GUI")), "prefix <prefix>&r to change the command prefix.", "help <subjects:[subject]> &r for more help."});
      } else {
         String var2 = var1[0];
         if (var2.equals("subjects")) {
            Command.sendChatMessage(String.valueOf((new StringBuilder()).append("Subjects: ").append(subjectsList)));
         } else {
            HelpCommand.Subject var3 = (HelpCommand.Subject)Arrays.stream(subjects).filter(HelpCommand::lambda$call$0).findFirst().orElse((Object)null);
            if (var3 == null) {
               Command.sendChatMessage(String.valueOf((new StringBuilder()).append("No help found for &b").append(var1[0])));
               return;
            }

            Command.sendStringChatMessage(var3.info);
         }
      }

   }

   static {
      HelpCommand.Subject[] var0 = subjects;
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         HelpCommand.Subject var3 = var0[var2];
         subjectsList = String.valueOf((new StringBuilder()).append(subjectsList).append(var3.names[0]).append(", "));
      }

      subjectsList = subjectsList.substring(0, subjectsList.length() - 2);
   }

   public HelpCommand() {
      super("help", new SyntaxChunk[0]);
      this.setDescription("Delivers help on certain subjects. Use &b-help subjects&r for a list.");
   }

   private static boolean lambda$call$0(String var0, HelpCommand.Subject var1) {
      String[] var2 = var1.names;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String var5 = var2[var4];
         if (var5.equalsIgnoreCase(var0)) {
            return true;
         }
      }

      return false;
   }

   private static class Subject {
      String[] names;
      String[] info;

      public Subject(String[] var1, String[] var2) {
         this.names = var1;
         this.info = var2;
      }
   }
}
