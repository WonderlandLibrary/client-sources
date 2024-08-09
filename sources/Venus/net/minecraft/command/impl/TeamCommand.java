/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ColorArgument;
import net.minecraft.command.arguments.ComponentArgument;
import net.minecraft.command.arguments.ScoreHolderArgument;
import net.minecraft.command.arguments.TeamArgument;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class TeamCommand {
    private static final SimpleCommandExceptionType DUPLICATE_TEAM_NAME = new SimpleCommandExceptionType(new TranslationTextComponent("commands.team.add.duplicate"));
    private static final DynamicCommandExceptionType TEAM_NAME_TOO_LONG = new DynamicCommandExceptionType(TeamCommand::lambda$static$0);
    private static final SimpleCommandExceptionType EMPTY_NO_CHANGE = new SimpleCommandExceptionType(new TranslationTextComponent("commands.team.empty.unchanged"));
    private static final SimpleCommandExceptionType NAME_NO_CHANGE = new SimpleCommandExceptionType(new TranslationTextComponent("commands.team.option.name.unchanged"));
    private static final SimpleCommandExceptionType COLOR_NO_CHANGE = new SimpleCommandExceptionType(new TranslationTextComponent("commands.team.option.color.unchanged"));
    private static final SimpleCommandExceptionType FRIENDLY_FIRE_ALREADY_ON = new SimpleCommandExceptionType(new TranslationTextComponent("commands.team.option.friendlyfire.alreadyEnabled"));
    private static final SimpleCommandExceptionType FRIENDLY_FIRE_ALREADY_OFF = new SimpleCommandExceptionType(new TranslationTextComponent("commands.team.option.friendlyfire.alreadyDisabled"));
    private static final SimpleCommandExceptionType SEE_FRIENDLY_INVISIBLES_ALREADY_ON = new SimpleCommandExceptionType(new TranslationTextComponent("commands.team.option.seeFriendlyInvisibles.alreadyEnabled"));
    private static final SimpleCommandExceptionType SEE_FRIENDLY_INVISIBLES_ALREADY_OFF = new SimpleCommandExceptionType(new TranslationTextComponent("commands.team.option.seeFriendlyInvisibles.alreadyDisabled"));
    private static final SimpleCommandExceptionType NAMETAG_VISIBILITY_NO_CHANGE = new SimpleCommandExceptionType(new TranslationTextComponent("commands.team.option.nametagVisibility.unchanged"));
    private static final SimpleCommandExceptionType DEATH_MESSAGE_VISIBILITY_NO_CHANGE = new SimpleCommandExceptionType(new TranslationTextComponent("commands.team.option.deathMessageVisibility.unchanged"));
    private static final SimpleCommandExceptionType COLLISION_NO_CHANGE = new SimpleCommandExceptionType(new TranslationTextComponent("commands.team.option.collisionRule.unchanged"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("team").requires(TeamCommand::lambda$register$1)).then(((LiteralArgumentBuilder)Commands.literal("list").executes(TeamCommand::lambda$register$2)).then(Commands.argument("team", TeamArgument.team()).executes(TeamCommand::lambda$register$3)))).then(Commands.literal("add").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("team", StringArgumentType.word()).executes(TeamCommand::lambda$register$4)).then(Commands.argument("displayName", ComponentArgument.component()).executes(TeamCommand::lambda$register$5))))).then(Commands.literal("remove").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("team", TeamArgument.team()).executes(TeamCommand::lambda$register$6)))).then(Commands.literal("empty").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("team", TeamArgument.team()).executes(TeamCommand::lambda$register$7)))).then(Commands.literal("join").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("team", TeamArgument.team()).executes(TeamCommand::lambda$register$8)).then(Commands.argument("members", ScoreHolderArgument.scoreHolders()).suggests(ScoreHolderArgument.SUGGEST_ENTITY_SELECTOR).executes(TeamCommand::lambda$register$9))))).then(Commands.literal("leave").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("members", ScoreHolderArgument.scoreHolders()).suggests(ScoreHolderArgument.SUGGEST_ENTITY_SELECTOR).executes(TeamCommand::lambda$register$10)))).then(Commands.literal("modify").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("team", TeamArgument.team()).then((ArgumentBuilder<CommandSource, ?>)Commands.literal("displayName").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("displayName", ComponentArgument.component()).executes(TeamCommand::lambda$register$11)))).then(Commands.literal("color").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("value", ColorArgument.color()).executes(TeamCommand::lambda$register$12)))).then(Commands.literal("friendlyFire").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("allowed", BoolArgumentType.bool()).executes(TeamCommand::lambda$register$13)))).then(Commands.literal("seeFriendlyInvisibles").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("allowed", BoolArgumentType.bool()).executes(TeamCommand::lambda$register$14)))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("nametagVisibility").then((ArgumentBuilder<CommandSource, ?>)Commands.literal("never").executes(TeamCommand::lambda$register$15))).then(Commands.literal("hideForOtherTeams").executes(TeamCommand::lambda$register$16))).then(Commands.literal("hideForOwnTeam").executes(TeamCommand::lambda$register$17))).then(Commands.literal("always").executes(TeamCommand::lambda$register$18)))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("deathMessageVisibility").then((ArgumentBuilder<CommandSource, ?>)Commands.literal("never").executes(TeamCommand::lambda$register$19))).then(Commands.literal("hideForOtherTeams").executes(TeamCommand::lambda$register$20))).then(Commands.literal("hideForOwnTeam").executes(TeamCommand::lambda$register$21))).then(Commands.literal("always").executes(TeamCommand::lambda$register$22)))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("collisionRule").then((ArgumentBuilder<CommandSource, ?>)Commands.literal("never").executes(TeamCommand::lambda$register$23))).then(Commands.literal("pushOwnTeam").executes(TeamCommand::lambda$register$24))).then(Commands.literal("pushOtherTeams").executes(TeamCommand::lambda$register$25))).then(Commands.literal("always").executes(TeamCommand::lambda$register$26)))).then(Commands.literal("prefix").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("prefix", ComponentArgument.component()).executes(TeamCommand::lambda$register$27)))).then(Commands.literal("suffix").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("suffix", ComponentArgument.component()).executes(TeamCommand::lambda$register$28))))));
    }

    private static int leaveFromTeams(CommandSource commandSource, Collection<String> collection) {
        ServerScoreboard serverScoreboard = commandSource.getServer().getScoreboard();
        for (String string : collection) {
            serverScoreboard.removePlayerFromTeams(string);
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.team.leave.success.single", collection.iterator().next()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.team.leave.success.multiple", collection.size()), false);
        }
        return collection.size();
    }

    private static int joinTeam(CommandSource commandSource, ScorePlayerTeam scorePlayerTeam, Collection<String> collection) {
        ServerScoreboard serverScoreboard = commandSource.getServer().getScoreboard();
        for (String string : collection) {
            ((Scoreboard)serverScoreboard).addPlayerToTeam(string, scorePlayerTeam);
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.team.join.success.single", collection.iterator().next(), scorePlayerTeam.func_237501_d_()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.team.join.success.multiple", collection.size(), scorePlayerTeam.func_237501_d_()), false);
        }
        return collection.size();
    }

    private static int setNameTagVisibility(CommandSource commandSource, ScorePlayerTeam scorePlayerTeam, Team.Visible visible) throws CommandSyntaxException {
        if (scorePlayerTeam.getNameTagVisibility() == visible) {
            throw NAMETAG_VISIBILITY_NO_CHANGE.create();
        }
        scorePlayerTeam.setNameTagVisibility(visible);
        commandSource.sendFeedback(new TranslationTextComponent("commands.team.option.nametagVisibility.success", scorePlayerTeam.func_237501_d_(), visible.getDisplayName()), false);
        return 1;
    }

    private static int setDeathMessageVisibility(CommandSource commandSource, ScorePlayerTeam scorePlayerTeam, Team.Visible visible) throws CommandSyntaxException {
        if (scorePlayerTeam.getDeathMessageVisibility() == visible) {
            throw DEATH_MESSAGE_VISIBILITY_NO_CHANGE.create();
        }
        scorePlayerTeam.setDeathMessageVisibility(visible);
        commandSource.sendFeedback(new TranslationTextComponent("commands.team.option.deathMessageVisibility.success", scorePlayerTeam.func_237501_d_(), visible.getDisplayName()), false);
        return 1;
    }

    private static int setCollisionRule(CommandSource commandSource, ScorePlayerTeam scorePlayerTeam, Team.CollisionRule collisionRule) throws CommandSyntaxException {
        if (scorePlayerTeam.getCollisionRule() == collisionRule) {
            throw COLLISION_NO_CHANGE.create();
        }
        scorePlayerTeam.setCollisionRule(collisionRule);
        commandSource.sendFeedback(new TranslationTextComponent("commands.team.option.collisionRule.success", scorePlayerTeam.func_237501_d_(), collisionRule.getDisplayName()), false);
        return 1;
    }

    private static int setCanSeeFriendlyInvisibles(CommandSource commandSource, ScorePlayerTeam scorePlayerTeam, boolean bl) throws CommandSyntaxException {
        if (scorePlayerTeam.getSeeFriendlyInvisiblesEnabled() == bl) {
            if (bl) {
                throw SEE_FRIENDLY_INVISIBLES_ALREADY_ON.create();
            }
            throw SEE_FRIENDLY_INVISIBLES_ALREADY_OFF.create();
        }
        scorePlayerTeam.setSeeFriendlyInvisiblesEnabled(bl);
        commandSource.sendFeedback(new TranslationTextComponent("commands.team.option.seeFriendlyInvisibles." + (bl ? "enabled" : "disabled"), scorePlayerTeam.func_237501_d_()), false);
        return 1;
    }

    private static int setAllowFriendlyFire(CommandSource commandSource, ScorePlayerTeam scorePlayerTeam, boolean bl) throws CommandSyntaxException {
        if (scorePlayerTeam.getAllowFriendlyFire() == bl) {
            if (bl) {
                throw FRIENDLY_FIRE_ALREADY_ON.create();
            }
            throw FRIENDLY_FIRE_ALREADY_OFF.create();
        }
        scorePlayerTeam.setAllowFriendlyFire(bl);
        commandSource.sendFeedback(new TranslationTextComponent("commands.team.option.friendlyfire." + (bl ? "enabled" : "disabled"), scorePlayerTeam.func_237501_d_()), false);
        return 1;
    }

    private static int setDisplayName(CommandSource commandSource, ScorePlayerTeam scorePlayerTeam, ITextComponent iTextComponent) throws CommandSyntaxException {
        if (scorePlayerTeam.getDisplayName().equals(iTextComponent)) {
            throw NAME_NO_CHANGE.create();
        }
        scorePlayerTeam.setDisplayName(iTextComponent);
        commandSource.sendFeedback(new TranslationTextComponent("commands.team.option.name.success", scorePlayerTeam.func_237501_d_()), false);
        return 1;
    }

    private static int setColor(CommandSource commandSource, ScorePlayerTeam scorePlayerTeam, TextFormatting textFormatting) throws CommandSyntaxException {
        if (scorePlayerTeam.getColor() == textFormatting) {
            throw COLOR_NO_CHANGE.create();
        }
        scorePlayerTeam.setColor(textFormatting);
        commandSource.sendFeedback(new TranslationTextComponent("commands.team.option.color.success", scorePlayerTeam.func_237501_d_(), textFormatting.getFriendlyName()), false);
        return 1;
    }

    private static int emptyTeam(CommandSource commandSource, ScorePlayerTeam scorePlayerTeam) throws CommandSyntaxException {
        ServerScoreboard serverScoreboard = commandSource.getServer().getScoreboard();
        ArrayList<String> arrayList = Lists.newArrayList(scorePlayerTeam.getMembershipCollection());
        if (arrayList.isEmpty()) {
            throw EMPTY_NO_CHANGE.create();
        }
        for (String string : arrayList) {
            ((Scoreboard)serverScoreboard).removePlayerFromTeam(string, scorePlayerTeam);
        }
        commandSource.sendFeedback(new TranslationTextComponent("commands.team.empty.success", arrayList.size(), scorePlayerTeam.func_237501_d_()), false);
        return arrayList.size();
    }

    private static int removeTeam(CommandSource commandSource, ScorePlayerTeam scorePlayerTeam) {
        ServerScoreboard serverScoreboard = commandSource.getServer().getScoreboard();
        serverScoreboard.removeTeam(scorePlayerTeam);
        commandSource.sendFeedback(new TranslationTextComponent("commands.team.remove.success", scorePlayerTeam.func_237501_d_()), false);
        return serverScoreboard.getTeams().size();
    }

    private static int addTeam(CommandSource commandSource, String string) throws CommandSyntaxException {
        return TeamCommand.addTeam(commandSource, string, new StringTextComponent(string));
    }

    private static int addTeam(CommandSource commandSource, String string, ITextComponent iTextComponent) throws CommandSyntaxException {
        ServerScoreboard serverScoreboard = commandSource.getServer().getScoreboard();
        if (serverScoreboard.getTeam(string) != null) {
            throw DUPLICATE_TEAM_NAME.create();
        }
        if (string.length() > 16) {
            throw TEAM_NAME_TOO_LONG.create(16);
        }
        ScorePlayerTeam scorePlayerTeam = serverScoreboard.createTeam(string);
        scorePlayerTeam.setDisplayName(iTextComponent);
        commandSource.sendFeedback(new TranslationTextComponent("commands.team.add.success", scorePlayerTeam.func_237501_d_()), false);
        return serverScoreboard.getTeams().size();
    }

    private static int listMembers(CommandSource commandSource, ScorePlayerTeam scorePlayerTeam) {
        Collection<String> collection = scorePlayerTeam.getMembershipCollection();
        if (collection.isEmpty()) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.team.list.members.empty", scorePlayerTeam.func_237501_d_()), true);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.team.list.members.success", scorePlayerTeam.func_237501_d_(), collection.size(), TextComponentUtils.makeGreenSortedList(collection)), true);
        }
        return collection.size();
    }

    private static int listTeams(CommandSource commandSource) {
        Collection<ScorePlayerTeam> collection = commandSource.getServer().getScoreboard().getTeams();
        if (collection.isEmpty()) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.team.list.teams.empty"), true);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.team.list.teams.success", collection.size(), TextComponentUtils.func_240649_b_(collection, ScorePlayerTeam::func_237501_d_)), true);
        }
        return collection.size();
    }

    private static int setPrefix(CommandSource commandSource, ScorePlayerTeam scorePlayerTeam, ITextComponent iTextComponent) {
        scorePlayerTeam.setPrefix(iTextComponent);
        commandSource.sendFeedback(new TranslationTextComponent("commands.team.option.prefix.success", iTextComponent), true);
        return 0;
    }

    private static int setSuffix(CommandSource commandSource, ScorePlayerTeam scorePlayerTeam, ITextComponent iTextComponent) {
        scorePlayerTeam.setSuffix(iTextComponent);
        commandSource.sendFeedback(new TranslationTextComponent("commands.team.option.suffix.success", iTextComponent), true);
        return 0;
    }

    private static int lambda$register$28(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.setSuffix((CommandSource)commandContext.getSource(), TeamArgument.getTeam(commandContext, "team"), ComponentArgument.getComponent(commandContext, "suffix"));
    }

    private static int lambda$register$27(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.setPrefix((CommandSource)commandContext.getSource(), TeamArgument.getTeam(commandContext, "team"), ComponentArgument.getComponent(commandContext, "prefix"));
    }

    private static int lambda$register$26(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.setCollisionRule((CommandSource)commandContext.getSource(), TeamArgument.getTeam(commandContext, "team"), Team.CollisionRule.ALWAYS);
    }

    private static int lambda$register$25(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.setCollisionRule((CommandSource)commandContext.getSource(), TeamArgument.getTeam(commandContext, "team"), Team.CollisionRule.PUSH_OTHER_TEAMS);
    }

    private static int lambda$register$24(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.setCollisionRule((CommandSource)commandContext.getSource(), TeamArgument.getTeam(commandContext, "team"), Team.CollisionRule.PUSH_OWN_TEAM);
    }

    private static int lambda$register$23(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.setCollisionRule((CommandSource)commandContext.getSource(), TeamArgument.getTeam(commandContext, "team"), Team.CollisionRule.NEVER);
    }

    private static int lambda$register$22(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.setDeathMessageVisibility((CommandSource)commandContext.getSource(), TeamArgument.getTeam(commandContext, "team"), Team.Visible.ALWAYS);
    }

    private static int lambda$register$21(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.setDeathMessageVisibility((CommandSource)commandContext.getSource(), TeamArgument.getTeam(commandContext, "team"), Team.Visible.HIDE_FOR_OWN_TEAM);
    }

    private static int lambda$register$20(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.setDeathMessageVisibility((CommandSource)commandContext.getSource(), TeamArgument.getTeam(commandContext, "team"), Team.Visible.HIDE_FOR_OTHER_TEAMS);
    }

    private static int lambda$register$19(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.setDeathMessageVisibility((CommandSource)commandContext.getSource(), TeamArgument.getTeam(commandContext, "team"), Team.Visible.NEVER);
    }

    private static int lambda$register$18(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.setNameTagVisibility((CommandSource)commandContext.getSource(), TeamArgument.getTeam(commandContext, "team"), Team.Visible.ALWAYS);
    }

    private static int lambda$register$17(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.setNameTagVisibility((CommandSource)commandContext.getSource(), TeamArgument.getTeam(commandContext, "team"), Team.Visible.HIDE_FOR_OWN_TEAM);
    }

    private static int lambda$register$16(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.setNameTagVisibility((CommandSource)commandContext.getSource(), TeamArgument.getTeam(commandContext, "team"), Team.Visible.HIDE_FOR_OTHER_TEAMS);
    }

    private static int lambda$register$15(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.setNameTagVisibility((CommandSource)commandContext.getSource(), TeamArgument.getTeam(commandContext, "team"), Team.Visible.NEVER);
    }

    private static int lambda$register$14(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.setCanSeeFriendlyInvisibles((CommandSource)commandContext.getSource(), TeamArgument.getTeam(commandContext, "team"), BoolArgumentType.getBool(commandContext, "allowed"));
    }

    private static int lambda$register$13(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.setAllowFriendlyFire((CommandSource)commandContext.getSource(), TeamArgument.getTeam(commandContext, "team"), BoolArgumentType.getBool(commandContext, "allowed"));
    }

    private static int lambda$register$12(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.setColor((CommandSource)commandContext.getSource(), TeamArgument.getTeam(commandContext, "team"), ColorArgument.getColor(commandContext, "value"));
    }

    private static int lambda$register$11(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.setDisplayName((CommandSource)commandContext.getSource(), TeamArgument.getTeam(commandContext, "team"), ComponentArgument.getComponent(commandContext, "displayName"));
    }

    private static int lambda$register$10(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.leaveFromTeams((CommandSource)commandContext.getSource(), ScoreHolderArgument.getScoreHolder(commandContext, "members"));
    }

    private static int lambda$register$9(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.joinTeam((CommandSource)commandContext.getSource(), TeamArgument.getTeam(commandContext, "team"), ScoreHolderArgument.getScoreHolder(commandContext, "members"));
    }

    private static int lambda$register$8(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.joinTeam((CommandSource)commandContext.getSource(), TeamArgument.getTeam(commandContext, "team"), Collections.singleton(((CommandSource)commandContext.getSource()).assertIsEntity().getScoreboardName()));
    }

    private static int lambda$register$7(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.emptyTeam((CommandSource)commandContext.getSource(), TeamArgument.getTeam(commandContext, "team"));
    }

    private static int lambda$register$6(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.removeTeam((CommandSource)commandContext.getSource(), TeamArgument.getTeam(commandContext, "team"));
    }

    private static int lambda$register$5(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.addTeam((CommandSource)commandContext.getSource(), StringArgumentType.getString(commandContext, "team"), ComponentArgument.getComponent(commandContext, "displayName"));
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.addTeam((CommandSource)commandContext.getSource(), StringArgumentType.getString(commandContext, "team"));
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.listMembers((CommandSource)commandContext.getSource(), TeamArgument.getTeam(commandContext, "team"));
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return TeamCommand.listTeams((CommandSource)commandContext.getSource());
    }

    private static boolean lambda$register$1(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("commands.team.add.longName", object);
    }
}

