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
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntFunction;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;

public class ExperienceCommand {
    private static final SimpleCommandExceptionType SET_POINTS_INVALID_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.experience.set.points.invalid"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        LiteralCommandNode<CommandSource> literalCommandNode = commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("experience").requires(ExperienceCommand::lambda$register$0)).then(Commands.literal("add").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("targets", EntityArgument.players()).then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("amount", IntegerArgumentType.integer()).executes(ExperienceCommand::lambda$register$1)).then(Commands.literal("points").executes(ExperienceCommand::lambda$register$2))).then(Commands.literal("levels").executes(ExperienceCommand::lambda$register$3)))))).then(Commands.literal("set").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("targets", EntityArgument.players()).then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("amount", IntegerArgumentType.integer(0)).executes(ExperienceCommand::lambda$register$4)).then(Commands.literal("points").executes(ExperienceCommand::lambda$register$5))).then(Commands.literal("levels").executes(ExperienceCommand::lambda$register$6)))))).then(Commands.literal("query").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("targets", EntityArgument.player()).then((ArgumentBuilder<CommandSource, ?>)Commands.literal("points").executes(ExperienceCommand::lambda$register$7))).then(Commands.literal("levels").executes(ExperienceCommand::lambda$register$8)))));
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("xp").requires(ExperienceCommand::lambda$register$9)).redirect(literalCommandNode));
    }

    private static int queryExperience(CommandSource commandSource, ServerPlayerEntity serverPlayerEntity, Type type) {
        int n = type.xpGetter.applyAsInt(serverPlayerEntity);
        commandSource.sendFeedback(new TranslationTextComponent("commands.experience.query." + type.name, serverPlayerEntity.getDisplayName(), n), true);
        return n;
    }

    private static int addExperience(CommandSource commandSource, Collection<? extends ServerPlayerEntity> collection, int n, Type type) {
        for (ServerPlayerEntity serverPlayerEntity : collection) {
            type.xpAdder.accept(serverPlayerEntity, n);
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.experience.add." + type.name + ".success.single", n, collection.iterator().next().getDisplayName()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.experience.add." + type.name + ".success.multiple", n, collection.size()), false);
        }
        return collection.size();
    }

    private static int setExperience(CommandSource commandSource, Collection<? extends ServerPlayerEntity> collection, int n, Type type) throws CommandSyntaxException {
        int n2 = 0;
        for (ServerPlayerEntity serverPlayerEntity : collection) {
            if (!type.xpSetter.test(serverPlayerEntity, n)) continue;
            ++n2;
        }
        if (n2 == 0) {
            throw SET_POINTS_INVALID_EXCEPTION.create();
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.experience.set." + type.name + ".success.single", n, collection.iterator().next().getDisplayName()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.experience.set." + type.name + ".success.multiple", n, collection.size()), false);
        }
        return collection.size();
    }

    private static boolean lambda$register$9(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static int lambda$register$8(CommandContext commandContext) throws CommandSyntaxException {
        return ExperienceCommand.queryExperience((CommandSource)commandContext.getSource(), EntityArgument.getPlayer(commandContext, "targets"), Type.LEVELS);
    }

    private static int lambda$register$7(CommandContext commandContext) throws CommandSyntaxException {
        return ExperienceCommand.queryExperience((CommandSource)commandContext.getSource(), EntityArgument.getPlayer(commandContext, "targets"), Type.POINTS);
    }

    private static int lambda$register$6(CommandContext commandContext) throws CommandSyntaxException {
        return ExperienceCommand.setExperience((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), IntegerArgumentType.getInteger(commandContext, "amount"), Type.LEVELS);
    }

    private static int lambda$register$5(CommandContext commandContext) throws CommandSyntaxException {
        return ExperienceCommand.setExperience((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), IntegerArgumentType.getInteger(commandContext, "amount"), Type.POINTS);
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return ExperienceCommand.setExperience((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), IntegerArgumentType.getInteger(commandContext, "amount"), Type.POINTS);
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return ExperienceCommand.addExperience((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), IntegerArgumentType.getInteger(commandContext, "amount"), Type.LEVELS);
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return ExperienceCommand.addExperience((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), IntegerArgumentType.getInteger(commandContext, "amount"), Type.POINTS);
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        return ExperienceCommand.addExperience((CommandSource)commandContext.getSource(), EntityArgument.getPlayers(commandContext, "targets"), IntegerArgumentType.getInteger(commandContext, "amount"), Type.POINTS);
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    static enum Type {
        POINTS("points", PlayerEntity::giveExperiencePoints, Type::lambda$static$0, Type::lambda$static$1),
        LEVELS("levels", ServerPlayerEntity::addExperienceLevel, Type::lambda$static$2, Type::lambda$static$3);

        public final BiConsumer<ServerPlayerEntity, Integer> xpAdder;
        public final BiPredicate<ServerPlayerEntity, Integer> xpSetter;
        public final String name;
        private final ToIntFunction<ServerPlayerEntity> xpGetter;

        private Type(String string2, BiConsumer<ServerPlayerEntity, Integer> biConsumer, BiPredicate<ServerPlayerEntity, Integer> biPredicate, ToIntFunction<ServerPlayerEntity> toIntFunction) {
            this.xpAdder = biConsumer;
            this.name = string2;
            this.xpSetter = biPredicate;
            this.xpGetter = toIntFunction;
        }

        private static int lambda$static$3(ServerPlayerEntity serverPlayerEntity) {
            return serverPlayerEntity.experienceLevel;
        }

        private static boolean lambda$static$2(ServerPlayerEntity serverPlayerEntity, Integer n) {
            serverPlayerEntity.setExperienceLevel(n);
            return false;
        }

        private static int lambda$static$1(ServerPlayerEntity serverPlayerEntity) {
            return MathHelper.floor(serverPlayerEntity.experience * (float)serverPlayerEntity.xpBarCap());
        }

        private static boolean lambda$static$0(ServerPlayerEntity serverPlayerEntity, Integer n) {
            if (n >= serverPlayerEntity.xpBarCap()) {
                return true;
            }
            serverPlayerEntity.func_195394_a(n);
            return false;
        }
    }
}

