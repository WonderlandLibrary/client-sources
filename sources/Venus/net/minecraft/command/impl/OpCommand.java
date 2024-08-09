/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.GameProfileArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.TranslationTextComponent;

public class OpCommand {
    private static final SimpleCommandExceptionType ALREADY_OP = new SimpleCommandExceptionType(new TranslationTextComponent("commands.op.failed"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("op").requires(OpCommand::lambda$register$0)).then(Commands.argument("targets", GameProfileArgument.gameProfile()).suggests(OpCommand::lambda$register$3).executes(OpCommand::lambda$register$4)));
    }

    private static int opPlayers(CommandSource commandSource, Collection<GameProfile> collection) throws CommandSyntaxException {
        PlayerList playerList = commandSource.getServer().getPlayerList();
        int n = 0;
        for (GameProfile gameProfile : collection) {
            if (playerList.canSendCommands(gameProfile)) continue;
            playerList.addOp(gameProfile);
            ++n;
            commandSource.sendFeedback(new TranslationTextComponent("commands.op.success", collection.iterator().next().getName()), false);
        }
        if (n == 0) {
            throw ALREADY_OP.create();
        }
        return n;
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return OpCommand.opPlayers((CommandSource)commandContext.getSource(), GameProfileArgument.getGameProfiles(commandContext, "targets"));
    }

    private static CompletableFuture lambda$register$3(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        PlayerList playerList = ((CommandSource)commandContext.getSource()).getServer().getPlayerList();
        return ISuggestionProvider.suggest(playerList.getPlayers().stream().filter(arg_0 -> OpCommand.lambda$register$1(playerList, arg_0)).map(OpCommand::lambda$register$2), suggestionsBuilder);
    }

    private static String lambda$register$2(ServerPlayerEntity serverPlayerEntity) {
        return serverPlayerEntity.getGameProfile().getName();
    }

    private static boolean lambda$register$1(PlayerList playerList, ServerPlayerEntity serverPlayerEntity) {
        return !playerList.canSendCommands(serverPlayerEntity.getGameProfile());
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(0);
    }
}

