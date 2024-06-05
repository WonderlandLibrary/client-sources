package com.ibm.icu.impl.number;

import com.ibm.icu.text.*;
import java.text.*;
import java.util.*;

public class NumberStringBuilder implements CharSequence
{
    public static final NumberStringBuilder EMPTY;
    private char[] chars;
    private NumberFormat.Field[] fields;
    private int zero;
    private int length;
    private static final Map<NumberFormat.Field, Character> fieldToDebugChar;
    
    public NumberStringBuilder() {
        this(40);
    }
    
    public NumberStringBuilder(final int capacity) {
        this.chars = new char[capacity];
        this.fields = new NumberFormat.Field[capacity];
        this.zero = capacity / 2;
        this.length = 0;
    }
    
    public NumberStringBuilder(final NumberStringBuilder source) {
        this.copyFrom(source);
    }
    
    public void copyFrom(final NumberStringBuilder source) {
        this.chars = Arrays.copyOf(source.chars, source.chars.length);
        this.fields = Arrays.copyOf(source.fields, source.fields.length);
        this.zero = source.zero;
        this.length = source.length;
    }
    
    @Override
    public int length() {
        return this.length;
    }
    
    public int codePointCount() {
        return Character.codePointCount(this, 0, this.length());
    }
    
    @Override
    public char charAt(final int index) {
        assert index >= 0;
        assert index < this.length;
        return this.chars[this.zero + index];
    }
    
    public NumberFormat.Field fieldAt(final int index) {
        assert index >= 0;
        assert index < this.length;
        return this.fields[this.zero + index];
    }
    
    public int getFirstCodePoint() {
        if (this.length == 0) {
            return -1;
        }
        return Character.codePointAt(this.chars, this.zero, this.zero + this.length);
    }
    
    public int getLastCodePoint() {
        if (this.length == 0) {
            return -1;
        }
        return Character.codePointBefore(this.chars, this.zero + this.length, this.zero);
    }
    
    public int codePointAt(final int index) {
        return Character.codePointAt(this.chars, this.zero + index, this.zero + this.length);
    }
    
    public int codePointBefore(final int index) {
        return Character.codePointBefore(this.chars, this.zero + index, this.zero);
    }
    
    public NumberStringBuilder clear() {
        this.zero = this.getCapacity() / 2;
        this.length = 0;
        return this;
    }
    
    public int appendCodePoint(final int codePoint, final NumberFormat.Field field) {
        return this.insertCodePoint(this.length, codePoint, field);
    }
    
    public int insertCodePoint(final int index, final int codePoint, final NumberFormat.Field field) {
        final int count = Character.charCount(codePoint);
        final int position = this.prepareForInsert(index, count);
        Character.toChars(codePoint, this.chars, position);
        this.fields[position] = field;
        if (count == 2) {
            this.fields[position + 1] = field;
        }
        return count;
    }
    
    public int append(final CharSequence sequence, final NumberFormat.Field field) {
        return this.insert(this.length, sequence, field);
    }
    
    public int insert(final int index, final CharSequence sequence, final NumberFormat.Field field) {
        if (sequence.length() == 0) {
            return 0;
        }
        if (sequence.length() == 1) {
            return this.insertCodePoint(index, sequence.charAt(0), field);
        }
        return this.insert(index, sequence, 0, sequence.length(), field);
    }
    
    public int insert(final int index, final CharSequence sequence, final int start, final int end, final NumberFormat.Field field) {
        final int count = end - start;
        final int position = this.prepareForInsert(index, count);
        for (int i = 0; i < count; ++i) {
            this.chars[position + i] = sequence.charAt(start + i);
            this.fields[position + i] = field;
        }
        return count;
    }
    
    public int splice(final int startThis, final int endThis, final CharSequence sequence, final int startOther, final int endOther, final NumberFormat.Field field) {
        final int thisLength = endThis - startThis;
        final int otherLength = endOther - startOther;
        final int count = otherLength - thisLength;
        int position;
        if (count > 0) {
            position = this.prepareForInsert(startThis, count);
        }
        else {
            position = this.remove(startThis, -count);
        }
        for (int i = 0; i < otherLength; ++i) {
            this.chars[position + i] = sequence.charAt(startOther + i);
            this.fields[position + i] = field;
        }
        return count;
    }
    
    public int append(final char[] chars, final NumberFormat.Field[] fields) {
        return this.insert(this.length, chars, fields);
    }
    
    public int insert(final int index, final char[] chars, final NumberFormat.Field[] fields) {
        assert chars.length == fields.length;
        final int count = chars.length;
        if (count == 0) {
            return 0;
        }
        final int position = this.prepareForInsert(index, count);
        for (int i = 0; i < count; ++i) {
            this.chars[position + i] = chars[i];
            this.fields[position + i] = ((fields == null) ? null : fields[i]);
        }
        return count;
    }
    
