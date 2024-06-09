package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class CommandEntityData extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00002345";
    
    @Override
    public String Ý() {
        return "entitydata";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.entitydata.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 2) {
            throw new WrongUsageException("commands.entitydata.usage", new Object[0]);
        }
        final Entity var3 = CommandBase.Â(sender, args[0]);
        if (var3 instanceof EntityPlayer) {
            throw new CommandException("commands.entitydata.noPlayers", new Object[] { var3.Ý() });
        }
        final NBTTagCompound var4 = new NBTTagCompound();
        var3.Âµá€(var4);
        final NBTTagCompound var5 = (NBTTagCompound)var4.Â();
        NBTTagCompound var6;
        try {
            var6 = JsonToNBT.HorizonCode_Horizon_È(CommandBase.HorizonCode_Horizon_È(sender, args, 1).Ø());
        }
        catch (NBTException var7) {
            throw new CommandException("commands.entitydata.tagError", new Object[] { var7.getMessage() });
        }
        var6.Å("UUIDMost");
        var6.Å("UUIDLeast");
        var4.HorizonCode_Horizon_È(var6);
        if (var4.equals(var5)) {
            throw new CommandException("commands.entitydata.failed", new Object[] { var4.toString() });
        }
        var3.Ó(var4);
        CommandBase.HorizonCode_Horizon_È(sender, this, "commands.entitydata.success", var4.toString());
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.HorizonCode_Horizon_È(args, MinecraftServer.áƒ().ˆá()) : null;
    }
    
    @Override
    public boolean Â(final String[] args, final int index) {
        return index == 0;
    }
}
