package net.minecraft.command;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats$Type;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerSelector;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class CommandHandler implements ICommandManager {
   private static final Logger LOGGER = LogManager.getLogger();
   protected final Map commandMap = Maps.newHashMap();
   private final Set commandSet = Sets.newHashSet();
   private final String prefix;

   public CommandHandler(@NotNull String var1) {
      this.prefix = var1;
   }

   public int executeCommand(ICommandSender var1, String var2) {
      var2 = var2.trim();
      if(var2.startsWith(this.prefix)) {
         var2 = var2.substring(1);
      }

      String[] var3 = var2.split(" ");
      String var4 = var3[0];
      var3 = dropFirstString(var3);
      ICommand var5 = (ICommand)this.commandMap.get(var4);
      this.getUsernameIndex(var5, var3);
      byte var7 = 0;
      ChatComponentTranslation var8 = new ChatComponentTranslation("commands.generic.notFound", new Object[0]);
      var8.getChatStyle().setColor(EnumChatFormatting.RED);
      var1.addChatMessage(var8);
      var1.setCommandStat(CommandResultStats$Type.SUCCESS_COUNT, var7);
      return var7;
   }

   protected boolean tryExecute(ICommandSender var1, String[] var2, ICommand var3, String var4) {
      ICommand var10000 = var3;
      ICommandSender var10001 = var1;
      String[] var10002 = var2;

      try {
         var10000.processCommand(var10001, var10002);
         return true;
      } catch (WrongUsageException var7) {
         ChatComponentTranslation var11 = new ChatComponentTranslation("commands.generic.usage", new Object[]{new ChatComponentTranslation(var7.getMessage(), var7.getErrorObjects())});
         var11.getChatStyle().setColor(EnumChatFormatting.RED);
         var1.addChatMessage(var11);
      } catch (CommandException var8) {
         ChatComponentTranslation var10 = new ChatComponentTranslation(var8.getMessage(), var8.getErrorObjects());
         var10.getChatStyle().setColor(EnumChatFormatting.RED);
         var1.addChatMessage(var10);
      } catch (Throwable var9) {
         LOGGER.warn("Unexpected error occurred while processing command: \"" + var4 + "\"");
         ChatComponentTranslation var6 = new ChatComponentTranslation("commands.generic.exception", new Object[0]);
         var6.getChatStyle().setColor(EnumChatFormatting.RED);
         var1.addChatMessage(var6);
      }

      return false;
   }

   public void registerCommand(ICommand var1) {
      this.commandMap.put(var1.getCommandName(), var1);
      this.commandSet.add(var1);

      for(String var3 : var1.getCommandAliases()) {
         ICommand var4 = (ICommand)this.commandMap.get(var3);
         if(!var4.getCommandName().equals(var3)) {
            this.commandMap.put(var3, var1);
         }
      }

   }

   @NotNull
   protected static String[] dropFirstString(@NotNull String[] var0) {
      String[] var1 = new String[var0.length - 1];
      System.arraycopy(var0, 1, var1, 0, var0.length - 1);
      return var1;
   }

   public List getTabCompletionOptions(ICommandSender var1, @NotNull String var2, BlockPos var3) {
      String[] var4 = var2.split(" ", -1);
      String var5 = var4[0];
      if(var4.length == 1) {
         ArrayList var9 = Lists.newArrayList();

         for(Entry var8 : this.commandMap.entrySet()) {
            if(CommandBase.doesStringStartWith(var5, (String)var8.getKey()) && ((ICommand)var8.getValue()).canCommandSenderUseCommand(var1)) {
               var9.add(var8.getKey());
            }
         }

         return var9;
      } else {
         ICommand var6 = (ICommand)this.commandMap.get(var5);
         return var6.canCommandSenderUseCommand(var1)?var6.addTabCompletionOptions(var1, dropFirstString(var4), var3):null;
      }
   }

   public List getPossibleCommands(ICommandSender var1) {
      ArrayList var2 = Lists.newArrayList();

      for(ICommand var4 : this.commandSet) {
         if(var4.canCommandSenderUseCommand(var1)) {
            var2.add(var4);
         }
      }

      return var2;
   }

   public Map getCommands() {
      return this.commandMap;
   }

   private int getUsernameIndex(ICommand var1, String[] var2) {
      for(int var3 = 0; var3 < var2.length; ++var3) {
         if(var1.isUsernameIndex(var2, var3) && PlayerSelector.matchesMultiplePlayers(var2[var3])) {
            return var3;
         }
      }

      return -1;
   }
}
