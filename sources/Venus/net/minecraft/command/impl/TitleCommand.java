/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import java.util.Locale;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ComponentArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.STitlePacket;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TranslationTextComponent;

public class TitleCommand {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("title").requires(TitleCommand::lambda$register$0)).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("targets", EntityArgument.players()).then((ArgumentBuilder<CommandSource, ?>)Commands.literal("clear").executes(TitleCommand::lambda$register$1))).then(Commands.literal("reset").executes(TitleCommand::lambda$register$2))).then(Commands.literal("title").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("title", ComponentArgument.component()).executes(TitleCommand::lambda$register$3)))).then(Commands.literal("subtitle").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("title", ComponentArgument.component()).executes(TitleCommand::lambda$register$4)))).then(Commands.literal("actionbar").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("title", ComponentArgument.component()).executes(TitleCommand::lambda$register$5)))).then(Commands.literal("times").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("fadeIn", IntegerArgumentType.integer(0)).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("stay", IntegerArgumentType.integer(0)).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("fadeOut", IntegerArgumentType.integer(0)).executes(TitleCommand::lambda$register$6)))))));
    }

    private static int clear(CommandSource commandSource, Collection<ServerPlayerEntity> collection) {
        STitlePacket sTitlePacket = new STitlePacket(STitlePacket.Type.CLEAR, null);
        for (ServerPlayerEntity serverPlayerEntity : collection) {
            serverPlayerEntity.connection.sendPacket(sTitlePacket);
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.title.cleared.single", collection.iterator().next().getDisplayName()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.title.cleared.multiple", collection.size()), false);
        }
        return collection.size();
    }

    private static int reset(CommandSource commandSource, Collection<ServerPlayerEntity> collection) {
        STitlePacket sTitlePacket = new STitlePacket(STitlePacket.Type.RESET, null);
        for (ServerPlayerEntity serverPlayerEntity : collection) {
            serverPlayerEntity.connection.sendPacket(sTitlePacket);
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.title.reset.single", collection.iterator().next().getDisplayName()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.title.reset.multiple", collection.size()), false);
        }
        return collection.size();
    }

    private static int show(CommandSource commandSource, Collection<ServerPlayerEntity> collection, ITextComponent iTextComponent, STitlePacket.Type type) throws CommandSyntaxException {
        for (ServerPlayerEntity serverPlayerEntity : collection) {
            serverPlayerEntity.connection.sendPacket(new STitlePacket(type, TextComponentUtils.func_240645_a_(commandSource, iTextComponent, serverPlayerEntity, 0)));
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.title.show." + type.name().toLowerCase(Locale.ROOT) + ".single", collection.iterator().next().getDisplayName()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.title.show." + type.name().toLowerCase(Locale.ROOT) + ".multiple", collection.size()), false);
        }
        return collection.size();
    }

    private static int setTimes(CommandSource commandSource, Collection<ServerPlayerEntity> collection, int n, int n2, int n3) {
        STitlePacket sTitlePacket = new STitlePacket(n, n2, n3);
        for (ServerPlayerEntity serverPlayerEntity : collection) {
            serverPlayerEntity.connection.sendPacket(sTitlePacket);
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.title.times.single", collection.iterator().next().getDisplayName()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.title.times.multiple", collection.size()), false);
        }
        return collection.size();
    }

    private static int lambda$register$6(CommandContext commandContext) throws CommandSyntaxException {
        return TitleCommand.setTimes((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), IntegerArgumentType.getInteger(commandContext, "fadeIn"), IntegerArgumentType.getInteger(commandContext, "stay"), IntegerArgumentType.getInteger(commandContext, "fadeOut"));
    }

    private static int lambda$register$5(CommandContext commandContext) throws CommandSyntaxException {
        return TitleCommand.show((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), ComponentArgument.getComponent(commandContext, "title"), STitlePacket.Type.ACTIONBAR);
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return TitleCommand.show((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), ComponentArgument.getComponent(commandContext, "title"), STitlePacket.Type.SUBTITLE);
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return TitleCommand.show((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), ComponentArgument.getComponent(commandContext, "title"), STitlePacket.Type.TITLE);
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return TitleCommand.reset((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"));
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        return TitleCommand.clear((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"));
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }
}

