package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class CommandDifficulty extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000422";
    
    @Override
    public String Ý() {
        return "difficulty";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.difficulty.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length <= 0) {
            throw new WrongUsageException("commands.difficulty.usage", new Object[0]);
        }
        final EnumDifficulty var3 = this.Âµá€(args[0]);
        MinecraftServer.áƒ().HorizonCode_Horizon_È(var3);
        CommandBase.HorizonCode_Horizon_È(sender, this, "commands.difficulty.success", new ChatComponentTranslation(var3.Â(), new Object[0]));
    }
    
    protected EnumDifficulty Âµá€(final String p_180531_1_) throws CommandException {
        return (!p_180531_1_.equalsIgnoreCase("peaceful") && !p_180531_1_.equalsIgnoreCase("p")) ? ((!p_180531_1_.equalsIgnoreCase("easy") && !p_180531_1_.equalsIgnoreCase("e")) ? ((!p_180531_1_.equalsIgnoreCase("normal") && !p_180531_1_.equalsIgnoreCase("n")) ? ((!p_180531_1_.equalsIgnoreCase("hard") && !p_180531_1_.equalsIgnoreCase("h")) ? EnumDifficulty.HorizonCode_Horizon_È(CommandBase.HorizonCode_Horizon_È(p_180531_1_, 0, 3)) : EnumDifficulty.Ø­áŒŠá) : EnumDifficulty.Ý) : EnumDifficulty.Â) : EnumDifficulty.HorizonCode_Horizon_È;
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.HorizonCode_Horizon_È(args, "peaceful", "easy", "normal", "hard") : null;
    }
}
