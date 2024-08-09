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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
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

    private MutableThreadContextStack(MutableThreadContextStack mutableThreadContextStack) {
        this.list = new ArrayList<String>(mutableThreadContextStack.list);
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
        int n = this.list.size() - 1;
        String string = this.list.remove(n);
        return string;
    }

    @Override
    public String peek() {
        if (this.list.isEmpty()) {
            return null;
        }
        int n = this.list.size() - 1;
        return this.list.get(n);
    }

    @Override
    public void push(String string) {
        this.checkInvariants();
        this.list.add(string);
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
    public void trim(int n) {
        this.checkInvariants();
        if (n < 0) {
            throw new IllegalArgumentException("Maximum stack depth cannot be negative");
        }
        if (this.list == null) {
            return;
        }
        ArrayList<String> arrayList = new ArrayList<String>(this.list.size());
        int n2 = Math.min(n, this.list.size());
        for (int i = 0; i < n2; ++i) {
            arrayList.add(this.list.get(i));
        }
        this.list.clear();
        this.list.addAll(arrayList);
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
    public boolean contains(Object object) {
        return this.list.contains(object);
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
    public <T> T[] toArray(T[] TArray) {
        return this.list.toArray(TArray);
    }

    @Override
    public boolean add(String string) {
        this.checkInvariants();
        return this.list.add(string);
    }

    @Override
    public boolean remove(Object object) {
        this.checkInvariants();
        return this.list.remove(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return this.list.containsAll(collection);
    }

    @Override
    public boolean addAll(Collection<? extends String> collection) {
        this.checkInvariants();
        return this.list.addAll(collection);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        this.checkInvariants();
        return this.list.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        this.checkInvariants();
        return this.list.retainAll(collection);
    }

    public String toString() {
        return String.valueOf(this.list);
    }

    @Override
    public void formatTo(StringBuilder stringBuilder) {
        stringBuilder.append('[');
        for (int i = 0; i < this.list.size(); ++i) {
            if (i > 0) {
                stringBuilder.append(',').append(' ');
            }
            stringBuilder.append(this.list.get(i));
        }
        stringBuilder.append(']');
    }

    @Override
    public int hashCode() {
        int n = 31;
        int n2 = 1;
        n2 = 31 * n2 + (this.list == null ? 0 : this.list.hashCode());
        return n2;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null) {
            return true;
        }
        if (!(object instanceof ThreadContextStack)) {
            return true;
        }
        ThreadContextStack threadContextStack = (ThreadContextStack)object;
        List<String> list = threadContextStack.asList();
        return this.list == null ? list != null : !this.list.equals(list);
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

    @Override
    public ThreadContext.ContextStack copy() {
        return this.copy();
    }

    @Override
    public boolean add(Object object) {
        return this.add((String)object);
    }
}

