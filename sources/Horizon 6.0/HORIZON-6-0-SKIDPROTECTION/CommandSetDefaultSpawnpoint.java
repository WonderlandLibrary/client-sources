package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class CommandSetDefaultSpawnpoint extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000973";
    
    @Override
    public String Ý() {
        return "setworldspawn";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.setworldspawn.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        BlockPos var3;
        if (args.length == 0) {
            var3 = CommandBase.Â(sender).£á();
        }
        else {
            if (args.length != 3 || sender.k_() == null) {
                throw new WrongUsageException("commands.setworldspawn.usage", new Object[0]);
            }
            var3 = CommandBase.HorizonCode_Horizon_È(sender, args, 0, true);
        }
        sender.k_().Ñ¢á(var3);
        MinecraftServer.áƒ().Œ().HorizonCode_Horizon_È(new S05PacketSpawnPosition(var3));
        CommandBase.HorizonCode_Horizon_È(sender, this, "commands.setworldspawn.success", var3.HorizonCode_Horizon_È(), var3.Â(), var3.Ý());
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length > 0 && args.length <= 3) ? CommandBase.HorizonCode_Horizon_È(args, 0, pos) : null;
    }
}
