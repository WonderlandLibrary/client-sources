package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collection;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import com.google.common.collect.Lists;

public class CommandFill extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00002342";
    
    @Override
    public String Ý() {
        return "fill";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.fill.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 7) {
            throw new WrongUsageException("commands.fill.usage", new Object[0]);
        }
        sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Â, 0);
        final BlockPos var3 = CommandBase.HorizonCode_Horizon_È(sender, args, 0, false);
        final BlockPos var4 = CommandBase.HorizonCode_Horizon_È(sender, args, 3, false);
        final Block var5 = CommandBase.à(sender, args[6]);
        int var6 = 0;
        if (args.length >= 8) {
            var6 = CommandBase.HorizonCode_Horizon_È(args[7], 0, 15);
        }
        final BlockPos var7 = new BlockPos(Math.min(var3.HorizonCode_Horizon_È(), var4.HorizonCode_Horizon_È()), Math.min(var3.Â(), var4.Â()), Math.min(var3.Ý(), var4.Ý()));
        final BlockPos var8 = new BlockPos(Math.max(var3.HorizonCode_Horizon_È(), var4.HorizonCode_Horizon_È()), Math.max(var3.Â(), var4.Â()), Math.max(var3.Ý(), var4.Ý()));
        int var9 = (var8.HorizonCode_Horizon_È() - var7.HorizonCode_Horizon_È() + 1) * (var8.Â() - var7.Â() + 1) * (var8.Ý() - var7.Ý() + 1);
        if (var9 > 32768) {
            throw new CommandException("commands.fill.tooManyBlocks", new Object[] { var9, 32768 });
        }
        if (var7.Â() < 0 || var8.Â() >= 256) {
            throw new CommandException("commands.fill.outOfWorld", new Object[0]);
        }
        final World var10 = sender.k_();
        for (int var11 = var7.Ý(); var11 < var8.Ý() + 16; var11 += 16) {
            for (int var12 = var7.HorizonCode_Horizon_È(); var12 < var8.HorizonCode_Horizon_È() + 16; var12 += 16) {
                if (!var10.Ó(new BlockPos(var12, var8.Â() - var7.Â(), var11))) {
                    throw new CommandException("commands.fill.outOfWorld", new Object[0]);
                }
            }
        }
        NBTTagCompound var13 = new NBTTagCompound();
        boolean var14 = false;
        if (args.length >= 10 && var5.£á()) {
            final String var15 = CommandBase.HorizonCode_Horizon_È(sender, args, 9).Ø();
            try {
                var13 = JsonToNBT.HorizonCode_Horizon_È(var15);
                var14 = true;
            }
            catch (NBTException var16) {
                throw new CommandException("commands.fill.tagError", new Object[] { var16.getMessage() });
            }
        }
        final ArrayList var17 = Lists.newArrayList();
        var9 = 0;
        for (int var18 = var7.Ý(); var18 <= var8.Ý(); ++var18) {
            for (int var19 = var7.Â(); var19 <= var8.Â(); ++var19) {
                for (int var20 = var7.HorizonCode_Horizon_È(); var20 <= var8.HorizonCode_Horizon_È(); ++var20) {
                    final BlockPos var21 = new BlockPos(var20, var19, var18);
                    if (args.length >= 9) {
                        if (!args[8].equals("outline") && !args[8].equals("hollow")) {
                            if (args[8].equals("destroy")) {
                                var10.Â(var21, true);
                            }
                            else if (args[8].equals("keep")) {
                                if (!var10.Ø­áŒŠá(var21)) {
                                    continue;
                                }
                            }
                            else if (args[8].equals("replace") && !var5.£á()) {
                                if (args.length > 9) {
                                    final Block var22 = CommandBase.à(sender, args[9]);
                                    if (var10.Â(var21).Ý() != var22) {
                                        continue;
                                    }
                                }
                                if (args.length > 10) {
                                    final int var23 = CommandBase.HorizonCode_Horizon_È(args[10]);
                                    final IBlockState var24 = var10.Â(var21);
                                    if (var24.Ý().Ý(var24) != var23) {
                                        continue;
                                    }
                                }
                            }
                        }
                        else if (var20 != var7.HorizonCode_Horizon_È() && var20 != var8.HorizonCode_Horizon_È() && var19 != var7.Â() && var19 != var8.Â() && var18 != var7.Ý() && var18 != var8.Ý()) {
                            if (args[8].equals("hollow")) {
                                var10.HorizonCode_Horizon_È(var21, Blocks.Â.¥à(), 2);
                                var17.add(var21);
                            }
                            continue;
                        }
                    }
                    final TileEntity var25 = var10.HorizonCode_Horizon_È(var21);
                    if (var25 != null) {
                        if (var25 instanceof IInventory) {
                            ((IInventory)var25).ŒÏ();
                        }
                        var10.HorizonCode_Horizon_È(var21, Blocks.¥ÇªÅ.¥à(), (var5 == Blocks.¥ÇªÅ) ? 2 : 4);
                    }
                    final IBlockState var24 = var5.Ý(var6);
                    if (var10.HorizonCode_Horizon_È(var21, var24, 2)) {
                        var17.add(var21);
                        ++var9;
                        if (var14) {
                            final TileEntity var26 = var10.HorizonCode_Horizon_È(var21);
                            if (var26 != null) {
                                var13.HorizonCode_Horizon_È("x", var21.HorizonCode_Horizon_È());
                                var13.HorizonCode_Horizon_È("y", var21.Â());
                                var13.HorizonCode_Horizon_È("z", var21.Ý());
                                var26.HorizonCode_Horizon_È(var13);
                            }
                        }
                    }
                }
            }
        }
        for (final BlockPos var28 : var17) {
            final Block var29 = var10.Â(var28).Ý();
            var10.HorizonCode_Horizon_È(var28, var29);
        }
        if (var9 <= 0) {
            throw new CommandException("commands.fill.failed", new Object[0]);
        }
        sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Â, var9);
        CommandBase.HorizonCode_Horizon_È(sender, this, "commands.fill.success", var9);
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length > 0 && args.length <= 3) ? CommandBase.HorizonCode_Horizon_È(args, 0, pos) : ((args.length > 3 && args.length <= 6) ? CommandBase.HorizonCode_Horizon_È(args, 3, pos) : ((args.length == 7) ? CommandBase.HorizonCode_Horizon_È(args, Block.HorizonCode_Horizon_È.Ý()) : ((args.length == 9) ? CommandBase.HorizonCode_Horizon_È(args, "replace", "destroy", "keep", "hollow", "outline") : null)));
    }
}
