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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.test.TestFunctionInfo;
import net.minecraft.test.TestRegistry;
import net.minecraft.util.text.StringTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class TestArgArgument
implements ArgumentType<TestFunctionInfo> {
    private static final Collection<String> field_229664_a_ = Arrays.asList("techtests.piston", "techtests");

    @Override
    public TestFunctionInfo parse(StringReader stringReader) throws CommandSyntaxException {
        String string = stringReader.readUnquotedString();
        Optional<TestFunctionInfo> optional = TestRegistry.func_229537_d_(string);
        if (optional.isPresent()) {
            return optional.get();
        }
        StringTextComponent stringTextComponent = new StringTextComponent("No such test: " + string);
        throw new CommandSyntaxException(new SimpleCommandExceptionType(stringTextComponent), stringTextComponent);
    }

    public static TestArgArgument func_229665_a_() {
        return new TestArgArgument();
    }

    public static TestFunctionInfo func_229666_a_(CommandContext<CommandSource> commandContext, String string) {
        return commandContext.getArgument(string, TestFunctionInfo.class);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        Stream<String> stream = TestRegistry.func_229529_a_().stream().map(TestFunctionInfo::func_229657_a_);
        return ISuggestionProvider.suggest(stream, suggestionsBuilder);
    }

    @Override
    public Collection<String> getExamples() {
        return field_229664_a_;
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

