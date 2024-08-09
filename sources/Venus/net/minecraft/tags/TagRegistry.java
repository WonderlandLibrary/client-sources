/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tags;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class TagRegistry<T> {
    private ITagCollection<T> collection = ITagCollection.getEmptyTagCollection();
    private final List<NamedTag<T>> tags = Lists.newArrayList();
    private final Function<ITagCollectionSupplier, ITagCollection<T>> supplierToCollectionFunction;

    public TagRegistry(Function<ITagCollectionSupplier, ITagCollection<T>> function) {
        this.supplierToCollectionFunction = function;
    }

    public ITag.INamedTag<T> createTag(String string) {
        NamedTag namedTag = new NamedTag(new ResourceLocation(string));
        this.tags.add(namedTag);
        return namedTag;
    }

    public void fetchTags() {
        this.collection = ITagCollection.getEmptyTagCollection();
        Tag tag = Tag.getEmptyTag();
        this.tags.forEach(arg_0 -> TagRegistry.lambda$fetchTags$1(tag, arg_0));
    }

    public void fetchTags(ITagCollectionSupplier iTagCollectionSupplier) {
        ITagCollection<T> iTagCollection = this.supplierToCollectionFunction.apply(iTagCollectionSupplier);
        this.collection = iTagCollection;
        this.tags.forEach(arg_0 -> TagRegistry.lambda$fetchTags$2(iTagCollection, arg_0));
    }

    public ITagCollection<T> getCollection() {
        return this.collection;
    }

    public List<? extends ITag.INamedTag<T>> getTags() {
        return this.tags;
    }

    public Set<ResourceLocation> getTagIdsFromSupplier(ITagCollectionSupplier iTagCollectionSupplier) {
        ITagCollection<T> iTagCollection = this.supplierToCollectionFunction.apply(iTagCollectionSupplier);
        Set set = this.tags.stream().map(NamedTag::getName).collect(Collectors.toSet());
        ImmutableSet<ResourceLocation> immutableSet = ImmutableSet.copyOf(iTagCollection.getRegisteredTags());
        return Sets.difference(set, immutableSet);
    }

    private static void lambda$fetchTags$2(ITagCollection iTagCollection, NamedTag namedTag) {
        namedTag.fetchTag(iTagCollection::get);
    }

    private static void lambda$fetchTags$1(ITag iTag, NamedTag namedTag) {
        namedTag.fetchTag(arg_0 -> TagRegistry.lambda$fetchTags$0(iTag, arg_0));
    }

    private static ITag lambda$fetchTags$0(ITag iTag, ResourceLocation resourceLocation) {
        return iTag;
    }

    static class NamedTag<T>
    implements ITag.INamedTag<T> {
        @Nullable
        private ITag<T> tag;
        protected final ResourceLocation id;

        private NamedTag(ResourceLocation resourceLocation) {
            this.id = resourceLocation;
        }

        @Override
        public ResourceLocation getName() {
            return this.id;
        }

        private ITag<T> getTag() {
            if (this.tag == null) {
                throw new IllegalStateException("Tag " + this.id + " used before it was bound");
            }
            return this.tag;
        }

        void fetchTag(Function<ResourceLocation, ITag<T>> function) {
            this.tag = function.apply(this.id);
        }

        @Override
        public boolean contains(T t) {
            return this.getTag().contains(t);
        }

        @Override
        public List<T> getAllElements() {
            return this.getTag().getAllElements();
        }
    }
}

