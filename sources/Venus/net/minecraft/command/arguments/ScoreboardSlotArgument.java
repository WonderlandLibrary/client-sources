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
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ScoreboardSlotArgument
implements ArgumentType<Integer> {
    private static final Collection<String> EXAMPLES = Arrays.asList("sidebar", "foo.bar");
    public static final DynamicCommandExceptionType SCOREBOARD_UNKNOWN_DISPLAY_SLOT = new DynamicCommandExceptionType(ScoreboardSlotArgument::lambda$static$0);

    private ScoreboardSlotArgument() {
    }

    public static ScoreboardSlotArgument scoreboardSlot() {
        return new ScoreboardSlotArgument();
    }

    public static int getScoreboardSlot(CommandContext<CommandSource> commandContext, String string) {
        return commandContext.getArgument(string, Integer.class);
    }

    @Override
    public Integer parse(StringReader stringReader) throws CommandSyntaxException {
        String string = stringReader.readUnquotedString();
        int n = Scoreboard.getObjectiveDisplaySlotNumber(string);
        if (n == -1) {
            throw SCOREBOARD_UNKNOWN_DISPLAY_SLOT.create(string);
        }
        return n;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        return ISuggestionProvider.suggest(Scoreboard.getDisplaySlotStrings(), suggestionsBuilder);
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
        return new TranslationTextComponent("argument.scoreboardDisplaySlot.invalid", object);
    }
}

