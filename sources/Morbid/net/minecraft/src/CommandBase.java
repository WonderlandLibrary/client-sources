package net.minecraft.src;

import net.minecraft.server.*;
import java.util.*;

public abstract class CommandBase implements ICommand
{
    private static IAdminCommand theAdmin;
    
    static {
        CommandBase.theAdmin = null;
    }
    
    public int getRequiredPermissionLevel() {
        return 4;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender par1ICommandSender) {
        return "/" + this.getCommandName();
    }
    
    @Override
    public List getCommandAliases() {
        return null;
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender par1ICommandSender) {
        return par1ICommandSender.canCommandSenderUseCommand(this.getRequiredPermissionLevel(), this.getCommandName());
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        return null;
    }
    
    public static int parseInt(final ICommandSender par0ICommandSender, final String par1Str) {
        try {
            return Integer.parseInt(par1Str);
        }
        catch (NumberFormatException var3) {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { par1Str });
        }
    }
    
    public static int parseIntWithMin(final ICommandSender par0ICommandSender, final String par1Str, final int par2) {
        return parseIntBounded(par0ICommandSender, par1Str, par2, Integer.MAX_VALUE);
    }
    
    public static int parseIntBounded(final ICommandSender par0ICommandSender, final String par1Str, final int par2, final int par3) {
        final int var4 = parseInt(par0ICommandSender, par1Str);
        if (var4 < par2) {
            throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] { var4, par2 });
        }
        if (var4 > par3) {
            throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] { var4, par3 });
        }
        return var4;
    }
    
    public static double parseDouble(final ICommandSender par0ICommandSender, final String par1Str) {
        try {
            return Double.parseDouble(par1Str);
        }
        catch (NumberFormatException var3) {
            throw new NumberInvalidException("commands.generic.double.invalid", new Object[] { par1Str });
        }
    }
    
    public static EntityPlayerMP getCommandSenderAsPlayer(final ICommandSender par0ICommandSender) {
        if (par0ICommandSender instanceof EntityPlayerMP) {
            return (EntityPlayerMP)par0ICommandSender;
        }
        throw new PlayerNotFoundException("You must specify which player you wish to perform this action on.", new Object[0]);
    }
    
    public static EntityPlayerMP func_82359_c(final ICommandSender par0ICommandSender, final String par1Str) {
        EntityPlayerMP var2 = PlayerSelector.matchOnePlayer(par0ICommandSender, par1Str);
        if (var2 != null) {
            return var2;
        }
        var2 = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(par1Str);
        if (var2 == null) {
            throw new PlayerNotFoundException();
        }
        return var2;
    }
    
    public static String func_96332_d(final ICommandSender par0ICommandSender, final String par1Str) {
        final EntityPlayerMP var2 = PlayerSelector.matchOnePlayer(par0ICommandSender, par1Str);
        if (var2 != null) {
            return var2.getEntityName();
        }
        if (PlayerSelector.hasArguments(par1Str)) {
            throw new PlayerNotFoundException();
        }
        return par1Str;
    }
    
    public static String func_82360_a(final ICommandSender par0ICommandSender, final String[] par1ArrayOfStr, final int par2) {
        return func_82361_a(par0ICommandSender, par1ArrayOfStr, par2, false);
    }
    
    public static String func_82361_a(final ICommandSender par0ICommandSender, final String[] par1ArrayOfStr, final int par2, final boolean par3) {
        final StringBuilder var4 = new StringBuilder();
        for (int var5 = par2; var5 < par1ArrayOfStr.length; ++var5) {
            if (var5 > par2) {
                var4.append(" ");
            }
            String var6 = par1ArrayOfStr[var5];
            if (par3) {
                final String var7 = PlayerSelector.matchPlayersAsString(par0ICommandSender, var6);
                if (var7 != null) {
                    var6 = var7;
                }
                else if (PlayerSelector.hasArguments(var6)) {
                    throw new PlayerNotFoundException();
                }
            }
            var4.append(var6);
        }
        return var4.toString();
    }
    
    public static String joinNiceString(final Object[] par0ArrayOfObj) {
        final StringBuilder var1 = new StringBuilder();
        for (int var2 = 0; var2 < par0ArrayOfObj.length; ++var2) {
            final String var3 = par0ArrayOfObj[var2].toString();
            if (var2 > 0) {
                if (var2 == par0ArrayOfObj.length - 1) {
                    var1.append(" and ");
                }
                else {
                    var1.append(", ");
                }
            }
            var1.append(var3);
        }
        return var1.toString();
    }
    
    public static String func_96333_a(final Collection par0Collection) {
        return joinNiceString(par0Collection.toArray(new String[0]));
    }
    
    public static boolean doesStringStartWith(final String par0Str, final String par1Str) {
        return par1Str.regionMatches(true, 0, par0Str, 0, par0Str.length());
    }
    
    public static List getListOfStringsMatchingLastWord(final String[] par0ArrayOfStr, final String... par1ArrayOfStr) {
        final String var2 = par0ArrayOfStr[par0ArrayOfStr.length - 1];
        final ArrayList var3 = new ArrayList();
        for (final String var6 : par1ArrayOfStr) {
            if (doesStringStartWith(var2, var6)) {
                var3.add(var6);
            }
        }
        return var3;
    }
    
    public static List getListOfStringsFromIterableMatchingLastWord(final String[] par0ArrayOfStr, final Iterable par1Iterable) {
        final String var2 = par0ArrayOfStr[par0ArrayOfStr.length - 1];
        final ArrayList var3 = new ArrayList();
        for (final String var5 : par1Iterable) {
            if (doesStringStartWith(var2, var5)) {
                var3.add(var5);
            }
        }
        return var3;
    }
    
    @Override
    public boolean isUsernameIndex(final String[] par1ArrayOfStr, final int par2) {
        return false;
    }
    
    public static void notifyAdmins(final ICommandSender par0ICommandSender, final String par1Str, final Object... par2ArrayOfObj) {
        notifyAdmins(par0ICommandSender, 0, par1Str, par2ArrayOfObj);
    }
    
    public static void notifyAdmins(final ICommandSender par0ICommandSender, final int par1, final String par2Str, final Object... par3ArrayOfObj) {
        if (CommandBase.theAdmin != null) {
            CommandBase.theAdmin.notifyAdmins(par0ICommandSender, par1, par2Str, par3ArrayOfObj);
        }
    }
    
    public static void setAdminCommander(final IAdminCommand par0IAdminCommand) {
        CommandBase.theAdmin = par0IAdminCommand;
    }
    
    public int compareTo(final ICommand par1ICommand) {
        return this.getCommandName().compareTo(par1ICommand.getCommandName());
    }
    
    @Override
    public int compareTo(final Object par1Obj) {
        return this.compareTo((ICommand)par1Obj);
    }
}
