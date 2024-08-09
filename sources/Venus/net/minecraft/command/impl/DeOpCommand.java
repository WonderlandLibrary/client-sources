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
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.TranslationTextComponent;

public class DeOpCommand {
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.deop.failed"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("deop").requires(DeOpCommand::lambda$register$0)).then(Commands.argument("targets", GameProfileArgument.gameProfile()).suggests(DeOpCommand::lambda$register$1).executes(DeOpCommand::lambda$register$2)));
    }

    private static int deopPlayers(CommandSource commandSource, Collection<GameProfile> collection) throws CommandSyntaxException {
        PlayerList playerList = commandSource.getServer().getPlayerList();
        int n = 0;
        for (GameProfile gameProfile : collection) {
            if (!playerList.canSendCommands(gameProfile)) continue;
            playerList.removeOp(gameProfile);
            ++n;
            commandSource.sendFeedback(new TranslationTextComponent("commands.deop.success", collection.iterator().next().getName()), false);
        }
        if (n == 0) {
            throw FAILED_EXCEPTION.create();
        }
        commandSource.getServer().kickPlayersNotWhitelisted(commandSource);
        return n;
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return DeOpCommand.deopPlayers((CommandSource)commandContext.getSource(), GameProfileArgument.getGameProfiles(commandContext, "targets"));
    }

    private static CompletableFuture lambda$register$1(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        return ISuggestionProvider.suggest(((CommandSource)commandContext.getSource()).getServer().getPlayerList().getOppedPlayerNames(), suggestionsBuilder);
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(0);
    }
}

