package net.minecraft.src;

import net.minecraft.server.*;
import java.util.*;

public class CommandDifficulty extends CommandBase
{
    private static final String[] difficulties;
    
    static {
        difficulties = new String[] { "options.difficulty.peaceful", "options.difficulty.easy", "options.difficulty.normal", "options.difficulty.hard" };
    }
    
    @Override
    public String getCommandName() {
        return "difficulty";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender par1ICommandSender) {
        return par1ICommandSender.translateString("commands.difficulty.usage", new Object[0]);
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length > 0) {
            final int var3 = this.getDifficultyForName(par1ICommandSender, par2ArrayOfStr[0]);
            MinecraftServer.getServer().setDifficultyForAllWorlds(var3);
            final String var4 = StatCollector.translateToLocal(CommandDifficulty.difficulties[var3]);
            CommandBase.notifyAdmins(par1ICommandSender, "commands.difficulty.success", var4);
            return;
        }
        throw new WrongUsageException("commands.difficulty.usage", new Object[0]);
    }
    
    protected int getDifficultyForName(final ICommandSender par1ICommandSender, final String par2Str) {
        return (!par2Str.equalsIgnoreCase("peaceful") && !par2Str.equalsIgnoreCase("p")) ? ((!par2Str.equalsIgnoreCase("easy") && !par2Str.equalsIgnoreCase("e")) ? ((!par2Str.equalsIgnoreCase("normal") && !par2Str.equalsIgnoreCase("n")) ? ((!par2Str.equalsIgnoreCase("hard") && !par2Str.equalsIgnoreCase("h")) ? CommandBase.parseIntBounded(par1ICommandSender, par2Str, 0, 3) : 3) : 2) : 1) : 0;
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        return (par2ArrayOfStr.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, "peaceful", "easy", "normal", "hard") : null;
    }
}
