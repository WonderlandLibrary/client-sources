package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class CommandGameMode extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000448";
    
    @Override
    public String Ý() {
        return "gamemode";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.gamemode.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length <= 0) {
            throw new WrongUsageException("commands.gamemode.usage", new Object[0]);
        }
        final WorldSettings.HorizonCode_Horizon_È var3 = this.Ø(sender, args[0]);
        final EntityPlayerMP var4 = (args.length >= 2) ? CommandBase.HorizonCode_Horizon_È(sender, args[1]) : CommandBase.Â(sender);
        var4.HorizonCode_Horizon_È(var3);
        var4.Ï­à = 0.0f;
        if (sender.k_().Çªà¢().Â("sendCommandFeedback")) {
            var4.HorizonCode_Horizon_È(new ChatComponentTranslation("gameMode.changed", new Object[0]));
        }
        final ChatComponentTranslation var5 = new ChatComponentTranslation("gameMode." + var3.Â(), new Object[0]);
        if (var4 != sender) {
            CommandBase.HorizonCode_Horizon_È(sender, this, 1, "commands.gamemode.success.other", var4.v_(), var5);
        }
        else {
            CommandBase.HorizonCode_Horizon_È(sender, this, 1, "commands.gamemode.success.self", var5);
        }
    }
    
    protected WorldSettings.HorizonCode_Horizon_È Ø(final ICommandSender p_71539_1_, final String p_71539_2_) throws CommandException {
        return (!p_71539_2_.equalsIgnoreCase(WorldSettings.HorizonCode_Horizon_È.Â.Â()) && !p_71539_2_.equalsIgnoreCase("s")) ? ((!p_71539_2_.equalsIgnoreCase(WorldSettings.HorizonCode_Horizon_È.Ý.Â()) && !p_71539_2_.equalsIgnoreCase("c")) ? ((!p_71539_2_.equalsIgnoreCase(WorldSettings.HorizonCode_Horizon_È.Ø­áŒŠá.Â()) && !p_71539_2_.equalsIgnoreCase("a")) ? ((!p_71539_2_.equalsIgnoreCase(WorldSettings.HorizonCode_Horizon_È.Âµá€.Â()) && !p_71539_2_.equalsIgnoreCase("sp")) ? WorldSettings.HorizonCode_Horizon_È(CommandBase.HorizonCode_Horizon_È(p_71539_2_, 0, WorldSettings.HorizonCode_Horizon_È.values().length - 2)) : WorldSettings.HorizonCode_Horizon_È.Âµá€) : WorldSettings.HorizonCode_Horizon_È.Ø­áŒŠá) : WorldSettings.HorizonCode_Horizon_È.Ý) : WorldSettings.HorizonCode_Horizon_È.Â;
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.HorizonCode_Horizon_È(args, "survival", "creative", "adventure", "spectator") : ((args.length == 2) ? CommandBase.HorizonCode_Horizon_È(args, this.Ø­áŒŠá()) : null);
    }
    
    protected String[] Ø­áŒŠá() {
        return MinecraftServer.áƒ().ˆá();
    }
    
    @Override
    public boolean Â(final String[] args, final int index) {
        return index == 1;
    }
}