    public int append(final NumberStringBuilder other) {
        return this.insert(this.length, other);
    }
    
    public int insert(final int index, final NumberStringBuilder other) {
        if (this == other) {
            throw new IllegalArgumentException("Cannot call insert/append on myself");
        }
        final int count = other.length;
        if (count == 0) {
            return 0;
        }
        final int position = this.prepareForInsert(index, count);
        for (int i = 0; i < count; ++i) {
            this.chars[position + i] = other.charAt(i);
            this.fields[position + i] = other.fieldAt(i);
        }
        return count;
    }
    
    private int prepareForInsert(final int index, final int count) {
        if (index == 0 && this.zero - count >= 0) {
            this.zero -= count;
            this.length += count;
            return this.zero;
        }
        if (index == this.length && this.zero + this.length + count < this.getCapacity()) {
            this.length += count;
            return this.zero + this.length - count;
        }
        return this.prepareForInsertHelper(index, count);
    }
    
    private int prepareForInsertHelper(final int index, final int count) {
        final int oldCapacity = this.getCapacity();
        final int oldZero = this.zero;
        final char[] oldChars = this.chars;
        final NumberFormat.Field[] oldFields = this.fields;
        if (this.length + count > oldCapacity) {
            final int newCapacity = (this.length + count) * 2;
            final int newZero = newCapacity / 2 - (this.length + count) / 2;
            final char[] newChars = new char[newCapacity];
            final NumberFormat.Field[] newFields = new NumberFormat.Field[newCapacity];
            System.arraycopy(oldChars, oldZero, newChars, newZero, index);
            System.arraycopy(oldChars, oldZero + index, newChars, newZero + index + count, this.length - index);
            System.arraycopy(oldFields, oldZero, newFields, newZero, index);
            System.arraycopy(oldFields, oldZero + index, newFields, newZero + index + count, this.length - index);
            this.chars = newChars;
            this.fields = newFields;
            this.zero = newZero;
            this.length += count;
        }
        else {
            final int newZero2 = oldCapacity / 2 - (this.length + count) / 2;
            System.arraycopy(oldChars, oldZero, oldChars, newZero2, this.length);
            System.arraycopy(oldChars, newZero2 + index, oldChars, newZero2 + index + count, this.length - index);
            System.arraycopy(oldFields, oldZero, oldFields, newZero2, this.length);
            System.arraycopy(oldFields, newZero2 + index, oldFields, newZero2 + index + count, this.length - index);
            this.zero = newZero2;
            this.length += count;
        }
        return this.zero + index;
    }
    
    private int remove(final int index, final int count) {
        final int position = index + this.zero;
        System.arraycopy(this.chars, position + count, this.chars, position, this.length - index - count);
        System.arraycopy(this.fields, position + count, this.fields, position, this.length - index - count);
        this.length -= count;
        return position;
    }
    
    private int getCapacity() {
        return this.chars.length;
    }
    
    @Override
    public CharSequence subSequence(final int start, final int end) {
        if (start < 0 || end > this.length || end < start) {
            throw new IndexOutOfBoundsException();
        }
        final NumberStringBuilder other = new NumberStringBuilder(this);
        other.zero = this.zero + start;
        other.length = end - start;
        return other;
    }
    
    @Override
    public String toString() {
        return new String(this.chars, this.zero, this.length);
    }
    
