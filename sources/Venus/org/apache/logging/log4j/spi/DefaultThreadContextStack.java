/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.spi;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.spi.MutableThreadContextStack;
import org.apache.logging.log4j.spi.ThreadContextStack;
import org.apache.logging.log4j.util.StringBuilderFormattable;
import org.apache.logging.log4j.util.StringBuilders;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultThreadContextStack
implements ThreadContextStack,
StringBuilderFormattable {
    private static final long serialVersionUID = 5050501L;
    private static final ThreadLocal<MutableThreadContextStack> STACK = new ThreadLocal();
    private final boolean useStack;

    public DefaultThreadContextStack(boolean bl) {
        this.useStack = bl;
    }

    private MutableThreadContextStack getNonNullStackCopy() {
        MutableThreadContextStack mutableThreadContextStack = STACK.get();
        return mutableThreadContextStack == null ? new MutableThreadContextStack() : mutableThreadContextStack.copy();
    }

    @Override
    public boolean add(String string) {
        if (!this.useStack) {
            return true;
        }
        MutableThreadContextStack mutableThreadContextStack = this.getNonNullStackCopy();
        mutableThreadContextStack.add(string);
        mutableThreadContextStack.freeze();
        STACK.set(mutableThreadContextStack);
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends String> collection) {
        if (!this.useStack || collection.isEmpty()) {
            return true;
        }
        MutableThreadContextStack mutableThreadContextStack = this.getNonNullStackCopy();
        mutableThreadContextStack.addAll(collection);
        mutableThreadContextStack.freeze();
        STACK.set(mutableThreadContextStack);
        return false;
    }

    @Override
    public List<String> asList() {
        MutableThreadContextStack mutableThreadContextStack = STACK.get();
        if (mutableThreadContextStack == null) {
            return Collections.emptyList();
        }
        return mutableThreadContextStack.asList();
    }

    @Override
    public void clear() {
        STACK.remove();
    }

    @Override
    public boolean contains(Object object) {
        MutableThreadContextStack mutableThreadContextStack = STACK.get();
        return mutableThreadContextStack != null && mutableThreadContextStack.contains(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        if (collection.isEmpty()) {
            return false;
        }
        MutableThreadContextStack mutableThreadContextStack = STACK.get();
        return mutableThreadContextStack != null && mutableThreadContextStack.containsAll(collection);
    }

    @Override
    public ThreadContextStack copy() {
        MutableThreadContextStack mutableThreadContextStack = null;
        if (!this.useStack || (mutableThreadContextStack = STACK.get()) == null) {
            return new MutableThreadContextStack();
        }
        return mutableThreadContextStack.copy();
    }

    @Override
    public boolean equals(Object object) {
        ThreadContextStack threadContextStack;
        if (this == object) {
            return false;
        }
        if (object == null) {
            return true;
        }
        if (object instanceof DefaultThreadContextStack) {
            threadContextStack = (DefaultThreadContextStack)object;
            if (this.useStack != threadContextStack.useStack) {
                return true;
            }
        }
        if (!(object instanceof ThreadContextStack)) {
            return true;
        }
        threadContextStack = (ThreadContextStack)object;
        MutableThreadContextStack mutableThreadContextStack = STACK.get();
        if (mutableThreadContextStack == null) {
            return true;
        }
        return mutableThreadContextStack.equals(threadContextStack);
    }

    @Override
    public int getDepth() {
        MutableThreadContextStack mutableThreadContextStack = STACK.get();
        return mutableThreadContextStack == null ? 0 : mutableThreadContextStack.getDepth();
    }

    @Override
    public int hashCode() {
        MutableThreadContextStack mutableThreadContextStack = STACK.get();
        int n = 31;
        int n2 = 1;
        n2 = 31 * n2 + (mutableThreadContextStack == null ? 0 : mutableThreadContextStack.hashCode());
        return n2;
    }

    @Override
    public boolean isEmpty() {
        MutableThreadContextStack mutableThreadContextStack = STACK.get();
        return mutableThreadContextStack == null || mutableThreadContextStack.isEmpty();
    }

    @Override
    public Iterator<String> iterator() {
        MutableThreadContextStack mutableThreadContextStack = STACK.get();
        if (mutableThreadContextStack == null) {
            List list = Collections.emptyList();
            return list.iterator();
        }
        return mutableThreadContextStack.iterator();
    }

    @Override
    public String peek() {
        MutableThreadContextStack mutableThreadContextStack = STACK.get();
        if (mutableThreadContextStack == null || mutableThreadContextStack.size() == 0) {
            return "";
        }
        return mutableThreadContextStack.peek();
    }

    @Override
    public String pop() {
        if (!this.useStack) {
            return "";
        }
        MutableThreadContextStack mutableThreadContextStack = STACK.get();
        if (mutableThreadContextStack == null || mutableThreadContextStack.size() == 0) {
            return "";
        }
        MutableThreadContextStack mutableThreadContextStack2 = (MutableThreadContextStack)mutableThreadContextStack.copy();
        String string = mutableThreadContextStack2.pop();
        mutableThreadContextStack2.freeze();
        STACK.set(mutableThreadContextStack2);
        return string;
    }

    @Override
    public void push(String string) {
        if (!this.useStack) {
            return;
        }
        this.add(string);
    }

    @Override
    public boolean remove(Object object) {
        if (!this.useStack) {
            return true;
        }
        MutableThreadContextStack mutableThreadContextStack = STACK.get();
        if (mutableThreadContextStack == null || mutableThreadContextStack.size() == 0) {
            return true;
        }
        MutableThreadContextStack mutableThreadContextStack2 = (MutableThreadContextStack)mutableThreadContextStack.copy();
        boolean bl = mutableThreadContextStack2.remove(object);
        mutableThreadContextStack2.freeze();
        STACK.set(mutableThreadContextStack2);
        return bl;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        if (!this.useStack || collection.isEmpty()) {
            return true;
        }
        MutableThreadContextStack mutableThreadContextStack = STACK.get();
        if (mutableThreadContextStack == null || mutableThreadContextStack.isEmpty()) {
            return true;
        }
        MutableThreadContextStack mutableThreadContextStack2 = (MutableThreadContextStack)mutableThreadContextStack.copy();
        boolean bl = mutableThreadContextStack2.removeAll(collection);
        mutableThreadContextStack2.freeze();
        STACK.set(mutableThreadContextStack2);
        return bl;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        if (!this.useStack || collection.isEmpty()) {
            return true;
        }
        MutableThreadContextStack mutableThreadContextStack = STACK.get();
        if (mutableThreadContextStack == null || mutableThreadContextStack.isEmpty()) {
            return true;
        }
        MutableThreadContextStack mutableThreadContextStack2 = (MutableThreadContextStack)mutableThreadContextStack.copy();
        boolean bl = mutableThreadContextStack2.retainAll(collection);
        mutableThreadContextStack2.freeze();
        STACK.set(mutableThreadContextStack2);
        return bl;
    }

    @Override
    public int size() {
        MutableThreadContextStack mutableThreadContextStack = STACK.get();
        return mutableThreadContextStack == null ? 0 : mutableThreadContextStack.size();
    }

    @Override
    public Object[] toArray() {
        MutableThreadContextStack mutableThreadContextStack = STACK.get();
        if (mutableThreadContextStack == null) {
            return new String[0];
        }
        return mutableThreadContextStack.toArray(new Object[mutableThreadContextStack.size()]);
    }

    @Override
    public <T> T[] toArray(T[] TArray) {
        MutableThreadContextStack mutableThreadContextStack = STACK.get();
        if (mutableThreadContextStack == null) {
            if (TArray.length > 0) {
                TArray[0] = null;
            }
            return TArray;
        }
        return mutableThreadContextStack.toArray(TArray);
    }

    public String toString() {
        MutableThreadContextStack mutableThreadContextStack = STACK.get();
        return mutableThreadContextStack == null ? "[]" : mutableThreadContextStack.toString();
    }

    @Override
    public void formatTo(StringBuilder stringBuilder) {
        MutableThreadContextStack mutableThreadContextStack = STACK.get();
        if (mutableThreadContextStack == null) {
            stringBuilder.append("[]");
        } else {
            StringBuilders.appendValue(stringBuilder, mutableThreadContextStack);
        }
    }

    @Override
    public void trim(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Maximum stack depth cannot be negative");
        }
        MutableThreadContextStack mutableThreadContextStack = STACK.get();
        if (mutableThreadContextStack == null) {
            return;
        }
        MutableThreadContextStack mutableThreadContextStack2 = (MutableThreadContextStack)mutableThreadContextStack.copy();
        mutableThreadContextStack2.trim(n);
        mutableThreadContextStack2.freeze();
        STACK.set(mutableThreadContextStack2);
    }

    @Override
    public ThreadContext.ContextStack getImmutableStackOrNull() {
        return STACK.get();
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

