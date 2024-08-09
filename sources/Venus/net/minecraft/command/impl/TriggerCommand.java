/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.ObjectiveArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.util.text.TranslationTextComponent;

public class TriggerCommand {
    private static final SimpleCommandExceptionType NOT_PRIMED = new SimpleCommandExceptionType(new TranslationTextComponent("commands.trigger.failed.unprimed"));
    private static final SimpleCommandExceptionType NOT_A_TRIGGER = new SimpleCommandExceptionType(new TranslationTextComponent("commands.trigger.failed.invalid"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)Commands.literal("trigger").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("objective", ObjectiveArgument.objective()).suggests(TriggerCommand::lambda$register$0).executes(TriggerCommand::lambda$register$1)).then(Commands.literal("add").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("value", IntegerArgumentType.integer()).executes(TriggerCommand::lambda$register$2)))).then(Commands.literal("set").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("value", IntegerArgumentType.integer()).executes(TriggerCommand::lambda$register$3)))));
    }

    public static CompletableFuture<Suggestions> suggestTriggers(CommandSource commandSource, SuggestionsBuilder suggestionsBuilder) {
        Entity entity2 = commandSource.getEntity();
        ArrayList<String> arrayList = Lists.newArrayList();
        if (entity2 != null) {
            ServerScoreboard serverScoreboard = commandSource.getServer().getScoreboard();
            String string = entity2.getScoreboardName();
            for (ScoreObjective scoreObjective : serverScoreboard.getScoreObjectives()) {
                Score score;
                if (scoreObjective.getCriteria() != ScoreCriteria.TRIGGER || !serverScoreboard.entityHasObjective(string, scoreObjective) || (score = serverScoreboard.getOrCreateScore(string, scoreObjective)).isLocked()) continue;
                arrayList.add(scoreObjective.getName());
            }
        }
        return ISuggestionProvider.suggest(arrayList, suggestionsBuilder);
    }

    private static int addToTrigger(CommandSource commandSource, Score score, int n) {
        score.increaseScore(n);
        commandSource.sendFeedback(new TranslationTextComponent("commands.trigger.add.success", score.getObjective().func_197890_e(), n), false);
        return score.getScorePoints();
    }

    private static int setTrigger(CommandSource commandSource, Score score, int n) {
        score.setScorePoints(n);
        commandSource.sendFeedback(new TranslationTextComponent("commands.trigger.set.success", score.getObjective().func_197890_e(), n), false);
        return n;
    }

    private static int incrementTrigger(CommandSource commandSource, Score score) {
        score.increaseScore(1);
        commandSource.sendFeedback(new TranslationTextComponent("commands.trigger.simple.success", score.getObjective().func_197890_e()), false);
        return score.getScorePoints();
    }

    private static Score checkValidTrigger(ServerPlayerEntity serverPlayerEntity, ScoreObjective scoreObjective) throws CommandSyntaxException {
        String string;
        if (scoreObjective.getCriteria() != ScoreCriteria.TRIGGER) {
            throw NOT_A_TRIGGER.create();
        }
        Scoreboard scoreboard = serverPlayerEntity.getWorldScoreboard();
        if (!scoreboard.entityHasObjective(string = serverPlayerEntity.getScoreboardName(), scoreObjective)) {
            throw NOT_PRIMED.create();
        }
        Score score = scoreboard.getOrCreateScore(string, scoreObjective);
        if (score.isLocked()) {
            throw NOT_PRIMED.create();
        }
        score.setLocked(false);
        return score;
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return TriggerCommand.setTrigger((CommandSource)commandContext.getSource(), TriggerCommand.checkValidTrigger(((CommandSource)commandContext.getSource()).asPlayer(), ObjectiveArgument.getObjective(commandContext, "objective")), IntegerArgumentType.getInteger(commandContext, "value"));
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return TriggerCommand.addToTrigger((CommandSource)commandContext.getSource(), TriggerCommand.checkValidTrigger(((CommandSource)commandContext.getSource()).asPlayer(), ObjectiveArgument.getObjective(commandContext, "objective")), IntegerArgumentType.getInteger(commandContext, "value"));
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        return TriggerCommand.incrementTrigger((CommandSource)commandContext.getSource(), TriggerCommand.checkValidTrigger(((CommandSource)commandContext.getSource()).asPlayer(), ObjectiveArgument.getObjective(commandContext, "objective")));
    }

    private static CompletableFuture lambda$register$0(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        return TriggerCommand.suggestTriggers((CommandSource)commandContext.getSource(), suggestionsBuilder);
    }
}

