package net.minecraft.command;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandSetSpawnpoint extends CommandBase {
   public String getCommandName() {
      return "spawnpoint";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.spawnpoint.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if(var2.length > 1 && var2.length < 4) {
         throw new WrongUsageException("commands.spawnpoint.usage", new Object[0]);
      } else {
         EntityPlayerMP var3 = var2.length > 0?getPlayer(var1, var2[0]):getCommandSenderAsPlayer(var1);
         BlockPos var4 = var2.length > 3?parseBlockPos(var1, var2, 1, true):var3.getPosition();
         if(var3.worldObj != null) {
            var3.setSpawnPoint(var4, true);
            notifyOperators(var1, this, "commands.spawnpoint.success", new Object[]{var3.getName(), Integer.valueOf(var4.getX()), Integer.valueOf(var4.getY()), Integer.valueOf(var4.getZ())});
         }

      }
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 1?getListOfStringsMatchingLastWord(var2, MinecraftServer.getServer().getAllUsernames()):(var2.length > 1 && var2.length <= 4?b(var2, 1, var3):null);
   }

   public boolean isUsernameIndex(String[] var1, int var2) {
      return true;
   }

   private static CommandException a(CommandException var0) {
      return var0;
   }
}
