package net.minecraft.command.server;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;

public class CommandAchievement extends CommandBase {
   private static final String __OBFID = "CL_00000113";

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      if (var2.length == 1) {
         return getListOfStringsMatchingLastWord(var2, new String[]{"give", "take"});
      } else if (var2.length != 2) {
         return var2.length == 3 ? getListOfStringsMatchingLastWord(var2, MinecraftServer.getServer().getAllUsernames()) : null;
      } else {
         ArrayList var4 = Lists.newArrayList();
         Iterator var5 = StatList.allStats.iterator();

         while(var5.hasNext()) {
            StatBase var6 = (StatBase)var5.next();
            var4.add(var6.statId);
         }

         return func_175762_a(var2, var4);
      }
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.achievement.usage";
   }

   public String getCommandName() {
      return "achievement";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public boolean isUsernameIndex(String[] var1, int var2) {
      return var2 == 2;
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 2) {
         throw new WrongUsageException("commands.achievement.usage", new Object[0]);
      } else {
         StatBase var3 = StatList.getOneShotStat(var2[1]);
         if (var3 == null && !var2[1].equals("*")) {
            throw new CommandException("commands.achievement.unknownAchievement", new Object[]{var2[1]});
         } else {
            EntityPlayerMP var4 = var2.length >= 3 ? getPlayer(var1, var2[2]) : getCommandSenderAsPlayer(var1);
            boolean var5 = var2[0].equalsIgnoreCase("give");
            boolean var6 = var2[0].equalsIgnoreCase("take");
            if (var5 || var6) {
               if (var3 == null) {
                  Iterator var7;
                  Achievement var8;
                  if (var5) {
                     var7 = AchievementList.achievementList.iterator();

                     while(var7.hasNext()) {
                        var8 = (Achievement)var7.next();
                        var4.triggerAchievement(var8);
                     }

                     notifyOperators(var1, this, "commands.achievement.give.success.all", new Object[]{var4.getName()});
                  } else if (var6) {
                     var7 = Lists.reverse(AchievementList.achievementList).iterator();

                     while(var7.hasNext()) {
                        var8 = (Achievement)var7.next();
                        var4.func_175145_a(var8);
                     }

                     notifyOperators(var1, this, "commands.achievement.take.success.all", new Object[]{var4.getName()});
                  }
               } else {
                  if (var3 instanceof Achievement) {
                     Achievement var11 = (Achievement)var3;
                     Iterator var9;
                     Achievement var10;
                     ArrayList var12;
                     if (var5) {
                        if (var4.getStatFile().hasAchievementUnlocked(var11)) {
                           throw new CommandException("commands.achievement.alreadyHave", new Object[]{var4.getName(), var3.func_150955_j()});
                        }

                        for(var12 = Lists.newArrayList(); var11.parentAchievement != null && !var4.getStatFile().hasAchievementUnlocked(var11.parentAchievement); var11 = var11.parentAchievement) {
                           var12.add(var11.parentAchievement);
                        }

                        var9 = Lists.reverse(var12).iterator();

                        while(var9.hasNext()) {
                           var10 = (Achievement)var9.next();
                           var4.triggerAchievement(var10);
                        }
                     } else if (var6) {
                        if (!var4.getStatFile().hasAchievementUnlocked(var11)) {
                           throw new CommandException("commands.achievement.dontHave", new Object[]{var4.getName(), var3.func_150955_j()});
                        }

                        for(var12 = Lists.newArrayList(Iterators.filter(AchievementList.achievementList.iterator(), new Predicate(this, var4, var3) {
                           private final EntityPlayerMP val$var4;
                           final CommandAchievement this$0;
                           private static final String __OBFID = "CL_00002350";
                           private final StatBase val$var3;

                           public boolean func_179605_a(Achievement var1) {
                              return this.val$var4.getStatFile().hasAchievementUnlocked(var1) && var1 != this.val$var3;
                           }

                           public boolean apply(Object var1) {
                              return this.func_179605_a((Achievement)var1);
                           }

                           {
                              this.this$0 = var1;
                              this.val$var4 = var2;
                              this.val$var3 = var3;
                           }
                        })); var11.parentAchievement != null && var4.getStatFile().hasAchievementUnlocked(var11.parentAchievement); var11 = var11.parentAchievement) {
                           var12.remove(var11.parentAchievement);
                        }

                        var9 = var12.iterator();

                        while(var9.hasNext()) {
                           var10 = (Achievement)var9.next();
                           var4.func_175145_a(var10);
                        }
                     }
                  }

                  if (var5) {
                     var4.triggerAchievement(var3);
                     notifyOperators(var1, this, "commands.achievement.give.success.one", new Object[]{var4.getName(), var3.func_150955_j()});
                  } else if (var6) {
                     var4.func_175145_a(var3);
                     notifyOperators(var1, this, "commands.achievement.take.success.one", new Object[]{var3.func_150955_j(), var4.getName()});
                  }
               }
            }

         }
      }
   }
}
