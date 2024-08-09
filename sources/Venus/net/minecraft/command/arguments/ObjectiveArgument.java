/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ObjectiveArgument
implements ArgumentType<String> {
    private static final Collection<String> EXAMPLES = Arrays.asList("foo", "*", "012");
    private static final DynamicCommandExceptionType OBJECTIVE_NOT_FOUND = new DynamicCommandExceptionType(ObjectiveArgument::lambda$static$0);
    private static final DynamicCommandExceptionType OBJECTIVE_READ_ONLY = new DynamicCommandExceptionType(ObjectiveArgument::lambda$static$1);
    public static final DynamicCommandExceptionType OBJECTIVE_NAME_TOO_LONG = new DynamicCommandExceptionType(ObjectiveArgument::lambda$static$2);

    public static ObjectiveArgument objective() {
        return new ObjectiveArgument();
    }

    public static ScoreObjective getObjective(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        String string2 = commandContext.getArgument(string, String.class);
        ServerScoreboard serverScoreboard = commandContext.getSource().getServer().getScoreboard();
        ScoreObjective scoreObjective = serverScoreboard.getObjective(string2);
        if (scoreObjective == null) {
            throw OBJECTIVE_NOT_FOUND.create(string2);
        }
        return scoreObjective;
    }

    public static ScoreObjective getWritableObjective(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        ScoreObjective scoreObjective = ObjectiveArgument.getObjective(commandContext, string);
        if (scoreObjective.getCriteria().isReadOnly()) {
            throw OBJECTIVE_READ_ONLY.create(scoreObjective.getName());
        }
        return scoreObjective;
    }

    @Override
    public String parse(StringReader stringReader) throws CommandSyntaxException {
        String string = stringReader.readUnquotedString();
        if (string.length() > 16) {
            throw OBJECTIVE_NAME_TOO_LONG.create(16);
        }
        return string;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        if (commandContext.getSource() instanceof CommandSource) {
            return ISuggestionProvider.suggest(((CommandSource)commandContext.getSource()).getServer().getScoreboard().func_197897_d(), suggestionsBuilder);
        }
        if (commandContext.getSource() instanceof ISuggestionProvider) {
            ISuggestionProvider iSuggestionProvider = (ISuggestionProvider)commandContext.getSource();
            return iSuggestionProvider.getSuggestionsFromServer(commandContext, suggestionsBuilder);
        }
        return Suggestions.empty();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    private static Message lambda$static$2(Object object) {
        return new TranslationTextComponent("commands.scoreboard.objectives.add.longName", object);
    }

    private static Message lambda$static$1(Object object) {
        return new TranslationTextComponent("arguments.objective.readonly", object);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("arguments.objective.notFound", object);
    }
}

