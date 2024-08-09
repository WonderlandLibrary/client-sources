/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.ILocationArgument;
import net.minecraft.command.arguments.LocationInput;
import net.minecraft.command.arguments.LocationPart;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ColumnPos;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ColumnPosArgument
implements ArgumentType<ILocationArgument> {
    private static final Collection<String> EXAMPLES = Arrays.asList("0 0", "~ ~", "~1 ~-2", "^ ^", "^-1 ^0");
    public static final SimpleCommandExceptionType INCOMPLETE_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("argument.pos2d.incomplete"));

    public static ColumnPosArgument columnPos() {
        return new ColumnPosArgument();
    }

    public static ColumnPos fromBlockPos(CommandContext<CommandSource> commandContext, String string) {
        BlockPos blockPos = commandContext.getArgument(string, ILocationArgument.class).getBlockPos(commandContext.getSource());
        return new ColumnPos(blockPos.getX(), blockPos.getZ());
    }

    @Override
    public ILocationArgument parse(StringReader stringReader) throws CommandSyntaxException {
        int n = stringReader.getCursor();
        if (!stringReader.canRead()) {
            throw INCOMPLETE_EXCEPTION.createWithContext(stringReader);
        }
        LocationPart locationPart = LocationPart.parseInt(stringReader);
        if (stringReader.canRead() && stringReader.peek() == ' ') {
            stringReader.skip();
            LocationPart locationPart2 = LocationPart.parseInt(stringReader);
            return new LocationInput(locationPart, new LocationPart(true, 0.0), locationPart2);
        }
        stringReader.setCursor(n);
        throw INCOMPLETE_EXCEPTION.createWithContext(stringReader);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        if (!(commandContext.getSource() instanceof ISuggestionProvider)) {
            return Suggestions.empty();
        }
        String string = suggestionsBuilder.getRemaining();
        Collection<ISuggestionProvider.Coordinates> collection = !string.isEmpty() && string.charAt(0) == '^' ? Collections.singleton(ISuggestionProvider.Coordinates.DEFAULT_LOCAL) : ((ISuggestionProvider)commandContext.getSource()).func_217294_q();
        return ISuggestionProvider.func_211269_a(string, collection, suggestionsBuilder, Commands.predicate(this::parse));
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

