/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.google.common.collect.Maps;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import javax.annotation.Nullable;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.state.Property;
import net.minecraft.tags.ITagCollection;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;

public class ItemParser {
    public static final SimpleCommandExceptionType ITEM_TAGS_NOT_ALLOWED = new SimpleCommandExceptionType(new TranslationTextComponent("argument.item.tag.disallowed"));
    public static final DynamicCommandExceptionType ITEM_BAD_ID = new DynamicCommandExceptionType(ItemParser::lambda$static$0);
    private static final BiFunction<SuggestionsBuilder, ITagCollection<Item>, CompletableFuture<Suggestions>> DEFAULT_SUGGESTIONS_BUILDER = ItemParser::lambda$static$1;
    private final StringReader reader;
    private final boolean allowTags;
    private final Map<Property<?>, Comparable<?>> field_197336_d = Maps.newHashMap();
    private Item item;
    @Nullable
    private CompoundNBT nbt;
    private ResourceLocation tag = new ResourceLocation("");
    private int readerCursor;
    private BiFunction<SuggestionsBuilder, ITagCollection<Item>, CompletableFuture<Suggestions>> suggestionsBuilder = DEFAULT_SUGGESTIONS_BUILDER;

    public ItemParser(StringReader stringReader, boolean bl) {
        this.reader = stringReader;
        this.allowTags = bl;
    }

    public Item getItem() {
        return this.item;
    }

    @Nullable
    public CompoundNBT getNbt() {
        return this.nbt;
    }

    public ResourceLocation getTag() {
        return this.tag;
    }

    public void readItem() throws CommandSyntaxException {
        int n = this.reader.getCursor();
        ResourceLocation resourceLocation = ResourceLocation.read(this.reader);
        this.item = Registry.ITEM.getOptional(resourceLocation).orElseThrow(() -> this.lambda$readItem$2(n, resourceLocation));
    }

    public void readTag() throws CommandSyntaxException {
        if (!this.allowTags) {
            throw ITEM_TAGS_NOT_ALLOWED.create();
        }
        this.suggestionsBuilder = this::suggestTag;
        this.reader.expect('#');
        this.readerCursor = this.reader.getCursor();
        this.tag = ResourceLocation.read(this.reader);
    }

    public void readNBT() throws CommandSyntaxException {
        this.nbt = new JsonToNBT(this.reader).readStruct();
    }

    public ItemParser parse() throws CommandSyntaxException {
        this.suggestionsBuilder = this::suggestTagOrItem;
        if (this.reader.canRead() && this.reader.peek() == '#') {
            this.readTag();
        } else {
            this.readItem();
            this.suggestionsBuilder = this::suggestItem;
        }
        if (this.reader.canRead() && this.reader.peek() == '{') {
            this.suggestionsBuilder = DEFAULT_SUGGESTIONS_BUILDER;
            this.readNBT();
        }
        return this;
    }

    private CompletableFuture<Suggestions> suggestItem(SuggestionsBuilder suggestionsBuilder, ITagCollection<Item> iTagCollection) {
        if (suggestionsBuilder.getRemaining().isEmpty()) {
            suggestionsBuilder.suggest(String.valueOf('{'));
        }
        return suggestionsBuilder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestTag(SuggestionsBuilder suggestionsBuilder, ITagCollection<Item> iTagCollection) {
        return ISuggestionProvider.suggestIterable(iTagCollection.getRegisteredTags(), suggestionsBuilder.createOffset(this.readerCursor));
    }

    private CompletableFuture<Suggestions> suggestTagOrItem(SuggestionsBuilder suggestionsBuilder, ITagCollection<Item> iTagCollection) {
        if (this.allowTags) {
            ISuggestionProvider.suggestIterable(iTagCollection.getRegisteredTags(), suggestionsBuilder, String.valueOf('#'));
        }
        return ISuggestionProvider.suggestIterable(Registry.ITEM.keySet(), suggestionsBuilder);
    }

    public CompletableFuture<Suggestions> fillSuggestions(SuggestionsBuilder suggestionsBuilder, ITagCollection<Item> iTagCollection) {
        return this.suggestionsBuilder.apply(suggestionsBuilder.createOffset(this.reader.getCursor()), iTagCollection);
    }

    private CommandSyntaxException lambda$readItem$2(int n, ResourceLocation resourceLocation) {
        this.reader.setCursor(n);
        return ITEM_BAD_ID.createWithContext(this.reader, resourceLocation.toString());
    }

    private static CompletableFuture lambda$static$1(SuggestionsBuilder suggestionsBuilder, ITagCollection iTagCollection) {
        return suggestionsBuilder.buildFuture();
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("argument.item.id.invalid", object);
    }
}

