package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class CommandBlockData extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00002349";
    
    @Override
    public String Ý() {
        return "blockdata";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.blockdata.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 4) {
            throw new WrongUsageException("commands.blockdata.usage", new Object[0]);
        }
        sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Â, 0);
        final BlockPos var3 = CommandBase.HorizonCode_Horizon_È(sender, args, 0, false);
        final World var4 = sender.k_();
        if (!var4.Ó(var3)) {
            throw new CommandException("commands.blockdata.outOfWorld", new Object[0]);
        }
        final TileEntity var5 = var4.HorizonCode_Horizon_È(var3);
        if (var5 == null) {
            throw new CommandException("commands.blockdata.notValid", new Object[0]);
        }
        final NBTTagCompound var6 = new NBTTagCompound();
        var5.Â(var6);
        final NBTTagCompound var7 = (NBTTagCompound)var6.Â();
        NBTTagCompound var8;
        try {
            var8 = JsonToNBT.HorizonCode_Horizon_È(CommandBase.HorizonCode_Horizon_È(sender, args, 3).Ø());
        }
        catch (NBTException var9) {
            throw new CommandException("commands.blockdata.tagError", new Object[] { var9.getMessage() });
        }
        var6.HorizonCode_Horizon_È(var8);
        var6.HorizonCode_Horizon_È("x", var3.HorizonCode_Horizon_È());
        var6.HorizonCode_Horizon_È("y", var3.Â());
        var6.HorizonCode_Horizon_È("z", var3.Ý());
        if (var6.equals(var7)) {
            throw new CommandException("commands.blockdata.failed", new Object[] { var6.toString() });
        }
        var5.HorizonCode_Horizon_È(var6);
        var5.ŠÄ();
        var4.áŒŠÆ(var3);
        sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Â, 1);
        CommandBase.HorizonCode_Horizon_È(sender, this, "commands.blockdata.success", var6.toString());
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length > 0 && args.length <= 3) ? CommandBase.HorizonCode_Horizon_È(args, 0, pos) : null;
    }
}
