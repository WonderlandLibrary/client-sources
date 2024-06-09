package HORIZON-6-0-SKIDPROTECTION;

public class CommandShowSeed extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00001053";
    
    @Override
    public boolean HorizonCode_Horizon_È(final ICommandSender sender) {
        return MinecraftServer.áƒ().¥à() || super.HorizonCode_Horizon_È(sender);
    }
    
    @Override
    public String Ý() {
        return "seed";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.seed.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        final Object var3 = (sender instanceof EntityPlayer) ? ((EntityPlayer)sender).Ï­Ðƒà : MinecraftServer.áƒ().HorizonCode_Horizon_È(0);
        sender.HorizonCode_Horizon_È(new ChatComponentTranslation("commands.seed.success", new Object[] { ((World)var3).Æ() }));
    }
}
