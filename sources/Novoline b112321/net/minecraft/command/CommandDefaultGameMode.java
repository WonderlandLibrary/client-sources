package net.minecraft.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.CommandGameMode;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.WorldSettings$GameType;

public class CommandDefaultGameMode extends CommandGameMode {
   public String getCommandName() {
      return "defaultgamemode";
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.defaultgamemode.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      if(var2.length <= 0) {
         throw new WrongUsageException("commands.defaultgamemode.usage", new Object[0]);
      } else {
         WorldSettings$GameType var3 = this.getGameModeFromCommand(var1, var2[0]);
         this.setGameType(var3);
         notifyOperators(var1, this, "commands.defaultgamemode.success", new Object[]{new ChatComponentTranslation("gameMode." + var3.getName(), new Object[0])});
      }
   }

   protected void setGameType(WorldSettings$GameType var1) {
      MinecraftServer var2 = MinecraftServer.getServer();
      var2.setGameType(var1);
      if(var2.getForceGamemode()) {
         for(EntityPlayerMP var4 : MinecraftServer.getServer().getConfigurationManager().func_181057_v()) {
            var4.setGameType(var1);
            var4.fallDistance = 0.0F;
         }
      }

   }

   private static CommandException a(CommandException var0) {
      return var0;
   }
}
