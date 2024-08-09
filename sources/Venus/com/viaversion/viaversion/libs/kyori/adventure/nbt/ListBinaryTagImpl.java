/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Debug$Renderer
 *  org.jetbrains.annotations.Range
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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Debug.Renderer(text="\"ListBinaryTag[type=\" + this.type.toString() + \"]\"", childrenArray="this.tags.toArray()", hasChildren="!this.tags.isEmpty()")
final class ListBinaryTagImpl
extends AbstractBinaryTag
implements ListBinaryTag {
    static final ListBinaryTag EMPTY = new ListBinaryTagImpl(BinaryTagTypes.END, Collections.emptyList());
    private final List<BinaryTag> tags;
    private final BinaryTagType<? extends BinaryTag> elementType;
    private final int hashCode;

    ListBinaryTagImpl(BinaryTagType<? extends BinaryTag> binaryTagType, List<BinaryTag> list) {
        this.tags = Collections.unmodifiableList(list);
        this.elementType = binaryTagType;
        this.hashCode = list.hashCode();
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
    public BinaryTag get(@Range(from=0L, to=0x7FFFFFFFL) int n) {
        return this.tags.get(n);
    }

    @Override
    @NotNull
    public ListBinaryTag set(int n, @NotNull BinaryTag binaryTag, @Nullable Consumer<? super BinaryTag> consumer) {
        return this.edit(arg_0 -> ListBinaryTagImpl.lambda$set$0(n, binaryTag, consumer, arg_0), binaryTag.type());
    }

    @Override
    @NotNull
    public ListBinaryTag remove(int n, @Nullable Consumer<? super BinaryTag> consumer) {
        return this.edit(arg_0 -> ListBinaryTagImpl.lambda$remove$1(n, consumer, arg_0), null);
    }

    @Override
    @NotNull
    public ListBinaryTag add(BinaryTag binaryTag) {
        ListBinaryTagImpl.noAddEnd(binaryTag);
        if (this.elementType != BinaryTagTypes.END) {
            ListBinaryTagImpl.mustBeSameType(binaryTag, this.elementType);
        }
        return this.edit(arg_0 -> ListBinaryTagImpl.lambda$add$2(binaryTag, arg_0), binaryTag.type());
    }

    @Override
    @NotNull
    public ListBinaryTag add(Iterable<? extends BinaryTag> iterable) {
        if (iterable instanceof Collection && ((Collection)iterable).isEmpty()) {
            return this;
        }
        BinaryTagType<?> binaryTagType = ListBinaryTagImpl.mustBeSameType(iterable);
        return this.edit(arg_0 -> ListBinaryTagImpl.lambda$add$3(iterable, arg_0), binaryTagType);
    }

    static void noAddEnd(BinaryTag binaryTag) {
        if (binaryTag.type() == BinaryTagTypes.END) {
            throw new IllegalArgumentException(String.format("Cannot add a %s to a %s", BinaryTagTypes.END, BinaryTagTypes.LIST));
        }
    }

    static BinaryTagType<?> mustBeSameType(Iterable<? extends BinaryTag> iterable) {
        BinaryTagType<? extends BinaryTag> binaryTagType = null;
        for (BinaryTag binaryTag : iterable) {
            if (binaryTagType == null) {
                binaryTagType = binaryTag.type();
                continue;
            }
            ListBinaryTagImpl.mustBeSameType(binaryTag, binaryTagType);
        }
        return binaryTagType;
    }

    static void mustBeSameType(BinaryTag binaryTag, BinaryTagType<? extends BinaryTag> binaryTagType) {
        if (binaryTag.type() != binaryTagType) {
            throw new IllegalArgumentException(String.format("Trying to add tag of type %s to list of %s", binaryTag.type(), binaryTagType));
        }
    }

    private ListBinaryTag edit(Consumer<List<BinaryTag>> consumer, @Nullable BinaryTagType<? extends BinaryTag> binaryTagType) {
        ArrayList<BinaryTag> arrayList = new ArrayList<BinaryTag>(this.tags);
        consumer.accept(arrayList);
        BinaryTagType<? extends BinaryTag> binaryTagType2 = this.elementType;
        if (binaryTagType != null && binaryTagType2 == BinaryTagTypes.END) {
            binaryTagType2 = binaryTagType;
        }
        return new ListBinaryTagImpl(binaryTagType2, arrayList);
    }

    @Override
    @NotNull
    public Stream<BinaryTag> stream() {
        return this.tags.stream();
    }

    @Override
    public Iterator<BinaryTag> iterator() {
        Iterator<BinaryTag> iterator2 = this.tags.iterator();
        return new Iterator<BinaryTag>(this, iterator2){
            final Iterator val$iterator;
            final ListBinaryTagImpl this$0;
            {
                this.this$0 = listBinaryTagImpl;
                this.val$iterator = iterator2;
            }

            @Override
            public boolean hasNext() {
                return this.val$iterator.hasNext();
            }

            @Override
            public BinaryTag next() {
                return (BinaryTag)this.val$iterator.next();
            }

            @Override
            public void forEachRemaining(Consumer<? super BinaryTag> consumer) {
                this.val$iterator.forEachRemaining(consumer);
            }

            @Override
            public Object next() {
                return this.next();
            }
        };
    }

    @Override
    public void forEach(Consumer<? super BinaryTag> consumer) {
        this.tags.forEach(consumer);
    }

    @Override
    public Spliterator<BinaryTag> spliterator() {
        return Spliterators.spliterator(this.tags, 1040);
    }

    public boolean equals(Object object) {
        return this == object || object instanceof ListBinaryTagImpl && this.tags.equals(((ListBinaryTagImpl)object).tags);
    }

    public int hashCode() {
        return this.hashCode;
    }

    @Override
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("tags", this.tags), ExaminableProperty.of("type", this.elementType));
    }

    @Override
    @NotNull
    public Object add(Iterable iterable) {
        return this.add(iterable);
    }

    @Override
    @NotNull
    public Object add(BinaryTag binaryTag) {
        return this.add(binaryTag);
    }

    private static void lambda$add$3(Iterable iterable, List list) {
        for (BinaryTag binaryTag : iterable) {
            list.add(binaryTag);
        }
    }

    private static void lambda$add$2(BinaryTag binaryTag, List list) {
        list.add(binaryTag);
    }

    private static void lambda$remove$1(int n, Consumer consumer, List list) {
        BinaryTag binaryTag = (BinaryTag)list.remove(n);
        if (consumer != null) {
            consumer.accept(binaryTag);
        }
    }

    private static void lambda$set$0(int n, BinaryTag binaryTag, Consumer consumer, List list) {
        BinaryTag binaryTag2 = list.set(n, binaryTag);
        if (consumer != null) {
            consumer.accept(binaryTag2);
        }
    }
}

