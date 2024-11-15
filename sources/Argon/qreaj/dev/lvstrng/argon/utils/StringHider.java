package dev.lvstrng.argon.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class StringHider implements CharSequence {
    char[] buf1;
    char[] buf2;

    public StringHider(final String s) {
        final int length = s.length();
        final char[] key = new char[Math.min(length, 64)];
        final ThreadLocalRandom current = ThreadLocalRandom.current();
        int i = 0;
        while (i < key.length) {
            key[i] = (char) current.nextInt(65535);
            ++i;
        }
        this.buf1 = s.toCharArray();
        this.buf2 = key;
    }

    public StringHider(final char[] value, final char[] key) {
        this.buf1 = key;
        this.buf2 = value;
    }

    public static StringHider of(final String s) {
        return new StringHider(s);
    }

    public static StringHider of(final String encrypted, final String key) {
        return new StringHider(encrypted.toCharArray(), key.toCharArray());
    }

    private static void copy(final char[] array, final char[] array2, final int n2) {
        int i = 0;
        while (i < n2) {
            array[i] ^= array2[i % array2.length];
            ++i;
        }
    }

    @Override
    public int length() {
        return this.buf2.length;
    }

    @Override
    public char charAt(final int index) {
        return (char) (this.buf2[index] ^ this.buf1[index % this.buf1.length]);
    }

    @NotNull
    @Override
    public String toString() {
        final char[] copy = Arrays.copyOf(this.buf2, this.buf2.length);
        copy(copy, this.buf1, copy.length);
        return new String(copy).intern();
    }

    @NotNull
    @Override
    public CharSequence subSequence(final int start, final int end) {
        final int n2 = end - start;
        final char[] value = new char[n2];
        final char[] key = new char[n2];
        System.arraycopy(this.buf2, start, value, 0, n2);
        int i = 0;
        while (i < n2) {
            key[i] = this.buf1[(start + i) % this.buf1.length];
            ++i;
        }
        return new StringHider(value, key);
    }
}