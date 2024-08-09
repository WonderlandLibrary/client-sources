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
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class TeamArgument
implements ArgumentType<String> {
    private static final Collection<String> EXAMPLES = Arrays.asList("foo", "123");
    private static final DynamicCommandExceptionType TEAM_NOT_FOUND = new DynamicCommandExceptionType(TeamArgument::lambda$static$0);

    public static TeamArgument team() {
        return new TeamArgument();
    }

    public static ScorePlayerTeam getTeam(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        String string2 = commandContext.getArgument(string, String.class);
        ServerScoreboard serverScoreboard = commandContext.getSource().getServer().getScoreboard();
        ScorePlayerTeam scorePlayerTeam = serverScoreboard.getTeam(string2);
        if (scorePlayerTeam == null) {
            throw TEAM_NOT_FOUND.create(string2);
        }
        return scorePlayerTeam;
    }

    @Override
    public String parse(StringReader stringReader) throws CommandSyntaxException {
        return stringReader.readUnquotedString();
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        return commandContext.getSource() instanceof ISuggestionProvider ? ISuggestionProvider.suggest(((ISuggestionProvider)commandContext.getSource()).getTeamNames(), suggestionsBuilder) : Suggestions.empty();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("team.notFound", object);
    }
}

