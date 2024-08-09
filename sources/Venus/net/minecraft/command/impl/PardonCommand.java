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
import net.minecraft.server.management.BanList;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TranslationTextComponent;

public class PardonCommand {
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.pardon.failed"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("pardon").requires(PardonCommand::lambda$register$0)).then(Commands.argument("targets", GameProfileArgument.gameProfile()).suggests(PardonCommand::lambda$register$1).executes(PardonCommand::lambda$register$2)));
    }

    private static int unbanPlayers(CommandSource commandSource, Collection<GameProfile> collection) throws CommandSyntaxException {
        BanList banList = commandSource.getServer().getPlayerList().getBannedPlayers();
        int n = 0;
        for (GameProfile gameProfile : collection) {
            if (!banList.isBanned(gameProfile)) continue;
            banList.removeEntry(gameProfile);
            ++n;
            commandSource.sendFeedback(new TranslationTextComponent("commands.pardon.success", TextComponentUtils.getDisplayName(gameProfile)), false);
        }
        if (n == 0) {
            throw FAILED_EXCEPTION.create();
        }
        return n;
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return PardonCommand.unbanPlayers((CommandSource)commandContext.getSource(), GameProfileArgument.getGameProfiles(commandContext, "targets"));
    }

    private static CompletableFuture lambda$register$1(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        return ISuggestionProvider.suggest(((CommandSource)commandContext.getSource()).getServer().getPlayerList().getBannedPlayers().getKeys(), suggestionsBuilder);
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(0);
    }
}

