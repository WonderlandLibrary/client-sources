/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Date;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.GameProfileArgument;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.management.BanList;
import net.minecraft.server.management.ProfileBanEntry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TranslationTextComponent;

public class BanCommand {
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.ban.failed"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("ban").requires(BanCommand::lambda$register$0)).then(((RequiredArgumentBuilder)Commands.argument("targets", GameProfileArgument.gameProfile()).executes(BanCommand::lambda$register$1)).then(Commands.argument("reason", MessageArgument.message()).executes(BanCommand::lambda$register$2))));
    }

    private static int banGameProfiles(CommandSource commandSource, Collection<GameProfile> collection, @Nullable ITextComponent iTextComponent) throws CommandSyntaxException {
        BanList banList = commandSource.getServer().getPlayerList().getBannedPlayers();
        int n = 0;
        for (GameProfile gameProfile : collection) {
            if (banList.isBanned(gameProfile)) continue;
            ProfileBanEntry profileBanEntry = new ProfileBanEntry(gameProfile, (Date)null, commandSource.getName(), (Date)null, iTextComponent == null ? null : iTextComponent.getString());
            banList.addEntry(profileBanEntry);
            ++n;
            commandSource.sendFeedback(new TranslationTextComponent("commands.ban.success", TextComponentUtils.getDisplayName(gameProfile), profileBanEntry.getBanReason()), false);
            ServerPlayerEntity serverPlayerEntity = commandSource.getServer().getPlayerList().getPlayerByUUID(gameProfile.getId());
            if (serverPlayerEntity == null) continue;
            serverPlayerEntity.connection.disconnect(new TranslationTextComponent("multiplayer.disconnect.banned"));
        }
        if (n == 0) {
            throw FAILED_EXCEPTION.create();
        }
        return n;
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return BanCommand.banGameProfiles((CommandSource)commandContext.getSource(), GameProfileArgument.getGameProfiles(commandContext, "targets"), MessageArgument.getMessage(commandContext, "reason"));
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        return BanCommand.banGameProfiles((CommandSource)commandContext.getSource(), GameProfileArgument.getGameProfiles(commandContext, "targets"), null);
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(0);
    }
}

