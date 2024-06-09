package HORIZON-6-0-SKIDPROTECTION;

public class CommandSaveOn extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000873";
    
    @Override
    public String Ý() {
        return "save-on";
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.save-on.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        final MinecraftServer var3 = MinecraftServer.áƒ();
        boolean var4 = false;
        for (int var5 = 0; var5 < var3.Ý.length; ++var5) {
            if (var3.Ý[var5] != null) {
                final WorldServer var6 = var3.Ý[var5];
                if (var6.ˆá) {
                    var6.ˆá = false;
                    var4 = true;
                }
            }
        }
        if (var4) {
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.save.enabled", new Object[0]);
            return;
        }
        throw new CommandException("commands.save-on.alreadyOn", new Object[0]);
    }
}
