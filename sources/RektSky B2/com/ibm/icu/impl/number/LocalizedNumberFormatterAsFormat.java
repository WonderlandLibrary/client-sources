package com.ibm.icu.impl.number;

import com.ibm.icu.util.*;
import java.text.*;
import java.io.*;
import com.ibm.icu.number.*;

public class LocalizedNumberFormatterAsFormat extends Format
{
    private static final long serialVersionUID = 1L;
    private final transient LocalizedNumberFormatter formatter;
    private final transient ULocale locale;
    
    public LocalizedNumberFormatterAsFormat(final LocalizedNumberFormatter formatter, final ULocale locale) {
        this.formatter = formatter;
        this.locale = locale;
    }
    
    @Override
    public StringBuffer format(final Object obj, final StringBuffer toAppendTo, final FieldPosition pos) {
        if (!(obj instanceof Number)) {
            throw new IllegalArgumentException();
        }
        final FormattedNumber result = this.formatter.format((Number)obj);
        pos.setBeginIndex(0);
        pos.setEndIndex(0);
        final boolean found = result.nextFieldPosition(pos);
        if (found && toAppendTo.length() != 0) {
            pos.setBeginIndex(pos.getBeginIndex() + toAppendTo.length());
            pos.setEndIndex(pos.getEndIndex() + toAppendTo.length());
        }
        result.appendTo(toAppendTo);
        return toAppendTo;
    }
    
    @Override
    public AttributedCharacterIterator formatToCharacterIterator(final Object obj) {
        if (!(obj instanceof Number)) {
            throw new IllegalArgumentException();
        }
        return this.formatter.format((Number)obj).toCharacterIterator();
    }
    
    @Override
    public Object parseObject(final String source, final ParsePosition pos) {
        throw new UnsupportedOperationException();
    }
    
    public LocalizedNumberFormatter getNumberFormatter() {
        return this.formatter;
    }
    
    @Override
    public int hashCode() {
        return this.formatter.hashCode();
    }
    
    @Override
    public boolean equals(final Object other) {
        return this == other || (other != null && other instanceof LocalizedNumberFormatterAsFormat && this.formatter.equals(((LocalizedNumberFormatterAsFormat)other).getNumberFormatter()));
    }
    
    private Object writeReplace() throws ObjectStreamException {
        final Proxy proxy = new Proxy();
        proxy.languageTag = this.locale.toLanguageTag();
        proxy.skeleton = this.formatter.toSkeleton();
        return proxy;
    }
    
    static class Proxy implements Externalizable
    {
        private static final long serialVersionUID = 1L;
        String languageTag;
        String skeleton;
        
        public Proxy() {
        }
        
        @Override
        public void writeExternal(final ObjectOutput out) throws IOException {
            out.writeByte(0);
            out.writeUTF(this.languageTag);
            out.writeUTF(this.skeleton);
        }
        
        @Override
        public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
            in.readByte();
            this.languageTag = in.readUTF();
            this.skeleton = in.readUTF();
        }
        
        private Object readResolve() throws ObjectStreamException {
            return NumberFormatter.forSkeleton(this.skeleton).locale(ULocale.forLanguageTag(this.languageTag)).toFormat();
        }
    }
}
