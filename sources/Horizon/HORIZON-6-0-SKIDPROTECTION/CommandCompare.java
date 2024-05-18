package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class CommandCompare extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00002346";
    
    @Override
    public String Ý() {
        return "testforblocks";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.compare.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 9) {
            throw new WrongUsageException("commands.compare.usage", new Object[0]);
        }
        sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Â, 0);
        final BlockPos var3 = CommandBase.HorizonCode_Horizon_È(sender, args, 0, false);
        final BlockPos var4 = CommandBase.HorizonCode_Horizon_È(sender, args, 3, false);
        final BlockPos var5 = CommandBase.HorizonCode_Horizon_È(sender, args, 6, false);
        final StructureBoundingBox var6 = new StructureBoundingBox(var3, var4);
        final StructureBoundingBox var7 = new StructureBoundingBox(var5, var5.HorizonCode_Horizon_È(var6.Â()));
        int var8 = var6.Ý() * var6.Ø­áŒŠá() * var6.Âµá€();
        if (var8 > 524288) {
            throw new CommandException("commands.compare.tooManyBlocks", new Object[] { var8, 524288 });
        }
        if (var6.Â < 0 || var6.Âµá€ >= 256 || var7.Â < 0 || var7.Âµá€ >= 256) {
            throw new CommandException("commands.compare.outOfWorld", new Object[0]);
        }
        final World var9 = sender.k_();
        if (var9.HorizonCode_Horizon_È(var6) && var9.HorizonCode_Horizon_È(var7)) {
            boolean var10 = false;
            if (args.length > 9 && args[9].equals("masked")) {
                var10 = true;
            }
            var8 = 0;
            final BlockPos var11 = new BlockPos(var7.HorizonCode_Horizon_È - var6.HorizonCode_Horizon_È, var7.Â - var6.Â, var7.Ý - var6.Ý);
            for (int var12 = var6.Ý; var12 <= var6.Ó; ++var12) {
                for (int var13 = var6.Â; var13 <= var6.Âµá€; ++var13) {
                    for (int var14 = var6.HorizonCode_Horizon_È; var14 <= var6.Ø­áŒŠá; ++var14) {
                        final BlockPos var15 = new BlockPos(var14, var13, var12);
                        final BlockPos var16 = var15.HorizonCode_Horizon_È(var11);
                        boolean var17 = false;
                        final IBlockState var18 = var9.Â(var15);
                        if (!var10 || var18.Ý() != Blocks.Â) {
                            if (var18 == var9.Â(var16)) {
                                final TileEntity var19 = var9.HorizonCode_Horizon_È(var15);
                                final TileEntity var20 = var9.HorizonCode_Horizon_È(var16);
                                if (var19 != null && var20 != null) {
                                    final NBTTagCompound var21 = new NBTTagCompound();
                                    var19.Â(var21);
                                    var21.Å("x");
                                    var21.Å("y");
                                    var21.Å("z");
                                    final NBTTagCompound var22 = new NBTTagCompound();
                                    var20.Â(var22);
                                    var22.Å("x");
                                    var22.Å("y");
                                    var22.Å("z");
                                    if (!var21.equals(var22)) {
                                        var17 = true;
                                    }
                                }
                                else if (var19 != null) {
                                    var17 = true;
                                }
                            }
                            else {
                                var17 = true;
                            }
                            ++var8;
                            if (var17) {
                                throw new CommandException("commands.compare.failed", new Object[0]);
                            }
                        }
                    }
                }
            }
            sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Â, var8);
            CommandBase.HorizonCode_Horizon_È(sender, this, "commands.compare.success", var8);
            return;
        }
        throw new CommandException("commands.compare.outOfWorld", new Object[0]);
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length > 0 && args.length <= 3) ? CommandBase.HorizonCode_Horizon_È(args, 0, pos) : ((args.length > 3 && args.length <= 6) ? CommandBase.HorizonCode_Horizon_È(args, 3, pos) : ((args.length > 6 && args.length <= 9) ? CommandBase.HorizonCode_Horizon_È(args, 6, pos) : ((args.length == 10) ? CommandBase.HorizonCode_Horizon_È(args, "masked", "all") : null)));
    }
}
