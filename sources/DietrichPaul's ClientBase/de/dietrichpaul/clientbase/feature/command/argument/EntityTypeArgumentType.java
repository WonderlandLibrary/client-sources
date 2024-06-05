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
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import de.dietrichpaul.clientbase.feature.command.suggestor.Suggestor;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public class EntityTypeArgumentType implements ArgumentType<EntityType<?>> {
    public final static Dynamic2CommandExceptionType NOT_FOUND_EXCEPTION = new Dynamic2CommandExceptionType((element, type) -> Text.translatable("argument.resource.not_found", element, type));

    private final Predicate<EntityType<?>> filter;

    private EntityTypeArgumentType(Predicate<EntityType<?>> filter) {
        this.filter = filter;
    }

    public static EntityTypeArgumentType entityType(Predicate<EntityType<?>> filter) {
        return new EntityTypeArgumentType(filter);
    }

    public static EntityType<?> getEntityType(CommandContext<CommandSource> context, String name) {
        return context.getArgument(name, EntityType.class);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return CommandSource.suggestIdentifiers(Registries.ENTITY_TYPE.stream().filter(filter).map(EntityType::getId), builder);
    }

    @Override
    public EntityType<?> parse(StringReader reader) throws CommandSyntaxException {
        Identifier identifier = Identifier.fromCommandInput(reader);
        EntityType<?> type;
        if (!Registries.ENTITY_TYPE.containsId(identifier) || !filter.test(type = Registries.ENTITY_TYPE.get(identifier))) {
            throw NOT_FOUND_EXCEPTION.create(identifier, RegistryKeys.ENTITY_TYPE.getValue());
        }
        return type;
    }
}
