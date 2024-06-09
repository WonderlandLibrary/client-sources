package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.WorldSettings;

public class CommandGameMode extends CommandBase {
   public String getCommandName() {
      return "gamemode";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public String getCommandUsage(ICommandSender sender) {
      return "commands.gamemode.usage";
   }

   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
      if (args.length <= 0) {
         throw new WrongUsageException("commands.gamemode.usage", new Object[0]);
      } else {
         WorldSettings.GameType var3 = this.getGameModeFromCommand(sender, args[0]);
         EntityPlayerMP var4 = args.length >= 2 ? CommandBase.getPlayer(sender, args[1]) : CommandBase.getCommandSenderAsPlayer(sender);
         var4.setGameType(var3);
         var4.fallDistance = 0.0F;
         if (sender.getEntityWorld().getGameRules().getGameRuleBooleanValue("sendCommandFeedback")) {
            var4.addChatMessage(new ChatComponentTranslation("gameMode.changed", new Object[0]));
         }

         ChatComponentTranslation var5 = new ChatComponentTranslation("gameMode." + var3.getName(), new Object[0]);
         if (var4 != sender) {
            CommandBase.notifyOperators(sender, this, 1, "commands.gamemode.success.other", var4.getName(), var5);
         } else {
            CommandBase.notifyOperators(sender, this, 1, "commands.gamemode.success.self", var5);
         }

      }
   }

   protected WorldSettings.GameType getGameModeFromCommand(ICommandSender p_71539_1_, String p_71539_2_) throws CommandException {
      return !p_71539_2_.equalsIgnoreCase(WorldSettings.GameType.SURVIVAL.getName()) && !p_71539_2_.equalsIgnoreCase("s") ? (!p_71539_2_.equalsIgnoreCase(WorldSettings.GameType.CREATIVE.getName()) && !p_71539_2_.equalsIgnoreCase("c") ? (!p_71539_2_.equalsIgnoreCase(WorldSettings.GameType.ADVENTURE.getName()) && !p_71539_2_.equalsIgnoreCase("a") ? (!p_71539_2_.equalsIgnoreCase(WorldSettings.GameType.SPECTATOR.getName()) && !p_71539_2_.equalsIgnoreCase("sp") ? WorldSettings.getGameTypeById(CommandBase.parseInt(p_71539_2_, 0, WorldSettings.GameType.values().length - 2)) : WorldSettings.GameType.SPECTATOR) : WorldSettings.GameType.ADVENTURE) : WorldSettings.GameType.CREATIVE) : WorldSettings.GameType.SURVIVAL;
   }

   public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
      return args.length == 1 ? CommandBase.getListOfStringsMatchingLastWord(args, "survival", "creative", "adventure", "spectator") : (args.length == 2 ? CommandBase.getListOfStringsMatchingLastWord(args, this.getListOfPlayerUsernames()) : null);
   }

   protected String[] getListOfPlayerUsernames() {
      return MinecraftServer.getServer().getAllUsernames();
   }

   public boolean isUsernameIndex(String[] args, int index) {
      return index == 1;
   }
}
