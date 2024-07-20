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

public class DefaultThreadContextStack
implements ThreadContextStack,
StringBuilderFormattable {
    private static final long serialVersionUID = 5050501L;
    private static final ThreadLocal<MutableThreadContextStack> STACK = new ThreadLocal();
    private final boolean useStack;

    public DefaultThreadContextStack(boolean useStack) {
        this.useStack = useStack;
    }

    private MutableThreadContextStack getNonNullStackCopy() {
        MutableThreadContextStack values = STACK.get();
        return values == null ? new MutableThreadContextStack() : values.copy();
    }

    @Override
    public boolean add(String s) {
        if (!this.useStack) {
            return false;
        }
        MutableThreadContextStack copy = this.getNonNullStackCopy();
        copy.add(s);
        copy.freeze();
        STACK.set(copy);
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends String> strings) {
        if (!this.useStack || strings.isEmpty()) {
            return false;
        }
        MutableThreadContextStack copy = this.getNonNullStackCopy();
        copy.addAll(strings);
        copy.freeze();
        STACK.set(copy);
        return true;
    }

    @Override
    public List<String> asList() {
        MutableThreadContextStack values = STACK.get();
        if (values == null) {
            return Collections.emptyList();
        }
        return values.asList();
    }

    @Override
    public void clear() {
        STACK.remove();
    }

    @Override
    public boolean contains(Object o) {
        MutableThreadContextStack values = STACK.get();
        return values != null && values.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> objects) {
        if (objects.isEmpty()) {
            return true;
        }
        MutableThreadContextStack values = STACK.get();
        return values != null && values.containsAll(objects);
    }

    @Override
    public ThreadContextStack copy() {
        MutableThreadContextStack values = null;
        if (!this.useStack || (values = STACK.get()) == null) {
            return new MutableThreadContextStack();
        }
        return values.copy();
    }

    @Override
    public boolean equals(Object obj) {
        ThreadContextStack other;
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof DefaultThreadContextStack) {
            other = (DefaultThreadContextStack)obj;
            if (this.useStack != other.useStack) {
                return false;
            }
        }
        if (!(obj instanceof ThreadContextStack)) {
            return false;
        }
        other = (ThreadContextStack)obj;
        MutableThreadContextStack values = STACK.get();
        if (values == null) {
            return false;
        }
        return values.equals(other);
    }

    @Override
    public int getDepth() {
        MutableThreadContextStack values = STACK.get();
        return values == null ? 0 : values.getDepth();
    }

    @Override
    public int hashCode() {
        MutableThreadContextStack values = STACK.get();
        int prime = 31;
        int result = 1;
        result = 31 * result + (values == null ? 0 : values.hashCode());
        return result;
    }

    @Override
    public boolean isEmpty() {
        MutableThreadContextStack values = STACK.get();
        return values == null || values.isEmpty();
    }

    @Override
    public Iterator<String> iterator() {
        MutableThreadContextStack values = STACK.get();
        if (values == null) {
            List empty = Collections.emptyList();
            return empty.iterator();
        }
        return values.iterator();
    }

    @Override
    public String peek() {
        MutableThreadContextStack values = STACK.get();
        if (values == null || values.size() == 0) {
            return "";
        }
        return values.peek();
    }

    @Override
    public String pop() {
        if (!this.useStack) {
            return "";
        }
        MutableThreadContextStack values = STACK.get();
        if (values == null || values.size() == 0) {
            return "";
        }
        MutableThreadContextStack copy = (MutableThreadContextStack)values.copy();
        String result = copy.pop();
        copy.freeze();
        STACK.set(copy);
        return result;
    }

    @Override
    public void push(String message) {
        if (!this.useStack) {
            return;
        }
        this.add(message);
    }

    @Override
    public boolean remove(Object o) {
        if (!this.useStack) {
            return false;
        }
        MutableThreadContextStack values = STACK.get();
        if (values == null || values.size() == 0) {
            return false;
        }
        MutableThreadContextStack copy = (MutableThreadContextStack)values.copy();
        boolean result = copy.remove(o);
        copy.freeze();
        STACK.set(copy);
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> objects) {
        if (!this.useStack || objects.isEmpty()) {
            return false;
        }
        MutableThreadContextStack values = STACK.get();
        if (values == null || values.isEmpty()) {
            return false;
        }
        MutableThreadContextStack copy = (MutableThreadContextStack)values.copy();
        boolean result = copy.removeAll(objects);
        copy.freeze();
        STACK.set(copy);
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> objects) {
        if (!this.useStack || objects.isEmpty()) {
            return false;
        }
        MutableThreadContextStack values = STACK.get();
        if (values == null || values.isEmpty()) {
            return false;
        }
        MutableThreadContextStack copy = (MutableThreadContextStack)values.copy();
        boolean result = copy.retainAll(objects);
        copy.freeze();
        STACK.set(copy);
        return result;
    }

    @Override
    public int size() {
        MutableThreadContextStack values = STACK.get();
        return values == null ? 0 : values.size();
    }

    @Override
    public Object[] toArray() {
        MutableThreadContextStack result = STACK.get();
        if (result == null) {
            return new String[0];
        }
        return result.toArray(new Object[result.size()]);
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        MutableThreadContextStack result = STACK.get();
        if (result == null) {
            if (ts.length > 0) {
                ts[0] = null;
            }
            return ts;
        }
        return result.toArray(ts);
    }

    public String toString() {
        MutableThreadContextStack values = STACK.get();
        return values == null ? "[]" : values.toString();
    }

    @Override
    public void formatTo(StringBuilder buffer) {
        MutableThreadContextStack values = STACK.get();
        if (values == null) {
            buffer.append("[]");
        } else {
            StringBuilders.appendValue(buffer, values);
        }
    }

    @Override
    public void trim(int depth) {
        if (depth < 0) {
            throw new IllegalArgumentException("Maximum stack depth cannot be negative");
        }
        MutableThreadContextStack values = STACK.get();
        if (values == null) {
            return;
        }
        MutableThreadContextStack copy = (MutableThreadContextStack)values.copy();
        copy.trim(depth);
        copy.freeze();
        STACK.set(copy);
    }

    @Override
    public ThreadContext.ContextStack getImmutableStackOrNull() {
        return STACK.get();
    }
}

