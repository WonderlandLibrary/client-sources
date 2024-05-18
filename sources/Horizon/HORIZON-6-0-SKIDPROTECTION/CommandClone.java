package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import com.google.common.collect.Lists;

public class CommandClone extends CommandBase
{
    private static final String HorizonCode_Horizon_È = "CL_00002348";
    
    @Override
    public String Ý() {
        return "clone";
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String Ý(final ICommandSender sender) {
        return "commands.clone.usage";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 9) {
            throw new WrongUsageException("commands.clone.usage", new Object[0]);
        }
        sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Â, 0);
        final BlockPos var3 = CommandBase.HorizonCode_Horizon_È(sender, args, 0, false);
        final BlockPos var4 = CommandBase.HorizonCode_Horizon_È(sender, args, 3, false);
        final BlockPos var5 = CommandBase.HorizonCode_Horizon_È(sender, args, 6, false);
        final StructureBoundingBox var6 = new StructureBoundingBox(var3, var4);
        final StructureBoundingBox var7 = new StructureBoundingBox(var5, var5.HorizonCode_Horizon_È(var6.Â()));
        int var8 = var6.Ý() * var6.Ø­áŒŠá() * var6.Âµá€();
        if (var8 > 32768) {
            throw new CommandException("commands.clone.tooManyBlocks", new Object[] { var8, 32768 });
        }
        boolean var9 = false;
        Block var10 = null;
        int var11 = -1;
        if ((args.length < 11 || (!args[10].equals("force") && !args[10].equals("move"))) && var6.HorizonCode_Horizon_È(var7)) {
            throw new CommandException("commands.clone.noOverlap", new Object[0]);
        }
        if (args.length >= 11 && args[10].equals("move")) {
            var9 = true;
        }
        if (var6.Â < 0 || var6.Âµá€ >= 256 || var7.Â < 0 || var7.Âµá€ >= 256) {
            throw new CommandException("commands.clone.outOfWorld", new Object[0]);
        }
        final World var12 = sender.k_();
        if (!var12.HorizonCode_Horizon_È(var6) || !var12.HorizonCode_Horizon_È(var7)) {
            throw new CommandException("commands.clone.outOfWorld", new Object[0]);
        }
        boolean var13 = false;
        if (args.length >= 10) {
            if (args[9].equals("masked")) {
                var13 = true;
            }
            else if (args[9].equals("filtered")) {
                if (args.length < 12) {
                    throw new WrongUsageException("commands.clone.usage", new Object[0]);
                }
                var10 = CommandBase.à(sender, args[11]);
                if (args.length >= 13) {
                    var11 = CommandBase.HorizonCode_Horizon_È(args[12], 0, 15);
                }
            }
        }
        final ArrayList var14 = Lists.newArrayList();
        final ArrayList var15 = Lists.newArrayList();
        final ArrayList var16 = Lists.newArrayList();
        final LinkedList var17 = Lists.newLinkedList();
        final BlockPos var18 = new BlockPos(var7.HorizonCode_Horizon_È - var6.HorizonCode_Horizon_È, var7.Â - var6.Â, var7.Ý - var6.Ý);
        for (int var19 = var6.Ý; var19 <= var6.Ó; ++var19) {
            for (int var20 = var6.Â; var20 <= var6.Âµá€; ++var20) {
                for (int var21 = var6.HorizonCode_Horizon_È; var21 <= var6.Ø­áŒŠá; ++var21) {
                    final BlockPos var22 = new BlockPos(var21, var20, var19);
                    final BlockPos var23 = var22.HorizonCode_Horizon_È(var18);
                    final IBlockState var24 = var12.Â(var22);
                    if ((!var13 || var24.Ý() != Blocks.Â) && (var10 == null || (var24.Ý() == var10 && (var11 < 0 || var24.Ý().Ý(var24) == var11)))) {
                        final TileEntity var25 = var12.HorizonCode_Horizon_È(var22);
                        if (var25 != null) {
                            final NBTTagCompound var26 = new NBTTagCompound();
                            var25.Â(var26);
                            var15.add(new HorizonCode_Horizon_È(var23, var24, var26));
                            var17.addLast(var22);
                        }
                        else if (!var24.Ý().HorizonCode_Horizon_È() && !var24.Ý().áˆºÑ¢Õ()) {
                            var16.add(new HorizonCode_Horizon_È(var23, var24, null));
                            var17.addFirst(var22);
                        }
                        else {
                            var14.add(new HorizonCode_Horizon_È(var23, var24, null));
                            var17.addLast(var22);
                        }
                    }
                }
            }
        }
        if (var9) {
            for (final BlockPos var28 : var17) {
                final TileEntity var29 = var12.HorizonCode_Horizon_È(var28);
                if (var29 instanceof IInventory) {
                    ((IInventory)var29).ŒÏ();
                }
                var12.HorizonCode_Horizon_È(var28, Blocks.¥ÇªÅ.¥à(), 2);
            }
            for (final BlockPos var28 : var17) {
                var12.HorizonCode_Horizon_È(var28, Blocks.Â.¥à(), 3);
            }
        }
        final ArrayList var30 = Lists.newArrayList();
        var30.addAll(var14);
        var30.addAll(var15);
        var30.addAll(var16);
        final List var31 = Lists.reverse((List)var30);
        for (final HorizonCode_Horizon_È var33 : var31) {
            final TileEntity var34 = var12.HorizonCode_Horizon_È(var33.HorizonCode_Horizon_È);
            if (var34 instanceof IInventory) {
                ((IInventory)var34).ŒÏ();
            }
            var12.HorizonCode_Horizon_È(var33.HorizonCode_Horizon_È, Blocks.¥ÇªÅ.¥à(), 2);
        }
        var8 = 0;
        for (final HorizonCode_Horizon_È var33 : var30) {
            if (var12.HorizonCode_Horizon_È(var33.HorizonCode_Horizon_È, var33.Â, 2)) {
                ++var8;
            }
        }
        for (final HorizonCode_Horizon_È var33 : var15) {
            final TileEntity var34 = var12.HorizonCode_Horizon_È(var33.HorizonCode_Horizon_È);
            if (var33.Ý != null && var34 != null) {
                var33.Ý.HorizonCode_Horizon_È("x", var33.HorizonCode_Horizon_È.HorizonCode_Horizon_È());
                var33.Ý.HorizonCode_Horizon_È("y", var33.HorizonCode_Horizon_È.Â());
                var33.Ý.HorizonCode_Horizon_È("z", var33.HorizonCode_Horizon_È.Ý());
                var34.HorizonCode_Horizon_È(var33.Ý);
                var34.ŠÄ();
            }
            var12.HorizonCode_Horizon_È(var33.HorizonCode_Horizon_È, var33.Â, 2);
        }
        for (final HorizonCode_Horizon_È var33 : var31) {
            var12.HorizonCode_Horizon_È(var33.HorizonCode_Horizon_È, var33.Â.Ý());
        }
        final List var35 = var12.Â(var6, false);
        if (var35 != null) {
            for (final NextTickListEntry var37 : var35) {
                if (var6.HorizonCode_Horizon_È(var37.HorizonCode_Horizon_È)) {
                    final BlockPos var38 = var37.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var18);
                    var12.Â(var38, var37.HorizonCode_Horizon_È(), (int)(var37.Â - var12.ŒÏ().Ó()), var37.Ý);
                }
            }
        }
        if (var8 <= 0) {
            throw new CommandException("commands.clone.failed", new Object[0]);
        }
        sender.HorizonCode_Horizon_È(CommandResultStats.HorizonCode_Horizon_È.Â, var8);
        CommandBase.HorizonCode_Horizon_È(sender, this, "commands.clone.success", var8);
    }
    
    @Override
    public List HorizonCode_Horizon_È(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length > 0 && args.length <= 3) ? CommandBase.HorizonCode_Horizon_È(args, 0, pos) : ((args.length > 3 && args.length <= 6) ? CommandBase.HorizonCode_Horizon_È(args, 3, pos) : ((args.length > 6 && args.length <= 9) ? CommandBase.HorizonCode_Horizon_È(args, 6, pos) : ((args.length == 10) ? CommandBase.HorizonCode_Horizon_È(args, "replace", "masked", "filtered") : ((args.length == 11) ? CommandBase.HorizonCode_Horizon_È(args, "normal", "force", "move") : ((args.length == 12 && "filtered".equals(args[9])) ? CommandBase.HorizonCode_Horizon_È(args, Block.HorizonCode_Horizon_È.Ý()) : null)))));
    }
    
    static class HorizonCode_Horizon_È
    {
        public final BlockPos HorizonCode_Horizon_È;
        public final IBlockState Â;
        public final NBTTagCompound Ý;
        private static final String Ø­áŒŠá = "CL_00002347";
        
        public HorizonCode_Horizon_È(final BlockPos p_i46037_1_, final IBlockState p_i46037_2_, final NBTTagCompound p_i46037_3_) {
            this.HorizonCode_Horizon_È = p_i46037_1_;
            this.Â = p_i46037_2_;
            this.Ý = p_i46037_3_;
        }
    }
}
