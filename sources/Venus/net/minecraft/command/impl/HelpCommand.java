/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.google.common.collect.Iterables;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.CommandNode;
import java.util.Map;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class HelpCommand {
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("commands.help.failed"));

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("help").executes(arg_0 -> HelpCommand.lambda$register$0(commandDispatcher, arg_0))).then(Commands.argument("command", StringArgumentType.greedyString()).executes(arg_0 -> HelpCommand.lambda$register$1(commandDispatcher, arg_0))));
    }

    private static int lambda$register$1(CommandDispatcher commandDispatcher, CommandContext commandContext) throws CommandSyntaxException {
        ParseResults<CommandSource> parseResults = commandDispatcher.parse(StringArgumentType.getString(commandContext, "command"), (CommandSource)commandContext.getSource());
        if (parseResults.getContext().getNodes().isEmpty()) {
            throw FAILED_EXCEPTION.create();
        }
        Map<CommandNode<CommandSource>, String> map = commandDispatcher.getSmartUsage(Iterables.getLast(parseResults.getContext().getNodes()).getNode(), (CommandSource)commandContext.getSource());
        for (String string : map.values()) {
            ((CommandSource)commandContext.getSource()).sendFeedback(new StringTextComponent("/" + parseResults.getReader().getString() + " " + string), true);
        }
        return map.size();
    }

    private static int lambda$register$0(CommandDispatcher commandDispatcher, CommandContext commandContext) throws CommandSyntaxException {
        Map<CommandNode<CommandSource>, String> map = commandDispatcher.getSmartUsage(commandDispatcher.getRoot(), (CommandSource)commandContext.getSource());
        for (String string : map.values()) {
            ((CommandSource)commandContext.getSource()).sendFeedback(new StringTextComponent("/" + string), true);
        }
        return map.size();
    }
}

