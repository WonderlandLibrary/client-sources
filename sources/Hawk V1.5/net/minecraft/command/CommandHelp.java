package net.minecraft.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

public class CommandHelp extends CommandBase {
   private static final String __OBFID = "CL_00000529";

   public int getRequiredPermissionLevel() {
      return 0;
   }

   public List addTabCompletionOptions(ICommandSender var1, String[] var2, BlockPos var3) {
      if (var2.length == 1) {
         Set var4 = this.getCommands().keySet();
         return getListOfStringsMatchingLastWord(var2, (String[])var4.toArray(new String[var4.size()]));
      } else {
         return null;
      }
   }

   protected List getSortedPossibleCommands(ICommandSender var1) {
      List var2 = MinecraftServer.getServer().getCommandManager().getPossibleCommands(var1);
      Collections.sort(var2);
      return var2;
   }

   public String getCommandName() {
      return "help";
   }

   public void processCommand(ICommandSender var1, String[] var2) throws CommandException {
      List var3 = this.getSortedPossibleCommands(var1);
      boolean var4 = true;
      int var5 = (var3.size() - 1) / 7;
      boolean var6 = false;

      int var7;
      try {
         var7 = var2.length == 0 ? 0 : parseInt(var2[0], 1, var5 + 1) - 1;
      } catch (NumberInvalidException var13) {
         Map var9 = this.getCommands();
         ICommand var10 = (ICommand)var9.get(var2[0]);
         if (var10 != null) {
            throw new WrongUsageException(var10.getCommandUsage(var1), new Object[0]);
         }

         if (MathHelper.parseIntWithDefault(var2[0], -1) != -1) {
            throw var13;
         }

         throw new CommandNotFoundException();
      }

      int var8 = Math.min((var7 + 1) * 7, var3.size());
      ChatComponentTranslation var14 = new ChatComponentTranslation("commands.help.header", new Object[]{var7 + 1, var5 + 1});
      var14.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
      var1.addChatMessage(var14);

      for(int var15 = var7 * 7; var15 < var8; ++var15) {
         ICommand var11 = (ICommand)var3.get(var15);
         ChatComponentTranslation var12 = new ChatComponentTranslation(var11.getCommandUsage(var1), new Object[0]);
         var12.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, String.valueOf((new StringBuilder("/")).append(var11.getCommandName()).append(" "))));
         var1.addChatMessage(var12);
      }

      if (var7 == 0 && var1 instanceof EntityPlayer) {
         ChatComponentTranslation var16 = new ChatComponentTranslation("commands.help.footer", new Object[0]);
         var16.getChatStyle().setColor(EnumChatFormatting.GREEN);
         var1.addChatMessage(var16);
      }

   }

   public String getCommandUsage(ICommandSender var1) {
      return "commands.help.usage";
   }

   public List getCommandAliases() {
      return Arrays.asList("?");
   }

   protected Map getCommands() {
      return MinecraftServer.getServer().getCommandManager().getCommands();
   }
}
