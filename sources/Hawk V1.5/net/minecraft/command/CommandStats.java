package net.minecraft.command;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class CommandStats extends CommandBase {
   private static final String __OBFID = "CL_00002339";

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 1 ? getListOfStringsMatchingLastWord(var2, new String[]{"entity", "block"}) : (var2.length == 2 && var2[0].equals("entity") ? getListOfStringsMatchingLastWord(var2, this.func_175776_d()) : (var2.length == 3 && var2[0].equals("entity") || var2.length == 5 && var2[0].equals("block") ? getListOfStringsMatchingLastWord(var2, new String[]{"set", "clear"}) : ((var2.length != 4 || !var2[0].equals("entity")) && (var2.length != 6 || !var2[0].equals("block")) ? (var2.length == 6 && var2[0].equals("entity") || var2.length == 8 && var2[0].equals("block") ? func_175762_a(var2, this.func_175777_e()) : null) : getListOfStringsMatchingLastWord(var2, CommandResultStats.Type.func_179634_c()))));
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.stats.usage";
   }

   public String getCommandName() {
      return "stats";
   }

   public boolean isUsernameIndex(String[] var1, int var2) {
      return var1.length > 0 && var1[0].equals("entity") && var2 == 1;
   }

   protected List func_175777_e() {
      Collection var1 = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard().getScoreObjectives();
      ArrayList var2 = Lists.newArrayList();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         ScoreObjective var4 = (ScoreObjective)var3.next();
         if (!var4.getCriteria().isReadOnly()) {
            var2.add(var4.getName());
         }
      }

      return var2;
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 1) {
         throw new WrongUsageException("commands.stats.usage", new Object[0]);
      } else {
         boolean var3;
         if (var2[0].equals("entity")) {
            var3 = false;
         } else {
            if (!var2[0].equals("block")) {
               throw new WrongUsageException("commands.stats.usage", new Object[0]);
            }

            var3 = true;
         }

         byte var4;
         if (var3) {
            if (var2.length < 5) {
               throw new WrongUsageException("commands.stats.block.usage", new Object[0]);
            }

            var4 = 4;
         } else {
            if (var2.length < 3) {
               throw new WrongUsageException("commands.stats.entity.usage", new Object[0]);
            }

            var4 = 2;
         }

         int var5 = var4 + 1;
         String var6 = var2[var4];
         if ("set".equals(var6)) {
            if (var2.length < var5 + 3) {
               if (var5 == 5) {
                  throw new WrongUsageException("commands.stats.block.set.usage", new Object[0]);
               }

               throw new WrongUsageException("commands.stats.entity.set.usage", new Object[0]);
            }
         } else {
            if (!"clear".equals(var6)) {
               throw new WrongUsageException("commands.stats.usage", new Object[0]);
            }

            if (var2.length < var5 + 1) {
               if (var5 == 5) {
                  throw new WrongUsageException("commands.stats.block.clear.usage", new Object[0]);
               }

               throw new WrongUsageException("commands.stats.entity.clear.usage", new Object[0]);
            }
         }

         CommandResultStats.Type var7 = CommandResultStats.Type.func_179635_a(var2[var5++]);
         if (var7 == null) {
            throw new CommandException("commands.stats.failed", new Object[0]);
         } else {
            World var8 = var1.getEntityWorld();
            CommandResultStats var9;
            BlockPos var10;
            TileEntity var11;
            if (var3) {
               var10 = func_175757_a(var1, var2, 1, false);
               var11 = var8.getTileEntity(var10);
               if (var11 == null) {
                  throw new CommandException("commands.stats.noCompatibleBlock", new Object[]{var10.getX(), var10.getY(), var10.getZ()});
               }

               if (var11 instanceof TileEntityCommandBlock) {
                  var9 = ((TileEntityCommandBlock)var11).func_175124_c();
               } else {
                  if (!(var11 instanceof TileEntitySign)) {
                     throw new CommandException("commands.stats.noCompatibleBlock", new Object[]{var10.getX(), var10.getY(), var10.getZ()});
                  }

                  var9 = ((TileEntitySign)var11).func_174880_d();
               }
            } else {
               Entity var12 = func_175768_b(var1, var2[1]);
               var9 = var12.func_174807_aT();
            }

            if ("set".equals(var6)) {
               String var14 = var2[var5++];
               String var13 = var2[var5];
               if (var14.length() == 0 || var13.length() == 0) {
                  throw new CommandException("commands.stats.failed", new Object[0]);
               }

               CommandResultStats.func_179667_a(var9, var7, var14, var13);
               notifyOperators(var1, this, "commands.stats.success", new Object[]{var7.func_179637_b(), var13, var14});
            } else if ("clear".equals(var6)) {
               CommandResultStats.func_179667_a(var9, var7, (String)null, (String)null);
               notifyOperators(var1, this, "commands.stats.cleared", new Object[]{var7.func_179637_b()});
            }

            if (var3) {
               var10 = func_175757_a(var1, var2, 1, false);
               var11 = var8.getTileEntity(var10);
               var11.markDirty();
            }

         }
      }
   }

   protected String[] func_175776_d() {
      return MinecraftServer.getServer().getAllUsernames();
   }
}
