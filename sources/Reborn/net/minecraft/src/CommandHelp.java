package net.minecraft.src;

import net.minecraft.server.*;
import java.util.*;

public class CommandHelp extends CommandBase
{
    @Override
    public String getCommandName() {
        return "help";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender par1ICommandSender) {
        return par1ICommandSender.translateString("commands.help.usage", new Object[0]);
    }
    
    @Override
    public List getCommandAliases() {
        return Arrays.asList("?");
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        final List var3 = this.getSortedPossibleCommands(par1ICommandSender);
        final byte var4 = 7;
        final int var5 = (var3.size() - 1) / var4;
        final boolean var6 = false;
        int var7;
        try {
            var7 = ((par2ArrayOfStr.length == 0) ? 0 : (CommandBase.parseIntBounded(par1ICommandSender, par2ArrayOfStr[0], 1, var5 + 1) - 1));
        }
        catch (NumberInvalidException var12) {
            final Map var8 = this.getCommands();
            final ICommand var9 = var8.get(par2ArrayOfStr[0]);
            if (var9 != null) {
                throw new WrongUsageException(var9.getCommandUsage(par1ICommandSender), new Object[0]);
            }
            throw new CommandNotFoundException();
        }
        final int var10 = Math.min((var7 + 1) * var4, var3.size());
        par1ICommandSender.sendChatToPlayer(EnumChatFormatting.DARK_GREEN + par1ICommandSender.translateString("commands.help.header", var7 + 1, var5 + 1));
        for (int var11 = var7 * var4; var11 < var10; ++var11) {
            final ICommand var9 = var3.get(var11);
            par1ICommandSender.sendChatToPlayer(var9.getCommandUsage(par1ICommandSender));
        }
        if (var7 == 0 && par1ICommandSender instanceof EntityPlayer) {
            par1ICommandSender.sendChatToPlayer(EnumChatFormatting.GREEN + par1ICommandSender.translateString("commands.help.footer", new Object[0]));
        }
    }
    
    protected List getSortedPossibleCommands(final ICommandSender par1ICommandSender) {
        final List var2 = MinecraftServer.getServer().getCommandManager().getPossibleCommands(par1ICommandSender);
        Collections.sort((List<Comparable>)var2);
        return var2;
    }
    
    protected Map getCommands() {
        return MinecraftServer.getServer().getCommandManager().getCommands();
    }
}
