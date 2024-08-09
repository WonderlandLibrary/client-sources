/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.PotionArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.TranslationTextComponent;

public class EffectCommand {
    private static final SimpleCommandExceptionType GIVE_FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.effect.give.failed"));
    private static final SimpleCommandExceptionType CLEAR_EVERYTHING_FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.effect.clear.everything.failed"));
    private static final SimpleCommandExceptionType CLEAR_SPECIFIC_FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.effect.clear.specific.failed"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("effect").requires(EffectCommand::lambda$register$0)).then(((LiteralArgumentBuilder)Commands.literal("clear").executes(EffectCommand::lambda$register$1)).then(((RequiredArgumentBuilder)Commands.argument("targets", EntityArgument.entities()).executes(EffectCommand::lambda$register$2)).then(Commands.argument("effect", PotionArgument.mobEffect()).executes(EffectCommand::lambda$register$3))))).then(Commands.literal("give").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("targets", EntityArgument.entities()).then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("effect", PotionArgument.mobEffect()).executes(EffectCommand::lambda$register$4)).then(((RequiredArgumentBuilder)Commands.argument("seconds", IntegerArgumentType.integer(1, 1000000)).executes(EffectCommand::lambda$register$5)).then(((RequiredArgumentBuilder)Commands.argument("amplifier", IntegerArgumentType.integer(0, 255)).executes(EffectCommand::lambda$register$6)).then(Commands.argument("hideParticles", BoolArgumentType.bool()).executes(EffectCommand::lambda$register$7))))))));
    }

    private static int addEffect(CommandSource commandSource, Collection<? extends Entity> collection, Effect effect, @Nullable Integer n, int n2, boolean bl) throws CommandSyntaxException {
        int n3 = 0;
        int n4 = n != null ? (effect.isInstant() ? n : n * 20) : (effect.isInstant() ? 1 : 600);
        for (Entity entity2 : collection) {
            EffectInstance effectInstance;
            if (!(entity2 instanceof LivingEntity) || !((LivingEntity)entity2).addPotionEffect(effectInstance = new EffectInstance(effect, n4, n2, false, bl))) continue;
            ++n3;
        }
        if (n3 == 0) {
            throw GIVE_FAILED_EXCEPTION.create();
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.effect.give.success.single", effect.getDisplayName(), collection.iterator().next().getDisplayName(), n4 / 20), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.effect.give.success.multiple", effect.getDisplayName(), collection.size(), n4 / 20), false);
        }
        return n3;
    }

    private static int clearAllEffects(CommandSource commandSource, Collection<? extends Entity> collection) throws CommandSyntaxException {
        int n = 0;
        for (Entity entity2 : collection) {
            if (!(entity2 instanceof LivingEntity) || !((LivingEntity)entity2).clearActivePotions()) continue;
            ++n;
        }
        if (n == 0) {
            throw CLEAR_EVERYTHING_FAILED_EXCEPTION.create();
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.effect.clear.everything.success.single", collection.iterator().next().getDisplayName()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.effect.clear.everything.success.multiple", collection.size()), false);
        }
        return n;
    }

    private static int clearEffect(CommandSource commandSource, Collection<? extends Entity> collection, Effect effect) throws CommandSyntaxException {
        int n = 0;
        for (Entity entity2 : collection) {
            if (!(entity2 instanceof LivingEntity) || !((LivingEntity)entity2).removePotionEffect(effect)) continue;
            ++n;
        }
        if (n == 0) {
            throw CLEAR_SPECIFIC_FAILED_EXCEPTION.create();
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.effect.clear.specific.success.single", effect.getDisplayName(), collection.iterator().next().getDisplayName()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.effect.clear.specific.success.multiple", effect.getDisplayName(), collection.size()), false);
        }
        return n;
    }

    private static int lambda$register$7(CommandContext commandContext) throws CommandSyntaxException {
        return EffectCommand.addEffect((CommandSource)commandContext.getSource(), EntityArgument.getEntities(commandContext, "targets"), PotionArgument.getMobEffect(commandContext, "effect"), IntegerArgumentType.getInteger(commandContext, "seconds"), IntegerArgumentType.getInteger(commandContext, "amplifier"), !BoolArgumentType.getBool(commandContext, "hideParticles"));
    }

    private static int lambda$register$6(CommandContext commandContext) throws CommandSyntaxException {
        return EffectCommand.addEffect((CommandSource)commandContext.getSource(), EntityArgument.getEntities(commandContext, "targets"), PotionArgument.getMobEffect(commandContext, "effect"), IntegerArgumentType.getInteger(commandContext, "seconds"), IntegerArgumentType.getInteger(commandContext, "amplifier"), true);
    }

    private static int lambda$register$5(CommandContext commandContext) throws CommandSyntaxException {
        return EffectCommand.addEffect((CommandSource)commandContext.getSource(), EntityArgument.getEntities(commandContext, "targets"), PotionArgument.getMobEffect(commandContext, "effect"), IntegerArgumentType.getInteger(commandContext, "seconds"), 0, true);
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return EffectCommand.addEffect((CommandSource)commandContext.getSource(), EntityArgument.getEntities(commandContext, "targets"), PotionArgument.getMobEffect(commandContext, "effect"), null, 0, true);
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return EffectCommand.clearEffect((CommandSource)commandContext.getSource(), EntityArgument.getEntities(commandContext, "targets"), PotionArgument.getMobEffect(commandContext, "effect"));
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return EffectCommand.clearAllEffects((CommandSource)commandContext.getSource(), EntityArgument.getEntities(commandContext, "targets"));
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        return EffectCommand.clearAllEffects((CommandSource)commandContext.getSource(), ImmutableList.of(((CommandSource)commandContext.getSource()).assertIsEntity()));
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }
}

