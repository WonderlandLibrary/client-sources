/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.google.common.collect.Maps;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;

public class BlockStateParser {
    public static final SimpleCommandExceptionType STATE_TAGS_NOT_ALLOWED = new SimpleCommandExceptionType(new TranslationTextComponent("argument.block.tag.disallowed"));
    public static final DynamicCommandExceptionType STATE_BAD_ID = new DynamicCommandExceptionType(BlockStateParser::lambda$static$0);
    public static final Dynamic2CommandExceptionType STATE_UNKNOWN_PROPERTY = new Dynamic2CommandExceptionType(BlockStateParser::lambda$static$1);
    public static final Dynamic2CommandExceptionType STATE_DUPLICATE_PROPERTY = new Dynamic2CommandExceptionType(BlockStateParser::lambda$static$2);
    public static final Dynamic3CommandExceptionType STATE_INVALID_PROPERTY_VALUE = new Dynamic3CommandExceptionType(BlockStateParser::lambda$static$3);
    public static final Dynamic2CommandExceptionType STATE_NO_VALUE = new Dynamic2CommandExceptionType(BlockStateParser::lambda$static$4);
    public static final SimpleCommandExceptionType STATE_UNCLOSED = new SimpleCommandExceptionType(new TranslationTextComponent("argument.block.property.unclosed"));
    private static final BiFunction<SuggestionsBuilder, ITagCollection<Block>, CompletableFuture<Suggestions>> SUGGEST_NONE = BlockStateParser::lambda$static$5;
    private final StringReader reader;
    private final boolean tagsAllowed;
    private final Map<Property<?>, Comparable<?>> properties = Maps.newHashMap();
    private final Map<String, String> stringProperties = Maps.newHashMap();
    private ResourceLocation blockID = new ResourceLocation("");
    private StateContainer<Block, BlockState> blockStateContainer;
    private BlockState state;
    @Nullable
    private CompoundNBT nbt;
    private ResourceLocation tag = new ResourceLocation("");
    private int cursorPos;
    private BiFunction<SuggestionsBuilder, ITagCollection<Block>, CompletableFuture<Suggestions>> suggestor = SUGGEST_NONE;

    public BlockStateParser(StringReader stringReader, boolean bl) {
        this.reader = stringReader;
        this.tagsAllowed = bl;
    }

    public Map<Property<?>, Comparable<?>> getProperties() {
        return this.properties;
    }

    @Nullable
    public BlockState getState() {
        return this.state;
    }

    @Nullable
    public CompoundNBT getNbt() {
        return this.nbt;
    }

    @Nullable
    public ResourceLocation getTag() {
        return this.tag;
    }

    public BlockStateParser parse(boolean bl) throws CommandSyntaxException {
        this.suggestor = this::suggestTagOrBlock;
        if (this.reader.canRead() && this.reader.peek() == '#') {
            this.readTag();
            this.suggestor = this::func_212599_i;
            if (this.reader.canRead() && this.reader.peek() == '[') {
                this.readStringProperties();
                this.suggestor = this::suggestNbt;
            }
        } else {
            this.readBlock();
            this.suggestor = this::suggestPropertyOrNbt;
            if (this.reader.canRead() && this.reader.peek() == '[') {
                this.readProperties();
                this.suggestor = this::suggestNbt;
            }
        }
        if (bl && this.reader.canRead() && this.reader.peek() == '{') {
            this.suggestor = SUGGEST_NONE;
            this.readNBT();
        }
        return this;
    }

    private CompletableFuture<Suggestions> suggestPropertyOrEnd(SuggestionsBuilder suggestionsBuilder, ITagCollection<Block> iTagCollection) {
        if (suggestionsBuilder.getRemaining().isEmpty()) {
            suggestionsBuilder.suggest(String.valueOf(']'));
        }
        return this.suggestProperty(suggestionsBuilder, iTagCollection);
    }

    private CompletableFuture<Suggestions> suggestStringPropertyOrEnd(SuggestionsBuilder suggestionsBuilder, ITagCollection<Block> iTagCollection) {
        if (suggestionsBuilder.getRemaining().isEmpty()) {
            suggestionsBuilder.suggest(String.valueOf(']'));
        }
        return this.suggestStringProperty(suggestionsBuilder, iTagCollection);
    }

