/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.FunctionObject;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.TimedFunction;
import net.minecraft.command.TimedFunctionTag;
import net.minecraft.command.TimerCallbackManager;
import net.minecraft.command.arguments.FunctionArgument;
import net.minecraft.command.arguments.TimeArgument;
import net.minecraft.command.impl.FunctionCommand;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class ScheduleCommand {
    private static final SimpleCommandExceptionType field_218913_a = new SimpleCommandExceptionType(new TranslationTextComponent("commands.schedule.same_tick"));
    private static final DynamicCommandExceptionType field_229811_b_ = new DynamicCommandExceptionType(ScheduleCommand::lambda$static$0);
    private static final SuggestionProvider<CommandSource> field_229812_c_ = ScheduleCommand::lambda$static$1;

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("schedule").requires(ScheduleCommand::lambda$register$2)).then(Commands.literal("function").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("function", FunctionArgument.function()).suggests(FunctionCommand.FUNCTION_SUGGESTER).then((ArgumentBuilder<CommandSource, ?>)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("time", TimeArgument.func_218091_a()).executes(ScheduleCommand::lambda$register$3)).then(Commands.literal("append").executes(ScheduleCommand::lambda$register$4))).then(Commands.literal("replace").executes(ScheduleCommand::lambda$register$5)))))).then(Commands.literal("clear").then((ArgumentBuilder<CommandSource, ?>)Commands.argument("function", StringArgumentType.greedyString()).suggests(field_229812_c_).executes(ScheduleCommand::lambda$register$6))));
    }

    private static int func_241063_a_(CommandSource commandSource, Pair<ResourceLocation, Either<FunctionObject, ITag<FunctionObject>>> pair, int n, boolean bl) throws CommandSyntaxException {
        if (n == 0) {
            throw field_218913_a.create();
        }
        long l = commandSource.getWorld().getGameTime() + (long)n;
        ResourceLocation resourceLocation = pair.getFirst();
        TimerCallbackManager<MinecraftServer> timerCallbackManager = commandSource.getServer().func_240793_aU_().getServerWorldInfo().getScheduledEvents();
        pair.getSecond().ifLeft(arg_0 -> ScheduleCommand.lambda$func_241063_a_$7(resourceLocation, bl, timerCallbackManager, l, commandSource, n, arg_0)).ifRight(arg_0 -> ScheduleCommand.lambda$func_241063_a_$8(resourceLocation, bl, timerCallbackManager, l, commandSource, n, arg_0));
        return (int)Math.floorMod(l, Integer.MAX_VALUE);
    }

    private static int func_229817_a_(CommandSource commandSource, String string) throws CommandSyntaxException {
        int n = commandSource.getServer().func_240793_aU_().getServerWorldInfo().getScheduledEvents().func_227575_a_(string);
        if (n == 0) {
            throw field_229811_b_.create(string);
        }
        commandSource.sendFeedback(new TranslationTextComponent("commands.schedule.cleared.success", n, string), false);
        return n;
    }

    private static void lambda$func_241063_a_$8(ResourceLocation resourceLocation, boolean bl, TimerCallbackManager timerCallbackManager, long l, CommandSource commandSource, int n, ITag iTag) {
        String string = "#" + resourceLocation.toString();
        if (bl) {
            timerCallbackManager.func_227575_a_(string);
        }
        timerCallbackManager.func_227576_a_(string, l, new TimedFunctionTag(resourceLocation));
        commandSource.sendFeedback(new TranslationTextComponent("commands.schedule.created.tag", resourceLocation, n, l), false);
    }

    private static void lambda$func_241063_a_$7(ResourceLocation resourceLocation, boolean bl, TimerCallbackManager timerCallbackManager, long l, CommandSource commandSource, int n, FunctionObject functionObject) {
        String string = resourceLocation.toString();
        if (bl) {
            timerCallbackManager.func_227575_a_(string);
        }
        timerCallbackManager.func_227576_a_(string, l, new TimedFunction(resourceLocation));
        commandSource.sendFeedback(new TranslationTextComponent("commands.schedule.created.function", resourceLocation, n, l), false);
    }

    private static int lambda$register$6(CommandContext commandContext) throws CommandSyntaxException {
        return ScheduleCommand.func_229817_a_((CommandSource)commandContext.getSource(), StringArgumentType.getString(commandContext, "function"));
    }

    private static int lambda$register$5(CommandContext commandContext) throws CommandSyntaxException {
        return ScheduleCommand.func_241063_a_((CommandSource)commandContext.getSource(), FunctionArgument.func_218110_b(commandContext, "function"), IntegerArgumentType.getInteger(commandContext, "time"), true);
    }

    private static int lambda$register$4(CommandContext commandContext) throws CommandSyntaxException {
        return ScheduleCommand.func_241063_a_((CommandSource)commandContext.getSource(), FunctionArgument.func_218110_b(commandContext, "function"), IntegerArgumentType.getInteger(commandContext, "time"), false);
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return ScheduleCommand.func_241063_a_((CommandSource)commandContext.getSource(), FunctionArgument.func_218110_b(commandContext, "function"), IntegerArgumentType.getInteger(commandContext, "time"), true);
    }

    private static boolean lambda$register$2(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static CompletableFuture lambda$static$1(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        return ISuggestionProvider.suggest(((CommandSource)commandContext.getSource()).getServer().func_240793_aU_().getServerWorldInfo().getScheduledEvents().func_227574_a_(), suggestionsBuilder);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("commands.schedule.cleared.failure", object);
    }
}

