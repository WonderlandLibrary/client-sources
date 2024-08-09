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
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.BlockStateParser;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.state.Property;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BlockPredicateArgument
implements ArgumentType<IResult> {
    private static final Collection<String> EXAMPLES = Arrays.asList("stone", "minecraft:stone", "stone[foo=bar]", "#stone", "#stone[foo=bar]{baz=nbt}");
    private static final DynamicCommandExceptionType UNKNOWN_TAG = new DynamicCommandExceptionType(BlockPredicateArgument::lambda$static$0);

    public static BlockPredicateArgument blockPredicate() {
        return new BlockPredicateArgument();
    }

    @Override
    public IResult parse(StringReader stringReader) throws CommandSyntaxException {
        BlockStateParser blockStateParser = new BlockStateParser(stringReader, true).parse(false);
        if (blockStateParser.getState() != null) {
            BlockPredicate blockPredicate = new BlockPredicate(blockStateParser.getState(), blockStateParser.getProperties().keySet(), blockStateParser.getNbt());
            return arg_0 -> BlockPredicateArgument.lambda$parse$1(blockPredicate, arg_0);
        }
        ResourceLocation resourceLocation = blockStateParser.getTag();
        return arg_0 -> BlockPredicateArgument.lambda$parse$2(resourceLocation, blockStateParser, arg_0);
    }

    public static Predicate<CachedBlockInfo> getBlockPredicate(CommandContext<CommandSource> commandContext, String string) throws CommandSyntaxException {
        return commandContext.getArgument(string, IResult.class).create(commandContext.getSource().getServer().func_244266_aF());
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> commandContext, SuggestionsBuilder suggestionsBuilder) {
        StringReader stringReader = new StringReader(suggestionsBuilder.getInput());
        stringReader.setCursor(suggestionsBuilder.getStart());
        BlockStateParser blockStateParser = new BlockStateParser(stringReader, true);
        try {
            blockStateParser.parse(false);
        } catch (CommandSyntaxException commandSyntaxException) {
            // empty catch block
        }
        return blockStateParser.getSuggestions(suggestionsBuilder, BlockTags.getCollection());
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    @Override
    public Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    private static Predicate lambda$parse$2(ResourceLocation resourceLocation, BlockStateParser blockStateParser, ITagCollectionSupplier iTagCollectionSupplier) throws CommandSyntaxException {
        ITag<Block> iTag = iTagCollectionSupplier.getBlockTags().get(resourceLocation);
        if (iTag == null) {
            throw UNKNOWN_TAG.create(resourceLocation.toString());
        }
        return new TagPredicate(iTag, blockStateParser.getStringProperties(), blockStateParser.getNbt());
    }

    private static Predicate lambda$parse$1(BlockPredicate blockPredicate, ITagCollectionSupplier iTagCollectionSupplier) throws CommandSyntaxException {
        return blockPredicate;
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("arguments.block.tag.unknown", object);
    }

    static class BlockPredicate
    implements Predicate<CachedBlockInfo> {
        private final BlockState state;
        private final Set<Property<?>> properties;
        @Nullable
        private final CompoundNBT nbt;

        public BlockPredicate(BlockState blockState, Set<Property<?>> set, @Nullable CompoundNBT compoundNBT) {
            this.state = blockState;
            this.properties = set;
            this.nbt = compoundNBT;
        }

        @Override
        public boolean test(CachedBlockInfo cachedBlockInfo) {
            BlockState blockState = cachedBlockInfo.getBlockState();
            if (!blockState.isIn(this.state.getBlock())) {
                return true;
            }
            for (Property<?> property : this.properties) {
                if (blockState.get(property) == this.state.get(property)) continue;
                return true;
            }
            if (this.nbt == null) {
                return false;
            }
            TileEntity tileEntity = cachedBlockInfo.getTileEntity();
            return tileEntity != null && NBTUtil.areNBTEquals(this.nbt, tileEntity.write(new CompoundNBT()), true);
        }

        @Override
        public boolean test(Object object) {
            return this.test((CachedBlockInfo)object);
        }
    }

    public static interface IResult {
        public Predicate<CachedBlockInfo> create(ITagCollectionSupplier var1) throws CommandSyntaxException;
    }

    static class TagPredicate
    implements Predicate<CachedBlockInfo> {
        private final ITag<Block> tag;
        @Nullable
        private final CompoundNBT nbt;
        private final Map<String, String> properties;

        private TagPredicate(ITag<Block> iTag, Map<String, String> map, @Nullable CompoundNBT compoundNBT) {
            this.tag = iTag;
            this.properties = map;
            this.nbt = compoundNBT;
        }

        @Override
        public boolean test(CachedBlockInfo cachedBlockInfo) {
            BlockState blockState = cachedBlockInfo.getBlockState();
            if (!blockState.isIn(this.tag)) {
                return true;
            }
            for (Map.Entry<String, String> entry : this.properties.entrySet()) {
                Property<?> property = blockState.getBlock().getStateContainer().getProperty(entry.getKey());
                if (property == null) {
                    return true;
                }
                Comparable comparable = property.parseValue(entry.getValue()).orElse(null);
                if (comparable == null) {
                    return true;
                }
                if (blockState.get(property) == comparable) continue;
                return true;
            }
            if (this.nbt == null) {
                return false;
            }
            TileEntity tileEntity = cachedBlockInfo.getTileEntity();
            return tileEntity != null && NBTUtil.areNBTEquals(this.nbt, tileEntity.write(new CompoundNBT()), true);
        }

        @Override
        public boolean test(Object object) {
            return this.test((CachedBlockInfo)object);
        }
    }
}

