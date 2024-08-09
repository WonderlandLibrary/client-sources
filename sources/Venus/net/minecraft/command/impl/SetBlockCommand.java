/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.BlockStateArgument;
import net.minecraft.command.arguments.BlockStateInput;
import net.minecraft.inventory.IClearable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

public class SetBlockCommand {
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.setblock.failed"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("setblock").requires(SetBlockCommand::lambda$register$0)).then(Commands.argument("pos", BlockPosArgument.blockPos()).then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("block", BlockStateArgument.blockState()).executes(SetBlockCommand::lambda$register$1)).then(Commands.literal("destroy").executes(SetBlockCommand::lambda$register$2))).then(Commands.literal("keep").executes(SetBlockCommand::lambda$register$4))).then(Commands.literal("replace").executes(SetBlockCommand::lambda$register$5)))));
    }

    private static int setBlock(CommandSource commandSource, BlockPos blockPos, BlockStateInput blockStateInput, Mode mode, @Nullable Predicate<CachedBlockInfo> predicate) throws CommandSyntaxException {
        boolean bl;
        ServerWorld serverWorld = commandSource.getWorld();
        if (predicate != null && !predicate.test(new CachedBlockInfo(serverWorld, blockPos, true))) {
            throw FAILED_EXCEPTION.create();
        }
        if (mode == Mode.DESTROY) {
            serverWorld.destroyBlock(blockPos, false);
            bl = !blockStateInput.getState().isAir() || !serverWorld.getBlockState(blockPos).isAir();
        } else {
            TileEntity tileEntity = serverWorld.getTileEntity(blockPos);
            IClearable.clearObj(tileEntity);
            bl = true;
        }
        if (bl && !blockStateInput.place(serverWorld, blockPos, 1)) {
            throw FAILED_EXCEPTION.create();
        }
        serverWorld.func_230547_a_(blockPos, blockStateInput.getState().getBlock());
        commandSource.sendFeedback(new TranslationTextComponent("commands.setblock.success", blockPos.getX(), blockPos.getY(), blockPos.getZ()), false);
        return 0;
    }

    private static int lambda$register$5(CommandContext commandContext) throws CommandSyntaxException {
        return SetBlockCommand.setBlock((CommandSource)commandContext.getSource(), BlockPosArgument.getLoadedBlockPos(commandContext, "pos"), BlockStateArgument.getBlockState(commandContext, "block"), Mode.REPLACE, null);
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return SetBlockCommand.setBlock((CommandSource)commandContext.getSource(), BlockPosArgument.getLoadedBlockPos(commandContext, "pos"), BlockStateArgument.getBlockState(commandContext, "block"), Mode.REPLACE, SetBlockCommand::lambda$register$3);
    }

    private static boolean lambda$register$3(CachedBlockInfo cachedBlockInfo) {
        return cachedBlockInfo.getWorld().isAirBlock(cachedBlockInfo.getPos());
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return SetBlockCommand.setBlock((CommandSource)commandContext.getSource(), BlockPosArgument.getLoadedBlockPos(commandContext, "pos"), BlockStateArgument.getBlockState(commandContext, "block"), Mode.DESTROY, null);
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        return SetBlockCommand.setBlock((CommandSource)commandContext.getSource(), BlockPosArgument.getLoadedBlockPos(commandContext, "pos"), BlockStateArgument.getBlockState(commandContext, "block"), Mode.REPLACE, null);
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    public static enum Mode {
        REPLACE,
        DESTROY;

    }

    public static interface IFilter {
        @Nullable
        public BlockStateInput filter(MutableBoundingBox var1, BlockPos var2, BlockStateInput var3, ServerWorld var4);
    }
}

