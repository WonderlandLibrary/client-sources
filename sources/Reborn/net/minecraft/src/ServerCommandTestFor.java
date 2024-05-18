package net.minecraft.src;

public class ServerCommandTestFor extends CommandBase
{
    @Override
    public String getCommandName() {
        return "testfor";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length != 1) {
            throw new WrongUsageException("commands.testfor.usage", new Object[0]);
        }
        if (!(par1ICommandSender instanceof TileEntityCommandBlock)) {
            throw new CommandException("commands.testfor.failed", new Object[0]);
        }
        CommandBase.func_82359_c(par1ICommandSender, par2ArrayOfStr[0]);
    }
    
    @Override
    public boolean isUsernameIndex(final String[] par1ArrayOfStr, final int par2) {
        return par2 == 0;
    }
}
