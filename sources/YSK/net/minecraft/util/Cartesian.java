package net.minecraft.util;

import com.google.common.base.*;
import java.lang.reflect.*;
import com.google.common.collect.*;
import java.util.*;

public class Cartesian
{
    private static <T> Iterable<List<T>> arraysAsLists(final Iterable<Object[]> iterable) {
        return (Iterable<List<T>>)Iterables.transform((Iterable)iterable, (Function)new GetList(null));
    }
    
    private static <T> T[] createArray(final Class<? super T> clazz, final int n) {
        return (T[])Array.newInstance(clazz, n);
    }
    
    static Object[] access$0(final Class clazz, final int n) {
        return createArray(clazz, n);
    }
    
    private static <T> T[] toArray(final Class<? super T> clazz, final Iterable<? extends T> iterable) {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<? extends T> iterator = iterable.iterator();
        "".length();
        if (2 <= 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            arrayList.add(iterator.next());
        }
        return arrayList.toArray(createArray((Class<? super Object>)clazz, arrayList.size()));
    }
    
    public static <T> Iterable<T[]> cartesianProduct(final Class<T> clazz, final Iterable<? extends Iterable<? extends T>> iterable) {
        return new Product<T>(clazz, toArray((Class<? super Iterable>)Iterable.class, (Iterable<? extends Iterable>)iterable), null);
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
            if (3 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static <T> Iterable<List<T>> cartesianProduct(final Iterable<? extends Iterable<? extends T>> iterable) {
        return (Iterable<List<T>>)arraysAsLists(cartesianProduct(Object.class, iterable));
    }
    
    static class GetList<T> implements Function<Object[], List<T>>
    {
        private GetList() {
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
                if (4 == 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public Object apply(final Object o) {
            return this.apply((Object[])o);
        }
        
        public List<T> apply(final Object[] array) {
            return Arrays.asList((T[])array);
        }
        
        GetList(final GetList list) {
            this();
        }
    }
    
    static class Product<T> implements Iterable<T[]>
    {
        private final Iterable<? extends T>[] iterables;
        private final Class<T> clazz;
        
        @Override
        public Iterator<T[]> iterator() {
            Object iterator;
            if (this.iterables.length <= 0) {
                iterator = Collections.singletonList(Cartesian.access$0(this.clazz, "".length())).iterator();
                "".length();
                if (3 >= 4) {
                    throw null;
                }
            }
            else {
                iterator = new ProductIterator(this.clazz, this.iterables, null);
            }
            return (Iterator<T[]>)iterator;
        }
        
        private Product(final Class<T> clazz, final Iterable<? extends T>[] iterables) {
            this.clazz = clazz;
            this.iterables = iterables;
        }
        
        Product(final Class clazz, final Iterable[] array, final Product product) {
            this(clazz, array);
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
                if (-1 != -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static class ProductIterator<T> extends UnmodifiableIterator<T[]>
        {
            private final T[] results;
            private final Iterable<? extends T>[] iterables;
            private final Iterator<? extends T>[] iterators;
            private int index;
            
            public Object next() {
                return this.next();
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
                    if (4 <= 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            private void endOfData() {
                this.index = -" ".length();
                Arrays.fill(this.iterators, null);
                Arrays.fill(this.results, null);
            }
            
            private ProductIterator(final Class<T> clazz, final Iterable<? extends T>[] iterables) {
                this.index = -"  ".length();
                this.iterables = iterables;
                this.iterators = (Iterator<? extends T>[])Cartesian.access$0(Iterator.class, this.iterables.length);
                int i = "".length();
                "".length();
                if (false) {
                    throw null;
                }
                while (i < this.iterables.length) {
                    this.iterators[i] = iterables[i].iterator();
                    ++i;
                }
                this.results = Cartesian.access$0(clazz, this.iterators.length);
            }
            
            public T[] next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                while (this.index < this.iterators.length) {
                    this.results[this.index] = this.iterators[this.index].next();
                    this.index += " ".length();
                }
                return (T[])this.results.clone();
            }
            
            public boolean hasNext() {
                if (this.index == -"  ".length()) {
                    this.index = "".length();
                    final Iterator<? extends T>[] iterators;
                    final int length = (iterators = this.iterators).length;
                    int i = "".length();
                    "".length();
                    if (-1 == 3) {
                        throw null;
                    }
                    while (i < length) {
                        if (!iterators[i].hasNext()) {
                            this.endOfData();
                            "".length();
                            if (true != true) {
                                throw null;
                            }
                            break;
                        }
                        else {
                            ++i;
                        }
                    }
                    return " ".length() != 0;
                }
                else {
                    if (this.index >= this.iterators.length) {
                        this.index = this.iterators.length - " ".length();
                        "".length();
                        if (false) {
                            throw null;
                        }
                        while (this.index >= 0) {
                            if (this.iterators[this.index].hasNext()) {
                                "".length();
                                if (-1 == 1) {
                                    throw null;
                                }
                                break;
                            }
                            else if (this.index == 0) {
                                this.endOfData();
                                "".length();
                                if (1 < -1) {
                                    throw null;
                                }
                                break;
                            }
                            else {
                                final Iterator<? extends T> iterator = this.iterables[this.index].iterator();
                                this.iterators[this.index] = iterator;
                                if (!iterator.hasNext()) {
                                    this.endOfData();
                                    "".length();
                                    if (-1 >= 3) {
                                        throw null;
                                    }
                                    break;
                                }
                                else {
                                    this.index -= " ".length();
                                }
                            }
                        }
                    }
                    if (this.index >= 0) {
                        return " ".length() != 0;
                    }
                    return "".length() != 0;
                }
            }
            
            ProductIterator(final Class clazz, final Iterable[] array, final ProductIterator productIterator) {
                this(clazz, array);
            }
        }
    }
}
