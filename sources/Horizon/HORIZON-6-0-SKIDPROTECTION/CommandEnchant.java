package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class CommandEnchant extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00000377";
    
    @Override
    public String Ý() {
        return "enchant";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.enchant.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 2) {
            throw new WrongUsageException("commands.enchant.usage", new Object[0]);
        }
        final EntityPlayerMP var3 = CommandBase.HorizonCode_Horizon_È(sender, args[0]);
        sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Ø­áŒŠá, 0);
        int var4;
        try {
            var4 = CommandBase.HorizonCode_Horizon_È(args[1], 0);
        }
        catch (NumberInvalidException var6) {
            final Enchantment var5 = Enchantment.HorizonCode_Horizon_È(args[1]);
            if (var5 == null) {
                throw var6;
            }
            var4 = var5.ŒÏ;
        }
        int var7 = 1;
        final ItemStack var8 = var3.áŒŠá();
        if (var8 == null) {
            throw new CommandException("commands.enchant.noItem", new Object[0]);
        }
        final Enchantment var9 = Enchantment.HorizonCode_Horizon_È(var4);
        if (var9 == null) {
            throw new NumberInvalidException("commands.enchant.notFound", new Object[] { var4 });
        }
        if (!var9.HorizonCode_Horizon_È(var8)) {
            throw new CommandException("commands.enchant.cantEnchant", new Object[0]);
        }
        if (args.length >= 3) {
            var7 = CommandBase.HorizonCode_Horizon_È(args[2], var9.Ý(), var9.Ø­áŒŠá());
        }
        if (var8.£á()) {
            final NBTTagList var10 = var8.£à();
            if (var10 != null) {
                for (int var11 = 0; var11 < var10.Âµá€(); ++var11) {
                    final short var12 = var10.Â(var11).Âµá€("id");
                    if (Enchantment.HorizonCode_Horizon_È(var12) != null) {
                        final Enchantment var13 = Enchantment.HorizonCode_Horizon_È(var12);
                        if (!var13.HorizonCode_Horizon_È(var9)) {
                            throw new CommandException("commands.enchant.cantCombine", new Object[] { var9.Ø­áŒŠá(var7), var13.Ø­áŒŠá(var10.Â(var11).Âµá€("lvl")) });
                        }
                    }
                }
            }
        }
        var8.HorizonCode_Horizon_È(var9, var7);
        CommandBase.HorizonCode_Horizon_È(sender, this, "commands.enchant.success", new Object[0]);
        sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Ø­áŒŠá, 1);
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.HorizonCode_Horizon_È(args, this.Ø­áŒŠá()) : ((args.length == 2) ? CommandBase.HorizonCode_Horizon_È(args, Enchantment.HorizonCode_Horizon_È()) : null);
    }
    
    protected String[] Ø­áŒŠá() {
        return MinecraftServer.áƒ().ˆá();
    }
    
    @Override
    public boolean Â(final String[] args, final int index) {
        return index == 0;
    }
}
