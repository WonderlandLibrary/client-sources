/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentIteratorFlag;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentIteratorType;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class ComponentIterator
implements Iterator<Component> {
    private Component component;
    private final ComponentIteratorType type;
    private final Set<ComponentIteratorFlag> flags;
    private final Deque<Component> deque;

    ComponentIterator(@NotNull Component component, @NotNull ComponentIteratorType componentIteratorType, @NotNull Set<ComponentIteratorFlag> set) {
        this.component = component;
        this.type = componentIteratorType;
        this.flags = set;
        this.deque = new ArrayDeque<Component>();
    }

    @Override
    public boolean hasNext() {
        return this.component != null || !this.deque.isEmpty();
    }

    @Override
    public Component next() {
        if (this.component != null) {
            Component component = this.component;
            this.component = null;
            this.type.populate(component, this.deque, this.flags);
            return component;
        }
        if (this.deque.isEmpty()) {
            throw new NoSuchElementException();
        }
        this.component = this.deque.poll();
        return this.next();
    }

    @Override
    public Object next() {
        return this.next();
    }
}

