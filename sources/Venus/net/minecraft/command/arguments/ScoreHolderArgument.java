/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.command.arguments.EntitySelectorParser;
import net.minecraft.command.arguments.IArgumentSerializer;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ScoreHolderArgument
implements ArgumentType<INameProvider> {
    public static final SuggestionProvider<CommandSource> SUGGEST_ENTITY_SELECTOR = ScoreHolderArgument::lambda$static$1;
    private static final Collection<String> EXAMPLES = Arrays.asList("Player", "0123", "*", "@e");
    private static final SimpleCommandExceptionType EMPTY_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("argument.scoreHolder.empty"));
    private final boolean allowMultiple;

    public ScoreHolderArgument(boolean bl) {
        this.allowMultiple = bl;
    }

    public static String getSingleScoreHolderNoObjectives(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        return ScoreHolderArgument.getScoreHolderNoObjectives(commandContext, string).iterator().next();
    }

    public static Collection<String> getScoreHolderNoObjectives(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        return ScoreHolderArgument.getScoreHolder(commandContext, string, Collections::emptyList);
    }

    public static Collection<String> getScoreHolder(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        return ScoreHolderArgument.getScoreHolder(commandContext, string, commandContext.getSource().getServer().getScoreboard()::getObjectiveNames);
    }

    public static Collection<String> getScoreHolder(CommandContext<CommandSource> commandContext, String string, Supplier<Collection<String>> supplier) throws CommandSyntaxException {
        Collection<String> collection = commandContext.getArgument(string, INameProvider.class).getNames(commandContext.getSource(), supplier);
        if (collection.isEmpty()) {
            throw EntityArgument.ENTITY_NOT_FOUND.create();
        }
        return collection;
    }

    public static ScoreHolderArgument scoreHolder() {
        return new ScoreHolderArgument(false);
    }

    public static ScoreHolderArgument scoreHolders() {
        return new ScoreHolderArgument(true);
    }

    @Override
    public INameProvider parse(StringReader stringReader) throws CommandSyntaxException {
        if (stringReader.canRead() && stringReader.peek() == '@') {
            EntitySelectorParser entitySelectorParser = new EntitySelectorParser(stringReader);
            EntitySelector entitySelector = entitySelectorParser.parse();
            if (!this.allowMultiple && entitySelector.getLimit() > 1) {
                throw EntityArgument.TOO_MANY_ENTITIES.create();
            }
            return new NameProvider(entitySelector);
        }
        int n = stringReader.getCursor();
        while (stringReader.canRead() && stringReader.peek() != ' ') {
            stringReader.skip();
        }
        String string = stringReader.getString().substring(n, stringReader.getCursor());
        if (string.equals("*")) {
            return ScoreHolderArgument::lambda$parse$2;
        }
        Set<String> set = Collections.singleton(string);
        return (arg_0, arg_1) -> ScoreHolderArgument.lambda$parse$3(set, arg_0, arg_1);
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    private static Collection lambda$parse$3(Collection collection, CommandSource commandSource, Supplier supplier) throws CommandSyntaxException {
        return collection;
    }

    private static Collection lambda$parse$2(CommandSource commandSource, Supplier supplier) throws CommandSyntaxException {
        Collection collection = (Collection)supplier.get();
        if (collection.isEmpty()) {
            throw EMPTY_EXCEPTION.create();
        }
        return collection;
    }

    private static CompletableFuture lambda$static$1(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        StringReader stringReader = new StringReader(suggestionsBuilder.getInput());
        stringReader.setCursor(suggestionsBuilder.getStart());
        EntitySelectorParser entitySelectorParser = new EntitySelectorParser(stringReader);
        try {
            entitySelectorParser.parse();
        } catch (CommandSyntaxException commandSyntaxException) {
            // empty catch block
        }
        return entitySelectorParser.fillSuggestions(suggestionsBuilder, arg_0 -> ScoreHolderArgument.lambda$static$0(commandContext, arg_0));
    }

    private static void lambda$static$0(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) {
        ISuggestionProvider.suggest(((CommandSource)commandContext.getSource()).getPlayerNames(), suggestionsBuilder);
    }

    @FunctionalInterface
    public static interface INameProvider {
        public Collection<String> getNames(CommandSource var1, Supplier<Collection<String>> var2) throws CommandSyntaxException;
    }

    public static class NameProvider
    implements INameProvider {
        private final EntitySelector selector;

        public NameProvider(EntitySelector entitySelector) {
            this.selector = entitySelector;
        }

        @Override
        public Collection<String> getNames(CommandSource commandSource, Supplier<Collection<String>> supplier) throws CommandSyntaxException {
            List<? extends Entity> list = this.selector.select(commandSource);
            if (list.isEmpty()) {
                throw EntityArgument.ENTITY_NOT_FOUND.create();
            }
            ArrayList<String> arrayList = Lists.newArrayList();
            for (Entity entity2 : list) {
                arrayList.add(entity2.getScoreboardName());
            }
            return arrayList;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements IArgumentSerializer<ScoreHolderArgument> {
        @Override
        public void write(ScoreHolderArgument scoreHolderArgument, PacketBuffer packetBuffer) {
            int n = 0;
            if (scoreHolderArgument.allowMultiple) {
                n = (byte)(n | 1);
            }
            packetBuffer.writeByte(n);
        }

        @Override
        public ScoreHolderArgument read(PacketBuffer packetBuffer) {
            byte by = packetBuffer.readByte();
            boolean bl = (by & 1) != 0;
            return new ScoreHolderArgument(bl);
        }

        @Override
        public void write(ScoreHolderArgument scoreHolderArgument, JsonObject jsonObject) {
            jsonObject.addProperty("amount", scoreHolderArgument.allowMultiple ? "multiple" : "single");
        }

        @Override
        public void write(ArgumentType argumentType, JsonObject jsonObject) {
            this.write((ScoreHolderArgument)argumentType, jsonObject);
        }

        @Override
        public ArgumentType read(PacketBuffer packetBuffer) {
            return this.read(packetBuffer);
        }

        @Override
        public void write(ArgumentType argumentType, PacketBuffer packetBuffer) {
            this.write((ScoreHolderArgument)argumentType, packetBuffer);
        }
    }
}

