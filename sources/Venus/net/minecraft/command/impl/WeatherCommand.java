/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;

public class WeatherCommand {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("weather").requires(WeatherCommand::lambda$register$0)).then(((LiteralArgumentBuilder)Commands.literal("clear").executes(WeatherCommand::lambda$register$1)).then(Commands.argument("duration", IntegerArgumentType.integer(0, 1000000)).executes(WeatherCommand::lambda$register$2)))).then(((LiteralArgumentBuilder)Commands.literal("rain").executes(WeatherCommand::lambda$register$3)).then(Commands.argument("duration", IntegerArgumentType.integer(0, 1000000)).executes(WeatherCommand::lambda$register$4)))).then(((LiteralArgumentBuilder)Commands.literal("thunder").executes(WeatherCommand::lambda$register$5)).then(Commands.argument("duration", IntegerArgumentType.integer(0, 1000000)).executes(WeatherCommand::lambda$register$6))));
    }

    private static int setClear(CommandSource commandSource, int n) {
        commandSource.getWorld().func_241113_a_(n, 0, false, true);
        commandSource.sendFeedback(new TranslationTextComponent("commands.weather.set.clear"), false);
        return n;
    }

    private static int setRain(CommandSource commandSource, int n) {
        commandSource.getWorld().func_241113_a_(0, n, true, true);
        commandSource.sendFeedback(new TranslationTextComponent("commands.weather.set.rain"), false);
        return n;
    }

    private static int setThunder(CommandSource commandSource, int n) {
        commandSource.getWorld().func_241113_a_(0, n, true, false);
        commandSource.sendFeedback(new TranslationTextComponent("commands.weather.set.thunder"), false);
        return n;
    }

    private static int lambda$register$6(CommandContext commandContext) throws CommandSyntaxException {
        return WeatherCommand.setThunder((CommandSource)commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "duration") * 20);
    }

    private static int lambda$register$5(CommandContext commandContext) throws CommandSyntaxException {
        return WeatherCommand.setThunder((CommandSource)commandContext.getSource(), 6000);
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return WeatherCommand.setRain((CommandSource)commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "duration") * 20);
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return WeatherCommand.setRain((CommandSource)commandContext.getSource(), 6000);
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return WeatherCommand.setClear((CommandSource)commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "duration") * 20);
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        return WeatherCommand.setClear((CommandSource)commandContext.getSource(), 6000);
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }
}

