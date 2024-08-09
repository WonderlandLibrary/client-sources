/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.TimeArgument;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

public class TimeCommand {
    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("time").requires(TimeCommand::lambda$register$0)).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("set").then((ArgumentBuilder<CommandSource, ?>)Commands.literal("day").executes(TimeCommand::lambda$register$1))).then(Commands.literal("noon").executes(TimeCommand::lambda$register$2))).then(Commands.literal("night").executes(TimeCommand::lambda$register$3))).then(Commands.literal("midnight").executes(TimeCommand::lambda$register$4))).then(Commands.argument("time", TimeArgument.func_218091_a()).executes(TimeCommand::lambda$register$5)))).then(Commands.literal("add").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("time", TimeArgument.func_218091_a()).executes(TimeCommand::lambda$register$6)))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("query").then((ArgumentBuilder<CommandSource, ?>)Commands.literal("daytime").executes(TimeCommand::lambda$register$7))).then(Commands.literal("gametime").executes(TimeCommand::lambda$register$8))).then(Commands.literal("day").executes(TimeCommand::lambda$register$9))));
    }

    private static int getDayTime(ServerWorld serverWorld) {
        return (int)(serverWorld.getDayTime() % 24000L);
    }

    private static int sendQueryResults(CommandSource commandSource, int n) {
        commandSource.sendFeedback(new TranslationTextComponent("commands.time.query", n), true);
        return n;
    }

    public static int setTime(CommandSource commandSource, int n) {
        for (ServerWorld serverWorld : commandSource.getServer().getWorlds()) {
            serverWorld.func_241114_a_(n);
        }
        commandSource.sendFeedback(new TranslationTextComponent("commands.time.set", n), false);
        return TimeCommand.getDayTime(commandSource.getWorld());
    }

    public static int addTime(CommandSource commandSource, int n) {
        for (ServerWorld serverWorld : commandSource.getServer().getWorlds()) {
            serverWorld.func_241114_a_(serverWorld.getDayTime() + (long)n);
        }
        int n2 = TimeCommand.getDayTime(commandSource.getWorld());
        commandSource.sendFeedback(new TranslationTextComponent("commands.time.set", n2), false);
        return n2;
    }

    private static int lambda$register$9(CommandContext commandContext) throws CommandSyntaxException {
        return TimeCommand.sendQueryResults((CommandSource)commandContext.getSource(), (int)(((CommandSource)commandContext.getSource()).getWorld().getDayTime() / 24000L % Integer.MAX_VALUE));
    }

    private static int lambda$register$8(CommandContext commandContext) throws CommandSyntaxException {
        return TimeCommand.sendQueryResults((CommandSource)commandContext.getSource(), (int)(((CommandSource)commandContext.getSource()).getWorld().getGameTime() % Integer.MAX_VALUE));
    }

    private static int lambda$register$7(CommandContext commandContext) throws CommandSyntaxException {
        return TimeCommand.sendQueryResults((CommandSource)commandContext.getSource(), TimeCommand.getDayTime(((CommandSource)commandContext.getSource()).getWorld()));
    }

    private static int lambda$register$6(CommandContext commandContext) throws CommandSyntaxException {
        return TimeCommand.addTime((CommandSource)commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "time"));
    }

    private static int lambda$register$5(CommandContext commandContext) throws CommandSyntaxException {
        return TimeCommand.setTime((CommandSource)commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "time"));
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return TimeCommand.setTime((CommandSource)commandContext.getSource(), 18000);
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return TimeCommand.setTime((CommandSource)commandContext.getSource(), 13000);
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return TimeCommand.setTime((CommandSource)commandContext.getSource(), 6000);
    }

    private static int lambda$register$1(CommandContext commandContext) throws CommandSyntaxException {
        return TimeCommand.setTime((CommandSource)commandContext.getSource(), 1000);
    }

    private static boolean lambda$register$0(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }
}

