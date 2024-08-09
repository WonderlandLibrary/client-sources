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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ColorArgument
implements ArgumentType<TextFormatting> {
    private static final Collection<String> EXAMPLES = Arrays.asList("red", "green");
    public static final DynamicCommandExceptionType COLOR_INVALID = new DynamicCommandExceptionType(ColorArgument::lambda$static$0);

    private ColorArgument() {
    }

    public static ColorArgument color() {
        return new ColorArgument();
    }

    public static TextFormatting getColor(CommandContext<CommandSource> commandContext, String string) {
        return commandContext.getArgument(string, TextFormatting.class);
    }

    @Override
    public TextFormatting parse(StringReader stringReader) throws CommandSyntaxException {
        String string = stringReader.readUnquotedString();
        TextFormatting textFormatting = TextFormatting.getValueByName(string);
        if (textFormatting != null && !textFormatting.isFancyStyling()) {
            return textFormatting;
        }
        throw COLOR_INVALID.create(string);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        return ISuggestionProvider.suggest(TextFormatting.getValidValues(true, false), suggestionsBuilder);
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
        return new TranslationTextComponent("argument.color.invalid", object);
    }
}

