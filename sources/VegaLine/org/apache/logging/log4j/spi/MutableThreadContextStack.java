/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.spi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.spi.ThreadContextStack;
import org.apache.logging.log4j.util.StringBuilderFormattable;

public class MutableThreadContextStack
implements ThreadContextStack,
StringBuilderFormattable {
    private static final long serialVersionUID = 50505011L;
    private final List<String> list;
    private boolean frozen;

    public MutableThreadContextStack() {
        this(new ArrayList<String>());
    }

    public MutableThreadContextStack(List<String> list) {
        this.list = new ArrayList<String>(list);
    }

    private MutableThreadContextStack(MutableThreadContextStack stack) {
        this.list = new ArrayList<String>(stack.list);
    }

    private void checkInvariants() {
        if (this.frozen) {
            throw new UnsupportedOperationException("context stack has been frozen");
        }
    }

    @Override
    public String pop() {
        this.checkInvariants();
        if (this.list.isEmpty()) {
            return null;
        }
        int last = this.list.size() - 1;
        String result = this.list.remove(last);
        return result;
    }

    @Override
    public String peek() {
        if (this.list.isEmpty()) {
            return null;
        }
        int last = this.list.size() - 1;
        return this.list.get(last);
    }

    @Override
    public void push(String message) {
        this.checkInvariants();
        this.list.add(message);
    }

    @Override
    public int getDepth() {
        return this.list.size();
    }

    @Override
    public List<String> asList() {
        return this.list;
    }

    @Override
    public void trim(int depth) {
        this.checkInvariants();
        if (depth < 0) {
            throw new IllegalArgumentException("Maximum stack depth cannot be negative");
        }
        if (this.list == null) {
            return;
        }
        ArrayList<String> copy = new ArrayList<String>(this.list.size());
        int count = Math.min(depth, this.list.size());
        for (int i = 0; i < count; ++i) {
            copy.add(this.list.get(i));
        }
        this.list.clear();
        this.list.addAll(copy);
    }

    @Override
    public ThreadContextStack copy() {
        return new MutableThreadContextStack(this);
    }

    @Override
    public void clear() {
        this.checkInvariants();
        this.list.clear();
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.list.contains(o);
    }

    @Override
    public Iterator<String> iterator() {
        return this.list.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return this.list.toArray(ts);
    }

    @Override
    public boolean add(String s) {
        this.checkInvariants();
        return this.list.add(s);
    }

    @Override
    public boolean remove(Object o) {
        this.checkInvariants();
        return this.list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> objects) {
        return this.list.containsAll(objects);
    }

    @Override
    public boolean addAll(Collection<? extends String> strings) {
        this.checkInvariants();
        return this.list.addAll(strings);
    }

    @Override
    public boolean removeAll(Collection<?> objects) {
        this.checkInvariants();
        return this.list.removeAll(objects);
    }

    @Override
    public boolean retainAll(Collection<?> objects) {
        this.checkInvariants();
        return this.list.retainAll(objects);
    }

    public String toString() {
        return String.valueOf(this.list);
    }

    @Override
    public void formatTo(StringBuilder buffer) {
        buffer.append('[');
        for (int i = 0; i < this.list.size(); ++i) {
            if (i > 0) {
                buffer.append(',').append(' ');
            }
            buffer.append(this.list.get(i));
        }
        buffer.append(']');
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.list == null ? 0 : this.list.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ThreadContextStack)) {
            return false;
        }
        ThreadContextStack other = (ThreadContextStack)obj;
        List<String> otherAsList = other.asList();
        return !(this.list == null ? otherAsList != null : !this.list.equals(otherAsList));
    }

    @Override
    public ThreadContext.ContextStack getImmutableStackOrNull() {
        return this.copy();
    }

    public void freeze() {
        this.frozen = true;
    }

    public boolean isFrozen() {
        return this.frozen;
    }
}

