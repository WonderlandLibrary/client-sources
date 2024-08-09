/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tags;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Set;
import net.minecraft.tags.ITag;

public class Tag<T>
implements ITag<T> {
    private final ImmutableList<T> immutableContents;
    private final Set<T> contents;
    @VisibleForTesting
    protected final Class<?> contentsClassType;

    protected Tag(Set<T> set, Class<?> clazz) {
        this.contentsClassType = clazz;
        this.contents = set;
        this.immutableContents = ImmutableList.copyOf(set);
    }

    public static <T> Tag<T> getEmptyTag() {
        return new Tag(ImmutableSet.of(), Void.class);
    }

    public static <T> Tag<T> getTagFromContents(Set<T> set) {
        return new Tag<T>(set, Tag.getContentsClass(set));
    }

    @Override
    public boolean contains(T t) {
        return this.contentsClassType.isInstance(t) && this.contents.contains(t);
    }

    @Override
    public List<T> getAllElements() {
        return this.immutableContents;
    }

    private static <T> Class<?> getContentsClass(Set<T> set) {
        if (set.isEmpty()) {
            return Void.class;
        }
        Class<?> clazz = null;
        for (T t : set) {
            if (clazz == null) {
                clazz = t.getClass();
                continue;
            }
            clazz = Tag.findCommonParentClass(clazz, t.getClass());
        }
        return clazz;
    }

    private static Class<?> findCommonParentClass(Class<?> clazz, Class<?> clazz2) {
        while (!clazz.isAssignableFrom(clazz2)) {
            clazz = clazz.getSuperclass();
        }
        return clazz;
    }
}

