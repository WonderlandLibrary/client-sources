package net.minecraft.block.properties;

import net.minecraft.util.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.*;

public class PropertyEnum<T extends Enum> extends PropertyHelper<T>
{
    private static final String[] I;
    private final ImmutableSet<T> allowedValues;
    private final Map<String, T> nameToValue;
    
    @Override
    public String getName(final Comparable comparable) {
        return this.getName((Enum)comparable);
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u001f8\u0018\u0018\u0011\"!\u0011L\u000e3!\u0001\t\u000br%\u0015\u001a\u001dr9\u001c\tX!,\u0019\tX<,\u0019\tXu", "RMtlx");
        PropertyEnum.I[" ".length()] = I("\u007f", "XTPce");
    }
    
    public static <T extends java.lang.Enum> PropertyEnum<T> create(final String s, final Class<T> clazz, final T... array) {
        return create(s, clazz, Lists.newArrayList((Object[])array));
    }
    
    @Override
    public String getName(final T t) {
        return ((IStringSerializable)t).getName();
    }
    
    public static <T extends java.lang.Enum> PropertyEnum<T> create(final String s, final Class<T> clazz) {
        return create(s, clazz, (com.google.common.base.Predicate<T>)Predicates.alwaysTrue());
    }
    
    static {
        I();
    }
    
    public static <T extends java.lang.Enum> PropertyEnum<T> create(final String s, final Class<T> clazz, final Predicate<T> predicate) {
        return create(s, clazz, Collections2.filter((Collection)Lists.newArrayList((Object[])clazz.getEnumConstants()), (Predicate)predicate));
    }
    
    protected PropertyEnum(final String s, final Class<T> clazz, final Collection<T> collection) {
        super(s, clazz);
        this.nameToValue = (Map<String, T>)Maps.newHashMap();
        this.allowedValues = (ImmutableSet<T>)ImmutableSet.copyOf((Collection)collection);
        final Iterator<T> iterator = collection.iterator();
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Enum enum1 = (Enum)iterator.next();
            final String name = ((IStringSerializable)enum1).getName();
            if (this.nameToValue.containsKey(name)) {
                throw new IllegalArgumentException(PropertyEnum.I["".length()] + name + PropertyEnum.I[" ".length()]);
            }
            this.nameToValue.put(name, (T)enum1);
        }
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
            if (3 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static <T extends java.lang.Enum> PropertyEnum<T> create(final String s, final Class<T> clazz, final Collection<T> collection) {
        return new PropertyEnum<T>(s, clazz, collection);
    }
    
    @Override
    public Collection<T> getAllowedValues() {
        return (Collection<T>)this.allowedValues;
    }
}
