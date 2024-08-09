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
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.BlockStateInput;
import net.minecraft.command.arguments.BlockStateParser;
import net.minecraft.tags.BlockTags;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BlockStateArgument
implements ArgumentType<BlockStateInput> {
    private static final Collection<String> EXAMPLES = Arrays.asList("stone", "minecraft:stone", "stone[foo=bar]", "foo{bar=baz}");

    public static BlockStateArgument blockState() {
        return new BlockStateArgument();
    }

    @Override
    public BlockStateInput parse(StringReader stringReader) throws CommandSyntaxException {
        BlockStateParser blockStateParser = new BlockStateParser(stringReader, false).parse(false);
        return new BlockStateInput(blockStateParser.getState(), blockStateParser.getProperties().keySet(), blockStateParser.getNbt());
    }

    public static BlockStateInput getBlockState(CommandContext<CommandSource> commandContext, String string) {
        return commandContext.getArgument(string, BlockStateInput.class);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        StringReader stringReader = new StringReader(suggestionsBuilder.getInput());
        stringReader.setCursor(suggestionsBuilder.getStart());
        BlockStateParser blockStateParser = new BlockStateParser(stringReader, false);
        try {
            blockStateParser.parse(false);
        } catch (CommandSyntaxException commandSyntaxException) {
            // empty catch block
        }
        return blockStateParser.getSuggestions(suggestionsBuilder, BlockTags.getCollection());
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

