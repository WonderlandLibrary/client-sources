package net.minecraft.src;

import net.minecraft.server.*;

public class CommandDefaultGameMode extends CommandGameMode
{
    @Override
    public String getCommandName() {
        return "defaultgamemode";
    }
    
    @Override
    public String getCommandUsage(final ICommandSender par1ICommandSender) {
        return par1ICommandSender.translateString("commands.defaultgamemode.usage", new Object[0]);
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length > 0) {
            final EnumGameType var3 = this.getGameModeFromCommand(par1ICommandSender, par2ArrayOfStr[0]);
            this.setGameType(var3);
            final String var4 = StatCollector.translateToLocal("gameMode." + var3.getName());
            CommandBase.notifyAdmins(par1ICommandSender, "commands.defaultgamemode.success", var4);
            return;
        }
        throw new WrongUsageException("commands.defaultgamemode.usage", new Object[0]);
    }
    
    protected void setGameType(final EnumGameType par1EnumGameType) {
        MinecraftServer.getServer().setGameType(par1EnumGameType);
    }
}
