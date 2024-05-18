/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.feature.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import de.dietrichpaul.clientbase.feature.command.suggestor.Suggestor;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class EnumArgumentType<T extends Enum<T>> implements ArgumentType<T> {
    private final static DynamicCommandExceptionType UNKNOWN_FIELD = new DynamicCommandExceptionType(name -> Text.literal("Unknown enum field " + name + "."));

    private final T[] values;

    private EnumArgumentType(T[] values) {
        this.values = values;
    }

    public static <T extends Enum<T>> EnumArgumentType<T> enumField(T[] values) {
        return new EnumArgumentType<>(values);
    }

    public static <T extends Enum<T>> T getEnumField(CommandContext<?> context, String name, Class<T> clazz) {
        return context.getArgument(name, clazz);
    }

    @Override
    public T parse(StringReader reader) throws CommandSyntaxException {
        String name = reader.readString();
        for (T t : values) {
            if (name.equals(t.toString())) {
                return t;
            }
        }
        throw UNKNOWN_FIELD.createWithContext(reader, name);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return new Suggestor(builder)
                .addAll(Arrays.stream(values).map(T::toString))
                .buildFuture();
    }
}
