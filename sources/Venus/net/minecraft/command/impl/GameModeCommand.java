/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;

public class GameModeCommand {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        LiteralArgumentBuilder literalArgumentBuilder = (LiteralArgumentBuilder)Commands.literal("gamemode").requires(GameModeCommand::lambda$register$0);
        for (GameType gameType : GameType.values()) {
            if (gameType == GameType.NOT_SET) continue;
            literalArgumentBuilder.then(((LiteralArgumentBuilder)Commands.literal(gameType.getName()).executes(arg_0 -> GameModeCommand.lambda$register$1(gameType, arg_0))).then(Commands.argument("target", EntityArgument.players()).executes(arg_0 -> GameModeCommand.lambda$register$2(gameType, arg_0))));
        }
        commandDispatcher.register(literalArgumentBuilder);
    }

    private static void sendGameModeFeedback(CommandSource commandSource, ServerPlayerEntity serverPlayerEntity, GameType gameType) {
        TranslationTextComponent translationTextComponent = new TranslationTextComponent("gameMode." + gameType.getName());
        if (commandSource.getEntity() == serverPlayerEntity) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.gamemode.success.self", translationTextComponent), false);
        } else {
            if (commandSource.getWorld().getGameRules().getBoolean(GameRules.SEND_COMMAND_FEEDBACK)) {
                serverPlayerEntity.sendMessage(new TranslationTextComponent("gameMode.changed", translationTextComponent), Util.DUMMY_UUID);
            }
            commandSource.sendFeedback(new TranslationTextComponent("commands.gamemode.success.other", serverPlayerEntity.getDisplayName(), translationTextComponent), false);
        }
    }

    private static int setGameMode(CommandContext<CommandSource> commandContext, Collection<ServerPlayerEntity> collection, GameType gameType) {
        int n = 0;
        for (ServerPlayerEntity serverPlayerEntity : collection) {
            if (serverPlayerEntity.interactionManager.getGameType() == gameType) continue;
            serverPlayerEntity.setGameType(gameType);
            GameModeCommand.sendGameModeFeedback(commandContext.getSource(), serverPlayerEntity, gameType);
            ++n;
        }
        return n;
    }

    private static int lambda$register$2(GameType gameType, CommandContext commandContext) throws CommandSyntaxException {
        return GameModeCommand.setGameMode(commandContext, EntityArgument.getPlayers(commandContext, "target"), gameType);
    }

    private static int lambda$register$1(GameType gameType, CommandContext commandContext) throws CommandSyntaxException {
        return GameModeCommand.setGameMode(commandContext, Collections.singleton(((CommandSource)commandContext.getSource()).asPlayer()), gameType);
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }
}

