package net.minecraft.src;

import java.util.*;

public class CommandHandler implements ICommandManager
{
    private final Map commandMap;
    private final Set commandSet;
    
    public CommandHandler() {
        this.commandMap = new HashMap();
        this.commandSet = new HashSet();
    }
    
    @Override
    public int executeCommand(final ICommandSender par1ICommandSender, String par2Str) {
        par2Str = par2Str.trim();
        if (par2Str.startsWith("/")) {
            par2Str = par2Str.substring(1);
        }
        String[] var3 = par2Str.split(" ");
        final String var4 = var3[0];
        var3 = dropFirstString(var3);
        final ICommand var5 = this.commandMap.get(var4);
        final int var6 = this.getUsernameIndex(var5, var3);
        int var7 = 0;
        try {
            if (var5 == null) {
                throw new CommandNotFoundException();
            }
            if (var5.canCommandSenderUseCommand(par1ICommandSender)) {
                if (var6 > -1) {
                    final EntityPlayerMP[] var8 = PlayerSelector.matchPlayers(par1ICommandSender, var3[var6]);
                    final String var9 = var3[var6];
                    final EntityPlayerMP[] var10 = var8;
                    for (int var11 = var8.length, var12 = 0; var12 < var11; ++var12) {
                        final EntityPlayerMP var13 = var10[var12];
                        var3[var6] = var13.getEntityName();
                        try {
                            var5.processCommand(par1ICommandSender, var3);
                            ++var7;
                        }
                        catch (CommandException var14) {
                            par1ICommandSender.sendChatToPlayer(EnumChatFormatting.RED + par1ICommandSender.translateString(var14.getMessage(), var14.getErrorOjbects()));
                        }
                    }
                    var3[var6] = var9;
                }
                else {
                    var5.processCommand(par1ICommandSender, var3);
                    ++var7;
                }
            }
            else {
                par1ICommandSender.sendChatToPlayer(EnumChatFormatting.RED + "You do not have permission to use this command.");
            }
        }
        catch (WrongUsageException var15) {
            par1ICommandSender.sendChatToPlayer(EnumChatFormatting.RED + par1ICommandSender.translateString("commands.generic.usage", par1ICommandSender.translateString(var15.getMessage(), var15.getErrorOjbects())));
        }
        catch (CommandException var16) {
            par1ICommandSender.sendChatToPlayer(EnumChatFormatting.RED + par1ICommandSender.translateString(var16.getMessage(), var16.getErrorOjbects()));
        }
        catch (Throwable var17) {
            par1ICommandSender.sendChatToPlayer(EnumChatFormatting.RED + par1ICommandSender.translateString("commands.generic.exception", new Object[0]));
            var17.printStackTrace();
        }
        return var7;
    }
    
    public ICommand registerCommand(final ICommand par1ICommand) {
        final List var2 = par1ICommand.getCommandAliases();
        this.commandMap.put(par1ICommand.getCommandName(), par1ICommand);
        this.commandSet.add(par1ICommand);
        if (var2 != null) {
            for (final String var4 : var2) {
                final ICommand var5 = this.commandMap.get(var4);
                if (var5 == null || !var5.getCommandName().equals(var4)) {
                    this.commandMap.put(var4, par1ICommand);
                }
            }
        }
        return par1ICommand;
    }
    
    private static String[] dropFirstString(final String[] par0ArrayOfStr) {
        final String[] var1 = new String[par0ArrayOfStr.length - 1];
        for (int var2 = 1; var2 < par0ArrayOfStr.length; ++var2) {
            var1[var2 - 1] = par0ArrayOfStr[var2];
        }
        return var1;
    }
    
    @Override
    public List getPossibleCommands(final ICommandSender par1ICommandSender, final String par2Str) {
        final String[] var3 = par2Str.split(" ", -1);
        final String var4 = var3[0];
        if (var3.length == 1) {
            final ArrayList var5 = new ArrayList();
            for (final Map.Entry var7 : this.commandMap.entrySet()) {
                if (CommandBase.doesStringStartWith(var4, var7.getKey()) && var7.getValue().canCommandSenderUseCommand(par1ICommandSender)) {
                    var5.add(var7.getKey());
                }
            }
            return var5;
        }
        if (var3.length > 1) {
            final ICommand var8 = this.commandMap.get(var4);
            if (var8 != null) {
                return var8.addTabCompletionOptions(par1ICommandSender, dropFirstString(var3));
            }
        }
        return null;
    }
    
    @Override
    public List getPossibleCommands(final ICommandSender par1ICommandSender) {
        final ArrayList var2 = new ArrayList();
        for (final ICommand var4 : this.commandSet) {
            if (var4.canCommandSenderUseCommand(par1ICommandSender)) {
                var2.add(var4);
            }
        }
        return var2;
    }
    
    @Override
    public Map getCommands() {
        return this.commandMap;
    }
    
    private int getUsernameIndex(final ICommand par1ICommand, final String[] par2ArrayOfStr) {
        if (par1ICommand == null) {
            return -1;
        }
        for (int var3 = 0; var3 < par2ArrayOfStr.length; ++var3) {
            if (par1ICommand.isUsernameIndex(par2ArrayOfStr, var3) && PlayerSelector.matchesMultiplePlayers(par2ArrayOfStr[var3])) {
                return var3;
            }
        }
        return -1;
    }
}
