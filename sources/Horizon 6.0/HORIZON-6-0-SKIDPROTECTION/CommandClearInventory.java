package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collection;
import java.util.List;

public class CommandClearInventory extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000218";
    
    @Override
    public String Ý() {
        return "clear";
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.clear.usage";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        final EntityPlayerMP var3 = (args.length == 0) ? CommandBase.Â(sender) : CommandBase.HorizonCode_Horizon_È(sender, args[0]);
        final Item_1028566121 var4 = (args.length >= 2) ? CommandBase.Ó(sender, args[1]) : null;
        final int var5 = (args.length >= 3) ? CommandBase.HorizonCode_Horizon_È(args[2], -1) : -1;
        final int var6 = (args.length >= 4) ? CommandBase.HorizonCode_Horizon_È(args[3], -1) : -1;
        NBTTagCompound var7 = null;
        if (args.length >= 5) {
            try {
                var7 = JsonToNBT.HorizonCode_Horizon_È(CommandBase.HorizonCode_Horizon_È(args, 4));
            }
            catch (NBTException var8) {
                throw new CommandException("commands.clear.tagError", new Object[] { var8.getMessage() });
            }
        }
        if (args.length >= 2 && var4 == null) {
            throw new CommandException("commands.clear.failure", new Object[] { var3.v_() });
        }
        final int var9 = var3.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È(var4, var5, var6, var7);
        var3.ŒÂ.Ý();
        if (!var3.áˆºáˆºáŠ.Ø­áŒŠá) {
            var3.Šáƒ();
        }
        sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Ø­áŒŠá, var9);
        if (var9 == 0) {
            throw new CommandException("commands.clear.failure", new Object[] { var3.v_() });
        }
        if (var6 == 0) {
            sender.HorizonCode_Horizon_È(new ChatComponentTranslation("commands.clear.testing", new Object[] { var3.v_(), var9 }));
        }
        else {
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.clear.success", var3.v_(), var9);
        }
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.HorizonCode_Horizon_È(args, this.Ø­áŒŠá()) : ((args.length == 2) ? CommandBase.HorizonCode_Horizon_È(args, Item_1028566121.HorizonCode_Horizon_È.Ý()) : null);
    }
    
    protected String[] Ø­áŒŠá() {
        return MinecraftServer.áƒ().ˆá();
    }
    
    @Override
    public boolean Â(final String[] args, final int index) {
        return index == 0;
    }
}
