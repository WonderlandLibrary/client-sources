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
import net.minecraft.command.arguments.LocalLocationArgument;
import net.minecraft.command.arguments.LocationInput;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BlockPosArgument
implements ArgumentType<ILocationArgument> {
    private static final Collection<String> EXAMPLES = Arrays.asList("0 0 0", "~ ~ ~", "^ ^ ^", "^1 ^ ^-5", "~0.5 ~1 ~-5");
    public static final SimpleCommandExceptionType POS_UNLOADED = new SimpleCommandExceptionType(new TranslationTextComponent("argument.pos.unloaded"));
    public static final SimpleCommandExceptionType POS_OUT_OF_WORLD = new SimpleCommandExceptionType(new TranslationTextComponent("argument.pos.outofworld"));

    public static BlockPosArgument blockPos() {
        return new BlockPosArgument();
    }

    public static BlockPos getLoadedBlockPos(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        BlockPos blockPos = commandContext.getArgument(string, ILocationArgument.class).getBlockPos(commandContext.getSource());
        if (!commandContext.getSource().getWorld().isBlockLoaded(blockPos)) {
            throw POS_UNLOADED.create();
        }
        commandContext.getSource().getWorld();
        if (!ServerWorld.isValid(blockPos)) {
            throw POS_OUT_OF_WORLD.create();
        }
        return blockPos;
    }

    public static BlockPos getBlockPos(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        return commandContext.getArgument(string, ILocationArgument.class).getBlockPos(commandContext.getSource());
    }

    @Override
    public ILocationArgument parse(StringReader stringReader) throws CommandSyntaxException {
        return stringReader.canRead() && stringReader.peek() == '^' ? LocalLocationArgument.parse(stringReader) : LocationInput.parseInt(stringReader);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        if (!(commandContext.getSource() instanceof ISuggestionProvider)) {
            return Suggestions.empty();
        }
        String string = suggestionsBuilder.getRemaining();
        Collection<ISuggestionProvider.Coordinates> collection = !string.isEmpty() && string.charAt(0) == '^' ? Collections.singleton(ISuggestionProvider.Coordinates.DEFAULT_LOCAL) : ((ISuggestionProvider)commandContext.getSource()).func_217294_q();
        return ISuggestionProvider.func_209000_a(string, collection, suggestionsBuilder, Commands.predicate(this::parse));
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

