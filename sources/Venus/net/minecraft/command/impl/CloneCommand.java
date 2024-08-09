/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.BlockPredicateArgument;
import net.minecraft.inventory.IClearable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerTickList;
import net.minecraft.world.server.ServerWorld;

public class CloneCommand {
    private static final SimpleCommandExceptionType OVERLAP_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.clone.overlap"));
    private static final Dynamic2CommandExceptionType CLONE_TOO_BIG_EXCEPTION = new Dynamic2CommandExceptionType(CloneCommand::lambda$static$0);
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.clone.failed"));
    public static final Predicate<CachedBlockInfo> NOT_AIR = CloneCommand::lambda$static$1;

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("clone").requires(CloneCommand::lambda$register$2)).then(Commands.argument("begin", BlockPosArgument.blockPos()).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("end", BlockPosArgument.blockPos()).then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("destination", BlockPosArgument.blockPos()).executes(CloneCommand::lambda$register$4)).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("replace").executes(CloneCommand::lambda$register$6)).then(Commands.literal("force").executes(CloneCommand::lambda$register$8))).then(Commands.literal("move").executes(CloneCommand::lambda$register$10))).then(Commands.literal("normal").executes(CloneCommand::lambda$register$12)))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("masked").executes(CloneCommand::lambda$register$13)).then(Commands.literal("force").executes(CloneCommand::lambda$register$14))).then(Commands.literal("move").executes(CloneCommand::lambda$register$15))).then(Commands.literal("normal").executes(CloneCommand::lambda$register$16)))).then(Commands.literal("filtered").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("filter", BlockPredicateArgument.blockPredicate()).executes(CloneCommand::lambda$register$17)).then(Commands.literal("force").executes(CloneCommand::lambda$register$18))).then(Commands.literal("move").executes(CloneCommand::lambda$register$19))).then(Commands.literal("normal").executes(CloneCommand::lambda$register$20))))))));
    }

    private static int doClone(CommandSource commandSource, BlockPos blockPos, BlockPos blockPos2, BlockPos blockPos3, Predicate<CachedBlockInfo> predicate, Mode mode) throws CommandSyntaxException {
        MutableBoundingBox mutableBoundingBox = new MutableBoundingBox(blockPos, blockPos2);
        BlockPos blockPos4 = blockPos3.add(mutableBoundingBox.getLength());
        MutableBoundingBox mutableBoundingBox2 = new MutableBoundingBox(blockPos3, blockPos4);
        if (!mode.allowsOverlap() && mutableBoundingBox2.intersectsWith(mutableBoundingBox)) {
            throw OVERLAP_EXCEPTION.create();
        }
        int n = mutableBoundingBox.getXSize() * mutableBoundingBox.getYSize() * mutableBoundingBox.getZSize();
        if (n > 32768) {
            throw CLONE_TOO_BIG_EXCEPTION.create(32768, n);
        }
        ServerWorld serverWorld = commandSource.getWorld();
        if (serverWorld.isAreaLoaded(blockPos, blockPos2) && serverWorld.isAreaLoaded(blockPos3, blockPos4)) {
            Object object;
            ArrayList<BlockInfo> arrayList = Lists.newArrayList();
            ArrayList<BlockInfo> arrayList2 = Lists.newArrayList();
            ArrayList<BlockInfo> arrayList3 = Lists.newArrayList();
            LinkedList linkedList = Lists.newLinkedList();
            BlockPos blockPos5 = new BlockPos(mutableBoundingBox2.minX - mutableBoundingBox.minX, mutableBoundingBox2.minY - mutableBoundingBox.minY, mutableBoundingBox2.minZ - mutableBoundingBox.minZ);
            for (int i = mutableBoundingBox.minZ; i <= mutableBoundingBox.maxZ; ++i) {
                for (int j = mutableBoundingBox.minY; j <= mutableBoundingBox.maxY; ++j) {
                    for (int k = mutableBoundingBox.minX; k <= mutableBoundingBox.maxX; ++k) {
                        Iterator iterator2 = new BlockPos(k, j, i);
                        BlockPos object22 = ((BlockPos)((Object)iterator2)).add(blockPos5);
                        object = new CachedBlockInfo(serverWorld, (BlockPos)((Object)iterator2), false);
                        BlockState blockState = ((CachedBlockInfo)object).getBlockState();
                        if (!predicate.test((CachedBlockInfo)object)) continue;
                        TileEntity tileEntity = serverWorld.getTileEntity((BlockPos)((Object)iterator2));
                        if (tileEntity != null) {
                            CompoundNBT compoundNBT = tileEntity.write(new CompoundNBT());
                            arrayList2.add(new BlockInfo(object22, blockState, compoundNBT));
                            linkedList.addLast(iterator2);
                            continue;
                        }
                        if (!blockState.isOpaqueCube(serverWorld, (BlockPos)((Object)iterator2)) && !blockState.hasOpaqueCollisionShape(serverWorld, (BlockPos)((Object)iterator2))) {
                            arrayList3.add(new BlockInfo(object22, blockState, null));
                            linkedList.addFirst(iterator2);
                            continue;
                        }
                        arrayList.add(new BlockInfo(object22, blockState, null));
                        linkedList.addLast(iterator2);
                    }
                }
            }
            if (mode == Mode.MOVE) {
                for (BlockPos blockPos6 : linkedList) {
                    TileEntity tileEntity = serverWorld.getTileEntity(blockPos6);
                    IClearable.clearObj(tileEntity);
                    serverWorld.setBlockState(blockPos6, Blocks.BARRIER.getDefaultState(), 1);
                }
                for (BlockPos blockPos7 : linkedList) {
                    serverWorld.setBlockState(blockPos7, Blocks.AIR.getDefaultState(), 0);
                }
            }
            ArrayList<BlockInfo> arrayList4 = Lists.newArrayList();
            arrayList4.addAll(arrayList);
            arrayList4.addAll(arrayList2);
            arrayList4.addAll(arrayList3);
            List list = Lists.reverse(arrayList4);
            for (Iterator iterator2 : list) {
                TileEntity tileEntity = serverWorld.getTileEntity(((BlockInfo)((Object)iterator2)).pos);
                IClearable.clearObj(tileEntity);
                serverWorld.setBlockState(((BlockInfo)((Object)iterator2)).pos, Blocks.BARRIER.getDefaultState(), 1);
            }
            int n2 = 0;
            for (BlockInfo blockInfo : arrayList4) {
                if (!serverWorld.setBlockState(blockInfo.pos, blockInfo.state, 1)) continue;
                ++n2;
            }
            for (BlockInfo blockInfo : arrayList2) {
                object = serverWorld.getTileEntity(blockInfo.pos);
                if (blockInfo.tag != null && object != null) {
                    blockInfo.tag.putInt("x", blockInfo.pos.getX());
                    blockInfo.tag.putInt("y", blockInfo.pos.getY());
                    blockInfo.tag.putInt("z", blockInfo.pos.getZ());
                    ((TileEntity)object).read(blockInfo.state, blockInfo.tag);
                    ((TileEntity)object).markDirty();
                }
                serverWorld.setBlockState(blockInfo.pos, blockInfo.state, 1);
            }
            for (BlockInfo blockInfo : list) {
                serverWorld.func_230547_a_(blockInfo.pos, blockInfo.state.getBlock());
            }
            ((ServerTickList)serverWorld.getPendingBlockTicks()).copyTicks(mutableBoundingBox, blockPos5);
            if (n2 == 0) {
                throw FAILED_EXCEPTION.create();
            }
            commandSource.sendFeedback(new TranslationTextComponent("commands.clone.success", n2), false);
            return n2;
        }
        throw BlockPosArgument.POS_UNLOADED.create();
    }

    private static int lambda$register$20(CommandContext commandContext) throws CommandSyntaxException {
        return CloneCommand.doClone((CommandSource)commandContext.getSource(), BlockPosArgument.getLoadedBlockPos(commandContext, "begin"), BlockPosArgument.getLoadedBlockPos(commandContext, "end"), BlockPosArgument.getLoadedBlockPos(commandContext, "destination"), BlockPredicateArgument.getBlockPredicate(commandContext, "filter"), Mode.NORMAL);
    }

    private static int lambda$register$19(CommandContext commandContext) throws CommandSyntaxException {
        return CloneCommand.doClone((CommandSource)commandContext.getSource(), BlockPosArgument.getLoadedBlockPos(commandContext, "begin"), BlockPosArgument.getLoadedBlockPos(commandContext, "end"), BlockPosArgument.getLoadedBlockPos(commandContext, "destination"), BlockPredicateArgument.getBlockPredicate(commandContext, "filter"), Mode.MOVE);
    }

    private static int lambda$register$18(CommandContext commandContext) throws CommandSyntaxException {
        return CloneCommand.doClone((CommandSource)commandContext.getSource(), BlockPosArgument.getLoadedBlockPos(commandContext, "begin"), BlockPosArgument.getLoadedBlockPos(commandContext, "end"), BlockPosArgument.getLoadedBlockPos(commandContext, "destination"), BlockPredicateArgument.getBlockPredicate(commandContext, "filter"), Mode.FORCE);
    }

    private static int lambda$register$17(CommandContext commandContext) throws CommandSyntaxException {
        return CloneCommand.doClone((CommandSource)commandContext.getSource(), BlockPosArgument.getLoadedBlockPos(commandContext, "begin"), BlockPosArgument.getLoadedBlockPos(commandContext, "end"), BlockPosArgument.getLoadedBlockPos(commandContext, "destination"), BlockPredicateArgument.getBlockPredicate(commandContext, "filter"), Mode.NORMAL);
    }

    private static int lambda$register$16(CommandContext commandContext) throws CommandSyntaxException {
        return CloneCommand.doClone((CommandSource)commandContext.getSource(), BlockPosArgument.getLoadedBlockPos(commandContext, "begin"), BlockPosArgument.getLoadedBlockPos(commandContext, "end"), BlockPosArgument.getLoadedBlockPos(commandContext, "destination"), NOT_AIR, Mode.NORMAL);
    }

    private static int lambda$register$15(CommandContext commandContext) throws CommandSyntaxException {
        return CloneCommand.doClone((CommandSource)commandContext.getSource(), BlockPosArgument.getLoadedBlockPos(commandContext, "begin"), BlockPosArgument.getLoadedBlockPos(commandContext, "end"), BlockPosArgument.getLoadedBlockPos(commandContext, "destination"), NOT_AIR, Mode.MOVE);
    }

    private static int lambda$register$14(CommandContext commandContext) throws CommandSyntaxException {
        return CloneCommand.doClone((CommandSource)commandContext.getSource(), BlockPosArgument.getLoadedBlockPos(commandContext, "begin"), BlockPosArgument.getLoadedBlockPos(commandContext, "end"), BlockPosArgument.getLoadedBlockPos(commandContext, "destination"), NOT_AIR, Mode.FORCE);
    }

    private static int lambda$register$13(CommandContext commandContext) throws CommandSyntaxException {
        return CloneCommand.doClone((CommandSource)commandContext.getSource(), BlockPosArgument.getLoadedBlockPos(commandContext, "begin"), BlockPosArgument.getLoadedBlockPos(commandContext, "end"), BlockPosArgument.getLoadedBlockPos(commandContext, "destination"), NOT_AIR, Mode.NORMAL);
    }

    private static int lambda$register$12(CommandContext commandContext) throws CommandSyntaxException {
        return CloneCommand.doClone((CommandSource)commandContext.getSource(), BlockPosArgument.getLoadedBlockPos(commandContext, "begin"), BlockPosArgument.getLoadedBlockPos(commandContext, "end"), BlockPosArgument.getLoadedBlockPos(commandContext, "destination"), CloneCommand::lambda$register$11, Mode.NORMAL);
    }

    private static boolean lambda$register$11(CachedBlockInfo cachedBlockInfo) {
        return false;
    }

    private static int lambda$register$10(CommandContext commandContext) throws CommandSyntaxException {
        return CloneCommand.doClone((CommandSource)commandContext.getSource(), BlockPosArgument.getLoadedBlockPos(commandContext, "begin"), BlockPosArgument.getLoadedBlockPos(commandContext, "end"), BlockPosArgument.getLoadedBlockPos(commandContext, "destination"), CloneCommand::lambda$register$9, Mode.MOVE);
    }

    private static boolean lambda$register$9(CachedBlockInfo cachedBlockInfo) {
        return false;
    }

    private static int lambda$register$8(CommandContext commandContext) throws CommandSyntaxException {
        return CloneCommand.doClone((CommandSource)commandContext.getSource(), BlockPosArgument.getLoadedBlockPos(commandContext, "begin"), BlockPosArgument.getLoadedBlockPos(commandContext, "end"), BlockPosArgument.getLoadedBlockPos(commandContext, "destination"), CloneCommand::lambda$register$7, Mode.FORCE);
    }

    private static boolean lambda$register$7(CachedBlockInfo cachedBlockInfo) {
        return false;
    }

    private static int lambda$register$6(CommandContext commandContext) throws CommandSyntaxException {
        return CloneCommand.doClone((CommandSource)commandContext.getSource(), BlockPosArgument.getLoadedBlockPos(commandContext, "begin"), BlockPosArgument.getLoadedBlockPos(commandContext, "end"), BlockPosArgument.getLoadedBlockPos(commandContext, "destination"), CloneCommand::lambda$register$5, Mode.NORMAL);
    }

    private static boolean lambda$register$5(CachedBlockInfo cachedBlockInfo) {
        return false;
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return CloneCommand.doClone((CommandSource)commandContext.getSource(), BlockPosArgument.getLoadedBlockPos(commandContext, "begin"), BlockPosArgument.getLoadedBlockPos(commandContext, "end"), BlockPosArgument.getLoadedBlockPos(commandContext, "destination"), CloneCommand::lambda$register$3, Mode.NORMAL);
    }

    private static boolean lambda$register$3(CachedBlockInfo cachedBlockInfo) {
        return false;
    }

    private static boolean lambda$register$2(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static boolean lambda$static$1(CachedBlockInfo cachedBlockInfo) {
        return !cachedBlockInfo.getBlockState().isAir();
    }

    private static Message lambda$static$0(Object object, Object object2) {
        return new TranslationTextComponent("commands.clone.toobig", object, object2);
    }

    static enum Mode {
        FORCE(true),
        MOVE(true),
        NORMAL(false);

        private final boolean allowOverlap;

        private Mode(boolean bl) {
            this.allowOverlap = bl;
        }

        public boolean allowsOverlap() {
            return this.allowOverlap;
        }
    }

    static class BlockInfo {
        public final BlockPos pos;
        public final BlockState state;
        @Nullable
        public final CompoundNBT tag;

        public BlockInfo(BlockPos blockPos, BlockState blockState, @Nullable CompoundNBT compoundNBT) {
            this.pos = blockPos;
            this.state = blockState;
            this.tag = compoundNBT;
        }
    }
}

