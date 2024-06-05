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

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class Suggestor {
    private final SuggestionsBuilder builder;

    public Suggestor(SuggestionsBuilder builder) {
        this.builder = builder;
    }

    public Suggestor add(String str) {
        if (str.toLowerCase().startsWith(builder.getRemainingLowerCase()))
            builder.suggest(str);
        return this;
    }

    public Suggestor addAll(Iterable<String> list) {
        list.forEach(this::add);
        return this;
    }

    public Suggestor addAll(Stream<String> stream) {
        stream.forEach(this::add);
        return this;
    }

    public CompletableFuture<Suggestions> buildFuture() {
        return builder.buildFuture();
    }
}
