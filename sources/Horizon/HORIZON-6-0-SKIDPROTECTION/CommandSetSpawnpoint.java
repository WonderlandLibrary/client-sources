package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class CommandSetSpawnpoint extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00001026";
    
    @Override
    public String Ý() {
        return "spawnpoint";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.spawnpoint.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length > 0 && args.length < 4) {
            throw new WrongUsageException("commands.spawnpoint.usage", new Object[0]);
        }
        final EntityPlayerMP var3 = (args.length > 0) ? CommandBase.HorizonCode_Horizon_È(sender, args[0]) : CommandBase.Â(sender);
        final BlockPos var4 = (args.length > 3) ? CommandBase.HorizonCode_Horizon_È(sender, args, 1, true) : var3.£á();
        if (var3.Ï­Ðƒà != null) {
            var3.HorizonCode_Horizon_È(var4, true);
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.spawnpoint.success", var3.v_(), var4.HorizonCode_Horizon_È(), var4.Â(), var4.Ý());
        }
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.HorizonCode_Horizon_È(args, MinecraftServer.áƒ().ˆá()) : ((args.length > 1 && args.length <= 4) ? CommandBase.HorizonCode_Horizon_È(args, 1, pos) : null);
    }
    
    @Override
    public boolean Â(final String[] args, final int index) {
        return index == 0;
    }
}
