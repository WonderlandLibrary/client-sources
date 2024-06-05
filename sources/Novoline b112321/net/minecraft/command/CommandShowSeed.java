package net.minecraft.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public class CommandShowSeed extends CommandBase {
   public boolean canCommandSenderUseCommand(ICommandSender var1) {
      return MinecraftServer.getServer().isSinglePlayer() || super.canCommandSenderUseCommand(var1);
   }

   public String getCommandName() {
      return "seed";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.seed.usage";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      Object var3 = var1 instanceof EntityPlayer?((EntityPlayer)var1).worldObj:MinecraftServer.getServer().worldServerForDimension(0);
      var1.addChatMessage(new ChatComponentTranslation("commands.seed.success", new Object[]{Long.valueOf(((World)var3).getSeed())}));
   }

   private static CommandException a(CommandException var0) {
      return var0;
   }
}
