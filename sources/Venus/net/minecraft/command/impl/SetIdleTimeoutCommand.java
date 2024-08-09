/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;

public class SetIdleTimeoutCommand {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("setidletimeout").requires(SetIdleTimeoutCommand::lambda$register$0)).then(Commands.argument("minutes", IntegerArgumentType.integer(0)).executes(SetIdleTimeoutCommand::lambda$register$1)));
    }

    private static int setTimeout(CommandSource commandSource, int n) {
        commandSource.getServer().setPlayerIdleTimeout(n);
        commandSource.sendFeedback(new TranslationTextComponent("commands.setidletimeout.success", n), false);
        return n;
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        return SetIdleTimeoutCommand.setTimeout((CommandSource)commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "minutes"));
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(0);
    }
}

