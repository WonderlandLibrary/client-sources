/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.advancements.FunctionManager;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.ResourceLocation;

public class FunctionObject {
    private final IEntry[] entries;
    private final ResourceLocation id;

    public FunctionObject(ResourceLocation resourceLocation, IEntry[] iEntryArray) {
        this.id = resourceLocation;
        this.entries = iEntryArray;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public IEntry[] getEntries() {
        return this.entries;
    }

    public static FunctionObject func_237140_a_(ResourceLocation resourceLocation, CommandDispatcher<CommandSource> commandDispatcher, CommandSource commandSource, List<String> list) {
        ArrayList<CommandEntry> arrayList = Lists.newArrayListWithCapacity(list.size());
        for (int i = 0; i < list.size(); ++i) {
            Object object;
            int n = i + 1;
            String string = list.get(i).trim();
            StringReader stringReader = new StringReader(string);
            if (!stringReader.canRead() || stringReader.peek() == '#') continue;
            if (stringReader.peek() == '/') {
                stringReader.skip();
                if (stringReader.peek() == '/') {
                    throw new IllegalArgumentException("Unknown or invalid command '" + string + "' on line " + n + " (if you intended to make a comment, use '#' not '//')");
                }
                object = stringReader.readUnquotedString();
                throw new IllegalArgumentException("Unknown or invalid command '" + string + "' on line " + n + " (did you mean '" + (String)object + "'? Do not use a preceding forwards slash.)");
            }
            try {
                object = commandDispatcher.parse(stringReader, commandSource);
                if (((ParseResults)object).getReader().canRead()) {
                    throw Commands.func_227481_a_(object);
                }
                arrayList.add(new CommandEntry((ParseResults<CommandSource>)object));
                continue;
            } catch (CommandSyntaxException commandSyntaxException) {
                throw new IllegalArgumentException("Whilst parsing command on line " + n + ": " + commandSyntaxException.getMessage());
            }
        }
        return new FunctionObject(resourceLocation, arrayList.toArray(new IEntry[0]));
    }

    public static interface IEntry {
        public void execute(FunctionManager var1, CommandSource var2, ArrayDeque<FunctionManager.QueuedCommand> var3, int var4) throws CommandSyntaxException;
    }

    public static class CommandEntry
    implements IEntry {
        private final ParseResults<CommandSource> field_196999_a;

        public CommandEntry(ParseResults<CommandSource> parseResults) {
            this.field_196999_a = parseResults;
        }

        @Override
        public void execute(FunctionManager functionManager, CommandSource commandSource, ArrayDeque<FunctionManager.QueuedCommand> arrayDeque, int n) throws CommandSyntaxException {
            functionManager.getCommandDispatcher().execute(new ParseResults<CommandSource>(this.field_196999_a.getContext().withSource(commandSource), this.field_196999_a.getReader(), this.field_196999_a.getExceptions()));
        }

        public String toString() {
            return this.field_196999_a.getReader().getString();
        }
    }

    public static class FunctionEntry
    implements IEntry {
        private final CacheableFunction function;

        public FunctionEntry(FunctionObject functionObject) {
            this.function = new CacheableFunction(functionObject);
        }

        @Override
        public void execute(FunctionManager functionManager, CommandSource commandSource, ArrayDeque<FunctionManager.QueuedCommand> arrayDeque, int n) {
            this.function.func_218039_a(functionManager).ifPresent(arg_0 -> FunctionEntry.lambda$execute$0(n, arrayDeque, functionManager, commandSource, arg_0));
        }

        public String toString() {
            return "function " + this.function.getId();
        }

        private static void lambda$execute$0(int n, ArrayDeque arrayDeque, FunctionManager functionManager, CommandSource commandSource, FunctionObject functionObject) {
            IEntry[] iEntryArray = functionObject.getEntries();
            int n2 = n - arrayDeque.size();
            int n3 = Math.min(iEntryArray.length, n2);
            for (int i = n3 - 1; i >= 0; --i) {
                arrayDeque.addFirst(new FunctionManager.QueuedCommand(functionManager, commandSource, iEntryArray[i]));
            }
        }
    }

    public static class CacheableFunction {
        public static final CacheableFunction EMPTY = new CacheableFunction((ResourceLocation)null);
        @Nullable
        private final ResourceLocation id;
        private boolean isValid;
        private Optional<FunctionObject> function = Optional.empty();

        public CacheableFunction(@Nullable ResourceLocation resourceLocation) {
            this.id = resourceLocation;
        }

        public CacheableFunction(FunctionObject functionObject) {
            this.isValid = true;
            this.id = null;
            this.function = Optional.of(functionObject);
        }

        public Optional<FunctionObject> func_218039_a(FunctionManager functionManager) {
            if (!this.isValid) {
                if (this.id != null) {
                    this.function = functionManager.get(this.id);
                }
                this.isValid = true;
            }
            return this.function;
        }

        @Nullable
        public ResourceLocation getId() {
            return this.function.map(CacheableFunction::lambda$getId$0).orElse(this.id);
        }

        private static ResourceLocation lambda$getId$0(FunctionObject functionObject) {
            return functionObject.id;
        }
    }
}

