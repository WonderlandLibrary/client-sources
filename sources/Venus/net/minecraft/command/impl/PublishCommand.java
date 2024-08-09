/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.HTTPUtil;
import net.minecraft.util.text.TranslationTextComponent;

public class PublishCommand {
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.publish.failed"));
    private static final DynamicCommandExceptionType ALREADY_PUBLISHED_EXCEPTION = new DynamicCommandExceptionType(PublishCommand::lambda$static$0);

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("publish").requires(PublishCommand::lambda$register$1)).executes(PublishCommand::lambda$register$2)).then(Commands.argument("port", IntegerArgumentType.integer(0, 65535)).executes(PublishCommand::lambda$register$3)));
    }

    private static int publish(CommandSource commandSource, int n) throws CommandSyntaxException {
        if (commandSource.getServer().getPublic()) {
            throw ALREADY_PUBLISHED_EXCEPTION.create(commandSource.getServer().getServerPort());
        }
        if (!commandSource.getServer().shareToLAN(commandSource.getServer().getGameType(), false, n)) {
            throw FAILED_EXCEPTION.create();
        }
        commandSource.sendFeedback(new TranslationTextComponent("commands.publish.success", n), false);
        return n;
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return PublishCommand.publish((CommandSource)commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "port"));
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return PublishCommand.publish((CommandSource)commandContext.getSource(), HTTPUtil.getSuitableLanPort());
    }

    private static boolean lambda$register$1(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("commands.publish.alreadyPublished", object);
    }
}

