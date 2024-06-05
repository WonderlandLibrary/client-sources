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
import de.dietrichpaul.clientbase.feature.hack.Hack;
import net.minecraft.text.Text;

import java.util.concurrent.CompletableFuture;

public class HackArgumentType implements ArgumentType<Hack> {
    private final static DynamicCommandExceptionType HACK_DOES_NOT_EXIST = new DynamicCommandExceptionType(name -> Text.of("The hack " + name + " does not exist."));

    public static HackArgumentType hack() {
        return new HackArgumentType();
    }

    public static Hack getHack(CommandContext<?> ctx, String name) {
        return ctx.getArgument(name, Hack.class);
    }

    @Override
    public Hack parse(StringReader reader) throws CommandSyntaxException {
        String name = reader.readString();
        Hack hack = ClientBase.INSTANCE.getHackList().getHack(name);
        if (hack == null) {
            throw HACK_DOES_NOT_EXIST.createWithContext(reader, name);
        }
        return hack;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return new Suggestor(builder)
                .addAll(ClientBase.INSTANCE.getHackList().getHacks().stream().map(Hack::getName))
                .buildFuture();
    }
}
