package net.minecraft.src;

import java.util.*;
import net.minecraft.server.*;

public class CommandGameMode extends CommandBase
{
    @Override
    public String getCommandName() {
        return "gamemode";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender par1ICommandSender) {
        return par1ICommandSender.translateString("commands.gamemode.usage", new Object[0]);
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length > 0) {
            final EnumGameType var3 = this.getGameModeFromCommand(par1ICommandSender, par2ArrayOfStr[0]);
            final EntityPlayerMP var4 = (par2ArrayOfStr.length >= 2) ? CommandBase.func_82359_c(par1ICommandSender, par2ArrayOfStr[1]) : CommandBase.getCommandSenderAsPlayer(par1ICommandSender);
            var4.setGameType(var3);
            var4.fallDistance = 0.0f;
            final String var5 = StatCollector.translateToLocal("gameMode." + var3.getName());
            if (var4 != par1ICommandSender) {
                CommandBase.notifyAdmins(par1ICommandSender, 1, "commands.gamemode.success.other", var4.getEntityName(), var5);
            }
            else {
                CommandBase.notifyAdmins(par1ICommandSender, 1, "commands.gamemode.success.self", var5);
            }
            return;
        }
        throw new WrongUsageException("commands.gamemode.usage", new Object[0]);
    }
    
    protected EnumGameType getGameModeFromCommand(final ICommandSender par1ICommandSender, final String par2Str) {
        return (!par2Str.equalsIgnoreCase(EnumGameType.SURVIVAL.getName()) && !par2Str.equalsIgnoreCase("s")) ? ((!par2Str.equalsIgnoreCase(EnumGameType.CREATIVE.getName()) && !par2Str.equalsIgnoreCase("c")) ? ((!par2Str.equalsIgnoreCase(EnumGameType.ADVENTURE.getName()) && !par2Str.equalsIgnoreCase("a")) ? WorldSettings.getGameTypeById(CommandBase.parseIntBounded(par1ICommandSender, par2Str, 0, EnumGameType.values().length - 2)) : EnumGameType.ADVENTURE) : EnumGameType.CREATIVE) : EnumGameType.SURVIVAL;
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        return (par2ArrayOfStr.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, "survival", "creative", "adventure") : ((par2ArrayOfStr.length == 2) ? CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, this.getListOfPlayerUsernames()) : null);
    }
    
    protected String[] getListOfPlayerUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public boolean isUsernameIndex(final String[] par1ArrayOfStr, final int par2) {
        return par2 == 1;
    }
}
