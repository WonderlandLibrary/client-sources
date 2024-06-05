package net.minecraft.command;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandExecuteAt$1;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class CommandExecuteAt extends CommandBase {
   public String getCommandName() {
      return "execute";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.execute.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if(var2.length < 5) {
         throw new WrongUsageException("commands.execute.usage", new Object[0]);
      } else {
         Entity var3 = getEntity(var1, var2[0], Entity.class);
         double var4 = parseDouble(var3.posX, var2[1], false);
         double var6 = parseDouble(var3.posY, var2[2], false);
         double var8 = parseDouble(var3.posZ, var2[3], false);
         BlockPos var10 = new BlockPos(var4, var6, var8);
         byte var11 = 4;
         if("detect".equals(var2[4]) && var2.length > 10) {
            World var12 = var3.getEntityWorld();
            double var13 = parseDouble(var4, var2[5], false);
            double var15 = parseDouble(var6, var2[6], false);
            double var17 = parseDouble(var8, var2[7], false);
            Block var19 = getBlockByText(var1, var2[8]);
            int var20 = parseInt(var2[9], -1, 15);
            BlockPos var21 = new BlockPos(var13, var15, var17);
            IBlockState var22 = var12.getBlockState(var21);
            if(var22.getBlock() != var19 || var22.getBlock().getMetaFromState(var22) != var20) {
               throw new CommandException("commands.execute.failed", new Object[]{"detect", var3.getName()});
            }

            var11 = 10;
         }

         String var24 = buildString(var2, var11);
         CommandExecuteAt$1 var25 = new CommandExecuteAt$1(this, var3, var1, var10, var4, var6, var8);
         ICommandManager var14 = MinecraftServer.getServer().getCommandManager();
         ICommandManager var10000 = var14;
         CommandExecuteAt$1 var10001 = var25;
         String var10002 = var24;

         try {
            int var26 = var10000.executeCommand(var10001, var10002);
            if(var26 < 1) {
               throw new CommandException("commands.execute.allInvocationsFailed", new Object[]{var24});
            }
         } catch (Throwable var23) {
            throw new CommandException("commands.execute.failed", new Object[]{var24, var3.getName()});
         }
      }
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 1?getListOfStringsMatchingLastWord(var2, MinecraftServer.getServer().getAllUsernames()):(var2.length > 1 && var2.length <= 4?b(var2, 1, var3):(var2.length > 5 && var2.length <= 8 && "detect".equals(var2[4])?b(var2, 5, var3):(var2.length == 9 && "detect".equals(var2[4])?getListOfStringsMatchingLastWord(var2, Block.blockRegistry.getKeys()):null)));
   }

   public boolean isUsernameIndex(String[] var1, int var2) {
      return true;
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
