package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class CommandTestFor extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00001182";
    
    @Override
    public String Ý() {
        return "testfor";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.testfor.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 1) {
            throw new WrongUsageException("commands.testfor.usage", new Object[0]);
        }
        final Entity var3 = CommandBase.Â(sender, args[0]);
        NBTTagCompound var4 = null;
        if (args.length >= 2) {
            try {
                var4 = JsonToNBT.HorizonCode_Horizon_È(CommandBase.HorizonCode_Horizon_È(args, 1));
            }
            catch (NBTException var5) {
                throw new CommandException("commands.testfor.tagError", new Object[] { var5.getMessage() });
            }
        }
        if (var4 != null) {
            final NBTTagCompound var6 = new NBTTagCompound();
            var3.Âµá€(var6);
            if (!CommandTestForBlock.HorizonCode_Horizon_È(var4, var6, true)) {
                throw new CommandException("commands.testfor.failure", new Object[] { var3.v_() });
            }
        }
        CommandBase.HorizonCode_Horizon_È(sender, this, "commands.testfor.success", var3.v_());
    }
    
    @Override
    public boolean Â(final String[] args, final int index) {
        return index == 0;
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.HorizonCode_Horizon_È(args, MinecraftServer.áƒ().ˆá()) : null;
    }
}
