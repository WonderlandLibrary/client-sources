/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class KickCommand {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("kick").requires(KickCommand::lambda$register$0)).then(((RequiredArgumentBuilder)Commands.argument("targets", EntityArgument.players()).executes(KickCommand::lambda$register$1)).then(Commands.argument("reason", MessageArgument.message()).executes(KickCommand::lambda$register$2))));
    }

    private static int kickPlayers(CommandSource commandSource, Collection<ServerPlayerEntity> collection, ITextComponent iTextComponent) {
        for (ServerPlayerEntity serverPlayerEntity : collection) {
            serverPlayerEntity.connection.disconnect(iTextComponent);
            commandSource.sendFeedback(new TranslationTextComponent("commands.kick.success", serverPlayerEntity.getDisplayName(), iTextComponent), false);
        }
        return collection.size();
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return KickCommand.kickPlayers((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), MessageArgument.getMessage(commandContext, "reason"));
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        return KickCommand.kickPlayers((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), new TranslationTextComponent("multiplayer.disconnect.kicked"));
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(0);
    }
}

