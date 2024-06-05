package net.minecraft.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent$Action;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandHelp extends CommandBase {
   public String getCommandName() {
      return "help";
   }

   public int getRequiredPermissionLevel() {
      return 0;
   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.help.usage";
   }

   public List getCommandAliases() {
      return Arrays.asList(new String[]{"?"});
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      List var3 = this.getSortedPossibleCommands(var1);
      int var4 = (var3.size() - 1) / 7;

      int var5;
      try {
         var5 = var2.length == 0?0:parseInt(var2[0], 1, var4 + 1) - 1;
      } catch (NumberInvalidException var11) {
         Map var7 = this.getCommands();
         ICommand var8 = (ICommand)var7.get(var2[0]);
         throw new WrongUsageException(var8.getCommandUsage(var1), new Object[0]);
      }

      int var6 = Math.min((var5 + 1) * 7, var3.size());
      ChatComponentTranslation var12 = new ChatComponentTranslation("commands.help.header", new Object[]{Integer.valueOf(var5 + 1), Integer.valueOf(var4 + 1)});
      var12.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
      var1.addChatMessage(var12);

      for(int var13 = var5 * 7; var13 < var6; ++var13) {
         ICommand var9 = (ICommand)var3.get(var13);
         ChatComponentTranslation var10 = new ChatComponentTranslation(var9.getCommandUsage(var1), new Object[0]);
         var10.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent$Action.SUGGEST_COMMAND, "/" + var9.getCommandName() + " "));
         var1.addChatMessage(var10);
      }

      if(var1 instanceof EntityPlayer) {
         ChatComponentTranslation var14 = new ChatComponentTranslation("commands.help.footer", new Object[0]);
         var14.getChatStyle().setColor(EnumChatFormatting.GREEN);
         var1.addChatMessage(var14);
      }

   }

   protected List getSortedPossibleCommands(ICommandSender var1) {
      List var2 = MinecraftServer.getServer().getCommandManager().getPossibleCommands(var1);
      Collections.sort(var2);
      return var2;
   }

   protected Map getCommands() {
      return MinecraftServer.getServer().getCommandManager().getCommands();
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      if(var2.length == 1) {
         Set var4 = this.getCommands().keySet();
         return getListOfStringsMatchingLastWord(var2, (String[])var4.toArray(new String[0]));
      } else {
         return null;
      }
   }

   private static NumberInvalidException a(NumberInvalidException var0) {
      return var0;
   }
}
