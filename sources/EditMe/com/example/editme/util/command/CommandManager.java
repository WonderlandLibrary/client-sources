package com.example.editme.util.command;

import com.example.editme.EditmeMod;
import com.example.editme.commands.BindCommand;
import com.example.editme.commands.Command;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import org.reflections.Reflections;
import org.reflections.scanners.Scanner;

public class CommandManager {
   private ArrayList commands = new ArrayList();

   private static Set findClasses(String var0, Class var1) {
      Reflections var2 = new Reflections(var0, new Scanner[0]);
      return var2.getSubTypesOf(var1);
   }

   public static String[] removeElement(String[] var0, int var1) {
      LinkedList var2 = new LinkedList();

      for(int var3 = 0; var3 < var0.length; ++var3) {
         if (var3 != var1) {
            var2.add(var0[var3]);
         }
      }

      return (String[])((String[])var2.toArray(var0));
   }

   public ArrayList getCommands() {
      return this.commands;
   }

   public void callCommand(String var1) {
      String[] var2 = var1.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
      String var3 = var2[0].substring(1);
      String[] var4 = removeElement(var2, 0);

      for(int var5 = 0; var5 < var4.length; ++var5) {
         if (var4[var5] != null) {
            var4[var5] = strip(var4[var5], "\"");
         }
      }

      Iterator var7 = this.commands.iterator();

      Command var6;
      do {
         if (!var7.hasNext()) {
            Command.sendChatMessage("Unknown command. try 'commands' for a list of commands.");
            return;
         }

         var6 = (Command)var7.next();
      } while(!var6.getLabel().equalsIgnoreCase(var3));

      var6.call(var2);
   }

   public Command getCommandByLabel(String var1) {
      Iterator var2 = this.commands.iterator();

      Command var3;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         var3 = (Command)var2.next();
      } while(!var3.getLabel().equals(var1));

      return var3;
   }

   public CommandManager() {
      Set var1 = findClasses(BindCommand.class.getPackage().getName(), Command.class);
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         Class var3 = (Class)var2.next();
         if (Command.class.isAssignableFrom(var3)) {
            try {
               Command var4 = (Command)var3.getConstructor().newInstance();
               this.commands.add(var4);
            } catch (Exception var5) {
               var5.printStackTrace();
               System.err.println(String.valueOf((new StringBuilder()).append("Couldn't initiate command ").append(var3.getSimpleName()).append("! Err: ").append(var5.getClass().getSimpleName()).append(", message: ").append(var5.getMessage())));
            }
         }
      }

      EditmeMod.log.info("Commands initialised");
   }

   private static String strip(String var0, String var1) {
      return var0.startsWith(var1) && var0.endsWith(var1) ? var0.substring(var1.length(), var0.length() - var1.length()) : var0;
   }
}
