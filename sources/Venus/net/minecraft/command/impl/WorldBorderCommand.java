/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Locale;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.Vec2Argument;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.border.WorldBorder;

public class WorldBorderCommand {
    private static final SimpleCommandExceptionType CENTER_NO_CHANGE = new SimpleCommandExceptionType(new TranslationTextComponent("commands.worldborder.center.failed"));
    private static final SimpleCommandExceptionType SIZE_NO_CHANGE = new SimpleCommandExceptionType(new TranslationTextComponent("commands.worldborder.set.failed.nochange"));
    private static final SimpleCommandExceptionType SIZE_TOO_SMALL = new SimpleCommandExceptionType(new TranslationTextComponent("commands.worldborder.set.failed.small."));
    private static final SimpleCommandExceptionType SIZE_TOO_BIG = new SimpleCommandExceptionType(new TranslationTextComponent("commands.worldborder.set.failed.big."));
    private static final SimpleCommandExceptionType WARNING_TIME_NO_CHANGE = new SimpleCommandExceptionType(new TranslationTextComponent("commands.worldborder.warning.time.failed"));
    private static final SimpleCommandExceptionType WARNING_DISTANCE_NO_CHANGE = new SimpleCommandExceptionType(new TranslationTextComponent("commands.worldborder.warning.distance.failed"));
    private static final SimpleCommandExceptionType DAMAGE_BUFFER_NO_CHANGE = new SimpleCommandExceptionType(new TranslationTextComponent("commands.worldborder.damage.buffer.failed"));
    private static final SimpleCommandExceptionType DAMAGE_AMOUNT_NO_CHANGE = new SimpleCommandExceptionType(new TranslationTextComponent("commands.worldborder.damage.amount.failed"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("worldborder").requires(WorldBorderCommand::lambda$register$0)).then(Commands.literal("add").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("distance", FloatArgumentType.floatArg(-6.0E7f, 6.0E7f)).executes(WorldBorderCommand::lambda$register$1)).then(Commands.argument("time", IntegerArgumentType.integer(0)).executes(WorldBorderCommand::lambda$register$2))))).then(Commands.literal("set").then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)Commands.argument("distance", FloatArgumentType.floatArg(-6.0E7f, 6.0E7f)).executes(WorldBorderCommand::lambda$register$3)).then(Commands.argument("time", IntegerArgumentType.integer(0)).executes(WorldBorderCommand::lambda$register$4))))).then(Commands.literal("center").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("pos", Vec2Argument.vec2()).executes(WorldBorderCommand::lambda$register$5)))).then(((LiteralArgumentBuilder)Commands.literal("damage").then((ArgumentBuilder<CommandSource, ?>)Commands.literal("amount").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("damagePerBlock", FloatArgumentType.floatArg(0.0f)).executes(WorldBorderCommand::lambda$register$6)))).then(Commands.literal("buffer").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("distance", FloatArgumentType.floatArg(0.0f)).executes(WorldBorderCommand::lambda$register$7))))).then(Commands.literal("get").executes(WorldBorderCommand::lambda$register$8))).then(((LiteralArgumentBuilder)Commands.literal("warning").then((ArgumentBuilder<CommandSource, ?>)Commands.literal("distance").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("distance", IntegerArgumentType.integer(0)).executes(WorldBorderCommand::lambda$register$9)))).then(Commands.literal("time").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("time", IntegerArgumentType.integer(0)).executes(WorldBorderCommand::lambda$register$10)))));
    }

    private static int setDamageBuffer(CommandSource commandSource, float f) throws CommandSyntaxException {
        WorldBorder worldBorder = commandSource.getWorld().getWorldBorder();
        if (worldBorder.getDamageBuffer() == (double)f) {
            throw DAMAGE_BUFFER_NO_CHANGE.create();
        }
        worldBorder.setDamageBuffer(f);
        commandSource.sendFeedback(new TranslationTextComponent("commands.worldborder.damage.buffer.success", String.format(Locale.ROOT, "%.2f", Float.valueOf(f))), false);
        return (int)f;
    }

    private static int setDamageAmount(CommandSource commandSource, float f) throws CommandSyntaxException {
        WorldBorder worldBorder = commandSource.getWorld().getWorldBorder();
        if (worldBorder.getDamagePerBlock() == (double)f) {
            throw DAMAGE_AMOUNT_NO_CHANGE.create();
        }
        worldBorder.setDamagePerBlock(f);
        commandSource.sendFeedback(new TranslationTextComponent("commands.worldborder.damage.amount.success", String.format(Locale.ROOT, "%.2f", Float.valueOf(f))), false);
        return (int)f;
    }

    private static int setWarningTime(CommandSource commandSource, int n) throws CommandSyntaxException {
        WorldBorder worldBorder = commandSource.getWorld().getWorldBorder();
        if (worldBorder.getWarningTime() == n) {
            throw WARNING_TIME_NO_CHANGE.create();
        }
        worldBorder.setWarningTime(n);
        commandSource.sendFeedback(new TranslationTextComponent("commands.worldborder.warning.time.success", n), false);
        return n;
    }

    private static int setWarningDistance(CommandSource commandSource, int n) throws CommandSyntaxException {
        WorldBorder worldBorder = commandSource.getWorld().getWorldBorder();
        if (worldBorder.getWarningDistance() == n) {
            throw WARNING_DISTANCE_NO_CHANGE.create();
        }
        worldBorder.setWarningDistance(n);
        commandSource.sendFeedback(new TranslationTextComponent("commands.worldborder.warning.distance.success", n), false);
        return n;
    }

    private static int getSize(CommandSource commandSource) {
        double d = commandSource.getWorld().getWorldBorder().getDiameter();
        commandSource.sendFeedback(new TranslationTextComponent("commands.worldborder.get", String.format(Locale.ROOT, "%.0f", d)), true);
        return MathHelper.floor(d + 0.5);
    }

    private static int setCenter(CommandSource commandSource, Vector2f vector2f) throws CommandSyntaxException {
        WorldBorder worldBorder = commandSource.getWorld().getWorldBorder();
        if (worldBorder.getCenterX() == (double)vector2f.x && worldBorder.getCenterZ() == (double)vector2f.y) {
            throw CENTER_NO_CHANGE.create();
        }
        worldBorder.setCenter(vector2f.x, vector2f.y);
        commandSource.sendFeedback(new TranslationTextComponent("commands.worldborder.center.success", String.format(Locale.ROOT, "%.2f", Float.valueOf(vector2f.x)), String.format("%.2f", Float.valueOf(vector2f.y))), false);
        return 1;
    }

    private static int setSize(CommandSource commandSource, double d, long l) throws CommandSyntaxException {
        WorldBorder worldBorder = commandSource.getWorld().getWorldBorder();
        double d2 = worldBorder.getDiameter();
        if (d2 == d) {
            throw SIZE_NO_CHANGE.create();
        }
        if (d < 1.0) {
            throw SIZE_TOO_SMALL.create();
        }
        if (d > 6.0E7) {
            throw SIZE_TOO_BIG.create();
        }
        if (l > 0L) {
            worldBorder.setTransition(d2, d, l);
            if (d > d2) {
                commandSource.sendFeedback(new TranslationTextComponent("commands.worldborder.set.grow", String.format(Locale.ROOT, "%.1f", d), Long.toString(l / 1000L)), false);
            } else {
                commandSource.sendFeedback(new TranslationTextComponent("commands.worldborder.set.shrink", String.format(Locale.ROOT, "%.1f", d), Long.toString(l / 1000L)), false);
            }
        } else {
            worldBorder.setTransition(d);
            commandSource.sendFeedback(new TranslationTextComponent("commands.worldborder.set.immediate", String.format(Locale.ROOT, "%.1f", d)), false);
        }
        return (int)(d - d2);
    }

    private static int lambda$register$10(CommandContext commandContext) throws CommandSyntaxException {
        return WorldBorderCommand.setWarningTime((CommandSource)commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "time"));
    }

    private static int lambda$register$9(CommandContext commandContext) throws CommandSyntaxException {
        return WorldBorderCommand.setWarningDistance((CommandSource)commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "distance"));
    }

    private static int lambda$register$8(CommandContext commandContext) throws CommandSyntaxException {
        return WorldBorderCommand.getSize((CommandSource)commandContext.getSource());
    }

    private static int lambda$register$7(CommandContext commandContext) throws CommandSyntaxException {
        return WorldBorderCommand.setDamageBuffer((CommandSource)commandContext.getSource(), FloatArgumentType.getFloat(commandContext, "distance"));
    }

    private static int lambda$register$6(CommandContext commandContext) throws CommandSyntaxException {
        return WorldBorderCommand.setDamageAmount((CommandSource)commandContext.getSource(), FloatArgumentType.getFloat(commandContext, "damagePerBlock"));
    }

    private static int lambda$register$5(CommandContext commandContext) throws CommandSyntaxException {
        return WorldBorderCommand.setCenter((CommandSource)commandContext.getSource(), Vec2Argument.getVec2f(commandContext, "pos"));
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return WorldBorderCommand.setSize((CommandSource)commandContext.getSource(), FloatArgumentType.getFloat(commandContext, "distance"), (long)IntegerArgumentType.getInteger(commandContext, "time") * 1000L);
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return WorldBorderCommand.setSize((CommandSource)commandContext.getSource(), FloatArgumentType.getFloat(commandContext, "distance"), 0L);
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return WorldBorderCommand.setSize((CommandSource)commandContext.getSource(), ((CommandSource)commandContext.getSource()).getWorld().getWorldBorder().getDiameter() + (double)FloatArgumentType.getFloat(commandContext, "distance"), ((CommandSource)commandContext.getSource()).getWorld().getWorldBorder().getTimeUntilTarget() + (long)IntegerArgumentType.getInteger(commandContext, "time") * 1000L);
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        return WorldBorderCommand.setSize((CommandSource)commandContext.getSource(), ((CommandSource)commandContext.getSource()).getWorld().getWorldBorder().getDiameter() + (double)FloatArgumentType.getFloat(commandContext, "distance"), 0L);
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }
}

