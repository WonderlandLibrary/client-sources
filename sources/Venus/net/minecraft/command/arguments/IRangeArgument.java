/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.command.CommandSource;

public interface IRangeArgument<T extends MinMaxBounds<?>>
extends ArgumentType<T> {
    public static IntRange intRange() {
        return new IntRange();
    }

    public static FloatRange func_243493_b() {
        return new FloatRange();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class IntRange
    implements IRangeArgument<MinMaxBounds.IntBound> {
        private static final Collection<String> EXAMPLES = Arrays.asList("0..5", "0", "-5", "-100..", "..100");

        public static MinMaxBounds.IntBound getIntRange(CommandContext<CommandSource> commandContext, String string) {
            return commandContext.getArgument(string, MinMaxBounds.IntBound.class);
        }

        @Override
        public MinMaxBounds.IntBound parse(StringReader stringReader) throws CommandSyntaxException {
            return MinMaxBounds.IntBound.fromReader(stringReader);
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

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class FloatRange
    implements IRangeArgument<MinMaxBounds.FloatBound> {
        private static final Collection<String> EXAMPLES = Arrays.asList("0..5.2", "0", "-5.4", "-100.76..", "..100");

        @Override
        public MinMaxBounds.FloatBound parse(StringReader stringReader) throws CommandSyntaxException {
            return MinMaxBounds.FloatBound.fromReader(stringReader);
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
}