    private CompletableFuture<Suggestions> suggestProperty(SuggestionsBuilder suggestionsBuilder, ITagCollection<Block> iTagCollection) {
        String string = suggestionsBuilder.getRemaining().toLowerCase(Locale.ROOT);
        for (Property property : this.state.getProperties()) {
            if (this.properties.containsKey(property) || !property.getName().startsWith(string)) continue;
            suggestionsBuilder.suggest(property.getName() + "=");
        }
        return suggestionsBuilder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestStringProperty(SuggestionsBuilder suggestionsBuilder, ITagCollection<Block> iTagCollection) {
        ITag<Block> iTag;
        String string = suggestionsBuilder.getRemaining().toLowerCase(Locale.ROOT);
        if (this.tag != null && !this.tag.getPath().isEmpty() && (iTag = iTagCollection.get(this.tag)) != null) {
            for (Block block : iTag.getAllElements()) {
                for (Property<?> property : block.getStateContainer().getProperties()) {
                    if (this.stringProperties.containsKey(property.getName()) || !property.getName().startsWith(string)) continue;
                    suggestionsBuilder.suggest(property.getName() + "=");
                }
            }
        }
        return suggestionsBuilder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestNbt(SuggestionsBuilder suggestionsBuilder, ITagCollection<Block> iTagCollection) {
        if (suggestionsBuilder.getRemaining().isEmpty() && this.func_212598_k(iTagCollection)) {
            suggestionsBuilder.suggest(String.valueOf('{'));
        }
        return suggestionsBuilder.buildFuture();
    }

    private boolean func_212598_k(ITagCollection<Block> iTagCollection) {
        ITag<Block> iTag;
        if (this.state != null) {
            return this.state.getBlock().isTileEntityProvider();
        }
        if (this.tag != null && (iTag = iTagCollection.get(this.tag)) != null) {
            for (Block block : iTag.getAllElements()) {
                if (!block.isTileEntityProvider()) continue;
                return false;
            }
        }
        return true;
    }

    private CompletableFuture<Suggestions> suggestEquals(SuggestionsBuilder suggestionsBuilder, ITagCollection<Block> iTagCollection) {
        if (suggestionsBuilder.getRemaining().isEmpty()) {
            suggestionsBuilder.suggest(String.valueOf('='));
        }
        return suggestionsBuilder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestPropertyEndOrContinue(SuggestionsBuilder suggestionsBuilder, ITagCollection<Block> iTagCollection) {
        if (suggestionsBuilder.getRemaining().isEmpty()) {
            suggestionsBuilder.suggest(String.valueOf(']'));
        }
        if (suggestionsBuilder.getRemaining().isEmpty() && this.properties.size() < this.state.getProperties().size()) {
            suggestionsBuilder.suggest(String.valueOf(','));
        }
        return suggestionsBuilder.buildFuture();
    }

    private static <T extends Comparable<T>> SuggestionsBuilder suggestValue(SuggestionsBuilder suggestionsBuilder, Property<T> property) {
        for (Comparable comparable : property.getAllowedValues()) {
            if (comparable instanceof Integer) {
                suggestionsBuilder.suggest((Integer)comparable);
                continue;
            }
            suggestionsBuilder.suggest(property.getName(comparable));
        }
        return suggestionsBuilder;
    }

    private CompletableFuture<Suggestions> func_239295_a_(SuggestionsBuilder suggestionsBuilder, ITagCollection<Block> iTagCollection, String string) {
        ITag<Block> iTag;
        boolean bl = false;
        if (this.tag != null && !this.tag.getPath().isEmpty() && (iTag = iTagCollection.get(this.tag)) != null) {
            block0: for (Block block : iTag.getAllElements()) {
                Property<?> property = block.getStateContainer().getProperty(string);
                if (property != null) {
                    BlockStateParser.suggestValue(suggestionsBuilder, property);
                }
                if (bl) continue;
                for (Property<?> property2 : block.getStateContainer().getProperties()) {
                    if (this.stringProperties.containsKey(property2.getName())) continue;
                    bl = true;
                    continue block0;
                }
            }
        }
        if (bl) {
            suggestionsBuilder.suggest(String.valueOf(','));
        }
        suggestionsBuilder.suggest(String.valueOf(']'));
        return suggestionsBuilder.buildFuture();
    }

    private CompletableFuture<Suggestions> func_212599_i(SuggestionsBuilder suggestionsBuilder, ITagCollection<Block> iTagCollection) {
        ITag<Block> iTag;
        if (suggestionsBuilder.getRemaining().isEmpty() && (iTag = iTagCollection.get(this.tag)) != null) {
            Block block;
            boolean bl = false;
            boolean bl2 = false;
            Iterator<Block> iterator2 = iTag.getAllElements().iterator();
            while (!(!iterator2.hasNext() || (bl |= !(block = iterator2.next()).getStateContainer().getProperties().isEmpty()) && (bl2 |= block.isTileEntityProvider()))) {
            }
            if (bl) {
                suggestionsBuilder.suggest(String.valueOf('['));
            }
            if (bl2) {
                suggestionsBuilder.suggest(String.valueOf('{'));
            }
        }
        return this.suggestTag(suggestionsBuilder, iTagCollection);
    }

    private CompletableFuture<Suggestions> suggestPropertyOrNbt(SuggestionsBuilder suggestionsBuilder, ITagCollection<Block> iTagCollection) {
        if (suggestionsBuilder.getRemaining().isEmpty()) {
            if (!this.state.getBlock().getStateContainer().getProperties().isEmpty()) {
                suggestionsBuilder.suggest(String.valueOf('['));
            }
            if (this.state.getBlock().isTileEntityProvider()) {
                suggestionsBuilder.suggest(String.valueOf('{'));
            }
        }
        return suggestionsBuilder.buildFuture();
    }

    private CompletableFuture<Suggestions> suggestTag(SuggestionsBuilder suggestionsBuilder, ITagCollection<Block> iTagCollection) {
        return ISuggestionProvider.suggestIterable(iTagCollection.getRegisteredTags(), suggestionsBuilder.createOffset(this.cursorPos).add(suggestionsBuilder));
    }

    private CompletableFuture<Suggestions> suggestTagOrBlock(SuggestionsBuilder suggestionsBuilder, ITagCollection<Block> iTagCollection) {
        if (this.tagsAllowed) {
            ISuggestionProvider.suggestIterable(iTagCollection.getRegisteredTags(), suggestionsBuilder, String.valueOf('#'));
        }
        ISuggestionProvider.suggestIterable(Registry.BLOCK.keySet(), suggestionsBuilder);
        return suggestionsBuilder.buildFuture();
    }

    public void readBlock() throws CommandSyntaxException {
        int n = this.reader.getCursor();
        this.blockID = ResourceLocation.read(this.reader);
        Block block = Registry.BLOCK.getOptional(this.blockID).orElseThrow(() -> this.lambda$readBlock$6(n));
        this.blockStateContainer = block.getStateContainer();
        this.state = block.getDefaultState();
    }

    public void readTag() throws CommandSyntaxException {
        if (!this.tagsAllowed) {
            throw STATE_TAGS_NOT_ALLOWED.create();
        }
        this.suggestor = this::suggestTag;
        this.reader.expect('#');
        this.cursorPos = this.reader.getCursor();
        this.tag = ResourceLocation.read(this.reader);
    }

    public void readProperties() throws CommandSyntaxException {
        this.reader.skip();
        this.suggestor = this::suggestPropertyOrEnd;
        this.reader.skipWhitespace();
        while (this.reader.canRead() && this.reader.peek() != ']') {
            this.reader.skipWhitespace();
            int n = this.reader.getCursor();
            String string = this.reader.readString();
            Property<?> property = this.blockStateContainer.getProperty(string);
            if (property == null) {
                this.reader.setCursor(n);
                throw STATE_UNKNOWN_PROPERTY.createWithContext(this.reader, this.blockID.toString(), string);
            }
            if (this.properties.containsKey(property)) {
                this.reader.setCursor(n);
                throw STATE_DUPLICATE_PROPERTY.createWithContext(this.reader, this.blockID.toString(), string);
            }
            this.reader.skipWhitespace();
            this.suggestor = this::suggestEquals;
            if (!this.reader.canRead() || this.reader.peek() != '=') {
                throw STATE_NO_VALUE.createWithContext(this.reader, this.blockID.toString(), string);
            }
            this.reader.skip();
            this.reader.skipWhitespace();
            this.suggestor = (arg_0, arg_1) -> BlockStateParser.lambda$readProperties$7(property, arg_0, arg_1);
            int n2 = this.reader.getCursor();
            this.parseValue(property, this.reader.readString(), n2);
            this.suggestor = this::suggestPropertyEndOrContinue;
            this.reader.skipWhitespace();
            if (!this.reader.canRead()) continue;
            if (this.reader.peek() == ',') {
                this.reader.skip();
                this.suggestor = this::suggestProperty;
                continue;
            }
            if (this.reader.peek() == ']') break;
            throw STATE_UNCLOSED.createWithContext(this.reader);
        }
        if (this.reader.canRead()) {
            this.reader.skip();
            return;
        }
        throw STATE_UNCLOSED.createWithContext(this.reader);
    }

    public void readStringProperties() throws CommandSyntaxException {
        this.reader.skip();
        this.suggestor = this::suggestStringPropertyOrEnd;
        int n = -1;
        this.reader.skipWhitespace();
        while (this.reader.canRead() && this.reader.peek() != ']') {
            this.reader.skipWhitespace();
            int n2 = this.reader.getCursor();
            String string = this.reader.readString();
            if (this.stringProperties.containsKey(string)) {
                this.reader.setCursor(n2);
                throw STATE_DUPLICATE_PROPERTY.createWithContext(this.reader, this.blockID.toString(), string);
            }
            this.reader.skipWhitespace();
            if (!this.reader.canRead() || this.reader.peek() != '=') {
                this.reader.setCursor(n2);
                throw STATE_NO_VALUE.createWithContext(this.reader, this.blockID.toString(), string);
            }
            this.reader.skip();
            this.reader.skipWhitespace();
            this.suggestor = (arg_0, arg_1) -> this.lambda$readStringProperties$8(string, arg_0, arg_1);
            n = this.reader.getCursor();
            String string2 = this.reader.readString();
            this.stringProperties.put(string, string2);
            this.reader.skipWhitespace();
            if (!this.reader.canRead()) continue;
            n = -1;
            if (this.reader.peek() == ',') {
                this.reader.skip();
                this.suggestor = this::suggestStringProperty;
                continue;
            }
            if (this.reader.peek() == ']') break;
            throw STATE_UNCLOSED.createWithContext(this.reader);
        }
        if (this.reader.canRead()) {
            this.reader.skip();
            return;
        }
        if (n >= 0) {
            this.reader.setCursor(n);
        }
        throw STATE_UNCLOSED.createWithContext(this.reader);
    }

    public void readNBT() throws CommandSyntaxException {
        this.nbt = new JsonToNBT(this.reader).readStruct();
    }

    private <T extends Comparable<T>> void parseValue(Property<T> property, String string, int n) throws CommandSyntaxException {
        Optional<T> optional = property.parseValue(string);
        if (!optional.isPresent()) {
            this.reader.setCursor(n);
            throw STATE_INVALID_PROPERTY_VALUE.createWithContext(this.reader, this.blockID.toString(), property.getName(), string);
        }
        this.state = (BlockState)this.state.with(property, (Comparable)optional.get());
        this.properties.put(property, (Comparable)optional.get());
    }

    public static String toString(BlockState blockState) {
        StringBuilder stringBuilder = new StringBuilder(Registry.BLOCK.getKey(blockState.getBlock()).toString());
        if (!blockState.getProperties().isEmpty()) {
            stringBuilder.append('[');
            boolean bl = false;
            for (Map.Entry entry : blockState.getValues().entrySet()) {
                if (bl) {
                    stringBuilder.append(',');
                }
                BlockStateParser.propValToString(stringBuilder, (Property)entry.getKey(), (Comparable)entry.getValue());
                bl = true;
            }
            stringBuilder.append(']');
        }
        return stringBuilder.toString();
    }

    private static <T extends Comparable<T>> void propValToString(StringBuilder stringBuilder, Property<T> property, Comparable<?> comparable) {
        stringBuilder.append(property.getName());
        stringBuilder.append('=');
        stringBuilder.append(property.getName(comparable));
    }

    public CompletableFuture<Suggestions> getSuggestions(SuggestionsBuilder suggestionsBuilder, ITagCollection<Block> iTagCollection) {
        return this.suggestor.apply(suggestionsBuilder.createOffset(this.reader.getCursor()), iTagCollection);
    }

    public Map<String, String> getStringProperties() {
        return this.stringProperties;
    }

    private CompletableFuture lambda$readStringProperties$8(String string, SuggestionsBuilder suggestionsBuilder, ITagCollection iTagCollection) {
        return this.func_239295_a_(suggestionsBuilder, iTagCollection, string);
    }

    private static CompletableFuture lambda$readProperties$7(Property property, SuggestionsBuilder suggestionsBuilder, ITagCollection iTagCollection) {
        return BlockStateParser.suggestValue(suggestionsBuilder, property).buildFuture();
    }

    private CommandSyntaxException lambda$readBlock$6(int n) {
        this.reader.setCursor(n);
        return STATE_BAD_ID.createWithContext(this.reader, this.blockID.toString());
    }

    private static CompletableFuture lambda$static$5(SuggestionsBuilder suggestionsBuilder, ITagCollection iTagCollection) {
        return suggestionsBuilder.buildFuture();
    }

    private static Message lambda$static$4(Object object, Object object2) {
        return new TranslationTextComponent("argument.block.property.novalue", object, object2);
    }

    private static Message lambda$static$3(Object object, Object object2, Object object3) {
        return new TranslationTextComponent("argument.block.property.invalid", object, object3, object2);
    }

    private static Message lambda$static$2(Object object, Object object2) {
        return new TranslationTextComponent("argument.block.property.duplicate", object2, object);
    }

    private static Message lambda$static$1(Object object, Object object2) {
        return new TranslationTextComponent("argument.block.property.unknown", object, object2);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("argument.block.id.invalid", object);
    }
}

