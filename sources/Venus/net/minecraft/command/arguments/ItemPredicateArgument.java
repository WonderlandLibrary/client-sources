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
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.ItemParser;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ItemPredicateArgument
implements ArgumentType<IResult> {
    private static final Collection<String> EXAMPLES = Arrays.asList("stick", "minecraft:stick", "#stick", "#stick{foo=bar}");
    private static final DynamicCommandExceptionType UNKNOWN_TAG = new DynamicCommandExceptionType(ItemPredicateArgument::lambda$static$0);

    public static ItemPredicateArgument itemPredicate() {
        return new ItemPredicateArgument();
    }

    @Override
    public IResult parse(StringReader stringReader) throws CommandSyntaxException {
        ItemParser itemParser = new ItemParser(stringReader, true).parse();
        if (itemParser.getItem() != null) {
            ItemPredicate itemPredicate = new ItemPredicate(itemParser.getItem(), itemParser.getNbt());
            return arg_0 -> ItemPredicateArgument.lambda$parse$1(itemPredicate, arg_0);
        }
        ResourceLocation resourceLocation = itemParser.getTag();
        return arg_0 -> ItemPredicateArgument.lambda$parse$2(resourceLocation, itemParser, arg_0);
    }

    public static Predicate<ItemStack> getItemPredicate(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        return commandContext.getArgument(string, IResult.class).create(commandContext);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        StringReader stringReader = new StringReader(suggestionsBuilder.getInput());
        stringReader.setCursor(suggestionsBuilder.getStart());
        ItemParser itemParser = new ItemParser(stringReader, true);
        try {
            itemParser.parse();
        } catch (CommandSyntaxException commandSyntaxException) {
            // empty catch block
        }
        return itemParser.fillSuggestions(suggestionsBuilder, ItemTags.getCollection());
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    private static Predicate lambda$parse$2(ResourceLocation resourceLocation, ItemParser itemParser, CommandContext commandContext) throws CommandSyntaxException {
        ITag<Item> iTag = ((CommandSource)commandContext.getSource()).getServer().func_244266_aF().getItemTags().get(resourceLocation);
        if (iTag == null) {
            throw UNKNOWN_TAG.create(resourceLocation.toString());
        }
        return new TagPredicate(iTag, itemParser.getNbt());
    }

    private static Predicate lambda$parse$1(ItemPredicate itemPredicate, CommandContext commandContext) throws CommandSyntaxException {
        return itemPredicate;
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("arguments.item.tag.unknown", object);
    }

    static class ItemPredicate
    implements Predicate<ItemStack> {
        private final Item item;
        @Nullable
        private final CompoundNBT nbt;

        public ItemPredicate(Item item, @Nullable CompoundNBT compoundNBT) {
            this.item = item;
            this.nbt = compoundNBT;
        }

        @Override
        public boolean test(ItemStack itemStack) {
            return itemStack.getItem() == this.item && NBTUtil.areNBTEquals(this.nbt, itemStack.getTag(), true);
        }

        @Override
        public boolean test(Object object) {
            return this.test((ItemStack)object);
        }
    }

    public static interface IResult {
        public Predicate<ItemStack> create(CommandContext<CommandSource> var1) throws CommandSyntaxException;
    }

    static class TagPredicate
    implements Predicate<ItemStack> {
        private final ITag<Item> tag;
        @Nullable
        private final CompoundNBT nbt;

        public TagPredicate(ITag<Item> iTag, @Nullable CompoundNBT compoundNBT) {
            this.tag = iTag;
            this.nbt = compoundNBT;
        }

        @Override
        public boolean test(ItemStack itemStack) {
            return this.tag.contains(itemStack.getItem()) && NBTUtil.areNBTEquals(this.nbt, itemStack.getTag(), true);
        }

        @Override
        public boolean test(Object object) {
            return this.test((ItemStack)object);
        }
    }
}

