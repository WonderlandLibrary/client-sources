/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ComponentArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.TextComponentUtils;

public class TellRawCommand {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("tellraw").requires(TellRawCommand::lambda$register$0)).then(Commands.argument("targets", EntityArgument.players()).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("message", ComponentArgument.component()).executes(TellRawCommand::lambda$register$1))));
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        int n = 0;
        for (ServerPlayerEntity serverPlayerEntity : EntityArgument.getPlayers(commandContext, "targets")) {
            serverPlayerEntity.sendMessage(TextComponentUtils.func_240645_a_((CommandSource)commandContext.getSource(), ComponentArgument.getComponent(commandContext, "message"), serverPlayerEntity, 0), Util.DUMMY_UUID);
            ++n;
        }
        return n;
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }
}

