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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Vec3Argument
implements ArgumentType<ILocationArgument> {
    private static final Collection<String> EXAMPLES = Arrays.asList("0 0 0", "~ ~ ~", "^ ^ ^", "^1 ^ ^-5", "0.1 -0.5 .9", "~0.5 ~1 ~-5");
    public static final SimpleCommandExceptionType POS_INCOMPLETE = new SimpleCommandExceptionType(new TranslationTextComponent("argument.pos3d.incomplete"));
    public static final SimpleCommandExceptionType POS_MIXED_TYPES = new SimpleCommandExceptionType(new TranslationTextComponent("argument.pos.mixed"));
    private final boolean centerIntegers;

    public Vec3Argument(boolean bl) {
        this.centerIntegers = bl;
    }

    public static Vec3Argument vec3() {
        return new Vec3Argument(true);
    }

    public static Vec3Argument vec3(boolean bl) {
        return new Vec3Argument(bl);
    }

    public static Vector3d getVec3(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        return commandContext.getArgument(string, ILocationArgument.class).getPosition(commandContext.getSource());
    }

    public static ILocationArgument getLocation(CommandContext<CommandSource> commandContext, String string) {
        return commandContext.getArgument(string, ILocationArgument.class);
    }

    @Override
    public ILocationArgument parse(StringReader stringReader) throws CommandSyntaxException {
        return stringReader.canRead() && stringReader.peek() == '^' ? LocalLocationArgument.parse(stringReader) : LocationInput.parseDouble(stringReader, this.centerIntegers);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        if (!(commandContext.getSource() instanceof ISuggestionProvider)) {
            return Suggestions.empty();
        }
        String string = suggestionsBuilder.getRemaining();
        Collection<ISuggestionProvider.Coordinates> collection = !string.isEmpty() && string.charAt(0) == '^' ? Collections.singleton(ISuggestionProvider.Coordinates.DEFAULT_LOCAL) : ((ISuggestionProvider)commandContext.getSource()).func_217293_r();
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

