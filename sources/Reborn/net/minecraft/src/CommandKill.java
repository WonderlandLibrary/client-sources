package net.minecraft.src;

public class CommandKill extends CommandBase
{
    @Override
    public String getCommandName() {
        return "kill";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        final EntityPlayerMP var3 = CommandBase.getCommandSenderAsPlayer(par1ICommandSender);
        var3.attackEntityFrom(DamageSource.outOfWorld, 1000);
        par1ICommandSender.sendChatToPlayer("Ouch. That looks like it hurt.");
    }
}
