/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.google.common.collect.Iterables;
import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.command.arguments.EntitySelectorParser;
import net.minecraft.command.arguments.IArgumentSerializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class EntityArgument
implements ArgumentType<EntitySelector> {
    private static final Collection<String> EXAMPLES = Arrays.asList("Player", "0123", "@e", "@e[type=foo]", "dd12be42-52a9-4a91-a8a1-11c01849e498");
    public static final SimpleCommandExceptionType TOO_MANY_ENTITIES = new SimpleCommandExceptionType(new TranslationTextComponent("argument.entity.toomany"));
    public static final SimpleCommandExceptionType TOO_MANY_PLAYERS = new SimpleCommandExceptionType(new TranslationTextComponent("argument.player.toomany"));
    public static final SimpleCommandExceptionType ONLY_PLAYERS_ALLOWED = new SimpleCommandExceptionType(new TranslationTextComponent("argument.player.entities"));
    public static final SimpleCommandExceptionType ENTITY_NOT_FOUND = new SimpleCommandExceptionType(new TranslationTextComponent("argument.entity.notfound.entity"));
    public static final SimpleCommandExceptionType PLAYER_NOT_FOUND = new SimpleCommandExceptionType(new TranslationTextComponent("argument.entity.notfound.player"));
    public static final SimpleCommandExceptionType SELECTOR_NOT_ALLOWED = new SimpleCommandExceptionType(new TranslationTextComponent("argument.entity.selector.not_allowed"));
    private final boolean single;
    private final boolean playersOnly;

    protected EntityArgument(boolean bl, boolean bl2) {
        this.single = bl;
        this.playersOnly = bl2;
    }

    public static EntityArgument entity() {
        return new EntityArgument(true, false);
    }

    public static Entity getEntity(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        return commandContext.getArgument(string, EntitySelector.class).selectOne(commandContext.getSource());
    }

    public static EntityArgument entities() {
        return new EntityArgument(false, false);
    }

    public static Collection<? extends Entity> getEntities(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        Collection<? extends Entity> collection = EntityArgument.getEntitiesAllowingNone(commandContext, string);
        if (collection.isEmpty()) {
            throw ENTITY_NOT_FOUND.create();
        }
        return collection;
    }

    public static Collection<? extends Entity> getEntitiesAllowingNone(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        return commandContext.getArgument(string, EntitySelector.class).select(commandContext.getSource());
    }

    public static Collection<ServerPlayerEntity> getPlayersAllowingNone(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        return commandContext.getArgument(string, EntitySelector.class).selectPlayers(commandContext.getSource());
    }

    public static EntityArgument player() {
        return new EntityArgument(true, true);
    }

    public static ServerPlayerEntity getPlayer(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        return commandContext.getArgument(string, EntitySelector.class).selectOnePlayer(commandContext.getSource());
    }

    public static EntityArgument players() {
        return new EntityArgument(false, true);
    }

    public static Collection<ServerPlayerEntity> getPlayers(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        List<ServerPlayerEntity> list = commandContext.getArgument(string, EntitySelector.class).selectPlayers(commandContext.getSource());
        if (list.isEmpty()) {
            throw PLAYER_NOT_FOUND.create();
        }
        return list;
    }

    @Override
    public EntitySelector parse(StringReader stringReader) throws CommandSyntaxException {
        boolean bl = false;
        EntitySelectorParser entitySelectorParser = new EntitySelectorParser(stringReader);
        EntitySelector entitySelector = entitySelectorParser.parse();
        if (entitySelector.getLimit() > 1 && this.single) {
            if (this.playersOnly) {
                stringReader.setCursor(0);
                throw TOO_MANY_PLAYERS.createWithContext(stringReader);
            }
            stringReader.setCursor(0);
            throw TOO_MANY_ENTITIES.createWithContext(stringReader);
        }
        if (entitySelector.includesEntities() && this.playersOnly && !entitySelector.isSelfSelector()) {
            stringReader.setCursor(0);
            throw ONLY_PLAYERS_ALLOWED.createWithContext(stringReader);
        }
        return entitySelector;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        if (commandContext.getSource() instanceof ISuggestionProvider) {
            StringReader stringReader = new StringReader(suggestionsBuilder.getInput());
            stringReader.setCursor(suggestionsBuilder.getStart());
            ISuggestionProvider iSuggestionProvider = (ISuggestionProvider)commandContext.getSource();
            EntitySelectorParser entitySelectorParser = new EntitySelectorParser(stringReader, iSuggestionProvider.hasPermissionLevel(2));
            try {
                entitySelectorParser.parse();
            } catch (CommandSyntaxException commandSyntaxException) {
                // empty catch block
            }
            return entitySelectorParser.fillSuggestions(suggestionsBuilder, arg_0 -> this.lambda$listSuggestions$0(iSuggestionProvider, arg_0));
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

    private void lambda$listSuggestions$0(ISuggestionProvider iSuggestionProvider, SuggestionsBuilder suggestionsBuilder) {
        Collection<String> collection = iSuggestionProvider.getPlayerNames();
        Collection<String> collection2 = this.playersOnly ? collection : Iterables.concat(collection, iSuggestionProvider.getTargetedEntity());
        ISuggestionProvider.suggest(collection2, suggestionsBuilder);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements IArgumentSerializer<EntityArgument> {
        @Override
        public void write(EntityArgument entityArgument, PacketBuffer packetBuffer) {
            byte by = 0;
            if (entityArgument.single) {
                by = (byte)(by | 1);
            }
            if (entityArgument.playersOnly) {
                by = (byte)(by | 2);
            }
            packetBuffer.writeByte(by);
        }

        @Override
        public EntityArgument read(PacketBuffer packetBuffer) {
            byte by = packetBuffer.readByte();
            return new EntityArgument((by & 1) != 0, (by & 2) != 0);
        }

        @Override
        public void write(EntityArgument entityArgument, JsonObject jsonObject) {
            jsonObject.addProperty("amount", entityArgument.single ? "single" : "multiple");
            jsonObject.addProperty("type", entityArgument.playersOnly ? "players" : "entities");
        }

        @Override
        public void write(ArgumentType argumentType, JsonObject jsonObject) {
            this.write((EntityArgument)argumentType, jsonObject);
        }

        @Override
        public ArgumentType read(PacketBuffer packetBuffer) {
            return this.read(packetBuffer);
        }

        @Override
        public void write(ArgumentType argumentType, PacketBuffer packetBuffer) {
            this.write((EntityArgument)argumentType, packetBuffer);
        }
    }
}

