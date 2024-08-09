/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.command.arguments.SuggestionProviders;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.text.TranslationTextComponent;

public class RecipeCommand {
    private static final SimpleCommandExceptionType GIVE_FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.recipe.give.failed"));
    private static final SimpleCommandExceptionType TAKE_FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.recipe.take.failed"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("recipe").requires(RecipeCommand::lambda$register$0)).then(Commands.literal("give").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("targets", EntityArgument.players()).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("recipe", ResourceLocationArgument.resourceLocation()).suggests(SuggestionProviders.ALL_RECIPES).executes(RecipeCommand::lambda$register$1))).then(Commands.literal("*").executes(RecipeCommand::lambda$register$2))))).then(Commands.literal("take").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("targets", EntityArgument.players()).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("recipe", ResourceLocationArgument.resourceLocation()).suggests(SuggestionProviders.ALL_RECIPES).executes(RecipeCommand::lambda$register$3))).then(Commands.literal("*").executes(RecipeCommand::lambda$register$4)))));
    }

    private static int giveRecipes(CommandSource commandSource, Collection<ServerPlayerEntity> collection, Collection<IRecipe<?>> collection2) throws CommandSyntaxException {
        int n = 0;
        for (ServerPlayerEntity serverPlayerEntity : collection) {
            n += serverPlayerEntity.unlockRecipes(collection2);
        }
        if (n == 0) {
            throw GIVE_FAILED_EXCEPTION.create();
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.recipe.give.success.single", collection2.size(), collection.iterator().next().getDisplayName()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.recipe.give.success.multiple", collection2.size(), collection.size()), false);
        }
        return n;
    }

    private static int takeRecipes(CommandSource commandSource, Collection<ServerPlayerEntity> collection, Collection<IRecipe<?>> collection2) throws CommandSyntaxException {
        int n = 0;
        for (ServerPlayerEntity serverPlayerEntity : collection) {
            n += serverPlayerEntity.resetRecipes(collection2);
        }
        if (n == 0) {
            throw TAKE_FAILED_EXCEPTION.create();
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.recipe.take.success.single", collection2.size(), collection.iterator().next().getDisplayName()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.recipe.take.success.multiple", collection2.size(), collection.size()), false);
        }
        return n;
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return RecipeCommand.takeRecipes((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), ((CommandSource)commandContext.getSource()).getServer().getRecipeManager().getRecipes());
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return RecipeCommand.takeRecipes((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), Collections.singleton(ResourceLocationArgument.getRecipe(commandContext, "recipe")));
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return RecipeCommand.giveRecipes((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), ((CommandSource)commandContext.getSource()).getServer().getRecipeManager().getRecipes());
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        return RecipeCommand.giveRecipes((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), Collections.singleton(ResourceLocationArgument.getRecipe(commandContext, "recipe")));
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }
}

