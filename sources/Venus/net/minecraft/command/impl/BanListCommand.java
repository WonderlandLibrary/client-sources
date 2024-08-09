/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.server.management.BanEntry;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.TranslationTextComponent;

public class BanListCommand {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("banlist").requires(BanListCommand::lambda$register$0)).executes(BanListCommand::lambda$register$1)).then(Commands.literal("ips").executes(BanListCommand::lambda$register$2))).then(Commands.literal("players").executes(BanListCommand::lambda$register$3)));
    }

    private static int sendBanList(CommandSource commandSource, Collection<? extends BanEntry<?>> collection) {
        if (collection.isEmpty()) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.banlist.none"), true);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.banlist.list", collection.size()), true);
            for (BanEntry<?> banEntry : collection) {
                commandSource.sendFeedback(new TranslationTextComponent("commands.banlist.entry", banEntry.getDisplayName(), banEntry.getBannedBy(), banEntry.getBanReason()), true);
            }
        }
        return collection.size();
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return BanListCommand.sendBanList((CommandSource)commandContext.getSource(), ((CommandSource)commandContext.getSource()).getServer().getPlayerList().getBannedPlayers().getEntries());
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return BanListCommand.sendBanList((CommandSource)commandContext.getSource(), ((CommandSource)commandContext.getSource()).getServer().getPlayerList().getBannedIPs().getEntries());
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        PlayerList playerList = ((CommandSource)commandContext.getSource()).getServer().getPlayerList();
        return BanListCommand.sendBanList((CommandSource)commandContext.getSource(), Lists.newArrayList(Iterables.concat(playerList.getBannedPlayers().getEntries(), playerList.getBannedIPs().getEntries())));
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(0);
    }
}