    public String toDebugString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("<NumberStringBuilder [");
        sb.append(this.toString());
        sb.append("] [");
        for (int i = this.zero; i < this.zero + this.length; ++i) {
            if (this.fields[i] == null) {
                sb.append('n');
            }
            else {
                sb.append(NumberStringBuilder.fieldToDebugChar.get(this.fields[i]));
            }
        }
        sb.append("]>");
        return sb.toString();
    }
    
    public char[] toCharArray() {
        return Arrays.copyOfRange(this.chars, this.zero, this.zero + this.length);
    }
    
    public NumberFormat.Field[] toFieldArray() {
        return Arrays.copyOfRange(this.fields, this.zero, this.zero + this.length);
    }
    
    public boolean contentEquals(final char[] chars, final NumberFormat.Field[] fields) {
        if (chars.length != this.length) {
            return false;
        }
        if (fields.length != this.length) {
            return false;
        }
        for (int i = 0; i < this.length; ++i) {
            if (this.chars[this.zero + i] != chars[i]) {
                return false;
            }
            if (this.fields[this.zero + i] != fields[i]) {
                return false;
            }
        }
        return true;
    }
    
    public boolean contentEquals(final NumberStringBuilder other) {
        if (this.length != other.length) {
            return false;
        }
        for (int i = 0; i < this.length; ++i) {
            if (this.charAt(i) != other.charAt(i) || this.fieldAt(i) != other.fieldAt(i)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Don't call #hashCode() or #equals() on a mutable.");
    }
    
    @Override
    public boolean equals(final Object other) {
        throw new UnsupportedOperationException("Don't call #hashCode() or #equals() on a mutable.");
    }
    
    public boolean nextFieldPosition(final FieldPosition fp) {
        Format.Field rawField = fp.getFieldAttribute();
        if (rawField == null) {
            if (fp.getField() == 0) {
                rawField = NumberFormat.Field.INTEGER;
            }
            else {
                if (fp.getField() != 1) {
                    return false;
                }
                rawField = NumberFormat.Field.FRACTION;
            }
        }
        if (!(rawField instanceof NumberFormat.Field)) {
            throw new IllegalArgumentException("You must pass an instance of com.ibm.icu.text.NumberFormat.Field as your FieldPosition attribute.  You passed: " + rawField.getClass().toString());
        }
        final NumberFormat.Field field = (NumberFormat.Field)rawField;
        boolean seenStart = false;
        int fractionStart = -1;
        final int startIndex = fp.getEndIndex();
        for (int i = this.zero + startIndex; i <= this.zero + this.length; ++i) {
            final NumberFormat.Field _field = (i < this.zero + this.length) ? this.fields[i] : null;
            if (seenStart && field != _field) {
                if (field != NumberFormat.Field.INTEGER || _field != NumberFormat.Field.GROUPING_SEPARATOR) {
                    fp.setEndIndex(i - this.zero);
                    break;
                }
            }
            else {
                if (!seenStart && field == _field) {
                    fp.setBeginIndex(i - this.zero);
                    seenStart = true;
                }
                if (_field == NumberFormat.Field.INTEGER || _field == NumberFormat.Field.DECIMAL_SEPARATOR) {
                    fractionStart = i - this.zero + 1;
                }
            }
        }
        if (field == NumberFormat.Field.FRACTION && !seenStart && fractionStart != -1) {
            fp.setBeginIndex(fractionStart);
            fp.setEndIndex(fractionStart);
        }
        return seenStart;
    }
    
    public AttributedCharacterIterator toCharacterIterator() {
        final AttributedString as = new AttributedString(this.toString());
        NumberFormat.Field current = null;
        int currentStart = -1;
        for (int i = 0; i < this.length; ++i) {
            final NumberFormat.Field field = this.fields[i + this.zero];
            if (current == NumberFormat.Field.INTEGER && field == NumberFormat.Field.GROUPING_SEPARATOR) {
                as.addAttribute(NumberFormat.Field.GROUPING_SEPARATOR, NumberFormat.Field.GROUPING_SEPARATOR, i, i + 1);
            }
            else if (current != field) {
                if (current != null) {
                    as.addAttribute(current, current, currentStart, i);
                }
                current = field;
                currentStart = i;
            }
        }
        if (current != null) {
            as.addAttribute(current, current, currentStart, this.length);
        }
        return as.getIterator();
    }
    
    static {
        EMPTY = new NumberStringBuilder();
        (fieldToDebugChar = new HashMap<NumberFormat.Field, Character>()).put(NumberFormat.Field.SIGN, '-');
        NumberStringBuilder.fieldToDebugChar.put(NumberFormat.Field.INTEGER, 'i');
        NumberStringBuilder.fieldToDebugChar.put(NumberFormat.Field.FRACTION, 'f');
        NumberStringBuilder.fieldToDebugChar.put(NumberFormat.Field.EXPONENT, 'e');
        NumberStringBuilder.fieldToDebugChar.put(NumberFormat.Field.EXPONENT_SIGN, '+');
        NumberStringBuilder.fieldToDebugChar.put(NumberFormat.Field.EXPONENT_SYMBOL, 'E');
        NumberStringBuilder.fieldToDebugChar.put(NumberFormat.Field.DECIMAL_SEPARATOR, '.');
        NumberStringBuilder.fieldToDebugChar.put(NumberFormat.Field.GROUPING_SEPARATOR, ',');
        NumberStringBuilder.fieldToDebugChar.put(NumberFormat.Field.PERCENT, '%');
        NumberStringBuilder.fieldToDebugChar.put(NumberFormat.Field.PERMILLE, '\u2030');
        NumberStringBuilder.fieldToDebugChar.put(NumberFormat.Field.CURRENCY, '$');
    }
}
