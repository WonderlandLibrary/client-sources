/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

public class AdvancementCommand {
    private static final SuggestionProvider<CommandSource> SUGGEST_ADVANCEMENTS = AdvancementCommand::lambda$static$0;

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("advancement").requires(AdvancementCommand::lambda$register$1)).then(Commands.literal("grant").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("targets", EntityArgument.players()).then((ArgumentBuilder<CommandSource, ?>)Commands.literal("only").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("advancement", ResourceLocationArgument.resourceLocation()).suggests(SUGGEST_ADVANCEMENTS).executes(AdvancementCommand::lambda$register$2)).then(Commands.argument("criterion", StringArgumentType.greedyString()).suggests(AdvancementCommand::lambda$register$3).executes(AdvancementCommand::lambda$register$4))))).then(Commands.literal("from").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("advancement", ResourceLocationArgument.resourceLocation()).suggests(SUGGEST_ADVANCEMENTS).executes(AdvancementCommand::lambda$register$5)))).then(Commands.literal("until").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("advancement", ResourceLocationArgument.resourceLocation()).suggests(SUGGEST_ADVANCEMENTS).executes(AdvancementCommand::lambda$register$6)))).then(Commands.literal("through").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("advancement", ResourceLocationArgument.resourceLocation()).suggests(SUGGEST_ADVANCEMENTS).executes(AdvancementCommand::lambda$register$7)))).then(Commands.literal("everything").executes(AdvancementCommand::lambda$register$8))))).then(Commands.literal("revoke").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("targets", EntityArgument.players()).then((ArgumentBuilder<CommandSource, ?>)Commands.literal("only").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("advancement", ResourceLocationArgument.resourceLocation()).suggests(SUGGEST_ADVANCEMENTS).executes(AdvancementCommand::lambda$register$9)).then(Commands.argument("criterion", StringArgumentType.greedyString()).suggests(AdvancementCommand::lambda$register$10).executes(AdvancementCommand::lambda$register$11))))).then(Commands.literal("from").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("advancement", ResourceLocationArgument.resourceLocation()).suggests(SUGGEST_ADVANCEMENTS).executes(AdvancementCommand::lambda$register$12)))).then(Commands.literal("until").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("advancement", ResourceLocationArgument.resourceLocation()).suggests(SUGGEST_ADVANCEMENTS).executes(AdvancementCommand::lambda$register$13)))).then(Commands.literal("through").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("advancement", ResourceLocationArgument.resourceLocation()).suggests(SUGGEST_ADVANCEMENTS).executes(AdvancementCommand::lambda$register$14)))).then(Commands.literal("everything").executes(AdvancementCommand::lambda$register$15)))));
    }

    private static int forEachAdvancement(CommandSource commandSource, Collection<ServerPlayerEntity> collection, Action action, Collection<Advancement> collection2) {
        int n = 0;
        for (ServerPlayerEntity serverPlayerEntity : collection) {
            n += action.applyToAdvancements(serverPlayerEntity, collection2);
        }
        if (n == 0) {
            if (collection2.size() == 1) {
                if (collection.size() == 1) {
                    throw new CommandException(new TranslationTextComponent(action.getPrefix() + ".one.to.one.failure", collection2.iterator().next().getDisplayText(), collection.iterator().next().getDisplayName()));
                }
                throw new CommandException(new TranslationTextComponent(action.getPrefix() + ".one.to.many.failure", collection2.iterator().next().getDisplayText(), collection.size()));
            }
            if (collection.size() == 1) {
                throw new CommandException(new TranslationTextComponent(action.getPrefix() + ".many.to.one.failure", collection2.size(), collection.iterator().next().getDisplayName()));
            }
            throw new CommandException(new TranslationTextComponent(action.getPrefix() + ".many.to.many.failure", collection2.size(), collection.size()));
        }
        if (collection2.size() == 1) {
            if (collection.size() == 1) {
                commandSource.sendFeedback(new TranslationTextComponent(action.getPrefix() + ".one.to.one.success", collection2.iterator().next().getDisplayText(), collection.iterator().next().getDisplayName()), false);
            } else {
                commandSource.sendFeedback(new TranslationTextComponent(action.getPrefix() + ".one.to.many.success", collection2.iterator().next().getDisplayText(), collection.size()), false);
            }
        } else if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent(action.getPrefix() + ".many.to.one.success", collection2.size(), collection.iterator().next().getDisplayName()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent(action.getPrefix() + ".many.to.many.success", collection2.size(), collection.size()), false);
        }
        return n;
    }

    private static int updateCriterion(CommandSource commandSource, Collection<ServerPlayerEntity> collection, Action action, Advancement advancement, String string) {
        int n = 0;
        if (!advancement.getCriteria().containsKey(string)) {
            throw new CommandException(new TranslationTextComponent("commands.advancement.criterionNotFound", advancement.getDisplayText(), string));
        }
        for (ServerPlayerEntity serverPlayerEntity : collection) {
            if (!action.applyToCriterion(serverPlayerEntity, advancement, string)) continue;
            ++n;
        }
        if (n == 0) {
            if (collection.size() == 1) {
                throw new CommandException(new TranslationTextComponent(action.getPrefix() + ".criterion.to.one.failure", string, advancement.getDisplayText(), collection.iterator().next().getDisplayName()));
            }
            throw new CommandException(new TranslationTextComponent(action.getPrefix() + ".criterion.to.many.failure", string, advancement.getDisplayText(), collection.size()));
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent(action.getPrefix() + ".criterion.to.one.success", string, advancement.getDisplayText(), collection.iterator().next().getDisplayName()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent(action.getPrefix() + ".criterion.to.many.success", string, advancement.getDisplayText(), collection.size()), false);
        }
        return n;
    }

    private static List<Advancement> getMatchingAdvancements(Advancement advancement, Mode mode) {
        ArrayList<Advancement> arrayList = Lists.newArrayList();
        if (mode.includesParents) {
            for (Advancement advancement2 = advancement.getParent(); advancement2 != null; advancement2 = advancement2.getParent()) {
                arrayList.add(advancement2);
            }
        }
        arrayList.add(advancement);
        if (mode.includesChildren) {
            AdvancementCommand.addAllChildren(advancement, arrayList);
        }
        return arrayList;
    }

    private static void addAllChildren(Advancement advancement, List<Advancement> list) {
        for (Advancement advancement2 : advancement.getChildren()) {
            list.add(advancement2);
            AdvancementCommand.addAllChildren(advancement2, list);
        }
    }

    private static int lambda$register$15(CommandContext commandContext) throws CommandSyntaxException {
        return AdvancementCommand.forEachAdvancement((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), Action.REVOKE, ((CommandSource)commandContext.getSource()).getServer().getAdvancementManager().getAllAdvancements());
    }

    private static int lambda$register$14(CommandContext commandContext) throws CommandSyntaxException {
        return AdvancementCommand.forEachAdvancement((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), Action.REVOKE, AdvancementCommand.getMatchingAdvancements(ResourceLocationArgument.getAdvancement(commandContext, "advancement"), Mode.THROUGH));
    }

    private static int lambda$register$13(CommandContext commandContext) throws CommandSyntaxException {
        return AdvancementCommand.forEachAdvancement((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), Action.REVOKE, AdvancementCommand.getMatchingAdvancements(ResourceLocationArgument.getAdvancement(commandContext, "advancement"), Mode.UNTIL));
    }

    private static int lambda$register$12(CommandContext commandContext) throws CommandSyntaxException {
        return AdvancementCommand.forEachAdvancement((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), Action.REVOKE, AdvancementCommand.getMatchingAdvancements(ResourceLocationArgument.getAdvancement(commandContext, "advancement"), Mode.FROM));
    }

    private static int lambda$register$11(CommandContext commandContext) throws CommandSyntaxException {
        return AdvancementCommand.updateCriterion((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), Action.REVOKE, ResourceLocationArgument.getAdvancement(commandContext, "advancement"), StringArgumentType.getString(commandContext, "criterion"));
    }

    private static CompletableFuture lambda$register$10(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        return ISuggestionProvider.suggest(ResourceLocationArgument.getAdvancement(commandContext, "advancement").getCriteria().keySet(), suggestionsBuilder);
    }

    private static int lambda$register$9(CommandContext commandContext) throws CommandSyntaxException {
        return AdvancementCommand.forEachAdvancement((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), Action.REVOKE, AdvancementCommand.getMatchingAdvancements(ResourceLocationArgument.getAdvancement(commandContext, "advancement"), Mode.ONLY));
    }

    private static int lambda$register$8(CommandContext commandContext) throws CommandSyntaxException {
        return AdvancementCommand.forEachAdvancement((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), Action.GRANT, ((CommandSource)commandContext.getSource()).getServer().getAdvancementManager().getAllAdvancements());
    }

    private static int lambda$register$7(CommandContext commandContext) throws CommandSyntaxException {
        return AdvancementCommand.forEachAdvancement((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), Action.GRANT, AdvancementCommand.getMatchingAdvancements(ResourceLocationArgument.getAdvancement(commandContext, "advancement"), Mode.THROUGH));
    }

    private static int lambda$register$6(CommandContext commandContext) throws CommandSyntaxException {
        return AdvancementCommand.forEachAdvancement((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), Action.GRANT, AdvancementCommand.getMatchingAdvancements(ResourceLocationArgument.getAdvancement(commandContext, "advancement"), Mode.UNTIL));
    }

    private static int lambda$register$5(CommandContext commandContext) throws CommandSyntaxException {
        return AdvancementCommand.forEachAdvancement((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), Action.GRANT, AdvancementCommand.getMatchingAdvancements(ResourceLocationArgument.getAdvancement(commandContext, "advancement"), Mode.FROM));
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return AdvancementCommand.updateCriterion((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), Action.GRANT, ResourceLocationArgument.getAdvancement(commandContext, "advancement"), StringArgumentType.getString(commandContext, "criterion"));
    }

    private static CompletableFuture lambda$register$3(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        return ISuggestionProvider.suggest(ResourceLocationArgument.getAdvancement(commandContext, "advancement").getCriteria().keySet(), suggestionsBuilder);
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return AdvancementCommand.forEachAdvancement((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), Action.GRANT, AdvancementCommand.getMatchingAdvancements(ResourceLocationArgument.getAdvancement(commandContext, "advancement"), Mode.ONLY));
    }

    private static boolean lambda$register$1(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static CompletableFuture lambda$static$0(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        Collection<Advancement> collection = ((CommandSource)commandContext.getSource()).getServer().getAdvancementManager().getAllAdvancements();
        return ISuggestionProvider.func_212476_a(collection.stream().map(Advancement::getId), suggestionsBuilder);
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    static enum Action {
        GRANT("grant"){

            @Override
            protected boolean applyToAdvancement(ServerPlayerEntity serverPlayerEntity, Advancement advancement) {
                AdvancementProgress advancementProgress = serverPlayerEntity.getAdvancements().getProgress(advancement);
                if (advancementProgress.isDone()) {
                    return true;
                }
                for (String string : advancementProgress.getRemaningCriteria()) {
                    serverPlayerEntity.getAdvancements().grantCriterion(advancement, string);
                }
                return false;
            }

            @Override
            protected boolean applyToCriterion(ServerPlayerEntity serverPlayerEntity, Advancement advancement, String string) {
                return serverPlayerEntity.getAdvancements().grantCriterion(advancement, string);
            }
        }
        ,
        REVOKE("revoke"){

            @Override
            protected boolean applyToAdvancement(ServerPlayerEntity serverPlayerEntity, Advancement advancement) {
                AdvancementProgress advancementProgress = serverPlayerEntity.getAdvancements().getProgress(advancement);
                if (!advancementProgress.hasProgress()) {
                    return true;
                }
                for (String string : advancementProgress.getCompletedCriteria()) {
                    serverPlayerEntity.getAdvancements().revokeCriterion(advancement, string);
                }
                return false;
            }

            @Override
            protected boolean applyToCriterion(ServerPlayerEntity serverPlayerEntity, Advancement advancement, String string) {
                return serverPlayerEntity.getAdvancements().revokeCriterion(advancement, string);
            }
        };

        private final String prefix;

        private Action(String string2) {
            this.prefix = "commands.advancement." + string2;
        }

        public int applyToAdvancements(ServerPlayerEntity serverPlayerEntity, Iterable<Advancement> iterable) {
            int n = 0;
            for (Advancement advancement : iterable) {
                if (!this.applyToAdvancement(serverPlayerEntity, advancement)) continue;
                ++n;
            }
            return n;
        }

        protected abstract boolean applyToAdvancement(ServerPlayerEntity var1, Advancement var2);

        protected abstract boolean applyToCriterion(ServerPlayerEntity var1, Advancement var2, String var3);

        protected String getPrefix() {
            return this.prefix;
        }
    }

    static enum Mode {
        ONLY(false, false),
        THROUGH(true, true),
        FROM(false, true),
        UNTIL(true, false),
        EVERYTHING(true, true);

        private final boolean includesParents;
        private final boolean includesChildren;

        private Mode(boolean bl, boolean bl2) {
            this.includesParents = bl;
            this.includesChildren = bl2;
        }
    }
}

