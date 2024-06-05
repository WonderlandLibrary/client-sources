package net.minecraft.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.WorldInfo;

public class CommandToggleDownfall extends CommandBase {
   public String getCommandName() {
      return "toggledownfall";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.downfall.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      this.toggleDownfall();
      notifyOperators(var1, this, "commands.downfall.success", new Object[0]);
   }

   protected void toggleDownfall() {
      WorldInfo var1 = MinecraftServer.getServer().worldServers[0].getWorldInfo();
      var1.setRaining(!var1.isRaining());
   }
}
