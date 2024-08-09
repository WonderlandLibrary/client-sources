/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.arguments.ItemInput;
import net.minecraft.command.arguments.ItemParser;
import net.minecraft.tags.ItemTags;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ItemArgument
implements ArgumentType<ItemInput> {
    private static final Collection<String> EXAMPLES = Arrays.asList("stick", "minecraft:stick", "stick{foo=bar}");

    public static ItemArgument item() {
        return new ItemArgument();
    }

    @Override
    public ItemInput parse(StringReader stringReader) throws CommandSyntaxException {
        ItemParser itemParser = new ItemParser(stringReader, false).parse();
        return new ItemInput(itemParser.getItem(), itemParser.getNbt());
    }

    public static <S> ItemInput getItem(CommandContext<S> commandContext, String string) {
        return commandContext.getArgument(string, ItemInput.class);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        StringReader stringReader = new StringReader(suggestionsBuilder.getInput());
        stringReader.setCursor(suggestionsBuilder.getStart());
        ItemParser itemParser = new ItemParser(stringReader, false);
        try {
            itemParser.parse();
        } catch (CommandSyntaxException commandSyntaxException) {
            // empty catch block
        }
        return itemParser.fillSuggestions(suggestionsBuilder, ItemTags.getCollection());
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

