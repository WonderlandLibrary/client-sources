/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.google.common.collect.Sets;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TranslationTextComponent;

public class TagCommand {
    private static final SimpleCommandExceptionType ADD_FAILED = new SimpleCommandExceptionType(new TranslationTextComponent("commands.tag.add.failed"));
    private static final SimpleCommandExceptionType REMOVE_FAILED = new SimpleCommandExceptionType(new TranslationTextComponent("commands.tag.remove.failed"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("tag").requires(TagCommand::lambda$register$0)).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("targets", EntityArgument.entities()).then((ArgumentBuilder<CommandSource, ?>)Commands.literal("add").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("name", StringArgumentType.word()).executes(TagCommand::lambda$register$1)))).then(Commands.literal("remove").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("name", StringArgumentType.word()).suggests(TagCommand::lambda$register$2).executes(TagCommand::lambda$register$3)))).then(Commands.literal("list").executes(TagCommand::lambda$register$4))));
    }

    private static Collection<String> getAllTags(Collection<? extends Entity> collection) {
        HashSet<String> hashSet = Sets.newHashSet();
        for (Entity entity2 : collection) {
            hashSet.addAll(entity2.getTags());
        }
        return hashSet;
    }

    private static int addTag(CommandSource commandSource, Collection<? extends Entity> collection, String string) throws CommandSyntaxException {
        int n = 0;
        for (Entity entity2 : collection) {
            if (!entity2.addTag(string)) continue;
            ++n;
        }
        if (n == 0) {
            throw ADD_FAILED.create();
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.tag.add.success.single", string, collection.iterator().next().getDisplayName()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.tag.add.success.multiple", string, collection.size()), false);
        }
        return n;
    }

    private static int removeTag(CommandSource commandSource, Collection<? extends Entity> collection, String string) throws CommandSyntaxException {
        int n = 0;
        for (Entity entity2 : collection) {
            if (!entity2.removeTag(string)) continue;
            ++n;
        }
        if (n == 0) {
            throw REMOVE_FAILED.create();
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.tag.remove.success.single", string, collection.iterator().next().getDisplayName()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.tag.remove.success.multiple", string, collection.size()), false);
        }
        return n;
    }

    private static int listTags(CommandSource commandSource, Collection<? extends Entity> collection) {
        HashSet<String> hashSet = Sets.newHashSet();
        for (Entity entity2 : collection) {
            hashSet.addAll(entity2.getTags());
        }
        if (collection.size() == 1) {
            Entity entity3 = collection.iterator().next();
            if (hashSet.isEmpty()) {
                commandSource.sendFeedback(new TranslationTextComponent("commands.tag.list.single.empty", entity3.getDisplayName()), true);
            } else {
                commandSource.sendFeedback(new TranslationTextComponent("commands.tag.list.single.success", entity3.getDisplayName(), hashSet.size(), TextComponentUtils.makeGreenSortedList(hashSet)), true);
            }
        } else if (hashSet.isEmpty()) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.tag.list.multiple.empty", collection.size()), true);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.tag.list.multiple.success", collection.size(), hashSet.size(), TextComponentUtils.makeGreenSortedList(hashSet)), true);
        }
        return hashSet.size();
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return TagCommand.listTags((CommandSource)commandContext.getSource(), EntityArgument.getEntities(commandContext, "targets"));
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return TagCommand.removeTag((CommandSource)commandContext.getSource(), EntityArgument.getEntities(commandContext, "targets"), StringArgumentType.getString(commandContext, "name"));
    }

    private static CompletableFuture lambda$register$2(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        return ISuggestionProvider.suggest(TagCommand.getAllTags(EntityArgument.getEntities(commandContext, "targets")), suggestionsBuilder);
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        return TagCommand.addTag((CommandSource)commandContext.getSource(), EntityArgument.getEntities(commandContext, "targets"), StringArgumentType.getString(commandContext, "name"));
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }
}

