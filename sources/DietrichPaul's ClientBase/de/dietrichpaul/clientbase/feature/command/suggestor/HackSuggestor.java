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
package de.dietrichpaul.clientbase.feature.command.suggestor;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.feature.hack.Hack;
import net.minecraft.command.CommandSource;

import java.util.concurrent.CompletableFuture;

public class HackSuggestor implements SuggestionProvider<CommandSource> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        return new Suggestor(builder)
                .addAll(ClientBase.INSTANCE.getHackList().getHacks().stream().map(Hack::getName))
                .buildFuture();
    }
}
