/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.text.NumberFormat;
import java.text.Format;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FormattedStringBuilder
implements CharSequence {
    public static final FormattedStringBuilder EMPTY;
    char[] chars;
    Format.Field[] fields;
    int zero;
    int length;
    private static final Map<Format.Field, Character> fieldToDebugChar;
    static final boolean $assertionsDisabled;

    public FormattedStringBuilder() {
        this(40);
    }

    public FormattedStringBuilder(int n) {
        this.chars = new char[n];
        this.fields = new Format.Field[n];
        this.zero = n / 2;
        this.length = 0;
    }

    public FormattedStringBuilder(FormattedStringBuilder formattedStringBuilder) {
        this.copyFrom(formattedStringBuilder);
    }

    public void copyFrom(FormattedStringBuilder formattedStringBuilder) {
        this.chars = Arrays.copyOf(formattedStringBuilder.chars, formattedStringBuilder.chars.length);
        this.fields = Arrays.copyOf(formattedStringBuilder.fields, formattedStringBuilder.fields.length);
        this.zero = formattedStringBuilder.zero;
        this.length = formattedStringBuilder.length;
    }

    @Override
    public int length() {
        return this.length;
    }

    public int codePointCount() {
        return Character.codePointCount(this, 0, this.length());
    }

    @Override
    public char charAt(int n) {
        if (!$assertionsDisabled && n < 0) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && n >= this.length) {
            throw new AssertionError();
        }
        return this.chars[this.zero + n];
    }

    public Format.Field fieldAt(int n) {
        if (!$assertionsDisabled && n < 0) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && n >= this.length) {
            throw new AssertionError();
        }
        return this.fields[this.zero + n];
    }

    public int getFirstCodePoint() {
        if (this.length == 0) {
            return 1;
        }
        return Character.codePointAt(this.chars, this.zero, this.zero + this.length);
    }

    public int getLastCodePoint() {
        if (this.length == 0) {
            return 1;
        }
        return Character.codePointBefore(this.chars, this.zero + this.length, this.zero);
    }

    public int codePointAt(int n) {
        return Character.codePointAt(this.chars, this.zero + n, this.zero + this.length);
    }

    public int codePointBefore(int n) {
        return Character.codePointBefore(this.chars, this.zero + n, this.zero);
    }

    public FormattedStringBuilder clear() {
        this.zero = this.getCapacity() / 2;
        this.length = 0;
        return this;
    }

    public int appendChar16(char c, Format.Field field) {
        return this.insertChar16(this.length, c, field);
    }

    public int insertChar16(int n, char c, Format.Field field) {
        int n2 = 1;
        int n3 = this.prepareForInsert(n, n2);
        this.chars[n3] = c;
        this.fields[n3] = field;
        return n2;
    }

    public int appendCodePoint(int n, Format.Field field) {
        return this.insertCodePoint(this.length, n, field);
    }

    public int insertCodePoint(int n, int n2, Format.Field field) {
        int n3 = Character.charCount(n2);
        int n4 = this.prepareForInsert(n, n3);
        Character.toChars(n2, this.chars, n4);
        this.fields[n4] = field;
        if (n3 == 2) {
            this.fields[n4 + 1] = field;
        }
        return n3;
    }

    public int append(CharSequence charSequence, Format.Field field) {
        return this.insert(this.length, charSequence, field);
    }

    public int insert(int n, CharSequence charSequence, Format.Field field) {
        if (charSequence.length() == 0) {
            return 1;
        }
        if (charSequence.length() == 1) {
            return this.insertCodePoint(n, charSequence.charAt(0), field);
        }
        return this.insert(n, charSequence, 0, charSequence.length(), field);
    }

    public int insert(int n, CharSequence charSequence, int n2, int n3, Format.Field field) {
        int n4 = n3 - n2;
        int n5 = this.prepareForInsert(n, n4);
        for (int i = 0; i < n4; ++i) {
            this.chars[n5 + i] = charSequence.charAt(n2 + i);
            this.fields[n5 + i] = field;
        }
        return n4;
    }

    public int splice(int n, int n2, CharSequence charSequence, int n3, int n4, Format.Field field) {
        int n5 = n4 - n3;
        int n6 = n2 - n;
        int n7 = n5 - n6;
        int n8 = n7 > 0 ? this.prepareForInsert(n, n7) : this.remove(n, -n7);
        for (int i = 0; i < n5; ++i) {
            this.chars[n8 + i] = charSequence.charAt(n3 + i);
            this.fields[n8 + i] = field;
        }
        return n7;
    }

    public int append(char[] cArray, Format.Field[] fieldArray) {
        return this.insert(this.length, cArray, fieldArray);
    }

    public int insert(int n, char[] cArray, Format.Field[] fieldArray) {
        if (!$assertionsDisabled && fieldArray != null && cArray.length != fieldArray.length) {
            throw new AssertionError();
        }
        int n2 = cArray.length;
        if (n2 == 0) {
            return 1;
        }
        int n3 = this.prepareForInsert(n, n2);
        for (int i = 0; i < n2; ++i) {
            this.chars[n3 + i] = cArray[i];
            this.fields[n3 + i] = fieldArray == null ? null : fieldArray[i];
        }
        return n2;
    }

    public int append(FormattedStringBuilder formattedStringBuilder) {
        return this.insert(this.length, formattedStringBuilder);
    }

    public int insert(int n, FormattedStringBuilder formattedStringBuilder) {
        if (this == formattedStringBuilder) {
            throw new IllegalArgumentException("Cannot call insert/append on myself");
        }
        int n2 = formattedStringBuilder.length;
        if (n2 == 0) {
            return 1;
        }
        int n3 = this.prepareForInsert(n, n2);
        for (int i = 0; i < n2; ++i) {
            this.chars[n3 + i] = formattedStringBuilder.charAt(i);
            this.fields[n3 + i] = formattedStringBuilder.fieldAt(i);
        }
        return n2;
    }

    private int prepareForInsert(int n, int n2) {
        if (n == 0 && this.zero - n2 >= 0) {
            this.zero -= n2;
            this.length += n2;
            return this.zero;
        }
        if (n == this.length && this.zero + this.length + n2 < this.getCapacity()) {
            this.length += n2;
            return this.zero + this.length - n2;
        }
        return this.prepareForInsertHelper(n, n2);
    }

    private int prepareForInsertHelper(int n, int n2) {
        int n3 = this.getCapacity();
        int n4 = this.zero;
        char[] cArray = this.chars;
        Format.Field[] fieldArray = this.fields;
        if (this.length + n2 > n3) {
            int n5 = (this.length + n2) * 2;
            int n6 = n5 / 2 - (this.length + n2) / 2;
            char[] cArray2 = new char[n5];
            Format.Field[] fieldArray2 = new Format.Field[n5];
            System.arraycopy(cArray, n4, cArray2, n6, n);
            System.arraycopy(cArray, n4 + n, cArray2, n6 + n + n2, this.length - n);
            System.arraycopy(fieldArray, n4, fieldArray2, n6, n);
            System.arraycopy(fieldArray, n4 + n, fieldArray2, n6 + n + n2, this.length - n);
            this.chars = cArray2;
            this.fields = fieldArray2;
            this.zero = n6;
            this.length += n2;
        } else {
            int n7 = n3 / 2 - (this.length + n2) / 2;
            System.arraycopy(cArray, n4, cArray, n7, this.length);
            System.arraycopy(cArray, n7 + n, cArray, n7 + n + n2, this.length - n);
            System.arraycopy(fieldArray, n4, fieldArray, n7, this.length);
            System.arraycopy(fieldArray, n7 + n, fieldArray, n7 + n + n2, this.length - n);
            this.zero = n7;
            this.length += n2;
        }
        return this.zero + n;
    }

    private int remove(int n, int n2) {
        int n3 = n + this.zero;
        System.arraycopy(this.chars, n3 + n2, this.chars, n3, this.length - n - n2);
        System.arraycopy(this.fields, n3 + n2, this.fields, n3, this.length - n - n2);
        this.length -= n2;
        return n3;
    }

    private int getCapacity() {
        return this.chars.length;
    }

    @Override
    @Deprecated
    public CharSequence subSequence(int n, int n2) {
        if (!$assertionsDisabled && n < 0) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && n2 > this.length) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && n2 < n) {
            throw new AssertionError();
        }
        FormattedStringBuilder formattedStringBuilder = new FormattedStringBuilder(this);
        formattedStringBuilder.zero = this.zero + n;
        formattedStringBuilder.length = n2 - n;
        return formattedStringBuilder;
    }

    public String subString(int n, int n2) {
        if (n < 0 || n2 > this.length || n2 < n) {
            throw new IndexOutOfBoundsException();
        }
        return new String(this.chars, n + this.zero, n2 - n);
    }

    @Override
    public String toString() {
        return new String(this.chars, this.zero, this.length);
    }

    public String toDebugString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<FormattedStringBuilder [");
        stringBuilder.append(this.toString());
        stringBuilder.append("] [");
        for (int i = this.zero; i < this.zero + this.length; ++i) {
            if (this.fields[i] == null) {
                stringBuilder.append('n');
                continue;
            }
            if (fieldToDebugChar.containsKey(this.fields[i])) {
                stringBuilder.append(fieldToDebugChar.get(this.fields[i]));
                continue;
            }
            stringBuilder.append('?');
        }
        stringBuilder.append("]>");
        return stringBuilder.toString();
    }

    public char[] toCharArray() {
        return Arrays.copyOfRange(this.chars, this.zero, this.zero + this.length);
    }

    public Format.Field[] toFieldArray() {
        return Arrays.copyOfRange(this.fields, this.zero, this.zero + this.length);
    }

    public boolean contentEquals(char[] cArray, Format.Field[] fieldArray) {
        if (cArray.length != this.length) {
            return true;
        }
        if (fieldArray.length != this.length) {
            return true;
        }
        for (int i = 0; i < this.length; ++i) {
            if (this.chars[this.zero + i] != cArray[i]) {
                return true;
            }
            if (this.fields[this.zero + i] == fieldArray[i]) continue;
            return true;
        }
        return false;
    }

    public boolean contentEquals(FormattedStringBuilder formattedStringBuilder) {
        if (this.length != formattedStringBuilder.length) {
            return true;
        }
        for (int i = 0; i < this.length; ++i) {
            if (this.charAt(i) == formattedStringBuilder.charAt(i) && this.fieldAt(i) == formattedStringBuilder.fieldAt(i)) continue;
            return true;
        }
        return false;
    }

    public int hashCode() {
        throw new UnsupportedOperationException("Don't call #hashCode() or #equals() on a mutable.");
    }

    public boolean equals(Object object) {
        throw new UnsupportedOperationException("Don't call #hashCode() or #equals() on a mutable.");
    }

    static {
        $assertionsDisabled = !FormattedStringBuilder.class.desiredAssertionStatus();
        EMPTY = new FormattedStringBuilder();
        fieldToDebugChar = new HashMap<Format.Field, Character>();
        fieldToDebugChar.put(NumberFormat.Field.SIGN, Character.valueOf('-'));
        fieldToDebugChar.put(NumberFormat.Field.INTEGER, Character.valueOf('i'));
        fieldToDebugChar.put(NumberFormat.Field.FRACTION, Character.valueOf('f'));
        fieldToDebugChar.put(NumberFormat.Field.EXPONENT, Character.valueOf('e'));
        fieldToDebugChar.put(NumberFormat.Field.EXPONENT_SIGN, Character.valueOf('+'));
        fieldToDebugChar.put(NumberFormat.Field.EXPONENT_SYMBOL, Character.valueOf('E'));
        fieldToDebugChar.put(NumberFormat.Field.DECIMAL_SEPARATOR, Character.valueOf('.'));
        fieldToDebugChar.put(NumberFormat.Field.GROUPING_SEPARATOR, Character.valueOf(','));
        fieldToDebugChar.put(NumberFormat.Field.PERCENT, Character.valueOf('%'));
        fieldToDebugChar.put(NumberFormat.Field.PERMILLE, Character.valueOf('\u2030'));
        fieldToDebugChar.put(NumberFormat.Field.CURRENCY, Character.valueOf('$'));
        fieldToDebugChar.put(NumberFormat.Field.MEASURE_UNIT, Character.valueOf('u'));
        fieldToDebugChar.put(NumberFormat.Field.COMPACT, Character.valueOf('C'));
    }
}

