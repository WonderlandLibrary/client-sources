package net.minecraft.util;

import java.util.*;
import com.google.common.collect.*;

public class ClassInheritanceMultiMap<T> extends AbstractSet<T>
{
    private static final Set<Class<?>> field_181158_a;
    private final Set<Class<?>> knownKeys;
    private static final String[] I;
    private final Class<T> baseClass;
    private final Map<Class<?>, List<T>> map;
    private final List<T> field_181745_e;
    
    public <S> Iterable<S> getByClass(final Class<S> clazz) {
        return new Iterable<S>(this, clazz) {
            private final Class val$clazz;
            final ClassInheritanceMultiMap this$0;
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (1 >= 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public Iterator<S> iterator() {
                final List list = ClassInheritanceMultiMap.access$0(this.this$0).get(this.this$0.func_181157_b(this.val$clazz));
                if (list == null) {
                    return (Iterator<S>)Iterators.emptyIterator();
                }
                return (Iterator<S>)Iterators.filter((Iterator)list.iterator(), this.val$clazz);
            }
        };
    }
    
    @Override
    public boolean remove(final Object o) {
        int n = "".length();
        final Iterator<Class<?>> iterator = this.knownKeys.iterator();
        "".length();
        if (1 >= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Class<?> clazz = iterator.next();
            if (clazz.isAssignableFrom(o.getClass())) {
                final List<T> list = this.map.get(clazz);
                if (list == null || !list.remove(o)) {
                    continue;
                }
                n = " ".length();
            }
        }
        return n != 0;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        field_181158_a = Sets.newHashSet();
    }
    
    protected void createLookup(final Class<?> clazz) {
        ClassInheritanceMultiMap.field_181158_a.add(clazz);
        final Iterator<T> iterator = this.field_181745_e.iterator();
        "".length();
        if (2 < -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final T t = iterator.next();
            if (clazz.isAssignableFrom(t.getClass())) {
                this.func_181743_a(t, clazz);
            }
        }
        this.knownKeys.add(clazz);
    }
    
    static Map access$0(final ClassInheritanceMultiMap classInheritanceMultiMap) {
        return classInheritanceMultiMap.map;
    }
    
    private void func_181743_a(final T t, final Class<?> clazz) {
        final List<T> list = this.map.get(clazz);
        if (list == null) {
            final Map<Class<?>, List<T>> map = this.map;
            final Object[] array = new Object[" ".length()];
            array["".length()] = t;
            map.put(clazz, Lists.newArrayList(array));
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        else {
            list.add(t);
        }
    }
    
    @Override
    public Iterator<T> iterator() {
        UnmodifiableIterator unmodifiableIterator;
        if (this.field_181745_e.isEmpty()) {
            unmodifiableIterator = Iterators.emptyIterator();
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            unmodifiableIterator = Iterators.unmodifiableIterator((Iterator)this.field_181745_e.iterator());
        }
        return (Iterator<T>)unmodifiableIterator;
    }
    
    protected Class<?> func_181157_b(final Class<?> clazz) {
        if (this.baseClass.isAssignableFrom(clazz)) {
            if (!this.knownKeys.contains(clazz)) {
                this.createLookup(clazz);
            }
            return clazz;
        }
        throw new IllegalArgumentException(ClassInheritanceMultiMap.I["".length()] + clazz);
    }
    
    @Override
    public int size() {
        return this.field_181745_e.size();
    }
    
    @Override
    public boolean add(final T t) {
        final Iterator<Class<?>> iterator = this.knownKeys.iterator();
        "".length();
        if (2 == 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Class<?> clazz = iterator.next();
            if (clazz.isAssignableFrom(t.getClass())) {
                this.func_181743_a(t, clazz);
            }
        }
        return " ".length() != 0;
    }
    
    @Override
    public boolean contains(final Object o) {
        return Iterators.contains((Iterator)this.getByClass(o.getClass()).iterator(), o);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0001\u001f6V\u0004e\u001b6\u001e\u0007e\u00187\u0006P1\u001fx\u0002\u0015$\u0002;\u0019P#\u001f*Q", "EpXqp");
    }
    
    public ClassInheritanceMultiMap(final Class<T> baseClass) {
        this.map = (Map<Class<?>, List<T>>)Maps.newHashMap();
        this.knownKeys = (Set<Class<?>>)Sets.newIdentityHashSet();
        this.field_181745_e = (List<T>)Lists.newArrayList();
        this.baseClass = baseClass;
        this.knownKeys.add(baseClass);
        this.map.put((Class<?>)baseClass, (List<?>)this.field_181745_e);
        final Iterator<Class<?>> iterator = ClassInheritanceMultiMap.field_181158_a.iterator();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            this.createLookup(iterator.next());
        }
    }
}
