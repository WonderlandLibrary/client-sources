/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.google.common.collect.Maps;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;

public class SuggestionProviders {
    private static final Map<ResourceLocation, SuggestionProvider<ISuggestionProvider>> REGISTRY = Maps.newHashMap();
    private static final ResourceLocation ASK_SERVER_ID = new ResourceLocation("ask_server");
    public static final SuggestionProvider<ISuggestionProvider> ASK_SERVER = SuggestionProviders.register(ASK_SERVER_ID, SuggestionProviders::lambda$static$0);
    public static final SuggestionProvider<CommandSource> ALL_RECIPES = SuggestionProviders.register(new ResourceLocation("all_recipes"), SuggestionProviders::lambda$static$1);
    public static final SuggestionProvider<CommandSource> AVAILABLE_SOUNDS = SuggestionProviders.register(new ResourceLocation("available_sounds"), SuggestionProviders::lambda$static$2);
    public static final SuggestionProvider<CommandSource> field_239574_d_ = SuggestionProviders.register(new ResourceLocation("available_biomes"), SuggestionProviders::lambda$static$3);
    public static final SuggestionProvider<CommandSource> SUMMONABLE_ENTITIES = SuggestionProviders.register(new ResourceLocation("summonable_entities"), SuggestionProviders::lambda$static$5);

    public static <S extends ISuggestionProvider> SuggestionProvider<S> register(ResourceLocation resourceLocation, SuggestionProvider<ISuggestionProvider> suggestionProvider) {
        if (REGISTRY.containsKey(resourceLocation)) {
            throw new IllegalArgumentException("A command suggestion provider is already registered with the name " + resourceLocation);
        }
        REGISTRY.put(resourceLocation, suggestionProvider);
        return new Wrapper(resourceLocation, suggestionProvider);
    }

    public static SuggestionProvider<ISuggestionProvider> get(ResourceLocation resourceLocation) {
        return REGISTRY.getOrDefault(resourceLocation, ASK_SERVER);
    }

    public static ResourceLocation getId(SuggestionProvider<ISuggestionProvider> suggestionProvider) {
        return suggestionProvider instanceof Wrapper ? ((Wrapper)suggestionProvider).id : ASK_SERVER_ID;
    }

    public static SuggestionProvider<ISuggestionProvider> ensureKnown(SuggestionProvider<ISuggestionProvider> suggestionProvider) {
        return suggestionProvider instanceof Wrapper ? suggestionProvider : ASK_SERVER;
    }

    private static CompletableFuture lambda$static$5(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        return ISuggestionProvider.func_201725_a(Registry.ENTITY_TYPE.stream().filter(EntityType::isSummonable), suggestionsBuilder, EntityType::getKey, SuggestionProviders::lambda$static$4);
    }

    private static Message lambda$static$4(EntityType entityType) {
        return new TranslationTextComponent(Util.makeTranslationKey("entity", EntityType.getKey(entityType)));
    }

    private static CompletableFuture lambda$static$3(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        return ISuggestionProvider.suggestIterable(((ISuggestionProvider)commandContext.getSource()).func_241861_q().getRegistry(Registry.BIOME_KEY).keySet(), suggestionsBuilder);
    }

    private static CompletableFuture lambda$static$2(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        return ISuggestionProvider.suggestIterable(((ISuggestionProvider)commandContext.getSource()).getSoundResourceLocations(), suggestionsBuilder);
    }

    private static CompletableFuture lambda$static$1(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        return ISuggestionProvider.func_212476_a(((ISuggestionProvider)commandContext.getSource()).getRecipeResourceLocations(), suggestionsBuilder);
    }

    private static CompletableFuture lambda$static$0(CommandContext commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
        return ((ISuggestionProvider)commandContext.getSource()).getSuggestionsFromServer(commandContext, suggestionsBuilder);
    }

    public static class Wrapper
    implements SuggestionProvider<ISuggestionProvider> {
        private final SuggestionProvider<ISuggestionProvider> provider;
        private final ResourceLocation id;

        public Wrapper(ResourceLocation resourceLocation, SuggestionProvider<ISuggestionProvider> suggestionProvider) {
            this.provider = suggestionProvider;
            this.id = resourceLocation;
        }

        @Override
        public CompletableFuture<Suggestions> getSuggestions(CommandContext<ISuggestionProvider> commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
            return this.provider.getSuggestions(commandContext, suggestionsBuilder);
        }
    }
}

