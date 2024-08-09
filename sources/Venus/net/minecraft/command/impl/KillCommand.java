/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.TranslationTextComponent;

public class KillCommand {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("kill").requires(KillCommand::lambda$register$0)).executes(KillCommand::lambda$register$1)).then(Commands.argument("targets", EntityArgument.entities()).executes(KillCommand::lambda$register$2)));
    }

    private static int killEntities(CommandSource commandSource, Collection<? extends Entity> collection) {
        for (Entity entity2 : collection) {
            entity2.onKillCommand();
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.kill.success.single", collection.iterator().next().getDisplayName()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.kill.success.multiple", collection.size()), false);
        }
        return collection.size();
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return KillCommand.killEntities((CommandSource)commandContext.getSource(), EntityArgument.getEntities(commandContext, "targets"));
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        return KillCommand.killEntities((CommandSource)commandContext.getSource(), ImmutableList.of(((CommandSource)commandContext.getSource()).assertIsEntity()));
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }
}

