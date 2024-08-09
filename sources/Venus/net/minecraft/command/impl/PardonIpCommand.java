/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.impl.BanIpCommand;
import net.minecraft.server.management.IPBanList;
import net.minecraft.util.text.TranslationTextComponent;

public class PardonIpCommand {
    private static final SimpleCommandExceptionType IP_INVALID_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.pardonip.invalid"));
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.pardonip.failed"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("pardon-ip").requires(PardonIpCommand::lambda$register$0)).then(Commands.argument("target", StringArgumentType.word()).suggests(PardonIpCommand::lambda$register$1).executes(PardonIpCommand::lambda$register$2)));
    }

    private static int unbanIp(CommandSource commandSource, String string) throws CommandSyntaxException {
        Matcher matcher = BanIpCommand.IP_PATTERN.matcher(string);
        if (!matcher.matches()) {
            throw IP_INVALID_EXCEPTION.create();
        }
        IPBanList iPBanList = commandSource.getServer().getPlayerList().getBannedIPs();
        if (!iPBanList.isBanned(string)) {
            throw FAILED_EXCEPTION.create();
        }
        iPBanList.removeEntry(string);
        commandSource.sendFeedback(new TranslationTextComponent("commands.pardonip.success", string), false);
        return 0;
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return PardonIpCommand.unbanIp((CommandSource)commandContext.getSource(), StringArgumentType.getString(commandContext, "target"));
    }

    private static CompletableFuture lambda$register$1(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        return ISuggestionProvider.suggest(((CommandSource)commandContext.getSource()).getServer().getPlayerList().getBannedIPs().getKeys(), suggestionsBuilder);
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(0);
    }
}

