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
import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.feature.command.suggestor.Suggestor;
import net.minecraft.client.util.InputUtil;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class KeyBindingArgumentType implements ArgumentType<InputUtil.Key> {
    private final DynamicCommandExceptionType UNKNOWN_KEY = new DynamicCommandExceptionType(k -> Text.literal("Unknown key: " + k));
    private final DynamicCommandExceptionType UNBOUND_KEY = new DynamicCommandExceptionType(k -> Text.literal("Unbound key: " + k));

    public static KeyBindingArgumentType boundKey() {
        return new KeyBindingArgumentType();
    }

    public static InputUtil.Key getBoundKey(CommandContext<CommandSource> ctx, String key) {
        return ctx.getArgument(key, InputUtil.Key.class);
    }

    @Override
    public InputUtil.Key parse(StringReader reader) throws CommandSyntaxException {
        String keyName = reader.readString().toLowerCase(Locale.ROOT);
        try {
            InputUtil.Key key = InputUtil.fromTranslationKey("key.keyboard." + keyName);
            if (ClientBase.INSTANCE.getKeybindingList().getBindings(key) == null)
                throw UNBOUND_KEY.createWithContext(reader, keyName);
            return key;
        } catch (IllegalArgumentException e) {
            throw UNKNOWN_KEY.createWithContext(reader, keyName);
        }
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return new Suggestor(builder)
                .addAll(InputUtil.Key.KEYS.entrySet().stream()
                        .filter(e -> e.getValue().getCategory() == InputUtil.Type.KEYSYM
                                && ClientBase.INSTANCE.getKeybindingList().getBindings(e.getValue()) != null)
                        .map(e -> e.getKey().substring("key.keyboard.".length()))
                )
                .buildFuture();
    }
}
