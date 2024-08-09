/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.command.arguments.EntitySelectorParser;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class GameProfileArgument
implements ArgumentType<IProfileProvider> {
    private static final Collection<String> EXAMPLES = Arrays.asList("Player", "0123", "dd12be42-52a9-4a91-a8a1-11c01849e498", "@e");
    public static final SimpleCommandExceptionType PLAYER_UNKNOWN = new SimpleCommandExceptionType(new TranslationTextComponent("argument.player.unknown"));

    public static Collection<GameProfile> getGameProfiles(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        return commandContext.getArgument(string, IProfileProvider.class).getNames(commandContext.getSource());
    }

    public static GameProfileArgument gameProfile() {
        return new GameProfileArgument();
    }

    @Override
    public IProfileProvider parse(StringReader stringReader) throws CommandSyntaxException {
        if (stringReader.canRead() && stringReader.peek() == '@') {
            EntitySelectorParser entitySelectorParser = new EntitySelectorParser(stringReader);
            EntitySelector entitySelector = entitySelectorParser.parse();
            if (entitySelector.includesEntities()) {
                throw EntityArgument.ONLY_PLAYERS_ALLOWED.create();
            }
            return new ProfileProvider(entitySelector);
        }
        int n = stringReader.getCursor();
        while (stringReader.canRead() && stringReader.peek() != ' ') {
            stringReader.skip();
        }
        String string = stringReader.getString().substring(n, stringReader.getCursor());
        return arg_0 -> GameProfileArgument.lambda$parse$0(string, arg_0);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        if (commandContext.getSource() instanceof ISuggestionProvider) {
            StringReader stringReader = new StringReader(suggestionsBuilder.getInput());
            stringReader.setCursor(suggestionsBuilder.getStart());
            EntitySelectorParser entitySelectorParser = new EntitySelectorParser(stringReader);
            try {
                entitySelectorParser.parse();
            } catch (CommandSyntaxException commandSyntaxException) {
                // empty catch block
            }
            return entitySelectorParser.fillSuggestions(suggestionsBuilder, arg_0 -> GameProfileArgument.lambda$listSuggestions$1(commandContext, arg_0));
        }
        return Suggestions.empty();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    private static void lambda$listSuggestions$1(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) {
        ISuggestionProvider.suggest(((ISuggestionProvider)commandContext.getSource()).getPlayerNames(), suggestionsBuilder);
    }

    private static Collection lambda$parse$0(String string, CommandSource commandSource) throws CommandSyntaxException {
        GameProfile gameProfile = commandSource.getServer().getPlayerProfileCache().getGameProfileForUsername(string);
        if (gameProfile == null) {
            throw PLAYER_UNKNOWN.create();
        }
        return Collections.singleton(gameProfile);
    }

    @FunctionalInterface
    public static interface IProfileProvider {
        public Collection<GameProfile> getNames(CommandSource var1) throws CommandSyntaxException;
    }

    public static class ProfileProvider
    implements IProfileProvider {
        private final EntitySelector selector;

        public ProfileProvider(EntitySelector entitySelector) {
            this.selector = entitySelector;
        }

        @Override
        public Collection<GameProfile> getNames(CommandSource commandSource) throws CommandSyntaxException {
            List<ServerPlayerEntity> list = this.selector.selectPlayers(commandSource);
            if (list.isEmpty()) {
                throw EntityArgument.PLAYER_NOT_FOUND.create();
            }
            ArrayList<GameProfile> arrayList = Lists.newArrayList();
            for (ServerPlayerEntity serverPlayerEntity : list) {
                arrayList.add(serverPlayerEntity.getGameProfile());
            }
            return arrayList;
        }
    }
}

