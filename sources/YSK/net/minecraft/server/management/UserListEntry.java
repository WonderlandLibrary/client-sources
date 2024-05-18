package net.minecraft.server.management;

import com.google.gson.*;

public class UserListEntry<T>
{
    private final T value;
    
    protected UserListEntry(final T value, final JsonObject jsonObject) {
        this.value = value;
    }
    
    T getValue() {
        return this.value;
    }
    
    protected void onSerialization(final JsonObject jsonObject) {
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
            if (-1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public UserListEntry(final T value) {
        this.value = value;
    }
    
    boolean hasBanExpired() {
        return "".length() != 0;
    }
}
