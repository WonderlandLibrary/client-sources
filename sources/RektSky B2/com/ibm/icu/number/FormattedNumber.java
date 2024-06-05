package com.ibm.icu.number;

import com.ibm.icu.impl.number.*;
import com.ibm.icu.util.*;
import java.io.*;
import java.text.*;
import java.math.*;
import com.ibm.icu.text.*;
import java.util.*;

public class FormattedNumber
{
    final NumberStringBuilder nsb;
    final DecimalQuantity fq;
    
    FormattedNumber(final NumberStringBuilder nsb, final DecimalQuantity fq) {
        this.nsb = nsb;
        this.fq = fq;
    }
    
    @Override
    public String toString() {
        return this.nsb.toString();
    }
    
    public <A extends Appendable> A appendTo(final A appendable) {
        try {
            appendable.append(this.nsb);
        }
        catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
        return appendable;
    }
    
    @Deprecated
    public void populateFieldPosition(final FieldPosition fieldPosition) {
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        this.nextFieldPosition(fieldPosition);
    }
    
    public boolean nextFieldPosition(final FieldPosition fieldPosition) {
        this.fq.populateUFieldPosition(fieldPosition);
        return this.nsb.nextFieldPosition(fieldPosition);
    }
    
    @Deprecated
    public AttributedCharacterIterator getFieldIterator() {
        return this.nsb.toCharacterIterator();
    }
    
    public AttributedCharacterIterator toCharacterIterator() {
        return this.nsb.toCharacterIterator();
    }
    
    public BigDecimal toBigDecimal() {
        return this.fq.toBigDecimal();
    }
    
    @Deprecated
    public PluralRules.IFixedDecimal getFixedDecimal() {
        return this.fq;
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(this.nsb.toCharArray()) ^ Arrays.hashCode(this.nsb.toFieldArray()) ^ this.fq.toBigDecimal().hashCode();
    }
    
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof FormattedNumber)) {
            return false;
        }
        final FormattedNumber _other = (FormattedNumber)other;
        return Arrays.equals(this.nsb.toCharArray(), _other.nsb.toCharArray()) && Arrays.equals(this.nsb.toFieldArray(), _other.nsb.toFieldArray()) && this.fq.toBigDecimal().equals(_other.fq.toBigDecimal());
    }
}
