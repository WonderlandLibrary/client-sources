/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number;

import com.ibm.icu.number.FormattedNumber;
import com.ibm.icu.number.LocalizedNumberFormatter;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.util.ULocale;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.text.AttributedCharacterIterator;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class LocalizedNumberFormatterAsFormat
extends Format {
    private static final long serialVersionUID = 1L;
    private final transient LocalizedNumberFormatter formatter;
    private final transient ULocale locale;

    public LocalizedNumberFormatterAsFormat(LocalizedNumberFormatter localizedNumberFormatter, ULocale uLocale) {
        this.formatter = localizedNumberFormatter;
        this.locale = uLocale;
    }

    @Override
    public StringBuffer format(Object object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (!(object instanceof Number)) {
            throw new IllegalArgumentException();
        }
        FormattedNumber formattedNumber = this.formatter.format((Number)object);
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        boolean bl = formattedNumber.nextFieldPosition(fieldPosition);
        if (bl && stringBuffer.length() != 0) {
            fieldPosition.setBeginIndex(fieldPosition.getBeginIndex() + stringBuffer.length());
            fieldPosition.setEndIndex(fieldPosition.getEndIndex() + stringBuffer.length());
        }
        formattedNumber.appendTo(stringBuffer);
        return stringBuffer;
    }

    @Override
    public AttributedCharacterIterator formatToCharacterIterator(Object object) {
        if (!(object instanceof Number)) {
            throw new IllegalArgumentException();
        }
        return this.formatter.format((Number)object).toCharacterIterator();
    }

    @Override
    public Object parseObject(String string, ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }

    public LocalizedNumberFormatter getNumberFormatter() {
        return this.formatter;
    }

    public int hashCode() {
        return this.formatter.hashCode();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null) {
            return true;
        }
        if (!(object instanceof LocalizedNumberFormatterAsFormat)) {
            return true;
        }
        return this.formatter.equals(((LocalizedNumberFormatterAsFormat)object).getNumberFormatter());
    }

    private Object writeReplace() throws ObjectStreamException {
        Proxy proxy = new Proxy();
        proxy.languageTag = this.locale.toLanguageTag();
        proxy.skeleton = this.formatter.toSkeleton();
        return proxy;
    }

    static class Proxy
    implements Externalizable {
        private static final long serialVersionUID = 1L;
        String languageTag;
        String skeleton;

        @Override
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeByte(0);
            objectOutput.writeUTF(this.languageTag);
            objectOutput.writeUTF(this.skeleton);
        }

        @Override
        public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
            objectInput.readByte();
            this.languageTag = objectInput.readUTF();
            this.skeleton = objectInput.readUTF();
        }

        private Object readResolve() throws ObjectStreamException {
            return NumberFormatter.forSkeleton(this.skeleton).locale(ULocale.forLanguageTag(this.languageTag)).toFormat();
        }
    }
}

