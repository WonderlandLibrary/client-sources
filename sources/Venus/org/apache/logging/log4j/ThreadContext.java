/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.apache.logging.log4j.spi.CleanableThreadContextMap;
import org.apache.logging.log4j.spi.DefaultThreadContextMap;
import org.apache.logging.log4j.spi.DefaultThreadContextStack;
import org.apache.logging.log4j.spi.NoOpThreadContextMap;
import org.apache.logging.log4j.spi.ReadOnlyThreadContextMap;
import org.apache.logging.log4j.spi.ThreadContextMap;
import org.apache.logging.log4j.spi.ThreadContextMap2;
import org.apache.logging.log4j.spi.ThreadContextMapFactory;
import org.apache.logging.log4j.spi.ThreadContextStack;
import org.apache.logging.log4j.util.PropertiesUtil;

public final class ThreadContext {
    public static final Map<String, String> EMPTY_MAP = Collections.emptyMap();
    public static final ThreadContextStack EMPTY_STACK = new EmptyThreadContextStack(null);
    private static final String DISABLE_MAP = "disableThreadContextMap";
    private static final String DISABLE_STACK = "disableThreadContextStack";
    private static final String DISABLE_ALL = "disableThreadContext";
    private static boolean disableAll;
    private static boolean useMap;
    private static boolean useStack;
    private static ThreadContextMap contextMap;
    private static ThreadContextStack contextStack;
    private static ReadOnlyThreadContextMap readOnlyContextMap;

    private ThreadContext() {
    }

    static void init() {
        contextMap = null;
        PropertiesUtil propertiesUtil = PropertiesUtil.getProperties();
        disableAll = propertiesUtil.getBooleanProperty(DISABLE_ALL);
        useStack = !propertiesUtil.getBooleanProperty(DISABLE_STACK) && !disableAll;
        useMap = !propertiesUtil.getBooleanProperty(DISABLE_MAP) && !disableAll;
        contextStack = new DefaultThreadContextStack(useStack);
        contextMap = !useMap ? new NoOpThreadContextMap() : ThreadContextMapFactory.createThreadContextMap();
        if (contextMap instanceof ReadOnlyThreadContextMap) {
            readOnlyContextMap = (ReadOnlyThreadContextMap)((Object)contextMap);
        }
    }

    public static void put(String string, String string2) {
        contextMap.put(string, string2);
    }

    public static void putAll(Map<String, String> map) {
        if (contextMap instanceof ThreadContextMap2) {
            ((ThreadContextMap2)contextMap).putAll(map);
        } else if (contextMap instanceof DefaultThreadContextMap) {
            ((DefaultThreadContextMap)contextMap).putAll(map);
        } else {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                contextMap.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public static String get(String string) {
        return contextMap.get(string);
    }

    public static void remove(String string) {
        contextMap.remove(string);
    }

    public static void removeAll(Iterable<String> iterable) {
        if (contextMap instanceof CleanableThreadContextMap) {
            ((CleanableThreadContextMap)contextMap).removeAll(iterable);
        } else if (contextMap instanceof DefaultThreadContextMap) {
            ((DefaultThreadContextMap)contextMap).removeAll(iterable);
        } else {
            for (String string : iterable) {
                contextMap.remove(string);
            }
        }
    }

    public static void clearMap() {
        contextMap.clear();
    }

    public static void clearAll() {
        ThreadContext.clearMap();
        ThreadContext.clearStack();
    }

    public static boolean containsKey(String string) {
        return contextMap.containsKey(string);
    }

    public static Map<String, String> getContext() {
        return contextMap.getCopy();
    }

    public static Map<String, String> getImmutableContext() {
        Map<String, String> map = contextMap.getImmutableMapOrNull();
        return map == null ? EMPTY_MAP : map;
    }

    public static ReadOnlyThreadContextMap getThreadContextMap() {
        return readOnlyContextMap;
    }

    public static boolean isEmpty() {
        return contextMap.isEmpty();
    }

    public static void clearStack() {
        contextStack.clear();
    }

    public static ContextStack cloneStack() {
        return contextStack.copy();
    }

    public static ContextStack getImmutableStack() {
        ContextStack contextStack = ThreadContext.contextStack.getImmutableStackOrNull();
        return contextStack == null ? EMPTY_STACK : contextStack;
    }

    public static void setStack(Collection<String> collection) {
        if (collection.isEmpty() || !useStack) {
            return;
        }
        contextStack.clear();
        contextStack.addAll(collection);
    }

    public static int getDepth() {
        return contextStack.getDepth();
    }

    public static String pop() {
        return contextStack.pop();
    }

    public static String peek() {
        return contextStack.peek();
    }

    public static void push(String string) {
        contextStack.push(string);
    }

    public static void push(String string, Object ... objectArray) {
        contextStack.push(ParameterizedMessage.format(string, objectArray));
    }

    public static void removeStack() {
        contextStack.clear();
    }

    public static void trim(int n) {
        contextStack.trim(n);
    }

    static {
        ThreadContext.init();
    }

    static class 1 {
    }

    public static interface ContextStack
    extends Serializable,
    Collection<String> {
        public String pop();

        public String peek();

        public void push(String var1);

        public int getDepth();

        public List<String> asList();

        public void trim(int var1);

        public ContextStack copy();

        public ContextStack getImmutableStackOrNull();
    }

    private static class EmptyIterator<E>
    implements Iterator<E> {
        private EmptyIterator() {
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public E next() {
            throw new NoSuchElementException("This is an empty iterator!");
        }

        @Override
        public void remove() {
        }

        EmptyIterator(1 var1_1) {
            this();
        }
    }

    private static class EmptyThreadContextStack
    extends AbstractCollection<String>
    implements ThreadContextStack {
        private static final long serialVersionUID = 1L;
        private static final Iterator<String> EMPTY_ITERATOR = new EmptyIterator<String>(null);

        private EmptyThreadContextStack() {
        }

        @Override
        public String pop() {
            return null;
        }

        @Override
        public String peek() {
            return null;
        }

        @Override
        public void push(String string) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getDepth() {
            return 1;
        }

        @Override
        public List<String> asList() {
            return Collections.emptyList();
        }

        @Override
        public void trim(int n) {
        }

        @Override
        public boolean equals(Object object) {
            return object instanceof Collection && ((Collection)object).isEmpty();
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public ContextStack copy() {
            return this;
        }

        @Override
        public <T> T[] toArray(T[] TArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(String string) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return true;
        }

        @Override
        public boolean addAll(Collection<? extends String> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Iterator<String> iterator() {
            return EMPTY_ITERATOR;
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public ContextStack getImmutableStackOrNull() {
            return this;
        }

        @Override
        public boolean add(Object object) {
            return this.add((String)object);
        }

        EmptyThreadContextStack(1 var1_1) {
            this();
        }
    }
}

