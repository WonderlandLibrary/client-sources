package net.minecraft.util;

import org.apache.commons.lang3.*;

public class RegistryNamespacedDefaultedByKey<K, V> extends RegistryNamespaced<K, V>
{
    private V defaultValue;
    private final K defaultValueKey;
    
    @Override
    public V getObjectById(final int n) {
        final V objectById = super.getObjectById(n);
        V defaultValue;
        if (objectById == null) {
            defaultValue = this.defaultValue;
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else {
            defaultValue = objectById;
        }
        return defaultValue;
    }
    
    @Override
    public void register(final int n, final K k, final V defaultValue) {
        if (this.defaultValueKey.equals(k)) {
            this.defaultValue = defaultValue;
        }
        super.register(n, k, defaultValue);
    }
    
    public void validateKey() {
        Validate.notNull((Object)this.defaultValueKey);
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
    
    public RegistryNamespacedDefaultedByKey(final K defaultValueKey) {
        this.defaultValueKey = defaultValueKey;
    }
    
    @Override
    public V getObject(final K k) {
        final V object = super.getObject(k);
        V defaultValue;
        if (object == null) {
            defaultValue = this.defaultValue;
            "".length();
            if (-1 == 3) {
                throw null;
            }
        }
        else {
            defaultValue = object;
        }
        return defaultValue;
    }
}
