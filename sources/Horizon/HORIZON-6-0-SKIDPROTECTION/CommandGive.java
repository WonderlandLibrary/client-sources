package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collection;
import java.util.List;

public class CommandGive extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000502";
    
    @Override
    public String Ý() {
        return "give";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.give.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 2) {
            throw new WrongUsageException("commands.give.usage", new Object[0]);
        }
        final EntityPlayerMP var3 = CommandBase.HorizonCode_Horizon_È(sender, args[0]);
        final Item_1028566121 var4 = CommandBase.Ó(sender, args[1]);
        final int var5 = (args.length >= 3) ? CommandBase.HorizonCode_Horizon_È(args[2], 1, 64) : 1;
        final int var6 = (args.length >= 4) ? CommandBase.HorizonCode_Horizon_È(args[3]) : 0;
        final ItemStack var7 = new ItemStack(var4, var5, var6);
        if (args.length >= 5) {
            final String var8 = CommandBase.HorizonCode_Horizon_È(sender, args, 4).Ø();
            try {
                var7.Ø­áŒŠá(JsonToNBT.HorizonCode_Horizon_È(var8));
            }
            catch (NBTException var9) {
                throw new CommandException("commands.give.tagError", new Object[] { var9.getMessage() });
            }
        }
        final boolean var10 = var3.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È(var7);
        if (var10) {
            var3.Ï­Ðƒà.HorizonCode_Horizon_È((Entity)var3, "random.pop", 0.2f, ((var3.ˆÐƒØ().nextFloat() - var3.ˆÐƒØ().nextFloat()) * 0.7f + 1.0f) * 2.0f);
            var3.ŒÂ.Ý();
        }
        if (var10 && var7.Â <= 0) {
            var7.Â = 1;
            sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Ø­áŒŠá, var5);
            final EntityItem var11 = var3.HorizonCode_Horizon_È(var7, false);
            if (var11 != null) {
                var11.Šáƒ();
            }
        }
        else {
            sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Ø­áŒŠá, var5 - var7.Â);
            final EntityItem var11 = var3.HorizonCode_Horizon_È(var7, false);
            if (var11 != null) {
                var11.¥Æ();
                var11.HorizonCode_Horizon_È(var3.v_());
            }
        }
        CommandBase.HorizonCode_Horizon_È(sender, this, "commands.give.success", var7.Çªà¢(), var5, var3.v_());
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
