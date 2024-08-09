/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class TimeArgument
implements ArgumentType<Integer> {
    private static final Collection<String> field_218093_a = Arrays.asList("0d", "0s", "0t", "0");
    private static final SimpleCommandExceptionType field_218094_b = new SimpleCommandExceptionType(new TranslationTextComponent("argument.time.invalid_unit"));
    private static final DynamicCommandExceptionType field_218095_c = new DynamicCommandExceptionType(TimeArgument::lambda$static$0);
    private static final Object2IntMap<String> field_218096_d = new Object2IntOpenHashMap<String>();

    public static TimeArgument func_218091_a() {
        return new TimeArgument();
    }

    @Override
    public Integer parse(StringReader stringReader) throws CommandSyntaxException {
        float f = stringReader.readFloat();
        String string = stringReader.readUnquotedString();
        int n = field_218096_d.getOrDefault((Object)string, 0);
        if (n == 0) {
            throw field_218094_b.create();
        }
        int n2 = Math.round(f * (float)n);
        if (n2 < 0) {
            throw field_218095_c.create(n2);
        }
        return n2;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        StringReader stringReader = new StringReader(suggestionsBuilder.getRemaining());
        try {
            stringReader.readFloat();
        } catch (CommandSyntaxException commandSyntaxException) {
            return suggestionsBuilder.buildFuture();
        }
        return ISuggestionProvider.suggest(field_218096_d.keySet(), suggestionsBuilder.createOffset(suggestionsBuilder.getStart() + stringReader.getCursor()));
    }

    @Override
    public Collection<String> getExamples() {
        return field_218093_a;
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("argument.time.invalid_tick_count", object);
    }

    static {
        field_218096_d.put("d", 24000);
        field_218096_d.put("s", 20);
        field_218096_d.put("t", 1);
        field_218096_d.put("", 1);
    }
}

