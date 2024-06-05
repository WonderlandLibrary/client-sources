/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.AbstractBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagType;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagTypes;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.ListBinaryTag;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

@Debug.Renderer(text="\"ListBinaryTag[type=\" + this.type.toString() + \"]\"", childrenArray="this.tags.toArray()", hasChildren="!this.tags.isEmpty()")
final class ListBinaryTagImpl
extends AbstractBinaryTag
implements ListBinaryTag {
    static final ListBinaryTag EMPTY = new ListBinaryTagImpl(BinaryTagTypes.END, Collections.<BinaryTag>emptyList());
    private final List<BinaryTag> tags;
    private final BinaryTagType<? extends BinaryTag> elementType;
    private final int hashCode;

    ListBinaryTagImpl(BinaryTagType<? extends BinaryTag> elementType, List<BinaryTag> tags) {
        this.tags = Collections.unmodifiableList(tags);
        this.elementType = elementType;
        this.hashCode = tags.hashCode();
    }

    @Override
    @NotNull
    public BinaryTagType<? extends BinaryTag> elementType() {
        return this.elementType;
    }

    @Override
    public int size() {
        return this.tags.size();
    }

    @Override
    @NotNull
    public BinaryTag get(@Range(from=0L, to=0x7FFFFFFFL) int index) {
        return this.tags.get(index);
    }

    @Override
    @NotNull
    public ListBinaryTag set(int index, @NotNull BinaryTag newTag, @Nullable Consumer<? super BinaryTag> removed) {
        return this.edit(tags -> {
            BinaryTag oldTag = tags.set(index, newTag);
            if (removed == null) return;
            removed.accept(oldTag);
        }, newTag.type());
    }

    @Override
    @NotNull
    public ListBinaryTag remove(int index, @Nullable Consumer<? super BinaryTag> removed) {
        return this.edit(tags -> {
            BinaryTag oldTag = (BinaryTag)tags.remove(index);
            if (removed == null) return;
            removed.accept(oldTag);
        }, null);
    }

    @Override
    @NotNull
    public ListBinaryTag add(BinaryTag tag) {
        ListBinaryTagImpl.noAddEnd(tag);
        if (this.elementType == BinaryTagTypes.END) return this.edit(tags -> tags.add(tag), tag.type());
        ListBinaryTagImpl.mustBeSameType(tag, this.elementType);
        return this.edit(tags -> tags.add(tag), tag.type());
    }

    @Override
    @NotNull
    public ListBinaryTag add(Iterable<? extends BinaryTag> tagsToAdd) {
        if (tagsToAdd instanceof Collection && ((Collection)tagsToAdd).isEmpty()) {
            return this;
        }
        BinaryTagType<?> type = ListBinaryTagImpl.mustBeSameType(tagsToAdd);
        return this.edit(tags -> {
            Iterator iterator = tagsToAdd.iterator();
            while (iterator.hasNext()) {
                BinaryTag tag = (BinaryTag)iterator.next();
                tags.add(tag);
            }
        }, type);
    }

    static void noAddEnd(BinaryTag tag) {
        if (tag.type() != BinaryTagTypes.END) return;
        throw new IllegalArgumentException(String.format("Cannot add a %s to a %s", BinaryTagTypes.END, BinaryTagTypes.LIST));
    }

    static BinaryTagType<?> mustBeSameType(Iterable<? extends BinaryTag> tags) {
        BinaryTagType<? extends BinaryTag> type = null;
        Iterator<? extends BinaryTag> iterator = tags.iterator();
        while (iterator.hasNext()) {
            BinaryTag tag = iterator.next();
            if (type == null) {
                type = tag.type();
                continue;
            }
            ListBinaryTagImpl.mustBeSameType(tag, type);
        }
        return type;
    }

    static void mustBeSameType(BinaryTag tag, BinaryTagType<? extends BinaryTag> type) {
        if (tag.type() == type) return;
        throw new IllegalArgumentException(String.format("Trying to add tag of type %s to list of %s", tag.type(), type));
    }

    private ListBinaryTag edit(Consumer<List<BinaryTag>> consumer, @Nullable BinaryTagType<? extends BinaryTag> maybeElementType) {
        ArrayList<BinaryTag> tags = new ArrayList<BinaryTag>(this.tags);
        consumer.accept(tags);
        BinaryTagType<? extends BinaryTag> elementType = this.elementType;
        if (maybeElementType == null) return new ListBinaryTagImpl(elementType, tags);
        if (elementType != BinaryTagTypes.END) return new ListBinaryTagImpl(elementType, tags);
        elementType = maybeElementType;
        return new ListBinaryTagImpl(elementType, tags);
    }

    @Override
    @NotNull
    public Stream<BinaryTag> stream() {
        return this.tags.stream();
    }

    @Override
    public Iterator<BinaryTag> iterator() {
        final Iterator<BinaryTag> iterator = this.tags.iterator();
        return new Iterator<BinaryTag>(){

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public BinaryTag next() {
                return (BinaryTag)iterator.next();
            }

            @Override
            public void forEachRemaining(Consumer<? super BinaryTag> action) {
                iterator.forEachRemaining(action);
            }
        };
    }

    @Override
    public void forEach(Consumer<? super BinaryTag> action) {
        this.tags.forEach(action);
    }

    @Override
    public Spliterator<BinaryTag> spliterator() {
        return Spliterators.spliterator(this.tags, 1040);
    }

    public boolean equals(Object that) {
        if (this == that) return true;
        if (!(that instanceof ListBinaryTagImpl)) return false;
        if (!this.tags.equals(((ListBinaryTagImpl)that).tags)) return false;
        return true;
    }

    public int hashCode() {
        return this.hashCode;
    }

    @Override
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("tags", this.tags), ExaminableProperty.of("type", this.elementType));
    }
}

