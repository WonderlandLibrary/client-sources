/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.google.common.base.Joiner;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.ColumnPosArgument;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ColumnPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ForceLoadCommand {
    private static final Dynamic2CommandExceptionType TOO_BIG_EXCEPTION = new Dynamic2CommandExceptionType(ForceLoadCommand::lambda$static$0);
    private static final Dynamic2CommandExceptionType QUERY_FAILURE_EXCEPTION = new Dynamic2CommandExceptionType(ForceLoadCommand::lambda$static$1);
    private static final SimpleCommandExceptionType FAILED_ADD_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.forceload.added.failure"));
    private static final SimpleCommandExceptionType REMOVE_FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.forceload.removed.failure"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("forceload").requires(ForceLoadCommand::lambda$register$2)).then(Commands.literal("add").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("from", ColumnPosArgument.columnPos()).executes(ForceLoadCommand::lambda$register$3)).then(Commands.argument("to", ColumnPosArgument.columnPos()).executes(ForceLoadCommand::lambda$register$4))))).then(((LiteralArgumentBuilder)Commands.literal("remove").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("from", ColumnPosArgument.columnPos()).executes(ForceLoadCommand::lambda$register$5)).then(Commands.argument("to", ColumnPosArgument.columnPos()).executes(ForceLoadCommand::lambda$register$6)))).then(Commands.literal("all").executes(ForceLoadCommand::lambda$register$7)))).then(((LiteralArgumentBuilder)Commands.literal("query").executes(ForceLoadCommand::lambda$register$8)).then(Commands.argument("pos", ColumnPosArgument.columnPos()).executes(ForceLoadCommand::lambda$register$9))));
    }

    private static int doQuery(CommandSource commandSource, ColumnPos columnPos) throws CommandSyntaxException {
        ChunkPos chunkPos = new ChunkPos(columnPos.x >> 4, columnPos.z >> 4);
        ServerWorld serverWorld = commandSource.getWorld();
        RegistryKey<World> registryKey = serverWorld.getDimensionKey();
        boolean bl = serverWorld.getForcedChunks().contains(chunkPos.asLong());
        if (bl) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.forceload.query.success", chunkPos, registryKey.getLocation()), true);
            return 0;
        }
        throw QUERY_FAILURE_EXCEPTION.create(chunkPos, registryKey.getLocation());
    }

    private static int doList(CommandSource commandSource) {
        ServerWorld serverWorld = commandSource.getWorld();
        RegistryKey<World> registryKey = serverWorld.getDimensionKey();
        LongSet longSet = serverWorld.getForcedChunks();
        int n = longSet.size();
        if (n > 0) {
            String string = Joiner.on(", ").join(longSet.stream().sorted().map(ChunkPos::new).map(ChunkPos::toString).iterator());
            if (n == 1) {
                commandSource.sendFeedback(new TranslationTextComponent("commands.forceload.list.single", registryKey.getLocation(), string), true);
            } else {
                commandSource.sendFeedback(new TranslationTextComponent("commands.forceload.list.multiple", n, registryKey.getLocation(), string), true);
            }
        } else {
            commandSource.sendErrorMessage(new TranslationTextComponent("commands.forceload.added.none", registryKey.getLocation()));
        }
        return n;
    }

    private static int removeAll(CommandSource commandSource) {
        ServerWorld serverWorld = commandSource.getWorld();
        RegistryKey<World> registryKey = serverWorld.getDimensionKey();
        LongSet longSet = serverWorld.getForcedChunks();
        longSet.forEach(arg_0 -> ForceLoadCommand.lambda$removeAll$10(serverWorld, arg_0));
        commandSource.sendFeedback(new TranslationTextComponent("commands.forceload.removed.all", registryKey.getLocation()), false);
        return 1;
    }

    private static int doAddOrRemove(CommandSource commandSource, ColumnPos columnPos, ColumnPos columnPos2, boolean bl) throws CommandSyntaxException {
        int n = Math.min(columnPos.x, columnPos2.x);
        int n2 = Math.min(columnPos.z, columnPos2.z);
        int n3 = Math.max(columnPos.x, columnPos2.x);
        int n4 = Math.max(columnPos.z, columnPos2.z);
        if (n >= -30000000 && n2 >= -30000000 && n3 < 30000000 && n4 < 30000000) {
            int n5 = n3 >> 4;
            int n6 = n >> 4;
            int n7 = n4 >> 4;
            int n8 = n2 >> 4;
            long l = ((long)(n5 - n6) + 1L) * ((long)(n7 - n8) + 1L);
            if (l > 256L) {
                throw TOO_BIG_EXCEPTION.create(256, l);
            }
            ServerWorld serverWorld = commandSource.getWorld();
            RegistryKey<World> registryKey = serverWorld.getDimensionKey();
            ChunkPos chunkPos = null;
            int n9 = 0;
            for (int i = n6; i <= n5; ++i) {
                for (int j = n8; j <= n7; ++j) {
                    boolean bl2 = serverWorld.forceChunk(i, j, bl);
                    if (!bl2) continue;
                    ++n9;
                    if (chunkPos != null) continue;
                    chunkPos = new ChunkPos(i, j);
                }
            }
            if (n9 == 0) {
                throw (bl ? FAILED_ADD_EXCEPTION : REMOVE_FAILED_EXCEPTION).create();
            }
            if (n9 == 1) {
                commandSource.sendFeedback(new TranslationTextComponent("commands.forceload." + (bl ? "added" : "removed") + ".single", chunkPos, registryKey.getLocation()), false);
            } else {
                ChunkPos chunkPos2 = new ChunkPos(n6, n8);
                ChunkPos chunkPos3 = new ChunkPos(n5, n7);
                commandSource.sendFeedback(new TranslationTextComponent("commands.forceload." + (bl ? "added" : "removed") + ".multiple", n9, registryKey.getLocation(), chunkPos2, chunkPos3), false);
            }
            return n9;
        }
        throw BlockPosArgument.POS_OUT_OF_WORLD.create();
    }

    private static void lambda$removeAll$10(ServerWorld serverWorld, long l) {
        serverWorld.forceChunk(ChunkPos.getX(l), ChunkPos.getZ(l), true);
    }

    private static int lambda$register$9(CommandContext commandContext) throws CommandSyntaxException {
        return ForceLoadCommand.doQuery((CommandSource)commandContext.getSource(), ColumnPosArgument.fromBlockPos(commandContext, "pos"));
    }

    private static int lambda$register$8(CommandContext commandContext) throws CommandSyntaxException {
        return ForceLoadCommand.doList((CommandSource)commandContext.getSource());
    }

    private static int lambda$register$7(CommandContext commandContext) throws CommandSyntaxException {
        return ForceLoadCommand.removeAll((CommandSource)commandContext.getSource());
    }

    private static int lambda$register$6(CommandContext commandContext) throws CommandSyntaxException {
        return ForceLoadCommand.doAddOrRemove((CommandSource)commandContext.getSource(), ColumnPosArgument.fromBlockPos(commandContext, "from"), ColumnPosArgument.fromBlockPos(commandContext, "to"), false);
    }

    private static int lambda$register$5(CommandContext commandContext) throws CommandSyntaxException {
        return ForceLoadCommand.doAddOrRemove((CommandSource)commandContext.getSource(), ColumnPosArgument.fromBlockPos(commandContext, "from"), ColumnPosArgument.fromBlockPos(commandContext, "from"), false);
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return ForceLoadCommand.doAddOrRemove((CommandSource)commandContext.getSource(), ColumnPosArgument.fromBlockPos(commandContext, "from"), ColumnPosArgument.fromBlockPos(commandContext, "to"), true);
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return ForceLoadCommand.doAddOrRemove((CommandSource)commandContext.getSource(), ColumnPosArgument.fromBlockPos(commandContext, "from"), ColumnPosArgument.fromBlockPos(commandContext, "from"), true);
    }

    private static boolean lambda$register$2(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static Message lambda$static$1(Object object, Object object2) {
        return new TranslationTextComponent("commands.forceload.query.failure", object, object2);
    }

    private static Message lambda$static$0(Object object, Object object2) {
        return new TranslationTextComponent("commands.forceload.toobig", object, object2);
    }
}

