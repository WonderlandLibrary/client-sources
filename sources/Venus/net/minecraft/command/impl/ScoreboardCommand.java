/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.ComponentArgument;
import net.minecraft.command.arguments.ObjectiveArgument;
import net.minecraft.command.arguments.ObjectiveCriteriaArgument;
import net.minecraft.command.arguments.OperationArgument;
import net.minecraft.command.arguments.ScoreHolderArgument;
import net.minecraft.command.arguments.ScoreboardSlotArgument;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TranslationTextComponent;

public class ScoreboardCommand {
    private static final SimpleCommandExceptionType OBJECTIVE_ALREADY_EXISTS_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.scoreboard.objectives.add.duplicate"));
    private static final SimpleCommandExceptionType DISPLAY_ALREADY_CLEAR_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.scoreboard.objectives.display.alreadyEmpty"));
    private static final SimpleCommandExceptionType DISPLAY_ALREADY_SET_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.scoreboard.objectives.display.alreadySet"));
    private static final SimpleCommandExceptionType ENABLE_TRIGGER_FAILED = new SimpleCommandExceptionType(new TranslationTextComponent("commands.scoreboard.players.enable.failed"));
    private static final SimpleCommandExceptionType ENABLE_TRIGGER_INVALID = new SimpleCommandExceptionType(new TranslationTextComponent("commands.scoreboard.players.enable.invalid"));
    private static final Dynamic2CommandExceptionType SCOREBOARD_PLAYER_NOT_FOUND_EXCEPTION = new Dynamic2CommandExceptionType(ScoreboardCommand::lambda$static$0);

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("scoreboard").requires(ScoreboardCommand::lambda$register$1)).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("objectives").then((ArgumentBuilder<CommandSource, ?>)Commands.literal("list").executes(ScoreboardCommand::lambda$register$2))).then(Commands.literal("add").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("objective", StringArgumentType.word()).then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("criteria", ObjectiveCriteriaArgument.objectiveCriteria()).executes(ScoreboardCommand::lambda$register$3)).then(Commands.argument("displayName", ComponentArgument.component()).executes(ScoreboardCommand::lambda$register$4)))))).then(Commands.literal("modify").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("objective", ObjectiveArgument.objective()).then((ArgumentBuilder<CommandSource, ?>)Commands.literal("displayname").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("displayName", ComponentArgument.component()).executes(ScoreboardCommand::lambda$register$5)))).then(ScoreboardCommand.createRenderTypeArgument())))).then(Commands.literal("remove").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("objective", ObjectiveArgument.objective()).executes(ScoreboardCommand::lambda$register$6)))).then(Commands.literal("setdisplay").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("slot", ScoreboardSlotArgument.scoreboardSlot()).executes(ScoreboardCommand::lambda$register$7)).then(Commands.argument("objective", ObjectiveArgument.objective()).executes(ScoreboardCommand::lambda$register$8)))))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("players").then((ArgumentBuilder<CommandSource, ?>)((LiteralArgumentBuilder)Commands.literal("list").executes(ScoreboardCommand::lambda$register$9)).then(Commands.argument("target", ScoreHolderArgument.scoreHolder()).suggests(ScoreHolderArgument.SUGGEST_ENTITY_SELECTOR).executes(ScoreboardCommand::lambda$register$10)))).then(Commands.literal("set").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("targets", ScoreHolderArgument.scoreHolders()).suggests(ScoreHolderArgument.SUGGEST_ENTITY_SELECTOR).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("objective", ObjectiveArgument.objective()).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("score", IntegerArgumentType.integer()).executes(ScoreboardCommand::lambda$register$11)))))).then(Commands.literal("get").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("target", ScoreHolderArgument.scoreHolder()).suggests(ScoreHolderArgument.SUGGEST_ENTITY_SELECTOR).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("objective", ObjectiveArgument.objective()).executes(ScoreboardCommand::lambda$register$12))))).then(Commands.literal("add").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("targets", ScoreHolderArgument.scoreHolders()).suggests(ScoreHolderArgument.SUGGEST_ENTITY_SELECTOR).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("objective", ObjectiveArgument.objective()).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("score", IntegerArgumentType.integer(0)).executes(ScoreboardCommand::lambda$register$13)))))).then(Commands.literal("remove").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("targets", ScoreHolderArgument.scoreHolders()).suggests(ScoreHolderArgument.SUGGEST_ENTITY_SELECTOR).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("objective", ObjectiveArgument.objective()).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("score", IntegerArgumentType.integer(0)).executes(ScoreboardCommand::lambda$register$14)))))).then(Commands.literal("reset").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("targets", ScoreHolderArgument.scoreHolders()).suggests(ScoreHolderArgument.SUGGEST_ENTITY_SELECTOR).executes(ScoreboardCommand::lambda$register$15)).then(Commands.argument("objective", ObjectiveArgument.objective()).executes(ScoreboardCommand::lambda$register$16))))).then(Commands.literal("enable").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("targets", ScoreHolderArgument.scoreHolders()).suggests(ScoreHolderArgument.SUGGEST_ENTITY_SELECTOR).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("objective", ObjectiveArgument.objective()).suggests(ScoreboardCommand::lambda$register$17).executes(ScoreboardCommand::lambda$register$18))))).then(Commands.literal("operation").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("targets", ScoreHolderArgument.scoreHolders()).suggests(ScoreHolderArgument.SUGGEST_ENTITY_SELECTOR).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("targetObjective", ObjectiveArgument.objective()).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("operation", OperationArgument.operation()).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("source", ScoreHolderArgument.scoreHolders()).suggests(ScoreHolderArgument.SUGGEST_ENTITY_SELECTOR).then((ArgumentBuilder<CommandSource, ?>)Commands.argument("sourceObjective", ObjectiveArgument.objective()).executes(ScoreboardCommand::lambda$register$19)))))))));
    }

    private static LiteralArgumentBuilder<CommandSource> createRenderTypeArgument() {
        LiteralArgumentBuilder<CommandSource> literalArgumentBuilder = Commands.literal("rendertype");
        for (ScoreCriteria.RenderType renderType : ScoreCriteria.RenderType.values()) {
            literalArgumentBuilder.then((ArgumentBuilder<CommandSource, ?>)Commands.literal(renderType.getId()).executes(arg_0 -> ScoreboardCommand.lambda$createRenderTypeArgument$20(renderType, arg_0)));
        }
        return literalArgumentBuilder;
    }

    private static CompletableFuture<Suggestions> suggestTriggers(CommandSource commandSource, Collection<String> collection, SuggestionsBuilder suggestionsBuilder) {
        ArrayList<String> arrayList = Lists.newArrayList();
        ServerScoreboard serverScoreboard = commandSource.getServer().getScoreboard();
        for (ScoreObjective scoreObjective : serverScoreboard.getScoreObjectives()) {
            if (scoreObjective.getCriteria() != ScoreCriteria.TRIGGER) continue;
            boolean bl = false;
            for (String string : collection) {
                if (serverScoreboard.entityHasObjective(string, scoreObjective) && !serverScoreboard.getOrCreateScore(string, scoreObjective).isLocked()) continue;
                bl = true;
                break;
            }
            if (!bl) continue;
            arrayList.add(scoreObjective.getName());
        }
        return ISuggestionProvider.suggest(arrayList, suggestionsBuilder);
    }

    private static int getPlayerScore(CommandSource commandSource, String string, ScoreObjective scoreObjective) throws CommandSyntaxException {
        ServerScoreboard serverScoreboard = commandSource.getServer().getScoreboard();
        if (!serverScoreboard.entityHasObjective(string, scoreObjective)) {
            throw SCOREBOARD_PLAYER_NOT_FOUND_EXCEPTION.create(scoreObjective.getName(), string);
        }
        Score score = serverScoreboard.getOrCreateScore(string, scoreObjective);
        commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.players.get.success", string, score.getScorePoints(), scoreObjective.func_197890_e()), true);
        return score.getScorePoints();
    }

    private static int applyScoreOperation(CommandSource commandSource, Collection<String> collection, ScoreObjective scoreObjective, OperationArgument.IOperation iOperation, Collection<String> collection2, ScoreObjective scoreObjective2) throws CommandSyntaxException {
        ServerScoreboard serverScoreboard = commandSource.getServer().getScoreboard();
        int n = 0;
        for (String string : collection) {
            Score score = serverScoreboard.getOrCreateScore(string, scoreObjective);
            for (String string2 : collection2) {
                Score score2 = serverScoreboard.getOrCreateScore(string2, scoreObjective2);
                iOperation.apply(score, score2);
            }
            n += score.getScorePoints();
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.players.operation.success.single", scoreObjective.func_197890_e(), collection.iterator().next(), n), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.players.operation.success.multiple", scoreObjective.func_197890_e(), collection.size()), false);
        }
        return n;
    }

    private static int enableTrigger(CommandSource commandSource, Collection<String> collection, ScoreObjective scoreObjective) throws CommandSyntaxException {
        if (scoreObjective.getCriteria() != ScoreCriteria.TRIGGER) {
            throw ENABLE_TRIGGER_INVALID.create();
        }
        ServerScoreboard serverScoreboard = commandSource.getServer().getScoreboard();
        int n = 0;
        for (String string : collection) {
            Score score = serverScoreboard.getOrCreateScore(string, scoreObjective);
            if (!score.isLocked()) continue;
            score.setLocked(true);
            ++n;
        }
        if (n == 0) {
            throw ENABLE_TRIGGER_FAILED.create();
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.players.enable.success.single", scoreObjective.func_197890_e(), collection.iterator().next()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.players.enable.success.multiple", scoreObjective.func_197890_e(), collection.size()), false);
        }
        return n;
    }

    private static int resetPlayerAllScores(CommandSource commandSource, Collection<String> collection) {
        ServerScoreboard serverScoreboard = commandSource.getServer().getScoreboard();
        for (String string : collection) {
            serverScoreboard.removeObjectiveFromEntity(string, null);
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.players.reset.all.single", collection.iterator().next()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.players.reset.all.multiple", collection.size()), false);
        }
        return collection.size();
    }

    private static int resetPlayerScore(CommandSource commandSource, Collection<String> collection, ScoreObjective scoreObjective) {
        ServerScoreboard serverScoreboard = commandSource.getServer().getScoreboard();
        for (String string : collection) {
            serverScoreboard.removeObjectiveFromEntity(string, scoreObjective);
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.players.reset.specific.single", scoreObjective.func_197890_e(), collection.iterator().next()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.players.reset.specific.multiple", scoreObjective.func_197890_e(), collection.size()), false);
        }
        return collection.size();
    }

    private static int setPlayerScore(CommandSource commandSource, Collection<String> collection, ScoreObjective scoreObjective, int n) {
        ServerScoreboard serverScoreboard = commandSource.getServer().getScoreboard();
        for (String string : collection) {
            Score score = serverScoreboard.getOrCreateScore(string, scoreObjective);
            score.setScorePoints(n);
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.players.set.success.single", scoreObjective.func_197890_e(), collection.iterator().next(), n), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.players.set.success.multiple", scoreObjective.func_197890_e(), collection.size(), n), false);
        }
        return n * collection.size();
    }

    private static int addToPlayerScore(CommandSource commandSource, Collection<String> collection, ScoreObjective scoreObjective, int n) {
        ServerScoreboard serverScoreboard = commandSource.getServer().getScoreboard();
        int n2 = 0;
        for (String string : collection) {
            Score score = serverScoreboard.getOrCreateScore(string, scoreObjective);
            score.setScorePoints(score.getScorePoints() + n);
            n2 += score.getScorePoints();
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.players.add.success.single", n, scoreObjective.func_197890_e(), collection.iterator().next(), n2), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.players.add.success.multiple", n, scoreObjective.func_197890_e(), collection.size()), false);
        }
        return n2;
    }

    private static int removeFromPlayerScore(CommandSource commandSource, Collection<String> collection, ScoreObjective scoreObjective, int n) {
        ServerScoreboard serverScoreboard = commandSource.getServer().getScoreboard();
        int n2 = 0;
        for (String string : collection) {
            Score score = serverScoreboard.getOrCreateScore(string, scoreObjective);
            score.setScorePoints(score.getScorePoints() - n);
            n2 += score.getScorePoints();
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.players.remove.success.single", n, scoreObjective.func_197890_e(), collection.iterator().next(), n2), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.players.remove.success.multiple", n, scoreObjective.func_197890_e(), collection.size()), false);
        }
        return n2;
    }

    private static int listPlayers(CommandSource commandSource) {
        Collection<String> collection = commandSource.getServer().getScoreboard().getObjectiveNames();
        if (collection.isEmpty()) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.players.list.empty"), true);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.players.list.success", collection.size(), TextComponentUtils.makeGreenSortedList(collection)), true);
        }
        return collection.size();
    }

    private static int listPlayerScores(CommandSource commandSource, String string) {
        Map<ScoreObjective, Score> map = commandSource.getServer().getScoreboard().getObjectivesForEntity(string);
        if (map.isEmpty()) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.players.list.entity.empty", string), true);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.players.list.entity.success", string, map.size()), true);
            for (Map.Entry<ScoreObjective, Score> entry : map.entrySet()) {
                commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.players.list.entity.entry", entry.getKey().func_197890_e(), entry.getValue().getScorePoints()), true);
            }
        }
        return map.size();
    }

    private static int clearObjectiveDisplaySlot(CommandSource commandSource, int n) throws CommandSyntaxException {
        ServerScoreboard serverScoreboard = commandSource.getServer().getScoreboard();
        if (serverScoreboard.getObjectiveInDisplaySlot(n) == null) {
            throw DISPLAY_ALREADY_CLEAR_EXCEPTION.create();
        }
        ((Scoreboard)serverScoreboard).setObjectiveInDisplaySlot(n, null);
        commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.objectives.display.cleared", Scoreboard.getDisplaySlotStrings()[n]), false);
        return 1;
    }

    private static int setObjectiveDisplaySlot(CommandSource commandSource, int n, ScoreObjective scoreObjective) throws CommandSyntaxException {
        ServerScoreboard serverScoreboard = commandSource.getServer().getScoreboard();
        if (serverScoreboard.getObjectiveInDisplaySlot(n) == scoreObjective) {
            throw DISPLAY_ALREADY_SET_EXCEPTION.create();
        }
        ((Scoreboard)serverScoreboard).setObjectiveInDisplaySlot(n, scoreObjective);
        commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.objectives.display.set", Scoreboard.getDisplaySlotStrings()[n], scoreObjective.getDisplayName()), false);
        return 1;
    }

    private static int setDisplayName(CommandSource commandSource, ScoreObjective scoreObjective, ITextComponent iTextComponent) {
        if (!scoreObjective.getDisplayName().equals(iTextComponent)) {
            scoreObjective.setDisplayName(iTextComponent);
            commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.objectives.modify.displayname", scoreObjective.getName(), scoreObjective.func_197890_e()), false);
        }
        return 1;
    }

    private static int setRenderType(CommandSource commandSource, ScoreObjective scoreObjective, ScoreCriteria.RenderType renderType) {
        if (scoreObjective.getRenderType() != renderType) {
            scoreObjective.setRenderType(renderType);
            commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.objectives.modify.rendertype", scoreObjective.func_197890_e()), false);
        }
        return 1;
    }

    private static int removeObjective(CommandSource commandSource, ScoreObjective scoreObjective) {
        ServerScoreboard serverScoreboard = commandSource.getServer().getScoreboard();
        serverScoreboard.removeObjective(scoreObjective);
        commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.objectives.remove.success", scoreObjective.func_197890_e()), false);
        return serverScoreboard.getScoreObjectives().size();
    }

    private static int addObjective(CommandSource commandSource, String string, ScoreCriteria scoreCriteria, ITextComponent iTextComponent) throws CommandSyntaxException {
        ServerScoreboard serverScoreboard = commandSource.getServer().getScoreboard();
        if (serverScoreboard.getObjective(string) != null) {
            throw OBJECTIVE_ALREADY_EXISTS_EXCEPTION.create();
        }
        if (string.length() > 16) {
            throw ObjectiveArgument.OBJECTIVE_NAME_TOO_LONG.create(16);
        }
        serverScoreboard.addObjective(string, scoreCriteria, iTextComponent, scoreCriteria.getRenderType());
        ScoreObjective scoreObjective = serverScoreboard.getObjective(string);
        commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.objectives.add.success", scoreObjective.func_197890_e()), false);
        return serverScoreboard.getScoreObjectives().size();
    }

    private static int listObjectives(CommandSource commandSource) {
        Collection<ScoreObjective> collection = commandSource.getServer().getScoreboard().getScoreObjectives();
        if (collection.isEmpty()) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.objectives.list.empty"), true);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.scoreboard.objectives.list.success", collection.size(), TextComponentUtils.func_240649_b_(collection, ScoreObjective::func_197890_e)), true);
        }
        return collection.size();
    }

    private static int lambda$createRenderTypeArgument$20(ScoreCriteria.RenderType renderType, CommandContext commandContext) throws CommandSyntaxException {
        return ScoreboardCommand.setRenderType((CommandSource)commandContext.getSource(), ObjectiveArgument.getObjective(commandContext, "objective"), renderType);
    }

    private static int lambda$register$19(CommandContext commandContext) throws CommandSyntaxException {
        return ScoreboardCommand.applyScoreOperation((CommandSource)commandContext.getSource(), ScoreHolderArgument.getScoreHolder(commandContext, "targets"), ObjectiveArgument.getWritableObjective(commandContext, "targetObjective"), OperationArgument.getOperation(commandContext, "operation"), ScoreHolderArgument.getScoreHolder(commandContext, "source"), ObjectiveArgument.getObjective(commandContext, "sourceObjective"));
    }

    private static int lambda$register$18(CommandContext commandContext) throws CommandSyntaxException {
        return ScoreboardCommand.enableTrigger((CommandSource)commandContext.getSource(), ScoreHolderArgument.getScoreHolder(commandContext, "targets"), ObjectiveArgument.getObjective(commandContext, "objective"));
    }

    private static CompletableFuture lambda$register$17(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        return ScoreboardCommand.suggestTriggers((CommandSource)commandContext.getSource(), ScoreHolderArgument.getScoreHolder(commandContext, "targets"), suggestionsBuilder);
    }

    private static int lambda$register$16(CommandContext commandContext) throws CommandSyntaxException {
        return ScoreboardCommand.resetPlayerScore((CommandSource)commandContext.getSource(), ScoreHolderArgument.getScoreHolder(commandContext, "targets"), ObjectiveArgument.getObjective(commandContext, "objective"));
    }

    private static int lambda$register$15(CommandContext commandContext) throws CommandSyntaxException {
        return ScoreboardCommand.resetPlayerAllScores((CommandSource)commandContext.getSource(), ScoreHolderArgument.getScoreHolder(commandContext, "targets"));
    }

    private static int lambda$register$14(CommandContext commandContext) throws CommandSyntaxException {
        return ScoreboardCommand.removeFromPlayerScore((CommandSource)commandContext.getSource(), ScoreHolderArgument.getScoreHolder(commandContext, "targets"), ObjectiveArgument.getWritableObjective(commandContext, "objective"), IntegerArgumentType.getInteger(commandContext, "score"));
    }

    private static int lambda$register$13(CommandContext commandContext) throws CommandSyntaxException {
        return ScoreboardCommand.addToPlayerScore((CommandSource)commandContext.getSource(), ScoreHolderArgument.getScoreHolder(commandContext, "targets"), ObjectiveArgument.getWritableObjective(commandContext, "objective"), IntegerArgumentType.getInteger(commandContext, "score"));
    }

    private static int lambda$register$12(CommandContext commandContext) throws CommandSyntaxException {
        return ScoreboardCommand.getPlayerScore((CommandSource)commandContext.getSource(), ScoreHolderArgument.getSingleScoreHolderNoObjectives(commandContext, "target"), ObjectiveArgument.getObjective(commandContext, "objective"));
    }

    private static int lambda$register$11(CommandContext commandContext) throws CommandSyntaxException {
        return ScoreboardCommand.setPlayerScore((CommandSource)commandContext.getSource(), ScoreHolderArgument.getScoreHolder(commandContext, "targets"), ObjectiveArgument.getWritableObjective(commandContext, "objective"), IntegerArgumentType.getInteger(commandContext, "score"));
    }

    private static int lambda$register$10(CommandContext commandContext) throws CommandSyntaxException {
        return ScoreboardCommand.listPlayerScores((CommandSource)commandContext.getSource(), ScoreHolderArgument.getSingleScoreHolderNoObjectives(commandContext, "target"));
    }

    private static int lambda$register$9(CommandContext commandContext) throws CommandSyntaxException {
        return ScoreboardCommand.listPlayers((CommandSource)commandContext.getSource());
    }

    private static int lambda$register$8(CommandContext commandContext) throws CommandSyntaxException {
        return ScoreboardCommand.setObjectiveDisplaySlot((CommandSource)commandContext.getSource(), ScoreboardSlotArgument.getScoreboardSlot(commandContext, "slot"), ObjectiveArgument.getObjective(commandContext, "objective"));
    }

    private static int lambda$register$7(CommandContext commandContext) throws CommandSyntaxException {
        return ScoreboardCommand.clearObjectiveDisplaySlot((CommandSource)commandContext.getSource(), ScoreboardSlotArgument.getScoreboardSlot(commandContext, "slot"));
    }

    private static int lambda$register$6(CommandContext commandContext) throws CommandSyntaxException {
        return ScoreboardCommand.removeObjective((CommandSource)commandContext.getSource(), ObjectiveArgument.getObjective(commandContext, "objective"));
    }

    private static int lambda$register$5(CommandContext commandContext) throws CommandSyntaxException {
        return ScoreboardCommand.setDisplayName((CommandSource)commandContext.getSource(), ObjectiveArgument.getObjective(commandContext, "objective"), ComponentArgument.getComponent(commandContext, "displayName"));
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return ScoreboardCommand.addObjective((CommandSource)commandContext.getSource(), StringArgumentType.getString(commandContext, "objective"), ObjectiveCriteriaArgument.getObjectiveCriteria(commandContext, "criteria"), ComponentArgument.getComponent(commandContext, "displayName"));
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return ScoreboardCommand.addObjective((CommandSource)commandContext.getSource(), StringArgumentType.getString(commandContext, "objective"), ObjectiveCriteriaArgument.getObjectiveCriteria(commandContext, "criteria"), new StringTextComponent(StringArgumentType.getString(commandContext, "objective")));
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return ScoreboardCommand.listObjectives((CommandSource)commandContext.getSource());
    }

    private static boolean lambda$register$1(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static Message lambda$static$0(Object object, Object object2) {
        return new TranslationTextComponent("commands.scoreboard.players.get.null", object, object2);
    }
}

