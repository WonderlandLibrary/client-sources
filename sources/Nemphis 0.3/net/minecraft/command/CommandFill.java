/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.command;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.RegistryNamespacedDefaultedByKey;
import net.minecraft.world.World;

public class CommandFill
extends CommandBase {
    private static final String __OBFID = "CL_00002342";

    @Override
    public String getCommandName() {
        return "fill";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.fill.usage";
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 7) {
            throw new WrongUsageException("commands.fill.usage", new Object[0]);
        }
        sender.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
        var3 = CommandFill.func_175757_a(sender, args, 0, false);
        var4 = CommandFill.func_175757_a(sender, args, 3, false);
        var5 = CommandBase.getBlockByText(sender, args[6]);
        var6 = 0;
        if (args.length >= 8) {
            var6 = CommandFill.parseInt(args[7], 0, 15);
        }
        var7 = new BlockPos(Math.min(var3.getX(), var4.getX()), Math.min(var3.getY(), var4.getY()), Math.min(var3.getZ(), var4.getZ()));
        var8 = new BlockPos(Math.max(var3.getX(), var4.getX()), Math.max(var3.getY(), var4.getY()), Math.max(var3.getZ(), var4.getZ()));
        var9 = (var8.getX() - var7.getX() + 1) * (var8.getY() - var7.getY() + 1) * (var8.getZ() - var7.getZ() + 1);
        if (var9 > 32768) {
            throw new CommandException("commands.fill.tooManyBlocks", new Object[]{var9, 32768});
        }
        if (var7.getY() < 0) throw new CommandException("commands.fill.outOfWorld", new Object[0]);
        if (var8.getY() >= 256) throw new CommandException("commands.fill.outOfWorld", new Object[0]);
        var10 = sender.getEntityWorld();
        var11 = var7.getZ();
        while (var11 < var8.getZ() + 16) {
            var12 = var7.getX();
            while (var12 < var8.getX() + 16) {
                if (!var10.isBlockLoaded(new BlockPos(var12, var8.getY() - var7.getY(), var11))) {
                    throw new CommandException("commands.fill.outOfWorld", new Object[0]);
                }
                var12 += 16;
            }
            var11 += 16;
        }
        var22 = new NBTTagCompound();
        var23 = false;
        if (args.length >= 10 && var5.hasTileEntity()) {
            var13 = CommandFill.getChatComponentFromNthArg(sender, args, 9).getUnformattedText();
            try {
                var22 = JsonToNBT.func_180713_a(var13);
                var23 = true;
            }
            catch (NBTException var21) {
                throw new CommandException("commands.fill.tagError", new Object[]{var21.getMessage()});
            }
        }
        var24 = Lists.newArrayList();
        var9 = 0;
        var14 = var7.getZ();
        while (var14 <= var8.getZ()) {
            var15 = var7.getY();
            while (var15 <= var8.getY()) {
                var16 = var7.getX();
                while (var16 <= var8.getX()) {
                    var17 = new BlockPos(var16, var15, var14);
                    if (args.length < 9) ** GOTO lbl71
                    if (args[8].equals("outline") || args[8].equals("hollow")) ** GOTO lbl64
                    if (!args[8].equals("destroy")) ** GOTO lbl52
                    var10.destroyBlock(var17, true);
                    ** GOTO lbl71
lbl52: // 1 sources:
                    if (!args[8].equals("keep")) ** GOTO lbl55
                    if (var10.isAirBlock(var17)) ** GOTO lbl71
                    ** GOTO lbl83
lbl55: // 1 sources:
                    if (!args[8].equals("replace") || var5.hasTileEntity()) ** GOTO lbl71
                    if (args.length <= 9) ** GOTO lbl59
                    var18 = CommandBase.getBlockByText(sender, args[9]);
                    if (var10.getBlockState(var17).getBlock() != var18) ** GOTO lbl83
lbl59: // 2 sources:
                    if (args.length <= 10) ** GOTO lbl71
                    var28 = CommandBase.parseInt(args[10]);
                    var19 = var10.getBlockState(var17);
                    if (var19.getBlock().getMetaFromState(var19) == var28) ** GOTO lbl71
                    ** GOTO lbl83
lbl64: // 1 sources:
                    if (var16 != var7.getX() && var16 != var8.getX() && var15 != var7.getY() && var15 != var8.getY() && var14 != var7.getZ() && var14 != var8.getZ()) {
                        if (args[8].equals("hollow")) {
                            var10.setBlockState(var17, Blocks.air.getDefaultState(), 2);
                            var24.add(var17);
                            ** GOTO lbl83
                        } else {
                            ** GOTO lbl70
                        }
                    }
                    ** GOTO lbl71
lbl70: // 2 sources:
                    ** GOTO lbl83
lbl71: // 7 sources:
                    if ((var29 = var10.getTileEntity(var17)) != null) {
                        if (var29 instanceof IInventory) {
                            ((IInventory)var29).clearInventory();
                        }
                        var10.setBlockState(var17, Blocks.barrier.getDefaultState(), var5 == Blocks.barrier ? 2 : 4);
                    }
                    if (var10.setBlockState(var17, var19 = var5.getStateFromMeta(var6), 2)) {
                        var24.add(var17);
                        ++var9;
                        if (var23 && (var20 = var10.getTileEntity(var17)) != null) {
                            var22.setInteger("x", var17.getX());
                            var22.setInteger("y", var17.getY());
                            var22.setInteger("z", var17.getZ());
                            var20.readFromNBT(var22);
                        }
                    }
lbl83: // 10 sources:
                    ++var16;
                }
                ++var15;
            }
            ++var14;
        }
        for (BlockPos var26 : var24) {
            var27 = var10.getBlockState(var26).getBlock();
            var10.func_175722_b(var26, var27);
        }
        if (var9 <= 0) {
            throw new CommandException("commands.fill.failed", new Object[0]);
        }
        sender.func_174794_a(CommandResultStats.Type.AFFECTED_BLOCKS, var9);
        CommandFill.notifyOperators(sender, (ICommand)this, "commands.fill.success", new Object[]{var9});
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return args.length > 0 && args.length <= 3 ? CommandFill.func_175771_a(args, 0, pos) : (args.length > 3 && args.length <= 6 ? CommandFill.func_175771_a(args, 3, pos) : (args.length == 7 ? CommandFill.func_175762_a(args, Block.blockRegistry.getKeys()) : (args.length == 9 ? CommandFill.getListOfStringsMatchingLastWord(args, "replace", "destroy", "keep", "hollow", "outline") : null)));
    }
}

