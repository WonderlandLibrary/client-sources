package net.minecraft.command;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats$Type;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandStats extends CommandBase {
   public String getCommandName() {
      return "stats";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.stats.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if(var2.length < 1) {
         throw new WrongUsageException("commands.stats.usage", new Object[0]);
      } else {
         if(var2[0].equals("entity")) {
            boolean var3 = false;
         } else {
            if(!var2[0].equals("block")) {
               throw new WrongUsageException("commands.stats.usage", new Object[0]);
            }

            boolean var7 = true;
         }

         if(var2.length < 5) {
            throw new WrongUsageException("commands.stats.block.usage", new Object[0]);
         } else {
            int var4 = 4;
            String var5 = var2[var4++];
            if("set".equals(var5)) {
               if(var2.length < var4 + 3) {
                  if(var4 == 5) {
                     throw new WrongUsageException("commands.stats.block.set.usage", new Object[0]);
                  }

                  throw new WrongUsageException("commands.stats.entity.set.usage", new Object[0]);
               }
            } else {
               if(!"clear".equals(var5)) {
                  throw new WrongUsageException("commands.stats.usage", new Object[0]);
               }

               if(var2.length < var4 + 1) {
                  if(var4 == 5) {
                     throw new WrongUsageException("commands.stats.block.clear.usage", new Object[0]);
                  }

                  throw new WrongUsageException("commands.stats.entity.clear.usage", new Object[0]);
               }
            }

            CommandResultStats$Type var6 = CommandResultStats$Type.getTypeByName(var2[var4++]);
            throw new CommandException("commands.stats.failed", new Object[0]);
         }
      }
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 1?getListOfStringsMatchingLastWord(var2, new String[]{"entity", "block"}):(var2.length == 2 && var2[0].equals("entity")?getListOfStringsMatchingLastWord(var2, this.func_175776_d()):(var2.length >= 2 && var2.length <= 4 && var2[0].equals("block")?b(var2, 1, var3):((var2.length != 3 || !var2[0].equals("entity")) && (var2.length != 5 || !var2[0].equals("block"))?((var2.length != 4 || !var2[0].equals("entity")) && (var2.length != 6 || !var2[0].equals("block"))?(var2.length == 6 && var2[0].equals("entity") || var2.length == 8 && var2[0].equals("block")?getListOfStringsMatchingLastWord(var2, this.func_175777_e()):null):getListOfStringsMatchingLastWord(var2, CommandResultStats$Type.getTypeNames())):getListOfStringsMatchingLastWord(var2, new String[]{"set", "clear"}))));
   }

   protected String[] func_175776_d() {
      return MinecraftServer.getServer().getAllUsernames();
   }

   protected List func_175777_e() {
      Collection var1 = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard().getScoreObjectives();
      ArrayList var2 = Lists.newArrayList();

      for(ScoreObjective var4 : var1) {
         if(!var4.getCriteria().isReadOnly()) {
            var2.add(var4.getName());
         }
      }

      return var2;
   }

   public boolean isUsernameIndex(String[] var1, int var2) {
      return var1.length > 0 && var1[0].equals("entity") && var2 == 1;
   }

   private static CommandException a(CommandException var0) {
      return var0;
   }
}
