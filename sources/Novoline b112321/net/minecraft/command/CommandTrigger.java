package net.minecraft.command;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandTrigger extends CommandBase {
   public String getCommandName() {
      return "trigger";
   }

   public int getRequiredPermissionLevel() {
      return 0;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.trigger.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if(var2.length < 3) {
         throw new WrongUsageException("commands.trigger.usage", new Object[0]);
      } else {
         EntityPlayerMP var3;
         if(var1 instanceof EntityPlayerMP) {
            var3 = (EntityPlayerMP)var1;
         } else {
            Entity var4 = var1.getCommandSenderEntity();
            if(!(var4 instanceof EntityPlayerMP)) {
               throw new CommandException("commands.trigger.invalidPlayer", new Object[0]);
            }

            var3 = (EntityPlayerMP)var4;
         }

         Scoreboard var8 = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
         ScoreObjective var5 = var8.getObjective(var2[0]);
         if(var5.getCriteria() == IScoreObjectiveCriteria.TRIGGER) {
            int var6 = parseInt(var2[2]);
            if(!var8.entityHasObjective(var3.getName(), var5)) {
               throw new CommandException("commands.trigger.invalidObjective", new Object[]{var2[0]});
            } else {
               Score var7 = var8.getValueFromObjective(var3.getName(), var5);
               if(var7.isLocked()) {
                  throw new CommandException("commands.trigger.disabled", new Object[]{var2[0]});
               } else {
                  if("set".equals(var2[1])) {
                     var7.setScorePoints(var6);
                  } else {
                     if(!"add".equals(var2[1])) {
                        throw new CommandException("commands.trigger.invalidMode", new Object[]{var2[1]});
                     }

                     var7.increseScore(var6);
                  }

                  var7.setLocked(true);
                  if(var3.theItemInWorldManager.isCreative()) {
                     notifyOperators(var1, this, "commands.trigger.success", new Object[]{var2[0], var2[1], var2[2]});
                  }

               }
            }
         } else {
            throw new CommandException("commands.trigger.invalidObjective", new Object[]{var2[0]});
         }
      }
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      if(var2.length == 1) {
         Scoreboard var4 = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
         ArrayList var5 = Lists.newArrayList();

         for(ScoreObjective var7 : var4.getScoreObjectives()) {
            if(var7.getCriteria() == IScoreObjectiveCriteria.TRIGGER) {
               var5.add(var7.getName());
            }
         }

         return getListOfStringsMatchingLastWord(var2, (String[])((String[])var5.toArray(new String[var5.size()])));
      } else {
         return var2.length == 2?getListOfStringsMatchingLastWord(var2, new String[]{"add", "set"}):null;
      }
   }

   private static CommandException a(CommandException var0) {
      return var0;
   }
}
