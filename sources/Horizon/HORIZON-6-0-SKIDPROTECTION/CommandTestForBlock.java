package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collection;
import java.util.List;
import java.util.Iterator;

public class CommandTestForBlock extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00001181";
    
    @Override
    public String Ý() {
        return "testforblock";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.testforblock.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 4) {
            throw new WrongUsageException("commands.testforblock.usage", new Object[0]);
        }
        sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Â, 0);
        final BlockPos var3 = CommandBase.HorizonCode_Horizon_È(sender, args, 0, false);
        final Block var4 = Block.HorizonCode_Horizon_È(args[3]);
        if (var4 == null) {
            throw new NumberInvalidException("commands.setblock.notFound", new Object[] { args[3] });
        }
        int var5 = -1;
        if (args.length >= 5) {
            var5 = CommandBase.HorizonCode_Horizon_È(args[4], -1, 15);
        }
        final World var6 = sender.k_();
        if (!var6.Ó(var3)) {
            throw new CommandException("commands.testforblock.outOfWorld", new Object[0]);
        }
        NBTTagCompound var7 = new NBTTagCompound();
        boolean var8 = false;
        if (args.length >= 6 && var4.£á()) {
            final String var9 = CommandBase.HorizonCode_Horizon_È(sender, args, 5).Ø();
            try {
                var7 = JsonToNBT.HorizonCode_Horizon_È(var9);
                var8 = true;
            }
            catch (NBTException var10) {
                throw new CommandException("commands.setblock.tagError", new Object[] { var10.getMessage() });
            }
        }
        final IBlockState var11 = var6.Â(var3);
        final Block var12 = var11.Ý();
        if (var12 != var4) {
            throw new CommandException("commands.testforblock.failed.tile", new Object[] { var3.HorizonCode_Horizon_È(), var3.Â(), var3.Ý(), var12.ŒÏ(), var4.ŒÏ() });
        }
        if (var5 > -1) {
            final int var13 = var11.Ý().Ý(var11);
            if (var13 != var5) {
                throw new CommandException("commands.testforblock.failed.data", new Object[] { var3.HorizonCode_Horizon_È(), var3.Â(), var3.Ý(), var13, var5 });
            }
        }
        if (var8) {
            final TileEntity var14 = var6.HorizonCode_Horizon_È(var3);
            if (var14 == null) {
                throw new CommandException("commands.testforblock.failed.tileEntity", new Object[] { var3.HorizonCode_Horizon_È(), var3.Â(), var3.Ý() });
            }
            final NBTTagCompound var15 = new NBTTagCompound();
            var14.Â(var15);
            if (!HorizonCode_Horizon_È(var7, var15, true)) {
                throw new CommandException("commands.testforblock.failed.nbt", new Object[] { var3.HorizonCode_Horizon_È(), var3.Â(), var3.Ý() });
            }
        }
        sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Â, 1);
        CommandBase.HorizonCode_Horizon_È(sender, this, "commands.testforblock.success", var3.HorizonCode_Horizon_È(), var3.Â(), var3.Ý());
    }
    
    public static boolean HorizonCode_Horizon_È(final NBTBase p_175775_0_, final NBTBase p_175775_1_, final boolean p_175775_2_) {
        if (p_175775_0_ == p_175775_1_) {
            return true;
        }
        if (p_175775_0_ == null) {
            return true;
        }
        if (p_175775_1_ == null) {
            return false;
        }
        if (!p_175775_0_.getClass().equals(p_175775_1_.getClass())) {
            return false;
        }
        if (p_175775_0_ instanceof NBTTagCompound) {
            final NBTTagCompound var9 = (NBTTagCompound)p_175775_0_;
            final NBTTagCompound var10 = (NBTTagCompound)p_175775_1_;
            for (final String var12 : var9.Âµá€()) {
                final NBTBase var13 = var9.HorizonCode_Horizon_È(var12);
                if (!HorizonCode_Horizon_È(var13, var10.HorizonCode_Horizon_È(var12), p_175775_2_)) {
                    return false;
                }
            }
            return true;
        }
        if (!(p_175775_0_ instanceof NBTTagList) || !p_175775_2_) {
            return p_175775_0_.equals(p_175775_1_);
        }
        final NBTTagList var14 = (NBTTagList)p_175775_0_;
        final NBTTagList var15 = (NBTTagList)p_175775_1_;
        if (var14.Âµá€() == 0) {
            return var15.Âµá€() == 0;
        }
        for (int var16 = 0; var16 < var14.Âµá€(); ++var16) {
            final NBTBase var17 = var14.à(var16);
            boolean var18 = false;
            for (int var19 = 0; var19 < var15.Âµá€(); ++var19) {
                if (HorizonCode_Horizon_È(var17, var15.à(var19), p_175775_2_)) {
                    var18 = true;
                    break;
                }
            }
            if (!var18) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length > 0 && args.length <= 3) ? CommandBase.HorizonCode_Horizon_È(args, 0, pos) : ((args.length == 4) ? CommandBase.HorizonCode_Horizon_È(args, Block.HorizonCode_Horizon_È.Ý()) : null);
    }
}
