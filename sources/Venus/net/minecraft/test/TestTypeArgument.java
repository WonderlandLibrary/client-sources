/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.test;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.test.TestRegistry;
import net.minecraft.util.text.StringTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class TestTypeArgument
implements ArgumentType<String> {
    private static final Collection<String> field_229610_a_ = Arrays.asList("techtests", "mobtests");

    @Override
    public String parse(StringReader stringReader) throws CommandSyntaxException {
        String string = stringReader.readUnquotedString();
        if (TestRegistry.func_229534_b_(string)) {
            return string;
        }
        StringTextComponent stringTextComponent = new StringTextComponent("No such test class: " + string);
        throw new CommandSyntaxException(new SimpleCommandExceptionType(stringTextComponent), stringTextComponent);
    }

    public static TestTypeArgument func_229611_a_() {
        return new TestTypeArgument();
    }

    public static String func_229612_a_(CommandContext<CommandSource> commandContext, String string) {
        return commandContext.getArgument(string, String.class);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        return ISuggestionProvider.suggest(TestRegistry.func_229533_b_().stream(), suggestionsBuilder);
    }

    @Override
    public Collection<String> getExamples() {
        return field_229610_a_;
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

