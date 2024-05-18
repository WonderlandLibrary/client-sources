package net.minecraft.util;

public abstract class LazyLoadBase<T>
{
    private boolean isLoaded;
    private T value;
    
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
            if (3 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public T getValue() {
        if (!this.isLoaded) {
            this.isLoaded = (" ".length() != 0);
            this.value = this.load();
        }
        return this.value;
    }
    
    protected abstract T load();
    
    public LazyLoadBase() {
        this.isLoaded = ("".length() != 0);
    }
}
