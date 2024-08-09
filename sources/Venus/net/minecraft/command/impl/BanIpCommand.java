/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.management.IPBanEntry;
import net.minecraft.server.management.IPBanList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class BanIpCommand {
    public static final Pattern IP_PATTERN = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    private static final SimpleCommandExceptionType IP_INVALID = new SimpleCommandExceptionType(new TranslationTextComponent("commands.banip.invalid"));
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.banip.failed"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("ban-ip").requires(BanIpCommand::lambda$register$0)).then(((RequiredArgumentBuilder)Commands.argument("target", StringArgumentType.word()).executes(BanIpCommand::lambda$register$1)).then(Commands.argument("reason", MessageArgument.message()).executes(BanIpCommand::lambda$register$2))));
    }

    private static int banUsernameOrIp(CommandSource commandSource, String string, @Nullable ITextComponent iTextComponent) throws CommandSyntaxException {
        Matcher matcher = IP_PATTERN.matcher(string);
        if (matcher.matches()) {
            return BanIpCommand.banIpAddress(commandSource, string, iTextComponent);
        }
        ServerPlayerEntity serverPlayerEntity = commandSource.getServer().getPlayerList().getPlayerByUsername(string);
        if (serverPlayerEntity != null) {
            return BanIpCommand.banIpAddress(commandSource, serverPlayerEntity.getPlayerIP(), iTextComponent);
        }
        throw IP_INVALID.create();
    }

    private static int banIpAddress(CommandSource commandSource, String string, @Nullable ITextComponent iTextComponent) throws CommandSyntaxException {
        IPBanList iPBanList = commandSource.getServer().getPlayerList().getBannedIPs();
        if (iPBanList.isBanned(string)) {
            throw FAILED_EXCEPTION.create();
        }
        List<ServerPlayerEntity> list = commandSource.getServer().getPlayerList().getPlayersMatchingAddress(string);
        IPBanEntry iPBanEntry = new IPBanEntry(string, (Date)null, commandSource.getName(), (Date)null, iTextComponent == null ? null : iTextComponent.getString());
        iPBanList.addEntry(iPBanEntry);
        commandSource.sendFeedback(new TranslationTextComponent("commands.banip.success", string, iPBanEntry.getBanReason()), false);
        if (!list.isEmpty()) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.banip.info", list.size(), EntitySelector.joinNames(list)), false);
        }
        for (ServerPlayerEntity serverPlayerEntity : list) {
            serverPlayerEntity.connection.disconnect(new TranslationTextComponent("multiplayer.disconnect.ip_banned"));
        }
        return list.size();
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return BanIpCommand.banUsernameOrIp((CommandSource)commandContext.getSource(), StringArgumentType.getString(commandContext, "target"), MessageArgument.getMessage(commandContext, "reason"));
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        return BanIpCommand.banUsernameOrIp((CommandSource)commandContext.getSource(), StringArgumentType.getString(commandContext, "target"), null);
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(0);
    }
}

