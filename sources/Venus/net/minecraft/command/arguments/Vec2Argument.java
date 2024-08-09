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
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Vec2Argument
implements ArgumentType<ILocationArgument> {
    private static final Collection<String> EXAMPLES = Arrays.asList("0 0", "~ ~", "0.1 -0.5", "~1 ~-2");
    public static final SimpleCommandExceptionType VEC2_INCOMPLETE = new SimpleCommandExceptionType(new TranslationTextComponent("argument.pos2d.incomplete"));
    private final boolean centerIntegers;

    public Vec2Argument(boolean bl) {
        this.centerIntegers = bl;
    }

    public static Vec2Argument vec2() {
        return new Vec2Argument(true);
    }

    public static Vector2f getVec2f(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        Vector3d vector3d = commandContext.getArgument(string, ILocationArgument.class).getPosition(commandContext.getSource());
        return new Vector2f((float)vector3d.x, (float)vector3d.z);
    }

    @Override
    public ILocationArgument parse(StringReader stringReader) throws CommandSyntaxException {
        int n = stringReader.getCursor();
        if (!stringReader.canRead()) {
            throw VEC2_INCOMPLETE.createWithContext(stringReader);
        }
        LocationPart locationPart = LocationPart.parseDouble(stringReader, this.centerIntegers);
        if (stringReader.canRead() && stringReader.peek() == ' ') {
            stringReader.skip();
            LocationPart locationPart2 = LocationPart.parseDouble(stringReader, this.centerIntegers);
            return new LocationInput(locationPart, new LocationPart(true, 0.0), locationPart2);
        }
        stringReader.setCursor(n);
        throw VEC2_INCOMPLETE.createWithContext(stringReader);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        if (!(commandContext.getSource() instanceof ISuggestionProvider)) {
            return Suggestions.empty();
        }
        String string = suggestionsBuilder.getRemaining();
        Collection<ISuggestionProvider.Coordinates> collection = !string.isEmpty() && string.charAt(0) == '^' ? Collections.singleton(ISuggestionProvider.Coordinates.DEFAULT_LOCAL) : ((ISuggestionProvider)commandContext.getSource()).func_217293_r();
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

