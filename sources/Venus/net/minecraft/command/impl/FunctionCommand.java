/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.advancements.FunctionManager;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.FunctionObject;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.FunctionArgument;
import net.minecraft.util.text.TranslationTextComponent;

public class FunctionCommand {
    public static final SuggestionProvider<CommandSource> FUNCTION_SUGGESTER = FunctionCommand::lambda$static$0;

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("function").requires(FunctionCommand::lambda$register$1)).then(Commands.argument("name", FunctionArgument.function()).suggests(FUNCTION_SUGGESTER).executes(FunctionCommand::lambda$register$2)));
    }

    private static int executeFunctions(CommandSource commandSource, Collection<FunctionObject> collection) {
        int n = 0;
        for (FunctionObject functionObject : collection) {
            n += commandSource.getServer().getFunctionManager().execute(functionObject, commandSource.withFeedbackDisabled().withMinPermissionLevel(2));
        }
        if (collection.size() == 1) {
            commandSource.sendFeedback(new TranslationTextComponent("commands.function.success.single", n, collection.iterator().next().getId()), false);
        } else {
            commandSource.sendFeedback(new TranslationTextComponent("commands.function.success.multiple", n, collection.size()), false);
        }
        return n;
    }

    private static int lambda$register$2(CommandContext commandContext) throws CommandSyntaxException {
        return FunctionCommand.executeFunctions((CommandSource)commandContext.getSource(), FunctionArgument.getFunctions(commandContext, "name"));
    }

    private static boolean lambda$register$1(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static CompletableFuture lambda$static$0(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        FunctionManager functionManager = ((CommandSource)commandContext.getSource()).getServer().getFunctionManager();
        ISuggestionProvider.suggestIterable(functionManager.getFunctionTagIdentifiers(), suggestionsBuilder, "#");
        return ISuggestionProvider.suggestIterable(functionManager.getFunctionIdentifiers(), suggestionsBuilder);
    }
}

