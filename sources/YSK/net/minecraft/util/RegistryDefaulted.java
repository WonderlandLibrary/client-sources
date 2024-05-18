package net.minecraft.util;

public class RegistryDefaulted<K, V> extends RegistrySimple<K, V>
{
    private final V defaultObject;
    
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
    public V getObject(final K k) {
        final V object = super.getObject(k);
        V defaultObject;
        if (object == null) {
            defaultObject = this.defaultObject;
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        else {
            defaultObject = object;
        }
        return defaultObject;
    }
    
    public RegistryDefaulted(final V defaultObject) {
        this.defaultObject = defaultObject;
    }
}
