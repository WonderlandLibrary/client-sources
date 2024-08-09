/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
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
import net.minecraft.server.management.WhiteList;
import net.minecraft.server.management.WhitelistEntry;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TranslationTextComponent;

public class WhitelistCommand {
    private static final SimpleCommandExceptionType ALREADY_ON = new SimpleCommandExceptionType(new TranslationTextComponent("commands.whitelist.alreadyOn"));
    private static final SimpleCommandExceptionType ALREADY_OFF = new SimpleCommandExceptionType(new TranslationTextComponent("commands.whitelist.alreadyOff"));
    private static final SimpleCommandExceptionType PLAYER_ALREADY_WHITELISTED = new SimpleCommandExceptionType(new TranslationTextComponent("commands.whitelist.add.failed"));
    private static final SimpleCommandExceptionType PLAYER_NOT_WHITELISTED = new SimpleCommandExceptionType(new TranslationTextComponent("commands.whitelist.remove.failed"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("whitelist").requires(WhitelistCommand::lambda$register$0)).then(Commands.literal("on").executes(WhitelistCommand::lambda$register$1))).then(Commands.literal("off").executes(WhitelistCommand::lambda$register$2))).then(Commands.literal("list").executes(WhitelistCommand::lambda$register$3))).then(Commands.literal("add").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("targets", GameProfileArgument.gameProfile()).suggests(WhitelistCommand::lambda$register$6).executes(WhitelistCommand::lambda$register$7)))).then(Commands.literal("remove").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("targets", GameProfileArgument.gameProfile()).suggests(WhitelistCommand::lambda$register$8).executes(WhitelistCommand::lambda$register$9)))).then(Commands.literal("reload").executes(WhitelistCommand::lambda$register$10)));
    }

    private static int reload(CommandSource commandSource) {
        commandSource.getServer().getPlayerList().reloadWhitelist();
        commandSource.sendFeedback(new TranslationTextComponent("commands.whitelist.reloaded"), false);
        commandSource.getServer().kickPlayersNotWhitelisted(commandSource);
        return 0;
    }

    private static int addPlayers(CommandSource commandSource, Collection<GameProfile> collection) throws CommandSyntaxException {
        WhiteList whiteList = commandSource.getServer().getPlayerList().getWhitelistedPlayers();
        int n = 0;
        for (GameProfile gameProfile : collection) {
            if (whiteList.isWhitelisted(gameProfile)) continue;
            WhitelistEntry whitelistEntry = new WhitelistEntry(gameProfile);
            whiteList.addEntry(whitelistEntry);
            commandSource.sendFeedback(new TranslationTextComponent("commands.whitelist.add.success", TextComponentUtils.getDisplayName(gameProfile)), false);
            ++n;
        }
        if (n == 0) {
            throw PLAYER_ALREADY_WHITELISTED.create();
        }
        return n;
    }

    private static int removePlayers(CommandSource commandSource, Collection<GameProfile> collection) throws CommandSyntaxException {
        WhiteList whiteList = commandSource.getServer().getPlayerList().getWhitelistedPlayers();
        int n = 0;
        for (GameProfile gameProfile : collection) {
            if (!whiteList.isWhitelisted(gameProfile)) continue;
            WhitelistEntry whitelistEntry = new WhitelistEntry(gameProfile);
            whiteList.removeEntry(whitelistEntry);
            commandSource.sendFeedback(new TranslationTextComponent("commands.whitelist.remove.success", TextComponentUtils.getDisplayName(gameProfile)), false);
            ++n;
        }
        if (n == 0) {
            throw PLAYER_NOT_WHITELISTED.create();
        }
        commandSource.getServer().kickPlayersNotWhitelisted(commandSource);
        return n;
    }

    private static int enableWhiteList(CommandSource commandSource) throws CommandSyntaxException {
        PlayerList playerList = commandSource.getServer().getPlayerList();
        if (playerList.isWhiteListEnabled()) {
            throw ALREADY_ON.create();
        }
        playerList.setWhiteListEnabled(false);
        commandSource.sendFeedback(new TranslationTextComponent("commands.whitelist.enabled"), false);
        commandSource.getServer().kickPlayersNotWhitelisted(commandSource);
        return 0;
    }

    private static int disableWhiteList(CommandSource commandSource) throws CommandSyntaxException {
        PlayerList playerList = commandSource.getServer().getPlayerList();
        if (!playerList.isWhiteListEnabled()) {
            throw ALREADY_OFF.create();
        }
        playerList.setWhiteListEnabled(true);
        commandSource.sendFeedback(new TranslationTextComponent("commands.whitelist.disabled"), false);
        return 0;
    }

    private static int listWhitelistedPlayers(CommandSource commandSource) {
        CharSequence[] charSequenceArray = commandSource.getServer().getPlayerList().getWhitelistedPlayerNames();
        if (charSequenceArray.length == 0) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.whitelist.none"), true);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.whitelist.list", charSequenceArray.length, String.join((CharSequence)", ", charSequenceArray)), true);
        }
        return charSequenceArray.length;
    }

    private static int lambda$register$10(CommandContext commandContext) throws CommandSyntaxException {
        return WhitelistCommand.reload((CommandSource)commandContext.getSource());
    }

    private static int lambda$register$9(CommandContext commandContext) throws CommandSyntaxException {
        return WhitelistCommand.removePlayers((CommandSource)commandContext.getSource(), GameProfileArgument.getGameProfiles(commandContext, "targets"));
    }

    private static CompletableFuture lambda$register$8(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        return ISuggestionProvider.suggest(((CommandSource)commandContext.getSource()).getServer().getPlayerList().getWhitelistedPlayerNames(), suggestionsBuilder);
    }

    private static int lambda$register$7(CommandContext commandContext) throws CommandSyntaxException {
        return WhitelistCommand.addPlayers((CommandSource)commandContext.getSource(), GameProfileArgument.getGameProfiles(commandContext, "targets"));
    }

    private static CompletableFuture lambda$register$6(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        PlayerList playerList = ((CommandSource)commandContext.getSource()).getServer().getPlayerList();
        return ISuggestionProvider.suggest(playerList.getPlayers().stream().filter(arg_0 -> WhitelistCommand.lambda$register$4(playerList, arg_0)).map(WhitelistCommand::lambda$register$5), suggestionsBuilder);
    }

    private static String lambda$register$5(ServerPlayerEntity serverPlayerEntity) {
        return serverPlayerEntity.getGameProfile().getName();
    }

    private static boolean lambda$register$4(PlayerList playerList, ServerPlayerEntity serverPlayerEntity) {
        return !playerList.getWhitelistedPlayers().isWhitelisted(serverPlayerEntity.getGameProfile());
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return WhitelistCommand.listWhitelistedPlayers((CommandSource)commandContext.getSource());
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return WhitelistCommand.disableWhiteList((CommandSource)commandContext.getSource());
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        return WhitelistCommand.enableWhiteList((CommandSource)commandContext.getSource());
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(0);
    }
}

