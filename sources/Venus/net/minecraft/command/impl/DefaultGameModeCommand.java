/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;

public class DefaultGameModeCommand {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        LiteralArgumentBuilder literalArgumentBuilder = (LiteralArgumentBuilder)Commands.literal("defaultgamemode").requires(DefaultGameModeCommand::lambda$register$0);
        for (GameType gameType : GameType.values()) {
            if (gameType == GameType.NOT_SET) continue;
            literalArgumentBuilder.then(Commands.literal(gameType.getName()).executes(arg_0 -> DefaultGameModeCommand.lambda$register$1(gameType, arg_0)));
        }
        commandDispatcher.register(literalArgumentBuilder);
    }

    private static int setGameType(CommandSource commandSource, GameType gameType) {
        int n = 0;
        MinecraftServer minecraftServer = commandSource.getServer();
        minecraftServer.setGameType(gameType);
        if (minecraftServer.getForceGamemode()) {
            for (ServerPlayerEntity serverPlayerEntity : minecraftServer.getPlayerList().getPlayers()) {
                if (serverPlayerEntity.interactionManager.getGameType() == gameType) continue;
                serverPlayerEntity.setGameType(gameType);
                ++n;
            }
        }
        commandSource.sendFeedback(new TranslationTextComponent("commands.defaultgamemode.success", gameType.getDisplayName()), false);
        return n;
    }

    private static int lambda$register$1(GameType gameType, CommandContext commandContext) throws CommandSyntaxException {
        return DefaultGameModeCommand.setGameType((CommandSource)commandContext.getSource(), gameType);
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }
}

