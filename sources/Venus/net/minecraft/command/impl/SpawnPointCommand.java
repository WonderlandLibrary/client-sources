/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.AngleArgument;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class SpawnPointCommand {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("spawnpoint").requires(SpawnPointCommand::lambda$register$0)).executes(SpawnPointCommand::lambda$register$1)).then(((RequiredArgumentBuilder)Commands.argument("targets", EntityArgument.players()).executes(SpawnPointCommand::lambda$register$2)).then(((RequiredArgumentBuilder)Commands.argument("pos", BlockPosArgument.blockPos()).executes(SpawnPointCommand::lambda$register$3)).then(Commands.argument("angle", AngleArgument.func_242991_a()).executes(SpawnPointCommand::lambda$register$4)))));
    }

    private static int setSpawnPoint(CommandSource commandSource, Collection<ServerPlayerEntity> collection, BlockPos blockPos, float f) {
        RegistryKey<World> registryKey = commandSource.getWorld().getDimensionKey();
        for (ServerPlayerEntity serverPlayerEntity : collection) {
            serverPlayerEntity.func_242111_a(registryKey, blockPos, f, true, true);
        }
        String string = registryKey.getLocation().toString();
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.spawnpoint.success.single", blockPos.getX(), blockPos.getY(), blockPos.getZ(), Float.valueOf(f), string, collection.iterator().next().getDisplayName()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.spawnpoint.success.multiple", blockPos.getX(), blockPos.getY(), blockPos.getZ(), Float.valueOf(f), string, collection.size()), false);
        }
        return collection.size();
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return SpawnPointCommand.setSpawnPoint((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), BlockPosArgument.getBlockPos(commandContext, "pos"), AngleArgument.func_242992_a(commandContext, "angle"));
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return SpawnPointCommand.setSpawnPoint((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), BlockPosArgument.getBlockPos(commandContext, "pos"), 0.0f);
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return SpawnPointCommand.setSpawnPoint((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), new BlockPos(((CommandSource)commandContext.getSource()).getPos()), 0.0f);
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        return SpawnPointCommand.setSpawnPoint((CommandSource)commandContext.getSource(), Collections.singleton(((CommandSource)commandContext.getSource()).asPlayer()), new BlockPos(((CommandSource)commandContext.getSource()).getPos()), 0.0f);
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }
}

