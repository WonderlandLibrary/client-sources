/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;

public class StopCommand {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("stop").requires(StopCommand::lambda$register$0)).executes(StopCommand::lambda$register$1));
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        ((CommandSource)commandContext.getSource()).sendFeedback(new TranslationTextComponent("commands.stop.stopping"), false);
        ((CommandSource)commandContext.getSource()).getServer().initiateShutdown(true);
        return 0;
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }
}

