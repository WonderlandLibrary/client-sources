/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class MessageCommand {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        LiteralCommandNode<CommandSource> literalCommandNode = commandDispatcher.register((LiteralArgumentBuilder)Commands.literal("msg").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("targets", EntityArgument.players()).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("message", MessageArgument.message()).executes(MessageCommand::lambda$register$0))));
        commandDispatcher.register((LiteralArgumentBuilder)Commands.literal("tell").redirect(literalCommandNode));
        commandDispatcher.register((LiteralArgumentBuilder)Commands.literal("w").redirect(literalCommandNode));
    }

    private static int sendPrivateMessage(CommandSource commandSource, Collection<ServerPlayerEntity> collection, ITextComponent iTextComponent) {
        Consumer<ITextComponent> consumer;
        UUID uUID = commandSource.getEntity() == null ? Util.DUMMY_UUID : commandSource.getEntity().getUniqueID();
        Entity entity2 = commandSource.getEntity();
        if (entity2 instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity2;
            consumer = arg_0 -> MessageCommand.lambda$sendPrivateMessage$1(serverPlayerEntity, iTextComponent, arg_0);
        } else {
            consumer = arg_0 -> MessageCommand.lambda$sendPrivateMessage$2(commandSource, iTextComponent, arg_0);
        }
        for (ServerPlayerEntity serverPlayerEntity : collection) {
            consumer.accept(serverPlayerEntity.getDisplayName());
            serverPlayerEntity.sendMessage(new TranslationTextComponent("commands.message.display.incoming", commandSource.getDisplayName(), iTextComponent).mergeStyle(TextFormatting.GRAY, TextFormatting.ITALIC), uUID);
        }
        return collection.size();
    }

    private static void lambda$sendPrivateMessage$2(CommandSource commandSource, ITextComponent iTextComponent, ITextComponent iTextComponent2) {
        commandSource.sendFeedback(new TranslationTextComponent("commands.message.display.outgoing", iTextComponent2, iTextComponent).mergeStyle(TextFormatting.GRAY, TextFormatting.ITALIC), true);
    }

    private static void lambda$sendPrivateMessage$1(ServerPlayerEntity serverPlayerEntity, ITextComponent iTextComponent, ITextComponent iTextComponent2) {
        serverPlayerEntity.sendMessage(new TranslationTextComponent("commands.message.display.outgoing", iTextComponent2, iTextComponent).mergeStyle(TextFormatting.GRAY, TextFormatting.ITALIC), serverPlayerEntity.getUniqueID());
    }

    private static int lambda$register$0(CommandContext commandContext) throws CommandSyntaxException {
        return MessageCommand.sendPrivateMessage((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), MessageArgument.getMessage(commandContext, "message"));
    }
}

