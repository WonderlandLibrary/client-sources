/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.google.common.collect.Lists;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ObjectiveCriteriaArgument
implements ArgumentType<ScoreCriteria> {
    private static final Collection<String> EXAMPLES = Arrays.asList("foo", "foo.bar.baz", "minecraft:foo");
    public static final DynamicCommandExceptionType OBJECTIVE_INVALID_CRITERIA = new DynamicCommandExceptionType(ObjectiveCriteriaArgument::lambda$static$0);

    private ObjectiveCriteriaArgument() {
    }

    public static ObjectiveCriteriaArgument objectiveCriteria() {
        return new ObjectiveCriteriaArgument();
    }

    public static ScoreCriteria getObjectiveCriteria(CommandContext<CommandSource> commandContext, String string) {
        return commandContext.getArgument(string, ScoreCriteria.class);
    }

    @Override
    public ScoreCriteria parse(StringReader stringReader) throws CommandSyntaxException {
        int n = stringReader.getCursor();
        while (stringReader.canRead() && stringReader.peek() != ' ') {
            stringReader.skip();
        }
        String string = stringReader.getString().substring(n, stringReader.getCursor());
        return ScoreCriteria.func_216390_a(string).orElseThrow(() -> ObjectiveCriteriaArgument.lambda$parse$1(stringReader, n, string));
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        ArrayList<String> arrayList = Lists.newArrayList(ScoreCriteria.INSTANCES.keySet());
        for (StatType statType : Registry.STATS) {
            for (Object t : statType.getRegistry()) {
                String string = this.makeStatName(statType, t);
                arrayList.add(string);
            }
        }
        return ISuggestionProvider.suggest(arrayList, suggestionsBuilder);
    }

    public <T> String makeStatName(StatType<T> statType, Object object) {
        return Stat.buildName(statType, object);
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    private static CommandSyntaxException lambda$parse$1(StringReader stringReader, int n, String string) {
        stringReader.setCursor(n);
        return OBJECTIVE_INVALID_CRITERIA.create(string);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("argument.criteria.invalid", object);
    }
}

