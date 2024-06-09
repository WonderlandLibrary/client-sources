package net.minecraft.command;

import java.util.List;
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

   public String getCommandUsage(ICommandSender sender) {
      return "commands.spawnpoint.usage";
   }

   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
      if (args.length > 0 && args.length < 4) {
         throw new WrongUsageException("commands.spawnpoint.usage", new Object[0]);
      } else {
         EntityPlayerMP var3 = args.length > 0 ? CommandBase.getPlayer(sender, args[0]) : CommandBase.getCommandSenderAsPlayer(sender);
         BlockPos var4 = args.length > 3 ? CommandBase.func_175757_a(sender, args, 1, true) : var3.getPosition();
         if (var3.worldObj != null) {
            var3.func_180473_a(var4, true);
            CommandBase.notifyOperators(sender, this, "commands.spawnpoint.success", var3.getName(), var4.getX(), var4.getY(), var4.getZ());
         }

      }
   }

   public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
      return args.length == 1 ? CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : (args.length > 1 && args.length <= 4 ? CommandBase.func_175771_a(args, 1, pos) : null);
   }

   public boolean isUsernameIndex(String[] args, int index) {
      return index == 0;
   }
}
