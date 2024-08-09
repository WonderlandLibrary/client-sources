/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import java.util.function.Function;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TranslationTextComponent;

public class ListCommand {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("list").executes(ListCommand::lambda$register$0)).then(Commands.literal("uuids").executes(ListCommand::lambda$register$1)));
    }

    private static int listNames(CommandSource commandSource) {
        return ListCommand.listPlayers(commandSource, PlayerEntity::getDisplayName);
    }

    private static int listUUIDs(CommandSource commandSource) {
        return ListCommand.listPlayers(commandSource, ListCommand::lambda$listUUIDs$2);
    }

    private static int listPlayers(CommandSource commandSource, Function<ServerPlayerEntity, ITextComponent> function) {
        PlayerList playerList = commandSource.getServer().getPlayerList();
        List<ServerPlayerEntity> list = playerList.getPlayers();
        IFormattableTextComponent iFormattableTextComponent = TextComponentUtils.func_240649_b_(list, function);
        commandSource.sendFeedback(new TranslationTextComponent("commands.list.players", list.size(), playerList.getMaxPlayers(), iFormattableTextComponent), true);
        return list.size();
    }

    private static ITextComponent lambda$listUUIDs$2(ServerPlayerEntity serverPlayerEntity) {
        return new TranslationTextComponent("commands.list.nameAndId", serverPlayerEntity.getName(), serverPlayerEntity.getGameProfile().getId());
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        return ListCommand.listUUIDs((CommandSource)commandContext.getSource());
    }

    private static int lambda$register$0(CommandContext commandContext) throws CommandSyntaxException {
        return ListCommand.listNames((CommandSource)commandContext.getSource());
    }
}

