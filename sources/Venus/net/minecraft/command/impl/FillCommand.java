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
import java.util.Collections;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.BlockPredicateArgument;
import net.minecraft.command.arguments.BlockStateArgument;
import net.minecraft.command.arguments.BlockStateInput;
import net.minecraft.command.impl.SetBlockCommand;
import net.minecraft.inventory.IClearable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

public class FillCommand {
    private static final Dynamic2CommandExceptionType TOO_BIG_EXCEPTION = new Dynamic2CommandExceptionType(FillCommand::lambda$static$0);
    private static final BlockStateInput AIR = new BlockStateInput(Blocks.AIR.getDefaultState(), Collections.emptySet(), null);
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.fill.failed"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("fill").requires(FillCommand::lambda$register$1)).then(Commands.argument("from", BlockPosArgument.blockPos()).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("to", BlockPosArgument.blockPos()).then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("block", BlockStateArgument.blockState()).executes(FillCommand::lambda$register$2)).then(((LiteralArgumentBuilder)Commands.literal("replace").executes(FillCommand::lambda$register$3)).then(Commands.argument("filter", BlockPredicateArgument.blockPredicate()).executes(FillCommand::lambda$register$4)))).then(Commands.literal("keep").executes(FillCommand::lambda$register$6))).then(Commands.literal("outline").executes(FillCommand::lambda$register$7))).then(Commands.literal("hollow").executes(FillCommand::lambda$register$8))).then(Commands.literal("destroy").executes(FillCommand::lambda$register$9))))));
    }

    private static int doFill(CommandSource commandSource, MutableBoundingBox mutableBoundingBox, BlockStateInput blockStateInput, Mode mode, @Nullable Predicate<CachedBlockInfo> predicate) throws CommandSyntaxException {
        Object object;
        int n = mutableBoundingBox.getXSize() * mutableBoundingBox.getYSize() * mutableBoundingBox.getZSize();
        if (n > 32768) {
            throw TOO_BIG_EXCEPTION.create(32768, n);
        }
        ArrayList<BlockPos> arrayList = Lists.newArrayList();
        ServerWorld serverWorld = commandSource.getWorld();
        int n2 = 0;
        for (BlockPos blockPos : BlockPos.getAllInBoxMutable(mutableBoundingBox.minX, mutableBoundingBox.minY, mutableBoundingBox.minZ, mutableBoundingBox.maxX, mutableBoundingBox.maxY, mutableBoundingBox.maxZ)) {
            if (predicate != null && !predicate.test(new CachedBlockInfo(serverWorld, blockPos, true)) || (object = mode.filter.filter(mutableBoundingBox, blockPos, blockStateInput, serverWorld)) == null) continue;
            TileEntity tileEntity = serverWorld.getTileEntity(blockPos);
            IClearable.clearObj(tileEntity);
            if (!((BlockStateInput)object).place(serverWorld, blockPos, 1)) continue;
            arrayList.add(blockPos.toImmutable());
            ++n2;
        }
        for (BlockPos blockPos : arrayList) {
            object = serverWorld.getBlockState(blockPos).getBlock();
            serverWorld.func_230547_a_(blockPos, (Block)object);
        }
        if (n2 == 0) {
            throw FAILED_EXCEPTION.create();
        }
        commandSource.sendFeedback(new TranslationTextComponent("commands.fill.success", n2), false);
        return n2;
    }

    private static int lambda$register$9(CommandContext commandContext) throws CommandSyntaxException {
        return FillCommand.doFill((CommandSource)commandContext.getSource(), new MutableBoundingBox(BlockPosArgument.getLoadedBlockPos(commandContext, "from"), BlockPosArgument.getLoadedBlockPos(commandContext, "to")), BlockStateArgument.getBlockState(commandContext, "block"), Mode.DESTROY, null);
    }

    private static int lambda$register$8(CommandContext commandContext) throws CommandSyntaxException {
        return FillCommand.doFill((CommandSource)commandContext.getSource(), new MutableBoundingBox(BlockPosArgument.getLoadedBlockPos(commandContext, "from"), BlockPosArgument.getLoadedBlockPos(commandContext, "to")), BlockStateArgument.getBlockState(commandContext, "block"), Mode.HOLLOW, null);
    }

    private static int lambda$register$7(CommandContext commandContext) throws CommandSyntaxException {
        return FillCommand.doFill((CommandSource)commandContext.getSource(), new MutableBoundingBox(BlockPosArgument.getLoadedBlockPos(commandContext, "from"), BlockPosArgument.getLoadedBlockPos(commandContext, "to")), BlockStateArgument.getBlockState(commandContext, "block"), Mode.OUTLINE, null);
    }

    private static int lambda$register$6(CommandContext commandContext) throws CommandSyntaxException {
        return FillCommand.doFill((CommandSource)commandContext.getSource(), new MutableBoundingBox(BlockPosArgument.getLoadedBlockPos(commandContext, "from"), BlockPosArgument.getLoadedBlockPos(commandContext, "to")), BlockStateArgument.getBlockState(commandContext, "block"), Mode.REPLACE, FillCommand::lambda$register$5);
    }

    private static boolean lambda$register$5(CachedBlockInfo cachedBlockInfo) {
        return cachedBlockInfo.getWorld().isAirBlock(cachedBlockInfo.getPos());
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return FillCommand.doFill((CommandSource)commandContext.getSource(), new MutableBoundingBox(BlockPosArgument.getLoadedBlockPos(commandContext, "from"), BlockPosArgument.getLoadedBlockPos(commandContext, "to")), BlockStateArgument.getBlockState(commandContext, "block"), Mode.REPLACE, BlockPredicateArgument.getBlockPredicate(commandContext, "filter"));
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return FillCommand.doFill((CommandSource)commandContext.getSource(), new MutableBoundingBox(BlockPosArgument.getLoadedBlockPos(commandContext, "from"), BlockPosArgument.getLoadedBlockPos(commandContext, "to")), BlockStateArgument.getBlockState(commandContext, "block"), Mode.REPLACE, null);
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return FillCommand.doFill((CommandSource)commandContext.getSource(), new MutableBoundingBox(BlockPosArgument.getLoadedBlockPos(commandContext, "from"), BlockPosArgument.getLoadedBlockPos(commandContext, "to")), BlockStateArgument.getBlockState(commandContext, "block"), Mode.REPLACE, null);
    }

    private static boolean lambda$register$1(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static Message lambda$static$0(Object object, Object object2) {
        return new TranslationTextComponent("commands.fill.toobig", object, object2);
    }

    static enum Mode {
        REPLACE(Mode::lambda$static$0),
        OUTLINE(Mode::lambda$static$1),
        HOLLOW(Mode::lambda$static$2),
        DESTROY(Mode::lambda$static$3);

        public final SetBlockCommand.IFilter filter;

        private Mode(SetBlockCommand.IFilter iFilter) {
            this.filter = iFilter;
        }

        private static BlockStateInput lambda$static$3(MutableBoundingBox mutableBoundingBox, BlockPos blockPos, BlockStateInput blockStateInput, ServerWorld serverWorld) {
            serverWorld.destroyBlock(blockPos, false);
            return blockStateInput;
        }

        private static BlockStateInput lambda$static$2(MutableBoundingBox mutableBoundingBox, BlockPos blockPos, BlockStateInput blockStateInput, ServerWorld serverWorld) {
            return blockPos.getX() != mutableBoundingBox.minX && blockPos.getX() != mutableBoundingBox.maxX && blockPos.getY() != mutableBoundingBox.minY && blockPos.getY() != mutableBoundingBox.maxY && blockPos.getZ() != mutableBoundingBox.minZ && blockPos.getZ() != mutableBoundingBox.maxZ ? AIR : blockStateInput;
        }

        private static BlockStateInput lambda$static$1(MutableBoundingBox mutableBoundingBox, BlockPos blockPos, BlockStateInput blockStateInput, ServerWorld serverWorld) {
            return blockPos.getX() != mutableBoundingBox.minX && blockPos.getX() != mutableBoundingBox.maxX && blockPos.getY() != mutableBoundingBox.minY && blockPos.getY() != mutableBoundingBox.maxY && blockPos.getZ() != mutableBoundingBox.minZ && blockPos.getZ() != mutableBoundingBox.maxZ ? null : blockStateInput;
        }

        private static BlockStateInput lambda$static$0(MutableBoundingBox mutableBoundingBox, BlockPos blockPos, BlockStateInput blockStateInput, ServerWorld serverWorld) {
            return blockStateInput;
        }
    }
}

